package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.DecisionActionUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class DCDecisionServiceResponseMapper extends DecisionServiceResponseMapper{
	private static transient Logger logger = Logger.getLogger(DCDecisionServiceResponseMapper.class);
	private static String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		mapApplicationGroup(uloApplicationGroup, iibApplicationGroup);
		mapThaiBevPartner(uloApplicationGroup, iibApplicationGroup);
		
		//Mapping null application
		ArrayList<PersonalInfoDataM> personalInfos = uloApplicationGroup.getUsingPersonalInfo();
		for(PersonalInfoDataM personalInfo : personalInfos){
			if(!ServiceUtil.empty(personalInfo)){
				ArrayList<ApplicationDataM> applications = uloApplicationGroup.getApplications(personalInfo.getPersonalId(),personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
				if(ServiceUtil.empty(applications)){
					mapApplicationCaseNoApplication(iibApplicationGroup, uloApplicationGroup,personalInfo.getPersonalId());
				}
			}
		}

	}
		
	@Override
	public  void mapApplicationGroup(ApplicationGroupDataM  uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup){
		uloApplicationGroup.setIsVetoEligible(DecisionServiceUtil.isVetoEligible(iibApplicationGroup.getApplications()));
		uloApplicationGroup.setLastDecision(DecisionActionUtil.getAppGroupLastDecision(uloApplicationGroup));
		uloApplicationGroup.setLastDecisionDate(DecisionServiceUtil.getDate());
		uloApplicationGroup.setDecisionAction(DecisionActionUtil.getAppGroupLastDecision(uloApplicationGroup));
		ArrayList<ApplicationDataM> applications = uloApplicationGroup.getApplications();
	
		if(applications == null || (applications!=null && applications.size() <= 0)){
			uloApplicationGroup.setIsVetoEligible(DecisionServiceUtil.isVetoEligible(iibApplicationGroup.getApplications()));
			uloApplicationGroup.setLastDecision(getAppGroupLastDecision(uloApplicationGroup,iibApplicationGroup));
			uloApplicationGroup.setLastDecisionDate(DecisionServiceUtil.getDate());
			uloApplicationGroup.setDecisionAction(getAppGroupLastDecision(uloApplicationGroup,iibApplicationGroup));			
		}
		
		String appType = ProcessUtil.getFinalApplicationType(iibApplicationGroup.getApplications());
		if (null !=appType && !appType.isEmpty() && !"".equals(appType)) {
//			uloApplicationGroup.setApplicationType(appType);
			ProcessUtil.setApplyType(appType, uloApplicationGroup);
		}else{
			logger.debug("apply type is null!!");
		}
		
		logger.debug("appType>>>"+appType);
	}
	
	@Override
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null==uloLoan){
					logger.debug("cant find ulo loan id>>"+iibLoan.getLoanId());
					continue;
				}
			
				CardDataM uloCard = uloLoan.getCard();
				if(null!=uloCard){
					mapCard(uloLoan, iibLoan,iibApplication,uloApplicationGroup.getApplicationType());	
				}											 
			}
		}
	}
	
	@Override
	public void mapCard(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan,decisionservice_iib.ApplicationDataM iibApplication, String uloApplicationType) {
		CardDataM uloCard = uloLoan.getCard();
		decisionservice_iib.CardDataM iibCard =   iibLoan.getCard();
		if(null!=iibCard){
			String cardType = ProcessUtil.getCardType(uloApplicationType,uloCard,iibLoan,iibApplication, uloLoan.getRequestCoaProductCode());
			if(null!=cardType && !"".equals(cardType)){
				uloCard.setCardType(cardType);
				if(ProcessUtil.isEligibleCoaProductCode(iibLoan)){
					uloCard.setRecommendCardCode(cardType);
				}	
			}	
			String cardLevel = ProcessUtil.getCardTypeLevel(uloApplicationType,iibLoan,iibApplication);
			if(null!=cardLevel && !"".equals(cardLevel)){
				uloCard.setCardLevel(cardLevel);
			}
			if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
				decisionservice_iib.LoanPricingDataM iibLoanPricing = new decisionservice_iib.LoanPricingDataM();
				iibLoanPricing = iibLoan.getLoanPricings().get(0);
				uloCard.setCardFee(iibLoanPricing.getFeeWaiveCode());
			}	
			if(null!=iibCard.getPlasticCode() && !"".equals(iibCard.getPlasticCode())){
				uloCard.setPlasticCode(iibCard.getPlasticCode());
			}
		}
	}
	
	@Override
	public void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication) {
			uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
			uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
			uloApplication.setIsVetoEligibleFlag(iibApplication.getIsVetoEligibleFlag());		
			mapPersonalApplication(uloApplicationGroup, uloApplication, iibApplication);	
			uloApplication.setRecommendDecision(ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision()));
			
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
			//#CR-ADD
			if(null!=iibApplication.getRecommendProjectCode() && !"".equals(iibApplication.getRecommendProjectCode())){
				uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
			}
			if(null!=iibApplication.getReExecuteKeyProgramFlag() && !"".equals(iibApplication.getReExecuteKeyProgramFlag())){
				uloApplication.setReExecuteKeyProgramFlag(iibApplication.getReExecuteKeyProgramFlag());
			}
	}
	
	public static String getAppGroupLastDecision(ApplicationGroupDataM uloAppGroup,decisionservice_iib.ApplicationGroupDataM iibAppGroup) {
		if (uloAppGroup == null)
			return null;

		if(iibAppGroup == null)
			return null;
		
		List<decisionservice_iib.ApplicationDataM> iibAppList = iibAppGroup.getApplications();
	
		// Begin BPM Decision logic
		boolean missingDecision = false;
		boolean hasApprove = false;
		boolean hasReject = false;
		boolean hasPreceed = false;
		boolean hasRefer = false;

		String DECISION_SERVICE_APPPROVE = ServiceCache.getConstant("DECISION_SERVICE_APPPROVE");
		String DECISION_SERVICE_REJECTED = ServiceCache.getConstant("DECISION_SERVICE_REJECTED");
		String DECISION_SERVICE_REFERRED = ServiceCache.getConstant("DECISION_SERVICE_REFERRED");
		String DECISION_SERVICE_PROCEED = ServiceCache.getConstant("DECISION_SERVICE_PROCEED");
		String finalDecisionTypeCancel = ServiceCache.getConstant("DECISION_FINAL_DECISION_CANCEL");
		if (DECISION_SERVICE_APPPROVE == null) {
			throw new NullPointerException("Constant FINAL_APP_DECISION_APPROVE from BPMCacheControl is not presented and loaded correctly");
		}
		if (DECISION_SERVICE_REJECTED == null) {
			throw new NullPointerException("Constant FINAL_APP_DECISION_REJECT from BPMCacheControl is not presented and loaded correctly");
		}

		for (decisionservice_iib.ApplicationDataM app : iibAppList) {
			if (app == null)
				continue;
			if(finalDecisionTypeCancel.equalsIgnoreCase(app.getFinalAppDecision()))
				continue;
			String recommendDecision = ProcessUtil.getULORecommendDecision(app.getRecommendDecision());
			if (recommendDecision == null || recommendDecision.isEmpty()) {
				missingDecision = true;
			}
			if (DECISION_SERVICE_APPPROVE.equalsIgnoreCase(recommendDecision)) {
				hasApprove = true;
			} else if (DECISION_SERVICE_REJECTED.equalsIgnoreCase(recommendDecision)) {
				hasReject = true;
			} else if (DECISION_SERVICE_REFERRED.equalsIgnoreCase(recommendDecision)) {
				hasRefer = true;
			} else if (DECISION_SERVICE_PROCEED.equalsIgnoreCase(recommendDecision)) {
				hasPreceed = true;
			}
		}

		if (missingDecision) {
			// throw new IllegalArgumentException("There're some missing decisions in OrigApplication! Verify that all recommend decisions have" +
			// " been sent/mapped from decision service");
			logger.warn("There're some missing decisions in OrigApplication! Verify that all recommend decisions have" + " been sent/mapped from decision service");
		}
		// Set result
		String result = null;
		if (hasRefer) {
			result = DECISION_SERVICE_REFERRED;
		} else if (hasPreceed) {
			result = DECISION_SERVICE_PROCEED;
		} else if (hasApprove) {
			result = DECISION_SERVICE_APPPROVE;
		} else if (hasReject) {
			result = DECISION_SERVICE_REJECTED;
		}
		return result;

	}
}
