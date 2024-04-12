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
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;

public class SendToSeller extends NotificationActionHelper  implements NotificationActionInf{
	private static transient Logger logger = Logger.getLogger(SendToSeller.class);	
	String SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String NOTIFICATION_SALE_TYPE_NORMAL=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_NORMAL");
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	@Override
	public Object notificationProcessCondition(Object... obj) throws Exception {
		NotificationInfoDataM notificationInfo  = (NotificationInfoDataM)obj[0];	 
		RecipientTypeDataM recipientType = (RecipientTypeDataM)obj[1];
		String saleID = notificationInfo.getSaleId();
		HashMap<String, ArrayList<JobCodeDataM>>  hJobCodes = notificationInfo.getJobCodes();
		logger.debug("hJobCodes.keySet() = "+hJobCodes.keySet());
		String SMS_KEY_SELLER = TemplateBuilderConstant.TemplateType.SMS+"_"+SEND_TO_TYPE_SELLER;
		String EMAIL_KEY_SELLER = TemplateBuilderConstant.TemplateType.EMAIL+"_"+SEND_TO_TYPE_SELLER;
		logger.debug("SMS_KEY_SELLER = "+SMS_KEY_SELLER);
		logger.debug("EMAIL_KEY_SELLER = "+EMAIL_KEY_SELLER);
		logger.debug("hJobCodes = "+hJobCodes);
		if(hJobCodes.containsKey(SMS_KEY_SELLER) || hJobCodes.containsKey(EMAIL_KEY_SELLER) ){
			NotificationDAO  notificationDAO = NotificationFactory.getNotificationDAO();
			ArrayList<String> saleList = new ArrayList<String>();
			saleList.add(saleID);
			ArrayList<VCEmpInfoDataM> vcEmplist = notificationDAO.selectVCEmpList(saleList);
			// set applicant info for find template data
			ArrayList<RecipientInfoDataM> recipientInfos = notificationDAO.loadRecipient(notificationInfo);				
			//if(hJobCodes.containsKey(SMS_KEY_SELLER) && isApproveAll(recipientInfos)){
			if(hJobCodes.containsKey(SMS_KEY_SELLER)){
				HashMap<String, String> hMobileNo = notificationDAO.getSellerMobileNo(saleList, NOTIFICATION_SALE_TYPE_NORMAL,notificationInfo.getApplicationGroupId());
				NotificationUtil.mapVcEmpMobileNo(vcEmplist, hMobileNo);
				if(!InfBatchUtil.empty(hMobileNo)){
					this.setMobileElementData(recipientType,hMobileNo,recipientInfos);
				}
			}
			if(hJobCodes.containsKey(EMAIL_KEY_SELLER)){
				this.setEmailElementData(vcEmplist,recipientType,recipientInfos);
			}
			
		}
		return null;
	}
	
	private void setEmailElementData(ArrayList<VCEmpInfoDataM>  vcEmplist,RecipientTypeDataM recipientType,ArrayList<RecipientInfoDataM> recipientInfos){
		 logger.debug("vcEmplist = "+vcEmplist.size());
		 if(!InfBatchUtil.empty(vcEmplist)){
			 for(VCEmpInfoDataM vcEmp : vcEmplist){
				 String email = vcEmp.getEmail();
				 logger.debug("email = "+email);					
					 EmailRecipientDataM emailRecipient = new EmailRecipientDataM();
					 emailRecipient.setEmail(email);
					 emailRecipient.setRecipientType(SEND_TO_TYPE_SELLER);
					 emailRecipient.setRecipientObject(recipientInfos);
					 recipientType.put(emailRecipient);
			 }	
		 }
	}
	private void setMobileElementData(RecipientTypeDataM recipientType,HashMap<String, String> hMobile,ArrayList<RecipientInfoDataM> recipientInfos){
		logger.debug("hMobile = "+hMobile);
		if(!InfBatchUtil.empty(hMobile)){
			ArrayList<String> saleIds = new ArrayList<String>(hMobile.keySet());
			for(String saleId : saleIds){
				String mobile = hMobile.get(saleId);				 
				logger.debug("mobile = "+mobile);
				SMSRecipientDataM smsRecipient= new SMSRecipientDataM();
				smsRecipient.setMobileNo(mobile);
				smsRecipient.setRecipientType(SEND_TO_TYPE_SELLER);
				smsRecipient.setRecipientObject(recipientInfos);
				recipientType.put(smsRecipient);
			}	
		}
	}
	
	public boolean isApproveAll(ArrayList<RecipientInfoDataM> applicantInfos) {
		 int count=0;
		 if(null!=applicantInfos && applicantInfos.size()>0){
			 for(RecipientInfoDataM sendToCustomer : applicantInfos){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(sendToCustomer.getFinalDecision())){
					 count++;
				 }
			 }
			 if(count==applicantInfos.size()){
				 return true;
			 }
		 }
		return false;
	}
}
