package com.eaf.inf.batch.ulo.capport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;

public class NotifyCapportProcess extends NotifyHelper {
	private static transient Logger logger = Logger.getLogger(NotifyCapportProcess.class);

	String CAPPORT_NOTIFY_TEMPLATE_ID = InfBatchProperty.getInfBatchConfig("CAPPORT_NOTIFICATION_TEMPLATE_ID");
	String CAPPORT_NOTIFICATION_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("CAPPORT_NOTIFICATION_INTERFACE_CODE");
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
	String INTERFACE_STATUS_ERROR = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_ERROR");
	
	//static String STACK_NOTIFICATION_TEMPLATE_ID = InfBatchProperty.getInfBatchConfig("ADJUST_CAPPORT_TRANSACTION_TEMPLATE_ID");
	//static String STACK_WARNING_PARAM_TYPE = InfBatchProperty.getInfBatchConfig("ADJUST_CAPPORT_TRANSACTION_WARNING_PARAM_TYPE");
	//static String STACK_TO_PARAM_TYPE = InfBatchProperty.getInfBatchConfig("ADJUST_CAPPORT_TRANSACTION_TO_PARAM_TYPE");
	//static String STACK_CC_PARAM_TYPE = InfBatchProperty.getInfBatchConfig("ADJUST_CAPPORT_TRANSACTION_CC_PARAM_TYPE");

	public RecipientTypeDataM getRecipient(String capportName) {
		logger.debug("getRecipient..");
		//String[] EMAIL_ACCOUNTING_TEAM = ServiceCache.getGeneralParam("EMAIL_CAPPORT_NOTIFY").split("\\,");
		
		//Get RECIPIENT_EMAILS from MF_CAPPORT
		String emailList = getRecipientEmails(capportName);
		String[] EMAIL_ACCOUNTING_TEAM = (!Util.empty(emailList)) ? emailList.split("\\,") : null;
		
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		if(null != EMAIL_ACCOUNTING_TEAM){
			for (String EMAIL_NAME : EMAIL_ACCOUNTING_TEAM) {
				EmailRecipientDataM emailRecipient = new EmailRecipientDataM();
					emailRecipient.setEmail(EMAIL_NAME);
				recipientType.put(emailRecipient);
			}
		}		
		return recipientType;
	}
	
	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) {
		logger.debug("getEmailRequest..");
		NotifyCapportRequest requestObject = (NotifyCapportRequest)transactionResult.getTransactionObject();
		String EMAIL_ADDRESS_FROM = InfBatchProperty.getGeneralParam("EMAIL_POLICY_PO");
		
		String capportName = requestObject.getCapportName();
		
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
			templateBuilderRequest.setTemplateId(CAPPORT_NOTIFY_TEMPLATE_ID);
			templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
			templateBuilderRequest.setDbType(notifyTemplate.getDbType());
			templateBuilderRequest.setRequestObject(transactionResult.getTransactionObject());
			templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
			
			logger.debug("getTemplateId.."+templateBuilderRequest.getTemplateId());
			logger.debug("getTemplateType.."+templateBuilderRequest.getTemplateType());
			logger.debug("getDbType.."+templateBuilderRequest.getDbType());
			logger.debug("getUniqueId.."+templateBuilderRequest.getUniqueId());
			
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
			
			logger.debug("build:"+templateBuilderResponse);
			
		EmailRequest emailRequest = new EmailRequest();
			emailRequest.setUniqueId(transactionResult.getUniqueId());
			emailRequest.setTemplateId(CAPPORT_NOTIFY_TEMPLATE_ID);
			emailRequest.setTemplateName(templateBuilderResponse.getTemplateName());
			emailRequest.setTo(getRecipient(capportName).getEmailAddress());
			emailRequest.setFrom(EMAIL_ADDRESS_FROM);
//			emailRequest.setAttachments(requestObject.getInstructionMemoFiles());
			emailRequest.setSubject(templateBuilderResponse.getHeaderMsg());
			emailRequest.setContent(templateBuilderResponse.getBodyMsg());
			emailRequest.setSentDate(InfBatchProperty.getTimestamp());
			
			
			logger.debug("getUniqueId.."+emailRequest.getUniqueId());
			logger.debug("getTemplateId.."+emailRequest.getTemplateId());
			logger.debug("getTemplateName.."+emailRequest.getTemplateName());
			logger.debug("getTo.."+emailRequest.getTo());
			logger.debug("getFrom.."+emailRequest.getFrom());
			logger.debug("getSubject.."+emailRequest.getSubject());
			logger.debug("getContent.."+emailRequest.getContent());
			logger.debug("getSentDate.."+emailRequest.getSentDate());
			
		return emailRequest;
	}





	/* (non-Javadoc)
	 * @see com.eaf.core.ulo.service.notify.inf.NotifyHelper#sendEmail(com.eaf.orig.ulo.email.model.EmailRequest)
	 */
	@Override
	public EmailResponse sendEmail(EmailRequest emailRequest) throws Exception {

		logger.debug("sendEmailTo : " + emailRequest.getEmailToString());
		logger.debug("Id : " + emailRequest.getUniqueId().getId());

		EmailResponse emailResponse = new EmailResponse();
		try {
			EmailClient emailClient = new EmailClient();
			emailResponse = emailClient.send(emailRequest);
			logger.debug("emailResponse : " + emailResponse.getStatusCode() + " - " + emailResponse.getStatusDesc());
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
			throw new Exception(e.getLocalizedMessage());
		}

		return emailResponse;
	}
	protected void insertInfBatchLog(ArrayList<InfBatchLogDataM> infBatchLogs)throws InfBatchException{
	   	 Connection conn = InfBatchObjectDAO.getConnection();	
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
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public boolean requiredNotify() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public KmobileRequest getSMSKMobileRequest(
			NotifyTransactionResultDataM transactionResult,
			NotifyTemplateDataM notifyTemplate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KmobileResponse sendSMSKMobile(KmobileRequest kMobileRequest)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preNotifyTransactionResult(
			NotifyTransactionResultDataM transactionResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(
			NotifyTransactionResultDataM transactionResult) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult)throws Exception{
		logger.debug("postNotifyTransactionResult..");
		// TODO Auto-generated method stub
	}
	
	public void postNotifyTransactionResult(String logMessage,int rs)throws Exception{
		logger.debug("postNotifyTransactionResult..");
		// TODO Auto-generated method stub
		
		ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>();
		InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
		infBatchLog.setInterfaceCode(CAPPORT_NOTIFICATION_INTERFACE_CODE);
		if(rs>0){
			infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE); 
		}else{
			infBatchLog.setInterfaceStatus(INTERFACE_STATUS_ERROR); 
		}

		infBatchLog.setLogMessage(logMessage);
		infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
		infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
		infBatchLogs.add(infBatchLog);
		insertInfBatchLog(infBatchLogs);
		
	}

	@Override
	public boolean validationNotifyTransactionResult(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecipientEmails(String CapportName)
	{
		String emailList = null;
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		OrigObjectDAO orig = new OrigObjectDAO();
		try
		{
			conn = orig.getConnection(OrigServiceLocator.OL_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT RECIPIENT_EMAILS FROM MF_CAP_PORT CP ");
			sql.append(" JOIN OL_IMPORT IMP ON IMP.IMPORT_ID = CP.IMPORT_ID AND IMP.STATUS = 'A' ");
			sql.append(" WHERE CP.CAP_PORT_NAME = ? ");
			
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, CapportName);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				emailList = rs.getString("RECIPIENT_EMAILS"); 
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		
		logger.debug("getRecipientEmails : emailList = " + emailList);
		return emailList;
	}

}
