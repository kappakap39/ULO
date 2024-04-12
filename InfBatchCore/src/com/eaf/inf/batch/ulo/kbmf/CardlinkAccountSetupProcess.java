package com.eaf.inf.batch.ulo.kbmf;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;

public class CardlinkAccountSetupProcess extends NotifyHelper{
	private static transient Logger logger = Logger.getLogger(CardlinkAccountSetupProcess.class);
	String CARDLINK_ACCOUNT_SETUP_TEMPLATE_ID = InfBatchProperty.getInfBatchConfig("CARDLINK_ACCOUNT_SETUP_TEMPLATE_ID");
	//String CARD_NO_WARNING_EMAIL = InfBatchProperty.getGeneralParam("CARD_NO_WARNING_EMAIL");
	String CARDLINK_ACCOUNT_SETUP_WARNING_EMAIL = InfBatchProperty.getGeneralParam("CARDLINK_ACCOUNT_SETUP_EMAIL");
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
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		EmailRecipientDataM emailRecipient = new EmailRecipientDataM();
		emailRecipient.setEmail(CARDLINK_ACCOUNT_SETUP_WARNING_EMAIL);
		recipientType.put(emailRecipient);
		return recipientType;
	}

	@Override
	public boolean requiredNotify() {
		return true;
	}

	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		NotifyTransactionDataM notifyTransaction = new NotifyTransactionDataM();
		GenerateFileDataM generateFileData = (GenerateFileDataM)getNotifyRequest().getRequestObject();
		ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
		NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
		notifyTransactionResult.setTransactionObject(generateFileData);
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
		notifyTemplate.setTemplateId(CARDLINK_ACCOUNT_SETUP_TEMPLATE_ID);
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
