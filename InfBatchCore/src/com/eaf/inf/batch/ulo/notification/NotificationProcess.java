
package com.eaf.inf.batch.ulo.notification;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.InfExportDataM;
import com.eaf.core.ulo.common.model.InfKmobileTextDataM;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.SMSKMobileRecipientDataM;
import com.eaf.core.ulo.service.notify.model.SMSRecipientDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.notification.condition.NotificationActionInf;
import com.eaf.inf.batch.ulo.notification.condition.NotificationControl;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.inf.NotificationTemplateInf;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateResponseDataM;
import com.eaf.inf.batch.ulo.notification.model.OrigContactLogDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.KmobileServiceProxy;
import com.eaf.service.module.model.KmobileRequestDataM;
import com.eaf.service.module.model.KmobileResponseDataM;

import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.ServiceResponse;

public class NotificationProcess extends NotifyHelper{
	private static transient Logger logger = Logger.getLogger(NotificationProcess.class);
	private static String PREFIX_CONFIG_NAME=InfBatchProperty.getInfBatchConfig("NOTIFICATION_PREFIX_CONFIG_NAME");
	String EMAIL_ADDRESS_FROM = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_EMAIL_ADDRESS_FROM");
	private static String BATCH_KMOBILE_MODULE_ID = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_MODULE_ID");
	private static String NOTIFICATION_SEND_KMOBILE_URL = SystemConfig.getProperty("NOTIFICATION_SEND_KMOBILE_URL");
	private static String NOTIFICATION_SEND_KMOBILE_USERNAME = SystemConfig.getProperty("NOTIFICATION_SEND_KMOBILE_USERNAME");
	private static String NOTIFICATION_SEND_KMOBILE_PASSPHRASE = SystemConfig.getProperty("NOTIFICATION_SEND_KMOBILE_PASSPHRASE");
	private static String NOTIFICATION_SEND_KMOBILE_PAGECODE = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_PAGE_CODE");
	private static String BATCH_KMOBILE_SEND_BY = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_SEND_BY");
	private static String BATCH_KMOBILE_SEND_FLAG = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_SEND_FLAG");
	private static String BATCH_KMOBILE_PAGE_CODE = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_PAGE_CODE");
	private static String BATCH_KMOBILE_IMAGE_TYPE_DEFAULT = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_IMAGE_TYPE_DEFAULT");
	private static String BATCH_KMOBILE_MESSAGE_NAME_DEFAULT = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_MESSAGE_NAME_DEFAULT");
	private static String BATCH_KMOBILE_CAMPAIGN_CODE_DEFAULT = InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_CAMPAIGN_CODE_DEFAULT");
	private NotificationInfoDataM notificationInfo;
	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest) throws Exception{
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		NotificationInfoRequestDataM notificationRequest = (NotificationInfoRequestDataM) notifyRequest.getRequestObject();
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();	
		notificationInfo  = notificationDAO.getNotificationInfo(notificationRequest);		
		if(!InfBatchUtil.empty(notificationInfo)){
			notificationInfo.setApplicationStatus(notificationRequest.getApplicationStatus());
			notificationInfo.setApplicationGroupId(notificationRequest.getApplicationGroupId());
			notificationInfo.setSendingTime(notificationRequest.getSendingTime());
			notificationInfo.setSaleType(notificationRequest.getSaleType());
			notificationInfo.setMaxLifeCycle(notificationRequest.getLifeCycle());
			notificationInfo.setApplyType(NotificationUtil.getApplicationType(notificationInfo.getApplicationGroupId(), notificationInfo.getApplicationType()
					, notificationInfo.getApplicationTemplate()));
			ArrayList<String> sendTos = notificationInfo.getSendTos();
			if(!InfBatchUtil.empty(sendTos)){
				for(String sendTo :sendTos){
					NotificationActionInf notification = NotificationControl.getNotification(PREFIX_CONFIG_NAME+sendTo);
					notification.notificationProcessCondition(notificationInfo,recipientType);
				}
			}
		}
		return recipientType;
	}

	@Override
	public NotifyTransactionDataM processNotifyTransaction(){
		NotifyTransactionDataM notifyTransaction = null;
		if(null!= getRecipient() && getRecipient().isValid()){
			notifyTransaction = new NotifyTransactionDataM();
			 ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
			 NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
			 notifyTransactionResult.setTransactionObject(getNotificationInfo());
			 notifyTransactionResult.setUniqueId(getNotificationInfo().getApplicationGroupId(),getNotificationInfo().getMaxLifeCycle());
			 transactions.add(notifyTransactionResult);
			 notifyTransaction.setTransactions(transactions);
		}
		return notifyTransaction;
	}
	
	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) throws Exception {
		String NOTIFICATION_SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
		ArrayList<NotifyTemplateDataM>  notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		RecipientTypeDataM recipientTypeDataM =getRecipient();
		NotificationInfoDataM notification = (NotificationInfoDataM)transactionResult.getTransactionObject();
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();	
		String APP_STATUS = notification.getApplicationStatus();
		String APPLICATION_GROUP_ID = notification.getApplicationGroupId();
		int LIFE_CYCLE = notification.getMaxLifeCycle();
		logger.debug("APP_STATUS : "+APP_STATUS);
		logger.debug("APPLICATION_GROUP_ID : "+APPLICATION_GROUP_ID);
		logger.debug("LIFE_CYCLE : "+LIFE_CYCLE);
		if(!InfBatchUtil.empty(recipientTypeDataM.getSmsRecipients())){
			ArrayList<SMSRecipientDataM> listSmsRecipients = recipientTypeDataM.getSmsRecipients();
			if(!InfBatchUtil.empty(listSmsRecipients)){
				HashMap<String, ArrayList<RecipientInfoDataM>> hTemplateApplicant = new HashMap<String, ArrayList<RecipientInfoDataM>>();
				for(SMSRecipientDataM sms : listSmsRecipients){
					ArrayList<RecipientInfoDataM>  recipientinfos= (ArrayList<RecipientInfoDataM>)sms.getRecipientObject();
					String  SEND_TO = sms.getRecipientType();
					logger.debug("Recipient SEND_TO : "+SEND_TO);
					if(null!=recipientinfos){
						for(RecipientInfoDataM recipientinfo :recipientinfos){
							NotificationTemplateRequestDataM templateRequest = new NotificationTemplateRequestDataM();
								templateRequest.setRequestObj(notification);
								templateRequest.setRecipientInfo(recipientinfo);
							String templateSendTo = NOTIFICATION_SEND_TO_TYPE_CUSTOMER.equals(SEND_TO) ? SEND_TO : "OTHER" ;
							String className = InfBatchProperty.getInfBatchConfig("NOTIFICATION_REALTIME_NOTIFY_TEMPLATE_"+templateSendTo);
							logger.debug("className : "+className);
							NotificationTemplateInf notifyTemplateInf = (NotificationTemplateInf)Class.forName(className).newInstance();
							NotificationTemplateResponseDataM templateResponse = notifyTemplateInf.processNotifyTemplate(templateRequest);
							ArrayList<String> listSMSTemplate = (ArrayList<String>)templateResponse.getResponseObj();
//								HashMap<String, ArrayList<String>>  hListTemplate = notificationDAO.getSMSTemplateNotification(notification,recipientinfo);
//								ArrayList<String> listSMSTemplate = hListTemplate.get(SEND_TO);
							if(null!=listSMSTemplate && listSMSTemplate.size()>0){
								for(String templateId : listSMSTemplate){
									sms.addTemplates(templateId);
									ArrayList<RecipientInfoDataM> mapRecipients =(ArrayList<RecipientInfoDataM>)hTemplateApplicant.get(templateId);
									if(InfBatchUtil.empty(mapRecipients)){
										mapRecipients = new ArrayList<RecipientInfoDataM>();
										hTemplateApplicant.put(templateId, mapRecipients);
									}
									if(!mapRecipients.contains(recipientinfo)){
										mapRecipients.add(recipientinfo);
									}
								} 
							}
						}
					}
				}
				logger.debug("SMS hTemplateApplicant>>"+hTemplateApplicant);
				if(null!=hTemplateApplicant && !hTemplateApplicant.isEmpty()){
					for(String tempId: new ArrayList<>(hTemplateApplicant.keySet())){
						ArrayList<RecipientInfoDataM> recipientinfoMap = hTemplateApplicant.get(tempId);
						notifyTemplates.add(getNotifyTemplate(tempId,TemplateBuilderConstant.TemplateType.SMS,recipientinfoMap));
					}
				}
			}
		}
		
		//Case KMobile Check app Status from App list
		if(!InfBatchUtil.empty(recipientTypeDataM.getSmsKMobileRecipients())){//listSmsRecipients
			ArrayList<SMSKMobileRecipientDataM> listKmobileRecipients = recipientTypeDataM.getSmsKMobileRecipients();
			if(!InfBatchUtil.empty(listKmobileRecipients)){
				HashMap<String, ArrayList<RecipientInfoDataM>> hTemplateApplicant = new HashMap<String, ArrayList<RecipientInfoDataM>>();
				for(SMSKMobileRecipientDataM kMobile: listKmobileRecipients){
					String SEND_TO =kMobile.getRecipientType();
					ArrayList<RecipientInfoDataM>  recipientinfos= (ArrayList<RecipientInfoDataM>)kMobile.getRecipientObject();
					logger.debug("KMobileRecipient SEND_TO >> "+SEND_TO);
					if(null != recipientinfos){
						for(RecipientInfoDataM recipientinfo :recipientinfos){
							HashMap<String, ArrayList<String>>  hListTemplate = notificationDAO.loadSMSTemplateNotification(notification,recipientinfo);
							ArrayList<String> listSMSTemplate = hListTemplate.get(SEND_TO);
							if(null!=listSMSTemplate && listSMSTemplate.size()>0){
								for(String templateId : listSMSTemplate){
									kMobile.addTemplates(templateId);
									ArrayList<RecipientInfoDataM> mapRecipients =(ArrayList<RecipientInfoDataM>)hTemplateApplicant.get(templateId);
									if(InfBatchUtil.empty(mapRecipients)){
										mapRecipients = new ArrayList<RecipientInfoDataM>();
										hTemplateApplicant.put(templateId, mapRecipients);
									}
									if(!mapRecipients.contains(recipientinfo)){
										mapRecipients.add(recipientinfo);
									}
								} 
							}
						}
					}
				}
				if(null!=hTemplateApplicant && !hTemplateApplicant.isEmpty()){
					for(String tempId: new ArrayList<>(hTemplateApplicant.keySet())){
						ArrayList<RecipientInfoDataM> recipientinfoMap = hTemplateApplicant.get(tempId);
						notifyTemplates.add(getNotifyTemplate(tempId,TemplateBuilderConstant.TemplateType.K_MOBILE,recipientinfoMap));
					}
				}
			}
		}
		
		if(!InfBatchUtil.empty(recipientTypeDataM.getEmailRecipients())){
			HashMap<String, ArrayList<String>>  hListTemplate   = notificationDAO.getEmailTemplateNotification(notification);
			if(!InfBatchUtil.empty(hListTemplate)){
				HashMap<String, ArrayList<RecipientInfoDataM>> hTemplateRecipientinfo = new HashMap<String,ArrayList<RecipientInfoDataM>>();					
				for(EmailRecipientDataM emailRecipient :recipientTypeDataM.getEmailRecipients()){
					String SEND_TO = emailRecipient.getRecipientType();
					ArrayList<String> templates = hListTemplate.get(SEND_TO);
					logger.debug("EmailRecipient SEND_TO  >> "+SEND_TO);
					if(!InfBatchUtil.empty(templates)){
						emailRecipient.setTemplates(templates);
						if(!InfBatchUtil.empty(emailRecipient.getRecipientObject())){
							ArrayList<RecipientInfoDataM> recipientInfos = (ArrayList<RecipientInfoDataM>)emailRecipient.getRecipientObject();
							if(null!=templates && templates.size()>0){
								for(String templateId : templates){
									if(hTemplateRecipientinfo.containsKey(templateId)){
										ArrayList<RecipientInfoDataM>  existingList = hTemplateRecipientinfo.get(templateId);
										if(!existingList.contains(recipientInfos)){
											existingList.addAll(recipientInfos);
										}										
									}else{
										hTemplateRecipientinfo.put(templateId, recipientInfos);
									}

								}
							}
						}
					}
				}
				if(!InfBatchUtil.empty(hTemplateRecipientinfo)){
					ArrayList<String> keys = new ArrayList<String>(hTemplateRecipientinfo.keySet());
					for(String emailTemplateId :keys ){
						logger.debug("emailTemplateId : "+emailTemplateId);
						notifyTemplates.add(getNotifyTemplate(emailTemplateId,TemplateBuilderConstant.TemplateType.EMAIL,hTemplateRecipientinfo.get(emailTemplateId)));
					}
				}		
			}		
		}
		return notifyTemplates;		
	}
	
	@Override
	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		String TEMPLATE_ID=notifyTemplate.getTemplateId();
		ArrayList<String> recipientTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_EMAIL_SEND_TO_"+TEMPLATE_ID);
		logger.debug("Template ID : "+TEMPLATE_ID);
		logger.debug("Send To : "+InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SEND_TO_"+TEMPLATE_ID));
		EmailRequest emailRequest = new EmailRequest();	
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
		templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
		templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
		templateBuilderRequest.setDbType(notifyTemplate.getDbType());
		templateBuilderRequest.setRequestObject(notifyTemplate.getTemplateObject());
		templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
		templateBuilderRequest.setLanguage(notifyTemplate.getNationality());
		
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
		emailRequest.setUniqueId(transactionResult.getUniqueId());
		emailRequest.setTemplateId(TEMPLATE_ID);
		emailRequest.setTemplateName(templateBuilderResponse.getTemplateName());
		emailRequest.setTo(getRecipient().getEmailAddress(recipientTypes));		
		emailRequest.setFrom(EMAIL_ADDRESS_FROM);	
		emailRequest.setSubject(templateBuilderResponse.getHeaderMsg());
		emailRequest.setContent(templateBuilderResponse.getBodyMsg());
		emailRequest.setSentDate(InfBatchProperty.getTimestamp());
		logger.debug("email : "+getRecipient().getEmailToString(recipientTypes));
		return emailRequest;
	}
	
	@Override
	public SMSRequest getSMSRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		logger.debug("getNotification SMSRequest..");
		String TEMPLATE_ID=notifyTemplate.getTemplateId();
		ArrayList<String> recipientTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+TEMPLATE_ID);
		logger.debug("template id : "+TEMPLATE_ID);
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
		logger.fatal("mobile>>>"+getRecipient().getMobileToString(recipientTypes));
		return smsRequest;
	}
	
	@Override
	public KmobileRequest getSMSKMobileRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		logger.debug("getNotification KMobile Request..");
		String TEMPLATE_ID=notifyTemplate.getTemplateId();
		ArrayList<String> recipientTypes = InfBatchProperty.getListInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+TEMPLATE_ID);
		String NOTIFICATION_SEND_TO_TYPE_CUSTOMER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
		logger.debug("template id : "+TEMPLATE_ID);
		logger.debug("recipientTypes : "+InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_SEND_TO_"+TEMPLATE_ID));
		KmobileRequest kMobileRequest = new KmobileRequest();
		for(String recipientType : recipientTypes){
			if(NOTIFICATION_SEND_TO_TYPE_CUSTOMER.equals(recipientType)){
				TemplateController templateController = new TemplateController();
				TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
				templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
				templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
				templateBuilderRequest.setDbType(notifyTemplate.getDbType());
				templateBuilderRequest.setRequestObject(notifyTemplate.getTemplateObject());
				templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
				templateBuilderRequest.setLanguage(notifyTemplate.getNationality());
				
				TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
				kMobileRequest.setRequestID(transactionResult.getUniqueId());
				kMobileRequest.setUsername(NOTIFICATION_SEND_KMOBILE_USERNAME);
				kMobileRequest.setPassphrase(NOTIFICATION_SEND_KMOBILE_PASSPHRASE);
				kMobileRequest.setPageCode(NOTIFICATION_SEND_KMOBILE_PAGECODE);
				kMobileRequest.setMobileNo(getRecipient().getKMobileNos(recipientTypes,TEMPLATE_ID).get(0));
				kMobileRequest.setTemplateName(templateBuilderResponse.getTemplateName());
				kMobileRequest.setMessageTH(templateBuilderResponse.getBodyMsgTh());
				kMobileRequest.setMessageEN(templateBuilderResponse.getBodyMsgEn());
				kMobileRequest.setImageType(BATCH_KMOBILE_IMAGE_TYPE_DEFAULT);
				kMobileRequest.setMessageName(BATCH_KMOBILE_MESSAGE_NAME_DEFAULT);
				kMobileRequest.setCampaignCode(BATCH_KMOBILE_CAMPAIGN_CODE_DEFAULT);				
				kMobileRequest.setSendBy(BATCH_KMOBILE_SEND_BY);
				kMobileRequest.setPageCode(BATCH_KMOBILE_PAGE_CODE);
				kMobileRequest.setSendFlag(BATCH_KMOBILE_SEND_FLAG);
				kMobileRequest.setAlertMessageTH(templateBuilderResponse.getAlertMessageTh());
				kMobileRequest.setAlertMessageEN(templateBuilderResponse.getAlertMessageEn());
			}
		}
		return kMobileRequest;
	}

	@Override
	public KmobileResponse sendSMSKMobile(KmobileRequest kMobileRequest) throws Exception {
		logger.debug("sendKMOBILE ..to.."+kMobileRequest.getMobileNo());
		logger.debug("kMobileRequest.getRequestID().."+kMobileRequest.getRequestID());
		KmobileResponse kMobileResponse = new KmobileResponse();
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		String contentMessage = null;
		boolean insertContactLog = false;
		InfKmobileTextDataM kmobileText = null;
		try{
			String applicationGroupId = notificationInfo.getApplicationGroupId();
			int lifeCycle = notificationInfo.getMaxLifeCycle();
			if(!Util.empty(notificationDAO.getKmobileRoleNameFromAppLog(applicationGroupId,lifeCycle))){
				logger.debug("For Kmobile Sending Realtime");
				kMobileResponse = sendKmobile(kMobileRequest);
				insertContactLog = true;
			}else{
				logger.debug("For Kmobile Batch");
				String mobileNo = kMobileRequest.getMobileNo().replaceAll("[\\D]", "");	// remove all character but number
				kMobileRequest.setMobileNo(mobileNo);
				boolean validMobileNo = validateMobileNoFormat(mobileNo);
				logger.debug("validMobileNo : "+validMobileNo);
				if(validMobileNo){
					String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
					kmobileText = new InfKmobileTextDataM();				
					kmobileText.setCustomer(kMobileRequest.getMobileNo());
					kmobileText.setMessageTH(kMobileRequest.getMessageTH());
					kmobileText.setMessageEN(kMobileRequest.getMessageEN());
					kmobileText.setSendBy(kMobileRequest.getSendBy());
					kmobileText.setPageCode(kMobileRequest.getPageCode());
					kmobileText.setSendFlag(kMobileRequest.getSendFlag());
					kmobileText.setCampaignName(kMobileRequest.getCampaignCode());
					kmobileText.setMessageName(kMobileRequest.getMessageName());
					kmobileText.setAlertMessageTH(kMobileRequest.getAlertMessageTH());
					kmobileText.setAlertMessageEN(kMobileRequest.getAlertMessageEN());
					
					contentMessage = buildMessageContent(kmobileText);
					InfExportDataM infExport = new InfExportDataM();
						infExport.setModuleID(BATCH_KMOBILE_MODULE_ID);
						infExport.setContent(contentMessage);
						infExport.setFileName(kMobileRequest.getRequestID().getId());
						infExport.setUpdateBy(SYSTEM_USER_ID);
						infExport.setDataDate(ApplicationDate.getTimestamp());
						infExport.setUpdateDate(ApplicationDate.getTimestamp());
					notificationDAO.insertInfExportKmobile(infExport);
				}
				//dao.insertInfExportKmobile(contentMessage, BATCH_KMOBILE_MODULE_ID);
			}	
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			kMobileResponse.setStatus(ServiceResponse.Status.SYSTEM_EXCEPTION);
			kMobileResponse.setMsgDesc(e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}	
		logger.debug(kMobileResponse);
		try{
			if(insertContactLog){
				OrigContactLogDataM  contactLog = new OrigContactLogDataM();
				contactLog.setApplicationGroupId(kMobileRequest.getRequestID().getId());
				contactLog.setLifeCycle(kMobileRequest.getRequestID().getLifeCycle());
				contactLog.setContactType(TemplateBuilderConstant.TemplateType.K_MOBILE);
				contactLog.setSendTo(kMobileRequest.getMobileNo());
				contactLog.setTemplateName(kMobileRequest.getTemplateName());
				if(!Util.empty(kMobileRequest.getMessageTH())){
					contactLog.setMessage(kMobileRequest.getMessageTH());
				}else{
					contactLog.setMessage(kMobileRequest.getMessageEN());
				}			
				contactLog.setSendBy(InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
				contactLog.setSendStatus(kMobileResponse.getStatus());
				notificationDAO.insertTableContactLog(contactLog);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}
		return kMobileResponse;
	}

	@Override
	public void preNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
	}

	@Override
	public boolean requiredNotify() {
		return true;
	}

	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
		
	}

	private NotifyTemplateDataM getNotifyTemplate(String templateId,String templateType,ArrayList<RecipientInfoDataM> recipientinfos){
		if(!InfBatchUtil.empty(templateId) && !InfBatchUtil.empty(templateType)){
			NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
			notifyTemplate.setTemplateId(templateId);
			notifyTemplate.setTemplateType(templateType);
			notifyTemplate.setTemplateObject(recipientinfos);
			if(!Util.empty(recipientinfos.get(0).getNationality())){
				notifyTemplate.setNationality(recipientinfos.get(0).getNationality());
			}
			if(ApplicationUtil.eApp(recipientinfos.get(0).getSourse())){
				notifyTemplate.setNationality(InfBatchProperty.getInfBatchConfig("LANGUAGE_TH"));
			}
	
			logger.debug("templateId >> "+templateId);
			logger.debug("templateType >> "+templateType);
			return notifyTemplate;
		}
		return null;
	}

	public NotificationInfoDataM getNotificationInfo() {
		if(null==notificationInfo){
			notificationInfo = new NotificationInfoDataM();
		}
		return notificationInfo;
	}
	
	private static KmobileRequestDataM getKmobileRequest(KmobileRequest kMobileRequest){
		KmobileRequestDataM kRequest = new KmobileRequestDataM();
		kRequest.setRequestID(kMobileRequest.getRequestID().getId());
		kRequest.setUsername(kMobileRequest.getUsername());
		kRequest.setPassphrase(kMobileRequest.getPassphrase());
		kRequest.setMobileNo(kMobileRequest.getMobileNo());
		kRequest.setPageCode(kMobileRequest.getPageCode());
		kRequest.setMessageEN(kMobileRequest.getMessageEN());
		kRequest.setMessageTH(kMobileRequest.getMessageTH());
		kRequest.setImageType(kMobileRequest.getImageType());
		kRequest.setAlertMessageTH(kMobileRequest.getAlertMessageTH());
		kRequest.setAlertMessageEN(kMobileRequest.getAlertMessageEN());
		//kRequest.setLifeCycle(kMobileRequest.getRequestID().getLifeCycle());
		return kRequest;
	}
	
	private static KmobileResponse sendKmobile(KmobileRequest kMobileRequest){
		KmobileResponse kMobileResponse = new KmobileResponse();
			kMobileResponse.setStatus(ServiceResponse.Status.SUCCESS);
		String applicationGroupNo = "";
		try{
			String applicationGroupId = kMobileRequest.getRequestID().getId();
			applicationGroupNo = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupNo(applicationGroupId);
			String mobileNo = kMobileRequest.getMobileNo().replaceAll("[\\D]", "");	// remove all character but number
			kMobileRequest.setMobileNo(mobileNo);
			boolean validMobileNo = validateMobileNoFormat(mobileNo);
			logger.debug("applicationGroupId >> "+applicationGroupId);
			logger.debug("validMobileNo : "+validMobileNo);
			if(validMobileNo){
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setUniqueId(applicationGroupId);
				serviceRequest.setRefId(applicationGroupNo);
				serviceRequest.setServiceId(KmobileServiceProxy.serviceId);
				serviceRequest.setEndpointUrl(NOTIFICATION_SEND_KMOBILE_URL);
				serviceRequest.setObjectData(getKmobileRequest(kMobileRequest));
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest);
				//RESPONSE
				KmobileResponseDataM responseObject = (KmobileResponseDataM)serviceResponse.getObjectData();
				if(null==responseObject){
					logger.debug("responseObject  is null>> ");
					return kMobileResponse;
				}
				logger.debug("responseObject >> "+responseObject);
				kMobileResponse.setStatus(responseObject.getStatus());
				ServiceErrorInfo errorInfo = serviceResponse.getErrorInfo();
				if(null != errorInfo){
					kMobileResponse.setMsgDesc(errorInfo.getErrorDesc());
				}
			}
			if(!validMobileNo){
				StringBuilder result = new StringBuilder(applicationGroupNo).append(" ").append("MobileNo format is wrong");
				
				kMobileResponse.setStatus(ServiceResponse.Status.WARNING);
				kMobileResponse.setMsgDesc(result.toString());
			}
		}catch(Exception e){
			StringBuilder result = new StringBuilder(applicationGroupNo).append(" ").append(e.getLocalizedMessage());
			
			kMobileResponse.setStatus((ServiceResponse.Status.SYSTEM_EXCEPTION));
			kMobileResponse.setMsgDesc(result.toString());
			logger.fatal("ERROR",e);
		}
		logger.debug(kMobileResponse);
		return kMobileResponse;
	}
	
	private static String buildMessageContent(InfKmobileTextDataM kMobile){
		StringBuilder str = new StringBuilder();
		if(!Util.empty(kMobile)){
			str.append(kmobileBuildContent(kMobile.getSendBy()));
			str.append(kmobileBuildContent(kMobile.getCustomer()));
			str.append(kmobileBuildContent(kMobile.getMessageTH()));
			str.append(kmobileBuildContent(kMobile.getMessageEN()));
			str.append(kmobileBuildContent(kMobile.getAlertMessageTH()));
			str.append(kmobileBuildContent(kMobile.getAlertMessageEN()));
			str.append(kmobileBuildContent(kMobile.getImageTH()));
			str.append(kmobileBuildContent(kMobile.getImageEN()));
			str.append(kmobileBuildContent(kMobile.getSendFlag()));
			str.append(kmobileBuildContent(kMobile.getSchedule()));
			str.append(kmobileBuildContent(kMobile.getPageCode()));
			str.append(kmobileBuildContent(kMobile.getMessageName()));
			str.append(kmobileBuildContent(kMobile.getCampaignName()));
			str.delete(str.length()-1, str.length());
		}
		return str.toString();
	}

	@Override
	public boolean validationNotifyTransactionResult(Object object){
		return true;
	}
	
	private static StringBuffer kmobileBuildContent(String content){
		StringBuffer returnContent = new StringBuffer("");
		if(!Util.empty(content)){
			returnContent.append("\"");
			returnContent.append(content);
			returnContent.append("\",");
		}else{
			returnContent.append("\"\",");
		}
		return returnContent;
		
	}
}
