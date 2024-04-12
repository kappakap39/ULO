package com.eaf.inf.batch.ulo.notification.dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.model.InfExportDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.card.notification.model.CardNotificationDataM;
import com.eaf.inf.batch.ulo.card.notification.model.CardParamDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationConfig;
import com.eaf.inf.batch.ulo.notification.config.model.ReasonApplication;
import com.eaf.inf.batch.ulo.notification.eod.sendto.util.EODSendToManagerUtil;
import com.eaf.inf.batch.ulo.notification.model.BranchSummaryResponseDataM;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailRejectNCBPersonalInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailTemplateDataM;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotiCCDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationEODRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;
import com.eaf.inf.batch.ulo.notification.model.NotifyEODApplicationDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyEODManagerInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyEODSalesInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.OrigContactLogDataM;
import com.eaf.inf.batch.ulo.notification.model.Reason;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.SMSTemplateDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class NotificationDAOImpl extends InfBatchObjectDAO  implements NotificationDAO {
	private static transient Logger logger = Logger.getLogger(NotificationDAOImpl.class);
	String NOTIFICATION_KPL_PRODUCT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_KPL_PRODUCT");
	String NOTIFICATION_KEC_PRODUCT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_KEC_PRODUCT");
	String NOTIFICATION_CC_PRODUCT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_CC_PRODUCT");
	String CASH_DAY_1= InfBatchProperty.getInfBatchConfig("NOTIFICATION_CASH_DAY_1");
	String CASH_1_HOUR= InfBatchProperty.getInfBatchConfig("NOTIFICATION_CASH_1_HOUR");
	String DM_DOC_STATUS_NOT_IN_WAREHOUSE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_DM_DOC_STATUS_NOT_IN_WAREHOUSE");
	String DM_DOC_STATUS_BORROWED= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_DM_DOC_STATUS_BORROWED");
	String WARE_HOUSE_APP_GROUP_JOB_STATE_APPROVED= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_APP_GROUP_JOB_STATE_APPROVED");
	String SUB_DOC_STATUS_NOT_IN_WAREHOUS= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_SUB_DOC_STATUS_NOT_IN_WAREHOUS");
	String WARE_HOUSE_NOTIFICATION_TYPE_NOT_RECEIVE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_NOTIFICATION_TYPE_NOT_RECEIVE");
	String WARE_HOUSE_NOTIFICATION_TYPE_INCOMPLETE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_NOTIFICATION_TYPE_INCOMPLETE");
	String WARE_HOUSE_TRANSACTION_ACTION_BORROWED= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_TRANSACTION_ACTION_BORROWED");
	String WARE_HOUSE_TRANSACTION_STATUS= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_TRANSACTION_STATUS");
	String REASON_TYPE_REJECT= InfBatchProperty.getInfBatchConfig("NOTIFICATION_REASON_TYPE_REJECT");
	String KEY_DIVISION="DIVISION";
	String KEY_DEPARTMENT="DEPARTMENT";
	String KEY_NETWORK="NETWORK";
	String EMAIL_TO="EMAIL_TO";
	String EMAIL_CC_TO="CC_TO";
	String NOTIFICATION_SALE_TYPE_NORMAL=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_NORMAL");
	String NOTIFICATION_SALE_TYPE_REFERENCE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_REFERENCE");
	String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_TYPE_ADD_SUP=InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_ADD_SUP");
	String XRULES_VER_LEVEL_APPLICATION=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_XRULES_VER_LEVEL_APPLICATION");
	String NOTIFICATION_EMAIL_BRANCH_SUMMARY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_TEMPLATE_ID_BRANCH_SUMMARY");
	String APPLICATION_STATUS_REJECT=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	String APPLICATION_STATUS_APPROVE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_APPROVE");
	String DEFUALT_EFF_ST_CD = SystemConstant.getConstant("DEFUALT_EFF_ST_CD");
	String APPLICATION_TEMPLATE_THAIBEV = SystemConstant.getConstant("APPLICATION_TEMPLATE_THAIBEV");
	
	String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	String NOTIFICATION_FINAL_APP_DECISION_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
	String NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION");
	String PERSONAL_RELATION_LEVEL = InfBatchProperty.getInfBatchConfig("PERSONAL_RELATION_LEVEL");
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String FIELD_ID_CARD_TYPE = InfBatchProperty.getInfBatchConfig("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL = InfBatchProperty.getInfBatchConfig("FIELD_ID_CARD_LEVEL");
	String CARD_NO = InfBatchProperty.getInfBatchConfig("CARD_NO");
	String CUST_NO = InfBatchProperty.getInfBatchConfig("CUST_NO");
	String MEMBER_SHIP = InfBatchProperty.getInfBatchConfig("MEMBER_SHIP");
	String PARAM_ID_MEMBER_SHIP = InfBatchProperty.getInfBatchConfig("PARAM_ID_MEMBER_SHIP");
	String DEFUALT_DATA_TYPE_ALL = InfBatchProperty.getInfBatchConfig("DEFUALT_DATA_TYPE_ALL");
	ArrayList<String> NOTIFICATION_SMS_NEXT_DAY_JOB_STATE_END = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_JOB_STATE_END");
	
	//String NOTIFICATION_KMOBILE_ROLE_NAME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_KMOBILE_ROLE_NAME");
	ArrayList<String> NOTIFICATION_KMOBILE_ROLE_NAME = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_KMOBILE_ROLE_NAME");
	
	String NOTIFICATION_APP_STATUS_PRE_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APP_STATUS_PRE_APPROVE");
	String NOTIFICATION_APP_STATUS_PRE_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APP_STATUS_PRE_REJECT");
	
	String GEN_PARAM_CC_INFINITE = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_INFINITE");//0001
	String GEN_PARAM_CC_WISDOM = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_WISDOM");//0002
	String GEN_PARAM_CC_PREMIER = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_PREMIER");//0003
	
	String NOTIFICATION_EOD_XRULES_VER_LEVEL_APPLICATION = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_XRULES_VER_LEVEL_APPLICATION");
	String NOTIFICATION_SEND_TO_TYPE_SELLER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String NOTIFICATION_SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
	
	String NOTIFICATION_SALE_BRANCH_CHANNEL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_BRANCH_CHANNEL");
	String NOTIFICATION_SALE_CREDITCENTER_CHANNEL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_CREDITCENTER_CHANNEL");
	String NOTIFICATION_SALE_TELESALE_CHANNEL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TELESALE_CHANNEL");
	String NOTIFICATION_SALE_DSA_CHANNEL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_DSA_CHANNEL");
	
	String NOTIFICATION_TEMPLATE_ENABLE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_TEMPLATE_ENABLE");
	String NOTIFICATION_TEMPLATE_DISABLE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_TEMPLATE_DISABLE");
	String NOTIFICATION_SMS_NEXT_DAY_INF_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_INF_CODE");
	String NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME");
	String APPLICATION_TYPE_INCREASE = InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_INCREASE");
	String NOTIFICATION_SMS_NEXT_DAY_DEFUALT_SEND_TO = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_DEFUALT_SEND_TO");
	String NOTIFICATION_SMS_NEXT_DAY_SEND_TO_CONDITION = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_SEND_TO_CONDITION");
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
	String NOTIFICATION_SEND_TIME_EOD = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TIME_EOD");
	ArrayList<String> NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_LIST = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_LIST");
//	ArrayList<String> NOTIFICATION_EMAIL_SUMMARY_NCB_REASON_CODES = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_NCB_REASON_CODES");
//	ArrayList<String> NOTIFICATION_EOD_FIX_EMAIL_APPLICATION_TEMPLATE  = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_FIX_EMAIL_APPLICATION_TEMPLATE");
	String NOTIFICATION_SEND_TO_TYPE_COMPLIANCE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_COMPLIANCE");
	
	@Override
	public HashMap<String,SMSTemplateDataM> getSMSTemplateInfo(ArrayList<String> templateIds)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,SMSTemplateDataM> hSMSTemplates = new HashMap<String,SMSTemplateDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT SMS.TEMPLATE_ID, SMS.TEMPLATE_NAME, SMS.BUSS_CLASS, SMS.MESSAGE_EN, SMS.MESSAGE_TH");
			sql.append(" FROM MS_SMS_TEMPLATE_MASTER SMS ");
			sql.append(" WHERE SMS.TEMPLATE_ID IN (");
			String COMMA="";
			for(String templateId : templateIds){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());	
			int index=1;
			for(String templateId : templateIds){
				ps.setString(index,templateId); 	
			}
			rs = ps.executeQuery();

		 
			while(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");		
				SMSTemplateDataM smsTemplate  = hSMSTemplates.get(TEMPLATE_ID);
				if(InfBatchUtil.empty(smsTemplate)){
					smsTemplate = new SMSTemplateDataM();
					hSMSTemplates.put(TEMPLATE_ID, smsTemplate);
				}
				smsTemplate.setTemplatId(TEMPLATE_ID);
				smsTemplate.setTemplatName(rs.getString("TEMPLATE_NAME"));
				smsTemplate.setBussClass(rs.getString("BUSS_CLASS"));
				smsTemplate.setMessageEn(rs.getString("MESSAGE_EN"));				
				smsTemplate.setMessageTh(rs.getString("MESSAGE_TH"));	
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
		 return hSMSTemplates;
	}
	 

	@Override
	public HashMap<String,EmailTemplateDataM> getEmailTemplateInfo(ArrayList<String> templateIds)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,EmailTemplateDataM> hEmailTemplate = new HashMap<String,EmailTemplateDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT EMAIL.TEMPLATE_ID, EMAIL.TEMPLATE_NAME, EMAIL.CONTENT, EMAIL.BUS_GRP_ID, EMAIL.SUBJECT,");
			sql.append("  EMAIL.TEMPLATE_TYPE");
			sql.append(" FROM MS_EMAIL_TEMPLATE_MASTER EMAIL ");
			sql.append(" WHERE EMAIL.TEMPLATE_ID IN (");
			String COMMA ="";
			for(String templateId : templateIds){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index=1;
			for(String templateId : templateIds){
				ps.setString(index++,templateId); 
			}
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				EmailTemplateDataM emailTemplate = hEmailTemplate.get(TEMPLATE_ID);
				if(InfBatchUtil.empty(emailTemplate)){
					emailTemplate = new EmailTemplateDataM();
					hEmailTemplate.put(TEMPLATE_ID, emailTemplate);
				}

				emailTemplate.setTemplateId(rs.getString("TEMPLATE_ID"));
				emailTemplate.setTemplateName(rs.getString("TEMPLATE_NAME"));
				emailTemplate.setContent(rs.getString("CONTENT"));
				emailTemplate.setBusGrpId(rs.getString("BUS_GRP_ID"));				
				emailTemplate.setSubject(rs.getString("SUBJECT"));								
				emailTemplate.setTemplateType(rs.getString("TEMPLATE_TYPE"));
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
		 return hEmailTemplate;
	}

	@Override
	public NotificationInfoDataM getNotificationInfo(NotificationInfoRequestDataM notificationRequest) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		NotificationInfoDataM  notificationInfo = null;
		String SALE_FLAG = MConstant.FLAG.NO;
		if(!Util.empty(notificationRequest.getSaleType())){
			SALE_FLAG = MConstant.FLAG.YES;
		}
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT  S.SEND_TIME ,S.NOTIFICATION_TYPE ,NVL(P.PRIORITY,'1') AS PRIORITY ,D.SEND_TO ,D.FIX_EMAIL ");
			sql.append(" ,M.JOB_CODE ,M.OPTIONAL1 ,M.OPTIONAL2 ,M.OPTIONAL3 ,M.OPTIONAL4 ");
			sql.append(" ,M.OPTIONAL5 , M.PATTERN , APP.*,G.CASH_TRANSFER_TYPE ");
	        sql.append(" FROM (SELECT MAX(APP.LIFE_CYCLE) AS MAX_LIFE_CYCLE, APP.FINAL_APP_DECISION, APP_GROUP.APPLICATION_TEMPLATE,S_NORMAL.SALES_ID,S_NORMAL.SALE_CHANNEL ,");
	        sql.append("	APP_GROUP.APPLICATION_GROUP_ID,APP_GROUP.APPLICATION_TYPE, S_RECOMMEND.SALES_ID AS RECOMMEND, R.REASON_CODE,CT.CASH_TRANSFER_TYPE ");
		    sql.append(" 	FROM  ORIG_APPLICATION_GROUP  APP_GROUP ");
		    sql.append(" 	INNER JOIN ORIG_APPLICATION APP ON APP.APPLICATION_GROUP_ID = APP_GROUP.APPLICATION_GROUP_ID AND APP.LIFE_CYCLE = APP_GROUP.LIFE_CYCLE ");
		    sql.append(" 	LEFT JOIN ORIG_SALE_INFO S_NORMAL ON APP_GROUP.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID AND S_NORMAL.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"' ");
		    sql.append(" 	LEFT JOIN ORIG_SALE_INFO S_RECOMMEND ON APP_GROUP.APPLICATION_GROUP_ID = S_RECOMMEND.APPLICATION_GROUP_ID AND S_RECOMMEND.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_REFERENCE+"' ");
		    sql.append(" 	LEFT JOIN  ORIG_LOAN L ON L.APPLICATION_RECORD_ID  = APP.APPLICATION_RECORD_ID ");
		    sql.append(" 	LEFT JOIN  ORIG_CASH_TRANSFER CT ON CT.LOAN_ID  = L.LOAN_ID AND CT.CASH_TRANSFER_TYPE IS NOT NULL ");
		    sql.append(" 	LEFT JOIN (SELECT APPLICATION_RECORD_ID,APPLICATION_GROUP_ID, PKA_NOTIFICATION.GET_MIN_RANK_REASON_CODE(APPLICATION_GROUP_ID,APPLICATION_RECORD_ID)AS REASON_CODE FROM  ORIG_APPLICATION) R ");
		    sql.append(" 	 ON R.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID AND R.APPLICATION_RECORD_ID=APP.APPLICATION_RECORD_ID ");
		    sql.append(" 	WHERE  APP_GROUP.APPLICATION_GROUP_ID=? ");
		    sql.append(" 	GROUP BY APP_GROUP.APPLICATION_TEMPLATE, S_NORMAL.SALES_ID,S_NORMAL.SALE_CHANNEL , ");
		    sql.append(" 	APP_GROUP.APPLICATION_GROUP_ID,APP_GROUP.APPLICATION_TYPE, S_RECOMMEND.SALES_ID, R.REASON_CODE, CT.CASH_TRANSFER_TYPE, APP.FINAL_APP_DECISION	");	    
		    sql.append(" 	) APP ");
		    sql.append(" INNER JOIN MS_NOTI_APP_TEMPLATE T ON T.TEMPLATE_CODE = APP.APPLICATION_TEMPLATE OR T.TEMPLATE_CODE = '"+DEFUALT_DATA_TYPE_ALL+"' ");
		    sql.append(" INNER JOIN MS_NOTI_GROUP  G ON G.GROUP_CODE =T.GROUP_CODE AND (G.APPLICATION_STATUS = APP.FINAL_APP_DECISION OR G.APPLICATION_STATUS = 'ALL')");
			sql.append(" INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE");
			sql.append(" INNER JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE");
			sql.append(" INNER JOIN MS_NOTI_SENDING_DETAIL D ON  S.SENDING_ID = D.SENDING_ID");
			sql.append(" LEFT JOIN  MS_NOTI_SENDING_PRIORITY  P ON P.GROUP_CHANNEL_CODE = S.GROUP_CHANNEL_CODE AND P.SENDING_ID = S.SENDING_ID ");
			sql.append(" LEFT JOIN  MS_NOTI_MANAGER_CHANNEL M ON M.CHANNEL_CODE =C.CHANNEL_CODE");
			sql.append(" LEFT JOIN  MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE AND APP.REASON_CODE=RE.REASON_CODE");
			sql.append(" WHERE (G.HAVE_SALE_FLAG =? OR G.HAVE_SALE_FLAG='A')  AND  (G.CASH_TRANSFER_TYPE = APP.CASH_TRANSFER_TYPE OR  G.CASH_TRANSFER_TYPE='"+DEFUALT_DATA_TYPE_ALL+"') ");
			sql.append("  AND (C.CHANNEL_CODE = APP.SALE_CHANNEL OR C.CHANNEL_CODE = '"+DEFUALT_DATA_TYPE_ALL+"' OR (C.CHANNEL_CODE = 'N' AND APP.SALE_CHANNEL IS NULL ))  ");     
		    sql.append("  AND S.SEND_TIME = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());				 
			ps.setString(1,notificationRequest.getApplicationGroupId()); 		 		
			ps.setString(2,SALE_FLAG);
			ps.setString(3,notificationRequest.getSendingTime()); 			
			rs = ps.executeQuery();
			HashMap<String,ArrayList<JobCodeDataM>> jobCodeHash  = new HashMap<String,ArrayList<JobCodeDataM>>();
			while(rs.next()) {		
				if(null==notificationInfo){
					notificationInfo = new NotificationInfoDataM();
					notificationInfo.setJobCodes(jobCodeHash);
				}
				String SALES_ID = rs.getString("SALES_ID");
				String RECOMMEND = rs.getString("RECOMMEND");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				String NOTIFICATION_TYPE = rs.getString("NOTIFICATION_TYPE");
				String SEND_TO = rs.getString("SEND_TO");
				String APPLICATION_TYPE = rs.getString("APPLICATION_TYPE");
				Integer MAX_LIFE_CYCLE = rs.getInt("MAX_LIFE_CYCLE");
				String REASON_CODE = rs.getString("REASON_CODE");
				String CASH_TRANSFER_TYPE = rs.getString("CASH_TRANSFER_TYPE");
				String notificationId = NOTIFICATION_TYPE+"_"+SEND_TO;
				
				notificationInfo.setSaleId(SALES_ID);
				notificationInfo.setMaxLifeCycle(MAX_LIFE_CYCLE);
				notificationInfo.setSaleRecommend(RECOMMEND);
				notificationInfo.setApplicationTemplate(APPLICATION_TEMPLATE);
				notificationInfo.setSaleChanel(SALE_CHANNEL);	
				notificationInfo.setApplicationType(APPLICATION_TYPE);
				notificationInfo.addSendTo(SEND_TO);
				if(!DEFUALT_DATA_TYPE_ALL.equalsIgnoreCase(CASH_TRANSFER_TYPE) && !InfBatchUtil.empty(CASH_TRANSFER_TYPE)){
					notificationInfo.addCashTransType(CASH_TRANSFER_TYPE);
				}
				if(NOTIFICATION_SEND_TO_TYPE_COMPLIANCE.equals(SEND_TO)){
					ArrayList<String> rejectReasonCodes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SANCTION_LIST_REJECT_CODE");
					if(null==rejectReasonCodes){
						rejectReasonCodes = new ArrayList<String>();
					}
					HashMap<String,ArrayList<String>> rejectReasonCodeHash = notificationInfo.getRejectReasonCodes();
					if(null==rejectReasonCodeHash){
						rejectReasonCodeHash = new HashMap<String, ArrayList<String>>();
						notificationInfo.setRejectReasonCodes(rejectReasonCodeHash);
					}
					rejectReasonCodeHash.put(NotificationInfoDataM.COMPLIANCE_REJECT_REASON,rejectReasonCodes);
				}else{
					HashMap<String,ArrayList<String>> rejectReasonCodeHash = notificationInfo.getRejectReasonCodes();
					if(null==rejectReasonCodeHash){
						rejectReasonCodeHash = new HashMap<String, ArrayList<String>>();
						notificationInfo.setRejectReasonCodes(rejectReasonCodeHash);
					}
					ArrayList<String> rejectReasons = rejectReasonCodeHash.get(NotificationInfoDataM.DEFAULT_REJECT_REASON);
					if(null==rejectReasons){
						rejectReasons = new ArrayList<String>();
						rejectReasonCodeHash.put(NotificationInfoDataM.DEFAULT_REJECT_REASON, rejectReasons);
					}
					if(!Util.empty(REASON_CODE) &&!rejectReasons.contains(REASON_CODE)){
						rejectReasons.add(REASON_CODE);
					}
				}
				ArrayList<JobCodeDataM> jobCodes = jobCodeHash.get(notificationId);
				if(InfBatchUtil.empty(jobCodes)){
					jobCodes = new ArrayList<JobCodeDataM>();
					jobCodeHash.put(notificationId, jobCodes);
				}
				JobCodeDataM jobCode = new JobCodeDataM();
					jobCode.setNotificationType(NOTIFICATION_TYPE);
					jobCode.setPriority(rs.getString("PRIORITY"));
					jobCode.setFixEmail(rs.getString("FIX_EMAIL"));
					jobCode.setSendTo(rs.getString("SEND_TO"));
					jobCode.setJobCode(rs.getString("JOB_CODE"));
					jobCode.setOptional1(rs.getString("OPTIONAL1"));
					jobCode.setOptional2(rs.getString("OPTIONAL2"));
					jobCode.setOptional3(rs.getString("OPTIONAL3"));
					jobCode.setOptional4(rs.getString("OPTIONAL4"));
					jobCode.setOptional5(rs.getString("OPTIONAL5"));
					jobCode.setPattern(rs.getString("PATTERN"));
				jobCodes.add(jobCode);
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
		return notificationInfo;
	}
	
	private ArrayList<String>   getMinReasonCode(String applicationGroupId) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String>  minReasonCodes= new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PKA_REJECT_LETTER.GET_MIN_RANK_REASON('"+applicationGroupId+"',APP.APPLICATION_RECORD_ID) AS RANK_REASON");	 
			sql.append(" FROM ORIG_APPLICATION APP ");	 
			ps = conn.prepareStatement(sql.toString());						
			logger.debug("sql : " + sql);		
			rs = ps.executeQuery();

			while(rs.next()) {
				 String RANK_REASON = rs.getString("RANK_REASON");
				 if(!InfBatchUtil.empty(RANK_REASON)){
					 String[] rankCode = RANK_REASON.split("_");
						if(rankCode.length>1){
							String minReasonCode= rankCode[1];
							if(!minReasonCodes.contains(minReasonCode)&& !InfBatchUtil.empty(minReasonCode)){
								minReasonCodes.add(minReasonCode);
							}
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return minReasonCodes;	
	}
	
	@Override
	public VCEmpInfoDataM selectVCEmpManagerInfo(String SaleId) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VCEmpInfoDataM saleInfo  = new VCEmpInfoDataM();
		try{
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT '' AS ORGCODE , SALE.* FROM IP_SHR.VC_EMP SALE  WHERE SALE.EMP_ID = ?  ");	 
			ps = conn.prepareStatement(sql.toString());						
			logger.debug("sql : " + sql);		
			logger.debug("SaleId : " + SaleId);		
			ps.setString(1, SaleId);
			rs = ps.executeQuery();

			if(rs.next()) {
				saleInfo.setDeptId(FormatUtil.trim(rs.getString("DEPT_ID")));
				saleInfo.setPrnDeptId(FormatUtil.trim(rs.getString("PRN_DEPT_ID")));
				saleInfo.setEmpId(FormatUtil.trim(rs.getString("EMP_ID")));
				saleInfo.setOrgCode(FormatUtil.trim(rs.getString("ORGCODE")));
				saleInfo.setSubUnitCntrCd(FormatUtil.trim(rs.getString("SUB_UNIT_CNTR_CD")));
				saleInfo.setUnitCntrCd(FormatUtil.trim(rs.getString("UNIT_CNTR_CD")));
				saleInfo.setDeptCntrCd(FormatUtil.trim(rs.getString("DEPT_CNTR_CD")));
				saleInfo.setBsnLineDeptId(FormatUtil.trim(rs.getString("BSN_LINE_DEPT_ID")));
				saleInfo.setNtwCntrCd(FormatUtil.trim(rs.getString("NTW_CNTR_CD")));
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
		return saleInfo;
	}


	@Override
	public HashMap<String,ArrayList<VCEmpInfoDataM>> selectVCEmpInfoJobCode(String saleEmpId , ArrayList<JobCodeDataM> jobList)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,ArrayList<VCEmpInfoDataM>>  hDataList  = new HashMap<String,ArrayList<VCEmpInfoDataM>>();
		if(Util.empty(jobList)){
			return hDataList;
		}
		try{
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT VC_EMP.* ,'' AS ORGCODE FROM IP_SHR.VC_EMP WHERE VC_EMP.EMP_ID <> ? AND EMP_ST_CD <> 'I' " +
					"AND VC_EMP.JOB_CD IN (");
			String COMMA="";
			for(JobCodeDataM jobCodeData :jobList){
				sql.append(COMMA+"?, ?, ?, ?, ?, ?");
				COMMA=",";
			}
			sql.append(") ");
			ps = conn.prepareStatement(sql.toString());		
			int index = 1;	
			ps.setString(index++,FormatUtil.replece(saleEmpId));
			for(JobCodeDataM jobCodeData :jobList){
				ps.setString(index++,FormatUtil.replece(jobCodeData.getJobCode()));
				ps.setString(index++,FormatUtil.replece(jobCodeData.getOptional1()));
				ps.setString(index++,FormatUtil.replece(jobCodeData.getOptional2()));
				ps.setString(index++,FormatUtil.replece(jobCodeData.getOptional3()));
				ps.setString(index++,FormatUtil.replece(jobCodeData.getOptional4()));
				ps.setString(index++,FormatUtil.replece(jobCodeData.getOptional5()));
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				String mobile1 = FormatUtil.trim(rs.getString("MBL_PH1"));
				String mobile2 = FormatUtil.trim(rs.getString("MBL_PH2"));
				String email = FormatUtil.trim(rs.getString("EMAIL_ADR"));
				String jobCode = FormatUtil.trim(rs.getString("JOB_CD"));
				String deptControl = FormatUtil.trim(rs.getString("DEPT_CNTR_CD"));
				String deptId = FormatUtil.trim(rs.getString("DEPT_ID"));
				String KEY_JOB_CODE=jobCode+"_"+deptId;
				String KEY_PATTERN ="PATTERN_"+deptControl+"_"+jobCode;
				ArrayList<VCEmpInfoDataM> cvEmpInfos = hDataList.get(KEY_JOB_CODE);
				if(InfBatchUtil.empty(cvEmpInfos)){
					cvEmpInfos =  new ArrayList<VCEmpInfoDataM>();
					hDataList.put(KEY_JOB_CODE, cvEmpInfos);
				}
				ArrayList<VCEmpInfoDataM> cvEmpInfoPattern = hDataList.get(KEY_PATTERN);
				if(InfBatchUtil.empty(cvEmpInfoPattern)){
					cvEmpInfoPattern =  new ArrayList<VCEmpInfoDataM>();
					hDataList.put(KEY_PATTERN, cvEmpInfoPattern);
				}
				VCEmpInfoDataM  saleInfoM = new VCEmpInfoDataM();
					saleInfoM.setDeptId(deptId);
					saleInfoM.setDeptCntrCd(deptControl);
					saleInfoM.setEmpId(FormatUtil.trim(rs.getString("EMP_ID")));
					saleInfoM.setOrgCode(FormatUtil.trim(rs.getString("ORGCODE")));
					saleInfoM.setSubUnitCntrCd(FormatUtil.trim(rs.getString("SUB_UNIT_CNTR_CD")));
					saleInfoM.setUnitCntrCd(FormatUtil.trim(rs.getString("UNIT_CNTR_CD")));
					saleInfoM.setJobCode(jobCode);
					String CORP_TTL_CD = FormatUtil.replece(FormatUtil.trim(rs.getString("CORP_TTL_CD")));
					saleInfoM.setCorpTtlCd(FormatUtil.getInt(CORP_TTL_CD));
					saleInfoM.setMobilePhone(mobile1);
					saleInfoM.setEmail(email);
					if(InfBatchUtil.empty(mobile1)){
						saleInfoM.setMobilePhone(mobile2);
					}				
				cvEmpInfos.add(saleInfoM);
				cvEmpInfoPattern.add(saleInfoM);
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
		return hDataList;
	}
	@Override
	public NotificationEODRequestDataM getNotificationEodRequest() throws InfBatchException{
		NotificationEODRequestDataM eodRequest = new NotificationEODRequestDataM();
		try{
//			HashMap<String,ArrayList<NotificationEODDataM>> notificationData = findNotificationEod();
			BranchSummaryResponseDataM branchSummaryData = findNotificationEodBranchSummary();
			HashMap<String,ArrayList<NotificationEODDataM>> notificationDataRejectNCB = findNotificationEodRejectNCB();
			HashMap<String,ArrayList<NotificationEODDataM>> notificationDataBranchSummary = new HashMap<String,ArrayList<NotificationEODDataM>>();
			HashMap<String, ArrayList<VCEmpInfoDataM>> managerData = new HashMap<String, ArrayList<VCEmpInfoDataM>>(); 
			if(!InfBatchUtil.empty(branchSummaryData)){
				notificationDataBranchSummary = branchSummaryData.getEodBranchSummaryData();
				managerData = branchSummaryData.getManagerData();
			}
			HashMap<String,ArrayList<NotificationEODDataM>> notificationData = new HashMap<String,ArrayList<NotificationEODDataM>>();
			if(!InfBatchUtil.empty(notificationDataBranchSummary)){
				notificationData.putAll(notificationDataBranchSummary);
			}
			if(!InfBatchUtil.empty(notificationDataRejectNCB)){
				notificationData.putAll(notificationDataRejectNCB);
			}
			HashMap<String,ArrayList<String>> reasonData = new HashMap<String,ArrayList<String>>();
			if(!InfBatchUtil.empty(notificationData)){
				MasterNotificationConfigDAO masterConfigDAO = NotificationFactory.getMasterNotificationConfigDAO();
				for(String eodNotificationId : notificationData.keySet()){
					if(InfBatchUtil.empty(eodNotificationId)) continue;
					logger.debug("eodNotificationId : "+eodNotificationId);
					ArrayList<NotificationEODDataM> eodNotificationItems = notificationData.get(eodNotificationId);
					if(!InfBatchUtil.empty(eodNotificationItems)){
						for(NotificationEODDataM eodNotificationItem : eodNotificationItems){
							logger.debug("eodNotificationItem : "+eodNotificationItem);
							String applicationTemplate = Formatter.displayText(eodNotificationItem.getApplicationTemplate());
							String saleChannel = Formatter.displayText(eodNotificationItem.getSaleChannel());
							String recommendChannel = Formatter.displayText(eodNotificationItem.getRecommendChannel());
							String configId = applicationTemplate+"_"+saleChannel+"_"+recommendChannel;
							logger.debug("configId : "+configId);
							if(!reasonData.containsKey(configId)){
								ArrayList<String> reasonCodes = masterConfigDAO.getRejectReasons(applicationTemplate,saleChannel
										,recommendChannel,NOTIFICATION_SEND_TIME_EOD,NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_LIST);
								reasonData.put(configId,reasonCodes);
							}
						}
					}
				}
				eodRequest.setNotificationData(notificationData);
				eodRequest.setReasonData(reasonData);
				eodRequest.setManagerData(managerData);
			}
			logger.debug("eodRequest : "+eodRequest);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		return eodRequest;
	}
	@Override
	public NotificationEODRequestDataM getNotificationEodBranchSummaryRequest()throws InfBatchException {
		NotificationEODRequestDataM eodRequest = new NotificationEODRequestDataM();
		try{
			HashMap<String,ArrayList<NotificationEODDataM>> notificationData = new HashMap<String,ArrayList<NotificationEODDataM>>();
			HashMap<String, ArrayList<VCEmpInfoDataM>> managerData = new HashMap<String, ArrayList<VCEmpInfoDataM>>();
			BranchSummaryResponseDataM branchSummaryData = findNotificationEodBranchSummary();
			if(!InfBatchUtil.empty(branchSummaryData)){
				notificationData = branchSummaryData.getEodBranchSummaryData();
				managerData = branchSummaryData.getManagerData();
			}
			eodRequest = getEODTaskRequest(notificationData,managerData);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		return eodRequest;
	}
	@Override
	public NotificationEODRequestDataM getNotificationEodRejectNCBRequest()throws InfBatchException {
		NotificationEODRequestDataM eodRequest = new NotificationEODRequestDataM();
		try{
			HashMap<String,ArrayList<NotificationEODDataM>> notificationData = findNotificationEodRejectNCB();
			if(null==notificationData){
				notificationData = new HashMap<String,ArrayList<NotificationEODDataM>>();
			}
			eodRequest = getEODTaskRequest(notificationData);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		return eodRequest;
	}
	private NotificationEODRequestDataM getEODTaskRequest(HashMap<String,ArrayList<NotificationEODDataM>> notificationData)throws InfBatchException{
		return getEODTaskRequest(notificationData, null);
	}
	private NotificationEODRequestDataM getEODTaskRequest(HashMap<String,ArrayList<NotificationEODDataM>> notificationData,HashMap<String, ArrayList<VCEmpInfoDataM>> managerData)throws InfBatchException{
		NotificationEODRequestDataM eodRequest = new NotificationEODRequestDataM();
		try{
			if(null==notificationData){
				notificationData = new HashMap<String,ArrayList<NotificationEODDataM>>();
			}
			if(null==managerData){
				managerData = new HashMap<String, ArrayList<VCEmpInfoDataM>>();
			}
			HashMap<String,ArrayList<String>> reasonData = new HashMap<String,ArrayList<String>>();
			MasterNotificationConfigDAO masterConfigDAO = NotificationFactory.getMasterNotificationConfigDAO();
			for(String eodNotificationId : notificationData.keySet()){
				if(InfBatchUtil.empty(eodNotificationId)) continue;
				logger.debug("eodNotificationId : "+eodNotificationId);
				ArrayList<NotificationEODDataM> eodNotificationItems = notificationData.get(eodNotificationId);
				if(!InfBatchUtil.empty(eodNotificationItems)){
					for(NotificationEODDataM eodNotificationItem : eodNotificationItems){
						logger.debug("eodNotificationItem : "+eodNotificationItem);
						String applicationTemplate = Formatter.displayText(eodNotificationItem.getApplicationTemplate());
						String saleChannel = Formatter.displayText(eodNotificationItem.getSaleChannel());
						String recommendChannel = Formatter.displayText(eodNotificationItem.getRecommendChannel());
						String configId = applicationTemplate+"_"+saleChannel+"_"+recommendChannel;
						logger.debug("configId : "+configId);
						if(!reasonData.containsKey(configId)){
							ArrayList<String> reasonCodes = masterConfigDAO.getRejectReasons(applicationTemplate,saleChannel
									,recommendChannel,NOTIFICATION_SEND_TIME_EOD,NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_LIST);
							reasonData.put(configId,reasonCodes);
						}
					}
				}
			}
			eodRequest.setNotificationData(notificationData);
			eodRequest.setReasonData(reasonData);
			eodRequest.setManagerData(managerData);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		return eodRequest;
	}
	public String runMakeEmailBatchFlag(String applicationGroupId,int lifeCycle,Connection conn) throws InfBatchException{
		String runMakeEmailBatchFlag = "N";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT 1 ");
			sql.append(" FROM INF_BATCH_LOG  ");
			sql.append(" WHERE INTERFACE_CODE IN ('AFP','AFP001','AFP002','PDF001') AND SYSTEM01='EMAIL' AND SYSTEM03 = 'SELLER' ");
			sql.append(" AND APPLICATION_GROUP_ID=? AND LIFE_CYCLE=? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			ps.setInt(2, lifeCycle);
			rs = ps.executeQuery();
			if(rs.next()){
				runMakeEmailBatchFlag = "Y";
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return runMakeEmailBatchFlag;
	}
	private List<NotificationConfig> getNotificationConfigs(String templateCode,String channelCode,String sendTime,List<String> notificationTypes,List<String> sendTos,Connection conn) throws Exception{
		List<NotificationConfig> notificationConfigs = new ArrayList<NotificationConfig>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		logger.debug("templateCode : "+templateCode);
		logger.debug("channelCode : "+channelCode);
		logger.debug("sendTime : "+sendTime);
		logger.debug("notificationTypes : "+notificationTypes);
		logger.debug("sendTos : "+sendTos);
		try{
			StringBuilder sql = new StringBuilder();
				sql.append(" SELECT DISTINCT ");
				sql.append("     S.NOTIFICATION_TYPE, ");
				sql.append("     S.SEND_TIME, ");
				sql.append("     D.SEND_TO, ");
				sql.append("     M.JOB_CODE, ");
				sql.append("     M.OPTIONAL1 , ");
				sql.append("     M.OPTIONAL2 , ");
				sql.append("     M.OPTIONAL3 , ");
				sql.append("     M.OPTIONAL4 , ");
				sql.append("     M.OPTIONAL5 , ");
				sql.append("     M.PATTERN , ");
				sql.append("     D.FIX_EMAIL, ");
				sql.append("     NVL(D.SEND_TO,'')||NVL(M.JOB_CODE,'')||NVL(M.OPTIONAL1,'')||NVL(M.OPTIONAL2,'')||NVL(M.OPTIONAL3,'')||NVL(M.OPTIONAL4,'')||NVL(M.OPTIONAL5,'') NOTIFY_ID, ");
				sql.append("     M.CHANNEL_CODE AS MANAGER_CHANNEL ");
				sql.append(" FROM MS_NOTI_APP_TEMPLATE T ");
				sql.append(" JOIN MS_NOTI_GROUP G ON G.GROUP_CODE =T.GROUP_CODE ");
				sql.append(" JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE ");
				sql.append(" JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE ");
				sql.append(" JOIN MS_NOTI_SENDING_DETAIL D ON S.SENDING_ID = D.SENDING_ID ");
				sql.append(" LEFT JOIN MS_NOTI_SENDING_PRIORITY P ON P.GROUP_CHANNEL_CODE = S.GROUP_CHANNEL_CODE AND P.SENDING_ID = S.SENDING_ID ");
				sql.append(" LEFT JOIN MS_NOTI_MANAGER_CHANNEL M ON M.CHANNEL_CODE = C.CHANNEL_CODE ");
				sql.append(" WHERE (T.TEMPLATE_CODE = ? OR T.TEMPLATE_CODE = 'ALL') ");
				sql.append(" AND S.SEND_TIME = ? ");
				if(!Util.empty(notificationTypes)){
					sql.append(" AND S.NOTIFICATION_TYPE IN (");
					String comma = "";
					for(String notificationType : notificationTypes) {
						sql.append(comma+"?");
						comma = ",";
					}
					sql.append(")");
				}
				sql.append(" AND C.CHANNEL_CODE = ? ");
				if(!Util.empty(sendTos)){
					sql.append(" AND D.SEND_TO IN (");
					String comma = "";
					for(String sendTo : sendTos) {
						sql.append(comma+"?");
						comma = ",";
					}
					sql.append(")");
				}
				logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				int index = 1;
				ps.setString(index++, templateCode);
				ps.setString(index++, sendTime);
				if(!Util.empty(notificationTypes)){
					for(String notificationType : notificationTypes) {
						ps.setString(index++,notificationType);
					}
				}
				ps.setString(index++, channelCode);
				if(!Util.empty(sendTos)){
					for(String sendTo : sendTos) {
						ps.setString(index++,sendTo);
					}
				}
				rs = ps.executeQuery();
				while(rs.next()){
					String NOTIFICATION_TYPE = rs.getString("NOTIFICATION_TYPE");
					String SEND_TIME = rs.getString("SEND_TIME");
					String SEND_TO = rs.getString("SEND_TO");
					String JOB_CODE = rs.getString("JOB_CODE");
					String OPTIONAL1 = rs.getString("OPTIONAL1");
					String OPTIONAL2 = rs.getString("OPTIONAL2");
					String OPTIONAL3 = rs.getString("OPTIONAL3");
					String OPTIONAL4= rs.getString("OPTIONAL4");
					String OPTIONAL5 = rs.getString("OPTIONAL5");
					String PATTERN = rs.getString("PATTERN");
					String FIX_EMAIL = rs.getString("FIX_EMAIL");
					String NOTIFY_ID = rs.getString("NOTIFY_ID");
					String MANAGER_CHANNEL = rs.getString("MANAGER_CHANNEL");
					NotificationConfig notificationConfig = new NotificationConfig();
						notificationConfig.setNotificationType(NOTIFICATION_TYPE);
						notificationConfig.setSendTime(SEND_TIME);
						notificationConfig.setSendTo(SEND_TO);
						notificationConfig.setNotifyId(NOTIFY_ID);
						notificationConfig.setJobCode(JOB_CODE);
						notificationConfig.setOptional1(OPTIONAL1);
						notificationConfig.setOptional2(OPTIONAL2);
						notificationConfig.setOptional3(OPTIONAL3);
						notificationConfig.setOptional4(OPTIONAL4);
						notificationConfig.setOptional5(OPTIONAL5);
						notificationConfig.setPattern(PATTERN);
						notificationConfig.setFixEmail(FIX_EMAIL);
						notificationConfig.setManagerChannel(MANAGER_CHANNEL);
					notificationConfigs.add(notificationConfig);
				}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			closeConnection(ps, rs);
		}
		return notificationConfigs;
	}
	private String findEodRejectNcbNotificationId(String applicationGroupId,NotificationConfig notificationConfig){
		String eodNotificationId = "";
			eodNotificationId = applicationGroupId
								+"_"+notificationConfig.getSendTo();
		return eodNotificationId;
	}
	private String findEodNotificationId(NotificationConfig notificationConfig){
		return findEodNotificationId(notificationConfig, null);
	}
	private String findEodNotificationId(NotificationConfig notificationConfig,NotifyEODManagerInfoDataM notifyManagerInfo){
		String SEND_TO_TYPE_FIX = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX");
		String SEND_TO_TYPE_SELLER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
		String SEND_TO_TYPE_MANAGER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
		String NOTIFICATION_TYPE_BRANCH_SUMMARY = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY");
		String NOTIFICATION_TYPE_OTHER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER");
		String eodNotificationId = "";
		if(InfBatchProperty.lookup("NOTIFICATION_EOD_NOT_SEND_SALE_APPLICATION_TEMPLATE",notificationConfig.getApplicationTemplate())){
			if(!InfBatchUtil.empty(notificationConfig.getFixEmail())){
				eodNotificationId = notificationConfig.getNotificationType()
									+"_"+notificationConfig.getSendTo()
									+"_"+notificationConfig.getFixEmail();
//									+"_"+notificationConfig.getSaleBranchCode();		
			}else if(SEND_TO_TYPE_MANAGER.equals(notificationConfig.getSendTo()) && notificationConfig.getNotificationType().equals(NOTIFICATION_TYPE_BRANCH_SUMMARY)){
				if( !InfBatchUtil.empty(notifyManagerInfo)){
					if(!InfBatchUtil.empty(notifyManagerInfo.getManageInfos())){
						String managerId = notifyManagerInfo.getManageInfos().get(0).getEmpId();
						eodNotificationId = notificationConfig.getNotificationType()
								+"_"+notificationConfig.getSendTo()
//								+"_"+notificationConfig.getJobCode()
								+"_"+managerId;
//								+"_"+notificationConfig.getSaleBranchCode();
					}
				}
			}else if(SEND_TO_TYPE_MANAGER.equals(notificationConfig.getSendTo()) && notificationConfig.getNotificationType().equals(NOTIFICATION_TYPE_OTHER)){
				eodNotificationId = notificationConfig.getNotificationType()
						+"_"+notificationConfig.getSendTo()
						+"_"+notificationConfig.getJobCode();
//						+"_"+notificationConfig.getSaleBranchCode();
			}
		}else{
			if(SEND_TO_TYPE_FIX.equals(notificationConfig.getSendTo())){
				eodNotificationId = notificationConfig.getNotificationType()
									+"_"+notificationConfig.getSendTo()
									+"_"+notificationConfig.getFixEmail();
//									+"_"+notificationConfig.getSaleBranchCode();					
			}else if(SEND_TO_TYPE_MANAGER.equals(notificationConfig.getSendTo()) && notificationConfig.getNotificationType().equals(NOTIFICATION_TYPE_BRANCH_SUMMARY)){
				if( !InfBatchUtil.empty(notifyManagerInfo)){
					if(!InfBatchUtil.empty(notifyManagerInfo.getManageInfos())){
						String managerId = notifyManagerInfo.getManageInfos().get(0).getEmpId();
						eodNotificationId = notificationConfig.getNotificationType()
											+"_"+notificationConfig.getSendTo()
//											+"_"+notificationConfig.getJobCode()
											+"_"+managerId;
//											+"_"+notificationConfig.getSaleBranchCode();
					}
				}
			}else if(SEND_TO_TYPE_MANAGER.equals(notificationConfig.getSendTo()) && notificationConfig.getNotificationType().equals(NOTIFICATION_TYPE_OTHER)){
				eodNotificationId = notificationConfig.getNotificationType()
									+"_"+notificationConfig.getSendTo()
									+"_"+notificationConfig.getJobCode();
//									+"_"+notificationConfig.getSaleBranchCode();
			}else if(SEND_TO_TYPE_SELLER.equals(notificationConfig.getSendTo())){
				eodNotificationId = notificationConfig.getNotificationType()
									+"_"+notificationConfig.getSendTo()
									+"_"+notificationConfig.getSaleId();
//									+"_"+notificationConfig.getSaleBranchCode();
			}
		}
		return eodNotificationId;
	}
	public boolean removeEodNotificationOther(String notifyId,List<NotificationConfig> channelConfigs){
		if(null!=channelConfigs){
			for (NotificationConfig notificationConfig : channelConfigs) {
				if(InfBatchProperty.lookup("NOTIFICATION_EOD_NOTIFICATION_TYPE_REMOVE_SALE", notificationConfig.getNotificationType())
					&&null!=notificationConfig.getNotifyId()&&notificationConfig.getNotifyId().equals(notifyId)){
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public HashMap<String, ArrayList<NotificationEODDataM>> findNotificationEod() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
		String NOTIFICATION_EOD_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_INTERFACE_CODE");
		String SEND_TO_TYPE_MANAGER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
		HashMap<String,ArrayList<NotificationEODDataM>> notificationEodData  = new HashMap<String, ArrayList<NotificationEODDataM>>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT ");
			sql.append("     G.APPLICATION_GROUP_ID, ");
			sql.append("     G.APPLICATION_GROUP_NO, ");
			sql.append("     G.LIFE_CYCLE, ");
			sql.append("     ( ");
			sql.append("         CASE ");
			sql.append("             WHEN (G.JOB_STATE = 'NM9901' ");
			sql.append("                 OR  G.JOB_STATE='NM9904') ");
			sql.append("             THEN 'Approve' ");
			sql.append("             ELSE 'Reject' ");
			sql.append("         END ) APP_STATUS, ");
			sql.append("     G.JOB_STATE, ");
			sql.append("     NVL(S.SALE_CHANNEL,'N') SALE_CHANNEL, ");
			sql.append("     NVL(R.SALE_CHANNEL,'N') RECOMMEND_CHANNEL, ");
			sql.append("     G.APPLICATION_TEMPLATE, ");
			sql.append("     S.SALES_ID             SALE_ID, ");
			sql.append("     R.SALES_ID             RECOMMEND_ID, ");
			sql.append("     S.SALES_BRANCH_CODE AS SALES_BRANCH_CODE, ");
			sql.append("     R.SALES_BRANCH_CODE AS RECOMMEND_BRANCH_CODE, ");
			sql.append("     S.SALES_BRANCH_NAME AS SALES_BRANCH_NAME, ");
			sql.append("     R.SALES_BRANCH_NAME AS RECOMMEND_BRANCH_NAME ");
			sql.append(" FROM ORIG_APPLICATION_GROUP G ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO S ON S.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"' AND G.APPLICATION_GROUP_ID = S.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO R ON R.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_REFERENCE+"' AND G.APPLICATION_GROUP_ID = R.APPLICATION_GROUP_ID ");
			sql.append(" WHERE G.JOB_STATE IN ("+InfBatchProperty.getSQLParameter("NOTIFICATION_EOD_JOB_STATE")+")");
			sql.append(" AND NOT EXISTS ");
			sql.append("     (SELECT 1 FROM INF_BATCH_LOG B ");
			sql.append("      WHERE INTERFACE_CODE = '"+NOTIFICATION_EOD_INTERFACE_CODE+"' AND B.INTERFACE_STATUS = '"+INTERFACE_STATUS_COMPLETE+"'" +
					" AND G.APPLICATION_GROUP_ID = B.APPLICATION_GROUP_ID AND G.LIFE_CYCLE = B.LIFE_CYCLE) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			while(rs.next()){
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String APP_STATUS = rs.getString("APP_STATUS");
				String JOB_STATE = rs.getString("JOB_STATE");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				
				String SALE_ID = rs.getString("SALE_ID");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				String SALES_BRANCH_CODE = rs.getString("SALES_BRANCH_CODE");
				String SALES_BRANCH_NAME = rs.getString("SALES_BRANCH_NAME");

				String RECOMMEND_ID = rs.getString("RECOMMEND_ID");
				String RECOMMEND_CHANNEL = rs.getString("RECOMMEND_CHANNEL");
				String RECOMMEND_BRANCH_CODE = rs.getString("RECOMMEND_BRANCH_CODE");
				String RECOMMEND_BRANCH_NAME = rs.getString("RECOMMEND_BRANCH_NAME");
				
				int LIFE_CYCLE = rs.getInt("LIFE_CYCLE");
				
//				logger.debug("APPLICATION_GROUP_ID : "+APPLICATION_GROUP_ID);
//				logger.debug("APPLICATION_GROUP_NO : "+APPLICATION_GROUP_NO);
//				logger.debug("SALE_CHANNEL : "+SALE_CHANNEL);
//				logger.debug("RECOMMEND_CHANNEL : "+RECOMMEND_CHANNEL);
				
				//if(!Util.empty(SALE_CHANNEL)&&(!"N".equals(SALE_CHANNEL))){
				if(!Util.empty(SALE_CHANNEL)
						&&(!"N".equals(SALE_CHANNEL) || APPLICATION_TEMPLATE_THAIBEV.equals(APPLICATION_TEMPLATE))){
					SALE_CHANNEL = "N".equals(SALE_CHANNEL) ? DEFUALT_DATA_TYPE_ALL : SALE_CHANNEL ; // support ThaiBev no sale (send to fix)
					logger.debug("SALE_CHANNEL : "+SALE_CHANNEL);
					List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_NOTIFICATION_TYPE_SALE");
					List<NotificationConfig> channelConfigs = getNotificationConfigs(APPLICATION_TEMPLATE,SALE_CHANNEL,NOTIFICATION_SEND_TIME_EOD
							,notificationTypes,null, conn);
					List<NotificationConfig> filterChannelConfigs = new ArrayList<NotificationConfig>();
					if(null!=channelConfigs){
						for(NotificationConfig notificationConfig : channelConfigs){
							if(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY")
									.equals(notificationConfig.getNotificationType())){
								filterChannelConfigs.add(notificationConfig);
							}else if(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER")
									.equals(notificationConfig.getNotificationType())){
								String runMakeEmailBatchFlag = runMakeEmailBatchFlag(APPLICATION_GROUP_ID,LIFE_CYCLE,conn);
								logger.debug("runMakeEmailBatchFlag : "+runMakeEmailBatchFlag);
								if("N".equals(runMakeEmailBatchFlag)){
									filterChannelConfigs.add(notificationConfig);
								}
							}
						}
					}
					if(null!=filterChannelConfigs){
						for(NotificationConfig notificationConfig : filterChannelConfigs){
							logger.debug("sale.NotificationConfig : "+notificationConfig);
							notificationConfig.setApplicationTemplate(APPLICATION_TEMPLATE);
							notificationConfig.setSaleBranchCode(SALES_BRANCH_CODE);
							notificationConfig.setSaleId(SALE_ID);
							notificationConfig.setRecommendBranchCode(RECOMMEND_BRANCH_CODE);
							notificationConfig.setRecommendId(RECOMMEND_ID);
							String eodNotificationId = findEodNotificationId(notificationConfig);
							logger.debug("channelConfigs.eodNotificationId : "+eodNotificationId);
							if(!Util.empty(eodNotificationId)){
								ArrayList<NotificationEODDataM>  eodNotificationItems = notificationEodData.get(eodNotificationId);
								if(InfBatchUtil.empty(eodNotificationItems)){
									eodNotificationItems = new  ArrayList<NotificationEODDataM>();
									notificationEodData.put(eodNotificationId, eodNotificationItems);
								}
								NotificationEODDataM notificationEod = new NotificationEODDataM();
									notificationEod.setLifeCycle(LIFE_CYCLE);
									notificationEod.setApplicationGroupId(APPLICATION_GROUP_ID);
									notificationEod.setApplicationGroupNo(APPLICATION_GROUP_NO);
									notificationEod.setAppStatus(APP_STATUS);
									notificationEod.setFixEmail(notificationConfig.getFixEmail());
									notificationEod.setJobState(JOB_STATE);
									notificationEod.setRecommend(RECOMMEND_ID);
									notificationEod.setSaleChannel(SALE_CHANNEL);
									notificationEod.setRecommendChannel(RECOMMEND_CHANNEL);
									notificationEod.setSaleId(SALE_ID);
									notificationEod.setSendTime(notificationConfig.getSendTime());
									notificationEod.setSendTo(notificationConfig.getSendTo());
									notificationEod.setNotificationType(notificationConfig.getNotificationType());
									notificationEod.setApplicationTemplate(APPLICATION_TEMPLATE);
									notificationEod.setBranchName(SALES_BRANCH_NAME);
									notificationEod.setBranchCode(SALES_BRANCH_CODE);
									notificationEod.setManagerChannel(notificationConfig.getManagerChannel());
								JobCodeDataM jobCode = new JobCodeDataM();
									jobCode.setJobCode(notificationConfig.getJobCode());
									jobCode.setOptional1(notificationConfig.getOptional1());
									jobCode.setOptional2(notificationConfig.getOptional2());
									jobCode.setOptional3(notificationConfig.getOptional3());
									jobCode.setOptional4(notificationConfig.getOptional4());
									jobCode.setOptional5(notificationConfig.getOptional5());
									jobCode.setPattern(notificationConfig.getPattern());
								notificationEod.addJobCodes(jobCode);
								eodNotificationItems.add(notificationEod);
							}
						}
					}
				}
				//if(!Util.empty(RECOMMEND_CHANNEL)&&!"N".equals(RECOMMEND_CHANNEL)){
				if(!Util.empty(RECOMMEND_CHANNEL)
						&&(!"N".equals(RECOMMEND_CHANNEL) ||  APPLICATION_TEMPLATE_THAIBEV.equals(APPLICATION_TEMPLATE))){
					SALE_CHANNEL = "N".equals(SALE_CHANNEL) ? DEFUALT_DATA_TYPE_ALL : SALE_CHANNEL ; // support ThaiBev no sale (send to fix)
					logger.debug("RECOMMEND_CHANNEL : "+RECOMMEND_CHANNEL);
					List<String> sendTos = new ArrayList<String>();
					sendTos.add(SEND_TO_TYPE_MANAGER);
					sendTos.add(InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX"));
					List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_NOTIFICATION_TYPE_RECOMMEND");
					List<NotificationConfig> recommendConfigs = getNotificationConfigs(APPLICATION_TEMPLATE,RECOMMEND_CHANNEL,NOTIFICATION_SEND_TIME_EOD
							,notificationTypes,sendTos,conn);
//					logger.debug("recommendConfigs : "+recommendConfigs);
					if(null!=recommendConfigs){
						for(NotificationConfig notificationConfig : recommendConfigs){
							logger.debug("recommend.NotificationConfig : "+notificationConfig);
							notificationConfig.setApplicationTemplate(APPLICATION_TEMPLATE);
							notificationConfig.setSaleBranchCode(SALES_BRANCH_CODE);
							notificationConfig.setSaleId(SALE_ID);
							notificationConfig.setRecommendBranchCode(RECOMMEND_BRANCH_CODE);
							notificationConfig.setRecommendId(RECOMMEND_ID);
							String eodNotificationId = findEodNotificationId(notificationConfig);
							logger.debug("recommendConfigs.eodNotificationId : "+eodNotificationId);
							if(!Util.empty(eodNotificationId)){
								ArrayList<NotificationEODDataM>  eodNotificationItems = notificationEodData.get(eodNotificationId);
								if(InfBatchUtil.empty(eodNotificationItems)){
									eodNotificationItems = new  ArrayList<NotificationEODDataM>();
									notificationEodData.put(eodNotificationId, eodNotificationItems);
								}
								NotificationEODDataM notificationEod = new NotificationEODDataM();
									notificationEod.setLifeCycle(LIFE_CYCLE);
									notificationEod.setApplicationGroupId(APPLICATION_GROUP_ID);
									notificationEod.setApplicationGroupNo(APPLICATION_GROUP_NO);
									notificationEod.setAppStatus(APP_STATUS);
									notificationEod.setFixEmail(notificationConfig.getFixEmail());
									notificationEod.setJobState(JOB_STATE);
									notificationEod.setRecommend(RECOMMEND_ID);
									notificationEod.setSaleChannel(SALE_CHANNEL);
									notificationEod.setRecommendChannel(RECOMMEND_CHANNEL);
									notificationEod.setSaleId(SALE_ID);
									notificationEod.setSendTime(notificationConfig.getSendTime());
									notificationEod.setSendTo(notificationConfig.getSendTo());
									notificationEod.setNotificationType(notificationConfig.getNotificationType());
									notificationEod.setApplicationTemplate(APPLICATION_TEMPLATE);
									notificationEod.setBranchName(RECOMMEND_BRANCH_NAME);
									notificationEod.setManagerChannel(notificationConfig.getManagerChannel());
								JobCodeDataM jobCode = new JobCodeDataM();
									jobCode.setJobCode(notificationConfig.getJobCode());
									jobCode.setOptional1(notificationConfig.getOptional1());
									jobCode.setOptional2(notificationConfig.getOptional2());
									jobCode.setOptional3(notificationConfig.getOptional3());
									jobCode.setOptional4(notificationConfig.getOptional4());
									jobCode.setOptional5(notificationConfig.getOptional5());
									jobCode.setPattern(notificationConfig.getPattern());
								notificationEod.addJobCodes(jobCode);
								eodNotificationItems.add(notificationEod);
							}
						}
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return notificationEodData;
	}
	private BranchSummaryResponseDataM findNotificationEodBranchSummary() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
		String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
		//String NOTIFICATION_EOD_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_INTERFACE_CODE");
		String NOTIFICATION_EOD_SUMMARY_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_SUMMARY_INTERFACE_CODE");
		BranchSummaryResponseDataM branchSummaryData = new BranchSummaryResponseDataM();
		HashMap<String,ArrayList<NotificationEODDataM>> notificationEodData  = new HashMap<String, ArrayList<NotificationEODDataM>>();
		HashMap<String,ArrayList<VCEmpInfoDataM>> managerData  = new HashMap<String, ArrayList<VCEmpInfoDataM>>();
		int NOTIFICATION_EOD_BRANCH_SUMMARY_EXECUTE_ITEM = Integer.parseInt(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_BRANCH_SUMMARY_EXECUTE_ITEM"));
		List<String> qrList = new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT ");
			sql.append("     G.APPLICATION_GROUP_ID, ");
			sql.append("     G.APPLICATION_GROUP_NO, ");
			sql.append("     G.LIFE_CYCLE, ");
			sql.append("     ( ");
			sql.append("         CASE ");
			sql.append("             WHEN (G.JOB_STATE = 'NM9901' ");
			sql.append("                 OR  G.JOB_STATE='NM9904') ");
			sql.append("             THEN 'Approve' ");
			sql.append("             ELSE 'Reject' ");
			sql.append("         END ) APP_STATUS, ");
			sql.append("     G.JOB_STATE, ");
			sql.append("     NVL(S.SALE_CHANNEL,'N') SALE_CHANNEL, ");
			sql.append("     NVL(R.SALE_CHANNEL,'N') RECOMMEND_CHANNEL, ");
			sql.append("     G.APPLICATION_TEMPLATE, ");
			sql.append("     S.SALES_ID             SALE_ID, ");
			sql.append("     R.SALES_ID             RECOMMEND_ID, ");
			sql.append("     S.SALES_BRANCH_CODE AS SALES_BRANCH_CODE, ");
			sql.append("     R.SALES_BRANCH_CODE AS RECOMMEND_BRANCH_CODE, ");
			sql.append("     S.SALES_BRANCH_NAME AS SALES_BRANCH_NAME, ");
			sql.append("     R.SALES_BRANCH_NAME AS RECOMMEND_BRANCH_NAME ");
			sql.append(" FROM ORIG_APPLICATION_GROUP G ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO S ON S.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"' AND G.APPLICATION_GROUP_ID = S.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO R ON R.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_REFERENCE+"' AND G.APPLICATION_GROUP_ID = R.APPLICATION_GROUP_ID ");
			sql.append(" WHERE G.JOB_STATE IN ("+InfBatchProperty.getSQLParameter("NOTIFICATION_EOD_JOB_STATE")+")");
			
			sql.append(" AND NOT EXISTS ");
			sql.append("     (SELECT 1 FROM INF_BATCH_LOG B ");
			//sql.append("      WHERE INTERFACE_CODE = '"+NOTIFICATION_EOD_INTERFACE_CODE+"' AND B.INTERFACE_STATUS = '"+INTERFACE_STATUS_COMPLETE+"'" +
			sql.append("      WHERE INTERFACE_CODE = '"+NOTIFICATION_EOD_SUMMARY_INTERFACE_CODE+"' AND B.INTERFACE_STATUS IN ( '"+INTERFACE_STATUS_COMPLETE+"','"+INTERFACE_STATUS_NOT_SEND+"' )  " +
					" AND G.APPLICATION_GROUP_ID = B.APPLICATION_GROUP_ID AND G.LIFE_CYCLE = B.LIFE_CYCLE) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next() && qrList != null && qrList.size() < NOTIFICATION_EOD_BRANCH_SUMMARY_EXECUTE_ITEM){
				NotifyEODApplicationDataM notifyEOD = new NotifyEODApplicationDataM();
					notifyEOD.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					notifyEOD.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
					notifyEOD.setAppStatus(rs.getString("APP_STATUS"));
					notifyEOD.setJobState(rs.getString("JOB_STATE"));
					notifyEOD.setApplicationTemplate(rs.getString("APPLICATION_TEMPLATE"));
					notifyEOD.setSaleId(rs.getString("SALE_ID"));
					notifyEOD.setSaleChannel(rs.getString("SALE_CHANNEL"));
					notifyEOD.setSalesBranchCode(rs.getString("SALES_BRANCH_CODE"));
					notifyEOD.setSalesBranchName(rs.getString("SALES_BRANCH_NAME"));
					notifyEOD.setRecommendId(rs.getString("RECOMMEND_ID"));
					notifyEOD.setRecommendChannel(rs.getString("RECOMMEND_CHANNEL"));
					notifyEOD.setRecommendBranchCode(rs.getString("RECOMMEND_BRANCH_CODE"));
					notifyEOD.setRecommendBranchName(rs.getString("RECOMMEND_BRANCH_NAME"));
					notifyEOD.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY");
				addBranchSummaryItem(notificationEodData,notifyEOD,notificationTypes, managerData,conn);
				
				if(!qrList.contains(notifyEOD.getApplicationGroupNo())){
					qrList.add(notifyEOD.getApplicationGroupNo());
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		branchSummaryData.setEodBranchSummaryData(notificationEodData);
		branchSummaryData.setManagerData(managerData);
		return branchSummaryData;
	}
	private void addBranchSummaryItem(HashMap<String,ArrayList<NotificationEODDataM>> notificationEodData,NotifyEODApplicationDataM notifyEOD,List<String> notificationTypes
			,HashMap<String,ArrayList<VCEmpInfoDataM>> managerData,Connection conn)throws Exception{
		String SEND_TO_TYPE_MANAGER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
		List<NotifyEODManagerInfoDataM> notifyEODManagerInfos = new ArrayList<NotifyEODManagerInfoDataM>(); 
		try{
			String APPLICATION_GROUP_ID = notifyEOD.getApplicationGroupId();
			String APPLICATION_GROUP_NO = notifyEOD.getApplicationGroupNo();
			String APP_STATUS = notifyEOD.getAppStatus();
			String JOB_STATE = notifyEOD.getJobState();
			String APPLICATION_TEMPLATE = notifyEOD.getApplicationTemplate();
			
			String SALE_ID = notifyEOD.getSaleId();
			String SALE_CHANNEL = notifyEOD.getSaleChannel();
			String SALES_BRANCH_CODE = notifyEOD.getSalesBranchCode();
			String SALES_BRANCH_NAME = notifyEOD.getSalesBranchName();

			String RECOMMEND_ID = notifyEOD.getRecommendId();
			String RECOMMEND_CHANNEL = notifyEOD.getRecommendChannel();
			String RECOMMEND_BRANCH_CODE = notifyEOD.getRecommendBranchCode();
			String RECOMMEND_BRANCH_NAME = notifyEOD.getRecommendBranchName();
			int LIFE_CYCLE = notifyEOD.getLifeCycle();
			//if(!Util.empty(SALE_CHANNEL)&&(!"N".equals(SALE_CHANNEL))){
			List<NotifyEODSalesInfoDataM> notifySaleInfos = new ArrayList<NotifyEODSalesInfoDataM>();
			if(!Util.empty(SALE_CHANNEL)
					&&(!"N".equals(SALE_CHANNEL) || APPLICATION_TEMPLATE_THAIBEV.equals(APPLICATION_TEMPLATE))){
				SALE_CHANNEL = "N".equals(SALE_CHANNEL) ? DEFUALT_DATA_TYPE_ALL : SALE_CHANNEL ; // support ThaiBev no sale (send to fix)
				logger.debug("SALE_CHANNEL : "+SALE_CHANNEL);
				//List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_NOTIFICATION_TYPE_SALE");
				List<NotificationConfig> channelConfigs = getNotificationConfigs(APPLICATION_TEMPLATE,SALE_CHANNEL,NOTIFICATION_SEND_TIME_EOD
						,notificationTypes,null, conn);
				List<NotificationConfig> filterChannelConfigs = new ArrayList<NotificationConfig>();
				if(null!=channelConfigs){
					for(NotificationConfig notificationConfig : channelConfigs){
						if(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY")
								.equals(notificationConfig.getNotificationType())){
							filterChannelConfigs.add(notificationConfig);
						}else if(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER")
								.equals(notificationConfig.getNotificationType())){
							String runMakeEmailBatchFlag = runMakeEmailBatchFlag(APPLICATION_GROUP_ID,LIFE_CYCLE,conn);
							logger.debug("runMakeEmailBatchFlag : "+runMakeEmailBatchFlag);
							if("N".equals(runMakeEmailBatchFlag)){
								filterChannelConfigs.add(notificationConfig);
							}
						}
					}
				}
				if(null!=filterChannelConfigs){
					for(NotificationConfig notificationConfig : filterChannelConfigs){
						logger.debug("sale.NotificationConfig : "+notificationConfig);
						notificationConfig.setApplicationTemplate(APPLICATION_TEMPLATE);
						notificationConfig.setSaleBranchCode(SALES_BRANCH_CODE);
						notificationConfig.setSaleId(SALE_ID);
						notificationConfig.setRecommendBranchCode(RECOMMEND_BRANCH_CODE);
						notificationConfig.setRecommendId(RECOMMEND_ID);
						EODSendToManagerUtil.addEODSaleInfo(notifySaleInfos, notifyEOD, notificationConfig);
						NotifyEODSalesInfoDataM notifyEODSaleInfo = EODSendToManagerUtil.getEODSaleInfo(notifySaleInfos, notifyEOD, notificationConfig);
						NotifyEODManagerInfoDataM eodManagerInfo = null;
						if(!InfBatchUtil.empty(notifyEODSaleInfo)){
							eodManagerInfo = EODSendToManagerUtil.addManagerOfSeller(notifyEODManagerInfos, notifyEODSaleInfo);
						}
						String eodNotificationId = findEodNotificationId(notificationConfig,eodManagerInfo);
						logger.debug("channelConfigs.eodNotificationId : "+eodNotificationId);
						if(!Util.empty(eodNotificationId)){
							ArrayList<NotificationEODDataM>  eodNotificationItems = notificationEodData.get(eodNotificationId);
							if(InfBatchUtil.empty(eodNotificationItems)){
								eodNotificationItems = new  ArrayList<NotificationEODDataM>();
								notificationEodData.put(eodNotificationId, eodNotificationItems);
							}
							if(notificationConfig.getSendTo().equals(SEND_TO_TYPE_MANAGER)){
								ArrayList<VCEmpInfoDataM> vcEmpManagers = managerData.get(eodNotificationId);
								if(InfBatchUtil.empty(vcEmpManagers)){
									if(!InfBatchUtil.empty(eodManagerInfo)){
										if(!InfBatchUtil.empty(eodManagerInfo.getManageInfos())){
											managerData.put(eodNotificationId, eodManagerInfo.getManageInfos());	
										}
									}
								}
							}
							NotificationEODDataM notificationEod = new NotificationEODDataM();
								notificationEod.setLifeCycle(LIFE_CYCLE);
								notificationEod.setApplicationGroupId(APPLICATION_GROUP_ID);
								notificationEod.setApplicationGroupNo(APPLICATION_GROUP_NO);
								notificationEod.setAppStatus(APP_STATUS);
								notificationEod.setFixEmail(notificationConfig.getFixEmail());
								notificationEod.setJobState(JOB_STATE);
								notificationEod.setRecommend(RECOMMEND_ID);
								notificationEod.setSaleChannel(SALE_CHANNEL);
								notificationEod.setRecommendChannel(RECOMMEND_CHANNEL);
								notificationEod.setSaleId(SALE_ID);
								notificationEod.setSendTime(notificationConfig.getSendTime());
								notificationEod.setSendTo(notificationConfig.getSendTo());
								notificationEod.setNotificationType(notificationConfig.getNotificationType());
								notificationEod.setApplicationTemplate(APPLICATION_TEMPLATE);
								notificationEod.setBranchName(SALES_BRANCH_NAME);
								notificationEod.setBranchCode(SALES_BRANCH_CODE);
								notificationEod.setManagerChannel(notificationConfig.getManagerChannel());
							JobCodeDataM jobCode = new JobCodeDataM();
								jobCode.setJobCode(notificationConfig.getJobCode());
								jobCode.setOptional1(notificationConfig.getOptional1());
								jobCode.setOptional2(notificationConfig.getOptional2());
								jobCode.setOptional3(notificationConfig.getOptional3());
								jobCode.setOptional4(notificationConfig.getOptional4());
								jobCode.setOptional5(notificationConfig.getOptional5());
								jobCode.setPattern(notificationConfig.getPattern());
							notificationEod.addJobCodes(jobCode);
							eodNotificationItems.add(notificationEod);
						}
					}
				}
			}
			//if(!Util.empty(RECOMMEND_CHANNEL)&&!"N".equals(RECOMMEND_CHANNEL)){
			if(!Util.empty(RECOMMEND_CHANNEL)
					&&(!"N".equals(RECOMMEND_CHANNEL) ||  APPLICATION_TEMPLATE_THAIBEV.equals(APPLICATION_TEMPLATE))){
				SALE_CHANNEL = "N".equals(SALE_CHANNEL) ? DEFUALT_DATA_TYPE_ALL : SALE_CHANNEL ; // support ThaiBev no sale (send to fix)
				logger.debug("RECOMMEND_CHANNEL : "+RECOMMEND_CHANNEL);
				List<String> sendTos = new ArrayList<String>();
				sendTos.add(SEND_TO_TYPE_MANAGER);
				sendTos.add(InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX"));
				//List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_NOTIFICATION_TYPE_RECOMMEND");
				List<NotificationConfig> recommendConfigs = getNotificationConfigs(APPLICATION_TEMPLATE,RECOMMEND_CHANNEL,NOTIFICATION_SEND_TIME_EOD
						,notificationTypes,sendTos,conn);
//				logger.debug("recommendConfigs : "+recommendConfigs);
				if(null!=recommendConfigs){
					for(NotificationConfig notificationConfig : recommendConfigs){
						logger.debug("recommend.NotificationConfig : "+notificationConfig);
						notificationConfig.setApplicationTemplate(APPLICATION_TEMPLATE);
						notificationConfig.setSaleBranchCode(SALES_BRANCH_CODE);
						notificationConfig.setSaleId(SALE_ID);
						notificationConfig.setRecommendBranchCode(RECOMMEND_BRANCH_CODE);
						notificationConfig.setRecommendId(RECOMMEND_ID);
						EODSendToManagerUtil.addEODSaleInfo(notifySaleInfos, notifyEOD, notificationConfig);
						NotifyEODSalesInfoDataM notifyEODSaleInfo = EODSendToManagerUtil.getEODSaleInfo(notifySaleInfos, notifyEOD, notificationConfig);
						NotifyEODManagerInfoDataM eodManagerInfo = null;
						if(!InfBatchUtil.empty(notifyEODSaleInfo)){
							eodManagerInfo = EODSendToManagerUtil.addManagerOfRecommend(notifyEODManagerInfos, notifyEODSaleInfo);
						}
						String eodNotificationId = findEodNotificationId(notificationConfig,eodManagerInfo);
						logger.debug("recommendConfigs.eodNotificationId : "+eodNotificationId);
						if(!Util.empty(eodNotificationId)){
							ArrayList<NotificationEODDataM>  eodNotificationItems = notificationEodData.get(eodNotificationId);
							if(InfBatchUtil.empty(eodNotificationItems)){
								eodNotificationItems = new  ArrayList<NotificationEODDataM>();
								notificationEodData.put(eodNotificationId, eodNotificationItems);
							}
							if(notificationConfig.getSendTo().equals(SEND_TO_TYPE_MANAGER)){
								ArrayList<VCEmpInfoDataM> vcEmpManagers = managerData.get(eodNotificationId);
								if(InfBatchUtil.empty(vcEmpManagers)){
									if(!InfBatchUtil.empty(eodManagerInfo)){
										if(!InfBatchUtil.empty(eodManagerInfo.getManageInfos())){
											managerData.put(eodNotificationId, eodManagerInfo.getManageInfos());	
										}
									}
								}
							}
							NotificationEODDataM notificationEod = new NotificationEODDataM();
								notificationEod.setLifeCycle(LIFE_CYCLE);
								notificationEod.setApplicationGroupId(APPLICATION_GROUP_ID);
								notificationEod.setApplicationGroupNo(APPLICATION_GROUP_NO);
								notificationEod.setAppStatus(APP_STATUS);
								notificationEod.setFixEmail(notificationConfig.getFixEmail());
								notificationEod.setJobState(JOB_STATE);
								notificationEod.setRecommend(RECOMMEND_ID);
								notificationEod.setSaleChannel(SALE_CHANNEL);
								notificationEod.setRecommendChannel(RECOMMEND_CHANNEL);
								notificationEod.setSaleId(SALE_ID);
								notificationEod.setSendTime(notificationConfig.getSendTime());
								notificationEod.setSendTo(notificationConfig.getSendTo());
								notificationEod.setNotificationType(notificationConfig.getNotificationType());
								notificationEod.setApplicationTemplate(APPLICATION_TEMPLATE);
								notificationEod.setBranchName(RECOMMEND_BRANCH_NAME);
								notificationEod.setManagerChannel(notificationConfig.getManagerChannel());
							JobCodeDataM jobCode = new JobCodeDataM();
								jobCode.setJobCode(notificationConfig.getJobCode());
								jobCode.setOptional1(notificationConfig.getOptional1());
								jobCode.setOptional2(notificationConfig.getOptional2());
								jobCode.setOptional3(notificationConfig.getOptional3());
								jobCode.setOptional4(notificationConfig.getOptional4());
								jobCode.setOptional5(notificationConfig.getOptional5());
								jobCode.setPattern(notificationConfig.getPattern());
							notificationEod.addJobCodes(jobCode);
							eodNotificationItems.add(notificationEod);
						}
					}
				}
			}
		}catch (Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
	}
	private HashMap<String, ArrayList<NotificationEODDataM>> findNotificationEodRejectNCB() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
		String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
		//String NOTIFICATION_EOD_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_INTERFACE_CODE");
		String NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE");
		String SEND_TO_TYPE_MANAGER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
		HashMap<String,ArrayList<NotificationEODDataM>> notificationEodData  = new HashMap<String, ArrayList<NotificationEODDataM>>();
		int NOTIFICATION_EOD_REJECT_NCB_EXECUTE_ITEM = Integer.parseInt(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_REJECT_NCB_EXECUTE_ITEM"));
		List<String> qrList = new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT ");
			sql.append("     G.APPLICATION_GROUP_ID, ");
			sql.append("     G.APPLICATION_GROUP_NO, ");
			sql.append("     G.LIFE_CYCLE, ");
			sql.append("     ( ");
			sql.append("         CASE ");
			sql.append("             WHEN (G.JOB_STATE = 'NM9901' ");
			sql.append("                 OR  G.JOB_STATE='NM9904') ");
			sql.append("             THEN 'Approve' ");
			sql.append("             ELSE 'Reject' ");
			sql.append("         END ) APP_STATUS, ");
			sql.append("     G.JOB_STATE, ");
			sql.append("     NVL(S.SALE_CHANNEL,'N') SALE_CHANNEL, ");
			sql.append("     NVL(R.SALE_CHANNEL,'N') RECOMMEND_CHANNEL, ");
			sql.append("     G.APPLICATION_TEMPLATE, ");
			sql.append("     S.SALES_ID             SALE_ID, ");
			sql.append("     R.SALES_ID             RECOMMEND_ID, ");
			sql.append("     S.SALES_BRANCH_CODE AS SALES_BRANCH_CODE, ");
			sql.append("     R.SALES_BRANCH_CODE AS RECOMMEND_BRANCH_CODE, ");
			sql.append("     S.SALES_BRANCH_NAME AS SALES_BRANCH_NAME, ");
			sql.append("     R.SALES_BRANCH_NAME AS RECOMMEND_BRANCH_NAME ");
			sql.append(" FROM ORIG_APPLICATION_GROUP G ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO S ON S.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"' AND G.APPLICATION_GROUP_ID = S.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO R ON R.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_REFERENCE+"' AND G.APPLICATION_GROUP_ID = R.APPLICATION_GROUP_ID ");
			sql.append(" WHERE G.JOB_STATE IN ("+InfBatchProperty.getSQLParameter("NOTIFICATION_EOD_JOB_STATE")+")");
			
			sql.append(" AND NOT EXISTS ");
			sql.append("     (SELECT 1 FROM INF_BATCH_LOG B ");
			sql.append("      WHERE INTERFACE_CODE = '"+NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE+"' AND B.INTERFACE_STATUS  IN ( '"+INTERFACE_STATUS_COMPLETE+"','"+INTERFACE_STATUS_NOT_SEND+"' ) " +
					" AND G.APPLICATION_GROUP_ID = B.APPLICATION_GROUP_ID AND G.LIFE_CYCLE = B.LIFE_CYCLE) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next() && qrList != null && qrList.size() < NOTIFICATION_EOD_REJECT_NCB_EXECUTE_ITEM){
//				NotifyEODApplicationDataM notifyEOD = new NotifyEODApplicationDataM();
//					notifyEOD.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
//					notifyEOD.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
//					notifyEOD.setAppStatus(rs.getString("APP_STATUS"));
//					notifyEOD.setJobState(rs.getString("JOB_STATE"));
//					notifyEOD.setApplicationTemplate(rs.getString("APPLICATION_TEMPLATE"));
//					notifyEOD.setSaleId(rs.getString("SALE_ID"));
//					notifyEOD.setSaleChannel(rs.getString("SALE_CHANNEL"));
//					notifyEOD.setSalesBranchCode(rs.getString("SALES_BRANCH_CODE"));
//					notifyEOD.setSalesBranchName(rs.getString("SALES_BRANCH_NAME"));
//					notifyEOD.setRecommendId(rs.getString("RECOMMEND_ID"));
//					notifyEOD.setRecommendChannel(rs.getString("RECOMMEND_CHANNEL"));
//					notifyEOD.setRecommendBranchCode(rs.getString("RECOMMEND_BRANCH_CODE"));
//					notifyEOD.setRecommendBranchName(rs.getString("RECOMMEND_BRANCH_NAME"));
//					notifyEOD.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String APP_STATUS = rs.getString("APP_STATUS");
				String JOB_STATE = rs.getString("JOB_STATE");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				
				String SALE_ID = rs.getString("SALE_ID");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				String SALES_BRANCH_CODE = rs.getString("SALES_BRANCH_CODE");
				String SALES_BRANCH_NAME = rs.getString("SALES_BRANCH_NAME");

				String RECOMMEND_ID = rs.getString("RECOMMEND_ID");
				String RECOMMEND_CHANNEL = rs.getString("RECOMMEND_CHANNEL");
				String RECOMMEND_BRANCH_CODE = rs.getString("RECOMMEND_BRANCH_CODE");
				String RECOMMEND_BRANCH_NAME = rs.getString("RECOMMEND_BRANCH_NAME");
				
				int LIFE_CYCLE = rs.getInt("LIFE_CYCLE");
				
				String reasonCode = "";					
					NotifyApplicationSegment notifyApplicationSegment = NotificationUtil.notifyApplication(APPLICATION_GROUP_ID);
					Reason reason = notifyApplicationSegment.findReasonApplicationGroup(APPLICATION_GROUP_ID);
				reasonCode = reason.getReasonCode();
				ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>(); 
				if(!InfBatchUtil.empty(reasonCode) && InfBatchProperty.lookup("NOTIFICATION_EMAIL_SUMMARY_NCB_REASON_CODES",reasonCode)){
					List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER");

					//if(!Util.empty(SALE_CHANNEL)&&(!"N".equals(SALE_CHANNEL))){
					List<NotifyEODSalesInfoDataM> notifySaleInfos = new ArrayList<NotifyEODSalesInfoDataM>();
					if(!Util.empty(SALE_CHANNEL)
							&&(!"N".equals(SALE_CHANNEL) || APPLICATION_TEMPLATE_THAIBEV.equals(APPLICATION_TEMPLATE))){
						SALE_CHANNEL = "N".equals(SALE_CHANNEL) ? DEFUALT_DATA_TYPE_ALL : SALE_CHANNEL ; // support ThaiBev no sale (send to fix)
						logger.debug("SALE_CHANNEL : "+SALE_CHANNEL);
						//List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_NOTIFICATION_TYPE_SALE");
						List<NotificationConfig> channelConfigs = getNotificationConfigs(APPLICATION_TEMPLATE,SALE_CHANNEL,NOTIFICATION_SEND_TIME_EOD
								,notificationTypes,null, conn);
						List<NotificationConfig> filterChannelConfigs = new ArrayList<NotificationConfig>();
						if(null!=channelConfigs){
							for(NotificationConfig notificationConfig : channelConfigs){
								if(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY")
										.equals(notificationConfig.getNotificationType())){
									filterChannelConfigs.add(notificationConfig);
								}else if(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER")
										.equals(notificationConfig.getNotificationType())){
									String runMakeEmailBatchFlag = runMakeEmailBatchFlag(APPLICATION_GROUP_ID,LIFE_CYCLE,conn);
									logger.debug("runMakeEmailBatchFlag : "+runMakeEmailBatchFlag);
									if("N".equals(runMakeEmailBatchFlag)){
										filterChannelConfigs.add(notificationConfig);
									}
								}
							}
						}
						if(null!=filterChannelConfigs){
							for(NotificationConfig notificationConfig : filterChannelConfigs){
								logger.debug("sale.NotificationConfig : "+notificationConfig);
								notificationConfig.setApplicationTemplate(APPLICATION_TEMPLATE);
								notificationConfig.setSaleBranchCode(SALES_BRANCH_CODE);
								notificationConfig.setSaleId(SALE_ID);
								notificationConfig.setRecommendBranchCode(RECOMMEND_BRANCH_CODE);
								notificationConfig.setRecommendId(RECOMMEND_ID);
//								String eodNotificationId = findEodNotificationId(notificationConfig);
								String eodNotificationId = findEodRejectNcbNotificationId(APPLICATION_GROUP_ID,notificationConfig);
								logger.debug("channelConfigs.eodNotificationId : "+eodNotificationId);
								if(!Util.empty(eodNotificationId)){
									ArrayList<NotificationEODDataM>  eodNotificationItems = notificationEodData.get(eodNotificationId);
									if(InfBatchUtil.empty(eodNotificationItems)){
										eodNotificationItems = new  ArrayList<NotificationEODDataM>();
										notificationEodData.put(eodNotificationId, eodNotificationItems);
									}
									NotificationEODDataM notificationEod = new NotificationEODDataM();
										notificationEod.setLifeCycle(LIFE_CYCLE);
										notificationEod.setApplicationGroupId(APPLICATION_GROUP_ID);
										notificationEod.setApplicationGroupNo(APPLICATION_GROUP_NO);
										notificationEod.setAppStatus(APP_STATUS);
										notificationEod.setFixEmail(notificationConfig.getFixEmail());
										notificationEod.setJobState(JOB_STATE);
										notificationEod.setRecommend(RECOMMEND_ID);
										notificationEod.setSaleChannel(SALE_CHANNEL);
										notificationEod.setRecommendChannel(RECOMMEND_CHANNEL);
										notificationEod.setSaleId(SALE_ID);
										notificationEod.setSendTime(notificationConfig.getSendTime());
										notificationEod.setSendTo(notificationConfig.getSendTo());
										notificationEod.setNotificationType(notificationConfig.getNotificationType());
										notificationEod.setApplicationTemplate(APPLICATION_TEMPLATE);
										notificationEod.setBranchName(SALES_BRANCH_NAME);
										notificationEod.setBranchCode(SALES_BRANCH_CODE);
										notificationEod.setManagerChannel(notificationConfig.getManagerChannel());
									JobCodeDataM jobCode = new JobCodeDataM();
										jobCode.setJobCode(notificationConfig.getJobCode());
										jobCode.setOptional1(notificationConfig.getOptional1());
										jobCode.setOptional2(notificationConfig.getOptional2());
										jobCode.setOptional3(notificationConfig.getOptional3());
										jobCode.setOptional4(notificationConfig.getOptional4());
										jobCode.setOptional5(notificationConfig.getOptional5());
										jobCode.setPattern(notificationConfig.getPattern());
									notificationEod.addJobCodes(jobCode);
									eodNotificationItems.add(notificationEod);
								}
							}
						}
					}
					//if(!Util.empty(RECOMMEND_CHANNEL)&&!"N".equals(RECOMMEND_CHANNEL)){
					if(!Util.empty(RECOMMEND_CHANNEL)
							&&(!"N".equals(RECOMMEND_CHANNEL) ||  APPLICATION_TEMPLATE_THAIBEV.equals(APPLICATION_TEMPLATE))){
						SALE_CHANNEL = "N".equals(SALE_CHANNEL) ? DEFUALT_DATA_TYPE_ALL : SALE_CHANNEL ; // support ThaiBev no sale (send to fix)
						logger.debug("RECOMMEND_CHANNEL : "+RECOMMEND_CHANNEL);
						List<String> sendTos = new ArrayList<String>();
						sendTos.add(SEND_TO_TYPE_MANAGER);
						sendTos.add(InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX"));
						//List<String> notificationTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EOD_NOTIFICATION_TYPE_RECOMMEND");
						List<NotificationConfig> recommendConfigs = getNotificationConfigs(APPLICATION_TEMPLATE,RECOMMEND_CHANNEL,NOTIFICATION_SEND_TIME_EOD
								,notificationTypes,sendTos,conn);
//						logger.debug("recommendConfigs : "+recommendConfigs);
						if(null!=recommendConfigs){
							for(NotificationConfig notificationConfig : recommendConfigs){
								logger.debug("recommend.NotificationConfig : "+notificationConfig);
								notificationConfig.setApplicationTemplate(APPLICATION_TEMPLATE);
								notificationConfig.setSaleBranchCode(SALES_BRANCH_CODE);
								notificationConfig.setSaleId(SALE_ID);
								notificationConfig.setRecommendBranchCode(RECOMMEND_BRANCH_CODE);
								notificationConfig.setRecommendId(RECOMMEND_ID);
								String eodNotificationId = findEodNotificationId(notificationConfig);
								logger.debug("recommendConfigs.eodNotificationId : "+eodNotificationId);
								if(!Util.empty(eodNotificationId)){
									ArrayList<NotificationEODDataM>  eodNotificationItems = notificationEodData.get(eodNotificationId);
									if(InfBatchUtil.empty(eodNotificationItems)){
										eodNotificationItems = new  ArrayList<NotificationEODDataM>();
										notificationEodData.put(eodNotificationId, eodNotificationItems);
									}
									NotificationEODDataM notificationEod = new NotificationEODDataM();
										notificationEod.setLifeCycle(LIFE_CYCLE);
										notificationEod.setApplicationGroupId(APPLICATION_GROUP_ID);
										notificationEod.setApplicationGroupNo(APPLICATION_GROUP_NO);
										notificationEod.setAppStatus(APP_STATUS);
										notificationEod.setFixEmail(notificationConfig.getFixEmail());
										notificationEod.setJobState(JOB_STATE);
										notificationEod.setRecommend(RECOMMEND_ID);
										notificationEod.setSaleChannel(SALE_CHANNEL);
										notificationEod.setRecommendChannel(RECOMMEND_CHANNEL);
										notificationEod.setSaleId(SALE_ID);
										notificationEod.setSendTime(notificationConfig.getSendTime());
										notificationEod.setSendTo(notificationConfig.getSendTo());
										notificationEod.setNotificationType(notificationConfig.getNotificationType());
										notificationEod.setApplicationTemplate(APPLICATION_TEMPLATE);
										notificationEod.setBranchName(RECOMMEND_BRANCH_NAME);
										notificationEod.setManagerChannel(notificationConfig.getManagerChannel());
									JobCodeDataM jobCode = new JobCodeDataM();
										jobCode.setJobCode(notificationConfig.getJobCode());
										jobCode.setOptional1(notificationConfig.getOptional1());
										jobCode.setOptional2(notificationConfig.getOptional2());
										jobCode.setOptional3(notificationConfig.getOptional3());
										jobCode.setOptional4(notificationConfig.getOptional4());
										jobCode.setOptional5(notificationConfig.getOptional5());
										jobCode.setPattern(notificationConfig.getPattern());
									notificationEod.addJobCodes(jobCode);
									eodNotificationItems.add(notificationEod);
								}
							}
						}
					}
				}else{
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
						infBatchLog.setLifeCycle(LIFE_CYCLE);
						infBatchLog.setInterfaceCode(NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE);
						infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND);	//infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE); 
						infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setLogMessage("Not a Reject NCB");
					infBatchLogs.add(infBatchLog);
				}
				if(!InfBatchUtil.empty(infBatchLogs)){
					insertInfBatchLog(infBatchLogs);
				}
				if(!qrList.contains(APPLICATION_GROUP_NO)){
					qrList.add(APPLICATION_GROUP_NO);
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return notificationEodData;
	}
	private void insertInfBatchLog(ArrayList<InfBatchLogDataM> infBatchLogs)throws InfBatchException{
	   	 Connection conn = getConnection();	
	   	 try {
	   		 InfDAO infDAO = InfFactory.getInfDAO();
	   		 conn.setAutoCommit(false);
	   		 if(!InfBatchUtil.empty(infBatchLogs)){
	   			 for(InfBatchLogDataM infBatchLog : infBatchLogs){
	   				infDAO.insertInfBatchLog(infBatchLog, conn);
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
	private void insertInfBatchLog(HashMap<String,InfBatchLogDataM> hLog)throws InfBatchException{
	   	 Connection conn = getConnection();	
	   	 try {
	   		 InfDAO infDAO = InfFactory.getInfDAO();
	   		 conn.setAutoCommit(false);
	   		 if(!InfBatchUtil.empty(hLog)){
	   			 for(String key : hLog.keySet()){
	   				InfBatchLogDataM infBatchLog = hLog.get(key);
	   				if(!InfBatchUtil.empty(infBatchLog)){
	   					infDAO.insertInfBatchLog(infBatchLog, conn);
	   				}
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
	public ArrayList<VCEmpInfoDataM> findEmployeeInfos(ArrayList<String> empIds)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		logger.debug("empIds : "+empIds);
		ArrayList<VCEmpInfoDataM> empInfos  = new ArrayList<VCEmpInfoDataM>();
		if(Util.empty(empIds))return empInfos;
		try{
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT MBL_PH1,MBL_PH2,");
			sql.append(" EMAIL_ADR, EMP_ID");
			sql.append(" FROM IP_SHR.VC_EMP SALE  WHERE SALE.EMP_ID IN ( ");	 
			String COMMA="";
			for(String empId : empIds){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			ps = conn.prepareStatement(sql.toString());				
			logger.debug("sql : " + sql);		
			int index=1;
			for(String empId : empIds){
				ps.setString(index++, empId);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				VCEmpInfoDataM empInfo = new VCEmpInfoDataM();
				String mobile1 = FormatUtil.trim(rs.getString("MBL_PH1"));
				String mobile2 = FormatUtil.trim(rs.getString("MBL_PH2"));
				String email = FormatUtil.trim(rs.getString("EMAIL_ADR"));
				String EMP_ID = FormatUtil.trim(rs.getString("EMP_ID"));
				logger.debug("mobile1 : "+mobile1);
				logger.debug("mobile2 :"+mobile2);
				logger.debug("email : "+email);
				empInfo.setEmpId(EMP_ID);
				empInfo.setEmail(email);
				empInfo.setMobilePhone(mobile1);
				if(InfBatchUtil.empty(mobile1)){
					empInfo.setMobilePhone(mobile2);					
				}
				empInfos.add(empInfo);
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
		return empInfos;
	}
	@Override
	public ArrayList<VCEmpInfoDataM> selectVCEmpList(ArrayList<String> saleIds) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<VCEmpInfoDataM> empInfos = new ArrayList<VCEmpInfoDataM>();
		try{
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT MBL_PH1,MBL_PH2,");
			sql.append(" EMAIL_ADR, EMP_ID");
			sql.append(" FROM IP_SHR.VC_EMP SALE  WHERE SALE.EMP_ID IN ( ");	 
			String COMMA="";
			for(String saleId : saleIds){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			ps = conn.prepareStatement(sql.toString());				
			logger.debug("sql : " + sql);		
			int index=1;
			for(String saleId : saleIds){
				ps.setString(index++, saleId);
				logger.debug("saleId : " + saleId);	
			}
			rs = ps.executeQuery();
			while(rs.next()){
				VCEmpInfoDataM empInfo = new VCEmpInfoDataM();
				String mobile1 = FormatUtil.trim(rs.getString("MBL_PH1"));
				String mobile2 = FormatUtil.trim(rs.getString("MBL_PH2"));
				String email = FormatUtil.trim(rs.getString("EMAIL_ADR"));
				String EMP_ID = FormatUtil.trim(rs.getString("EMP_ID"));
				logger.debug("mobile1 : "+mobile1);
				logger.debug("mobile2 : "+mobile2);
				logger.debug("email : "+email);
				empInfo.setEmpId(EMP_ID);
				empInfo.setEmail(email);
				empInfo.setMobilePhone(mobile1);
				if(InfBatchUtil.empty(mobile1)){
					empInfo.setMobilePhone(mobile2);					
				}
				empInfos.add(empInfo);
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
		return empInfos;
	}


	@Override
	public boolean isContainsKPLProDuctApprove(String applicationGroupId)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count=0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) AS COUNT_KPL ");
			sql.append(" FROM  ORIG_APPLICATION");
			sql.append(" WHERE APPLICATION_GROUP_ID =?  ");
			sql.append(" AND SUBSTR(BUSINESS_CLASS_ID,1,INSTR(BUSINESS_CLASS_ID,'_')-1) LIKE '"+NOTIFICATION_KPL_PRODUCT+"'");
			sql.append(" AND ORIG_APPLICATION.FINAL_APP_DECISION=?");
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());				 
			ps.setString(1,applicationGroupId); 		
			ps.setString(2,APPLICATION_STATUS_APPROVE); 		
			rs = ps.executeQuery();
			if(rs.next()) {
				count =rs.getInt("COUNT_KPL");
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
		 return count>0;
	}


	@Override
	public boolean isCashDay1Approve(String applicationGroupId)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count=0;
		
		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(1) AS COUNT_CASHDAY ");
			sql.append(" FROM  ORIG_APPLICATION");
			sql.append(" INNER JOIN   ORIG_LOAN ON ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_LOAN.APPLICATION_RECORD_ID ");
			sql.append(" INNER JOIN   ORIG_CASH_TRANSFER  ON ORIG_CASH_TRANSFER.LOAN_ID = ORIG_LOAN.LOAN_ID ");
			sql.append(" WHERE ORIG_APPLICATION.APPLICATION_GROUP_ID =?  ");
			sql.append(" AND  ORIG_CASH_TRANSFER.CASH_TRANSFER_TYPE =?");
			sql.append(" AND ORIG_APPLICATION.FINAL_APP_DECISION=?");
 
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());				 
			ps.setString(1,applicationGroupId); 		
			ps.setString(2,CASH_DAY_1); 	
			ps.setString(3,APPLICATION_STATUS_APPROVE); 
			rs = ps.executeQuery();
			if(rs.next()) {
				count =rs.getInt("COUNT_CASHDAY");
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
		 return count>0;
	}


	@Override
	public ArrayList<DMNotificationDataM> selectNotificationDM(String dmNotificationType,String groupCode) throws InfBatchException{
		ArrayList<DMNotificationDataM> dmNotifications = new ArrayList<DMNotificationDataM>();
		try {
			dmNotifications = selectNotificationDMInfos(dmNotificationType,groupCode,this.selectNotiCCData(groupCode));
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		return dmNotifications;
	}
	public NotiCCDataM selectNotiCCData(String groupCode) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		NotiCCDataM   notiCCDataM = new NotiCCDataM();
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT CC.* FROM MS_NOTI_CC CC WHERE CC.GROUP_CODE = ?");			 
			ps = conn.prepareStatement(sql.toString());				
			logger.debug("sql : " + sql);		
			logger.debug("groupCode : " + groupCode);		
			ps.setString(1, groupCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				notiCCDataM.setCcFixEmail(rs.getString("CC_FIX_EMAIL"));
				notiCCDataM.setCcJobCode(rs.getString("CC_JOB_CODE"));
				notiCCDataM.setCcRoundFrom(rs.getInt("CC_ROUND_FROM"));
				notiCCDataM.setCcRoundTo(rs.getInt("CC_ROUND_TO"));
				notiCCDataM.setCcSendTo(rs.getString("CC_SEND_TO"));
				notiCCDataM.setChannelGroupCode(rs.getString("GROUP_CODE"));
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
		return notiCCDataM;
	}
	public ArrayList<DMNotificationDataM> selectNotificationDMInfos(String dmNotificationType,String groupCode,NotiCCDataM  notiCCDataM) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DMNotificationDataM>   dmNotifications  = new ArrayList<DMNotificationDataM>();
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  DISTINCT DM_DOC.DM_ID, DM_DOC.REF_NO2, C.NOTI_ROUND,TRUNC(C.MAX_CREATE_DATE) AS MAX_CREATE_DATE , DM_DOC.PARAM5 AS BRANCH_NAME, ");
			sql.append(" SUB_DOC_ALL.ALL_SUB_DOC, SUB_DOC_N.NOT_RECEIVE, TRANS.REQUESTED_USER");
			sql.append(" FROM DM_DOC");	 
			sql.append(" INNER JOIN (SELECT COUNT (1) AS NOT_RECEIVE,DM_ID  FROM DM_SUB_DOC  WHERE STATUS ='"+SUB_DOC_STATUS_NOT_IN_WAREHOUS+"' OR  STATUS IS NULL GROUP BY DM_ID ) SUB_DOC_N ON SUB_DOC_N.DM_ID = DM_DOC.DM_ID");
			sql.append(" LEFT JOIN (SELECT COUNT (1) AS ALL_SUB_DOC,DM_ID  FROM DM_SUB_DOC  GROUP BY DM_ID ) SUB_DOC_ALL ON SUB_DOC_ALL.DM_ID = DM_DOC.DM_ID");
			sql.append(" LEFT JOIN (SELECT COUNT (1) AS  NOTI_ROUND ,DM_ID,NOTIFICATION_TYPE,MAX(CREATE_DATE) AS MAX_CREATE_DATE FROM DM_CORRESPOND_LOG  WHERE NOTIFICATION_TYPE ='"+dmNotificationType+"'");
			sql.append(" 			GROUP BY  DM_ID,NOTIFICATION_TYPE) C ON DM_DOC.DM_ID = C.DM_ID ");	 
			sql.append(" LEFT JOIN  (SELECT T.DM_ID,T.REQUESTED_USER FROM DM_TRANSACTION T");
			sql.append(" 			WHERE  T.DM_TRANSACTION_ID = (SELECT MAX(T2.DM_TRANSACTION_ID) FROM DM_TRANSACTION T2 WHERE T2.DM_ID = T.DM_ID)) TRANS");
			sql.append(" ON TRANS.DM_ID = DM_DOC.DM_ID ");
			sql.append(" WHERE  DM_DOC.STATUS=?");	 
			
			ps = conn.prepareStatement(sql.toString());				
			logger.debug("sql : " + sql);		
			logger.debug("DM_DOC_STATUS_NOT_IN_WAREHOUSE : " + DM_DOC_STATUS_NOT_IN_WAREHOUSE);			
			ps.setString(1, DM_DOC_STATUS_NOT_IN_WAREHOUSE);
			rs = ps.executeQuery();
			while(rs.next()) {
				String DM_ID = rs.getString("DM_ID");
				String APPLICATION_GROUP_NO = rs.getString("REF_NO2");
				String REQUESTED_USER = rs.getString("REQUESTED_USER");
				String BRANCH_NAME = rs.getString("BRANCH_NAME");
				int NOTI_ROUND = rs.getInt("NOTI_ROUND");
				int ALL_SUB_DOC = rs.getInt("ALL_SUB_DOC");
				int NOT_RECEIVE = rs.getInt("NOT_RECEIVE");						
				Date MAX_CREATE_DATE = rs.getDate("MAX_CREATE_DATE");	
				
				DMNotificationDataM dmNotification = new DMNotificationDataM();
				dmNotification.setDmNotificationType(dmNotificationType);
				dmNotification.setChannelGroupCode(groupCode);
				dmNotification.setNoticcDataM(notiCCDataM);
				dmNotification.setDmId(DM_ID);
				dmNotification.setBarnchName(BRANCH_NAME);
				dmNotification.setRoundOfNotification(NOTI_ROUND);
				dmNotification.setCorresPondLogCreateDate(MAX_CREATE_DATE);
				dmNotification.setDmRequestUser(REQUESTED_USER);
				
				this.selectNotificationDMApplicationInfos(APPLICATION_GROUP_NO,dmNotification);						
				logger.debug("dmNotificationType>>"+dmNotificationType);
				logger.debug("NOT_RECEIVE>>"+NOT_RECEIVE);
				logger.debug("ALL_SUB_DOC>>"+ALL_SUB_DOC);
				if(WARE_HOUSE_NOTIFICATION_TYPE_NOT_RECEIVE.equals(dmNotificationType) && ALL_SUB_DOC==NOT_RECEIVE){
					dmNotifications.add(dmNotification);
				}else if(WARE_HOUSE_NOTIFICATION_TYPE_INCOMPLETE.equals(dmNotificationType) && (NOT_RECEIVE!=0 && NOT_RECEIVE!=ALL_SUB_DOC)){
					dmNotifications.add(dmNotification);
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return dmNotifications;
	}


	private  void selectNotificationDMApplicationInfos(String refNo2,DMNotificationDataM dmNotificationDataM) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.ORIG_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  DISTINCT APPLY_CHANNEL, APPLICATION_GROUP_ID,JOB_STATE,APPLICATION_GROUP_NO,");
			sql.append(" WEB_SCAN_USER, TRUNC(LAST_DECISION_DATE) AS LAST_DECISION_DATE ");
			sql.append(" FROM ORIG_APPLICATION_GROUP");	
			sql.append(" WHERE  APPLICATION_GROUP_NO =?");	 
			ps = conn.prepareStatement(sql.toString());				
			logger.debug("sql : " + sql);	
			logger.debug("refNo2::" + refNo2);	
			ps.setString(1, refNo2);
			rs = ps.executeQuery();
			if(rs.next()) {
				String APPLY_CHANNEL = rs.getString("APPLY_CHANNEL");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				Date LAST_DECISION_DATE = rs.getDate("LAST_DECISION_DATE");									
				String WEB_SCAN_USER = rs.getString("WEB_SCAN_USER");	
				String VC_EMP_ID=Formatter.getVcEmpId(WEB_SCAN_USER);
				logger.debug("WEB_SCAN_USER>>>"+WEB_SCAN_USER);
				logger.debug("VC_EMP_ID>>>"+VC_EMP_ID);
				String JOB_STATE = rs.getString("JOB_STATE");			
				dmNotificationDataM.setApplicationGroupId(APPLICATION_GROUP_ID);
				dmNotificationDataM.setApplyChanel(APPLY_CHANNEL);
				dmNotificationDataM.setLastDecisionDate(LAST_DECISION_DATE);
				dmNotificationDataM.setUserWebScan(VC_EMP_ID); 		
				dmNotificationDataM.setJobState(JOB_STATE);				
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
	}
	
	private String getCoverPageType(String qr1){
		return (!Util.empty(qr1))?qr1.substring(19,21):"";
	}
	@Override
	public String getDMGeneralParam(String paramCode) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String paramValue = null;		
		try {
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PARAM_VALUE FROM GENERAL_PARAM WHERE PARAM_CODE = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, paramCode);
			rs = ps.executeQuery();			
			if (rs.next()) {
				paramValue = rs.getString("PARAM_VALUE");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return paramValue;
	}


	@Override
	public HashMap<String,ArrayList<JobCodeDataM>> selectDMJobCodes(String scanChannel,String groupCode) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap<String,ArrayList<JobCodeDataM> >  hJobCodes = new HashMap<String,ArrayList<JobCodeDataM> >();
		try {
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT S.NOTIFICATION_TYPE, S.SEND_TIME, D.SEND_TO,");
			sql.append(" D.MAPPING_JOBCODE, D.FIX_EMAIL , M.OPTIONAL1, M.OPTIONAL2,");
		    sql.append(" M.OPTIONAL3, M.OPTIONAL4, M.OPTIONAL5");
		    sql.append(" FROM MS_NOTI_CHANNEL C,");
		    sql.append(" MS_NOTI_SENDING S,");
		    sql.append(" MS_NOTI_SENDING_DETAIL D");
		    sql.append(" LEFT JOIN MS_NOTI_CHANNEL_MAPPING M ON D.MAPPING_JOBCODE = M.JOB_CODE");
		    sql.append(" WHERE ( C.CHANNEL_ID = ?");
		    sql.append(" OR  C.CHANNEL_ID = 'ALL')");
		    sql.append(" AND C.CHANNEL_MAP_ID = S.CHANNEL_MAP_ID");
			sql.append(" AND S.SENDING_ID = D.SENDING_ID");
			sql.append(" AND S.SEND_TIME = 'EOD'");
			sql.append(" AND C.GROUP_CODE = ?");	
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("scanChannel=" + scanChannel);
			logger.debug("groupCode=" + groupCode);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, scanChannel);
			ps.setString(2, groupCode);
			rs = ps.executeQuery();			
			while (rs.next()) {
				String SEND_TO = rs.getString("SEND_TO");
				ArrayList<JobCodeDataM> jobCodes = hJobCodes.get(SEND_TO);
				if(InfBatchUtil.empty(jobCodes)){
					jobCodes = new ArrayList<JobCodeDataM>();
					hJobCodes.put(SEND_TO, jobCodes);
				}
				JobCodeDataM jobCode = new JobCodeDataM();
				jobCode.setNotificationType(rs.getString("NOTIFICATION_TYPE"));
				jobCode.setSendTo(SEND_TO);
				jobCode.setFixEmail(rs.getString("FIX_EMAIL"));
				jobCode.setJobCode(rs.getString("MAPPING_JOBCODE"));
				jobCode.setOptional1(rs.getString("OPTIONAL1"));
				jobCode.setOptional2(rs.getString("OPTIONAL2"));
				jobCode.setOptional3(rs.getString("OPTIONAL3"));
				jobCode.setOptional4(rs.getString("OPTIONAL4"));
				jobCode.setOptional5(rs.getString("OPTIONAL5"));
				jobCodes.add(jobCode);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return hJobCodes;
	}

	@Override
	public Date getApplicationDate() throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Date applicationDate = null;
		try {
			conn = getConnection(InfBatchServiceLocator.ORIG_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TRUNC(APP_DATE) AS APP_DATE FROM APPLICATION_DATE ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();			
			if (rs.next()) {
				applicationDate = rs.getDate("APP_DATE");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return applicationDate;
	}

	@Override
	public void insertDMCorrespondLog(DMNotificationDataM dmNotification,EmailRequest emailRequest,Connection conn)throws InfBatchException {
		try {
			 this.insertTableCorrespondLog(dmNotification,emailRequest, conn);		
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}

	public void insertTableCorrespondLog(DMNotificationDataM dmNotification,EmailRequest emailRequest,Connection conn)throws InfBatchException {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO DM_CORRESPOND_LOG ");
			sql.append("(CORRESPOND_LOG_ID, DM_ID, SEND_TO, SEND_BY, SEND_DATE,");
			sql.append(" SEND_STATUS, CREATE_BY, CREATE_DATE, CONTACT_TYPE,TEMPLATE_NAME, ");
			sql.append(" CC_TO, MESSAGE, SUBJECT, NOTIFICATION_TYPE) ");
			
			sql.append(" SELECT CORRESPOND_LOG_ID_PK.NEXTVAL,?,?,?,?,");
			sql.append(" ?, ?, SYSDATE, ?, ?,");
			sql.append(" ?, ?, ?, ?");
			sql.append(" FROM DUAL");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int intdex = 1;
			ps.setString(intdex++,dmNotification.getDmId());
			ps.setString(intdex++,emailRequest.getEmailToString());
			ps.setString(intdex++,emailRequest.getFrom());
			ps.setTimestamp(intdex++,emailRequest.getSentDate());
			
			ps.setString(intdex++,"");
			ps.setString(intdex++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(intdex++,TemplateBuilderConstant.TemplateType.EMAIL);
			ps.setString(intdex++,emailRequest.getTemplateName());
			
			ps.setString(intdex++,emailRequest.getCCemailToString());
			Clob clobContent = conn.createClob();
			clobContent.setString(1, emailRequest.getContent());
			ps.setClob(intdex++,clobContent);
			Clob clobSubject = conn.createClob();
			clobSubject.setString(1, emailRequest.getSubject());
			ps.setClob(intdex++,clobSubject);
			ps.setString(intdex++,dmNotification.getDmNotificationType());		
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
//				if (ps != null){
//					try{
//						ps.close();
//					} catch (Exception e) {
//						logger.fatal("ERROR",e);
//						throw new InfBatchException(e.getLocalizedMessage());
//					}
//				}
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}


	@Override
	public HashMap<String, ArrayList<JobCodeDataM>> selectJobCodeChannelMapping(ArrayList<String> webScanUsers) throws InfBatchException {
		HashMap<String, ArrayList<JobCodeDataM>>  channelJobCodes = new HashMap<String, ArrayList<JobCodeDataM>>();
		 try {
			 HashMap<String, HashMap<String, String>> hCondition = this.selectJobCodeConditions(webScanUsers);
			 if(!InfBatchUtil.empty(hCondition)){
				 ArrayList<String> userKeys = new ArrayList<String>(hCondition.keySet());
				 for(String keyId : userKeys){
					 ArrayList<JobCodeDataM> jobCodeList = selectChannelPammingData(hCondition.get(keyId));
					 if(!InfBatchUtil.empty(jobCodeList)){
						 channelJobCodes.put(keyId, jobCodeList);
					 }
				 }
				 
			 }
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		return channelJobCodes;
	}
	public ArrayList<JobCodeDataM>  selectChannelPammingData(HashMap<String, String> hCondition) throws InfBatchException {		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<JobCodeDataM> jobcodes = new ArrayList<JobCodeDataM>();

		try {
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT * ");  
			sql.append("  FROM MS_NOTI_CHANNEL_MAPPING M ");  
			sql.append("  WHERE (M.DIVISION =? AND M.NETWORK = ?)  ");  
			sql.append("  OR (M.DIVISION = ? AND M.DEPARTMENT = ?)  ");  
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index =1;
			ps.setString(index++, hCondition.get(KEY_DIVISION));
			ps.setString(index++, hCondition.get(KEY_NETWORK));
			ps.setString(index++, hCondition.get(KEY_DIVISION));
			ps.setString(index++, hCondition.get(KEY_DEPARTMENT));
			rs = ps.executeQuery();			
			while (rs.next()) {
				String JOB_CODE = rs.getString("JOB_CODE");
				String OPTIONAL1 =rs.getString("OPTIONAL1");
				String OPTIONAL2 =rs.getString("OPTIONAL2");
				String OPTIONAL3 =rs.getString("OPTIONAL3");
				String OPTIONAL4 =rs.getString("OPTIONAL4");
				String OPTIONAL5 =rs.getString("OPTIONAL5");
				String PATTERN =rs.getString("PATTERN");
				JobCodeDataM channelJObcCode = new JobCodeDataM();
				channelJObcCode.setJobCode(JOB_CODE);
				channelJObcCode.setOptional1(OPTIONAL1);
				channelJObcCode.setOptional2(OPTIONAL2);
				channelJObcCode.setOptional3(OPTIONAL3);
				channelJObcCode.setOptional4(OPTIONAL4);
				channelJObcCode.setOptional5(OPTIONAL5);
				channelJObcCode.setPattern(PATTERN);
				jobcodes.add(channelJObcCode);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return jobcodes;
	}
	public HashMap<String, HashMap<String, String>> selectJobCodeConditions(ArrayList<String> requestUsers) throws InfBatchException {		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap<String, HashMap<String, String>>   ccCondition = new HashMap<String, HashMap<String, String>>();	
		try {
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT EMP.EMP_ID,EMP.BSN_LINE_DEPT_ID AS DIVISION ,  ");
			sql.append(" EMP.NTW_CNTR_CD AS  NETWORK,  ");
			sql.append(" EMP.DEPT_CNTR_CD AS DEPARTMENT ");
			sql.append(" FROM IP_SHR.VC_EMP EMP ");
			sql.append(" WHERE EMP.EMP_ID IN (");
			String COMMA="";
			for(String requestUser : requestUsers){
				sql.append(COMMA+"'"+requestUser+"'");
			}
			sql.append(")");

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();			
			while (rs.next()) {
				 String EMP_ID = FormatUtil.trim(rs.getString("EMP_ID"));
				 String DIVISION_VALUE= FormatUtil.trim(rs.getString("DIVISION"));
				 String NETWORK_VALUE = FormatUtil.trim(rs.getString("NETWORK"));
				 String DEPARTMENT_VALUE = FormatUtil.trim(rs.getString("DEPARTMENT"));
				 HashMap<String, String> hData = new HashMap<String, String>();
				 hData.put(KEY_DIVISION, DIVISION_VALUE);
				 hData.put(KEY_NETWORK, NETWORK_VALUE);
				 hData.put(KEY_DEPARTMENT, DEPARTMENT_VALUE);
				 ccCondition.put(EMP_ID, hData);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return ccCondition;
	}


	@Override
	public ArrayList<DMNotificationDataM> selectReturnNotificationDM(String dmNotificationType,String groupCode) throws InfBatchException {
		ArrayList<DMNotificationDataM> dmNotifications = new ArrayList<DMNotificationDataM>();
		try {
			dmNotifications = selectReturnNotificationDMInfos(dmNotificationType,groupCode,this.selectNotiCCData(groupCode));
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		return dmNotifications;
	}
	
	public ArrayList<DMNotificationDataM> selectReturnNotificationDMInfos(String dmNotificationType,String groupCode,NotiCCDataM  notiCCDataM) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<DMNotificationDataM>   dmNotifications  = new ArrayList<DMNotificationDataM>();
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT  DM_DOC.DM_ID,C.NOTI_ROUND, DM_DOC.REF_NO2, ");
			sql.append(" TRANS.REQUESTED_USER, TRUNC(TRANS.DUE_DATE) AS DUE_DATE");
			sql.append(" FROM DM_DOC");	 
			sql.append(" LEFT JOIN (SELECT COUNT (1) AS  NOTI_ROUND ,DM_ID,NOTIFICATION_TYPE,MAX(CREATE_DATE) AS MAX_CREATE_DATE FROM DM_CORRESPOND_LOG  WHERE NOTIFICATION_TYPE ='"+dmNotificationType+"'");
			sql.append(" 			GROUP BY  DM_ID,NOTIFICATION_TYPE) C ON DM_DOC.DM_ID = C.DM_ID ");	 
			sql.append(" LEFT JOIN  (SELECT T.DM_ID,T.REQUESTED_USER,T.DUE_DATE,T.STATUS  FROM DM_TRANSACTION T");
			sql.append(" 			WHERE  T.CREATE_DATE = (SELECT MAX(T2.CREATE_DATE) FROM DM_TRANSACTION T2 WHERE T2.DM_ID = T.DM_ID)) TRANS");
			sql.append(" ON TRANS.DM_ID = DM_DOC.DM_ID AND TRANS.STATUS ='"+WARE_HOUSE_TRANSACTION_STATUS+"'");
			sql.append(" WHERE  DM_DOC.STATUS=?");	 
			ps = conn.prepareStatement(sql.toString());				
			logger.debug("sql : " + sql);			
			logger.debug("STATUS" + DM_DOC_STATUS_BORROWED);		
			ps.setString(1, DM_DOC_STATUS_BORROWED);
			rs = ps.executeQuery();
			while(rs.next()) {
				String DM_ID = rs.getString("DM_ID");
				String APPLICATION_GROUP_NO = rs.getString("REF_NO2");
				int NOTI_ROUND = rs.getInt("NOTI_ROUND");					
				Date DUE_DATE = rs.getDate("DUE_DATE");				
				String REQUESTED_USER = rs.getString("REQUESTED_USER");				
	 
				DMNotificationDataM dmNotification =  new DMNotificationDataM();
				dmNotification.setDmNotificationType(dmNotificationType);
				dmNotification.setChannelGroupCode(groupCode);
				dmNotification.setNoticcDataM(notiCCDataM);
				dmNotification.setDmId(DM_ID);
				dmNotification.setRoundOfNotification(NOTI_ROUND);
				dmNotification.setActionDueDate(DUE_DATE);
				dmNotification.setDmRequestUser(REQUESTED_USER);
				this.selectNotificationDMApplicationInfos(APPLICATION_GROUP_NO,dmNotification);
				dmNotifications.add(dmNotification);				 
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
		return dmNotifications;
	}


	@Override
	public EmailTemplateDataM getDMEmailTemplateInfo(String templateId) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EmailTemplateDataM emailtemplate = new EmailTemplateDataM();
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TEMPLATE_ID, TEMPLATE_NAME, CONTENT,SUBJECT,");
			sql.append(" TEMPLATE_TYPE");
			sql.append(" FROM MS_EMAIL_TEMPLATE_MASTER EMAIL ");
			sql.append(" WHERE EMAIL.TEMPLATE_ID =?");
			 
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index=1;
			ps.setString(index++,templateId); 
			rs = ps.executeQuery();			
			if(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				String TEMPLATE_NAME = rs.getString("TEMPLATE_NAME");
				String CONTENT = rs.getString("CONTENT");
				String SUBJECT = rs.getString("SUBJECT");
				String TEMPLATE_TYPE = rs.getString("TEMPLATE_TYPE");
				emailtemplate.setTemplateId(TEMPLATE_ID);
				emailtemplate.setTemplateName(TEMPLATE_NAME);
				emailtemplate.setContent(CONTENT);
				emailtemplate.setSubject(SUBJECT);
				emailtemplate.setTemplateType(TEMPLATE_TYPE);
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
		 return emailtemplate;
	}


	@Override
	public boolean isContainsSanctioListRejectCodeCondition (String applicationGroup, String[] rejectCodes) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count =0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT (1) AS COUNT_REJECT_CODE ");
			sql.append(" FROM ORIG_REASON R ");
			sql.append(" INNER JOIN ORIG_APPLICATION  A ON A.APPLICATION_RECORD_ID = R.APPLICATION_RECORD_ID ");
			sql.append(" WHERE R.REASON_TYPE =? AND A.APPLICATION_GROUP_ID = ?");
			sql.append(" AND R.REASON_CODE IN (");
			String COMMA="";
			for(String reasonCode :rejectCodes){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(")");
			logger.debug("sql : " + sql);
			logger.debug("applicationGroup>>" + applicationGroup);
			logger.debug("REASON_TYPE_REJECT>>" + REASON_TYPE_REJECT);
			ps = conn.prepareStatement(sql.toString());	
			int index=1;
			ps.setString(index++,REASON_TYPE_REJECT);
			ps.setString(index++,applicationGroup);
			for(String reasonCode :rejectCodes){
				ps.setString(index++,reasonCode);
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("COUNT_REJECT_CODE");
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
		 return count>0;
	}

	@Override
	public void insertTableContactLog(OrigContactLogDataM contactLog)throws InfBatchException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_CONTACT_LOG ");
			sql.append("(CONTACT_LOG_ID, SEND_TO, APPLICATION_GROUP_ID, SEND_BY, SEND_STATUS,");
			sql.append(" MESSAGE, SUBJECT, CONTACT_TYPE, TEMPLATE_NAME,CC_TO, ");
			sql.append(" CREATE_BY, SEND_DATE ,CREATE_DATE) ");
			
			sql.append(" SELECT ORIG_CONTACT_LOG_PK.NEXTVAL,?,?,?,?,");
			sql.append(" ?, ?, ?, ?, ?,");
			sql.append(" ?, SYSDATE, SYSDATE");
			sql.append(" FROM DUAL");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int intdex = 1;
			ps.setString(intdex++,contactLog.getSendTo());
			ps.setString(intdex++,contactLog.getApplicationGroupId());
			ps.setString(intdex++,contactLog.getSendBy());
			ps.setString(intdex++,contactLog.getSendStatus());

			Clob clobContent = conn.createClob();
			clobContent.setString(1, contactLog.getMessage());
			ps.setClob(intdex++,clobContent);
			Clob clobSubject = conn.createClob();
			clobSubject.setString(1, contactLog.getSubject());
			ps.setClob(intdex++,clobSubject);
			ps.setString(intdex++,contactLog.getContactType());				
			ps.setString(intdex++,contactLog.getTemplateName());
			ps.setString(intdex++,contactLog.getCcTo());
			
			ps.setString(intdex++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			
			ps.executeUpdate();
			
		} catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}


	@Override
	public ArrayList<CardNotificationDataM> selectCardNotification() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CardNotificationDataM> cardNotifications = new ArrayList<>();
		String CARD_NO_MIN_REMAINING = InfBatchProperty.getGeneralParam("CARD_NO_MIN_REMAINING");
		logger.debug("CARD_NO_MIN_REMAINING : "+CARD_NO_MIN_REMAINING);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT CARD_PARAM.CARD_PARAM_ID, RUNNING_PARAM.VALUE_FROM, RUNNING_PARAM.VALUE1, RUNNING_PARAM.VALUE_TO, ");
			sql.append(" RUNNING_PARAM.VALUE_TO-(RUNNING_PARAM.VALUE_FROM+RUNNING_PARAM.VALUE1) RESULT, CARD_PARAM.CARD_TYPE_ID,CARD_TYPE.CARD_GROUP, ");
			sql.append(" CARD_TYPE.CARD_LEVEL,CARD_TYPE_LIST.DISPLAY_NAME CARD_TYPE,CARD_LEVEL_LIST.DISPLAY_NAME CARD_LEVEL ");
			sql.append(" FROM CARD_PARAM ");
			sql.append(" LEFT JOIN RUNNING_PARAM ON RUNNING_PARAM.PARAM_ID = CARD_PARAM.VALUE1 ");
			sql.append(" LEFT JOIN CARD_TYPE ON CARD_TYPE.CARD_TYPE_ID = CARD_PARAM.CARD_TYPE_ID ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER CARD_TYPE_LIST ON (CARD_TYPE_LIST.FIELD_ID = ? AND CARD_TYPE_LIST.CHOICE_NO = CARD_TYPE.CARD_GROUP) ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER CARD_LEVEL_LIST ON (CARD_LEVEL_LIST.FIELD_ID = ? AND CARD_LEVEL_LIST.CHOICE_NO = CARD_TYPE.CARD_LEVEL) ");
			sql.append(" WHERE CARD_PARAM.PARAMCD = ? ");
			sql.append(" AND RUNNING_PARAM.VALUE_TO-(RUNNING_PARAM.VALUE_FROM+RUNNING_PARAM.VALUE1) <= ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, FIELD_ID_CARD_TYPE);
			ps.setString(parameter++, FIELD_ID_CARD_LEVEL);
			rs = ps.executeQuery();
			while(rs.next()){
				CardNotificationDataM cardNotification = new CardNotificationDataM();
				cardNotification.setCardParamId(rs.getString("CARD_PARAM_ID"));
				cardNotification.setValueFrom(rs.getString("VALUE_FROM"));
				cardNotification.setValue1(rs.getString("VALUE1"));
				cardNotification.setValueTo(rs.getString("VALUE_TO"));
				cardNotification.setResult(rs.getString("RESULT"));
				cardNotification.setCardTypeDesc(rs.getString("CARD_TYPE"));
				cardNotification.setCardLevelDesc(rs.getString("CARD_LEVEL"));
				cardNotifications.add(cardNotification);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
				
		return cardNotifications;
	}
	@Override
	public ArrayList<RecipientInfoDataM> loadRecipientSmsNextDay(
			NotificationInfoDataM notificationInfo) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RecipientInfoDataM> recipientInfos = new ArrayList<RecipientInfoDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT P.MOBILE_NO, P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" ,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			sql.append(" ,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, CTP.CARD_CODE");
			sql.append(" FROM ORIG_PERSONAL_INFO P");
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP  G ON  G.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION  R ON  R.PERSONAL_ID = P.PERSONAL_ID AND R.RELATION_LEVEL='A'");
			sql.append(" INNER JOIN ORIG_APPLICATION  A ON  R.REF_ID = A.APPLICATION_RECORD_ID");
			sql.append(" INNER JOIN ORIG_LOAN  L ON  L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID");
			sql.append(" LEFT JOIN ORIG_CARD  C ON L.LOAN_ID = C.LOAN_ID");
			sql.append(" LEFT JOIN CARD_TYPE  CTP ON CTP.CARD_TYPE_ID = C.CARD_TYPE  AND CTP.CARD_LEVEL = C.CARD_LEVEL");
			sql.append(" LEFT JOIN ORIG_CASH_TRANSFER  CT ON  CT.LOAN_ID = L.LOAN_ID AND CT.CASH_TRANSFER_TYPE IS NOT NULL ");
			sql.append(" WHERE ");
			sql.append("  P.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			sql.append("  AND P.APPLICATION_GROUP_ID=?");
			sql.append("  AND A.LIFE_CYCLE=?");
			sql.append("  GROUP BY ");
			sql.append(" 	P.MOBILE_NO, P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" 	,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			sql.append(" 	,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, CTP.CARD_CODE");
			
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+notificationInfo.getApplicationGroupId());
			logger.debug("maxLifeCycle : "+notificationInfo.getMaxLifeCycle());
			
			ps = conn.prepareStatement(sql.toString());				 			
			ps.setString(1,notificationInfo.getApplicationGroupId()); 			
			ps.setInt(2,notificationInfo.getMaxLifeCycle()); 			
			rs = ps.executeQuery();
			while(rs.next()) {			
				RecipientInfoDataM  applicantInfo = new  RecipientInfoDataM();
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String NATIONALITY = rs.getString("NATIONALITY");
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String CASH_TRANSFER_TYPE = rs.getString("CASH_TRANSFER_TYPE");
				String CARD_CODE = rs.getString("CARD_CODE");
 				
				applicantInfo.setEmail(EMAIL_PRIMARY);
				applicantInfo.setPhoneNo(MOBILE_NO);
				applicantInfo.setPersonalType(PERSONAL_TYPE);
				applicantInfo.setPolicyProgramId(PROLICY_PROGRAM_ID);
				applicantInfo.setFinalDecision(FINAL_APP_DECISION);
				applicantInfo.setBusinessClassId(BUSINESS_CLASS_ID);
				applicantInfo.setNationality(NATIONALITY);
				applicantInfo.setApplicationRecordId(APPLICATION_RECORD_ID);
				applicantInfo.setApplicationGroupId(APPLICATION_GROUP_ID);
				applicantInfo.setPersonalId(PERSONAL_ID);
				applicantInfo.setApplicationStatus(notificationInfo.getApplicationStatus());
				applicantInfo.setMaxLifeCycle(notificationInfo.getMaxLifeCycle());
				applicantInfo.setCashTranferType(CASH_TRANSFER_TYPE);
				applicantInfo.setCardCode(CARD_CODE);
				recipientInfos.add(applicantInfo);
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
		 return recipientInfos;
	}
	@Override
	public ArrayList<RecipientInfoDataM> loadRecipient(NotificationInfoDataM notificationInfo) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RecipientInfoDataM> recipientInfos= new ArrayList<RecipientInfoDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT P.MOBILE_NO, P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" ,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			//sql.append(" ,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, CTP.CARD_CODE");
			sql.append(" ,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, C.CARD_TYPE,G.SOURCE");
			sql.append(" FROM ORIG_PERSONAL_INFO P");
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP  G ON  G.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION  R ON  R.PERSONAL_ID = P.PERSONAL_ID AND R.RELATION_LEVEL='A'");
			sql.append(" INNER JOIN ORIG_APPLICATION  A ON  R.REF_ID = A.APPLICATION_RECORD_ID");
			sql.append(" INNER JOIN ORIG_LOAN  L ON  L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID");
			sql.append(" LEFT JOIN ORIG_CARD  C ON L.LOAN_ID = C.LOAN_ID");
			//sql.append(" LEFT JOIN CARD_TYPE  CTP ON CTP.CARD_TYPE_ID = C.CARD_TYPE  AND CTP.CARD_LEVEL = C.CARD_LEVEL");
			sql.append(" LEFT JOIN ORIG_CASH_TRANSFER  CT ON  CT.LOAN_ID = L.LOAN_ID AND CT.CASH_TRANSFER_TYPE IS NOT NULL ");
			sql.append(" WHERE ");
			sql.append("  P.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			sql.append("  AND P.APPLICATION_GROUP_ID=?");
			sql.append("  AND A.LIFE_CYCLE=?");
			sql.append("  GROUP BY ");
			sql.append(" 	P.MOBILE_NO, P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" 	,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			//sql.append(" 	,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, CTP.CARD_CODE");
			sql.append(" 	,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, C.CARD_TYPE,G.SOURCE");
			
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+notificationInfo.getApplicationGroupId());
			logger.debug("maxLifeCycle : "+notificationInfo.getMaxLifeCycle());
			
			ps = conn.prepareStatement(sql.toString());				 			
			ps.setString(1,notificationInfo.getApplicationGroupId()); 			
			ps.setInt(2,notificationInfo.getMaxLifeCycle()); 			
			rs = ps.executeQuery();
			while(rs.next()) {			
				RecipientInfoDataM  applicantInfo = new  RecipientInfoDataM();
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String NATIONALITY = rs.getString("NATIONALITY");
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String CASH_TRANSFER_TYPE = rs.getString("CASH_TRANSFER_TYPE");
				//String CARD_CODE = rs.getString("CARD_CODE");
				String CARD_TYPE = rs.getString("CARD_TYPE");
				String CARD_CODE = CardInfoUtil.getFullCardDetail(CARD_TYPE, "CARD_CODE");
				String SOURCE= rs.getString("SOURCE");

				applicantInfo.setEmail(EMAIL_PRIMARY);
				applicantInfo.setPhoneNo(MOBILE_NO);
				applicantInfo.setPersonalType(PERSONAL_TYPE);
				applicantInfo.setPolicyProgramId(PROLICY_PROGRAM_ID);
				applicantInfo.setFinalDecision(FINAL_APP_DECISION);
				applicantInfo.setBusinessClassId(BUSINESS_CLASS_ID);
				applicantInfo.setNationality(NATIONALITY);
				applicantInfo.setApplicationRecordId(APPLICATION_RECORD_ID);
				applicantInfo.setApplicationGroupId(APPLICATION_GROUP_ID);
				applicantInfo.setPersonalId(PERSONAL_ID);
				applicantInfo.setApplicationStatus(notificationInfo.getApplicationStatus());
				applicantInfo.setMaxLifeCycle(notificationInfo.getMaxLifeCycle());
				applicantInfo.setCashTranferType(CASH_TRANSFER_TYPE);
				applicantInfo.setCardCode(CARD_CODE);
				applicantInfo.setSourse(SOURCE);
				recipientInfos.add(applicantInfo);
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
		 return recipientInfos;
	}
	@Override
	public ArrayList<RecipientInfoDataM> loadRecipientDV(NotificationInfoDataM notificationInfo) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RecipientInfoDataM> recipientInfos= new ArrayList<RecipientInfoDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT P.MOBILE_NO, P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" ,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			//sql.append(" ,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, CTP.CARD_CODE");
			sql.append(" ,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, C.CARD_TYPE");
			sql.append(" FROM ORIG_PERSONAL_INFO P");
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP  G ON  G.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION  R ON  R.PERSONAL_ID = P.PERSONAL_ID ");
			sql.append(" INNER JOIN ORIG_APPLICATION  A ON  R.REF_ID = A.APPLICATION_RECORD_ID");
			sql.append(" INNER JOIN ORIG_LOAN  L ON  L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID");
			sql.append(" LEFT JOIN ORIG_CARD  C ON L.LOAN_ID = C.LOAN_ID");
			//sql.append(" LEFT JOIN CARD_TYPE  CTP ON CTP.CARD_TYPE_ID = C.CARD_TYPE  AND CTP.CARD_LEVEL = C.CARD_LEVEL");
			sql.append(" LEFT JOIN ORIG_CASH_TRANSFER  CT ON  CT.LOAN_ID = L.LOAN_ID AND CT.CASH_TRANSFER_TYPE IS NOT NULL ");
			sql.append(" WHERE ");
			//sql.append("  P.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			sql.append("  P.APPLICATION_GROUP_ID=?");
			sql.append("  AND A.LIFE_CYCLE=?");
			sql.append("  AND EXISTS (SELECT 1 FROM XRULES_PHONE_VERIFICATION ");
			sql.append("  JOIN XRULES_VERIFICATION_RESULT ON XRULES_PHONE_VERIFICATION.VER_RESULT_ID=XRULES_VERIFICATION_RESULT.VER_RESULT_ID ");
			sql.append("  AND XRULES_VERIFICATION_RESULT.REF_ID=P.PERSONAL_ID AND VER_LEVEL='P' ");
			sql.append("  ) ");
			sql.append("  GROUP BY ");
			sql.append(" 	P.MOBILE_NO, P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" 	,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			//sql.append(" 	,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, CTP.CARD_CODE");
			sql.append(" 	,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, CT.CASH_TRANSFER_TYPE, C.CARD_TYPE");
			
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+notificationInfo.getApplicationGroupId());
			logger.debug("maxLifeCycle : "+notificationInfo.getMaxLifeCycle());
			
			ps = conn.prepareStatement(sql.toString());				 			
			ps.setString(1,notificationInfo.getApplicationGroupId()); 			
			ps.setInt(2,notificationInfo.getMaxLifeCycle()); 			
			rs = ps.executeQuery();
			while(rs.next()) {			
				RecipientInfoDataM  applicantInfo = new  RecipientInfoDataM();
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String NATIONALITY = rs.getString("NATIONALITY");
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String CASH_TRANSFER_TYPE = rs.getString("CASH_TRANSFER_TYPE");
				//String CARD_CODE = rs.getString("CARD_CODE");
				String CARD_TYPE = rs.getString("CARD_TYPE");
				String CARD_CODE = CardInfoUtil.getFullCardDetail(CARD_TYPE, "CARD_CODE");

				applicantInfo.setEmail(EMAIL_PRIMARY);
				applicantInfo.setPhoneNo(MOBILE_NO);
				applicantInfo.setPersonalType(PERSONAL_TYPE);
				applicantInfo.setPolicyProgramId(PROLICY_PROGRAM_ID);
				applicantInfo.setFinalDecision(FINAL_APP_DECISION);
				applicantInfo.setBusinessClassId(BUSINESS_CLASS_ID);
				applicantInfo.setNationality(NATIONALITY);
				applicantInfo.setApplicationRecordId(APPLICATION_RECORD_ID);
				applicantInfo.setApplicationGroupId(APPLICATION_GROUP_ID);
				applicantInfo.setPersonalId(PERSONAL_ID);
				applicantInfo.setApplicationStatus(notificationInfo.getApplicationStatus());
				applicantInfo.setMaxLifeCycle(notificationInfo.getMaxLifeCycle());
				applicantInfo.setCashTranferType(CASH_TRANSFER_TYPE);
				applicantInfo.setCardCode(CARD_CODE);
				recipientInfos.add(applicantInfo);
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
		 return recipientInfos;
	}
	@Override
	public ArrayList<RecipientInfoDataM> loadRecipientCash1Hour(NotificationInfoDataM notificationInfo) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RecipientInfoDataM> recipientInfos= new ArrayList<RecipientInfoDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT P.MOBILE_NO, P.EMAIL_PRIMARY, P.PERSONAL_TYPE, A.PROLICY_PROGRAM_ID");
			sql.append(" ,A.FINAL_APP_DECISION ,A.BUSINESS_CLASS_ID,P.NATIONALITY , A.APPLICATION_RECORD_ID");
			sql.append(" ,G.APPLICATION_GROUP_ID , P.PERSONAL_ID, C.CASH_TRANSFER_TYPE");
			sql.append(" FROM ORIG_PERSONAL_INFO P");
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP  G ON  G.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION  R ON  R.PERSONAL_ID = P.PERSONAL_ID AND R.RELATION_LEVEL='A'");
			sql.append(" INNER JOIN ORIG_APPLICATION  A ON  R.REF_ID = A.APPLICATION_RECORD_ID");
			sql.append(" INNER JOIN ORIG_LOAN  L ON  L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID");
			sql.append(" INNER JOIN ORIG_CASH_TRANSFER  C  ON  C.LOAN_ID = L.LOAN_ID");
			sql.append(" WHERE ");
			sql.append("  P.PERSONAL_TYPE IN ('"+PERSONAL_TYPE_APPLICANT+"','"+PERSONAL_TYPE_SUPPLEMENTARY+"')");
			sql.append("  AND P.APPLICATION_GROUP_ID=?");
			sql.append("  AND A.LIFE_CYCLE=?");
			sql.append("  AND C.CASH_TRANSFER_TYPE=?");
			sql.append("  AND SUBSTR(A.BUSINESS_CLASS_ID,1,INSTR(BUSINESS_CLASS_ID,'_')-1) =?");
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+notificationInfo.getApplicationGroupId());
			logger.debug("maxLifeCycle : "+notificationInfo.getMaxLifeCycle());
			
			ps = conn.prepareStatement(sql.toString());				 			
			ps.setString(1,notificationInfo.getApplicationGroupId()); 			
			ps.setInt(2,notificationInfo.getMaxLifeCycle()); 			
			ps.setString(3,CASH_1_HOUR); 			
			ps.setString(4,NOTIFICATION_KEC_PRODUCT); 	
			
			rs = ps.executeQuery();
			while(rs.next()) {			
				RecipientInfoDataM  applicantInfo = new  RecipientInfoDataM();
				String MOBILE_NO = rs.getString("MOBILE_NO");
				String EMAIL_PRIMARY = rs.getString("EMAIL_PRIMARY");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PROLICY_PROGRAM_ID = rs.getString("PROLICY_PROGRAM_ID");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");
				String NATIONALITY = rs.getString("NATIONALITY");
				String APPLICATION_RECORD_ID = rs.getString("APPLICATION_RECORD_ID");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String CASH_TRANSFER_TYPE = rs.getString("CASH_TRANSFER_TYPE");
				
				applicantInfo.setEmail(EMAIL_PRIMARY);
				applicantInfo.setPhoneNo(MOBILE_NO);
				applicantInfo.setPersonalType(PERSONAL_TYPE);
				applicantInfo.setPolicyProgramId(PROLICY_PROGRAM_ID);
				applicantInfo.setFinalDecision(FINAL_APP_DECISION);
				applicantInfo.setBusinessClassId(BUSINESS_CLASS_ID);
				applicantInfo.setNationality(NATIONALITY);
				applicantInfo.setApplicationRecordId(APPLICATION_RECORD_ID);
				applicantInfo.setApplicationGroupId(APPLICATION_GROUP_ID);
				applicantInfo.setPersonalId(PERSONAL_ID);
				applicantInfo.setApplicationStatus(notificationInfo.getApplicationStatus());
				applicantInfo.setMaxLifeCycle(notificationInfo.getMaxLifeCycle());
				applicantInfo.setCashTranferType(CASH_TRANSFER_TYPE);
				recipientInfos.add(applicantInfo);
				
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
		return recipientInfos;
	}
	@Override
	public ArrayList<EmailRejectNCBPersonalInfoDataM> findPersonalRejectNcb(String applicationGroupId)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection conn =null;
		ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs = new ArrayList<EmailRejectNCBPersonalInfoDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("    SELECT DISTINCT A.APPLICATION_NO, A.FINAL_APP_DECISION ");
			sql.append("	   , PS.PERSONAL_ID, PS.PERSONAL_TYPE	");
			sql.append("	   , PKA_REJECT_LETTER.GET_PERSONAL_MIN_RANK_REASON(AG.APPLICATION_GROUP_ID,PS.PERSONAL_ID,A.APPLICATION_RECORD_ID) AS REASON_CODE ");
			sql.append("	   , PKA_NOTIFICATION.GET_PRODUCT_BY_NATION(AG.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,NATIONALITY) AS PRODUCT_NAME ");
			sql.append("	FROM ORIG_APPLICATION_GROUP AG 	");
			sql.append("	JOIN ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID AND AG.LIFE_CYCLE = A.LIFE_CYCLE ");
			sql.append("	JOIN ORIG_PERSONAL_RELATION R ON A.APPLICATION_RECORD_ID = R.REF_ID AND R.RELATION_LEVEL='A' ");
			sql.append("	JOIN ORIG_PERSONAL_INFO PS ON R.PERSONAL_ID = PS.PERSONAL_ID ");
			sql.append("	WHERE AG.APPLICATION_GROUP_ID = ?	");
			logger.debug("sql : "+sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++,applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				String APPLICATION_NO = rs.getString("APPLICATION_NO");
				String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				String PERSONAL_ID = rs.getString("PERSONAL_ID");
				String REASON_CODE = rs.getString("REASON_CODE");
				EmailRejectNCBPersonalInfoDataM personalRejectNcb = new EmailRejectNCBPersonalInfoDataM();
					personalRejectNcb.setApplicationNo(APPLICATION_NO);
					personalRejectNcb.setProductName(PRODUCT_NAME);
					personalRejectNcb.setFinalAppDecision(FINAL_APP_DECISION);
					personalRejectNcb.setPersonalType(PERSONAL_TYPE);
					personalRejectNcb.setPersonalId(PERSONAL_ID);
					personalRejectNcb.setApplicationGroupId(applicationGroupId);
					personalRejectNcb.setReasonCode(REASON_CODE);
				personalRejectNcbs.add(personalRejectNcb);
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
		return personalRejectNcbs;
	}
	@Override
	public String getGeneralParamValue(String paramCode)throws InfBatchException{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection conn = null;
//		String paramValue = null;		
//		try {
//			conn = getConnection();
//			StringBuilder sql = new StringBuilder("");
//			sql.append("SELECT PARAM_VALUE FROM GENERAL_PARAM WHERE PARAM_CODE = ?");
//			String dSql = String.valueOf(sql);
//			logger.debug("Sql=" + dSql);
//			ps = conn.prepareStatement(dSql);
//			rs = null;
//			ps.setString(1, paramCode);
//			rs = ps.executeQuery();			
//			if (rs.next()) {
//				paramValue = rs.getString("PARAM_VALUE");
//			}
//		} catch (Exception e) {
//			logger.fatal(e.getLocalizedMessage());
//			throw new InfBatchException(e.getLocalizedMessage());
//		} finally {
//			try {
//				closeConnection(conn, rs, ps);
//			} catch (Exception e) {
//				logger.fatal(e.getLocalizedMessage());
//				throw new InfBatchException(e.getLocalizedMessage());
//			}
//		}
//		return paramValue;
		return InfBatchProperty.getGeneralParam(paramCode);
	}
	@Override
	public HashMap<String, String> getGeneralParamMap(ArrayList<String> codeList)throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> hParam = new HashMap<String, String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			String comma = "";
			sql.append(" SELECT PARAM_CODE, PARAM_VALUE FROM GENERAL_PARAM WHERE PARAM_CODE IN ( ");
				for(String code : codeList){
					sql.append(comma)
					   .append(" ? ");
					comma = ",";
				}
			sql.append(" ) ");
			logger.debug("sql : " + sql);
			logger.debug("codeList : " + codeList);
			ps = conn.prepareStatement(sql.toString());
				int index = 1;
				for(String code : codeList){
					ps.setString(index++, code);
				}
			rs = ps.executeQuery();
			while(rs.next()) {
				String paramCode = rs.getString("PARAM_CODE");
				String paramValue = rs.getString("PARAM_VALUE");
				hParam.put(paramCode, paramValue);
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
		logger.debug("hParam : "+hParam);
		return hParam;
	}
	@Override
	public boolean isExceptionReasonCodes(String applicationGroupId)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int count = 0;		
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT COUNT(1) AS C_REASON FROM   XRULES_POLICY_RULES P  ");  
			sql.append(" INNER JOIN XRULES_VERIFICATION_RESULT  V  ON V.VER_RESULT_ID = P.VER_RESULT_ID AND V.VER_LEVEL='"+XRULES_VER_LEVEL_APPLICATION+"' ");
			sql.append(" INNER JOIN  ORIG_APPLICATION  A ON A.APPLICATION_RECORD_ID = V.REF_ID ");
			sql.append(" WHERE A.APPLICATION_GROUP_ID  = ?");
			sql.append("  AND   P.REASON IN (");
			sql.append("  ( SELECT");
			sql.append("             SUBSTR(SUB_PARAM, INSTR(SUB_PARAM,',',1,LEV) + 1, INSTR(SUB_PARAM,',',1,LEV");
            sql.append("          +1 )-INSTR(SUB_PARAM,',',1,LEV)-1)");
            sql.append("    FROM");
            sql.append("      (");
            sql.append("           SELECT");
            sql.append("              ','||PARAM_VALUE||',' SUB_PARAM");
            sql.append("          FROM");
            sql.append("               GENERAL_PARAM");
            sql.append("            WHERE");
            sql.append("                PARAM_CODE IN ('REJECT_REASON_AMLO',");
            sql.append("                                'REJECT_REASON_NCB')),");
            sql.append("          (");
            sql.append("               SELECT");
            sql.append("                    LEVEL LEV");
            sql.append("                 FROM");
            sql.append("                        DUAL CONNECT BY LEVEL <= 20 )");
            sql.append("           WHERE");
            sql.append("    LEV <= LENGTH(SUB_PARAM)-LENGTH(REPLACE(SUB_PARAM,','))-1)");
			sql.append(")");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId=" + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index=1;
			ps.setString(index++, applicationGroupId);
			rs = ps.executeQuery();			
			if (rs.next()) {
				count = rs.getInt("C_REASON");
				logger.debug("count>>"+count);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return count>0;
	}


	@Override
	public HashMap<String, String> getSellerMobileNo(ArrayList<String> saleIds,String saleType,String applicationGroupId)throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap<String, String> hData = new HashMap<String, String>();	
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SALES_PHONE_NO, SALES_ID FROM ORIG_SALE_INFO WHERE APPLICATION_GROUP_ID = ?");
			sql.append(" AND SALES_TYPE =? ");
			sql.append(" AND SALES_ID IN (");
			String COMMA="";
			for(String salseId :saleIds){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append("  ) AND SALES_PHONE_NO IS NOT NULL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId=" + applicationGroupId);
			logger.debug("saleType=" + saleType);
			ps = conn.prepareStatement(dSql);
			rs = null;
			int intdex=1;
			ps.setString(intdex++, applicationGroupId);
			ps.setString(intdex++, saleType);
			for(String salseId :saleIds){
				ps.setString(intdex++, salseId);
				logger.debug("salseId>>"+salseId);
			}
			rs = ps.executeQuery();			
			while (rs.next()) {
				 String SALES_ID = rs.getString("SALES_ID");
				 String SALES_PHONE_NO = rs.getString("SALES_PHONE_NO");
				 hData.put(SALES_ID, SALES_PHONE_NO);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return hData;
	}


	@Override
	public VCEmpInfoDataM selectVCEmpInfo(String SaleId)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VCEmpInfoDataM vcEmpInfo  = new VCEmpInfoDataM();
		
		 String BRANCH_INFO_STATUS = InfBatchProperty.getInfBatchConfig("NOTIFY_WARE_HOUSE_BRANCH_INFO_STATUS");
		try{
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  VC_RC_CD.TH_CNTR_NM, VC_EMP.KBNK_BR_NO, VC_EMP.* ");	 
			sql.append(" FROM IP_SHR.VC_EMP  ");	 
			sql.append(" LEFT JOIN IP_SHR.VC_RC_CD ON VC_EMP.KBNK_BR_NO=VC_RC_CD.KBNK_BR_NO  AND VC_RC_CD.EFF_ST_CD = '"+BRANCH_INFO_STATUS+"'");	 
			sql.append("  WHERE VC_EMP.EMP_ID = ?  AND  VC_RC_CD.EFF_ST_CD =?");	 
			ps = conn.prepareStatement(sql.toString());						
			logger.debug("sql : " + sql);		
			logger.debug("EMP_ID : " + SaleId);		
			ps.setString(1, SaleId);
			ps.setString(2, DEFUALT_EFF_ST_CD);
			rs = ps.executeQuery();

			if(rs.next()) {
				vcEmpInfo.setDeptId(FormatUtil.trim(rs.getString("DEPT_ID")));
				vcEmpInfo.setPrnDeptId(FormatUtil.trim(rs.getString("PRN_DEPT_ID")));
				vcEmpInfo.setEmpId(FormatUtil.trim(rs.getString("EMP_ID")));
				vcEmpInfo.setSubUnitCntrCd(FormatUtil.trim(rs.getString("SUB_UNIT_CNTR_CD")));
				vcEmpInfo.setUnitCntrCd(FormatUtil.trim(rs.getString("UNIT_CNTR_CD")));
				vcEmpInfo.setDeptCntrCd(FormatUtil.trim(rs.getString("DEPT_CNTR_CD")));
				vcEmpInfo.setBsnLineDeptId(FormatUtil.trim(rs.getString("BSN_LINE_DEPT_ID")));
				vcEmpInfo.setNtwCntrCd(FormatUtil.trim(rs.getString("NTW_CNTR_CD")));
				vcEmpInfo.setBranchNo(FormatUtil.trim(rs.getString("KBNK_BR_NO")));
				vcEmpInfo.setBranchName(FormatUtil.trim(rs.getString("TH_CNTR_NM")));
				vcEmpInfo.setDepartMentName(FormatUtil.trim(rs.getString("TH_DEPT_NM")));
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
		return vcEmpInfo;
	}


	@Override
	public boolean isContainRejected(String applicationGroupId)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count=0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(1) AS COUNT_REJECT ");
			sql.append(" FROM  ORIG_APPLICATION APP1");
			sql.append(" WHERE APP1.APPLICATION_GROUP_ID =? AND  APP1.FINAL_APP_DECISION=?");
			sql.append(" AND APP1.LIFE_CYCLE = (SELECT MAX(LIFE_CYCLE)  FROM  ORIG_APPLICATION A    WHERE  A.APPLICATION_GROUP_ID = APP1.APPLICATION_GROUP_ID)");
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());				 
			ps.setString(1,applicationGroupId); 		
			ps.setString(2,APPLICATION_STATUS_REJECT); 		
			rs = ps.executeQuery();
			if(rs.next()) {
				count =rs.getInt("COUNT_REJECT");
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
		 return count>0;
	}


	@Override
	public boolean isContainApproved(String applicationGroupId)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count=0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(1) AS COUNT_APPROVE ");
			sql.append(" FROM  ORIG_APPLICATION APP1");
			sql.append(" WHERE APP1.APPLICATION_GROUP_ID =? AND  APP1.FINAL_APP_DECISION=?");
			sql.append(" AND APP1.LIFE_CYCLE = (SELECT MAX(LIFE_CYCLE)  FROM  ORIG_APPLICATION A    WHERE  A.APPLICATION_GROUP_ID = APP1.APPLICATION_GROUP_ID)");
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());				 
			ps.setString(1,applicationGroupId); 		
			ps.setString(2,APPLICATION_STATUS_APPROVE); 		
			rs = ps.executeQuery();
			if(rs.next()) {
				count =rs.getInt("COUNT_APPROVE");
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
		 return count>0;
	}


	@Override
	public boolean isKECAndKCCApprove(String applicationGroupId)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count=0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) AS COUNT_CARD ");
			sql.append(" FROM  ORIG_APPLICATION");
			sql.append(" WHERE APPLICATION_GROUP_ID =?  ");
			sql.append(" AND SUBSTR(BUSINESS_CLASS_ID,1,INSTR(BUSINESS_CLASS_ID,'_')-1) IN ('"+NOTIFICATION_KEC_PRODUCT+"','"+NOTIFICATION_CC_PRODUCT+"')");
			sql.append(" AND ORIG_APPLICATION.FINAL_APP_DECISION=?");
			logger.debug("sql : " + sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());				 
			ps.setString(1,applicationGroupId); 		
			ps.setString(2,APPLICATION_STATUS_APPROVE); 		
			rs = ps.executeQuery();
			if(rs.next()) {
				count =rs.getInt("COUNT_CARD");
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
		 return count>0;
	}
	@Override
	public HashMap<String,ArrayList<String>> loadSMSTemplateNotification(NotificationInfoDataM notificationInfo,RecipientInfoDataM reipientInfo) throws InfBatchException {
		HashMap<String,ArrayList<String>> listTemplate = new HashMap<String,ArrayList<String>>();
		logger.debug("FinalDecision : "+reipientInfo.getFinalDecision());
		if(NOTIFICATION_FINAL_APP_DECISION_REJECT.contains(reipientInfo.getFinalDecision())){
			NotifyApplicationSegment notifyApplicationSegment = NotificationUtil.notifyApplication(notificationInfo.getApplicationGroupId());
			Reason reason = notifyApplicationSegment.findReasonProduct(reipientInfo.getProduct());
			String REASON_CODE = reason.getReasonCode();
			logger.debug("REASON_CODE : "+REASON_CODE);
			if(!InfBatchUtil.empty(REASON_CODE)){
				listTemplate = getSMSTemplateNotification(notificationInfo,reipientInfo,REASON_CODE);
			}
		}else{
			listTemplate = getSMSTemplateNotification(notificationInfo,reipientInfo,"");
		}
		return listTemplate;
	}
	
	@Override
	public HashMap<String,ArrayList<String>> loadSMSTemplateNotificationNoDecision(NotificationInfoDataM notificationInfo,RecipientInfoDataM reipientInfo) throws InfBatchException {
		HashMap<String,ArrayList<String>> listTemplate = new HashMap<String,ArrayList<String>>();
		//logger.debug("FinalDecision : "+reipientInfo.getFinalDecision());
		listTemplate = getSMSTemplateNotificationNoDecision(notificationInfo,reipientInfo,"");
		
		return listTemplate;
	}
	
	@Override
	public HashMap<String,ArrayList<String>> getSMSTemplateNotification(NotificationInfoDataM notificationInfo,RecipientInfoDataM reipientInfo,String reasonCode) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,ArrayList<String>> listTemplate = new HashMap<String,ArrayList<String>>();
		String templateId = notificationInfo.getApplicationTemplate();
		String SAVINGPLUS = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SENDING_TIME_SAVINGPLUS");
				
		//ArrayList<String> reasonCodes = notificationInfo.getRejectReasonCodes().get(NotificationInfoDataM.DEFAULT_REJECT_REASON);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
		
			sql.append(" SELECT DISTINCT MS.TEMPLATE_ID, MS.SENT_TO ");
			sql.append(" FROM MS_SMS_TEMPLATE_MASTER MS ");
			sql.append(" INNER JOIN MS_SMS_TEMPLATE_APPLY_TYPE  AP ON MS.TEMPLATE_ID = AP.TEMPLATE_ID ");
			sql.append(" LEFT JOIN MS_SMS_TEMPLATE_REJECT_REASON RR ON MS.TEMPLATE_ID = RR.TEMPLATE_ID ");
			sql.append(" LEFT JOIN MS_SMS_TEMPLATE_CASH_TRANSFER CT ON MS.TEMPLATE_ID = CT.TEMPLATE_ID ");		
			sql.append(" WHERE MS.SENT_TO IN (");
			if(null!=notificationInfo.getSendTos()){
				String COMMA="";
				for(String sendTo:notificationInfo.getSendTos()){
					sql.append(COMMA+"'"+sendTo+"'");
					COMMA=",";
				}
			}
			sql.append(" ) ");
			sql.append(" AND MS.APPLICATION_STATUS = ? ");
			
			logger.debug("APPLICATION GROUP TEMPLATE ID :: "+templateId);
			logger.debug("FINAL APP DECISION "+reipientInfo.getFinalDecision());
			
			if(reipientInfo.getFinalDecision().contains(NOTIFICATION_FINAL_APP_DECISION_REJECT)){
				if(InfBatchProperty.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(templateId) 
						|| InfBatchProperty.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(templateId) 
						|| InfBatchProperty.getGeneralParam(GEN_PARAM_CC_PREMIER).contains(templateId)){
					if(InfBatchUtil.empty(notificationInfo.getSaleType())){
						sql.append(" AND RR.REJECT_CODE IS NOT NULL ");
						if(!InfBatchUtil.empty(reasonCode)){
							sql.append(" AND RR.REJECT_CODE = '"+reasonCode+"' ");
						}
					}
				}else{	
					if(!InfBatchUtil.empty(reasonCode)){
						sql.append(" AND RR.REJECT_CODE IS NOT NULL ");
						if(!InfBatchUtil.empty(reasonCode)){
							sql.append(" AND RR.REJECT_CODE = '"+reasonCode+"' ");
						}
					}
				}
//				if(InfBatchProperty.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(templateId) 
//						|| InfBatchProperty.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(templateId) 
//						|| InfBatchProperty.getGeneralParam(GEN_PARAM_CC_PREMIER).contains(templateId)){
//					if(InfBatchUtil.empty(notificationInfo.getSaleType())){
//						sql.append(" AND RR.REJECT_CODE IS NOT NULL ");
//						if(null!=reasonCodes && reasonCodes.size()>0){
//							sql.append(" AND RR.REJECT_CODE IN (");
//							String comma="";
//							for(String reason : reasonCodes){
//								sql.append(comma+"'"+reason+"'");
//								comma=",";
//							}
//							sql.append(")");
//						}
//					}
//				}else{	
//					sql.append(" AND RR.REJECT_CODE IS NOT NULL ");
//					if(null!=reasonCodes && reasonCodes.size()>0){
//						sql.append(" AND RR.REJECT_CODE IN (");
//						String comma="";
//						for(String reason : reasonCodes){
//							sql.append(comma+"'"+reason+"'");
//							comma=",";
//						}
//						sql.append(")");
//					}
//				}
			}
			sql.append(" AND MS.PRODUCT_TYPE IS NOT NULL ");
			sql.append(" AND MS.PRODUCT_TYPE = ? ");
			sql.append(" AND AP.APPLY_TYPE = ? ");
			sql.append(" AND MS.ENABLED = ? ");

			if(NOTIFICATION_KEC_PRODUCT.equals(reipientInfo.getProduct())){
				logger.debug("notificationInfoDataM.getCashTransType() :: "+notificationInfo.getCashTransTypes());
				if(!Util.empty(notificationInfo.getCashTransTypes())  && !Util.empty(reipientInfo.getCashTranferType())
						&& notificationInfo.getCashTransTypes().contains(reipientInfo.getCashTranferType())){
					sql.append("AND CT.CASH_TRANSFER_TYPE ='"+reipientInfo.getCashTranferType()+"'"); 
				}else{
					 sql.append("AND CT.CASH_TRANSFER_TYPE ='ALL'"); 
				}				 
			}

			logger.debug("sql NOTIFICATION_TEMPLATE : "+sql.toString());	
			logger.debug("PRODUCT :: "+reipientInfo.getProduct());
			logger.debug("ApplicatonType :: "+notificationInfo.getApplyType());
			logger.debug("reipientInfo.getFinalDecision() :: "+reipientInfo.getFinalDecision());
			
			ps = conn.prepareStatement(sql.toString());	
			ps.setString(1,reipientInfo.getFinalDecision());
			ps.setString(2,reipientInfo.getProduct());
			ps.setString(3,notificationInfo.getApplyType());
			ps.setString(4, NOTIFICATION_TEMPLATE_ENABLE);
			rs = ps.executeQuery();
			while(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				// KPL: As on approve we can have 1 SMS template for saving plus and another 1 SMS template
				// for approval, then a logic to pickup the right SMS template based on sending time 
				if (SAVINGPLUS.equals(notificationInfo.getSendingTime())) {
					if (!TEMPLATE_ID.contains(SAVINGPLUS))
						continue;
				}
				else {
					if (TEMPLATE_ID.contains(SAVINGPLUS))
						continue;
				}
				String SENT_TO = rs.getString("SENT_TO");
				ArrayList<String> templates = listTemplate.get(SENT_TO);
				if(null==templates){
					templates = new ArrayList<String>();
					listTemplate.put(SENT_TO, templates);
				}
				if(!templates.contains(TEMPLATE_ID)){
					templates.add(TEMPLATE_ID);
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return listTemplate;
	}
	
	@Override
	public HashMap<String,ArrayList<String>> getSMSTemplateNotificationNoDecision(NotificationInfoDataM notificationInfo,RecipientInfoDataM reipientInfo,String reasonCode) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,ArrayList<String>> listTemplate = new HashMap<String,ArrayList<String>>();
		String templateId = notificationInfo.getApplicationTemplate();
		//ArrayList<String> reasonCodes = notificationInfo.getRejectReasonCodes().get(NotificationInfoDataM.DEFAULT_REJECT_REASON);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
		
			sql.append(" SELECT DISTINCT MS.TEMPLATE_ID, MS.SENT_TO ");
			sql.append(" FROM MS_SMS_TEMPLATE_MASTER MS ");
			sql.append(" INNER JOIN MS_SMS_TEMPLATE_APPLY_TYPE  AP ON MS.TEMPLATE_ID = AP.TEMPLATE_ID ");
			sql.append(" LEFT JOIN MS_SMS_TEMPLATE_REJECT_REASON RR ON MS.TEMPLATE_ID = RR.TEMPLATE_ID ");
			sql.append(" LEFT JOIN MS_SMS_TEMPLATE_CASH_TRANSFER CT ON MS.TEMPLATE_ID = CT.TEMPLATE_ID ");		
			sql.append(" WHERE MS.SENT_TO IN (");
			if(null!=notificationInfo.getSendTos()){
				String COMMA="";
				for(String sendTo:notificationInfo.getSendTos()){
					sql.append(COMMA+"'"+sendTo+"'");
					COMMA=",";
				}
			}
			sql.append(" ) ");
			sql.append(" AND MS.APPLICATION_STATUS = ? ");
			
			logger.debug("APPLICATION GROUP TEMPLATE ID :: "+templateId);
			logger.debug("FINAL APP DECISION "+reipientInfo.getFinalDecision());
			
			sql.append(" AND MS.PRODUCT_TYPE IS NOT NULL ");
			sql.append(" AND MS.PRODUCT_TYPE = ? ");
			sql.append(" AND AP.APPLY_TYPE = ? ");
			sql.append(" AND MS.ENABLED = ? ");

			if(NOTIFICATION_KEC_PRODUCT.equals(reipientInfo.getProduct())){
				logger.debug("notificationInfoDataM.getCashTransType() :: "+notificationInfo.getCashTransTypes());
				if(!Util.empty(notificationInfo.getCashTransTypes())  && !Util.empty(reipientInfo.getCashTranferType())
						&& notificationInfo.getCashTransTypes().contains(reipientInfo.getCashTranferType())){
					sql.append("AND CT.CASH_TRANSFER_TYPE ='"+reipientInfo.getCashTranferType()+"'"); 
				}else{
					 sql.append("AND CT.CASH_TRANSFER_TYPE ='ALL'"); 
				}				 
			}

			logger.debug("sql NOTIFICATION_TEMPLATE : "+sql.toString());	
			logger.debug("PRODUCT :: "+reipientInfo.getProduct());
			logger.debug("ApplicatonType :: "+notificationInfo.getApplyType());
			logger.debug("reipientInfo.getFinalDecision() :: "+reipientInfo.getFinalDecision());
			
			ps = conn.prepareStatement(sql.toString());	
			ps.setString(1,"ALL");
			ps.setString(2,reipientInfo.getProduct());
			ps.setString(3,notificationInfo.getApplyType());
			ps.setString(4, NOTIFICATION_TEMPLATE_ENABLE);
			rs = ps.executeQuery();
			while(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				String SENT_TO = rs.getString("SENT_TO");
				ArrayList<String> templates = listTemplate.get(SENT_TO);
				if(null==templates){
					templates = new ArrayList<String>();
					listTemplate.put(SENT_TO, templates);
				}
				if(!templates.contains(TEMPLATE_ID)){
					templates.add(TEMPLATE_ID);
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return listTemplate;
	}
	
	@Override
	public HashMap<String,ArrayList<String>> getSellerSMSTemplateNotification(NotificationInfoDataM notificationInfo,RecipientInfoDataM reipientInfo) throws InfBatchException {
		String NOTIFICATION_SEND_TO_TYPE_SELLER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,ArrayList<String>> listTemplate = new HashMap<String,ArrayList<String>>();
		String templateId = notificationInfo.getApplicationTemplate();		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
		
			sql.append(" SELECT DISTINCT MS.TEMPLATE_ID, MS.SENT_TO ");
			sql.append(" FROM MS_SMS_TEMPLATE_MASTER MS ");
			sql.append(" INNER JOIN MS_SMS_TEMPLATE_APPLY_TYPE  AP ON MS.TEMPLATE_ID = AP.TEMPLATE_ID ");
			sql.append(" LEFT JOIN MS_SMS_TEMPLATE_REJECT_REASON RR ON MS.TEMPLATE_ID = RR.TEMPLATE_ID ");
			sql.append(" LEFT JOIN MS_SMS_TEMPLATE_CASH_TRANSFER CT ON MS.TEMPLATE_ID = CT.TEMPLATE_ID ");		
			sql.append(" WHERE MS.SENT_TO = '"+NOTIFICATION_SEND_TO_TYPE_SELLER+"' ");
			sql.append(" AND MS.APPLICATION_STATUS = ? ");
			sql.append(" AND MS.PRODUCT_TYPE IS NOT NULL ");
			sql.append(" AND MS.PRODUCT_TYPE = ? ");
			sql.append(" AND AP.APPLY_TYPE = ? ");
			sql.append(" AND MS.ENABLED = ? ");
			if(NOTIFICATION_KEC_PRODUCT.equals(reipientInfo.getProduct())){
				logger.debug("notificationInfoDataM.getCashTransType() :: "+notificationInfo.getCashTransTypes());
				if(!Util.empty(notificationInfo.getCashTransTypes())  && !Util.empty(reipientInfo.getCashTranferType())
						&& notificationInfo.getCashTransTypes().contains(reipientInfo.getCashTranferType())){
					sql.append("AND CT.CASH_TRANSFER_TYPE ='"+reipientInfo.getCashTranferType()+"'"); 
				}else{
					 sql.append("AND CT.CASH_TRANSFER_TYPE ='ALL'"); 
				}				 
			}
			logger.debug("sql NOTIFICATION_TEMPLATE : "+sql.toString());
			logger.debug("APPLICATION GROUP TEMPLATE ID :: "+templateId);
			logger.debug("FINAL APP DECISION "+reipientInfo.getFinalDecision());
			logger.debug("PRODUCT :: "+reipientInfo.getProduct());
			logger.debug("ApplicatonType :: "+notificationInfo.getApplyType());
			logger.debug("reipientInfo.getFinalDecision() :: "+reipientInfo.getFinalDecision());
			ps = conn.prepareStatement(sql.toString());	
				ps.setString(1,reipientInfo.getFinalDecision());
				ps.setString(2,reipientInfo.getProduct());
				ps.setString(3,notificationInfo.getApplyType());
				ps.setString(4, NOTIFICATION_TEMPLATE_ENABLE);
			rs = ps.executeQuery();
			while(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				String SENT_TO = rs.getString("SENT_TO");
				ArrayList<String> templates = listTemplate.get(SENT_TO);
				if(null==templates){
					templates = new ArrayList<String>();
					listTemplate.put(SENT_TO, templates);
				}
				if(!templates.contains(TEMPLATE_ID)){
					templates.add(TEMPLATE_ID);
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return listTemplate;
	}
	@Override
	public HashMap<String,ArrayList<String>> getEmailTemplateNotification(NotificationInfoDataM notificationInfo)throws InfBatchException{
		HashMap<String,ArrayList<String>>  templateMap = new HashMap<String,ArrayList<String>>();
		try{
			if(notificationInfo.isSendEmail()){
				getEmailNotification(notificationInfo,templateMap);
			}
			if(notificationInfo.getSendTos().contains(NOTIFICATION_SEND_TO_TYPE_COMPLIANCE)){
				getEmailCompliance(notificationInfo,templateMap);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		logger.debug("templateMap : "+templateMap);
		return templateMap;
	}
	private void getEmailNotification(NotificationInfoDataM notificationInfo,HashMap<String,ArrayList<String>> templateMap)throws InfBatchException{
		String NOTIFICATION_TYPE_VALUE_EMAIL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_TYPE_VALUE_EMAIL");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			logger.debug("rejectReasonCodes : "+notificationInfo.getRejectReasonCodes());
			sql.append(" SELECT  TEMPLATE_ID, SUB_SEND_TO  ");
	        sql.append(" FROM MS_EMAIL_TEMPLATE_MASTER  MS ");                
	    	sql.append(" INNER JOIN (SELECT SUBSTR(SUB_PARAM, INSTR(SUB_PARAM,',',1,LEV) + 1, INSTR(SUB_PARAM,',',1,LEV+1 )-INSTR(SUB_PARAM,',',1,LEV)-1)AS SUB_SEND_TO,SUB_TEMPLATE_ID ");
	    	sql.append(" FROM  (SELECT  ','||SEND_TO ||',' SUB_PARAM ,TEMPLATE_ID AS SUB_TEMPLATE_ID FROM MS_EMAIL_TEMPLATE_MASTER  WHERE APPLICATION_STATUS ='"+notificationInfo.getApplicationStatus()+"' ), ");
	    	sql.append("  		(SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 20   ) WHERE  LEV <= LENGTH(SUB_PARAM)-LENGTH(REPLACE(SUB_PARAM,','))-1) SUB ");
	    	sql.append("  ON SUB.SUB_TEMPLATE_ID = MS.TEMPLATE_ID ");
	    	if(APPLICATION_STATUS_REJECT.equals(notificationInfo.getApplicationStatus()) && !InfBatchUtil.empty(notificationInfo.getRejectReasonCodes())){
	    		sql.append(" JOIN MS_NOTI_REJECT_REASON RJ ON   MS.TEMPLATE_ID = RJ.EMAIL_TEMPLATE_ID ");
	    		sql.append(" AND RJ.REASON_CODE IN ( ");
	    		String COMMA="";
	        	for(String reasonCode :notificationInfo.getRejectAllReasonCodes()){
	        		sql.append(COMMA+"'"+reasonCode+"'");
	        		COMMA=",";
	        	}
	        	sql.append("  )");
	    	}
	        sql.append("  WHERE  SUB_SEND_TO IN (");
//	        if(null!=notificationInfo.getSendTos()){
//	        	String COMMA="";
//	        	for(String sendTo :notificationInfo.getSendTos()){
//	        		sql.append(COMMA+"'"+sendTo+"'");
//	        		COMMA=",";
//	        	}
//	        }
	        if(!Util.empty(notificationInfo.getJobCodes())){
	        	HashMap<String, ArrayList<JobCodeDataM>> jobMap = notificationInfo.getJobCodes();
	        	String COMMA="";
	        	ArrayList<String> existingSendTos = new ArrayList<String>(); 
	        	for(String key : jobMap.keySet()){
	        		ArrayList<JobCodeDataM> jobCodes = jobMap.get(key);
	        		if(!InfBatchUtil.empty(jobCodes)){
	        			for(JobCodeDataM jobCode : jobCodes){
	        				String notificationType = jobCode.getNotificationType();
	        				String sendTo = jobCode.getSendTo();
	        				if(NOTIFICATION_TYPE_VALUE_EMAIL.equals(notificationType) && !existingSendTos.contains(sendTo)
	        						&& !NOTIFICATION_SEND_TO_TYPE_COMPLIANCE.equals(sendTo)){
	        					sql.append(COMMA+"'"+sendTo+"'");
	        	        		COMMA=",";
	        	        		existingSendTos.add(sendTo);
	        				}
	        			}
	        		}
	        	}
	        }
	        sql.append("  )");
	        sql.append("  AND APPLICATION_STATUS = ? ");
			logger.debug("sql: "+sql.toString());	
			ps = conn.prepareStatement(sql.toString());	
			ps.setString(1, notificationInfo.getApplicationStatus());
			logger.debug("notificationInfoDataM.getApplicationStatus() : "+notificationInfo.getApplicationStatus());
			rs = ps.executeQuery();
			while(rs.next()) {
				String SUB_SEND_TO = rs.getString("SUB_SEND_TO");
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				ArrayList<String> templates = templateMap.get(SUB_SEND_TO);
				if(null==templates){
					templates = new ArrayList<String>();
					templateMap.put(SUB_SEND_TO, templates);
				}
				if(!templates.contains(TEMPLATE_ID)){
					templates.add(TEMPLATE_ID);
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
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	private void getEmailCompliance(NotificationInfoDataM notificationInfo,HashMap<String,ArrayList<String>> templateMap)throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TEMPLATE_ID,SEND_TO FROM MS_EMAIL_TEMPLATE_MASTER WHERE SEND_TO = '"+NOTIFICATION_SEND_TO_TYPE_COMPLIANCE+"' ");
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());	
			rs = ps.executeQuery();
			while(rs.next()) {
				String SEND_TO = rs.getString("SEND_TO");
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				ArrayList<String> templates = templateMap.get(SEND_TO);
				if(null==templates){
					templates = new ArrayList<String>();
					templateMap.put(SEND_TO, templates);
				}
				if(!templates.contains(TEMPLATE_ID)){
					templates.add(TEMPLATE_ID);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void insertInfExportKmobile(String contentMessage,String MODULE_ID)
			throws InfBatchException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");//INF_EXPORT_PK.NEXTVAL
			sql.append(" INSERT INTO INF_EXPORT ");
			sql.append(" (EXPORT_ID, MODULE_ID,   ");
			sql.append(" DATA_DATE, CONTENT, UPDATE_DATE, UPDATE_BY) ");
			sql.append(" VALUES (INF_EXPORT_PK.NEXTVAL,?,?,?,?,?) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, MODULE_ID);
			ps.setTimestamp(2, ApplicationDate.getTimestamp());
			ps.setString(3, contentMessage);
			ps.setTimestamp(4, ApplicationDate.getTimestamp());
			ps.setString(5, SYSTEM_USER_ID);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void insertInfExportKmobile(InfExportDataM infExport)throws InfBatchException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");//INF_EXPORT_PK.NEXTVAL
			sql.append(" INSERT INTO INF_EXPORT ");
			sql.append(" (EXPORT_ID, MODULE_ID,   ");
			sql.append(" DATA_DATE, FILE_NAME, CONTENT, UPDATE_DATE, UPDATE_BY) ");
			sql.append(" VALUES (INF_EXPORT_PK.NEXTVAL,?,?,?,?,?,?) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				int index = 1;
				ps.setString(index++, infExport.getModuleID());
				ps.setTimestamp(index++, infExport.getDataDate());
				ps.setString(index++, infExport.getFileName());
				ps.setString(index++, infExport.getContent());
				ps.setTimestamp(index++, infExport.getUpdateDate());
				ps.setString(index++, infExport.getUpdateBy());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public ArrayList<CardParamDataM> getCardParams() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CardParamDataM> listCardParam = new ArrayList<CardParamDataM>();
		StringBuilder sql = new StringBuilder();
		try{
			conn = getConnection();
			/*sql.append(" SELECT CP.CARD_PARAM_ID,CP.CARD_PARAM_DESC,CP.CARD_TYPE_ID,CP.PARAMCD, ");
			sql.append(" CP.VALUE1,CP.VALUE2,CT.CARD_GROUP,  ");
			sql.append(" CT.CARD_LEVEL AS CARD_LEVEL_ID,CARD_TYPE_LIST.DISPLAY_NAME AS CARD_TYPE_DESC, ");
			sql.append(" CARD_LEVEL_LIST.DISPLAY_NAME AS CARD_LEVEL_DESC  ");
			sql.append(" FROM CARD_PARAM CP ");
			sql.append(" LEFT JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = CP.CARD_TYPE_ID  ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER CARD_TYPE_LIST ON  ");
			sql.append(" (CARD_TYPE_LIST.FIELD_ID = ? AND CARD_TYPE_LIST.CHOICE_NO = CT.CARD_GROUP)  ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER CARD_LEVEL_LIST ON  ");
			sql.append(" (CARD_LEVEL_LIST.FIELD_ID = ? AND CARD_LEVEL_LIST.CHOICE_NO = CT.CARD_LEVEL)  ");
			sql.append(" WHERE CP.CARD_PARAM_ID IN ('CC_049_CARD','CC_005_CARD','CC_008_CARD') ");*/
			
			
			/*sql.append(" SELECT CP.CARD_PARAM_ID,CP.CARD_PARAM_DESC,CP.CARD_TYPE_ID,CP.PARAMCD,  ");
			sql.append(" CP.VALUE1,CP.VALUE2, ");
			sql.append(" CT.CARD_GROUP AS CARD_GROUP_ID, ");
			sql.append(" CT.CARD_LEVEL AS CARD_LEVEL_ID, ");
			sql.append(" CARD_TYPE_LIST.DISPLAY_NAME AS CARD_TYPE_DESC, ");
			sql.append(" CARD_LEVEL_LIST.DISPLAY_NAME AS CARD_LEVEL_DESC ");
			sql.append(" FROM CARD_PARAM CP ");
			sql.append(" LEFT JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = CP.CARD_TYPE_ID   ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER CARD_TYPE_LIST ON (CARD_TYPE_LIST.FIELD_ID = ? AND CARD_TYPE_LIST.CHOICE_NO = CT.CARD_GROUP)   ");
			sql.append(" LEFT JOIN LIST_BOX_MASTER CARD_LEVEL_LIST ON (CARD_LEVEL_LIST.FIELD_ID = ? AND CARD_LEVEL_LIST.CHOICE_NO = CT.CARD_LEVEL)   ");
			sql.append(" WHERE CP.PARAMCD = ? ");
			*/
			
			sql.append(" SELECT DISTINCT VALUE1,VALUE2,PARAMCD FROM CARD_PARAM ");
	
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				CardParamDataM cardParam = new CardParamDataM();
				cardParam.setParamCode(rs.getString("PARAMCD"));
				cardParam.setValue1(rs.getString("VALUE1"));
				cardParam.setValue2(rs.getString("VALUE2"));
				listCardParam.add(cardParam);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return listCardParam;
	}


	@Override
	public HashMap<String, RunningParamM> getRunningParam()throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,RunningParamM> runningParamData = new HashMap<String,RunningParamM>();
		String paramIdKey = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ");
			sql.append(" PARAM_ID, PARAM_DESC, ");
			sql.append(" VALUE_FROM, VALUE_TO, VALUE1,VALUE2 ");
			sql.append(" FROM RUNNING_PARAM  ");
			sql.append(" WHERE VALUE_FROM IS NOT NULL  ");
			sql.append(" AND VALUE_TO IS NOT NULL  ");
			sql.append(" AND VALUE1 IS NOT NULL  ");
			sql.append(" AND VALUE2 IS NOT NULL ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				if(!Util.empty(rs.getString("PARAM_ID"))){
					RunningParamM runningParam = new RunningParamM();
					paramIdKey = rs.getString("PARAM_ID");
					runningParam.setParamID(paramIdKey);
					runningParam.setParamDesc(rs.getString("PARAM_DESC"));
					runningParam.setValueFrom(rs.getDouble("VALUE_FROM"));
					runningParam.setValueTo(rs.getDouble("VALUE_TO"));
					runningParam.setValue1(rs.getDouble("VALUE1"));
					runningParam.setValue2(rs.getDouble("VALUE2"));
					runningParamData.put(paramIdKey, runningParam);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return runningParamData;
	}


	@Override
	public String getKmobileRoleNameFromAppLog(String applicationGroupId,int lifeCycle)throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String appLogId = null;
		
		int index = 0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			
//			sql.append(" SELECT MAX(APP_LOG_ID) AS APP_LOG_ID FROM ORIG_APPLICATION_LOG  ");
//			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND LIFE_CYCLE = ? ");
//			sql.append(" AND (APPLICATION_STATUS = ? OR APPLICATION_STATUS = ?) ");
//			sql.append(" AND ROLE_NAME IN (");
			sql.append(" SELECT MAX(APP_LOG_ID) AS APP_LOG_ID FROM ORIG_APPLICATION_LOG  ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND LIFE_CYCLE = ? ");
			sql.append(" AND APPLICATION_STATUS = ? AND ROLE_NAME IN ( ");	
			if(null!=NOTIFICATION_KMOBILE_ROLE_NAME){
				String COMMA="";
				for(String sendTo :NOTIFICATION_KMOBILE_ROLE_NAME){
					sql.append(COMMA+"'"+sendTo+"'");
					COMMA=",";
				}
			}
			sql.append(" ) ");
	
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(++index, applicationGroupId);
			ps.setInt(++index, lifeCycle);
			ps.setString(++index,NOTIFICATION_APP_STATUS_PRE_APPROVE);
			//ps.setString(++index,NOTIFICATION_APP_STATUS_PRE_REJECT);
	
			rs = ps.executeQuery();
			if(rs.next()){
				if(!Util.empty(rs.getString("APP_LOG_ID"))){
					appLogId = rs.getString("APP_LOG_ID");
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return appLogId;
	}


	@Override
	public String getTemplateCode(String templateId) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String TEMPLATE_CODE = null;		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TEMPLATE_CODE FROM MS_TEMPLATE WHERE TEMPLATE_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, templateId);
			rs = ps.executeQuery();			
			if(rs.next()){
				TEMPLATE_CODE = rs.getString("TEMPLATE_CODE");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		logger.debug("TEMPLATE_CODE : "+TEMPLATE_CODE);
		return TEMPLATE_CODE;
	}


	@Override
	public String getCardLinkCustFlag(String applicationGroupId)throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String cardLinkCustFlag = "";		
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT NEW_CARDLINK_CUST_FLAG  FROM ORIG_CARDLINK_CUSTOMER C ");
			sql.append(" INNER JOIN  ORIG_PERSONAL_INFO P ON P.PERSONAL_ID = C.PERSONAL_ID ");
			sql.append(" WHERE P.PERSONAL_TYPE =? AND  P.APPLICATION_GROUP_ID =? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, PERSONAL_TYPE_SUPPLEMENTARY);
			ps.setString(2, applicationGroupId);
			rs = ps.executeQuery();			
			if (rs.next()) {
				cardLinkCustFlag = rs.getString("NEW_CARDLINK_CUST_FLAG");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		logger.debug("cardLinkCustFlag>>"+cardLinkCustFlag);
		return cardLinkCustFlag;
	}

	@Override
	public HashMap<String,NotificationInfoDataM> getNotificationInfoNextDay()throws InfBatchException {
		String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,NotificationInfoDataM>  notificationNextDays  = new HashMap<String,NotificationInfoDataM>();
		HashMap<String,NotifyApplicationSegment> hAppReason = new HashMap<String,NotifyApplicationSegment>();
		HashMap<String,InfBatchLogDataM> hNotSendLog = new HashMap<String,InfBatchLogDataM>(); 
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			ArrayList<String> sendToConditions = new ArrayList<>(Arrays.asList(NOTIFICATION_SMS_NEXT_DAY_SEND_TO_CONDITION.split(",")));
			
			sql.append(" SELECT DISTINCT  S.SEND_TIME ,S.NOTIFICATION_TYPE ,NVL(P.PRIORITY,'1') AS PRIORITY ,D.SEND_TO");
			sql.append(" ,M.JOB_CODE ,M.OPTIONAL1 ,M.OPTIONAL2 ,M.OPTIONAL3 ,M.OPTIONAL4 ");
			sql.append(" ,M.OPTIONAL5 , M.PATTERN , APP.*,G.CASH_TRANSFER_TYPE ");
	        //sql.append(" FROM (SELECT MAX(APP.LIFE_CYCLE) AS MAX_LIFE_CYCLE, APP_GROUP.APPLICATION_TEMPLATE,S_NORMAL.SALES_ID,S_NORMAL.SALE_CHANNEL ,");
			sql.append(" FROM (SELECT APP_GROUP.LIFE_CYCLE AS MAX_LIFE_CYCLE, APP_GROUP.APPLICATION_TEMPLATE,S_NORMAL.SALES_ID,S_NORMAL.SALE_CHANNEL ,");
	        sql.append("    DECODE(S_NORMAL.SALES_ID,NULL,'N','','N','Y') AS SALE_FLAG,");
	        sql.append("	APP_GROUP.APPLICATION_GROUP_ID,APP_GROUP.APPLICATION_TYPE, S_RECOMMEND.SALES_ID AS RECOMMEND, R.REASON_CODE,CT.CASH_TRANSFER_TYPE, APP_GROUP.APPLICATION_GROUP_NO ");
		    sql.append(" 	FROM  ORIG_APPLICATION_GROUP  APP_GROUP ");
		    sql.append(" 	INNER JOIN ORIG_APPLICATION APP ON APP.APPLICATION_GROUP_ID = APP_GROUP.APPLICATION_GROUP_ID AND APP_GROUP.LIFE_CYCLE = APP.LIFE_CYCLE ");
		    sql.append("		AND APP_GROUP.JOB_STATE IN (	");
		    String COMMA="";
		    for(String jobState : NOTIFICATION_SMS_NEXT_DAY_JOB_STATE_END){
		    	sql.append(COMMA+"?");
		    	COMMA=",";
		    }
		    sql.append("		) AND APP.FINAL_APP_DECISION = '"+NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION+"' ");
		    sql.append(" 	LEFT JOIN ORIG_SALE_INFO S_NORMAL ON APP_GROUP.APPLICATION_GROUP_ID = S_NORMAL.APPLICATION_GROUP_ID AND S_NORMAL.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"' ");
		    sql.append(" 	LEFT JOIN ORIG_SALE_INFO S_RECOMMEND ON APP_GROUP.APPLICATION_GROUP_ID = S_RECOMMEND.APPLICATION_GROUP_ID AND S_RECOMMEND.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_REFERENCE+"' ");
		    sql.append(" 	LEFT JOIN  ORIG_LOAN L ON L.APPLICATION_RECORD_ID  = APP.APPLICATION_RECORD_ID ");
		    sql.append(" 	LEFT JOIN  ORIG_CASH_TRANSFER CT ON CT.LOAN_ID  = L.LOAN_ID ");
		    sql.append(" 	LEFT JOIN (SELECT APPLICATION_RECORD_ID,APPLICATION_GROUP_ID, PKA_NOTIFICATION.GET_MIN_RANK_REASON_CODE(APPLICATION_GROUP_ID,APPLICATION_RECORD_ID)AS REASON_CODE FROM  ORIG_APPLICATION) R ");
		    sql.append(" 	 ON R.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID AND R.APPLICATION_RECORD_ID=APP.APPLICATION_RECORD_ID ");
//		    sql.append(" 	WHERE ");		    
//			sql.append("      NOT EXISTS ");
//			sql.append("            ( SELECT   (1) ");
//			sql.append("                FROM    INF_BATCH_LOG B ");
//			sql.append("                WHERE  INTERFACE_CODE = '"+NOTIFICATION_SMS_NEXT_DAY_INF_CODE+"' ");
//			sql.append("                AND B.INTERFACE_STATUS = '"+INTERFACE_STATUS_COMPLETE+"' ");
//			sql.append("                AND APP_GROUP.APPLICATION_GROUP_ID = B.APPLICATION_GROUP_ID) ");
		    sql.append(" 	GROUP BY APP_GROUP.APPLICATION_TEMPLATE, S_NORMAL.SALES_ID,S_NORMAL.SALE_CHANNEL ,S_NORMAL.SALES_ID, ");
		    sql.append(" 	APP_GROUP.APPLICATION_GROUP_ID,APP_GROUP.APPLICATION_TYPE, S_RECOMMEND.SALES_ID, R.REASON_CODE, CT.CASH_TRANSFER_TYPE, APP_GROUP.APPLICATION_GROUP_NO,APP_GROUP.LIFE_CYCLE	");	    
		    sql.append(" 	) APP ");
		    sql.append(" INNER JOIN MS_NOTI_APP_TEMPLATE T ON T.TEMPLATE_CODE = APP.APPLICATION_TEMPLATE OR T.TEMPLATE_CODE = '"+DEFUALT_DATA_TYPE_ALL+"' ");
		    sql.append(" INNER JOIN MS_NOTI_GROUP  G ON G.GROUP_CODE =T.GROUP_CODE ");
			sql.append(" INNER JOIN MS_NOTI_CHANNEL C ON C.GROUP_CODE =T.GROUP_CODE");
			sql.append(" INNER JOIN MS_NOTI_SENDING S ON S.GROUP_CHANNEL_CODE = C.GROUP_CHANNEL_CODE");
			sql.append(" INNER JOIN MS_NOTI_SENDING_DETAIL D ON  S.SENDING_ID = D.SENDING_ID");
			sql.append(" LEFT JOIN  MS_NOTI_SENDING_PRIORITY  P ON P.GROUP_CHANNEL_CODE = S.GROUP_CHANNEL_CODE AND P.SENDING_ID = S.SENDING_ID ");
			sql.append(" LEFT JOIN  MS_NOTI_MANAGER_CHANNEL M ON M.CHANNEL_CODE =C.CHANNEL_CODE");
			sql.append(" LEFT JOIN  MS_NOTI_REJECT_REASON RE ON RE.GROUP_CODE = G.GROUP_CODE AND APP.REASON_CODE=RE.REASON_CODE");
			sql.append(" WHERE G.HAVE_SALE_FLAG =APP.SALE_FLAG  AND  (G.CASH_TRANSFER_TYPE = APP.CASH_TRANSFER_TYPE OR  G.CASH_TRANSFER_TYPE='"+DEFUALT_DATA_TYPE_ALL+"') ");
			sql.append("  AND (C.CHANNEL_CODE = APP.SALE_CHANNEL OR C.CHANNEL_CODE = '"+DEFUALT_DATA_TYPE_ALL+"' OR (C.CHANNEL_CODE = 'N' AND APP.SALE_CHANNEL IS NULL ))  ");     
		    sql.append("  AND S.SEND_TIME = ? AND D.SEND_TO IN ( ");
		    COMMA="";
		    for(String sendTo :sendToConditions){
		    	  sql.append(COMMA+"?");
		    	  COMMA=",";
		    }
		    sql.append("  )");
			sql.append("  AND NOT EXISTS ");
			sql.append("            ( SELECT   (1) ");
			sql.append("                FROM    INF_BATCH_LOG B ");
			sql.append("                WHERE  INTERFACE_CODE = '"+NOTIFICATION_SMS_NEXT_DAY_INF_CODE+"' ");
			sql.append("                AND B.INTERFACE_STATUS IN ('"+INTERFACE_STATUS_COMPLETE+"', '"+INTERFACE_STATUS_NOT_SEND+"') ");
			sql.append("                AND APP.APPLICATION_GROUP_ID = B.APPLICATION_GROUP_ID ");
			sql.append("                AND APP.MAX_LIFE_CYCLE = B.LIFE_CYCLE) ");
			logger.debug("sql : " + sql);
			logger.debug("NOTIFICATION_SMS_NEXT_DAY_JOB_STATE_END : " +NOTIFICATION_SMS_NEXT_DAY_JOB_STATE_END);
			logger.debug("NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME : " +NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME);
			 
			ps = conn.prepareStatement(sql.toString());		
			int index=0;
			for(String jobState :NOTIFICATION_SMS_NEXT_DAY_JOB_STATE_END){
				ps.setString(++index,jobState); 
		    }
			ps.setString(++index,NOTIFICATION_SMS_NEXT_DAY_SENDING_TIME); 		
			for(String sendTo :sendToConditions){
				ps.setString(++index,sendTo); 
		    }
			rs = ps.executeQuery();
			 
			while(rs.next()) {		
				String APPLICATION_GROUP_ID  = rs.getString("APPLICATION_GROUP_ID");
				NotificationInfoDataM notificationInfo = notificationNextDays.get(APPLICATION_GROUP_ID);
				if(InfBatchUtil.empty(notificationInfo)){
					notificationInfo =new NotificationInfoDataM();
					notificationNextDays.put(APPLICATION_GROUP_ID, notificationInfo);
				}
				String SALES_ID = rs.getString("SALES_ID");
				String RECOMMEND = rs.getString("RECOMMEND");
				String APPLICATION_TEMPLATE = rs.getString("APPLICATION_TEMPLATE");
				String SALE_CHANNEL = rs.getString("SALE_CHANNEL");
				String NOTIFICATION_TYPE =rs.getString("NOTIFICATION_TYPE");
				String SEND_TO =rs.getString("SEND_TO"); // NOTIFICATION_SMS_NEXT_DAY_DEFUALT_SEND_TO;
				String APPLICATION_TYPE =rs.getString("APPLICATION_TYPE");
				Integer MAX_LIFE_CYCLE =rs.getInt("MAX_LIFE_CYCLE");
				//String REASON_CODE =rs.getString("REASON_CODE");
				String CASH_TRANSFER_TYPE =rs.getString("CASH_TRANSFER_TYPE");
				String KEY_JOB = NOTIFICATION_TYPE+"_"+SEND_TO;
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				NotifyApplicationSegment notifyApplicationSegment = hAppReason.get(APPLICATION_GROUP_ID);
				if(InfBatchUtil.empty(notifyApplicationSegment)){
					notifyApplicationSegment  = NotificationUtil.notifyApplication(APPLICATION_GROUP_ID);
					hAppReason.put(APPLICATION_GROUP_ID, notifyApplicationSegment);
				}
				Reason reason = notifyApplicationSegment.findReasonApplicationGroup(APPLICATION_GROUP_ID);
				String REASON_CODE = reason.getReasonCode();
				if(!InfBatchUtil.empty(REASON_CODE)){
					HashMap<String,ArrayList<JobCodeDataM>> hJobCodeData  = notificationInfo.getJobCodes();
					if(InfBatchUtil.empty(hJobCodeData)){
						hJobCodeData = new HashMap<String,ArrayList<JobCodeDataM>>();
						notificationInfo.setJobCodes(hJobCodeData);
					}
					
					notificationInfo.setSaleId(SALES_ID);
					notificationInfo.setMaxLifeCycle(MAX_LIFE_CYCLE);
					notificationInfo.setSaleRecommend(RECOMMEND);
					notificationInfo.setApplicationTemplate(APPLICATION_TEMPLATE);
					notificationInfo.setSaleChanel(SALE_CHANNEL);	
					notificationInfo.setApplicationType(APPLICATION_TYPE);
					notificationInfo.setApplicationGroupId(APPLICATION_GROUP_ID);
					notificationInfo.setApplicationGroupNo(APPLICATION_GROUP_NO);
					notificationInfo.addSendTo(SEND_TO);

					if(!DEFUALT_DATA_TYPE_ALL.equalsIgnoreCase(CASH_TRANSFER_TYPE) && !InfBatchUtil.empty(CASH_TRANSFER_TYPE)){
						notificationInfo.addCashTransType(CASH_TRANSFER_TYPE);
					}
					String rejectReasonType = NotificationInfoDataM.DEFAULT_REJECT_REASON;
					if(NOTIFICATION_SEND_TO_TYPE_COMPLIANCE.equals(SEND_TO)){
						rejectReasonType = NotificationInfoDataM.COMPLIANCE_REJECT_REASON;
					}
					HashMap<String,ArrayList<String>> rejectReasonCodeHash = notificationInfo.getRejectReasonCodes();
					if(null==rejectReasonCodeHash){
						rejectReasonCodeHash = new HashMap<String, ArrayList<String>>();
						notificationInfo.setRejectReasonCodes(rejectReasonCodeHash);
					}
					ArrayList<String> rejectReasons = rejectReasonCodeHash.get(rejectReasonType);
					if(null==rejectReasons){
						rejectReasons = new ArrayList<String>();
						rejectReasonCodeHash.put(rejectReasonType, rejectReasons);
					}
					if(!Util.empty(REASON_CODE) &&!rejectReasons.contains(REASON_CODE)){
						rejectReasons.add(REASON_CODE);
					}
					ArrayList<JobCodeDataM> jobCodeList = hJobCodeData.get(KEY_JOB);
					if(InfBatchUtil.empty(jobCodeList)){
						jobCodeList = new ArrayList<JobCodeDataM>();
						hJobCodeData.put(KEY_JOB, jobCodeList);
					}
					JobCodeDataM jobCodeData = new JobCodeDataM();
					jobCodeData.setNotificationType(NOTIFICATION_TYPE);
					jobCodeData.setPriority(rs.getString("PRIORITY"));
					jobCodeData.setSendTo(rs.getString("SEND_TO"));
					jobCodeData.setJobCode(rs.getString("JOB_CODE"));
					jobCodeData.setOptional1(rs.getString("OPTIONAL1"));
					jobCodeData.setOptional2(rs.getString("OPTIONAL2"));
					jobCodeData.setOptional3(rs.getString("OPTIONAL3"));
					jobCodeData.setOptional4(rs.getString("OPTIONAL4"));
					jobCodeData.setOptional5(rs.getString("OPTIONAL5"));
					jobCodeData.setPattern(rs.getString("PATTERN"));
					jobCodeList.add(jobCodeData);
				}else{
					if(notificationNextDays.containsKey(APPLICATION_GROUP_ID)){
						notificationNextDays.remove(APPLICATION_GROUP_ID);
					}
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(APPLICATION_GROUP_ID);
						infBatchLog.setLifeCycle(MAX_LIFE_CYCLE);
						infBatchLog.setInterfaceCode(NOTIFICATION_SMS_NEXT_DAY_INF_CODE);
						infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND); 
						infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setLogMessage("Reason was not found");
					hNotSendLog.put(APPLICATION_GROUP_ID,infBatchLog);
				}
			}		
			if(!InfBatchUtil.empty(hNotSendLog)){
				insertInfBatchLog(hNotSendLog);
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
		return notificationNextDays;
	}
	
	@Override
	public ReasonApplication findReasonApplication(String applicationGroupId) throws InfBatchException {
		ReasonApplication reasonApplication = new ReasonApplication();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTINCT ");
			sql.append("     XRULES_POLICY_RULES.REASON REASON, ");
			sql.append("     XRULES_POLICY_RULES.RANK RANK ");
			sql.append(" FROM ORIG_APPLICATION ");
			sql.append(" JOIN XRULES_VERIFICATION_RESULT ON ORIG_APPLICATION.APPLICATION_RECORD_ID = XRULES_VERIFICATION_RESULT.REF_ID  ");
			sql.append(" AND XRULES_VERIFICATION_RESULT.VER_LEVEL = 'A' ");
			sql.append(" JOIN XRULES_POLICY_RULES ON XRULES_POLICY_RULES.VER_RESULT_ID = XRULES_VERIFICATION_RESULT.VER_RESULT_ID ");
			sql.append(" WHERE ORIG_APPLICATION.APPLICATION_GROUP_ID = ? AND XRULES_POLICY_RULES.VERIFIED_RESULT = ?");
			sql.append(" ORDER BY XRULES_POLICY_RULES.RANK ASC ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			ps.setString(2, SystemConstant.getConstant("POLICY_VER_RESULT_DECLINE"));
			rs = ps.executeQuery();			
			if(rs.next()){
				reasonApplication.setFoundReason(true);
				reasonApplication.setReasonCode(rs.getString("REASON"));
				try{
					reasonApplication.setReasonRank(rs.getInt("RANK"));
				}catch(Exception e){
					reasonApplication.setReasonRank(0);
				}
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return reasonApplication;
	}
	@Override
	public List<ReasonApplication> findReasonApplications(String applicationGroupId) throws InfBatchException {
		List<ReasonApplication> reasonApplications = new ArrayList<ReasonApplication>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTINCT ");
			sql.append("     XRULES_POLICY_RULES.REASON REASON, ");
			sql.append("     XRULES_POLICY_RULES.RANK RANK ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" JOIN ORIG_APPLICATION ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_APPLICATION.APPLICATION_GROUP_ID " +
					"AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ORIG_APPLICATION.LIFE_CYCLE ");
			sql.append(" JOIN XRULES_VERIFICATION_RESULT ON ORIG_APPLICATION.APPLICATION_RECORD_ID = XRULES_VERIFICATION_RESULT.REF_ID  ");
			sql.append(" AND XRULES_VERIFICATION_RESULT.VER_LEVEL = 'A' ");
			sql.append(" JOIN XRULES_POLICY_RULES ON XRULES_POLICY_RULES.VER_RESULT_ID = XRULES_VERIFICATION_RESULT.VER_RESULT_ID ");
			sql.append(" WHERE ORIG_APPLICATION.APPLICATION_GROUP_ID = ? AND XRULES_POLICY_RULES.VERIFIED_RESULT = ?");
			sql.append(" ORDER BY XRULES_POLICY_RULES.RANK ASC ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			ps.setString(2, SystemConstant.getConstant("POLICY_VER_RESULT_DECLINE"));
			rs = ps.executeQuery();			
			while(rs.next()){
				ReasonApplication reasonApplication = new ReasonApplication();
				reasonApplication.setFoundReason(true);
				reasonApplication.setReasonCode(rs.getString("REASON"));
				try{
					reasonApplication.setReasonRank(rs.getInt("RANK"));
				}catch(Exception e){
					reasonApplication.setReasonRank(0);
				}
				reasonApplications.add(reasonApplication);
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return reasonApplications;
	}
}
