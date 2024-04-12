package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.FixedAccountDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public class OrigFixedAccountDAOImpl extends OrigObjectDAO implements
		OrigFixedAccountDAO {
	public OrigFixedAccountDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigFixedAccountDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigFixedAccountM(FixedAccountDataM fixedAccountM)
			throws ApplicationException {
		logger.debug("fixedAccountM.getFixedAccId() :"+fixedAccountM.getFixedAccId());
	    if(Util.empty(fixedAccountM.getFixedAccId())){
			String fixedAcctId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_FIXED_ACC_PK);
			fixedAccountM.setFixedAccId(fixedAcctId);
			fixedAccountM.setCreateBy(userId);
	    }
	    fixedAccountM.setUpdateBy(userId);
		createTableINC_FIXED_ACC(fixedAccountM);
		
//		ArrayList<FixedAccountDetailDataM> detailList = fixedAccountM.getFixedAccountDetails();
//		if(!Util.empty(detailList)) {
//			OrigFixedAccountDetailDAO detailDAO = ORIGDAOFactory.getFixedAccountDetailDAO();
//			for(FixedAccountDetailDataM detailM: detailList) {
//				detailM.setFixedAccId(fixedAccountM.getFixedAccId());
//				detailDAO.createOrigFixedAccountDetailM(detailM);
//			}
//		}
	}

	private void createTableINC_FIXED_ACC(FixedAccountDataM fixedAccountM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_FIXED_ACC ");
			sql.append("( FIXED_ACC_ID, INCOME_ID, OPEN_DATE, ACCOUNT_NO, ");
			sql.append(" ACCOUNT_NAME, HOLDING_RATIO, ACCOUNT_BALANCE, BANK_CODE, ");
			sql.append(" COMPARE_FLAG, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, fixedAccountM.getFixedAccId());
			ps.setString(cnt++, fixedAccountM.getIncomeId());
			ps.setDate(cnt++, fixedAccountM.getOpenDate());			
			ps.setString(cnt++, fixedAccountM.getAccountNo());
			
			ps.setString(cnt++, fixedAccountM.getAccountName());
			ps.setBigDecimal(cnt++, fixedAccountM.getHoldingRatio());
			ps.setBigDecimal(cnt++, fixedAccountM.getAccountBalance());
			ps.setString(cnt++, fixedAccountM.getBankCode());
			
			ps.setString(cnt++, fixedAccountM.getCompareFlag());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, fixedAccountM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, fixedAccountM.getUpdateBy());
			
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
	public void deleteOrigFixedAccountM(String incomeId, String fixedAccId)
			throws ApplicationException {
//		OrigFixedAccountDetailDAO detailDAO = ORIGDAOFactory.getFixedAccountDetailDAO();
//		if(!Util.empty(fixedAccId)) {
//			ArrayList<FixedAccountDataM> fixAcctForIncome = loadOrigFixedAccountM(incomeId);
//			if(!Util.empty(fixAcctForIncome)) {
//				for(FixedAccountDataM fixAcctM: fixAcctForIncome) {
//					detailDAO.deleteOrigFixedAccountDetailM(fixAcctM.getFixedAccId(), null);
//				}
//			}
//		} else {
//			detailDAO.deleteOrigFixedAccountDetailM(fixedAccId, null);
//		}
		
		deleteTableINC_FIXED_ACC(incomeId, fixedAccId);
	}

	private void deleteTableINC_FIXED_ACC(String incomeId, String fixedAccId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_FIXED_ACC ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(fixedAccId != null && !fixedAccId.isEmpty()) {
				sql.append(" AND FIXED_ACC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(fixedAccId != null && !fixedAccId.isEmpty()) {
				ps.setString(2, fixedAccId);
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
	public ArrayList<FixedAccountDataM> loadOrigFixedAccountM(String incomeId,
			String incomeType, Connection conn) throws ApplicationException {
		return loadOrigFixedAccountM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<FixedAccountDataM> loadOrigFixedAccountM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<FixedAccountDataM> result = selectTableINC_FIXED_ACC(incomeId, incomeType);

//		if(!Util.empty(result)) {
//			OrigFixedAccountDetailDAO detailDAO = ORIGDAOFactory.getFixedAccountDetailDAO();
//			for(FixedAccountDataM fixAcctM: result) {
//				ArrayList<FixedAccountDetailDataM> detailList = detailDAO.
//						loadOrigFixedAccountDetailM(fixAcctM.getFixedAccId());
//				if(!Util.empty(detailList)) {
//					fixAcctM.setFixedAccountDetails(detailList);					
//				}
//			}
//		}
		return result;
	}

	private ArrayList<FixedAccountDataM> selectTableINC_FIXED_ACC(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<FixedAccountDataM> fixAcctList = new ArrayList<FixedAccountDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT FIXED_ACC_ID, INCOME_ID, OPEN_DATE, ACCOUNT_NO, ");
			sql.append(" ACCOUNT_NAME, HOLDING_RATIO, ACCOUNT_BALANCE, BANK_CODE, ");
			sql.append(" COMPARE_FLAG, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM INC_FIXED_ACC WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY FIXED_ACC_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				FixedAccountDataM fixAcctM = new FixedAccountDataM();
				fixAcctM.setSeq(fixAcctList.size()+1);
				fixAcctM.setIncomeType(incomeType);
				fixAcctM.setFixedAccId(rs.getString("FIXED_ACC_ID"));
				fixAcctM.setIncomeId(rs.getString("INCOME_ID"));
				fixAcctM.setOpenDate(rs.getDate("OPEN_DATE"));				
				fixAcctM.setAccountNo(rs.getString("ACCOUNT_NO"));
				
				fixAcctM.setAccountName(rs.getString("ACCOUNT_NAME"));
				fixAcctM.setHoldingRatio(rs.getBigDecimal("HOLDING_RATIO"));
				fixAcctM.setAccountBalance(rs.getBigDecimal("ACCOUNT_BALANCE"));
				fixAcctM.setBankCode(rs.getString("BANK_CODE"));
				
				fixAcctM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				fixAcctM.setCreateBy(rs.getString("CREATE_BY"));
				fixAcctM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				fixAcctM.setUpdateBy(rs.getString("UPDATE_BY"));
				fixAcctM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				fixAcctList.add(fixAcctM);
			}

			return fixAcctList;
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
	public void saveUpdateOrigFixedAccountM(FixedAccountDataM fixedAccountM)
			throws ApplicationException {
		int returnRows = 0;
		fixedAccountM.setUpdateBy(userId);
		returnRows = updateTableINC_FIXED_ACC(fixedAccountM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_FIXED_ACC then call Insert method");
			fixedAccountM.setCreateBy(userId);
			createOrigFixedAccountM(fixedAccountM);
		} else {
		
//			ArrayList<FixedAccountDetailDataM> detailList = fixedAccountM.getFixedAccountDetails();
//			OrigFixedAccountDetailDAO detailDAO = ORIGDAOFactory.getFixedAccountDetailDAO();
//			if(!Util.empty(detailList)) {
//				for(FixedAccountDetailDataM detailM: detailList) {
//					detailM.setFixedAccId(fixedAccountM.getFixedAccId());
//					detailDAO.saveUpdateOrigFixedAccountDetailM(detailM);
//				}
//			}
//			detailDAO.deleteNotInKeyFixedAccountDetail(detailList, fixedAccountM.getFixedAccId());
		}
	}

	private int updateTableINC_FIXED_ACC(FixedAccountDataM fixedAccountM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_FIXED_ACC ");
			sql.append(" SET OPEN_DATE = ?, ACCOUNT_NO = ?, ACCOUNT_NAME = ?, ");
			sql.append(" HOLDING_RATIO = ?, ACCOUNT_BALANCE = ?, BANK_CODE = ?, ");
			sql.append(" COMPARE_FLAG = ?, UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE FIXED_ACC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setDate(cnt++, fixedAccountM.getOpenDate());			
			ps.setString(cnt++, fixedAccountM.getAccountNo());
			ps.setString(cnt++, fixedAccountM.getAccountName());
			
			ps.setBigDecimal(cnt++, fixedAccountM.getHoldingRatio());
			ps.setBigDecimal(cnt++, fixedAccountM.getAccountBalance());
			ps.setString(cnt++, fixedAccountM.getBankCode());
			
			ps.setString(cnt++, fixedAccountM.getCompareFlag());
			ps.setString(cnt++, fixedAccountM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, fixedAccountM.getFixedAccId());
			
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
	public void deleteNotInKeyFixedAccount(
			ArrayList<IncomeCategoryDataM> fixedAccountList, String incomeId)
			throws ApplicationException {
//		ArrayList<String> fixAcctList = selectNotInKeyTableINC_FIXED_ACC(fixedAccountList, incomeId);
//		if(!Util.empty(fixAcctList)) {
//			OrigFixedAccountDetailDAO detailDAO = ORIGDAOFactory.getFixedAccountDetailDAO();
//			for(String fixAcctId: fixAcctList) {
//				detailDAO.deleteOrigFixedAccountDetailM(fixAcctId, null);
//			}
//		}
		
		deleteNotInKeyTableINC_FIXED_ACC(fixedAccountList, incomeId);
	}

	private ArrayList<String> selectNotInKeyTableINC_FIXED_ACC(
			ArrayList<IncomeCategoryDataM> fixedAccountList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> fixAcctList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT FIXED_ACC_ID ");
			sql.append(" FROM INC_FIXED_ACC WHERE INCOME_ID = ? ");
			if (!Util.empty(fixedAccountList)) {
                sql.append(" AND FIXED_ACC_ID NOT IN ( ");

                for (IncomeCategoryDataM fixAcctM: fixedAccountList) {
                    sql.append(" '" + fixAcctM.getId() + "' , ");
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
				String fixAcctId = rs.getString("FIXED_ACC_ID");
				fixAcctList.add(fixAcctId);
			}

			return fixAcctList;
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

	private void deleteNotInKeyTableINC_FIXED_ACC(
			ArrayList<IncomeCategoryDataM> fixedAccountList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_FIXED_ACC ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(fixedAccountList)) {
                sql.append(" AND FIXED_ACC_ID NOT IN ( ");

                for (IncomeCategoryDataM fixAccM: fixedAccountList) {
                    sql.append(" '" + fixAccM.getId() + "' , ");
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
