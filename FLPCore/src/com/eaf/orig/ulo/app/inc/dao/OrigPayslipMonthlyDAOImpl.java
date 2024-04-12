package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;

public class OrigPayslipMonthlyDAOImpl extends OrigObjectDAO implements
		OrigPayslipMonthlyDAO {
	public OrigPayslipMonthlyDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPayslipMonthlyDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPayslipMonthlyM(PayslipMonthlyDataM payslipMonthlyM)
			throws ApplicationException {
		logger.debug("payslipMonthlyM.getPayslipMonthlyId() :"+payslipMonthlyM.getPayslipMonthlyId());
	    if(Util.empty(payslipMonthlyM.getPayslipMonthlyId())){
			String payslipMonthlyId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_PAYSLIP_MONTHLY_PK);
			payslipMonthlyM.setPayslipMonthlyId(payslipMonthlyId);
			payslipMonthlyM.setCreateBy(userId);
	    }
	    payslipMonthlyM.setUpdateBy(userId);
		createTableINC_PAYSLIP_MONTHLY(payslipMonthlyM);
		
		ArrayList<PayslipMonthlyDetailDataM> detailList = payslipMonthlyM.getPayslipMonthlyDetails();
		if(detailList != null && !detailList.isEmpty()) {
			OrigPayslipMonthlyDetailDAO detailDAO = ORIGDAOFactory.getPayslipMonthlyDetailDAO(userId);
			for(PayslipMonthlyDetailDataM detailM: detailList) {
				detailM.setPayslipMonthlyId(payslipMonthlyM.getPayslipMonthlyId());
				detailDAO.createOrigPayslipMonthlyDetailM(detailM);
			}
		}
	}

	private void createTableINC_PAYSLIP_MONTHLY(
			PayslipMonthlyDataM payslipMonthlyM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_PAYSLIP_MONTHLY ");
			sql.append("( PAYSLIP_MONTHLY_ID, PAYSLIP_ID, INCOME_TYPE,  ");
			sql.append(" INCOME_DESC, INCOME_OTH_DESC,  ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,  ?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payslipMonthlyM.getPayslipMonthlyId());
			ps.setString(cnt++, payslipMonthlyM.getPayslipId());		
			ps.setString(cnt++, payslipMonthlyM.getIncomeType());
			
			ps.setString(cnt++, payslipMonthlyM.getIncomeDesc());
			ps.setString(cnt++, payslipMonthlyM.getIncomeOthDesc());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payslipMonthlyM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payslipMonthlyM.getUpdateBy());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigPayslipMonthlyM(String payslipId,
			String payslipMonthlyId) throws ApplicationException {
		OrigPayslipMonthlyDetailDAO detailDAO = ORIGDAOFactory.getPayslipMonthlyDetailDAO();
		if(Util.empty(payslipMonthlyId)) {
			ArrayList<String> monthlyForPayslip = selectMonthlyIdINC_PAYSLIP_MONTHLY(payslipId);
			if(!Util.empty(monthlyForPayslip)) {
				for(String monthlyID: monthlyForPayslip) {
					detailDAO.deleteOrigPayslipMonthlyDetailM(monthlyID, null);
				}
			}
		} else {
			detailDAO.deleteOrigPayslipMonthlyDetailM(payslipMonthlyId, null);
		}
		
		deleteTableINC_PAYSLIP_MONTHLY(payslipId, payslipMonthlyId);
	}

	private ArrayList<String> selectMonthlyIdINC_PAYSLIP_MONTHLY(String payslipId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> monthlyList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYSLIP_MONTHLY_ID ");
			sql.append(" FROM INC_PAYSLIP_MONTHLY WHERE PAYSLIP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, payslipId);

			rs = ps.executeQuery();

			while (rs.next()) {
				monthlyList.add(rs.getString("PAYSLIP_MONTHLY_ID"));
			}

			return monthlyList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void deleteTableINC_PAYSLIP_MONTHLY(String payslipId,
			String payslipMonthlyId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_PAYSLIP_MONTHLY ");
			sql.append(" WHERE PAYSLIP_ID = ? ");
			if(!Util.empty(payslipMonthlyId)) {
				sql.append(" AND PAYSLIP_MONTHLY_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, payslipId);
			if(!Util.empty(payslipMonthlyId)) {
				ps.setString(2, payslipMonthlyId);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<PayslipMonthlyDataM> loadOrigPayslipMonthlyM(
			String payslipId) throws ApplicationException {
		ArrayList<PayslipMonthlyDataM> result = selectTableINC_PAYSLIP_MONTHLY(payslipId);

		if(!Util.empty(result)) {
			OrigPayslipMonthlyDetailDAO detailDAO = ORIGDAOFactory.getPayslipMonthlyDetailDAO();
			for(PayslipMonthlyDataM payMonthlyM: result) {
				ArrayList<PayslipMonthlyDetailDataM> detailList = detailDAO.loadOrigPayslipMonthlyDetailM(
										payMonthlyM.getPayslipMonthlyId());
				if(!Util.empty(detailList)) {
					payMonthlyM.setPayslipMonthlyDetails(detailList);					
				}
			}
		}
		return result;
	}

	private ArrayList<PayslipMonthlyDataM> selectTableINC_PAYSLIP_MONTHLY(
			String payslipId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PayslipMonthlyDataM> payslipMonthlyList = new ArrayList<PayslipMonthlyDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYSLIP_MONTHLY_ID, PAYSLIP_ID, INCOME_TYPE,  ");
			sql.append(" INCOME_DESC, INCOME_OTH_DESC, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_PAYSLIP_MONTHLY WHERE PAYSLIP_ID = ? ");
			sql.append(" ORDER BY PAYSLIP_MONTHLY_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, payslipId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PayslipMonthlyDataM payslipMonthlyM = new PayslipMonthlyDataM();
				payslipMonthlyM.setSeq(payslipMonthlyList.size()+1);
				payslipMonthlyM.setPayslipMonthlyId(rs.getString("PAYSLIP_MONTHLY_ID"));
				payslipMonthlyM.setPayslipId(rs.getString("PAYSLIP_ID"));
				payslipMonthlyM.setIncomeType(rs.getString("INCOME_TYPE"));
				
				payslipMonthlyM.setIncomeDesc(rs.getString("INCOME_DESC"));
				payslipMonthlyM.setIncomeOthDesc(rs.getString("INCOME_OTH_DESC"));
				
				payslipMonthlyM.setCreateBy(rs.getString("CREATE_BY"));
				payslipMonthlyM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				payslipMonthlyM.setUpdateBy(rs.getString("UPDATE_BY"));
				payslipMonthlyM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				payslipMonthlyList.add(payslipMonthlyM);
			}

			return payslipMonthlyList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigPayslipMonthlyM(
			PayslipMonthlyDataM payslipMonthlyM) throws ApplicationException {
		int returnRows = 0;
		payslipMonthlyM.setUpdateBy(userId);
		returnRows = updateTableINC_PAYSLIP_MONTHLY(payslipMonthlyM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_PAYSLIP_MONTHLY then call Insert method");
			payslipMonthlyM.setCreateBy(userId);
			createOrigPayslipMonthlyM(payslipMonthlyM);
		} else {
		
			ArrayList<PayslipMonthlyDetailDataM> detailList = payslipMonthlyM.getPayslipMonthlyDetails();
			OrigPayslipMonthlyDetailDAO detailDAO = ORIGDAOFactory.getPayslipMonthlyDetailDAO(userId);
			if(detailList != null && !detailList.isEmpty()) {
				for(PayslipMonthlyDetailDataM detailM: detailList) {
					detailM.setPayslipMonthlyId(payslipMonthlyM.getPayslipMonthlyId());
					detailDAO.saveUpdateOrigPayslipMonthlyDetailM(detailM);
				}
			}
			detailDAO.deleteNotInKeyPayslipMonthlyDetail(detailList, payslipMonthlyM.getPayslipMonthlyId());
		}
	}

	private int updateTableINC_PAYSLIP_MONTHLY(
			PayslipMonthlyDataM payslipMonthlyM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_PAYSLIP_MONTHLY ");
			sql.append(" SET INCOME_TYPE = ?, INCOME_DESC = ?, INCOME_OTH_DESC = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE PAYSLIP_MONTHLY_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payslipMonthlyM.getIncomeType());
			ps.setString(cnt++, payslipMonthlyM.getIncomeDesc());
			ps.setString(cnt++, payslipMonthlyM.getIncomeOthDesc());
			
			ps.setString(cnt++, payslipMonthlyM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, payslipMonthlyM.getPayslipMonthlyId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyPayslipMonthly(
			ArrayList<PayslipMonthlyDataM> payslipMonthlyList, String payslipId)
			throws ApplicationException {
		logger.debug(" payslipId "+payslipId);
		OrigPayslipMonthlyDetailDAO detailDAO = ORIGDAOFactory.getPayslipMonthlyDetailDAO();
		ArrayList<String> monthlyList = selectNotInKeyTableINC_PAYSLIP_MONTHLY(payslipMonthlyList, payslipId);
		if(!Util.empty(monthlyList)) {
			logger.debug("not in key payslip monthly id "+monthlyList.size());
			for(String monthlyId: monthlyList) {
				logger.debug("not in key payslip monthly id "+monthlyId);
				detailDAO.deleteOrigPayslipMonthlyDetailM(monthlyId, null);
			}
		}
		
		deleteNotInKeyTableINC_PAYSLIP_MONTHLY(payslipMonthlyList, payslipId);
	}

	private ArrayList<String> selectNotInKeyTableINC_PAYSLIP_MONTHLY(
			ArrayList<PayslipMonthlyDataM> payslipMonthlyList, String payslipId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> monthlyList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYSLIP_MONTHLY_ID ");
			sql.append(" FROM INC_PAYSLIP_MONTHLY WHERE PAYSLIP_ID = ? ");
			if (!Util.empty(payslipMonthlyList)) {
                sql.append(" AND PAYSLIP_MONTHLY_ID NOT IN ( ");

                for (PayslipMonthlyDataM monthlyM: payslipMonthlyList) {
                    sql.append(" '" + monthlyM.getPayslipMonthlyId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, payslipId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String monthlyId = rs.getString("PAYSLIP_MONTHLY_ID");
				monthlyList.add(monthlyId);
			}

			return monthlyList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void deleteNotInKeyTableINC_PAYSLIP_MONTHLY(
			ArrayList<PayslipMonthlyDataM> payslipMonthlyList, String payslipId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_PAYSLIP_MONTHLY ");
            sql.append(" WHERE PAYSLIP_ID = ? ");
            
            if (!Util.empty(payslipMonthlyList)) {
                sql.append(" AND PAYSLIP_MONTHLY_ID NOT IN ( ");

                for (PayslipMonthlyDataM payslipMonthlyM: payslipMonthlyList) {
                    sql.append(" '" + payslipMonthlyM.getPayslipMonthlyId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, payslipId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
}
