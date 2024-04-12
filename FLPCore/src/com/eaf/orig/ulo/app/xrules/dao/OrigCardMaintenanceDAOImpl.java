package com.eaf.orig.ulo.app.xrules.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDataM;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDetailDataM;
import com.eaf.orig.ulo.model.cardMaintenance.PersonalCardMaintenanceDataM;


public class OrigCardMaintenanceDAOImpl extends OrigObjectDAO implements OrigCardMaintenanceDAO{
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public OrigCardMaintenanceDAOImpl() {
		
	}
	public OrigCardMaintenanceDAOImpl(String userId) {
		this.userId = userId;
	}
	private String userId;
	@Override
	public CardMaintenanceDataM getCardMaintenance(String applicationGroupId,String cardlinkRefNo)throws ApplicationException {
		return getCardMaintenance(applicationGroupId, cardlinkRefNo, "");
	}
	@Override
	public CardMaintenanceDataM getCardMaintenance(String applicationGroupId,String cardlinkRefNo, String regType)throws ApplicationException {
		CardMaintenanceDataM cardMaintenance = selectCardMaintenanceData(applicationGroupId,cardlinkRefNo, regType);
		if(Util.empty(cardMaintenance)){
			cardMaintenance = new CardMaintenanceDataM();
		}
		return cardMaintenance;
	}
	private CardMaintenanceDataM selectCardMaintenanceData(String applicationGroupId,String cardlinkRefNo, String regType) throws ApplicationException{
		CardMaintenanceDataM cardMaintenance =  new CardMaintenanceDataM();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTINCT ");
			sql.append("     G.APPLICATION_GROUP_NO AS QR, ");
			sql.append("     G.APPLICATION_GROUP_ID, ");
			sql.append("     A.CARDLINK_REF_NO, ");
			sql.append("     A.APPLICATION_RECORD_ID, ");
			sql.append("     A.APPLICATION_TYPE AS APPLY_TYPE, ");
			sql.append("     TO_CHAR(CR.PROCESSIONG_DATE,'dd/MM/yyyy | HH:MM','NLS_CALENDAR=''THAI BUDDHA') AS PROCESSIONG_DATE, ");
			sql.append("     CC.CARDLINK_CUST_NO, ");
			sql.append("     CC.CARDLINK_STATUS AS PERSONAL_STATUS, ");
			sql.append("     A.BUSINESS_CLASS_ID, ");
			sql.append("     P.IDNO, ");
			sql.append("     P.PERSONAL_ID, ");
			sql.append("     P.TH_FIRST_NAME, ");
			sql.append("     P.TH_LAST_NAME, ");
			sql.append("     P.PERSONAL_TYPE, ");
			sql.append("     C.CARD_NO_MARK, ");
			sql.append("     A.CARDLINK_FLAG CARD_STATUS, ");
			sql.append("     C.APPLICATION_TYPE, ");
			sql.append("     C.CARD_TYPE, ");
			sql.append("     C.CARD_LEVEL, ");
			sql.append("     BM.DISPLAY_NAME CARD_DESC, ");
			sql.append("     L.LOAN_AMT, ");
			sql.append("     C.CARD_ID, ");
			sql.append("     C.CARD_NO, ");
			sql.append("     A.CARDLINK_SEQ, ");
			sql.append("     CR.MAIN_CUST_RESP_DESC, ");
			sql.append("     CR.SUP_CUST_RESP_DESC, ");
			sql.append("     CR.MAIN_CARD_RESP_DESC1, ");
			sql.append("     CR.SUP_CARD_RESP_DESC1, ");
			sql.append("     CR.MAIN_CARD_RESP_DESC2, ");
			sql.append("     CR.SUP_CARD_RESP_DESC2, ");
			sql.append("     CR.MAIN_CARD_RESP_DESC3, ");
			sql.append("     CR.SUP_CARD_RESP_DESC3, ");
			sql.append("     CR.MAIN_CARD_NO1, ");
			sql.append("     CR.MAIN_CARD_NO2, ");
			sql.append("     CR.MAIN_CARD_NO3, ");
			sql.append("     CR.SUP_CARD_NO1, ");
			sql.append("     CR.SUP_CARD_NO2, ");
			sql.append("     CR.SUP_CARD_NO3 ");
			sql.append(" FROM " + PREFIX + "ORIG_APPLICATION_GROUP G ");
			sql.append(" JOIN " + PREFIX + "ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID AND A.CARDLINK_FLAG IN ('F','W','S') ");
			sql.append(" JOIN " + PREFIX + "ORIG_LOAN L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" JOIN " + PREFIX + "ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
			sql.append(" JOIN " + PREFIX + "ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" JOIN " + PREFIX + "ORIG_PERSONAL_INFO P ON PR.PERSONAL_ID = P.PERSONAL_ID ");
			sql.append(" JOIN " + PREFIX + "INF_CARDLINK_RESULT CR ON CR.REF_ID = A.CARDLINK_REF_NO ");
			sql.append(" JOIN " + PREFIX + "ORIG_CARDLINK_CUSTOMER CC ON CC.CARDLINK_CUST_ID = C.CARDLINK_CUST_ID AND CC.CARDLINK_STATUS IN ('F','W','S') ");
			sql.append(" JOIN " + PREFIX + "INF_BATCH_LOG BL ON BL.REF_ID = A.CARDLINK_REF_NO AND BL.INTERFACE_CODE = 'CL001' ");
			sql.append(" LEFT JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = C.CARD_TYPE ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER BM ON  BM.FIELD_ID = '108' AND BM.CHOICE_NO = CT.CARD_CODE ");
			sql.append(" WHERE ");
			sql.append("     G.APPLICATION_GROUP_ID = ?  AND A.CARDLINK_REF_NO = ? ");
			sql.append(" AND CR.RESULT_ID = (SELECT MAX(CR1.RESULT_ID) FROM  " + PREFIX + "INF_CARDLINK_RESULT CR1 WHERE CR1.REF_ID = A.CARDLINK_REF_NO) ");
			sql.append(" ORDER BY ");
			sql.append("     P.PERSONAL_TYPE, ");
			sql.append("     G.APPLICATION_GROUP_NO, ");
			sql.append("     A.CARDLINK_FLAG, ");
			sql.append("     A.CARDLINK_REF_NO, ");
			sql.append("     A.CARDLINK_SEQ, ");
			sql.append("     PROCESSIONG_DATE ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			ps.setString(2, cardlinkRefNo);
			rs = null;
			rs = ps.executeQuery();
			while(rs.next()){
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_GROUP_NO = rs.getString("QR");
				String CARDLINK_REF_NO = rs.getString("CARDLINK_REF_NO");
				String CARDLINK_FLAG = rs.getString("CARD_STATUS");
				String PROCESSING_DATE = rs.getString("PROCESSIONG_DATE");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				cardMaintenance.setApplicationGroupId(APPLICATION_GROUP_ID);
				cardMaintenance.setApplicationGroupNo(APPLICATION_GROUP_NO);
				cardMaintenance.setCardlinkRefNo(CARDLINK_REF_NO);
				cardMaintenance.setSendCardklinkFlag(CARDLINK_FLAG);
				cardMaintenance.setProcessingDate(PROCESSING_DATE);
				cardMaintenance.setRegType(regType);
				
				PersonalCardMaintenanceDataM personalCardMaintenance =  cardMaintenance.getPersonalCardMaintenanceById(PERSONAL_ID);
				if(null==personalCardMaintenance){
					personalCardMaintenance = new PersonalCardMaintenanceDataM();
					cardMaintenance.addPersonalCardMaintenanceById(personalCardMaintenance);
				}
				
				String CARDLINK_CUST_NO = rs.getString("CARDLINK_CUST_NO");
				String TH_FIRST_NAME = rs.getString("TH_FIRST_NAME");
				String TH_LAST_NAME = rs.getString("TH_LAST_NAME");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PERSONAL_STATUS = rs.getString("PERSONAL_STATUS");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String CUST_ERROR_DESC = rs.getString("MAIN_CUST_RESP_DESC");
				String CUST_SUP_ERROR_DESC = rs.getString("SUP_CUST_RESP_DESC");
				
				personalCardMaintenance.setPersonalId(PERSONAL_ID);
				personalCardMaintenance.setCustNo(CARDLINK_CUST_NO);
				personalCardMaintenance.setBusinessClassId(BUSINESS_CLASS_ID);
				personalCardMaintenance.setFirstname(TH_FIRST_NAME);
				personalCardMaintenance.setLastname(TH_LAST_NAME);
				personalCardMaintenance.setPersonalType(PERSONAL_TYPE);
				personalCardMaintenance.setPersonalStatus(PERSONAL_STATUS);
				personalCardMaintenance.setErrorCustDesc(CUST_ERROR_DESC);
				personalCardMaintenance.setErrorSupCustDesc(CUST_SUP_ERROR_DESC);
				
				ArrayList<CardMaintenanceDetailDataM> cardMaintenanceDetails = personalCardMaintenance.getCardMaintenanceDetails();
				if(null==cardMaintenanceDetails){
					cardMaintenanceDetails = new ArrayList<CardMaintenanceDetailDataM>();
					personalCardMaintenance.setCardMaintenanceDetails(cardMaintenanceDetails);
				}
				CardMaintenanceDetailDataM cardMaintenanceDetail = new CardMaintenanceDetailDataM();
					String APPLY_TYPE = rs.getString("APPLY_TYPE");
					String APPLICATION_TYPE = rs.getString("APPLICATION_TYPE");
					String CARD_NO_MARK = rs.getString("CARD_NO_MARK");
					String CARD_NO = rs.getString("CARD_NO");
					String CARD_TYPE = rs.getString("CARD_TYPE");
					String CARD_LEVEL = rs.getString("CARD_LEVEL");
					String CARD_DESC = rs.getString("CARD_DESC");
					String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
					String CARD_STATUS = rs.getString("CARD_STATUS");
					BigDecimal LOAN_AMT = rs.getBigDecimal("LOAN_AMT");
					String CARD_ID = rs.getString("CARD_ID");
					String CARD_ERROR_DESCRIPTION_1 = rs.getString("MAIN_CARD_RESP_DESC1");
					String CARD_SUP_ERROR_DESCRIPTION_1 = rs.getString("SUP_CARD_RESP_DESC1");
					String CARD_ERROR_DESCRIPTION_2 = rs.getString("MAIN_CARD_RESP_DESC2");
					String CARD_SUP_ERROR_DESCRIPTION_2 = rs.getString("SUP_CARD_RESP_DESC2");
					String CARD_ERROR_DESCRIPTION_3 = rs.getString("MAIN_CARD_RESP_DESC3");
					String CARD_SUP_ERROR_DESCRIPTION_3 = rs.getString("SUP_CARD_RESP_DESC3");
					String MAIN_CARD_NO1 = rs.getString("MAIN_CARD_NO1");
					String MAIN_CARD_NO2 = rs.getString("MAIN_CARD_NO2");
					String MAIN_CARD_NO3 = rs.getString("MAIN_CARD_NO3");
					String SUP_CARD_NO1 = rs.getString("SUP_CARD_NO1");
					String SUP_CARD_NO2 = rs.getString("SUP_CARD_NO2");
					String SUP_CARD_NO3 = rs.getString("SUP_CARD_NO3");
					
					cardMaintenanceDetail.setApplyType(APPLY_TYPE);
					cardMaintenanceDetail.setApplicationType(APPLICATION_TYPE);
					cardMaintenanceDetail.setCardStatus(CARD_STATUS);
					cardMaintenanceDetail.setCardDesc(CARD_DESC);
					cardMaintenanceDetail.setCardNoMark(CARD_NO_MARK);
					cardMaintenanceDetail.setCardNo(CARD_NO);
					cardMaintenanceDetail.setLoanAmt(LOAN_AMT);
					cardMaintenanceDetail.setCardType(CARD_TYPE);
					cardMaintenanceDetail.setCardLevel(CARD_LEVEL);
					cardMaintenanceDetail.setApplicationRecordId(APPLICATION_RECORD_ID);
					cardMaintenanceDetail.setCardId(CARD_ID);
					cardMaintenanceDetail.setErrorCardDesc1(CARD_ERROR_DESCRIPTION_1);
					cardMaintenanceDetail.setErrorSupCardDesc1(CARD_SUP_ERROR_DESCRIPTION_1);
					cardMaintenanceDetail.setErrorCardDesc2(CARD_ERROR_DESCRIPTION_2);
					cardMaintenanceDetail.setErrorSupCardDesc2(CARD_SUP_ERROR_DESCRIPTION_2);
					cardMaintenanceDetail.setErrorCardDesc3(CARD_ERROR_DESCRIPTION_3);
					cardMaintenanceDetail.setErrorSupCardDesc3(CARD_SUP_ERROR_DESCRIPTION_3);
					cardMaintenanceDetail.setMainCardNo1(MAIN_CARD_NO1);
					cardMaintenanceDetail.setMainCardNo2(MAIN_CARD_NO2);
					cardMaintenanceDetail.setMainCardNo3(MAIN_CARD_NO3);
					cardMaintenanceDetail.setSupCardNo1(SUP_CARD_NO1);
					cardMaintenanceDetail.setSupCardNo2(SUP_CARD_NO2);
					cardMaintenanceDetail.setSupCardNo3(SUP_CARD_NO3);
					
				cardMaintenanceDetails.add(cardMaintenanceDetail);
			}
			return cardMaintenance;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public double updateCard(CardDataM card) throws ApplicationException{
		return updateCard(card, "");
	}
	@Override
	public double updateCard(CardDataM card, String regType) throws ApplicationException{
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE " + PREFIX + "ORIG_CARD SET CARD_NO=?, CARD_NO_MARK=?, CARD_NO_ENCRYPTED2=?, HASHING_CARD_NO=? ");
			sql.append(" WHERE CARD_ID=? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, card.getCardNo());
			ps.setString(cnt++, card.getCardNoMark());
			ps.setString(cnt++, card.getCardNoEncrypted());
			ps.setString(cnt++, card.getHashingCardNo());
			ps.setString(cnt++, card.getCardId());
			ps.executeUpdate();
			return returnRows;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public String getCardId(String applicationRecordId) throws ApplicationException
	{
		return getCardId(applicationRecordId, "");
	}
	@Override
	public String getCardId(String applicationRecordId, String regType) throws ApplicationException{
		String cardId = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		int cnt = 1;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT C.CARD_ID ");
			sql.append(" FROM " + PREFIX + "ORIG_APPLICATION A ");
			sql.append(" JOIN " + PREFIX + "ORIG_LOAN L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" JOIN " + PREFIX + "ORIG_CARD C ON C.LOAN_ID=L.LOAN_ID ");
			sql.append(" WHERE A.APPLICATION_RECORD_ID=? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, applicationRecordId);
			rs = ps.executeQuery();
			if (rs.next()) {
				cardId = rs.getString("CARD_ID");
			}
			return cardId;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return cardId;
	}
	@Override
	public double resendCardlinkCard(String applicationRecordId) throws ApplicationException {
		return resendCardlinkCard(applicationRecordId, "");
	}
	@Override
	public double resendCardlinkCard(String applicationRecordId, String regType) throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE " + PREFIX + "ORIG_APPLICATION SET CARDLINK_FLAG = 'W' WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationRecordId);
			ps.executeUpdate();
			return returnRows;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public double resendCardlinkCard(String applicationRecordId,Integer seq) throws ApplicationException {
		return resendCardlinkCard(applicationRecordId, seq, "");
	}
	@Override
	public double resendCardlinkCard(String applicationRecordId,Integer seq, String regType) throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE " + PREFIX + "ORIG_APPLICATION SET CARDLINK_FLAG = 'W' , CARDLINK_SEQ = ? WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, seq);
			ps.setString(2, applicationRecordId);
			ps.executeUpdate();
			return returnRows;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public double cancelCardlinkCard(String applicationRecordId) throws ApplicationException {
		return cancelCardlinkCard(applicationRecordId, "");
	}
	@Override
	public double cancelCardlinkCard(String applicationRecordId, String regType) throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try{
			conn = getConnection();
			String sql = "UPDATE " + PREFIX + "ORIG_APPLICATION SET CARDLINK_FLAG = 'N' WHERE APPLICATION_RECORD_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, applicationRecordId);
			ps.executeUpdate();
			return returnRows;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public double generateNewCustNo(String personalId) throws ApplicationException {
		double returnRows = 0;
		return returnRows;
	}
	@Override
	public double resendCardlinkCust(String personalId,String cardlinkCustNo) throws ApplicationException {
		return resendCardlinkCust(personalId, cardlinkCustNo, "");
	}
	@Override
	public double resendCardlinkCust(String personalId,String cardlinkCustNo, String regType) throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try {
			conn = getConnection();
			String sql = "UPDATE " + PREFIX + "ORIG_CARDLINK_CUSTOMER SET CARDLINK_STATUS = 'W' WHERE PERSONAL_ID = ? AND CARDLINK_CUST_NO = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, personalId);
			ps.setString(2, cardlinkCustNo);
			ps.executeUpdate();
			return returnRows;
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
	public double cancelCardlinkCust(String personalId) throws ApplicationException {
		return cancelCardlinkCust(personalId, "");
	}
	@Override
	public double cancelCardlinkCust(String personalId, String regType) throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try {
			conn = getConnection();
			String sql = "UPDATE " + PREFIX + "ORIG_CARDLINK_CUSTOMER SET CARDLINK_STATUS = 'N' WHERE PERSONAL_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, personalId);
			ps.executeUpdate();
			return returnRows;
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
	public double updateCustIdOrigCard(String cardId,String cardlinkCustId) throws ApplicationException {
		return updateCustIdOrigCard(cardId, cardlinkCustId, "");
	}
	@Override
	public double updateCustIdOrigCard(String cardId,String cardlinkCustId, String regType) throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try {
			conn = getConnection();
			String sql = "UPDATE " + PREFIX + "ORIG_CARD SET CARDLINK_CUST_ID = ? WHERE CARD_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cardlinkCustId);
			ps.setString(2, cardId);
			ps.executeUpdate();
			return returnRows;
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
	public double updateNewCardlinkCustFlag(String cardlinkCustNo,String personalId)throws ApplicationException {
		return updateNewCardlinkCustFlag(cardlinkCustNo, personalId, "");
	}
	@Override
	public double updateNewCardlinkCustFlag(String cardlinkCustNo,String personalId, String regType)throws ApplicationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String PREFIX = (regType.equals("MLP")) ? "MLP_" : "";
		try{
			conn = getConnection();
			String sql = "UPDATE " + PREFIX + "ORIG_CARDLINK_CUSTOMER SET NEW_CARDLINK_CUST_FLAG = 'N' WHERE CARDLINK_CUST_NO = ? AND PERSONAL_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cardlinkCustNo);
			ps.setString(2, personalId);
			ps.executeUpdate();
			return returnRows;
		}catch (Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void cancelAppGroupMLP(String applicationGroupId, String userName) throws ApplicationException {
		String APPLICATION_STATIC_CANCELLED = SystemConstant.getConstant("APPLICATION_STATIC_CANCELLED");
		String JOBSTATE_CANCEL = SystemConstant.getConstant("JOBSTATE_CANCEL");
		String BPM_DECISION_CANCEL = SystemConstant.getConstant("BPM_DECISION_CANCEL");
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE MLP_ORIG_APPLICATION_GROUP SET APPLICATION_STATUS = ?, JOB_STATE = ? ");
			sql.append(" ,LAST_DECISION = ?, LAST_DECISION_DATE = SYSDATE ");
			sql.append(" ,UPDATE_DATE = SYSDATE, UPDATE_BY = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, APPLICATION_STATIC_CANCELLED);
			ps.setString(cnt++, JOBSTATE_CANCEL);
			ps.setString(cnt++, BPM_DECISION_CANCEL);
			ps.setString(cnt++, userName);
			ps.setString(cnt++, applicationGroupId);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void cancelAppMLP(String applicationRecordId, String userName) throws ApplicationException {
		String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE MLP_ORIG_APPLICATION SET FINAL_APP_DECISION = ?, FINAL_APP_DECISION_DATE = SYSDATE ");
			sql.append(" ,UPDATE_DATE = SYSDATE, UPDATE_BY = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, DECISION_FINAL_DECISION_CANCEL);
			ps.setString(cnt++, userName);
			ps.setString(cnt++, applicationRecordId);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void createCancelReasonMLP(String applicationGroupId, String applicationRecordId, 
			String reason,String userName, String userRole) throws ApplicationException {
		String CARDLINK_REASON_CODE = SystemConstant.getConstant("CARDLINK_REASON_CODE");
		String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO MLP_ORIG_REASON ");
			sql.append(" (APPLICATION_RECORD_ID, REASON_TYPE, REASON_CODE, ROLE, REMARK, ");
			sql.append(" REASON_OTH_DESC, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, APPLICATION_GROUP_ID ) ");
			sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationRecordId);
			ps.setString(cnt++, REASON_TYPE_CANCEL);
			ps.setString(cnt++, CARDLINK_REASON_CODE);
			ps.setString(cnt++, userRole);
			ps.setString(cnt++, reason);
			ps.setString(cnt++, "");
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userName);
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userName);
			ps.setString(cnt++, applicationGroupId);
			
			ps.executeUpdate();
			
			sql = new StringBuffer("");
			sql.append("INSERT INTO MLP_ORIG_REASON_LOG ");
			sql.append(" (APPLICATION_RECORD_ID, REASON_LOG_ID, REASON_TYPE, ");
			sql.append(" REASON_CODE, ROLE, REMARK, REASON_OTH_DESC, CREATE_DATE, CREATE_BY, APPLICATION_GROUP_ID ) ");
			sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?)");
			dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			cnt = 1;
			ps.setString(cnt++, applicationRecordId);
			ps.setString(cnt++, GenerateUnique.generate(OrigConstant.SeqNames.ORIG_REASON_LOG_PK));
			ps.setString(cnt++, REASON_TYPE_CANCEL);
			ps.setString(cnt++, CARDLINK_REASON_CODE);
			ps.setString(cnt++, userRole);
			ps.setString(cnt++, reason);		
			ps.setString(cnt++, "");
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userName);
			ps.setString(cnt++, applicationGroupId);
			
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
}
