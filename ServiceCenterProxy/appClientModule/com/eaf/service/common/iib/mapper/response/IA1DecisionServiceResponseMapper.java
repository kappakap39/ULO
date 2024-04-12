package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.DecisionActionUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class IA1DecisionServiceResponseMapper extends DecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(IA1DecisionServiceResponseMapper.class);	
	private String IA_APPLICATION_TYPE="";
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		IA_APPLICATION_TYPE = ProcessUtil.getFinalApplicationType(iibApplicationGroup.getApplications());	
		logger.debug("IA_APPLICATION_TYPE>>"+IA_APPLICATION_TYPE);
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
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
				logger.debug("uloPersonalInfo : "+uloPersonalInfo);
				if(null!=uloPersonalInfo){
					mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);
					if(null!=iibPersonalInfo.getVerificationResult()){
						verificationResultMapper(uloApplicationGroup,uloPersonalInfo, iibPersonalInfo.getVerificationResult());
						if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult().getIncomeSources())){
							uloPersonalInfo.setIncomeSources(new ArrayList<IncomeSourceDataM>());
							for(decisionservice_iib.IncomeSourceDataM iibIncomeSource : iibPersonalInfo.getVerificationResult().getIncomeSources()){
								mapIncomeSource(uloPersonalInfo,iibIncomeSource);
							}
						}						
					}else{
						logger.debug("iib verification resilt is null!!");
					}
				}
			}
		}else{
			logger.debug("iib personal is null!!");
		}
	}
	
	@Override
	public void mapApplicationGroup(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) {	
		uloApplicationGroup.setLastDecision(DecisionActionUtil.getAppGroupLastDecision(uloApplicationGroup));
		uloApplicationGroup.setLastDecisionDate(DecisionServiceUtil.getDate());
		uloApplicationGroup.setDecisionAction(DecisionActionUtil.getAppGroupLastDecision(uloApplicationGroup));
		String appType = ProcessUtil.getFinalApplicationType(iibApplicationGroup.getApplications());
		if (appType != null && !appType.isEmpty()) {
//			uloApplicationGroup.setApplicationType(appType);
			ProcessUtil.setApplyType(appType, uloApplicationGroup);
		}else{
			logger.debug("apply type is null!!");
		}		
		logger.debug("appType>>>"+appType);
	}
	
	@Override
	public void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication){
		uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
		uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
		logger.debug("iibApplication.getRecommendDecision()>>"+iibApplication.getRecommendDecision());
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
		//#CR-ADD
//		if(null!=iibApplication.getRecommendProjectCode() && !"".equals(iibApplication.getRecommendProjectCode())){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
//		}

		mapPersonalApplication(uloApplicationGroup, uloApplication, iibApplication);
		
		//KPL Additional
		//clear diffRequestResult && clear diffRequestFlag : case Re-process
		uloApplication.setDiffRequestFlag(null);
		uloApplication.setDiffRequestResult(null);
	}
	

	@Override
	public  void loanMapper(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication, decisionservice_iib.ApplicationDataM iibApplication) throws Exception{
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){

			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
				logger.debug("iibLoan.getLoanId()>>"+iibLoan.getLoanId());
				if(null==iibLoan.getLoanId()){
					continue;
				}
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null!=uloLoan){
					mapLoan(uloLoan, iibLoan);
					
					CardDataM uloCard = uloLoan.getCard();
					if(null!=uloCard){
						mapCard(uloLoan, iibLoan,iibApplication,IA_APPLICATION_TYPE);	
					}
								
					if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
						loanPricingsMapper(uloLoan, iibLoan);
					}

					if(null!=iibLoan.getLoanTiers() && iibLoan.getLoanTiers().size()>0){
						loanTiersMapper(uloLoan, iibLoan);
					}
					
					PaymentMethodDataM uloPaymentMenthod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
					if(null!=iibApplication.getBillingCycle() && null!=uloPaymentMenthod){
						mapPaymentMethod(uloApplicationGroup,uloApplication,uloPaymentMenthod,iibApplication);
					}
					
					//KPL Additional
					uloLoan.setCoreBankProduct(iibLoan.getCbProduct());
					uloLoan.setCoreBankSubProduct(iibLoan.getCbSubProduct());
					uloLoan.setCoreBankMarketCode(iibLoan.getCbMarketCode());
					
				}else{
					logger.debug("Can not find loan id is>>"+iibLoan.getLoanId());
				}
			}

		}
	}
	
	@Override
	public  void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup,VerificationResultDataM uloVerResult
			,decisionservice_iib.VerificationResultDataM iibVerResult) throws Exception{
		
			uloVerResult.setDocCompletedFlag(iibVerResult.getDocCompletedFlag());	
			if(null!=iibVerResult.getDocumentSuggestions() && iibVerResult.getDocumentSuggestions().size()>0){
				ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
				uloVerResult.setRequiredDocs(requireDocs);
				for(decisionservice_iib.DocumentSuggestionsDataM iibDocSuggestions : iibVerResult.getDocumentSuggestions()){
					mapDocumentSuggestions(uloVerResult, iibDocSuggestions);
				}	
			}
			
	}
	
	
}
