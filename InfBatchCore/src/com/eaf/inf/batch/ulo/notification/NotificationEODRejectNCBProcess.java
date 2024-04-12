package com.eaf.inf.batch.ulo.notification;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.ServiceResultDataM;
import com.eaf.core.ulo.service.notify.model.TransactionLogDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.notify.task.NotifyTask;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;

public class NotificationEODRejectNCBProcess extends NotificationEODProcess {
	private static transient Logger logger = Logger.getLogger(NotificationEODRejectNCBProcess.class);
	String NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE");
	String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
	@Override
	public RecipientTypeDataM getRecipient(){
		return super.getRecipient();
	}
	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		return super.processNotifyTransaction();
	}
	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) {
		ArrayList<NotifyTemplateDataM> notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		String templateId = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_EOD_TEMPLATE_ID_EMAIL");
		logger.debug("templateId : "+templateId);
		NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
			notifyTemplate.setTemplateId(templateId);
			notifyTemplate.setTemplateType(TemplateBuilderConstant.TemplateType.EMAIL);
		notifyTemplates.add(notifyTemplate);
		return notifyTemplates;
	}
	@Override
	public void preNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
		
	}
	@Override
	public boolean requiredNotify() {
		return true;
	}
	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) throws Exception {
		try{
			ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>(); 
			ArrayList<ServiceResultDataM> serviceResults = transactionResult.getServiceResults();
			List<TransactionLogDataM> transactionLogs = getTransactionLog();
			for(ServiceResultDataM serviceResult : serviceResults){
				Object requestObject = serviceResult.getRequestObject();
				Object responseObject = serviceResult.getResponseObject();
				TransactionLogDataM transLog = new TransactionLogDataM();
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
								infBatchLog.setInterfaceCode(NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE);
								infBatchLog.setInterfaceStatus(interfaceStatus);	//infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE); 
								infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
								infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
								infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
									StringBuilder sendTo = new StringBuilder();
									String comma = "";
									for(String to : emailRequest.getTo()){
										sendTo.append(comma).append(to);
										comma = ",";
									}
								infBatchLog.setSystem04(sendTo.toString());
								infBatchLog.setLogMessage(emailResponse.getStatusDesc());
								infBatchLogs.add(infBatchLog);
								
								transLog.setApplicationgroupId(uniqueId.getId());
								transLog.setLifeCycle(uniqueId.getLifeCycle());
								transactionLogs.add(transLog);
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
								infBatchLog.setInterfaceCode(NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE);
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
								infBatchLog.setLogMessage(smsResponse.getStatusDesc());
								infBatchLogs.add(infBatchLog);
								
								transLog.setApplicationgroupId(uniqueId.getId());
								transLog.setLifeCycle(uniqueId.getLifeCycle());
								transactionLogs.add(transLog);
							}
						}
					}
				}
			}
			if(!InfBatchUtil.empty(infBatchLogs)){
				insertInfBatchLog(infBatchLogs);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
	}
	@Override
	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
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
			emailRequest.setTo(getRecipient().getEmailAddress(InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_EOD_NOTIFICATION_TYPE_E0011")));
			emailRequest.setCcTo(getRecipient().getCcEmailAddress(InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_EOD_NOTIFICATION_TYPE_E0011")));
			emailRequest.setFrom(EMAIL_ADDRESS_FROM);
			emailRequest.setSubject(templateBuilderResponse.getHeaderMsg());
			emailRequest.setContent(templateBuilderResponse.getBodyMsg());
			emailRequest.setSentDate(InfBatchProperty.getTimestamp());
			emailRequest.setAttachments(templateBuilderResponse.getAttachments());
		}
		return emailRequest;
	}
	@Override
	public SMSRequest getSMSRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		return super.getSMSRequest(transactionResult, notifyTemplate);
	}
	@Override
	public boolean validationNotifyTransactionResult(Object object) {
		return super.validationNotifyTransactionResult(object);
	}
	
	@Override
	public void notifyLog(Object object, NotifyResponse notifyResponse)
			throws Exception {
		try{
			logger.debug("notifyResponse : "+notifyResponse);
			if(!InfBatchConstant.ResultCode.SUCCESS.equals(notifyResponse.getStatusCode())){
				NotifyRequest notifyRequest = (NotifyRequest)object;
				ArrayList<NotificationEODDataM> notificationEODRejectncbs = getNotifyObjectRequest(notifyRequest);
				List<TransactionLogDataM> transactionLogs = getTransactionLog();
				for(NotificationEODDataM notificationEODRejectncb : notificationEODRejectncbs){
					if(!InfBatchUtil.empty(notificationEODRejectncb)){
						boolean isInsert = true;
						for(TransactionLogDataM transLog : transactionLogs){
							String applicationGroupId = transLog.getApplicationgroupId();
							int lifeCycle = transLog.getLifeCycle();
							if(notificationEODRejectncb.getApplicationGroupId().equals(applicationGroupId) && notificationEODRejectncb.getLifeCycle() == lifeCycle){
								isInsert = false;
								break;
							}
						}
						
						if(isInsert){
							String logMessage = notifyResponse.getStatusDesc();
							InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
								infBatchLog.setApplicationGroupId(notificationEODRejectncb.getApplicationGroupId());
								infBatchLog.setLifeCycle(notificationEODRejectncb.getLifeCycle());
								infBatchLog.setInterfaceCode(NOTIFICATION_EOD_REJECT_NCB_INTERFACE_CODE);
								infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND);  
								infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
								infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
								infBatchLog.setLogMessage(logMessage);
							InfDAO dao = InfFactory.getInfDAO();
							dao.insertInfBatchLog(infBatchLog);
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
	}
	
	private ArrayList<NotificationEODDataM> getNotifyObjectRequest(NotifyRequest notifyReq){		
		TaskDataM task= (TaskDataM) notifyReq.getRequestObject();
		TaskObjectDataM taskObject = task.getTaskObject();
		ArrayList<NotificationEODDataM> notificationEODRejectncbs = (ArrayList<NotificationEODDataM>)taskObject.getObject();
		if(null==notificationEODRejectncbs){
			notificationEODRejectncbs = new ArrayList<NotificationEODDataM>();
		}
		return notificationEODRejectncbs;		
	}
}
