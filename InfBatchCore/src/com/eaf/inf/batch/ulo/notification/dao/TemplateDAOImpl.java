package com.eaf.inf.batch.ulo.notification.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.display.Formatter.Format;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.inf.batch.ulo.notification.condition.NotificationCondition;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailBranchSummaryDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailEODRejectNCBDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;
import com.eaf.inf.batch.ulo.notification.model.Reason;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigFollowDocHistoryDAO;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.FollowDocHistoryDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.util.ServiceUtil;

public class TemplateDAOImpl extends InfBatchObjectDAO  implements TemplateDAO{
	private static transient Logger logger = Logger.getLogger(TemplateDAOImpl.class);
	String CASH_1_HOUR = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CASH_1_HOUR");
	String REPLACE_PRODUCT_FIELD_ID=InfBatchProperty.getInfBatchConfig("NOTIFICATION_REPLACE_PRODUCT_FIELD_ID");
	String FINAL_APP_DECISION_APPROVE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	String FINAL_APP_DECISION_REJECT=InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
	String APPLICATION_STATUS_REJECT=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	String APPLICATION_STATUS_APPROVE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_APPROVE");
	String APPICATION_TYPE_INCREASE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPICATION_TYPE_INCREASE");
	String APPICATION_TYPE_NEW=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_TYPE_NEW");
	String PARAM_CODE_TEL_NO_PO =InfBatchProperty.getInfBatchConfig("NOTIFICATION_PARAM_CODE_TEL_NO_PO");
	String PARAM_CODE_EMAIL_PO =InfBatchProperty.getInfBatchConfig("NOTIFICATION_PARAM_CODE_EMAIL_PO");
	String PARAM_CODE_EMAIL_POLICY_PO=InfBatchProperty.getInfBatchConfig("NOTIFICATION_PARAM_CODE_EMAIL_POLICY_PO");
	String NOTIFICATION_CASH_1_HOUR=InfBatchProperty.getInfBatchConfig("NOTIFICATION_CASH_1_HOUR");
	String NOTIFICATION_SANCTION_LIST_REJECT_CODE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SANCTION_LIST_REJECT_CODE");
	String REASON_TYPE_REJECT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_REASON_TYPE_REJECT");
	String DOC_LIST_FIELD_ID= InfBatchProperty.getInfBatchConfig("NOTIFY_WARE_HOUSE_DOC_LIST_FIELD_ID");
	String JOB_STATE_APPROVE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_JOB_STATE_APPROVE");
	String JOB_STATE_REJECT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_JOB_STATE_REJECT");
	String DM_DOC_STATUS_NOT_IN_WAREHOUSE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_DM_DOC_STATUS_NOT_IN_WAREHOUSE");
	
	String CARD_NUMBER_FORMAT = InfBatchProperty.getInfBatchConfig("CARD_NUMBER_FORMAT");
	String RECOMMEND_DEC_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_RECOMMEND_DECISION_APPROVE");
	String RECOMMEND_DEC_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_RECOMMEND_DECISION_REJECT");
	String THAI_NATIONALITY = InfBatchProperty.getInfBatchConfig("THAI_NATIONALITY");
	String PERSONAL_RELATION_LEVEL = InfBatchProperty.getInfBatchConfig("PERSONAL_RELATION_LEVEL");
	String PERSONAL_TYPE_ALL = InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_ALL");
	String NOTIFICATION_APPICATION_TYPE_INCREASE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPICATION_TYPE_INCREASE");
	String NOTIFICATION_KEC_PRODUCT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_KEC_PRODUCT");
	String NOTIFICATION_CC_PRODUCT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CC_PRODUCT");
	String NOTIFICATION_EMAIL_SUMMARY_NCB_REASON_CODES = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_NCB_REASON_CODES");
	String NOTIFICATION_EMAIL_SUMMARY_DOC_NOT_COMPLETE_REASON_CODES = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_DOC_NOT_COMPLETE_REASON_CODES");
	String NOTIFICATION_SALE_TYPE_NORMAL=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_NORMAL");
	String NOTIFICATION_SALE_TYPE_REFERENCE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_REFERENCE");
	String FIELD_ID_APPLICATION_CARD_TYPE=InfBatchProperty.getInfBatchConfig("FIELD_ID_APPLICATION_CARD_TYPE");
	
	
	String NOTIFICATION_WISDOM_INFINITE_CARD_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_WISDOM_INFINITE_CARD_CODE");
	String NOTIFICATION_WISDOM_CARD_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_WISDOM_CARD_CODE");
	String NOTIFICATION_THE_PREMIER_CARD_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_THE_PREMIER_CARD_CODE");
	
	String NOTIFICATION_CONTACT_POINT_INFINITE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CONTACT_POINT_INFINITE");
	String NOTIFICATION_CONTACT_POINT_WISDOM = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CONTACT_POINT_WISDOM");
	String NOTIFICATION_CONTACT_POINT_PREMIER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CONTACT_POINT_PREMIER");
	String NOTIFICATION_CONTACT_POINT_GENERIC = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CONTACT_POINT_GENERIC");
	
	String NOTIFICATION_APPLICATION_STATUS_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_APPROVE");
	String NOTIFICATION_APPLICATION_STATUS_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	
	String REJECT_LETTER_MAIN_CARD_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_MAIN_CARD_TH");	
	String REJECT_LETTER_SUP_CARD_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SUP_CARD_TH");	
	String REJECT_LETTER_MAIN_CARD_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_MAIN_CARD_EN");	
	String REJECT_LETTER_SUP_CARD_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SUP_CARD_EN");	
	
	String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	
	String NOTIFICATION_CASH_DAY_1 = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CASH_DAY_1");
	String LANGUAGE_TH = InfBatchProperty.getInfBatchConfig("LANGUAGE_TH");
	String LANGUAGE_OTHER = InfBatchProperty.getInfBatchConfig("LANGUAGE_OTHER");
	String TITLE_NAME_TH= InfBatchProperty.getInfBatchConfig("TITLE_NAME_TH");
	String NOTIFICATION_EOD_XRULES_VER_LEVEL_APPLICATION = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_XRULES_VER_LEVEL_APPLICATION"); 
	
	String NOTIFY_DOC_LIST_FIELD_ID= InfBatchProperty.getInfBatchConfig("NOTIFY_DOC_LIST_FIELD_ID");
	
	@Override
	public  HashMap<String, Object> getSMSnonCashDay1ApproveValues(ArrayList<RecipientInfoDataM> receipientInfos ) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> nonCashDay1Approves =new HashMap<String, Object>();
		String applicationGroupId="";
		int maxlifeCycle =1;
		ArrayList<String> applicationRecordIds= new ArrayList<String>();
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo: receipientInfos){
				applicationGroupId=recipientInfo.getApplicationGroupId();
				maxlifeCycle =recipientInfo.getMaxLifeCycle();
				if(!applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT(A.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME");
			sql.append(" FROM  ORIG_APPLICATION A ");
			sql.append(" WHERE  A.LIFE_CYCLE = ?");
			sql.append("  AND A.APPLICATION_GROUP_ID =? ");
			if(applicationRecordIds.size()>0){
				String comma="";
				sql.append("  AND A.APPLICATION_RECORD_ID (");
				for(String appRecordId :applicationRecordIds){
					sql.append(comma+"'"+appRecordId+"'");
					comma=",";
				}
				sql.append(")");
			}
			 
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setInt(1,maxlifeCycle); 		
			ps.setString(2,applicationGroupId); 						
			rs = ps.executeQuery();	 
			if(rs.next()) {
				nonCashDay1Approves.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, rs.getString("PRODUCT_NAME"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return nonCashDay1Approves;
	}
	
	@Override
	public  HashMap<String, Object> getSMSInCreaseApproveValues(ArrayList<RecipientInfoDataM> receipientInfos) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> inCreaseApprove =new HashMap<String, Object>();
		String applicationGroupId ="";
		int maxLifeCycle =1;
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo :receipientInfos){
				if(!applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
				maxLifeCycle=recipientInfo.getMaxLifeCycle();
				applicationGroupId=recipientInfo.getApplicationGroupId();
			}
		}
		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG ");
			sql.append(" INNER JOIN   ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID");
			sql.append(" WHERE  A.LIFE_CYCLE = ?");
			sql.append("  AND A.APPLICATION_GROUP_ID =?  AND AG.APPLICATION_TYPE=? ");
			if(applicationRecordIds.size()>0){
				sql.append(" AND A.APPLICATION_RECORD_ID IN (");
				String comma="";
				for(String appRecordId :applicationRecordIds){
					sql.append(comma+"'"+appRecordId+"'");
					comma=",";
				}
				sql.append(")");
			}
			
			 
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setInt(1,maxLifeCycle); 		
			ps.setString(2,applicationGroupId); 			
			ps.setString(3,APPICATION_TYPE_INCREASE); 
			rs = ps.executeQuery();	 
			if(rs.next()) {
				inCreaseApprove.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, rs.getString("PRODUCT_NAME"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return inCreaseApprove;
	}
	
	@Override
	public  HashMap<String, Object> getSMSRejectValues(ArrayList<RecipientInfoDataM> receipientInfos ) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> rejects =new HashMap<String, Object>();
		String applicationGroupId="";
		int maxLifeCycle=1;
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo : receipientInfos){
				applicationGroupId = recipientInfo.getApplicationGroupId();
				maxLifeCycle = recipientInfo.getMaxLifeCycle();
				if(!applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG ");
			sql.append(" INNER JOIN   ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID");
			sql.append(" WHERE  A.LIFE_CYCLE = ?");
			sql.append("  AND A.APPLICATION_GROUP_ID =? ");
			if(applicationRecordIds.size()>0){
				String comma="";
				sql.append(" AND A.APPLICATION_RECORD_ID IN (");
				for(String appRecordId :applicationRecordIds){
					sql.append(comma+"'"+appRecordId+"'");
					comma=",";
				}
				sql.append(")");
			}
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setInt(1,maxLifeCycle); 		
			ps.setString(2,applicationGroupId); 			
			rs = ps.executeQuery();	 
			if(rs.next()) {
				rejects.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, rs.getString("PRODUCT_NAME"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return rejects;
	}
	
	@Override
	public  HashMap<String, Object>  getEmailBranchApprovedValues(String applicationGroupID, int maxLifeCycle) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hBranchApproves =new HashMap<String,Object>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT AG.APPLICATION_GROUP_NO, BRANCH_NAME,");
			sql.append(" PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME,");
			sql.append(" PKA_NOTIFICATION.GET_PERSONAL_NAME(AG.APPLICATION_GROUP_ID,AG.APPLICATION_TYPE) AS CUSTOMER_NAME,");
			sql.append(" (SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE  PARAM_CODE = '"+PARAM_CODE_TEL_NO_PO+"') AS PO_TELEPHONE,");
			sql.append(" (SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE  PARAM_CODE = '"+PARAM_CODE_EMAIL_PO+"') AS PO_EMAIL");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG");
			sql.append(" WHERE  AG.APPLICATION_GROUP_ID =?");
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setString(1,applicationGroupID); 					
			rs = ps.executeQuery();	 
			if(rs.next()) {
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String BRANCH_NAME = rs.getString("BRANCH_NAME");
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				String PO_TELEPHONE = rs.getString("PO_TELEPHONE");
				String PO_EMAIL = rs.getString("PO_EMAIL");
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO,APPLICATION_GROUP_NO);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.BRANCH_NAME, BRANCH_NAME);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, PRODUCT_NAME);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, CUSTOMER_NAME);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.REFERENCE_NO,APPLICATION_GROUP_NO);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.PO_TELEPHONE_NO,PO_TELEPHONE);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.PO_EMAIL,PO_EMAIL);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hBranchApproves;
	}
	@Override
	public ArrayList<EmailEODRejectNCBDataM> getEodEmailRejectNcb(String applicationGroupId)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		ArrayList<EmailEODRejectNCBDataM> eodEmailRejectNcbs = new ArrayList<EmailEODRejectNCBDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("	SELECT DISTINCT ");
			sql.append("	   AG.APPLICATION_TYPE, ");
			sql.append("	   AG.APPLICATION_GROUP_ID, ");
			sql.append("	   AG.APPLICATION_GROUP_NO, ");
			sql.append("	   AG.APPLICATION_TEMPLATE,	");
			sql.append("	   A.LIFE_CYCLE, A.BUSINESS_CLASS_ID,A.APPLICATION_RECORD_ID ,A.APPLICATION_NO,A.PROLICY_PROGRAM_ID, A.FINAL_APP_DECISION, A.BUSINESS_CLASS_ID ");
			sql.append("	   , C.CARD_TYPE, C.CARD_LEVEL ");
			sql.append("	   , CTP.CARD_CODE 	");
			sql.append("	   , PS.EMAIL_PRIMARY, PS.PERSONAL_TYPE, PS.NATIONALITY, PS.PERSONAL_ID, PS.IDNO ");
			sql.append("	   , PS.TH_FIRST_NAME||' '||PS.TH_LAST_NAME AS PERSONAL_NAME_TH	");
			sql.append("	   , PS.EN_FIRST_NAME||' '||PS.EN_LAST_NAME AS PERSONAL_NAME_EN	");
			sql.append("	   , DECODE(NATIONALITY,'"+LANGUAGE_TH+"','"+TITLE_NAME_TH+"',EN_TITLE_DESC) AS TITLE_NAME	");
			sql.append("	   , PKA_REJECT_LETTER.GET_PERSONAL_MIN_RANK_REASON(AG.APPLICATION_GROUP_ID,PS.PERSONAL_ID,A.APPLICATION_RECORD_ID) AS REASON_CODE 	");
			sql.append("	   , DECODE(NATIONALITY,'"+LANGUAGE_TH+"',L.SYSTEM_ID3,L.SYSTEM_ID4)AS PRODUCT_NAME , L.SYSTEM_ID3 AS PRODUCT_NAME_TH, L.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
			sql.append("	   , PKA_REJECT_LETTER.GET_PRODUCT_TH_EN(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID) AS PRODUCT_NAME_TH_EN ");
			sql.append("	FROM ORIG_APPLICATION_GROUP AG 	");
			sql.append("	JOIN ORIG_APPLICATION A ON  A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID AND A.LIFE_CYCLE = AG.LIFE_CYCLE AND A.FINAL_APP_DECISION = '"+FINAL_APP_DECISION_REJECT+"' ");
			sql.append("	JOIN ORIG_PERSONAL_RELATION R  ON  A.APPLICATION_RECORD_ID = R.REF_ID AND R.RELATION_LEVEL='A' ");
			sql.append("	JOIN ORIG_PERSONAL_INFO PS ON  R.PERSONAL_ID = PS.PERSONAL_ID 	");
			sql.append("	JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID 	");
			sql.append("	LEFT JOIN ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
			sql.append("	LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
			sql.append("	LEFT JOIN LIST_BOX_MASTER L ON  L.SYSTEM_ID2 = A.BUSINESS_CLASS_ID AND L.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append("	WHERE AG.APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				String APPLICATION_NO = rs.getString("APPLICATION_NO");
				String TITLE_NAME = rs.getString("TITLE_NAME");
				String PERSONAL_NAME_TH = rs.getString("PERSONAL_NAME_TH");
				String PERSONAL_NAME_EN = rs.getString("PERSONAL_NAME_EN");
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String PRODUCT_NAME_TH = rs.getString("PRODUCT_NAME_TH");
				String PRODUCT_NAME_EN = rs.getString("PRODUCT_NAME_EN");
				String PRODUCT_NAME_TH_EN = rs.getString("PRODUCT_NAME_TH_EN");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String NATIONALITY = rs.getString("NATIONALITY");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String IDNO = rs.getString("IDNO");
				String CARD_TYPE = rs.getString("CARD_TYPE");
				String CARD_LEVEL = rs.getString("CARD_LEVEL");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				EmailEODRejectNCBDataM eodEmailRejectNcb = new EmailEODRejectNCBDataM();
					eodEmailRejectNcb.setApplicationGroupId(applicationGroupId);
					eodEmailRejectNcb.setApplicationNo(APPLICATION_NO);
					eodEmailRejectNcb.setTitleName(TITLE_NAME);
					eodEmailRejectNcb.setPersonalNameTh(PERSONAL_NAME_TH);
					eodEmailRejectNcb.setPersonalNameEn(PERSONAL_NAME_EN);
					eodEmailRejectNcb.setApplicationNo(APPLICATION_NO);
					eodEmailRejectNcb.setProductName(PRODUCT_NAME);
					eodEmailRejectNcb.setProductNameEn(PRODUCT_NAME_EN);
					eodEmailRejectNcb.setProductNameTh(PRODUCT_NAME_TH);
					eodEmailRejectNcb.setProductNameThEn(PRODUCT_NAME_TH_EN);
					eodEmailRejectNcb.setReferenceNo(APPLICATION_GROUP_NO);
					eodEmailRejectNcb.setPolicyProgramId(PROLICY_PROGRAM_ID);
					eodEmailRejectNcb.setFinalAppDecision(FINAL_APP_DECISION);
					eodEmailRejectNcb.setBusinessClassId(BUSINESS_CLASS_ID);
					eodEmailRejectNcb.setPersonalType(PERSONAL_TYPE);
					eodEmailRejectNcb.setNationality(NATIONALITY);
					eodEmailRejectNcb.setPersonalId(PERSONAL_ID);
					eodEmailRejectNcb.setIdNo(IDNO);
					eodEmailRejectNcb.setCardType(CARD_TYPE);
					eodEmailRejectNcb.setCardLevel(CARD_LEVEL);
					eodEmailRejectNcb.setApplicationTemplate(APPLICATION_TEMPLATE);
				eodEmailRejectNcbs.add(eodEmailRejectNcb);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return eodEmailRejectNcbs;
	}
	@Override
	public  HashMap<String, Object>  getEmailAccountApprovedValues(String applicationGroupID, int maxLifeCycle) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hBranchApproves =new HashMap<String,Object>();
		 Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_NOTIFICATION.GET_PERSONAL_NAME_IDNO(AG.APPLICATION_GROUP_ID,AG.APPLICATION_TYPE) AS PERSONAL_NAME_IDNO,");
			sql.append(" T.ACCOUNT_NAME, T.TRANSFER_ACCOUNT, T.PERCENT_TRANSFER, L.LOAN_AMT, C.CARD_NO");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG");
			sql.append(" INNER JOIN   ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN   ORIG_LOAN  L ON A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID ");
			sql.append(" LEFT JOIN   ORIG_CARD  C ON L.LOAN_ID = C.LOAN_ID ");
			sql.append(" LEFT JOIN   ORIG_CASH_TRANSFER  T ON L.LOAN_ID = T.LOAN_ID  AND  T.CASH_TRANSFER_TYPE =?");
			sql.append(" WHERE  AG.APPLICATION_GROUP_ID =?");
			sql.append(" AND  A.LIFE_CYCLE = ?");
			sql.append(" AND  SUBSTR(A.BUSINESS_CLASS_ID,1,INSTR(A.BUSINESS_CLASS_ID,'_')-1) = '"+NOTIFICATION_KEC_PRODUCT+"'");
			
			logger.debug(">>sql>>"+sql);
			logger.debug(">>NOTIFICATION_CASH_1_HOUR>>"+NOTIFICATION_CASH_1_HOUR);
			ps = conn.prepareStatement(sql.toString());		
			ps.setString(1,NOTIFICATION_CASH_1_HOUR); 					
			ps.setString(2,applicationGroupID); 					
			ps.setInt(3,maxLifeCycle); 										
			rs = ps.executeQuery();	 
			if(rs.next()) {
				String[] PERSONAL_NAME_IDNO = rs.getString("PERSONAL_NAME_IDNO").split(",");
				String ACCOUNT_NAME = rs.getString("ACCOUNT_NAME");
				String TRANSFER_ACCOUNT = rs.getString("TRANSFER_ACCOUNT");
				BigDecimal PERCENT_TRANSFER = rs.getBigDecimal("PERCENT_TRANSFER");

				BigDecimal LOAN_AMT = rs.getBigDecimal("LOAN_AMT");
				String KEC_CARD_ENCRP = rs.getString("CARD_NO");
				String KEC_CARD_NO="";
				logger.debug(">>>PERCENT_TRANSFER>>"+PERCENT_TRANSFER);
				logger.debug(">>>LOAN_AMT>>"+LOAN_AMT);
				BigDecimal TRANSFER_AMOUNT = BigDecimal.ZERO;
				if(null!=PERCENT_TRANSFER && null!=LOAN_AMT){
					TRANSFER_AMOUNT = (LOAN_AMT.multiply(PERCENT_TRANSFER)).divide(new BigDecimal(100));
				}
		  		if(!InfBatchUtil.empty(KEC_CARD_ENCRP)){
		 			try{
						KEC_CARD_NO = encryptor.decrypt(KEC_CARD_ENCRP);
					}catch(Exception e){
						logger.debug("ERROR",e);
					}
		  		}		  		 
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME,PERSONAL_NAME_IDNO[0]);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.IDNO,PERSONAL_NAME_IDNO.length>0?PERSONAL_NAME_IDNO[1]:"");
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.CARD_NO,KEC_CARD_NO );
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.ACCOUNT_NO,TRANSFER_ACCOUNT );
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.ACCOUNT_NAME,ACCOUNT_NAME);
				hBranchApproves.put(TemplateBuilderConstant.TemplateVariableName.AMOUNT,TRANSFER_AMOUNT.toString());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hBranchApproves;
	}
	
	@Override
	public  HashMap<String, Object>  getBranchRejectedValues(String applicationGroupID) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hBranchRejected =new HashMap<String,Object>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT AG.APPLICATION_GROUP_NO, BRANCH_NAME,");
			sql.append(" PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME,");
			sql.append(" PKA_NOTIFICATION.GET_PERSONAL_NAME(AG.APPLICATION_GROUP_ID,AG.APPLICATION_TYPE) AS CUSTOMER_NAME,");
			sql.append(" (SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE  PARAM_CODE = '"+PARAM_CODE_TEL_NO_PO+"') AS PO_TELEPHONE,");
			sql.append(" (SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE  PARAM_CODE = '"+PARAM_CODE_EMAIL_PO+"') AS PO_EMAIL");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG");
			sql.append(" WHERE  AG.APPLICATION_GROUP_ID =?");			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setString(1,applicationGroupID); 					
			rs = ps.executeQuery();	 
			if(rs.next()) {
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String BRANCH_NAME = rs.getString("BRANCH_NAME");
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				String PO_TELEPHONE = rs.getString("PO_TELEPHONE");
				String PO_EMAIL = rs.getString("PO_EMAIL");
				hBranchRejected.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO,APPLICATION_GROUP_NO);
				hBranchRejected.put(TemplateBuilderConstant.TemplateVariableName.BRANCH_NAME, BRANCH_NAME);
				hBranchRejected.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, PRODUCT_NAME);
				hBranchRejected.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, CUSTOMER_NAME);
				hBranchRejected.put(TemplateBuilderConstant.TemplateVariableName.REFERENCE_NO,APPLICATION_GROUP_NO);
				hBranchRejected.put(TemplateBuilderConstant.TemplateVariableName.PO_TELEPHONE_NO,PO_TELEPHONE);
				hBranchRejected.put(TemplateBuilderConstant.TemplateVariableName.PO_EMAIL,PO_EMAIL);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hBranchRejected;
	}
	@Override
	public  HashMap<String, Object>  getCustomerRejectedValues(String applicationGroupID) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hCustomerRejected =new HashMap<String,Object>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT AG.APPLICATION_GROUP_NO, BRANCH_NAME,");
			sql.append(" PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME,");
			sql.append(" PKA_NOTIFICATION.GET_PERSONAL_NAME(AG.APPLICATION_GROUP_ID,AG.APPLICATION_TYPE) AS CUSTOMER_NAME,");
			sql.append(" (SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE  PARAM_CODE = '"+PARAM_CODE_TEL_NO_PO+"') AS PO_TELEPHONE,");
			sql.append(" (SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE  PARAM_CODE = '"+PARAM_CODE_EMAIL_PO+"') AS PO_EMAIL");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG");
			sql.append(" WHERE  AG.APPLICATION_GROUP_ID =?");
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setString(1,applicationGroupID); 					
			rs = ps.executeQuery();	 
			if(rs.next()) {
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String BRANCH_NAME = rs.getString("BRANCH_NAME");
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				String PO_TELEPHONE = rs.getString("PO_TELEPHONE");
				String PO_EMAIL = rs.getString("PO_EMAIL");
				hCustomerRejected.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO,APPLICATION_GROUP_NO);
				hCustomerRejected.put(TemplateBuilderConstant.TemplateVariableName.BRANCH_NAME, BRANCH_NAME);
				hCustomerRejected.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, PRODUCT_NAME);
				hCustomerRejected.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, CUSTOMER_NAME);
				hCustomerRejected.put(TemplateBuilderConstant.TemplateVariableName.REFERENCE_NO,APPLICATION_GROUP_NO);
				hCustomerRejected.put(TemplateBuilderConstant.TemplateVariableName.PO_TELEPHONE_NO,PO_TELEPHONE);
				hCustomerRejected.put(TemplateBuilderConstant.TemplateVariableName.PO_EMAIL,PO_EMAIL);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hCustomerRejected;
	}
	
	@Override
	public  HashMap<String, Object>  getSanctionListRejectedvalues(String applicationGroupID, int maxLifeCycle) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hSanctionListRejected =new HashMap<String,Object>();
		String[] rejectCodes = NOTIFICATION_SANCTION_LIST_REJECT_CODE.split(",");
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID) AS PRODUCT_NAME,");
			sql.append(" PKA_NOTIFICATION.GET_PERSONAL_NAME(AG.APPLICATION_GROUP_ID,AG.APPLICATION_TYPE) AS CUSTOMER_NAME,");
			sql.append(" (SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE  PARAM_CODE = '"+PARAM_CODE_EMAIL_POLICY_PO+"') AS EMAIL_POLICY_PO");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG");
			sql.append(" INNER JOIN ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID");
			sql.append(" INNER JOIN ORIG_REASON  R ON A.APPLICATION_RECORD_ID = R.APPLICATION_RECORD_ID ");
			sql.append(" WHERE  AG.APPLICATION_GROUP_ID =? AND R.REASON_TYPE =?");
			sql.append(" AND A.LIFE_CYCLE=?");
			sql.append(" AND R.REASON_CODE IN (");
			String COMMA="";
			for(String reasonCode :rejectCodes){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());	
			int index =1;
			ps.setString(index++,applicationGroupID); 					
			ps.setString(index++,REASON_TYPE_REJECT); 	
			ps.setInt(index++,maxLifeCycle); 	
			for(String reasonCode :rejectCodes){
				ps.setString(index++,reasonCode);
			}
			rs = ps.executeQuery();	 
			if(rs.next()) {
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				String EMAIL_POLICY_PO = rs.getString("EMAIL_POLICY_PO");
				hSanctionListRejected.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, PRODUCT_NAME);
				hSanctionListRejected.put(TemplateBuilderConstant.TemplateVariableName.FIRSTNAME_LASTNAME, CUSTOMER_NAME);
				hSanctionListRejected.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_POLICY_PO,EMAIL_POLICY_PO);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hSanctionListRejected;
	}
	@Override
	public  HashMap<String, Object> findRejectedVariable(String applicationGroupId, int maxLifeCycle) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> templateVariableApplicant =new HashMap<String,Object>();
		HashMap<String, Object> templateVariableSupplementary =new HashMap<String,Object>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
//			sql.append(" SELECT ");
//			sql.append("     PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID)   AS PRODUCT_NAME, ");
//			sql.append("     PKA_NOTIFICATION.GET_PERSONAL_NAME(AG.APPLICATION_GROUP_ID,AG.APPLICATION_TYPE) AS ");
//			sql.append("     CUSTOMER_NAME, ");
//			sql.append("     (SELECT PARAM_VALUE FROM GENERAL_PARAM WHERE PARAM_CODE = '"+PARAM_CODE_EMAIL_POLICY_PO+"') AS EMAIL_POLICY_PO ");
//			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
//			sql.append(" INNER JOIN ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
//			sql.append(" WHERE AG.APPLICATION_GROUP_ID =? ");
//			sql.append(" AND A.LIFE_CYCLE=? ");
//			logger.debug("sql : "+sql);
//			ps = conn.prepareStatement(sql.toString());	
//			int index =1;
//			ps.setString(index++,applicationGroupId); 					
//			ps.setInt(index++,maxLifeCycle); 	
//			rs = ps.executeQuery();	 
//			if(rs.next()) {
//				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
//				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
//				String EMAIL_POLICY_PO = rs.getString("EMAIL_POLICY_PO");
//				templateVariable.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, PRODUCT_NAME);
//				templateVariable.put(TemplateBuilderConstant.TemplateVariableName.FIRSTNAME_LASTNAME, CUSTOMER_NAME);
//				templateVariable.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, CUSTOMER_NAME);
//				templateVariable.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_POLICY_PO,EMAIL_POLICY_PO);
//			}
			sql.append(" SELECT ORIG_APPLICATION.FINAL_APP_DECISION ");
			sql.append("         , ORIG_PERSONAL_INFO.PERSONAL_ID ");
			sql.append("         , ORIG_PERSONAL_INFO.PERSONAL_TYPE ");
			sql.append("         , ORIG_PERSONAL_INFO.TH_FIRST_NAME ");
			sql.append("         , ORIG_PERSONAL_INFO.TH_LAST_NAME ");
			sql.append("         , PKA_NOTIFICATION.GET_PRODUCT(ORIG_APPLICATION.APPLICATION_GROUP_ID,ORIG_APPLICATION.APPLICATION_RECORD_ID) AS PRODUCT_NAME ");
			sql.append("         ,(SELECT PARAM_VALUE  FROM GENERAL_PARAM WHERE PARAM_CODE = '"+PARAM_CODE_EMAIL_POLICY_PO+"') AS EMAIL_POLICY_PO ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" JOIN ORIG_APPLICATION ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_APPLICATION.APPLICATION_GROUP_ID ");
			sql.append("         AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ORIG_APPLICATION.LIFE_CYCLE ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION ON ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_PERSONAL_RELATION.REF_ID AND ORIG_PERSONAL_RELATION.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO ON ORIG_PERSONAL_RELATION.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID ");
			sql.append("         AND ORIG_PERSONAL_INFO.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"') 	");
			sql.append(" WHERE ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ? 	");
			sql.append("         AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ? 	");
			logger.debug("sql : "+sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			logger.debug("maxLifeCycle : "+maxLifeCycle);
			ps = conn.prepareStatement(sql.toString());	
				int index =1;
				ps.setString(index++,applicationGroupId); 					
				ps.setInt(index++,maxLifeCycle); 	
			rs = ps.executeQuery();
			NotifyApplicationSegment notifyApplicationSegment = NotificationUtil.notifyApplication(applicationGroupId);
			while(rs.next()){
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String TH_FIRST_NAME = rs.getString("TH_FIRST_NAME");
				String TH_LAST_NAME = rs.getString("TH_LAST_NAME");
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String EMAIL_POLICY_PO = rs.getString("EMAIL_POLICY_PO");
				String customerName = Formatter.displayText(TH_FIRST_NAME)+' '+Formatter.displayText(TH_LAST_NAME);
				if(PERSONAL_TYPE_APPLICANT.equals(PERSONAL_TYPE)){
					if(NotificationUtil.isRejectSanction(notifyApplicationSegment,FINAL_APP_DECISION,PERSONAL_ID)){
						templateVariableApplicant.put(TemplateBuilderConstant.TemplateVariableName.FIRSTNAME_LASTNAME,customerName);
						templateVariableApplicant.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME,customerName);
						templateVariableApplicant.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,Formatter.displayText(PRODUCT_NAME));
						templateVariableApplicant.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_POLICY_PO,Formatter.displayText(EMAIL_POLICY_PO));
					}
				}
				if(PERSONAL_TYPE_SUPPLEMENTARY.equals(PERSONAL_TYPE)){
					if(NotificationUtil.isRejectSanction(notifyApplicationSegment,FINAL_APP_DECISION,PERSONAL_ID)){
						templateVariableSupplementary.put(TemplateBuilderConstant.TemplateVariableName.FIRSTNAME_LASTNAME,customerName);
						templateVariableSupplementary.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME,customerName);
						templateVariableSupplementary.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,Formatter.displayText(PRODUCT_NAME));
						templateVariableSupplementary.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_POLICY_PO,Formatter.displayText(EMAIL_POLICY_PO));
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		if(!InfBatchUtil.empty(templateVariableApplicant)){
			return templateVariableApplicant;
		}
		return templateVariableSupplementary;
	}
	@Override
	public ArrayList<EmailBranchSummaryDataM> getBranchSummaryData(ArrayList<String> applicationGroupIds) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		ArrayList<EmailBranchSummaryDataM> branchSummarys = new ArrayList<EmailBranchSummaryDataM>();
		if(InfBatchUtil.empty(applicationGroupIds)) return branchSummarys;
		String PO_EMAIL = InfBatchProperty.getGeneralParam(InfBatchProperty.getInfBatchConfig("NOTIFICATION_PARAM_CODE_TEL_NO_PO"));
		String PO_TELEPHONE = InfBatchProperty.getGeneralParam(InfBatchProperty.getInfBatchConfig("NOTIFICATION_PARAM_CODE_EMAIL_PO"));
		logger.debug("PO_EMAIL : "+PO_EMAIL);
		logger.debug("PO_TELEPHONE : "+PO_TELEPHONE);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ");
			sql.append("     ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID, ");
			sql.append("     ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO, ");
			sql.append("     ORIG_APPLICATION_GROUP.JOB_STATE, ");
			sql.append("     ORIG_PERSONAL_INFO.PERSONAL_ID, ");
			sql.append("     ORIG_PERSONAL_INFO.TH_FIRST_NAME ||' '|| ORIG_PERSONAL_INFO.TH_LAST_NAME AS CUSTOMER_NAME, ");
			sql.append("     ORIG_PERSONAL_INFO.MOBILE_NO, ");
			sql.append("     SALE.SALES_ID AS SALE_ID , ");
			sql.append("     RECOMMEND.SALES_ID AS RECOMMEND_ID , ");
			sql.append("     ORIG_APPLICATION.APPLICATION_RECORD_ID, ");
			sql.append("     ORIG_APPLICATION.FINAL_APP_DECISION, ");
			sql.append("     ORIG_CARD.CARD_TYPE , ");
			sql.append("     ORIG_CARD.CARD_LEVEL, ");
			// KPL: Add PKA_NOTIFICATION to use as KPL product name
			//df1165 eApp change from TH to EN
			sql.append(" 	 PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID,ORIG_APPLICATION.APPLICATION_RECORD_ID, 'EN') AS PRODUCT_NAME, ");
			sql.append("     ORIG_CARD.APPLICATION_TYPE APPLICATION_CARD_TYPE ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" JOIN ORIG_PERSONAL_INFO ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_PERSONAL_INFO.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION ON ORIG_PERSONAL_INFO.PERSONAL_ID = ORIG_PERSONAL_RELATION.PERSONAL_ID ");
			sql.append(" JOIN ORIG_APPLICATION ON ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_PERSONAL_RELATION.REF_ID AND ORIG_PERSONAL_RELATION.RELATION_LEVEL = 'A' ");
			sql.append(" AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ORIG_APPLICATION.LIFE_CYCLE ");
			sql.append(" JOIN ORIG_LOAN ON ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_LOAN.APPLICATION_RECORD_ID ");
			// KPL: Change INNER JOIN to LEFT JOIN for ORIG_CARD
			sql.append(" LEFT JOIN ORIG_CARD ON ORIG_LOAN.LOAN_ID = ORIG_CARD.LOAN_ID ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO SALE ON SALE.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"' AND ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = SALE.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO RECOMMEND ON RECOMMEND.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_REFERENCE+"' AND ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = RECOMMEND.APPLICATION_GROUP_ID ");
			sql.append(" WHERE ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID IN (");
			String COMMA="";
			for(String applicationGroupId : applicationGroupIds) {
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			sql.append(" AND ORIG_APPLICATION.FINAL_APP_DECISION  IN('"+APPLICATION_STATUS_REJECT+"','"+APPLICATION_STATUS_APPROVE+"')");
			sql.append(" ORDER BY ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO,ORIG_PERSONAL_INFO.PERSONAL_TYPE ");
			logger.debug("sql : "+sql);
			
			ps = conn.prepareStatement(sql.toString());	
			int index =1;
			for(String applicationGroupId :applicationGroupIds){
				ps.setString(index++,applicationGroupId);
			}
			rs = ps.executeQuery();
			
			while(rs.next()) {
				EmailBranchSummaryDataM emailBranchSummary = new EmailBranchSummaryDataM();
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String SALE_ID = rs.getString("SALE_ID");
				String RECOMMEND_ID = rs.getString("RECOMMEND_ID");
				String CARD_TYPE_ID = rs.getString("CARD_TYPE");
				String APPLICATION_CARD_TYPE = rs.getString("APPLICATION_CARD_TYPE");
				if (null == APPLICATION_CARD_TYPE)
					APPLICATION_CARD_TYPE = "";
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				emailBranchSummary.setFinalDecision(FINAL_APP_DECISION);
				emailBranchSummary.setApplicationNo(APPLICATION_GROUP_NO);
				emailBranchSummary.setCustomerPhoneNo(MOBILE_NO);
				emailBranchSummary.setCustomerFullName(CUSTOMER_NAME);
				emailBranchSummary.setSellerId(SALE_ID);
				emailBranchSummary.setRecommenderId(RECOMMEND_ID);
				logger.debug("CARD_TYPE_ID : "+CARD_TYPE_ID);
				logger.debug("APPLICATION_CARD_TYPE : "+APPLICATION_CARD_TYPE);
				logger.debug("FINAL_APP_DECISION : "+FINAL_APP_DECISION);
				if (null != CARD_TYPE_ID) {
					String CARD_NAME = CardInfoUtil.getCardDetail(CARD_TYPE_ID,"CARD_TYPE_DESC")
						+" - "
						+CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE,APPLICATION_CARD_TYPE);
					emailBranchSummary.setProductName(CARD_NAME);
				}
				else
					emailBranchSummary.setProductName(rs.getString("PRODUCT_NAME"));
				emailBranchSummary.setPoEmail(PO_EMAIL);
				emailBranchSummary.setPoPhoneNo(PO_TELEPHONE);
				if(APPLICATION_STATUS_REJECT.equals(FINAL_APP_DECISION)){
					logger.debug("APPLICATION_RECORD_ID : "+APPLICATION_RECORD_ID);
					NotifyApplicationSegment notifyApplicationSegment  = NotificationUtil.notifyApplication(APPLICATION_GROUP_ID);
					Reason reason = notifyApplicationSegment.findReasonApplication(APPLICATION_RECORD_ID);
					String reasonCode = reason.getReasonCode();
					logger.debug("reasonCode : "+reasonCode);
					if(InfBatchProperty.lookup("NOTIFICATION_EMAIL_SUMMARY_DOC_NOT_COMPLETE_REASON_CODES",reasonCode)){
						emailBranchSummary.setRejectReason(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_DOC_NOT_COMPLETE_REASON_DESC"));
						ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO()
								.loadApplicationGroupDocument(APPLICATION_GROUP_ID, PERSONAL_ID);
						ArrayList<FollowDocHistoryDataM> followDocHistorys = findFollowDocHistorys(applicationGroup);
						if(null!=followDocHistorys){
							List<String> notCompleteDocs = new ArrayList<String>();
							for(FollowDocHistoryDataM followDocHistory : followDocHistorys){
								logger.debug("followDocHistory.DocumentCode : "+followDocHistory);
								notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode()));
							}
							emailBranchSummary.setNotCompleteDocs(notCompleteDocs);
						}
					}else if(InfBatchProperty.lookup("NOTIFICATION_EMAIL_SUMMARY_NCB_REASON_CODES",reasonCode)){
						emailBranchSummary.setRejectReason(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_NCB_REASON_DESC"));
					}else{
						emailBranchSummary.setRejectReason(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_DOC_OTHER_REASON_DESC"));
					}
				}
				branchSummarys.add(emailBranchSummary);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return branchSummarys;
	}
	@Override
	public ArrayList<FollowDocHistoryDataM> loadFollowDocHistorys(ApplicationGroupDataM applicationGroup) throws InfBatchException{
		ArrayList<FollowDocHistoryDataM> followDocHistorys = new ArrayList<FollowDocHistoryDataM>(); 
		try{
			if(!InfBatchUtil.empty(applicationGroup)){
				followDocHistorys = findFollowDocHistorys(applicationGroup);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
		return followDocHistorys;
	}
	private ArrayList<FollowDocHistoryDataM> findFollowDocHistorys(ApplicationGroupDataM applicationGroup) throws Exception{
		ArrayList<FollowDocHistoryDataM> followDocHistorys = new ArrayList<FollowDocHistoryDataM>();
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		logger.debug("applicationGroupId : "+applicationGroupId);
		OrigFollowDocHistoryDAO followDocHistoryDAO = ORIGDAOFactory.getFollowDocHistoryDAO();
		int maxSeq = followDocHistoryDAO.selectMaxFollowupSeq(applicationGroupId);
		int currentSeq = maxSeq+1;
		logger.debug("maxSeq : "+maxSeq);
		logger.debug("currentSeq : "+currentSeq);
		ArrayList<DocumentCheckListDataM> documentCheckLists = applicationGroup.getDocumentCheckLists();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo:personalInfos){
				if(!Util.empty(documentCheckLists)){
					for(DocumentCheckListDataM documentCheckList :documentCheckLists){
						if(!isDocComplete(personalInfo, documentCheckList)){
							String documentCode = documentCheckList.getDocumentCode();
							ArrayList<DocumentCheckListReasonDataM> docCheckListReasons = documentCheckList.getDocumentCheckListReasons();
							if(!Util.empty(docCheckListReasons)){
								for(DocumentCheckListReasonDataM docCheckListReason : docCheckListReasons){
									if(!isContainDocReasonAndDocCode(followDocHistorys,docCheckListReason.getDocReason(),documentCode)){
										FollowDocHistoryDataM followDocHistory = new FollowDocHistoryDataM();
										followDocHistory.setApplicationGroupId(applicationGroup.getApplicationGroupId());
										followDocHistory.setDocumentCode(documentCode);
										followDocHistory.setFollowupSeq(currentSeq);
										followDocHistory.setDocReason(docCheckListReason.getDocReason());
										followDocHistorys.add(followDocHistory);
									}
								}							
							}
						}
					}
				}
			}
		}
		return followDocHistorys;
	}
	private static boolean isContainDocReasonAndDocCode(ArrayList<FollowDocHistoryDataM> followDocHistoryList,String docReason,String docCode){
		if(!Util.empty(followDocHistoryList)){
			for(FollowDocHistoryDataM followDocHis:followDocHistoryList){
				String docCodeFollow = followDocHis.getDocumentCode();
				String docReasonFollow = followDocHis.getDocReason();
				if(!Util.empty(docCodeFollow) && !Util.empty(docReasonFollow) && docCodeFollow.equals(docCode) && docReasonFollow.equals(docReason)){
					return true;
				}
			}
		}
		return false;
	}
	private static boolean isDocComplete(PersonalInfoDataM personalInfo, DocumentCheckListDataM  documentCheckList) {
		boolean isDocComplete = true; 
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		String REASON_CODE_NO_DOC = SystemConstant.getConstant("REASON_CODE_NO_DOC");
		String chkListDocCode = documentCheckList.getDocumentCode();
		String chkListDocTypeId = documentCheckList.getDocTypeId();
		boolean foundGeneratedReasonNoDoc = false;
		ArrayList<DocumentCheckListReasonDataM> documentCheckListReasons = documentCheckList.getDocumentCheckListReasons();
		if(null!=documentCheckListReasons){
			for(DocumentCheckListReasonDataM documentCheckReasons : documentCheckListReasons){
				String reasonCode = documentCheckReasons.getDocReason();
				String generateFlag = documentCheckReasons.getGenerateFlag();
				if(REASON_CODE_NO_DOC.equals(reasonCode) && FLAG_YES.equals(generateFlag)){
					foundGeneratedReasonNoDoc = true;
					break; 
				}
			}
		}
		logger.debug("chkListDocCode : "+chkListDocCode);	// ORIG_DOC_CHECK_LIST.DOCUMENT_CODE
		logger.debug("chkListDocTypeId : "+chkListDocTypeId); // ORIG_DOC_CHECK_LIST.DOC_TYPE_ID
		
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(null==verificationResult) {
			verificationResult = new VerificationResultDataM();
		}		
		ArrayList<RequiredDocDataM> requiredDocList = verificationResult.getRequiredDocs();
		if(!ServiceUtil.empty(requiredDocList)) {
			for(RequiredDocDataM requiredDoc : requiredDocList) {
				if(null==requiredDoc) {
					requiredDoc = new RequiredDocDataM();
				}
				ArrayList<RequiredDocDetailDataM> requiredDocDetailList = requiredDoc.getRequiredDocDetails();
				if(!ServiceUtil.empty(requiredDocDetailList)) {
					for(RequiredDocDetailDataM requiredDocDetail : requiredDocDetailList) {
						if(null==requiredDocDetail) {
							requiredDocDetail = new RequiredDocDetailDataM();
						}
						String reqDocScnType = requiredDoc.getScenarioType();
						String reqDocDocCompletedFlag = requiredDoc.getDocCompletedFlag();
						String reqDocDetailDocCode = requiredDocDetail.getDocumentCode();
						logger.debug("reqDocScnType : " + reqDocScnType);	// XRULES_REQUIRED_DOC.SCENARIO_TYPE
						logger.debug("reqDocDocCompletedFlag : " + reqDocDocCompletedFlag);	// XRULES_REQUIRED_DOC.DOC_COMPLETED_FLAG
						logger.debug("reqDocDetailDocCode : " + reqDocDetailDocCode); // XRULES_REQUIRED_DOC_DETAIL.DOCUMENT_CODE
						if(!ServiceUtil.empty(chkListDocCode) && !ServiceUtil.empty(reqDocScnType) && !ServiceUtil.empty(chkListDocTypeId) && !ServiceUtil.empty(reqDocDetailDocCode)	// check null
								&& chkListDocTypeId.equals(reqDocScnType) && chkListDocCode.equals(reqDocDetailDocCode)) {		// check match
							if((null==reqDocDocCompletedFlag || !FLAG_YES.equals(reqDocDocCompletedFlag)) || (FLAG_YES.equals(reqDocDocCompletedFlag) && !foundGeneratedReasonNoDoc)) {	// check detail
								isDocComplete = false;
							}
						}
					}
				}
			}
		}
		
		logger.info("result isDocComplete : "+isDocComplete);
		return isDocComplete;
	}
	@Override
	public HashMap<String, Object> getDMNotReceivePhysicalDocumentValues(ArrayList<DMNotificationDataM>  dmNotifications) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hNotReceive =new HashMap<String,Object>();
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  DISTINCT TO_DATE(PARAM3,'yyyy-mm-dd hh24:mi:ss') AS SCAN_DATE ,DM_DOC.TH_FIRST_NAME || ' ' ||DM_DOC.TH_LAST_NAME AS CUSTOMER_NAME ");
			sql.append(" FROM  DM_DOC");
			sql.append(" WHERE  DM_DOC.DM_ID IN (");
			String COMMA="";
			for(DMNotificationDataM  dmNotification :dmNotifications){
				logger.debug(">>dmNotification.getDmId()>>"+dmNotification.getDmId());
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());	
			int index =1;
			for(DMNotificationDataM  dmNotification :dmNotifications){
				ps.setString(index++,dmNotification.getDmId()); 	
			}
			rs = ps.executeQuery();	 
			int i=1;
			StringBuilder CUSTOMER_DUEDATE= new StringBuilder("");
			while(rs.next()) {
				Date SCAN_DATE= rs.getDate("SCAN_DATE");
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				CUSTOMER_DUEDATE.append("<p>"+String.valueOf(i++)+"."+getSpace(1)+CUSTOMER_NAME+getSpace(5)+Formatter.display(SCAN_DATE, Formatter.EN, Format.ddMMyyyy)+"</p>");
			}
			hNotReceive.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_DUE_DATE, CUSTOMER_DUEDATE.toString());
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hNotReceive;
	}
		
    private String getSpace(int spaceNum){
    	StringBuilder space = new StringBuilder("");
    	for(int i=0 ;i<spaceNum;i++){
    		space.append("&nbsp;");
    	}
    	return space.toString();
    }

	@Override
	public HashMap<String, Object> getDMIncompletePhysicalDocumentValues(ArrayList<DMNotificationDataM>  dmNotifications) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hNotReceive =new HashMap<String,Object>();
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DM_ID,SCAN_DATE,CUSTOMER_NAME,LISTAGG(DISPLAY_NAME,'') WITHIN GROUP (ORDER BY DISPLAY_NAME)  AS  DOCUMENT_NAME ");
			sql.append(" FROM(");
			sql.append(" SELECT DISTINCT ");
			sql.append(" DM_DOC.DM_ID, ");
		    sql.append(" TO_DATE(PARAM3,'yyyy-mm-dd hh24:mi:ss')  AS SCAN_DATE, ");
		    sql.append(" DM_DOC.TH_FIRST_NAME || ' ' ||DM_DOC.TH_LAST_NAME AS CUSTOMER_NAME, ");
		    sql.append(" SUB.DISPLAY_NAME ");
		    sql.append(" FROM DM_DOC ");
		    sql.append(" LEFT JOIN (SELECT  '<p>-&nbsp;'||L.DISPLAY_NAME || '</p>' AS DISPLAY_NAME , S.DM_ID  FROM  DM_SUB_DOC S  "); 
		    sql.append(" 		   LEFT JOIN LIST_BOX_MASTER L ON L.CHOICE_NO =S.DOC_TYPE AND L.FIELD_ID ='"+DOC_LIST_FIELD_ID+"' WHERE S.STATUS ='"+DM_DOC_STATUS_NOT_IN_WAREHOUSE+"')SUB ON DM_DOC.DM_ID = SUB.DM_ID ");
   			sql.append(" WHERE  DM_DOC.DM_ID IN (");
   			String COMMA="";
   			for(DMNotificationDataM  dmNotification :dmNotifications){
   				sql.append(COMMA+"?");
   				COMMA=",";
   			}
   			sql.append(")");
   			sql.append(" )APP GROUP BY ");
		    sql.append(" DM_ID, ");
		    sql.append(" SCAN_DATE, ");
		    sql.append(" CUSTOMER_NAME ");

			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());	
			int index =1;
			for(DMNotificationDataM  dmNotification :dmNotifications){
				ps.setString(index++,dmNotification.getDmId()); 	
			}
			rs = ps.executeQuery();	 
			int i=1;
			StringBuilder  data = new StringBuilder("");
			while(rs.next()) {
				Date SCAN_DATE = rs.getDate("SCAN_DATE");
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				String DOCUMENT_NAME = rs.getString("DOCUMENT_NAME");
				data.append("<p>"+String.valueOf(i++)+"."+getSpace(1)+CUSTOMER_NAME+getSpace(5)+Formatter.display(SCAN_DATE, Formatter.EN,Format.ddMMyyyy)+"</p>");
				data.append(getSpace(2)+DOCUMENT_NAME);
				logger.debug("data>>"+data.toString());
			}
			hNotReceive.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_DUE_DATE, data.toString());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hNotReceive;
	}

	@Override
	public HashMap<String, Object> getDMReturnningPhysicalDocumentValues(DMNotificationDataM dmNotification) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object>  hDocReturn =new HashMap<String,Object>();
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DM_DOC.DM_ID,L.DISPLAY_NAME AS DOCUMENT_NAME, ");
			sql.append(" TRANS.REMARK, TO_CHAR(TRANS.DUE_DATE,'dd/mm/yyyy') AS DUE_DATE, TO_CHAR(TRANS.ACTION_DATE,'dd/mm/yyyy') AS ACTION_DATE,");
			sql.append(" DM_DOC.TH_FIRST_NAME || ' ' ||DM_DOC.TH_LAST_NAME AS CUSTOMER_NAME ");
			sql.append(" FROM  DM_DOC ");
			sql.append(" LEFT JOIN DM_SUB_DOC S ON  DM_DOC.DM_ID =S.DM_ID  ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER L ON  L.CHOICE_NO =S.DOC_TYPE AND L.FIELD_ID ='"+DOC_LIST_FIELD_ID+"' ");	
			sql.append(" LEFT JOIN  (SELECT T.DM_ID,T.REMARK,T.DUE_DATE, T.ACTION_DATE FROM DM_TRANSACTION T ");
			sql.append(" 	WHERE  T.CREATE_DATE = (SELECT MAX(T2.CREATE_DATE) FROM DM_TRANSACTION T2 WHERE T2.DM_ID = T.DM_ID)) TRANS ");
			sql.append(" 	ON TRANS.DM_ID = DM_DOC.DM_ID");
			sql.append(" WHERE DM_DOC.DM_ID  =? ");
			 
			logger.debug(">>sql>>"+sql);
			logger.debug(">>dmNotification.getDmId()>>"+dmNotification.getDmId());
			ps = conn.prepareStatement(sql.toString());	
			int index =1;
			ps.setString(index++,dmNotification.getDmId()); 	
			rs = ps.executeQuery();	 
			int i=1;
			StringBuilder docList = new StringBuilder("");
			while(rs.next()) {
				String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
				String DOCUMENT_NAME = rs.getString("DOCUMENT_NAME");
				String BORROWED_DATE = rs.getString("ACTION_DATE");
				String DUE_DATE = rs.getString("DUE_DATE");
				String REMARK = rs.getString("REMARK");				
				docList.append("<p>"+String.valueOf(i++)+"."+NotificationCondition.getSpace(1)+Formatter.displayText(DOCUMENT_NAME)+"</p>");							
				hDocReturn.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_DETAIL, docList);
				hDocReturn.put(TemplateBuilderConstant.TemplateVariableName.REMARK,REMARK);
				hDocReturn.put(TemplateBuilderConstant.TemplateVariableName.BORROWED_DATE,BORROWED_DATE);
				hDocReturn.put(TemplateBuilderConstant.TemplateVariableName.DUE_DATE,DUE_DATE);
				hDocReturn.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME,CUSTOMER_NAME);
			}			
			hDocReturn.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_DETAIL,docList.toString());
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hDocReturn;
	}


	@Override
	public HashMap<String, Object> getSMSTemplateCCandKECNonIncrease(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		String applicationGroupId ="";
		ArrayList<String> listCusName = new ArrayList<String>();
//		ArrayList<String> listCusNameTh = new ArrayList<String>();
//		ArrayList<String> listCusNameEn = new ArrayList<String>();
		ArrayList<String> listProductTh = new ArrayList<String>();
		ArrayList<String> listProductEn = new ArrayList<String>();
		ArrayList<String> finalDecisions = new  ArrayList<String>();
		ArrayList<String> personalIds = new ArrayList<String>();
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		String contactPoint="";
		String cardCode = "";
		HashMap<String,String> personMapEn = new HashMap<String,String>();
		HashMap<String,String> personMapTh = new HashMap<String,String>();
		String NOTIFICATION_CC_PRODUCT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_CC_PRODUCT");
		String NOTIFICATION_KEC_PRODUCT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_KEC_PRODUCT");
		boolean foundCard = false;
		String source = "";
		if(null!=receipientInfos){
			for(RecipientInfoDataM recipien : receipientInfos){
				logger.debug("recipien : " + recipien.toString());
				if(!InfBatchUtil.empty(recipien.getFinalDecision()) && !finalDecisions.contains(recipien.getFinalDecision())){
					finalDecisions.add(recipien.getFinalDecision());
				}
				if(!InfBatchUtil.empty(recipien.getPersonalId()) && !personalIds.contains(recipien.getPersonalId())){
					personalIds.add(recipien.getPersonalId());
				}
				if(InfBatchUtil.empty(applicationGroupId)){
					applicationGroupId =recipien.getApplicationGroupId();
				}
				if(!InfBatchUtil.empty(recipien.getApplicationRecordId()) && !applicationRecordIds.contains(recipien.getApplicationRecordId())){
					applicationRecordIds.add(recipien.getApplicationRecordId());
				}
				// We expect that each call to this method will be supplied with data for 1 product 
				if (NOTIFICATION_CC_PRODUCT.equals(recipien.getProduct()) || NOTIFICATION_KEC_PRODUCT.equals(recipien.getProduct()))
					foundCard = true;
			}
		}
		try{
			logger.debug("applicationRecordIds : " + applicationRecordIds);
			conn = getConnection();
			StringBuilder sql = new StringBuilder();

//			sql.append(" SELECT DISTINCT LB.SYSTEM_ID4 CARD_TYPE_DESC, LB.SYSTEM_ID3 TH_CARD_TYPE_DESC,P.PERSONAL_ID, ");
//			sql.append(" P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH, ");
//			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN, ");
//			//sql.append(" CT.CARD_CODE,PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME ");
//			sql.append(" CT.CARD_CODE,PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,APP.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME, ");
//			sql.append("  PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
//			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
//			sql.append(" JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID ");
//			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = APP.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
//			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
//			sql.append(" JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID  ");
//			sql.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
//			sql.append(" JOIN CARD_TYPE CT ON C.CARD_TYPE = CT.CARD_TYPE_ID ");
//			sql.append(" JOIN LIST_BOX_MASTER LB ON LB.CHOICE_NO = CT.CARD_CODE AND LB.FIELD_ID = ? ");
//			sql.append(" LEFT JOIN LIST_BOX_MASTER PRODUCT_LIST ON  PRODUCT_LIST.SYSTEM_ID2 = APP.BUSINESS_CLASS_ID AND PRODUCT_LIST.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
//			sql.append(" WHERE APP.APPLICATION_GROUP_ID =?  ");
//			sql.append(" AND AG.APPLICATION_TYPE <> '"+NOTIFICATION_APPICATION_TYPE_INCREASE+"' ");
//			sql.append(" AND APP.APPLICATION_RECORD_ID IN (");
			
			sql.append(" SELECT DISTINCT P.PERSONAL_ID, L.LOAN_AMT,AG.SOURCE, ");
			sql.append(" P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH, ");
			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN, ");
			if (foundCard)
				sql.append(" C.CARD_TYPE, ");
			sql.append(" PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,APP.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME, ");
			sql.append(" PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = APP.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
			sql.append(" JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID  ");
			// For KPL we will not join with ORIG_CARD
			if (foundCard)
				sql.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER PRODUCT_LIST ON  PRODUCT_LIST.SYSTEM_ID2 = APP.BUSINESS_CLASS_ID AND PRODUCT_LIST.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append(" WHERE APP.APPLICATION_GROUP_ID =?  ");
			sql.append(" AND AG.APPLICATION_TYPE <> '"+NOTIFICATION_APPICATION_TYPE_INCREASE+"' ");
			sql.append(" AND APP.APPLICATION_RECORD_ID IN (");
			if(null!=applicationRecordIds){
				String commaApp="";
				for(String appRecordId : applicationRecordIds){
					sql.append(commaApp+"'"+appRecordId+"'");
					commaApp=",";
				}
			}
			sql.append(" )");
			
			if(personalIds.size()>0){
				sql.append(" AND P.PERSONAL_ID IN (");
				String comma="";
				for(String personalId :personalIds){
					sql.append(comma+"'"+personalId+"'");
					comma=",";
				}
				sql.append(" )");
			}
			
			if(finalDecisions.size()>0){
				sql.append(" AND APP.FINAL_APP_DECISION  IN (");
				String comma="";
				for(String finalDecision :finalDecisions){
					sql.append(comma+"'"+finalDecision+"'");
					comma=",";
				}
				sql.append(" )");
			}
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());	
				int index = 1;
				//ps.setString(index++,NOTIFY_DOC_LIST_FIELD_ID);
				ps.setString(index++,applicationGroupId);
			rs = ps.executeQuery();
			StringBuilder cDesc = new StringBuilder();
			StringBuilder pDesc = new StringBuilder();
			StringBuilder cDescTh = new StringBuilder();
			StringBuilder cDescEn = new StringBuilder();
			
			String loanAmount = "0";
			
			String commaResult="";
			String comma="";
			
			while(rs.next()){
				String CARD_TYPE_TH_DESC = "";
				String CARD_TYPE_EN_DESC = "";
				if (foundCard) {
//					cardCode = rs.getString("CARD_CODE");
					String cardType = rs.getString("CARD_TYPE");
					CARD_TYPE_TH_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_TH_DESC");
					CARD_TYPE_EN_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_EN_DESC");
					cardCode = CardInfoUtil.getFullCardDetail(cardType, "CARD_CODE");
					if (InfBatchUtil.empty(CARD_TYPE_TH_DESC)) {
						CARD_TYPE_TH_DESC = rs.getString("PRODUCT_NAME_TH");
					}
					if (InfBatchUtil.empty(CARD_TYPE_EN_DESC)) {
						CARD_TYPE_EN_DESC = rs.getString("PRODUCT_NAME_EN");
					}
				}
				pDesc.append(comma+rs.getString("PRODUCT_NAME"));
				//cDescTh.append(comma+rs.getString("TH_CARD_TYPE_DESC"));
				//cDescEn.append(comma+rs.getString("CARD_TYPE_DESC"));
				cDescTh.append(comma+CARD_TYPE_TH_DESC);
				cDescEn.append(comma+CARD_TYPE_EN_DESC);
				comma=",";
				
				if(FormatUtil.EN.toUpperCase().equals(templateMaster.getLanguage().toUpperCase())){
					listCusName.add(rs.getString("CUSTOMER_NAME_SURNAME_EN"));
					//cDesc.append(commaResult+rs.getString("CARD_TYPE_DESC"));
					cDesc.append(commaResult+CARD_TYPE_EN_DESC);
					commaResult=",";
				}else{
					listCusName.add(rs.getString("CUSTOMER_NAME_SURNAME_TH"));
					//cDesc.append(commaResult+rs.getString("TH_CARD_TYPE_DESC"));
					cDesc.append(commaResult+CARD_TYPE_TH_DESC);
					commaResult=",";
				}
				
				
				// KMOBILE
				//listCusNameEn.add(rs.getString("CUSTOMER_NAME_SURNAME_EN"));
				//listCusNameTh.add(rs.getString("CUSTOMER_NAME_SURNAME_TH"));
				personMapEn.put(rs.getString("PERSONAL_ID"), rs.getString("CUSTOMER_NAME_SURNAME_EN"));
				personMapTh.put(rs.getString("PERSONAL_ID"), rs.getString("CUSTOMER_NAME_SURNAME_TH"));
				listProductTh.add(rs.getString("PRODUCT_NAME_TH"));
				listProductEn.add(rs.getString("PRODUCT_NAME_EN"));
				
				
				if(!InfBatchUtil.empty(rs.getInt("LOAN_AMT"))){
		 			try{
		 				loanAmount = String.format("%,d",rs.getInt("LOAN_AMT"));
					}catch(Exception e){logger.debug("ERROR",e);}
		  		}
				source = rs.getString("SOURCE");
			}
			
			contactPoint = getContactPoint(templateMaster, finalDecisions.get(0), cardCode);
			String cardType = cDesc.toString();
			String productName = pDesc.toString();
			String cusName = listToString(listCusName);
			String cardTypeTh = cDescTh.toString();
			String cardTypeEn = cDescEn.toString();
			// KMOBILE
			//String customerNameTh = listToString(listCusNameTh);
			//String customerNameEn = listToString(listCusNameEn);
			String productNameTh = listToString(listProductTh);
			String productNameEn = listToString(listProductEn);
			String customerNameTh = mapToString(personMapTh);
			String customerNameEn = mapToString(personMapEn);
			String contactPointTh = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, FormatUtil.TH);
			String contactPointEn = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, FormatUtil.EN);
			
//			cardType = cardType.substring(0, cardType.length()-2);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE, cardType);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, cusName);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT,contactPoint);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_TH,contactPointTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_EN,contactPointEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,productName);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_TH, cardTypeTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_EN, cardTypeEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH, productNameTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN, productNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_TH, customerNameTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_EN, customerNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LINE, loanAmount);
			map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LIMIT, loanAmount);
			//For CR0347
			if(InfBatchUtil.eApp(source)){
				map.put(TemplateBuilderConstant.TemplateVariableName.MSG_CREDIT_LIMIT,InfBatchConstant.message.CREDIT_LIMIT+" "+loanAmount+" "+InfBatchConstant.message.BATH+" ");
				map.put(TemplateBuilderConstant.TemplateVariableName.MSG_TOT_CREDIT_LIMIT,InfBatchConstant.message.TOT_CREDIT_LIMIT+" "+loanAmount+" "+InfBatchConstant.message.BATH+" ");
			}
			//CR370-371
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, productNameTh + " " + productNameEn);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}

	@Override
	public HashMap<String, Object> getSMSTemplateCCIncrease(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
		String loanAmount = "0";
		String applicationGroupId = "";
		ArrayList<String> personalIds = new ArrayList<String>();
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		ArrayList<String> finalDecisions = new  ArrayList<String>();
		String customerName="";
		String customerNameEn="";
		String customerNameTh="";
		String contactPointEn = "";
		String contactPointTh = "";
		String productNameEn = "";
		String productNameTh = "";
		String contactPoint="";
		
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo :receipientInfos){
				if(InfBatchUtil.empty(recipientInfo.getApplicationGroupId())){
					applicationGroupId = recipientInfo.getApplicationGroupId();
				}
				applicationGroupId = recipientInfo.getApplicationGroupId();
				if(!personalIds.contains(recipientInfo.getPersonalId())){
					personalIds.add(recipientInfo.getPersonalId());
				}
				if(!applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
				if(!InfBatchUtil.empty(recipientInfo.getFinalDecision()) && !finalDecisions.contains(recipientInfo.getFinalDecision())){
					finalDecisions.add(recipientInfo.getFinalDecision());
				}
			}
		
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT ");
			//sql.append(" C.CARD_NO, L.LOAN_AMT, LB.SYSTEM_ID4 CARD_TYPE_DESC, LB.SYSTEM_ID3 TH_CARD_TYPE_DESC,P.PERSONAL_ID, ");
			sql.append(" C.CARD_NO, L.LOAN_AMT, C.CARD_TYPE ,P.PERSONAL_ID, ");
			sql.append(" P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH, ");
			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN, ");
			//sql.append(" CT.CARD_CODE, PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
			sql.append("  PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION APP  ON APP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = APP.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
			sql.append(" JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID ");
			sql.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID  ");
//			sql.append(" JOIN CARD_TYPE CT ON C.CARD_TYPE = CT.CARD_TYPE_ID ");
//			sql.append(" JOIN LIST_BOX_MASTER LB ON LB.CHOICE_NO = CT.CARD_CODE AND LB.FIELD_ID = ? ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER PRODUCT_LIST ON  PRODUCT_LIST.SYSTEM_ID2 = APP.BUSINESS_CLASS_ID AND PRODUCT_LIST.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append(" WHERE APP.APPLICATION_GROUP_ID = ? ");
			sql.append(" AND AG.APPLICATION_TYPE = '"+NOTIFICATION_APPICATION_TYPE_INCREASE+"' ");
			if(personalIds.size()>0){
				sql.append(" AND P.PERSONAL_ID IN (");
				String comma="";
				for(String personalId : personalIds){
					sql.append(comma+"'"+personalId+"'");
					comma=",";
				}
				sql.append(")");
			}
			if(applicationRecordIds.size()>0){
				sql.append(" AND  APP.APPLICATION_RECORD_ID IN (");
				String comma="";
				for(String applicationRecordId : applicationRecordIds){
					sql.append(comma+"'"+applicationRecordId+"'");
					comma=",";
				}
				sql.append(")");
			}

			if(finalDecisions.size()>0){
				sql.append(" AND APP.FINAL_APP_DECISION  IN (");
				String comma="";
				for(String finalDecision :finalDecisions){
					sql.append(comma+"'"+finalDecision+"'");
					comma=",";
				}
				sql.append(" )");
			}
			logger.debug(">>sql>>"+sql.toString());
			ps = conn.prepareStatement(sql.toString());	
				int index = 1;
//				ps.setString(index++,NOTIFY_DOC_LIST_FIELD_ID);
				ps.setString(index++,applicationGroupId);
			rs = ps.executeQuery();
			
			StringBuilder cardNoBuilder = new StringBuilder();
			StringBuilder cardTypeBuilder = new StringBuilder();
			StringBuilder cardTypeThBuilder = new StringBuilder();
			StringBuilder cardTypeEnBuilder = new StringBuilder();
			String comma = "";
			StringBuilder fullCardNoBuilder = new StringBuilder();
			
			while(rs.next()) {
			
				String CARD_NUMBER = rs.getString("CARD_NO");
				String FULL_CARD_NUMBER = rs.getString("CARD_NO");
				String cardTypeId = rs.getString("CARD_TYPE");
				String CARD_TYPE = null;
//				String CARD_TYPE_TH = null;
//				String CARD_TYPE_EN = null;
				//String cardCode = rs.getString("CARD_CODE");
				String CARD_TYPE_TH_DESC = CardInfoUtil.getFullCardDetail(cardTypeId, "CARD_TYPE_TH_DESC");
				String CARD_TYPE_EN_DESC = CardInfoUtil.getFullCardDetail(cardTypeId, "CARD_TYPE_EN_DESC");
				String cardCode = CardInfoUtil.getFullCardDetail(cardTypeId, "CARD_CODE");
				
//				CARD_TYPE_TH = rs.getString("TH_CARD_TYPE_DESC");
//				CARD_TYPE_EN = rs.getString("CARD_TYPE_DESC");
				if(FormatUtil.EN.toUpperCase().equals(templateMaster.getLanguage().toUpperCase())){
					customerName = rs.getString("CUSTOMER_NAME_SURNAME_EN");
					//CARD_TYPE = rs.getString("CARD_TYPE_DESC");
					CARD_TYPE = CARD_TYPE_EN_DESC;
				}else{
					customerName = rs.getString("CUSTOMER_NAME_SURNAME_TH");
					//CARD_TYPE = rs.getString("TH_CARD_TYPE_DESC");
					CARD_TYPE = CARD_TYPE_TH_DESC;
				}
				if(!InfBatchUtil.empty(CARD_NUMBER)){
		 			try{
		 				CARD_NUMBER = encryptor.decrypt(CARD_NUMBER);
					}catch(Exception e){logger.debug("ERROR",e);}
		  		}
				if(!InfBatchUtil.empty(FULL_CARD_NUMBER)){
		 			try{
		 				FULL_CARD_NUMBER = encryptor.decrypt(FULL_CARD_NUMBER);
					}catch(Exception e){logger.debug("ERROR",e);}
		  		}
				if(!InfBatchUtil.empty(rs.getInt("LOAN_AMT"))){
		 			try{
		 				loanAmount = String.format("%,d",rs.getInt("LOAN_AMT"));
					}catch(Exception e){logger.debug("ERROR",e);}
		  		}
				// KMOBILE
				customerNameEn = rs.getString("CUSTOMER_NAME_SURNAME_EN");
				customerNameTh = rs.getString("CUSTOMER_NAME_SURNAME_TH");
				contactPointEn = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, FormatUtil.EN);
				contactPointTh = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, FormatUtil.TH);
				productNameEn = rs.getString("PRODUCT_NAME_EN");
				productNameTh = rs.getString("PRODUCT_NAME_TH");
				contactPoint = getContactPoint(templateMaster, finalDecisions.get(0), cardCode);
				CARD_NUMBER = CARD_NUMBER_FORMAT.replace("XXXX", CARD_NUMBER.substring(CARD_NUMBER.length()-4,CARD_NUMBER.length()));
				
				if(!InfBatchUtil.empty(FULL_CARD_NUMBER)){
					FULL_CARD_NUMBER = FormatUtil.maskNumber(FULL_CARD_NUMBER, "####-##XX-XXXX-####");
				}
				
				cardNoBuilder.append(comma + CARD_NUMBER);
				cardTypeBuilder.append(comma + CARD_TYPE);
				cardTypeThBuilder.append(comma + CARD_TYPE_TH_DESC);
				cardTypeEnBuilder.append(comma + CARD_TYPE_EN_DESC);
				fullCardNoBuilder.append(comma+ FULL_CARD_NUMBER);
				comma = ",";
			}
			String cardNoResult = cardNoBuilder.toString();
			String cardTypeResult = cardTypeBuilder.toString();
			String cardTypeThResult = cardTypeThBuilder.toString();
			String cardTypeEnResult = cardTypeEnBuilder.toString();
			String fullCardNoResult = fullCardNoBuilder.toString();
			
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_NO, cardNoResult); 
			map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LIMIT,loanAmount);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME,customerName);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE,cardTypeResult);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT,contactPoint);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_EN,contactPointEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_TH,contactPointTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_TH,cardTypeThResult);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_EN,cardTypeEnResult);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH,productNameTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN,productNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_EN,customerNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_TH,customerNameTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LINE,loanAmount);
			//CR370-371
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, productNameTh + " " + productNameEn);
			//DF1400
			map.put(TemplateBuilderConstant.TemplateVariableName.FULL_CARD_NUMBER, fullCardNoResult);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}

	@Override
	public HashMap<String, Object> getSMSTemplateKECIncrease(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		String loanAmount = "0";
		String applicationGroupId="";
		int maxLifeCycle=1;
		Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
		ArrayList<String> personalIds = new ArrayList<String>();
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		ArrayList<String> finalDecisions = new ArrayList<String>();
		String customerName="";
		String customerNameEn="";
		String customerNameTh="";
		String contactPointEn="";
		String contactPointTh="";
		String contactPoint="";
		String cardCode = "";
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo :receipientInfos){
				if(applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
				if(personalIds.contains(recipientInfo.getPersonalId())){
					personalIds.add(recipientInfo.getPersonalId());
				}
				if(!InfBatchUtil.empty(recipientInfo.getFinalDecision()) && !finalDecisions.contains(recipientInfo.getFinalDecision())){
					finalDecisions.add(recipientInfo.getFinalDecision());
				}
				applicationGroupId=recipientInfo.getApplicationGroupId();
				maxLifeCycle=recipientInfo.getMaxLifeCycle();
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			
			//sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME,L.LOAN_AMT, ");
			sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME,L.LOAN_AMT, ");
			sql.append(" P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH, ");
			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN, C.CARD_TYPE,C.CARD_NO ");
			//sql.append(" CT.CARD_CODE ");
			sql.append(" , PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,'"+LANGUAGE_TH+"') AS PRODUCT_NAME_TH ");
			sql.append(" , PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,'"+LANGUAGE_OTHER+"') AS PRODUCT_NAME_EN ");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID  ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
			sql.append(" INNER JOIN ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID  ");
			sql.append(" INNER JOIN ORIG_LOAN L ON A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID  ");
			sql.append(" INNER JOIN ORIG_LOAN L ON A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID   ");
			sql.append(" INNER JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID  ");
			//sql.append(" INNER JOIN CARD_TYPE CT ON C.CARD_TYPE = CT.CARD_TYPE_ID ");
			//sql.append(" AND C.CARD_LEVEL = CT.CARD_LEVEL ");
			sql.append(" WHERE  A.LIFE_CYCLE = ? ");
			sql.append(" AND A.APPLICATION_GROUP_ID =? ");
			sql.append(" AND AG.APPLICATION_TYPE='"+NOTIFICATION_APPICATION_TYPE_INCREASE+"' ");
			if(personalIds.size()>0){
				sql.append(" AND  P.PERSONAL_ID IN (");
				String comma="";
				for(String personalId :personalIds){
					sql.append(comma+"'"+personalId+"'");
					comma=",";
				}
				sql.append("");
			}
			
			if(applicationRecordIds.size()>0){
				sql.append(" AND  A.APPLICATION_RECORD_ID IN (");
				String comma="";
				for(String applicationRecordId :applicationRecordIds){
					sql.append(comma+"'"+applicationRecordId+"'");
					comma=",";
				}
				sql.append("");
			}
			 	
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setInt(1,maxLifeCycle); 		
			ps.setString(2,applicationGroupId); 			
			rs = ps.executeQuery();	 
			if(rs.next()) {
				if(!InfBatchUtil.empty(rs.getInt("LOAN_AMT"))){
		 			try{
		 				loanAmount = String.format("%,d",rs.getInt("LOAN_AMT"));
					}catch(Exception e){
						logger.debug("ERROR",e);
					}
		  		}
				
				if(FormatUtil.EN.toUpperCase().equals(templateMaster.getLanguage().toUpperCase())){
					customerName = rs.getString("CUSTOMER_NAME_SURNAME_EN");
				}else{
					customerName = rs.getString("CUSTOMER_NAME_SURNAME_TH");
				}
				//cardCode = rs.getString("CARD_CODE");
				String cardType = rs.getString("CARD_TYPE");
				cardCode = CardInfoUtil.getFullCardDetail(cardType, "CARD_CODE");
				// KMOBILE
				customerNameEn = rs.getString("CUSTOMER_NAME_SURNAME_EN");
				customerNameTh = rs.getString("CUSTOMER_NAME_SURNAME_TH");
				contactPointEn = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, Formatter.EN);
				contactPointTh = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, Formatter.TH);
				logger.debug("finalDecisions.get(0) >> "+finalDecisions.get(0));
				contactPoint = getContactPoint(templateMaster, finalDecisions.get(0), cardCode);
				
				String CARD_NUMBER = rs.getString("CARD_NO");
				if(!InfBatchUtil.empty(CARD_NUMBER)){
		 			try{
		 				CARD_NUMBER = encryptor.decrypt(CARD_NUMBER);
					}catch(Exception e){logger.debug("ERROR",e);}
		  		}
				CARD_NUMBER = CARD_NUMBER_FORMAT.replace("XXXX", CARD_NUMBER.substring(CARD_NUMBER.length()-4,CARD_NUMBER.length()));
				
				String FULL_CARD_NUMBER = rs.getString("CARD_NO");
				if(!InfBatchUtil.empty(CARD_NUMBER)){
		 			try{
		 				FULL_CARD_NUMBER = encryptor.decrypt(FULL_CARD_NUMBER);
					}catch(Exception e){logger.debug("ERROR",e);}
		  		}
				if(!InfBatchUtil.empty(FULL_CARD_NUMBER)){
					FULL_CARD_NUMBER = FormatUtil.maskNumber(FULL_CARD_NUMBER, "####-##XX-XXXX-####");
				}
				
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, rs.getString("PRODUCT_NAME"));
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH, rs.getString("PRODUCT_NAME_TH"));
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN, rs.getString("PRODUCT_NAME_EN"));
				map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LIMIT, loanAmount);
				map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME,customerName);
				map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT,contactPoint);
				map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_EN,contactPointEn);
				map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_TH,contactPointTh);
				map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_EN,customerNameEn);
				map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_TH,customerNameTh);
				map.put(TemplateBuilderConstant.TemplateVariableName.CARD_NO,CARD_NUMBER);
				map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LINE, loanAmount);
				
				//CR370-371
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, rs.getString("PRODUCT_NAME_TH") + " " + rs.getString("PRODUCT_NAME_EN"));
				//DF1400
				map.put(TemplateBuilderConstant.TemplateVariableName.FULL_CARD_NUMBER, FULL_CARD_NUMBER);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}

	@Override
	public HashMap<String, Object> getSMSTemplateCCChannel(ArrayList<RecipientInfoDataM> receipientInfos) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		ArrayList<String> finalDecisions = new ArrayList<String>();
		String applicationGroupId ="";
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo : receipientInfos){
				if(!InfBatchUtil.empty(recipientInfo.getApplicationRecordId()) && !applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
				if(!InfBatchUtil.empty(recipientInfo.getFinalDecision()) && !finalDecisions.contains(recipientInfo.getFinalDecision())){
					finalDecisions.add(recipientInfo.getFinalDecision());
				}
				applicationGroupId=recipientInfo.getApplicationGroupId();
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();			
//			sql.append(" SELECT DISTINCT C.CARD_TYPE,P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH, ");
//			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN, ");
//			sql.append(" L.APPLICATION_RECORD_ID,P.NATIONALITY ");
//			sql.append(" FROM ORIG_APPLICATION APP ");
//			sql.append(" JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID  ");
//			sql.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
//			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID  AND P.PERSONAL_TYPE IN ('A','S') ");
			sql.append(" SELECT DISTINCT C.CARD_TYPE,P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH, ");
			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN, ");
			sql.append(" L.APPLICATION_RECORD_ID,P.NATIONALITY,P.PERSONAL_TYPE ");
			sql.append(" FROM ORIG_APPLICATION APP ");
			sql.append(" JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID  ");
			sql.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION ON APP.APPLICATION_RECORD_ID = ORIG_PERSONAL_RELATION.REF_ID AND ORIG_PERSONAL_RELATION.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON ORIG_PERSONAL_RELATION.PERSONAL_ID = P.PERSONAL_ID ");			
			sql.append(" WHERE APP.APPLICATION_GROUP_ID =? ");
			if(applicationRecordIds.size()>0){
				sql.append(" AND APP.APPLICATION_RECORD_ID IN (");
				String comma="";
				for(String appRecordId : applicationRecordIds){
					sql.append(comma +"'"+appRecordId+"'");
					comma =",";
				}
				sql.append(" )");
			}
			if(finalDecisions.size()>0){
				sql.append(" AND APP.FINAL_APP_DECISION IN (");
				String comma="";
				for(String finalDecision : finalDecisions){
					sql.append(comma +"'"+finalDecision+"'");
					comma =",";
				}
				sql.append(" )");
			}
			sql.append(" ORDER BY P.PERSONAL_TYPE ");
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());
				int index = 1;
//				ps.setString(index++, NOTIFY_DOC_LIST_FIELD_ID);
				ps.setString(index++, applicationGroupId);
			rs = ps.executeQuery();	 
			StringBuilder cDesc = new StringBuilder();
			String customerNameSurname = null;
			List<String> existingCards = new ArrayList<String>(); 
			if(rs.next()) {
				//String cardCode = rs.getString("CARD_CODE");
				String cardType = rs.getString("CARD_TYPE");
				String CARD_TYPE_TH_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_TH_DESC");
				String CARD_TYPE_EN_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_EN_DESC");
				String cardCode = CardInfoUtil.getFullCardDetail(cardType, "CARD_CODE");
				String cardDescription = "";
				if(THAI_NATIONALITY.equals(rs.getString("NATIONALITY"))){
					customerNameSurname = rs.getString("CUSTOMER_NAME_SURNAME_TH");
					//cDesc.append(rs.getString("TH_CARD_TYPE_DESC")+", ");
					//cardDescription = rs.getString("TH_CARD_TYPE_DESC")+", ";
					cardDescription = CARD_TYPE_TH_DESC+", ";
				}else{
					customerNameSurname = rs.getString("CUSTOMER_NAME_SURNAME_EN");
					//cDesc.append(rs.getString("CARD_TYPE_DESC")+", ");
					//cardDescription = rs.getString("CARD_TYPE_DESC")+", ";
					cardDescription = CARD_TYPE_EN_DESC+", ";
				}	
				addCardTypeDescription(cDesc, existingCards, cardCode, cardDescription);
			}
			String cardType = cDesc.toString();
			cardType = cardType.substring(0, cardType.length()-2);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_SURNAME, customerNameSurname);	
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE,cardType);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}
	private void addCardTypeDescription(StringBuilder cardBuilder,List<String> existingCards,String newCardCode,String newCardDescription){
		if(!existingCards.contains(newCardCode)){
			cardBuilder.append(newCardDescription);
			existingCards.add(newCardCode);
		}
	}
	@Override
	public HashMap<String, Object> getSMSTemplateKECandKPLReject(ArrayList<RecipientInfoDataM> receipientInfos) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		String  applicationGroupId="";
		int maxLifeCycle =1;
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo : receipientInfos){
				applicationGroupId = recipientInfo.getApplicationGroupId();
				maxLifeCycle =recipientInfo.getMaxLifeCycle();
				if(!applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();

			//sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME ,AG.APPLICATION_TYPE ");
			sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME ,AG.APPLICATION_TYPE ");
//			sql.append("		, PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,'"+LANGUAGE_TH+"') AS PRODUCT_NAME_TH ");
//			sql.append("		, PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,'"+LANGUAGE_OTHER+"') AS PRODUCT_NAME_EN ");
			sql.append("		, PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER PRODUCT_LIST ON  PRODUCT_LIST.SYSTEM_ID2 = A.BUSINESS_CLASS_ID AND PRODUCT_LIST.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append(" WHERE  A.LIFE_CYCLE = ?");
			sql.append(" AND A.APPLICATION_GROUP_ID =?  AND AG.APPLICATION_TYPE=? ");
			if(applicationRecordIds.size()>0){
				sql.append(" AND  A.APPLICATION_RECORD_ID IN (");
				String comma="";
				for(String appRecordId  : applicationRecordIds){
					sql.append(comma+"'"+appRecordId+"'");
					comma=",";
				}
				sql.append(")");
			}
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setInt(1,maxLifeCycle); 		
			ps.setString(2,applicationGroupId); 			
			ps.setString(3,APPICATION_TYPE_NEW); 

			rs = ps.executeQuery();	 
			if(rs.next()) {
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, rs.getString("PRODUCT_NAME"));
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH, rs.getString("PRODUCT_NAME_TH"));
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN, rs.getString("PRODUCT_NAME_EN"));
				
				//CR370-371
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, rs.getString("PRODUCT_NAME_TH") + " " + rs.getString("PRODUCT_NAME_EN"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}
	@Deprecated
	private String getCardName(String product,String cardTypeId,String cardLevel) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		String productName="";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  CARD_TYPE_DESC FROM  CARD_TYPE ");
			sql.append(" WHERE CARD_TYPE_ID =?");
			if(NOTIFICATION_CC_PRODUCT.equals(product)){
				sql.append(" AND  CARD_LEVEL =?");
			}
			logger.debug(">>sql>>"+sql);
			logger.debug(">>product:cardTyepId:cardLevel>>"+product+":"+cardTypeId+":"+cardLevel);
			ps = conn.prepareStatement(sql.toString());		
			ps.setString(1,cardTypeId); 
			if(NOTIFICATION_CC_PRODUCT.equals(product)){
				ps.setString(2,cardLevel); 
			}
			rs = ps.executeQuery();	 
			if(rs.next()) {
				productName = rs.getString("CARD_TYPE_DESC");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return productName;
	}
	private String getDocumenNames(ArrayList<String> docCodes) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		StringBuilder documents = new StringBuilder("");
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT DISTINCT TH_DESC FROM    MS_DOC_LIST ");
			sql.append(" WHERE DOCUMENT_CODE IN (");
			if(null!=docCodes && docCodes.size()>0){
				String comma="";
				for(String documentCode :docCodes){
					sql.append(comma+"'"+documentCode+"'");
					comma=",";
				}
				
			} 
			sql.append(")");
			logger.debug(">>sql>>"+sql);
			 
			ps = conn.prepareStatement(sql.toString());		 
			rs = ps.executeQuery();	 
			String docComma="";
			while(rs.next()) {
				String TH_DESC = rs.getString("TH_DESC");
				documents.append(docComma+TH_DESC);
				docComma=",";
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return documents.toString();
	}
	
	private String listToString(List<String> list){
		StringBuilder result = new StringBuilder();
		String comma="";
		for(String data:list){
			result.append(comma+data);
			comma=",";
		}
		return result.toString();
	}
	private String mapToString(HashMap<String,String> map){
		StringBuilder result = new StringBuilder();
		String comma="";
		for(String key : map.keySet()){
			String value = map.get(key);
			if(!InfBatchUtil.empty(key) && !InfBatchUtil.empty(value)){
				result.append(comma+value);
				comma=",";
			}
		}
		return result.toString();
	}
	private String getContactPoint(TemplateMasterDataM templateMaster,String finalAppDecision,String cardCode){
		return getContactPoint(templateMaster, finalAppDecision, cardCode, templateMaster.getLanguage());
	}
	private String getContactPoint(TemplateMasterDataM templateMaster,String finalAppDecision,String cardCode,String language){
		
		String contactPointPrefix=templateMaster.getContactPoint();
		if(TemplateBuilderConstant.TemplateType.SMS.equals(templateMaster.getTemplateType())){
			contactPointPrefix=contactPointPrefix+"_"+TemplateBuilderConstant.TemplateType.SMS;
		}
		
		String contactPoint = null;
		if(NOTIFICATION_APPLICATION_STATUS_APPROVE.equals(finalAppDecision) 
				&& NOTIFICATION_CC_PRODUCT.equals(templateMaster.getProductType())){
				if(NOTIFICATION_WISDOM_INFINITE_CARD_CODE.equals(cardCode)){
					contactPoint = contactPointPrefix+"_"+NOTIFICATION_CONTACT_POINT_INFINITE+"_"+language.toUpperCase();
				}else if(NOTIFICATION_WISDOM_CARD_CODE.equals(cardCode)){
					contactPoint = contactPointPrefix+"_"+NOTIFICATION_CONTACT_POINT_WISDOM+"_"+language.toUpperCase();
				}else if(NOTIFICATION_THE_PREMIER_CARD_CODE.equals(cardCode)){
					contactPoint = contactPointPrefix+"_"+NOTIFICATION_CONTACT_POINT_PREMIER+"_"+language.toUpperCase();
				}else{
					contactPoint = contactPointPrefix+"_"+NOTIFICATION_CONTACT_POINT_GENERIC+"_"+language.toUpperCase();
				}
		}else{
			contactPoint = contactPointPrefix+"_"+language.toUpperCase();
		}
		logger.debug("contact point general param >> "+contactPoint);
		try{
			contactPoint = getGeneralParam(contactPoint);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("contact point >> "+contactPoint);
		return contactPoint;
	}
	
	private String getGeneralParam(String paramId) throws InfBatchException {
		String generalParam = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM GENERAL_PARAM WHERE PARAM_CODE = ?");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,paramId);
			rs = ps.executeQuery();
			if(rs.next()){
				generalParam = rs.getString("PARAM_VALUE");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		logger.debug("generalParam >> "+generalParam);
		return generalParam;
	}

	@Override
	public HashMap<String, Object> getSMSTemplateRejectDocumentList(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		String applicationGroupId ="";
		ArrayList<String> listCusName = new ArrayList<String>();
//		ArrayList<String> listCusNameEn = new ArrayList<String>();
//		ArrayList<String> listCusNameTh = new ArrayList<String>();
		ArrayList<String> listProductTh = new  ArrayList<String>();
		ArrayList<String> listProductEn = new  ArrayList<String>();
		ArrayList<String> finalDecisions = new  ArrayList<String>();
		ArrayList<String> personalIds = new ArrayList<String>();
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		ArrayList<String> personalType = new ArrayList<String>();
		HashMap<String,String> personMapEn = new HashMap<String,String>();
		HashMap<String,String> personMapTh = new HashMap<String,String>();
		String contactPoint="";
		String cardCode = "";
		String personId = "";
		String NOTIFICATION_CC_PRODUCT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_CC_PRODUCT");
		String NOTIFICATION_KEC_PRODUCT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_KEC_PRODUCT");
		boolean foundCard = false;
		if(null!=receipientInfos){
			for(RecipientInfoDataM recipien : receipientInfos){
				if(!InfBatchUtil.empty(recipien.getFinalDecision()) && !finalDecisions.contains(recipien.getFinalDecision())){
					finalDecisions.add(recipien.getFinalDecision());
				}
				if(!InfBatchUtil.empty(recipien.getPersonalId()) && !personalIds.contains(recipien.getPersonalId())){
					personalIds.add(recipien.getPersonalId());
				}
				if(InfBatchUtil.empty(applicationGroupId)){
					applicationGroupId =recipien.getApplicationGroupId();
				}
				if(!InfBatchUtil.empty(recipien.getApplicationRecordId()) && !applicationRecordIds.contains(recipien.getApplicationRecordId())){
					applicationRecordIds.add(recipien.getApplicationRecordId());
				}
				if(!InfBatchUtil.empty(recipien.getPersonalType())){
					personalType.add(recipien.getPersonalType());
				}
				// We expect that each call to this method will be supplied with data for 1 product 
				if (NOTIFICATION_CC_PRODUCT.equals(recipien.getProduct()) || NOTIFICATION_KEC_PRODUCT.equals(recipien.getProduct()))
					foundCard = true;
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			//sql.append(" SELECT DISTINCT LB.SYSTEM_ID4 CARD_TYPE_DESC, LB.SYSTEM_ID3 TH_CARD_TYPE_DESC,P.PERSONAL_ID, ");
			sql.append(" SELECT DISTINCT P.PERSONAL_ID, ");
			sql.append(" P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH, ");
			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN, ");
			//sql.append(" CT.CARD_CODE,PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME ");
			//sql.append(" CT.CARD_CODE,PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,APP.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME, ");
			if (foundCard)
				sql.append(" C.CARD_TYPE, ");
			sql.append(" PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,APP.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME, ");
			sql.append(" PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN ");	
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = APP.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
			sql.append(" JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID  ");
			// For KPL we will not join with ORIG_CARD
			if (foundCard)
				sql.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
			//sql.append(" JOIN CARD_TYPE CT ON C.CARD_TYPE = CT.CARD_TYPE_ID AND C.CARD_LEVEL = CT.CARD_LEVEL  ");
			//sql.append(" JOIN CARD_TYPE CT ON C.CARD_TYPE = CT.CARD_TYPE_ID  ");
			//sql.append(" JOIN LIST_BOX_MASTER LB ON LB.CHOICE_NO = CT.CARD_CODE AND LB.FIELD_ID = ? ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER PRODUCT_LIST ON  PRODUCT_LIST.SYSTEM_ID2 = APP.BUSINESS_CLASS_ID AND PRODUCT_LIST.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append(" WHERE APP.APPLICATION_GROUP_ID =?  ");
			//sql.append(" AND AG.APPLICATION_TYPE <> '"+NOTIFICATION_APPICATION_TYPE_INCREASE+"' ");
			sql.append(" AND APP.APPLICATION_RECORD_ID IN (");
			if(null!=applicationRecordIds){
				String commaApp="";
				for(String appRecordId : applicationRecordIds){
					sql.append(commaApp+"'"+appRecordId+"'");
					commaApp=",";
				}
			}
			sql.append(" )");
			
			if(personalIds.size()>0){
				sql.append(" AND P.PERSONAL_ID IN (");
				String comma="";
				for(String personalId :personalIds){
					sql.append(comma+"'"+personalId+"'");
					comma=",";
				}
				sql.append(" )");
			}
			
			if(finalDecisions.size()>0){
				sql.append(" AND APP.FINAL_APP_DECISION  IN (");
				String comma="";
				for(String finalDecision :finalDecisions){
					sql.append(comma+"'"+finalDecision+"'");
					comma=",";
				}
				sql.append(" )");
			}
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
//				ps.setString(index++,NOTIFY_DOC_LIST_FIELD_ID);
				ps.setString(index++,applicationGroupId);
			rs = ps.executeQuery();
			StringBuilder cDesc = new StringBuilder();
			StringBuilder pDesc = new StringBuilder();
			StringBuilder cDescTh = new StringBuilder();
			StringBuilder cDescEn = new StringBuilder();
			
			String commaResult="";
			String comma="";
			while(rs.next()){
				String CARD_TYPE_TH_DESC = "";
				String CARD_TYPE_EN_DESC = "";
				pDesc.append(comma+rs.getString("PRODUCT_NAME"));
				//cardCode = rs.getString("CARD_CODE");
				//cDescTh.append(comma+rs.getString("TH_CARD_TYPE_DESC"));
				//cDescEn.append(comma+rs.getString("CARD_TYPE_DESC"));
				if (foundCard) {
					String cardType = rs.getString("CARD_TYPE");
					CARD_TYPE_TH_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_TH_DESC");
					CARD_TYPE_EN_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_EN_DESC");
					cardCode = CardInfoUtil.getFullCardDetail(cardType, "CARD_CODE");
					cDescTh.append(comma+CARD_TYPE_TH_DESC);
					cDescEn.append(comma+CARD_TYPE_EN_DESC);
					comma=",";
				}
				if(FormatUtil.EN.toUpperCase().equals(templateMaster.getLanguage().toUpperCase())){
					listCusName.add(rs.getString("CUSTOMER_NAME_SURNAME_EN"));
					//cDesc.append(commaResult+rs.getString("CARD_TYPE_DESC"));
					cDesc.append(commaResult+CARD_TYPE_EN_DESC);
					commaResult=",";
				}else{
					listCusName.add(rs.getString("CUSTOMER_NAME_SURNAME_TH"));
					//cDesc.append(commaResult+rs.getString("TH_CARD_TYPE_DESC"));
					cDesc.append(commaResult+CARD_TYPE_EN_DESC);
					commaResult=",";
				}
				
				personId = rs.getString("PERSONAL_ID");
				// KMOBILE
//				listCusNameEn.add(rs.getString("CUSTOMER_NAME_SURNAME_EN"));
//				listCusNameTh.add(rs.getString("CUSTOMER_NAME_SURNAME_TH"));
				personMapEn.put(rs.getString("PERSONAL_ID"), rs.getString("CUSTOMER_NAME_SURNAME_EN"));
				personMapTh.put(rs.getString("PERSONAL_ID"), rs.getString("CUSTOMER_NAME_SURNAME_TH"));
				listProductEn.add(rs.getString("PRODUCT_NAME_EN"));
				listProductTh.add(rs.getString("PRODUCT_NAME_TH"));
			}

			contactPoint = getContactPoint(templateMaster, finalDecisions.get(0), cardCode);
			String cardType = cDesc.toString();
			String productName = pDesc.toString();
			String cusName = listToString(listCusName);
			String cardTypeTh = cDescTh.toString();
			String cardTypeEn = cDescEn.toString();
			// KMOBILE
//			String customerNameEn = listToString(listCusNameEn);
//			String customerNameTh = listToString(listCusNameTh);
			String customerNameTh = mapToString(personMapTh);
			String customerNameEn = mapToString(personMapEn);
			String contactPointEn = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, FormatUtil.EN);
			String contactPointTh = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, FormatUtil.TH);
			String productNameTh = listToString(listProductTh);
			String productNameEn = listToString(listProductEn);
			
//			cardType = cardType.substring(0, cardType.length()-2);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE, cardType);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, cusName);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT,contactPoint);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_EN, contactPointEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_TH, contactPointTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,productName);
//			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST,Formatter.displayText(getDocumentListNotComplete(personId,templateMaster.getLanguage(),personalType)));
//			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST_TH,Formatter.displayText(getDocumentListNotComplete(personId,LANGUAGE_TH,personalType)));
//			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST_OTHER,Formatter.displayText(getDocumentListNotComplete(personId,LANGUAGE_OTHER,personalType)));
//			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST,Formatter.displayText(getDocumentListNotComplete(applicationGroupId,personId,templateMaster.getLanguage(),personalType)));
//			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST_TH,Formatter.displayText(getDocumentListNotComplete(applicationGroupId,personId,LANGUAGE_TH,personalType)));
//			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST_OTHER,Formatter.displayText(getDocumentListNotComplete(applicationGroupId,personId,LANGUAGE_OTHER,personalType)));
			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST,Formatter.displayText(getSMSDocumentListNotComplete(applicationGroupId,personId,templateMaster.getLanguage())));
			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST_TH,Formatter.displayText(getSMSDocumentListNotComplete(applicationGroupId,personId,LANGUAGE_TH)));
			map.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST_OTHER,Formatter.displayText(getSMSDocumentListNotComplete(applicationGroupId,personId,LANGUAGE_OTHER)));
			map.put(TemplateBuilderConstant.TemplateVariableName.EAPP_DOCUMENT_LIST,Formatter.displayText(getEAppSMSDocumentListNotComplete(applicationGroupId,personId,templateMaster.getLanguage())));
			map.put(TemplateBuilderConstant.TemplateVariableName.EAPP_DOCUMENT_LIST_TH,Formatter.displayText(getEAppSMSDocumentListNotComplete(applicationGroupId,personId,LANGUAGE_TH)));
			map.put(TemplateBuilderConstant.TemplateVariableName.EAPP_DOCUMENT_LIST_OTHER,Formatter.displayText(getEAppSMSDocumentListNotComplete(applicationGroupId,personId,LANGUAGE_OTHER)));
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_TH, cardTypeTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_EN, cardTypeEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH, productNameTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN, productNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_EN, customerNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_TH, customerNameTh);
			
			//CR370-371
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, productNameTh + " " + productNameEn);

//			logger.debug("HELLO JSON >> "+new Gson().toJson(map));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}
	
	private String getDocumentList(String applicationGroupId,String language,ArrayList<String> listPersonalType){
		StringBuilder documentList  = new StringBuilder("");
		try {
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			boolean sendToMain = false;
			String applicantDocList = null;
			String supplementaryDocList = null;
			if(!InfBatchUtil.empty(listPersonalType)){
				if(listPersonalType.contains(PERSONAL_TYPE_APPLICANT)){
					sendToMain = true;
				}
				if(sendToMain){
					applicantDocList = dao.getRejectDocumentList(applicationGroupId,PERSONAL_TYPE_APPLICANT);
				}else{
					supplementaryDocList = dao.getRejectDocumentList(applicationGroupId,PERSONAL_TYPE_SUPPLEMENTARY);
				}
				
						

				String MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_TH;
				String SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_TH;
				if(!RejectLetterUtil.TH.equals(language.toUpperCase())){
					MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_EN;
					SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_EN;
				}			
				if(!InfBatchUtil.empty(applicantDocList)){
					setDocumentList(documentList,applicantDocList,MAIN_CARD_TITLE);
				}
				if(!InfBatchUtil.empty(supplementaryDocList)){
					setDocumentList(documentList,supplementaryDocList,SUB_CARD_TITLE);
				}
				logger.debug("documentList.toString() >> "+documentList.toString());
			}
		} catch (Exception e) {
			logger.fatal("ERROR");
		}
		return documentList.toString();
	}
	@Deprecated
	private String getDocumentListNotComplete(String personalId,String language,ArrayList<String> listPersonalType){
		StringBuilder documentList  = new StringBuilder("");
		try {
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			boolean sendToMain = false;
			String applicantDocList = null;
			String supplementaryDocList = null;
			if(!InfBatchUtil.empty(listPersonalType)){
				if(listPersonalType.contains(PERSONAL_TYPE_APPLICANT)){
					sendToMain = true;
				}
				if(sendToMain){
					applicantDocList = dao.getRejectDocumentListNotComplete(personalId, PERSONAL_TYPE_APPLICANT);
				}else{
					applicantDocList = dao.getRejectDocumentListNotComplete(personalId, PERSONAL_TYPE_SUPPLEMENTARY);
				}
				String MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_TH;
				String SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_TH;
				if(!RejectLetterUtil.TH.equals(language.toUpperCase())){
					MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_EN;
					SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_EN;
				}			
				if(!InfBatchUtil.empty(applicantDocList)){
					setDocumentList(documentList,applicantDocList,MAIN_CARD_TITLE);
				}
				if(!InfBatchUtil.empty(supplementaryDocList)){
					setDocumentList(documentList,supplementaryDocList,SUB_CARD_TITLE);
				}
				logger.debug("documentList.toString() >> "+documentList.toString());
			}
		} catch (Exception e) {
			logger.fatal("ERROR");
		}
		return documentList.toString();
	}
	private String getDocumentListNotComplete(String applicationGroupId,String personalId,String language,ArrayList<String> listPersonalType)throws InfBatchException{
		StringBuilder documentList  = new StringBuilder("");
		try {
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			String MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_TH;
			String SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_TH;
			String cacheFieldId = "VALUE";
			if(!RejectLetterUtil.TH.equals(language.toUpperCase())){
				MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_EN;
				SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_EN;
				cacheFieldId = "EN_DESC";
			}
			if(!InfBatchUtil.empty(listPersonalType)){
				ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO()
						.loadApplicationGroupDocument(applicationGroupId, personalId);
				ArrayList<FollowDocHistoryDataM> followDocHistorys = findFollowDocHistorys(applicationGroup);
				if(null!=followDocHistorys){
					List<String> notCompleteDocs = new ArrayList<String>();
					List<String> existingDocumentCodes = new ArrayList<String>();
					for(FollowDocHistoryDataM followDocHistory : followDocHistorys){
						logger.debug("followDocHistory.DocumentCode : "+followDocHistory);
						//notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode()));
						if(!existingDocumentCodes.contains(followDocHistory.getDocumentCode())){
							existingDocumentCodes.add(followDocHistory.getDocumentCode());
							notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode(), cacheFieldId));
						}
					}
					logger.debug("notCompleteDocs : "+notCompleteDocs);
					if(!InfBatchUtil.empty(notCompleteDocs)){
						if(listPersonalType.contains(PERSONAL_TYPE_APPLICANT)){
							setDocumentList(documentList, notCompleteDocs, MAIN_CARD_TITLE);
						}else{
							setDocumentList(documentList, notCompleteDocs, SUB_CARD_TITLE);
						}
					}
				}
			}
			logger.debug("documentList.toString() >> "+documentList.toString());
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
		return documentList.toString();
	}
	private String getSMSDocumentListNotComplete(String applicationGroupId,String personalId,String language)throws InfBatchException{
		StringBuilder documentList  = new StringBuilder("");
		try {
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			String cacheFieldId = "VALUE";
			if(!RejectLetterUtil.TH.equals(language.toUpperCase())){
				cacheFieldId = "EN_DESC";
			}
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO()
					.loadApplicationGroupDocument(applicationGroupId, personalId);
			ArrayList<FollowDocHistoryDataM> followDocHistorys = findFollowDocHistorys(applicationGroup);
			if(null!=followDocHistorys){
				List<String> notCompleteDocs = new ArrayList<String>();
				List<String> existingDocumentCodes = new ArrayList<String>();
				for(FollowDocHistoryDataM followDocHistory : followDocHistorys){
					logger.debug("followDocHistory.DocumentCode : "+followDocHistory);
					if(!existingDocumentCodes.contains(followDocHistory.getDocumentCode())){
						existingDocumentCodes.add(followDocHistory.getDocumentCode());
						notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode(), cacheFieldId));
					}
				}
				logger.debug("notCompleteDocs : "+notCompleteDocs);
				if(!InfBatchUtil.empty(notCompleteDocs)){
					setDocumentList(documentList, notCompleteDocs);
					documentList.append("<br><br>");
				}
			}
			logger.debug("documentList.toString() >> "+documentList.toString());
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
		return documentList.toString();
	}
	private String getEAppSMSDocumentListNotComplete(String applicationGroupId,String personalId,String language)throws InfBatchException{
		StringBuilder documentList  = new StringBuilder("");
		try {
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			String cacheFieldId = "VALUE";
			if(!RejectLetterUtil.TH.equals(language.toUpperCase())){
				cacheFieldId = "EN_DESC";
			}
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO()
					.loadApplicationGroupDocument(applicationGroupId, personalId);
			ArrayList<FollowDocHistoryDataM> followDocHistorys = findFollowDocHistorys(applicationGroup);
			if(null!=followDocHistorys){
				List<String> notCompleteDocs = new ArrayList<String>();
				List<String> existingDocumentCodes = new ArrayList<String>();
				for(FollowDocHistoryDataM followDocHistory : followDocHistorys){
					logger.debug("followDocHistory.DocumentCode : "+followDocHistory);
					if(!existingDocumentCodes.contains(followDocHistory.getDocumentCode())){
						existingDocumentCodes.add(followDocHistory.getDocumentCode());
						notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode(), cacheFieldId));
					}
				}
				logger.debug("notCompleteDocs : "+notCompleteDocs);
				if(!InfBatchUtil.empty(notCompleteDocs)){
					setEAppDocumentList(documentList, notCompleteDocs);
				}
			}
			logger.debug("documentList.toString() >> "+documentList.toString());
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
		return documentList.toString();
	}
	private void setDocumentList(StringBuilder documentListAppend,String docName ,String title){
		try {
			String[] docList = docName.split("_");
			documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(10)+title);
			if(!InfBatchUtil.empty(docList)){
				for(String doc:docList){
					documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+doc);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	private void setDocumentList(StringBuilder documentListAppend,List<String> documents ,String title)throws InfBatchException{
		try {
//			documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(10)+title);
//			for(String document : documents){
//				documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+document);
//			}
			documentListAppend.append(title);
			for(String document : documents){
				if(!InfBatchUtil.empty(document)){
					documentListAppend.append("<br>  "+document);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
	}
	private void setDocumentList(StringBuilder documentListAppend,List<String> documents)throws InfBatchException{
		try {
			int seq = 1;
			for(String document : documents){
				if(!InfBatchUtil.empty(document)){
					documentListAppend.append("<br>  ");
					documentListAppend.append((seq++)+".");
					documentListAppend.append(document);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
	}
	private void setEAppDocumentList(StringBuilder documentListAppend,List<String> documents)throws InfBatchException{
		try {
			int seq = 1;
			int listSize = documents.size();
			for(String document : documents){
				if(!InfBatchUtil.empty(document)){
					documentListAppend.append((seq++)+". ");
					documentListAppend.append(document);
					if(seq-1 < listSize) {
						documentListAppend.append("\n");						
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
	}
	@Override
	public HashMap<String, Object> getSMSTemplateKECCashTranfer(ArrayList<RecipientInfoDataM> receipientInfos,
			TemplateMasterDataM templateMaster) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		String applicationGroupId ="";
		ArrayList<String> listCusName = new ArrayList<String>();
//		ArrayList<String> listCusNameEn = new ArrayList<String>();
//		ArrayList<String> listCusNameTh = new ArrayList<String>();
		ArrayList<String> listProductTh = new ArrayList<String>();
		ArrayList<String> listProductEn = new ArrayList<String>();
		ArrayList<String> finalDecisions = new  ArrayList<String>();
		ArrayList<String> personalIds = new ArrayList<String>();
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		HashMap<String,String> personMapEn = new HashMap<String,String>();
		HashMap<String,String> personMapTh = new HashMap<String,String>();
		String contactPoint="";
		String cardCode = "";
		DecimalFormat df = new DecimalFormat("#,###");
		String source = "";
		
		if(null!=receipientInfos){
			for(RecipientInfoDataM recipien : receipientInfos){
				if(!InfBatchUtil.empty(recipien.getFinalDecision()) && !finalDecisions.contains(recipien.getFinalDecision())){
					finalDecisions.add(recipien.getFinalDecision());
				}
				if(!InfBatchUtil.empty(recipien.getPersonalId()) && !personalIds.contains(recipien.getPersonalId())){
					personalIds.add(recipien.getPersonalId());
				}
				if(InfBatchUtil.empty(applicationGroupId)){
					applicationGroupId =recipien.getApplicationGroupId();
				}
				if(!InfBatchUtil.empty(recipien.getApplicationRecordId()) && !applicationRecordIds.contains(recipien.getApplicationRecordId())){
					applicationRecordIds.add(recipien.getApplicationRecordId());
				}
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			
			//sql.append(" SELECT DISTINCT LB.SYSTEM_ID4 CARD_TYPE_DESC, LB.SYSTEM_ID3 TH_CARD_TYPE_DESC,P.PERSONAL_ID, ");
			sql.append(" SELECT DISTINCT P.PERSONAL_ID,C.CARD_TYPE,AG.SOURCE, ");
			sql.append(" P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH,  ");
			sql.append(" P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN,  ");
			//sql.append("  CT.CARD_CODE,PKA_NOTIFICATION.GET_PRODUCT(AG.APPLICATION_GROUP_ID,'') AS PRODUCT_NAME, ");
			//sql.append("  CT.CARD_CODE,PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,APP.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME, ");
			sql.append("  PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,APP.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME, ");
			sql.append("  PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN , ");
			sql.append("  T.TRANSFER_ACCOUNT,L.LOAN_AMT,T.PERCENT_TRANSFER ");
			sql.append("  FROM ORIG_APPLICATION_GROUP AG  ");
			sql.append(" JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID  ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = APP.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"'  "); 
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
			sql.append(" JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID  ");
			sql.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
			//sql.append(" JOIN CARD_TYPE CT ON C.CARD_TYPE = CT.CARD_TYPE_ID ");
			//sql.append(" JOIN LIST_BOX_MASTER LB ON LB.CHOICE_NO = CT.CARD_CODE AND LB.FIELD_ID = ? ");
			sql.append(" JOIN ORIG_CASH_TRANSFER T ON T.LOAN_ID = L.LOAN_ID ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER PRODUCT_LIST ON  PRODUCT_LIST.SYSTEM_ID2 = APP.BUSINESS_CLASS_ID AND PRODUCT_LIST.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append(" WHERE APP.APPLICATION_GROUP_ID =?  ");
			sql.append(" AND T.CASH_TRANSFER_TYPE IN ('"+NOTIFICATION_CASH_1_HOUR+"','"+NOTIFICATION_CASH_DAY_1+"') ");
			sql.append(" AND APP.APPLICATION_RECORD_ID IN (");
			if(null!=applicationRecordIds){
				String commaApp="";
				for(String appRecordId : applicationRecordIds){
					sql.append(commaApp+"'"+appRecordId+"'");
					commaApp=",";
				}
			}
			sql.append(" )");
			
			if(personalIds.size()>0){
				sql.append(" AND P.PERSONAL_ID IN (");
				String comma="";
				for(String personalId :personalIds){
					sql.append(comma+"'"+personalId+"'");
					comma=",";
				}
				sql.append(" )");
			}
			
			if(finalDecisions.size()>0){
				sql.append(" AND APP.FINAL_APP_DECISION  IN (");
				String comma="";
				for(String finalDecision :finalDecisions){
					sql.append(comma+"'"+finalDecision+"'");
					comma=",";
				}
				sql.append(" )");
			}
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			int index = 1;
//				ps.setString(index++,NOTIFY_DOC_LIST_FIELD_ID);
				ps.setString(index++,applicationGroupId);
			rs = ps.executeQuery();
			StringBuilder cDesc = new StringBuilder();
			StringBuilder pDesc = new StringBuilder();
			StringBuilder cDescTh = new StringBuilder();
			StringBuilder cDescEn = new StringBuilder();
			BigDecimal transferAmount = BigDecimal.ZERO;
			String transferAccountNo = "";
			String transferAccountNoLast4digit = "";
			String loanAmount= "";
			String commaResult="";
			String comma="";
			while(rs.next()){
//				cardCode = rs.getString("CARD_CODE");
				String cardType = rs.getString("CARD_TYPE");
				String CARD_TYPE_TH_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_TH_DESC");
				String CARD_TYPE_EN_DESC = CardInfoUtil.getFullCardDetail(cardType, "CARD_TYPE_EN_DESC");
				cardCode = CardInfoUtil.getFullCardDetail(cardType, "CARD_CODE"); 
				pDesc.append(comma+rs.getString("PRODUCT_NAME"));
//				cDescTh.append(comma+rs.getString("TH_CARD_TYPE_DESC"));
//				cDescEn.append(comma+rs.getString("CARD_TYPE_DESC"));
				cDescTh.append(comma+CARD_TYPE_TH_DESC);
				cDescEn.append(comma+CARD_TYPE_EN_DESC);
				comma=",";
				
				BigDecimal LOAN_AMT = rs.getBigDecimal("LOAN_AMT");
				BigDecimal PERCENT_TRANSFER = rs.getBigDecimal("PERCENT_TRANSFER");
				
				if(null!=PERCENT_TRANSFER && null!=LOAN_AMT){
					transferAmount = (LOAN_AMT.multiply(PERCENT_TRANSFER)).divide(new BigDecimal(100));
				}
				transferAccountNo = rs.getString("TRANSFER_ACCOUNT");
				
				if (transferAccountNo.length() > 4) {
					transferAccountNoLast4digit = transferAccountNo.substring(transferAccountNo.length() - 4);
				}else{
					transferAccountNoLast4digit = transferAccountNo;
				}
				
				
				if(FormatUtil.EN.toUpperCase().equals(templateMaster.getLanguage().toUpperCase())){
					listCusName.add(rs.getString("CUSTOMER_NAME_SURNAME_EN"));
					//cDesc.append(commaResult+rs.getString("CARD_TYPE_DESC"));
					cDesc.append(commaResult+CARD_TYPE_EN_DESC);
					commaResult=",";
				}else{
					listCusName.add(rs.getString("CUSTOMER_NAME_SURNAME_TH"));
					//cDesc.append(commaResult+rs.getString("TH_CARD_TYPE_DESC"));
					cDesc.append(commaResult+CARD_TYPE_TH_DESC);
					commaResult=",";
				}
				
				if(!InfBatchUtil.empty(rs.getInt("LOAN_AMT"))){
		 			try{
		 				loanAmount = String.format("%,d",rs.getInt("LOAN_AMT"));
					}catch(Exception e){logger.debug("ERROR",e);}
		  		}
				
				
				// KMOBILE
//				listCusNameEn.add(rs.getString("CUSTOMER_NAME_SURNAME_EN"));
//				listCusNameTh.add(rs.getString("CUSTOMER_NAME_SURNAME_TH"));
				personMapEn.put(rs.getString("PERSONAL_ID"), rs.getString("CUSTOMER_NAME_SURNAME_EN"));
				personMapTh.put(rs.getString("PERSONAL_ID"), rs.getString("CUSTOMER_NAME_SURNAME_TH"));
				listProductEn.add(rs.getString("PRODUCT_NAME_EN"));
				listProductTh.add(rs.getString("PRODUCT_NAME_TH"));
				source = rs.getString("SOURCE");
			}
			
			contactPoint = getContactPoint(templateMaster, finalDecisions.get(0), cardCode);
			String contactPointEn = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, Formatter.EN);
			String contactPointTh = getContactPoint(templateMaster, finalDecisions.get(0), cardCode, Formatter.TH);
			String cardType = cDesc.toString();
			String productName = pDesc.toString();
			String cusName = listToString(listCusName);
//			String customerNameEn = listToString(listCusNameEn);
//			String customerNameTh = listToString(listCusNameTh);
			String customerNameTh = mapToString(personMapTh);
			String customerNameEn = mapToString(personMapEn);
			String cardTypeTh = cDescTh.toString();
			String cardTypeEn = cDescEn.toString();
			String productNameTh = listToString(listProductTh);
			String productNameEn = listToString(listProductEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE, cardType);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, cusName);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT,contactPoint);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_EN,contactPointEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_TH,contactPointTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,productName);
			map.put(TemplateBuilderConstant.TemplateVariableName.AMOUNT,df.format(transferAmount));
			map.put(TemplateBuilderConstant.TemplateVariableName.ACCOUNT_NO,transferAccountNo);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_TH, cardTypeTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CARD_TYPE_EN, cardTypeEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH, productNameTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN, productNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_EN, customerNameEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_TH, customerNameTh);
			map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LIMIT, loanAmount);
			map.put(TemplateBuilderConstant.TemplateVariableName.ACCOUNT_NO_LAST_4_DIGIT, transferAccountNoLast4digit);
			map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LINE, loanAmount);
			
			//For CR0347
			if(InfBatchUtil.eApp(source)){
				map.put(TemplateBuilderConstant.TemplateVariableName.MSG_CREDIT_LIMIT,InfBatchConstant.message.CREDIT_LIMIT+" "+loanAmount+" "+InfBatchConstant.message.BATH+" ");
				map.put(TemplateBuilderConstant.TemplateVariableName.MSG_CREDIT_LIMIT_CASH_TRANFER," "+InfBatchConstant.message.CASH_CREDIT_LIMIT+" "+loanAmount+" "+InfBatchConstant.message.BATH);
				map.put(TemplateBuilderConstant.TemplateVariableName.MSG_AMOUNT,InfBatchConstant.message.AMOUNT+" "+df.format(transferAmount)+" "+InfBatchConstant.message.BATH);
			}
			
			//CR370-371
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, productNameTh + " " + productNameEn);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}

	/**
	 * Method to select data from database and put into map for merge with message template
	 */
	@Override
	public HashMap<String, Object> getSMSTemplateKPLApprove(ArrayList<RecipientInfoDataM> receipientInfos,TemplateMasterDataM templateMaster) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		HashMap<String, Object> map =new HashMap<String, Object>();
		String  applicationGroupId="";
		int maxLifeCycle =1;
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		ArrayList<String> finalDecisions = new  ArrayList<String>();
		if(!InfBatchUtil.empty(receipientInfos)){
			for(RecipientInfoDataM recipientInfo : receipientInfos){
				applicationGroupId = recipientInfo.getApplicationGroupId();
				maxLifeCycle =recipientInfo.getMaxLifeCycle();
				if(!applicationRecordIds.contains(recipientInfo.getApplicationRecordId())){
					applicationRecordIds.add(recipientInfo.getApplicationRecordId());
				}
				if(!InfBatchUtil.empty(recipientInfo.getFinalDecision()) && !finalDecisions.contains(recipientInfo.getFinalDecision())){
					finalDecisions.add(recipientInfo.getFinalDecision());
				}
			}
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME, AG.APPLICATION_TYPE ");
			sql.append("		,PRODUCT_LIST.SYSTEM_ID3 AS PRODUCT_NAME_TH, PRODUCT_LIST.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
			sql.append(" 		,P.TH_FIRST_NAME ||' '||P.TH_LAST_NAME AS CUSTOMER_NAME_SURNAME_TH ");
			sql.append(" 		,P.EN_FIRST_NAME ||' '||P.EN_LAST_NAME AS CUSTOMER_NAME_SURNAME_EN ");
			//sql.append("		,L.LOAN_AMT - L.STAMP_DUTY AS TRANSFER_AMT, L.TERM, L.INSTALLMENT_AMT, L.INTEREST_RATE AS INT_RATE_AMOUNT , PMT.ACCOUNT_NO "); //Defect#2209
			sql.append("		,L.LOAN_AMT AS TRANSFER_AMT, L.TERM, L.INSTALLMENT_AMT, L.INTEREST_RATE AS INT_RATE_AMOUNT , PMT.ACCOUNT_NO ");
			sql.append(" FROM  ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_LOAN L ON A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID ");
			sql.append(" JOIN ORIG_LOAN_TIER T ON L.LOAN_ID = T.LOAN_ID ");
			sql.append(" JOIN ORIG_PAYMENT_METHOD PMT ON L.PAYMENT_METHOD_ID = PMT.PAYMENT_METHOD_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL = '"+PERSONAL_RELATION_LEVEL+"' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = PR.PERSONAL_ID  ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER PRODUCT_LIST ON PRODUCT_LIST.SYSTEM_ID2 = A.BUSINESS_CLASS_ID AND PRODUCT_LIST.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append(" WHERE  A.LIFE_CYCLE=?");
			sql.append(" AND A.APPLICATION_GROUP_ID=? AND AG.APPLICATION_TYPE=? ");
			if(applicationRecordIds.size()>0){
				sql.append(" AND  A.APPLICATION_RECORD_ID IN (");
				String comma="";
				for(String appRecordId  : applicationRecordIds){
					sql.append(comma+"'"+appRecordId+"'");
					comma=",";
				}
				sql.append(")");
			}
			if(finalDecisions.size()>0){
				sql.append(" AND A.FINAL_APP_DECISION  IN (");
				String comma="";
				for(String finalDecision :finalDecisions){
					sql.append(comma+"'"+finalDecision+"'");
					comma=",";
				}
				sql.append(" )");
			}
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());		
			ps.setInt(1,maxLifeCycle); 		
			ps.setString(2,applicationGroupId); 			
			ps.setString(3,APPICATION_TYPE_NEW); 
			rs = ps.executeQuery();	 
			if(rs.next()) {
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, rs.getString("PRODUCT_NAME"));
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH, rs.getString("PRODUCT_NAME_TH"));
				map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN, rs.getString("PRODUCT_NAME_EN"));
				map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_TH, rs.getString("CUSTOMER_NAME_SURNAME_TH"));
				map.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME_EN, rs.getString("CUSTOMER_NAME_SURNAME_EN"));
				map.put(TemplateBuilderConstant.TemplateVariableName.TRANSFER_AMT, rs.getString("TRANSFER_AMT"));
				map.put(TemplateBuilderConstant.TemplateVariableName.TERM, rs.getString("TERM"));
				map.put(TemplateBuilderConstant.TemplateVariableName.INSTALLMENT, rs.getString("INSTALLMENT_AMT"));
				map.put(TemplateBuilderConstant.TemplateVariableName.INTEREST, rs.getString("INT_RATE_AMOUNT"));
				String acct = rs.getString("ACCOUNT_NO");
				if (null == acct)
					acct = "";
				else if (acct.length() > 4)
					acct = acct.substring(acct.length()-4);
				map.put(TemplateBuilderConstant.TemplateVariableName.TRANSFER_TO_LAST4_DIGITS, acct);
				map.put(TemplateBuilderConstant.TemplateVariableName.ACCOUNT_NO_LAST_4_DIGIT, acct);
				map.put(TemplateBuilderConstant.TemplateVariableName.CREDIT_LINE, rs.getString("TRANSFER_AMT"));
			}
			String contactPoint = getContactPoint(templateMaster, finalDecisions.get(0), null);
			String contactPointEn = getContactPoint(templateMaster, finalDecisions.get(0), null, FormatUtil.EN);
			String contactPointTh = getContactPoint(templateMaster, finalDecisions.get(0), null, FormatUtil.TH);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT,contactPoint);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_EN, contactPointEn);
			map.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_POINT_TH, contactPointTh);
			//CR370-371
			map.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, rs.getString("PRODUCT_NAME_TH") + " " + rs.getString("PRODUCT_NAME_EN"));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return map;
	}

}
