package com.eaf.inf.batch.ulo.notification;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.ServiceResultDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationEODConfigDataM;
import com.eaf.inf.batch.ulo.notification.eod.sendto.inf.EODSendingInf;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;
import com.eaf.notify.task.NotifyTask;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;

public class NotificationEODProcess extends NotifyHelper {
	private static transient Logger logger = Logger.getLogger(NotificationEODProcess.class);
	String SEND_TO_TYPE_FIX = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX");
	String SEND_TO_TYPE_RECOMMEND=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_RECOMMEND");
	String SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String SEND_TO_TYPE_MANAGER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
	String NOTIFICATION_EMAIL_BRANCH_SUMMARY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_TEMPLATE_ID_BRANCH_SUMMARY");
	String NOTIFICATION_EOD_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_INTERFACE_CODE");
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest) throws Exception{
		TaskDataM task  = (TaskDataM)notifyRequest.getRequestObject();
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		TaskObjectDataM taskObject = task.getTaskObject();
		String notificationType = taskObject.getUniqueType();
		logger.debug("notificationType : "+notificationType);
		@SuppressWarnings("unchecked")
		ArrayList<NotificationEODDataM> notificationEods = (ArrayList<NotificationEODDataM>)taskObject.getObject();
		logger.debug("notificationEods : "+notificationEods);
		ArrayList<String> existingEmails = new ArrayList<String>();
		EODRecipientRequestDataM eodReceipientRequest = new EODRecipientRequestDataM();
			eodReceipientRequest.setNotificationEods(notificationEods);
			eodReceipientRequest.setEmails(existingEmails);
		String className = InfBatchProperty.getInfBatchConfig("EOD_PROCESS_SEND_TO_"+notificationType);
		logger.debug("className : "+className);
		EODSendingInf sendInf = (EODSendingInf)Class.forName(className).newInstance();
		sendInf.processGetRecipient(eodReceipientRequest,recipientType);
		return recipientType;
	}
	@SuppressWarnings("unchecked")
	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		NotifyTransactionDataM notifyTransaction = null;
		if(null!=getRecipient() && getRecipient().isValid()){
			notifyTransaction = new NotifyTransactionDataM();
			TaskDataM task  = (TaskDataM)getNotifyRequest().getRequestObject();
			TaskObjectDataM taskObject = task.getTaskObject();
			ArrayList<NotificationEODDataM> notificationEods = (ArrayList<NotificationEODDataM>)taskObject.getObject();
			NotificationEODConfigDataM notificationEODConfig = (NotificationEODConfigDataM)taskObject.getConfiguration();
			ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
			NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
			notifyTransactionResult.setTransactionObject(notificationEods);
			notifyTransactionResult.setConfigurationObject(notificationEODConfig);
			transactions.add(notifyTransactionResult);
			notifyTransaction.setTransactions(transactions);
		}
		return notifyTransaction;
	}
	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) {
		String NOTIFICATION_EOD_NOTIFY_TEMPLATE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_NOTIFY_TEMPLATE");
		ArrayList<NotifyTemplateDataM> notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		@SuppressWarnings("unchecked")
		ArrayList<NotificationEODDataM> notificationEods = (ArrayList<NotificationEODDataM>)transactionResult.getTransactionObject();
		List<String> notificationTypes = new ArrayList<String>();
		for(NotificationEODDataM notificationEod : notificationEods){
			String notificationType = notificationEod.getNotificationType();
//			logger.debug("notificationType : "+notificationType);
			if(!InfBatchUtil.empty(notificationType)){
				if(!notificationTypes.contains(notificationType)){
					notificationTypes.add(notificationType);
				}
			}
		}
		logger.debug("notificationTypes : "+notificationTypes);
		if(!InfBatchUtil.empty(notificationTypes)){
			for(String notificationType : notificationTypes){
				String configId = NOTIFICATION_EOD_NOTIFY_TEMPLATE+notificationType;
				String templateId = InfBatchProperty.getInfBatchConfig(configId);
				logger.debug("notificationType : "+notificationType);
				logger.debug("configId : "+configId);
				logger.debug("templateId : "+templateId);
				NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
					notifyTemplate.setTemplateId(templateId);
					notifyTemplate.setTemplateType(TemplateBuilderConstant.TemplateType.EMAIL);
					notifyTemplates.add(notifyTemplate);
			}
		}
		return notifyTemplates;
	}
	@Override
	public void preNotifyTransactionResult(
		NotifyTransactionResultDataM transactionResult) {
	}

	@Override
	public boolean requiredNotify() {
		return true;
	}
	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult)throws Exception{
		ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>(); 
		ArrayList<ServiceResultDataM> serviceResults = transactionResult.getServiceResults();
		for(ServiceResultDataM serviceResult : serviceResults){
			Object requestObject = serviceResult.getRequestObject();
			Object responseObject = serviceResult.getResponseObject();
			if(requestObject instanceof EmailRequest && responseObject instanceof EmailResponse){
				EmailRequest emailRequest = (EmailRequest)requestObject;
				EmailResponse emailResponse = (EmailResponse)responseObject;
				if(null!=emailRequest && null!=emailResponse){
					if(!InfBatchUtil.empty(emailRequest.getUniqueIds())){
						String interfaceStatus = getInterfaceStatusEmailResponse(emailResponse);
						for(NotifyTask uniqueId : emailRequest.getUniqueIds()){
							InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
								infBatchLog.setApplicationGroupId(uniqueId.getId());
								infBatchLog.setLifeCycle(uniqueId.getLifeCycle());
								infBatchLog.setInterfaceCode(NOTIFICATION_EOD_INTERFACE_CODE);
								infBatchLog.setInterfaceStatus(interfaceStatus);	//infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE); 
								infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
								infBatchLog.setSystem04(Arrays.toString(emailRequest.getTo()));
								infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
								infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
									StringBuilder sendTo = new StringBuilder();
									String comma = "";
									for(String to : emailRequest.getTo()){
										sendTo.append(comma).append(to);
										comma = ",";
									}
								infBatchLog.setSystem04(sendTo.toString());
							infBatchLogs.add(infBatchLog);
						}
					}
				}
			}else if(requestObject instanceof SMSRequest && responseObject instanceof SMSResponse){
				SMSRequest smsRequest = (SMSRequest)requestObject;
				SMSResponse smsResponse = (SMSResponse)responseObject;
				if(null!=smsRequest && null!=smsResponse){
					if(!InfBatchUtil.empty(smsRequest.getUniqueIds())){
						String interfaceStatus = getInterfaceStatusSmsResponse(smsResponse);
						for(NotifyTask uniqueId : smsRequest.getUniqueIds()){
							logger.debug("uniqueId : "+uniqueId);
							InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
								infBatchLog.setApplicationGroupId(smsRequest.getUniqueId().getId());
								infBatchLog.setLifeCycle(smsRequest.getUniqueId().getLifeCycle());
								infBatchLog.setInterfaceCode(NOTIFICATION_EOD_INTERFACE_CODE);
								infBatchLog.setInterfaceStatus(interfaceStatus); //infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE);  
								infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.SMS);
								infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
								infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
								StringBuilder sendTo = new StringBuilder();
								String comma = "";
								for(String mobileNo : smsRequest.getMobileNoElement()){
									sendTo.append(comma).append(mobileNo);
									comma = ",";
								}
								infBatchLog.setSystem04(sendTo.toString());
								infBatchLogs.add(infBatchLog);
						}
					}
					
				}
			}
		}
		if(null!=infBatchLogs){
			insertInfBatchLog(infBatchLogs);
		}
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
	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate)throws Exception{
		String EMAIL_ADDRESS_FROM = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_EMAIL_ADDRESS_FROM");
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
			templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
			templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
			templateBuilderRequest.setDbType(notifyTemplate.getDbType());
			templateBuilderRequest.setRequestObject(transactionResult.getTransactionObject());
			templateBuilderRequest.setConfigurationObject(transactionResult.getConfigurationObject());
			templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
		EmailRequest emailRequest = new EmailRequest();
		if(!InfBatchUtil.empty(templateBuilderResponse.getTemplateVariable())){
			emailRequest.setUniqueIds(templateBuilderResponse.getTemplateVariable().getUniqueIds());
			emailRequest.setTemplateId(templateBuilderResponse.getTemplateId());
			emailRequest.setTemplateName(templateBuilderResponse.getTemplateName());
			logger.debug("notifyTemplate.getTemplateId() : "+notifyTemplate.getTemplateId());
			logger.debug("getRecipient : "+getRecipient());
			if(InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_EOD_TEMPLATE_ID_EMAILSUM").equals(notifyTemplate.getTemplateId())){
				emailRequest.setTo(getRecipient().getEmailAddress(InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_EOD_NOTIFICATION_TYPE_E0006")));
				emailRequest.setCcTo(getRecipient().getCcEmailAddress(InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_EOD_NOTIFICATION_TYPE_E0006")));
			}else{
				emailRequest.setTo(getRecipient().getEmailAddress(InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_EOD_NOTIFICATION_TYPE_E0011")));
				emailRequest.setCcTo(getRecipient().getCcEmailAddress(InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_EOD_NOTIFICATION_TYPE_E0011")));
			}
//			logger.debug("emailRequest.getTo() : "+emailRequest.getTo());
//			logger.debug("emailRequest.getCcTo() : "+emailRequest.getCcTo());
			emailRequest.setFrom(EMAIL_ADDRESS_FROM);
			emailRequest.setSubject(templateBuilderResponse.getHeaderMsg());
			emailRequest.setContent(templateBuilderResponse.getBodyMsg());
			emailRequest.setSentDate(InfBatchProperty.getTimestamp());
			emailRequest.setAttachments(templateBuilderResponse.getAttachments());
		}
		return emailRequest;
	}
	@Override
	public SMSRequest getSMSRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate)throws Exception{
		SMSRequest  smsRequest = new SMSRequest();
		ArrayList<String>  mobileNoElement =  getRecipient().getMobileNos();	
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
			templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
			templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
			templateBuilderRequest.setDbType(notifyTemplate.getDbType());
			templateBuilderRequest.setRequestObject(transactionResult.getTransactionObject());
			templateBuilderRequest.setConfigurationObject(transactionResult.getConfigurationObject());
			templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
		if(!InfBatchUtil.empty(templateBuilderResponse.getTemplateVariable())){
			smsRequest.setUniqueIds(templateBuilderResponse.getTemplateVariable().getUniqueIds());
			smsRequest.setTemplateName(templateBuilderResponse.getTemplateName());
			smsRequest.setMobileNoElement(mobileNoElement);
			smsRequest.setMsg(templateBuilderResponse.getBodyMsg());
		}
		return smsRequest;
	}
	@Override
	public KmobileRequest getSMSKMobileRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		return null;
	}
	@Override
	public KmobileResponse sendSMSKMobile(KmobileRequest kMobileRequest)throws Exception {
		return null;
	}
	@Override
	public boolean validationNotifyTransactionResult(Object object) {
		if(null!=getRecipient() && getRecipient().isValid()){
			return true;
		}
		return false;
	}
}
