package com.eaf.inf.batch.ulo.notification.eod.sendto;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationEODConfigDataM;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class EODSendtoManagerBranchSummary extends EODSendingHelper{
	private static transient Logger logger = Logger.getLogger(EODSendtoManagerBranchSummary.class);
	@Override
	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient) throws Exception {
		NotificationEODConfigDataM  notificationEODConfigDataM  = eodReceipientRequest.getNotificationEODConfig();
		if(!InfBatchUtil.empty(notificationEODConfigDataM)){
			VCEmpInfoDataM empInfo = notificationEODConfigDataM.getVcEmpManager();
			if(!InfBatchUtil.empty(empInfo)){
				if(!InfBatchUtil.empty(empInfo.getEmail())){
					String notificationType = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY");
					String email = empInfo.getEmail();
					 logger.debug("email : "+email);
					 if(!InfBatchUtil.empty(email) && !eodReceipientRequest.getEmails().contains(email)){
						 EmailRecipientDataM emailRecipient= new EmailRecipientDataM();
						 emailRecipient.setEmail(empInfo.getEmail());
						 emailRecipient.setRecipientType(notificationType);
						 recipient.put(emailRecipient);
						 eodReceipientRequest.getEmails().add(email);
					 }
				}
			}
		}
	}
//	@Override
//	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient) throws Exception {
//		NotificationEODConfigDataM  notificationEODConfigDataM  = eodReceipientRequest.getNotificationEODConfig();
//		if(!InfBatchUtil.empty(notificationEODConfigDataM)){
//			ArrayList<VCEmpInfoDataM> empInfos = notificationEODConfigDataM.getManagerInfos();
//			logger.debug("empInfos : "+empInfos);
//			if(!InfBatchUtil.empty(empInfos)){
//				String notificationType = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY");
//				 for(VCEmpInfoDataM empInfo :empInfos){
//					 if(!InfBatchUtil.empty(empInfo.getEmail())){
//						 String email = empInfo.getEmail();
//						 logger.debug("email : "+email);
//						 if(!InfBatchUtil.empty(email) && !eodReceipientRequest.getEmails().contains(email)){
//							 EmailRecipientDataM emailRecipient= new EmailRecipientDataM();
//							 emailRecipient.setEmail(empInfo.getEmail());
//							 emailRecipient.setRecipientType(notificationType);
//							 recipient.put(emailRecipient);
//							 eodReceipientRequest.getEmails().add(email);
//						 }
//					 }
//				 }
//			 }
//		}
//	}
}
