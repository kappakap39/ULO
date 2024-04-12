package com.eaf.inf.batch.ulo.notification.sms.nextday.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.SMSRecipientDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.condition.NotificationActionHelper;
import com.eaf.inf.batch.ulo.notification.condition.NotificationActionInf;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;

public class SendSMSNextDayToSeller extends NotificationActionHelper implements
		NotificationActionInf {

	private static transient Logger logger = Logger.getLogger(SendSMSNextDayToSeller.class);
	
	String SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String NOTIFICATION_SALE_TYPE_NORMAL=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_NORMAL");
	String NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION");
	@Override
	public Object notificationProcessCondition(Object... obj) {
		NotificationInfoDataM notificationInfoDataM  = (NotificationInfoDataM)obj[0];	 
		RecipientTypeDataM recipientTypeDataM = (RecipientTypeDataM)obj[1];	
 		try {		
			String saleID = notificationInfoDataM.getSaleId();
			HashMap<String, ArrayList<JobCodeDataM>>  hJobCodes = notificationInfoDataM.getJobCodes();
			logger.debug("hJobCodes.keySet() = "+hJobCodes.keySet());
			String SMS_KEY_SELLER = TemplateBuilderConstant.TemplateType.SMS+"_"+SEND_TO_TYPE_SELLER;
			logger.debug("SMS_KEY_SELLER = "+SMS_KEY_SELLER);
			logger.debug("hJobCodes = "+hJobCodes);
			if(hJobCodes.containsKey(SMS_KEY_SELLER)){
				NotificationDAO  dao = NotificationFactory.getNotificationDAO();
				ArrayList<String> saleList = new ArrayList<String>();
				saleList.add(saleID);
				ArrayList<VCEmpInfoDataM>  vcEmplist = dao.selectVCEmpList(saleList);
			
				// set applicant info for find template data
				ArrayList<RecipientInfoDataM> applicantInfos =dao.loadRecipient(notificationInfoDataM);
				ArrayList<RecipientInfoDataM> filteredApplicant = filterRecipient(applicantInfos);
				if(!InfBatchUtil.empty(filteredApplicant) && hJobCodes.containsKey(SMS_KEY_SELLER)){
					HashMap<String, String> hMobileNo = dao.getSellerMobileNo(saleList, NOTIFICATION_SALE_TYPE_NORMAL,notificationInfoDataM.getApplicationGroupId());
					NotificationUtil.mapVcEmpMobileNo(vcEmplist, hMobileNo);
					if(!InfBatchUtil.empty(hMobileNo)){
						//this.setMobileElementData(recipientTypeDataM,hMobileNo,applicantInfos);
						this.setMobileElementData(recipientTypeDataM,hMobileNo,filteredApplicant);
					}
				}				
			}
			 
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
	
	private void setMobileElementData(RecipientTypeDataM recipientTypeDataM,HashMap<String, String> hMobile,ArrayList<RecipientInfoDataM> applicantInfos){	
		try {
			logger.debug("hMobile = "+hMobile);
			if(!InfBatchUtil.empty(hMobile)){
				ArrayList<String> saleIds = new ArrayList<String>(hMobile.keySet());
				for(String saleId : saleIds){
					String mobile = hMobile.get(saleId);				 
					logger.debug("mobile = "+mobile);
					SMSRecipientDataM smsRecipient= new SMSRecipientDataM();
					smsRecipient.setMobileNo(mobile);
					smsRecipient.setRecipientType(SEND_TO_TYPE_SELLER);
					smsRecipient.setRecipientObject(applicantInfos);
					recipientTypeDataM.put(smsRecipient);
				}	
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	private ArrayList<RecipientInfoDataM> filterRecipient(ArrayList<RecipientInfoDataM> applicantInfos){
		ArrayList<RecipientInfoDataM> filteredApplicant = new ArrayList<RecipientInfoDataM>();
		if(!InfBatchUtil.empty(applicantInfos)){
			for(RecipientInfoDataM applicant : applicantInfos){
				if(!InfBatchUtil.empty(applicant)){
					if(applicant.getFinalDecision().equals(NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION)){
						filteredApplicant.add(applicant);
					}
				}
			}
		}
		return filteredApplicant;
	}
}
