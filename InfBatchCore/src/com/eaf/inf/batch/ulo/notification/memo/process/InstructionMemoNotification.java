package com.eaf.inf.batch.ulo.notification.memo.process;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.inf.batch.ulo.notification.memo.model.InstructionMemoRequest;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;

public class InstructionMemoNotification extends NotifyHelper{
	private static transient Logger logger = Logger.getLogger(InstructionMemoNotification.class);
	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest) {
		String[] EMAIL_ACCOUNTING_TEAM = ServiceCache.getGeneralParam("EMAIL_ACCOUNTING_TEAM").split("\\,");
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
	@Override
	public boolean requiredNotify() {
		return true;
	}
	@Override
	public void preNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
		
	}
	@Override
	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) {
		logger.debug("getEmailRequest..");
		InstructionMemoRequest requestObject = (InstructionMemoRequest)transactionResult.getTransactionObject();
		String EMAIL_ADDRESS_FROM = InfBatchProperty.getGeneralParam("EMAIL_POLICY_PO");
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
			templateBuilderRequest.setTemplateId(notifyTemplate.getTemplateId());
			templateBuilderRequest.setTemplateType(notifyTemplate.getTemplateType());
			templateBuilderRequest.setDbType(notifyTemplate.getDbType());
			templateBuilderRequest.setRequestObject(transactionResult.getTransactionObject());
			templateBuilderRequest.setUniqueId(transactionResult.getUniqueId());
		TemplateBuilderResponse templateBuilderResponse = templateController.build(templateBuilderRequest);
		EmailRequest emailRequest = new EmailRequest();
			emailRequest.setUniqueId(transactionResult.getUniqueId());
			emailRequest.setTemplateId(templateBuilderResponse.getTemplateId());
			emailRequest.setTemplateName(templateBuilderResponse.getTemplateName());
			emailRequest.setTo(getRecipient().getEmailAddress());
			emailRequest.setFrom(EMAIL_ADDRESS_FROM);
			emailRequest.setAttachments(requestObject.getInstructionMemoFiles());
			emailRequest.setSubject(templateBuilderResponse.getHeaderMsg());
			emailRequest.setContent(templateBuilderResponse.getBodyMsg());
			emailRequest.setSentDate(InfBatchProperty.getTimestamp());
		return emailRequest;
	}
	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) {
		ArrayList<NotifyTemplateDataM> notifyTemplates = new ArrayList<>();
		NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
		notifyTemplate.setTemplateId(InfBatchProperty.getInfBatchConfig("NOTIFY_INSTRUCTION_EMAIL_TEMPLATE"));
		notifyTemplate.setTemplateType(TemplateBuilderConstant.TemplateType.EMAIL);
		notifyTemplates.add(notifyTemplate);
		return notifyTemplates;
	}
	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
		
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
	public boolean validationNotifyTransactionResult(Object object) {
		return true;
	}
	
}
