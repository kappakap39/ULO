package com.eaf.inf.batch.ulo.notification.sms.nextday;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.SMSRecipientDataM;
import com.eaf.core.ulo.service.notify.model.ServiceResultDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.notification.condition.NotificationActionInf;
import com.eaf.inf.batch.ulo.notification.condition.NotificationControl;
import com.eaf.inf.batch.ulo.notification.inf.NotificationTemplateInf;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateResponseDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;

public class NotificationSMSNextDayProcess extends NotifyHelper{
	private static transient Logger logger = Logger.getLogger(NotificationSMSNextDayProcess.class);
	String NOTIFICATION_SMS_NEXT_DAY_INF_CODE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_INF_CODE");
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
	String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
	String NOTIFICATION_SEND_TO_TYPE_RECOMMEND=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_RECOMMEND");
	String NOTIFICATION_SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest)throws Exception {
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		NotificationInfoDataM notificationSMSNextDay = getNotifyObjectRequest(notifyRequest);
		if(!InfBatchUtil.empty(notificationSMSNextDay)  &&  !InfBatchUtil.empty(notificationSMSNextDay.getSendTos())){
			notificationSMSNextDay.setApplyType(NotificationUtil.getApplicationType(notificationSMSNextDay.getApplicationGroupId(),
					notificationSMSNextDay.getApplicationType(), notificationSMSNextDay.getApplicationTemplate()));
			for(String sendTo :notificationSMSNextDay.getSendTos()){
				NotificationActionInf notification = NotificationControl.getNotification("NOTIFICATION_SMS_NEXT_DAY_TO_"+sendTo);
				if(!InfBatchUtil.empty(notification)){
					notification.notificationProcessCondition(notificationSMSNextDay,recipientType);
				}				
			}
		}
		return recipientType;
	}
	
	@Override
	public boolean validationNotifyTransactionResult(Object object) {
		NotificationInfoDataM notificationSMSNextDay = getNotifyObjectRequest(getNotifyRequest());	
		if(!InfBatchUtil.empty(getRecipient()) && getRecipient().isValid() && !InfBatchUtil.empty(notificationSMSNextDay)){
			return true;
		}
		return false;
	}
	
	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		NotifyTransactionDataM notifyTransaction = new NotifyTransactionDataM();
		NotificationInfoDataM notificationSMSNextDay = getNotifyObjectRequest(getNotifyRequest());	
		if(!InfBatchUtil.empty(getRecipient()) && getRecipient().isValid() && !InfBatchUtil.empty(notificationSMSNextDay)){
			 ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
			 NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
			 notifyTransactionResult.setTransactionObject(notificationSMSNextDay);
			 notifyTransactionResult.setUniqueId(notificationSMSNextDay.getApplicationGroupId());
			 transactions.add(notifyTransactionResult);
			 notifyTransaction.setTransactions(transactions);
		}
		return notifyTransaction;
	}
	
	
	@Override
	public SMSRequest getSMSRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		logger.debug("getNotification SMSRequest..");
		String TEMPLATE_ID = notifyTemplate.getTemplateId();
		ArrayList<String> recipientTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+TEMPLATE_ID);
		logger.debug("TEMPLATE_ID : "+TEMPLATE_ID);
		logger.debug("recipientTypes : "+InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+TEMPLATE_ID));		
		SMSRequest  smsRequest = new SMSRequest();
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
		templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
		templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
		templateBuilderRequest.setDbType(notifyTemplate.getDbType());
		templateBuilderRequest.setRequestObject(notifyTemplate.getTemplateObject());
		templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
		templateBuilderRequest.setLanguage(notifyTemplate.getNationality());
		
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
		smsRequest.setUniqueId(transactionResult.getUniqueId());
		smsRequest.setTemplateName(templateBuilderResponse.getTemplateName());
		smsRequest.setMobileNoElement(getRecipient().getMobileNos(recipientTypes,TEMPLATE_ID));
		smsRequest.setMsg(templateBuilderResponse.getBodyMsg());
		if(FormatUtil.TH.toUpperCase().equals(notifyTemplate.getNationality().toUpperCase())){
			smsRequest.setSmsLanguage(FormatUtil.TH.toUpperCase());
		}else{
			if(!Util.empty(notifyTemplate.getNationality())){
				smsRequest.setSmsLanguage(FormatUtil.EN.toUpperCase());
			}else{
				smsRequest.setSmsLanguage(FormatUtil.TH.toUpperCase());
			}
		}
		return smsRequest;
	}
	@Override
	public boolean requiredNotify() {
		return true;
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
	public void preNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {		
	}

	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) throws Exception {
		ArrayList<NotifyTemplateDataM>  notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		RecipientTypeDataM recipientType =getRecipient();
		NotificationInfoDataM notification = (NotificationInfoDataM)transactionResult.getTransactionObject();
//		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();	
//		String APP_STATUS = notification.getApplicationStatus();
		String APPLICATION_GROUP_ID = notification.getApplicationGroupId();
//		logger.debug("APP_STATUS >> "+APP_STATUS);
		logger.debug("APPLICATION_GROUP_ID >> "+APPLICATION_GROUP_ID);
		if(!InfBatchUtil.empty(recipientType.getSmsRecipients())){
			ArrayList<SMSRecipientDataM> smsRecipients = recipientType.getSmsRecipients();
			if(!InfBatchUtil.empty(smsRecipients)){
				HashMap<String,ArrayList<RecipientInfoDataM>> templateApplicantHash = new HashMap<String,ArrayList<RecipientInfoDataM>>();
				for(SMSRecipientDataM smsRecipient : smsRecipients){
					ArrayList<RecipientInfoDataM>  recipientinfos = (ArrayList<RecipientInfoDataM>)smsRecipient.getRecipientObject();
					String  SEND_TO = smsRecipient.getRecipientType();
					logger.debug("Recipient SEND_TO : "+SEND_TO);
					if(null!=recipientinfos){
						for(RecipientInfoDataM recipientInfo :recipientinfos){
							//NOTIFICATION_SMS_NEXT_DAY_NOTIFY_TEMPLATE_
							NotificationTemplateRequestDataM templateRequest = new NotificationTemplateRequestDataM();
								templateRequest.setRequestObj(notification);
								templateRequest.setRecipientInfo(recipientInfo);
							String className = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_NOTIFY_TEMPLATE_"+SEND_TO);
							logger.debug("className : "+className);
							NotificationTemplateInf notifyTemplateInf = (NotificationTemplateInf)Class.forName(className).newInstance();
							NotificationTemplateResponseDataM templateResponse = notifyTemplateInf.processNotifyTemplate(templateRequest);
							ArrayList<String> smsTemplates = (ArrayList<String>)templateResponse.getResponseObj();
//							HashMap<String, ArrayList<String>> smsTemplateHash = notificationDAO.getSMSTemplateNotification(notification,recipientInfo);
//							ArrayList<String> smsTemplates = new ArrayList<String>(); 
//							if(NOTIFICATION_SEND_TO_TYPE_RECOMMEND.equals(SEND_TO)){
//								smsTemplates = smsTemplateHash.get(NOTIFICATION_SEND_TO_TYPE_SELLER);
//							}else{
//								smsTemplates = smsTemplateHash.get(SEND_TO);
//							}
							if(null!=smsTemplates && smsTemplates.size()>0){
								for(String templateId : smsTemplates){
									smsRecipient.addTemplates(templateId);
									ArrayList<RecipientInfoDataM> recipientInfos =(ArrayList<RecipientInfoDataM>)templateApplicantHash.get(templateId);
									if(InfBatchUtil.empty(recipientInfos)){
										recipientInfos = new ArrayList<RecipientInfoDataM>();
										templateApplicantHash.put(templateId, recipientInfos);
									}
									if(!recipientInfos.contains(recipientInfo)){
										recipientInfos.add(recipientInfo);
									}
								} 
							}
						}
					}
				}
				if(null!=templateApplicantHash && !templateApplicantHash.isEmpty()){
					for(String templateApplicant: new ArrayList<>(templateApplicantHash.keySet())){
						ArrayList<RecipientInfoDataM> recipientInfos = templateApplicantHash.get(templateApplicant);
						notifyTemplates.add(getNotifyTemplate(templateApplicant,TemplateBuilderConstant.TemplateType.SMS,recipientInfos));
					}
				}
			}
		}
		return notifyTemplates;	
	}
	private NotifyTemplateDataM getNotifyTemplate(String templateId,String templateType,ArrayList<RecipientInfoDataM> recipientinfos){
		NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
		if(!InfBatchUtil.empty(templateId) && !InfBatchUtil.empty(templateType)){
			notifyTemplate.setTemplateId(templateId);
			notifyTemplate.setTemplateType(templateType);
			notifyTemplate.setTemplateObject(recipientinfos);
			if(!Util.empty(recipientinfos)&&recipientinfos.size()>0&&!Util.empty(recipientinfos.get(0).getNationality())){
				notifyTemplate.setNationality(recipientinfos.get(0).getNationality());
			}else{
				notifyTemplate.setNationality(FormatUtil.TH.toUpperCase());
			}
			logger.debug("templateId >> "+templateId);
			logger.debug("templateType >> "+templateType);
		}
		return notifyTemplate;
	}
	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) throws Exception {
		NotificationInfoDataM notificationInfo = (NotificationInfoDataM)transactionResult.getTransactionObject();
		ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>(); 
		ArrayList<ServiceResultDataM> serviceResults = transactionResult.getServiceResults();
		for(ServiceResultDataM serviceResult : serviceResults){
			Object requestObject = serviceResult.getRequestObject();
			Object responseObject = serviceResult.getResponseObject();
			if(requestObject instanceof EmailRequest && responseObject instanceof EmailResponse){
				EmailRequest emailRequest = (EmailRequest)requestObject;
				EmailResponse emailResponse = (EmailResponse)responseObject;
				if(null!=emailRequest && null!=emailResponse){
					int maxLifeCycle = (notificationInfo.getApplicationGroupId().equals(emailRequest.getUniqueId().getId()))?notificationInfo.getMaxLifeCycle():1;
					String interfaceStatus = getInterfaceStatusEmailResponse(emailResponse);
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(emailRequest.getUniqueId().getId());
						infBatchLog.setLifeCycle(emailRequest.getUniqueId().getLifeCycle());
						infBatchLog.setInterfaceCode(NOTIFICATION_SMS_NEXT_DAY_INF_CODE);
						infBatchLog.setInterfaceStatus(interfaceStatus); //infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE); 
						infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.EMAIL);
						infBatchLog.setSystem04(emailRequest.getEmailToString());
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						if(maxLifeCycle!=0){
							infBatchLog.setLifeCycle(maxLifeCycle);
						}
						infBatchLog.setLogMessage(emailResponse.getStatusDesc());
					infBatchLogs.add(infBatchLog);
				}
			}else if(requestObject instanceof SMSRequest && responseObject instanceof SMSResponse){
				SMSRequest smsRequest = (SMSRequest)requestObject;
				SMSResponse smsResponse = (SMSResponse)responseObject;
				if(null!=smsRequest && null!=smsResponse){
					int maxLifeCycle = (notificationInfo.getApplicationGroupId().equals(smsRequest.getUniqueId().getId()))?notificationInfo.getMaxLifeCycle():1;
					String interfaceStatus = getInterfaceStatusSmsResponse(smsResponse);
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(smsRequest.getUniqueId().getId());
						infBatchLog.setLifeCycle(smsRequest.getUniqueId().getLifeCycle());
						infBatchLog.setInterfaceCode(NOTIFICATION_SMS_NEXT_DAY_INF_CODE);
						infBatchLog.setInterfaceStatus(interfaceStatus); //infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE);  
						infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.SMS);
						infBatchLog.setSystem04(smsRequest.getMobileToString());
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						if(maxLifeCycle!=0){
							infBatchLog.setLifeCycle(maxLifeCycle);
						}
						infBatchLog.setLogMessage(smsResponse.getStatusDesc());
					infBatchLogs.add(infBatchLog);
				}
			}else if(requestObject instanceof KmobileRequest && responseObject instanceof KmobileResponse){
				KmobileRequest kmobileRequest = (KmobileRequest)requestObject;
				KmobileResponse kmobileResponse = (KmobileResponse)responseObject;
				if(null!=kmobileRequest && null!=kmobileResponse){
					int maxLifeCycle = (notificationInfo.getApplicationGroupId().equals(kmobileRequest.getRequestID().getId()))?notificationInfo.getMaxLifeCycle():1;
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(kmobileRequest.getRequestID().getId());
						infBatchLog.setLifeCycle(kmobileRequest.getRequestID().getLifeCycle());
						infBatchLog.setInterfaceCode(NOTIFICATION_SMS_NEXT_DAY_INF_CODE);
						infBatchLog.setInterfaceStatus(INTERFACE_STATUS_COMPLETE);  
						infBatchLog.setSystem01(TemplateBuilderConstant.TemplateType.K_MOBILE);
						infBatchLog.setSystem04(kmobileRequest.getMobileNo());
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						if(maxLifeCycle!=0){
							infBatchLog.setLifeCycle(maxLifeCycle);
						}
					infBatchLogs.add(infBatchLog);
				}
			}
		}
		if(null!=infBatchLogs){
			createINF_BATCH_LOG(infBatchLogs);
		}
	}
	private void createINF_BATCH_LOG(ArrayList<InfBatchLogDataM> InfBatchLogList) throws Exception{
	   	 Connection conn = InfBatchObjectDAO.getConnection();	
	   	 try {
	   		 InfDAO dao = InfFactory.getInfDAO();
	   		 conn.setAutoCommit(false);
	   		 if(!InfBatchUtil.empty(InfBatchLogList)){
	   			 for(InfBatchLogDataM infBatchLog : InfBatchLogList){
	   				dao.insertInfBatchLog(infBatchLog, conn);
	   			 }
	   		 }
	   		 conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			conn.rollback();		
			throw new Exception(e);
		}finally{
			InfBatchObjectDAO.closeConnection(conn);
		}
	}
	private NotificationInfoDataM getNotifyObjectRequest(NotifyRequest notifyReq){		
		TaskDataM task= (TaskDataM) notifyReq.getRequestObject();
		TaskObjectDataM taskObject = task.getTaskObject();
		NotificationInfoDataM notificationSMSNextDay = (NotificationInfoDataM)taskObject.getObject();
		if(null==notificationSMSNextDay){
			notificationSMSNextDay = new NotificationInfoDataM();
		}
		return notificationSMSNextDay;		
	}
	@Override
	public void notifyLog(Object object,NotifyResponse notifyResponse)throws Exception{
		try{
			logger.debug("notifyResponse : "+notifyResponse);
			if(!InfBatchConstant.ResultCode.SUCCESS.equals(notifyResponse.getStatusCode())){
				NotifyRequest notifyRequest = (NotifyRequest)object;
				NotificationInfoDataM notificationSMSNextDay = getNotifyObjectRequest(notifyRequest);
				if(!InfBatchUtil.empty(notificationSMSNextDay)){
					String logMessage = notifyResponse.getStatusDesc();
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(notificationSMSNextDay.getApplicationGroupId());
						infBatchLog.setLifeCycle(notificationSMSNextDay.getMaxLifeCycle());
						infBatchLog.setInterfaceCode(NOTIFICATION_SMS_NEXT_DAY_INF_CODE);
						infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND);  
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setLogMessage(logMessage);
					InfDAO dao = InfFactory.getInfDAO();
					dao.insertInfBatchLog(infBatchLog);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
	}
}
