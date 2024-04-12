package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationEODConfigDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.inf.batch.ulo.notification.model.EmailEODRejectNCBDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailRejectNCBFilterApplicationDataM;
import com.eaf.inf.batch.ulo.notification.model.EmailRejectNCBPersonalInfoDataM;
import com.eaf.notify.task.NotifyTask;

public class EmailTemplateEODRejectNCB extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(EmailTemplateEODRejectNCB.class);
	private static String GEN_PARAM_CC_INFINITE = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_INFINITE");	//0001
	private static String GEN_PARAM_CC_WISDOM = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_WISDOM");		//0002
	private static String GEN_PARAM_CC_PREMIER = InfBatchProperty.getInfBatchConfig("GEN_PARAM_CC_PREMIER");	//0003
	private static String PERSONAL_TYPE_APPLICANT=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");	
	private static String PERSONAL_TYPE_SUPPLEMENTARY=InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");
	public static String NOTIFICATION_FINAL_APP_DECISION_APPROVE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_APPROVE");
	public static String NOTIFICATION_FINAL_APP_DECISION_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
	public static String NOTIFICATION_APPLICATION_STATUS_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	@Override
	public TemplateVariableDataM getTemplateVariable() throws Exception{
		TemplateVariableDataM templateVariable = null;
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		ArrayList<EmailRejectNCBFilterApplicationDataM> filterApplications = filterApplication(templateBuilderRequest.getRequestObject());
		if(!InfBatchUtil.empty(filterApplications)){
			TemplateDAO templateDAO = NotificationFactory.getTemplateDAO();
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
			for(EmailRejectNCBFilterApplicationDataM filterApplication : filterApplications){
				ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs = notificationDAO
						.findPersonalRejectNcb(filterApplication.getApplicationGroupId());
				EmailRejectNCBPersonalInfoDataM personalRejectNcb = findPersonalRejectNcb(personalRejectNcbs,filterApplication.getApplicationTemplate());
				if(!InfBatchUtil.empty(personalRejectNcb)){
					if(isValidRejectReason(filterApplication, personalRejectNcb)){
						ArrayList<EmailEODRejectNCBDataM> eodEmailRejectNcbs = templateDAO.getEodEmailRejectNcb(personalRejectNcb.getApplicationGroupId());
						if(!InfBatchUtil.empty(eodEmailRejectNcbs)){
							EmailEODRejectNCBDataM eodEmailRejectNcb = getEodEmailRejectNcb(eodEmailRejectNcbs);
							if(!InfBatchUtil.empty(eodEmailRejectNcb)){
								templateVariable = new TemplateVariableDataM();
								List<NotifyTask> uniqueIds = new ArrayList<NotifyTask>();
								uniqueIds.add(new NotifyTask(eodEmailRejectNcb.getApplicationGroupId(),filterApplication.getLifeCycle()));
								HashMap<String, Object>  hData = new HashMap<String, Object>();
									hData.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO, Formatter.displayText(eodEmailRejectNcb.getApplicationNo()));
									hData.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, Formatter.displayText(eodEmailRejectNcb.getPersonalNameTh()));
									hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, Formatter.displayText(eodEmailRejectNcb.getProductName()));
									hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH_EN, Formatter.displayText(eodEmailRejectNcb.getProductNameThEn()));
									hData.put(TemplateBuilderConstant.TemplateVariableName.REFERENCE_NO, Formatter.displayText(eodEmailRejectNcb.getReferenceNo()));
									hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN, Formatter.displayText(eodEmailRejectNcb.getProductNameEn()));
									hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH, Formatter.displayText(eodEmailRejectNcb.getProductNameTh()));
								templateVariable.setTemplateVariable(hData);
								templateVariable.setUniqueIds(uniqueIds);
							}
						}
					}
				}
			}
		}
		return templateVariable;
	}
	@SuppressWarnings("unchecked")
	private ArrayList<EmailRejectNCBFilterApplicationDataM> filterApplication(Object object){
		String NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER");
		String NOTIFICATION_APPLICATION_STATUS_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
		ArrayList<EmailRejectNCBFilterApplicationDataM> filterApplications = new ArrayList<EmailRejectNCBFilterApplicationDataM>();
		if(object instanceof ArrayList<?>){
			ArrayList<?> objectArr = (ArrayList<?>)object;
			if(objectArr.get(0) instanceof NotificationEODDataM){
				ArrayList<String> applicationGroupIds = new ArrayList<String>();
				ArrayList<NotificationEODDataM> notificationEods = (ArrayList<NotificationEODDataM>)object;
				for(NotificationEODDataM notificationEod :notificationEods){
					if(!applicationGroupIds.contains(notificationEod.getApplicationGroupId()) 
							&& notificationEod.getNotificationType().equals(NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER) 
							&& notificationEod.getAppStatus().equals(NOTIFICATION_APPLICATION_STATUS_REJECT)){
						applicationGroupIds.add(notificationEod.getApplicationGroupId());
						EmailRejectNCBFilterApplicationDataM filterApplication = new EmailRejectNCBFilterApplicationDataM();
							filterApplication.setApplicationGroupId(notificationEod.getApplicationGroupId());
							filterApplication.setApplicationTemplate(notificationEod.getApplicationTemplate());
							filterApplication.setSaleChannel(notificationEod.getSaleChannel());
							filterApplication.setRecommendChannel(notificationEod.getRecommendChannel());
							filterApplication.setAppStatus(notificationEod.getAppStatus());
							filterApplication.setLifeCycle(notificationEod.getLifeCycle());
							filterApplications.add(filterApplication);
					}
				}
			}
		}
		return filterApplications;
	}
	private EmailRejectNCBPersonalInfoDataM findPersonalRejectNcb(ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs,String templateId){
		if(!InfBatchUtil.empty(personalRejectNcbs)){
			if(InfBatchProperty.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(templateId) ||
				InfBatchProperty.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(templateId) ||
				InfBatchProperty.getGeneralParam(GEN_PARAM_CC_PREMIER).contains(templateId)){
				boolean isRejectAll = isNCBRejectAll(personalRejectNcbs);
				boolean isAddSupApplication= isRejectNCBSupplementaryApplication(personalRejectNcbs);
				 if(isRejectAll && !isAddSupApplication){
					 for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
						 if(PERSONAL_TYPE_APPLICANT.equals(personalRejectNcb.getPersonalType())){
							 return  personalRejectNcb;
						 }
					 }
				 }
			}else{
				//Case not  infinite wisdom premier check priority if still not send Email or sms will send Letter				
				//Reject All
				boolean isRejectAll = isNCBRejectAll(personalRejectNcbs);
				//MainApprove and SupReject
				boolean isMainApprove = isRejectNCBMainApprove(personalRejectNcbs);
				boolean isSupReject = isRejectNCBSupplementaryReject(personalRejectNcbs);
				//Case Add sup and Reject All
				boolean isAddSupApplication = isRejectNCBAddSupplementaryApplication(personalRejectNcbs);
				if(isAddSupApplication && isRejectAll){
					for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
						 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalRejectNcb.getPersonalType())){
							 return personalRejectNcb;
						 }
					 }
				}else{
					if(isMainApprove && isSupReject){							
						for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
							 if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalRejectNcb.getPersonalType())){
								  return personalRejectNcb;
							 }
						 }
					}else if(isRejectAll){ 
						for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
							 if(PERSONAL_TYPE_APPLICANT.equals(personalRejectNcb.getPersonalType())){
								 return personalRejectNcb;
							 }
						 }
					}
				}
			}
		}
		return null;
	}
	private boolean isNCBRejectAll(ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs){
		 int count=0;
		 if(!InfBatchUtil.empty(personalRejectNcbs)){
			 for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
				 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(personalRejectNcb.getFinalAppDecision())){
					 count++;
				 }
			 }
			 if(count==personalRejectNcbs.size()){
				 return true;
			 }
		 }
		 return false;
	}
	private boolean isRejectNCBSupplementaryApplication(ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs){
		if(!InfBatchUtil.empty(personalRejectNcbs)){
			 for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
				 if(PERSONAL_TYPE_APPLICANT.equals(personalRejectNcb.getPersonalType())){
//					 logger.debug("==is contain main applicant==");
					 return false;
				 }
			 }
		 }else{
			 return false;
		 }
		return true;
	}
	private boolean isRejectNCBMainApprove(ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs){
		if(!InfBatchUtil.empty(personalRejectNcbs)){
			 for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
				 if(NOTIFICATION_FINAL_APP_DECISION_APPROVE.equals(personalRejectNcb.getFinalAppDecision()) && PERSONAL_TYPE_APPLICANT.equals(personalRejectNcb.getFinalAppDecision())){
					 return true;
				 }
			 }
		}
		return false;
	}
	private boolean isRejectNCBSupplementaryReject(ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs){
		boolean result = false;
		if(!InfBatchUtil.empty(personalRejectNcbs)){
			 for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
				 if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(personalRejectNcb.getFinalAppDecision()) && PERSONAL_TYPE_SUPPLEMENTARY.equals(personalRejectNcb.getFinalAppDecision())){
					 return true;
				 }
			 }
		 }
		return false;
	}
	private boolean isRejectNCBAddSupplementaryApplication(ArrayList<EmailRejectNCBPersonalInfoDataM> personalRejectNcbs){
		 if(!InfBatchUtil.empty(personalRejectNcbs)){
			 for(EmailRejectNCBPersonalInfoDataM personalRejectNcb : personalRejectNcbs){
				 if(PERSONAL_TYPE_APPLICANT.equals(personalRejectNcb.getPersonalType())){
					return false;
				 }
			 }
		 }else{
			 return false;
		 }
		 return true;
	}
	private EmailEODRejectNCBDataM getEodEmailRejectNcb(ArrayList<EmailEODRejectNCBDataM> eodEmailRejectNcbs){
		EmailEODRejectNCBDataM emailEodRejectNCBApplicant = null;
		EmailEODRejectNCBDataM emailEodRejectNCBSupplementary = null;
		if(!InfBatchUtil.empty(eodEmailRejectNcbs)){
			for(EmailEODRejectNCBDataM eodEmailRejectNcb : eodEmailRejectNcbs){
				if(PERSONAL_TYPE_APPLICANT.equals(eodEmailRejectNcb.getPersonalType())){
					emailEodRejectNCBApplicant = eodEmailRejectNcb;
				}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(eodEmailRejectNcb.getPersonalType())){
					emailEodRejectNCBSupplementary = eodEmailRejectNcb;
				}
			}
		}
		if(InfBatchUtil.empty(emailEodRejectNCBApplicant)){
			return emailEodRejectNCBSupplementary;
		}
		return emailEodRejectNCBApplicant;
	}
	private boolean isValidRejectReason(EmailRejectNCBFilterApplicationDataM filterApplication,EmailRejectNCBPersonalInfoDataM personalRejectNcb){
		NotificationEODConfigDataM notificationEodConfig = (NotificationEODConfigDataM)getTemplateBuilderRequest().getConfigurationObject();
		if(null==notificationEodConfig){
			notificationEodConfig = new NotificationEODConfigDataM();
		}
		HashMap<String,ArrayList<String>> reasonDataHash = notificationEodConfig.getReasonData();
		if(!InfBatchUtil.empty(reasonDataHash)){
			String applicationTemplate = filterApplication.getApplicationTemplate();
			String saleChannel = filterApplication.getSaleChannel();
			String recommendChannel = filterApplication.getRecommendChannel();
			String rejectReasonId = applicationTemplate + "_" + saleChannel + "_" + recommendChannel;
			ArrayList<String> reasons = reasonDataHash.get(rejectReasonId);
			String reasonCode = personalRejectNcb.getReasonCode();
			String applicationStatus = filterApplication.getAppStatus();
			if(!InfBatchUtil.empty(reasons) && !InfBatchUtil.empty(reasonCode) && !InfBatchUtil.empty(applicationStatus)){
				if(reasons.contains(reasonCode) && NOTIFICATION_APPLICATION_STATUS_REJECT.equals(applicationStatus)){
					return true;
				}
			}
		}
		return false;
	}
}
