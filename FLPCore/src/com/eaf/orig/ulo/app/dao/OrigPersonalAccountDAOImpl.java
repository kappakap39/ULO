package com.eaf.orig.ulo.app.dao;

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
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AccountDataM;

public class OrigPersonalAccountDAOImpl extends OrigObjectDAO implements OrigPersonalAccountDAO{
	public OrigPersonalAccountDAOImpl(String userId) {
		this.userId = userId;
	}

	public OrigPersonalAccountDAOImpl() {
		
	}

	private String userId = "";

	@Override
	public void createOrigPersonalAccountM(AccountDataM accountM) throws ApplicationException {
		try {
			// Get Primary Key
			logger.debug("accountM.getAccountId() :" + accountM.getPersonalAccountId());
			if (Util.empty(accountM.getPersonalAccountId())) {
				String accountId = GenerateUnique.generate(MConstant.ACCOUNT_ID_PK);
				accountM.setPersonalAccountId(accountId);
				accountM.setCreateBy(userId);
			}
			accountM.setUpdateBy(userId);
			createTableORIG_PERSONAL_ACCOUNT(accountM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_PERSONAL_ACCOUNT(AccountDataM accountM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PERSONAL_ACCOUNT ");
			sql.append("( PERSONAL_ACCOUNT_ID,PERSONAL_ID,ACCOUNT_TYPE,ACCOUNT_NO,ACCOUNT_NAME, ");
			sql.append("  OPEN_DATE,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, accountM.getPersonalAccountId());
			ps.setString(cnt++, accountM.getPersonalId());
			ps.setString(cnt++, accountM.getAccountType());
			ps.setString(cnt++, accountM.getAccountNo());
			ps.setString(cnt++, accountM.getAccountName());
			ps.setDate(cnt++, accountM.getOpenDate());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, accountM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, accountM.getUpdateBy());

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
	public void deleteOrigPersonalAccountM(String personalId, String accountId) throws ApplicationException {
		deleteTableORIG_PERSONAL_ACCOUNT(personalId, accountId);
	}

	private void deleteTableORIG_PERSONAL_ACCOUNT(String personalId, String accountId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_PERSONAL_ACCOUNT ");
			sql.append(" WHERE PERSONAL_ID = ?");
			if (!Util.empty(accountId)) {
				sql.append(" AND PERSONAL_ACCOUNT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if (!Util.empty(accountId)) {
				ps.setString(2, accountId);
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
	public ArrayList<AccountDataM> loadOrigPersonalAccountM(String personalID,Connection conn) throws ApplicationException {
		ArrayList<AccountDataM> result = selectTableORIG_PERSONAL_ACCOUNT(personalID,conn);
		return result;
	}

	private ArrayList<AccountDataM> selectTableORIG_PERSONAL_ACCOUNT(String personalID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AccountDataM> accountList = new ArrayList<AccountDataM>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT PERSONAL_ACCOUNT_ID,PERSONAL_ID,ACCOUNT_TYPE,ACCOUNT_NO,ACCOUNT_NAME,OPEN_DATE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_PERSONAL_ACCOUNT WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while (rs.next()) {
				AccountDataM accountM = new AccountDataM();
				accountM.setPersonalAccountId(rs.getString("PERSONAL_ACCOUNT_ID"));
				accountM.setPersonalId(rs.getString("PERSONAL_ID"));
				accountM.setAccountType(rs.getString("ACCOUNT_TYPE"));
				accountM.setAccountNo(rs.getString("ACCOUNT_NO"));
				accountM.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountM.setOpenDate(rs.getDate("OPEN_DATE"));
				accountM.setCreateBy(rs.getString("CREATE_BY"));
				accountM.setUpdateBy(rs.getString("UPDATE_BY"));
				accountM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				accountM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				accountList.add(accountM);
			}

			return accountList;
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
	public ArrayList<AccountDataM> loadOrigPersonalAccountM(String personalID) throws ApplicationException {
		ArrayList<AccountDataM> result = selectTableORIG_PERSONAL_ACCOUNT(personalID);
		return result;
	}

	private ArrayList<AccountDataM> selectTableORIG_PERSONAL_ACCOUNT(String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<AccountDataM> accountList = new ArrayList<AccountDataM>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT PERSONAL_ACCOUNT_ID,PERSONAL_ID,ACCOUNT_TYPE,ACCOUNT_NO,ACCOUNT_NAME,OPEN_DATE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_PERSONAL_ACCOUNT WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while (rs.next()) {
				AccountDataM accountM = new AccountDataM();
				accountM.setPersonalAccountId(rs.getString("PERSONAL_ACCOUNT_ID"));
				accountM.setPersonalId(rs.getString("PERSONAL_ID"));
				accountM.setAccountType(rs.getString("ACCOUNT_TYPE"));
				accountM.setAccountNo(rs.getString("ACCOUNT_NO"));
				accountM.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountM.setOpenDate(rs.getDate("OPEN_DATE"));
				accountM.setCreateBy(rs.getString("CREATE_BY"));
				accountM.setUpdateBy(rs.getString("UPDATE_BY"));
				accountM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				accountM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				accountList.add(accountM);
			}

			return accountList;
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
	public void saveUpdateOrigPersonalAccountM(AccountDataM accountM) throws ApplicationException {
		int returnRows = 0;
		accountM.setUpdateBy(userId);
		returnRows = updateTableORIG_PERSONAL_ACCOUNT(accountM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_PERSONAL_ACCOUNT then call Insert method");
			accountM.setCreateBy(userId);
			createOrigPersonalAccountM(accountM);
		}
	}

	private int updateTableORIG_PERSONAL_ACCOUNT(AccountDataM accountM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" UPDATE ORIG_PERSONAL_ACCOUNT ");
			sql.append(" SET ACCOUNT_TYPE = ?, ACCOUNT_NO = ?, ACCOUNT_NAME = ?,OPEN_DATE = ?, UPDATE_BY = ?,UPDATE_DATE = ? ");
			sql.append(" WHERE PERSONAL_ACCOUNT_ID = ? AND PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, accountM.getAccountType());
			ps.setString(cnt++, accountM.getAccountNo());
			ps.setString(cnt++, accountM.getAccountName());
			ps.setDate(cnt++, accountM.getOpenDate());
			ps.setString(cnt++, accountM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, accountM.getPersonalAccountId());
			ps.setString(cnt++, accountM.getPersonalId());

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
	public void deleteNotInKeyAccount(ArrayList<AccountDataM> accountList, String personalID) throws ApplicationException {
		deleteNotInKeyORIG_PERSONAL_ACCOUNT(accountList, personalID);
	}

	private void deleteNotInKeyORIG_PERSONAL_ACCOUNT(ArrayList<AccountDataM> accountList, String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_PERSONAL_ACCOUNT ");
			sql.append(" WHERE PERSONAL_ID = ? ");

			if (accountList != null && !accountList.isEmpty()) {
				sql.append(" AND PERSONAL_ACCOUNT_ID NOT IN ( ");
				for (AccountDataM accountM : accountList) {
					logger.debug("accountM.getAccountId() = " + accountM.getPersonalAccountId());
					sql.append(" '" + accountM.getPersonalAccountId() + "', ");
				}

				if (sql.toString().trim().endsWith(",")) {
					sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
				}

				sql.append(" ) ");
			}

			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			logger.error("ERROR >> ", e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ", e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public String loadAccountName(String personalID,String accountNo) throws ApplicationException {
		String result = selectAccountName(personalID,accountNo);
		return result;
	}

	private String selectAccountName(String personalID,String accountNo) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String result = "";
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT DISTINCT ACCOUNT_NO,ACCOUNT_NAME ");
			sql.append(" FROM ORIG_PERSONAL_ACCOUNT WHERE PERSONAL_ID = ? AND ACCOUNT_NO = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			ps.setString(2, accountNo);
			rs = ps.executeQuery();

			while (rs.next()) {
				result = rs.getString("ACCOUNT_NAME");
			}

			return result;
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
}
