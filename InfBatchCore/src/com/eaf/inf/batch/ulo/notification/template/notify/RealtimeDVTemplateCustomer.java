package com.eaf.inf.batch.ulo.notification.template.notify;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.inf.helper.NotificationTemplateHelper;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateResponseDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;

public class RealtimeDVTemplateCustomer extends NotificationTemplateHelper{
	private static transient Logger logger = Logger.getLogger(RealtimeDVTemplateCustomer.class);
	String NOTIFICATION_SEND_TO_TYPE_CUSTOMER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
	@Override
	public NotificationTemplateResponseDataM processNotifyTemplate(NotificationTemplateRequestDataM templateRequest)throws Exception{
		NotificationInfoDataM notification = (NotificationInfoDataM)templateRequest.getRequestObj();
		RecipientInfoDataM recipientInfo = templateRequest.getRecipientInfo();
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		HashMap<String, ArrayList<String>>  hListTemplate = notificationDAO.loadSMSTemplateNotificationNoDecision(notification,recipientInfo);
		logger.debug("hListTemplate : "+hListTemplate);
		ArrayList<String> smsTemplates = hListTemplate.get(NOTIFICATION_SEND_TO_TYPE_CUSTOMER);
		NotificationTemplateResponseDataM templateResponse = new NotificationTemplateResponseDataM();
			templateResponse.setResponseObj(smsTemplates);
		return templateResponse;
	}
}