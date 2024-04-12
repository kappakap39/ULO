package com.eaf.service.common.iib.eapp.mapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.ApplicationScores;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.BScores;
import com.ava.flp.eapp.iib.model.BolOrganizations;
import com.ava.flp.eapp.iib.model.Card;
import com.ava.flp.eapp.iib.model.CardLinkCards;
import com.ava.flp.eapp.iib.model.CardLinkCustomers;
import com.ava.flp.eapp.iib.model.CcaCampaign;
import com.ava.flp.eapp.iib.model.Doclists;
import com.ava.flp.eapp.iib.model.DocumentScenarios;
import com.ava.flp.eapp.iib.model.DocumentSuggestions;
import com.ava.flp.eapp.iib.model.FicoScore;
import com.ava.flp.eapp.iib.model.Guidelines;
import com.ava.flp.eapp.iib.model.IncomeSources;
import com.ava.flp.eapp.iib.model.KBankPayroll;
import com.ava.flp.eapp.iib.model.LoanPricings;
import com.ava.flp.eapp.iib.model.LoanTiers;
import com.ava.flp.eapp.iib.model.Loans;
import com.ava.flp.eapp.iib.model.NcbAccounts;
import com.ava.flp.eapp.iib.model.NcbAddress;
import com.ava.flp.eapp.iib.model.NcbIds;
import com.ava.flp.eapp.iib.model.NcbInfo;
import com.ava.flp.eapp.iib.model.NcbNames;
import com.ava.flp.eapp.iib.model.OrPolicyRules;
import com.ava.flp.eapp.iib.model.OrPolicyRulesDetails;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.PolicyRuleConditions;
import com.ava.flp.eapp.iib.model.PolicyRuleReasons;
import com.ava.flp.eapp.iib.model.PolicyRules;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.ava.flp.eapp.iib.model.WebVerifications;
import com.eaf.core.ulo.common.util.DecisionActionUtil;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM.VerifyId;
import com.eaf.orig.ulo.model.app.BScoreDataM;
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanPricingDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;
import com.eaf.orig.ulo.model.app.NCBDocumentHistoryDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PriorityPassVerification;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountDataM;
import com.eaf.orig.ulo.model.ncb.NcbAddressDataM;
import com.eaf.orig.ulo.model.ncb.NcbIdDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.orig.ulo.model.ncb.NcbNameDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.KBankHeader;

public class EFAEDecisionServiceResponseMapper extends
EDecisionServiceResponseMapper{
	private static transient Logger logger = Logger.getLogger(EFAEDecisionServiceResponseMapper.class);
	private KBankHeader  kbankHeader;	
	private static String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		logger.debug("start applicationGroupMapper!!");
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		//remove personal map per EFA
//		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
		mapApplicationGroup(uloApplicationGroup, iibApplicationGroup);
		
	}
	String BORROWER = ServiceCache.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = ServiceCache.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	@Override
	public  void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(Applications iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					if(null!=iibApplication.getVerificationResult()){
						verificationResultMapper(uloApplicationGroup,uloApplication, iibApplication.getVerificationResult(),null);
					}
				}else{
					logger.debug("iib application is null!!");
				}
				
			}
		} 
	}
	
	@Override
	public  void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(PersonalInfos iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					 logger.debug("Cant find personal id >>"+iibPersonalInfo.getPersonalId());
					 continue;
				}
				logger.debug("find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
				if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult())){
					verificationResultMapper(uloApplicationGroup,uloPersonalInfo, null,iibPersonalInfo.getVerificationResult());
							
				}else{
					logger.debug("iibPersonalInfo.getVerificationResult()  is null");
				}	
			}	
		}		
	}	
	
	@Override
	public  void verificationResultMapper(ApplicationGroupDataM uloApplicationGroup,Object object
			,VerificationResultApplications iibVerificationResult,VerificationResultPersonalInfos iibVerificationResultPerson) throws Exception{
		 if(object instanceof PersonalInfoDataM){
			 logger.debug("##mapper ulo verification####");
			 PersonalInfoDataM uloPersonalInfo =(PersonalInfoDataM)object;
			 VerificationResultDataM uloVerificationResult = uloPersonalInfo.getVerificationResult();
			 if(null==uloVerificationResult){
				 uloVerificationResult = new VerificationResultDataM();
				 uloPersonalInfo.setVerificationResult(uloVerificationResult);
				 logger.debug("verification is null will create new !!");
			 }
			 mapPersonalVerificationResult(uloApplicationGroup,uloVerificationResult, iibVerificationResultPerson);
		 }else if(object instanceof ApplicationDataM){
			 ApplicationDataM uloApplication = (ApplicationDataM)object;
			 VerificationResultDataM uloVerResult =uloApplication.getVerificationResult();
			 if(null==uloVerResult){
				 uloVerResult = new VerificationResultDataM();
				 uloApplication.setVerificationResult(uloVerResult);
			 }
			 mapApplicationVerificationResult(uloVerResult, iibVerificationResult);
		 }
	}
 
	@Override
	public  void policyRuleMapper(VerificationResultDataM uloVerResult,VerificationResultApplications iibVerResult)throws Exception{
		if(null!=iibVerResult.getPolicyrules()){
			List<PolicyRules> iibPolicyRules=  iibVerResult.getPolicyrules();
			ArrayList<PolicyRulesDataM> uloPolicyRuleResult = uloVerResult.getPolicyRules();
			if (null==uloPolicyRuleResult) {
				uloPolicyRuleResult = new ArrayList<PolicyRulesDataM>();
			}
			
			// Prepare ULO Policy
			Set<String> uloPolicyCodes = new HashSet<String>();
			for (PolicyRulesDataM uloPolicy : uloPolicyRuleResult) {
				if (uloPolicy == null)
					continue;
				uloPolicyCodes.add(uloPolicy.getPolicyCode());
			}
			
			// Check existing, add new and start mapping
			Set<String> iibRuleCodes = new HashSet<String>();
			for (PolicyRules iibRule : iibPolicyRules) {
				if (iibRule == null)
					continue;
	
				String iibRuleCode = iibRule.getPolicyCode();
				logger.debug("iibRuleCode>>"+iibRuleCode);
				List<PolicyRuleReasons> iibPolicyRuleResons = iibRule.getPolicyRuleReasons();
				if(null!=iibPolicyRuleResons && iibPolicyRuleResons.size()>0){
					for(PolicyRuleReasons  policyRuleReason : iibPolicyRuleResons){
						String iibReasonCode=policyRuleReason.getReasonCode();
						String surrogateKey = DecisionServiceUtil.getSurrogateKey(iibRuleCode, iibReasonCode);
						iibRuleCodes.add(surrogateKey);
						if (iibRuleCode != null && uloPolicyCodes.contains(iibRuleCode)) {// Map Existing
							PolicyRulesDataM uloRule = ProcessUtil.getULOPolicyByCode(uloPolicyRuleResult, iibRuleCode, iibReasonCode);
							if(uloRule == null){
								uloRule = new PolicyRulesDataM();
								uloPolicyRuleResult.add(uloRule);
							}
							mapPolicyRules(uloRule, iibRule,policyRuleReason); // Map policy here
						} else {
							// Add new
							PolicyRulesDataM uloRule = new PolicyRulesDataM();
							mapPolicyRules(uloRule, iibRule,policyRuleReason); // Map policy here
							uloPolicyRuleResult.add(uloRule);
						}
					}
				}else{
					String surrogateKey = DecisionServiceUtil.getSurrogateKey(iibRuleCode, "");
					iibRuleCodes.add(surrogateKey);
					if (uloPolicyCodes.contains(iibRuleCode)) {// Map Existing
						PolicyRulesDataM uloRule = ProcessUtil.getULOPolicyByCode(uloPolicyRuleResult, iibRuleCode);
						mapPolicyRules(uloRule, iibRule,null); // Map policy here
					} else {
						// Add new
						PolicyRulesDataM uloRule = new PolicyRulesDataM();
						mapPolicyRules(uloRule, iibRule,null); // Map policy here
						uloPolicyRuleResult.add(uloRule);
					}
				}
				
			}
			
			if(null!=uloPolicyRuleResult){
//				logger.debug("uloPolicyRuleResult>>>"+new Gson().toJson(uloPolicyRuleResult));
			}

			// Prune Policies
			for (Iterator<PolicyRulesDataM> it = uloPolicyRuleResult.iterator(); it.hasNext();) {
				PolicyRulesDataM uloPolicy = it.next();
				String surrogateKey = DecisionServiceUtil.getSurrogateKey(uloPolicy.getPolicyCode(), uloPolicy.getReason());
				if (!iibRuleCodes.contains(surrogateKey)) {
					it.remove();
				}
			}		
			uloVerResult.setPolicyRules(uloPolicyRuleResult);
		}else{
			logger.debug("iib policy rule is null!!");
		}		
	}
	@Override    
	public  void mapApplicationGroup(ApplicationGroupDataM  uloApplicationGroup,ApplicationGroup iibApplicationGroup) {
		uloApplicationGroup.setIsVetoEligible(DecisionServiceUtil.isVetoEligibles(iibApplicationGroup.getApplications()));
		uloApplicationGroup.setLastDecision(DecisionActionUtil.getAppGroupLastDecision(uloApplicationGroup));
		uloApplicationGroup.setLastDecisionDate(DecisionServiceUtil.getDate());
		uloApplicationGroup.setDecisionAction(DecisionActionUtil.getAppGroupLastDecision(uloApplicationGroup));
		
		String appType = ProcessUtil.getFinalApplicationTypes(iibApplicationGroup.getApplications());
		if (null !=appType && !appType.isEmpty() && !"".equals(appType)) {
//			uloApplicationGroup.setApplicationType(appType);
			ProcessUtil.setApplyType(appType, uloApplicationGroup);
		}else{
			logger.debug("apply type is null!!");
		}
		
		logger.debug("appType>>>"+appType);
		logger.debug("appType>>>"+uloApplicationGroup.getApplicationType());
	}
	@Override
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,Applications iibApplication){
		uloApplication.setRecommendDecision(ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision()));
		
		
		logger.debug("uloApplication.getRecommendDecision()>>"+uloApplication.getRecommendDecision());
		if (ProcessUtil.REC_APP_DECISION_APPROVE.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_APPROVE);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
		} else if (ProcessUtil.REC_APP_DECISION_REJECT.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_REJECT);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
			mapReasonPolicyRule(uloApplication,iibApplication);
		}	
	}
	
	@Override
	public void  mapPersonalApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,Applications iibApplication ) {
		PersonalRelationDataM uloPersonalRelation = uloApplicationGroup.getPersonalRelationByRefId(uloApplication.getApplicationRecordId()); 
		logger.debug("iibApplication.getPersonalId()>>"+iibApplication.getPersonalId());
		PersonalInfoDataM uloPersonal = uloApplicationGroup.getPersonalById(iibApplication.getPersonalId());
		if(null==uloPersonal){
			logger.debug("ULO PersonalInfo is null!!");
			return;
		}
		if(null==uloPersonalRelation){
			uloPersonalRelation = new PersonalRelationDataM();
			uloPersonalRelation.setPersonalId(iibApplication.getPersonalId());
			uloPersonalRelation.setRefId(uloApplication.getApplicationRecordId());
			uloPersonalRelation.setPersonalType(uloPersonal.getPersonalType());
			uloPersonal.getPersonalRelations().add(uloPersonalRelation);
		}else{
			uloPersonalRelation.setPersonalId(iibApplication.getPersonalId());
		}
	}

	@Override
	public  void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup,VerificationResultDataM uloVerResult
			,VerificationResultPersonalInfos iibVerResult) throws Exception{
		
//		logger.debug("iibVerResult>>"+new Gson().toJson(iibVerResult));
		// Map doc complete
		uloVerResult.setDocCompletedFlag(iibVerResult.getDocCompletedFlag());		
		uloVerResult.setRequiredVerWebFlag(iibVerResult.getRequiredVerWebFlag());
		uloVerResult.setRequirePriorityPass(iibVerResult.getRequiredPriorityPass());
		uloVerResult.setRequiredVerCustFlag(iibVerResult.getRequiredVerCustFlag());
		uloVerResult.setRequiredVerHrFlag(iibVerResult.getRequiredVerHrFlag());
		uloVerResult.setRequiredVerIncomeFlag(iibVerResult.getRequiredVerIncomeFlag());
		uloVerResult.setRequiredVerPrivilegeFlag(iibVerResult.getRequiredVerPrivilegeFlag());
		

		// Set require Customer Verification Flag
		boolean completedCusVer = ProcessUtil.isCompletedVerification(uloApplicationGroup, VerifyId.VERIFY_CUSTOMER);//ST000000000023 : Update only not completed, I0048 Also
		if(!completedCusVer){
			if (ProcessUtil.YES_FLAG.equalsIgnoreCase(uloVerResult.getRequiredVerCustFlag())) {
				uloVerResult.setVerCusResult(ProcessUtil.verRequiredDesc);
				uloVerResult.setVerCusResultCode(ProcessUtil.verRequiredCode);
				
				//uloVerResult.setIndentifyQuesitionSets((ArrayList<IdentifyQuestionSetDataM>) ProcessUtil.mergeCustomerQuestionSet(uloVerResult.getIndentifyQuesitionSets(), iibVerResult.getQuestionSets()));
				
				//Add new or replace questions by set codes
				String questionType = DecisionServiceCacheControl.getConstant("QUESTION_SET_TYPE_C");
				//uloVerResult.setIndentifyQuestions((ArrayList<IdentifyQuestionDataM>) ProcessUtil.replaceQuestionByType(uloVerResult.getIndentifyQuesitionSets(), uloVerResult.getIndentifyQuestions(), questionType));
				uloVerResult.setIndentifyQuestions((ArrayList<IdentifyQuestionDataM>) ProcessUtil.replaceQuestionByType(uloVerResult.getIndentifyQuesitionSets(), uloVerResult.getIndentifyQuestions(), questionType, uloApplicationGroup.getReprocessFlag(), VerifyId.VERIFY_CUSTOMER));
			} else {
				uloVerResult.setRequiredVerCustFlag(DecisionServiceUtil.FLAG_N);
				uloVerResult.setVerCusResult(ProcessUtil.verWaivedDesc);
				uloVerResult.setVerCusResultCode(ProcessUtil.verWaivedCode);
				logger.debug("ReprocessFlag : "+uloApplicationGroup.getReprocessFlag());
				if((ProcessUtil.YES_FLAG.equals(uloApplicationGroup.getReprocessFlag()))){
					uloVerResult.setIndentifyQuesitionSets(null);
					uloVerResult.setIndentifyQuestions(null);
					uloVerResult.setVerCusComplete(null);
				}
			}
		}

		// Set require HR Verification Flag
		boolean completedHrVer = ProcessUtil.isCompletedVerification(uloApplicationGroup, VerifyId.VERIFY_HR);//ST000000000023 : Update only not completed, I0048 Also
		logger.debug("completedHrVer : "+completedHrVer);		
		if(!completedHrVer){
			if (DecisionServiceUtil.FLAG_Y.equalsIgnoreCase(uloVerResult.getRequiredVerHrFlag())) {
				uloVerResult.setVerHrResult(ProcessUtil.verRequiredDesc);
				uloVerResult.setVerHrResultCode(ProcessUtil.verRequiredCode);
		//		uloVerResult.setIndentifyQuesitionSets((ArrayList<IdentifyQuestionSetDataM>) ProcessUtil.mergeHRQuestionSet(uloVerResult.getIndentifyQuesitionSets(), iibVerResult.getIndentifyQuesitionSets()));
				
				//Add new or replace questions by set codes
				String questionType = DecisionServiceCacheControl.getConstant("QUESTION_SET_TYPE_HR");;
				uloVerResult.setIndentifyQuestions((ArrayList<IdentifyQuestionDataM>) ProcessUtil.replaceQuestionByType(uloVerResult.getIndentifyQuesitionSets(), uloVerResult.getIndentifyQuestions(), questionType));

			} else {
				uloVerResult.setRequiredVerHrFlag(DecisionServiceUtil.FLAG_N);
				uloVerResult.setVerHrResult(ProcessUtil.verWaivedDesc);
				uloVerResult.setVerHrResultCode(ProcessUtil.verWaivedCode);
			}

		} 
		
		// Map require income verification flag
		if (DecisionServiceUtil.FLAG_Y.equals(uloVerResult.getRequiredVerIncomeFlag())) {
			uloVerResult.setSummaryIncomeResult(ProcessUtil.verRequiredDesc);
			uloVerResult.setSummaryIncomeResultCode(ProcessUtil.verRequiredCode);
		} else {			
			if(!ProcessUtil.isCompletedVerification(uloApplicationGroup, VerifyId.VERIFY_INCOME)){//Refer to I0048 : Correct verification result logic
				uloVerResult.setSummaryIncomeResult(ProcessUtil.verWaivedDesc);
				uloVerResult.setSummaryIncomeResultCode(ProcessUtil.verWaivedCode);				
				uloVerResult.setRequiredVerIncomeFlag(DecisionServiceUtil.FLAG_N);
			}								
		}
		
		//Map required ver web  flag
		if(null!=iibVerResult.getWebVerifications()&& iibVerResult.getWebVerifications().size()>0 
				&& DecisionServiceUtil.FLAG_Y.equals(iibVerResult.getRequiredVerWebFlag())){
			mapVerWebVerification(uloVerResult, iibVerResult.getWebVerifications(), uloApplicationGroup);
		}else{
			logger.debug("webverification is null!!");
			if(DecisionServiceUtil.FLAG_N.equals(iibVerResult.getRequiredVerWebFlag())){
				uloVerResult.setVerWebResult(ProcessUtil.verWaivedDesc);
				uloVerResult.setVerWebResultCode(ProcessUtil.verWaivedCode);
				uloVerResult.setWebVerifications(new ArrayList<WebVerificationDataM>());
			}
		}
		
//		if(null!=iibVerResult.getPriorityPassVerification() && iibVerResult.getPriorityPassVerification().size()>0){
//			for(decisionservice_iib.PriorityPassVerificationTypeDataM iibPriorityPass : iibVerResult.getPriorityPassVerification()){
//				mapPriorityPassVerification(uloVerResult,iibPriorityPass);
//			}
//		}
		
		
		if(null!=iibVerResult.getDocumentSuggestions() && iibVerResult.getDocumentSuggestions().size()>0){
			ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
			uloVerResult.setRequiredDocs(requireDocs);
			for(DocumentSuggestions iibDocSuggestions : iibVerResult.getDocumentSuggestions()){
				mapDocumentSuggestions(uloVerResult, iibDocSuggestions);
			}	
		}
		
		// Map require privilege verification flag
		if (DecisionServiceUtil.FLAG_Y.equals(uloVerResult.getRequiredVerPrivilegeFlag())) {
			uloVerResult.setVerPrivilegeResult(ProcessUtil.verRequiredDesc);
			uloVerResult.setVerPrivilegeResultCode(ProcessUtil.verRequiredCode);
			ProcessUtil.addImageSplitAndRequiredDocCaseRequirePrivilegeVer(uloApplicationGroup);
		} else {
			uloVerResult.setRequiredVerPrivilegeFlag(DecisionServiceUtil.FLAG_N);
			uloVerResult.setVerPrivilegeResult(ProcessUtil.verWaivedDesc);
			uloVerResult.setVerPrivilegeResultCode(ProcessUtil.verWaivedCode);
			logger.debug("reprocessFlag : "+uloApplicationGroup.getReprocessFlag());
			if(DecisionServiceUtil.FLAG_Y.equals(uloApplicationGroup.getReprocessFlag())){
				uloVerResult.setPrivilegeProjectCodes(null);
			}
		}
	}	
	
	
	@Override
	public  void mapApplicationVerificationResult(VerificationResultDataM uloVerResult,VerificationResultApplications iibVerResult) throws Exception{
			policyRuleMapper(uloVerResult,iibVerResult);
	}
	
	
	@Override		
	public void mapPolicyRules(PolicyRulesDataM uloPolicy, PolicyRules iibPolicyRules,PolicyRuleReasons iibPolicyReason) throws Exception {
		if (iibPolicyRules == null) {
			logger.debug("iibPolicyRules is null");
			return;
		}
		if (uloPolicy == null) {
			logger.debug("uloPolicy is null");
			return;
		}
		logger.debug("iibPolicyRules.getPolicyCode()>>"+iibPolicyRules.getPolicyCode());
		uloPolicy.setPolicyCode(iibPolicyRules.getPolicyCode());
		uloPolicy.setVerifiedResult(iibPolicyRules.getResult());
	//	uloPolicy.setOverrideFlag(iibPolicyRules.getOverrideFlag());
		if(null!=iibPolicyReason){
			uloPolicy.setReason(iibPolicyReason.getReasonCode());
			if(null!=iibPolicyReason.getReasonRank() && !"".equals(iibPolicyReason.getReasonRank())){
				uloPolicy.setRank(Integer.valueOf(iibPolicyReason.getReasonRank()));
			}
		}

		// Map orPolicyRule
		List<OrPolicyRules> iibOrPolicyRules = iibPolicyRules.getOrPolicyRules();
		if (iibOrPolicyRules != null && iibOrPolicyRules.size() > 0) {
			// Prepare ULO rules
			List<ORPolicyRulesDataM> uloOrPolicies = uloPolicy.getOrPolicyRules();
			if (uloOrPolicies == null) {
				uloOrPolicies = new ArrayList<ORPolicyRulesDataM>();
				uloPolicy.setOrPolicyRules((ArrayList<ORPolicyRulesDataM>) uloOrPolicies);
			}
			Set<String> uloCodes = new HashSet<String>();
			for (ORPolicyRulesDataM uloOrPolicy : uloOrPolicies) {
				if (uloOrPolicy == null || uloOrPolicy.getPolicyCode() == null || uloOrPolicy.getPolicyCode().isEmpty())
					continue;
				uloCodes.add(uloOrPolicy.getPolicyCode());
			}

			// Map existing or add new
			 Set<String> iibOverideCodes= new HashSet<String>();
			for (OrPolicyRules iibOrpolicy : iibOrPolicyRules) {
				if (iibOrpolicy == null)
					continue;
				String iibOverideCode = iibOrpolicy.getOverideCode();
				if (iibOverideCode == null || iibOverideCode.isEmpty())
					continue;
				iibOverideCodes.add(iibOverideCode);

				if (uloCodes.contains(iibOverideCode)) {// Map existing
					ORPolicyRulesDataM uloOrPolicy = ProcessUtil.getULOORPolicyByCode(uloOrPolicies, iibOverideCode);
					mapORPolicyRules(uloOrPolicy, iibOrpolicy, iibPolicyRules);
				} else {// Add new
					ORPolicyRulesDataM uloOrPolicy = new ORPolicyRulesDataM();
					mapORPolicyRules(uloOrPolicy, iibOrpolicy, iibPolicyRules);
					uloOrPolicies.add(uloOrPolicy);
				}
			}
			
			// Prune data
			for(Iterator<ORPolicyRulesDataM> it = uloOrPolicies.iterator(); it.hasNext();){
				ORPolicyRulesDataM uloORPolicy = it.next();
				if(uloORPolicy == null)continue;
				if(!iibOverideCodes.contains(uloORPolicy.getPolicyCode())){
					it.remove();
				}
			}
			logger.debug("Before policy condition :: ");
			//map policy condition
			ArrayList<PolicyRulesConditionDataM> uloPolicyRuleConditions = uloPolicy.getPolicyRulesConditions();
			if (uloPolicyRuleConditions == null) {
				uloPolicyRuleConditions = new ArrayList<PolicyRulesConditionDataM>();
				uloPolicy.setPolicyRulesConditions(uloPolicyRuleConditions);
			}
			List<PolicyRuleConditions> iibPolicyRuleConditions =  iibPolicyRules.getPolicyRuleConditions();
			ArrayList<String> conditionCodes = new ArrayList<String>();
			logger.debug("Before policy condition :: "+iibPolicyRuleConditions.size()+", is null : "+(null != iibPolicyRuleConditions?"not null":"null"));
			if(null != iibPolicyRuleConditions && iibPolicyRuleConditions.size()>0){
				for(PolicyRuleConditions iibPolicyCondition : iibPolicyRuleConditions){
					 PolicyRulesConditionDataM uloPolicyRuleCondition = uloPolicy.getConditionCode(iibPolicyCondition.getConditionCode());
					 conditionCodes.add(iibPolicyCondition.getConditionCode());
					 logger.debug("iibPolicyCondition.getConditionCode()>>"+iibPolicyCondition.getConditionCode());
					 if(null==uloPolicyRuleCondition){
						 logger.debug("uloPolicy.getConditionCode(iibPolicyCondition.getConditionCode()) :: "+uloPolicy.getConditionCode(iibPolicyCondition.getConditionCode()));
						 uloPolicyRuleCondition = new PolicyRulesConditionDataM();
						 mapPolicyCondition(uloPolicyRuleCondition, iibPolicyCondition);
						 uloPolicyRuleConditions.add(uloPolicyRuleCondition);
					 }
				}
//				logger.debug("uloPolicyRuleConditions>>"+new Gson().toJson(uloPolicyRuleConditions));
			}
			
			// Prune data
			if(null!=uloPolicyRuleConditions && null!=iibPolicyRuleConditions){
				for(Iterator<PolicyRulesConditionDataM> it = uloPolicyRuleConditions.iterator(); it.hasNext();){
					PolicyRulesConditionDataM uloPolicyCondition = it.next();
					if(uloPolicyCondition == null)continue;
					if(!conditionCodes.contains(uloPolicyCondition.getConditionCode())){
						it.remove();
					}
				}
			}
		}
	}
	@Override
	public void mapPolicyCondition(PolicyRulesConditionDataM uloPolicyRulesCondition,PolicyRuleConditions iibPolicyRuleCondition ) throws Exception{
		uloPolicyRulesCondition.setConditionCode(iibPolicyRuleCondition.getConditionCode());
	}
	@Override
	public void mapORPolicyRules(ORPolicyRulesDataM uloORPolicy, OrPolicyRules iibOrpolicy,PolicyRules iibPolicyRule) {
		if (uloORPolicy == null) {
			return;
		}
		if (iibOrpolicy == null) {
			return;
		}
		
		//Set minimum authority
		uloORPolicy.setMinApprovalAuth(iibOrpolicy.getMinApprovalAuth());

		
		uloORPolicy.setPolicyCode(iibOrpolicy.getOverideCode());
		if (iibPolicyRule != null)
			uloORPolicy.setVerifiedResult(iibOrpolicy.getOverideResult());// Field is not valid

		// Map orPolicyRuleDetail
		List<OrPolicyRulesDetails> iibOrRulesDetail = iibOrpolicy.getOrPolicyRulesDetails();
		if (iibOrRulesDetail != null && iibOrRulesDetail.size() > 0) {
			// Prepare ULO models
			List<ORPolicyRulesDetailDataM> uloDetails = uloORPolicy.getOrPolicyRulesDetails();
			if (uloDetails == null) {
				uloDetails = new ArrayList<ORPolicyRulesDetailDataM>();
				uloORPolicy.setOrPolicyRulesDetails((ArrayList<ORPolicyRulesDetailDataM>) uloDetails);
			}
			Set<String> uloCodes = new HashSet<String>();
			for (ORPolicyRulesDetailDataM uloDetail : uloDetails) {
				if (uloDetail == null || uloDetail.getGuidelineCode() == null || uloDetail.getGuidelineCode().isEmpty())
					continue;
				uloCodes.add(uloDetail.getGuidelineCode());
			}

			// Map existing or add new
			 Set<String> iibGuideLineCodes = new HashSet<String>();
			for (OrPolicyRulesDetails  iibRuleDetail : iibOrRulesDetail) {
				if (iibRuleDetail == null)
					continue;
				String code = iibRuleDetail.getGuidelineCode();
				if (code == null || code.isEmpty())
					continue;
				iibGuideLineCodes.add(code);
				if (uloCodes.contains(code)) {// Map existing
					ORPolicyRulesDetailDataM uloORPolicyDetail = ProcessUtil.getULOORPolicyDetailByCode(uloDetails, code);
					mapORPolicyRulesDetail(uloORPolicyDetail, iibRuleDetail);
				} else {
					ORPolicyRulesDetailDataM uloORPolicyDetail = new ORPolicyRulesDetailDataM();
					mapORPolicyRulesDetail(uloORPolicyDetail, iibRuleDetail);
					uloDetails.add(uloORPolicyDetail);
				}
			}
			
			// Prune data
			for(Iterator<ORPolicyRulesDetailDataM> it = uloDetails.iterator(); it.hasNext();){
				ORPolicyRulesDetailDataM uloOrDetail = it.next();
				if(uloOrDetail == null)continue;
				if(!iibGuideLineCodes.contains(uloOrDetail.getGuidelineCode())){
					it.remove();
				}
			}
				
		}
	}
	@Override
	public void mapORPolicyRulesDetail(ORPolicyRulesDetailDataM uloORPolicy, OrPolicyRulesDetails iibOrPolicyRuleDetail) {
		if (iibOrPolicyRuleDetail == null) {
			return;
		}
		if (uloORPolicy == null) {
			return;
		}

		uloORPolicy.setGuidelineCode(iibOrPolicyRuleDetail.getGuidelineCode());
		if(null!=iibOrPolicyRuleDetail.getRank()){
			uloORPolicy.setRank(DecisionServiceUtil.bigDecimalToInt(iibOrPolicyRuleDetail.getRank()));
		}
		uloORPolicy.setVerifiedResult(iibOrPolicyRuleDetail.getResult());

		// Set children
		uloORPolicy.setGuidelines((ArrayList<GuidelineDataM>) mergeXRuleGuideLine(uloORPolicy.getGuidelines(), iibOrPolicyRuleDetail));
	}
	
	@Override
	public List<GuidelineDataM> mergeXRuleGuideLine(List<GuidelineDataM> uloGuideLines,OrPolicyRulesDetails iibOrPolicyRulesDetail) {
		// Validation
		if (iibOrPolicyRulesDetail == null) {
			return uloGuideLines;
		}
		List<Guidelines> iibGuidelines   = iibOrPolicyRulesDetail.getGuidelines();
		if (iibGuidelines == null || iibGuidelines.size() < 1) {
			return uloGuideLines;
		}

		// Prepare ULO data
		if (uloGuideLines == null) {
			uloGuideLines = new ArrayList<GuidelineDataM>();
		}

		Set<String> uloNames = new HashSet<String>();
		for (GuidelineDataM guideLine : uloGuideLines) {
			if (guideLine == null)
				continue;
			uloNames.add(guideLine.getName());
		}

		// Add new or Update existing name
		Set<String> iibNameSet = new HashSet<String>();
		for (Guidelines iibGuideline : iibGuidelines) {
			if (iibGuideline == null)
				continue;
			iibNameSet.add(iibGuideline.getName());
			GuidelineDataM uloGuideLine = ProcessUtil.getGuideLineByName(uloGuideLines, iibGuideline.getName());
			if (uloGuideLine == null) {
				uloGuideLine = new GuidelineDataM();
				uloGuideLines.add(uloGuideLine);
			}
			mapGuideline(uloGuideLine, iibGuideline);
		}
		
		// Prune data
		for(Iterator<GuidelineDataM> it = uloGuideLines.iterator(); it.hasNext();){
			GuidelineDataM uloGuide = it.next();
			if(uloGuide == null)continue;
			if(!iibNameSet.contains(uloGuide.getName())){
				it.remove();
			}
		}
		return uloGuideLines;
	}
	@Override
	public  void mapGuideline(GuidelineDataM uloGuideLine, Guidelines iibGuideline ) {
		if (uloGuideLine == null) {
			return;
		}
		if (iibGuideline == null) {
			return;
		}

		uloGuideLine.setName(iibGuideline.getName());
		uloGuideLine.setValue(iibGuideline.getValue());
	}
			
	public  void mapLoan(LoanDataM uloLoan ,Loans iibLoan){
		uloLoan.setLoanId(iibLoan.getLoanId());
		uloLoan.setMaxCreditLimit(iibLoan.getMaxCreditLimit());
		uloLoan.setMaxCreditLimitBot(iibLoan.getMaxCreditLimitBot());
		uloLoan.setMinCreditLimit(iibLoan.getMinCreditLimit());
		uloLoan.setRecommendLoanAmt(iibLoan.getRecommendLoanAmt());
		uloLoan.setRecommendTerm(iibLoan.getRecommendTerm());
		//uloLoan.setMaxCreditLimitWithOutDBR(iibLoan.getMaxCreditLimitWithOutDBR());
		
		if(null==uloLoan.getLoanAmt() ||  BigDecimal.ZERO.compareTo(uloLoan.getLoanAmt())==0){
			uloLoan.setLoanAmt(iibLoan.getRecommendLoanAmt());
		}
		//uloLoan.setCurrentCreditLimit(iibLoan.getCurrentCreditLimit());
		//uloLoan.setRateType(iibLoan.getRateType());
		//uloLoan.setSignSpread(iibLoan.getSignSpread());
		uloLoan.setFinalCreditLimit(iibLoan.getNormalCreditLimit());
	}
	@Override
	public  void mapCard(LoanDataM uloLoan,Loans iibLoan,Applications iibApplicatin ,String uloApplicationType){	
		CardDataM uloCard = uloLoan.getCard();
		Card iibCard =   iibLoan.getCard();
		if(null!=iibCard){
			if(null!=iibCard.getPlasticCode() && !"".equals(iibCard.getPlasticCode())){
				uloCard.setPlasticCode(iibCard.getPlasticCode());
			}

			uloCard.setCardNo(iibCard.getCardNo());
//			uloCard.setCardNoEncrypted(iibCard.getCardNoEncrypted());
//			if(!ServiceUtil.empty(iibCard.getCardNoMark())){
//				uloCard.setCardNoMark(iibCard.getCardNoMark());
//			}
			if(null!=iibCard.getCardlinkCardCode() && !"".equals(iibCard.getCardlinkCardCode())){
				uloCard.setCardLinkCardCode(iibCard.getCardlinkCardCode());
			}
			
			if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
				LoanPricings iibLoanPricing = new LoanPricings();
				iibLoanPricing = iibLoan.getLoanPricings().get(0);
				uloCard.setCardFee(iibLoanPricing.getFeeWaiveCode());
			}	
			String cardType = ProcessUtil.getCardType(uloApplicationType,uloCard, iibLoan,iibApplicatin, uloLoan.getRequestCoaProductCode());
			if(null!=cardType && !"".equals(cardType)){
				uloCard.setCardType(cardType);
				if(ProcessUtil.isEligibleCoaProductCode(iibLoan)){
					uloCard.setRecommendCardCode(cardType);
				}
			}
			
			String cardLevel = ProcessUtil.getCardTypeLevel(uloApplicationType, iibLoan,iibApplicatin);
			if(null!=cardLevel && !"".equals(cardLevel)){
				uloCard.setCardLevel(cardLevel);
			}
		}
	}
	@Override
	public void mapThaiBevPartner(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup iibApplicationGroup){
		List<PersonalInfos> iibPersonalInfos = iibApplicationGroup.getPersonalInfos();
		String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
		String THAIBEV_CARD_TYPE = ServiceCache.getConstant("THAIBEV_CARD_TYPE");
		for(PersonalInfos iibPersonalInfo : iibPersonalInfos){
			String personalId = iibPersonalInfo.getPersonalId();
			ArrayList<ApplicationDataM> applications = uloApplicationGroup.filterApplicationPersonalLifeCycle(personalId, PERSONAL_RELATION_APPLICATION_LEVEL);
			if(!ServiceUtil.empty(applications)){
				for(ApplicationDataM application : applications){
					LoanDataM loan = application.getLoan();
					if(!ServiceUtil.empty(loan) && !ServiceUtil.empty(loan.getCard()) && THAIBEV_CARD_TYPE.equals(loan.getCard().getCardType())){
						CardDataM card = loan.getCard();
//						if(!ServiceUtil.empty(iibPersonalInfo) && !ServiceUtil.empty(iibPersonalInfo.getThaiBevPartner())){
//							card.setMembershipNo(iibPersonalInfo.getThaiBevPartner().getThaiBevMembershipNo());
//						}
					}
				}
			}
		}
	}
	
	
	@Override
	public void mapReasonPolicyRulePersonalLevel(ApplicationGroupDataM uloApplicationGroup, VerificationResultApplications iibVer,ApplicationDataM application){
		ArrayList<ReasonDataM> reasons = new ArrayList<ReasonDataM>();
		ArrayList<String> uloReasonsCodeApplication = uloApplicationGroup.getReasonCodes();
//		decisionservice_iib.VerificationResultDataM iibVer = iibApplication.getVerificationResult();
		if(!ServiceUtil.empty(iibVer)){
			List<PolicyRules> iibPolicyRules = iibVer.getPolicyrules();
			ArrayList<ReasonLogDataM> reasonLogs = new ArrayList<ReasonLogDataM>();
			ArrayList<ReasonDataM> reasonsList = new ArrayList<ReasonDataM>();
			if(!ServiceUtil.empty(iibPolicyRules)){
				for(PolicyRules iibPolicyRule : iibPolicyRules){
					if(!ServiceUtil.empty(iibPolicyRule.getPolicyRuleReasons())){
						List<PolicyRuleReasons> iibPolicyRuleReasons = iibPolicyRule.getPolicyRuleReasons();
						for(PolicyRuleReasons iibPolicyRuleReason:iibPolicyRuleReasons){
							if(!ServiceUtil.empty(iibPolicyRuleReason) && !ServiceUtil.empty(iibPolicyRuleReason.getReasonCode()) && !uloReasonsCodeApplication.contains(iibPolicyRuleReason.getReasonCode())){
								ReasonDataM reason = new ReasonDataM();
								reason.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
								reason.setReasonCode(iibPolicyRuleReason.getReasonCode());
								reason.setReasonType(ServiceCache.getConstant("RECOMMEND_DECISION_REJECTED"));
								reasonsList.add(reason);
								
								ReasonLogDataM reasonLog = new ReasonLogDataM();
								reasonLog.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
								reasonLog.setReasonCode(iibPolicyRuleReason.getReasonCode());
								reasonLog.setReasonType(ServiceCache.getConstant("RECOMMEND_DECISION_REJECTED"));
								reasonLogs.add(reasonLog);
							}
						}
					}
				}
			}
		
			if(!ServiceUtil.empty(reasonLogs) && !ServiceUtil.empty(reasonsList)){
				uloApplicationGroup.setReasonLogs(reasonLogs);
				uloApplicationGroup.setReasons(reasonsList);
			}
		}
	}
	@Override
	public void mapReasonPolicyRule(ApplicationDataM uloApplication, Applications iibApplication){
		ArrayList<ReasonDataM> reasons = new ArrayList<ReasonDataM>();
		ArrayList<String> uloReasonsCodeApplication = uloApplication.getReasonCodes();
		VerificationResultApplications iibVer = iibApplication.getVerificationResult();
		if(null != iibVer){
			List<PolicyRules> iibPolicyRules = iibVer.getPolicyrules();
			ArrayList<ReasonLogDataM> reasonLogs = new ArrayList<ReasonLogDataM>();
			ArrayList<ReasonDataM> reasonsList = new ArrayList<ReasonDataM>();
			for(PolicyRules iibPolicyRule : iibPolicyRules){
				if(!ServiceUtil.empty(iibPolicyRule.getPolicyRuleReasons())){
					List<PolicyRuleReasons> iibPolicyRuleReasons = iibPolicyRule.getPolicyRuleReasons();
					for(PolicyRuleReasons iibPolicyRuleReason:iibPolicyRuleReasons){
						if(!ServiceUtil.empty(iibPolicyRuleReason) && !ServiceUtil.empty(iibPolicyRuleReason.getReasonCode()) && !uloReasonsCodeApplication.contains(iibPolicyRuleReason.getReasonCode())){
							ReasonDataM reason = new ReasonDataM();
							reason.setApplicationGroupId(uloApplication.getApplicationGroupId());
							reason.setApplicationRecordId(uloApplication.getApplicationRecordId());
							reason.setReasonCode(iibPolicyRuleReason.getReasonCode());
							reason.setReasonType(ServiceCache.getConstant("RECOMMEND_DECISION_REJECTED"));
							reasonsList.add(reason);
							
							ReasonLogDataM reasonLog = new ReasonLogDataM();
							reasonLog.setApplicationGroupId(uloApplication.getApplicationGroupId());
							reasonLog.setApplicationRecordId(uloApplication.getApplicationRecordId());
							reasonLog.setReasonCode(iibPolicyRuleReason.getReasonCode());
							reasonLog.setReasonType(ServiceCache.getConstant("RECOMMEND_DECISION_REJECTED"));
							reasonLogs.add(reasonLog);
						}
					}
				}
			}
			if(!ServiceUtil.empty(reasonLogs) && !ServiceUtil.empty(reasonsList)){
				uloApplication.setReasonLogs(reasonLogs);
				uloApplication.setReasons(reasonsList);
			}
		}
	}
	
	public KBankHeader getKbankHeader() {
		return kbankHeader;
	}
	public void setKbankHeader(KBankHeader kbankHeader) {
		this.kbankHeader = kbankHeader;
	}

	private boolean processRequireVerify(List<PersonalInfoDataM> personals){
		if ((personals == null) || (personals.isEmpty())) {
			return false;
		}
		for (PersonalInfoDataM person : personals) {
			if (person != null) {
				VerificationResultDataM ver = person.getVerificationResult();
				if (ver != null) {
					if ("Y".equalsIgnoreCase(ver.getRequiredVerCustFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerHrFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerPrivilegeFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerWebFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerIncomeFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequirePriorityPass())) {
								return true;
					}
					
					if (!PersonalInfoDataM.PersonalType.INFO.equals(person.getPersonalType()) && 
							"N".equalsIgnoreCase(ver.getDocCompletedFlag())){
						return true;
					}
				}
			}
		}
		return false;
	}
}
