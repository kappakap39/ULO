package com.eaf.orig.ulo.control.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.model.util.ModelUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigHistoryDataDAO;
import com.eaf.orig.ulo.app.util.CardLinkCustomerUtil;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.control.util.model.ApplicationResultRequest;
import com.eaf.orig.ulo.control.util.model.ApplicationResultResponse;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.HistoryDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.google.gson.Gson;
//import java.util.Arrays;

public class ApplicationUtil {
	private static transient Logger logger = Logger.getLogger(ApplicationUtil.class);	
	static String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	static String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");	
	static String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");	
	static String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");	
	static String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");	
	static ArrayList<String> FINAL_APP_DECISION_CONDITION = SystemConstant.getArrayListConstant("SET_APPLICATION_FINAL_APP_DECISION_CONDITION");	
	static String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	static String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
	static String GEN_PARAM_CC_INFINITE = SystemConstant.getConstant("GEN_PARAM_CC_INFINITE");
	static String GEN_PARAM_CC_WISDOM = SystemConstant.getConstant("GEN_PARAM_CC_WISDOM");
	static String DEFAULT_REC_DECISION = SystemConstant.getConstant("DEFAULT_REC_DECISION");
	static String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	static String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	static String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	static String APPLICATION_TEMPLATE_INCREASE = SystemConstant.getConstant("APPLICATION_TEMPLATE_INCREASE");
	static String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	static String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	static String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	static String ROLE_IA = SystemConstant.getConstant("ROLE_IA");	
	static ArrayList<String> EAPP_SOURCE = SystemConfig.getArrayListGeneralParam("EAPP_SOURCE");
	static ArrayList<String> CJD_SOURCE = SystemConfig.getArrayListGeneralParam("CJD_SOURCE");
	public static String getLastCardType(String applicationGroupId,String hashCardNo){
		String lastCardType = "";
		try{
			logger.debug("hashCardNo >> "+hashCardNo);
			ApplicationGroupDataM applicationGroup = getHistoryApplicationGroup(applicationGroupId,ROLE_IA);
			if(null != applicationGroup){
				List<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
				if(!Util.empty(applications)){
					for (ApplicationDataM application : applications) {
						CardDataM card = application.getCard();
						if(null != card){
							logger.debug("card.getHashingCardNo() >> "+card.getHashingCardNo());
							if(null != card.getHashingCardNo() && card.getHashingCardNo().equals(hashCardNo)){
								lastCardType = card.getCardType();
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		logger.debug("lastCardType >> "+lastCardType);
		return lastCardType;
	}
	public static ApplicationResultResponse getApplicationResult(ApplicationResultRequest applicationResultRequest){
		ApplicationResultResponse applicationResultResponse = new ApplicationResultResponse();
		try{
			String applicationGroupId = applicationResultRequest.getApplicationGroupId();
			String product = applicationResultRequest.getProduct();
			logger.debug("applicationGroupId >> "+applicationGroupId);
			logger.debug("product >> "+product);
			ApplicationGroupDataM applicationGroup = getHistoryApplicationGroup(applicationGroupId,ROLE_IA);
			if(null != applicationGroup){
				List<ApplicationDataM> applications = applicationGroup.filterListApplicationProductLifeCycle(product);
				if(!Util.empty(applications)){
					if(applications.size() == 1){
						ApplicationDataM application = applications.get(0);
						if(null != application){
							
							if(!APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(getCardApplicationType(application))){
								applicationResultResponse.setApplyType(application.getApplicationType());
							}						
							applicationResultResponse.setProjectCode(application.getProjectCode());
							applicationResultResponse.setRecommendDecision(application.getRecommendDecision());
						}
					}else{
						if(PRODUCT_CRADIT_CARD.equals(product)){
							for (ApplicationDataM application : applications) {
								CardDataM card = application.getCard();
								if(null != card && null != card.getCardType() && null != card.getCardLevel() 
										&& card.getCardType().equals(applicationResultRequest.getCardType()) 
											&& card.getCardLevel().equals(applicationResultRequest.getCardLevel())){
									if(!APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(getCardApplicationType(application))){
										applicationResultResponse.setApplyType(application.getApplicationType());
									}
									
									applicationResultResponse.setProjectCode(application.getProjectCode());
									applicationResultResponse.setRecommendDecision(application.getRecommendDecision());
								}
							}
						}else if(PRODUCT_K_EXPRESS_CASH.equals(product)){
							for (ApplicationDataM application : applications) {
								CardDataM card = application.getCard();
								if(null != card && null != card.getCardType() 
										&& card.getCardType().equals(applicationResultRequest.getCardType())){
									applicationResultResponse.setApplyType(application.getApplicationType());
									applicationResultResponse.setProjectCode(application.getProjectCode());
									applicationResultResponse.setRecommendDecision(application.getRecommendDecision());
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		if(Util.empty(applicationResultResponse.getApplyType())){
			applicationResultResponse.setApplyType(applicationResultRequest.getApplyType());
		}
		if(Util.empty(applicationResultResponse.getRecommendDecision())){
			applicationResultResponse.setRecommendDecision(DEFAULT_REC_DECISION);
		}
		logger.debug(applicationResultResponse);
		return applicationResultResponse;		
	}
	
	public static ArrayList<ApplicationDataM> getApplicationCCProducts(ApplicationGroupDataM applicationGroup){
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		try {
			String APPLICATION_TYPE = applicationGroup.getApplicationType();
			if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)){
				filterApplication = applicationGroup.filterApplicationCardTypeLifeCycle(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_SUPPLEMENTARY);
			}else{
				filterApplication = applicationGroup.filterApplicationCardTypeLifeCycle(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_BORROWER);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}		
		return filterApplication;
	}
	
	public static ApplicationGroupDataM getHistoryApplicationGroup(String applicationGroupId,String role){
//		String prevRoleId = getLastRole(role);
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("role >> "+role);
//		logger.debug("prevRoleId >> "+prevRoleId);
		try{
			OrigHistoryDataDAO historyDAO = ORIGDAOFactory.getHistoryDataDAO();
			if(!Util.empty(role)){
				HistoryDataM historyM = historyDAO.loadOrigHistoryDataM(applicationGroupId, role);
				if(null != historyM){
					Gson gson = new Gson();
					String appData = historyM.getAppData();
					logger.debug("appData >> "+appData);
					ApplicationGroupDataM historyApplicationGroup = gson.fromJson(historyM.getAppData(), ApplicationGroupDataM.class);
					return historyApplicationGroup;
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return null;
	}
	
	public static String getProjectCodeFromHistory(ApplicationDataM application, ApplicationGroupDataM historyApplicationGroup){
		if(null != historyApplicationGroup) {
			List<ApplicationDataM> historyApplications = historyApplicationGroup.filterListApplicationProductLifeCycle(application.getProduct());
			if(!Util.empty(historyApplications)) {
				String projectCode = null;
				if(historyApplications.size() == 1) {
					projectCode = historyApplications.get(0).getProjectCode();
				}else{							
					CardDataM cardData = application.getCard();
					if(null != cardData && !Util.empty(cardData.getCardType())){
						for(ApplicationDataM historyApplication: historyApplications){
							CardDataM cardM = historyApplication.getCard();
							if(cardData.getCardType().equals(cardM.getCardType())){
								projectCode = historyApplication.getProjectCode();
							}
						}
					}
					if(Util.empty(projectCode)) {
						projectCode = historyApplications.get(0).getProjectCode();
					}
				}
				return projectCode;
			}
		}
		return null;
	}
	
	private static String getLastRole(String roleId){
		String[] SENSITIVE_ROLE_MAPPING = SystemConstant.getArrayConstant("SENSITIVE_ROLE_MAPPING");
		if(null != SENSITIVE_ROLE_MAPPING){
			for (String ROLE_MAPPING : SENSITIVE_ROLE_MAPPING) {
				String CURRENT_ROLE = ROLE_MAPPING.split("\\|")[0];
				String LAST_ROLE = ROLE_MAPPING.split("\\|")[1];
				if(CURRENT_ROLE.equals(roleId)){
					return LAST_ROLE;
				}
			}
		}
		return "";
	}
	
	public static HashMap<String, CompareDataM> getCISComparisonField(HttpServletRequest request){
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		return applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
	}
	
	public static void setApplicationFinalDecision(ArrayList<ApplicationDataM>  applications){
		 try {
			 if(!Util.empty(applications)){
				 for(ApplicationDataM applicationsDataM : applications){
					 ORIGDAOFactory.getApplicationDAO().setApplicationFinalDecision(applicationsDataM,FINAL_APP_DECISION_CONDITION);
					 ArrayList<ReasonDataM> reasons = ORIGDAOFactory.getReasonDAO().loadOrigReasonM(applicationsDataM.getApplicationRecordId());
					 ArrayList<String> reasonCodeApp = applicationsDataM.getReasonCodes();
					 if(!Util.empty(reasons)){
						 for(ReasonDataM reason : reasons){
							 if(!Util.empty(reason.getReasonCode()) && !reasonCodeApp.contains(reason.getReasonCode())){
								 applicationsDataM.addReason(reason);
							 }
						 }
					 }	
				 }
			 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	
	public static String getPaymentMethodUniqueId(String personalType,String product){
		return personalType+"_"+product;
	}	
	public static String getAdditionalServiceUniqueId(String personalType,String product){
		return personalType+"_"+product;
	}
	
	public static void clearNotMatchApplicationRelation(ApplicationGroupDataM applicationGroup){
		String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
		String applicationType = applicationGroup.getApplicationType();
		ArrayList<String> personalIds = applicationGroup.getPersonalIds();
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		for (Iterator<ApplicationDataM> iterator = applications.iterator(); iterator.hasNext();) {
			ApplicationDataM application = iterator.next();
			String applicationRecordId = application.getApplicationRecordId();
			PersonalRelationDataM personalRelation =  applicationGroup.getPersonalRelationByRefId(applicationRecordId);
			if(null != personalRelation){
				String personalId = personalRelation.getPersonalId();
				if(!personalIds.contains(personalId)){
					iterator.remove();
				}
			}else{
				iterator.remove();
			}			
		}		
		ArrayList<String> applicationIds = applicationGroup.getApplicationIds();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
				if(null != personalRelations){
					for (Iterator<PersonalRelationDataM> iterator = personalRelations.iterator(); iterator.hasNext();) {
						PersonalRelationDataM personalRelation = iterator.next();
						String applicationRecordId = personalRelation.getRefId();
						if(!applicationIds.contains(applicationRecordId)){
							iterator.remove();
						}
					}
				}
			}
		}
		
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
			ArrayList<PaymentMethodDataM> paymentMethods = applicationGroup.getPaymentMethods();
			if(null != paymentMethods){
				for (Iterator<PaymentMethodDataM> iterator = paymentMethods.iterator(); iterator.hasNext();) {
					PaymentMethodDataM paymentMethod = iterator.next();
					if(!personalIds.contains(paymentMethod.getPersonalId())){
						iterator.remove();
					}
				}
			}
			ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = applicationGroup.getSpecialAdditionalServices();
			if(null != specialAdditionalServices){
				for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
					SpecialAdditionalServiceDataM specialAdditionalService = iterator.next();	
					if(!personalIds.contains(specialAdditionalService.getPersonalId())){
						iterator.remove();
					}
				}
			}
		}else{
			ArrayList<String> paymentMethodIds = applicationGroup.getPaymentMethodIds();
			ArrayList<PaymentMethodDataM> paymentMethods = applicationGroup.getPaymentMethods();
			if(null != paymentMethods){
				for (Iterator<PaymentMethodDataM> iterator = paymentMethods.iterator(); iterator.hasNext();) {
					PaymentMethodDataM paymentMethod = iterator.next();
					if(Util.empty(paymentMethodIds)){
						iterator.remove();
					}else{
						if(!paymentMethodIds.contains(paymentMethod.getPaymentMethodId())){
							iterator.remove();
						}
					}
				}
			}
			ArrayList<String> specialAdditionalServiceIds = applicationGroup.getSpecialAdditionalServiceIds();
			ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = applicationGroup.getSpecialAdditionalServices();
			if(null != specialAdditionalServices){
				for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
					SpecialAdditionalServiceDataM specialAdditionalService = iterator.next();
					if(Util.empty(specialAdditionalServiceIds)){
						iterator.remove();
					}else{
						if(!specialAdditionalServiceIds.contains(specialAdditionalService.getServiceId())){
							iterator.remove();
						}
					}
				}
			}
		}
	}
	public static void clearNotMatchApplicationRelation(PersonalApplicationInfoDataM personalApplicationInfo){
		String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
		String applicationType = personalApplicationInfo.getApplicationType();
		String personalId = personalApplicationInfo.getPersonalId();		
		ArrayList<String> applicationIds = personalApplicationInfo.getApplicationIds();
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if(null != personalRelations){
			for (Iterator<PersonalRelationDataM> iterator = personalRelations.iterator(); iterator.hasNext();) {
				PersonalRelationDataM personalRelation = iterator.next();
				String applicationRecordId = personalRelation.getRefId();
				if(!applicationIds.contains(applicationRecordId)){
					iterator.remove();
				}
			}
		}
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
			ArrayList<PaymentMethodDataM> paymentMethods = personalApplicationInfo.getPaymentMethods();
			if(null != paymentMethods){
				for (Iterator<PaymentMethodDataM> iterator = paymentMethods.iterator(); iterator.hasNext();) {
					PaymentMethodDataM paymentMethod = iterator.next();
					if(null != personalId && !personalId.equals(paymentMethod.getPersonalId())){
						iterator.remove();
					}
				}
			}
			ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = personalApplicationInfo.getSpecialAdditionalServices();
			if(null != specialAdditionalServices){
				for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
					SpecialAdditionalServiceDataM specialAdditionalService = iterator.next();	
					if(null != personalId && !personalId.equals(specialAdditionalService.getPersonalId())){
						iterator.remove();
					}
				}
			}
		}else{
			ArrayList<String> paymentMethodIds = personalApplicationInfo.getPaymentMethodIds();
			ArrayList<PaymentMethodDataM> paymentMethods = personalApplicationInfo.getPaymentMethods();
			if(null != paymentMethods){
				for (Iterator<PaymentMethodDataM> iterator = paymentMethods.iterator(); iterator.hasNext();) {
					PaymentMethodDataM paymentMethod = iterator.next();
					if(Util.empty(paymentMethodIds)){
						iterator.remove();
					}else{
						if(!paymentMethodIds.contains(paymentMethod.getPaymentMethodId())){
							iterator.remove();
						}
					}
				}
			}
			ArrayList<String> specialAdditionalServiceIds = personalApplicationInfo.getSpecialAdditionalServiceIds();
			ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = personalApplicationInfo.getSpecialAdditionalServices();
			if(null != specialAdditionalServices){
				for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
					SpecialAdditionalServiceDataM specialAdditionalService = iterator.next();
					if(Util.empty(specialAdditionalServiceIds)){
						iterator.remove();
					}else{
						if(!specialAdditionalServiceIds.contains(specialAdditionalService.getServiceId())){
							iterator.remove();
						}
					}
				}
			}
		}
	}
	public static String[] getCashTranferTypes(){
		String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
		String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
		String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};	
		return cashTransFerType;
	}	
	public static String getApplicationGroupNo(ApplicationGroupDataM applicationGroup){
		StringBuilder applicationGroupNo = new StringBuilder();
		applicationGroupNo.append(applicationGroup.getApplicationGroupNo());
		if(1 < applicationGroup.getMaxLifeCycle()){
			applicationGroupNo.append("/")
				.append("V"+(applicationGroup.getMaxLifeCycle()-1));
		}
		return applicationGroupNo.toString();
	}	
	public static String getApplicationGroupNo(HashMap<String, Object> tableResult){
		StringBuilder applicationGroupNo = new StringBuilder();		
		String applicationGroupNoValue = (String)tableResult.get("APPLICATION_GROUP_NO");
		int maxLifeCycle = tableResult.get("MAX_LIFE_CYCLE") == null ? 0 
				: Integer.parseInt(tableResult.get("MAX_LIFE_CYCLE").toString());
		applicationGroupNo.append(applicationGroupNoValue);
		if(1 < maxLifeCycle){
			applicationGroupNo.append("/")
				.append("V"+(maxLifeCycle-1));
		}
		return applicationGroupNo.toString();
	}
	public static void defaultCardLinkCustId(ApplicationGroupDataM applicationGroup){
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null != personalInfos){
			for(PersonalInfoDataM personalInfo:personalInfos){
				ArrayList<CardlinkCustomerDataM> cardLinkCustomers = personalInfo.getCardLinkCustomers();
				if(null != cardLinkCustomers){
					for (CardlinkCustomerDataM cardlinkCustomer : cardLinkCustomers) {
						String cardlinkCustId = cardlinkCustomer.getCardlinkCustId();
						if(Util.empty(cardlinkCustId)){
							cardlinkCustId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARDLINK_CUSTOMER_PK);
							cardlinkCustomer.setCardlinkCustId(cardlinkCustId);
						}
					}
				}
			}
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(null != applications){
			for(ApplicationDataM application:applications){
				String applicationRecordId = application.getApplicationRecordId();
				CardlinkCustomerDataM cardLinkCustomer = getCardlinkCustomer(applicationGroup, applicationRecordId);
				if(null != cardLinkCustomer){
					String cardlinkCustId = cardLinkCustomer.getCardlinkCustId();
					CardDataM card = application.getCard();
					if(null != card){
						card.setCardlinkCustId(cardlinkCustId);
					}
				}
			}
		}
	}
	public static CardlinkCustomerDataM getCardlinkCustomer(ApplicationGroupDataM applicationGroup,String applicationRecordId){
		return CardLinkCustomerUtil.getCardlinkCustomer(applicationGroup, applicationRecordId);
	}
	
	
	public static void setAdditionalService(ApplicationGroupDataM applicationGroup){
		String templateType = applicationGroup.getApplicationTemplate();
		logger.debug("templateType>>"+templateType);
		PersonalInfoDataM personalInfo= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		SpecialAdditionalServiceDataM emailServiceCC = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfo.getPersonalId()
													  , PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_EMAIL);

		
		if(SystemConfig.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(templateType) ||
		    SystemConfig.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(templateType)){
			//Select Additional Email but email is blank
			if(!Util.empty(emailServiceCC) && Util.empty(personalInfo.getEmailPrimary())){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalInfo.getPersonalId(), PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_EMAIL);
			}
		}else{
			SpecialAdditionalServiceDataM postalServiceCC = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfo.getPersonalId()
				     										, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_POSTAL);
			
			ArrayList<ApplicationDataM> applicationsCC = applicationGroup.filterApplicationLifeCycle(PRODUCT_CRADIT_CARD);
			if(!Util.empty(applicationsCC)){
				if(Util.empty(personalInfo.getEmailPrimary()) && Util.empty(postalServiceCC)){
					SpecialAdditionalServiceDataM postalService = new SpecialAdditionalServiceDataM();
					String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
					postalService.init(serviceId);
					postalService.setPersonalId(personalInfo.getPersonalId());
					postalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_POSTAL);
					applicationGroup.addSpecialAdditionalService(postalService);
					logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
					
					for(ApplicationDataM application : applicationsCC){
						application.addAdditionalServiceId(postalService.getServiceId());
					}
				}else if(!Util.empty(personalInfo.getEmailPrimary()) && Util.empty(postalServiceCC)){
					SpecialAdditionalServiceDataM postalService = new SpecialAdditionalServiceDataM();
					String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
					postalService.init(serviceId);
					postalService.setPersonalId(personalInfo.getPersonalId());
					postalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_EMAIL);
					applicationGroup.addSpecialAdditionalService(postalService);
					logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_EMAIL);
					
					for(ApplicationDataM application : applicationsCC){
						application.addAdditionalServiceId(postalService.getServiceId());
					}
				}
			}
			
			
			SpecialAdditionalServiceDataM postalServiceKEC = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfo.getPersonalId()
					, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_POSTAL);
			ArrayList<ApplicationDataM> applicationsKEC = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
			if(!Util.empty(applicationsKEC)){
				if(Util.empty(personalInfo.getEmailPrimary()) && Util.empty(postalServiceKEC)){
					SpecialAdditionalServiceDataM postalService = new SpecialAdditionalServiceDataM();
					String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
					postalService.init(serviceId);
					postalService.setPersonalId(personalInfo.getPersonalId());
					postalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_POSTAL);
					applicationGroup.addSpecialAdditionalService(postalService);
					logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
					
					for(ApplicationDataM application : applicationsKEC){
						application.addAdditionalServiceId(postalService.getServiceId());
					}
				}else if(!Util.empty(personalInfo.getEmailPrimary()) && Util.empty(postalServiceKEC)){
					SpecialAdditionalServiceDataM postalService = new SpecialAdditionalServiceDataM();
					String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
					postalService.init(serviceId);
					postalService.setPersonalId(personalInfo.getPersonalId());
					postalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_EMAIL);
					applicationGroup.addSpecialAdditionalService(postalService);
					logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_EMAIL);
					
					for(ApplicationDataM application : applicationsKEC){
						application.addAdditionalServiceId(postalService.getServiceId());
					}
				}
			}


		}
		
	}
	
	private static String getCardApplicationType(ApplicationDataM applicationDataM){
		if(!Util.empty(applicationDataM.getLoan()) && !Util.empty(applicationDataM.getLoan().getCard())){
			return applicationDataM.getLoan().getCard().getApplicationType();
		}			
		return "";
	}
	
	public static HashMap<String, String>  getGuidelineNameValues(ArrayList<GuidelineDataM> guidelines,HttpServletRequest request){
		HashMap<String,String> guideLineNames = new HashMap<String,String>();
		if(!Util.empty(guidelines)) {
			for(GuidelineDataM guidelineM : guidelines) {
				if(!ModelUtil.empty(guidelineM.getName())){
					   String nameDesc = LabelUtil.getText(request, "GUIDELINE_NAME_"+guidelineM.getName().trim());
					   if(Util.empty(nameDesc)){
					      nameDesc=guidelineM.getName();
					   }
					   String guidlineValue = guidelineM.getValue();
					   String preFixData = SystemConstant.getConstant(guidelineM.getName()+"_POST_VALUE");
					   if(!Util.empty(preFixData)){
						   guidlineValue += preFixData;
					   }
					   guideLineNames.put(nameDesc, guidlineValue);
					   
				}
			}
			return guideLineNames;
		}
		return null;
	}
	
	public static boolean isRejectDecision(ApplicationGroupDataM applicationGroup){
		boolean rejectAction = false;
		String  DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		int applicationSize = applications.size();
		int rejectSize = 0;
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
					rejectSize++;
				}
			}
		}
		logger.debug(">>>applicationSize>>>"+applicationSize);
		logger.debug(">>>rejectSize>>>"+rejectSize);
		if(applicationSize == rejectSize){
			rejectAction = true;
		}
		return rejectAction;		
	}
	
	public static void setPolicyRuleToApplication(ApplicationGroupDataM applicationGroup,ApplicationDataM application,String roleId,PersonalInfoDataM personalInfo){
		if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
			ArrayList<ReasonLogDataM> reasonLogs = new ArrayList<ReasonLogDataM>();
			ArrayList<ReasonDataM> reasonsList = new ArrayList<ReasonDataM>();
//			if(!Util.empty(applicationGroup.getPersonalInfoApplication(application.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL))){
//					PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoApplication(application.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL);
					if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) 
							&& !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
						ArrayList<PolicyRulesDataM> personalPolicyRules = personalInfo.getVerificationResult().getPolicyRules();
						ArrayList<PolicyRulesDataM>	applicationPolicyRules = new ArrayList<PolicyRulesDataM>();
						if(!Util.empty(personalPolicyRules)){
							for(PolicyRulesDataM personalPolicyRule:personalPolicyRules){
								PolicyRulesDataM policyRule = new PolicyRulesDataM();
								policyRule.setRank(personalPolicyRule.getRank());
								policyRule.setPolicyCode(personalPolicyRule.getPolicyCode());
								policyRule.setCreateBy(personalPolicyRule.getCreateBy());
								policyRule.setCreateDate(personalPolicyRule.getCreateDate());
								policyRule.setUpdateBy(personalPolicyRule.getUpdateBy());
								policyRule.setUpdateDate(personalPolicyRule.getUpdateDate());
								policyRule.setOrPolicyRules(setOrPolicyRule(personalPolicyRule.getOrPolicyRules()));
								policyRule.setPolicyRulesConditions(setPolicyRulesConditions(personalPolicyRule.getPolicyRulesConditions()));
								policyRule.setOverrideFlag(personalPolicyRule.getOverrideFlag());
								policyRule.setResult(personalPolicyRule.getResult());
								policyRule.setVerifiedResult(personalPolicyRule.getVerifiedResult());
								policyRule.setReason(personalPolicyRule.getReason());
								applicationPolicyRules.add(policyRule);
								
								if(!Util.empty(personalPolicyRule.getReason())){
									ReasonDataM reason = new ReasonDataM();
									reason.setApplicationGroupId(applicationGroup.getApplicationGroupId());
									reason.setApplicationRecordId(application.getApplicationRecordId());
									reason.setReasonCode(personalPolicyRule.getReason());
									reason.setReasonType(SystemConstant.getConstant("RECOMMEND_DECISION_REJECTED"));
									reasonsList.add(reason);
									
									ReasonLogDataM reasonLog = new ReasonLogDataM();
									reasonLog.setApplicationGroupId(applicationGroup.getApplicationGroupId());
									reasonLog.setApplicationRecordId(application.getApplicationRecordId());
									reasonLog.setReasonCode(personalPolicyRule.getReason());
									reasonLog.setReasonType(SystemConstant.getConstant("RECOMMEND_DECISION_REJECTED"));
									reasonLogs.add(reasonLog);
								}
								
							}
						}
						if(!Util.empty(reasonLogs) && !Util.empty(reasonsList)){
							application.setReasons(reasonsList);
							application.setReasonLogs(reasonLogs);
						}
						VerificationResultDataM verification = new VerificationResultDataM();
						verification.setPolicyRules(applicationPolicyRules);
						application.setVerificationResult(verification);
						application.setFinalAppDecision(DECISION_FINAL_DECISION_REJECT);
						application.setRecommendDecision(FINAL_APP_DECISION_REJECT);
						application.setIsVetoEligibleFlag(applicationGroup.getIsVetoEligible());
					}
			}
		}
		
	private static ArrayList<PolicyRulesConditionDataM> setPolicyRulesConditions(ArrayList<PolicyRulesConditionDataM> policyRulesConditions){
		ArrayList<PolicyRulesConditionDataM> policyRulesConditionsApplications = new ArrayList<PolicyRulesConditionDataM>();
		if(!Util.empty(policyRulesConditions)){
			for(PolicyRulesConditionDataM policyRulesCondition : policyRulesConditions){
				PolicyRulesConditionDataM policyRulesConditionsApplication = new PolicyRulesConditionDataM();
				policyRulesConditionsApplication.setPolicyRulesId(policyRulesCondition.getPolicyRulesId());
				policyRulesConditionsApplication.setConditionCode(policyRulesCondition.getConditionCode());
				policyRulesConditionsApplication.setCreateBy(policyRulesCondition.getCreateBy());
				policyRulesConditionsApplication.setUpdateBy(policyRulesCondition.getUpdateBy());
				policyRulesConditionsApplication.setUpdateDate(policyRulesCondition.getUpdateDate());
				policyRulesConditionsApplications.add(policyRulesConditionsApplication);
			}
		}
		return policyRulesConditionsApplications;
	}
	public static boolean isGenerateMemberShipNo(String applicationType,String ApplicationTemplate,String applicationCardType){
		return !APPLICATION_TYPE_INCREASE.equals(applicationType) 
				&& !(SUPPLEMENTARY.equals(applicationCardType)&&APPLICATION_TEMPLATE_INCREASE.equals(ApplicationTemplate));
	}
	private static ArrayList<ORPolicyRulesDataM> setOrPolicyRule(ArrayList<ORPolicyRulesDataM> orpolicyrules){
		ArrayList<ORPolicyRulesDataM> orpolicyruleApplications = new ArrayList<ORPolicyRulesDataM>();
		if(!Util.empty(orpolicyrules)){
			for(ORPolicyRulesDataM orpolicyrule : orpolicyrules){
				ORPolicyRulesDataM orpolicyruleApplication = new ORPolicyRulesDataM();
				orpolicyruleApplication.setPolicyRulesId(orpolicyrule.getPolicyRulesId());
				orpolicyruleApplication.setReason(orpolicyrule.getReason());
				orpolicyruleApplication.setPolicyCode(orpolicyrule.getPolicyCode());
				orpolicyruleApplication.setResultDesc(orpolicyrule.getResult());
				orpolicyruleApplication.setVerifiedResult(orpolicyrule.getVerifiedResult());
				orpolicyruleApplication.setCreateBy(orpolicyrule.getCreateBy());
				orpolicyruleApplication.setCreateDate(orpolicyrule.getCreateDate());
				orpolicyruleApplication.setUpdateBy(orpolicyrule.getUpdateBy());
				orpolicyruleApplication.setMinApprovalAuth(orpolicyrule.getMinApprovalAuth());
				orpolicyruleApplication.setAppRecordId(orpolicyrule.getAppRecordId());
				orpolicyruleApplications.add(orpolicyruleApplication);
			}
		}
		return orpolicyruleApplications;
	}
	public static void reGenerateApplicationNo(ApplicationGroupDataM applicationGroup){
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			Collections.sort(applications, new Comparator<ApplicationDataM>(){
				@Override
				public int compare(ApplicationDataM application1, ApplicationDataM application2){
					 //return application1.getApplicationNo().compareTo(application2.getApplicationNo());
					String applicationRecordId1 = application1.getApplicationRecordId();
					String applicationRecordId2 = application2.getApplicationRecordId();
					if(!Util.empty(applicationRecordId1) && !Util.empty(applicationRecordId2)){
						return applicationRecordId1.compareTo(applicationRecordId2);
					}else{
						return 99;
					}
				}
			});
			int index = 0;
			for(ApplicationDataM application : applications){
				if(!Util.empty(application)){
					String applicationNo = GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo(),(index++)+1,applicationGroup.getMaxLifeCycle());
					application.setApplicationNo(applicationNo);
				}
			}
		}
	}
	
	//KPL Addtional
	public static boolean isClaim(ApplicationGroupDataM applicationGroup)
	{
		if(applicationGroup != null 
			&& applicationGroup.getClaimBy() != null 
			&& applicationGroup.getClaimBy().isEmpty())
		{return true;}
		return false;
	}
	public static boolean eApp(String source){
		if(SystemConfig.containsGeneralParam("EAPP_SOURCE", source)){
			return true;
		}
		return false;
	}
	public static boolean cjd(String source){
		if(SystemConfig.containsGeneralParam("CJD_SOURCE", source)){
			return true;
		}
		return false;
	}
	public static String eAppSQL(String fieldCondition) {
 		StringBuilder condition = new StringBuilder();
 		if ( null != fieldCondition && null != EAPP_SOURCE && EAPP_SOURCE.size() > 0 ) {
 			condition.append( fieldCondition.concat(" ") );
 			int count = 0;
 			String COMMA = ",";
 			if ( null != EAPP_SOURCE && EAPP_SOURCE.size() > 0 ) {
 				for ( String source : EAPP_SOURCE ) {
 					if ( source.length() > 0 )
						condition.append("'" + source + "'");
					if ( count != EAPP_SOURCE.size() -1 ) {
						condition.append(COMMA);
					}
					count++;
				}
 			}
			if ( fieldCondition.indexOf("(") != -1 ) {
				condition.append(" ) ");
			}
 		}
 		return condition.toString();
 	}
	public static String cjdSQL(String fieldCondition) {
		StringBuilder condition = new StringBuilder();
 		if ( null != fieldCondition && null != CJD_SOURCE && CJD_SOURCE.size() > 0 ) {
 			condition.append( fieldCondition.concat(" ") );
 			int count = 0;
 			String COMMA = ",";
 			if ( null != CJD_SOURCE && CJD_SOURCE.size() > 0 ) {
 				for ( String source : CJD_SOURCE ) {
 					if ( source.length() > 0 )
						condition.append("'" + source + "'");
					if ( count != CJD_SOURCE.size() -1 ) {
						condition.append(COMMA);
					}
					count++;
				}
 			}
			if ( fieldCondition.indexOf("(") != -1 ) {
				condition.append(" ) ");
			}
 		}
 		return condition.toString();
	}
	public static long getDiffDay(Date firstDate, Date lastDate)
	  {
	    if ((firstDate != null) && (lastDate != null))
	    {
	      long DIFF_TIME = firstDate.getTime() - lastDate.getTime();
	      long DIFF_DAY = DIFF_TIME / 86400000L;
	      return DIFF_DAY;
	    }
	    return 0L;
	  }
}
