package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.WisdomDataM;

public class OrigCardDAOImpl extends OrigObjectDAO implements OrigCardDAO {
	public OrigCardDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigCardDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigCardM(CardDataM cardM) throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("cardM.getCardId() :"+cardM.getCardId());
		    if(Util.empty(cardM.getCardId())){
				String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
				cardM.setCardId(cardId);
				cardM.setCreateBy(userId);
		    }
		    cardM.setUpdateBy(userId);
			createTableORIG_CARD(cardM);
			
			WisdomDataM wisdomM = cardM.getWisdom();
			if(wisdomM != null) {
				wisdomM.setCardId(cardM.getCardId());
				OrigWisdomDAO wisdomDAO = ORIGDAOFactory.getWisdomDAO(userId);
				wisdomDAO.createOrigWisdomM(wisdomM);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_CARD(CardDataM cardM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_CARD ");
			sql.append("( CARD_ID, CARD_NO, APPLICATION_TYPE, CARD_TYPE, ");
			sql.append(" LOAN_ID, HASHING_CARD_NO, SEQ, CAMPAIGN_CODE, CGA_CODE, ");
			sql.append(" CGA_APPLY_CHANNEL, MEMBERSHIP_NO, GANG_NO, NO_APP_IN_GANG, ");
			sql.append(" PERCENT_LIMIT_MAINCARD, MAIN_CARD_NO, MAIN_CARD_HOLDER_NAME, ");
			sql.append(" MAIN_CARD_PHONE_NO, CARD_FEE, CHASSIS_NO, REFERRAL_CARD_NO, ");
			sql.append(" CARD_FACE, CARD_LEVEL, CARDLINK_CARD_CODE, REQUEST_CARD_TYPE, ");
			sql.append(" CARD_NO_MARK, FLP_CARD_TYPE, RECOMMEND_CARD_CODE, CARDLINK_CUST_ID, ");
			sql.append(" CARD_NO_ENCRYPTED2, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" MAX_ELIGIBLE_CARD_TYPE, MAX_ELIGIBLE_CARD_LEVEL,COMPLETE_FLAG ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,  ?,?,?,  ");
			sql.append(" ?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,?,   ?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cardM.getCardId());
			ps.setString(cnt++, cardM.getCardNo());
			ps.setString(cnt++, cardM.getApplicationType());
			ps.setString(cnt++, cardM.getCardType());
			
			ps.setString(cnt++, cardM.getLoanId());
			ps.setString(cnt++, cardM.getHashingCardNo());
			ps.setBigDecimal(cnt++, cardM.getSeq());
			ps.setString(cnt++, cardM.getCampaignCode());			
			ps.setString(cnt++, cardM.getCgaCode());
			
			ps.setString(cnt++, cardM.getCgaApplyChannel());			
			ps.setString(cnt++, cardM.getMembershipNo());
			ps.setString(cnt++, cardM.getGangNo());
			ps.setBigDecimal(cnt++, cardM.getNoAppInGang());
			
			ps.setString(cnt++, cardM.getPercentLimitMaincard());
			ps.setString(cnt++, cardM.getMainCardNo());
			ps.setString(cnt++, cardM.getMainCardHolderName());
			
			ps.setString(cnt++, cardM.getMainCardPhoneNo());
			ps.setString(cnt++, cardM.getCardFee());
			ps.setString(cnt++, cardM.getChassisNo());
			ps.setString(cnt++, cardM.getReferralCardNo());
			
			ps.setString(cnt++, cardM.getPlasticCode());
			ps.setString(cnt++, cardM.getCardLevel());
			ps.setString(cnt++, cardM.getCardLinkCardCode());
			ps.setString(cnt++, cardM.getRequestCardType());
			
			ps.setString(cnt++, cardM.getCardNoMark());
			ps.setString(cnt++, cardM.getFlpCardType());
			ps.setString(cnt++, cardM.getRecommendCardCode());
			ps.setString(cnt++, cardM.getCardlinkCustId());
			
			ps.setString(cnt++, cardM.getCardNoEncrypted());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cardM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, cardM.getUpdateBy());
			
			//KBMF V2.4
			ps.setString(cnt++, cardM.getMaxEligibleCardType());
			ps.setString(cnt++, cardM.getMaxEligibleCardLevel());
			
			ps.setString(cnt++, cardM.getCompleteFlag());
			
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
	public void deleteOrigCardM(String loanID, String cardId) throws ApplicationException {
		OrigWisdomDAO wisdomDAO = ORIGDAOFactory.getWisdomDAO();
		if(Util.empty(cardId)) {
			String cardID = selectCardIdORIG_CARD(loanID);
			if(!Util.empty(cardID)) {
				wisdomDAO.deleteOrigWisdomM(cardID);
			}
		} else {
			wisdomDAO.deleteOrigWisdomM(cardId);
		}
		
		deleteTableORIG_CARD(loanID, cardId);
	}

	private String selectCardIdORIG_CARD(String loanID) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CARD_ID ");
			sql.append(" FROM ORIG_CARD WHERE LOAN_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanID);

			rs = ps.executeQuery();

			if(rs.next()) {
				return rs.getString("CARD_ID");
			}

			return null;
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

	private void deleteTableORIG_CARD(String loanID, String cardId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_CARD ");
			sql.append(" WHERE LOAN_ID = ? ");
			if(cardId != null && !cardId.isEmpty()) {
				sql.append(" AND CARD_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, loanID);
			if(cardId != null && !cardId.isEmpty()) {
				ps.setString(2, cardId);
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
	public CardDataM loadOrigCardM(String loanID) throws ApplicationException {
		CardDataM result = selectTableORIG_CARD(loanID);
		
		if(!Util.empty(result)) {
			OrigWisdomDAO wisdomDAO = ORIGDAOFactory.getWisdomDAO();
			WisdomDataM wisdomM = wisdomDAO.loadOrigWisdomM(result.getCardId());
			if(wisdomM != null) {
				result.setWisdom(wisdomM);
			}
		}
		return result;
	}
	@Override
	public CardDataM loadOrigCardM(String loanID, Connection conn)
			throws ApplicationException {
		CardDataM result = selectTableORIG_CARD(loanID,conn);
		if(!Util.empty(result)) {
			OrigWisdomDAO wisdomDAO = ORIGDAOFactory.getWisdomDAO();
			WisdomDataM wisdomM = wisdomDAO.loadOrigWisdomM(result.getCardId(),conn);
			if(wisdomM != null) {
				result.setWisdom(wisdomM);
			}
		}
		return result;
	}
	@Override
	public CardDataM loadOrigCardMGroup(String loanID) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_CARD(loanID,conn);
		}catch (Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	private CardDataM selectTableORIG_CARD(String loanID) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_CARD(loanID,conn);
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
	private CardDataM selectTableORIG_CARD(String loanID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		CardDataM cardM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CARD_ID, CARD_NO, APPLICATION_TYPE, CARD_TYPE, ");
			sql.append(" LOAN_ID, HASHING_CARD_NO, SEQ, CAMPAIGN_CODE, CGA_CODE, ");
			sql.append(" CGA_APPLY_CHANNEL, MEMBERSHIP_NO, GANG_NO, NO_APP_IN_GANG, ");
			sql.append(" PERCENT_LIMIT_MAINCARD, MAIN_CARD_NO, MAIN_CARD_HOLDER_NAME, ");
			sql.append(" MAIN_CARD_PHONE_NO, CARD_FEE, CHASSIS_NO, REFERRAL_CARD_NO, ");
			sql.append(" CARD_FACE, CARD_LEVEL, CARDLINK_CARD_CODE, REQUEST_CARD_TYPE, ");
			sql.append(" CARD_NO_MARK, FLP_CARD_TYPE, RECOMMEND_CARD_CODE, CARDLINK_CUST_ID, ");
			sql.append(" CARD_NO_ENCRYPTED2, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" MAX_ELIGIBLE_CARD_TYPE, MAX_ELIGIBLE_CARD_LEVEL, COMPLETE_FLAG ");
			sql.append(" FROM ORIG_CARD  WHERE LOAN_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanID);

			rs = ps.executeQuery();

			if (rs.next()) {
				cardM = new CardDataM();
				cardM.setCardId(rs.getString("CARD_ID"));
				cardM.setCardNo(rs.getString("CARD_NO"));
				cardM.setApplicationType(rs.getString("APPLICATION_TYPE"));
				cardM.setCardType(rs.getString("CARD_TYPE"));
				
				cardM.setLoanId(rs.getString("LOAN_ID"));
				cardM.setHashingCardNo(rs.getString("HASHING_CARD_NO"));
				cardM.setSeq(rs.getBigDecimal("SEQ"));
				cardM.setCampaignCode(rs.getString("CAMPAIGN_CODE"));
				cardM.setCgaCode(rs.getString("CGA_CODE"));
				
				cardM.setCgaApplyChannel(rs.getString("CGA_APPLY_CHANNEL"));
				cardM.setMembershipNo(rs.getString("MEMBERSHIP_NO"));
				cardM.setGangNo(rs.getString("GANG_NO"));
				cardM.setNoAppInGang(rs.getBigDecimal("NO_APP_IN_GANG"));
				
				cardM.setPercentLimitMaincard(rs.getString("PERCENT_LIMIT_MAINCARD"));
				cardM.setMainCardNo(rs.getString("MAIN_CARD_NO"));
				cardM.setMainCardHolderName(rs.getString("MAIN_CARD_HOLDER_NAME"));
				
				cardM.setMainCardPhoneNo(rs.getString("MAIN_CARD_PHONE_NO"));
				cardM.setCardFee(rs.getString("CARD_FEE"));
				cardM.setChassisNo(rs.getString("CHASSIS_NO"));
				cardM.setReferralCardNo(rs.getString("REFERRAL_CARD_NO"));
				
				cardM.setPlasticCode(rs.getString("CARD_FACE"));
				cardM.setCardLevel(rs.getString("CARD_LEVEL"));
				cardM.setCardLinkCardCode(rs.getString("CARDLINK_CARD_CODE"));
				cardM.setRequestCardType(rs.getString("REQUEST_CARD_TYPE"));
				
				cardM.setCardNoMark(rs.getString("CARD_NO_MARK"));
				cardM.setFlpCardType(rs.getString("FLP_CARD_TYPE"));
				cardM.setRecommendCardCode(rs.getString("RECOMMEND_CARD_CODE"));
				cardM.setCardlinkCustId(rs.getString("CARDLINK_CUST_ID"));
				
				cardM.setCardNoEncrypted(rs.getString("CARD_NO_ENCRYPTED2"));
				
				cardM.setCreateBy(rs.getString("CREATE_BY"));
				cardM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				cardM.setUpdateBy(rs.getString("UPDATE_BY"));
				cardM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				cardM.setMaxEligibleCardType(rs.getString("MAX_ELIGIBLE_CARD_TYPE"));
				cardM.setMaxEligibleCardLevel(rs.getString("MAX_ELIGIBLE_CARD_LEVEL"));
				
				cardM.setCompleteFlag(rs.getString("COMPLETE_FLAG"));
			}

			return cardM;
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
	public void saveUpdateOrigCardM(CardDataM cardM)
			throws ApplicationException {
		int returnRows = 0;
		cardM.setUpdateBy(userId);
		returnRows = updateTableORIG_CARD(cardM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_CARD then call Insert method");
			cardM.setCreateBy(userId);
			createOrigCardM(cardM);
		} else {
		
			WisdomDataM wisdomM = cardM.getWisdom();
			if(wisdomM != null) {
				wisdomM.setCardId(cardM.getCardId());
				OrigWisdomDAO wisdomDAO = ORIGDAOFactory.getWisdomDAO(userId);
				wisdomDAO.saveUpdateOrigWisdomM(wisdomM);
			}
		}
	}

	private int updateTableORIG_CARD(CardDataM cardM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_CARD ");
			sql.append(" SET CARD_NO = ?, APPLICATION_TYPE = ?, CARD_TYPE = ?, ");
			sql.append(" HASHING_CARD_NO = ?, SEQ = ?, CAMPAIGN_CODE = ?, CGA_CODE = ?, ");
			sql.append(" CGA_APPLY_CHANNEL = ?, MEMBERSHIP_NO = ?, GANG_NO = ?, NO_APP_IN_GANG = ?, ");
			sql.append(" PERCENT_LIMIT_MAINCARD = ?, MAIN_CARD_NO = ?, MAIN_CARD_HOLDER_NAME = ?, ");
			sql.append(" MAIN_CARD_PHONE_NO = ?, CARD_FEE = ?, CHASSIS_NO = ?, REFERRAL_CARD_NO = ?, ");
			sql.append(" CARD_FACE = ?, CARD_LEVEL = ?, CARDLINK_CARD_CODE = ?, REQUEST_CARD_TYPE = ?, ");
			sql.append(" CARD_NO_MARK = ?, FLP_CARD_TYPE = ?, RECOMMEND_CARD_CODE = ?, CARDLINK_CUST_ID=?, ");
			sql.append(" CARD_NO_ENCRYPTED2 = ?, UPDATE_BY = ?,UPDATE_DATE = ?, ");
			sql.append(" MAX_ELIGIBLE_CARD_TYPE = ?, MAX_ELIGIBLE_CARD_LEVEL = ?, ");
			sql.append(" COMPLETE_FLAG = ? ");
			
			sql.append(" WHERE CARD_ID = ? AND LOAN_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cardM.getCardNo());
			ps.setString(cnt++, cardM.getApplicationType());
			ps.setString(cnt++, cardM.getCardType());
			
			ps.setString(cnt++, cardM.getHashingCardNo());
			ps.setBigDecimal(cnt++, cardM.getSeq());
			ps.setString(cnt++, cardM.getCampaignCode());			
			ps.setString(cnt++, cardM.getCgaCode());
			
			ps.setString(cnt++, cardM.getCgaApplyChannel());
			ps.setString(cnt++, cardM.getMembershipNo());
			ps.setString(cnt++, cardM.getGangNo());
			ps.setBigDecimal(cnt++, cardM.getNoAppInGang());
			
			ps.setString(cnt++, cardM.getPercentLimitMaincard());
			ps.setString(cnt++, cardM.getMainCardNo());
			ps.setString(cnt++, cardM.getMainCardHolderName());
			
			ps.setString(cnt++, cardM.getMainCardPhoneNo());
			ps.setString(cnt++, cardM.getCardFee());
			ps.setString(cnt++, cardM.getChassisNo());
			ps.setString(cnt++, cardM.getReferralCardNo());
			
			ps.setString(cnt++, cardM.getPlasticCode());
			ps.setString(cnt++, cardM.getCardLevel());
			ps.setString(cnt++, cardM.getCardLinkCardCode());
			ps.setString(cnt++, cardM.getRequestCardType());
			
			ps.setString(cnt++, cardM.getCardNoMark());
			ps.setString(cnt++, cardM.getFlpCardType());
			ps.setString(cnt++, cardM.getRecommendCardCode());
			ps.setString(cnt++, cardM.getCardlinkCustId());
			
			ps.setString(cnt++, cardM.getCardNoEncrypted());
			
			ps.setString(cnt++, cardM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			//KBMF V2.4
			ps.setString(cnt++, cardM.getMaxEligibleCardType());
			ps.setString(cnt++, cardM.getMaxEligibleCardLevel());
			
			ps.setString(cnt++, cardM.getCompleteFlag());
			
			// WHERE CLAUSE
			ps.setString(cnt++, cardM.getCardId());
			ps.setString(cnt++, cardM.getLoanId());
			
			
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

}
