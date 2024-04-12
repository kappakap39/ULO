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
import com.eaf.orig.ulo.model.app.OpenedEndFundDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM;

public class OrigOpenEndFundDAOImpl extends OrigObjectDAO implements
		OrigOpenEndFundDAO {
	public OrigOpenEndFundDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigOpenEndFundDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigOpenEndFundM(OpenedEndFundDataM openEndFundM)
			throws ApplicationException {
		logger.debug("openEndFundM.getOpnEndFundId() :"+openEndFundM.getOpnEndFundId());
	    if(Util.empty(openEndFundM.getOpnEndFundId())){
			String opnEndFundId = GenerateUnique.generate(OrigConstant.SeqNames.INC_OPN_END_FUND_PK);
			openEndFundM.setOpnEndFundId(opnEndFundId);
			openEndFundM.setCreateBy(userId);
	    }
	    openEndFundM.setUpdateBy(userId);
		createTableINC_OPN_END_FUND(openEndFundM);
		
		ArrayList<OpenedEndFundDetailDataM> detailList = openEndFundM.getOpenEndFundDetails();
		if(!Util.empty(detailList)) {
			OrigOpenEndFundDetailDAO detailDAO = ORIGDAOFactory.getOpenEndFundDetailDAO(userId);
			for(OpenedEndFundDetailDataM detailM: detailList) {
				detailM.setOpnEndFundId(openEndFundM.getOpnEndFundId());
				detailDAO.createOrigOpenEndFundDetailM(detailM);
			}
		}
	}

	private void createTableINC_OPN_END_FUND(OpenedEndFundDataM openEndFundM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_OPN_END_FUND ");
			sql.append("( OPN_END_FUND_ID, INCOME_ID, OPEN_DATE, BANK_CODE, ");
			sql.append(" FUND_NAME, ACCOUNT_NO, ACCOUNT_NAME, HOLDING_RATIO, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, openEndFundM.getOpnEndFundId());
			ps.setString(cnt++, openEndFundM.getIncomeId());
			ps.setDate(cnt++, openEndFundM.getOpenDate());
			ps.setString(cnt++, openEndFundM.getBankCode());
			
			ps.setString(cnt++, openEndFundM.getFundName());
			ps.setString(cnt++, openEndFundM.getAccountNo());
			ps.setString(cnt++, openEndFundM.getAccountName());
			ps.setBigDecimal(cnt++, openEndFundM.getHoldingRatio());
			ps.setString(cnt++, openEndFundM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, openEndFundM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, openEndFundM.getUpdateBy());
			
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
	public void deleteOrigOpenEndFundM(String incomeId, String opnEndFundId)
			throws ApplicationException {
		OrigOpenEndFundDetailDAO detailDAO = ORIGDAOFactory.getOpenEndFundDetailDAO();		
		if(Util.empty(opnEndFundId)) {
			ArrayList<String> opnEndForIncome = selectOpenEndIdINC_OPN_END_FUND(incomeId);
			if(!Util.empty(opnEndForIncome)) {
				for(String opnEndFundID: opnEndForIncome) {
					detailDAO.deleteOrigOpenEndFundDetailM(opnEndFundID, null);
				}
			}
		} else {
			detailDAO.deleteOrigOpenEndFundDetailM(opnEndFundId, null);
		}
		
		deleteTableINC_OPN_END_FUND(incomeId, opnEndFundId);
	}

	private ArrayList<String> selectOpenEndIdINC_OPN_END_FUND(String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> opnEndList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OPN_END_FUND_ID ");
			sql.append(" FROM INC_OPN_END_FUND WHERE INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				opnEndList.add(rs.getString("OPN_END_FUND_ID"));
			}

			return opnEndList;
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

	private void deleteTableINC_OPN_END_FUND(String incomeId,
			String opnEndFundId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_OPN_END_FUND ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(opnEndFundId)) {
				sql.append(" AND OPN_END_FUND_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(opnEndFundId)) {
				ps.setString(2, opnEndFundId);
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
	public ArrayList<OpenedEndFundDataM> loadOrigOpenEndFundM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigOpenEndFundM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<OpenedEndFundDataM> loadOrigOpenEndFundM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<OpenedEndFundDataM> result = selectTableINC_OPN_END_FUND(incomeId, incomeType);
		
		if(!Util.empty(result)) {
			OrigOpenEndFundDetailDAO detailDAO = ORIGDAOFactory.getOpenEndFundDetailDAO();
			for(OpenedEndFundDataM openEndFundM: result) {
				ArrayList<OpenedEndFundDetailDataM> detailList = detailDAO.
						loadOrigOpenEndFundDetailM(openEndFundM.getOpnEndFundId());
				if(!Util.empty(detailList)) {
					openEndFundM.setOpenEndFundDetails(detailList);					
				}
			}
		}
		return result;
	}

	private ArrayList<OpenedEndFundDataM> selectTableINC_OPN_END_FUND(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<OpenedEndFundDataM> opnEndList = new ArrayList<OpenedEndFundDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OPN_END_FUND_ID, INCOME_ID, OPEN_DATE, BANK_CODE, ");
			sql.append(" FUND_NAME, ACCOUNT_NO, ACCOUNT_NAME, HOLDING_RATIO, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_OPN_END_FUND WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY OPN_END_FUND_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				OpenedEndFundDataM openEndFundM = new OpenedEndFundDataM();
				openEndFundM.setSeq(opnEndList.size()+1);
				openEndFundM.setIncomeType(incomeType);
				openEndFundM.setOpnEndFundId(rs.getString("OPN_END_FUND_ID"));
				openEndFundM.setIncomeId(rs.getString("INCOME_ID"));
				openEndFundM.setOpenDate(rs.getDate("OPEN_DATE"));
				openEndFundM.setBankCode(rs.getString("BANK_CODE"));
				
				openEndFundM.setFundName(rs.getString("FUND_NAME"));				
				openEndFundM.setAccountNo(rs.getString("ACCOUNT_NO"));
				openEndFundM.setAccountName(rs.getString("ACCOUNT_NAME"));
				openEndFundM.setHoldingRatio(rs.getBigDecimal("HOLDING_RATIO"));
				openEndFundM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				openEndFundM.setCreateBy(rs.getString("CREATE_BY"));
				openEndFundM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				openEndFundM.setUpdateBy(rs.getString("UPDATE_BY"));
				openEndFundM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				opnEndList.add(openEndFundM);
			}

			return opnEndList;
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
	public void saveUpdateOrigOpenEndFundM(OpenedEndFundDataM openEndFundM)
			throws ApplicationException {
		int returnRows = 0;
		openEndFundM.setUpdateBy(userId);
		returnRows = updateTableINC_OPN_END_FUND(openEndFundM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_OPN_END_FUND then call Insert method");
			openEndFundM.setCreateBy(userId);
			createOrigOpenEndFundM(openEndFundM);
		} else {
		
			ArrayList<OpenedEndFundDetailDataM> detailList = openEndFundM.getOpenEndFundDetails();
			OrigOpenEndFundDetailDAO detailDAO = ORIGDAOFactory.getOpenEndFundDetailDAO(userId);
			if(!Util.empty(detailList)) {
				for(OpenedEndFundDetailDataM detailM: detailList) {
					detailM.setOpnEndFundId(openEndFundM.getOpnEndFundId());
					detailDAO.saveUpdateOrigOpenEndFundDetailM(detailM);
				}
			}
			detailDAO.deleteNotInKeyOpenEndFundDetail(detailList, openEndFundM.getOpnEndFundId());
		}
	}

	private int updateTableINC_OPN_END_FUND(OpenedEndFundDataM openEndFundM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_OPN_END_FUND ");
			sql.append(" SET OPEN_DATE = ?, BANK_CODE = ?, FUND_NAME = ?, ");
			sql.append(" ACCOUNT_NO = ?, ACCOUNT_NAME = ?, HOLDING_RATIO = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE OPN_END_FUND_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setDate(cnt++, openEndFundM.getOpenDate());
			ps.setString(cnt++, openEndFundM.getBankCode());
			ps.setString(cnt++, openEndFundM.getFundName());			
			ps.setString(cnt++, openEndFundM.getAccountNo());
			
			ps.setString(cnt++, openEndFundM.getAccountName());
			ps.setBigDecimal(cnt++, openEndFundM.getHoldingRatio());
			ps.setString(cnt++, openEndFundM.getCompareFlag());
			
			ps.setString(cnt++, openEndFundM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, openEndFundM.getOpnEndFundId());
			
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
	public void deleteNotInKeyOpenEndFund(
			ArrayList<IncomeCategoryDataM> openEndFundMList, String incomeId)
			throws ApplicationException {
		ArrayList<String> openEndFundIdList = selectNotInKeyTableINC_OPN_END_FUND(openEndFundMList, incomeId);
		if(!Util.empty(openEndFundIdList)) {
			OrigOpenEndFundDetailDAO detailDAO = ORIGDAOFactory.getOpenEndFundDetailDAO();
			for(String openEndId: openEndFundIdList) {
				detailDAO.deleteOrigOpenEndFundDetailM(openEndId, null);
			}
		}
		deleteNotInKeyTableINC_OPN_END_FUND(openEndFundMList, incomeId);
	}

	private ArrayList<String> selectNotInKeyTableINC_OPN_END_FUND(
			ArrayList<IncomeCategoryDataM> openEndFundMList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> opnEndList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OPN_END_FUND_ID ");
			sql.append(" FROM INC_OPN_END_FUND WHERE INCOME_ID = ? ");
			if (!Util.empty(openEndFundMList)) {
                sql.append(" AND OPN_END_FUND_ID NOT IN ( ");

                for (IncomeCategoryDataM openEndFundM: openEndFundMList) {
                    sql.append(" '" + openEndFundM.getId() + "' , ");
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
				String openEndFundId = rs.getString("OPN_END_FUND_ID");
				opnEndList.add(openEndFundId);
			}

			return opnEndList;
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

	private void deleteNotInKeyTableINC_OPN_END_FUND(
			ArrayList<IncomeCategoryDataM> openEndFundMList, String incomeId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_OPN_END_FUND ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(openEndFundMList)) {
                sql.append(" AND OPN_END_FUND_ID NOT IN ( ");

                for (IncomeCategoryDataM openEndFundM: openEndFundMList) {
                    sql.append(" '" + openEndFundM.getId() + "' , ");
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
