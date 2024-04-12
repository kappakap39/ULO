package com.eaf.inf.batch.ulo.card.stack.notification;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackInfoM;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackRequestDataM;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;

public class InfBatchCardStackProcess extends NotifyHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchCardStackProcess.class);

	static String STACK_NOTIFICATION_TEMPLATE_ID = InfBatchProperty.getInfBatchConfig("STACK_NOTIFICATION_TEMPLATE_ID");
	static String STACK_WARNING_PARAM_TYPE = InfBatchProperty.getInfBatchConfig("STACK_WARNING_PARAM_TYPE");
	static String STACK_TO_PARAM_TYPE = InfBatchProperty.getInfBatchConfig("STACK_TO_PARAM_TYPE");
	static String STACK_CC_PARAM_TYPE = InfBatchProperty.getInfBatchConfig("STACK_CC_PARAM_TYPE");

	private void getRecipient(RecipientTypeDataM recipientType, String paramType, int numOfParamType) {
		EmailRecipientDataM emailRecipientDataM = new EmailRecipientDataM();
		if (validateTotalCardType(paramType, numOfParamType)) {
			if (!InfBatchUtil.empty(getStackParamValueString(STACK_TO_PARAM_TYPE, paramType))) {
				emailRecipientDataM.setEmail(getStackParamValueString(STACK_TO_PARAM_TYPE, paramType));
				emailRecipientDataM.setCcEmail(InfBatchUtil.empty(getStackParamValueString(STACK_CC_PARAM_TYPE, paramType)) ? ""
						: getStackParamValueString(STACK_CC_PARAM_TYPE, paramType));
				recipientType.getEmailRecipients().add(emailRecipientDataM);
			}

		}

	}

	private boolean validateTotalCardType(String paramType, int numOfParamType) {
		if (getStackParamValueInt(STACK_WARNING_PARAM_TYPE, paramType) >= numOfParamType) {
			return true;
		}
		return false;
	}

	private static int getStackParamValueInt(String paramCode, String paramType) {
		String PARAM_VALUE = SystemConfig.getGeneralParam(getParamTypePurgeDay(paramCode, paramType));
		return Integer.parseInt(PARAM_VALUE);
	}

	private static String getStackParamValueString(String paramCode, String paramType) {
		return SystemConfig.getGeneralParam(getParamTypePurgeDay(paramCode, paramType));
	}

	private static String getParamTypePurgeDay(String stackPurgeDayParamType, String paramType) {
		return stackPurgeDayParamType.replaceAll("\\{PARAM_TYPE\\}", paramType);
	}

	@Override
	public boolean validationNotifyTransactionResult(Object object) {
		RecipientTypeDataM recipient = getRecipient();
		if (!InfBatchUtil.empty(getRecipient()) && getRecipient().isValid() && recipient.getEmailRecipients().size() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		NotifyTransactionDataM notifyTransaction = new NotifyTransactionDataM();
		RunningParamStackRequestDataM runningParamStackRequestDataM = getNotifyObjectRequest(getNotifyRequest());
		if (!InfBatchUtil.empty(getRecipient()) && getRecipient().isValid()
				&& !InfBatchUtil.empty(runningParamStackRequestDataM.getRunningParamStackInfoM())) {
			ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
			for (RunningParamStackInfoM runningParamStackInfoM : runningParamStackRequestDataM.getRunningParamStackInfoM()) {
				if (InfBatchConstant.RUNNING_PARAM_STACK.PROCESS_WARNING.equals(runningParamStackInfoM.getProcessType())) {
					NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
					notifyTransactionResult.setTransactionObject(runningParamStackInfoM);
					notifyTransactionResult.setUniqueId(runningParamStackInfoM.getProcessType());
					transactions.add(notifyTransactionResult);
				}

			}
			notifyTransaction.setTransactions(transactions);
		}
		return notifyTransaction;
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
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
			throw new Exception(e.getLocalizedMessage());
		}

		return emailResponse;
	}

	@Override
	public boolean requiredNotify() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public KmobileRequest getSMSKMobileRequest(NotifyTransactionResultDataM transactionResult, NotifyTemplateDataM notifyTemplate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KmobileResponse sendSMSKMobile(KmobileRequest kMobileRequest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) throws Exception {
		ArrayList<NotifyTemplateDataM> notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
		notifyTemplate.setTemplateId(STACK_NOTIFICATION_TEMPLATE_ID);
		notifyTemplate.setTemplateType(TemplateBuilderConstant.TemplateType.EMAIL);
		notifyTemplates.add(notifyTemplate);
		return notifyTemplates;
	}

	@Override
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) throws Exception {

	}

	private RunningParamStackRequestDataM getNotifyObjectRequest(NotifyRequest notifyReq) {
		TaskDataM task = (TaskDataM) notifyReq.getRequestObject();
		TaskObjectDataM taskObject = task.getTaskObject();
		RunningParamStackRequestDataM runningParamStackRequestDataM = (RunningParamStackRequestDataM) taskObject.getObject();
		if (null == runningParamStackRequestDataM) {
			runningParamStackRequestDataM = new RunningParamStackRequestDataM();
		}
		return runningParamStackRequestDataM;
	}

	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest) throws Exception {
		RecipientTypeDataM recipientType = new RecipientTypeDataM();
		RunningParamStackRequestDataM runningParamStackRequestDataM = getNotifyObjectRequest(notifyRequest);

		if (!InfBatchUtil.empty(runningParamStackRequestDataM)) {
			for (RunningParamStackInfoM runningParamStackInfoM : runningParamStackRequestDataM.getRunningParamStackInfoM()) {
				if (!InfBatchUtil.empty(runningParamStackInfoM.getProcessType()) && !InfBatchUtil.empty(runningParamStackInfoM.getNumOfParamType())) {
					getRecipient(recipientType, runningParamStackInfoM.getParamType(), runningParamStackInfoM.getNumOfParamType());
				}

			}
		}
		return recipientType;
	}

}
