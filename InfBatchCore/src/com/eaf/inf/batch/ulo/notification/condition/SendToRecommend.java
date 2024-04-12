package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.SMSRecipientDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class SendToRecommend  extends NotificationActionHelper  implements NotificationActionInf{
	private static transient Logger logger = Logger.getLogger(SendToRecommend.class);	
	String SEND_TO_TYPE_RECOMMEND=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_RECOMMEND");
	String SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	@Override
	public Object notificationProcessCondition(Object... obj) throws Exception {
		NotificationInfoDataM notificationInfo  = (NotificationInfoDataM)obj[0];	 
		RecipientTypeDataM recipientType = (RecipientTypeDataM)obj[1];
		String recSaleID = notificationInfo.getSaleRecommend();
		HashMap<String, ArrayList<JobCodeDataM>>  hJobCodes = notificationInfo.getJobCodes();
		logger.debug("hJobCodes.keySet() = "+hJobCodes.keySet());
		String SMS_KEY_RECOMMEND = TemplateBuilderConstant.TemplateType.SMS+"_"+SEND_TO_TYPE_RECOMMEND;
		String EMAIL_KEY_RECOMMEND = TemplateBuilderConstant.TemplateType.EMAIL+"_"+SEND_TO_TYPE_RECOMMEND;
		logger.debug("SMS_KEY_RECOMMEND = "+SMS_KEY_RECOMMEND);
		logger.debug("EMAIL_KEY_RECOMMEND = "+EMAIL_KEY_RECOMMEND);
		logger.debug("hJobCodes = "+hJobCodes);
		NotificationDAO  notificationDAO = NotificationFactory.getNotificationDAO();
		// set applicant info for find template data
		ArrayList<RecipientInfoDataM> recipientInfos = notificationDAO.loadRecipient(notificationInfo);				
		
		ArrayList<VCEmpInfoDataM>  vcEmplist = null;
		if(null!=recSaleID && !"".equals(recSaleID)){
			ArrayList<String> recSaleList = new ArrayList<String>();
			recSaleList.add(recSaleID);
			vcEmplist = notificationDAO.selectVCEmpList(recSaleList);
		}
		ArrayList<JobCodeDataM> smsRecommendJobCodes = hJobCodes.get(SMS_KEY_RECOMMEND);
		ArrayList<JobCodeDataM> emailRecommendJobCodes =hJobCodes.get(EMAIL_KEY_RECOMMEND);
		if(null!= smsRecommendJobCodes && smsRecommendJobCodes.size()>0){
			if(NOTIFICATION_SEND_TO_PRIORITY.equals(smsRecommendJobCodes.get(0).getPriority())){
				this.setElementData(vcEmplist, TemplateBuilderConstant.TemplateType.SMS,recipientType,recipientInfos);
			}
			
		}
		if(null!=emailRecommendJobCodes && emailRecommendJobCodes.size()>0){
			if(NOTIFICATION_SEND_TO_PRIORITY.equals(emailRecommendJobCodes.get(0).getPriority()))
			this.setElementData(vcEmplist, TemplateBuilderConstant.TemplateType.EMAIL,recipientType,recipientInfos);
		}
		return null;
	}
	
	private void setElementData(ArrayList<VCEmpInfoDataM> empInfos,String notificationType,RecipientTypeDataM recipientType,ArrayList<RecipientInfoDataM> recipientInfos){	
		 if(!InfBatchUtil.empty(empInfos)){
			 logger.debug("empInfos = "+empInfos.size());
			 for(VCEmpInfoDataM empInfo : empInfos){
				 String mobile = empInfo.getMobilePhone();
				 String email = empInfo.getEmail();
				 logger.debug("mobile = "+mobile);
				 logger.debug("email = "+email);
				 if(!InfBatchUtil.empty(mobile) && TemplateBuilderConstant.TemplateType.SMS.equals(notificationType)){
					 SMSRecipientDataM smsRecipient= new SMSRecipientDataM();
					 smsRecipient.setMobileNo(mobile);
					 //smsRecipient.setRecipientType(SEND_TO_TYPE_RECOMMEND);
					 smsRecipient.setRecipientType(SEND_TO_TYPE_SELLER);
					 smsRecipient.setRecipientObject(recipientInfos);
					 recipientType.put(smsRecipient);
				 }
				 if(!InfBatchUtil.empty(email) && TemplateBuilderConstant.TemplateType.EMAIL.equals(notificationType)){
					 EmailRecipientDataM emailRecipient = new EmailRecipientDataM();
					 emailRecipient.setEmail(email);
					 //emailRecipient.setRecipientType(SEND_TO_TYPE_RECOMMEND);
					 emailRecipient.setRecipientType(SEND_TO_TYPE_SELLER);
					 emailRecipient.setRecipientObject(recipientInfos);
					 recipientType.put(emailRecipient);
				 }
			 }	
		 }
	}
}
