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
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;

public class OrigSavingAccountDAOImpl extends OrigObjectDAO implements
		OrigSavingAccountDAO {
	public OrigSavingAccountDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigSavingAccountDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigSavingAccountM(SavingAccountDataM savingAccountM)
			throws ApplicationException {
		logger.debug("savingAccountM.getSavingAccId() :"+savingAccountM.getSavingAccId());
	    if(Util.empty(savingAccountM.getSavingAccId())){
			String savingAccId =GenerateUnique.generate(OrigConstant.SeqNames.INC_SAVING_ACC_PK);
			savingAccountM.setSavingAccId(savingAccId);
			savingAccountM.setCreateBy(userId);
	    }
	    savingAccountM.setUpdateBy(userId);
		createTableINC_SAVING_ACC(savingAccountM);
		
		ArrayList<SavingAccountDetailDataM> detailList = savingAccountM.getSavingAccountDetails();
		if(!Util.empty(detailList)) {
			OrigSavingAccountDetailDAO detailDAO = ORIGDAOFactory.getSavingAccountDetailDAO(userId);
			for(SavingAccountDetailDataM detailM: detailList) {
				detailM.setSavingAccId(savingAccountM.getSavingAccId());
				detailDAO.createOrigSavingAccountDetailM(detailM);
			}
		}
	}

	private void createTableINC_SAVING_ACC(SavingAccountDataM savingAccountM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_SAVING_ACC ");
			sql.append("( SAVING_ACC_ID, INCOME_ID, OPEN_DATE, ACCOUNT_NO, ACCOUNT_NAME, ");
			sql.append(" HOLDING_RATIO, ACCOUNT_BALANCE, BANK_CODE, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, savingAccountM.getSavingAccId());
			ps.setString(cnt++, savingAccountM.getIncomeId());
			ps.setDate(cnt++, savingAccountM.getOpenDate());			
			ps.setString(cnt++, savingAccountM.getAccountNo());
			ps.setString(cnt++, savingAccountM.getAccountName());
			
			ps.setBigDecimal(cnt++, savingAccountM.getHoldingRatio());
			ps.setBigDecimal(cnt++, savingAccountM.getAccountBalance());			
			ps.setString(cnt++, savingAccountM.getBankCode());
			ps.setString(cnt++, savingAccountM.getCompareFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, savingAccountM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, savingAccountM.getUpdateBy());
			
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
	public void deleteOrigSavingAccountM(String incomeId, String savingAccId)
			throws ApplicationException {
		OrigSavingAccountDetailDAO detailDAO = ORIGDAOFactory.getSavingAccountDetailDAO();
		if(Util.empty(savingAccId)) {
			ArrayList<String> savingForIncome = selectSavingAccIdINC_SAVING_ACC(incomeId);
			if(!Util.empty(savingForIncome)) {
				for(String savingAcctID: savingForIncome) {
					detailDAO.deleteOrigSavingAccountDetailM(savingAcctID, null);
				}
			}
		} else {
			detailDAO.deleteOrigSavingAccountDetailM(savingAccId, null);
		}
		
		deleteTableINC_SAVING_ACC(incomeId, savingAccId);
	}

	private ArrayList<String> selectSavingAccIdINC_SAVING_ACC(String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> savingList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SAVING_ACC_ID ");
			sql.append(" FROM INC_SAVING_ACC WHERE INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				savingList.add(rs.getString("SAVING_ACC_ID"));
			}

			return savingList;
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

	private void deleteTableINC_SAVING_ACC(String incomeId, String savingAccId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_SAVING_ACC ");
			sql.append(" WHERE INCOME_ID = ? ");
			if(!Util.empty(savingAccId)) {
				sql.append(" AND SAVING_ACC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, incomeId);
			if(!Util.empty(savingAccId)) {
				ps.setString(2, savingAccId);
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
	public ArrayList<SavingAccountDataM> loadOrigSavingAccountM(
			String incomeId, String incomeType, Connection conn)
			throws ApplicationException {
		return loadOrigSavingAccountM(incomeId, incomeType);
	}
	
	@Override
	public ArrayList<SavingAccountDataM> loadOrigSavingAccountM(String incomeId, String incomeType)
			throws ApplicationException {
		ArrayList<SavingAccountDataM> result = selectTableINC_SAVING_ACC(incomeId, incomeType);

		if(!Util.empty(result)) {
			OrigSavingAccountDetailDAO detailDAO = ORIGDAOFactory.getSavingAccountDetailDAO();
			for(SavingAccountDataM savingM: result) {
				ArrayList<SavingAccountDetailDataM> detailList = detailDAO.loadOrigSavingAccountDetailM(
										savingM.getSavingAccId());
				if(!Util.empty(detailList)) {
					savingM.setSavingAccountDetails(detailList);					
				}
			}
		}
		return result;
	}

	private ArrayList<SavingAccountDataM> selectTableINC_SAVING_ACC(
			String incomeId, String incomeType) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<SavingAccountDataM> savAcctList = new ArrayList<SavingAccountDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SAVING_ACC_ID, INCOME_ID, OPEN_DATE, ACCOUNT_NO, ACCOUNT_NAME, ");
			sql.append(" HOLDING_RATIO, ACCOUNT_BALANCE, BANK_CODE, COMPARE_FLAG, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_SAVING_ACC WHERE INCOME_ID = ? ");
			sql.append(" ORDER BY SAVING_ACC_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				SavingAccountDataM savingAcctM = new SavingAccountDataM();
				savingAcctM.setSeq(savAcctList.size()+1);
				savingAcctM.setIncomeType(incomeType);
				savingAcctM.setSavingAccId(rs.getString("SAVING_ACC_ID"));
				savingAcctM.setIncomeId(rs.getString("INCOME_ID"));
				savingAcctM.setOpenDate(rs.getDate("OPEN_DATE"));
				savingAcctM.setAccountNo(rs.getString("ACCOUNT_NO"));
				savingAcctM.setAccountName(rs.getString("ACCOUNT_NAME"));
				
				savingAcctM.setHoldingRatio(rs.getBigDecimal("HOLDING_RATIO"));
				savingAcctM.setAccountBalance(rs.getBigDecimal("ACCOUNT_BALANCE"));
				savingAcctM.setBankCode(rs.getString("BANK_CODE"));
				savingAcctM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				
				savingAcctM.setCreateBy(rs.getString("CREATE_BY"));
				savingAcctM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				savingAcctM.setUpdateBy(rs.getString("UPDATE_BY"));
				savingAcctM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				savAcctList.add(savingAcctM);
			}

			return savAcctList;
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
	public void saveUpdateOrigSavingAccountM(SavingAccountDataM savingAccountM)
			throws ApplicationException {
		int returnRows = 0;
		savingAccountM.setUpdateBy(userId);
		returnRows = updateTableINC_SAVING_ACC(savingAccountM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_SAVING_ACC then call Insert method");
			savingAccountM.setCreateBy(userId);
			createOrigSavingAccountM(savingAccountM);
		} else {
		
			ArrayList<SavingAccountDetailDataM> detailList = savingAccountM.getSavingAccountDetails();
			OrigSavingAccountDetailDAO detailDAO = ORIGDAOFactory.getSavingAccountDetailDAO(userId);
			if(!Util.empty(detailList)) {
				for(SavingAccountDetailDataM detailM: detailList) {
					detailM.setSavingAccId(savingAccountM.getSavingAccId());
					detailDAO.saveUpdateOrigSavingAccountDetailM(detailM);
				}
			}
			detailDAO.deleteNotInKeySavingAccountDetail(detailList, savingAccountM.getSavingAccId());
		}
	}

	private int updateTableINC_SAVING_ACC(SavingAccountDataM savingAccountM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_SAVING_ACC ");
			sql.append(" SET OPEN_DATE = ?, ACCOUNT_NO = ?, ACCOUNT_NAME = ?, HOLDING_RATIO = ?, ");
			sql.append(" ACCOUNT_BALANCE = ?, BANK_CODE = ?, COMPARE_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE SAVING_ACC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setDate(cnt++, savingAccountM.getOpenDate());
			ps.setString(cnt++, savingAccountM.getAccountNo());			
			ps.setString(cnt++, savingAccountM.getAccountName());
			ps.setBigDecimal(cnt++, savingAccountM.getHoldingRatio());
			
			ps.setBigDecimal(cnt++, savingAccountM.getAccountBalance());		
			ps.setString(cnt++, savingAccountM.getBankCode());
			ps.setString(cnt++, savingAccountM.getCompareFlag());
			
			ps.setString(cnt++, savingAccountM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, savingAccountM.getSavingAccId());
			
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
	public void deleteNotInKeySavingAccount(
			ArrayList<IncomeCategoryDataM> savingAccountMList, String incomeId) throws ApplicationException {
		ArrayList<String> savingList = selectNotInKeyTableINC_SAVING_ACC(savingAccountMList, incomeId);
		if(!Util.empty(savingList)) {
			OrigSavingAccountDetailDAO detailDAO = ORIGDAOFactory.getSavingAccountDetailDAO();
			for(String savAcctId: savingList) {
				detailDAO.deleteOrigSavingAccountDetailM(savAcctId, null);
			}
		}
		
		deleteNotInKeyTableINC_SAVING_ACC(savingAccountMList, incomeId);
	}

	private ArrayList<String> selectNotInKeyTableINC_SAVING_ACC(
			ArrayList<IncomeCategoryDataM> savingAccountMList, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> savAcctList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SAVING_ACC_ID ");
			sql.append(" FROM INC_SAVING_ACC WHERE INCOME_ID = ? ");
			if (!Util.empty(savingAccountMList)) {
                sql.append(" AND SAVING_ACC_ID NOT IN ( ");

                for (IncomeCategoryDataM savingAcctM: savingAccountMList) {
                    sql.append(" '" + savingAcctM.getId() + "' , ");
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
				String savAcctId = rs.getString("SAVING_ACC_ID");
				savAcctList.add(savAcctId);
			}

			return savAcctList;
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

	private void deleteNotInKeyTableINC_SAVING_ACC(
			ArrayList<IncomeCategoryDataM> savingAccountMList, String incomeId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_SAVING_ACC ");
            sql.append(" WHERE INCOME_ID = ? ");
            
            if (!Util.empty(savingAccountMList)) {
                sql.append(" AND SAVING_ACC_ID NOT IN ( ");

                for (IncomeCategoryDataM savingAccM: savingAccountMList) {
                    sql.append(" '" + savingAccM.getId() + "' , ");
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
