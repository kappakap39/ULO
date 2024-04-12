package com.eaf.inf.batch.ulo.card.notification;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.card.notification.model.CardNotificationDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.OrigContactLogDataM;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;

public class CardNotificationProcess extends NotifyHelper{
	private static transient Logger logger = Logger.getLogger(CardNotificationProcess.class);
	String CARD_NOTIFICATION_TEMPLATE_ID = InfBatchProperty.getInfBatchConfig("CARD_NOTIFICATION_TEMPLATE_ID");
	String CARD_NO_WARNING_EMAIL = InfBatchProperty.getGeneralParam("CARD_NO_WARNING_EMAIL");
	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest) {
//		ArrayList<CardNotificationDataM> cardNotifications  = (ArrayList<CardNotificationDataM>)notifyRequest.getRequestObject();
//		RecipientTypeDataM recipientType = new RecipientTypeDataM();
//		logger.debug("CARD_NO_WARNING_EMAIL : "+CARD_NO_WARNING_EMAIL);
//		logger.debug("cardNotifications : "+cardNotifications);
//		if(!Util.empty(cardNotifications)){
//			EmailRecipientDataM emailRecipient = new EmailRecipientDataM();
// 			emailRecipient.setEmail(CARD_NO_WARNING_EMAIL);
// 			recipientType.put(emailRecipient);	
//		}
		
		String[] emails=CARD_NO_WARNING_EMAIL.split(",");
		
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		for(String email:emails){
			EmailRecipientDataM emailRecipient = new EmailRecipientDataM();
			emailRecipient.setEmail(email);
			recipientType.put(emailRecipient);
		}
		
		return recipientType;
	}
	
//	@Override
//	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
//		logger.debug("getEmailRequest..");
//		String EMAIL_ADDRESS_FROM = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_EMAIL_ADDRESS_FROM");
//		TemplateController templateController = new TemplateController();
//		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
//			templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
//			templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
//			templateBuilderRequest.setDbType(notifyTemplate.getDbType());
//			templateBuilderRequest.setRequestObject(transactionResult.getTransactionObject());
//			templateBuilderRequest.setConfigurationObject(transactionResult.getConfigurationObject());
//			templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
//		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
//		EmailRequest emailRequest = new EmailRequest();
//			emailRequest.setUniqueId(transactionResult.getUniqueId());
//			emailRequest.setTemplateId(templateBuilderResponse.getTemplateId());
//			emailRequest.setTemplateName(templateBuilderResponse.getTemplateName());
//			emailRequest.setTo(getRecipient().getEmailAddress());
//			emailRequest.setCcTo(getRecipient().getCcEmailAddress());
//			emailRequest.setFrom(EMAIL_ADDRESS_FROM);
//			emailRequest.setSubject(templateBuilderResponse.getHeaderMsg());
//			emailRequest.setContent(templateBuilderResponse.getBodyMsg());
//			emailRequest.setSentDate(InfBatchProperty.getTimestamp());
//			emailRequest.setAttachments(templateBuilderResponse.getAttachments());
//		logger.debug("emailRequest : "+emailRequest);
//		return emailRequest;
//	}
	
//	@Override
//	public EmailResponse sendEmail(EmailRequest emailRequest) throws Exception{
//		logger.debug("sendEmailTo : "+emailRequest.getEmailToString());
//		logger.debug("Id : "+emailRequest.getUniqueId().getId());
//		EmailResponse emailResponse = new EmailResponse();
//		try{
//			EmailClient emailClient = new EmailClient();
//			emailResponse = emailClient.send(emailRequest);		
//		}catch(Exception e){
//			logger.fatal("ERROR ",e);
//			throw new Exception(e.getLocalizedMessage());
//		}		
//		try{
//			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
//			OrigContactLogDataM  contactLog = new OrigContactLogDataM();
//				contactLog.setApplicationGroupId(emailRequest.getUniqueId().getId());
//				contactLog.setLifeCycle(emailRequest.getUniqueId().getLifeCycle());
//				contactLog.setContactType(TemplateBuilderConstant.TemplateType.EMAIL);
//				contactLog.setSendTo(emailRequest.getEmailToString());
//				contactLog.setCcTo(emailRequest.getCCemailToString());
//				contactLog.setMessage(emailRequest.getContent());
//				contactLog.setSubject(emailRequest.getSubject());
//				contactLog.setTemplateName(emailRequest.getTemplateName());
//				contactLog.setSendBy(emailRequest.getFrom());
//				contactLog.setSendStatus(emailResponse.getStatusCode());
//			notificationDAO.insertTableContactLog(contactLog);	
//		}catch(Exception e){
//			logger.fatal("ERROR ",e);
//			throw new Exception(e.getLocalizedMessage());
//		}		
//		return emailResponse;
//	}
	
	@Override
	public boolean requiredNotify() {
		return true;
	}

	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		NotifyTransactionDataM notifyTransaction = new NotifyTransactionDataM();
		ArrayList<CardNotificationDataM> listCardNotification = (ArrayList<CardNotificationDataM>)getNotifyRequest().getRequestObject();
		ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
		NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
		notifyTransactionResult.setTransactionObject(listCardNotification);
		transactions.add(notifyTransactionResult);
		notifyTransaction.setTransactions(transactions);
		return notifyTransaction;
	}

	@Override
	public void preNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
		
	}

	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) {
		ArrayList<NotifyTemplateDataM> notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
		notifyTemplate.setTemplateId(CARD_NOTIFICATION_TEMPLATE_ID);
		notifyTemplate.setTemplateType(TemplateBuilderConstant.TemplateType.EMAIL);
		notifyTemplates.add(notifyTemplate);
		return notifyTemplates;
	}

	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
	}

	@Override
	public KmobileRequest getSMSKMobileRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception {
		return null;
	}
	@Override
	public KmobileResponse sendSMSKMobile(KmobileRequest kMobileRequest) throws Exception {
		return null;
	}

	@Override
	public boolean validationNotifyTransactionResult(Object object) {
		RecipientTypeDataM recipient = getRecipient();
		if(recipient == null || (recipient != null && recipient.getEmailRecipients() == null || recipient.getEmailRecipients().size()==0)){
			return false;
		}
		return true;
	}
}
