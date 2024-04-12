package com.eaf.service.common.iib.eapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.ApplicationScores;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.BScores;
import com.ava.flp.eapp.iib.model.BolOrganizations;
import com.ava.flp.eapp.iib.model.Capport;
import com.ava.flp.eapp.iib.model.CardLinkCards;
import com.ava.flp.eapp.iib.model.CardLinkCustomers;
import com.ava.flp.eapp.iib.model.CurrentAccounts;
import com.ava.flp.eapp.iib.model.DocumentSuggestions;
import com.ava.flp.eapp.iib.model.LoanTiers;
import com.ava.flp.eapp.iib.model.Loans;
import com.ava.flp.eapp.iib.model.NcbInfo;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.SavingAccountsPersonalInfos;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AccountDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BScoreDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class EDV2DecisionServiceResponseMapper extends
		EDecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(EDV2DecisionServiceResponseMapper.class);
	
	@Override
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,Applications iibApplication){
		uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
		uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
		uloApplication.setIsVetoEligibleFlag(iibApplication.getIsVetoEligibleFlag());		
		uloApplication.setPolicyProgramId(iibApplication.getPolicyProgramId());
		uloApplication.setRecommendDecision(ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision()));
		uloApplication.setLowIncomeFlag(iibApplication.getLowIncomeFlag());
		logger.debug("iibApplication.getLowIncomeFlag()>>"+iibApplication.getLowIncomeFlag());
		//#CR-ADD
		if(iibApplication.getRecommendProjectCode() != null && !iibApplication.getRecommendProjectCode().isEmpty()){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
		}
		
		if(null!=iibApplication.getReferCriterias() && iibApplication.getReferCriterias().size()>0){
			uloApplication.setReferCriteria(ProcessUtil.mapRefercriterias(iibApplication.getReferCriterias()));
		}
		
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
		}else if (ProcessUtil.REC_APP_DECISION_PRE_APPROVE.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_PRE_APPROVE);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
			mapReasonPolicyRule(uloApplication,iibApplication);
		}
		if(null!=iibApplication.getReExecuteKeyProgramFlag() && !"".equals(iibApplication.getReExecuteKeyProgramFlag())){
			uloApplication.setReExecuteKeyProgramFlag(iibApplication.getReExecuteKeyProgramFlag());
		}		
	}
	
	@Override
	public void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup iibApplicationGroup)throws Exception {
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(PersonalInfos iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					logger.debug("Cant find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
					continue;
				}
				logger.debug("Cant find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
				mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);
					
				if(null!=iibPersonalInfo.getBolOrganizations()){
					for(BolOrganizations iibBolOrganization : iibPersonalInfo.getBolOrganizations()){						 
						mapBolOrganization(uloPersonalInfo,iibBolOrganization);
					}
				}
				if(null!=iibPersonalInfo.getApplicationScores()&& iibPersonalInfo.getApplicationScores().size()>0){
					for(ApplicationScores iibAppScore : iibPersonalInfo.getApplicationScores()){
						mapApplicationScore(uloPersonalInfo,iibAppScore);
					}
				}
														
				if(null!=iibPersonalInfo.getCardLinkCustomers() &&  iibPersonalInfo.getCardLinkCustomers().size()>0){
					for(CardLinkCustomers iibCardLinkCust : iibPersonalInfo.getCardLinkCustomers()){	
						cardlinkCustomerMapper(uloPersonalInfo, iibCardLinkCust);
					}
				}
				
				if(null!=iibPersonalInfo.getCardLinkCards() &&  iibPersonalInfo.getCardLinkCards().size()>0){
					for(CardLinkCards iibCardLinkCard : iibPersonalInfo.getCardLinkCards()){
						cardlinkCardMapper(uloPersonalInfo, iibCardLinkCard);
					}						
				}	
				
				if(null!=iibPersonalInfo.getNcbInfo()){
					ncbInfoMapper(uloPersonalInfo,  iibPersonalInfo);
				}
				if(null!=iibPersonalInfo.getVerificationResult()){
					verificationResultMapper(uloApplicationGroup,uloPersonalInfo, null, iibPersonalInfo.getVerificationResult());
				}
				
				mapKBankInfo(uloPersonalInfo, iibPersonalInfo);
				if(!ServiceUtil.empty(iibPersonalInfo.getBScores())){
					for(BScores iibBScore : iibPersonalInfo.getBScores()){	
						bScoreMapper(uloPersonalInfo, iibBScore);
					}
				}
				if(!ServiceUtil.empty(iibPersonalInfo.getSavingAccounts())){
					for(SavingAccountsPersonalInfos iibSavingAccount : iibPersonalInfo.getSavingAccounts()){	
						savingAccountMapper(uloPersonalInfo, iibSavingAccount);
					}
				}
			}
		}else{
			logger.debug("iib personal is null!!");
		}
	}
	
	@Override
	public void loanTiersMapper(LoanDataM uloLoan, Loans iibLoan)throws Exception{
		mapMainloanTiers(uloLoan, iibLoan);
	}
	
	@Override
	public void loanPricingsMapper(LoanDataM uloLoan, Loans iibLoan )throws Exception{
		mapMainloanPricings(uloLoan, iibLoan);
	}
	
	@Override
	public void ncbInfoMapper(PersonalInfoDataM uloPersonalInfo, PersonalInfos iibPersonal) throws Exception {
		mapMainNcbInfo(uloPersonalInfo, iibPersonal);
	}
	
	@Override
	public void savingAccountMapper(PersonalInfoDataM uloPersonalInfo, SavingAccountsPersonalInfos iibSavingAccount) throws Exception {
		if(iibSavingAccount !=null){
			logger.debug("iib personal savingAccountMapper!");
			AccountDataM uloAccount = new AccountDataM();
			String accountType = SystemConstant.getConstant("SAVING_ACCOUNT_TYPE");
			mapAccount(uloAccount, iibSavingAccount,accountType);
			List<AccountDataM> uloAccountList = uloPersonalInfo.getAccounts();
			if (uloAccountList == null) {
				uloAccountList = new ArrayList<AccountDataM>();
				uloPersonalInfo.setAccounts((ArrayList<AccountDataM>) uloAccountList);
			}
			uloAccountList.add(uloAccount);
		}
	}
	
	@Override
	public void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup,VerificationResultDataM uloVerResult, VerificationResultPersonalInfos iibVerResult)throws Exception {
		logger.debug("mapping docuement complete flag");
		if(null!=iibVerResult.getDocCompletedFlag() && !"".equals(iibVerResult.getDocCompletedFlag())){
			uloVerResult.setDocCompletedFlag(iibVerResult.getDocCompletedFlag());	
		}
//		PrivilegeProjectCodeDataM uloPrivilegeProjectCode =  uloVerResult.getPrivilegeProjectCode(0);
//		if(null!=uloPrivilegeProjectCode && null!=iibVerResult.getPrivilegeProjectCode()){
//			mapPrivilegeProjectCode(uloPrivilegeProjectCode, iibVerResult.getPrivilegeProjectCode());
//		}
		if(null!=iibVerResult.getDocumentSuggestions() && iibVerResult.getDocumentSuggestions().size()>0){
			ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
			uloVerResult.setRequiredDocs(requireDocs);
			for(DocumentSuggestions iibDocSuggestions : iibVerResult.getDocumentSuggestions()){
				mapDocumentSuggestions(uloVerResult, iibDocSuggestions);
			}	
		}
	}
	
	@Override
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup, ApplicationDataM uloApplication, Applications iibApplication)throws Exception {
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(Loans iibLoan : iibApplication.getLoans()){
				logger.debug("iibLoan.getLoanId()>>"+iibLoan.getLoanId());
				if(null==iibLoan.getLoanId()){
					continue;
				}
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null!=uloLoan){
					//KPL Additional Add handle for diffReq calculate button
					if(!Util.empty(uloApplication.getDiffRequestFlag()))
					{
						if(!Util.empty(uloApplication.getDiffRequestResult()))
						{
							if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
								loanPricingsMapper(uloLoan, iibLoan);
							}
							
							// Remove as of 29/10/2018 - Not replace Slot2 loanTier after Diff Request Big submit
							/*if(null!=iibLoan.getLoanTiers() && iibLoan.getLoanTiers().size()>0){
								loanTiersMapper(uloLoan, iibLoan);
							}*/
							
							PaymentMethodDataM uloPaymentMenthod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
							if(null!=iibApplication.getBillingCycle() && null!=uloPaymentMenthod){
								mapPaymentMethod(uloApplicationGroup,uloApplication,uloPaymentMenthod,iibApplication);
							}
							
							//Defect#2301
							uloLoan.setStampDuty(iibLoan.getStampDuty());
							Capport iibCapport = iibLoan.getCapport();
							if(iibCapport != null)
							{
								uloLoan.setApplicationCapPortName(iibCapport.getApplicationCapportId());
								uloLoan.setAmountCapPortName(iibCapport.getAmountCapportId());
							}
							
							//Defect#2662
							uloLoan.setBotFactor(iibLoan.getBotFactor());
							uloLoan.setBotCondition(iibLoan.getBotCondition());
							uloLoan.setProductFactor(iibLoan.getProductFactor());
							uloLoan.setDebtAmount(iibLoan.getDebtAmount());
							uloLoan.setDebtBurdenCreditLimit(iibLoan.getDebtBurdenCreditLimit());
							uloLoan.setDebtRecommend(iibLoan.getDebtRecommend());
							uloLoan.setDebtBurden(iibLoan.getDebtBurden());
						}
						
						mapLoanCalcDiff(uloLoan, iibLoan);
					}
					else
					{
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
				}else{
					logger.debug("Can not find loan id is>>"+iibLoan.getLoanId());
				}
			}

		}
	}
	
	@Override
	public void mapLoan(LoanDataM uloLoan, Loans iibLoan){
		if(null!=iibLoan){
			super.mapLoan(uloLoan,iibLoan);
			uloLoan.setBotFactor(iibLoan.getBotFactor());
			uloLoan.setBotCondition(iibLoan.getBotCondition());
			uloLoan.setProductFactor(iibLoan.getProductFactor());
			uloLoan.setDebtAmount(iibLoan.getDebtAmount());
			uloLoan.setDebtBurdenCreditLimit(iibLoan.getDebtBurdenCreditLimit());
			uloLoan.setDebtRecommend(iibLoan.getDebtRecommend());
			uloLoan.setDebtBurden(iibLoan.getDebtBurden());
			
			//KPL Additional
			uloLoan.setStampDuty(iibLoan.getStampDuty());
			Capport iibCapport = iibLoan.getCapport();
			if(iibCapport != null)
			{
				uloLoan.setApplicationCapPortName(iibCapport.getApplicationCapportId());
				uloLoan.setAmountCapPortName(iibCapport.getAmountCapportId());
			}
			
			if(iibLoan.getRecommendTerm() != null)
			{uloLoan.setTerm(iibLoan.getRecommendTerm());}
			
			uloLoan.setCoreBankProduct(iibLoan.getCbProduct());
			uloLoan.setCoreBankSubProduct(iibLoan.getCbSubProduct());
			uloLoan.setCoreBankMarketCode(iibLoan.getCbMarketCode());
			uloLoan.setInstallmentAmt(iibLoan.getInstallmentAmount());
			
			List<LoanTiers> iibLoanTier = iibLoan.getLoanTiers();
			if(iibLoanTier != null)
			{
				for(LoanTiers iibLT : iibLoanTier)
				{uloLoan.setInterestRate(iibLT.getInterestRate());}
			}
		}
	}
	
	public void mapLoanCalcDiff(LoanDataM uloLoan, Loans iibLoan)
	{
		uloLoan.setLoanId(iibLoan.getLoanId());
		uloLoan.setMaxCreditLimit(iibLoan.getMaxCreditLimit());
		uloLoan.setMaxCreditLimitBot(iibLoan.getMaxCreditLimitBot());
		
		//Defect#3263 Bypass case reject MinCreditLimit = NULL
		if(iibLoan.getMinCreditLimit() != null)
		{uloLoan.setMinCreditLimit(iibLoan.getMinCreditLimit());}
		
//		uloLoan.setCurrentCreditLimit(iibLoan.getCurrentCreditLimit());
//		uloLoan.setRateType(iibLoan.getRateType());
//		uloLoan.setSignSpread(iibLoan.getSignSpread());
		uloLoan.setFinalCreditLimit(iibLoan.getNormalCreditLimit());
		
		uloLoan.setInstallmentAmt(iibLoan.getInstallmentAmount());
//		uloLoan.setInterestRate(iibLoan.getInterestRate());
		
		if(iibLoan.getRecommendLoanAmt() != null)
		{uloLoan.setLoanAmt(iibLoan.getRecommendLoanAmt());}
		if(iibLoan.getRecommendTerm() != null)
		{uloLoan.setTerm(iibLoan.getRecommendTerm());}
		
		List<LoanTiers> iibLoanTier = iibLoan.getLoanTiers();
		for(LoanTiers iibLT : iibLoanTier)
		{uloLoan.setInterestRate(iibLT.getInterestRate());}
	}
	
	//Defect#3549
	@Override
	public  void verificationResultMapper(ApplicationGroupDataM uloApplicationGroup,Object object,
			VerificationResultApplications iibVerificationResult,VerificationResultPersonalInfos iibVerificationResultPerson) throws Exception{
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
			 
			 //Defect#3549 Make calculate diffRequest ignore Policy rules mapping
			 if(Util.empty(uloApplication.getDiffRequestFlag()) || !Util.empty(uloApplication.getDiffRequestResult()))
			 {
				 mapApplicationVerificationResult(uloVerResult, iibVerificationResult);
			 }
		 }
	}
}
