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
import com.ava.flp.eapp.iib.model.IncomeCalculations;
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
import com.ava.flp.eapp.iib.model.NcbRuleResults;
import com.ava.flp.eapp.iib.model.OrPolicyRules;
import com.ava.flp.eapp.iib.model.OrPolicyRulesDetails;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.PolicyRuleConditions;
import com.ava.flp.eapp.iib.model.PolicyRuleReasons;
import com.ava.flp.eapp.iib.model.PolicyRules;
import com.ava.flp.eapp.iib.model.SavingAccountsPersonalInfos;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.ava.flp.eapp.iib.model.WebVerifications;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.DecisionActionUtil;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AccountDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BScoreDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM.VerifyId;
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IncomeCalculationDataM;
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
import com.eaf.orig.ulo.model.ncb.NcbAccountReportDataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportRuleDataM;
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

public class EDecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(EDecisionServiceResponseMapper.class);
	private KBankHeader  kbankHeader;	
	private static String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		logger.debug("start applicationGroupMapper!!");
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
		
		uloApplicationGroup.setRequireVerify(MConstant.FLAG_N);
		if(processRequireVerify(uloApplicationGroup.getPersonalInfos())){
			uloApplicationGroup.setRequireVerify(MConstant.FLAG_Y);
		}
	}
	String BORROWER = ServiceCache.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = ServiceCache.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
//	public void priorityPassMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
//		logger.debug("start priorityPassMapper!!");
//		if(null!=uloApplicationGroup){
//			ArrayList<ApplicationDataM> applications = uloApplicationGroup.filterApplicationLifeCycle();
//			if(null!=applications){
//				for(ApplicationDataM application :applications) {
//					CardDataM card = application.getCard();
//					if(null!=card&&null!=iibApplicationGroup.getPrioritypass()){
//						PriorityPass priorityPass = iibApplicationGroup.getPriorityPass();
//						WisdomDataM wisdom = card.getWisdom();
//						if(null==wisdom){
//							wisdom = new WisdomDataM();
//							card.setWisdom(wisdom);
//						}
//						if(BORROWER.equals(card.getApplicationType())){
//							if(null!=priorityPass.getPriorityPassMain())
//							wisdom.setNoPriorityPass(new BigDecimal(priorityPass.getPriorityPassMain()));
//						}else if (SUPPLEMENTARY.equals(card.getApplicationType())){
//							if(null!=priorityPass.getPriorityPassSup())
//							wisdom.setNoPriorityPass(new BigDecimal(priorityPass.getPriorityPassSup()));
//						}
//					}
//				}
//			}
//		}
//	}
	public  void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(Applications iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					if(null!=iibApplication.getVerificationResult()){
						verificationResultMapper(uloApplicationGroup,uloApplication, iibApplication.getVerificationResult(),null);
					}
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);	
				}else{
					logger.debug("iib application is null!!");
				}
				
			}
		} 
	}
	
	public  void loanMapper(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication, Applications iibApplication) throws Exception{
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(Loans iibLoan : iibApplication.getLoans()){
				logger.debug("iibLoan.getLoanId()>>"+iibLoan.getLoanId());
				if(null==iibLoan.getLoanId()){
					continue;
				}
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null!=uloLoan){
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
				}else{
					logger.debug("Can not find loan id is>>"+iibLoan.getLoanId());
				}
			}

		}
	}
	
	public void loanTiersMapper(LoanDataM uloLoan, Loans iibLoan)throws Exception{
//		try {
//			//set when decision is DE1,DV2,FI
		mapMainloanTiers(uloLoan, iibLoan);
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
	}
	
	public void loanPricingsMapper(LoanDataM uloLoan, Loans iibLoan )throws Exception{
//		try {
//			//set when decision is DE1,DV2
			mapMainloanPricings(uloLoan, iibLoan);
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
	}
	
	public  void mapMainloanPricings(LoanDataM uloLoan, Loans iibLoan )throws  Exception{
		//Prepare data
		List<LoanPricings> iibLoanPricngs = iibLoan.getLoanPricings();
		List<LoanPricingDataM> loanPricings = new ArrayList<LoanPricingDataM>();
		uloLoan.setLoanPricings(null);//Clear to always add new since there's no update criteria
			
		if(iibLoanPricngs == null || iibLoanPricngs.size() < 1){				
			return;
		}else{
			for(LoanPricings iibPricing : iibLoanPricngs){
				LoanPricingDataM uloPricing = new LoanPricingDataM();
				mapLoanPricing(uloPricing, iibPricing);
				loanPricings.add(uloPricing);
			}			
			//Start mapping
			uloLoan.setLoanPricings((ArrayList<LoanPricingDataM>) loanPricings);
		}	
	}
	
	public  void mapMainloanTiers(LoanDataM uloLoan, Loans iibLoan)throws  Exception{
		if(null!=iibLoan.getLoanTiers() && iibLoan.getLoanTiers().size()>0){
			int seq=0;
			for(LoanTiers iibLoanTier : iibLoan.getLoanTiers()){
				LoanTierDataM uloLoanTier =DecisionServiceUtil.getLoanTierDataSeq(uloLoan.getLoanTiers(), seq);
				if(null==uloLoanTier){
					uloLoanTier = new LoanTierDataM();
					uloLoan.addLoanTiers(uloLoanTier);	
				}					
				mapLoanTier(uloLoanTier, iibLoanTier,seq);
				seq++;
			}
		}
	}
	
	public  void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(PersonalInfos iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					 logger.debug("Cant find personal id >>"+iibPersonalInfo.getPersonalId());
					 continue;
				}
				logger.debug("find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
				mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);			
				incomeInfoMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
							
				if(!ServiceUtil.empty(iibPersonalInfo.getBolOrganizations())){
					for(BolOrganizations iibBolOrganization : iibPersonalInfo.getBolOrganizations()){						 
						mapBolOrganization(uloPersonalInfo,iibBolOrganization);
					}
				}
				
				if(!ServiceUtil.empty(iibPersonalInfo.getApplicationScores())){
					mapApplicationScore(uloPersonalInfo,iibPersonalInfo.getApplicationScores().get(0));
				}else{
					logger.debug("ApplicationScore is null");
				}

				if(!ServiceUtil.empty(iibPersonalInfo.getCardLinkCustomers())){
					for(CardLinkCustomers   iibCardLinkCust : iibPersonalInfo.getCardLinkCustomers()){	
						cardlinkCustomerMapper(uloPersonalInfo, iibCardLinkCust);
					}
				}
				
				if(!ServiceUtil.empty(iibPersonalInfo.getCardLinkCards())){
					for(CardLinkCards iibCardLinkCard : iibPersonalInfo.getCardLinkCards()){
						cardlinkCardMapper(uloPersonalInfo, iibCardLinkCard);
					}				
				}
				
				if(!ServiceUtil.empty(iibPersonalInfo.getNcbInfo())){
					ncbInfoMapper(uloPersonalInfo, iibPersonalInfo);
				}
				
				if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult())){
					verificationResultMapper(uloApplicationGroup,uloPersonalInfo, null,iibPersonalInfo.getVerificationResult());
					if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult().getIncomeSources())){
						uloPersonalInfo.setIncomeSources(new ArrayList<IncomeSourceDataM>());
						for(IncomeSources iibIncomeSource : iibPersonalInfo.getVerificationResult().getIncomeSources()){
							mapIncomeSource(uloPersonalInfo,iibIncomeSource);
						}
					}						
				}else{
					logger.debug("iibPersonalInfo.getVerificationResult()  is null");
				}	
				
				Applications  iibApplication = ProcessUtil.filterIIBPersonalApplications(iibApplicationGroup.getApplications(), uloPersonalInfo.getPersonalId());
				if(!ServiceUtil.empty(iibApplication)){
					privilegeProjectCodeMapper(uloPersonalInfo, iibApplication);
				}
				if(!ServiceUtil.empty(iibPersonalInfo.getSavingAccounts())){
					for(SavingAccountsPersonalInfos iibSavingAccount : iibPersonalInfo.getSavingAccounts()){	
						savingAccountMapper(uloPersonalInfo, iibSavingAccount);
					}
				}
			}
		}
	}	
	
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

	public  void mapAccount(AccountDataM uloAccount,SavingAccountsPersonalInfos iibSavingAccount,String accountType) throws Exception{
		uloAccount.setAccountNo(iibSavingAccount.getAccountNo());
		uloAccount.setAccountName(iibSavingAccount.getAccountName());
		uloAccount.setAccountType(accountType);
		uloAccount.setOpenDate(DecisionServiceUtil.toSqlDate(iibSavingAccount.getOpenDate()));
	}
	
	public void  incomeInfoMapper(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo, PersonalInfos iibPersonalInfo)  throws Exception{
		//map income at  decision point  in DE1 and Income service  : new CR  in file  Change FollowUp_20170327	
		//mapperIncomeInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
	}
	
	public void mapperIncomeInfo (ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo, PersonalInfos iibPersonalInfo)  throws Exception{
		if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult()) && !ServiceUtil.empty(iibPersonalInfo.getVerificationResult().getIncomeScreens())){
			List<IncomeInfoDataM> uloIncomeList = ProcessUtil.mergeIncomeScreenTypes(uloPersonalInfo.getIncomeInfos(), iibPersonalInfo.getVerificationResult().getIncomeScreens(), uloApplicationGroup.getApplications(),uloApplicationGroup.getReprocessFlag());
			if(null==uloIncomeList){
				uloIncomeList = new ArrayList<IncomeInfoDataM>();
			}
//			logger.debug("uloIncomeList>>"+new Gson().toJson(uloIncomeList));
			uloPersonalInfo.setIncomeInfos((ArrayList<IncomeInfoDataM>) uloIncomeList);
		}else{
			logger.debug("income screens is null");
//			#rawi clear income info for case odm not suggest income screen.
			uloPersonalInfo.setIncomeInfos(new ArrayList<IncomeInfoDataM>());
		}
	}
	
	public void cardlinkCustomerMapper(PersonalInfoDataM  uloPersonalInfo, CardLinkCustomers iibCardLinkCustomer) throws Exception{
		CardlinkCustomerDataM uloCardLinkCust = null;//uloPersonalInfo.getCardLinkCustomer(iibCardLinkCustomer.getOrgNo(), iibCardLinkCustomer.getCardlinkCustNo());
		if(null==uloCardLinkCust){
			uloCardLinkCust = new CardlinkCustomerDataM();
			uloPersonalInfo.addCardLinkCustomer(uloCardLinkCust);
		}
		mapCardlinkCustomer(uloCardLinkCust, iibCardLinkCustomer);
	}
	
	public void cardlinkCardMapper(PersonalInfoDataM  uloPersonalInfo, CardLinkCards iibCardLink){
		CardlinkCardDataM uloCardLink= null;//uloPersonalInfo.getCardLinkCardById(iibCardLink.getCardlinkCardId());
		if(null==uloCardLink){
			uloCardLink = new CardlinkCardDataM();
			uloPersonalInfo.addCardLinkCards(uloCardLink);
		}
		mapCardlinkCard(uloCardLink, iibCardLink);
	}
	
	public void ncbInfoMapper(PersonalInfoDataM  uloPersonalInfo,PersonalInfos iibPersonalInfo) throws Exception{
//		try {
//			//set only PB1 and DV2
//			//mapMainNcbInfo(uloPersonalInfo, iibNcbInfo);
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
 	}
	
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
	
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,Applications iibApplication){
		uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
		uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
		uloApplication.setIsVetoEligibleFlag(iibApplication.getIsVetoEligibleFlag());		
		uloApplication.setPolicyProgramId(iibApplication.getPolicyProgramId());
		uloApplication.setRecommendDecision(ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision()));
		uloApplication.setLowIncomeFlag(iibApplication.getLowIncomeFlag());
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
		}
		if(null!=iibApplication.getReExecuteKeyProgramFlag() && !"".equals(iibApplication.getReExecuteKeyProgramFlag())){
			uloApplication.setReExecuteKeyProgramFlag(iibApplication.getReExecuteKeyProgramFlag());
		}
	}
	
	
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
	
	
	public  void mapPaymentMethod(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,PaymentMethodDataM uloPaymentMethod,Applications iibApplication) throws Exception{
		//#check null from caller method
//		if(null==uloPaymentMethod){
//			PersonalInfoDataM uloPersonalInfo =uloApplicationGroup.getPersonalInfoApplication(uloApplication.getApplicationRecordId(), DecisionServiceUtil.PERSONAL_APPLICATION_LEVEL);
//			if(null==uloPersonalInfo){
//				uloPersonalInfo = new PersonalInfoDataM();
//			}	
//			uloPaymentMethod = new PaymentMethodDataM();
//			uloPaymentMethod.setPersonalId(uloPersonalInfo.getPersonalId());
//			uloApplicationGroup.addPaymentMethod(uloPaymentMethod);
//		}
		if(!ServiceUtil.empty(uloPaymentMethod)){
			uloPaymentMethod.setDueCycle(iibApplication.getBillingCycle());
		}
	}
	
	public  void mapPersonalInfo(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo){
		logger.debug("-----map personal info------");
		uloPersonalInfo.setTotVerifiedIncome(iibPersonalInfo.getTotVerifiedIncome());
		uloPersonalInfo.setTypeOfFin(iibPersonalInfo.getTypeofFin());
		uloPersonalInfo.setBureauRequiredFlag(iibPersonalInfo.getRequireBureau());
		if(null!=iibPersonalInfo.getVerificationResult() && null!=iibPersonalInfo.getVerificationResult().getVerifiedIncomeSource() && !"".equals(iibPersonalInfo.getVerificationResult().getVerifiedIncomeSource())){
			uloPersonalInfo.setSorceOfIncome(iibPersonalInfo.getVerificationResult().getVerifiedIncomeSource());
		}	
//		
//		if(null!=iibPersonalInfo.getTotalCCMainCard()&& !"".equals(iibPersonalInfo.getTotalCCMainCard())){
//			uloPersonalInfo.setNoMainCard(DecisionServiceUtil.toString(iibPersonalInfo.getTotalCCMainCard()));
//		}
//		
//		if(null!=iibPersonalInfo.getTotalCCSupCard() && !"".equals(iibPersonalInfo.getTotalCCSupCard())){
//			uloPersonalInfo.setNoSupCard(DecisionServiceUtil.toString(iibPersonalInfo.getTotalCCSupCard()));
//		}
		logger.debug("iibPersonalInfo.getTotVerifiedIncome()>>"+iibPersonalInfo.getTotVerifiedIncome());
		logger.debug("iibPersonalInfo.getTypeofFin()>>"+iibPersonalInfo.getTypeofFin());
	
		//Map KbankPayroll
		if(PERSONAL_RELATION_APPLICATION_LEVEL.equals(iibPersonalInfo.getPersonalType()))
		{
			logger.debug("Mapping KbankPayrollDataM paymentAccountNo to uloPersonalInfo payrollAccountNo ");
			KBankPayroll iibKPayroll = iibPersonalInfo.getKBankPayroll();
			if(iibKPayroll != null)
			{
				uloPersonalInfo.setPayrollAccountNo(iibKPayroll.getPaymentAccountNo());
			}
		}
	}
	
	public  void mapApplicationScore(PersonalInfoDataM uloPersonalInfo,ApplicationScores iibAppScore) throws Exception{
		if(null!=iibAppScore){
			VerificationResultDataM uloVerificationResult = uloPersonalInfo.getVerificationResult();
			if(uloVerificationResult == null){
				uloVerificationResult = new VerificationResultDataM();
				uloPersonalInfo.setVerificationResult(uloVerificationResult);
				logger.debug("verificationResultDataM is null, created new one.");
			}
			uloPersonalInfo.getVerificationResult().setRiskGradeCoarseCode(iibAppScore.getCoarseRiskGrade());
			uloPersonalInfo.getVerificationResult().setRiskGradeFineCode(iibAppScore.getFineRiskGrade());
			
		}
		
	}
	
	public  void mapBolOrganization(PersonalInfoDataM uloPersonalInfo,BolOrganizations iibBolOrganization) throws Exception{
		uloPersonalInfo.setCompanyOrgIdBol(iibBolOrganization.getOrgId());
		uloPersonalInfo.setPhoneNoBol(iibBolOrganization.getTelNo());
	}
	
	public  void mapMainNcbInfo(PersonalInfoDataM uloPerson,PersonalInfos iibPersonalInfo)throws Exception {
		if (uloPerson == null)
			return;
		NcbInfo iibNcbInfo = iibPersonalInfo.getNcbInfo();
		if(null==iibNcbInfo){
			return;
		}
		// Prepare data
		List<NCBDocumentHistoryDataM> ncbHisList = uloPerson.getNcbDocumentHistorys();
		boolean hasRefNoInULO = false;
		final String iibConsentRefNo = iibNcbInfo.getConsentRefNo();
		final String requireBureauFlag = iibPersonalInfo.getRequireBureau();
		if (ncbHisList != null && !ncbHisList.isEmpty() && null!=iibConsentRefNo && !"".equals(iibConsentRefNo)) {
			for (NCBDocumentHistoryDataM his : ncbHisList) {
				if (his == null)
					continue;
				if (iibConsentRefNo.equalsIgnoreCase(his.getConsentRefNo())) {
					hasRefNoInULO = true;
					break;
				}
			}
		}
		// Start Mapping logic
		if (!hasRefNoInULO || iibConsentRefNo == null || iibConsentRefNo.isEmpty()) {//SIT846
			// Replace NCB Info
			NcbInfoDataM uloNCBInfo = new NcbInfoDataM();
			mapNCBInfo(uloNCBInfo, iibNcbInfo);

			List<NcbInfoDataM> uloNCBList = uloPerson.getNcbInfos();
			if (uloNCBList == null) {
				uloNCBList = new ArrayList<NcbInfoDataM>();
				uloPerson.setNcbInfos((ArrayList<NcbInfoDataM>) uloNCBList);
			}
			uloNCBList.clear();
			uloNCBList.add(uloNCBInfo);

			// Add history
			if(iibConsentRefNo != null && !iibConsentRefNo.isEmpty()){
				if (ncbHisList == null) {
					ncbHisList = new ArrayList<NCBDocumentHistoryDataM>();
					uloPerson.setNcbDocumentHistorys((ArrayList<NCBDocumentHistoryDataM>) ncbHisList);
				}
				
				NCBDocumentHistoryDataM his = new NCBDocumentHistoryDataM();
				his.setConsentRefNo(iibConsentRefNo);
				his.setBureauRequiredFlag(requireBureauFlag);
				ncbHisList.add(his);
			}
		}
	}
	public void mapNumTimesOfEnquiry(NcbInfoDataM uloNCB, NcbInfo iibNcbInfo)throws Exception{
		if (iibNcbInfo == null) {
			return;
		}
		if (uloNCB == null) {
			return;
		}
		String NCB_DAY_OF_TIME_6M = DecisionServiceCacheControl.getConstant("NCB_DAY_OF_TIME_6M");
		Date dateOfSearch = DecisionServiceUtil.toSqlDate(iibNcbInfo.getDateOfSearch());
		if(!ServiceUtil.empty(dateOfSearch)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateOfSearch);
			calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH)-ServiceUtil.getInt(NCB_DAY_OF_TIME_6M)));
//			List<NcbEnquiryDataM> iibNcbEnquiry = iibNcbInfo.getNcbEnquiry();
//			if(!ServiceUtil.empty(iibNcbEnquiry)){
//				int countNcb = 0;
//				uloNCB.setNoOfTimesEnquiry(iibNcbEnquiry.size());
//				for(NcbEnquiryDataM iibNcbEn : iibNcbEnquiry){
//					Date dateOfEnquiry = DecisionServiceUtil.toSqlDate(iibNcbEn.getDateOfEnquiry());
//					if(calendar.getTime().compareTo(dateOfEnquiry) == -1){
//						countNcb++;
//					}
//				}
//				uloNCB.setNoOfTimesEnquiry6M(countNcb);
//			}
		}
	}
	public void mapNCBInfo(NcbInfoDataM uloNCB, NcbInfo iibNcbInfo) throws Exception{
		if (iibNcbInfo == null) {
			return;
		}
		if (uloNCB == null) {
			return;
		}
		mapNumTimesOfEnquiry(uloNCB, iibNcbInfo);
		uloNCB.setConsentRefNo(iibNcbInfo.getConsentRefNo());
//		uloNCB.setTotCCOutStanding(iibNcbInfo.get);
//		uloNCB.setNoOfCCCard(iibNcbInfo.getNoOfCCCard());
		uloNCB.setPersonalLoanUnderBotIssuer(iibNcbInfo.getNumberOfPersonalLoanUnderBOTIssuer());
		uloNCB.setDateOfSearch(DecisionServiceUtil.toSqlDate(iibNcbInfo.getDateOfSearch()));
		FicoScore score = iibNcbInfo.getFicoScore();
		if (score != null) {
			uloNCB.setFicoErrorCode(score.getFicoErrorCode());
			uloNCB.setFicoErrorMsg(score.getFicoErrorMsg());
			uloNCB.setFicoReason1Code(score.getFicoReason1Code());
			uloNCB.setFicoReason1Desc(score.getFicoReason1Desc());
			uloNCB.setFicoReason2Code(score.getFicoReason2Code());
			uloNCB.setFicoReason2Desc(score.getFicoReason2Desc());
			uloNCB.setFicoReason3Code(score.getFicoReason3Code());
			uloNCB.setFicoReason3Desc(score.getFicoReason3Desc());
			uloNCB.setFicoReason4Code(score.getFicoReason4Code());
			uloNCB.setFicoReason4Desc(score.getFicoReason4Desc());
			uloNCB.setFicoScore(score.getFicoScore());
		} else {
			logger.debug("mapNCBInfo() KCBSData.FicoScore is null! ");
		}

		// Replace NCB Account		
			List<NcbAccounts> iibAccounts = iibNcbInfo.getNcbAccounts();
			if (iibAccounts == null || iibAccounts.size() < 1) {
				uloNCB.setNcbAccounts(null);
			} else {
				List<NcbAccountDataM> uloNCBAccounts = uloNCB.getNcbAccounts();
				if (uloNCBAccounts == null) {
					uloNCBAccounts = new ArrayList<NcbAccountDataM>();
					uloNCB.setNcbAccounts((ArrayList<NcbAccountDataM>) uloNCBAccounts);
				}

				for (NcbAccounts iibAcc : iibAccounts) {
					if (iibAcc == null || "".equals(iibAcc.getSegmentValue()) || null==iibAcc.getSegmentValue())
						continue;
					NcbAccountDataM uloAcc = new NcbAccountDataM();
					mapNCBAccount(uloAcc, iibAcc);
					uloNCBAccounts.add(uloAcc);
				}
			}

		// Replace NCB Address
		List<NcbAddress>  iibNcbAddresses = iibNcbInfo.getNcbAddress();
			if (iibNcbAddresses == null || iibNcbAddresses.size() < 1) {
				uloNCB.setNcbAddress(null);
			} else {
				List<NcbAddressDataM> uloNCBAddresss = uloNCB.getNcbAddress();
				if (uloNCBAddresss == null) {
					uloNCBAddresss = new ArrayList<NcbAddressDataM>();
					uloNCB.setNcbAddress((ArrayList<NcbAddressDataM>) uloNCBAddresss);
				}

				for (NcbAddress iibNcbAddress : iibNcbAddresses) {
					if (iibNcbAddress == null)
						continue;
					NcbAddressDataM uloAddr = new NcbAddressDataM();
					mapNCBAddress(uloAddr, iibNcbAddress);
					uloNCBAddresss.add(uloAddr);
				}
			}


		// Replace NCB ID
		List<NcbIds> iibNcbIds = iibNcbInfo.getNcbIds();
			if (iibNcbIds == null || iibNcbIds.size() < 1) {
				uloNCB.setNcbIds(null);
			} else {
				List<NcbIdDataM> uloNCBids = uloNCB.getNcbIds();
				if (uloNCBids == null) {
					uloNCBids = new ArrayList<NcbIdDataM>();
					uloNCB.setNcbIds((ArrayList<NcbIdDataM>) uloNCBids);
				}

				for (NcbIds iibNcbId : iibNcbIds) {
					if (iibNcbId == null)
						continue;
					NcbIdDataM uloNcbId = new NcbIdDataM();
					mapNCBID(uloNcbId, iibNcbId);
					uloNCBids.add(uloNcbId);
				}
			}

		// Replace NCB Name
		List<NcbNames> iibNcbNames = iibNcbInfo.getNcbNames();
			if (iibNcbNames == null || iibNcbNames.size() < 1) {
				uloNCB.setNcbNames(null);
			} else {
				List<NcbNameDataM> uloNames = uloNCB.getNcbNames();
				if (uloNames == null) {
					uloNames = new ArrayList<NcbNameDataM>();
					uloNCB.setNcbNames((ArrayList<NcbNameDataM>) uloNames);
				}

				for (NcbNames iibNcbName : iibNcbNames) {
					if (iibNcbName == null)
						continue;
					NcbNameDataM uloNcbId = new NcbNameDataM();
					mapNCBName(uloNcbId, iibNcbName);
					uloNames.add(uloNcbId);
				}
			}
	}

	public void mapNCBAccount(NcbAccountDataM uloNCB, NcbAccounts iibAccount)throws Exception {
		if (iibAccount == null) {
			return;
		}
		if (uloNCB == null) {
			return;
		}

		uloNCB.setAccountNumber(iibAccount.getAccountNumber());
		uloNCB.setAccountStatus(iibAccount.getAccountStatus());
		uloNCB.setAccountType(iibAccount.getAccountType());
		uloNCB.setAmtOwed(DecisionServiceUtil.stringToBigDecimal(iibAccount.getAmountOwed()));
		uloNCB.setAmtPastDue(DecisionServiceUtil.stringToBigDecimal(iibAccount.getAmountPastDue()));
		//uloNCB.setAsOfDate(DecisionServiceUtil.toSqlDate(iibAccount.getAsOfDate()));
		uloNCB.setCollateral1(iibAccount.getCollateral1());
		uloNCB.setCollateral2(iibAccount.getCollateral2());
		uloNCB.setCollateral3(iibAccount.getCollateral3());
		uloNCB.setCreditlimOrloanamt(DecisionServiceUtil.stringToBigDecimal(iibAccount.getCreditLimit()));
		//uloNCB.setCreditCardType(iibAccount.getCreditCardType());
		uloNCB.setCreditTypeFlag(iibAccount.getCreditTypeFlag());
//		uloNCB.setDateAccClosed(DecisionServiceUtil.toSqlDate(iibAccount.getDateAccountClose()));
//		uloNCB.setDateOfLastPmt(DecisionServiceUtil.toSqlDate(iibAccount.getDateOfLastPayment()));
//		uloNCB.setDefaultDate(DecisionServiceUtil.toSqlDate(iibAccount.getDefaultDate()));
		uloNCB.setInstallAmt(DecisionServiceUtil.stringToBigDecimal(iibAccount.getInstallmentAmount()));
		uloNCB.setInstallFreq(DecisionServiceUtil.stringToBigDecimal(iibAccount.getInstalmentFrequency()));
		uloNCB.setInstallNoofpay(DecisionServiceUtil.stringToBigDecimal(iibAccount.getInstallmentNumberOfPayments()));
		uloNCB.setLoanClass(iibAccount.getLoanClass());
		uloNCB.setLoanObjective(iibAccount.getLoanObjective());
		uloNCB.setMemberCode(iibAccount.getMemberCode());
		uloNCB.setMemberShortName(iibAccount.getMemberShortName());
		uloNCB.setNumberofCoborr(DecisionServiceUtil.stringToBigDecimal(iibAccount.getNumberOfCoborrowers()));
		uloNCB.setOwnershipIndicator(iibAccount.getOwnershipIndicator());
		uloNCB.setPercentPayment(DecisionServiceUtil.stringToBigDecimal(iibAccount.getPercentPayment()));
		uloNCB.setPmtHist1(iibAccount.getPaymentHistory1());
		uloNCB.setPmtHist2(iibAccount.getPaymentHistory2());
		uloNCB.setSegmentValue(iibAccount.getSegmentValue());
		uloNCB.setUnitMake(iibAccount.getUnitMake());
		uloNCB.setUnitModel(iibAccount.getUnitModel());
//		uloNCB.setLastDebtRestDate(DecisionServiceUtil.toSqlDate(iibAccount.getDateOfLastDebtRestructure()));
		
		//Map children
		List<NcbRuleResults>  iibNcbRuleResults =  iibAccount.getNcbRuleResults();
		if(iibNcbRuleResults != null && iibNcbRuleResults.size()>0){
			ArrayList<NcbAccountReportDataM> accountReports = new ArrayList<NcbAccountReportDataM>();
			uloNCB.setAccountReports(accountReports);
			NcbAccountReportDataM uloReport = new NcbAccountReportDataM();
			accountReports.add(uloReport);
			
//			uloReport.setRestructureGT1Yr(iibNcbAccReport.getRestructureGT1Yr());
			
			List<NcbAccountReportRuleDataM> uloRuleReports = new ArrayList<NcbAccountReportRuleDataM>();
			uloReport.setRules((ArrayList<NcbAccountReportRuleDataM>) uloRuleReports);
			
			for(NcbRuleResults iibNcbRuleResult :iibNcbRuleResults){
				if(iibNcbRuleResult != null){
					NcbAccountReportRuleDataM uloRuleReport = new NcbAccountReportRuleDataM();
					uloRuleReport.setRuleId(iibNcbRuleResult.getRuleId());
					uloRuleReport.setRuleResult(iibNcbRuleResult.getRuleResult());
//					uloRuleReport.setProductCode(iibNcbAccReport.getProductCode());
					uloRuleReports.add(uloRuleReport);
				}
			}
			
		}else{
			logger.debug("iibNcbAccReports is null!!");
		}
	}

	public void mapNCBAddress(NcbAddressDataM uloNCB, NcbAddress iibNcbAddress) throws Exception {
		if (iibNcbAddress == null) {
			return;
		}
		if (uloNCB == null) {
			return;
		}

		uloNCB.setAddressLine1(iibNcbAddress.getAddressLine1());
		uloNCB.setAddressLine2(iibNcbAddress.getAddressLine2());
		uloNCB.setAddressLine3(iibNcbAddress.getAddressLine3());
		uloNCB.setAddressType(iibNcbAddress.getAddressType());
		uloNCB.setCountry(iibNcbAddress.getCountry());
		uloNCB.setDistrict(iibNcbAddress.getDistrict());
		uloNCB.setPostalCode(iibNcbAddress.getPostalCode());
		uloNCB.setProvince(iibNcbAddress.getProvince());
//		 uloNCB.setReportedDate(DecisionServiceUtil.toSqlDate(iibNcbAddress.getReportedDate()));
		uloNCB.setResidentialStatus(iibNcbAddress.getResidentialSuatus());
		uloNCB.setSegmentValue(iibNcbAddress.getSegmentValue());
		uloNCB.setSubdistrict(iibNcbAddress.getSubdistrict());
		uloNCB.setTelephone(iibNcbAddress.getTelephone());
		uloNCB.setTelephoneType(iibNcbAddress.getTelephoneType());
	}

	public void mapNCBID(NcbIdDataM uloNCB, NcbIds iibNcbId) throws Exception{
		if (iibNcbId == null) {
			return;
		}
		if (uloNCB == null) {
			return;
		}

		uloNCB.setIdIssueCountry(iibNcbId.getIdIssueCountry());
		uloNCB.setIdNumber(iibNcbId.getIdNumber());
		uloNCB.setIdType(iibNcbId.getIdType());
		uloNCB.setSegmentValue(iibNcbId.getSegmentValue());
	}

	public void mapNCBName(NcbNameDataM uloNCB, NcbNames iibNcbName) throws Exception  {
		if (iibNcbName == null) {
			return;
		}
		if (uloNCB == null) {
			return;
		}

		uloNCB.setConsentToEnquire(iibNcbName.getConsentToEnquire());
//		 uloNCB.setDateOfBirth(DecisionServiceUtil.toSqlDate(iibNcbName.getDateOfBirth()));
		uloNCB.setFamilyName1(iibNcbName.getFamilyName1());
		uloNCB.setFamilyName2(iibNcbName.getFamilyName2());
		uloNCB.setFirstName(iibNcbName.getFirstName());
		uloNCB.setGender(DecisionServiceUtil.stringToInt(iibNcbName.getGender()));
		uloNCB.setMaritalStatus(iibNcbName.getMaritalStatus());
		uloNCB.setMiddleName(iibNcbName.getMiddle());
		uloNCB.setNationality(iibNcbName.getNationality());
		 uloNCB.setNumberOfChildren(DecisionServiceUtil.stringToInt(iibNcbName.getNumberOfChildren()));
		uloNCB.setOccupation(iibNcbName.getOccupation());
		uloNCB.setSegmentValue(iibNcbName.getSegmentValue());
		uloNCB.setSpouseName(iibNcbName.getSpouseName());
		uloNCB.setTitle(iibNcbName.getTitle());
	}
	
//    public void mapNCBAccountReport(NcbAccountReportDataM uloReport, AccountReports iibNcbAccReport) throws Exception{
//        if (uloReport == null) {
//            return;
//        }
//        if (iibNcbAccReport == null) {
//            return;
//        }
//        
//        uloReport.setRestructureGT1Yr(iibNcbAccReport.getRestructureGT1Yr());
//        
//        //Map Children
//        List<Rules> iibNcbRuleReports = iibNcbAccReport.getRules();
//            
//        if(iibNcbRuleReports != null && iibNcbRuleReports.size() > 0){
//            List<NcbAccountReportRuleDataM> uloRuleReports = new ArrayList<NcbAccountReportRuleDataM>();
//            uloReport.setRules((ArrayList<NcbAccountReportRuleDataM>) uloRuleReports);
//            for(int i = 0, s = iibNcbRuleReports.size(); i < s; i++){
//                Rules iibNcbRuleReport = iibNcbRuleReports.get(i);
//                if(iibNcbRuleReport == null)continue;
//                NcbAccountReportRuleDataM uloRuleReport = new NcbAccountReportRuleDataM();
//                mapNCBAccountRuleReport(uloRuleReport, iibNcbRuleReport);
//                uloRuleReports.add(uloRuleReport);
//            }
//        }
//    }
    
//    public void mapNCBAccountRuleReport(NcbAccountReportRuleDataM uloRuleReport, Rules iibNcbRuleReport) throws Exception{
//        if (uloRuleReport == null) {
//            return;
//        }
//        if (iibNcbRuleReport == null) {
//            return;
//        }
//        
//        uloRuleReport.setRuleId(iibNcbRuleReport.getRuleId());
//        uloRuleReport.setRuleResult(iibNcbRuleReport.getRuleResult());
//        uloRuleReport.setProductCode(iibNcbRuleReport.getProductCode());
//    }
	
	public  void mapCardlinkCustomer(CardlinkCustomerDataM uloCardLinkCust,CardLinkCustomers iibCardLinkCustomer) throws Exception{
		 uloCardLinkCust.setCardlinkCustNo(iibCardLinkCustomer.getCardlinkCustNo());
		 uloCardLinkCust.setOrgId(iibCardLinkCustomer.getOrgNo());
		 uloCardLinkCust.setFinancialInfoCode(iibCardLinkCustomer.getFinancialInfoCode()); 
		 uloCardLinkCust.setVatCode(iibCardLinkCustomer.getVatCode());
		 uloCardLinkCust.setNewCardlinkCustFlag(DecisionServiceUtil.FLAG_N);
	}
	
	public  void mapCardlinkCard(CardlinkCardDataM uloCardLinkCard,CardLinkCards iibCardLinkCard){
		 uloCardLinkCard.setOrgId(iibCardLinkCard.getMainOrgNo());
		 uloCardLinkCard.setCardlinkCustNo(iibCardLinkCard.getMainCustNo());
		 uloCardLinkCard.setCardNo(iibCardLinkCard.getCardNoInEncrypt());
		 uloCardLinkCard.setBlockCode(iibCardLinkCard.getBlockCode());
		 uloCardLinkCard.setBlockDate(DecisionServiceUtil.toSqlDate(iibCardLinkCard.getBlockDate()));
		 uloCardLinkCard.setProjectCode(iibCardLinkCard.getProjectCode());
		 uloCardLinkCard.setSupOrgId(iibCardLinkCard.getSupOrgNo());
		 uloCardLinkCard.setSupCustNo(iibCardLinkCard.getSupCustNo());
		 uloCardLinkCard.setCoaProductCode(iibCardLinkCard.getCoaProductCode());
		 uloCardLinkCard.setCardCode(iibCardLinkCard.getCardCode());
		 uloCardLinkCard.setPlasticCode(iibCardLinkCard.getPlasticCode());
	}
	
	public  void mapSpecialAdditionalService(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.SpecialAdditionalServiceDataM iibAdditionalService) throws Exception{
		if (null==iibAdditionalService) {
			String msg = "iibAdditionalService  is null!";
			logger.debug("msg>>"+msg);
		}
	}
	
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
		
		if(null!=iibVerResult.getIncomeCalculations() && iibVerResult.getIncomeCalculations().size()>0){
			ArrayList<IncomeCalculationDataM> incomeCalculations = new ArrayList<IncomeCalculationDataM>();
			uloVerResult.setIncomeCalculations(incomeCalculations);
			for(IncomeCalculations iibIncomeCalculation : iibVerResult.getIncomeCalculations()){
				mapIncomeCalculations(uloVerResult, iibIncomeCalculation);
			}	
		}
		
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
	
	public void privilegeProjectCodeMapper(PersonalInfoDataM uloPersonalInfo,Applications  iibApplication){
		VerificationResultDataM uloVerResult = uloPersonalInfo.getVerificationResult();
		if(null==uloVerResult){
			uloVerResult = new VerificationResultDataM();
		}
		PrivilegeProjectCodeDataM uloprivilegeProjectCode = uloVerResult.getPrivilegeProjectCode(0);
		if(null==uloprivilegeProjectCode){
			ArrayList<PrivilegeProjectCodeDataM> uloPrivilegeProjectCodes = new ArrayList<PrivilegeProjectCodeDataM>();
			uloprivilegeProjectCode = new PrivilegeProjectCodeDataM();
			uloPrivilegeProjectCodes.add(uloprivilegeProjectCode);
			uloVerResult.setPrivilegeProjectCodes(uloPrivilegeProjectCodes);
		}
		 PrivilegeProjectCodeProductCCADataM  uloPrivilegeProjectCodeProductCCA = uloprivilegeProjectCode.getPrivilegeProjectCodeProductCCAs(0);
		 if(null==uloPrivilegeProjectCodeProductCCA){
			 uloPrivilegeProjectCodeProductCCA = new PrivilegeProjectCodeProductCCADataM();
			 ArrayList<PrivilegeProjectCodeProductCCADataM> uloPrivilegeCCAs = new ArrayList<PrivilegeProjectCodeProductCCADataM>();
			 uloPrivilegeCCAs.add(uloPrivilegeProjectCodeProductCCA);
			 uloprivilegeProjectCode.setPrivilegeProjectCodeProductCCAs(uloPrivilegeCCAs);
		 }
		 
		 CcaCampaign   iibCcaCampaignInfo =  iibApplication.getCcaCampaign();
		 if(null!=iibCcaCampaignInfo){
			 mapPrivilegeProjectCodeProductCCA(uloPrivilegeProjectCodeProductCCA, iibCcaCampaignInfo);
		 }
	}
	
	public  void mapVerWebVerification(VerificationResultDataM uloPersonalVerification,List<WebVerifications> iibWebVerifications, ApplicationGroupDataM uloAppGroup) {
		if (uloPersonalVerification == null) {
			return;
		}
		List<WebVerificationDataM> uloWebVerList = uloPersonalVerification.getWebVerifications();			
		boolean isCompleted = ProcessUtil.isCompletedVerification(uloAppGroup, VerifyId.VERIFY_WEB);	
		logger.debug("isVerWeb Completed>>>"+isCompleted);
		if(isCompleted)return;//ST000000000023 : Completed Verification will not be mapped	
		
		if (iibWebVerifications == null || iibWebVerifications.size() < 1) {
			if (uloWebVerList != null && !uloWebVerList.isEmpty()) {
				uloWebVerList.clear();
			}

			uloPersonalVerification.setRequiredVerWebFlag(DecisionServiceUtil.FLAG_N);
			uloPersonalVerification.setVerWebResult(ProcessUtil.verWaivedDesc);
			uloPersonalVerification.setVerWebResultCode(ProcessUtil.verWaivedCode);

		} else {

			if (uloWebVerList == null) {
				uloWebVerList = new ArrayList<WebVerificationDataM>();
				uloPersonalVerification.setWebVerifications((ArrayList<WebVerificationDataM>) uloWebVerList);
			}
			
			// Prepare constant
			uloPersonalVerification.setVerWebResult(ProcessUtil.verRequiredDesc);
			uloPersonalVerification.setVerWebResultCode(ProcessUtil.verRequiredCode);

			List<String> iibCodeSet = new ArrayList<String>();
			if(DecisionServiceUtil.FLAG_Y.equals(uloPersonalVerification.getRequiredVerWebFlag())){
				for (WebVerifications iibWebVer : iibWebVerifications) {
					if (iibWebVer == null)
						continue;
					iibCodeSet.add(iibWebVer.getWebCode());
				}
				
			}

			if(iibCodeSet.isEmpty() || DecisionServiceUtil.FLAG_N.equals(uloPersonalVerification.getRequiredVerWebFlag())){
				uloPersonalVerification.setRequiredVerWebFlag(DecisionServiceUtil.FLAG_N);
				uloPersonalVerification.setVerWebResult(ProcessUtil.verWaivedDesc);
				uloPersonalVerification.setVerWebResultCode(ProcessUtil.verWaivedCode);
			}

			// Remove which iib doesn't require
			Set<String> uloCodeSet = new HashSet<String>();
			for (Iterator<WebVerificationDataM> it = uloWebVerList.iterator(); it.hasNext();) {
				WebVerificationDataM uloVer = it.next();
				if (uloVer == null)
					continue;
				String uloWebCode = uloVer.getWebCode();
				uloCodeSet.add(uloVer.getWebCode());
				if (!iibCodeSet.contains(uloWebCode)) {
					it.remove();
				}
			}

			// Add new one
			for (String iibWebCode : iibCodeSet) {
				if (iibWebCode == null)
					continue;
				if (!uloCodeSet.contains(iibWebCode)) {
					WebVerificationDataM newWebVer = new WebVerificationDataM();
					newWebVer.setWebCode(iibWebCode);
					uloWebVerList.add(newWebVer);
				}
			}
		}
	}
		
	public  void mapApplicationVerificationResult(VerificationResultDataM uloVerResult,VerificationResultApplications iibVerResult) throws Exception{
			policyRuleMapper(uloVerResult,iibVerResult);
	}
	
	public  void mapIncomeSource(PersonalInfoDataM uloPersonalInfo,IncomeSources iibIncomeSource){
		IncomeSourceDataM uloIncomeSource = uloPersonalInfo.getIncomeSourceByType(iibIncomeSource.getIncomeSource());
		if(null!=uloIncomeSource){
			uloIncomeSource.setIncomeSource(iibIncomeSource.getIncomeSource());
		}else{
			uloIncomeSource = new IncomeSourceDataM();
			uloIncomeSource.setIncomeSource(iibIncomeSource.getIncomeSource());
			uloPersonalInfo.addIncomeSources(uloIncomeSource);
		}	
	}
	
	public  void mapPriorityPassVerification(VerificationResultDataM uloVerResult,decisionservice_iib.PriorityPassVerificationTypeDataM iibPriorityPass){
		PriorityPassVerification uloPriorityPass = new PriorityPassVerification();
		uloPriorityPass.setPriorityPassVerificationRequiredFlag(iibPriorityPass.getPriorityPassVerificationRequiredFlag());
		uloPriorityPass.setRecommendScreen(iibPriorityPass.getRecommendScreen());
		uloVerResult.addPriorityPassVerification(uloPriorityPass);
		 
	}
	public  void mapDocumentSuggestions(VerificationResultDataM uloVerResult,DocumentSuggestions iibDocSuggestions) {	
		if(null!=iibDocSuggestions.getDocumentScenarios()){
			for(DocumentScenarios iibDocScrnario : iibDocSuggestions.getDocumentScenarios()){
				/*RequiredDocDataM  uloRequireDoc = uloVerResult.filterRequireDocument(iibDocSuggestions.getDocumentGroup(), iibDocScrnario.getScen());
				if(null==uloRequireDoc){
					logger.fatal("uloRequireDoc is Null>>");
					uloRequireDoc = new RequiredDocDataM();
					uloVerResult.addRequiredDocs(uloRequireDoc);		
				}*/
				RequiredDocDataM uloRequireDoc = new RequiredDocDataM();
				uloVerResult.addRequiredDocs(uloRequireDoc);
				
				uloRequireDoc.setScenarioType(iibDocSuggestions.getDocumentGroup());
				uloRequireDoc.setDocCompletedFlag(iibDocSuggestions.getDocCompletedFlag());
				uloRequireDoc.setDocumentGroupCode(DecisionServiceUtil.bigDecimalToString( iibDocScrnario.getScen()));
				mapDocumentScenarios(uloRequireDoc,iibDocScrnario);				
			}
			
		}else{
			logger.debug("iib DocumentScenarios is Null>>");
			if(null!=uloVerResult.getRequiredDocs() && uloVerResult.getRequiredDocs().size()>0){
				ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
				for(RequiredDocDataM requireDoc : uloVerResult.getRequiredDocs()){
					logger.debug("requireDoc.getScenarioType()>>"+requireDoc.getScenarioType());
					logger.debug("iibDocSuggestions.getDocumentGroup()>>"+iibDocSuggestions.getDocumentGroup());
					if(!requireDoc.getScenarioType().equals(iibDocSuggestions.getDocumentGroup())){
						RequiredDocDataM  requireDocument = new RequiredDocDataM();
						requireDocument.setScenarioType(iibDocSuggestions.getDocumentGroup());
						requireDocs.add(requireDocument);
					}else{
						requireDoc.setScenarioType(iibDocSuggestions.getDocumentGroup());
					}					
				}
				requireDocs.addAll(uloVerResult.getRequiredDocs());
				uloVerResult.setRequiredDocs(requireDocs);

			}else{
				RequiredDocDataM  requireDocument = new RequiredDocDataM();
				requireDocument.setScenarioType(iibDocSuggestions.getDocumentGroup());
				uloVerResult.addRequiredDocs(requireDocument);
			}
			
		}
	}
	
	public void mapIncomeCalculations(VerificationResultDataM uloVerResult, IncomeCalculations iibIncomeCalculation) {
		IncomeCalculationDataM uloIncomeCalculation = new IncomeCalculationDataM();
		uloIncomeCalculation.setIncomeSourceType(iibIncomeCalculation.getIncomeSourceType());
		uloIncomeCalculation.setVerifiedIncome(iibIncomeCalculation.getVerifiedIncome());
		uloIncomeCalculation.setTypeOfFin(iibIncomeCalculation.getTypeOfFin());
		uloIncomeCalculation.setTotalVerifiedIncome(iibIncomeCalculation.getTotalVerifiedIncome());
		uloIncomeCalculation.setExistingCreditLine(iibIncomeCalculation.getExistingCreditLine());
		uloIncomeCalculation.setVerifiedIncomeType(iibIncomeCalculation.getVerifiedIncomeType());
		uloIncomeCalculation.setVerifiedIncomeCalculation(iibIncomeCalculation.getVerifiedIncomeCalculation());
		uloVerResult.addIncomeCalculation(uloIncomeCalculation);
	}
	
	public  void mapDocumentScenarios(RequiredDocDataM  requireDocument,DocumentScenarios iibDocScenario) {
		if(null==iibDocScenario.getDoclists()){
			logger.debug("iibDocScenario.getDoclists() is null");
		}else{
			for(Doclists  iibDocList : iibDocScenario.getDoclists()){
				logger.debug("iibDocList.getDocumentType()>>"+iibDocList.getDocumentType());
				RequiredDocDetailDataM uloRequireDoc = requireDocument.filterRequiredDocDetail(iibDocList.getDocumentType());
				if(null==uloRequireDoc){
					uloRequireDoc = new RequiredDocDetailDataM();
					requireDocument.addRequiredDocDetails(uloRequireDoc);
				}
				uloRequireDoc.setDocumentCode(iibDocList.getDocumentType());
				uloRequireDoc.setMandatoryFlag(ProcessUtil.getULOBooleanFlag(iibDocScenario.getScenType()));
			}
		}
	}
			
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
	
	public void mapPolicyCondition(PolicyRulesConditionDataM uloPolicyRulesCondition,PolicyRuleConditions iibPolicyRuleCondition ) throws Exception{
		uloPolicyRulesCondition.setConditionCode(iibPolicyRuleCondition.getConditionCode());
	}
	
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
			String cardType = ProcessUtil.getCardType(uloApplicationType,uloCard, iibLoan,iibApplicatin,uloLoan.getRequestCoaProductCode());
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
	
	public void mapApplicationCaseNoApplication(ApplicationGroup iibApplicationGroup,ApplicationGroupDataM uloApplicationGroup,String personalId)throws Exception{
		for(Applications iibApplication : iibApplicationGroup.getApplications()){				
			if(null!=iibApplication.getVerificationResult() && null!=iibApplication.getPersonalId() && iibApplication.getPersonalId().equals(personalId)){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibApplication.getPersonalId());
				VerificationResultDataM uloVerification = uloPersonalInfo.getVerificationResult();
				if(null==uloVerification){
					uloVerification = new VerificationResultDataM();
					uloPersonalInfo.setVerificationResult(uloVerification);
				}
				VerificationResultApplications iibVer = iibApplication.getVerificationResult();
				policyRuleMapper(uloVerification,iibVer);
				mapReasonPolicyRulePersonalLevel(uloApplicationGroup,iibVer,uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId()));
			}	
		}
	} 

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
	
	public  void mapLoanPricing(LoanPricingDataM uloLoanPricing,LoanPricings iibLoanPricing) {
		uloLoanPricing.setFeeWaiveCode(iibLoanPricing.getFeeWaiveCode());	
		uloLoanPricing.setFeeStartMonth(EAppUtil.bigDecimalToInt(iibLoanPricing.getFeeStartMonth()));
		uloLoanPricing.setFeeEndMonth(EAppUtil.bigDecimalToInt(iibLoanPricing.getFeeEndMonth()));
		uloLoanPricing.setTerminationFeeFlag(iibLoanPricing.getTerminationFeeFlag());
		uloLoanPricing.setTerminationFeeCode(iibLoanPricing.getTerminationFeeCode());
	}
	
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
	
	public  void mapLoanTier(LoanTierDataM uloLoanTier,LoanTiers iibLoanTier,Integer seq) throws Exception{
		uloLoanTier.setMonthlyInstallment(iibLoanTier.getMonthlyInstallment());
		uloLoanTier.setIntRateAmount(iibLoanTier.getInterestRate());
		uloLoanTier.setRateType(iibLoanTier.getRateType());
		uloLoanTier.setSeq(seq);
	}
	
	public  void mapPrivilegeProjectCode(PrivilegeProjectCodeDataM uloPrivilegeProjectCode,decisionservice_iib.PrivilegeProjectCodeDataM iibPrivilegeProjectCode){
		uloPrivilegeProjectCode.setProjectCode(iibPrivilegeProjectCode.getProjectCode());		
	}
	
	public  void mapPrivilegeProjectCodeProductCCA(PrivilegeProjectCodeProductCCADataM uloPrivilegeProjectCodeProductCCA, CcaCampaign   iibCcaCampaignInfo){
		if(null!=iibCcaCampaignInfo.getAdditionalDetail() && !"".equals(iibCcaCampaignInfo.getAdditionalDetail())){
			uloPrivilegeProjectCodeProductCCA.setCcaProduct(iibCcaCampaignInfo.getAdditionalDetail());
		}	 
	}
	
	public void mapKBankInfo(PersonalInfoDataM uloPersonalInfo, PersonalInfos iibPersonalInfo) {
		if(null!=iibPersonalInfo.getKGroupFlag() 
				&& !(iibPersonalInfo.getKGroupFlag()).isEmpty()) {
			String kGroupFlag = iibPersonalInfo.getKGroupFlag();
//			for (String s : iibPersonalInfo.getKGroupFlag())
//			{
//				kGroupFlag = s ;
//			}
			uloPersonalInfo.setkGroupFlag(kGroupFlag);
		}
	}
	
	public void bScoreMapper(PersonalInfoDataM  uloPersonalInfo, BScores iibBScore) throws Exception{
		if(iibBScore !=null){
			BScoreDataM uloBScore = new BScoreDataM();
			mapBScore(uloBScore, iibBScore);
			List<BScoreDataM> uloBScoreList = uloPersonalInfo.getBscores();
			if (uloBScoreList == null) {
				uloBScoreList = new ArrayList<BScoreDataM>();
				uloPersonalInfo.setBScores((ArrayList<BScoreDataM>) uloBScoreList);
			}
			uloBScoreList.add(uloBScore);
		}
	}
	public  void mapBScore(BScoreDataM uloBScore, BScores iibBScore) throws Exception{
			uloBScore.setLpmId(iibBScore.getLpmId());
			uloBScore.setPostDate(DecisionServiceUtil.toSqlDate(iibBScore.getPostDate()));
			uloBScore.setProductType(iibBScore.getProductType());
			uloBScore.setRiskGrade(iibBScore.getRiskGrade());
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
