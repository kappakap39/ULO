package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.config.model.ReasonApplication;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.util.NotificationUtil;

public class SendToCompliance extends NotificationActionHelper  implements NotificationActionInf{
	private static transient Logger logger = Logger.getLogger(SendToCompliance.class);	
	String SEND_TO_TYPE_COMPLIANCE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_COMPLIANCE");
//	String NOTIFICATION_SANCTION_LIST_REJECT_CODE=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SANCTION_LIST_REJECT_CODE");
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	@Override
	public Object notificationProcessCondition(Object... obj) throws Exception {
		NotificationInfoDataM notificationInfo  = (NotificationInfoDataM)obj[0];	 
		RecipientTypeDataM recipientType = (RecipientTypeDataM)obj[1];
		HashMap<String, ArrayList<JobCodeDataM>> jobcodeHash = notificationInfo.getJobCodes();
		logger.debug("jobcodeHash : "+jobcodeHash);
//		boolean foundSanctionList = foundSanctionList(notificationInfo.getApplicationGroupId());
//		logger.debug("foundSanctionList : "+foundSanctionList);
//		if(foundSanctionList){
//			String EMAIL_KEY_COMPLIANCE = TemplateBuilderConstant.TemplateType.EMAIL+"_"+SEND_TO_TYPE_COMPLIANCE;
//			logger.debug("EMAIL_KEY_COMPLIANCE : "+EMAIL_KEY_COMPLIANCE);
//			ArrayList<JobCodeDataM>  emailFixJobCodes =  jobcodeHash.get(EMAIL_KEY_COMPLIANCE);
//			logger.debug("emailFixJobCodes : "+emailFixJobCodes);
//			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
//			//set applicant for find template data
//			ArrayList<RecipientInfoDataM> sendTocustomers = notificationDAO.loadRecipient(notificationInfo);
//			logger.debug("sendTocustomers : "+sendTocustomers);
//			if(!InfBatchUtil.empty(emailFixJobCodes)){
//				notificationInfo.setSendToCompliance(true);
//				for(JobCodeDataM  jobCode :emailFixJobCodes){
//					logger.debug("jobCode : "+jobCode);
//					if(!InfBatchUtil.empty(jobCode.getFixEmail()) && NOTIFICATION_SEND_TO_PRIORITY.equals(jobCode.getPriority())){
//						String generalParam = jobCode.getFixEmail();
//						logger.debug("generalParam : "+generalParam);
//						String email = notificationDAO.getGeneralParamValue(generalParam);
//						if(!InfBatchUtil.empty(email)){
//							String[] emailList = email.split(",");
//							for(String emailconfix : emailList){
//								EmailRecipientDataM  emailRecipient  = new EmailRecipientDataM();
//								emailRecipient.setEmail(emailconfix);
//								emailRecipient.setRecipientType(SEND_TO_TYPE_COMPLIANCE);
//								emailRecipient.setRecipientObject(sendTocustomers);
//								recipientType.put(emailRecipient);
//							}
//						}
//					}
//				}
//			}
//		}
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		ArrayList<RecipientInfoDataM> sendTocustomers = notificationDAO.loadRecipient(notificationInfo);
		RecipientInfoDataM filteredSendToCustomer = filterRecipientInfoRejectSaction(notificationInfo,sendTocustomers);
		if(!InfBatchUtil.empty(filteredSendToCustomer)){
			String EMAIL_KEY_COMPLIANCE = TemplateBuilderConstant.TemplateType.EMAIL+"_"+SEND_TO_TYPE_COMPLIANCE;
			logger.debug("EMAIL_KEY_COMPLIANCE : "+EMAIL_KEY_COMPLIANCE);
			ArrayList<JobCodeDataM>  emailFixJobCodes =  jobcodeHash.get(EMAIL_KEY_COMPLIANCE);
			logger.debug("emailFixJobCodes : "+emailFixJobCodes);
			if(!InfBatchUtil.empty(emailFixJobCodes)){
				notificationInfo.setSendToCompliance(true);
				for(JobCodeDataM  jobCode :emailFixJobCodes){
					logger.debug("jobCode : "+jobCode);
					if(!InfBatchUtil.empty(jobCode.getFixEmail()) && NOTIFICATION_SEND_TO_PRIORITY.equals(jobCode.getPriority())){
						String generalParam = jobCode.getFixEmail();
						logger.debug("generalParam : "+generalParam);
						String email = notificationDAO.getGeneralParamValue(generalParam);
						if(!InfBatchUtil.empty(email)){
							String[] emailList = email.split(",");
							for(String emailconfix : emailList){
								ArrayList<RecipientInfoDataM> recipientObjs = new ArrayList<RecipientInfoDataM>();
									recipientObjs.add(filteredSendToCustomer);
								EmailRecipientDataM  emailRecipient  = new EmailRecipientDataM();
								emailRecipient.setEmail(emailconfix);
								emailRecipient.setRecipientType(SEND_TO_TYPE_COMPLIANCE);
								emailRecipient.setRecipientObject(recipientObjs);
								recipientType.put(emailRecipient);
							}
						}
					}
				}
			}
		}
		return null;
	}
	@Deprecated
	public boolean foundSanctionList(String applicationGroupId) throws InfBatchException{
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		logger.debug("applicationGroupId : "+applicationGroupId);
		List<ReasonApplication> reasonApplications = notificationDAO.findReasonApplications(applicationGroupId);
		if(null!=reasonApplications){
			for (ReasonApplication reasonApplication : reasonApplications) {
				if(null!=reasonApplication&&reasonApplication.isFoundReason()
						&&InfBatchProperty.lookup("NOTIFICATION_SANCTION_LIST_REJECT_CODE",reasonApplication.getReasonCode())) return true;
			}
		}
		return false;
	}
	private RecipientInfoDataM filterRecipientInfoRejectSaction(NotificationInfoDataM notificationInfo,ArrayList<RecipientInfoDataM> recipientInfos)throws InfBatchException{
		String PERSONAL_TYPE_APPLICANT = InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");
		String PERSONAL_TYPE_SUPPLEMENTARY = InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");
		RecipientInfoDataM recipientInfoApplicant = null;
		RecipientInfoDataM recipientInfoSupplementary = null;
		try{
			if(!InfBatchUtil.empty(recipientInfos)){
				for(RecipientInfoDataM recipientInfo : recipientInfos){
					if(!InfBatchUtil.empty(recipientInfo)){
						if(PERSONAL_TYPE_APPLICANT.equals(recipientInfo.getPersonalType())){
							recipientInfoApplicant = recipientInfo;
						}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(recipientInfo.getPersonalType())){
							recipientInfoSupplementary = recipientInfo;
						}
					}
				}
				NotifyApplicationSegment notifyApplicationSegment = NotificationUtil.notifyApplication(notificationInfo.getApplicationGroupId());
				if(!InfBatchUtil.empty(recipientInfoApplicant)){
					if(NotificationUtil.isRejectSanction(notifyApplicationSegment,recipientInfoApplicant.getFinalDecision(),recipientInfoApplicant.getPersonalId())){
						return recipientInfoApplicant;
					}
				}
				if(!InfBatchUtil.empty(recipientInfoSupplementary)){
					if(NotificationUtil.isRejectSanction(notifyApplicationSegment,recipientInfoSupplementary.getFinalDecision(),recipientInfoSupplementary.getPersonalId())){
						return recipientInfoSupplementary;
					}
				}
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			throw new InfBatchException(e);
		}
		return null;
	}
}
