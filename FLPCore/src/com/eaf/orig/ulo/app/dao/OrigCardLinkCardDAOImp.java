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
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;

public class OrigCardLinkCardDAOImp extends OrigObjectDAO implements OrigCardLinkCardDAO {
	public OrigCardLinkCardDAOImp(String userId) {
		this.userId = userId;
	}

	public OrigCardLinkCardDAOImp() {

	}

	private String userId;

	@Override
	public void createOrigCardlinkCardM(CardlinkCardDataM cardLinkCardM) throws ApplicationException {
		try {
			// Get Primary Key
			logger.debug("cardLinkCardM.getCardlinkCardId() :" + cardLinkCardM.getCardlinkCardId());
			if (Util.empty(cardLinkCardM.getCardlinkCardId())) {
				String cardLinkCardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARDLINK_CARD_PK);
				cardLinkCardM.setCardlinkCardId(cardLinkCardId);
				cardLinkCardM.setCreateBy(userId);
			}
			cardLinkCardM.setUpdateBy(userId);
			createTableORIG_CARDLINK_CARD(cardLinkCardM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_CARDLINK_CARD(CardlinkCardDataM cardLinkCardM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_CARDLINK_CARD ");
			sql.append("( PERSONAL_ID, CARDLINK_CARD_ID, CARDLINK_CUST_NO, ORIG_ID, ");
			sql.append(" CARD_NO, BLOCK_CODE, BLOCK_DATE, PROJECT_CODE, COA_PRODUCT_CODE, ");
			sql.append(" SUP_CUST_NO, SUP_ORG_ID, CARD_CODE, PLASTIC_CODE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,   ?,?,?,?,?,  ?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cardLinkCardM.getPersonalId());
			ps.setString(cnt++, cardLinkCardM.getCardlinkCardId());
			ps.setString(cnt++, cardLinkCardM.getCardlinkCustNo());
			ps.setString(cnt++, cardLinkCardM.getOrgId());

			ps.setString(cnt++, cardLinkCardM.getCardNo());
			ps.setString(cnt++, cardLinkCardM.getBlockCode());
			ps.setDate(cnt++, cardLinkCardM.getBlockDate());
			ps.setString(cnt++, cardLinkCardM.getProjectCode());
			ps.setString(cnt++, cardLinkCardM.getCoaProductCode());

			ps.setString(cnt++, cardLinkCardM.getSupCustNo());
			ps.setString(cnt++, cardLinkCardM.getSupOrgId());
			ps.setString(cnt++, cardLinkCardM.getCardCode());
			ps.setString(cnt++, cardLinkCardM.getPlasticCode());

			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cardLinkCardM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cardLinkCardM.getUpdateBy());

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
	public void deleteOrigCardlinkCardM(String personalID, String cardlinkCardId) throws ApplicationException {
		deleteTableORIG_CARDLINK_CARD(personalID, cardlinkCardId);
	}

	private void deleteTableORIG_CARDLINK_CARD(String personalID, String cardlinkCardId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_CARDLINK_CARD ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if (!Util.empty(cardlinkCardId)) {
				sql.append(" AND CARDLINK_CARD_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
			if (!Util.empty(cardlinkCardId)) {
				ps.setString(2, cardlinkCardId);
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
	public ArrayList<CardlinkCardDataM> loadOrigCardlinkCardM(String personalID) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_CARDLINK_CARD(personalID,conn);
		}catch (Exception e) {
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
	public ArrayList<CardlinkCardDataM> loadOrigCardlinkCardM(
			String personalID, Connection conn) throws ApplicationException {
		return selectTableORIG_CARDLINK_CARD(personalID, conn);
	}
	
	private ArrayList<CardlinkCardDataM> selectTableORIG_CARDLINK_CARD(String personalID,Connection conn) 
			throws ApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CardlinkCardDataM> cardLinkCardList = new ArrayList<CardlinkCardDataM>();
		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PERSONAL_ID, CARDLINK_CARD_ID, CARDLINK_CUST_NO, ORIG_ID, ");
			sql.append(" CARD_NO, BLOCK_CODE, BLOCK_DATE, PROJECT_CODE, COA_PRODUCT_CODE, ");
			sql.append(" SUP_CUST_NO, SUP_ORG_ID, CARD_CODE, PLASTIC_CODE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_CARDLINK_CARD  WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			if (rs.next()) {
				CardlinkCardDataM cardLinkCardM = new CardlinkCardDataM();
				cardLinkCardM.setPersonalId(rs.getString("PERSONAL_ID"));
				cardLinkCardM.setCardlinkCardId(rs.getString("CARDLINK_CARD_ID"));
				cardLinkCardM.setCardlinkCustNo(rs.getString("CARDLINK_CUST_NO"));
				cardLinkCardM.setOrgId(rs.getString("ORIG_ID"));

				cardLinkCardM.setCardNo(rs.getString("CARD_NO"));
				cardLinkCardM.setBlockCode(rs.getString("BLOCK_CODE"));
				cardLinkCardM.setBlockDate(rs.getDate("BLOCK_DATE"));
				cardLinkCardM.setProjectCode(rs.getString("PROJECT_CODE"));
				cardLinkCardM.setCoaProductCode(rs.getString("COA_PRODUCT_CODE"));

				cardLinkCardM.setSupCustNo(rs.getString("SUP_CUST_NO"));
				cardLinkCardM.setSupOrgId(rs.getString("SUP_ORG_ID"));
				cardLinkCardM.setCardCode(rs.getString("CARD_CODE"));
				cardLinkCardM.setPlasticCode(rs.getString("PLASTIC_CODE"));

				cardLinkCardM.setCreateBy(rs.getString("CREATE_BY"));
				cardLinkCardM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				cardLinkCardM.setUpdateBy(rs.getString("UPDATE_BY"));
				cardLinkCardM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				cardLinkCardList.add(cardLinkCardM);
			}

			return cardLinkCardList;
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
	public void saveUpdateOrigCardlinkCardM(CardlinkCardDataM cardLinkCardM) throws ApplicationException {
		int returnRows = 0;
		cardLinkCardM.setUpdateBy(userId);
		returnRows = updateTableORIG_CARDLINK_CARD(cardLinkCardM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_CARDLINK_CARD then call Insert method");
			cardLinkCardM.setCreateBy(userId);
			createOrigCardlinkCardM(cardLinkCardM);
		}
	}
	
	private int updateTableORIG_CARDLINK_CARD(CardlinkCardDataM cardLinkCardM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_CARDLINK_CARD ");
			sql.append(" SET CARDLINK_CUST_NO = ?, ORIG_ID = ?, CARD_NO = ?, ");
			sql.append(" BLOCK_CODE = ?, BLOCK_DATE = ?, PROJECT_CODE = ?, COA_PRODUCT_CODE = ?, ");
			sql.append(" SUP_CUST_NO = ?, SUP_ORG_ID = ?, CARD_CODE = ?, PLASTIC_CODE = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");

			sql.append(" WHERE CARDLINK_CARD_ID = ? AND PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cardLinkCardM.getCardlinkCustNo());
			ps.setString(cnt++, cardLinkCardM.getOrgId());
			ps.setString(cnt++, cardLinkCardM.getCardNo());

			ps.setString(cnt++, cardLinkCardM.getBlockCode());
			ps.setDate(cnt++, cardLinkCardM.getBlockDate());
			ps.setString(cnt++, cardLinkCardM.getProjectCode());
			ps.setString(cnt++, cardLinkCardM.getCoaProductCode());

			ps.setString(cnt++, cardLinkCardM.getSupCustNo());
			ps.setString(cnt++, cardLinkCardM.getSupOrgId());
			ps.setString(cnt++, cardLinkCardM.getCardCode());
			ps.setString(cnt++, cardLinkCardM.getPlasticCode());

			ps.setString(cnt++, cardLinkCardM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, cardLinkCardM.getCardlinkCardId());
			ps.setString(cnt++, cardLinkCardM.getPersonalId());

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
	public void deleteNotInKeyCardlinkCard(ArrayList<CardlinkCardDataM> cardLinkCardList, String personalId) throws ApplicationException {
		deleteNotInKeyTableORIG_CARDLINK_CARD(cardLinkCardList, personalId);
	}

	private void deleteNotInKeyTableORIG_CARDLINK_CARD(ArrayList<CardlinkCardDataM> cardLinkCardList, String personalId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// conn = Get Connection
			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_CARDLINK_CARD ");
			sql.append(" WHERE PERSONAL_ID = ? ");

			if (!Util.empty(cardLinkCardList)) {
				sql.append(" AND CARDLINK_CARD_ID NOT IN ( ");
				for (CardlinkCardDataM cardLinkCardM : cardLinkCardList) {
					logger.debug("cardLinkCardM.getCardlinkCardId() = " + cardLinkCardM.getCardlinkCardId());
					sql.append(" '" + cardLinkCardM.getCardlinkCardId() + "', ");
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
