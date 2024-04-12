package com.eaf.inf.batch.ulo.letter.reject.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterApplicationDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterBuildTemplateEntity;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableBundleEntity;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateReasonCodeDetailDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.product.GenerateLetterUtil;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationConfig;
import com.eaf.inf.batch.ulo.notification.dao.MasterNotificationConfigDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.NotificationCriteriaDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;
import com.eaf.inf.batch.ulo.notification.model.Reason;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.service.common.util.ServiceUtil;

public class RejectLetterDAOImpl extends InfBatchObjectDAO implements RejectLetterDAO{
	public static transient Logger logger = Logger.getLogger(RejectLetterDAOImpl.class);
	String JOB_STATE_END= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_JOB_STATE_END");	
	String FINAL_APP_DECISION_REJECT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_FINAL_APP_DECISION_REJECT");	
	String XRULES_VER_LEVEL_APPLICATION= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_XRULES_VER_LEVEL_APPLICATION");	
	String REJECT_LETTER_INTERFACE_CODE= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE");	
	String REJECT_LETTER_INTERFACE_CODE_T1= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T1");	
	String REJECT_LETTER_INTERFACE_CODE_T2= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T2");
	String REJECT_LETTER_INTERFACE_CODE_T3= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T3");
	String RELATION_LEVEL_APPLICATION= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_RELATION_LEVEL_APPLICATION");	
	String REJECT_LETTER_INTERFACE_STATUS_COMPLETE= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_COMPLETE");	
	String REJECT_LETTER_PRODUCT_NAME_CC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_CC");	
	String REJECT_LETTER_PRODUCT_NAME_KPL =InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KPL");	
	String REJECT_LETTER_PRODUCT_NAME_KEC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KEC");	
	String LETTER_NO_PK = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_LETTER_NO_PK");	
	String PARAM_CODE_CURRENT_YEAR = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PARAM_CODE_CURRENT_YEAR");	
	String REJECT_LETTER_TITLE_NAME_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_TITLE_NAME_TH");	
	String REJECT_LETTER_FIELD_ID_EN_TITLE_CODE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_FIELD_ID_EN_TITLE_CODE");	
	String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	String REJECT_LETTER_REJECT_REASON_FIELD_ID=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_REJECT_REASON_FIELD_ID");
	String NOTIFICATION_SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
	String NOTIFICATION_SEND_TO_TYPE_SELLER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String NOTIFICATION_APPLICATION_STATUS_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	String NOTIFICATION_SALE_TYPE_NORMAL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_NORMAL");
	String LANGUAGE_TH = InfBatchProperty.getInfBatchConfig("LANGUAGE_TH");
	String LANGUAGE_OTHER = InfBatchProperty.getInfBatchConfig("LANGUAGE_OTHER");
	String DEFUALT_DATA_TYPE_ALL = InfBatchProperty.getInfBatchConfig("DEFUALT_DATA_TYPE_ALL");
	String REJECT_LETTER_SEND_TIME = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SEND_TIME");
	String NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME");
	String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
	ArrayList<String> REJECT_LETTER_NOTIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_NOTIFICATION_TYPE_LIST");
	ArrayList<String> NEXTDAY_NOTIFICATION_TYPE = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_NOTIFICATION_TYPE_LIST");
	ArrayList<String> REJECT_LETTER_SPACE_PARAM_CODE = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_SPACE_PARAM_CODE");
	ArrayList<String> REJECT_LETTER_SEND_TO_SELLER_LIST = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_SEND_TO_SELLER_LIST");
	@Override
	public HashMap<String,RejectLetterDataM> selectRejectLetterInfo()throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,RejectLetterDataM> hRejectLetters = new HashMap<String,RejectLetterDataM>();
		ArrayList<String> jobStateEnd = new ArrayList<>(Arrays.asList(JOB_STATE_END.split(",")));
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT S.NOTIFICATION_TYPE,P.PRIORITY, P.SUP_PRIORITY,APP.APPLICATION_TYPE,  D.SEND_TO, APP.CARD_CODE, ");
			sql.append("  APP.APPLICATION_GROUP_ID, APP.LIFE_CYCLE,APP.APPLICATION_TEMPLATE,APP.BUSINESS_CLASS_ID,APP.APPLICATION_RECORD_ID");
			sql.append(" FROM ( SELECT DISTINCT ");
			sql.append("            AG.APPLICATION_TYPE, ");
			sql.append("            AG.APPLICATION_GROUP_ID, ");
			sql.append("            A.LIFE_CYCLE, DECODE(S_NORMAL.SALES_ID,NULL,'N','','N','Y') AS SALE_FLAG,");
			sql.append("            AG.APPLICATION_TEMPLATE, S_NORMAL.SALE_CHANNEL,");
			sql.append("            PKA_REJECT_LETTER.GET_PERSONAL_MIN_RANK_REASON(AG.APPLICATION_GROUP_ID,PS.PERSONAL_ID,A.APPLICATION_RECORD_ID) AS REASON_CODE, ");
			sql.append("            A.BUSINESS_CLASS_ID,A.APPLICATION_RECORD_ID ,CTP.CARD_CODE  ");
			sql.append("          FROM ");
			sql.append("            ORIG_APPLICATION_GROUP AG ");
			sql.append("        INNER JOIN ");
			sql.append("            (SELECT A1.PROLICY_PROGRAM_ID, A1.FINAL_APP_DECISION,A1.LIFE_CYCLE,A1.APPLICATION_GROUP_ID, ");
			sql.append("       		    A1.APPLICATION_RECORD_ID,A1.BUSINESS_CLASS_ID ");
			sql.append("                FROM ORIG_APPLICATION A1 ");
			sql.append("                WHERE A1.LIFE_CYCLE = ");
			sql.append("                    ( SELECT  MAX(LIFE_CYCLE) FROM ORIG_APPLICATION A2 ");
			sql.append("                        WHERE  A2.APPLICATION_GROUP_ID = A1.APPLICATION_GROUP_ID ");
			sql.append("                        GROUP BY A2.APPLICATION_GROUP_ID ) ) A ");
			sql.append("        ON  A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append("        INNER JOIN  ORIG_PERSONAL_RELATION R  ON  A.APPLICATION_RECORD_ID = R.REF_ID AND R.RELATION_LEVEL='"+XRULES_VER_LEVEL_APPLICATION+"' ");
			sql.append("        INNER JOIN ORIG_PERSONAL_INFO PS ON  R.PERSONAL_ID = PS.PERSONAL_ID ");
			sql.append("        LEFT JOIN ORIG_SALE_INFO S_NORMAL ON AG.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID AND S_NORMAL.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"'");
			sql.append(" 		INNER JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" 		LEFT JOIN  ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
			sql.append(" 		LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
			sql.append("        WHERE   AG.JOB_STATE IN (");
						String COMMA="";
						for(String jobState :jobStateEnd){
							sql.append(COMMA+"?");
							COMMA=",";
						}
			sql.append(")");
			sql.append("       AND A.FINAL_APP_DECISION ='"+FINAL_APP_DECISION_REJECT+"' ");
			sql.append("        AND NOT EXISTS ");
			sql.append("            ( SELECT   (1) ");
			sql.append("                FROM    INF_BATCH_LOG B ");
			sql.append("                WHERE  INTERFACE_CODE IN ('"+REJECT_LETTER_INTERFACE_CODE_T1+"','"+REJECT_LETTER_INTERFACE_CODE_T2+"','"+REJECT_LETTER_INTERFACE_CODE_T3+"') ");
			sql.append("                AND B.INTERFACE_STATUS = '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"' ");
			sql.append("                AND B.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID) ");
			sql.append(" 	AND PS.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			sql.append("    ) APP ");
			sql.append(" INNER JOIN MS_NOTI_APP_TEMPLATE T ON  T.TEMPLATE_CODE = APP.APPLICATION_TEMPLATE OR  T.TEMPLATE_CODE = 'ALL' ");
			sql.append(" INNER JOIN MS_NOTI_GROUP G ON G.GROUP_CODE =T.GROUP_CODE ");
			sql.append(" INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE ");
			sql.append(" INNER JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE ");
			sql.append(" INNER JOIN MS_NOTI_SENDING_DETAIL D ON S.SENDING_ID = D.SENDING_ID ");
			sql.append(" INNER JOIN MS_NOTI_SENDING_PRIORITY P ON  P.GROUP_CHANNEL_CODE = S.GROUP_CHANNEL_CODE AND P.SENDING_ID = S.SENDING_ID ");
			sql.append(" INNER JOIN MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE AND APP.REASON_CODE=RE.REASON_CODE ");
			//sql.append(" WHERE   S.SEND_TIME = 'EOD' AND S.NOTIFICATION_TYPE IN ('"+TemplateBuilderConstant.TemplateType.LETTER+"','"+TemplateBuilderConstant.TemplateType.EMAIL+"')");
			//sql.append(" WHERE   S.SEND_TIME = 'EOD' AND S.NOTIFICATION_TYPE IN ('"+TemplateBuilderConstant.TemplateType.LETTER+"','"+TemplateBuilderConstant.TemplateType.MAKEPDF+"')");
			sql.append(" WHERE   S.SEND_TIME = 'EOD' AND S.NOTIFICATION_TYPE IN ('"+TemplateBuilderConstant.TemplateType.LETTER+"','"+TemplateBuilderConstant.TemplateType.MAKEPDF+"','"+TemplateBuilderConstant.TemplateType.MAKEPDF_CH+"')");
			sql.append(" AND G.APPLICATION_STATUS ='"+NOTIFICATION_APPLICATION_STATUS_REJECT+"' AND D.SEND_TO  ='"+NOTIFICATION_SEND_TO_TYPE_CUSTOMER+"'  ");
			sql.append(" AND (G.HAVE_SALE_FLAG =APP.SALE_FLAG OR G.HAVE_SALE_FLAG IS NULL ) ");
			sql.append(" AND (C.CHANNEL_CODE = APP.SALE_CHANNEL OR C.CHANNEL_CODE = 'ALL' OR (C.CHANNEL_CODE = 'N' AND APP.SALE_CHANNEL IS NULL )) ");
			sql.append(" GROUP BY    S.NOTIFICATION_TYPE, P.PRIORITY,P.SUP_PRIORITY,APP.APPLICATION_TYPE, D.SEND_TO, APP.APPLICATION_RECORD_ID,");
			sql.append("    APP.APPLICATION_GROUP_ID, APP.LIFE_CYCLE, APP.APPLICATION_TEMPLATE,APP.BUSINESS_CLASS_ID ,APP.CARD_CODE");
		       		 			
			logger.debug("REJECT_LETTER_JOB_STATE_END : " + JOB_STATE_END);
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			for(String jobState :jobStateEnd){
				ps.setString(index++, jobState);
			}
			rs = ps.executeQuery();
			while(rs.next()) {				
				String NOTIFICATION_TYPE= rs.getString("NOTIFICATION_TYPE");
				String PRIORITY= rs.getString("PRIORITY");
				String SUP_PRIORITY= rs.getString("SUP_PRIORITY");
				String APPLICATION_TYPE= rs.getString("APPLICATION_TYPE");
				String APPLICATION_GROUP_ID= rs.getString("APPLICATION_GROUP_ID");
				int LIFE_CYCLE= rs.getInt("LIFE_CYCLE");
				String APPLICATION_TEMPLATE= rs.getString("APPLICATION_TEMPLATE");
				String BUSINESS_CLASS_ID= rs.getString("BUSINESS_CLASS_ID");
				String APPLICATION_RECORD_ID= rs.getString("APPLICATION_RECORD_ID");
				String CARD_CODE= rs.getString("CARD_CODE");
				String SEND_TO= rs.getString("SEND_TO");
			    
				String KEY_MAPPING =APPLICATION_TYPE+","+APPLICATION_GROUP_ID+","+APPLICATION_TEMPLATE;
				
				RejectLetterDataM rejectLetterDataM = hRejectLetters.get(KEY_MAPPING);
				if(InfBatchUtil.empty(rejectLetterDataM)){
					rejectLetterDataM =  new RejectLetterDataM();
					hRejectLetters.put(KEY_MAPPING, rejectLetterDataM);
				} 
				rejectLetterDataM.setApplicationType(APPLICATION_TYPE);
				rejectLetterDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
				rejectLetterDataM.setApplicationTemplateId(APPLICATION_TEMPLATE);
				rejectLetterDataM.setLifeCycle(LIFE_CYCLE);
				rejectLetterDataM.setSendTo(SEND_TO);
				rejectLetterDataM.setNotificationtype(NOTIFICATION_TYPE);
				if(!InfBatchUtil.empty(PRIORITY)){
					//if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.EMAIL)){
					if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF)){
						rejectLetterDataM.setEmailPriority(PRIORITY);
					}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF_CH)){
						rejectLetterDataM.setEmailPriority(PRIORITY);
					}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.LETTER)){
						rejectLetterDataM.setPaperPriority(PRIORITY);
					}
				}			
				if(!InfBatchUtil.empty(SUP_PRIORITY)){
					//if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.EMAIL)){
					if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF)){
						rejectLetterDataM.setSubEmailPriority(SUP_PRIORITY);
					}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF_CH)){
						rejectLetterDataM.setSubEmailPriority(SUP_PRIORITY);
					}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.LETTER)){
						rejectLetterDataM.setSubPaperPriority(SUP_PRIORITY);
					}
				}			
				
				RejectLetterApplicationDataM  rejectLetterApplication = new RejectLetterApplicationDataM();
				rejectLetterApplication.setApplicationRecordId(APPLICATION_RECORD_ID);
				rejectLetterApplication.setBusinessClassId(BUSINESS_CLASS_ID);
				rejectLetterApplication.setCardCode(CARD_CODE);
				rejectLetterDataM.addRejectLetterApplications(rejectLetterApplication);
		 
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
		 return hRejectLetters;
	}
	@Override
	public RejectLetterRequestDataM loadRejectLetter() throws InfBatchException{
		RejectLetterRequestDataM rejectLetterRequest = new RejectLetterRequestDataM();
			HashMap<String,RejectLetterDataM> hRejectLetters = new HashMap<String,RejectLetterDataM>();
			HashMap<String,String> priorityMap = new HashMap<String,String>();
			HashMap<String,String> sellerMap = new HashMap<String,String>();
		Connection conn = null;
		try{
			conn = getConnection();
			hRejectLetters = selectRejectLetterMap();
			for(String key : hRejectLetters.keySet()){
				RejectLetterDataM rejectLetter = hRejectLetters.get(key);
				String applicationTemplate = rejectLetter.getApplicationTemplateId();
				String reasonCode = rejectLetter.getReasonCode();
				String cashTransferType = !InfBatchUtil.empty(rejectLetter.getCashTransferType()) ? rejectLetter.getCashTransferType() : DEFUALT_DATA_TYPE_ALL ;
				String saleChannel = rejectLetter.getSaleChannel();
				String salesId = rejectLetter.getSalesId();
				// set priorityMap REJECT_LETTER
				for(String type : REJECT_LETTER_NOTIFICATION_TYPE){
					String KEY_PRIORITY_REJECT_LETTER = GenerateLetterUtil.generateKeyPriorityRejectLetter(type, REJECT_LETTER_SEND_TIME, applicationTemplate, reasonCode, cashTransferType);
					if(!priorityMap.containsKey(KEY_PRIORITY_REJECT_LETTER)){
						addNotificationPriority(type, REJECT_LETTER_SEND_TIME, applicationTemplate, reasonCode, cashTransferType, priorityMap, conn);
					}
				}
				// set priorityMap NEXTDAY
				for(String type : NEXTDAY_NOTIFICATION_TYPE){
					String KEY_PRIORITY_NEXTDAY = GenerateLetterUtil.generateKeyPriorityRejectLetter(type, NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME, applicationTemplate, reasonCode, cashTransferType);
					if(!priorityMap.containsKey(KEY_PRIORITY_NEXTDAY)){
						addNotificationPriority(type, NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME, applicationTemplate, reasonCode, cashTransferType, priorityMap, conn);
					}
				}
//				if(!InfBatchUtil.empty(saleChannel)){
				if(!InfBatchUtil.empty(saleChannel) && !InfBatchUtil.empty(salesId)){
					addNotificationSeller(applicationTemplate, saleChannel, NOTIFICATION_APPLICATION_STATUS_REJECT, reasonCode, TemplateBuilderConstant.TemplateType.MAKEPDF, REJECT_LETTER_SEND_TIME, REJECT_LETTER_SEND_TO_SELLER_LIST, sellerMap, conn);
				}
			}
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
		rejectLetterRequest.setRejectLetterMap(hRejectLetters);
		rejectLetterRequest.setPriorityMap(priorityMap);
		rejectLetterRequest.setSellerMap(sellerMap);
		return rejectLetterRequest;
	}@Override
	public RejectLetterRequestDataM loadFullRejectLetterRequest() throws InfBatchException{
		RejectLetterRequestDataM rejectLetterRequest = new RejectLetterRequestDataM();
			HashMap<String,RejectLetterDataM> hRejectLetters = new HashMap<String,RejectLetterDataM>();
			HashMap<String,String> priorityMap = new HashMap<String,String>();
		Connection conn = null;
		try{
			conn = getConnection();
			hRejectLetters = findFullRejectLetter();
			for(String key : hRejectLetters.keySet()){
				RejectLetterDataM rejectLetter = hRejectLetters.get(key);
				String applicationTemplate = rejectLetter.getApplicationTemplateId();
				String reasonCode = rejectLetter.getReasonCode();
				String cashTransferType = !InfBatchUtil.empty(rejectLetter.getCashTransferType()) ? rejectLetter.getCashTransferType() : DEFUALT_DATA_TYPE_ALL ;
				// set priorityMap REJECT_LETTER
				for(String type : REJECT_LETTER_NOTIFICATION_TYPE){
					String KEY_PRIORITY_REJECT_LETTER = GenerateLetterUtil.generateKeyPriorityRejectLetter(type, REJECT_LETTER_SEND_TIME, applicationTemplate, reasonCode, cashTransferType);
					if(!priorityMap.containsKey(KEY_PRIORITY_REJECT_LETTER)){
						addNotificationPriority(type, REJECT_LETTER_SEND_TIME, applicationTemplate, reasonCode, cashTransferType, priorityMap, conn);
					}
				}
				// set priorityMap NEXTDAY
				for(String type : NEXTDAY_NOTIFICATION_TYPE){
					String KEY_PRIORITY_NEXTDAY = GenerateLetterUtil.generateKeyPriorityRejectLetter(type, NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME, applicationTemplate, reasonCode, cashTransferType);
					if(!priorityMap.containsKey(KEY_PRIORITY_NEXTDAY)){
						addNotificationPriority(type, NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME, applicationTemplate, reasonCode, cashTransferType, priorityMap, conn);
					}
				}
			}
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
		rejectLetterRequest.setRejectLetterMap(hRejectLetters);
		rejectLetterRequest.setPriorityMap(priorityMap);
		return rejectLetterRequest;
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
	private void addNotificationSeller(String applicationTemplate,String channelCode,String applicationStatus,String reasonCode,String notificationType,String sendTime,ArrayList<String> sendToList,HashMap<String,String> sellerMap,Connection conn) throws InfBatchException{
		try{
			MasterNotificationConfigDAO dao = NotificationFactory.getMasterNotificationConfigDAO();
			NotificationCriteriaDataM notiCriteria = new NotificationCriteriaDataM();
				notiCriteria.setApplicationTemplate(applicationTemplate);
				notiCriteria.setChannelCode(channelCode);
				notiCriteria.setApplicationStatus(applicationStatus);
				notiCriteria.setReasonCode(reasonCode);
				notiCriteria.setNotificationType(notificationType);
				notiCriteria.setSendTime(sendTime);
				notiCriteria.setSendToList(sendToList);
			dao.getNotificationSeller(notiCriteria, sellerMap, conn);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
	private HashMap<String,RejectLetterDataM> selectRejectLetterMap()throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,RejectLetterDataM> hRejectLetters = new HashMap<String,RejectLetterDataM>();
		ArrayList<String> jobStateEnd = new ArrayList<>(Arrays.asList(JOB_STATE_END.split(",")));
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT S.NOTIFICATION_TYPE,P.PRIORITY, P.SUP_PRIORITY,APP.APPLICATION_TYPE,  D.SEND_TO, APP.CARD_CODE, ");
			sql.append("  APP.APPLICATION_GROUP_ID, APP.LIFE_CYCLE,APP.APPLICATION_TEMPLATE,APP.BUSINESS_CLASS_ID,APP.APPLICATION_RECORD_ID,APP.REASON_CODE,APP.CASH_TRANSFER_TYPE,APP.MOBILE_NO, APP.EMAIL_PRIMARY, APP.SALE_CHANNEL, APP.SALES_ID ");
			sql.append(" FROM ( SELECT DISTINCT ");
			sql.append("            AG.APPLICATION_TYPE, ");
			sql.append("            AG.APPLICATION_GROUP_ID, ");
			sql.append("            A.LIFE_CYCLE, DECODE(S_NORMAL.SALES_ID,NULL,'N','','N','Y') AS SALE_FLAG,");
			sql.append("            AG.APPLICATION_TEMPLATE, S_NORMAL.SALE_CHANNEL,S_NORMAL.SALES_ID,");
			sql.append("            PKA_REJECT_LETTER.GET_PERSONAL_MIN_RANK_REASON(AG.APPLICATION_GROUP_ID,PS.PERSONAL_ID,A.APPLICATION_RECORD_ID) AS REASON_CODE, ");
			sql.append("            A.BUSINESS_CLASS_ID,A.APPLICATION_RECORD_ID ,CTP.CARD_CODE, CT.CASH_TRANSFER_TYPE,PS.MOBILE_NO,PS.EMAIL_PRIMARY  ");
			sql.append("          FROM ");
			sql.append("            ORIG_APPLICATION_GROUP AG ");
//			sql.append("        INNER JOIN ");
//			sql.append("            (SELECT A1.PROLICY_PROGRAM_ID, A1.FINAL_APP_DECISION,A1.LIFE_CYCLE,A1.APPLICATION_GROUP_ID, ");
//			sql.append("       		    A1.APPLICATION_RECORD_ID,A1.BUSINESS_CLASS_ID ");
//			sql.append("                FROM ORIG_APPLICATION A1 ");
//			sql.append("                WHERE A1.LIFE_CYCLE = ");
//			sql.append("                    ( SELECT  MAX(LIFE_CYCLE) FROM ORIG_APPLICATION A2 ");
//			sql.append("                        WHERE  A2.APPLICATION_GROUP_ID = A1.APPLICATION_GROUP_ID ");
//			sql.append("                        GROUP BY A2.APPLICATION_GROUP_ID ) ) A ");
//			sql.append("        ON  A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append("	    INNER JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID AND AG.LIFE_CYCLE = A.LIFE_CYCLE  ");
			sql.append("        INNER JOIN  ORIG_PERSONAL_RELATION R  ON  A.APPLICATION_RECORD_ID = R.REF_ID AND R.RELATION_LEVEL='"+XRULES_VER_LEVEL_APPLICATION+"' ");
			sql.append("        INNER JOIN ORIG_PERSONAL_INFO PS ON  R.PERSONAL_ID = PS.PERSONAL_ID ");
			sql.append(" 		INNER JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append("		LEFT JOIN ORIG_CASH_TRANSFER CT ON CT.LOAN_ID = L.LOAN_ID ");
			sql.append(" 		LEFT JOIN  ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
			sql.append(" 		LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
			sql.append("        LEFT JOIN ORIG_SALE_INFO S_NORMAL ON AG.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID AND S_NORMAL.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"'");
			sql.append("        WHERE   AG.JOB_STATE IN (");
						String COMMA="";
						for(String jobState :jobStateEnd){
							sql.append(COMMA+"?");
							COMMA=",";
						}
			sql.append(")");
			sql.append("       AND A.FINAL_APP_DECISION ='"+FINAL_APP_DECISION_REJECT+"' ");
			sql.append("        AND NOT EXISTS ");
			sql.append("            ( SELECT   (1) ");
			sql.append("                FROM    INF_BATCH_LOG B ");
			sql.append("                WHERE  INTERFACE_CODE IN ('"+REJECT_LETTER_INTERFACE_CODE_T1+"','"+REJECT_LETTER_INTERFACE_CODE_T2+"') ");
			sql.append("                AND B.INTERFACE_STATUS = '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"' ");
			sql.append("                AND B.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append("				AND B.LIFE_CYCLE = AG.LIFE_CYCLE ) ");
			sql.append(" 	AND PS.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			sql.append("    ) APP ");
			sql.append(" INNER JOIN MS_NOTI_APP_TEMPLATE T ON  T.TEMPLATE_CODE = APP.APPLICATION_TEMPLATE OR  T.TEMPLATE_CODE = 'ALL' ");
			sql.append(" INNER JOIN MS_NOTI_GROUP G ON G.GROUP_CODE =T.GROUP_CODE ");
			sql.append(" INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE ");
			sql.append(" INNER JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE ");
			sql.append(" INNER JOIN MS_NOTI_SENDING_DETAIL D ON S.SENDING_ID = D.SENDING_ID ");
			sql.append(" INNER JOIN MS_NOTI_SENDING_PRIORITY P ON  P.GROUP_CHANNEL_CODE = S.GROUP_CHANNEL_CODE AND P.SENDING_ID = S.SENDING_ID ");
			sql.append(" INNER JOIN MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE AND APP.REASON_CODE=RE.REASON_CODE ");
			//sql.append(" WHERE   S.SEND_TIME = 'EOD' AND S.NOTIFICATION_TYPE IN ('"+TemplateBuilderConstant.TemplateType.LETTER+"','"+TemplateBuilderConstant.TemplateType.EMAIL+"')");
			sql.append(" WHERE   S.SEND_TIME = 'EOD' AND S.NOTIFICATION_TYPE IN ('"+TemplateBuilderConstant.TemplateType.LETTER+"','"+TemplateBuilderConstant.TemplateType.MAKEPDF+"')");
			//sql.append(" WHERE   S.SEND_TIME = 'EOD' AND S.NOTIFICATION_TYPE IN ('"+TemplateBuilderConstant.TemplateType.LETTER+"','"+TemplateBuilderConstant.TemplateType.MAKEPDF+"','"+TemplateBuilderConstant.TemplateType.MAKEPDF_CH+"')");
			//sql.append(" AND G.APPLICATION_STATUS ='"+NOTIFICATION_APPLICATION_STATUS_REJECT+"' AND D.SEND_TO  ='"+NOTIFICATION_SEND_TO_TYPE_CUSTOMER+"'  ");
			sql.append(" AND G.APPLICATION_STATUS ='"+NOTIFICATION_APPLICATION_STATUS_REJECT+"' ");
			sql.append(" AND (G.HAVE_SALE_FLAG =APP.SALE_FLAG OR G.HAVE_SALE_FLAG IS NULL ) ");
			sql.append(" AND (C.CHANNEL_CODE = APP.SALE_CHANNEL OR C.CHANNEL_CODE = 'ALL' OR (C.CHANNEL_CODE = 'N' AND APP.SALE_CHANNEL IS NULL )) ");
			sql.append(" GROUP BY    S.NOTIFICATION_TYPE, P.PRIORITY,P.SUP_PRIORITY,APP.APPLICATION_TYPE, D.SEND_TO, APP.APPLICATION_RECORD_ID,");
			sql.append("    APP.APPLICATION_GROUP_ID, APP.LIFE_CYCLE, APP.APPLICATION_TEMPLATE,APP.BUSINESS_CLASS_ID ,APP.CARD_CODE,APP.REASON_CODE,APP.CASH_TRANSFER_TYPE,APP.MOBILE_NO, APP.EMAIL_PRIMARY, APP.SALE_CHANNEL, APP.SALES_ID ");
			
			logger.debug("REJECT_LETTER_JOB_STATE_END : " + JOB_STATE_END);
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			for(String jobState :jobStateEnd){
				ps.setString(index++, jobState);
			}
			rs = ps.executeQuery();
			while(rs.next()) {				
				String NOTIFICATION_TYPE= rs.getString("NOTIFICATION_TYPE");
				String PRIORITY= rs.getString("PRIORITY");
				String SUP_PRIORITY= rs.getString("SUP_PRIORITY");
				String APPLICATION_TYPE= rs.getString("APPLICATION_TYPE");
				String APPLICATION_GROUP_ID= rs.getString("APPLICATION_GROUP_ID");
				int LIFE_CYCLE= rs.getInt("LIFE_CYCLE");
				String APPLICATION_TEMPLATE= rs.getString("APPLICATION_TEMPLATE");
				String BUSINESS_CLASS_ID= rs.getString("BUSINESS_CLASS_ID");
				String APPLICATION_RECORD_ID= rs.getString("APPLICATION_RECORD_ID");
				String CARD_CODE= rs.getString("CARD_CODE");
				String SEND_TO= rs.getString("SEND_TO");
				String REASON_CODE = rs.getString("REASON_CODE");
				String CASH_TRANSFER_TYPE = rs.getString("CASH_TRANSFER_TYPE");
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				String SALES_ID = rs.getString("SALES_ID");
				
				String KEY_MAPPING =APPLICATION_TYPE+","+APPLICATION_GROUP_ID+","+APPLICATION_TEMPLATE;
				
				RejectLetterDataM rejectLetterDataM = hRejectLetters.get(KEY_MAPPING);
				if(InfBatchUtil.empty(rejectLetterDataM)){
					rejectLetterDataM =  new RejectLetterDataM();
					hRejectLetters.put(KEY_MAPPING, rejectLetterDataM);
				} 
				rejectLetterDataM.setApplicationType(APPLICATION_TYPE);
				rejectLetterDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
				rejectLetterDataM.setApplicationTemplateId(APPLICATION_TEMPLATE);
				rejectLetterDataM.setLifeCycle(LIFE_CYCLE);
				//rejectLetterDataM.setSendTo(SEND_TO);
				rejectLetterDataM.setNotificationtype(NOTIFICATION_TYPE);
				rejectLetterDataM.setReasonCode(REASON_CODE);
				rejectLetterDataM.setCashTransferType(CASH_TRANSFER_TYPE);
				rejectLetterDataM.setMobileNo(MOBILE_NO);
				rejectLetterDataM.setEmailPrimary(EMAIL_PRIMARY);
				rejectLetterDataM.setSaleChannel(SALE_CHANNEL);
				rejectLetterDataM.setSalesId(SALES_ID);
				if(REJECT_LETTER_SEND_TO_SELLER_LIST.contains(SEND_TO) && InfBatchUtil.empty(rejectLetterDataM.getSendTo())){
					rejectLetterDataM.setSendTo(NOTIFICATION_SEND_TO_TYPE_SELLER);
				}else if(SEND_TO.equals(NOTIFICATION_SEND_TO_TYPE_CUSTOMER)){
					rejectLetterDataM.setSendTo(SEND_TO);
				}
				if(!InfBatchUtil.empty(PRIORITY) && SEND_TO.equals(NOTIFICATION_SEND_TO_TYPE_CUSTOMER)){
					//if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.EMAIL)){
					if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF)){
						rejectLetterDataM.setEmailPriority(PRIORITY);
					}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.LETTER)){
						rejectLetterDataM.setPaperPriority(PRIORITY);
					}
				}			
				if(!InfBatchUtil.empty(SUP_PRIORITY) && SEND_TO.equals(NOTIFICATION_SEND_TO_TYPE_CUSTOMER)){
					//if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.EMAIL)){
					if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF)){
						rejectLetterDataM.setSubEmailPriority(SUP_PRIORITY);
					}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.LETTER)){
						rejectLetterDataM.setSubPaperPriority(SUP_PRIORITY);
					}
				}			
				
				RejectLetterApplicationDataM  rejectLetterApplication = new RejectLetterApplicationDataM();
				rejectLetterApplication.setApplicationRecordId(APPLICATION_RECORD_ID);
				rejectLetterApplication.setBusinessClassId(BUSINESS_CLASS_ID);
				rejectLetterApplication.setCardCode(CARD_CODE);
				rejectLetterDataM.addRejectLetterApplications(rejectLetterApplication);
		 
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
		 return hRejectLetters;
	}
	private HashMap<String,RejectLetterDataM> findFullRejectLetter()throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,RejectLetterDataM> hRejectLetters = new HashMap<String,RejectLetterDataM>();
		HashMap<String,NotifyApplicationSegment> hAppReason = new HashMap<String,NotifyApplicationSegment>(); 
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
//			sql.append("       SELECT DISTINCT ");
//			sql.append("            AG.APPLICATION_TYPE, ");
//			sql.append("            AG.APPLICATION_GROUP_ID, ");
//			sql.append("            A.LIFE_CYCLE, DECODE(S_NORMAL.SALES_ID,NULL,'N','','N','Y') AS SALE_FLAG,");
//			sql.append("            AG.APPLICATION_TEMPLATE, S_NORMAL.SALE_CHANNEL,S_NORMAL.SALES_ID,");
//			sql.append("            A.BUSINESS_CLASS_ID,A.APPLICATION_RECORD_ID ,CTP.CARD_CODE, CT.CASH_TRANSFER_TYPE,PS.PERSONAL_ID,PS.MOBILE_NO,PS.EMAIL_PRIMARY  ");
//			sql.append("          FROM ");
//			sql.append("            ORIG_APPLICATION_GROUP AG ");
//			sql.append("	    INNER JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID AND AG.LIFE_CYCLE = A.LIFE_CYCLE  ");
//			sql.append("        INNER JOIN  ORIG_PERSONAL_RELATION R  ON  A.APPLICATION_RECORD_ID = R.REF_ID AND R.RELATION_LEVEL='"+XRULES_VER_LEVEL_APPLICATION+"' ");
//			sql.append("        INNER JOIN ORIG_PERSONAL_INFO PS ON  R.PERSONAL_ID = PS.PERSONAL_ID ");
//			sql.append(" 		INNER JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
//			sql.append("		LEFT JOIN ORIG_CASH_TRANSFER CT ON CT.LOAN_ID = L.LOAN_ID ");
//			sql.append(" 		LEFT JOIN  ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
//			sql.append(" 		LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
//			sql.append("        LEFT JOIN ORIG_SALE_INFO S_NORMAL ON AG.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID AND S_NORMAL.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"'");
//			sql.append("		WHERE AG.JOB_STATE IN ("+InfBatchProperty.getSQLParameter("REJECT_LETTER_JOB_STATE_END")+" ) ");
//			sql.append("       AND A.FINAL_APP_DECISION ='"+FINAL_APP_DECISION_REJECT+"' ");
//			sql.append("        AND NOT EXISTS ");
//			sql.append("            ( SELECT   (1) ");
//			sql.append("                FROM    INF_BATCH_LOG B ");
//			sql.append("                WHERE  INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' ");
//			sql.append("                AND B.INTERFACE_STATUS IN ( '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"' , '"+INTERFACE_STATUS_NOT_SEND+"' ) ");
//			sql.append("                AND B.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
//			sql.append("				AND B.LIFE_CYCLE = AG.LIFE_CYCLE ) ");
//			sql.append(" 	AND PS.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			sql.append("       SELECT DISTINCT ");
			sql.append("            AG.APPLICATION_TYPE, ");
			sql.append("            AG.APPLICATION_GROUP_ID, ");
			sql.append("            A.LIFE_CYCLE, DECODE(S_NORMAL.SALES_ID,NULL,'N','','N','Y') AS SALE_FLAG,");
			sql.append("            AG.APPLICATION_TEMPLATE, S_NORMAL.SALE_CHANNEL,S_NORMAL.SALES_ID,");
			sql.append("            A.BUSINESS_CLASS_ID,A.APPLICATION_RECORD_ID ,CTP.CARD_CODE, PS.PERSONAL_ID,PS.MOBILE_NO,PS.EMAIL_PRIMARY  ");
			sql.append("          FROM ");
			sql.append("            ORIG_APPLICATION_GROUP AG ");
			sql.append("	    INNER JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID AND AG.LIFE_CYCLE = A.LIFE_CYCLE  ");
			sql.append("        INNER JOIN  ORIG_PERSONAL_RELATION R  ON  A.APPLICATION_RECORD_ID = R.REF_ID AND R.RELATION_LEVEL='"+XRULES_VER_LEVEL_APPLICATION+"' ");
			sql.append("        INNER JOIN ORIG_PERSONAL_INFO PS ON  R.PERSONAL_ID = PS.PERSONAL_ID ");
			sql.append(" 		INNER JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" 		LEFT JOIN  ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
			sql.append(" 		LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
			sql.append("        LEFT JOIN ORIG_SALE_INFO S_NORMAL ON AG.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID AND S_NORMAL.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"'");
			sql.append("		WHERE AG.JOB_STATE IN ("+InfBatchProperty.getSQLParameter("REJECT_LETTER_JOB_STATE_END")+" ) ");
			sql.append("       AND A.FINAL_APP_DECISION ='"+FINAL_APP_DECISION_REJECT+"' ");
			sql.append("        AND NOT EXISTS ");
			sql.append("            ( SELECT   (1) ");
			sql.append("                FROM    INF_BATCH_LOG B ");
			sql.append("                WHERE  INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' ");
			sql.append("                AND B.INTERFACE_STATUS IN ( '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"' , '"+INTERFACE_STATUS_NOT_SEND+"' ) ");
			sql.append("                AND B.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append("				AND B.LIFE_CYCLE = AG.LIFE_CYCLE ) ");
			sql.append(" 	AND PS.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());		
			rs = ps.executeQuery();
			while(rs.next()) {				
				String APPLICATION_TYPE= rs.getString("APPLICATION_TYPE");
				String APPLICATION_GROUP_ID= rs.getString("APPLICATION_GROUP_ID");
				int LIFE_CYCLE= rs.getInt("LIFE_CYCLE");
				String APPLICATION_TEMPLATE= rs.getString("APPLICATION_TEMPLATE");
				String BUSINESS_CLASS_ID= rs.getString("BUSINESS_CLASS_ID");
				String APPLICATION_RECORD_ID= rs.getString("APPLICATION_RECORD_ID");
				String CARD_CODE= rs.getString("CARD_CODE");
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				String SALES_ID = rs.getString("SALES_ID");
				String SALE_FLAG = rs.getString("SALE_FLAG");
				String KEY_MAPPING =APPLICATION_TYPE+","+APPLICATION_GROUP_ID+","+APPLICATION_TEMPLATE;
				RejectLetterDataM rejectLetter = hRejectLetters.get(KEY_MAPPING);
				if(InfBatchUtil.empty(rejectLetter)){
					rejectLetter =  new RejectLetterDataM();
					hRejectLetters.put(KEY_MAPPING, rejectLetter);
				}
				NotifyApplicationSegment notifyApplicationSegment = hAppReason.get(APPLICATION_GROUP_ID);
				if(InfBatchUtil.empty(notifyApplicationSegment)){
					notifyApplicationSegment  = NotificationUtil.notifyApplication(APPLICATION_GROUP_ID);
					hAppReason.put(APPLICATION_GROUP_ID, notifyApplicationSegment);
				}
				Reason reason = notifyApplicationSegment.findReasonApplicationGroup(APPLICATION_GROUP_ID);
				String REASON_CODE = reason.getReasonCode();
				
				rejectLetter.setApplicationType(APPLICATION_TYPE);
				rejectLetter.setApplicationGroupId(APPLICATION_GROUP_ID);
				rejectLetter.setApplicationTemplateId(APPLICATION_TEMPLATE);
				rejectLetter.setLifeCycle(LIFE_CYCLE);
				rejectLetter.setReasonCode(REASON_CODE);
				//rejectLetter.setCashTransferType(CASH_TRANSFER_TYPE);
				rejectLetter.setCashTransferType(DEFUALT_DATA_TYPE_ALL);
				rejectLetter.setMobileNo(MOBILE_NO);
				rejectLetter.setEmailPrimary(EMAIL_PRIMARY);
				rejectLetter.setSaleChannel(SALE_CHANNEL);
				rejectLetter.setSalesId(SALES_ID);
				rejectLetter.setSaleFlag(SALE_FLAG);
				// Load notification config
				loadNotificationConfigs(rejectLetter,conn);	
				RejectLetterApplicationDataM  rejectLetterApplication = new RejectLetterApplicationDataM();
					rejectLetterApplication.setApplicationRecordId(APPLICATION_RECORD_ID);
					rejectLetterApplication.setBusinessClassId(BUSINESS_CLASS_ID);
					rejectLetterApplication.setCardCode(CARD_CODE);
					rejectLetter.addRejectLetterApplications(rejectLetterApplication);
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
		 return hRejectLetters;
	}
	private void loadNotificationConfigs(RejectLetterDataM rejectLetter,Connection conn)throws InfBatchException{
		try{
			List<NotificationConfig> notificationConfigs = selectNotificationConfigs(rejectLetter.getApplicationTemplateId(),rejectLetter.getReasonCode(),rejectLetter.getSaleFlag(),rejectLetter.getSaleChannel(),conn);
			if(!InfBatchUtil.empty(notificationConfigs)){
				for(NotificationConfig notificationConfig : notificationConfigs){
					addNotificationConfig(rejectLetter,notificationConfig);
				}
			}else{ // Case send mail when Config was not found
				addEmptyNotificationConfig(rejectLetter);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
	}
	private void addNotificationConfig(RejectLetterDataM rejectLetter,NotificationConfig notificationConfig){
		String NOTIFICATION_TYPE =  notificationConfig.getNotificationType();
		String PRIORITY = notificationConfig.getPriority();
		String SUP_PRIORITY = notificationConfig.getSubPriority();
		String SEND_TO = notificationConfig.getSendTo();
		rejectLetter.setNotificationtype(NOTIFICATION_TYPE);
		if(REJECT_LETTER_SEND_TO_SELLER_LIST.contains(SEND_TO)){
			rejectLetter.setSendTo(NOTIFICATION_SEND_TO_TYPE_SELLER);
		}else if(SEND_TO.equals(NOTIFICATION_SEND_TO_TYPE_CUSTOMER)){
			rejectLetter.setSendTo(SEND_TO);
		}
		if(!InfBatchUtil.empty(PRIORITY) && SEND_TO.equals(NOTIFICATION_SEND_TO_TYPE_CUSTOMER)){
			if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF)){
				rejectLetter.setEmailPriority(PRIORITY);
			}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.LETTER)){
				rejectLetter.setPaperPriority(PRIORITY);
			}
		}			
		if(!InfBatchUtil.empty(SUP_PRIORITY) && SEND_TO.equals(NOTIFICATION_SEND_TO_TYPE_CUSTOMER)){
			if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.MAKEPDF)){
				rejectLetter.setSubEmailPriority(SUP_PRIORITY);
			}else if(NOTIFICATION_TYPE.equals(TemplateBuilderConstant.TemplateType.LETTER)){
				rejectLetter.setSubPaperPriority(SUP_PRIORITY);
			}
		}		
	}
	private void addEmptyNotificationConfig(RejectLetterDataM rejectLetter){
		rejectLetter.setSendTo(NOTIFICATION_SEND_TO_TYPE_CUSTOMER);
		rejectLetter.setNotificationtype(TemplateBuilderConstant.TemplateType.LETTER);
		rejectLetter.setEmailPriority("");
		rejectLetter.setPaperPriority("");
	}
	private List<NotificationConfig> selectNotificationConfigs(String templateCode,String reasonCode,String saleFlag,String saleChannel,Connection conn) throws InfBatchException{
		List<NotificationConfig> notificationConfigs = new ArrayList<NotificationConfig>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder();
				sql.append(" SELECT DISTINCT MS_NOTI_SENDING.NOTIFICATION_TYPE ");
				sql.append("         , MS_NOTI_SENDING_DETAIL.SEND_TO ");
				sql.append("         , MS_NOTI_SENDING_PRIORITY.PRIORITY ");
				sql.append("         , MS_NOTI_SENDING_PRIORITY.SUP_PRIORITY ");
				sql.append(" FROM MS_NOTI_APP_TEMPLATE  ");
				sql.append(" INNER JOIN  MS_NOTI_GROUP ON MS_NOTI_GROUP.GROUP_CODE = MS_NOTI_APP_TEMPLATE.GROUP_CODE ");
				sql.append(" INNER JOIN MS_NOTI_CHANNEL ON MS_NOTI_CHANNEL.GROUP_CODE = MS_NOTI_APP_TEMPLATE.GROUP_CODE ");
				sql.append(" INNER JOIN MS_NOTI_SENDING ON MS_NOTI_SENDING.GROUP_CHANNEL_CODE = MS_NOTI_CHANNEL.GROUP_CHANNEL_CODE ");
				sql.append(" INNER JOIN MS_NOTI_SENDING_DETAIL ON MS_NOTI_SENDING.SENDING_ID = MS_NOTI_SENDING_DETAIL.SENDING_ID ");
				sql.append(" INNER JOIN MS_NOTI_SENDING_PRIORITY ON MS_NOTI_SENDING_PRIORITY.GROUP_CHANNEL_CODE = MS_NOTI_SENDING.GROUP_CHANNEL_CODE AND MS_NOTI_SENDING_PRIORITY.SENDING_ID = MS_NOTI_SENDING.SENDING_ID ");
				sql.append(" INNER JOIN MS_NOTI_REJECT_REASON ON MS_NOTI_REJECT_REASON.GROUP_CODE = MS_NOTI_GROUP.GROUP_CODE  ");
				sql.append(" WHERE (MS_NOTI_APP_TEMPLATE.TEMPLATE_CODE = ? OR  MS_NOTI_APP_TEMPLATE.TEMPLATE_CODE = '"+DEFUALT_DATA_TYPE_ALL+"') ");
				sql.append(" AND MS_NOTI_REJECT_REASON.REASON_CODE = ? ");
				sql.append(" AND MS_NOTI_SENDING.SEND_TIME = '"+REJECT_LETTER_SEND_TIME+"' ");
				sql.append(" AND MS_NOTI_SENDING.NOTIFICATION_TYPE IN ("+InfBatchProperty.getSQLParameter("REJECT_LETTER_NOTIFICATION_TYPE_LIST")+" ) ");
				sql.append(" AND MS_NOTI_GROUP.APPLICATION_STATUS ='"+NOTIFICATION_APPLICATION_STATUS_REJECT+"' ");
				//sql.append(" AND (  MS_NOTI_GROUP.HAVE_SALE_FLAG = ? OR  MS_NOTI_GROUP.HAVE_SALE_FLAG IS NULL ) ");
				sql.append(" AND ( MS_NOTI_CHANNEL.CHANNEL_CODE = ? ");
				sql.append("         OR  MS_NOTI_CHANNEL.CHANNEL_CODE = '"+DEFUALT_DATA_TYPE_ALL+"' ");
				sql.append("         OR  ( MS_NOTI_CHANNEL.CHANNEL_CODE = 'N' AND ? IS NULL ) ");
				sql.append(" 	 ) ");
				if(!InfBatchUtil.empty(saleFlag)){
					sql.append("  AND MS_NOTI_GROUP.HAVE_SALE_FLAG = ? ");
				}
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
				ps.setString(index++,templateCode);
				ps.setString(index++,reasonCode);
				//ps.setString(index++,saleFlag);
				ps.setString(index++,saleChannel);
				ps.setString(index++,saleChannel);
				if(!InfBatchUtil.empty(saleFlag)){
					ps.setString(index++,saleFlag);
				}
			logger.debug("templateCode : "+templateCode);
			logger.debug("reasonCode : "+reasonCode);
			logger.debug("saleFlag : "+saleFlag);
			logger.debug("saleChannel : "+saleChannel);
			rs = ps.executeQuery();
			while(rs.next()){
				String NOTIFICATION_TYPE = rs.getString("NOTIFICATION_TYPE");
				String SEND_TO = rs.getString("SEND_TO");
				String PRIORITY = rs.getString("PRIORITY");
				String SUP_PRIORITY = rs.getString("SUP_PRIORITY");
				NotificationConfig notificationConfig = new NotificationConfig();
					notificationConfig.setNotificationType(NOTIFICATION_TYPE);
					notificationConfig.setSendTo(SEND_TO);
					notificationConfig.setPriority(PRIORITY);
					notificationConfig.setSubPriority(SUP_PRIORITY);
				notificationConfigs.add(notificationConfig);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
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
	@Override
	public HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>> selectTemplateResonInfo(RejectLetterDataM rejectLetterDataM,NotifyApplicationSegment notifyApplicationSegment) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,ArrayList<TemplateReasonCodeDetailDataM>> hRejectLetterInfos = new HashMap<String,ArrayList<TemplateReasonCodeDetailDataM>>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();

//			 sql.append(" 	SELECT  AG.APPLICATION_GROUP_ID, PS.NATIONALITY, PS.PERSONAL_TYPE, ");
//			 sql.append(" 	A.PROLICY_PROGRAM_ID, A.FINAL_APP_DECISION , A.APPLICATION_RECORD_ID,PS.PERSONAL_ID, ");
//			 sql.append(" 	PKA_REJECT_LETTER.GET_PERSONAL_MIN_RANK_REASON(AG.APPLICATION_GROUP_ID,PS.PERSONAL_ID, ");
//			 sql.append(" 	A.APPLICATION_RECORD_ID) AS REASON_CODE,PS.PERSONAL_ID,A.BUSINESS_CLASS_ID,CTP.CARD_CODE  ");
//			 sql.append(" 	FROM  ORIG_APPLICATION_GROUP AG ");
//			 sql.append(" 	INNER JOIN  ORIG_APPLICATION A ON  A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
//			 sql.append(" 	INNER JOIN  ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL='A' ");
//			 sql.append(" 	INNER JOIN ORIG_PERSONAL_INFO PS ON  PR.PERSONAL_ID = PS.PERSONAL_ID ");
//			 sql.append(" 	INNER JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
//			 sql.append(" 	LEFT JOIN  ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
//			 sql.append(" 	LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
//			 sql.append(" 	WHERE  AG.APPLICATION_GROUP_ID =?  AND A.LIFE_CYCLE=? ");
			 sql.append(" 	SELECT  AG.APPLICATION_GROUP_ID, PS.NATIONALITY, PS.PERSONAL_TYPE, ");
			 sql.append(" 	A.PROLICY_PROGRAM_ID, A.FINAL_APP_DECISION , A.APPLICATION_RECORD_ID,PS.PERSONAL_ID, ");
			 sql.append(" 	PS.PERSONAL_ID,A.BUSINESS_CLASS_ID,CTP.CARD_CODE  ");
			 sql.append(" 	FROM  ORIG_APPLICATION_GROUP AG ");
			 sql.append(" 	INNER JOIN  ORIG_APPLICATION A ON  A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			 sql.append(" 	INNER JOIN  ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL='A' ");
			 sql.append(" 	INNER JOIN ORIG_PERSONAL_INFO PS ON  PR.PERSONAL_ID = PS.PERSONAL_ID ");
			 sql.append(" 	INNER JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			 sql.append(" 	LEFT JOIN  ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
			 sql.append(" 	LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
			 sql.append(" 	WHERE  AG.APPLICATION_GROUP_ID =?  AND A.LIFE_CYCLE=? ");
			 if(!rejectLetterDataM.getProducts().contains(REJECT_LETTER_PRODUCT_NAME_KPL)){
				 if(!ServiceUtil.empty(rejectLetterDataM.getCardCodes())){
					 sql.append(" 	AND  CTP.CARD_CODE  IN (");
					 String COMMA="";
					 for(String cardCode : rejectLetterDataM.getCardCodes()){
						 sql.append(COMMA+"?");
						 COMMA=",";
					 }
					 sql.append(")");
				 }
			 }
			 
			 sql.append(" 	AND  A.BUSINESS_CLASS_ID  IN (");
			 String COMMA1="";
			 for(String busClass : rejectLetterDataM.getBusinessClassList()){
				 sql.append(COMMA1+"?");
				 COMMA1=",";
			 }
			 sql.append(")");
			 
			logger.debug("sql : " + sql);
			logger.debug("rejectLetterDataM.getApplicationGroupId() : " + rejectLetterDataM.getApplicationGroupId());
			logger.debug("rejectLetterDataM.getLifeCycle() : " + rejectLetterDataM.getLifeCycle());
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++, rejectLetterDataM.getApplicationGroupId());
			ps.setInt(index++, rejectLetterDataM.getLifeCycle());
			 if(!rejectLetterDataM.getProducts().contains(REJECT_LETTER_PRODUCT_NAME_KPL)){
				 if(!ServiceUtil.empty(rejectLetterDataM.getCardCodes())){
					 for(String cardCode : rejectLetterDataM.getCardCodes()){
						 ps.setString(index++, cardCode);
						 logger.debug("cardCode : " + cardCode);
					 } 
				 }
			 }
			 
			 for(String busClass : rejectLetterDataM.getBusinessClassList()){
				 ps.setString(index++, busClass);
				 logger.debug("busClass : " + busClass);
			 }
			 
			rs = ps.executeQuery();
			while(rs.next()) {			
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String NATIONALITY = rs.getString("NATIONALITY");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				//String REASON_CODE = rs.getString("REASON_CODE");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String CARD_CODE = rs.getString("CARD_CODE");
				if(null==CARD_CODE)CARD_CODE="";
				String key = BUSINESS_CLASS_ID+"_"+CARD_CODE;
				String language = !LANGUAGE_TH.equals(NATIONALITY) ? LANGUAGE_OTHER : NATIONALITY ; 
				Reason reason = notifyApplicationSegment.findReasonPerson(PERSONAL_ID);
				String REASON_CODE = reason.getReasonCode();
				
				ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodeDetails =  hRejectLetterInfos.get(key);
				if(ServiceUtil.empty(templateReasonCodeDetails)){
					templateReasonCodeDetails = new ArrayList<TemplateReasonCodeDetailDataM>();
					hRejectLetterInfos.put(key, templateReasonCodeDetails);
				}
				TemplateReasonCodeDetailDataM templateReasonCodeDetailDataM = new TemplateReasonCodeDetailDataM();
				templateReasonCodeDetailDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
				templateReasonCodeDetailDataM.setLanguage(language);
				templateReasonCodeDetailDataM.setPersonalType(PERSONAL_TYPE);
				templateReasonCodeDetailDataM.setPolicyProgramId(PROLICY_PROGRAM_ID);
				templateReasonCodeDetailDataM.setFinalDecision(FINAL_APP_DECISION);
				templateReasonCodeDetailDataM.setApplicationRecordId(APPLICATION_RECORD_ID);
				templateReasonCodeDetailDataM.setPersonalId(PERSONAL_ID);
				templateReasonCodeDetailDataM.setCardCode(CARD_CODE);
				templateReasonCodeDetailDataM.setReasonCode(REASON_CODE);
				templateReasonCodeDetailDataM.setBusinessClassId(BUSINESS_CLASS_ID);
				templateReasonCodeDetails.add(templateReasonCodeDetailDataM);
				logger.debug(" info : reason >> "+key+" ==> "+templateReasonCodeDetailDataM.getReasonCode()+ " ==> "+APPLICATION_GROUP_ID);
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
		 return hRejectLetterInfos;
	}
	
	
	@Override
	public boolean isExsitingInterfaceBatch(String applicationRecordId)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count=0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(1) AS C_LETTER ");
			sql.append(" FROM  INF_BATCH_LOG");
			sql.append(" WHERE APPLICATION_RECORD_ID =? ");
			//sql.append(" AND INTERFACE_CODE IN ('"+REJECT_LETTER_INTERFACE_CODE_T1+"','"+REJECT_LETTER_INTERFACE_CODE_T2+"')");
			sql.append(" AND INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' ");
			 
			logger.debug("sql : " + sql);
			logger.debug("applicationRecordId : "+applicationRecordId);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++,applicationRecordId); 			
			rs = ps.executeQuery();
			if(rs.next()) {
				count =rs.getInt("C_LETTER");
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
		 return count>0;
	}
	
	@Override
	public String getTemplateCode(String reasonCode) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String templateCode="";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TEMPLATE_CODE ");
			sql.append(" FROM  MS_REJECT_LETTER_REASON ");
			sql.append(" WHERE REASON_CODE =? ");
			logger.debug("sql : " + sql);
			logger.debug("reasonCode : "+reasonCode);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++,reasonCode); 			
			rs = ps.executeQuery();
			if(rs.next()) {
				templateCode = rs.getString("TEMPLATE_CODE");
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
		return templateCode;
	}
	
	
	@Override
	public String generateLetterNo(String langauge) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String letterNo="";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_REJECT_LETTER.GET_LETTER_NO('"+langauge+"') AS LETTER_NO  FROM DUAL ");			 
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());				
			rs = ps.executeQuery();
			if(rs.next()) {
				letterNo = rs.getString("LETTER_NO");
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
		return letterNo;
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
	public ArrayList<RejectTemplateVariableDataM>getRejectTemplate1Values(RejectLetterProcessDataM rejectLetterProcess)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RejectTemplateVariableDataM> rejectTemplateVariables = new ArrayList<RejectTemplateVariableDataM>();
		String applicationgroupId =rejectLetterProcess.getApplicationGroupId();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT AG.APPLICATION_GROUP_ID, A.APPLICATION_RECORD_ID,");
			sql.append(" AG.APPLICATION_GROUP_NO, P.TH_FIRST_NAME || ' ' || P.TH_LAST_NAME  AS PERSONAL_NAME ,");
			sql.append(" P.EN_FIRST_NAME || ' ' || P.EN_LAST_NAME  AS PERSONAL_NAME_EN , ");
			sql.append(" A.BUSINESS_CLASS_ID, DECODE(NATIONALITY,'"+RejectLetterUtil.TH+"','"+REJECT_LETTER_TITLE_NAME_TH+"',EN_TITLE_DESC) AS TITLE_NAME,");
			sql.append(" P.PERSONAL_TYPE, P.EMAIL_PRIMARY,P.NATIONALITY, P.IDNO,P.PERSONAL_ID, ");
			sql.append(" PKA_REJECT_LETTER.GET_ADDRESS(P.PERSONAL_ID,AG.APPLICATION_TYPE) AS  ADDRESS,");
			sql.append(" PKA_REJECT_LETTER.GET_ZIPCODE (P.PERSONAL_ID, AG.APPLICATION_GROUP_ID) AS ZIPCODE, ");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME,");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT_TH_EN(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID) AS PRODUCT_NAME_TH_EN,");
			sql.append(" TO_CHAR(A.FINAL_APP_DECISION_DATE, 'dd/mm/yyyy')AS LAST_DECISION_DATE");
			sql.append(" ,P.CIS_NO AS CIS_NO ");	// Add for EDOCGen
			sql.append(" ,P.TH_FIRST_NAME, P.TH_MID_NAME, P.TH_LAST_NAME ");	// Add for EDOCGen
			sql.append(" ,A.LETTER_CHANNEL ");	// Add for EDOCGen
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" LEFT JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID");
			sql.append(" LEFT JOIN ORIG_LOAN  LN ON LN.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");	
			sql.append(" LEFT JOIN ORIG_CARD  CD ON CD.LOAN_ID = LN.LOAN_ID ");	
			sql.append(" INNER JOIN  (SELECT  PERSONAL_ID,APPLICATION_GROUP_ID,EN_TITLE_DESC,EN_FIRST_NAME ,EN_LAST_NAME, TH_FIRST_NAME, TH_MID_NAME, TH_LAST_NAME, PERSONAL_TYPE,EMAIL_PRIMARY,NATIONALITY ,IDNO, CIS_NO");	// Include CIS_NO for EDOCGen
			sql.append(" 			FROM ORIG_PERSONAL_INFO WHERE PERSONAL_ID  IN ( ");
						String commaPersonal="";
						for(String personalId :rejectLetterProcess.getPerosnalIds()){
							sql.append(commaPersonal+"'"+personalId+"'"	);
							commaPersonal=",";
						}
						sql.append("	)	) P  ON  P.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID");
			sql.append(" WHERE NOT EXISTS (SELECT 1 FROM   INF_BATCH_LOG B  ");
			//sql.append(" 	   WHERE  B.INTERFACE_CODE IN ('"+REJECT_LETTER_INTERFACE_CODE_T1+"','"+REJECT_LETTER_INTERFACE_CODE_T2+"')");
			sql.append(" 	   WHERE  B.INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' ");
			sql.append(" 	   AND  B.INTERFACE_STATUS = '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"'");
			sql.append(" 	   AND  B.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID )");
			sql.append(" AND  A.LIFE_CYCLE =?");
			sql.append(" AND AG.APPLICATION_GROUP_ID =?");
			sql.append(" AND A.FINAL_APP_DECISION =?");
			sql.append(" AND A.BUSINESS_CLASS_ID IN (");
			String commaBusClass="";
			for(String busClass : rejectLetterProcess.getBusinessClassIds()){
				sql.append(commaBusClass+"'"+busClass+"'");
				commaBusClass=",";
			}
			sql.append(" )");
			
			logger.debug("sql>>>"+sql);
			logger.debug("LifeCycle : "+rejectLetterProcess.getLifeCycle());
			logger.debug("applicationgroupId>>>"+applicationgroupId);
			logger.debug("FINAL_APP_DECISION_REJECT>>>"+FINAL_APP_DECISION_REJECT);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setInt(index++,rejectLetterProcess.getLifeCycle()); 			
			ps.setString(index++,applicationgroupId); 			
			ps.setString(index++, FINAL_APP_DECISION_REJECT); 	

			rs = ps.executeQuery();
			while(rs.next()) {
					String ADDRESS_LINE1="";
					String ADDRESS_LINE2="";
					String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
					String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
					String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
					String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
					String TITLE_NAME = rs.getString("TITLE_NAME");
					String PERSONAL_NAME = rs.getString("PERSONAL_NAME");
					String PERSONAL_NAME_EN = rs.getString("PERSONAL_NAME_EN");
					String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
					String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
					String NATIONALITY = rs.getString("NATIONALITY");
					String ADDRESS = rs.getString("ADDRESS");
					String ZIPCODE = rs.getString("ZIPCODE");
					String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
					String PRODUCT_NAME_TH_EN = rs.getString("PRODUCT_NAME_TH_EN");
					String LAST_DECISION_DATE = rs.getString("LAST_DECISION_DATE");
					String IDNO = rs.getString("IDNO");
					String PERSONAL_ID = rs.getString("PERSONAL_ID");
					String CIS_NO = rs.getString("CIS_NO");
					String TH_FIRST_NAME = rs.getString("TH_FIRST_NAME");
					String TH_MID_NAME = rs.getString("TH_MID_NAME");
					String TH_LAST_NAME = rs.getString("TH_LAST_NAME");
					String LETTER_CHANNEL = rs.getString("LETTER_CHANNEL");
					if(!InfBatchUtil.empty(ADDRESS)){
						String[] ADD_STR = ADDRESS.split("_");
						ADDRESS_LINE1 = ADD_STR.length>0?ADD_STR[0]:"";
						ADDRESS_LINE2 = ADD_STR.length>0?ADD_STR[1]:"";
					}
					RejectTemplateVariableDataM rejectTemplateVariableDataM = new RejectTemplateVariableDataM();
					rejectTemplateVariableDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
					rejectTemplateVariableDataM.setApplicationGroupNo(APPLICATION_GROUP_NO);
					rejectTemplateVariableDataM.setTitleName(TITLE_NAME);
					rejectTemplateVariableDataM.setPersonalNameTh(PERSONAL_NAME);
					rejectTemplateVariableDataM.setPersonalNameEn(PERSONAL_NAME_EN);
					rejectTemplateVariableDataM.setPersonalType(PERSONAL_TYPE);
					rejectTemplateVariableDataM.setEmailPrimary(EMAIL_PRIMARY);
					rejectTemplateVariableDataM.setNationality(NATIONALITY);
					rejectTemplateVariableDataM.setAddressLine1(ADDRESS_LINE1);
					rejectTemplateVariableDataM.setAddressLine2(ADDRESS_LINE2);
					rejectTemplateVariableDataM.setZipcode(ZIPCODE);
					rejectTemplateVariableDataM.setProductName(PRODUCT_NAME);
					rejectTemplateVariableDataM.setProductNameThEn(PRODUCT_NAME_TH_EN);
					rejectTemplateVariableDataM.setFinalDecisionDate(LAST_DECISION_DATE);
					rejectTemplateVariableDataM.setIdNo(IDNO);
					rejectTemplateVariableDataM.setApplicationRecordId(APPLICATION_RECORD_ID);
					rejectTemplateVariableDataM.setProductType(rejectLetterProcess.getProduct());
					rejectTemplateVariableDataM.setBusinessClassId(BUSINESS_CLASS_ID);
					rejectTemplateVariableDataM.setPersonalId(PERSONAL_ID);
					rejectTemplateVariableDataM.setCisNo(CIS_NO);
					rejectTemplateVariableDataM.setThFirstName(TH_FIRST_NAME);
					rejectTemplateVariableDataM.setThMidName(TH_MID_NAME);
					rejectTemplateVariableDataM.setThLastName(TH_LAST_NAME);
					rejectTemplateVariables.add(rejectTemplateVariableDataM);
					rejectTemplateVariableDataM.setLetterChannel(LETTER_CHANNEL); //KPL Additional
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
	public ArrayList<RejectTemplateVariableDataM> getNewTemplate4Values(RejectLetterProcessDataM rejectLetterProcess) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RejectTemplateVariableDataM> rejectTemplateVariables = new ArrayList<RejectTemplateVariableDataM>();
		String applicationgroupId =rejectLetterProcess.getApplicationGroupId();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT AG.APPLICATION_GROUP_ID, A.APPLICATION_RECORD_ID,");
			sql.append(" DECODE(NATIONALITY,'"+RejectLetterUtil.TH+"','"+REJECT_LETTER_TITLE_NAME_TH+"',EN_TITLE_DESC) AS TITLE_NAME,");
			sql.append(" AG.APPLICATION_GROUP_NO, P.TH_FIRST_NAME || ' ' || P.TH_LAST_NAME  AS PERSONAL_NAME ,");
			sql.append(" P.EN_FIRST_NAME || ' ' || P.EN_LAST_NAME  AS PERSONAL_NAME_EN ,");
			sql.append(" B.INTERFACE_LOG_ID, A.BUSINESS_CLASS_ID,");
			sql.append(" P.PERSONAL_TYPE, P.EMAIL_PRIMARY,P.NATIONALITY, P.IDNO, ");
			sql.append(" PKA_REJECT_LETTER.GET_ADDRESS(P.PERSONAL_ID,AG.APPLICATION_TYPE) AS  ADDRESS,");
			sql.append(" PKA_REJECT_LETTER.GET_ZIPCODE(P.PERSONAL_ID,AG.APPLICATION_GROUP_ID) AS  ZIPCODE,");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME,");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT_TH_EN(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID) AS PRODUCT_NAME_TH_EN,");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT_2LANG(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME_2LANG,");
			sql.append(" PKA_REJECT_LETTER.GET_REJECT_REASON(A.APPLICATION_RECORD_ID,NATIONALITY) AS REASON_CODE_DESC,");
			sql.append(" TO_CHAR(A.FINAL_APP_DECISION_DATE, 'dd/mm/yyyy')AS FINAL_APP_DECISION_DATE");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" LEFT JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID");
			sql.append(" LEFT JOIN ORIG_LOAN  LN ON LN.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");	
			sql.append(" LEFT JOIN ORIG_CARD  CD ON CD.LOAN_ID = LN.LOAN_ID ");	
			sql.append(" LEFT JOIN INF_BATCH_LOG B ON A.APPLICATION_RECORD_ID = B.APPLICATION_RECORD_ID ");		
			//sql.append(" AND B.INTERFACE_CODE IN ('"+REJECT_LETTER_INTERFACE_CODE_T1+"','"+REJECT_LETTER_INTERFACE_CODE_T2+"')");
			sql.append(" AND B.INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' ");
			sql.append(" AND B.INTERFACE_STATUS = '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"'");
			sql.append(" INNER JOIN  (SELECT  PERSONAL_ID,APPLICATION_GROUP_ID,EN_TITLE_DESC,EN_FIRST_NAME ,EN_LAST_NAME, TH_FIRST_NAME,TH_LAST_NAME, PERSONAL_TYPE,EMAIL_PRIMARY,NATIONALITY ,IDNO ");
			sql.append(" 	   FROM ORIG_PERSONAL_INFO WHERE PERSONAL_ID  IN ( ");
						String commaPersonal="";
						for(String personalId :rejectLetterProcess.getPerosnalIds()){
							sql.append(commaPersonal+"'"+personalId+"'"	);
							commaPersonal=",";
						}
						sql.append("	)	) P  ON  P.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID");
			sql.append(" WHERE A.LIFE_CYCLE =?");
			sql.append(" AND AG.APPLICATION_GROUP_ID =?");
			sql.append(" AND A.FINAL_APP_DECISION =?");
			sql.append(" AND A.BUSINESS_CLASS_ID IN (");
			String commaBusClass="";
			for(String busClass : rejectLetterProcess.getBusinessClassIds()){
				sql.append(commaBusClass+"'"+busClass+"'");
				commaBusClass=",";
			}
			sql.append(" )");
						
			logger.debug("sql>>>"+sql);
			logger.debug("LIFE_CYCLE : "+rejectLetterProcess.getLifeCycle());
			logger.debug("applicationgroupId>>>"+applicationgroupId);
			logger.debug("FINAL_APP_DECISION_REJECT>>>"+FINAL_APP_DECISION_REJECT);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setInt(index++,rejectLetterProcess.getLifeCycle()); 			
			ps.setString(index++,applicationgroupId); 			
			ps.setString(index++, FINAL_APP_DECISION_REJECT); 	
			rs = ps.executeQuery();
			while(rs.next()) {
//				String INTERFACE_LOG_ID = rs.getString("INTERFACE_LOG_ID");
//				if(InfBatchUtil.empty(INTERFACE_LOG_ID)){
					String ADDRESS_LINE1="";
					String ADDRESS_LINE2="";
					String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
					String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
					String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
					String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
					String REASON_CODE_DESC = rs.getString("REASON_CODE_DESC");
					String TITLE_NAME = rs.getString("TITLE_NAME");
					String PERSONAL_NAME_EN = rs.getString("PERSONAL_NAME_EN");
					String PERSONAL_NAME = rs.getString("PERSONAL_NAME");
					String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
					String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
					String NATIONALITY = rs.getString("NATIONALITY");
					String ADDRESS = rs.getString("ADDRESS");
					String ZIPCODE = rs.getString("ZIPCODE");
					String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
					String PRODUCT_NAME_TH_EN = rs.getString("PRODUCT_NAME_TH_EN");
					String PRODUCT_NAME_2LANG = rs.getString("PRODUCT_NAME_2LANG");
					String FINAL_APP_DECISION_DATE = rs.getString("FINAL_APP_DECISION_DATE");
					String IDNO = rs.getString("IDNO");
					if(!InfBatchUtil.empty(ADDRESS)){
						String[] ADD_STR = ADDRESS.split("_");
						ADDRESS_LINE1 = ADD_STR.length>0?ADD_STR[0]:"";
						ADDRESS_LINE2 = ADD_STR.length>1?ADD_STR[1]:"";
					}
					logger.debug("REASON_CODE>>"+REASON_CODE_DESC);
					
					RejectTemplateVariableDataM rejectTemplateVariableDataM = new RejectTemplateVariableDataM();
					rejectTemplateVariableDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
					rejectTemplateVariableDataM.setApplicationGroupNo(APPLICATION_GROUP_NO);
					rejectTemplateVariableDataM.setTitleName(TITLE_NAME);
					rejectTemplateVariableDataM.setPersonalNameTh(PERSONAL_NAME);
					rejectTemplateVariableDataM.setPersonalNameEn(PERSONAL_NAME_EN);
					rejectTemplateVariableDataM.setPersonalType(PERSONAL_TYPE);
					rejectTemplateVariableDataM.setEmailPrimary(EMAIL_PRIMARY);
					rejectTemplateVariableDataM.setNationality(NATIONALITY);
					rejectTemplateVariableDataM.setAddressLine1(ADDRESS_LINE1);
					rejectTemplateVariableDataM.setAddressLine2(ADDRESS_LINE2);
					rejectTemplateVariableDataM.setZipcode(ZIPCODE);
					rejectTemplateVariableDataM.setProductName(PRODUCT_NAME);
					rejectTemplateVariableDataM.setProductNameThEn(PRODUCT_NAME_TH_EN);
					rejectTemplateVariableDataM.setProductName2lang(PRODUCT_NAME_2LANG);
					rejectTemplateVariableDataM.setFinalDecisionDate(FINAL_APP_DECISION_DATE);
					rejectTemplateVariableDataM.setIdNo(IDNO);
					rejectTemplateVariableDataM.setApplicationRecordId(APPLICATION_RECORD_ID);
					rejectTemplateVariableDataM.setProductType(rejectLetterProcess.getProduct());
					rejectTemplateVariableDataM.setRejectReasonAllProduct(REASON_CODE_DESC);
					rejectTemplateVariableDataM.setBusinessClassId(BUSINESS_CLASS_ID);
					rejectTemplateVariables.add(rejectTemplateVariableDataM);
				}	
//			}
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
	public String getRejectDocumentList(String applicationGroupId,String personalType)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String documentList = "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  PKA_REJECT_LETTER.GET_DOCUMENT_CHECK_LIST(?,?) AS DOCUMENT_LIST  FROM DUAL");
			logger.debug("sql>>>"+sql);
			logger.debug("applicationgroupId>>>"+applicationGroupId);	 
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++,applicationGroupId); 			
			ps.setString(index++,personalType); 			
			rs = ps.executeQuery();
			if(rs.next()) {
				documentList = rs.getString("DOCUMENT_LIST");
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
		return documentList;
	}
	@Override
	public String getRejectDocumentListNotComplete(String personalId, String personalType)throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String documentList = "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  PKA_REJECT_LETTER.GET_DOCUMENT_LIST_NOT_COMPLETE(?,?) AS DOCUMENT_LIST  FROM DUAL");
			logger.debug("sql : "+sql);
			logger.debug("personalId : "+personalId);
			logger.debug("personalType : "+personalType);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++,personalId); 			
			ps.setString(index++,personalType); 			
			rs = ps.executeQuery();
			if(rs.next()){
				documentList = rs.getString("DOCUMENT_LIST");
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
		return documentList;
	}
	@Override
	public void resetLetterNOSequences() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		boolean isResetSequences=false;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  TO_CHAR(SYSDATE,'yyyy') AS CURREN_YEAR ,PARAM_VALUE AS PARAM_YEAR  ");
			sql.append(" FROM GENERAL_PARAM ");
			sql.append(" WHERE PARAM_CODE =? ");
			logger.debug("sql>>>"+sql);	 
			logger.debug("PARAM_CODE_CURRENT_YEAR>>>"+PARAM_CODE_CURRENT_YEAR);	 
			ps = conn.prepareStatement(sql.toString());		
			ps.setString(1,PARAM_CODE_CURRENT_YEAR); 				
			rs = ps.executeQuery();
			if(rs.next()) {
				String CURREN_YEAR = rs.getString("CURREN_YEAR");
				String PARAM_YEAR = rs.getString("PARAM_YEAR");
				if(!CURREN_YEAR.equals(PARAM_YEAR)){
					isResetSequences=true;
				}
			}
			
			logger.debug("isResetSequences>>"+isResetSequences);
			if(isResetSequences){
				sql = new StringBuilder();
				sql.append("{CALL PKA_REJECT_LETTER.RESET_SEQUENCES(?, ?)}");
				cs = conn.prepareCall(sql.toString());
				cs.setString("P_SEQ_NAME", LETTER_NO_PK);
				cs.setInt("START_NEW_NUMBER_SEQ", 1);
				rs  = cs.executeQuery();
				logger.debug("sql>>"+sql.toString());
				
				sql = new StringBuilder();
				sql.append(" UPDATE GENERAL_PARAM SET PARAM_VALUE = TO_CHAR(SYSDATE,'yyyy') ");
				sql.append(" WHERE  PARAM_CODE =?");
				logger.debug("sql>>"+sql.toString());
				ps = conn.prepareStatement(sql.toString());	
				ps.setString(1,PARAM_CODE_CURRENT_YEAR); 
				ps.executeUpdate();
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
				if (cs != null) {
					try {
						cs.close();
					} catch (Exception e) {
						logger.fatal("ERROR ",e);
					}
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}		
		}  
	}
	@Override
	public String minRankReasonCode(String applicationRecordId,String applicationGroupId) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String reasonCode = "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_NOTIFICATION.GET_MIN_RANK_REASON_CODE(?,?) AS REASON_CODE FROM  DUAL");
			logger.debug("sql>>>"+sql);
			logger.debug("applicationgroupId>>>"+applicationGroupId);	 
			logger.debug("applicationRecordId>>>"+applicationRecordId);	 
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++,applicationGroupId); 			
			ps.setString(index++,applicationRecordId); 			
			rs = ps.executeQuery();
			if(rs.next()) {
				reasonCode = rs.getString("REASON_CODE");
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
		return reasonCode;	
	}
	
	@Override
	public int getSenddingTimeOfCustomer(String applicationGroupId,String contactType)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int sendTime = 0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(1) AS NOTI_TIME FROM ORIG_CONTACT_LOG  WHERE APPLICATION_GROUP_ID =? AND CONTACT_TYPE <> ?");
			sql.append(" AND  ORIG_CONTACT_LOG.TEMPLATE_NAME NOT LIKE '%APPROVE%'");
			logger.debug("sql>>>"+sql);
			logger.debug("applicationgroupId>>>"+applicationGroupId);	 
			ps = conn.prepareStatement(sql.toString());		
			ps.setString(1,applicationGroupId); 			
			ps.setString(2,contactType); 			
			rs = ps.executeQuery();
			if(rs.next()) {
				sendTime = rs.getInt("NOTI_TIME");
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
		return sendTime;
		
	}

	@Override
	public HashMap<String, String> getRejectLetterSpace()throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> hSpace = new HashMap<String, String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			String comma = "";
			sql.append(" SELECT PARAM_CODE, PARAM_VALUE FROM GENERAL_PARAM WHERE PARAM_CODE IN ( ");
				for(String code : REJECT_LETTER_SPACE_PARAM_CODE){
					sql.append(comma)
					   .append(" ? ");
					comma = ",";
				}
			sql.append(" ) ");
			logger.debug("sql : " + sql);
			logger.debug("REJECT_LETTER_SPACE_PARAM_CODE : " + REJECT_LETTER_SPACE_PARAM_CODE);
			ps = conn.prepareStatement(sql.toString());
				int index = 1;
				for(String code : REJECT_LETTER_SPACE_PARAM_CODE){
					ps.setString(index++, code);
				}
			rs = ps.executeQuery();
			while(rs.next()) {
				String paramCode = rs.getString("PARAM_CODE");
				String paramValue = rs.getString("PARAM_VALUE");
				hSpace.put(paramCode, paramValue);
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
		logger.debug("hSpace : "+hSpace);
		return hSpace;
	}
	
	public HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>> selectTemplateResonInfoByProduct(RejectLetterDataM rejectLetterDataM, String product) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,ArrayList<TemplateReasonCodeDetailDataM>> hRejectLetterInfos = new HashMap<String,ArrayList<TemplateReasonCodeDetailDataM>>();
		try{
			 ArrayList<RejectLetterApplicationDataM> rejectLetterApplications = rejectLetterDataM.getRejectLetterApplications();
			 ArrayList<String> cardCodes = new ArrayList<String>();
			 ArrayList<String> businessClasses = new ArrayList<String>();
			 boolean isHaveNullCardCode = false;
			 if(null!=rejectLetterApplications){
				for(RejectLetterApplicationDataM rejectLetterApplicationDataM : rejectLetterApplications){
					String productItem = rejectLetterApplicationDataM.getProduct();
					if(productItem.equalsIgnoreCase(product)){
						String cardCode = rejectLetterApplicationDataM.getCardCode();
						if(cardCode==null)
							isHaveNullCardCode = true;
						if(!cardCodes.contains(cardCode) && null!=cardCode){
							cardCodes.add(cardCode);
						}
						String businessClass = rejectLetterApplicationDataM.getBusinessClassId();
						if(!businessClasses.contains(businessClass) && null!=businessClass){
							businessClasses.add(businessClass);
						}
					}
				}
			}
			conn = getConnection();
			StringBuilder sql = new StringBuilder();

			 sql.append(" 	SELECT  AG.APPLICATION_GROUP_ID, PS.NATIONALITY, PS.PERSONAL_TYPE, ");
			 sql.append(" 	A.PROLICY_PROGRAM_ID, A.FINAL_APP_DECISION , A.APPLICATION_RECORD_ID,PS.PERSONAL_ID, ");
			 sql.append(" 	PKA_REJECT_LETTER.GET_PERSONAL_MIN_RANK_REASON(AG.APPLICATION_GROUP_ID,PS.PERSONAL_ID, ");
			 sql.append(" 	A.APPLICATION_RECORD_ID) AS REASON_CODE,PS.PERSONAL_ID,A.BUSINESS_CLASS_ID,CTP.CARD_CODE  ");
			 sql.append(" 	FROM  ORIG_APPLICATION_GROUP AG ");
			 sql.append(" 	INNER JOIN  ORIG_APPLICATION A ON  A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			 sql.append(" 	INNER JOIN  ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID AND PR.RELATION_LEVEL='A' ");
			 sql.append(" 	INNER JOIN ORIG_PERSONAL_INFO PS ON  PR.PERSONAL_ID = PS.PERSONAL_ID ");
			 sql.append(" 	INNER JOIN ORIG_LOAN L  ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			 sql.append(" 	LEFT JOIN  ORIG_CARD C ON  L.LOAN_ID = C.LOAN_ID ");
			 sql.append(" 	LEFT JOIN CARD_TYPE CTP ON  CTP.CARD_TYPE_ID = C.CARD_TYPE AND CTP.CARD_LEVEL = C.CARD_LEVEL ");
			 sql.append(" 	WHERE  AG.APPLICATION_GROUP_ID =?  AND A.LIFE_CYCLE=? ");
			 if(!rejectLetterDataM.getProducts().contains(REJECT_LETTER_PRODUCT_NAME_KPL)){
				 sql.append(" AND (");
				 if(!ServiceUtil.empty(cardCodes)){
					 sql.append("  CTP.CARD_CODE IN (");
					 String COMMA="";
					 for(String cardCode : cardCodes){
						 sql.append(COMMA+"?");
						 COMMA=",";
					 }
					 sql.append(")");
				 }
				 if(isHaveNullCardCode){
					 if(!ServiceUtil.empty(cardCodes)){
						 sql.append(" OR");
					 }
					 sql.append(" CTP.CARD_CODE IS NULL");
				 }
				 sql.append(")");
			 }
			 
			 sql.append(" 	AND  A.BUSINESS_CLASS_ID  IN (");
			 String COMMA1="";
			 for(String busClass : businessClasses){
				 sql.append(COMMA1+"?");
				 COMMA1=",";
			 }
			 sql.append(")");
			 
			logger.debug("sql : " + sql);
			logger.debug("rejectLetterDataM.getApplicationGroupId() : " + rejectLetterDataM.getApplicationGroupId());
			logger.debug("rejectLetterDataM.getLifeCycle() : " + rejectLetterDataM.getLifeCycle());
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setString(index++, rejectLetterDataM.getApplicationGroupId());
			ps.setInt(index++, rejectLetterDataM.getLifeCycle());
			 if(!rejectLetterDataM.getProducts().contains(REJECT_LETTER_PRODUCT_NAME_KPL)){
				 if(!ServiceUtil.empty(cardCodes)){
					 for(String cardCode : cardCodes){
						 ps.setString(index++, cardCode);
						 logger.debug("cardCode : " + cardCode);
					 } 
				 }
			 }
			 
			 for(String busClass : businessClasses){
				 ps.setString(index++, busClass);
				 logger.debug("busClass : " + busClass);
			 }
			 
			rs = ps.executeQuery();
			while(rs.next()) {			
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String NATIONALITY = rs.getString("NATIONALITY");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String REASON_CODE = rs.getString("REASON_CODE");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String CARD_CODE = rs.getString("CARD_CODE");
				if(null==CARD_CODE)CARD_CODE="";
				String key = BUSINESS_CLASS_ID+"_"+CARD_CODE;
				String language = !LANGUAGE_TH.equals(NATIONALITY) ? LANGUAGE_OTHER : NATIONALITY ; 
								
				ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodeDetails =  hRejectLetterInfos.get(key);
				if(ServiceUtil.empty(templateReasonCodeDetails)){
					templateReasonCodeDetails = new ArrayList<TemplateReasonCodeDetailDataM>();
					hRejectLetterInfos.put(key, templateReasonCodeDetails);
				}
				TemplateReasonCodeDetailDataM templateReasonCodeDetailDataM = new TemplateReasonCodeDetailDataM();
				templateReasonCodeDetailDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
				templateReasonCodeDetailDataM.setLanguage(language);
				templateReasonCodeDetailDataM.setPersonalType(PERSONAL_TYPE);
				templateReasonCodeDetailDataM.setPolicyProgramId(PROLICY_PROGRAM_ID);
				templateReasonCodeDetailDataM.setFinalDecision(FINAL_APP_DECISION);
				templateReasonCodeDetailDataM.setApplicationRecordId(APPLICATION_RECORD_ID);
				templateReasonCodeDetailDataM.setPersonalId(PERSONAL_ID);
				templateReasonCodeDetailDataM.setCardCode(CARD_CODE);
				templateReasonCodeDetailDataM.setReasonCode(REASON_CODE);
				templateReasonCodeDetailDataM.setBusinessClassId(BUSINESS_CLASS_ID);
				templateReasonCodeDetails.add(templateReasonCodeDetailDataM);
				logger.debug(" info : reason >> "+key+" ==> "+templateReasonCodeDetailDataM.getReasonCode()+ " ==> "+APPLICATION_GROUP_ID);
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
		 return hRejectLetterInfos;
	}
	
	public ArrayList<RejectTemplateVariableDataM> getBundleTemplateValues(RejectLetterBuildTemplateEntity appGroup) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RejectTemplateVariableDataM> rejectTemplateVariables = new ArrayList<RejectTemplateVariableDataM>();
		String applicationgroupId =appGroup.getApplicationGroupId();
		try{
			conn = getConnection();
			for(RejectTemplateVariableBundleEntity app : appGroup.getApps()){
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT AG.APPLICATION_GROUP_ID, A.APPLICATION_RECORD_ID,");
			sql.append(" DECODE(NATIONALITY,'"+RejectLetterUtil.TH+"','"+REJECT_LETTER_TITLE_NAME_TH+"',EN_TITLE_DESC) AS TITLE_NAME,");
			sql.append(" AG.APPLICATION_GROUP_NO, P.TH_FIRST_NAME || ' ' || P.TH_LAST_NAME  AS PERSONAL_NAME ,");
			sql.append(" P.EN_FIRST_NAME || ' ' || P.EN_LAST_NAME  AS PERSONAL_NAME_EN ,");
			sql.append(" B.INTERFACE_LOG_ID, A.BUSINESS_CLASS_ID,");
			sql.append(" P.PERSONAL_TYPE, P.EMAIL_PRIMARY,P.NATIONALITY, P.IDNO, ");
			sql.append(" PKA_REJECT_LETTER.GET_ADDRESS(P.PERSONAL_ID,AG.APPLICATION_TYPE) AS  ADDRESS,");
			sql.append(" PKA_REJECT_LETTER.GET_ZIPCODE(P.PERSONAL_ID,AG.APPLICATION_GROUP_ID) AS  ZIPCODE,");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,P.NATIONALITY) AS PRODUCT_NAME,");
			sql.append(" PKA_REJECT_LETTER.GET_PRODUCT_TH_EN(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID) AS PRODUCT_NAME_TH_EN,");
			sql.append(" PKA_REJECT_LETTER.GET_REJECT_REASON(A.APPLICATION_RECORD_ID,NATIONALITY) AS REASON_CODE_DESC,");
			sql.append(" TO_CHAR(A.FINAL_APP_DECISION_DATE, 'dd/mm/yyyy')AS FINAL_APP_DECISION_DATE");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" LEFT JOIN ORIG_APPLICATION A ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID");
			sql.append(" LEFT JOIN ORIG_LOAN  LN ON LN.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");	
			sql.append(" LEFT JOIN ORIG_CARD  CD ON CD.LOAN_ID = LN.LOAN_ID ");	
			sql.append(" LEFT JOIN INF_BATCH_LOG B ON A.APPLICATION_RECORD_ID = B.APPLICATION_RECORD_ID ");		
			//sql.append(" AND B.INTERFACE_CODE IN ('"+REJECT_LETTER_INTERFACE_CODE_T1+"','"+REJECT_LETTER_INTERFACE_CODE_T2+"')");
			sql.append(" AND B.INTERFACE_CODE = '"+REJECT_LETTER_INTERFACE_CODE+"' ");
			sql.append(" AND B.INTERFACE_STATUS = '"+REJECT_LETTER_INTERFACE_STATUS_COMPLETE+"'");
			sql.append(" INNER JOIN  (SELECT  PERSONAL_ID,APPLICATION_GROUP_ID,EN_TITLE_DESC,EN_FIRST_NAME ,EN_LAST_NAME, TH_FIRST_NAME,TH_LAST_NAME, PERSONAL_TYPE,EMAIL_PRIMARY,NATIONALITY ,IDNO ");
			sql.append(" 	   FROM ORIG_PERSONAL_INFO WHERE PERSONAL_ID  IN ( ");
						String commaPersonal="";
						for(String personalId :app.getPersonalIds()){
							sql.append(commaPersonal+"'"+personalId+"'"	);
							commaPersonal=",";
						}
						sql.append("	)	) P  ON  P.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID");
			sql.append(" WHERE A.LIFE_CYCLE =?");
			sql.append(" AND AG.APPLICATION_GROUP_ID =?");
			sql.append(" AND A.FINAL_APP_DECISION =?");
			sql.append(" AND A.BUSINESS_CLASS_ID IN (");
			String commaBusClass="";
			for(String busClass : app.getBusinessClassIds()){
				sql.append(commaBusClass+"'"+busClass+"'");
				commaBusClass=",";
			}
			sql.append(" )");
						
			logger.debug("sql>>>"+sql);
			logger.debug("LIFE_CYCLE : "+appGroup.getLifeCycle());
			logger.debug("applicationgroupId>>>"+applicationgroupId);
			logger.debug("FINAL_APP_DECISION_REJECT>>>"+FINAL_APP_DECISION_REJECT);
			ps = conn.prepareStatement(sql.toString());		
			int index=1;
			ps.setInt(index++,appGroup.getLifeCycle()); 			
			ps.setString(index++,applicationgroupId); 			
			ps.setString(index++, FINAL_APP_DECISION_REJECT); 	
			rs = ps.executeQuery();
			while(rs.next()) {
					String ADDRESS_LINE1="";
					String ADDRESS_LINE2="";
					String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
					String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
					String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
					String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
					String REASON_CODE_DESC = rs.getString("REASON_CODE_DESC");
					String TITLE_NAME = rs.getString("TITLE_NAME");
					String PERSONAL_NAME_EN = rs.getString("PERSONAL_NAME_EN");
					String PERSONAL_NAME = rs.getString("PERSONAL_NAME");
					String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
					String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
					String NATIONALITY = rs.getString("NATIONALITY");
					String ADDRESS = rs.getString("ADDRESS");
					String ZIPCODE = rs.getString("ZIPCODE");
					String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
					String PRODUCT_NAME_TH_EN = rs.getString("PRODUCT_NAME_TH_EN");
					String FINAL_APP_DECISION_DATE = rs.getString("FINAL_APP_DECISION_DATE");
					String IDNO = rs.getString("IDNO");
					if(!InfBatchUtil.empty(ADDRESS)){
						String[] ADD_STR = ADDRESS.split("_");
						ADDRESS_LINE1 = ADD_STR.length>0?ADD_STR[0]:"";
						ADDRESS_LINE2 = ADD_STR.length>1?ADD_STR[1]:"";
					}
					logger.debug("REASON_CODE>>"+REASON_CODE_DESC);
					
					RejectTemplateVariableDataM rejectTemplateVariableDataM = new RejectTemplateVariableDataM();
					rejectTemplateVariableDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
					rejectTemplateVariableDataM.setApplicationGroupNo(APPLICATION_GROUP_NO);
					rejectTemplateVariableDataM.setTitleName(TITLE_NAME);
					rejectTemplateVariableDataM.setPersonalNameTh(PERSONAL_NAME);
					rejectTemplateVariableDataM.setPersonalNameEn(PERSONAL_NAME_EN);
					rejectTemplateVariableDataM.setPersonalType(PERSONAL_TYPE);
					rejectTemplateVariableDataM.setEmailPrimary(EMAIL_PRIMARY);
					rejectTemplateVariableDataM.setNationality(NATIONALITY);
					rejectTemplateVariableDataM.setAddressLine1(ADDRESS_LINE1);
					rejectTemplateVariableDataM.setAddressLine2(ADDRESS_LINE2);
					rejectTemplateVariableDataM.setZipcode(ZIPCODE);
					rejectTemplateVariableDataM.setProductName(PRODUCT_NAME);
					rejectTemplateVariableDataM.setProductNameThEn(PRODUCT_NAME_TH_EN);
					rejectTemplateVariableDataM.setFinalDecisionDate(FINAL_APP_DECISION_DATE);
					rejectTemplateVariableDataM.setIdNo(IDNO);
					rejectTemplateVariableDataM.setApplicationRecordId(APPLICATION_RECORD_ID);
					rejectTemplateVariableDataM.setProductType(app.getProductType());
					rejectTemplateVariableDataM.setRejectReasonAllProduct(REASON_CODE_DESC);
					rejectTemplateVariableDataM.setBusinessClassId(BUSINESS_CLASS_ID);
					rejectTemplateVariables.add(rejectTemplateVariableDataM);
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
		return rejectTemplateVariables;
	}
}