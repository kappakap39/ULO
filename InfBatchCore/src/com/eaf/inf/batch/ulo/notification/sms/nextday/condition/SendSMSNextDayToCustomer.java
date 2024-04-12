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
import com.eaf.inf.batch.ulo.notification.condition.SendToCustomer;
import com.eaf.inf.batch.ulo.notification.condition.SendtoCustomerUtil;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;

public class SendSMSNextDayToCustomer extends NotificationActionHelper  implements NotificationActionInf{
	private static transient Logger logger = Logger.getLogger(SendToCustomer.class);	
	String SEND_TO_TYPE_CUSTOMER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
	private static String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	private static String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");	
	private String GEN_PARAM_CC_INFINITE = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_INFINITE");//001
	private String GEN_PARAM_CC_WISDOM = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_WISDOM");//002
	private String GEN_PARAM_CC_PREMIER = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_PREMIER");//003
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	String NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION");
	@Override
	public Object notificationProcessCondition(Object... obj) throws Exception {
		NotificationInfoDataM notificationSMSNextDay  = (NotificationInfoDataM)obj[0];	 
		RecipientTypeDataM recipientTypeDataM = (RecipientTypeDataM)obj[1];	 
		String templateId = notificationSMSNextDay.getApplicationTemplate();
		logger.debug("templateId>>"+templateId);
		HashMap<String, ArrayList<JobCodeDataM>>  hJobCodes = notificationSMSNextDay.getJobCodes();
		String SMS_KEY_CUSTOMER = TemplateBuilderConstant.TemplateType.SMS+"_"+SEND_TO_TYPE_CUSTOMER;
		ArrayList<JobCodeDataM>  smsFixJobCodes =  hJobCodes.get(SMS_KEY_CUSTOMER);

		NotificationDAO dao = NotificationFactory.getNotificationDAO();
		ArrayList<RecipientInfoDataM> recipientInfos = dao.loadRecipient(notificationSMSNextDay);
		//HashMap<String, ArrayList<RecipientInfoDataM>>   filterCustomerProduct =SendtoCustomerUtil.filterRecipientInfoProduct(recipientInfos);
		HashMap<String, ArrayList<RecipientInfoDataM>>   filterCustomerProduct =filterRecipientInfo(recipientInfos);
		
		if(null!=filterCustomerProduct && !filterCustomerProduct.isEmpty()){
			ArrayList<String> keys = new ArrayList<String>(filterCustomerProduct.keySet());
			for(String keyName : keys){
				ArrayList<RecipientInfoDataM>  sendTocustomers =  filterCustomerProduct.get(keyName);							
				// Case Infinit Wisdom premier : this case not send Email
				if(null!=sendTocustomers && sendTocustomers.size()>0){
					if(InfBatchProperty.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(templateId) ||
							InfBatchProperty.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(templateId) ||
							InfBatchProperty.getGeneralParam(GEN_PARAM_CC_PREMIER).contains(templateId)){
							boolean isApproveAll=SendtoCustomerUtil.isApprovedAll(sendTocustomers);
							boolean isMainApprove=SendtoCustomerUtil.isMainApprove(sendTocustomers);
							boolean isAddSupApplication=SendtoCustomerUtil.isAddSupplementaryApplication(sendTocustomers);
							logger.debug("cas infinite wisdoc premier>>");
							logger.debug("isApproveAll>>"+isApproveAll);
							logger.debug("isMainApprove>>"+isMainApprove);
							logger.debug("isAddSupApplication>>"+isAddSupApplication);
							//case Application is AddSub and will not contain personal type A
							if(isAddSupApplication && isApproveAll){
								for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									addRecipentTypes(recipientTypeDataM, approvedCustomer, smsFixJobCodes);
								}
							}else if(isMainApprove){
								for(RecipientInfoDataM approvedCustomer : sendTocustomers){
								 	if(PERSONAL_TYPE_APPLICANT.equals(approvedCustomer.getPersonalType()) && NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(approvedCustomer.getFinalDecision())){
								 		addRecipentTypes(recipientTypeDataM, approvedCustomer, smsFixJobCodes);
								 	}
								}
							}
						}else{
							//Case not  infinite wisdom premier 
							boolean isRejectAll=SendtoCustomerUtil.isRejectAll(sendTocustomers);
							boolean isApproveAll=SendtoCustomerUtil.isApprovedAll(sendTocustomers);
							boolean isOnlySupApprove=SendtoCustomerUtil.isOnlySuplemenratyApprove(sendTocustomers);
							boolean isAddSupApplication=SendtoCustomerUtil.isAddSupplementaryApplication(sendTocustomers);
							boolean isMainApprove=SendtoCustomerUtil.isMainApprove(sendTocustomers);
							logger.debug("isRejectAll>>"+isRejectAll);
							logger.debug("isApproveAll>>"+isApproveAll);
							logger.debug("isOnlySupApprove>>"+isOnlySupApprove);
							logger.debug("isAddSupApplication>>"+isAddSupApplication);
							logger.debug("isMainApprove>>"+isMainApprove);

							if(isAddSupApplication){
								for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									addRecipentTypes(recipientTypeDataM, approvedCustomer, smsFixJobCodes);
								}
							}else{
								if(isRejectAll || isApproveAll){
									for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									 	if(PERSONAL_TYPE_APPLICANT.equals(approvedCustomer.getPersonalType())){
									 		//if Approve All  will not send Email
											addRecipentTypes(recipientTypeDataM, approvedCustomer, smsFixJobCodes);
									 	}
									}
								}else if(!isOnlySupApprove){ //this case not send Email
									for(RecipientInfoDataM approvedCustomer : sendTocustomers){
										addRecipentTypes(recipientTypeDataM, approvedCustomer, smsFixJobCodes);
									}
								}
							}											
						}
				}	
			}
		}
		return null;
	}
	private  void addRecipentTypes(RecipientTypeDataM recipientTypeDataM,RecipientInfoDataM sendCustomerDataM,ArrayList<JobCodeDataM>  smsFixJobCodes){
		try{
			if(!InfBatchUtil.empty(smsFixJobCodes) && smsFixJobCodes.size()>0){	
				if(!InfBatchUtil.empty(sendCustomerDataM.getPhoneNo()) && NOTIFICATION_SEND_TO_PRIORITY.equals(smsFixJobCodes.get(0).getPriority())){
					ArrayList<RecipientInfoDataM> recipientInfos = new ArrayList<RecipientInfoDataM>();
					recipientInfos.add(sendCustomerDataM);
					logger.debug(">>mobile>>"+sendCustomerDataM.getPhoneNo());
					SMSRecipientDataM smsRecipientDataM = new SMSRecipientDataM();
					smsRecipientDataM.setMobileNo(sendCustomerDataM.getPhoneNo());
					smsRecipientDataM.setRecipientType(SEND_TO_TYPE_CUSTOMER);
					smsRecipientDataM.setRecipientObject(recipientInfos);
					if(!recipientTypeDataM.getSmsRecipients().contains(smsRecipientDataM)){
						recipientTypeDataM.put(smsRecipientDataM);
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
	private HashMap<String, ArrayList<RecipientInfoDataM>> filterRecipientInfo(ArrayList<RecipientInfoDataM> recipientInfos){
		try {
			HashMap<String, ArrayList<RecipientInfoDataM>> hFilterRecipientProduct  = new HashMap<String, ArrayList<RecipientInfoDataM>>();
			if(null!=recipientInfos && recipientInfos.size()>0){
				for(RecipientInfoDataM recipientinfo: recipientInfos){
					if(recipientinfo.getFinalDecision().equals(NOTIFICATION_SMS_NEXT_DAY_FINAL_APP_DECISION)){
						String busClass = recipientinfo.getBusinessClassId();
						if(null==busClass) busClass="";
						String cartCode = recipientinfo.getCardCode();
						if(null==cartCode)cartCode="";
						String key = busClass+"_"+cartCode;
						ArrayList<RecipientInfoDataM> filterRecipients =  new ArrayList<RecipientInfoDataM>();
						if(hFilterRecipientProduct.containsKey(key)){
							filterRecipients =  hFilterRecipientProduct.get(key);
						}else{
							hFilterRecipientProduct.put(key, filterRecipients);
						}					
						filterRecipients.add(recipientinfo);
					}
				}
//				logger.debug("filter personal with product >>"+new Gson().toJson(hFilterRecipientProduct));	
				return hFilterRecipientProduct;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
}