package com.eaf.core.ulo.service.notify.inf;

import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.TransactionLogDataM;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;

public interface NotifyInf {
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest) throws Exception;
	public void init(NotifyRequest notifyRequest,RecipientTypeDataM recipient);
	public boolean requiredNotify();
	public NotifyRequest getNotifyRequest();
	public RecipientTypeDataM getRecipient();
	public NotifyTransactionDataM processNotifyTransaction();
	public EmailRequest getEmailRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception;
	public SMSRequest getSMSRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception;
	public KmobileRequest getSMSKMobileRequest(NotifyTransactionResultDataM transactionResult,NotifyTemplateDataM notifyTemplate) throws Exception;
	public EmailResponse sendEmail(EmailRequest emailRequest) throws Exception;
	public SMSResponse sendSMS(SMSRequest smsRequest) throws Exception;
	public KmobileResponse sendSMSKMobile(KmobileRequest kMobileRequest) throws Exception;
	public void preNotifyTransactionResult(NotifyTransactionResultDataM transactionResult);
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) throws Exception;
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) throws Exception;
	public boolean validationNotifyTransactionResult(Object object);
	public void notifyLog(Object object,NotifyResponse notifyResponse)throws Exception;
	public void init(NotifyRequest notifyRequest,RecipientTypeDataM recipient, List<TransactionLogDataM> transactionLogs);
	public List<TransactionLogDataM> getTransactionLog();
}
