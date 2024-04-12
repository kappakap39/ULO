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
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;

public class OrigCardLinkCustomerDAOImpl extends OrigObjectDAO implements OrigCardLinkCustomerDAO {
	public OrigCardLinkCustomerDAOImpl(String userId) {
		this.userId = userId;
	}

	public OrigCardLinkCustomerDAOImpl() {

	}

	private String userId = "";

	@Override
	public void createOrigCardlinkCustomerM(CardlinkCustomerDataM cardLinkCustM) throws ApplicationException {
		try {
			// Get Primary Key
			logger.debug("cardLinkCustM.getCardlinkCustId() :" + cardLinkCustM.getCardlinkCustId());
			if (Util.empty(cardLinkCustM.getCardlinkCustId())) {
				String cardLinkCustId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARDLINK_CUSTOMER_PK);
				cardLinkCustM.setCardlinkCustId(cardLinkCustId);
				cardLinkCustM.setCreateBy(userId);
			}
			cardLinkCustM.setUpdateBy(userId);
			createTableORIG_CARDLINK_CUSTOMER(cardLinkCustM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_CARDLINK_CUSTOMER(CardlinkCustomerDataM cardLinkCustM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_CARDLINK_CUSTOMER ");
			sql.append("( CARDLINK_CUST_ID, ORIG_ID, PERSONAL_ID, CARDLINK_CUST_NO, ");
			sql.append(" NEW_CARDLINK_CUST_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cardLinkCustM.getCardlinkCustId());
			ps.setString(cnt++, cardLinkCustM.getOrgId());
			ps.setString(cnt++, cardLinkCustM.getPersonalId());
			ps.setString(cnt++, cardLinkCustM.getCardlinkCustNo());

			ps.setString(cnt++, cardLinkCustM.getNewCardlinkCustFlag());

			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cardLinkCustM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cardLinkCustM.getUpdateBy());

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
	public void deleteOrigCardlinkCustomerM(String personalID, String cardlinkCustId) throws ApplicationException {
		deleteTableORIG_CARDLINK_CUSTOMER(personalID, cardlinkCustId);
	}

	private void deleteTableORIG_CARDLINK_CUSTOMER(String personalID, String cardlinkCustId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_CARDLINK_CUSTOMER ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if (cardlinkCustId != null && !cardlinkCustId.isEmpty()) {
				sql.append(" AND CARDLINK_CUST_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
			if (cardlinkCustId != null && !cardlinkCustId.isEmpty()) {
				ps.setString(2, cardlinkCustId);
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
	public ArrayList<CardlinkCustomerDataM> loadOrigCardlinkCustomerM(String personalID) throws ApplicationException {
		Connection conn = null;
		try {
			conn = getConnection();
			return selectTableORIG_CARDLINK_CUSTOMER(personalID, conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}

	}

	@Override
	public ArrayList<CardlinkCustomerDataM> loadOrigCardlinkCustomerM(String personalID, Connection conn) throws ApplicationException {
		return selectTableORIG_CARDLINK_CUSTOMER(personalID, conn);
	}

	private ArrayList<CardlinkCustomerDataM> selectTableORIG_CARDLINK_CUSTOMER(String personalID, Connection conn) throws ApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CardlinkCustomerDataM> cardLinkCustList = new ArrayList<CardlinkCustomerDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CARDLINK_CUST_ID, ORIG_ID, PERSONAL_ID, CARDLINK_CUST_NO, ");
			sql.append(" NEW_CARDLINK_CUST_FLAG, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_CARDLINK_CUSTOMER  WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while (rs.next()) {
				CardlinkCustomerDataM cardLinkCustM = new CardlinkCustomerDataM();
				cardLinkCustM.setCardlinkCustId(rs.getString("CARDLINK_CUST_ID"));
				cardLinkCustM.setOrgId(rs.getString("ORIG_ID"));
				cardLinkCustM.setPersonalId(rs.getString("PERSONAL_ID"));
				cardLinkCustM.setCardlinkCustNo(rs.getString("CARDLINK_CUST_NO"));

				cardLinkCustM.setNewCardlinkCustFlag(rs.getString("NEW_CARDLINK_CUST_FLAG"));

				cardLinkCustM.setCreateBy(rs.getString("CREATE_BY"));
				cardLinkCustM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				cardLinkCustM.setUpdateBy(rs.getString("UPDATE_BY"));
				cardLinkCustM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				cardLinkCustList.add(cardLinkCustM);
			}

			return cardLinkCustList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public void saveUpdateOrigCardlinkCustomerM(CardlinkCustomerDataM cardLinkCustM) throws ApplicationException {
		int returnRows = 0;
		cardLinkCustM.setUpdateBy(userId);
		returnRows = updateTableORIG_CARDLINK_CUSTOMER(cardLinkCustM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_CARDLINK_CUSTOMER then call Insert method");
			cardLinkCustM.setCreateBy(userId);
			createOrigCardlinkCustomerM(cardLinkCustM);
		}
	}

	private int updateTableORIG_CARDLINK_CUSTOMER(CardlinkCustomerDataM cardLinkCustM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_CARDLINK_CUSTOMER ");
			sql.append(" SET ORIG_ID = ?, CARDLINK_CUST_NO = ?, NEW_CARDLINK_CUST_FLAG = ?, ");
			sql.append(" UPDATE_BY = ?,UPDATE_DATE = ? ");

			sql.append(" WHERE CARDLINK_CUST_ID = ? AND PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cardLinkCustM.getOrgId());
			ps.setString(cnt++, cardLinkCustM.getCardlinkCustNo());
			ps.setString(cnt++, cardLinkCustM.getNewCardlinkCustFlag());

			ps.setString(cnt++, cardLinkCustM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, cardLinkCustM.getCardlinkCustId());
			ps.setString(cnt++, cardLinkCustM.getPersonalId());

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
	public void deleteNotInKeyCardlinkCustomer(ArrayList<CardlinkCustomerDataM> cardLinkCustList, String personalId) throws ApplicationException {
		deleteNotInKeyTableORIG_CARDLINK_CUSTOMER(cardLinkCustList, personalId);
	}

	private void deleteNotInKeyTableORIG_CARDLINK_CUSTOMER(ArrayList<CardlinkCustomerDataM> cardLinkCustList, String personalId)
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_CARDLINK_CUSTOMER ");
			sql.append(" WHERE PERSONAL_ID = ? ");

			if (!Util.empty(cardLinkCustList)) {
				sql.append(" AND CARDLINK_CUST_ID NOT IN ( ");
				for (CardlinkCustomerDataM cardLinkCustM : cardLinkCustList) {
					logger.debug("cardLinkCustM.getCardlinkCustId() = " + cardLinkCustM.getCardlinkCustId());
					sql.append(" '" + cardLinkCustM.getCardlinkCustId() + "', ");
				}

				if (sql.toString().trim().endsWith(",")) {
					sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
				}

				sql.append(" ) ");
			}

			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);

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

}
