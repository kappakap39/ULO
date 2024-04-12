package com.eaf.inf.batch.ulo.notification.eod.sendto;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;

public class EODSendtoFix extends EODSendingHelper{
	private static transient Logger logger = Logger.getLogger(EODSendtoFix.class);
	@Override
	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient) throws Exception{
//		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		if(null!=eodReceipientRequest.getNotificationEods())
	 		for(NotificationEODDataM notificationEod : eodReceipientRequest.getNotificationEods()){
	 			String emailParamCode = notificationEod.getFixEmail();
	 			logger.debug("emailParamCode : "+emailParamCode);
	 			if(!InfBatchUtil.empty(emailParamCode)){
	 				String email = InfBatchProperty.getGeneralParam(emailParamCode);
	 				logger.debug("email : "+email);
	 				if(!InfBatchUtil.empty(email)){
	 					String[] emails = email.split(",");
	 					for(String emailConfig : emails){
	 			 			logger.debug("emailConfig : "+emailConfig);	
	 			 			if(!InfBatchUtil.empty(emailConfig) && !eodReceipientRequest.getEmails().contains(emailConfig)){
	 			 				EmailRecipientDataM emailRecipient= new EmailRecipientDataM();
		 			 			emailRecipient.setEmail(emailConfig);
		 			 			emailRecipient.setRecipientType(notificationEod.getNotificationType());
		 			 			recipient.put(emailRecipient);
		 			 			eodReceipientRequest.getEmails().add(emailConfig);
	 			 			}
	 					}
	 				}
	 			}		 		
	 		}
	}
}
