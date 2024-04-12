package com.eaf.inf.batch.ulo.notification.warehouse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailTemplateDataM;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;

public class NotificationIncompleteProcess extends NotifyHelper  {
	private static transient Logger logger = Logger.getLogger(NotificationIncompleteProcess.class);
	String TEMPLATE_ID_NOT_INCOMPLETE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_TEMPLATE_ID_NOT_INCOMPLETE");
	
	@Override
	public RecipientTypeDataM getRecipient(NotifyRequest notifyRequest) {
		TaskDataM task  = (TaskDataM)notifyRequest.getRequestObject();
		RecipientTypeDataM recipientTypeDataM = new RecipientTypeDataM();
		try {
			TaskObjectDataM taskObject  = task.getTaskObject();
			String sendToEmail = taskObject.getUniqueId();
			HashMap<String, Object > hData = (HashMap<String, Object >)taskObject.getObject();			
			ArrayList<DMNotificationDataM>  dmNotificationList = (ArrayList<DMNotificationDataM>)hData.get(sendToEmail);
			ArrayList<String> existingEmails = new ArrayList<String>();
			if(!InfBatchUtil.empty(dmNotificationList)){
				for(DMNotificationDataM dmNotification : dmNotificationList ){
					EmailTemplateDataM emailTemplate =  dmNotification.getEmailtemplatedataM();
					ArrayList<String> emails =  emailTemplate.getEmailElement();
					if(!InfBatchUtil.empty(emails)){
						for(String  email :emails){
							if(!existingEmails.contains(email)){
								EmailRecipientDataM  emailRecipientDataM = new EmailRecipientDataM();
								emailRecipientDataM.setEmail(email);
								recipientTypeDataM.put(emailRecipientDataM);
								existingEmails.add(email);
							}
						}
					}
					ArrayList<String> ccEmails = emailTemplate.getCcEmailElement();
					if(!InfBatchUtil.empty(ccEmails) &&  !InfBatchUtil.empty(emails)){
						for(String ccEmail :ccEmails){
							EmailRecipientDataM  ccEmailRecipientDataM = new EmailRecipientDataM();
							ccEmailRecipientDataM.setCcEmail(ccEmail);
							recipientTypeDataM.put(ccEmailRecipientDataM);
						}
					}
				}

			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return recipientTypeDataM;
	}

	@Override
	public NotifyTransactionDataM processNotifyTransaction() {
		NotifyTransactionDataM notifyTransactionDataM = new NotifyTransactionDataM();
		try {
			TaskDataM task  = (TaskDataM)getNotifyRequest().getRequestObject();
			TaskObjectDataM taskObject  = task.getTaskObject();
			String sendToEmail = taskObject.getUniqueId();
			logger.debug("sendToEmail>>"+sendToEmail);
			HashMap<String, Object > hData = (HashMap<String, Object >)taskObject.getObject();			
			ArrayList<DMNotificationDataM>  dmNotificationList = (ArrayList<DMNotificationDataM>)hData.get(sendToEmail);		
			ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
			NotifyTransactionResultDataM notifyTransactionResult = new NotifyTransactionResultDataM();
			notifyTransactionResult.setTransactionObject(dmNotificationList);
			transactions.add(notifyTransactionResult);
			notifyTransactionDataM.setTransactions(transactions);			 
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		
		return notifyTransactionDataM;
	}
	
	@Override
	public ArrayList<NotifyTemplateDataM> getNotifyTemplate(NotifyTransactionResultDataM transactionResult) {
		ArrayList<NotifyTemplateDataM> notifyTemplates = new ArrayList<NotifyTemplateDataM>();
		NotifyTemplateDataM notifyTemplateDataM = new NotifyTemplateDataM();
		notifyTemplateDataM.setTemplateId(TEMPLATE_ID_NOT_INCOMPLETE);
		notifyTemplateDataM.setTemplateType(TemplateBuilderConstant.TemplateType.EMAIL);
		notifyTemplateDataM.setDbType(InfBatchServiceLocator.DM_DB);
		notifyTemplates.add(notifyTemplateDataM);
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
	public void postNotifyTransactionResult(NotifyTransactionResultDataM transactionResult) {
		Connection  conn= InfBatchObjectDAO.getConnection(InfBatchServiceLocator.DM_DB);	
		RecipientTypeDataM RecipientTypeDataM = getRecipient();
		ArrayList<DMNotificationDataM> dmNotificationList = (ArrayList<DMNotificationDataM>)transactionResult.getTransactionObject();
		EmailRequest emailRequest = (EmailRequest)transactionResult.getServiceResult().getRequestObject();
		try {
			conn.setAutoCommit(false);
			if(!InfBatchUtil.empty(dmNotificationList)){
				for(DMNotificationDataM dmNotification  :dmNotificationList ){
					NotificationDAO dao = NotificationFactory.getNotificationDAO();
					 dao.insertDMCorrespondLog(dmNotification,emailRequest, conn);
				}
			}
			conn.commit();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR",e1);
			}			
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
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
