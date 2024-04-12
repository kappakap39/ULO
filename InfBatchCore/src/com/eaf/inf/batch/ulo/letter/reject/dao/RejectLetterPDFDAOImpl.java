package com.eaf.inf.batch.ulo.letter.reject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFPersonalInfoDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.product.GenerateLetterUtil;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationConfig;
import com.eaf.inf.batch.ulo.notification.dao.MasterNotificationConfigDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.NotificationCriteriaDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;
import com.eaf.inf.batch.ulo.notification.model.Reason;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class RejectLetterPDFDAOImpl   extends InfBatchObjectDAO implements RejectLetterPDFDAO{
	private static transient Logger logger  = Logger.getLogger(RejectLetterPDFDAOImpl.class);
	String REJECT_LETTER_INTERFACE_STATUS_COMPLETE= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_COMPLETE");
	String REJECT_LETTER_INTERFACE_CODE= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE");
	String REJECT_LETTER_INTERFACE_CODE_T1= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T1");	
	String REJECT_LETTER_INTERFACE_CODE_T2= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T2");	
	String REJECT_LETTER_PDF_INTERFACE_CODE= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_INTERFACE_CODE");	
	String REJECT_LETTER_RELATION_LEVEL_APPLICATION= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_RELATION_LEVEL_APPLICATION");	
	String REJECT_LETTER_TITLE_NAME_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_TITLE_NAME_TH");	
	String NOTIFICATION_SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");	
	String NOTIFICATION_SEND_TO_TYPE_SELLER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String NOTIFICATION_SEND_TO_TYPE_RECOMMEND = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_RECOMMEND");
	String JOB_STATE_END= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_JOB_STATE_END");	
	String NOTIFICATION_APPLICATION_STATUS_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	String NOTIFICATION_SALE_TYPE_NORMAL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_NORMAL");
	String NOTIFICATION_SALE_TYPE_REFERENCE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_REFERENCE");
	String REJECT_LETTER_PDF_XRULES_VER_LEVEL_APPLICATION= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_XRULES_VER_LEVEL_APPLICATION");
	String REJECT_LETTER_PDF_NOFIFICATION_TYPE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_NOFIFICATION_TYPE");
	String REPLACE_PRODUCT_FIELD_ID=InfBatchProperty.getInfBatchConfig("NOTIFICATION_REPLACE_PRODUCT_FIELD_ID");
	String LANGUAGE_TH = InfBatchProperty.getInfBatchConfig("LANGUAGE_TH");
	String LANGUAGE_OTHER = InfBatchProperty.getInfBatchConfig("LANGUAGE_OTHER");
	String REJECT_LETTER_PDF_SEND_TIME = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SEND_TIME");
	String DEFUALT_DATA_TYPE_ALL = InfBatchProperty.getInfBatchConfig("DEFUALT_DATA_TYPE_ALL");
	String NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME");
	String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
	ArrayList<String> NEXTDAY_NOTIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_NOTIFICATION_TYPE_LIST");
	@Override
	public ArrayList<TaskObjectDataM> selectRejectLetterPDFInfo()throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TaskObjectDataM>  taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  A.APPLICATION_GROUP_NO , A.BRANCH_NAME, A.APPLICATION_GROUP_ID, "); 		
			sql.append(" A.LETTER_TYPE, A.LETTER_NO, A.LANGUAGE,A.APPLICATION_TEMPLATE, A.APPLICATION_TYPE,A.LIFE_CYCLE, "); 	
			sql.append(" LISTAGG(A.APPLICATION_RECORD_ID, ',') WITHIN GROUP (ORDER BY A.APPLICATION_RECORD_ID) AS APPLICATION_RECORD_ID");
			sql.append(" FROM (SELECT LOG.APPLICATION_RECORD_ID ,LOG.REF_ID AS LETTER_NO, LOG.SYSTEM01 AS  LETTER_TYPE, "); 
			sql.append(" LOG.SYSTEM02 AS LANGUAGE, AG.APPLICATION_GROUP_ID,AG.APPLICATION_GROUP_NO,AG.BRANCH_NAME ,AG.APPLICATION_TEMPLATE, AG.APPLICATION_TYPE, AG.LIFE_CYCLE ");
			sql.append(" FROM INF_BATCH_LOG LOG "); 
			sql.append(" INNER JOIN ORIG_APPLICATION AP ON AP.APPLICATION_RECORD_ID =LOG.APPLICATION_RECORD_ID "); 
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP AG ON AG.APPLICATION_GROUP_ID  = AP.APPLICATION_GROUP_ID AND AP.LIFE_CYCLE = AG.LIFE_CYCLE "); 
			sql.append(" WHERE LOG.INTERFACE_CODE  IN (?,?)  ");
			sql.append(" AND LOG.INTERFACE_STATUS =? AND LOG.SYSTEM01 =? ");
			sql.append(" AND LOG.SYSTEM03 = '"+NOTIFICATION_SEND_TO_TYPE_CUSTOMER+"' ");
			sql.append(" AND   NOT EXISTS  (SELECT 1");
			sql.append(" 	 FROM  INF_BATCH_LOG  WHERE INTERFACE_CODE = ? AND INTERFACE_STATUS =? AND APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID");
			sql.append(" 	  AND SYSTEM03='"+NOTIFICATION_SEND_TO_TYPE_CUSTOMER+"' ");
			sql.append("	  AND INF_BATCH_LOG.LIFE_CYCLE = AG.LIFE_CYCLE	) ");
			sql.append("  ) A GROUP BY ");
			sql.append(" A.APPLICATION_GROUP_NO , A.BRANCH_NAME, A.APPLICATION_GROUP_ID, "); 		
			sql.append(" A.LETTER_TYPE, A.LETTER_NO, A.LANGUAGE ,A.APPLICATION_TEMPLATE, A.APPLICATION_TYPE,A.LIFE_CYCLE "); 		
			
			logger.debug("sql>>"+sql.toString());
			logger.debug("REJECT_LETTER_INTERFACE_CODE_T1>>"+REJECT_LETTER_INTERFACE_CODE_T1);
			logger.debug("REJECT_LETTER_INTERFACE_CODE_T2>>"+REJECT_LETTER_INTERFACE_CODE_T2);
			logger.debug("REJECT_LETTER_INTERFACE_STATUS_COMPLETE>>"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
			logger.debug("REJECT_LETTER_PDF_INTERFACE_CODE>>"+REJECT_LETTER_PDF_INTERFACE_CODE);
			logger.debug("SYSTEM01>>"+RejectLetterUtil.EMAIL);
			
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++, REJECT_LETTER_INTERFACE_CODE_T1);
			ps.setString(index++, REJECT_LETTER_INTERFACE_CODE_T2);
			ps.setString(index++, REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
			ps.setString(index++, RejectLetterUtil.EMAIL);	
			ps.setString(index++, REJECT_LETTER_PDF_INTERFACE_CODE);
			ps.setString(index++, REJECT_LETTER_INTERFACE_STATUS_COMPLETE);			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String BRANCH_NAME = rs.getString("BRANCH_NAME");				
				String LETTER_TYPE = rs.getString("LETTER_TYPE");
				String LETTER_NO = rs.getString("LETTER_NO");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				String LANGUAGE = rs.getString("LANGUAGE");
				String APPLICATION_RECORD_IDS = rs.getString("APPLICATION_RECORD_ID");
				String APPLICATION_TYPE = rs.getString("APPLICATION_TYPE");
				Integer LIFE_CYCLE = rs.getInt("LIFE_CYCLE");
				ArrayList<String> applicationRecordIds = new ArrayList<String>();
				if(!InfBatchUtil.empty(APPLICATION_RECORD_IDS)){
					String[] appRecordIds = APPLICATION_RECORD_IDS.split(",");
					for(String appRecordId :appRecordIds){
						applicationRecordIds.add(appRecordId);
					}
				} 
				TaskObjectDataM taskObj = new TaskObjectDataM();
				RejectLetterPDFDataM rejectLetterPDFDataM = new RejectLetterPDFDataM();
				rejectLetterPDFDataM.setApplicationRecordIds(applicationRecordIds);
				rejectLetterPDFDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
				rejectLetterPDFDataM.setApplicationGroupNo(APPLICATION_GROUP_NO);
				rejectLetterPDFDataM.setBranchName(BRANCH_NAME);
				rejectLetterPDFDataM.setLetterNo(LETTER_NO);
				rejectLetterPDFDataM.setLetterType(LETTER_TYPE);
				rejectLetterPDFDataM.setLanguage(LANGUAGE);
				rejectLetterPDFDataM.setTemplateId(APPLICATION_TEMPLATE);
				rejectLetterPDFDataM.setSendTo(NOTIFICATION_SEND_TO_TYPE_CUSTOMER);
				rejectLetterPDFDataM.setApplicationType(APPLICATION_TYPE);
				rejectLetterPDFDataM.setLifeCycle(LIFE_CYCLE);
				taskObj.setObject(rejectLetterPDFDataM);
				taskObj.setUniqueId(LETTER_NO);
				taskObjects.add(taskObj);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return taskObjects;
	}
	
	@Override
	public ArrayList<TaskObjectDataM> selectSellerRejectLetterPDFInfo()throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TaskObjectDataM>  taskObjects = new ArrayList<TaskObjectDataM>();
		ArrayList<String> jobStateEnd = new ArrayList<>(Arrays.asList(JOB_STATE_END.split(",")));
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APP.APPLICATION_TEMPLATE,APP.SALES_ID,APP.SALE_CHANNEL , APP.APPLICATION_GROUP_ID,APP.APPLICATION_TYPE,");
			sql.append(" APP.REASON_CODE,APP.LETTER_NO, APP.LETTER_TYPE,APP.LANGUAGE, APPLICATION_GROUP_NO,BRANCH_NAME,D.SEND_TO,APP.LIFE_CYCLE, ");
			sql.append("  LISTAGG(APP.APPLICATION_RECORD_ID, ',') WITHIN GROUP (ORDER BY APP.APPLICATION_RECORD_ID) AS APPLICATION_RECORD_ID ");
            sql.append("  FROM (SELECT AG.APPLICATION_TEMPLATE,S_NORMAL.SALES_ID,S_NORMAL.SALE_CHANNEL , ");
            sql.append("		CASE WHEN S_NORMAL.SALES_TYPE = '01' THEN 'SELLER' WHEN S_NORMAL.SALES_TYPE = '02' THEN 'RECOMMEND' END AS SALE_TYPE_DESC,  ");
	        sql.append("		AG.APPLICATION_GROUP_ID,AG.APPLICATION_TYPE,  R.REASON_CODE ,LOG.REF_ID AS LETTER_NO, ");
	        sql.append("		LOG.SYSTEM01 AS  LETTER_TYPE,LOG.SYSTEM02 AS LANGUAGE, AG.APPLICATION_GROUP_NO,AG.BRANCH_NAME, APP.APPLICATION_RECORD_ID, AG.LIFE_CYCLE  ");
	        sql.append(" FROM  ORIG_APPLICATION_GROUP  AG  ");
	        sql.append(" INNER JOIN ORIG_APPLICATION APP ON APP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID AND AG.LIFE_CYCLE = APP.LIFE_CYCLE ");
	        sql.append(" INNER JOIN INF_BATCH_LOG LOG ON APP.APPLICATION_RECORD_ID =LOG.APPLICATION_RECORD_ID AND  LOG.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
	        sql.append("		  AND AG.LIFE_CYCLE = LOG.LIFE_CYCLE	");
	        sql.append("          AND LOG.INTERFACE_STATUS =?  AND   LOG.INTERFACE_CODE  IN (?,?) ");
		    sql.append(" INNER JOIN ORIG_SALE_INFO S_NORMAL ON AG.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID AND S_NORMAL.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"'  ");
//	        sql.append(" INNER JOIN ORIG_SALE_INFO S_NORMAL ON AG.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID  ");
//		    sql.append(" LEFT JOIN (SELECT APPLICATION_RECORD_ID,APPLICATION_GROUP_ID, PKA_NOTIFICATION.GET_MIN_RANK_REASON_CODE(APPLICATION_GROUP_ID,APPLICATION_RECORD_ID)AS REASON_CODE FROM  ORIG_APPLICATION) R  ");
//		    sql.append(" ON R.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID AND R.APPLICATION_RECORD_ID=APP.APPLICATION_RECORD_ID  ");
		    sql.append(" LEFT JOIN ( SELECT ORIG_APPLICATION.APPLICATION_RECORD_ID, ORIG_APPLICATION.APPLICATION_GROUP_ID, ");
		    sql.append("				PKA_REJECT_LETTER.GET_PERSONAL_MIN_RANK_REASON(ORIG_APPLICATION.APPLICATION_GROUP_ID, PS.PERSONAL_ID, ORIG_APPLICATION.APPLICATION_RECORD_ID)AS REASON_CODE  ");
		    sql.append(" 			FROM ORIG_APPLICATION ");
		    sql.append(" 			INNER JOIN  ORIG_PERSONAL_RELATION R  ON  ORIG_APPLICATION.APPLICATION_RECORD_ID = R.REF_ID AND R.RELATION_LEVEL= '"+REJECT_LETTER_PDF_XRULES_VER_LEVEL_APPLICATION+"' ");
		    sql.append(" 			INNER JOIN ORIG_PERSONAL_INFO PS ON  R.PERSONAL_ID = PS.PERSONAL_ID ");
		    sql.append(" 		) R ON R.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID AND R.APPLICATION_RECORD_ID=APP.APPLICATION_RECORD_ID ");			 
		    sql.append("  WHERE  AG.JOB_STATE IN ( ");
			String COMMA="";
			for(String jobState :jobStateEnd){
				sql.append(COMMA+"?");
				COMMA=",";
			}		    
		    sql.append(" ) ");
		    
		    sql.append(" AND NOT EXISTS  (SELECT 1");
			sql.append(" 	 FROM  INF_BATCH_LOG  WHERE INTERFACE_CODE = ? AND INTERFACE_STATUS =? AND APPLICATION_RECORD_ID = APP.APPLICATION_RECORD_ID");
			sql.append(" 	  AND SYSTEM03='"+NOTIFICATION_SEND_TO_TYPE_SELLER+"' AND LIFE_CYCLE = AG.LIFE_CYCLE )"); 	
		       
		    sql.append(" ) APP  ");
		    sql.append(" INNER JOIN MS_NOTI_APP_TEMPLATE T ON T.TEMPLATE_CODE = APP.APPLICATION_TEMPLATE OR T.TEMPLATE_CODE = 'ALL'  ");
		    sql.append(" INNER JOIN MS_NOTI_GROUP  G ON G.GROUP_CODE =T.GROUP_CODE  ");
		    sql.append(" INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE ");
		    sql.append(" INNER JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE ");
		    sql.append(" INNER JOIN MS_NOTI_SENDING_DETAIL D ON  S.SENDING_ID = D.SENDING_ID AND D.SEND_TO = APP.SALE_TYPE_DESC ");
		    sql.append(" INNER JOIN  MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE AND APP.REASON_CODE=RE.REASON_CODE ");
		    sql.append("  WHERE G.HAVE_SALE_FLAG ='Y'  AND (C.CHANNEL_CODE = APP.SALE_CHANNEL OR C.CHANNEL_CODE = 'ALL' OR (C.CHANNEL_CODE = 'N' AND APP.SALE_CHANNEL IS NULL ))   ");   
		    //sql.append("  AND  G.APPLICATION_STATUS ='"+NOTIFICATION_APPLICATION_STATUS_REJECT+"'	 AND S.NOTIFICATION_TYPE IN ('"+TemplateBuilderConstant.TemplateType.EMAIL+"') ");
		    sql.append("  AND  G.APPLICATION_STATUS ='"+NOTIFICATION_APPLICATION_STATUS_REJECT+"'	 AND S.NOTIFICATION_TYPE IN ("+InfBatchProperty.getSQLParameter("REJECT_LETTER_PDF_NOFIFICATION_TYPE")+") ");
		    //sql.append("  AND D.SEND_TO ='"+NOTIFICATION_SEND_TO_TYPE_SELLER+"' AND S.SEND_TIME = 'EOD' ");		
		    sql.append("  AND D.SEND_TO IN ('"+NOTIFICATION_SEND_TO_TYPE_SELLER+"', '"+NOTIFICATION_SEND_TO_TYPE_RECOMMEND+"') AND S.SEND_TIME = 'EOD' ");
		    
			sql.append("  GROUP BY  APP.APPLICATION_TEMPLATE,APP.SALES_ID,APP.SALE_CHANNEL , APP.APPLICATION_GROUP_ID,APP.APPLICATION_TYPE,APP.REASON_CODE, ");
			sql.append("  APP.LETTER_NO, APP.LETTER_TYPE,APP.APPLICATION_GROUP_NO,APP.BRANCH_NAME,APP.LANGUAGE,D.SEND_TO,APP.LIFE_CYCLE ");
			
			logger.debug("sql>>"+sql.toString());
			logger.debug("REJECT_LETTER_INTERFACE_CODE_T1>>"+REJECT_LETTER_INTERFACE_CODE_T1);
			logger.debug("REJECT_LETTER_INTERFACE_CODE_T2>>"+REJECT_LETTER_INTERFACE_CODE_T2);
			logger.debug("REJECT_LETTER_INTERFACE_STATUS_COMPLETE>>"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
			logger.debug("REJECT_LETTER_PDF_INTERFACE_CODE>>"+REJECT_LETTER_PDF_INTERFACE_CODE);
			logger.debug("SYSTEM01>>"+RejectLetterUtil.EMAIL);
			logger.debug("JOB_STATE_END>>"+JOB_STATE_END);
			
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++, REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
//			ps.setString(index++, RejectLetterUtil.EMAIL);
			ps.setString(index++, REJECT_LETTER_INTERFACE_CODE_T1);
			ps.setString(index++, REJECT_LETTER_INTERFACE_CODE_T2);
			for(String jobState :jobStateEnd){
				ps.setString(index++, jobState);
			}	
			ps.setString(index++, REJECT_LETTER_PDF_INTERFACE_CODE);
			ps.setString(index++, REJECT_LETTER_INTERFACE_STATUS_COMPLETE);			
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String LANGUAGE = rs.getString("LANGUAGE");
				if(InfBatchUtil.empty(LANGUAGE)){
					LANGUAGE = RejectLetterUtil.TH;
				}
				String BRANCH_NAME = rs.getString("BRANCH_NAME");				
				//String LETTER_TYPE = rs.getString("LETTER_TYPE");
				String LETTER_NO = rs.getString("LETTER_NO");
				String SALES_ID = rs.getString("SALES_ID");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				String APPLICATION_RECORD_IDS = rs.getString("APPLICATION_RECORD_ID");
				String APPLICATION_TYPE = rs.getString("APPLICATION_TYPE");
				String REASON_CODE = rs.getString("REASON_CODE");
				String SEND_TO = rs.getString("SEND_TO");
				Integer LIFE_CYCLE = rs.getInt("LIFE_CYCLE");
				ArrayList<String> applicationRecordIds = new ArrayList<String>();
				if(!InfBatchUtil.empty(APPLICATION_RECORD_IDS)){
					String[] appRecordIds = APPLICATION_RECORD_IDS.split(",");
					for(String appRecordId :appRecordIds){
						applicationRecordIds.add(appRecordId);
					}
				} 
				if(!InfBatchUtil.empty(SALES_ID)){
					TaskObjectDataM taskObj = new TaskObjectDataM();
					RejectLetterPDFDataM rejectLetterPDFDataM = new RejectLetterPDFDataM();
					rejectLetterPDFDataM.setApplicationRecordIds(applicationRecordIds);
					rejectLetterPDFDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
					rejectLetterPDFDataM.setApplicationGroupNo(APPLICATION_GROUP_NO);
					rejectLetterPDFDataM.setBranchName(BRANCH_NAME);
					rejectLetterPDFDataM.setLetterNo(LETTER_NO);
					rejectLetterPDFDataM.setLetterType(RejectLetterUtil.EMAIL); //rejectLetterPDFDataM.setLetterType(LETTER_TYPE);
					rejectLetterPDFDataM.setTemplateId(APPLICATION_TEMPLATE);
					//rejectLetterPDFDataM.setSendTo(NOTIFICATION_SEND_TO_TYPE_SELLER);
					rejectLetterPDFDataM.setSendTo(SEND_TO);
					rejectLetterPDFDataM.setLanguage(LANGUAGE);
					rejectLetterPDFDataM.setSaleId(SALES_ID);
					rejectLetterPDFDataM.setApplicationType(APPLICATION_TYPE);
					rejectLetterPDFDataM.setReasonCode(REASON_CODE);
					rejectLetterPDFDataM.setLifeCycle(LIFE_CYCLE);
					taskObj.setObject(rejectLetterPDFDataM);
					taskObj.setUniqueId(LETTER_NO);
					taskObjects.add(taskObj);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return taskObjects;
	}
	
	
	@Override
	public ArrayList<RejectTemplateVariableDataM> getRejectPDFTemplate(RejectLetterPDFDataM rejectTemplateDataM,RejectLetterPDFPersonalInfoDataM rejectPdfPersonal) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		 ArrayList<RejectTemplateVariableDataM> rejectTemplateVariables = new ArrayList<RejectTemplateVariableDataM>();
		 ArrayList<String> applicationRecordIds = rejectTemplateDataM.getApplicationRecordIds();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TO_CHAR(AP.FINAL_APP_DECISION_DATE,'YYYYMMDD') AS FINAL_APP_DECISION_DATE, ");
			sql.append(" SUBSTR(AP.BUSINESS_CLASS_ID,1,INSTR(AP.BUSINESS_CLASS_ID,'_')-1) AS  PRODUCT_TYPE, AP.APPLICATION_NO, AG.APPLICATION_GROUP_NO, ");
//			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT(AG.APPLICATION_GROUP_ID,AP.APPLICATION_RECORD_ID,?) AS PRODUCT_NAME ");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT_TH_EN(AG.APPLICATION_GROUP_ID,AP.APPLICATION_RECORD_ID) AS PRODUCT_NAME_TH_EN, ");
			sql.append(" DECODE(?,'"+LANGUAGE_TH+"',L.SYSTEM_ID3,L.SYSTEM_ID4)AS PRODUCT_NAME , L.SYSTEM_ID3 AS PRODUCT_NAME_TH, L.SYSTEM_ID4 AS PRODUCT_NAME_EN ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG");
			sql.append(" LEFT JOIN ORIG_APPLICATION AP ON AG.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID");
			sql.append(" LEFT JOIN LIST_BOX_MASTER L ON  L.SYSTEM_ID2 = AP.BUSINESS_CLASS_ID AND L.FIELD_ID = '"+REPLACE_PRODUCT_FIELD_ID+"' ");
			sql.append(" WHERE AG.APPLICATION_GROUP_ID = ? AND AP.APPLICATION_RECORD_ID IN (");
			String COMMA ="";
			for(String recordId :applicationRecordIds){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			logger.debug("sql>>"+sql.toString());	
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++, rejectPdfPersonal.getNationality());
			ps.setString(index++, rejectTemplateDataM.getApplicationGroupId());
			logger.debug("Nationality : "+rejectPdfPersonal.getNationality());
			logger.debug("applicationGroupId : "+rejectPdfPersonal.getApplicationGroupId());
			for(String recordId :applicationRecordIds){
				logger.debug("recordId>>"+recordId);	
				ps.setString(index++, recordId);
			}	
			rs = ps.executeQuery();
			 
			String CUSTOMER_NAME_TH = REJECT_LETTER_TITLE_NAME_TH+" "+rejectPdfPersonal.getThFirshName()+" "+rejectPdfPersonal.getThLastName();
			String CUSTOMER_NAME_EN = rejectPdfPersonal.getEnTitleNameDesc()+" "+rejectPdfPersonal.getEnFirshName()+" "+rejectPdfPersonal.getEnLastName();
			String SELLER_CUSTOMER_NAME_TH = REJECT_LETTER_TITLE_NAME_TH+" "+rejectPdfPersonal.getThFirshName()+" "+rejectPdfPersonal.getThLastName();
			while(rs.next()) {
				String FINAL_APP_DECISION_DATE = rs.getString("FINAL_APP_DECISION_DATE");
				String PRODUCT_TYPE = rs.getString("PRODUCT_TYPE");
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String PRODUCT_NAME_TH = rs.getString("PRODUCT_NAME_TH");
				String PRODUCT_NAME_EN = rs.getString("PRODUCT_NAME_EN");
				String PRODUCT_NAME_TH_EN = rs.getString("PRODUCT_NAME_TH_EN");
				String APPLICATION_NO = rs.getString("APPLICATION_NO");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				RejectTemplateVariableDataM rejectTemplateVariable = new RejectTemplateVariableDataM();
				rejectTemplateVariable.setIdNo(rejectPdfPersonal.getIdNo());
				rejectTemplateVariable.setPersonalNameTh(CUSTOMER_NAME_TH);
				rejectTemplateVariable.setPersonalNameEn(CUSTOMER_NAME_EN);
				rejectTemplateVariable.setSellerCustomerNameTh(SELLER_CUSTOMER_NAME_TH);
				rejectTemplateVariable.setEmailPrimary(rejectPdfPersonal.getEmail());
				rejectTemplateVariable.setPersonalType(rejectPdfPersonal.getPersonalType());
				rejectTemplateVariable.setProductType(PRODUCT_TYPE);
				rejectTemplateVariable.setFinalDecisionDate(FINAL_APP_DECISION_DATE);
				rejectTemplateVariable.setProductName(PRODUCT_NAME);
				rejectTemplateVariable.setProductNameTh(PRODUCT_NAME_TH);
				rejectTemplateVariable.setProductNameEn(PRODUCT_NAME_EN);
				rejectTemplateVariable.setProductNameThEn(PRODUCT_NAME_TH_EN);
				rejectTemplateVariable.setBusinessClassId(rejectPdfPersonal.getBusinessClassId());
				rejectTemplateVariable.setNationality(rejectPdfPersonal.getNationality());
				rejectTemplateVariable.setApplicationNo(APPLICATION_NO);
				rejectTemplateVariable.setApplicationGroupNo(APPLICATION_GROUP_NO);
				rejectTemplateVariables.add(rejectTemplateVariable);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return rejectTemplateVariables;
	}
	@Override
	public ArrayList<RejectLetterPDFPersonalInfoDataM> selectRejectPDFTemplateDatas(RejectLetterPDFDataM rejectTemplateDataM) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos= new ArrayList<RejectLetterPDFPersonalInfoDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" ,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			sql.append(" ,AG.APPLICATION_GROUP_ID , P.PERSONAL_ID, C.CARD_TYPE, C.CARD_LEVEL,P.EN_FIRST_NAME,P.EN_LAST_NAME,P.EN_TITLE_DESC");
			sql.append(" ,P.TH_FIRST_NAME, P.TH_LAST_NAME, P.IDNO ");
			sql.append(" FROM ORIG_PERSONAL_INFO P");
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP  AG ON  AG.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID ");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION  R ON  R.PERSONAL_ID = P.PERSONAL_ID AND R.RELATION_LEVEL='A'");
			sql.append(" INNER JOIN ORIG_APPLICATION  A ON  R.REF_ID = A.APPLICATION_RECORD_ID");
			sql.append(" INNER JOIN ORIG_LOAN  L ON  L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID");
			sql.append(" LEFT JOIN ORIG_CARD  C ON L.LOAN_ID = C.LOAN_ID");
			sql.append(" WHERE AG.APPLICATION_GROUP_ID =?");			
			sql.append("  AND A.APPLICATION_RECORD_ID IN(");
			String COMMA="";
			for(String appRecordId :rejectTemplateDataM.getApplicationRecordIds() ){
				sql.append(COMMA+"'"+appRecordId+"'");
				COMMA=",";
			}
			sql.append("  )");
			logger.debug("sql : " + sql);
			
			ps = conn.prepareStatement(sql.toString());				 			
			ps.setString(1,rejectTemplateDataM.getApplicationGroupId()); 				
			rs = ps.executeQuery();
			while(rs.next()) {			
				RejectLetterPDFPersonalInfoDataM  pdfPersonalInfo = new  RejectLetterPDFPersonalInfoDataM();
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String NATIONALITY = rs.getString("NATIONALITY");
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String CARD_TYPE = rs.getString("CARD_TYPE");
				String CARD_LEVEL = rs.getString("CARD_LEVEL");
				String EN_FIRST_NAME = rs.getString("EN_FIRST_NAME");
				String EN_LAST_NAME = rs.getString("EN_LAST_NAME");
				String EN_TITLE_DESC = rs.getString("EN_TITLE_DESC");
				String IDNO = rs.getString("IDNO");
				String TH_FIRST_NAME = rs.getString("TH_FIRST_NAME");
				String TH_LAST_NAME = rs.getString("TH_LAST_NAME");
				
				pdfPersonalInfo.setApplicationGroupId(APPLICATION_GROUP_ID);
				pdfPersonalInfo.setApplicationRecordId(APPLICATION_RECORD_ID);
				pdfPersonalInfo.setBusinessClassId(BUSINESS_CLASS_ID);
				pdfPersonalInfo.setCardLevel(CARD_LEVEL);
				pdfPersonalInfo.setCardType(CARD_TYPE);
				pdfPersonalInfo.setEmail(EMAIL_PRIMARY);
				pdfPersonalInfo.setEnFirshName(EN_FIRST_NAME);
				pdfPersonalInfo.setEnLastName(EN_LAST_NAME);
				pdfPersonalInfo.setEnTitleNameDesc(EN_TITLE_DESC);
				pdfPersonalInfo.setFinalDecision(FINAL_APP_DECISION);
				pdfPersonalInfo.setIdNo(IDNO);
				pdfPersonalInfo.setNationality(NATIONALITY);
				pdfPersonalInfo.setPersonalId(PERSONAL_ID);
				pdfPersonalInfo.setPersonalType(PERSONAL_TYPE);
				pdfPersonalInfo.setPolicyProgramId(PROLICY_PROGRAM_ID);
				pdfPersonalInfo.setThFirshName(TH_FIRST_NAME);
				pdfPersonalInfo.setThLastName(TH_LAST_NAME);
				pdfPersonalInfos.add(pdfPersonalInfo);
				
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		 return pdfPersonalInfos;
	}
	@Override
	public HashMap<String, String>  getContactCallCenterNoProduct(ArrayList<String> products,String language)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> hContactCallCenter = new HashMap<String, String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT");		
			String COMMA ="";
			if(!InfBatchUtil.empty(products)){
				for(String product : products){
					sql.append(COMMA+" (SELECT PARAM_VALUE FROM GENERAL_PARAM WHERE PARAM_CODE ='CONTACT_CENTER_NO_"+product+"_"+language+"') AS CALL_CENTER_"+product);	
					COMMA=",";
				}
				sql.append(" FROM DUAL  ");
			}
			 
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());				
			rs = ps.executeQuery();
			if(rs.next()) {
				if(!InfBatchUtil.empty(products)){
					for(String product : products){
						hContactCallCenter.put(product, rs.getString("CALL_CENTER_"+product));
					}
				}else{
					hContactCallCenter.put("CC", rs.getString("CALL_CENTER_CC"));
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return hContactCallCenter;
	}
	@Override
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn)throws InfBatchException{
		try {
//			int returnRow = this.updateTableInfBatchLog(infBatchLog, conn);
//			if(0==returnRow){
//				this.insertTableInfBatchLog(infBatchLog, conn);
//			}
			this.insertTableInfBatchLog(infBatchLog, conn);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}	
	}
	private int updateTableInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		int returnRows = 0;
		try {
			StringBuilder sql = new StringBuilder("");			
//				sql.append("UPDATE INF_BATCH_LOG ");
//				sql.append(" SET INTERFACE_DATE=SYSDATE, ");
//				sql.append(" CREATE_DATE =SYSDATE, ");
//				sql.append(" CREATE_BY=?, ");
//				sql.append(" INTERFACE_STATUS=?, ");
//				sql.append(" REF_ID=?, ");
//				sql.append(" REF_SEQ=?,");
//				sql.append(" LIFE_CYCLE=? ");
//				sql.append(" WHERE APPLICATION_RECORD_ID=? AND INTERFACE_CODE=? ");
//				sql.append(" AND SYSTEM01=? AND SYSTEM02=? AND SYSTEM03=? ");
				sql.append("UPDATE INF_BATCH_LOG ");
				sql.append(" SET INTERFACE_DATE=?, ");
				sql.append(" CREATE_DATE =?, ");
				sql.append(" CREATE_BY=?, ");
				sql.append(" INTERFACE_STATUS=?, ");
				sql.append(" REF_ID=?, ");
				sql.append(" REF_SEQ=?,");
				sql.append(" LIFE_CYCLE=? ");
				sql.append(" WHERE APPLICATION_RECORD_ID=? AND INTERFACE_CODE=? ");
				sql.append(" AND SYSTEM01=? AND SYSTEM02=? AND SYSTEM03=? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int index = 1;
			ps.setTimestamp(index++,infBatchLog.getInterfaceDate());
			ps.setTimestamp(index++,infBatchLog.getInterfaceDate());
			ps.setString(index++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(index++,infBatchLog.getInterfaceStatus());
			ps.setString(index++,infBatchLog.getRefId());
			ps.setString(index++,infBatchLog.getRefSeq());
			ps.setInt(index++,infBatchLog.getLifeCycle());
			
			ps.setString(index++,infBatchLog.getApplicationRecordId());
			ps.setString(index++,infBatchLog.getInterfaceCode());
			ps.setString(index++,infBatchLog.getSystem01());
			ps.setString(index++,infBatchLog.getSystem02());
			ps.setString(index++,infBatchLog.getSystem03());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				if (ps != null){
					try{
						ps.close();
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new InfBatchException(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	public void insertTableInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder("");			
			sql.append("INSERT INTO INF_BATCH_LOG ");
			sql.append(" (INTERFACE_LOG_ID, ");
			sql.append(" APPLICATION_RECORD_ID, ");
			sql.append(" INTERFACE_CODE, ");
			sql.append(" INTERFACE_DATE, ");
			sql.append(" CREATE_DATE, ");
			sql.append(" CREATE_BY, ");
			sql.append(" INTERFACE_STATUS, ");
			sql.append(" REF_ID, ");
			sql.append(" REF_SEQ, ");
			sql.append(" SYSTEM01, ");
			sql.append(" SYSTEM02, ");
			sql.append(" SYSTEM03, ");
			sql.append(" SYSTEM04, ");
			sql.append(" SYSTEM05, ");
			sql.append(" SYSTEM06, ");
			sql.append(" SYSTEM07, ");
			sql.append(" SYSTEM08, ");
			sql.append(" SYSTEM09, ");
			sql.append(" SYSTEM10, ");
			sql.append(" APPLICATION_GROUP_ID, ");
			sql.append(" LIFE_CYCLE, ");
			sql.append(" LOG_MESSAGE ) ");
			sql.append(" SELECT INF_BATCH_LOG_PK.NEXTVAL, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" SYSDATE, ");
			sql.append(" SYSDATE, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ? ");
			sql.append(" FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int index = 1;
			ps.setString(index++,infBatchLog.getApplicationRecordId());
			ps.setString(index++,infBatchLog.getInterfaceCode());
			ps.setString(index++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(index++,infBatchLog.getInterfaceStatus());
			ps.setString(index++,infBatchLog.getRefId());
			ps.setString(index++,infBatchLog.getRefSeq());
			ps.setString(index++,infBatchLog.getSystem01());
			ps.setString(index++,infBatchLog.getSystem02());
			ps.setString(index++,infBatchLog.getSystem03());
			ps.setString(index++,infBatchLog.getSystem04());
			ps.setString(index++,infBatchLog.getSystem05());
			ps.setString(index++,infBatchLog.getSystem06());
			ps.setString(index++,infBatchLog.getSystem07());
			ps.setString(index++,infBatchLog.getSystem08());
			ps.setString(index++,infBatchLog.getSystem09());
			ps.setString(index++,infBatchLog.getSystem10());
			ps.setString(index++,infBatchLog.getApplicationGroupId());
			ps.setInt(index++, infBatchLog.getLifeCycle());
			ps.setString(index++,infBatchLog.getLogMessage());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				if (ps != null){
					try{
						ps.close();
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new InfBatchException(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	private void insertInfBatchLog(ArrayList<InfBatchLogDataM> infBatchLogs)throws InfBatchException{
	   	 Connection conn = getConnection();	
	   	 try {
	   		
	   		 conn.setAutoCommit(false);
	   		 if(!InfBatchUtil.empty(infBatchLogs)){
	   			 for(InfBatchLogDataM infBatchLog : infBatchLogs){
	   				insertInfBatchLog(infBatchLog, conn);
	   			 }
	   		 }
	   		 conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			try {
				conn.rollback();
			}catch (Exception e1){
				logger.fatal("ERROR",e1);
				throw new InfBatchException(e.getLocalizedMessage());
			}			
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public RejectLetterPDFRequestDataM loadRejectLetterPDFRequest()throws InfBatchException{
		RejectLetterPDFRequestDataM rejectLetterPdfRequest = new RejectLetterPDFRequestDataM();
		HashMap<String,String> priorityMap = new HashMap<String,String>();
		Connection conn = null;
		try{
			conn = getConnection();
			ArrayList<RejectLetterPDFDataM> rejectLetterPDFs = new ArrayList<RejectLetterPDFDataM>();
			ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>();
				ArrayList<RejectLetterPDFDataM> customerPDFs = findRejectLetterPDFCustomer(infBatchLogs);
				if(!InfBatchUtil.empty(customerPDFs)){
					rejectLetterPDFs.addAll(customerPDFs);
				}
				ArrayList<RejectLetterPDFDataM> sellerPDFs = findRejectLetterPDFSeller(infBatchLogs);
				if(!InfBatchUtil.empty(sellerPDFs)){
					rejectLetterPDFs.addAll(sellerPDFs);
				}
			removeDuplicateLog(infBatchLogs,rejectLetterPDFs);
			if(!InfBatchUtil.empty(infBatchLogs)){
				insertInfBatchLog(infBatchLogs);
			}
			for(RejectLetterPDFDataM rejectLetterPDF : rejectLetterPDFs){
				createPriorityMap(rejectLetterPDF,priorityMap,conn);
			}
			rejectLetterPdfRequest.setRejectLetterPDFs(rejectLetterPDFs);
			rejectLetterPdfRequest.setPriorityMap(priorityMap);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return rejectLetterPdfRequest;
	}
	private ArrayList<RejectLetterPDFDataM> findRejectLetterPDFCustomer(ArrayList<InfBatchLogDataM> infBatchLogs)throws InfBatchException{
		String REJECT_LETTER_PDF_FUNCTION_TYPE_SUBMIT_MAKEPDF = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_FUNCTION_TYPE_SUBMIT_MAKEPDF");
		ArrayList<String> REJECT_LETTER_PDF_NOFIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_PDF_NOFIFICATION_TYPE");
		ArrayList<String> SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RejectLetterPDFDataM> rejectLetterPDFs = new ArrayList<RejectLetterPDFDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
				sql.append(" SELECT ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID ");
				sql.append("         , ORIG_APPLICATION_GROUP.BRANCH_NAME ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TEMPLATE ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TYPE ");
				sql.append("         , ORIG_APPLICATION_GROUP.LIFE_CYCLE ");
				sql.append("         , INF_BATCH_LOG.REF_ID AS LETTER_NO ");
				sql.append("         , INF_BATCH_LOG.SYSTEM02 AS LANGUAGE ");
				sql.append("         , ORIG_SALE_INFO.SALES_ID ");
				sql.append("		 , ORIG_SALE_INFO.SALE_CHANNEL ");
				sql.append("         , LISTAGG(ORIG_APPLICATION.APPLICATION_RECORD_ID, ',') WITHIN GROUP (ORDER BY ORIG_APPLICATION.APPLICATION_RECORD_ID) AS APPLICATION_RECORD_IDS ");
				sql.append(" FROM ORIG_APPLICATION_GROUP ");
				sql.append(" INNER JOIN ORIG_APPLICATION ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_APPLICATION.APPLICATION_GROUP_ID AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ORIG_APPLICATION.LIFE_CYCLE ");
				sql.append(" INNER JOIN INF_BATCH_LOG ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = INF_BATCH_LOG.APPLICATION_GROUP_ID AND ORIG_APPLICATION.APPLICATION_RECORD_ID = INF_BATCH_LOG.APPLICATION_RECORD_ID ");
				sql.append("         AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = INF_BATCH_LOG.LIFE_CYCLE  ");
				sql.append("         AND INF_BATCH_LOG.INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' AND INF_BATCH_LOG.INTERFACE_STATUS = '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"' AND INF_BATCH_LOG.SYSTEM01 = '"+RejectLetterUtil.EMAIL+"' ");
				sql.append(" INNER JOIN ORIG_SALE_INFO ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_SALE_INFO.APPLICATION_GROUP_ID AND ORIG_SALE_INFO.SALES_TYPE = '01' ");
				sql.append(" WHERE ORIG_APPLICATION_GROUP.JOB_STATE IN("+InfBatchProperty.getSQLParameter("REJECT_LETTER_JOB_STATE_END")+") ");
				sql.append(" AND NOT EXISTS ( ");
				sql.append("         SELECT 1 ");
				sql.append("         FROM INF_BATCH_LOG LOG ");
				sql.append("         WHERE ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = LOG.APPLICATION_GROUP_ID AND ORIG_APPLICATION.APPLICATION_RECORD_ID = LOG.APPLICATION_RECORD_ID ");
				sql.append("                 AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = LOG.LIFE_CYCLE  ");
				sql.append("                 AND LOG.INTERFACE_CODE = '"+REJECT_LETTER_PDF_INTERFACE_CODE+"' AND LOG.INTERFACE_STATUS IN ('"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"','"+INTERFACE_STATUS_NOT_SEND+"') ");
				sql.append(" ) ");
				sql.append(" GROUP BY ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID ");
				sql.append("         , ORIG_APPLICATION_GROUP.BRANCH_NAME ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TEMPLATE ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TYPE ");
				sql.append("         , ORIG_APPLICATION_GROUP.LIFE_CYCLE ");
				sql.append("         , INF_BATCH_LOG.REF_ID ");
				sql.append("         , INF_BATCH_LOG.SYSTEM02 ");
				sql.append("         , ORIG_SALE_INFO.SALES_ID ");
				sql.append("		 , ORIG_SALE_INFO.SALE_CHANNEL ");
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_RECORD_IDS = rs.getString("APPLICATION_RECORD_IDS");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				String APPLICATION_TYPE = rs.getString("APPLICATION_TYPE");
				String BRANCH_NAME = rs.getString("BRANCH_NAME");
				String LANGUAGE = rs.getString("LANGUAGE");
				String LETTER_NO = rs.getString("LETTER_NO");
				String SALES_ID = rs.getString("SALES_ID");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				int LIFE_CYCLE = rs.getInt("LIFE_CYCLE");
				ArrayList<String> applicationRecordIds = new ArrayList<String>();
				if(!InfBatchUtil.empty(APPLICATION_RECORD_IDS)){
					String[] appRecordIds = APPLICATION_RECORD_IDS.split(",");
					for(String appRecordId :appRecordIds){
						applicationRecordIds.add(appRecordId);
					}
				}
				ArrayList<RejectLetterPDFPersonalInfoDataM> personalInfos = findRejectLetterPDFPersonalInfo(APPLICATION_GROUP_ID,applicationRecordIds,conn);
				RejectLetterPDFPersonalInfoDataM rejectedPersonalInfo = GenerateLetterUtil.getPDFLetterConditionCase(personalInfos,APPLICATION_TEMPLATE);
				logger.debug("rejectedPersonalInfo : "+rejectedPersonalInfo);
				if(!InfBatchUtil.empty(rejectedPersonalInfo)){
					String reasonCode = rejectedPersonalInfo.getReasonCode();
					List<NotificationConfig> configs = getNotificationConfigs(APPLICATION_TEMPLATE,SALE_CHANNEL,reasonCode,REJECT_LETTER_PDF_SEND_TIME,NOTIFICATION_APPLICATION_STATUS_REJECT,SEND_TO_TYPE_CUSTOMER,conn);
					if(!InfBatchUtil.empty(configs)){
						for(NotificationConfig config : configs){
							if(!InfBatchUtil.empty(config)){
								if(REJECT_LETTER_PDF_NOFIFICATION_TYPE.contains(config.getNotificationType())){
									String sendTo = config.getSendTo();
									RejectLetterPDFDataM rejectLetterPDF = new RejectLetterPDFDataM();
										rejectLetterPDF.setApplicationRecordIds(applicationRecordIds);
										rejectLetterPDF.setApplicationGroupId(APPLICATION_GROUP_ID);
										rejectLetterPDF.setApplicationGroupNo(APPLICATION_GROUP_NO);
										rejectLetterPDF.setBranchName(BRANCH_NAME);
										rejectLetterPDF.setLetterNo(LETTER_NO);
										rejectLetterPDF.setLetterType(RejectLetterUtil.EMAIL);
										rejectLetterPDF.setTemplateId(APPLICATION_TEMPLATE);
										rejectLetterPDF.setSendTo(sendTo);
										rejectLetterPDF.setLanguage(LANGUAGE);
										rejectLetterPDF.setApplicationType(APPLICATION_TYPE);
										rejectLetterPDF.setLifeCycle(LIFE_CYCLE);
										rejectLetterPDF.setEmailPriority(config.getPriority());
										rejectLetterPDF.setSubEmailPriority(config.getSubPriority());
										if(!NOTIFICATION_SEND_TO_TYPE_CUSTOMER.equals(sendTo)){
											rejectLetterPDF.setSaleId(SALES_ID);
										}
										rejectLetterPDF.setPersonalInfos(personalInfos);
										rejectLetterPDF.setRejectedPersonalInfo(rejectedPersonalInfo);
										rejectLetterPDF.setReasonCode(reasonCode);
										rejectLetterPDF.setFunctionType(REJECT_LETTER_PDF_FUNCTION_TYPE_SUBMIT_MAKEPDF);
									rejectLetterPDFs.add(rejectLetterPDF);
								}
							}
						}
					}else{
						for(String applicationRecordId : applicationRecordIds){
							if(!isContainLog(infBatchLogs,APPLICATION_GROUP_ID,applicationRecordId)){
								InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
									infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
									infBatchLog.setApplicationRecordId(applicationRecordId);
									infBatchLog.setLifeCycle(LIFE_CYCLE);
									infBatchLog.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
									infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND); 
									infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
									infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
									infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
									infBatchLog.setLogMessage("Configuration was not found");
								infBatchLogs.add(infBatchLog);
							}
						}
					}
				}else{
					for(String applicationRecordId : applicationRecordIds){
						if(!isContainLog(infBatchLogs,APPLICATION_GROUP_ID,applicationRecordId)){
							InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
								infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
								infBatchLog.setApplicationRecordId(applicationRecordId);
								infBatchLog.setLifeCycle(LIFE_CYCLE);
								infBatchLog.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
								infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND); 
								infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
								infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
								infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
								infBatchLog.setLogMessage("Recipient was not found");
							infBatchLogs.add(infBatchLog);
						}
					}
				}
			}
//			if(!InfBatchUtil.empty(infBatchLogs)){
//				insertInfBatchLog(infBatchLogs);
//			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		logger.debug("Customer : "+rejectLetterPDFs);
		return rejectLetterPDFs;
	}
	private ArrayList<RejectLetterPDFDataM> findRejectLetterPDFSeller(ArrayList<InfBatchLogDataM> infBatchLogs)throws InfBatchException{
		String REJECT_LETTER_PDF_FUNCTION_TYPE_SUBMIT_MAKEPDF = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_FUNCTION_TYPE_SUBMIT_MAKEPDF");
		String REJECT_LETTER_PDF_FUNCTION_TYPE_WEALTH_SUP_REJECT = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_FUNCTION_TYPE_WEALTH_SUP_REJECT");
		ArrayList<String> REJECT_LETTER_PDF_NOFIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_PDF_NOFIFICATION_TYPE");
		ArrayList<String> REJECT_LETTER_SEND_TO_SELLER_LIST = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_SEND_TO_SELLER_LIST");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RejectLetterPDFDataM> rejectLetterPDFs = new ArrayList<RejectLetterPDFDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
				sql.append(" SELECT ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID ");
				sql.append("         , ORIG_APPLICATION_GROUP.BRANCH_NAME ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TEMPLATE ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TYPE ");
				sql.append("         , ORIG_APPLICATION_GROUP.LIFE_CYCLE ");
				sql.append("         , INF_BATCH_LOG.REF_ID AS LETTER_NO ");
				sql.append("         , INF_BATCH_LOG.SYSTEM02 AS LANGUAGE ");
				sql.append("         , ORIG_SALE_INFO.SALES_ID ");
				sql.append("		 , ORIG_SALE_INFO.SALE_CHANNEL ");
				sql.append("         , LISTAGG(ORIG_APPLICATION.APPLICATION_RECORD_ID, ',') WITHIN GROUP (ORDER BY ORIG_APPLICATION.APPLICATION_RECORD_ID) AS APPLICATION_RECORD_IDS ");
				sql.append(" FROM ORIG_APPLICATION_GROUP ");
				sql.append(" INNER JOIN ORIG_APPLICATION ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_APPLICATION.APPLICATION_GROUP_ID AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ORIG_APPLICATION.LIFE_CYCLE ");
				sql.append(" INNER JOIN INF_BATCH_LOG ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = INF_BATCH_LOG.APPLICATION_GROUP_ID AND ORIG_APPLICATION.APPLICATION_RECORD_ID = INF_BATCH_LOG.APPLICATION_RECORD_ID ");
				sql.append("         AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = INF_BATCH_LOG.LIFE_CYCLE  ");
				sql.append("         AND INF_BATCH_LOG.INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' AND INF_BATCH_LOG.INTERFACE_STATUS = '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"' ");
				sql.append(" INNER JOIN ORIG_SALE_INFO ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_SALE_INFO.APPLICATION_GROUP_ID AND ORIG_SALE_INFO.SALES_TYPE = '01' ");
				sql.append(" WHERE ORIG_APPLICATION_GROUP.JOB_STATE IN("+InfBatchProperty.getSQLParameter("REJECT_LETTER_JOB_STATE_END")+") ");
				sql.append(" AND NOT EXISTS ( ");
				sql.append("         SELECT 1 ");
				sql.append("         FROM INF_BATCH_LOG LOG ");
				sql.append("         WHERE ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = LOG.APPLICATION_GROUP_ID AND ORIG_APPLICATION.APPLICATION_RECORD_ID = LOG.APPLICATION_RECORD_ID ");
				sql.append("                 AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = LOG.LIFE_CYCLE  ");
				sql.append("                 AND LOG.INTERFACE_CODE = '"+REJECT_LETTER_PDF_INTERFACE_CODE+"' AND LOG.INTERFACE_STATUS IN ('"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"','"+INTERFACE_STATUS_NOT_SEND+"') ");
				sql.append(" ) ");
				sql.append(" GROUP BY ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID ");
				sql.append("         , ORIG_APPLICATION_GROUP.BRANCH_NAME ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TEMPLATE ");
				sql.append("         , ORIG_APPLICATION_GROUP.APPLICATION_TYPE ");
				sql.append("         , ORIG_APPLICATION_GROUP.LIFE_CYCLE ");
				sql.append("         , INF_BATCH_LOG.REF_ID ");
				sql.append("         , INF_BATCH_LOG.SYSTEM02 ");
				sql.append("         , ORIG_SALE_INFO.SALES_ID ");
				sql.append("		 , ORIG_SALE_INFO.SALE_CHANNEL ");
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_RECORD_IDS = rs.getString("APPLICATION_RECORD_IDS");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				String APPLICATION_TYPE = rs.getString("APPLICATION_TYPE");
				String BRANCH_NAME = rs.getString("BRANCH_NAME");
				String LANGUAGE = rs.getString("LANGUAGE");
				String LETTER_NO = rs.getString("LETTER_NO");
				String SALES_ID = rs.getString("SALES_ID");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				int LIFE_CYCLE = rs.getInt("LIFE_CYCLE");
				ArrayList<String> applicationRecordIds = new ArrayList<String>();
				if(!InfBatchUtil.empty(APPLICATION_RECORD_IDS)){
					String[] appRecordIds = APPLICATION_RECORD_IDS.split(",");
					for(String appRecordId :appRecordIds){
						applicationRecordIds.add(appRecordId);
					}
				}
				ArrayList<RejectLetterPDFPersonalInfoDataM> personalInfos = findRejectLetterPDFPersonalInfo(APPLICATION_GROUP_ID,applicationRecordIds,conn);
				RejectLetterPDFPersonalInfoDataM rejectedPersonalInfo = GenerateLetterUtil.getPDFLetterConditionCase(personalInfos,APPLICATION_TEMPLATE);
				if(!InfBatchUtil.empty(rejectedPersonalInfo)){
					String reasonCode = rejectedPersonalInfo.getReasonCode();
					List<NotificationConfig> configs = getNotificationConfigs(APPLICATION_TEMPLATE,SALE_CHANNEL,reasonCode,REJECT_LETTER_PDF_SEND_TIME,NOTIFICATION_APPLICATION_STATUS_REJECT,REJECT_LETTER_SEND_TO_SELLER_LIST,conn);
					if(!InfBatchUtil.empty(configs)){
						for(NotificationConfig config : configs){
							if(!InfBatchUtil.empty(config)){
								if(REJECT_LETTER_PDF_NOFIFICATION_TYPE.contains(config.getNotificationType())){
									String sendTo = config.getSendTo();
									RejectLetterPDFDataM rejectLetterPDF = new RejectLetterPDFDataM();
										rejectLetterPDF.setApplicationRecordIds(applicationRecordIds);
										rejectLetterPDF.setApplicationGroupId(APPLICATION_GROUP_ID);
										rejectLetterPDF.setApplicationGroupNo(APPLICATION_GROUP_NO);
										rejectLetterPDF.setBranchName(BRANCH_NAME);
										rejectLetterPDF.setLetterNo(LETTER_NO);
										rejectLetterPDF.setLetterType(RejectLetterUtil.EMAIL);
										rejectLetterPDF.setTemplateId(APPLICATION_TEMPLATE);
										rejectLetterPDF.setSendTo(sendTo);
										rejectLetterPDF.setLanguage(LANGUAGE);
										rejectLetterPDF.setApplicationType(APPLICATION_TYPE);
										rejectLetterPDF.setLifeCycle(LIFE_CYCLE);
										rejectLetterPDF.setEmailPriority(config.getPriority());
										rejectLetterPDF.setSubEmailPriority(config.getSubPriority());
										rejectLetterPDF.setSaleId(SALES_ID);
										rejectLetterPDF.setPersonalInfos(personalInfos);
										rejectLetterPDF.setRejectedPersonalInfo(rejectedPersonalInfo);
										rejectLetterPDF.setReasonCode(reasonCode);
										rejectLetterPDF.setFunctionType(REJECT_LETTER_PDF_FUNCTION_TYPE_SUBMIT_MAKEPDF);
									rejectLetterPDFs.add(rejectLetterPDF);
								}
							}
						}
					}else{
						for(String applicationRecordId : applicationRecordIds){
							if(!isContainLog(infBatchLogs,APPLICATION_GROUP_ID,applicationRecordId)){
								InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
									infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
									infBatchLog.setApplicationRecordId(applicationRecordId);
									infBatchLog.setLifeCycle(LIFE_CYCLE);
									infBatchLog.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
									infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND); 
									infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
									infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
									infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
									infBatchLog.setLogMessage("Configuration was not found");
								infBatchLogs.add(infBatchLog);
							}
						}
					}
				}else if(!InfBatchUtil.empty(SALES_ID)){
					boolean isInfiniteWisdomPremier = GenerateLetterUtil.isInfiniteWisdomPremierApplication(APPLICATION_TEMPLATE);
					boolean isRejectAll = GenerateLetterUtil.isPDFRejectAll(personalInfos);
					boolean isAddSupApplication = GenerateLetterUtil.isPDFAddSupplementaryApplication(personalInfos);
					if(isInfiniteWisdomPremier && isRejectAll && isAddSupApplication){
						NotifyApplicationSegment notifyApplicationSegment = NotificationUtil.notifyApplication(APPLICATION_GROUP_ID);
						if(!InfBatchUtil.empty(notifyApplicationSegment)){
							Reason reason = notifyApplicationSegment.findReasonApplicationGroup(APPLICATION_GROUP_ID);
							if(!InfBatchUtil.empty(reason)){
								String reasonCode = reason.getReasonCode();
								if(!InfBatchUtil.empty(reasonCode)){
									List<NotificationConfig> configs = getNotificationConfigs(APPLICATION_TEMPLATE, SALE_CHANNEL, reasonCode, REJECT_LETTER_PDF_SEND_TIME, NOTIFICATION_APPLICATION_STATUS_REJECT,REJECT_LETTER_SEND_TO_SELLER_LIST,conn);
									if(!InfBatchUtil.empty(configs)){
										for(NotificationConfig config : configs){
											if(!InfBatchUtil.empty(config)){
												if(REJECT_LETTER_PDF_NOFIFICATION_TYPE.contains(config.getNotificationType())){
													String sendTo = config.getSendTo();
													RejectLetterPDFDataM rejectLetterPDF = new RejectLetterPDFDataM();
														rejectLetterPDF.setApplicationRecordIds(applicationRecordIds);
														rejectLetterPDF.setApplicationGroupId(APPLICATION_GROUP_ID);
														rejectLetterPDF.setApplicationGroupNo(APPLICATION_GROUP_NO);
														rejectLetterPDF.setBranchName(BRANCH_NAME);
														rejectLetterPDF.setLetterNo(LETTER_NO);
														rejectLetterPDF.setLetterType(RejectLetterUtil.EMAIL);
														rejectLetterPDF.setTemplateId(APPLICATION_TEMPLATE);
														rejectLetterPDF.setSendTo(sendTo);
														rejectLetterPDF.setLanguage(LANGUAGE);
														rejectLetterPDF.setApplicationType(APPLICATION_TYPE);
														rejectLetterPDF.setLifeCycle(LIFE_CYCLE);
														rejectLetterPDF.setEmailPriority(config.getPriority());
														rejectLetterPDF.setSubEmailPriority(config.getSubPriority());
														rejectLetterPDF.setSaleId(SALES_ID);
														rejectLetterPDF.setPersonalInfos(personalInfos);
														rejectLetterPDF.setReasonCode(reasonCode);
														rejectLetterPDF.setFunctionType(REJECT_LETTER_PDF_FUNCTION_TYPE_WEALTH_SUP_REJECT);
													rejectLetterPDFs.add(rejectLetterPDF);
												}
											}
										}
									}else{
										for(String applicationRecordId : applicationRecordIds){
											if(!isContainLog(infBatchLogs,APPLICATION_GROUP_ID,applicationRecordId)){
												InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
													infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
													infBatchLog.setApplicationRecordId(applicationRecordId);
													infBatchLog.setLifeCycle(LIFE_CYCLE);
													infBatchLog.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
													infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND); 
													infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
													infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
													infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
													infBatchLog.setLogMessage("Configuration was not found");
												infBatchLogs.add(infBatchLog);
											}
										}
									}
								}
							}
						}
					}else{
						for(String applicationRecordId : applicationRecordIds){
							if(!isContainLog(infBatchLogs,APPLICATION_GROUP_ID,applicationRecordId)){
								InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
									infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
									infBatchLog.setApplicationRecordId(applicationRecordId);
									infBatchLog.setLifeCycle(LIFE_CYCLE);
									infBatchLog.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
									infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND); 
									infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
									infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
									infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
									infBatchLog.setLogMessage("Recipient was not found");
								infBatchLogs.add(infBatchLog);
							}
						}
					}
				}else{
					for(String applicationRecordId : applicationRecordIds){
						if(!isContainLog(infBatchLogs,APPLICATION_GROUP_ID,applicationRecordId)){
							InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
								infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
								infBatchLog.setApplicationRecordId(applicationRecordId);
								infBatchLog.setLifeCycle(LIFE_CYCLE);
								infBatchLog.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
								infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND); 
								infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
								infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
								infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
								infBatchLog.setLogMessage("Recipient was not found");
							infBatchLogs.add(infBatchLog);
						}
					}
				}
			}
//			if(!InfBatchUtil.empty(infBatchLogs)){
//				insertInfBatchLog(infBatchLogs);
//			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		logger.debug("Seller : "+rejectLetterPDFs);
		return rejectLetterPDFs;
	}
	private boolean isContainLog(ArrayList<InfBatchLogDataM> infBatchLogs,String applicationGroupId,String applicationRecordId){
		if(!InfBatchUtil.empty(infBatchLogs)){
			for(InfBatchLogDataM infBatchLog : infBatchLogs){
				if(!InfBatchUtil.empty(infBatchLog)){
					if(infBatchLog.getApplicationGroupId().equals(applicationGroupId) && infBatchLog.getApplicationRecordId().equals(applicationRecordId)){
						return true;
					}
				}
			}
		}
		return false;
	}
	private ArrayList<String> filterPdfApplicationGroupId(ArrayList<RejectLetterPDFDataM> rejectLetterPDFs){
		ArrayList<String> applicationGroupIds = new ArrayList<String>();
		if(!InfBatchUtil.empty(rejectLetterPDFs)){
			for(RejectLetterPDFDataM rejectLetterPdf : rejectLetterPDFs){
				if(!InfBatchUtil.empty(rejectLetterPdf)){
					String applicationGroupId = rejectLetterPdf.getApplicationGroupId();
					if(!applicationGroupIds.contains(applicationGroupId)){
						applicationGroupIds.add(applicationGroupId);
					}
				}
			}
		}
		return applicationGroupIds;
	}
	private void removeDuplicateLog(ArrayList<InfBatchLogDataM> infBatchLogs,ArrayList<RejectLetterPDFDataM> rejectLetterPDFs){
		if(!InfBatchUtil.empty(rejectLetterPDFs)){
			ArrayList<String> applicationGroupIds = filterPdfApplicationGroupId(rejectLetterPDFs);
			if(!InfBatchUtil.empty(applicationGroupIds) && !InfBatchUtil.empty(infBatchLogs)){
				for(String id : applicationGroupIds){
					if(!InfBatchUtil.empty(id)){
						for(Iterator<InfBatchLogDataM> iterator = infBatchLogs.iterator(); iterator.hasNext();){
							InfBatchLogDataM infBatchLog = iterator.next();
							if(!InfBatchUtil.empty(infBatchLog)){
								if(infBatchLog.getApplicationGroupId().equals(id)){
									iterator.remove();
								}
							}
						}
					}
				}
			}
		}
	}
	private ArrayList<RejectLetterPDFPersonalInfoDataM> findRejectLetterPDFPersonalInfo(String applicationGroupId,ArrayList<String> applicationRecordIds,Connection conn)throws InfBatchException{
		ArrayList<RejectLetterPDFPersonalInfoDataM> personalInfos = new ArrayList<RejectLetterPDFPersonalInfoDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		logger.debug("applicationGroupId : "+applicationGroupId);
		NotifyApplicationSegment  notifyApplicationSegment = NotificationUtil.notifyApplication(applicationGroupId);
		HashMap<String,Reason> existingPersonalRejectReason = new HashMap<String,Reason>(); 
		try{
//			StringBuilder sql = new StringBuilder();
//			sql.append(" SELECT ORIG_APPLICATION.APPLICATION_RECORD_ID ");
//			sql.append("         , ORIG_APPLICATION.PROLICY_PROGRAM_ID ");
//			sql.append("         , ORIG_APPLICATION.BUSINESS_CLASS_ID ");
//			sql.append("         , ORIG_APPLICATION.FINAL_APP_DECISION ");
//			sql.append("         , ORIG_PERSONAL_INFO.PERSONAL_ID ");
//			sql.append("         , ORIG_PERSONAL_INFO.PERSONAL_TYPE ");
//			sql.append("         , ORIG_PERSONAL_INFO.EMAIL_PRIMARY ");
//			sql.append("         , ORIG_PERSONAL_INFO.MOBILE_NO ");
//			sql.append("         , ORIG_PERSONAL_INFO.NATIONALITY ");
//			sql.append("         , ORIG_PERSONAL_INFO.EN_FIRST_NAME ");
//			sql.append("         , ORIG_PERSONAL_INFO.EN_LAST_NAME ");
//			sql.append("         , ORIG_PERSONAL_INFO.EN_TITLE_DESC ");
//			sql.append("         , ORIG_PERSONAL_INFO.TH_FIRST_NAME ");
//			sql.append("         , ORIG_PERSONAL_INFO.TH_LAST_NAME ");
//			sql.append("         , ORIG_PERSONAL_INFO.IDNO ");
//			sql.append("         , ORIG_CASH_TRANSFER.CASH_TRANSFER_TYPE ");
//			sql.append("         , ORIG_CARD.CARD_TYPE ");
//			sql.append("         , ORIG_CARD.CARD_LEVEL ");
//			sql.append(" FROM ORIG_APPLICATION ");
//			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION ON  ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_PERSONAL_RELATION.REF_ID AND ORIG_PERSONAL_RELATION.RELATION_LEVEL= 'A' ");
//			sql.append(" INNER JOIN ORIG_PERSONAL_INFO ON  ORIG_PERSONAL_RELATION.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID ");
//			sql.append(" INNER JOIN ORIG_LOAN  ON ORIG_LOAN.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
//			sql.append(" LEFT JOIN ORIG_CASH_TRANSFER ON ORIG_CASH_TRANSFER.LOAN_ID = ORIG_LOAN.LOAN_ID ");
//			sql.append(" LEFT JOIN ORIG_CARD ON ORIG_LOAN.LOAN_ID = ORIG_CARD.LOAN_ID ");
//			sql.append(" WHERE ORIG_APPLICATION.APPLICATION_GROUP_ID = ? ");
//			sql.append("         AND ORIG_APPLICATION.APPLICATION_RECORD_ID IN ( ");
//				String COMMA="";
//				for(String appRecordId : applicationRecordIds ){
//					sql.append(COMMA+"'"+appRecordId+"'");
//					COMMA=",";
//				}
//			sql.append("  )");
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ORIG_APPLICATION.APPLICATION_RECORD_ID ");
			sql.append("         , ORIG_APPLICATION.PROLICY_PROGRAM_ID ");
			sql.append("         , ORIG_APPLICATION.BUSINESS_CLASS_ID ");
			sql.append("         , ORIG_APPLICATION.FINAL_APP_DECISION ");
			sql.append("         , ORIG_PERSONAL_INFO.PERSONAL_ID ");
			sql.append("         , ORIG_PERSONAL_INFO.PERSONAL_TYPE ");
			sql.append("         , ORIG_PERSONAL_INFO.EMAIL_PRIMARY ");
			sql.append("         , ORIG_PERSONAL_INFO.MOBILE_NO ");
			sql.append("         , ORIG_PERSONAL_INFO.NATIONALITY ");
			sql.append("         , ORIG_PERSONAL_INFO.EN_FIRST_NAME ");
			sql.append("         , ORIG_PERSONAL_INFO.EN_LAST_NAME ");
			sql.append("         , ORIG_PERSONAL_INFO.EN_TITLE_DESC ");
			sql.append("         , ORIG_PERSONAL_INFO.TH_FIRST_NAME ");
			sql.append("         , ORIG_PERSONAL_INFO.TH_LAST_NAME ");
			sql.append("         , ORIG_PERSONAL_INFO.IDNO ");
			sql.append("         , ORIG_CARD.CARD_TYPE ");
			sql.append("         , ORIG_CARD.CARD_LEVEL ");
			sql.append(" FROM ORIG_APPLICATION ");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION ON  ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_PERSONAL_RELATION.REF_ID AND ORIG_PERSONAL_RELATION.RELATION_LEVEL= 'A' ");
			sql.append(" INNER JOIN ORIG_PERSONAL_INFO ON  ORIG_PERSONAL_RELATION.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID ");
			sql.append(" INNER JOIN ORIG_LOAN  ON ORIG_LOAN.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
			sql.append(" LEFT JOIN ORIG_CARD ON ORIG_LOAN.LOAN_ID = ORIG_CARD.LOAN_ID ");
			sql.append(" WHERE ORIG_APPLICATION.APPLICATION_GROUP_ID = ? ");
			sql.append("         AND ORIG_APPLICATION.APPLICATION_RECORD_ID IN ( ");
				String COMMA="";
				for(String appRecordId : applicationRecordIds ){
					sql.append(COMMA+"'"+appRecordId+"'");
					COMMA=",";
				}
			sql.append("  ) ");
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
				int index = 1;
				ps.setString(index++,applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String NATIONALITY = rs.getString("NATIONALITY");
				String EN_FIRST_NAME = rs.getString("EN_FIRST_NAME");
				String EN_LAST_NAME = rs.getString("EN_LAST_NAME");
				String EN_TITLE_DESC = rs.getString("EN_TITLE_DESC");
				String TH_FIRST_NAME = rs.getString("TH_FIRST_NAME");
				String TH_LAST_NAME = rs.getString("TH_LAST_NAME");
				String IDNO = rs.getString("IDNO");
				String CARD_TYPE = rs.getString("CARD_TYPE");
				String CARD_LEVEL = rs.getString("CARD_LEVEL");
				String REASON_CODE = "";
				if(!existingPersonalRejectReason.containsKey(PERSONAL_ID)){
					Reason reason = notifyApplicationSegment.findReasonPerson(PERSONAL_ID);
					REASON_CODE = reason.getReasonCode();
				}else{
					Reason reason = existingPersonalRejectReason.get(REASON_CODE);
					REASON_CODE = reason.getReasonCode();
				}
				RejectLetterPDFPersonalInfoDataM personalInfo = new RejectLetterPDFPersonalInfoDataM();
					personalInfo.setApplicationRecordId(APPLICATION_RECORD_ID);
					personalInfo.setBusinessClassId(BUSINESS_CLASS_ID);
					personalInfo.setCardLevel(CARD_LEVEL);
					personalInfo.setCardType(CARD_TYPE);
					personalInfo.setCashTransferType(DEFUALT_DATA_TYPE_ALL);
					personalInfo.setEmail(EMAIL_PRIMARY);
					personalInfo.setEnFirshName(EN_FIRST_NAME);
					personalInfo.setEnLastName(EN_LAST_NAME);
					personalInfo.setEnTitleNameDesc(EN_TITLE_DESC);
					personalInfo.setFinalDecision(FINAL_APP_DECISION);
					personalInfo.setIdNo(IDNO);
					personalInfo.setMobileNo(MOBILE_NO);
					personalInfo.setNationality(NATIONALITY);
					personalInfo.setPersonalId(PERSONAL_ID);
					personalInfo.setPersonalType(PERSONAL_TYPE);
					personalInfo.setPolicyProgramId(PROLICY_PROGRAM_ID);
					personalInfo.setThFirshName(TH_FIRST_NAME);
					personalInfo.setThLastName(TH_LAST_NAME);
					personalInfo.setReasonCode(REASON_CODE);
				personalInfos.add(personalInfo);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return personalInfos;
	}
	//private List<NotificationConfig> getNotificationConfigs(String templateCode,String saleTypeDesc,String saleChannel,String reasonCode,String sendTime,String applicationStatus,Connection conn) throws InfBatchException{
	private List<NotificationConfig> getNotificationConfigs(String templateCode,String saleChannel,String reasonCode,String sendTime,String applicationStatus,ArrayList<String> sendTos,Connection conn) throws InfBatchException{
		List<NotificationConfig> notificationConfigs = new ArrayList<NotificationConfig>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		logger.debug("templateCode : "+templateCode);
		//logger.debug("saleTypeDesc : "+saleTypeDesc);
		logger.debug("reasonCode : "+reasonCode);
		logger.debug("saleChannel : "+saleChannel);
		logger.debug("applicationStatus : "+applicationStatus);
		logger.debug("sendTime : "+sendTime);
		logger.debug("sendTos : "+sendTos);
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT DISTINCT MS_NOTI_SENDING_DETAIL.SEND_TO ");
			sql.append("	, MS_NOTI_SENDING.NOTIFICATION_TYPE ");
			sql.append("	, MS_NOTI_SENDING_PRIORITY.PRIORITY	");
			sql.append("	, MS_NOTI_SENDING_PRIORITY.SUP_PRIORITY ");
			sql.append("  FROM MS_NOTI_APP_TEMPLATE ");
			sql.append("  INNER JOIN MS_NOTI_GROUP ON MS_NOTI_GROUP.GROUP_CODE =MS_NOTI_APP_TEMPLATE.GROUP_CODE   ");
			sql.append("  INNER JOIN MS_NOTI_CHANNEL ON MS_NOTI_CHANNEL.GROUP_CODE =MS_NOTI_APP_TEMPLATE.GROUP_CODE  ");
			sql.append("  INNER JOIN MS_NOTI_SENDING ON MS_NOTI_SENDING.GROUP_CHANNEL_CODE = MS_NOTI_CHANNEL.GROUP_CHANNEL_CODE  ");
			sql.append("  INNER JOIN MS_NOTI_SENDING_DETAIL ON  MS_NOTI_SENDING.SENDING_ID = MS_NOTI_SENDING_DETAIL.SENDING_ID  ");
			sql.append("  INNER JOIN MS_NOTI_SENDING_PRIORITY ON  MS_NOTI_SENDING_PRIORITY.GROUP_CHANNEL_CODE = MS_NOTI_SENDING.GROUP_CHANNEL_CODE AND MS_NOTI_SENDING_PRIORITY.SENDING_ID = MS_NOTI_SENDING.SENDING_ID ");
			sql.append("  INNER JOIN  MS_NOTI_REJECT_REASON ON MS_NOTI_REJECT_REASON.GROUP_CODE = MS_NOTI_GROUP.GROUP_CODE ");
			sql.append("  WHERE (MS_NOTI_APP_TEMPLATE.TEMPLATE_CODE = ? OR MS_NOTI_APP_TEMPLATE.TEMPLATE_CODE = 'ALL'  )  ");
			//sql.append("   AND MS_NOTI_SENDING_DETAIL.SEND_TO = ? ");
			sql.append("   AND MS_NOTI_REJECT_REASON.REASON_CODE= ? ");
			sql.append("   AND MS_NOTI_GROUP.HAVE_SALE_FLAG ='Y' ");
			sql.append(" AND ( MS_NOTI_CHANNEL.CHANNEL_CODE = ? ");
			sql.append("     OR  MS_NOTI_CHANNEL.CHANNEL_CODE = 'ALL' ");
			sql.append("     OR  ( MS_NOTI_CHANNEL.CHANNEL_CODE = 'N' ");
			sql.append("         AND ? IS NULL )) ");
			sql.append(" AND MS_NOTI_GROUP.APPLICATION_STATUS = ? ");
			sql.append(" AND MS_NOTI_SENDING.NOTIFICATION_TYPE IN ("+InfBatchProperty.getSQLParameter("REJECT_LETTER_PDF_NOFIFICATION_TYPE")+") ");
			sql.append(" AND MS_NOTI_SENDING.SEND_TIME = ? ");
			if(!InfBatchUtil.empty(sendTos)){
				sql.append("   AND MS_NOTI_SENDING_DETAIL.SEND_TO IN ( ");
				String comma = "";
				for(String sendTo : sendTos){
					sql.append(comma+"?");
					comma = ",";
				}
				sql.append(" ) ");
			}
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
				ps.setString(index++, templateCode);
				//ps.setString(index++, saleTypeDesc);
				ps.setString(index++, reasonCode);
				ps.setString(index++, saleChannel);
				ps.setString(index++, saleChannel);
				ps.setString(index++, applicationStatus);
				ps.setString(index++, sendTime);
				if(!InfBatchUtil.empty(sendTos)){
					for(String sendTo : sendTos){
						ps.setString(index++, sendTo);
					}
				}
			rs = ps.executeQuery();
			while(rs.next()){
				String SEND_TO = rs.getString("SEND_TO");
				String NOTIFICATION_TYPE = rs.getString("NOTIFICATION_TYPE");
				String PRIORITY = rs.getString("PRIORITY");
				String SUP_PRIORITY = rs.getString("SUP_PRIORITY");
				NotificationConfig notificationConfig = new NotificationConfig();
					notificationConfig.setSendTo(SEND_TO);
					notificationConfig.setNotificationType(NOTIFICATION_TYPE);
					notificationConfig.setPriority(PRIORITY);
					notificationConfig.setSubPriority(SUP_PRIORITY);
				notificationConfigs.add(notificationConfig);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try {
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e);
			}
		}
		return notificationConfigs;
	}
	private void createPriorityMap(RejectLetterPDFDataM rejectLetterPDF,HashMap<String,String> priorityMap,Connection conn)throws InfBatchException{
		try{
			String applicationTemplate = rejectLetterPDF.getAppTemplate();
			//String reasonCode = rejectLetterPDF.getRejectedPersonalInfo().getReasonCode();
			//String personalId = rejectLetterPDF.getRejectedPersonalInfo().getPersonalId();
			String reasonCode = rejectLetterPDF.getReasonCode();
			List<String> cashTransferTypes = new ArrayList<String>();
			RejectLetterPDFPersonalInfoDataM rejectedPersonalInfo = rejectLetterPDF.getRejectedPersonalInfo();
			if(!InfBatchUtil.empty(rejectedPersonalInfo)){
				 cashTransferTypes = rejectLetterPDF.findCashTransferTypePersonal(rejectedPersonalInfo.getPersonalId());
			}
			if(InfBatchUtil.empty(cashTransferTypes)){
				cashTransferTypes = new ArrayList<String>(Arrays.asList(new String[]{ DEFUALT_DATA_TYPE_ALL }));
			}
			for(String cashTransferType : cashTransferTypes){
				cashTransferType = !InfBatchUtil.empty(cashTransferType) ? cashTransferType : DEFUALT_DATA_TYPE_ALL ;
				// set priorityMap NEXTDAY
				for(String type : NEXTDAY_NOTIFICATION_TYPE){
					String KEY_PRIORITY_NEXTDAY = GenerateLetterUtil.generateKeyPriorityRejectLetter(type, NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME, applicationTemplate, reasonCode, cashTransferType);
					if(!priorityMap.containsKey(KEY_PRIORITY_NEXTDAY)){
						addNotificationPriority(type, NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME, applicationTemplate, reasonCode, cashTransferType, priorityMap, conn);
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
	private void addNotificationPriority(String notificationType,String sendTime,String applicationTemplate,String reasonCode,String cashTransferType,HashMap<String,String> priorityMap,Connection conn) throws InfBatchException{
		try{
			MasterNotificationConfigDAO dao = NotificationFactory.getMasterNotificationConfigDAO();
			NotificationCriteriaDataM notiCriteria = new NotificationCriteriaDataM();
				notiCriteria.setApplicationTemplate(applicationTemplate);
				notiCriteria.setCashTransferType(cashTransferType);
				notiCriteria.setReasonCode(reasonCode);
				notiCriteria.setSendTime(sendTime);
				notiCriteria.setNotificationType(notificationType);
				notiCriteria.addSendTo("CUSTOMER");							
			dao.getNotificationPriority(notiCriteria, priorityMap, conn);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
}
