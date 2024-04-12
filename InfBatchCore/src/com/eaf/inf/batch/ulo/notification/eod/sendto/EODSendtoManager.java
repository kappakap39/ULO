package com.eaf.inf.batch.ulo.notification.eod.sendto;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.condition.NotificationCondition;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class EODSendtoManager extends EODSendingHelper {
	private static transient Logger logger = Logger.getLogger(EODSendtoManager.class);
	@Override
	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient) throws Exception {
		for(NotificationEODDataM notificationEod : eodReceipientRequest.getNotificationEods()){
			logger.debug("notificationEod.getSaleId() : "+notificationEod.getSaleId());
			logger.debug("notificationEod.getRecommend() : "+notificationEod.getRecommend());
			NotificationCondition notificationCondition = new NotificationCondition();
			 ArrayList<VCEmpInfoDataM> empInfos = new ArrayList<VCEmpInfoDataM>(); 
			 List<String> saleIds = new ArrayList<>();
			 if(!InfBatchUtil.empty(notificationEod.getSaleId())&&!saleIds.contains(notificationEod.getSaleId()) 
					 && notificationEod.getSaleChannel().equals(notificationEod.getManagerChannel())){
				 ArrayList<VCEmpInfoDataM> empSaleManagers = notificationCondition.getSendToVCEmpManagers(notificationEod.getSaleId()
						 ,notificationEod.getJobCodes());
				 if(!InfBatchUtil.empty(empSaleManagers)){
					 empInfos.addAll(empSaleManagers);
					 saleIds.add(notificationEod.getSaleId());
				 }
			 }
			 List<String> recommendIds = new ArrayList<>();
			 if(!InfBatchUtil.empty(notificationEod.getRecommend()) &&!recommendIds.contains(notificationEod.getRecommend()) 
					 && notificationEod.getRecommendChannel().equals(notificationEod.getManagerChannel())){
				 ArrayList<VCEmpInfoDataM> empRecommendManagers = notificationCondition.getSendToVCEmpManagers(notificationEod.getRecommend()
						 ,notificationEod.getJobCodes());
				 if(!InfBatchUtil.empty(empRecommendManagers)){
					 empInfos.addAll(empRecommendManagers);
					 recommendIds.add(notificationEod.getRecommend());
				 }
			 }
			 logger.debug("EODSendtoManager.empInfos : "+empInfos);
			 if(!InfBatchUtil.empty(empInfos)){
				 for(VCEmpInfoDataM empInfo :empInfos){
					 if(!InfBatchUtil.empty(empInfo.getEmail())){
						 String email = empInfo.getEmail();
						 if(!InfBatchUtil.empty(email) && !eodReceipientRequest.getEmails().contains(email)){
							 EmailRecipientDataM emailRecipient= new EmailRecipientDataM();
							 emailRecipient.setEmail(empInfo.getEmail());
							 emailRecipient.setRecipientType(notificationEod.getNotificationType());
							 recipient.put(emailRecipient);
							 eodReceipientRequest.getEmails().add(email);
						 }
					 }
				 }
			 }
 		}
	}
}
