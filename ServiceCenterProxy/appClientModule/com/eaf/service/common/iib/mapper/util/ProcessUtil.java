package com.eaf.service.common.iib.mapper.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.DebtInfo;
import com.ava.flp.eapp.iib.model.IncomeScreens;
import com.ava.flp.eapp.iib.model.Loans;
import com.ava.flp.eapp.iib.model.PolicyRules;
import com.ava.flp.eapp.iib.model.ReferCriterias;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM.VerifyId;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.service.common.util.ServiceUtil;

public class ProcessUtil {
	
	private static transient Logger logger = Logger.getLogger(ProcessUtil.class);
	public static final String APPLY_TYPE_NEW = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_NEW");
	public static final String APPLY_TYPE_ADD = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_ADD");
	public static final String APPLY_TYPE_UPGRADE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_UPGRADE");
	public static final String APPLY_TYPE_INCREASE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_INCREASE");
	public static final String APPLY_TYPE_SUPPLIMENTARY = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_ADD_SUP");
	public static final String APPLICATION_TYPE_ERROR = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_ERROR");
	public static final String APPLY_TYPE_ERROR_CODE = DecisionServiceCacheControl.getConstant("APPLY_TYPE_ERROR_CODE");
	public static final String APPLY_TYPE_EXP = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_EXP");
	public static final String CIDTYPE_PASSPORT = DecisionServiceCacheControl.getConstant("CIDTYPE_PASSPORT");
	public static String IM_PERSONAL_TYPE_APPLICANT = DecisionServiceCacheControl.getConstant("IM_PERSONAL_TYPE_APPLICANT");	
	public static String IM_PERSONAL_TYPE_SUPPLEMENTARY = DecisionServiceCacheControl.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");	
	public static final String JOBSTATE_REJECT = DecisionServiceCacheControl.getConstant("JOBSTATE_REJECT");
	public static final String JOBSTATE_APPROVE = DecisionServiceCacheControl.getConstant("JOBSTATE_APPROVE");	
	public static final String JOBSTATE_PENDING_FULLFRAUD = DecisionServiceCacheControl.getConstant("JOBSTATE_PENDING_FULLFRAUD");	
	public static final String APPLICATION_STATIC_APPROVED = DecisionServiceCacheControl.getConstant("APPLICATION_STATIC_APPROVED");
	public static final String APPLICATION_STATIC_REJECTED = DecisionServiceCacheControl.getConstant("APPLICATION_STATIC_REJECTED");
	public static final String DECISION_REQUIRED_FLAG = "M";
	
	public static final String YES_FLAG = "Y";
	public static final String NO_FLAG = "N";

	public static String  FILED_ID_REC_APP_DECISION = DecisionServiceCacheControl.getConstant("FIELD_ID_FICO_RECOMMEND_DECISION");//139
	public static String  FIELD_ID_DATA_VALIDATION_STATUS = DecisionServiceCacheControl.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	public static String  FIELD_ID_FINAL_RESULT = DecisionServiceCacheControl.getConstant("FIELD_ID_FINAL_RESULT");//58
	public static String  REC_APP_DECISION_APPROVE = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_APPPROVE");
	public static String  REC_APP_DECISION_REJECT = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_REJECTED");
	public static String  REC_APP_DECISION_PRE_APPROVE = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_PRE_APPPROVE");
	
	public static String  FINAL_APP_DECISION_APPROVE = DecisionServiceCacheControl.getConstant("RECOMMEND_DECISION_APPROVED");// Inconsistent naming
	public static String  FINAL_APP_DECISION_REJECT = DecisionServiceCacheControl.getConstant("RECOMMEND_DECISION_REJECTED");// Inconsistent naming
	public static String  FINAL_APP_DECISION_PRE_APPROVE = DecisionServiceCacheControl.getConstant("RECOMMEND_DECISION_PRE_APPROVE");// Inconsistent naming
	
	public static String  FIELD_ID_CARD_TYPE = DecisionServiceCacheControl.getConstant("FIELD_ID_CARD_TYPE");
	public static String  CACHE_CARD_INFO = DecisionServiceCacheControl.getConstant("CACHE_CARD_INFO");
	public static String  SPECIAL_ADDITIONAL_SERVICE_ATM = DecisionServiceCacheControl.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
	
	public static String verRequiredCode = DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_REQUIRED");
	public static  String verStatusFieldId =DecisionServiceCacheControl.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	public static  String verRequiredDesc = DecisionServiceCacheControl.getName(verStatusFieldId, verRequiredCode);
	public static  String verWaivedCode = DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_WAIVED");
	public static  String verWaivedDesc = DecisionServiceCacheControl.getName(verStatusFieldId, verWaivedCode);	
	
	public static String  APPLICATION_CARD_TYPE_BORROWER = DecisionServiceCacheControl.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	public static String  APPLICATION_CARD_TYPE_SUPPLEMENTARY = DecisionServiceCacheControl.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	
	public static String  PERSONAL_RELATION_APPLICATION_LEVEL = DecisionServiceCacheControl.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	public static String SPECIAL_ADDITIONAL_SERVICE_POSTAL = DecisionServiceCacheControl.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	public static String SPECIAL_ADDITIONAL_SERVICE_EMAIL = DecisionServiceCacheControl.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
	
	public static String GEN_PARAM_CC_INFINITE = DecisionServiceCacheControl.getConstant("GEN_PARAM_CC_INFINITE");
	public static String GEN_PARAM_CC_WISDOM = DecisionServiceCacheControl.getConstant("GEN_PARAM_CC_WISDOM");
	
	public static String PERSONAL_TYPE_APPLICANT = DecisionServiceCacheControl.getConstant("PERSONAL_TYPE_APPLICANT");
	
	public class PERSONAL_TYPE{
		public static final String APPLICANT="A";
		public static final String SUPPLEMENTARY ="S";
		public static final String INFOMATION ="I";
	}
	
	public class CUSTOMER_TYPE{
		public static final String INDIVIDUAL="I";
	}
	
	public class DECISION_CARD_APPLICATION_TYPE{
		public static final String MAIN="01";
		public static final String SUPLEMENTARY="02";
	}
	public class DOC_SLA_TYPE{
		public static final String FOLLOW_DOCUMENT_SLA_TYPE="01";
		public static final String CUSTOMER_DOCUMENT_SLA_TYPE="02";
		public static final String HR_DOCUMENT_SLA_TYPE="03";
		public static final String INCOME_DOCUMENT_SLA_TYPE="04";
	}
	
	public class DEBT_TYPE{
		public static final String WELFARE="01";
		public static final String COMMERCIAL="02";
		public static final String OTHER="99";
	}
	
	public class REF_LEVEL {
		public static final String APPLICATION = "A";
		public static final String PERSONAL_INFO = "P";
	}
	public  static String getCoaProductCode(String applicationType,String product,LoanDataM uloLoan) {
		logger.debug("applicationType>>"+applicationType);
		String result ="";
		String cardTypeCacheName = DecisionServiceCacheControl.getConstant("DM_CACHE_NAME_CARDTYPE");
		String PRODUCT_CODE_KEC = DecisionServiceCacheControl.getConstant("PRODUCT_CODE_KEC");
		String PRODUCT_CODE_CC = DecisionServiceCacheControl.getConstant("PRODUCT_CODE_CC");
		String DECISION_SERVICE_KEC_DUMMY_COA = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_KEC_DUMMY_COA");		
		String DECISION_SERVICE_CC_DUMMY_COA = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_CC_DUMMY_COA");		
		//KPL Additional
		String PRODUCT_CODE_KPL = DecisionServiceCacheControl.getConstant("PRODUCT_CODE_KPL");
		String DECISION_SERVICE_KPL_DUMMY_COA = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_KEC_DUMMY_COA");		
		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String PRODUCT_CODE_PLG = SystemConstant.getConstant("PRODUCT_CODE_PLG");
		
		if(null!=uloLoan){
			if(null!=uloLoan.getCard()){
				result = DecisionServiceCacheControl.getName(cardTypeCacheName, "CODE", uloLoan.getCard().getCardType(), "MAPPING1");		
			}
			//Add check KPL
			if(product.equals(PRODUCT_CODE_KPL))
			{
				result = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", PRODUCT_CODE_PLG, "SYSTEM_ID5");
			}
		}
		if(null==result || "".equals(result)){
			if(APPLY_TYPE_INCREASE.equals(applicationType)){
				if(product.equals(PRODUCT_CODE_KEC)){
					result= DECISION_SERVICE_KEC_DUMMY_COA;
				}else if(product.equals(PRODUCT_CODE_CC)){
					result= DECISION_SERVICE_CC_DUMMY_COA;
				}
			}
		}
		logger.debug("Coa product code>>"+result);
		return result;
	}
	
	public  static String getIAApplicationTypeFromTemplate(String templateId) {
		String incTemplateCode =DecisionServiceCacheControl.getGeneralParam("TEMPLATE_INCREASE");
		String templateCache = DecisionServiceCacheControl.getConstant("CACHE_TEMPLATE");
		String templateCode = DecisionServiceCacheControl.getName(templateCache, "TEMPLATE_ID", templateId, "TEMPLATE_CODE");
		logger.debug("templateId>>"+templateId);
		logger.debug("Inc templateCode>>"+incTemplateCode);
		logger.debug("templateCode>>"+templateCode);
		if(incTemplateCode.contains(templateCode)){
			return APPLY_TYPE_INCREASE;
		}		
		 return APPLY_TYPE_NEW;
	}
	
	public static PersonalInfoDataM getApplicationTypePersonalInfo(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM  personalInfo = new PersonalInfoDataM();
		try{
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE.APPLICANT);
			if(null==personalInfo){
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE.SUPPLEMENTARY);
			}
			if(null==personalInfo){
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE.INFOMATION);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}	
		return personalInfo;
	}
	
	public  static BigDecimal calculateTotalSavingandFundFromPriviliegeProjectCode(VerificationResultDataM uloVerification) {
		BigDecimal totalSavingandFund = BigDecimal.ZERO;
		if(null!=uloVerification.getPrivilegeProjectCodes()){
			for(PrivilegeProjectCodeDataM prvProjectCode : uloVerification.getPrivilegeProjectCodes()){
				ArrayList<PrivilegeProjectCodeProductSavingDataM> prvProjectProductSavings =prvProjectCode.getPrivilegeProjectCodeProductSavings();
				if(null!=prvProjectProductSavings){
					for(PrivilegeProjectCodeProductSavingDataM  prvProjectProductSaving : prvProjectProductSavings){
						if(null!=prvProjectProductSaving.getAccountBalance()){
							totalSavingandFund = totalSavingandFund.add(prvProjectProductSaving.getAccountBalance());
						}
					}
				}
			}
		}
		logger.debug("totalSavingandFund>>"+totalSavingandFund);
		return totalSavingandFund;
	}
	
	public  static String getBundleWithMainFlag(PersonalInfoDataM uloPersonalInfo) {
		 if(PERSONAL_TYPE.SUPPLEMENTARY.equals(uloPersonalInfo.getPersonalType())){
			 //do sm thing
			 return DecisionServiceUtil.FLAG_Y;
		 }

		return DecisionServiceUtil.FLAG_N;
	}
	
	public static String getSalaryLevel(String positionCode){
		if(positionCode == null || positionCode.isEmpty()){
			return null;
		}
		String positionCodeCacheName = DecisionServiceCacheControl.getConstant("CACHE_POSITION_CODE");
		
		return DecisionServiceCacheControl.getName(positionCodeCacheName, "CODE", positionCode, "POSITION_LEVEL");
	}
	
	public static String getPositionType(String positionCode){
		if(positionCode == null || positionCode.isEmpty()){
			return null;
		}
		String positionCodeCacheName = DecisionServiceCacheControl.getConstant("CACHE_POSITION_CODE");
		
		return DecisionServiceCacheControl.getName(positionCodeCacheName, "CODE", positionCode, "POSITION_TYPE");
	}
	
	public static boolean getIsInformationComplete(String personalId, ApplicationGroupDataM uloAppGroup) {
		// Validation
		if (personalId == null || personalId.isEmpty()) {
			return false;
		}
		if (uloAppGroup == null) {
			return false;
		}
		String appLevelRelation = DecisionServiceCacheControl.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
		List<ApplicationDataM> uloApps = uloAppGroup.filterApplicationPersonal(personalId, appLevelRelation);
		if(uloApps == null || uloApps.isEmpty()){
			return false;
		}
		
		//Determine completeness by applications
		for (ApplicationDataM uloApp : uloApps) {
			if (uloApp == null)
				continue;
			List<LoanDataM> uloLoans = uloApp.getLoans();
			if (uloLoans == null || uloLoans.isEmpty())
				continue;
			
			
			for(LoanDataM uloLoan : uloLoans){
				
				// Check payment method
				String paymentMethodId = uloLoan.getPaymentMethodId();
				PaymentMethodDataM method =uloAppGroup.getPaymentMethodById(paymentMethodId);
				if(method != null && !DecisionServiceUtil.INFORMATION_COMPLETED_CODE.equalsIgnoreCase(method.getCompleteData())){
					logger.debug("==CompleteData payment method not complete===");
					return false;//Payment method not completed!
				}
				
				// Check cash transfer
				List<CashTransferDataM> cTransfers = uloLoan.getCashTransfers();
				if(cTransfers != null && !cTransfers.isEmpty()){
					for(CashTransferDataM transfer : cTransfers){
						if(null==transfer) continue;
						if(!DecisionServiceUtil.INFORMATION_COMPLETED_CODE.equalsIgnoreCase(transfer.getCompleteData())){
							logger.debug("==CompleteData cash transfer not complete===");
							return false;//Cache transfer not completed!
						}
					}
				}
				
				//Check additional service
				SpecialAdditionalServiceDataM specialAdditionalServiceProduct = uloAppGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, uloApp.getProduct(), SPECIAL_ADDITIONAL_SERVICE_ATM);
				if(!ServiceUtil.empty(specialAdditionalServiceProduct)){
					if(!DecisionServiceUtil.INFORMATION_COMPLETED_CODE.equalsIgnoreCase(specialAdditionalServiceProduct.getCompleteData())){
						logger.debug("==CompleteData Additional service not complete===");
						return false;//Additional service not completed!
					}
				}
					
			}

		}
		
		//Determine completeness by Comparison data
		boolean completedCIS = ProcessUtil.isCISCompletedData(uloAppGroup, personalId);
		logger.debug("CompleteData completedCIS :"+completedCIS);
		if(completedCIS){
			return true;
		}
		return false;
	}
	
	public static boolean isCISCompletedData(ApplicationGroupDataM uloAppGroup, String personalId){
		if(uloAppGroup == null || personalId == null){//Treat no data as completed
			return true;
		}
		ComparisonGroupDataM compareGroups = uloAppGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
		if(compareGroups == null){//Treat no data as completed
			return true;
		}
		Map<String,CompareDataM> comparisonFields = compareGroups.getComparisonFields();
		if(comparisonFields == null || comparisonFields.isEmpty()){//Treat no data as completed
			return true;
		}
		
		for(Map.Entry<String, CompareDataM> entry : comparisonFields.entrySet()){
			if(entry == null)continue;
			
			CompareDataM compareData = entry.getValue();
			if(compareData == null)continue;
			
			if(!personalId.equals(compareData.getUniqueId()))continue;//Personal ID not match
			
			String fieldNameType = compareData.getFieldNameType();
			if(fieldNameType == null || !DecisionServiceUtil.CIS_COMPLETION_FIELDS.contains(fieldNameType))continue;
			
			//If flag W(aka "Wrong") is present, data comparison is not completed
			if(DecisionServiceUtil.COMPARE_FLAG_WRONG.equals(compareData.getCompareFlag())){
				return false;
			}
		}
		
		return true;
	}
	
	public static String getIIBRqUID(ApplicationGroupDataM uloApp) {
		if (uloApp == null)
			return null;
		
		Date now = DecisionServiceUtil.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		StringBuilder sb = new StringBuilder();
		sb.append(DecisionServiceCacheControl.getGeneralParam("KBANK_APP_ID"));
		sb.append("_");
		sb.append(sdf.format(now));
		sb.append("_");
		sb.append(uloApp.getApplicationGroupId());
		return sb.toString();
	}
	
	public  static String getCardType(String uloApplicationType,CardDataM uloCard,decisionservice_iib.LoanDataM iibLoan,decisionservice_iib.ApplicationDataM iibApplication, String requestCoaProductCode) {
		String cardTypeCacheName = DecisionServiceCacheControl.getConstant("DM_CACHE_NAME_CARDTYPE");
		String TYPE_INCREASE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_INCREASE");
		String PRODUCT_CODE_KEC = DecisionServiceCacheControl.getConstant("PRODUCT_CODE_KEC");
		String DECISION_SERVICE_COA_PRODUCT_CODE_K_SME = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_COA_PRODUCT_CODE_K_SME");
		
		logger.debug("iibApplication.getApplyTypeTransformationFlag()>>"+iibApplication.getApplyTypeTransformationFlag());
		logger.debug("iibLoan.getCard().getCardTransformationFlag()>>"+iibLoan.getCard().getCardTransformationFlag());
		// Begin logic
		String result = null;
		if(TYPE_INCREASE.equalsIgnoreCase(uloApplicationType) || YES_FLAG.equals(iibApplication.getApplyTypeTransformationFlag()) || YES_FLAG.equals(iibLoan.getCard().getCardTransformationFlag())){
			if(PRODUCT_CODE_KEC.equals(iibApplication.getOrgId())){
				/*change logic
				if(DECISION_SERVICE_COA_PRODUCT_CODE_K_SME.equals(iibLoan.getCoaProductCode())){
					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
				}else{
					result = uloCard.getCardType();
				}
				*/
				
				//use request coa product code to compare if change get card type from coa product code
				if(null != requestCoaProductCode && requestCoaProductCode.equals(iibLoan.getCoaProductCode())){
					result = uloCard.getCardType();
				}
				else{
					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
					if(Util.empty(result)){
						result = uloCard.getCardType();
					}
				}
			}else{
				result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
			}
			return result;
		}else{
			if(PRODUCT_CODE_KEC.equals(iibApplication.getOrgId()) ){
				/*change logic
				if(DECISION_SERVICE_COA_PRODUCT_CODE_K_SME.equals(iibLoan.getCoaProductCode())){
					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
				}else{
					result = uloCard.getCardType();
				}
				*/
				//use request coa product code to compare if change get card type from coa product code
				if(null != requestCoaProductCode && requestCoaProductCode.equals(iibLoan.getCoaProductCode())){
					result = uloCard.getCardType();
				}
				else{
					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
					if(Util.empty(result)){
						result = uloCard.getCardType();
					}
				}
			}else{
				result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getEligibleCoaProductCode(), "CODE");
			}
		}
		return result;
	}
	
	public  static String getCardType(String uloApplicationType,CardDataM uloCard,Loans iibLoan,Applications iibApplication, String requestCoaProductCode) {
		String cardTypeCacheName = DecisionServiceCacheControl.getConstant("DM_CACHE_NAME_CARDTYPE");
		String TYPE_INCREASE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_INCREASE");
		String PRODUCT_CODE_KEC = DecisionServiceCacheControl.getConstant("PRODUCT_CODE_KEC");
		String DECISION_SERVICE_COA_PRODUCT_CODE_K_SME = DecisionServiceCacheControl.getConstant("DECISION_SERVICE_COA_PRODUCT_CODE_K_SME");
		
				// Begin logic
		String result = null;
		if(TYPE_INCREASE.equalsIgnoreCase(uloApplicationType) 
//				|| YES_FLAG.equals(iibApplication.getApplyTypeTransformationFlag()) 
				|| YES_FLAG.equals(iibLoan.getCard().getCardTransformationFlag())
				){
			if(PRODUCT_CODE_KEC.equals(iibApplication.getOrgId())){
				/*change logic
				if(DECISION_SERVICE_COA_PRODUCT_CODE_K_SME.equals(iibLoan.getCoaProductCode())){
					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
				}else{
					result = uloCard.getCardType();
				}
				*/
				//use request coa product code to compare if change get card type from coa product code
				if(null != requestCoaProductCode && requestCoaProductCode.equals(iibLoan.getCoaProductCode())){
					result = uloCard.getCardType();
				}
				else{
					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
					if(Util.empty(result)){
						result = uloCard.getCardType();
					}
				}
			}else{
				result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
			}
			return result;
		}else{
			if(PRODUCT_CODE_KEC.equals(iibApplication.getOrgId()) ){
//				if(DECISION_SERVICE_COA_PRODUCT_CODE_K_SME.equals(iibLoan.getEligibleCoaProductCode())){
//					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getEligibleCoaProductCode(), "CODE");
//				}else{
					//result = uloCard.getCardType();
				//}
					
				if(null != requestCoaProductCode && requestCoaProductCode.equals(iibLoan.getCoaProductCode())){
						result = uloCard.getCardType();
				}
				else{
					result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CODE");
					if(Util.empty(result)){
						result = uloCard.getCardType();
					}
				}
			}else{
				result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getEligibleCoaProductCode(), "CODE");
			}
		}
		return result;
	}
	
	public  static String getCardTypeLevel(String uloApplicationType,decisionservice_iib.LoanDataM iibLoan,decisionservice_iib.ApplicationDataM iibApplication) {
		String cardTypeCacheName = DecisionServiceCacheControl.getConstant("DM_CACHE_NAME_CARDTYPE");
		String TYPE_INCREASE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_INCREASE");
		// Begin logic
		String result = null;
		if(TYPE_INCREASE.equalsIgnoreCase(uloApplicationType) || YES_FLAG.equals(iibApplication.getApplyTypeTransformationFlag()) || YES_FLAG.equals(iibLoan.getCard().getCardTransformationFlag())){
			
			result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CARD_LEVEL");
			
			return result;
		}else{
			
			result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getEligibleCoaProductCode(), "CARD_LEVEL");
			
		}
		logger.debug("card level>>"+result);
		return result;
	}
	public  static String getCardTypeLevel(String uloApplicationType,Loans iibLoan,Applications iibApplication) {
		String cardTypeCacheName = DecisionServiceCacheControl.getConstant("DM_CACHE_NAME_CARDTYPE");
		String TYPE_INCREASE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_INCREASE");
		// Begin logic
		String result = null;
		if(TYPE_INCREASE.equalsIgnoreCase(uloApplicationType) 
				//|| YES_FLAG.equals(iibApplication.getApplyTypeTransformationFlag()) 
				|| YES_FLAG.equals(iibLoan.getCard().getCardTransformationFlag())
				){
			
			result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getCoaProductCode(), "CARD_LEVEL");
			
			return result;
		}else{
			
			result = DecisionServiceCacheControl.getName(cardTypeCacheName, "MAPPING1", iibLoan.getEligibleCoaProductCode(), "CARD_LEVEL");

		}
		logger.debug("card level>>"+result);
		return result;
	}
	public static boolean isEligibleCoaProductCode(decisionservice_iib.LoanDataM iibLoan) {
		if(null!=iibLoan.getEligibleCoaProductCode() && !"".equals(iibLoan.getEligibleCoaProductCode())){
			return true;
		}
		return false;
	}
	
	public static boolean isEligibleCoaProductCode(Loans iibLoan) {
		if(null!=iibLoan.getEligibleCoaProductCode() && !"".equals(iibLoan.getEligibleCoaProductCode())){
			return true;
		}
		return false;
	}
	
	public static PolicyRulesDataM getULOPolicyByCode(List<PolicyRulesDataM> uloPolicies, String code, String reasonCode) {
		if (uloPolicies == null)
			return null;
		if (code == null || code.isEmpty() || reasonCode == null || reasonCode.isEmpty())
			return null;

		for (PolicyRulesDataM uloPolicy : uloPolicies) {
			if (uloPolicy == null)
				continue;
			if (code.equalsIgnoreCase(uloPolicy.getPolicyCode()) && reasonCode.equalsIgnoreCase(uloPolicy.getReason())) {
				return uloPolicy;
			}
		}
		return null;

	}
	
	public static PolicyRulesDataM getULOPolicyByCode(List<PolicyRulesDataM> uloPolicies, String code) {
		if (uloPolicies == null)
			return null;
		if (code == null || code.isEmpty())
			return null;

		for (PolicyRulesDataM uloPolicy : uloPolicies) {
			if (uloPolicy == null)
				continue;
			if (code.equalsIgnoreCase(uloPolicy.getPolicyCode()) && (null==uloPolicy.getReason() || "".equals(uloPolicy.getReason()))) {
				return uloPolicy;
			}
		}
		return null;

	}
	
	public static ORPolicyRulesDataM getULOORPolicyByCode(List<ORPolicyRulesDataM> uloORPolicies, String code) {
		if (uloORPolicies == null || uloORPolicies.isEmpty())
			return null;
		if (code == null || code.isEmpty())
			return null;

		for (ORPolicyRulesDataM ele : uloORPolicies) {
			if (ele == null)
				continue;
			if (code.equalsIgnoreCase(ele.getPolicyCode())) {
				return ele;
			}
		}
		return null;
	}

	public static ORPolicyRulesDetailDataM getULOORPolicyDetailByCode(List<ORPolicyRulesDetailDataM> uloORPolicies, String code) {
		if (uloORPolicies == null || uloORPolicies.isEmpty())
			return null;
		if (code == null || code.isEmpty())
			return null;

		for (ORPolicyRulesDetailDataM ele : uloORPolicies) {
			if (ele == null)
				continue;
			if (code.equalsIgnoreCase(ele.getGuidelineCode())) {
				return ele;
			}
		}
		return null;
	}
	
	public static GuidelineDataM getGuideLineByName(List<GuidelineDataM> uloGLList, String name) {
		if (uloGLList == null || uloGLList.size() < 1) {
			return null;
		}
		if (name == null || name.isEmpty()) {
			return null;
		}

		for (GuidelineDataM gl : uloGLList) {
			if (gl == null)
				continue;
			if (name.equalsIgnoreCase(gl.getName())) {
				return gl;
			}
		}
		return null;
	}
	
	public static void setApplyType(String applyType,ApplicationGroupDataM uloApplicationGroup){
		uloApplicationGroup.setApplicationType(applyType.replaceAll(APPLY_TYPE_ERROR_CODE, ""));
		if(APPLICATION_TYPE_ERROR.equals(applyType)){
			uloApplicationGroup.setApplyTypeStatus(APPLY_TYPE_ERROR_CODE);
		}else{
			uloApplicationGroup.setApplyTypeStatus(applyType);
		}
	}
	
	public static String setApplyType(String applyType){
		return applyType==null||applyType==""?null:applyType.replaceAll(APPLY_TYPE_ERROR_CODE, "");
	}
	
	public static String getFinalApplicationType(List<decisionservice_iib.ApplicationDataM> iibApps) {
	
		if (iibApps == null || iibApps.size() < 1) {
			return null;
		}
	
		String result = null;
		Set<String> applyTypes = new HashSet<String>();
		for (decisionservice_iib.ApplicationDataM iibApplication : iibApps) {
			if (iibApplication == null || iibApplication.getApplyType() == null || iibApplication.getApplyType().isEmpty()) {
				continue;
			}
			applyTypes.add(iibApplication.getApplyType());
		}
//		 logger.debug("decision service application list >>"+ new Gson().toJson(iibApps));
		  if(applyTypes.contains(APPLICATION_TYPE_ERROR)){
			result =APPLICATION_TYPE_ERROR;
		}else if (applyTypes.contains(APPLY_TYPE_NEW)) {
			result = APPLY_TYPE_NEW;
		} else if (applyTypes.contains(APPLY_TYPE_ADD)) {
			result = APPLY_TYPE_ADD;
		} else if (applyTypes.contains(APPLY_TYPE_UPGRADE)) {
			result = APPLY_TYPE_UPGRADE;
		} else if (applyTypes.contains(APPLY_TYPE_INCREASE)) {
			result = APPLY_TYPE_INCREASE;
		} else if (applyTypes.contains(APPLY_TYPE_EXP)) {
			result = APPLY_TYPE_EXP;
		} else if (applyTypes.contains(APPLY_TYPE_SUPPLIMENTARY)) {
			result = APPLY_TYPE_SUPPLIMENTARY;
		}
		logger.debug("result>>"+result);
		return result;
	}
	public static String getFinalSubmitApplicationTypes(List<com.ava.flp.eapp.submitapplication.model.Applications> iibApps) {
		
		if (iibApps == null || iibApps.size() < 1) {
			return null;
		}
	
		String result = null;
		Set<String> applyTypes = new HashSet<String>();
		for (com.ava.flp.eapp.submitapplication.model.Applications iibApplication : iibApps) {
			if (iibApplication == null || iibApplication.getApplyType() == null || iibApplication.getApplyType().isEmpty()) {
				continue;
			}
			applyTypes.add(iibApplication.getApplyType());
		}
//		 logger.debug("decision service application list >>"+ new Gson().toJson(iibApps));
		  if(applyTypes.contains(APPLICATION_TYPE_ERROR)){
			result =APPLICATION_TYPE_ERROR;
		}else if (applyTypes.contains(APPLY_TYPE_NEW)) {
			result = APPLY_TYPE_NEW;
		} else if (applyTypes.contains(APPLY_TYPE_ADD)) {
			result = APPLY_TYPE_ADD;
		} else if (applyTypes.contains(APPLY_TYPE_UPGRADE)) {
			result = APPLY_TYPE_UPGRADE;
		} else if (applyTypes.contains(APPLY_TYPE_INCREASE)) {
			result = APPLY_TYPE_INCREASE;
		} else if (applyTypes.contains(APPLY_TYPE_EXP)) {
			result = APPLY_TYPE_EXP;
		} else if (applyTypes.contains(APPLY_TYPE_SUPPLIMENTARY)) {
			result = APPLY_TYPE_SUPPLIMENTARY;
		}
		logger.debug("result>>"+result);
		return result;
	}
public static String getFinalApplicationTypes(List<Applications> iibApps) {
		
		if (iibApps == null || iibApps.size() < 1) {
			return null;
		}
	
		String result = null;
		Set<String> applyTypes = new HashSet<String>();
		for (Applications iibApplication : iibApps) {
			if (iibApplication == null || iibApplication.getApplyType() == null || iibApplication.getApplyType().isEmpty()) {
				continue;
			}
			applyTypes.add(iibApplication.getApplyType());
		}
//		 logger.debug("decision service application list >>"+ new Gson().toJson(iibApps));
		  if(applyTypes.contains(APPLICATION_TYPE_ERROR)){
			result =APPLICATION_TYPE_ERROR;
		}else if (applyTypes.contains(APPLY_TYPE_NEW)) {
			result = APPLY_TYPE_NEW;
		} else if (applyTypes.contains(APPLY_TYPE_ADD)) {
			result = APPLY_TYPE_ADD;
		} else if (applyTypes.contains(APPLY_TYPE_UPGRADE)) {
			result = APPLY_TYPE_UPGRADE;
		} else if (applyTypes.contains(APPLY_TYPE_INCREASE)) {
			result = APPLY_TYPE_INCREASE;
		} else if (applyTypes.contains(APPLY_TYPE_EXP)) {
			result = APPLY_TYPE_EXP;
		} else if (applyTypes.contains(APPLY_TYPE_SUPPLIMENTARY)) {
			result = APPLY_TYPE_SUPPLIMENTARY;
		}
		logger.debug("result>>"+result);
		return result;
	}
	public static String getULOBooleanFlag(String decisionFlag) {
		if(decisionFlag.equalsIgnoreCase(DECISION_REQUIRED_FLAG)){
			return YES_FLAG;
		}
		return NO_FLAG;
	}
	public static String getULORecommendDecision(String iibProductStatus) {
		logger.debug("iibProductStatus>>"+iibProductStatus);
		
		if(null==iibProductStatus || "".equals(iibProductStatus)){
			return "";
		}
		return DecisionServiceCacheControl.getNameListBox(FILED_ID_REC_APP_DECISION, "MAPPING6", iibProductStatus.trim(), "CHOICE_NO");
	}
	
	public static String getDecisionServiceProductStatus(String uloRecomendDecision) {
		logger.debug("uloRecomendDecision>>"+uloRecomendDecision);
		if(null==uloRecomendDecision || "".equals(uloRecomendDecision)){
			return "";
		}
		return DecisionServiceCacheControl.getNameListBox(FILED_ID_REC_APP_DECISION, "CHOICE_NO", uloRecomendDecision, "MAPPING6");
	}
	public static String getProcessActionApplicationGroup(ApplicationGroupDataM applicationGroup){
		String jobState = applicationGroup.getJobState();
		if(JOBSTATE_REJECT.equals(jobState)){
			return APPLICATION_STATIC_REJECTED;
		}else if(JOBSTATE_APPROVE.equals(jobState)){
			return APPLICATION_STATIC_APPROVED;
		}else if(JOBSTATE_PENDING_FULLFRAUD.equals(jobState))
		{
			if(MConstant.FLAG.YES.equals(applicationGroup.getFullFraudFlag()))
			{return APPLICATION_STATIC_REJECTED;}
			else
			{return APPLICATION_STATIC_APPROVED;}
		}
		return "";
	}
	public static boolean isCompletedVerification(ApplicationGroupDataM uloAppGroup, VerifyId verId){
		if(uloAppGroup == null)return false;
		if(verId == null)return false;
		
		List<FinalVerifyResultDataM> verResults = uloAppGroup.getFinalVerifys();
		if(verResults == null || verResults.isEmpty()){
			return false;
		}
		
		Set<String> completeStatus = new HashSet<String>();
		completeStatus.add(DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED"));
		completeStatus.add(DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS"));
		completeStatus.add(DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED_NOT_FRAUD"));		
		
		
		for(FinalVerifyResultDataM verResult : verResults){
			if(verResult == null || verResult.getVerifyResultCode() == null)continue;
			if(verId == verResult.getVerifyId()){
				return completeStatus.contains(verResult.getVerifyResultCode());
			}
		}
		return false;
	}
	public static List<IdentifyQuestionSetDataM> mergeCustomerQuestionSet(List<IdentifyQuestionSetDataM> uloQuestionSet,List<decisionservice_iib.QuestionSetDataM >iibCusQuestions) {
		
		if (uloQuestionSet == null) {
			uloQuestionSet = new ArrayList<IdentifyQuestionSetDataM>();
		}
		
		// Prepare data
		Set<String> uloQSet = new HashSet<String>();
		for (IdentifyQuestionSetDataM uloQ : uloQuestionSet) {
			if (uloQ == null)
				continue;
			uloQSet.add(uloQ.getQuestionSetCode());
		}
		
		// Prepare iib Question set
		Set<String> iibQSet = new HashSet<String>();
		String setTypeCustomer = DecisionServiceCacheControl.getConstant("QUESTION_SET_TYPE_C");
		
		// Map and add new from iib Customer Ver
		if (iibCusQuestions != null) {
			for(decisionservice_iib.QuestionSetDataM  iibCusQuestion : iibCusQuestions){
				if(null!=iibCusQuestion.getCallToWhom() && !"".equals(iibCusQuestion.getCallToWhom())){
					String code = iibCusQuestion.getQuestionCode();
					iibQSet.add(code);
					if (uloQSet.contains(code)) {
						IdentifyQuestionSetDataM uloQ = getULOQuestionSetByCode(uloQuestionSet, code);
						uloQ.setCallTo(iibCusQuestion.getCallToWhom());
					} else {
						IdentifyQuestionSetDataM uloQ = new IdentifyQuestionSetDataM();
						uloQ.setCallTo(iibCusQuestion.getCallToWhom());
						uloQ.setQuestionSetCode(code);
						uloQ.setQuestionSetType(setTypeCustomer);
						uloQuestionSet.add(uloQ);
					}
				}
			}

		}
		
		// Prune inconsistency
		for (Iterator<IdentifyQuestionSetDataM> it = uloQuestionSet.iterator(); it.hasNext();) {
			IdentifyQuestionSetDataM uloQ = it.next();
			if (uloQ == null)
				continue;
			String code = uloQ.getQuestionSetCode();
			if (setTypeCustomer.equalsIgnoreCase(uloQ.getQuestionSetType()) && !iibQSet.contains(code)) {
				it.remove();
			}
		}
		
		return uloQuestionSet;
	}
	
	public static IdentifyQuestionSetDataM getULOQuestionSetByCode(List<IdentifyQuestionSetDataM> uloQuestions, String code) {
		if (uloQuestions == null || uloQuestions.isEmpty())
			return null;
		if (code == null || code.isEmpty())
			return null;

		for (IdentifyQuestionSetDataM ele : uloQuestions) {
			if (ele == null)
				continue;
			if (code.equalsIgnoreCase(ele.getQuestionSetCode())) {
				return ele;
			}
		}
		return null;
	}
	
	public static List<IdentifyQuestionDataM> replaceQuestionByType(List<IdentifyQuestionSetDataM> questionSetList, List<IdentifyQuestionDataM> requestList, String questionSetType){
		//Validation
		if(questionSetList == null || questionSetList.isEmpty())return requestList;
		
		if(questionSetType == null || questionSetType.isEmpty())return requestList;
		
		if(requestList == null){
			requestList = new ArrayList<IdentifyQuestionDataM>();
		}
		
		List<IdentifyQuestionSetDataM> currentQuestionSetList = new ArrayList<IdentifyQuestionSetDataM>();
		for(IdentifyQuestionSetDataM qSet : questionSetList){
			if(qSet == null)continue;
			if(!questionSetType.equalsIgnoreCase(qSet.getQuestionSetType()))continue;//Not correct type
			
			currentQuestionSetList.add(qSet);
		}			
		
		//Remove current Question type
		for(Iterator<IdentifyQuestionDataM> it = requestList.iterator();it.hasNext();){
			IdentifyQuestionDataM qs = it.next();
			if(qs == null)continue;
			if(questionSetType.equalsIgnoreCase(qs.getQuestionSetType())){
				it.remove();
			}
		}

		//Get questions by type
		List<IdentifyQuestionDataM> questions = getULOQuestions(currentQuestionSetList);
		
		//No new question to be added
		if(questions == null || questions.isEmpty()){
			return requestList;
		}
		
		//Add all new questions
		requestList.addAll(questions);
		
		return requestList;
	}
	public static List<IdentifyQuestionDataM> replaceQuestionByType(List<IdentifyQuestionSetDataM> questionSetList, List<IdentifyQuestionDataM> requestList, String questionSetType, String reprocessFlag, VerifyId verId){
		//Validation
		if(questionSetList == null || questionSetList.isEmpty())return requestList;
		
		if(questionSetType == null || questionSetType.isEmpty())return requestList;
		
		if(requestList == null){
			requestList = new ArrayList<IdentifyQuestionDataM>();
		}
		
		List<IdentifyQuestionSetDataM> currentQuestionSetList = new ArrayList<IdentifyQuestionSetDataM>();
		for(IdentifyQuestionSetDataM qSet : questionSetList){
			if(qSet == null)continue;
			if(!questionSetType.equalsIgnoreCase(qSet.getQuestionSetType()))continue;//Not correct type
			
			currentQuestionSetList.add(qSet);
		}			
		
		logger.debug("reprocessFlag : "+reprocessFlag);
		if(!ServiceUtil.empty(reprocessFlag) && YES_FLAG.equals(reprocessFlag) && VerifyId.VERIFY_CUSTOMER==verId){
			//Get questions by type
			List<IdentifyQuestionDataM> newQuestions = getULOQuestions(currentQuestionSetList);
			//put result from previous questions
//			for(IdentifyQuestionDataM newQuestion : newQuestions){
//				for(IdentifyQuestionDataM prevQuestion : requestList){
//					logger.debug("new  setType : "+newQuestion.getQuestionSetType());
//					logger.debug("prev setType : "+prevQuestion.getQuestionSetType());
//					logger.debug("new  qNo : "+newQuestion.getQuestionNo());
//					logger.debug("prev qNo : "+prevQuestion.getQuestionNo());
//					if(newQuestion.getQuestionSetType().equals(prevQuestion.getQuestionSetType()) 
//							&& newQuestion.getSeq()==prevQuestion.getSeq()){
//						logger.debug("prev result : "+prevQuestion.getResult());
//						newQuestion.setQuestionNo(prevQuestion.getQuestionNo());
//						newQuestion.setResult(prevQuestion.getResult());
//					}
//				}
//			}
			replaceSameQuestionSet(newQuestions, requestList, questionSetType);
			
			//Remove current Question type
			for(Iterator<IdentifyQuestionDataM> it = requestList.iterator();it.hasNext();){
				IdentifyQuestionDataM qs = it.next();
				if(qs == null)continue;
				if(questionSetType.equalsIgnoreCase(qs.getQuestionSetType())){
					it.remove();
				}
			}
			//No new question to be added
			if(newQuestions == null || newQuestions.isEmpty()){
				return requestList;
			}
			//Add all new questions
			requestList.addAll(newQuestions);
		}else{
			//Remove current Question type
			for(Iterator<IdentifyQuestionDataM> it = requestList.iterator();it.hasNext();){
				IdentifyQuestionDataM qs = it.next();
				if(qs == null)continue;
				if(questionSetType.equalsIgnoreCase(qs.getQuestionSetType())){
					it.remove();
				}
			}

			//Get questions by type
			List<IdentifyQuestionDataM> questions = getULOQuestions(currentQuestionSetList);
			
			//No new question to be added
			if(questions == null || questions.isEmpty()){
				return requestList;
			}
			
			//Add all new questions
			requestList.addAll(questions);
		}
		return requestList;
	}
	
	private static void replaceSameQuestionSet(List<IdentifyQuestionDataM> newQuestions,List<IdentifyQuestionDataM> prevQuestions,String questionSetType){
		ArrayList<String> sameSets = new ArrayList<String>(); 
		for(IdentifyQuestionDataM newQuestion : newQuestions){
			for(IdentifyQuestionDataM prevQuestion : prevQuestions){
				if(newQuestion.getQuestionSetCode().equals(prevQuestion.getQuestionSetCode())){
					String questionSetCode = newQuestion.getQuestionSetCode();
					if(!sameSets.contains(questionSetCode)){
						sameSets.add(questionSetCode);
					}
				}
			}
		}
		logger.debug("sameSets : "+sameSets);
		//Remove New Questions by same type
		for(Iterator<IdentifyQuestionDataM> it = newQuestions.iterator();it.hasNext();){
			IdentifyQuestionDataM qs = it.next();
			if(qs == null)continue;
			if(questionSetType.equalsIgnoreCase(qs.getQuestionSetType()) && sameSets.contains(qs.getQuestionSetCode())){
				it.remove();
			}
		}
		// Add
		for(Iterator<IdentifyQuestionDataM> it = prevQuestions.iterator();it.hasNext();){
			IdentifyQuestionDataM qs = it.next();
			if(qs == null)continue;
			if(questionSetType.equalsIgnoreCase(qs.getQuestionSetType()) && sameSets.contains(qs.getQuestionSetCode())){
				IdentifyQuestionDataM newQs = new IdentifyQuestionDataM();
					newQs.setQuestionSetType(qs.getQuestionSetType());
					newQs.setQuestionSetCode(qs.getQuestionSetCode());
					newQs.setQuestionNo(qs.getQuestionNo());
					newQs.setSeq(qs.getSeq());
					newQs.setResult(qs.getResult());
				newQuestions.add(newQs);
			}
		}
	}
	
	public static List<IdentifyQuestionDataM> getULOQuestions(List<IdentifyQuestionSetDataM> questionSetList) {
		if (questionSetList == null || questionSetList.isEmpty()) {
			return null;
		}

		@SuppressWarnings("unchecked")
		List<HashMap<?, ?>> qGroupCache = (List<HashMap<?, ?>>) DecisionServiceCacheControl.getCache("QuestionByGroup");
		if (qGroupCache == null) {
			logger.debug("getULOQuestions() cache QuestionByGroup contains nothing in it. Unable to ramdomize question set by group!");
			return null;
		}

		// Unique Question set
		Set<DecisionQuestion> uniqQuestions = new HashSet<DecisionQuestion>();
		for (IdentifyQuestionSetDataM qSet : questionSetList) {
			String setCode = qSet.getQuestionSetCode();
			String setType = qSet.getQuestionSetType();
			Set<DecisionQuestion> questionsOfSet = getSelectiveRamdomedQuestionBySet(setCode, setType);
			if (questionsOfSet != null) {
				uniqQuestions.addAll(questionsOfSet);
			}
		}

		// Map result
		List<IdentifyQuestionDataM> result = new ArrayList<IdentifyQuestionDataM>();
		for (DecisionQuestion uniqQuestion : uniqQuestions) {
			IdentifyQuestionDataM uloQuestion = new IdentifyQuestionDataM();
			uloQuestion.setQuestionSetType(uniqQuestion.getType());
			uloQuestion.setQuestionSetCode(uniqQuestion.getSetCode());//SET_ID in MS_QUESTION_SET
			uloQuestion.setQuestionNo(uniqQuestion.getQuestionId());
			uloQuestion.setSeq(DecisionServiceUtil.parseInt(uniqQuestion.getPriority()));
			result.add(uloQuestion);
		}
		return result;
	}
	
	private static Set<DecisionQuestion> getSelectiveRamdomedQuestionBySet(String setCode, String setType) {
		if (setCode == null || setCode.isEmpty()) {
			logger.debug("getSelectiveRamdomQuestionBySet() question set id is null");
			return null;
		}

		@SuppressWarnings("unchecked")
		List<HashMap<?, ?>> qGroupCache = (List<HashMap<?, ?>>) DecisionServiceCacheControl.getCache("QuestionByGroup");
		if (qGroupCache == null) {
			logger.debug("getULOQuestions() cache QuestionByGroup contains nothing in it. Unable to ramdomize question set by group!");
			return null;
		}

		// Set unique question
		Set<DecisionQuestion> uniqQuestions = new HashSet<DecisionQuestion>();
		for (HashMap<?, ?> record : qGroupCache) {
			// Verify data integrity
			if (record == null)
				continue;
			String cacheSetCode = (String) record.get("QUESTION_SET_CODE");
			String cacheSetId = (String) record.get("QUESTION_SET_ID");
			String groupId = (String) record.get("GROUP_ID");
			String questionId = (String) record.get("QUESTION_ID");
			BigDecimal randNo = (BigDecimal) record.get("RAND_NUMBER");
			BigDecimal priority = (BigDecimal) record.get("PRIORITY");
			if (questionId == null || questionId.isEmpty() || !cacheSetCode.equalsIgnoreCase(setCode))
				continue;

			// Add
			DecisionQuestion decisionQ = new DecisionQuestion();
			decisionQ.setSetCode(setCode);
			decisionQ.setSetId(cacheSetId);
			decisionQ.setGroupId(groupId);
			decisionQ.setQuestionId(questionId);
			decisionQ.setRandomNo(randNo);
			decisionQ.setType(setType);
			decisionQ.setPriority(priority);
			uniqQuestions.add(decisionQ);
		}

		// Group questions
		Map<String, List<DecisionQuestion>> groupedQuestionMap = new HashMap<String, List<DecisionQuestion>>();
		for (DecisionQuestion qs : uniqQuestions) {
			String groupId = qs.getGroupId();
			List<DecisionQuestion> group = groupedQuestionMap.get(groupId);
			if (group == null) {
				group = new ArrayList<DecisionQuestion>();
				groupedQuestionMap.put(groupId, group);
			}
			group.add(qs);
		}

		// Prepare result collection
		uniqQuestions.clear();

		// Random the questions
		for (Map.Entry<String, List<DecisionQuestion>> entry : groupedQuestionMap.entrySet()) {
			List<DecisionQuestion> val = entry.getValue();
			DecisionQuestion representativeQs = val.get(0);
			// log.debug("representativeQs : "+representativeQs);
			if (representativeQs == null)
				continue;

			// Start logic
			int randomQuantity = DecisionServiceUtil.parseInt(representativeQs.getRandomNo());
			if (randomQuantity > 0) {// Get by specific size, otherwise the process will pick all questions case (size = 0 or less)
				int size = val.size();
				int finalRandomNo = Math.min(randomQuantity, size);
				int startIndex = Math.min((int) (size * Math.random()), size - finalRandomNo);// Random here
				startIndex = Math.max(0, startIndex);// Lower Limit

				int endIndex = Math.min(size, startIndex + finalRandomNo);
				// log.debug("Start index : "+startIndex+" , endIndex : "+endIndex);
				val = val.subList(startIndex, endIndex);
			}
			if (!val.isEmpty()) {
				uniqQuestions.addAll(val);
			}
		}
		// log.debug("After clear uniqQuestions : "+uniqQuestions);
		return uniqQuestions;
	}
	
	public static List<IdentifyQuestionSetDataM> mergeHRQuestionSet(List<IdentifyQuestionSetDataM> uloQuestionSets,List<decisionservice_iib.IdentifyQuestionSetDataM> iibQuestionSets) {

		if (uloQuestionSets == null) {
			uloQuestionSets = new ArrayList<IdentifyQuestionSetDataM>();
		}

		// Prepare data
		Set<String> uloQSet = new HashSet<String>();
		for (IdentifyQuestionSetDataM uloQ : uloQuestionSets) {
			if (uloQ == null)
				continue;
			uloQSet.add(uloQ.getQuestionSetCode());
		}

		// Prepare iib Question set
		Set<String> iibQSet = new HashSet<String>();
		String setTypeHR = DecisionServiceCacheControl.getConstant("QUESTION_SET_TYPE_HR");

		// Map and add new from Fico HR Ver
		if (iibQuestionSets != null ) {
			for(decisionservice_iib.IdentifyQuestionSetDataM iibQuestionSet : iibQuestionSets){
				if(null!=iibQuestionSet.getQuestionSetCode()  && "".equals(iibQuestionSet.getQuestionSetCode())){
					String code = iibQuestionSet.getQuestionSetCode();
					iibQSet.add(code);
					if (!uloQSet.contains(code)) {
						IdentifyQuestionSetDataM uloQ = new IdentifyQuestionSetDataM();
						uloQ.setQuestionSetCode(code);
						uloQ.setQuestionSetType(setTypeHR);
						uloQuestionSets.add(uloQ);
					}	
			}
		}
		// Prune inconsistency
		for (Iterator<IdentifyQuestionSetDataM> it = uloQuestionSets.iterator(); it.hasNext();) {
			IdentifyQuestionSetDataM uloQ = it.next();
			if (uloQ == null)
				continue;
			String code = uloQ.getQuestionSetCode();
			if (setTypeHR.equalsIgnoreCase(uloQ.getQuestionSetType()) && !iibQSet.contains(code)) {
				it.remove();
			}
		}
	}
		return uloQuestionSets;
	}
	
	public static String getCardEntryType(String cardApplcation){
		if(APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(cardApplcation)){
			return  DECISION_CARD_APPLICATION_TYPE.SUPLEMENTARY;
		}
		return DECISION_CARD_APPLICATION_TYPE.MAIN;
	} 
	public static String getCardEncrypt(String cardCode){
		logger.debug("cardCode>>"+cardCode);
		try {
			if(cardCode == null || cardCode.isEmpty()){
				return "";
			}
			//Prepare data & encryption
			Encryptor dihEnc = EncryptorFactory.getDIHEncryptor();
			String regularCardNo = dihEnc.decrypt(cardCode);//16-digit card number
			
			Encryptor kmEnc = EncryptorFactory.getKmAlertEncryptor();
			String encrypted2CardNo = kmEnc.encrypt(regularCardNo);
			logger.debug("encrypted2CardNo>>"+encrypted2CardNo);
			return encrypted2CardNo;
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return "";
	} 
	private static String getGroupIncomeType(String incomeType){
		String INC_TYPE_YEARLY_50TAWI = DecisionServiceCacheControl.getConstant("INC_TYPE_YEARLY_50TAWI");
		String INC_TYPE_MONTHLY_50TAWI = DecisionServiceCacheControl.getConstant("INC_TYPE_MONTHLY_50TAWI");
		if(INC_TYPE_MONTHLY_50TAWI.equalsIgnoreCase(incomeType)){
			return INC_TYPE_YEARLY_50TAWI;
		}
		return incomeType;
	}
	public static List<IncomeInfoDataM> mergeIncomeScreenType(List<IncomeInfoDataM> uloIncomes,  List<decisionservice_iib.IncomeScreenDataM> iibIncomeScreens, List<ApplicationDataM> uloApps, String reprocessFlag, decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) {
		if (iibIncomeScreens == null || iibIncomeScreens.size() < 1) {
			return null;
		}

		if (uloIncomes == null) {
			uloIncomes = new ArrayList<IncomeInfoDataM>();
		}
		String FIELD_ID_REVENUE_CATEGORY = DecisionServiceCacheControl.getConstant("FIELD_ID_REVENUE_CATEGORY");
		String DECISION_INCOME_TYPE_BUN_KL = DecisionServiceCacheControl.getConstant("DECISION_INCOME_TYPE_BUN_KL");
		String DECISION_INCOME_TYPE_BUN_HL = DecisionServiceCacheControl.getConstant("DECISION_INCOME_TYPE_BUN_HL");
		String DECISION_INCOME_TYPE_BUN_SME = DecisionServiceCacheControl.getConstant("DECISION_INCOME_TYPE_BUN_SME");
		String ULO_INCOME_TYPE_BUN_KL = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", DECISION_INCOME_TYPE_BUN_KL, "CHOICE_NO");
		String ULO_INCOME_TYPE_BUN_HL = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", DECISION_INCOME_TYPE_BUN_HL, "CHOICE_NO");
		String ULO_INCOME_TYPE_BUN_SME = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", DECISION_INCOME_TYPE_BUN_SME, "CHOICE_NO");

		// Parepare ULO income
		Set<String> currentIncomeTypes = new HashSet<String>();
		
//		 Hard Code !!!! when the type below is found
//		String INC_TYPE_YEARLY_50TAWI = DecisionServiceCacheControl.getConstant("INC_TYPE_YEARLY_50TAWI");
//		String INC_TYPE_MONTHLY_50TAWI = DecisionServiceCacheControl.getConstant("INC_TYPE_MONTHLY_50TAWI");
		Set<String> lastedIncomeTypes = new HashSet<String>();
		for (IncomeInfoDataM uloIncome : uloIncomes) {
			if (uloIncome == null)
				continue;
			// decision doesn't have Monthly Tawi, if this type already exists, yearly tawi should be added as well.
//			if(INC_TYPE_MONTHLY_50TAWI.equalsIgnoreCase(uloIncome.getIncomeType())) {
//				uloIncomeTypeSet.add(INC_TYPE_YEARLY_50TAWI);
//			}
//			
			String groupIncomeType = getGroupIncomeType(uloIncome.getIncomeType());
			lastedIncomeTypes.add(groupIncomeType);
			//comment this bcoz  system will add new income type  alway
			//decisionMappedIncomeScreenTypeSet.add(uloIncome.getIncomeType());
		}

		// Prepare decision Income & Add new
		for (decisionservice_iib.IncomeScreenDataM iibIncomeScreen : iibIncomeScreens) {
			String iibIncomeScreenType = iibIncomeScreen.getIncomeScreenType();
			logger.debug("iibIncomeScreenType>>"+iibIncomeScreenType);
			// log.debug("mergeIncome() Current decision IncomeScreenTypeCode : " + ficoIncome.getIncomeScreenTypeCode());
			String uloIncomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", iibIncomeScreenType, "CHOICE_NO");
			// log.debug("mergeIncome() IncomeScreenTypeCode After map to ULO Code IncomeScreenTypeCode : " + mappedFicoIncomeType);
			logger.debug("uloIncomeType>>"+uloIncomeType);
			String groupIncomeType = getGroupIncomeType(uloIncomeType);
			currentIncomeTypes.add(groupIncomeType);
			if (iibIncomeScreenType == null)
				continue;

			// Begin mapping
			if (iibIncomeScreenType.equalsIgnoreCase(DECISION_INCOME_TYPE_BUN_KL)) {
				setBundleKL(uloApps);
			}else if(iibIncomeScreenType.equalsIgnoreCase(DECISION_INCOME_TYPE_BUN_HL)) {
				setBundleHL(uloApps);
			}else if(iibIncomeScreenType.equalsIgnoreCase(DECISION_INCOME_TYPE_BUN_SME)) {
				setBundleSME(uloApps);
			}
			if(!lastedIncomeTypes.contains(groupIncomeType)){
				IncomeInfoDataM uloIncome = new IncomeInfoDataM();
					uloIncome.setIncomeType(uloIncomeType);
					uloIncome.setSeq(uloIncomes.size()+1);
					uloIncomes.add(uloIncome);
			}
		}

		// Prune inconsistent elements
		for (Iterator<IncomeInfoDataM> it = uloIncomes.iterator(); it.hasNext();) {
			IncomeInfoDataM uloIncome = it.next();
			if (uloIncome == null)
				continue;
//			uloIncome.setCompareFlag(NO_FLAG); According to ST000000000036 : Flag logic will be moved to WorkflowManager
//			#rawi comment check logic not remove yearlytawi
//			if (decisionMappedIncomeScreenTypeSet.contains(typeYearlyTawi))
//				continue; // decision returns only Yearly for Tawi ,case decision returns yearly tawi, delete process won't take action!
			String groupIncomeType = getGroupIncomeType(uloIncome.getIncomeType());
			if (!currentIncomeTypes.contains(groupIncomeType)) {
				it.remove();// Prune here
			}
		}

		if (!currentIncomeTypes.contains(ULO_INCOME_TYPE_BUN_KL)) {
			clearBundleKL(uloApps);
		}
		if (!currentIncomeTypes.contains(ULO_INCOME_TYPE_BUN_HL)) {
			clearBundleHL(uloApps);
		}
		if (!currentIncomeTypes.contains(ULO_INCOME_TYPE_BUN_SME)) {
			clearBundleSME(uloApps);
		}
		if(!ServiceUtil.empty(reprocessFlag) && YES_FLAG.equals(reprocessFlag)
				&& !DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME.equals(iibApplicationGroup.getCallAction())){
			for(IncomeInfoDataM uloIncomeInfo : uloIncomes){
				if(!ServiceUtil.empty(uloIncomeInfo)){
					//check case call income and bundle sme
					if(!ServiceUtil.empty(uloIncomeInfo.getCompareFlag())){
						uloIncomeInfo.setCompareFlag("");
					}
				}
			}
		}
		logger.debug("uloIncomes size>>"+uloIncomes.size());
		return uloIncomes;
	}
	public static List<IncomeInfoDataM> mergeIncomeScreenTypes(List<IncomeInfoDataM> uloIncomes,  List<IncomeScreens> iibIncomeScreens, List<ApplicationDataM> uloApps, String reprocessFlag) {
		if (iibIncomeScreens == null || iibIncomeScreens.size() < 1) {
			return null;
		}

		if (uloIncomes == null) {
			uloIncomes = new ArrayList<IncomeInfoDataM>();
		}
		String FIELD_ID_REVENUE_CATEGORY = DecisionServiceCacheControl.getConstant("FIELD_ID_REVENUE_CATEGORY");
		String DECISION_INCOME_TYPE_BUN_KL = DecisionServiceCacheControl.getConstant("DECISION_INCOME_TYPE_BUN_KL");
		String DECISION_INCOME_TYPE_BUN_HL = DecisionServiceCacheControl.getConstant("DECISION_INCOME_TYPE_BUN_HL");
		String DECISION_INCOME_TYPE_BUN_SME = DecisionServiceCacheControl.getConstant("DECISION_INCOME_TYPE_BUN_SME");
		String ULO_INCOME_TYPE_BUN_KL = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", DECISION_INCOME_TYPE_BUN_KL, "CHOICE_NO");
		String ULO_INCOME_TYPE_BUN_HL = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", DECISION_INCOME_TYPE_BUN_HL, "CHOICE_NO");
		String ULO_INCOME_TYPE_BUN_SME = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", DECISION_INCOME_TYPE_BUN_SME, "CHOICE_NO");

		// Parepare ULO income
		Set<String> currentIncomeTypes = new HashSet<String>();
		
//		 Hard Code !!!! when the type below is found
//		String INC_TYPE_YEARLY_50TAWI = DecisionServiceCacheControl.getConstant("INC_TYPE_YEARLY_50TAWI");
//		String INC_TYPE_MONTHLY_50TAWI = DecisionServiceCacheControl.getConstant("INC_TYPE_MONTHLY_50TAWI");
		Set<String> lastedIncomeTypes = new HashSet<String>();
		for (IncomeInfoDataM uloIncome : uloIncomes) {
			if (uloIncome == null)
				continue;
			// decision doesn't have Monthly Tawi, if this type already exists, yearly tawi should be added as well.
//			if(INC_TYPE_MONTHLY_50TAWI.equalsIgnoreCase(uloIncome.getIncomeType())) {
//				uloIncomeTypeSet.add(INC_TYPE_YEARLY_50TAWI);
//			}
//			
			String groupIncomeType = getGroupIncomeType(uloIncome.getIncomeType());
			lastedIncomeTypes.add(groupIncomeType);
			//comment this bcoz  system will add new income type  alway
			//decisionMappedIncomeScreenTypeSet.add(uloIncome.getIncomeType());
		}

		// Prepare decision Income & Add new
		for (IncomeScreens iibIncomeScreen : iibIncomeScreens) {
			String iibIncomeScreenType = iibIncomeScreen.getIncomeScreenType();
			logger.debug("iibIncomeScreenType>>"+iibIncomeScreenType);
			// log.debug("mergeIncome() Current decision IncomeScreenTypeCode : " + ficoIncome.getIncomeScreenTypeCode());
			String uloIncomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", iibIncomeScreenType, "CHOICE_NO");
			// log.debug("mergeIncome() IncomeScreenTypeCode After map to ULO Code IncomeScreenTypeCode : " + mappedFicoIncomeType);
			logger.debug("uloIncomeType>>"+uloIncomeType);
			String groupIncomeType = getGroupIncomeType(uloIncomeType);
			currentIncomeTypes.add(groupIncomeType);
			if (iibIncomeScreenType == null)
				continue;

			// Begin mapping
			if (iibIncomeScreenType.equalsIgnoreCase(DECISION_INCOME_TYPE_BUN_KL)) {
				setBundleKL(uloApps);
			}else if(iibIncomeScreenType.equalsIgnoreCase(DECISION_INCOME_TYPE_BUN_HL)) {
				setBundleHL(uloApps);
			}else if(iibIncomeScreenType.equalsIgnoreCase(DECISION_INCOME_TYPE_BUN_SME)) {
				setBundleSME(uloApps);
			}
			if(!lastedIncomeTypes.contains(groupIncomeType)){
				IncomeInfoDataM uloIncome = new IncomeInfoDataM();
					uloIncome.setIncomeType(uloIncomeType);
					uloIncome.setSeq(uloIncomes.size()+1);
					uloIncomes.add(uloIncome);
			}
		}

		// Prune inconsistent elements
		for (Iterator<IncomeInfoDataM> it = uloIncomes.iterator(); it.hasNext();) {
			IncomeInfoDataM uloIncome = it.next();
			if (uloIncome == null)
				continue;
//			uloIncome.setCompareFlag(NO_FLAG); According to ST000000000036 : Flag logic will be moved to WorkflowManager
//			#rawi comment check logic not remove yearlytawi
//			if (decisionMappedIncomeScreenTypeSet.contains(typeYearlyTawi))
//				continue; // decision returns only Yearly for Tawi ,case decision returns yearly tawi, delete process won't take action!
			String groupIncomeType = getGroupIncomeType(uloIncome.getIncomeType());
			if (!currentIncomeTypes.contains(groupIncomeType)) {
				it.remove();// Prune here
			}
		}

		if (!currentIncomeTypes.contains(ULO_INCOME_TYPE_BUN_KL)) {
			clearBundleKL(uloApps);
		}
		if (!currentIncomeTypes.contains(ULO_INCOME_TYPE_BUN_HL)) {
			clearBundleHL(uloApps);
		}
		if (!currentIncomeTypes.contains(ULO_INCOME_TYPE_BUN_SME)) {
			clearBundleSME(uloApps);
		}
		if(!ServiceUtil.empty(reprocessFlag) && YES_FLAG.equals(reprocessFlag)){
			for(IncomeInfoDataM uloIncomeInfo : uloIncomes){
				if(!ServiceUtil.empty(uloIncomeInfo)){
					if(!ServiceUtil.empty(uloIncomeInfo.getCompareFlag())){
						uloIncomeInfo.setCompareFlag("");
					}
				}
			}
		}
		logger.debug("uloIncomes size>>"+uloIncomes.size());
		return uloIncomes;
	}
	
	private static void setBundleKL(List<ApplicationDataM> uloApps) {
		if (uloApps == null || uloApps.isEmpty()) {
			return;
		}

		for (ApplicationDataM uloApp : uloApps) {
			BundleKLDataM uloBundle = uloApp.getBundleKL();
			if (uloBundle == null) {
				uloBundle = new BundleKLDataM();
			}
			uloBundle.setApplicationRecordId(uloApp.getApplicationRecordId());
			uloApp.setBundleKL(uloBundle);
		}
	}
	private static void clearBundleKL(List<ApplicationDataM> uloApps) {
		if (uloApps == null || uloApps.isEmpty()) {
			return;
		}

		for (ApplicationDataM uloApp : uloApps) {
			uloApp.setBundleKL(null);
		}
	}

	private static void setBundleHL(List<ApplicationDataM> uloApps) {
		if (uloApps == null || uloApps.isEmpty()) {
			return;
		}

		for (ApplicationDataM uloApp : uloApps) {
			BundleHLDataM uloBundle = uloApp.getBundleHL();
			if (uloBundle == null) {
				uloBundle = new BundleHLDataM();
			}
			uloBundle.setApplicationRecordId(uloApp.getApplicationRecordId());
			uloApp.setBundleHL(uloBundle);
		}
	}

	private static void clearBundleHL(List<ApplicationDataM> uloApps) {
		if (uloApps == null || uloApps.isEmpty()) {
			return;
		}

		for (ApplicationDataM uloApp : uloApps) {
			uloApp.setBundleHL(null);
		}
	}

	private static void setBundleSME(List<ApplicationDataM> uloApps) {
		if (uloApps == null || uloApps.isEmpty()) {
			return;
		}

		for (ApplicationDataM uloApp : uloApps) {
			BundleSMEDataM uloBundle = uloApp.getBundleSME();
			if (uloBundle == null) {
				uloBundle = new BundleSMEDataM();
			}
			uloBundle.setApplicationRecordId(uloApp.getApplicationRecordId());
			uloApp.setBundleSME(uloBundle);
		}
	}

	private static void clearBundleSME(List<ApplicationDataM> uloApps) {
		if (uloApps == null || uloApps.isEmpty()) {
			return;
		}

		for (ApplicationDataM uloApp : uloApps) {
			uloApp.setBundleSME(null);
		}
	}
	
	
	public static List<IncomeInfoDataM> mergePreviousAndGuarantorIncome(List<IncomeInfoDataM> uloIncomes, List<decisionservice_iib.IncomeInfoDataM> iibIncomes) {
		if (iibIncomes == null || iibIncomes.size() < 1) {
			return uloIncomes;
		}
		if (uloIncomes == null) {
			uloIncomes = new ArrayList<IncomeInfoDataM>();
		}

		String FIELD_ID_REVENUE_CATEGORY = DecisionServiceCacheControl.getConstant("FIELD_ID_REVENUE_CATEGORY");
		for (decisionservice_iib.IncomeInfoDataM iibIncome : iibIncomes) {
			if(null!=iibIncome.getPreviousIncomes() && iibIncome.getPreviousIncomes().size()>0){
				for(decisionservice_iib.PreviousIncomeDataM iibPreviousIncome : iibIncome.getPreviousIncomes()){
					if (iibPreviousIncome == null)
						continue;
					String mappedDecisionIncomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "SYSTEM_ID2", iibIncome.getIncomeType(), "CHOICE_NO");
					IncomeInfoDataM uloIncome = ProcessUtil.getULOPrevIncomeByType(uloIncomes, mappedDecisionIncomeType);
					if (uloIncome == null)
						continue;

					// Merge Previous Income
					uloIncome.setAllIncomes((ArrayList<IncomeCategoryDataM>) mergePreviousIncome(uloIncome.getAllIncomes(), iibPreviousIncome));
				}
			}

		}

		return uloIncomes;
	}
	
	
	public static List<IncomeCategoryDataM> mergePreviousIncome(List<IncomeCategoryDataM> uloIncomes, decisionservice_iib.PreviousIncomeDataM iibPrevIncome) {
		if (iibPrevIncome == null) {
			logger.warn("mergePreviousIncome(), decision IncPreviousIncome Array is null!");
			return uloIncomes;
		}
		if (uloIncomes == null) {
			uloIncomes = new ArrayList<IncomeCategoryDataM>();
		}

		PreviousIncomeDataM uloPrevIncome = null;
		for (IncomeCategoryDataM ele : uloIncomes) {
			if (ele instanceof PreviousIncomeDataM) {
				uloPrevIncome = (PreviousIncomeDataM) ele;
				break;
			}
		}
		if (uloPrevIncome == null) {
			uloPrevIncome = new PreviousIncomeDataM();
			uloPrevIncome.setSeq(uloIncomes.size() + 1);
			uloIncomes.add(uloPrevIncome);
		}

		// Start mapping here
		mapPreviousIncome(uloPrevIncome, iibPrevIncome);

		return uloIncomes;
	}
	
	
	private static void mapPreviousIncome(PreviousIncomeDataM uloPreviosIncome, decisionservice_iib.PreviousIncomeDataM iibPrevIncome) {
		if (iibPrevIncome == null) {
			logger.warn("mapPreviousIncome(), decision service IncPreviousIncome Model is null!");
			return;
		}
		if (uloPreviosIncome == null) {
			logger.warn("mapPreviousIncome(), ULO PreviousIncomeDataM Model is null!");
			return;
		}
		uloPreviosIncome.setIncomeDate(DecisionServiceUtil.toSqlDate(iibPrevIncome.getIncomeDate()));
		uloPreviosIncome.setProduct(iibPrevIncome.getProduct());
	}
	public static String getDecisionFinalAppDecision(String uloFinalDecision){
		logger.debug("uloFinalDecision>>"+uloFinalDecision);
		if(null==uloFinalDecision || "".equals(uloFinalDecision)){
			return "";
		}
		return DecisionServiceCacheControl.getNameListBox(FIELD_ID_FINAL_RESULT, "CHOICE_NO", uloFinalDecision, "MAPPING6");
	}
	public static String getVerCusResult(String verCustResultCode){
		logger.debug("verCustResultCode>>"+verCustResultCode);
		if(null==verCustResultCode || "".equals(verCustResultCode)){
			return "";
		}
		return DecisionServiceCacheControl.getNameListBox(FIELD_ID_DATA_VALIDATION_STATUS, "CHOICE_NO", verCustResultCode, "MAPPING6");
	}
	public static String getSubProduct(String product,String cardTypeId){
		logger.debug("product>>"+product);
		logger.debug("cardTypeId>>"+cardTypeId);
		if(null==product || null==cardTypeId){
			return "";
		}
		if(DecisionServiceCacheControl.getConstant("PRODUCT_K_EXPRESS_CASH").equals(product)){
			String cardCode = DecisionServiceCacheControl.getName(CACHE_CARD_INFO,"CARD_TYPE_ID",cardTypeId,"CARD_CODE");
			logger.debug("cardCode>>"+cardCode);
			if(null!=cardCode && !"".equals(cardCode)){
				return DecisionServiceCacheControl.getNameListBox(FIELD_ID_CARD_TYPE, "CHOICE_NO", cardCode, "MAPPING6");
			}
		}
		return "";
	}
	
	public static IncomeInfoDataM getULOPrevIncomeByType(List<IncomeInfoDataM> uloIncomes, String type) {
		if (type == null || type.isEmpty()) {
			return null;
		}
		if (uloIncomes == null || uloIncomes.isEmpty()) {
			return null;
		}

		for (IncomeInfoDataM prev : uloIncomes) {
			if (prev == null)
				continue;
			String uloType = prev.getIncomeType();
			if (type.equalsIgnoreCase(uloType)) {
				return prev;
			}
		}
		logger.warn("getULOPrevIncomeByType() income type " + type + " does not exist in ULO Income List");
		return null;
	}
	
	
	public static String getIMPersonalType(PersonalInfoDataM personalInfo) {
		String imPersonalType ="";
		if(null!= personalInfo){
			if(PERSONAL_TYPE.APPLICANT.equals(personalInfo.getPersonalType())) {
				imPersonalType= IM_PERSONAL_TYPE_APPLICANT;
			} else {
				int seq = personalInfo.getSeq();
				imPersonalType= IM_PERSONAL_TYPE_SUPPLEMENTARY+seq;
			}
		}
		return imPersonalType;
	}
	
	public static ArrayList<ApplicationImageSplitDataM> getPersonalApplicationImageSplits(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo) {
		 ArrayList<ApplicationImageSplitDataM> applicationImageSplits = new ArrayList<ApplicationImageSplitDataM>();
		 String imPersonalType = getIMPersonalType(uloPersonalInfo);
		 if(null!=uloApplicationGroup.getApplicationImages() && null!=imPersonalType){
			 for(ApplicationImageSplitDataM applicationImageSplit : uloApplicationGroup.getImageSplits()){
				 if(imPersonalType.equals(applicationImageSplit.getApplicantTypeIM())){
					 applicationImageSplits.add(applicationImageSplit);
				 }
			 }
		 }
		 return applicationImageSplits;
	}
	
	public static decisionservice_iib.PolicyRulesDataM getDecisionServicePolicyRule(List<decisionservice_iib.PolicyRulesDataM> policyRules,String policyCode) {
			if(null!=policyRules && null!=policyCode){
				for(decisionservice_iib.PolicyRulesDataM iibPolicy : policyRules){
					if(policyCode.equals(iibPolicy.getPolicyCode())){
						return iibPolicy;
					}
				}
			}
		return null;
	}
	public static PolicyRules getDecisionServicePolicyRules(List<PolicyRules> policyRules,String policyCode) {
		if(null!=policyRules && null!=policyCode){
			for(PolicyRules iibPolicy : policyRules){
				if(policyCode.equals(iibPolicy.getPolicyCode())){
					return iibPolicy;
				}
			}
		}
	return null;
	}
	
	
	
	public static void addImageSplitAndRequiredDocCaseRequirePrivilegeVer(ApplicationGroupDataM uloAppGroup){
		if(uloAppGroup == null)return;
		
		//Validation
		String applicantType = DecisionServiceCacheControl.getConstant("PERSONAL_TYPE_APPLICANT");
		PersonalInfoDataM applicant = uloAppGroup.getPersonalInfo(applicantType);
		if(applicant == null){
			logger.info("addImageSplitAndRequiredDocCaseRequirePrivilegeVer() Applicant is not present, skip adding image and required doc!");
			return;
		}
		
		String personalId = applicant.getPersonalId();
		String docType = DecisionServiceCacheControl.getGeneralParam("DOC_TYPE_ADDITIONAL", "PARAM_VALUE");
		String docGroup = DecisionServiceCacheControl.getGeneralParam("DOC_TYPE_ADDITIONAL", "PARAM_VALUE2");
		
		//******************* Generate Auto-Image for Privilege Verification *******************
		//Prepare images for applicant
		List<ApplicationImageDataM> imageList = uloAppGroup.getApplicationImages();
		if(imageList == null){
			imageList = new ArrayList<ApplicationImageDataM>();
			uloAppGroup.setApplicationImages((ArrayList<ApplicationImageDataM>) imageList);
		}
		if(imageList.isEmpty()){
			imageList.add(new ApplicationImageDataM());
		}
		
		//Auto generated image
		ApplicationImageDataM firstImage = imageList.get(0);
		ApplicationImageSplitDataM privilegeImageSplit = getPrivilegeImageSplit(firstImage);
		
		//Verify existing privilege image split
		boolean alreadyExist = false;
		for(ApplicationImageDataM uloImage : imageList){
			if(uloImage == null)continue;
			List<ApplicationImageSplitDataM> uloSplits = uloImage.getApplicationImageSplits();
			if(uloSplits == null)continue;
			for(ApplicationImageSplitDataM uloSplit : uloSplits){
				if(uloSplit == null)continue;
				if(!personalId.equals(uloSplit.getPersonalId()))continue;//PersonalID not matched
				if(docType.equals(uloSplit.getDocType())){
					alreadyExist = true;
					break;
				}
			}
		}
		if(!alreadyExist){				
			firstImage.add(privilegeImageSplit);
		}
		
		//******************* Generate Auto-Required Doc for Privilege Verification *******************
		VerificationResultDataM ver = applicant.getVerificationResult();
		if(ver == null){
			ver = new VerificationResultDataM();
			applicant.setVerificationResult(ver);
		}
		
		List<RequiredDocDataM> requiredDocs = ver.getRequiredDocs();
		if(requiredDocs == null){
			requiredDocs = new ArrayList<RequiredDocDataM>();
			ver.setRequiredDocs((ArrayList<RequiredDocDataM>) requiredDocs);
		}
		
		
		boolean docAlreadyExist = false;
		RequiredDocDataM existingRequiredDoc = null;
		for(int i = 0, s = requiredDocs.size(); i < s; i++){
			RequiredDocDataM doc = requiredDocs.get(i);
			if(doc == null)continue;
			if(docGroup.equals(doc.getScenarioType())){
				existingRequiredDoc = doc;
			}
			
			List<RequiredDocDetailDataM> details = doc.getRequiredDocDetails();
			if(details == null || details.isEmpty())continue;
			
			for(int j = 0, l = details.size(); j < l; j++){
				RequiredDocDetailDataM detail = details.get(j);
				if(detail == null)continue;
				if(docType.equals(detail.getDocumentCode())){
					docAlreadyExist = true;
				}
			}
		}
		
		if(!docAlreadyExist){
			RequiredDocDataM updateDoc = updateAndGetPrivilegeRequiredDoc(existingRequiredDoc);
			updateDoc.setVerResultId(ver.getVerResultId());
			if(existingRequiredDoc == null){
				requiredDocs.add(updateDoc);
			}
		}
	}
	
	public static ApplicationImageSplitDataM getPrivilegeImageSplit(ApplicationImageDataM firstImage){
		ApplicationImageSplitDataM split = new ApplicationImageSplitDataM();
		String docType = DecisionServiceCacheControl.getGeneralParam("DOC_TYPE_ADDITIONAL", "PARAM_VALUE");
		String docGroup = DecisionServiceCacheControl.getGeneralParam("DOC_TYPE_ADDITIONAL", "PARAM_VALUE2");
		split.setDocType(docType);
		split.setDocTypeGroup(docGroup);
		
		if(firstImage == null || firstImage.getApplicationImageSplits() == null || firstImage.getApplicationImageSplits().isEmpty()){
			split.setSeq(1);
			logger.info("getPrivilegeImageSplit() autoImage has no existing image in this application! Some information might not present...");
		}else{
			ApplicationImageSplitDataM firstSibling = firstImage.getApplicationImageSplits().get(0);
			split.setSeq(firstImage.getApplicationImageSplits().size()+1);
//			split.setImageId(firstSibling.getImgPageId());//ST000000000044 : Not map this
			split.setAppImgId(firstSibling.getAppImgId());
			split.setPersonalId(firstSibling.getPersonalId());
		}
		return split;
	}
	
	public static RequiredDocDataM updateAndGetPrivilegeRequiredDoc(RequiredDocDataM existingRequiredDoc){
		RequiredDocDataM doc = existingRequiredDoc;
		if(existingRequiredDoc == null){
			doc = new RequiredDocDataM();
		}
		
		String docType = DecisionServiceCacheControl.getGeneralParam("DOC_TYPE_ADDITIONAL", "PARAM_VALUE");
		String docGroup = DecisionServiceCacheControl.getGeneralParam("DOC_TYPE_ADDITIONAL", "PARAM_VALUE2");
		doc.setScenarioType(docGroup);
		doc.setDocumentGroupCode("1");//Scenario number
		
		List<RequiredDocDetailDataM> details = doc.getRequiredDocDetails();
		if(details == null){
			details = new ArrayList<RequiredDocDetailDataM>();
			doc.setRequiredDocDetails((ArrayList<RequiredDocDetailDataM>) details);
		}
		RequiredDocDetailDataM detail = new RequiredDocDetailDataM();
		detail.setDocumentCode(docType);
		detail.setMandatoryFlag(YES_FLAG);
		
		details.add(detail);
		return doc;
	}
	
	public static BigDecimal  totalOtherDebtAmount(decisionservice_iib.DebtInfoDataM iibDebtInfo){
		if(null==iibDebtInfo){return null;}
		BigDecimal total = BigDecimal.ZERO;
		if(null!=iibDebtInfo.getWelfareAmount()){
			total = total.add(iibDebtInfo.getWelfareAmount());
		}
		if(null!=iibDebtInfo.getDebtCommercialAmount()){
			total=total.add(iibDebtInfo.getDebtCommercialAmount());
		}
		if(null!=iibDebtInfo.getOtherDebtAmount()){
			total=total.add(iibDebtInfo.getOtherDebtAmount());
		}
		logger.debug("total debt>>"+total);
		return total;
	}
	
	public static BigDecimal  totalOtherDebtAmount(DebtInfo iibDebtInfo){
		if(null==iibDebtInfo){return null;}
		BigDecimal total = BigDecimal.ZERO;
		if(null!=iibDebtInfo.getWelfareAmount()){
			total = total.add(iibDebtInfo.getWelfareAmount());
		}
		if(null!=iibDebtInfo.getDebtCommercialAmount()){
			total=total.add(iibDebtInfo.getDebtCommercialAmount());
		}
		if(null!=iibDebtInfo.getOtherDebtAmount()){
			total=total.add(iibDebtInfo.getOtherDebtAmount());
		}
		logger.debug("total debt>>"+total);
		return total;
	}
	
	public static DebtInfoDataM  filterDebtInfo(ArrayList<DebtInfoDataM> uloDebtInfos,String debtType){
		 if(null!=uloDebtInfos && uloDebtInfos.size()>0 && (null!=debtType && !"".equals(debtType))){
			 for(DebtInfoDataM uloDebtInfo :uloDebtInfos){
				 if(debtType.equals(uloDebtInfo.getDebtType())){
					 return uloDebtInfo;
				 }
			 }
		 }
		 return null;
	}
	
	public static String[]  selectReferCriterias(String referCriteria){
		 if(null!=referCriteria && !"".equals(referCriteria)){
			  return referCriteria.split(",");
		 }
		 return null;
	}
	public static String  mapReferCriterias(List<decisionservice_iib.ReferCriteriaDataM> iibReferCriterias){
		if(null!=iibReferCriterias && iibReferCriterias.size()>0){
			StringBuilder criterias = new StringBuilder("");
			String COMMA="";
			for(decisionservice_iib.ReferCriteriaDataM iibCriteria : iibReferCriterias){
				if(null!=iibCriteria.getName() && !"".equals(iibCriteria.getName())){
					criterias.append(COMMA+iibCriteria.getName());
					COMMA=",";
				}
			}
			return criterias.toString();
		}
		return "";
	}
	public static String  mapRefercriterias(List<ReferCriterias> iibReferCriterias){
		if(null!=iibReferCriterias && iibReferCriterias.size()>0){
			StringBuilder criterias = new StringBuilder("");
			String COMMA="";
			for(ReferCriterias iibCriteria : iibReferCriterias){
				if(null!=iibCriteria.getName() && !"".equals(iibCriteria.getName())){
					criterias.append(COMMA+iibCriteria.getName());
					COMMA=",";
				}
			}
			return criterias.toString();
		}
		return "";
	}
	public static  decisionservice_iib.ApplicationDataM  filterIIBPersonalApplication(List<decisionservice_iib.ApplicationDataM> iibApplicationlist,String uloPersonalId){
		if(null!=iibApplicationlist){
			logger.debug("Ulo Personal Id >>" +uloPersonalId);
			for(decisionservice_iib.ApplicationDataM iibApplication : iibApplicationlist){
				logger.debug("iib Personal Id >>" +iibApplication.getPersonalId());
				if(uloPersonalId.equals(iibApplication.getPersonalId())){
					return iibApplication;
				}
			}
		}
		return null;
	}
	public static  Applications  filterIIBPersonalApplications(List<Applications> iibApplicationlist,String uloPersonalId){
		if(null!=iibApplicationlist){
			logger.debug("Ulo Personal Id >>" +uloPersonalId);
			for(Applications iibApplication : iibApplicationlist){
				logger.debug("iib Personal Id >>" +iibApplication.getPersonalId());
				if(uloPersonalId.equals(iibApplication.getPersonalId())){
					return iibApplication;
				}
			}
		}
		return null;
	}
}