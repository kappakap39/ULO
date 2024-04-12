package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.SavingAccountsPersonalInfos;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AccountDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.CapportLoanDataM;

public class DV2DecisionServiceResponseMapper extends DecisionServiceResponseMapper{
	private static transient Logger logger = Logger.getLogger(DV2DecisionServiceResponseMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
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
	public void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(decisionservice_iib.ApplicationDataM iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);			
					if(null!=iibApplication.getVerificationResult()){
						verificationResultMapper(uloApplicationGroup,uloApplication, iibApplication.getVerificationResult());
					}					
				}else{
					logger.debug("iib application is null!!");
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
					logger.debug("Cant find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
					continue;
				}
				logger.debug("Cant find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
				mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);
					
				if(null!=iibPersonalInfo.getBolOrganizations()){
					for(decisionservice_iib.BolOrganization iibBolOrganization : iibPersonalInfo.getBolOrganizations()){						 
						mapBolOrganization(uloPersonalInfo,iibBolOrganization);
					}
				}
				if(null!=iibPersonalInfo.getApplicationScores()&& iibPersonalInfo.getApplicationScores().size()>0){
					for(decisionservice_iib.ApplicationScoreDataM iibAppScore : iibPersonalInfo.getApplicationScores()){
						mapApplicationScore(uloPersonalInfo,iibAppScore);
					}
				}
														
				if(null!=iibPersonalInfo.getCardlinkCustomers() &&  iibPersonalInfo.getCardlinkCustomers().size()>0){
					for(decisionservice_iib.CardlinkCustomer   iibCardLinkCust : iibPersonalInfo.getCardlinkCustomers()){	
						cardlinkCustomerMapper(uloPersonalInfo, iibCardLinkCust);
					}
				}
				
				if(null!=iibPersonalInfo.getCardlinkCards() &&  iibPersonalInfo.getCardlinkCards().size()>0){
					for(decisionservice_iib.CardlinkCard iibCardLinkCard : iibPersonalInfo.getCardlinkCards()){
						cardlinkCardMapper(uloPersonalInfo, iibCardLinkCard);
					}						
				}	
				
				if(null!=iibPersonalInfo.getNcbInfo()){
					ncbInfoMapper(uloPersonalInfo,  iibPersonalInfo);
				}
				if(null!=iibPersonalInfo.getVerificationResult()){
					verificationResultMapper(uloApplicationGroup,uloPersonalInfo, iibPersonalInfo.getVerificationResult());
				}
				
				decisionservice_iib.ApplicationDataM  iibApplication = ProcessUtil.filterIIBPersonalApplication(iibApplicationGroup.getApplications(), uloPersonalInfo.getPersonalId());
				if(null!=iibApplication){
					privilegeProjectCodeMapper(uloPersonalInfo, iibApplication);
				}
				
				mapKBankInfo(uloPersonalInfo, iibPersonalInfo);
				if(!ServiceUtil.empty(iibPersonalInfo.getBScores())){
					for(decisionservice_iib.BScore  iibBScore : iibPersonalInfo.getBScores()){	
						bScoreMapper(uloPersonalInfo, iibBScore);
					}
				}
				if(!ServiceUtil.empty(iibPersonalInfo.getSavingAccounts())){
					for(decisionservice_iib.AccountInfo iibSavingAccount : iibPersonalInfo.getSavingAccounts()){	
						savingAccountMapper(uloPersonalInfo, iibSavingAccount);
					}
				}
			}
		}else{
			logger.debug("iib personal is null!!");
		}
	}
	
	@Override
	public void loanTiersMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan)throws Exception{
		mapMainloanTiers(uloLoan, iibLoan);
	}
	
	@Override
	public void loanPricingsMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan )throws Exception{
		mapMainloanPricings(uloLoan, iibLoan);
	}
	
	@Override
	public void ncbInfoMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonal) throws Exception {
		mapMainNcbInfo(uloPersonalInfo, iibPersonal);
	}
	
	@Override
	public void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup,VerificationResultDataM uloVerResult,decisionservice_iib.VerificationResultDataM iibVerResult)throws Exception {
		logger.debug("mapping docuement complete flag");
		if(null!=iibVerResult.getDocCompletedFlag() && !"".equals(iibVerResult.getDocCompletedFlag())){
			uloVerResult.setDocCompletedFlag(iibVerResult.getDocCompletedFlag());	
		}
		PrivilegeProjectCodeDataM uloPrivilegeProjectCode =  uloVerResult.getPrivilegeProjectCode(0);
		if(null!=uloPrivilegeProjectCode && null!=iibVerResult.getPrivilegeProjectCode()){
			mapPrivilegeProjectCode(uloPrivilegeProjectCode, iibVerResult.getPrivilegeProjectCode());
		}
		if(null!=iibVerResult.getDocumentSuggestions() && iibVerResult.getDocumentSuggestions().size()>0){
			ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
			uloVerResult.setRequiredDocs(requireDocs);
			for(decisionservice_iib.DocumentSuggestionsDataM iibDocSuggestions : iibVerResult.getDocumentSuggestions()){
				mapDocumentSuggestions(uloVerResult, iibDocSuggestions);
			}	
		}
	}
	
	@Override
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
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
							CapportLoanDataM iibCapport = iibLoan.getCapport();
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
							
							// DF_FLP02552_2530 : Add Max Debt Burden Credit Limit
							uloLoan.setMaxDebtBurdenCreditLimit( iibLoan.getMaxDebtBurdenCreditLimit() );
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
	public void mapLoan(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan){
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
			CapportLoanDataM iibCapport = iibLoan.getCapport();
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
			
			List<decisionservice_iib.LoanTierDataM> iibLoanTier = iibLoan.getLoanTiers();
			if(iibLoanTier != null)
			{
				for(decisionservice_iib.LoanTierDataM iibLT : iibLoanTier)
				{uloLoan.setInterestRate(iibLT.getIntRateAmount());}
			}
			
			// DF_FLP02552_2530 : Add Max Debt Burden Credit Limit
			uloLoan.setMaxDebtBurdenCreditLimit( iibLoan.getMaxDebtBurdenCreditLimit() );
		}
	}
	
	public void mapLoanCalcDiff(LoanDataM uloLoan ,decisionservice_iib.LoanDataM iibLoan)
	{
		uloLoan.setLoanId(iibLoan.getLoanId());
		uloLoan.setMaxCreditLimit(iibLoan.getMaxCreditLimit());
		uloLoan.setMaxCreditLimitBot(iibLoan.getMaxCreditLimitBot());
		
		//Defect#3263 Bypass case reject MinCreditLimit = NULL
		if(iibLoan.getMinCreditLimit() != null)
		{uloLoan.setMinCreditLimit(iibLoan.getMinCreditLimit());}
		
		uloLoan.setCurrentCreditLimit(iibLoan.getCurrentCreditLimit());
		uloLoan.setRateType(iibLoan.getRateType());
		uloLoan.setSignSpread(iibLoan.getSignSpread());
		uloLoan.setFinalCreditLimit(iibLoan.getNormalCreditLimit());
		
		uloLoan.setInstallmentAmt(iibLoan.getInstallmentAmount());
		uloLoan.setInterestRate(iibLoan.getInterestRate());
		
		if(iibLoan.getRecommendLoanAmt() != null)
		{uloLoan.setLoanAmt(iibLoan.getRecommendLoanAmt());}
		if(iibLoan.getRecommendTerm() != null)
		{uloLoan.setTerm(iibLoan.getRecommendTerm());}
		
		List<decisionservice_iib.LoanTierDataM> iibLoanTier = iibLoan.getLoanTiers();
		for(decisionservice_iib.LoanTierDataM iibLT : iibLoanTier)
		{uloLoan.setInterestRate(iibLT.getIntRateAmount());}
	}
	
	//Defect#3549
	@Override
	public  void verificationResultMapper(ApplicationGroupDataM uloApplicationGroup,Object object,decisionservice_iib.VerificationResultDataM iibVerificationResult) throws Exception{
		 if(object instanceof PersonalInfoDataM){
			 logger.debug("##mapper ulo verification####");
			 PersonalInfoDataM uloPersonalInfo =(PersonalInfoDataM)object;
			 VerificationResultDataM uloVerificationResult = uloPersonalInfo.getVerificationResult();
			 if(null==uloVerificationResult){
				 uloVerificationResult = new VerificationResultDataM();
				 uloPersonalInfo.setVerificationResult(uloVerificationResult);
				 logger.debug("verification is null will create new !!");
			 }
			 mapPersonalVerificationResult(uloApplicationGroup,uloVerificationResult, iibVerificationResult);
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
	@Override
	public void savingAccountMapper(PersonalInfoDataM uloPersonalInfo, decisionservice_iib.AccountInfo iibSavingAccount) throws Exception {
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
}
