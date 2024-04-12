package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.CurrentAccounts;
import com.eaf.core.ulo.common.util.DecisionActionUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AccountDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.CapportLoanDataM;

public class DE1DecisionServiceResponseMapper extends DecisionServiceResponseMapper{
	private static transient Logger logger = Logger.getLogger(DE1DecisionServiceResponseMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String EXECUTE_KEY_PROGRAM_NEW_INCOME = ServiceCache.getConstant("EXECUTE_KEY_PROGRAM_NEW_INCOME");
    String[] EXCEPTION_PROJECT_CODE = ServiceCache.getArrayConstant("EXCEPTION_PROJECT_CODE");
	
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
		mapApplicationGroup(uloApplicationGroup, iibApplicationGroup);
		mapThaiBevPartner(uloApplicationGroup, iibApplicationGroup);
		priorityPassMapper(uloApplicationGroup, iibApplicationGroup);
		
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
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {	
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null==uloLoan){
					logger.debug("cant find ulo loan id >>"+iibLoan.getLoanId());
					continue;
				}
				mapLoan(uloLoan, iibLoan);
							
				CardDataM uloCard = uloLoan.getCard();
				if(null!=uloCard){
					mapCard(uloLoan, iibLoan,iibApplication,uloApplicationGroup.getApplicationType());							
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
			}
		}
	}
	
	@Override
	public void loanTiersMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan)throws Exception{
			 super.mapMainloanTiers(uloLoan, iibLoan);
	}
	
	@Override
	public void loanPricingsMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan )throws Exception{
		super.mapMainloanPricings(uloLoan, iibLoan);
	}
	
	@Override
	public void mapApplicationVerificationResult(VerificationResultDataM uloVerResult,decisionservice_iib.VerificationResultDataM iibVerResult)throws Exception {
		super.mapApplicationVerificationResult(uloVerResult, iibVerResult);
	}
	
	@Override
	public void mapPersonalInfo(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo) {
		super.mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);
		super.mapKBankInfo(uloPersonalInfo, iibPersonalInfo);
	}
	
	@Override
	public void mapLoan(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan) {
		super.mapLoan(uloLoan, iibLoan);
		uloLoan.setLoanAmt(iibLoan.getRecommendLoanAmt());
		uloLoan.setBotFactor(iibLoan.getBotFactor());
		uloLoan.setBotCondition(iibLoan.getBotCondition());
		uloLoan.setProductFactor(iibLoan.getProductFactor());
		uloLoan.setDebtAmount(iibLoan.getDebtAmount());
		uloLoan.setDebtBurdenCreditLimit(iibLoan.getDebtBurdenCreditLimit());
		uloLoan.setDebtRecommend(iibLoan.getDebtRecommend());
		uloLoan.setDebtBurden(iibLoan.getDebtBurden());
		
		//KPL Additional
		uloLoan.setStampDuty(iibLoan.getStampDuty());
		CapportLoanDataM iibCapport = iibLoan.getCapport();
		if(iibCapport != null)
		{
			uloLoan.setApplicationCapPortName(iibCapport.getApplicationCapportId());
			uloLoan.setAmountCapPortName(iibCapport.getAmountCapportId());
		}
		
		uloLoan.setTerm(iibLoan.getRecommendTerm());
		List<decisionservice_iib.LoanTierDataM> iibLoanTier = iibLoan.getLoanTiers();
		if(iibLoanTier != null)
		{
			for(decisionservice_iib.LoanTierDataM iibLT : iibLoanTier)
			{uloLoan.setInterestRate(iibLT.getIntRateAmount());}
		}
		
		uloLoan.setCoreBankProduct(iibLoan.getCbProduct());
		uloLoan.setCoreBankSubProduct(iibLoan.getCbSubProduct());
		uloLoan.setCoreBankMarketCode(iibLoan.getCbMarketCode());
		uloLoan.setInstallmentAmt(iibLoan.getInstallmentAmount());
		
		// DF_FLP02552_2530 : Add Max Debt Burden Credit Limit
		uloLoan.setMaxDebtBurdenCreditLimit( iibLoan.getMaxDebtBurdenCreditLimit() );
	}
	
	@Override
	public void incomeInfoMapper(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo, decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		try {
			mapperIncomeInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo, iibApplicationGroup);
		} catch (Exception e) {
			throw new Exception(e);
		}		
	}
	
	@Override
	public  void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(decisionservice_iib.ApplicationDataM iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					mapApplication(uloApplicationGroup, uloApplication, iibApplication,iibApplicationGroup.getExecuteAction());
					if(null!=iibApplication.getVerificationResult()){
						verificationResultMapper(uloApplicationGroup,uloApplication, iibApplication.getVerificationResult());
					}
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);	
				}else{
					logger.debug("iib application is null!!");
				}
				
			}
		} 
	}
	
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication,String requestDecision){
		uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
		uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
		uloApplication.setIsVetoEligibleFlag(iibApplication.getIsVetoEligibleFlag());		
		uloApplication.setPolicyProgramId(iibApplication.getPolicyProgramId());
		uloApplication.setRecommendDecision(ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision()));
		
		if(ServiceCache.getConstant("DECISION_SERVICE_POINT_DV1").equals(requestDecision)){
			if (EXECUTE_KEY_PROGRAM_NEW_INCOME.equals(iibApplication.getReExecuteKeyProgramFlag()) && ProcessUtil.REC_APP_DECISION_APPROVE.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
				uloApplication.setRecommendDecision(ServiceCache.getConstant("DECISION_SERVICE_PROCEED"));
			}
		}
		
		//#CR-ADD
		if(iibApplication.getRecommendProjectCode() != null && !iibApplication.getRecommendProjectCode().isEmpty()){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
		}
        //for CR310 if project code in EXCEPTION_PROJECT_CODE and null from odm
        else if(!Util.empty(uloApplication.getProjectCode()) && !Util.empty(EXCEPTION_PROJECT_CODE)){
            for(String projectCode : EXCEPTION_PROJECT_CODE){
                if(projectCode.equals(uloApplication.getProjectCode())){
                    uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
                    break;
                }
            }
        }
		
		if(null!=iibApplication.getReferCriteria() && iibApplication.getReferCriteria().size()>0){
			uloApplication.setReferCriteria(ProcessUtil.mapReferCriterias(iibApplication.getReferCriteria()));
		}
		
		logger.debug("uloApplication.getRecommendDecision()>>"+uloApplication.getRecommendDecision());
		if (ProcessUtil.REC_APP_DECISION_APPROVE.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_APPROVE);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
			//set lowincome flag in case approve only
			uloApplication.setLowIncomeFlag(iibApplication.getLowIncomeFlag());
		} else if (ProcessUtil.REC_APP_DECISION_REJECT.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_REJECT);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
			mapReasonPolicyRule(uloApplication,iibApplication);
		}
		if(null!=iibApplication.getReExecuteKeyProgramFlag() && !"".equals(iibApplication.getReExecuteKeyProgramFlag())){
			uloApplication.setReExecuteKeyProgramFlag(iibApplication.getReExecuteKeyProgramFlag());
		}		
	}
	@Override
	public void CurrentAccountMapper(PersonalInfoDataM uloPersonalInfo, decisionservice_iib.AccountInfo iibCurrentAccount) throws Exception {
		if(iibCurrentAccount !=null){
			AccountDataM uloAccount = new AccountDataM();
			mapAccount(uloAccount, iibCurrentAccount);
			List<AccountDataM> uloAccountList = uloPersonalInfo.getAccounts();
			if (uloAccountList == null) {
				uloAccountList = new ArrayList<AccountDataM>();
				uloPersonalInfo.setAccounts((ArrayList<AccountDataM>) uloAccountList);
			}
			uloAccountList.add(uloAccount);
		}
	}
}
