package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class DocDecisionServiceResponseMapper extends DecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(DocDecisionServiceResponseMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
//		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
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
	public void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(decisionservice_iib.PersonalInfoDataM iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					continue;
				}
				logger.debug("find personal id >>"+iibPersonalInfo.getPersonalId());
				incomeInfoMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo, iibApplicationGroup);
				if(null!=iibPersonalInfo.getVerificationResult()){
					verificationResultMapper(uloApplicationGroup,uloPersonalInfo, iibPersonalInfo.getVerificationResult());
					if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult().getIncomeSources())){
						uloPersonalInfo.setIncomeSources(new ArrayList<IncomeSourceDataM>());
						for(decisionservice_iib.IncomeSourceDataM iibIncomeSource : iibPersonalInfo.getVerificationResult().getIncomeSources()){
							mapIncomeSource(uloPersonalInfo,iibIncomeSource);
						}
					}						
				}	
				
				decisionservice_iib.ApplicationDataM  iibApplication = ProcessUtil.filterIIBPersonalApplication(iibApplicationGroup.getApplications(), uloPersonalInfo.getPersonalId());
				if(null!=iibApplication){
					privilegeProjectCodeMapper(uloPersonalInfo, iibApplication);
				}	
			}
		}else{
			logger.debug("iib personal is null!");
		}
	}
	
	@Override
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null==uloLoan){
					logger.debug("Cant find ulo loan id>>"+iibLoan.getLoanId());
					continue;
				}			
				CardDataM uloCard = uloLoan.getCard();
				if(null!=uloCard){
					mapCard(uloLoan, iibLoan,iibApplication,uloApplicationGroup.getApplicationType());
				}												 
			}

		}else{
			logger.debug("iib loans is null!!");
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
	
	@Override
	public void mapCard(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan, decisionservice_iib.ApplicationDataM iibApplication,String uloApplicationType) {
		CardDataM uloCard = uloLoan.getCard();
		decisionservice_iib.CardDataM iibCard =   iibLoan.getCard();
		if(null!=iibCard){
			String cardType = ProcessUtil.getCardType(uloApplicationType,uloCard,iibLoan,iibApplication, uloLoan.getRequestCoaProductCode());
			if(null!=cardType && !"".equals(cardType)){
				uloCard.setCardType(cardType);
				if(ProcessUtil.isEligibleCoaProductCode(iibLoan)){
					uloCard.setRecommendCardCode(cardType);
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
			}	
			if(null!=iibCard.getPlasticCode() && !"".equals(iibCard.getPlasticCode())){
				uloCard.setPlasticCode(iibCard.getPlasticCode());
			}
		}else{
			logger.debug("iib card is null!!");
		}
	}
	
	@Override
	public void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup,VerificationResultDataM uloVerResult,decisionservice_iib.VerificationResultDataM iibVerResult)throws Exception {
		uloVerResult.setDocCompletedFlag(iibVerResult.getDocCompletedFlag());	
		logger.debug("iibVerResult.getDocCompletedFlag()>>"+iibVerResult.getDocCompletedFlag());
		if(null!=iibVerResult.getDocumentSuggestions() && iibVerResult.getDocumentSuggestions().size()>0){
			ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
			uloVerResult.setRequiredDocs(requireDocs);
			for(decisionservice_iib.DocumentSuggestionsDataM iibDocSuggestions : iibVerResult.getDocumentSuggestions()){
				mapDocumentSuggestions(uloVerResult, iibDocSuggestions);
			}
		}
		if(DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED").equals(uloVerResult.getVerPrivilegeResultCode())){
			ProcessUtil.addImageSplitAndRequiredDocCaseRequirePrivilegeVer(uloApplicationGroup);
		}	
	}
}
