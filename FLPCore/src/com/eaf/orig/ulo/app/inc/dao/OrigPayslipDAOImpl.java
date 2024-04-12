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
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;

public class OrigPayslipDAOImpl extends OrigObjectDAO implements OrigPayslipDAO {
	public OrigPayslipDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPayslipDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPayslipM(PayslipDataM payslipM)
			throws ApplicationException {
		logger.debug("payslipM.getPayslipId() :"+payslipM.getPayslipId());
	    if(Util.empty(payslipM.getPayslipId())){
			String payslipId = GenerateUnique.generate(OrigConstant.SeqNames.INC_PAYSLIP_PK);
			payslipM.setPayslipId(payslipId);
			payslipM.setCreateBy(userId);
	    }
	    payslipM.setUpdateBy(userId);
		createTableINC_PAYSLIP(payslipM);
		
		ArrayList<PayslipMonthlyDataM> monthlyList = payslipM.getPayslipMonthlys();
		if(!Util.empty(monthlyList)) {
			OrigPayslipMonthlyDAO monthlyDAO = ORIGDAOFactory.getPayslipMonthlyDAO(userId);
			for(PayslipMonthlyDataM payslipMonthlyM: monthlyList) {
				payslipMonthlyM.setPayslipId(payslipM.getPayslipId());
				monthlyDAO.createOrigPayslipMonthlyM(payslipMonthlyM);
			}
		}
	}

	private void createTableINC_PAYSLIP(PayslipDataM payslipM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_PAYSLIP ");
			sql.append("( PAYSLIP_ID, INCOME_ID, NO_MONTH, ACCUM_INCOME, BONUS, ACCUM_OTH_INCOME, ");
			sql.append(" SUM_SSO, BONUS_MONTH, BONUS_YEAR, SPACIAL_PENSION, COMPARE_FLAG, SALARY_DATE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,?,  ?,?,?,?,?,?,  ");
			sql.append(" ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payslipM.getPayslipId());
			ps.setString(cnt++, payslipM.getIncomeId());
			ps.setInt(cnt++, payslipM.getNoMonth());
			ps.setBigDecimal(cnt++, payslipM.getSumIncome());
			ps.setBigDecimal(cnt++, payslipM.getBonus());
			ps.setBigDecimal(cnt++, payslipM.getSumOthIncome());
			
			ps.setBigDecimal(cnt++, payslipM.getSumSso());
			ps.setString(cnt++, payslipM.getBonusMonth());
			ps.setString(cnt++, payslipM.getBonusYear());
			ps.setBigDecimal(cnt++, payslipM.getSpacialPension());
			ps.setString(cnt++, payslipM.getCompareFlag());
			ps.setDate(cnt++, payslipM.getSalaryDate());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payslipM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payslipM.getUpdateBy());
			
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
	public void deleteOrigPayslipM(String incomeId, String payslipId)
			throws ApplicationException {
		OrigPayslipMonthlyDAO monthlyDAO = ORIGDAOFactory.getPayslipMonthlyDAO();
		if(Util.empty(payslipId)) {
			ArrayList<String> paySlipListM = selectPayslipIdINC_PAYSLIP(incomeId);
			if(!Util.empty(paySlipListM)) {
				for(String payslipID: paySlipListM) {
					monthlyDAO.deleteOrigPayslipMonthlyM(payslipID, null);
				}
			}
		} else {
			monthlyDAO.deleteOrigPayslipMonthlyM(payslipId, null);
		}
		
		deleteTableINC_PAYSLIP(incomeId, payslipId);
	}

	private ArrayList<String> selectPayslipIdINC_PAYSLIP(String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> payslipList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYSLIP_ID ");
			sql.append(" FROM INC_PAYSLIP WHERE INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				payslipList.add(rs.getString("PAYSLIP_ID"));
			}

			return payslipList;
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

	private void deleteTableINC_PAYSLIP(String incomeId, String payslipId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_PAYSLIP ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(payslipId)) {
				sql.append(" AND PAYSLIP_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(payslipId)) {
				ps.setString(2, payslipId);
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
	public ArrayList<PayslipDataM> loadOrigPayslipM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigPayslipM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<PayslipDataM> loadOrigPayslipM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<PayslipDataM> result = selectTableINC_PAYSLIP(incomeId, incomeType);
		
		if(!Util.empty(result)) {
			OrigPayslipMonthlyDAO monthlyDAO = ORIGDAOFactory.getPayslipMonthlyDAO();
			for(PayslipDataM payslipM: result) {
				ArrayList<PayslipMonthlyDataM> monthlyList = monthlyDAO.loadOrigPayslipMonthlyM(payslipM.getPayslipId());
				if(!Util.empty(monthlyList)) {
					payslipM.setPayslipMonthlys(monthlyList);					
				}
			}
		}
		return result;
	}

	private ArrayList<PayslipDataM> selectTableINC_PAYSLIP(String incomeId, String incomeType) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PayslipDataM> payslipList = new ArrayList<PayslipDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYSLIP_ID, INCOME_ID, NO_MONTH, ACCUM_INCOME, BONUS, ACCUM_OTH_INCOME, ");
			sql.append(" SUM_SSO, BONUS_MONTH, BONUS_YEAR, SPACIAL_PENSION, COMPARE_FLAG, SALARY_DATE, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_PAYSLIP WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY PAYSLIP_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PayslipDataM payslipM = new PayslipDataM();
				payslipM.setSeq(payslipList.size()+1);
				payslipM.setIncomeType(incomeType);
				payslipM.setPayslipId(rs.getString("PAYSLIP_ID"));
				payslipM.setIncomeId(rs.getString("INCOME_ID"));
				payslipM.setNoMonth(rs.getInt("NO_MONTH"));
				payslipM.setSumIncome(rs.getBigDecimal("ACCUM_INCOME"));
				payslipM.setBonus(rs.getBigDecimal("BONUS"));
				payslipM.setSumOthIncome(rs.getBigDecimal("ACCUM_OTH_INCOME"));
				
				payslipM.setSumSso(rs.getBigDecimal("SUM_SSO"));
				payslipM.setBonusMonth(rs.getString("BONUS_MONTH"));
				payslipM.setBonusYear(rs.getString("BONUS_YEAR"));
				payslipM.setSpacialPension(rs.getBigDecimal("SPACIAL_PENSION"));
				payslipM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				payslipM.setSalaryDate(rs.getDate("SALARY_DATE"));
				
				payslipM.setCreateBy(rs.getString("CREATE_BY"));
				payslipM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				payslipM.setUpdateBy(rs.getString("UPDATE_BY"));
				payslipM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				payslipList.add(payslipM);
			}

			return payslipList;
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
	public void saveUpdateOrigPayslipM(PayslipDataM payslipM)
			throws ApplicationException {
		int returnRows = 0;
		payslipM.setUpdateBy(userId);
		returnRows = updateTableINC_PAYSLIP(payslipM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_PAYSLIP then call Insert method");
			payslipM.setCreateBy(userId);
			createOrigPayslipM(payslipM);
		} else {
		
			ArrayList<PayslipMonthlyDataM> monthlyList = payslipM.getPayslipMonthlys();
			OrigPayslipMonthlyDAO monthlyDAO = ORIGDAOFactory.getPayslipMonthlyDAO(userId);
			if(!Util.empty(monthlyList)) {
				for(PayslipMonthlyDataM payslipMonthlyM: monthlyList) {
					payslipMonthlyM.setPayslipId(payslipM.getPayslipId());
					monthlyDAO.saveUpdateOrigPayslipMonthlyM(payslipMonthlyM);
				}
			}
			monthlyDAO.deleteNotInKeyPayslipMonthly(monthlyList, payslipM.getPayslipId());
		}
	}

	private int updateTableINC_PAYSLIP(PayslipDataM payslipM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_PAYSLIP ");
			sql.append(" SET NO_MONTH = ?, ACCUM_INCOME = ?, BONUS = ?, SUM_SSO = ?, ACCUM_OTH_INCOME=?, ");
			sql.append(" BONUS_MONTH = ?, BONUS_YEAR = ?, SPACIAL_PENSION = ?, COMPARE_FLAG = ?, SALARY_DATE = ?,");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE PAYSLIP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setInt(cnt++, payslipM.getNoMonth());
			ps.setBigDecimal(cnt++, payslipM.getSumIncome());
			ps.setBigDecimal(cnt++, payslipM.getBonus());
			ps.setBigDecimal(cnt++, payslipM.getSumSso());
			ps.setBigDecimal(cnt++, payslipM.getSumOthIncome());
			
			ps.setString(cnt++, payslipM.getBonusMonth());
			ps.setString(cnt++, payslipM.getBonusYear());
			ps.setBigDecimal(cnt++, payslipM.getSpacialPension());
			ps.setString(cnt++, payslipM.getCompareFlag());
			ps.setDate(cnt++, payslipM.getSalaryDate());
			
			ps.setString(cnt++, payslipM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, payslipM.getPayslipId());
			
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
	public void deleteNotInKeyPayslip(ArrayList<IncomeCategoryDataM> payslipList,
			String incomeId) throws ApplicationException {
		ArrayList<String> notInKeyIDList = selectNotInKeyTableINC_PAYSLIP(payslipList, incomeId);
		if(!Util.empty(notInKeyIDList)) {
			OrigPayslipMonthlyDAO monthlyDAO = ORIGDAOFactory.getPayslipMonthlyDAO();
			for(String payslipId: notInKeyIDList) {
				monthlyDAO.deleteOrigPayslipMonthlyM(payslipId, null);
			}
		}
		
		deleteNotInKeyTableINC_PAYSLIP(payslipList, incomeId);
	}

	private ArrayList<String> selectNotInKeyTableINC_PAYSLIP(
			ArrayList<IncomeCategoryDataM> payslipList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> othOpnEndList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYSLIP_ID ");
			sql.append(" FROM INC_PAYSLIP WHERE INCOME_ID = ? ");
			if (!Util.empty(payslipList)) {
                sql.append(" AND PAYSLIP_ID NOT IN ( ");

                for (IncomeCategoryDataM payslipM: payslipList) {
                    sql.append(" '" + payslipM.getId() + "' , ");
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
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String openEndFundId = rs.getString("PAYSLIP_ID");
				othOpnEndList.add(openEndFundId);
			}

			return othOpnEndList;
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

	private void deleteNotInKeyTableINC_PAYSLIP(
			ArrayList<IncomeCategoryDataM> payslipList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_PAYSLIP ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(payslipList)) {
                sql.append(" AND PAYSLIP_ID NOT IN ( ");

                for (IncomeCategoryDataM payslipM: payslipList) {
                    sql.append(" '" + payslipM.getId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, incomeId);
            
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
