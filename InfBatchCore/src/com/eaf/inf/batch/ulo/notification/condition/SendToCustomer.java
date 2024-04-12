package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.SMSKMobileRecipientDataM;
import com.eaf.core.ulo.service.notify.model.SMSRecipientDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;

public class SendToCustomer extends NotificationActionHelper  implements NotificationActionInf{
	private static transient Logger logger = Logger.getLogger(SendToCustomer.class);	
	String SEND_TO_TYPE_CUSTOMER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CUSTOMER");
//	private static String APPLICATION_STATUS_REJECT=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	
	private static String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	private static String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");	
	private String GEN_PARAM_CC_INFINITE = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_INFINITE");//001
	private String GEN_PARAM_CC_WISDOM = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_WISDOM");//002
	private String GEN_PARAM_CC_PREMIER = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_PREMIER");//003
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	String NOTIFICATION_CUSTOMER_FINAL_APP_DECISION = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CUSTOMER_FINAL_APP_DECISION");
	@Override
	public Object notificationProcessCondition(Object... obj) throws Exception {
		NotificationInfoDataM notificationInfo  = (NotificationInfoDataM)obj[0];	 
		RecipientTypeDataM recipientType = (RecipientTypeDataM)obj[1];	
		String templateId = notificationInfo.getApplicationTemplate();
		logger.debug("templateId>>"+templateId);
		HashMap<String, ArrayList<JobCodeDataM>>  hJobCodes = notificationInfo.getJobCodes();
		String SMS_KEY_CUSTOMER = TemplateBuilderConstant.TemplateType.SMS+"_"+SEND_TO_TYPE_CUSTOMER;
		String EMAIL_KEY_CUSTOMER = TemplateBuilderConstant.TemplateType.EMAIL+"_"+SEND_TO_TYPE_CUSTOMER;
		String KMOBILE_KEY_CUSTOMER  = TemplateBuilderConstant.TemplateType.K_MOBILE+"_"+SEND_TO_TYPE_CUSTOMER;
		 
		ArrayList<JobCodeDataM>  smsFixJobCodes =  hJobCodes.get(SMS_KEY_CUSTOMER);
		ArrayList<JobCodeDataM>  emailFixJobCodes =  hJobCodes.get(EMAIL_KEY_CUSTOMER);
		ArrayList<JobCodeDataM>  kMobileFixJobCodes = hJobCodes.get(KMOBILE_KEY_CUSTOMER);
		
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		ArrayList<RecipientInfoDataM> recipientInfos = notificationDAO.loadRecipient(notificationInfo);
		HashMap<String, ArrayList<RecipientInfoDataM>>  filterCustomerProduct = SendtoCustomerUtil.filterRecipientInfoProduct(recipientInfos);
		
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
							logger.debug("kMobileFixJobCodes>>"+kMobileFixJobCodes);
							//case Application is AddSub and will not contain personal type A
							if(isAddSupApplication && isApproveAll){
								for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									addRecipentTypes(recipientType, approvedCustomer, smsFixJobCodes, null, kMobileFixJobCodes);
								}
							}else if(isMainApprove){
								 for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									 	if(PERSONAL_TYPE_APPLICANT.equals(approvedCustomer.getPersonalType()) && NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(approvedCustomer.getFinalDecision())){
									 		addRecipentTypes(recipientType, approvedCustomer, smsFixJobCodes, null, kMobileFixJobCodes);
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
							logger.debug("kMobileFixJobCodes>>"+kMobileFixJobCodes);
							
							if(isAddSupApplication){
								for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									addRecipentTypes(recipientType, approvedCustomer, smsFixJobCodes, emailFixJobCodes, kMobileFixJobCodes);
								}
							}else{
								if(isApproveAll){
									for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									 	if(PERSONAL_TYPE_APPLICANT.equals(approvedCustomer.getPersonalType())){
									 		//if Approve All  will not send Email
											addRecipentTypes(recipientType, approvedCustomer, smsFixJobCodes,isApproveAll?null:emailFixJobCodes, kMobileFixJobCodes);
									 	}
									}
								}else if(isRejectAll) {
									for(RecipientInfoDataM approvedCustomer : sendTocustomers){
									 	if(PERSONAL_TYPE_APPLICANT.equals(approvedCustomer.getPersonalType())){
									 		//if Reject All  will not send SMS/ KMobile
											addRecipentTypes(recipientType, approvedCustomer, null,emailFixJobCodes, kMobileFixJobCodes);
									 	}
									}
								}else if(!isOnlySupApprove){ //this case not send Email
									for(RecipientInfoDataM approvedCustomer : sendTocustomers){
										addRecipentTypes(recipientType, approvedCustomer, smsFixJobCodes, null, kMobileFixJobCodes);
									}
								}
							}											
						}
				}	
			}
		}
		return null;
	}
	
	
	private  void addRecipentTypes(RecipientTypeDataM recipientType,RecipientInfoDataM sendCustomer,ArrayList<JobCodeDataM>  smsFixJobCodes,ArrayList<JobCodeDataM>  emailFixJobCodes,ArrayList<JobCodeDataM> kMobileFixJobCodes){
		if(!InfBatchUtil.empty(smsFixJobCodes) && smsFixJobCodes.size()>0){	
			if(NOTIFICATION_CUSTOMER_FINAL_APP_DECISION.equals(sendCustomer.getFinalDecision())){
				if(!InfBatchUtil.empty(sendCustomer.getPhoneNo()) && NOTIFICATION_SEND_TO_PRIORITY.equals(smsFixJobCodes.get(0).getPriority())){
					ArrayList<RecipientInfoDataM> recipientInfos = new ArrayList<RecipientInfoDataM>();
					recipientInfos.add(sendCustomer);
					logger.debug(">>mobile>>"+sendCustomer.getPhoneNo());
					SMSRecipientDataM smsRecipientDataM = new SMSRecipientDataM();
					smsRecipientDataM.setMobileNo(sendCustomer.getPhoneNo());
					smsRecipientDataM.setRecipientType(SEND_TO_TYPE_CUSTOMER);
					smsRecipientDataM.setRecipientObject(recipientInfos);
					if(!recipientType.getSmsRecipients().contains(smsRecipientDataM)){
						recipientType.put(smsRecipientDataM);
					}
				}
			}
		}
		logger.debug("sendCustomer.getPhoneNo() >> "+sendCustomer.getPhoneNo());
		if(!InfBatchUtil.empty(kMobileFixJobCodes) && kMobileFixJobCodes.size()>0){
			logger.debug("kMobileFixJobCodes.get(0).getPriority() >> "+kMobileFixJobCodes.get(0).getPriority());
			if(!InfBatchUtil.empty(sendCustomer.getPhoneNo()) && NOTIFICATION_SEND_TO_PRIORITY.equals(kMobileFixJobCodes.get(0).getPriority())){
				ArrayList<RecipientInfoDataM> recipientInfos = new ArrayList<RecipientInfoDataM>();
				recipientInfos.add(sendCustomer);
				logger.debug(">>kmobile>>"+sendCustomer.getPhoneNo());
				SMSKMobileRecipientDataM smsKmobileRecipientDataM = new SMSKMobileRecipientDataM();
				smsKmobileRecipientDataM.setMobileNo(sendCustomer.getPhoneNo());
				smsKmobileRecipientDataM.setRecipientType(SEND_TO_TYPE_CUSTOMER);
				smsKmobileRecipientDataM.setRecipientObject(recipientInfos);
				if(!recipientType.getSmsKMobileRecipients().contains(smsKmobileRecipientDataM)){
					recipientType.put(smsKmobileRecipientDataM);
				}
			}
		}
		
		if(!InfBatchUtil.empty(emailFixJobCodes) && emailFixJobCodes.size()>0){
			if(NOTIFICATION_CUSTOMER_FINAL_APP_DECISION.equals(sendCustomer.getFinalDecision())){
				if(!InfBatchUtil.empty(sendCustomer.getEmail())){
					for(JobCodeDataM job : emailFixJobCodes){
						String sendPriority = job.getPriority();
						if(NOTIFICATION_SEND_TO_PRIORITY.equals(sendPriority)){
							ArrayList<RecipientInfoDataM> recipientInfos = new ArrayList<RecipientInfoDataM>();
							recipientInfos.add(sendCustomer);
							logger.debug(">>email>>"+sendCustomer.getEmail());
							EmailRecipientDataM emailRecipientDataM = new EmailRecipientDataM();
							emailRecipientDataM.setEmail(sendCustomer.getEmail());
							emailRecipientDataM.setRecipientType(SEND_TO_TYPE_CUSTOMER);
							emailRecipientDataM.setRecipientObject(recipientInfos);
							if(!recipientType.getEmailRecipients().contains(emailRecipientDataM)){
								recipientType.put(emailRecipientDataM);
							}	
						}
					}
				}
			}
		}
	}
//	private boolean isRejectExceptionReasonCodes(String appGroupId ,String appStatus){
//		try {
//			logger.debug("application status >>"+appStatus);		
//			if(APPLICATION_STATUS_REJECT.equals(appStatus)){
//				NotificationDAO dao = NotificationFactory.getNotificationDAO();
//				boolean isExpReasonCodes = dao.isExceptionReasonCodes(appGroupId);
//				return isExpReasonCodes;
//			}
//		} catch (Exception e) {
//			logger.fatal("ERROR",e);
//		}
//		return false;
//	}
}
