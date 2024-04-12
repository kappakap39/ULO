package com.eaf.service.common.iib.eapp.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.AdditionalServices;
import com.ava.flp.eapp.iib.model.Addresses;
import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.BankStatementDetails;
import com.ava.flp.eapp.iib.model.BankStatements;
import com.ava.flp.eapp.iib.model.Card;
import com.ava.flp.eapp.iib.model.CashTransfers;
import com.ava.flp.eapp.iib.model.CloseEndFounds;
import com.ava.flp.eapp.iib.model.DebtInfo;
import com.ava.flp.eapp.iib.model.DocumentSlaTypes;
import com.ava.flp.eapp.iib.model.Documents;
import com.ava.flp.eapp.iib.model.FinancialInstruments;
import com.ava.flp.eapp.iib.model.FixedAccounts;
import com.ava.flp.eapp.iib.model.FixedGuarantees;
import com.ava.flp.eapp.iib.model.Guidelines;
import com.ava.flp.eapp.iib.model.IncomeInfos;
import com.ava.flp.eapp.iib.model.IncomeSources;
import com.ava.flp.eapp.iib.model.KBankHeader;
import com.ava.flp.eapp.iib.model.KcbsResponse;
import com.ava.flp.eapp.iib.model.KviIncome;
import com.ava.flp.eapp.iib.model.LoanFees;
import com.ava.flp.eapp.iib.model.LoanPricings;
import com.ava.flp.eapp.iib.model.LoanTiers;
import com.ava.flp.eapp.iib.model.Loans;
import com.ava.flp.eapp.iib.model.MonthlyTawi50s;
import com.ava.flp.eapp.iib.model.MonthlyTaxi50Details;
import com.ava.flp.eapp.iib.model.NcbInfo;
import com.ava.flp.eapp.iib.model.OpenEndFundDetails;
import com.ava.flp.eapp.iib.model.OpendEndFounds;
import com.ava.flp.eapp.iib.model.OrPolicyRules;
import com.ava.flp.eapp.iib.model.OrPolicyRulesDetails;
import com.ava.flp.eapp.iib.model.PayRolls;
import com.ava.flp.eapp.iib.model.PaymentMethods;
import com.ava.flp.eapp.iib.model.PayslipMonthlyDetails;
import com.ava.flp.eapp.iib.model.PayslipMonthlys;
import com.ava.flp.eapp.iib.model.Payslips;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.PolicyRuleConditions;
import com.ava.flp.eapp.iib.model.PolicyRuleReasons;
import com.ava.flp.eapp.iib.model.PolicyRules;
import com.ava.flp.eapp.iib.model.PreviousIncomes;
import com.ava.flp.eapp.iib.model.ReferCriterias;
import com.ava.flp.eapp.iib.model.ReferencePersons;
import com.ava.flp.eapp.iib.model.SalaryCerts;
import com.ava.flp.eapp.iib.model.SaleInfos;
import com.ava.flp.eapp.iib.model.SavingAccountDetails;
import com.ava.flp.eapp.iib.model.SavingAccounts;
import com.ava.flp.eapp.iib.model.Taweesaps;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.ava.flp.eapp.iib.model.WebVerifications;
import com.ava.flp.eapp.iib.model.YearlyTawi50s;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.ClosedEndFundDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;
import com.eaf.orig.ulo.model.app.FixedAccountDataM;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanFeeDataM;
import com.eaf.orig.ulo.model.app.LoanPricingDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PayrollDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.TaweesapDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

import decisionservice_iib.DocumentSlaTypeDataM;

public class EDecisionServiceRequestMapper {
	private static transient Logger logger = Logger.getLogger(EDecisionServiceRequestMapper.class);
	private KBankHeader  kbankHeader;	
	String RECOMMEND_DECISION_CANCEL = ServiceCache.getConstant("RECOMMEND_DECISION_CANCEL");
	public  ApplicationGroup  getEDecsionServiceMapper(ApplicationGroupDataM uloApplicationGroup,String decisionPoint)throws Exception{
		if (null==uloApplicationGroup) {
			String msg = "uloApplicationGroup is null!";
			throw new Exception(msg);
		}else{
			ApplicationGroup iibApplicationGroup  = new ApplicationGroup();
			mapApplicationGroup(decisionPoint, uloApplicationGroup, iibApplicationGroup);
			applicationMapper(uloApplicationGroup, iibApplicationGroup);
			personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
			
			if(null!=uloApplicationGroup.getReferencePersons()){
				for(ReferencePersonDataM uloRefPerson : uloApplicationGroup.getReferencePersons()){
					ReferencePersons iibRefPersonal = new ReferencePersons();
					mapReferencePerson(uloRefPerson, iibRefPersonal);
					iibApplicationGroup.addReferencePersonsItem(iibRefPersonal);
				}
			}
			if(null!=uloApplicationGroup.getSaleInfos()){
				for(SaleInfoDataM uloSaleInfo : uloApplicationGroup.getSaleInfos()){
					SaleInfos iibSaleInfo = new SaleInfos();
					mapSaleInfo(uloSaleInfo, iibSaleInfo);
					iibApplicationGroup.addSaleInfosItem(iibSaleInfo);
				}
			}
			iibApplicationGroup.setProcessAction(uloApplicationGroup.getSourceAction());
			return iibApplicationGroup;
		}
	}
	
	public void  applicationMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup)throws Exception {

		ArrayList<ApplicationDataM> uloApplicationList = uloApplicationGroup.getApplicationLifeCycle(new BigDecimal(uloApplicationGroup.getMaxLifeCycle()));
		int countApplication = 0;
		if (null==uloApplicationList) {
			String msg = "uloApplicationList  is null!";
			throw new Exception(msg);
		}else{
			for(ApplicationDataM uloApplication : uloApplicationList){
				if(!RECOMMEND_DECISION_CANCEL.equals(uloApplication.getFinalAppDecision())){
					countApplication = countApplication+1;
					uloApplication.setSeq(countApplication);
					Applications iibApplication = new Applications();
					iibApplicationGroup.addApplicationsItem(iibApplication);
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					LoanDataM uloLoan =   uloApplication.getLoan();
					if(null!=uloLoan){
						mapBilingCycle(uloApplicationGroup,uloLoan, iibApplication);
					}			
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);
					
					VerificationResultApplications iibVerResult = new VerificationResultApplications();
					iibApplication.setVerificationResult(iibVerResult);
					verificationMapper(uloApplication, iibVerResult);
				}
			}
		}
	}
	
	public void  personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup)throws Exception {
		ArrayList<PersonalInfoDataM> uloPersonalInfos = uloApplicationGroup.getPersonalInfos();
		
		if (null==uloPersonalInfos) {
			String msg = "uloPersonal is null!";
			throw new Exception(msg);
		}else{
			for(PersonalInfoDataM uloPersonalInfo :  uloPersonalInfos){
				logger.debug("uloPersonalInfo.personalId : "+uloPersonalInfo.getPersonalId());
				PersonalInfos iibPersonalInfo = new PersonalInfos();
				iibApplicationGroup.addPersonalInfosItem(iibPersonalInfo);
			
				mapPersonalInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
				addressMapper(uloPersonalInfo, iibPersonalInfo);
				cardLinkMapper(uloPersonalInfo, iibPersonalInfo);
				cardLinkCustomerMapper(uloPersonalInfo, iibPersonalInfo);
				debtInfoMapper(uloPersonalInfo, iibPersonalInfo);
				ncbMapper(uloPersonalInfo, iibPersonalInfo);
				incomeMapper(uloPersonalInfo, iibPersonalInfo);
				logger.debug("uloPersonalInfo.incomeMapper : ");
				imageSplitMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
				
//				KPL Additional
				tcbLoansMapper(uloPersonalInfo, iibPersonalInfo);
				internalApplicantMapper(uloPersonalInfo, iibPersonalInfo);
				ccaCampaignsMapper(uloPersonalInfo, iibPersonalInfo);
				
				VerificationResultPersonalInfos iibVerResult = new VerificationResultPersonalInfos();
				iibPersonalInfo.setVerificationResult(iibVerResult);
				if(null!=uloPersonalInfo.getVerificationResult()){					
					verificationMapper(uloPersonalInfo, iibVerResult);
				}	
				incomeSourceMapper(uloPersonalInfo, iibVerResult);
			}
		}
	}
	

	public void imageSplitMapper(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo) throws Exception{
		ArrayList<ApplicationImageSplitDataM> applicationImageSplits = ProcessUtil.getPersonalApplicationImageSplits(uloApplicationGroup,uloPersonalInfo);
		if(null!=applicationImageSplits && applicationImageSplits.size()>0){
			mapApplicationImages(applicationImageSplits, iibPersonalInfo);
		}else{
			logger.debug("doc of personal id:"+uloPersonalInfo.getPersonalId()+"is null!!");
		}
	}
	
	public void  loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,Applications iibApplication)throws Exception {
		if (null==uloApplication.getLoans()) {
			String msg = "uloLoans is null!";
			throw new Exception(msg);
		}else{
			for(LoanDataM uloLoan : uloApplication.getLoans()){
				Loans iibLoan = new Loans();
				
				//KPL Additional Add handle for diffReq calculate button
				if(!Util.empty(uloApplication.getDiffRequestFlag()))
				{mapLoanCalcDiff(uloLoan, iibLoan);}
				else
				{mapLoan(uloLoan, iibLoan);}
				
				iibLoan.setCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				//add coa product code to ulo loan for compare set card type
				uloLoan.setRequestCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				iibApplication.addLoansItem(iibLoan);
				
				ArrayList<SpecialAdditionalServiceDataM>  specialAdditionalService = uloApplicationGroup.getSpecialAdditionalServiceLoan(uloLoan.getLoanId());
				if(null!=specialAdditionalService  && specialAdditionalService.size()>0){	
					for(SpecialAdditionalServiceDataM uloAdditionalService : specialAdditionalService){
						AdditionalServices iibAdditionalService = new AdditionalServices();
						mapAdditionalService(uloAdditionalService, iibAdditionalService);
						iibLoan.addAdditionalServicesItem(iibAdditionalService);
					}
				}
								
				if(null!=uloLoan.getPaymentMethodId()){
					PaymentMethodDataM uloPaymentMethod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
					PaymentMethods  iibPaymentMethod = new PaymentMethods();
					mapPaymentMethod(uloPaymentMethod, iibPaymentMethod);
					iibLoan.addPaymentMethodsItem(iibPaymentMethod);
				}
				
				Card iibCard = new Card();
				if(uloLoan.getCard() != null)
				{mapCard(uloLoan.getCard(), iibCard);}
				iibLoan.setCard(iibCard);
				
				cashTransferMapper(uloLoan,iibLoan);
				loanFeeMapper(uloLoan, iibLoan);
				loanPricingMapper(uloLoan, iibLoan);
				loanTierMapper(uloLoan, iibLoan);
			}
		}
	}
	public void  loanFeeMapper(LoanDataM uloLoan,Loans iibLoan)throws Exception {
		if (null==uloLoan.getLoanFees()) {
			logger.debug("uloLoanFees() is null!");
		}else{
			for(LoanFeeDataM uloLoanFee : uloLoan.getLoanFees()){
				LoanFees iibLoanFee = new LoanFees();
				mapLoanFee(uloLoanFee, iibLoanFee);
				iibLoan.addLoanFeesItem(iibLoanFee);
			}
		}
	}
	
	public void  loanTierMapper(LoanDataM uloLoan,Loans iibLoan)throws Exception {
		if (null==uloLoan.getLoanTiers()) {
			logger.debug("ulotLoanTiers is null!");
		}else{
			for(LoanTierDataM uloLoanTier : uloLoan.getLoanTiers()){
				LoanTiers iibLoanTier = new LoanTiers();
				mapLoanTier(uloLoanTier, iibLoanTier);
				iibLoan.addLoanTiersItem(iibLoanTier);
			}
		}
	}
	public void  loanPricingMapper(LoanDataM uloLoan,Loans iibLoan)throws Exception {
		if (null==uloLoan.getLoanPricings()) {
			logger.debug("uloLoanPricing is null!");
		}else{
			for(LoanPricingDataM uloLoanPricing : uloLoan.getLoanPricings()){
				LoanPricings iibLoanPricing = new LoanPricings();
				mapLoanPricing(uloLoanPricing, iibLoanPricing);
				iibLoan.addLoanPricingsItem(iibLoanPricing);
			}
		}
	}
		
	public void  incomeMapper(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo)throws Exception {
		if (null==uloPersonalInfo.getIncomeInfos()) {
			logger.debug("uloIncome is null!");
		}else{
			logger.debug("uloIncome is not null!");
			for(IncomeInfoDataM   uloIncomeInfo: uloPersonalInfo.getIncomeInfos()){
				IncomeInfos iibIncomeInfo = new IncomeInfos();
				mapIncomeInfo(uloIncomeInfo, iibIncomeInfo);
				iibPersonalInfo.addIncomeInfosItem(iibIncomeInfo);
				List<IncomeCategoryDataM> uloIncomeList = uloIncomeInfo.getAllIncomes();

				if(null==uloIncomeList || uloIncomeList.size()<=0){
					 continue;
				}
				String FIELD_ID_REVENUE_CATEGORY = DecisionServiceCacheControl.getConstant("FIELD_ID_REVENUE_CATEGORY");
				for (IncomeCategoryDataM uloIncomeElement : uloIncomeList) {
					String incomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "CHOICE_NO", uloIncomeElement.getIncomeType(), "SYSTEM_ID2");
					iibIncomeInfo.setIncomeType(incomeType);
					if (uloIncomeElement instanceof PayslipDataM) {
						PayslipDataM uloPayslip = (PayslipDataM) uloIncomeElement;
						Payslips iibPayslip = new Payslips();
						payslipMapper(uloPayslip, iibPayslip);
						if(uloPayslip != null && uloPayslip.getNoMonth() >= 6){
//	Refer to ST000000000078 : Alternative condition for income type, the value will be retrieved from SystemID4
							incomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "CHOICE_NO", uloPayslip.getIncomeType(), "SYSTEM_ID2");
							iibIncomeInfo.setIncomeType(incomeType);
						}
						
						iibIncomeInfo.addPayslipsItem(iibPayslip);
					}else if (uloIncomeElement instanceof TaweesapDataM) {
						TaweesapDataM uloTaweesap = (TaweesapDataM) uloIncomeElement;
						Taweesaps iibTaweesap = new Taweesaps();
						taweesapMapper(uloTaweesap, iibTaweesap);
						iibIncomeInfo.getTaweesaps();
						
					}else if (uloIncomeElement instanceof SavingAccountDataM) {
						SavingAccountDataM uloSavingAccount = (SavingAccountDataM) uloIncomeElement;	
						SavingAccounts iibSavingAccount = new SavingAccounts();
						savingAccountMapper(uloSavingAccount, iibSavingAccount);
						iibIncomeInfo.addSavingAccountsItem(iibSavingAccount);
						
					}else if (uloIncomeElement instanceof SalaryCertDataM) {
						SalaryCertDataM uloSalaryCert= (SalaryCertDataM) uloIncomeElement;
						SalaryCerts iibSalaryCert = new SalaryCerts();
						salaryCertMapper(uloSalaryCert, iibSalaryCert);
						iibIncomeInfo.addSalaryCertsItem(iibSalaryCert);
						
					}else if (uloIncomeElement instanceof PreviousIncomeDataM) {
						PreviousIncomeDataM uloPreviosIncome = (PreviousIncomeDataM) uloIncomeElement;
						PreviousIncomes iibPreviousIncome = new PreviousIncomes();
						previousIncomeMapper(uloPreviosIncome, iibPreviousIncome);
						iibIncomeInfo.addPreviousIncomesItem(iibPreviousIncome);
						
					}else if (uloIncomeElement instanceof PayrollDataM) {
						PayrollDataM uloPayroll = (PayrollDataM) uloIncomeElement;
						PayRolls iibPayroll = new PayRolls();
						payrollMapper(uloPayroll, iibPayroll);
						iibIncomeInfo.addPayRollsItem(iibPayroll);
						
					}else if (uloIncomeElement instanceof OpenedEndFundDataM) {
						OpenedEndFundDataM uloOpenedEndFund = (OpenedEndFundDataM) uloIncomeElement;
						OpendEndFounds iibOpenedEndFund = new OpendEndFounds();
						openedEndFundMapper(uloOpenedEndFund, iibOpenedEndFund);
						iibIncomeInfo.addOpendEndFoundsItem(iibOpenedEndFund);
						
					}else if (uloIncomeElement instanceof MonthlyTawi50DataM) {
						MonthlyTawi50DataM uloMonthlyTawi50= (MonthlyTawi50DataM) uloIncomeElement;
						MonthlyTawi50s iibMonthlyTawi50 = new MonthlyTawi50s();
						monthlyTawi50Mapper(uloMonthlyTawi50, iibMonthlyTawi50);
						iibIncomeInfo.addMonthlyTawi50sItem(iibMonthlyTawi50);
						
					}else if (uloIncomeElement instanceof KVIIncomeDataM) {
						KVIIncomeDataM uloKVIincome = (KVIIncomeDataM) uloIncomeElement;
						KviIncome iibKVIIncome = new KviIncome();
						kviIncomeMapper(uloKVIincome, iibKVIIncome);
						iibIncomeInfo.addKviIncomeItem(iibKVIIncome);
						
					}else if (uloIncomeElement instanceof FixedGuaranteeDataM) {
						FixedGuaranteeDataM uloFixedGuarantee = (FixedGuaranteeDataM) uloIncomeElement;
						FixedGuarantees iibFixedGuarantee = new FixedGuarantees();
						fixedGuaranteeMapper(uloFixedGuarantee, iibFixedGuarantee);
						iibIncomeInfo.addFixedGuaranteesItem(iibFixedGuarantee);
						
					}else if (uloIncomeElement instanceof FixedAccountDataM) {
						FixedAccountDataM uloFixedAccount = (FixedAccountDataM) uloIncomeElement;
						FixedAccounts iibFixedAccount = new FixedAccounts();
						fixedAccountMapper(uloFixedAccount, iibFixedAccount);
						iibIncomeInfo.addFixedAccountsItem(iibFixedAccount);
						
					}else if (uloIncomeElement instanceof FinancialInstrumentDataM) {
						FinancialInstrumentDataM uloFinancialInst = (FinancialInstrumentDataM) uloIncomeElement;
						FinancialInstruments iibFinalcialInst = new FinancialInstruments();
						financialInstrumentMapper(uloFinancialInst, iibFinalcialInst);
						iibIncomeInfo.addFinancialInstrumentsItem(iibFinalcialInst);
						
					}else if (uloIncomeElement instanceof ClosedEndFundDataM) {
						ClosedEndFundDataM uloCloseEndFund = (ClosedEndFundDataM) uloIncomeElement;
						CloseEndFounds iibCloseEndFund = new CloseEndFounds();
						closedEndFundMapper(uloCloseEndFund, iibCloseEndFund);
						iibIncomeInfo.addCloseEndFoundsItem(iibCloseEndFund);
						
					}else if (uloIncomeElement instanceof BankStatementDataM) {
						BankStatementDataM uloBankStatement = (BankStatementDataM) uloIncomeElement;
						BankStatements iibBankStatement = new BankStatements();
						bankStatementMapper(uloBankStatement, iibBankStatement);
						iibIncomeInfo.addBankStatementsItem(iibBankStatement);
						
					}else if (uloIncomeElement instanceof YearlyTawi50DataM) {
						YearlyTawi50DataM uloYearlyTawi50 = (YearlyTawi50DataM) uloIncomeElement;
						YearlyTawi50s iibYearlyTawi50 = new YearlyTawi50s();
						yearlyTawi50Mapper(uloYearlyTawi50, iibYearlyTawi50);
						iibIncomeInfo.addYearlyTawi50sItem(iibYearlyTawi50);
					}
				}
				
			}
		}
		 
	}
	
	public void  bankStatementMapper(BankStatementDataM  uloBankStatement,BankStatements iibBankStatement)throws Exception {
		if (null==uloBankStatement) {
			logger.debug("uloBankStatementis null!");
		}else{
			mapBankStatement(uloBankStatement, iibBankStatement);
			if(null!=uloBankStatement.getBankStatementDetails() &&  uloBankStatement.getBankStatementDetails().size()>0){
				for(BankStatementDetailDataM uloBankStatementDetail : uloBankStatement.getBankStatementDetails()){
					BankStatementDetails iibBankStatementDetail = new BankStatementDetails();
					mapBankStatementDetail(uloBankStatementDetail, iibBankStatementDetail);
					iibBankStatement.addBankStatementDetailsItem(iibBankStatementDetail);
				}
			}
		}
	}
	
	public void  closedEndFundMapper(ClosedEndFundDataM  uloCloseEndFund, CloseEndFounds  iibCloseEndFund)throws Exception {
		if (null==uloCloseEndFund) {
			logger.debug("uloCloseEndFund null!");
		}else{
			mapClosedEndFund(uloCloseEndFund, iibCloseEndFund);
		}	
	}
	
	public void  financialInstrumentMapper(FinancialInstrumentDataM  uloFinancialInst,FinancialInstruments  iibFinalcialInst)throws Exception {
		if (null==uloFinancialInst) {
			logger.debug("uloFinancialInst null!");
		}else{
			mapFinancialInstrument(uloFinancialInst, iibFinalcialInst);
		}
	}
	
	public void  fixedAccountMapper(FixedAccountDataM uloFixedAccount,FixedAccounts  iibFixedAccount)throws Exception {
		if (null==uloFixedAccount) {
			logger.debug("uloFixedAccount null!");
		}else{
			mapFixedAccount(uloFixedAccount, iibFixedAccount);
		}
	}
		  
	public void  fixedGuaranteeMapper(FixedGuaranteeDataM  uloFixedGuarantee,FixedGuarantees  iibFixedGuarantee)throws Exception {
		if (null==uloFixedGuarantee) {
			logger.debug("uloFixedGuarantee null!");
		}else{
			mapFixedGuarantee(uloFixedGuarantee, iibFixedGuarantee);
		}
	}
	
	public void  kviIncomeMapper(KVIIncomeDataM  uloKVIincome,KviIncome  iibKVIIncome)throws Exception {
		if (null==uloKVIincome) {
			logger.debug("uloKVIincome null!");
		}else{
			mapKviIncome(uloKVIincome, iibKVIIncome);
		}
	}
	
	public void  monthlyTawi50Mapper(MonthlyTawi50DataM  uloMonthlyTawi50,MonthlyTawi50s  iibMonthlyTawi50)throws Exception {
		if (null==uloMonthlyTawi50) {
			logger.debug("uloMonthlyTawi50 null!");
		}else{
			mapMonthlyTawi50(uloMonthlyTawi50, iibMonthlyTawi50);
			if(null!=uloMonthlyTawi50.getMonthlyTaxi50Details() &&  uloMonthlyTawi50.getMonthlyTaxi50Details().size()>0){
				for(MonthlyTawi50DetailDataM uloMonthlyTawi50Detail : uloMonthlyTawi50.getMonthlyTaxi50Details()){
					MonthlyTaxi50Details iibMonthlyTawi50Detail = new MonthlyTaxi50Details();
					mapMonthlyTawi50Detail(uloMonthlyTawi50Detail, iibMonthlyTawi50Detail);
					iibMonthlyTawi50.addMonthlyTaxi50DetailsItem(iibMonthlyTawi50Detail);
				}
			}
		}
	}
	
	public void  openedEndFundMapper(OpenedEndFundDataM  uloOpenedEndFund,OpendEndFounds  iibOpenedEndFund) throws Exception {
		if (null==uloOpenedEndFund) {
			logger.debug("uloOpenedEndFund null!");
		}else{
			mapOpenedEndFund(uloOpenedEndFund, iibOpenedEndFund);
			if(null!=uloOpenedEndFund.getOpenEndFundDetails() && uloOpenedEndFund.getOpenEndFundDetails().size()>0){
				for(OpenedEndFundDetailDataM uloOpenedEndFundDetail : uloOpenedEndFund.getOpenEndFundDetails()){
					OpenEndFundDetails iibOpenedEndFundDetail = new OpenEndFundDetails();
					mapOpenedEndFundDetail(uloOpenedEndFundDetail, iibOpenedEndFundDetail);
					iibOpenedEndFund.addOpenEndFundDetailsItem(iibOpenedEndFundDetail);
				}
			}
		}
	}
	
	public void  payrollMapper(PayrollDataM uloPayroll,PayRolls  iibPayroll)throws Exception {
		if (null==uloPayroll) {
			logger.debug("uloPayroll null!");
		}else{
			mapPayroll(uloPayroll, iibPayroll);
		}
	}
	
	public void  payslipMapper(PayslipDataM   uloPaySlip,Payslips  iibPaySlip)throws Exception {
		if (null==uloPaySlip) {
			logger.debug("uloPaySlip null!");
		}else{
			mapPayslip(uloPaySlip, iibPaySlip);
			if(null!=uloPaySlip.getPayslipMonthlys() &&  uloPaySlip.getPayslipMonthlys() .size()>0){
				for(PayslipMonthlyDataM uloPaySlipMonthly : uloPaySlip.getPayslipMonthlys()){
					PayslipMonthlys iibPaySlipMonthly = new PayslipMonthlys();				
					mapPayslipMonthly(uloPaySlipMonthly, iibPaySlipMonthly);
					iibPaySlip.addPayslipMonthlysItem(iibPaySlipMonthly);
				}
			}
		}
	}
	
	public void  previousIncomeMapper(PreviousIncomeDataM uloPreviosIncome,PreviousIncomes  iibPreviousIncome)throws Exception {
		if (null==uloPreviosIncome) {
			logger.debug("uloPreviosIncome null!");
		}else{
			mapPreviousIncome(uloPreviosIncome, iibPreviousIncome);
		}
	}
	
	public void  salaryCertMapper(SalaryCertDataM  uloSalaryCert,SalaryCerts  iibSalaryCert)throws Exception {
		if (null==uloSalaryCert) {
			logger.debug("uloSalaryCert null!");
		}else{
			mapSalaryCert(uloSalaryCert, iibSalaryCert);
		}
	}
	
	public void  savingAccountMapper(SavingAccountDataM  uloSavingAccount,SavingAccounts iibSavingAccount)throws Exception {
		if (null==uloSavingAccount) {
			logger.debug("uloSavingAccount null!");
		}else{
			mapSavingAccount(uloSavingAccount, iibSavingAccount);
			if(null!=uloSavingAccount.getSavingAccountDetails() && uloSavingAccount.getSavingAccountDetails().size()>0){
				for(SavingAccountDetailDataM uloSavingAccDetail : uloSavingAccount.getSavingAccountDetails()){
					SavingAccountDetails iibSavingAccountDetail = new SavingAccountDetails();
					mapSavingAccountDetail(uloSavingAccDetail, iibSavingAccountDetail);
					iibSavingAccount.addSavingAccountDetailsItem(iibSavingAccountDetail);
				}
			}
		}
	}
	
	public void  taweesapMapper(TaweesapDataM uloTaweesap,Taweesaps  iibTaweesap)throws Exception {
		if (null==uloTaweesap) {
			logger.debug("uloTaweesap null!");
		}else{
			mapTaweesapDataM(uloTaweesap, iibTaweesap);
		}
	}
	
	public void  yearlyTawi50Mapper(YearlyTawi50DataM  uloYearlyTawi50,YearlyTawi50s  iibYearlyTawi50)throws Exception {
		if (null==uloYearlyTawi50) {
			logger.debug("uloYearlyTawi50 null!");
		}else{
			mapYearlyTawi50(uloYearlyTawi50, iibYearlyTawi50);
		}
	}
		
	public void  ncbMapper(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo)throws Exception {
		if(null!=uloPersonalInfo.getNcbInfos()&&uloPersonalInfo.getNcbInfos().size()>0){
			NcbInfoDataM   uloNcbInfo = uloPersonalInfo.getNcbInfos().get(0);
			NcbInfo iibNcbInfo= new NcbInfo();
			KcbsResponse iibKcbsReason = new KcbsResponse();
			mapNcbInfo(uloNcbInfo, iibNcbInfo);
			mapKcbsReason(uloNcbInfo,iibKcbsReason);
			iibPersonalInfo.setNcbInfo(iibNcbInfo);
			iibPersonalInfo.setKcbsResponse(iibKcbsReason);
		}else{
			logger.debug("ulo ncb is null");
		}
	}
	public void  addressMapper(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo)throws Exception {
		if(null==uloPersonalInfo.getAddresses()){
			logger.debug("uloAddress is null!");
		}else{
			for(AddressDataM uloAddress : uloPersonalInfo.getAddresses()){
				Addresses iibAddress = new Addresses();
				mapAddress(uloAddress, iibAddress);
				iibPersonalInfo.addAddressesItem(iibAddress);
			}
		}
	}
	
	public void  incomeSourceMapper(PersonalInfoDataM uloPersonalInfo,VerificationResultPersonalInfos iibVerification)throws Exception {
		if(null==uloPersonalInfo.getIncomeSources()){
			logger.debug("uloIncomeSources is null!");
		}else{
			iibVerification.setVerifiedIncomeSource(uloPersonalInfo.getSorceOfIncome());
			for(IncomeSourceDataM   uloIncomeSource: uloPersonalInfo.getIncomeSources()){
				IncomeSources iibIncomeSource= new IncomeSources();
				mapIncomeSource(uloIncomeSource, iibIncomeSource);
				iibVerification.addIncomeSourcesItem(iibIncomeSource);
			}
		}
	}
	
	public void  debtInfoMapper(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo)throws Exception {
		if(null==uloPersonalInfo.getDebtInfos()){
			logger.debug("uloDebtInfo is null!");
		}else{
			DebtInfo iibDebtInfo = new DebtInfo();
			mapDebtInfo(uloPersonalInfo.getDebtInfos(), iibDebtInfo);
			iibPersonalInfo.addDebtInfoItem(iibDebtInfo);
		}
	}
		
	public void  cashTransferMapper(LoanDataM uloLoan ,Loans iibLoan)throws Exception {
		if(null!=uloLoan.getCashTransfers() && uloLoan.getCashTransfers().size()>0){
			for(CashTransferDataM uloCashTransfer : uloLoan.getCashTransfers()){
				CashTransfers iibCashTransfer = new CashTransfers();
				mapCashTransfer(uloCashTransfer, iibCashTransfer);
				iibLoan.addCashTransfersItem(iibCashTransfer);
			}
		}else{
			logger.debug("uloCashTransfers is null!");
		}
	}
	
	public void  verificationMapper(Object uloObject,VerificationResultPersonalInfos iibVerificationResult)throws Exception {
		if(uloObject instanceof PersonalInfoDataM){
			PersonalInfoDataM uloPersonal = (PersonalInfoDataM)uloObject;
			VerificationResultDataM uloVerification =uloPersonal.getVerificationResult();
			if(null!=uloVerification){
				mapPersonalVerificationResult(uloVerification, iibVerificationResult);

				if(null!=uloVerification.getWebVerifications()&& uloVerification.getWebVerifications().size()>0){
					for(WebVerificationDataM uloWebVerification : uloVerification.getWebVerifications()){
						WebVerifications iibWebVerification = new WebVerifications();
						mapWebVerification(uloWebVerification, iibWebVerification);
						iibVerificationResult.addWebVerificationsItem(iibWebVerification);
					}
				}
			}		
		}else if(uloObject instanceof ApplicationDataM){			
			ApplicationDataM  uloApplication = (ApplicationDataM)uloObject;
			VerificationResultDataM uloVerification =uloApplication.getVerificationResult();
			if(null!=uloVerification){
				mapApplicationVerificationResult(uloVerification, iibVerificationResult);
				//policyRulesMapper(uloVerification.getPolicyRules(), iibVerificationResult);
				
			}
		}
	}
	public void  verificationMapper(Object uloObject,VerificationResultApplications iibVerificationResult)throws Exception {
		if(uloObject instanceof PersonalInfoDataM){
			PersonalInfoDataM uloPersonal = (PersonalInfoDataM)uloObject;
			VerificationResultDataM uloVerification =uloPersonal.getVerificationResult();
			if(null!=uloVerification){
				//mapPersonalVerificationResult(uloVerification, iibVerificationResult);

//				if(null!=uloVerification.getWebVerifications()&& uloVerification.getWebVerifications().size()>0){
//					for(WebVerificationDataM uloWebVerification : uloVerification.getWebVerifications()){
//						WebVerifications iibWebVerification = new WebVerifications();
//						mapWebVerification(uloWebVerification, iibWebVerification);
						//iibVerificationResult.getWebVerifications(iibWebVerification);
//					}
//				}
			}		
		}else if(uloObject instanceof ApplicationDataM){			
			ApplicationDataM  uloApplication = (ApplicationDataM)uloObject;
			VerificationResultDataM uloVerification =uloApplication.getVerificationResult();
			if(null!=uloVerification){
				mapApplicationVerificationResult(uloVerification, iibVerificationResult);
				policyRulesMapper(uloVerification.getPolicyRules(), iibVerificationResult);
				
			}
		}
	}
	public void  policyRulesMapper(ArrayList<PolicyRulesDataM> uloPolicyRulesList,VerificationResultApplications iibVerificationResult) {
		if(null!=uloPolicyRulesList && uloPolicyRulesList.size()>0){
			ArrayList<String> policyCodes = new ArrayList<String>();
			for(PolicyRulesDataM uloPolicyRules : uloPolicyRulesList){
				String policyCode = uloPolicyRules.getPolicyCode();
				PolicyRules iibPolicyRule = new PolicyRules();
				if(!policyCodes.contains(policyCode)){
					iibPolicyRule=ProcessUtil.getDecisionServicePolicyRules(iibVerificationResult.getPolicyrules(), policyCode);	
					if(null==iibPolicyRule){
						iibPolicyRule = new PolicyRules();
						iibVerificationResult.addPolicyrulesItem(iibPolicyRule);
						
					}
				}else{
					iibVerificationResult.addPolicyrulesItem(iibPolicyRule);
				}						
				mapPolicyRules(uloPolicyRules, iibPolicyRule);
				if(null!=uloPolicyRules.getOrPolicyRules()){
				   for(ORPolicyRulesDataM uloORPolicyRules : uloPolicyRules.getOrPolicyRules()){
					   OrPolicyRules iibOrPolicyRules = new OrPolicyRules();						
					   mapORPolicyRules(uloORPolicyRules, iibOrPolicyRules);
					   iibPolicyRule.addOrPolicyRulesItem(iibOrPolicyRules);
				   }
			   	}
				if(null!=uloPolicyRules.getPolicyRulesConditions()){
					for(PolicyRulesConditionDataM uloPolicyRuleCondition : uloPolicyRules.getPolicyRulesConditions()){
					   PolicyRuleConditions iibPolicyRuleCondition = new PolicyRuleConditions();
					   mapPolicyRulesCondition(uloPolicyRuleCondition, iibPolicyRuleCondition);
					   iibPolicyRule.addPolicyRuleConditionsItem(iibPolicyRuleCondition);
				   }
				}
				if(null!=uloPolicyRules.getReason()){
					PolicyRuleReasons iibPolicyRuleReason = new PolicyRuleReasons();
					iibPolicyRuleReason.setReasonCode(uloPolicyRules.getReason());
					iibPolicyRuleReason.setReasonRank(String.valueOf(uloPolicyRules.getRank()));
					iibPolicyRule.addPolicyRuleReasonsItem(iibPolicyRuleReason);
				}
//				 add for check existing policy
				policyCodes.add(policyCode);
				 
			}
		}
	}
	
	
	public  void mapApplicationGroup(String decisionPoint,ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup){
		iibApplicationGroup.setApplicationDate(DecisionServiceUtil.dateToDateTime(uloApplicationGroup.getApplicationDate()));
		iibApplicationGroup.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
		iibApplicationGroup.setApplicationGroupNo(uloApplicationGroup.getApplicationGroupNo());
		iibApplicationGroup.setApplicationTemplate(uloApplicationGroup.getApplicationTemplate());
		iibApplicationGroup.setApplyChannel(uloApplicationGroup.getApplyChannel());
		iibApplicationGroup.setApplyDate(DecisionServiceUtil.dateToDateTime(uloApplicationGroup.getApplyDate()));
		iibApplicationGroup.setFraudApplicantFlag(uloApplicationGroup.getFraudApplicantFlag());
		iibApplicationGroup.setFraudCompanyFlag(uloApplicationGroup.getFraudCompanyFlag());
		iibApplicationGroup.setNumberOfSubmission(DecisionServiceUtil.intToBigDecimal(uloApplicationGroup.getMaxLifeCycle()));
		iibApplicationGroup.setPolicyExSignOffBy(uloApplicationGroup.getPolicyExSignOffBy());
		iibApplicationGroup.setPolicyExSignOffDate(DecisionServiceUtil.dateToDateTime(uloApplicationGroup.getPolicyExSignOffDate()));
		iibApplicationGroup.setRandomNo1(DecisionServiceUtil.intToBigDecimal(uloApplicationGroup.getRandomNo1()));
		iibApplicationGroup.setRandomNo2(DecisionServiceUtil.intToBigDecimal(uloApplicationGroup.getRandomNo2()));
		iibApplicationGroup.setRandomNo3(DecisionServiceUtil.intToBigDecimal(uloApplicationGroup.getRandomNo3()));
		iibApplicationGroup.setCallAction(decisionPoint);	
		iibApplicationGroup.setSystemDate(DecisionServiceUtil.getSystemdateTime());
		iibApplicationGroup.setProcessAction(ProcessUtil.getProcessActionApplicationGroup(uloApplicationGroup));
		iibApplicationGroup.setVetoFlag("");
		iibApplicationGroup.setExecuteAction("");
		iibApplicationGroup.setFullFraudFlag(uloApplicationGroup.getFullFraudFlag());
		String vetoFlag=DecisionServiceUtil.FLAG_N;
		if(uloApplicationGroup.getMaxLifeCycle()>1){
			vetoFlag= DecisionServiceUtil.FLAG_Y;
		}
		iibApplicationGroup.setVetoFlag(vetoFlag);
		//iibApplicationGroup.setDocumentSLAType(uloApplicationGroup.getDocumentSLAType());
	}
	
	
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication, Applications  iibApplication ){
		PersonalInfoDataM uloPersonalApplication = uloApplicationGroup.getPersonalInfoByRelation(uloApplication.getApplicationRecordId());
		if(null==uloPersonalApplication){
			uloPersonalApplication = ProcessUtil.getApplicationTypePersonalInfo(uloApplicationGroup);
		}
		
		iibApplication.setPersonalId(null!=uloPersonalApplication?uloPersonalApplication.getPersonalId():"");
		iibApplication.setApplicationRecordId(uloApplication.getApplicationRecordId());
		iibApplication.setApplyType(uloApplication.getApplicationType());
		iibApplication.setIsVetoEligibleFlag(uloApplication.getIsVetoEligibleFlag());
		iibApplication.setOrgId(uloApplication.getProduct());
		iibApplication.setPolicyProgramId(uloApplication.getPolicyProgramId());
		iibApplication.setRecommendProjectCode(uloApplication.getProjectCode());
		iibApplication.setErrorMessage(null);
		iibApplication.setSubProduct(null);
		
		
		logger.debug("uloApplication Seq >>"+uloApplication.getSeq());
		//change logic for eapp case reject from eapp
		/*
		 * 2019-07-30 change log set recommend decision use old logic for incident diff request
		 * new logic because recommend decision from e app in case reject will send RJ
		 * then odm can not use this for decision
		 */
		String productStatus =ProcessUtil.getDecisionServiceProductStatus(uloApplication.getPreviousRecommendDecision());
		if(null == productStatus || "".equals(productStatus)){
			productStatus =ProcessUtil.getDecisionServiceProductStatus(uloApplication.getRecommendDecision());
		}
//		String productStatus =ProcessUtil.getDecisionServiceProductStatus(uloApplication.getRecommendDecision());
		logger.debug("productStatus>>"+productStatus);
		iibApplication.setPreviousProductStatus(productStatus);
		iibApplication.setRecommendDecision(productStatus);
		String[] referCriterias =ProcessUtil.selectReferCriterias(uloApplication.getReferCriteria());
		if(null!=referCriterias){
			for(String referCriteria : referCriterias){
				ReferCriterias iibReferCririaDataM = new ReferCriterias();
				iibReferCririaDataM.setName(referCriteria);		
			}				
		}
		iibApplication.setSpSignoffDate(DecisionServiceUtil.dateToDateTime(uloApplication.getSpSignoffDate()));
		iibApplication.setMainApplicationRecordId(uloApplication.getMaincardRecordId());
		
			
		
		LoanDataM uloLoan = uloApplication.getLoan();
		if(null!=uloLoan){
			if(null!=uloLoan.getCard()){			
				iibApplication.setSubProduct(ProcessUtil.getSubProduct(uloApplication.getProduct(), uloLoan.getCard().getCardType()));
			}
		}
		iibApplication.setReExecuteKeyProgramFlag(uloApplication.getReExecuteKeyProgramFlag());
		
//		KPL Additional
		iibApplication.setBlockFlag(uloApplication.getSavingPlusFlag());
		iibApplication.setApplyTypeFlag(uloApplication.getApplyTypeFlag());
	}
	
	public void mapBilingCycle(ApplicationGroupDataM uloApplicationGroup, LoanDataM uloLoan,Applications iibApplication){
		PaymentMethodDataM uloPaymentMenthod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
		if(null!=uloPaymentMenthod && null!=uloPaymentMenthod.getDueCycle())
			iibApplication.setBillingCycle(uloPaymentMenthod.getDueCycle());
	}
	
	public  void mapPaymentMethod(PaymentMethodDataM uloPaymentMethod,PaymentMethods iibPaymentMethod){
		iibPaymentMethod.setAccountName(uloPaymentMethod.getAccountName());		
		iibPaymentMethod.setAccountNo(uloPaymentMethod.getAccountNo());
		iibPaymentMethod.setAccountType(uloPaymentMethod.getAccountType());
		iibPaymentMethod.setBankCode(uloPaymentMethod.getBankCode());
		iibPaymentMethod.setDueCycle(uloPaymentMethod.getDueCycle());
		logger.debug("mapPaymentMethod uloPaymentMethod>> "+uloPaymentMethod);
		iibPaymentMethod.setPaymentMethod(uloPaymentMethod.getPaymentMethod());
		iibPaymentMethod.setPaymentRatio(uloPaymentMethod.getPaymentRatio());
	}
	
	public  void mapPersonalInfo(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo){
		iibPersonalInfo.setWfVerfiedFlag(ProcessUtil.getIsInformationComplete(uloPersonalInfo.getPersonalId(), uloApplicationGroup) ? DecisionServiceUtil.FLAG_N : DecisionServiceUtil.FLAG_Y);
		
		if(ProcessUtil.PERSONAL_TYPE.INFOMATION.equals(uloPersonalInfo.getPersonalType())){
			iibPersonalInfo.setPersonalType(ProcessUtil.PERSONAL_TYPE.APPLICANT);
			logger.debug("set personal type =A");
		}else{
			iibPersonalInfo.setPersonalType(uloPersonalInfo.getPersonalType());
		}

		iibPersonalInfo.setAssetsAmount(uloPersonalInfo.getAssetsAmount());
		iibPersonalInfo.setBirthDate(DecisionServiceUtil.dateToDateTime(uloPersonalInfo.getBirthDate()));
		iibPersonalInfo.setBranchReceiveCard(uloPersonalInfo.getBranchReceiveCard());
		iibPersonalInfo.setBundleWithMainFlag(ProcessUtil.getBundleWithMainFlag(uloPersonalInfo));
		iibPersonalInfo.setRequireBureau(uloPersonalInfo.getBureauRequiredFlag());
		iibPersonalInfo.setBusinessType(uloPersonalInfo.getBusinessType());
		iibPersonalInfo.setCidType(uloPersonalInfo.getCidType());
		iibPersonalInfo.setCisNo(uloPersonalInfo.getCisNo());
		iibPersonalInfo.setCisPrimSegment(uloPersonalInfo.getCisPrimSegment());
		iibPersonalInfo.setCisPrimSubSegment(uloPersonalInfo.getCisPrimSubSegment());
		iibPersonalInfo.setCisSecSegment(uloPersonalInfo.getCisSecSegment());
		iibPersonalInfo.setCisSecSubSegment(uloPersonalInfo.getCisSecSubSegment());
//		iibPersonalInfo.setCompanyCisId(uloPersonalInfo.getCompanyCISId());
		iibPersonalInfo.setCustomerType(ProcessUtil.CUSTOMER_TYPE.INDIVIDUAL);
		iibPersonalInfo.setDegree(uloPersonalInfo.getDegree());
//		iibPersonalInfo.setDisclosureFlag(uloPersonalInfo.getDisclosureFlag());
//		iibPersonalInfo.setEmploymentType(uloPersonalInfo.getEmploymentType());
		iibPersonalInfo.setEnFirstName(uloPersonalInfo.getEnFirstName());
		iibPersonalInfo.setEnLastName(uloPersonalInfo.getEnLastName());
		iibPersonalInfo.setEnMidName(uloPersonalInfo.getEnMidName());
		iibPersonalInfo.setEnTitleCode(uloPersonalInfo.getEnTitleCode());
//		iibPersonalInfo.setFreelanceIncome(uloPersonalInfo.getFreelanceIncome());
//		iibPersonalInfo.setFreelanceType(uloPersonalInfo.getFreelanceType());
		iibPersonalInfo.setGender(uloPersonalInfo.getGender());
		iibPersonalInfo.setIdNo(uloPersonalInfo.getIdno());
		iibPersonalInfo.setMailingAddress(uloPersonalInfo.getMailingAddress());
		iibPersonalInfo.setMarried(uloPersonalInfo.getMarried());
//		iibPersonalInfo.setMonthlyExpense(uloPersonalInfo.getMonthlyExpense());
//		iibPersonalInfo.setMonthlyIncome(uloPersonalInfo.getMonthlyIncome());
		iibPersonalInfo.setNationality(uloPersonalInfo.getNationality());
//		iibPersonalInfo.setNetProfitIncome(uloPersonalInfo.getNetProfitIncome());
		iibPersonalInfo.setNumberOfChild(uloPersonalInfo.getNoOfChild());
		iibPersonalInfo.setPersonalId(uloPersonalInfo.getPersonalId());
		iibPersonalInfo.setPreviousCompanyName(uloPersonalInfo.getPrevCompany());
		iibPersonalInfo.setPreviousCompanyTitle(uloPersonalInfo.getPrevCompanyTitle());
		iibPersonalInfo.setPreviousWorkMonth(uloPersonalInfo.getPrevWorkMonth());
		iibPersonalInfo.setPreviousWorkYear(uloPersonalInfo.getPrevWorkYear());
		iibPersonalInfo.setProfession(uloPersonalInfo.getProfession());
		iibPersonalInfo.setReceiveIncomeBank(uloPersonalInfo.getReceiveIncomeBank());
		iibPersonalInfo.setRelationshipType(uloPersonalInfo.getRelationshipType());
//		iibPersonalInfo.setSalary(uloPersonalInfo.getSalary());
//		iibPersonalInfo.setSorceOfIncomeOther(uloPersonalInfo.getSorceOfIncomeOther());
//		iibPersonalInfo.setSrcOthIncBonus(DecisionServiceUtil.stringToBigDecimal(uloPersonalInfo.getSrcOthIncBonus()));
//		iibPersonalInfo.setSrcOthIncCommission(DecisionServiceUtil.stringToBigDecimal(uloPersonalInfo.getSrcOthIncCommission()));
		iibPersonalInfo.setThFirstName(uloPersonalInfo.getThFirstName());
		iibPersonalInfo.setThLastName(uloPersonalInfo.getThLastName());
		iibPersonalInfo.setThMidName(uloPersonalInfo.getThMidName());
		iibPersonalInfo.setThTitleCode(uloPersonalInfo.getThTitleCode());
		iibPersonalInfo.setTotalWorkMonth(uloPersonalInfo.getTotWorkMonth());
		iibPersonalInfo.setTotalWorkYear(uloPersonalInfo.getTotWorkYear());
		iibPersonalInfo.setTypeofFin(uloPersonalInfo.getTypeOfFin());
		iibPersonalInfo.setVatRegisterFlag(uloPersonalInfo.getVatRegistFlag());
		if(ProcessUtil.CIDTYPE_PASSPORT.equals(uloPersonalInfo.getCidType())){
			iibPersonalInfo.setPassportExpiryDate(DecisionServiceUtil.dateToDateTime(uloPersonalInfo.getCidExpDate()));
		}
		iibPersonalInfo.setVisaExpiryDate(DecisionServiceUtil.dateToDateTime(uloPersonalInfo.getVisaExpDate()));
		iibPersonalInfo.setVisaType(uloPersonalInfo.getVisaType());
		iibPersonalInfo.setWorkPermitExpiryDate(DecisionServiceUtil.dateToDateTime(uloPersonalInfo.getWorkPermitExpDate()));
		iibPersonalInfo.setWorkPermitNo(uloPersonalInfo.getWorkPermitNo());
		iibPersonalInfo.setPositionType(ProcessUtil.getPositionType(uloPersonalInfo.getPositionCode()));
		iibPersonalInfo.setPositionLevel(uloPersonalInfo.getPositionLevel());
		iibPersonalInfo.setSalaryLevel(ProcessUtil.getSalaryLevel(uloPersonalInfo.getPositionCode()));
		iibPersonalInfo.setOccupation(uloPersonalInfo.getOccupation());
		iibPersonalInfo.setTotVerifiedIncome(uloPersonalInfo.getTotVerifiedIncome());
		
		// TODO: Map infoFlag
//		iibPersonalInfo.setInfoFlag(??????)
		iibPersonalInfo.setCisPrimDesc(uloPersonalInfo.getCisPrimDesc());
		iibPersonalInfo.setCisPrimSubDesc(uloPersonalInfo.getCisPrimSubDesc());
		iibPersonalInfo.setCisSecDesc(uloPersonalInfo.getCisSecDesc());
		iibPersonalInfo.setCisSecSubDesc(uloPersonalInfo.getCisSecSubDesc());
		iibPersonalInfo.setLineId(uloPersonalInfo.getLineId());
		iibPersonalInfo.setIncomeResource(uloPersonalInfo.getIncomeResource());
		iibPersonalInfo.setIncomeResourceOther(uloPersonalInfo.getIncomeResourceOther());
		iibPersonalInfo.setCountryOfIncome(uloPersonalInfo.getCountryOfIncome());
		
//		KPL Additional
		iibPersonalInfo.setSpecialMerchant(uloPersonalInfo.getSpecialMerchantType());
		iibPersonalInfo.setCompanyType(uloPersonalInfo.getSpecialMerchantType());
		
		iibPersonalInfo.setNcbTranNo(uloPersonalInfo.getNcbTranNo());
		iibPersonalInfo.setConsentRefNo(uloPersonalInfo.getConsentRefNo());
		
		iibPersonalInfo.setOtherIncome(uloPersonalInfo.getOtherIncome());
		iibPersonalInfo.setIncome(uloPersonalInfo.getSalary());
		
		List<DocumentSlaTypes> docSlAs = DecisionServiceUtil.getEDocumentSLAType(uloApplicationGroup, uloPersonalInfo);
		if(null!=docSlAs){
			for(DocumentSlaTypes docSLA : docSlAs) {
//				iibPersonalInfo.getDocumentSlaTypes().addAll(docSlAs);		
				iibPersonalInfo.addDocumentSlaTypesItem(docSLA);
			}
		}else{
			logger.debug("DOCUMENT SLA IS NULL");
		}
 	
	}
	
	public  void mapAddress(AddressDataM uloAddress,Addresses iibAddress){
		iibAddress.setAddress(uloAddress.getAddress());
		iibAddress.setAddressType(uloAddress.getAddressType());
		iibAddress.setAdrsts(uloAddress.getAdrsts());
		iibAddress.setAmphur(uloAddress.getAmphur());
		iibAddress.setBuildingType(uloAddress.getBuildingType());
		iibAddress.setBuilding(uloAddress.getBuilding());
		iibAddress.setCompanyName(uloAddress.getCompanyName());
		iibAddress.setCompanyTitle(uloAddress.getCompanyTitle());
		iibAddress.setCountry(uloAddress.getCountry());
		iibAddress.setCompanyTitle(uloAddress.getCompanyTitle());
		iibAddress.setDepartment(uloAddress.getDepartment());
		iibAddress.setExt1(uloAddress.getExt1());
		iibAddress.setFax(uloAddress.getFax());
		iibAddress.setFloor(uloAddress.getFloor());
		iibAddress.setMobile(uloAddress.getMobile());
		iibAddress.setMoo(uloAddress.getMoo());
		iibAddress.setPhone1(uloAddress.getPhone1());
		iibAddress.setProvince(DecisionServiceUtil.bigDecimalToString(uloAddress.getProvince()));
		iibAddress.setRents(uloAddress.getRents());
		iibAddress.setRoad(uloAddress.getRoad());
		iibAddress.setRoom(uloAddress.getRoom());
		iibAddress.setSeq(DecisionServiceUtil.intToBigDecimal(uloAddress.getSeq()));
		iibAddress.setSoi(uloAddress.getSoi());
		iibAddress.setState(uloAddress.getState());
		iibAddress.setTambol(uloAddress.getTambol());
		iibAddress.setVatCode(uloAddress.getVatCode());
		iibAddress.setVatRegistration(uloAddress.getVatRegistration());
		iibAddress.setVilapt(uloAddress.getVilapt());
		iibAddress.setZipcode(uloAddress.getZipcode());
		iibAddress.setResideMonth(uloAddress.getResidem());
		iibAddress.setResideYear(uloAddress.getResidey());
		iibAddress.setResidenceProvince(uloAddress.getResidenceProvince());
		
	}
	
	public  void mapDebtInfo(ArrayList<DebtInfoDataM> uloDebtInfos,DebtInfo iibDebtInfo){
		DebtInfoDataM  uloWelfareDebtInfo =ProcessUtil.filterDebtInfo(uloDebtInfos,ProcessUtil.DEBT_TYPE.WELFARE);
		DebtInfoDataM  uloCommercialDebtInfo =ProcessUtil.filterDebtInfo(uloDebtInfos,ProcessUtil.DEBT_TYPE.COMMERCIAL);
		DebtInfoDataM  uloOtherDebtInfo =ProcessUtil.filterDebtInfo(uloDebtInfos,ProcessUtil.DEBT_TYPE.OTHER);
		
		if(null!=uloWelfareDebtInfo){
			iibDebtInfo.setWelfareAmount(uloWelfareDebtInfo.getDebtAmt());
		}
		if(null!=uloCommercialDebtInfo){
			iibDebtInfo.setDebtCommercialAmount(uloCommercialDebtInfo.getDebtAmt());
		}
		if(null!=uloOtherDebtInfo){
			iibDebtInfo.setOtherDebtAmount(uloOtherDebtInfo.getDebtAmt());
		}
		iibDebtInfo.setTotalOtherDebtAmount(ProcessUtil.totalOtherDebtAmount(iibDebtInfo));
	}
	
	public  void mapIncomeInfo(IncomeInfoDataM uloIncomeInfo,IncomeInfos iibIncomeInfo){
		iibIncomeInfo.setIncomeType(uloIncomeInfo.getIncomeType());
	}
	
	public  void mapBankStatement( BankStatementDataM uloBankStatement, BankStatements iibBankStatement){
		iibBankStatement.setAdditionalCode(uloBankStatement.getAdditionalCode());
		iibBankStatement.setBankCode(uloBankStatement.getBankCode());
		iibBankStatement.setStatementCode(uloBankStatement.getStatementCode());		
	}
	
	public  void mapBankStatementDetail( BankStatementDetailDataM uloBankStatementDetail,BankStatementDetails iibBankStatementDetail  ){
		iibBankStatementDetail.setAmount(uloBankStatementDetail.getAmount());
		iibBankStatementDetail.setMonth(uloBankStatementDetail.getMonth());
		iibBankStatementDetail.setSeq(DecisionServiceUtil.intToBigDecimal(uloBankStatementDetail.getSeq()));
		iibBankStatementDetail.setYear(uloBankStatementDetail.getYear());
	}
	
	public  void mapClosedEndFund(ClosedEndFundDataM uloCloseEndFund, CloseEndFounds iibCloseEndFund){
		iibCloseEndFund.setAccountBalance(uloCloseEndFund.getAccountBalance());
		iibCloseEndFund.setAccountName(uloCloseEndFund.getAccountName());
		iibCloseEndFund.setAccountNo(uloCloseEndFund.getAccountNo());
		iibCloseEndFund.setBankCode(uloCloseEndFund.getBankCode());
		iibCloseEndFund.setFundName(uloCloseEndFund.getFundName());
		iibCloseEndFund.setHoldingRatio(uloCloseEndFund.getHoldingRatio());
		iibCloseEndFund.setOpenDate(DecisionServiceUtil.dateToDateTime(uloCloseEndFund.getOpenDate()));
	}
	
	public  void mapFinancialInstrument(FinancialInstrumentDataM uloFinancialInst ,FinancialInstruments iibFinalcialInst  ){
		iibFinalcialInst.setCurrentBalance(uloFinancialInst.getCurrentBalance());
		iibFinalcialInst.setExpireDate(DecisionServiceUtil.dateToDateTime(uloFinancialInst.getExpireDate()));
		iibFinalcialInst.setFinInstrument(uloFinancialInst.getFinInstrument());
		iibFinalcialInst.setHolderName(uloFinancialInst.getHolderName());
		iibFinalcialInst.setHoldingRatio(uloFinancialInst.getHoldingRatio());
		iibFinalcialInst.setInstrumentType(uloFinancialInst.getInstrumentType());
		iibFinalcialInst.setIssuerName(uloFinancialInst.getIssuerName());
		iibFinalcialInst.setOpenDate(DecisionServiceUtil.dateToDateTime(uloFinancialInst.getOpenDate()));
	}
	
	public  void mapFixedAccount(FixedAccountDataM uloFixedAccount, FixedAccounts iibFixedAccount){
		iibFixedAccount.setAccountBalance(uloFixedAccount.getAccountBalance());
		iibFixedAccount.setAccountName(uloFixedAccount.getAccountName());
		iibFixedAccount.setAccountNo(uloFixedAccount.getAccountNo());
		iibFixedAccount.setBankCode(uloFixedAccount.getBankCode());
		iibFixedAccount.setHoldingRatio(uloFixedAccount.getHoldingRatio());
		iibFixedAccount.setOpenDate(DecisionServiceUtil.dateToDateTime(uloFixedAccount.getOpenDate()));
	}
	
	public  void mapKviIncome(KVIIncomeDataM uloKVIincome, KviIncome iibKVIIncome){
		iibKVIIncome.setPercentChequeReturn(uloKVIincome.getPercentChequeReturn());
		iibKVIIncome.setVerifiedIncome(uloKVIincome.getVerifiedIncome());
	}
	
	public  void mapMonthlyTawi50(MonthlyTawi50DataM uloMonthlyTawi50 ,MonthlyTawi50s iibMonthlyTawi50){
		iibMonthlyTawi50.setCompanyName(uloMonthlyTawi50.getCompanyName());
		iibMonthlyTawi50.setTawiIncomeType(uloMonthlyTawi50.getTawiIncomeType());	
	}
	
	public  void mapMonthlyTawi50Detail(MonthlyTawi50DetailDataM uloMonthlyTawi50Detail ,MonthlyTaxi50Details  iibMonthlyTawi50Detail){
		iibMonthlyTawi50Detail.setAmount(uloMonthlyTawi50Detail.getAmount());
		iibMonthlyTawi50Detail.setMonth(DecisionServiceUtil.stringToBigDecimal(uloMonthlyTawi50Detail.getMonth()));
		iibMonthlyTawi50Detail.setSeq(DecisionServiceUtil.intToBigDecimal(uloMonthlyTawi50Detail.getSeq()));
		iibMonthlyTawi50Detail.setYear(DecisionServiceUtil.stringToBigDecimal(uloMonthlyTawi50Detail.getYear()));
	}
	
	public  void mapOpenedEndFund(OpenedEndFundDataM uloOpenedEndFund,OpendEndFounds iibOpenedEndFund){
		iibOpenedEndFund.setAccountName(uloOpenedEndFund.getAccountName());
		iibOpenedEndFund.setAccountNo(uloOpenedEndFund.getAccountNo());
		iibOpenedEndFund.setBankCode(uloOpenedEndFund.getBankCode());
		iibOpenedEndFund.setFundName(uloOpenedEndFund.getFundName());
		iibOpenedEndFund.setHoldingRatio(uloOpenedEndFund.getHoldingRatio());
		iibOpenedEndFund.setOpenDate(DecisionServiceUtil.dateToDateTime(uloOpenedEndFund.getOpenDate()));
	}
	
	public  void mapOpenedEndFundDetail(OpenedEndFundDetailDataM uloOpenedEndFundDetail,OpenEndFundDetails iibOpenedEndFundDetail){
		iibOpenedEndFundDetail.setAmount(uloOpenedEndFundDetail.getAmount());
		iibOpenedEndFundDetail.setMonth(DecisionServiceUtil.stringToBigDecimal(uloOpenedEndFundDetail.getMonth()));
		iibOpenedEndFundDetail.setYear(DecisionServiceUtil.stringToBigDecimal(uloOpenedEndFundDetail.getYear()));
	}
	
	public  void mapPayroll(PayrollDataM uloPayroll , PayRolls iibPayroll){
		iibPayroll.setIncome(uloPayroll.getIncome());
		iibPayroll.setNoOfEmployee(DecisionServiceUtil.intToBigDecimal(uloPayroll.getNoOfEmployee()));
	}
	
	public  void mapPayslip(PayslipDataM uloPaySlip ,Payslips iibPaySlip){
		iibPaySlip.setBonus(uloPaySlip.getBonus());
		iibPaySlip.setBonusMonth(uloPaySlip.getBonusMonth());
		iibPaySlip.setBonusYear(uloPaySlip.getBonusYear());
		iibPaySlip.setNoMonth(DecisionServiceUtil.intToBigDecimal(uloPaySlip.getNoMonth()));
		iibPaySlip.setSalaryDate(DecisionServiceUtil.dateToDateTime(uloPaySlip.getSalaryDate()));
		iibPaySlip.setSpacialPension(uloPaySlip.getSpacialPension());
		iibPaySlip.setSumIncome(uloPaySlip.getSumIncome());
		iibPaySlip.setSumOthIncome(uloPaySlip.getSumOthIncome());
		iibPaySlip.setSumSso(uloPaySlip.getSumSso());
	}
	
	public  void mapPayslipMonthly(PayslipMonthlyDataM uloPaySlipMonthly ,PayslipMonthlys iibPaySlipMonthly){
		iibPaySlipMonthly.setIncomeType(uloPaySlipMonthly.getIncomeType());
		iibPaySlipMonthly.setSeq(DecisionServiceUtil.intToBigDecimal(uloPaySlipMonthly.getSeq()));
		iibPaySlipMonthly.setIncomeDesc(uloPaySlipMonthly.getIncomeDesc());
		iibPaySlipMonthly.setIncomeOthDesc(uloPaySlipMonthly.getIncomeOthDesc());
		iibPaySlipMonthly.setIncomeType(uloPaySlipMonthly.getIncomeType());
		iibPaySlipMonthly.setPayslipId(uloPaySlipMonthly.getPayslipId());
		iibPaySlipMonthly.setPayslipMonthlyId(uloPaySlipMonthly.getPayslipMonthlyId());
		
		if(null!=uloPaySlipMonthly.getPayslipMonthlyDetails() && uloPaySlipMonthly.getPayslipMonthlyDetails().size()>0){
			for(PayslipMonthlyDetailDataM uloPaySlipMonthlyDetail : uloPaySlipMonthly.getPayslipMonthlyDetails()){
				PayslipMonthlyDetails iibPaySlipMonthlyDetail = new PayslipMonthlyDetails();
				mapPayslipMonthlyDetail(uloPaySlipMonthlyDetail, iibPaySlipMonthlyDetail);
				iibPaySlipMonthly.addPayslipMonthlyDetailsItem(iibPaySlipMonthlyDetail);				
			}		
		}
	}
	
	public  void mapPayslipMonthlyDetail(PayslipMonthlyDetailDataM uloPaySlipMonthlyDetail ,PayslipMonthlyDetails iibPaySlipMonthlyDetail){
		iibPaySlipMonthlyDetail.setAmount(uloPaySlipMonthlyDetail.getAmount());
		iibPaySlipMonthlyDetail.setMonth(uloPaySlipMonthlyDetail.getMonth());
		iibPaySlipMonthlyDetail.setPayslipMonthlyDetailId(EAppUtil.stringToBigDecimal(uloPaySlipMonthlyDetail.getPayslipMonthlyDetailId()));
		iibPaySlipMonthlyDetail.setPayslipMonthlyId(EAppUtil.stringToBigDecimal(uloPaySlipMonthlyDetail.getPayslipMonthlyId()));
		iibPaySlipMonthlyDetail.setSeq(EAppUtil.intToBigDecimal(uloPaySlipMonthlyDetail.getSeq()));
		iibPaySlipMonthlyDetail.setYear(EAppUtil.stringToBigDecimal(uloPaySlipMonthlyDetail.getYear()));
	}
	
	public  void mapPreviousIncome(PreviousIncomeDataM uloPreviosIncome ,PreviousIncomes iibPreviousIncome){
		iibPreviousIncome.setIncome(uloPreviosIncome.getIncome());
		iibPreviousIncome.setIncomeDate(DecisionServiceUtil.dateToDateTime(uloPreviosIncome.getIncomeDate()));
		iibPreviousIncome.setProduct(uloPreviosIncome.getProduct());
	}
	
	public  void mapSalaryCert(SalaryCertDataM uloSalaryCert ,SalaryCerts iibSalaryCert){
		iibSalaryCert.setCompanyName(uloSalaryCert.getCompanyName());
		iibSalaryCert.setIncome(uloSalaryCert.getIncome());
		iibSalaryCert.setOtherIncome(uloSalaryCert.getOtherIncome());
		iibSalaryCert.setTotalIncome(uloSalaryCert.getTotalIncome());
		
	}
	
	public  void mapSavingAccount(SavingAccountDataM uloSavingAccount ,SavingAccounts iibSavingAccount){
		iibSavingAccount.setAccountBalance(uloSavingAccount.getAccountBalance());
		iibSavingAccount.setAccountName(uloSavingAccount.getAccountName());
		iibSavingAccount.setAccountNo(uloSavingAccount.getAccountNo());
		iibSavingAccount.setBankCode(uloSavingAccount.getBankCode());
		iibSavingAccount.setHoldingRatio(uloSavingAccount.getHoldingRatio());
		iibSavingAccount.setOpenDate(DecisionServiceUtil.dateToDateTime(uloSavingAccount.getOpenDate()));
	}
	
	public  void mapSavingAccountDetail(SavingAccountDetailDataM uloSavingAccountDetail ,SavingAccountDetails iibSavingAccountDetail){
		iibSavingAccountDetail.setAmount(uloSavingAccountDetail.getAmount());
		iibSavingAccountDetail.setMonth(uloSavingAccountDetail.getMonth());
		iibSavingAccountDetail.setYear(uloSavingAccountDetail.getYear());
	}
	
	public  void mapTaweesapDataM(TaweesapDataM uloTaweesap ,Taweesaps iibTaweesap){
		iibTaweesap.setAum(uloTaweesap.getAum());
	}
	
	public  void mapYearlyTawi50(YearlyTawi50DataM uloYearlyTawi50,YearlyTawi50s iibYearlyTawi50){
		iibYearlyTawi50.setCompanyName(uloYearlyTawi50.getCompanyName());
		iibYearlyTawi50.setIncome401(uloYearlyTawi50.getIncome401());
		iibYearlyTawi50.setIncome402(uloYearlyTawi50.getIncome402());
		iibYearlyTawi50.setMonth(DecisionServiceUtil.intToString(uloYearlyTawi50.getMonth()));
		iibYearlyTawi50.setSumSso(uloYearlyTawi50.getSumSso());
		iibYearlyTawi50.setYear(DecisionServiceUtil.intToString(uloYearlyTawi50.getYear()));
	}
	
	public  void mapIncomeSource(IncomeSourceDataM uloIncomeSource,IncomeSources iibIncomeSource){
		iibIncomeSource.setIncomeSource(uloIncomeSource.getIncomeSource());
	}
	
	public  void mapNcbInfo(NcbInfoDataM uloNcbInfo,NcbInfo iibNcbInfo){
		iibNcbInfo.setConsentRefNo(uloNcbInfo.getConsentRefNo());
		iibNcbInfo.setNumTimesOfEnquiry(DecisionServiceUtil.longToBigDecimal(uloNcbInfo.getNoOfTimesEnquiry()));
		iibNcbInfo.setNumTimesOfEnquiry6Month(DecisionServiceUtil.longToBigDecimal(uloNcbInfo.getNoOfTimesEnquiry6M()));
		iibNcbInfo.setNumberOfPersonalLoanUnderBOTIssuer(uloNcbInfo.getPersonalLoanUnderBotIssuer());
	}
	public void mapKcbsReason(NcbInfoDataM uloNcbInfo,KcbsResponse iibKcbsReason){
		iibKcbsReason.setConsentRefNo(uloNcbInfo.getConsentRefNo());
	}
	
	public  void mapFixedGuarantee(FixedGuaranteeDataM uloFixedGuarantee,FixedGuarantees iibFixedGuarantee){
		iibFixedGuarantee.setAccountName(uloFixedGuarantee.getAccountName());
		iibFixedGuarantee.setAccountNo(uloFixedGuarantee.getAccountNo());
		iibFixedGuarantee.setBranchNo(uloFixedGuarantee.getBranchNo());
		iibFixedGuarantee.setRetentionAmt(uloFixedGuarantee.getRetentionAmt());
		iibFixedGuarantee.setRetentionDate(DecisionServiceUtil.dateToDateTime(uloFixedGuarantee.getRetentionDate()));
		iibFixedGuarantee.setRetentionType(uloFixedGuarantee.getRetentionType());
		iibFixedGuarantee.setSub(uloFixedGuarantee.getSub());
	}
	
	public  void mapReferencePerson(ReferencePersonDataM uloRefPersonal,ReferencePersons iibRefPersonal){
		iibRefPersonal.setOfficePhone(uloRefPersonal.getOfficePhone());
		iibRefPersonal.setPhone1(uloRefPersonal.getPhone1());
		iibRefPersonal.setRelation(uloRefPersonal.getRelation());
		iibRefPersonal.setThFirstName(uloRefPersonal.getThFirstName());
		iibRefPersonal.setThLastName(uloRefPersonal.getThLastName());
		iibRefPersonal.setThTitleCode(uloRefPersonal.getThTitleCode());
	}
	
	public  void mapSaleInfo(SaleInfoDataM uloSaleInfo,SaleInfos iibSaleInfo){
		iibSaleInfo.setRegion(uloSaleInfo.getRegion());
//		iibSaleInfo.setSaleChannel(uloSaleInfo.getSaleChannel());
//		iibSaleInfo.setSalesBranchCode(uloSaleInfo.getSalesBranchCode());
//		iibSaleInfo.setSalesBranchName(uloSaleInfo.getSalesBranchName());
//		iibSaleInfo.setSalesComment(uloSaleInfo.getSalesComment());
//		iibSaleInfo.setSalesDeptName(uloSaleInfo.getSalesDeptName());
		iibSaleInfo.setSalesId(uloSaleInfo.getSalesId());
//		iibSaleInfo.setSalesInfoId(uloSaleInfo.getSalesInfoId());
		iibSaleInfo.setSalesName(uloSaleInfo.getSalesName());
		iibSaleInfo.setSalesName(uloSaleInfo.getSalesName());
//		iibSaleInfo.setSalesPhoneNo(uloSaleInfo.getSalesPhoneNo());
		iibSaleInfo.setSalesRCCode(uloSaleInfo.getSalesRCCode());
		iibSaleInfo.setSalesType(uloSaleInfo.getSalesType());
		iibSaleInfo.setZone(uloSaleInfo.getZone());
		iibSaleInfo.setSaleChannel(uloSaleInfo.getSaleChannel());
	}
	
	public  void mapAdditionalService(SpecialAdditionalServiceDataM uloAdditionalService,AdditionalServices iibAdditionalService){
		iibAdditionalService.setCurrentAccName(uloAdditionalService.getCurrentAccName());
		iibAdditionalService.setCurrentAccNo(uloAdditionalService.getCurrentAccNo());
		iibAdditionalService.setSavingAccName(uloAdditionalService.getSavingAccName());
		iibAdditionalService.setSavingAccNo(uloAdditionalService.getSavingAccNo());
		iibAdditionalService.setServiceType(uloAdditionalService.getServiceType());
	}
	
	public  void mapPersonalVerificationResult(VerificationResultDataM uloVerification,VerificationResultPersonalInfos iibVerResult){
		iibVerResult.setDocCompletedFlag(uloVerification.getDocCompletedFlag());
		iibVerResult.setRequiredVerCustFlag(uloVerification.getRequiredVerCustFlag());
		iibVerResult.setRequiredVerIncomeFlag(uloVerification.getRequiredVerIncomeFlag());
		iibVerResult.setRequiredVerPrivilegeFlag(uloVerification.getRequiredVerPrivilegeFlag());
		iibVerResult.setRequiredVerWebFlag(uloVerification.getRequiredVerWebFlag());
		iibVerResult.setSummaryIncomeResultCode(uloVerification.getSummaryIncomeResultCode());
	}
	
	public  void mapApplicationVerificationResult(VerificationResultDataM uloVerification,VerificationResultPersonalInfos iibVerResult){
		if(null!=uloVerification){
			
		}
	}
	public  void mapApplicationVerificationResult(VerificationResultDataM uloVerification,VerificationResultApplications iibVerResult){
		if(null!=uloVerification){
			
		}
	}
	public  void mapORPolicyRules(ORPolicyRulesDataM uloORPolicyRules,OrPolicyRules iibORPolicyRules){
		iibORPolicyRules.setMinApprovalAuth(uloORPolicyRules.getMinApprovalAuth());
		iibORPolicyRules.setOverideCode(uloORPolicyRules.getPolicyCode());
		iibORPolicyRules.setOverideResult(uloORPolicyRules.getVerifiedResult());
		
		if(null!=uloORPolicyRules.getOrPolicyRulesDetails() && uloORPolicyRules.getOrPolicyRulesDetails().size()>0){
			for(ORPolicyRulesDetailDataM uloORPolicyRulesDetail : uloORPolicyRules.getOrPolicyRulesDetails()){
				OrPolicyRulesDetails iibORPolicyRulesDetail = new OrPolicyRulesDetails();
				mapORPolicyRulesDetail(uloORPolicyRulesDetail, iibORPolicyRulesDetail);
				iibORPolicyRules.addOrPolicyRulesDetailsItem(iibORPolicyRulesDetail);
			}
		}
	}
	
	public void mapPolicyRulesCondition(PolicyRulesConditionDataM uloPolicyRuleCondition, PolicyRuleConditions iibPolicyRuleCondition){
		iibPolicyRuleCondition.setConditionCode(uloPolicyRuleCondition.getConditionCode());
	}
	
	public  void mapORPolicyRulesDetail(ORPolicyRulesDetailDataM uloORPolicyRulesDetail,OrPolicyRulesDetails iibORPolicyRulesDetail){
		iibORPolicyRulesDetail.setResult(uloORPolicyRulesDetail.getResult());
		iibORPolicyRulesDetail.setGuidelineCode(uloORPolicyRulesDetail.getGuidelineCode());
		iibORPolicyRulesDetail.setRank(DecisionServiceUtil.intToBigDecimal(uloORPolicyRulesDetail.getRank()));
		
		if(null!=uloORPolicyRulesDetail.getGuidelines() && uloORPolicyRulesDetail.getGuidelines().size()>0){
			for(GuidelineDataM uloGuideline : uloORPolicyRulesDetail.getGuidelines()){
				Guidelines iibGuideline = new Guidelines();
				mapGuideline(uloGuideline, iibGuideline);
				iibORPolicyRulesDetail.addGuidelinesItem(iibGuideline);
			}
		}	
	}
	
	public  void mapGuideline(GuidelineDataM uloGuideline,Guidelines iibGuideline){
		iibGuideline.setName(uloGuideline.getName());
		iibGuideline.setValue(uloGuideline.getValue());
	}
	
	public  void mapPolicyRules(PolicyRulesDataM uloPolicyRules,PolicyRules iibPolicyRules){
		iibPolicyRules.setPolicyCode(uloPolicyRules.getPolicyCode());
		iibPolicyRules.setResult(uloPolicyRules.getVerifiedResult());
	}
	
	public  void mapWebVerification(WebVerificationDataM uloWebVerification,WebVerifications iibWebVerification){
		iibWebVerification.setRemark(uloWebVerification.getRemark());
		iibWebVerification.setVerResult(uloWebVerification.getVerResult());
		iibWebVerification.setVerResultId(uloWebVerification.getVerResultId());
		iibWebVerification.setWebCode(uloWebVerification.getWebCode());
		iibWebVerification.setWebVerifyId(uloWebVerification.getWebVerifyId());
		iibWebVerification.setWebResult(uloWebVerification.getVerResult());
	}
	
	public  void mapLoan(LoanDataM uloLoan,Loans iibLoan){
//		iibLoan.setDisbursementAmt(uloLoan.getDisbursementAmt());
//		iibLoan.setFinalApproveAmt(uloLoan.getLoanAmt());
		iibLoan.setInstallmentAmount(uloLoan.getInstallmentAmt());
		iibLoan.setLoanId(uloLoan.getLoanId());
		iibLoan.setMaxCreditLimit(uloLoan.getMaxCreditLimit());
		iibLoan.setMaxCreditLimitBot(uloLoan.getMaxCreditLimitBot());
		iibLoan.setMinCreditLimit(uloLoan.getMinCreditLimit());
		iibLoan.setNormalCreditLimit(uloLoan.getFinalCreditLimit());
		iibLoan.setRecommendLoanAmt(uloLoan.getRecommendLoanAmt());
		iibLoan.setRequestLoanAmt(uloLoan.getRequestLoanAmt());
		iibLoan.setRequestTerm(uloLoan.getRequestTerm());
		iibLoan.setTerm(uloLoan.getTerm());
		iibLoan.setStampDuty(uloLoan.getStampDuty());
		iibLoan.setPenalty(null);
		iibLoan.setRecommendTerm(uloLoan.getRecommendTerm());
		iibLoan.setProductFactor(uloLoan.getProductFactor());
		iibLoan.setInstallmentDate(null);
		iibLoan.setBotCondition(uloLoan.getBotCondition());
		iibLoan.setBotFactor(uloLoan.getBotFactor());
		iibLoan.setCampaignCode(null);
		iibLoan.setDebtAmount(uloLoan.getDebtAmount());
		iibLoan.setDebtRecommend(uloLoan.getDebtRecommend());
	}
	
	public  void mapLoanCalcDiff(LoanDataM uloLoan,Loans iibLoan)
	{
		 mapLoan(uloLoan,iibLoan);
//		 iibLoan.setFinalApproveAmt(null);
		 iibLoan.setRecommendLoanAmt(null);
		 iibLoan.setRequestLoanAmt(uloLoan.getLoanAmt());
		 iibLoan.setRequestTerm(uloLoan.getTerm());
	}
	
	public  void mapCard(CardDataM uloCard,Card iibCard){
		iibCard.setCardLevel(uloCard.getCardLevel());
		iibCard.setCardlinkCardCode(uloCard.getCardLinkCardCode());
		iibCard.setCardNo(uloCard.getCardNo());
		iibCard.setCardType(uloCard.getCardType());
		iibCard.setCgaApplyChannel(uloCard.getCgaApplyChannel());
		iibCard.setCgaCode(uloCard.getCgaCode());
		iibCard.setChassisNo(uloCard.getChassisNo());
		iibCard.setMainCardNo(uloCard.getMainCardNo());
		iibCard.setMembershipNo(uloCard.getMembershipNo());
		iibCard.setPlasticCode(uloCard.getPlasticCode());
		iibCard.setCardEntryType(ProcessUtil.getCardEntryType(uloCard.getApplicationType()));
		iibCard.setCardlinkCardCode(uloCard.getCardLinkCardCode());
	}
	
	public  void mapCashTransfer(CashTransferDataM uloCashTransfer,CashTransfers iibCashTransfer){
		iibCashTransfer.setAccountName(uloCashTransfer.getAccountName());
		iibCashTransfer.setCallForCashFlag(uloCashTransfer.getCallForCashFlag());
		iibCashTransfer.setCashTransferType(uloCashTransfer.getCashTransferType());
		iibCashTransfer.setExpressTransfer(uloCashTransfer.getExpressTransfer());
		iibCashTransfer.setFirstTransferAmount(uloCashTransfer.getFirstTransferAmount());
		iibCashTransfer.setPercentTransfer(uloCashTransfer.getPercentTransfer());
		iibCashTransfer.setProductType(uloCashTransfer.getProductType());
		iibCashTransfer.setTransferAccount(uloCashTransfer.getTransferAccount());
	}
	
	public  void mapLoanFee(LoanFeeDataM uloLoanFee,LoanFees iibLoanFee){
		iibLoanFee.setEndMonth(DecisionServiceUtil.intToBigDecimal(uloLoanFee.getEndMonth()));
		iibLoanFee.setFee(uloLoanFee.getFee());
		iibLoanFee.setFeeCalcType(uloLoanFee.getFeeCalcType());
		iibLoanFee.setFeeLevelType(uloLoanFee.getFeeLevelType());
		iibLoanFee.setFeeSrc(uloLoanFee.getFeeSrc());
		iibLoanFee.setFeeType(uloLoanFee.getFeeType());
		iibLoanFee.setFinalFeeAmount(uloLoanFee.getFinalFeeAmount());
		iibLoanFee.setMaxFee(uloLoanFee.getMaxFee());
		iibLoanFee.setMinFee(uloLoanFee.getMinFee());
		iibLoanFee.setStartMonth(DecisionServiceUtil.intToBigDecimal(uloLoanFee.getStartMonth()));
	}
	
	public  void mapLoanPricing(LoanPricingDataM uloLoanPricing,LoanPricings iibLoanPricing){
		iibLoanPricing.setFeeWaiveCode(uloLoanPricing.getFeeWaiveCode());
	}
	
	public  void mapLoanTier(LoanTierDataM uloLoanTier,LoanTiers iibLoanTier){
		iibLoanTier.setInterestRate(uloLoanTier.getIntRateAmount());
		iibLoanTier.setRateType(uloLoanTier.getRateType());
	}
	
	public  void mapApplicationImages(ArrayList<ApplicationImageSplitDataM> imageSplits ,PersonalInfos iibPersonalInfo){
		 if(null!=imageSplits && imageSplits.size()>0){
			 List<String> docTypes = new ArrayList<String>();
			 for(ApplicationImageSplitDataM  appImage : imageSplits){
				 logger.debug("appImage.getDocType()>>"+appImage.getDocType());
				 if(!docTypes.contains(appImage.getDocType())){
					 Documents iibDocument = new Documents();
					 iibDocument.setDocumentType(appImage.getDocType());
					 iibPersonalInfo.addDocumentsItem(iibDocument);
					 docTypes.add(appImage.getDocType());
				 }
			 }
		
		 }
	}
	
	public void  cardLinkMapper(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo)throws Exception {
		if(null==uloPersonalInfo.getCardLinkCards()){
			logger.debug("uloCardLinkCards is null!");
		}else{
			for(CardlinkCardDataM uloCardLinkCard : uloPersonalInfo.getCardLinkCards()){
//				decisionservice_iib.CardlinkCard iibCardLinkCard = new decisionservice_iib.CardlinkCard();
//				mapCardlinkCard(uloCardLinkCard, iibCardLinkCard) ;
//				iibPersonalInfo.getCardlinkCards().add(iibCardLinkCard);
			}
		}
	}
	
	
	
	
	public void  cardLinkCustomerMapper(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo)throws Exception {
		if(null==uloPersonalInfo.getCardLinkCustomers()){
			logger.debug("uloCardLinkCustomers is null!");
		}else{
			for(CardlinkCustomerDataM uloCardLinkCustomer : uloPersonalInfo.getCardLinkCustomers()){
//				decisionservice_iib.CardlinkCustomer iibCardLinkCustomer = new decisionservice_iib.CardlinkCustomer();
//				mapCardlinkCustomer(uloCardLinkCustomer, iibCardLinkCustomer);
//				iibPersonalInfo.getCardlinkCustomers().add(iibCardLinkCustomer);
			}
		}
	}
	
	
	private void ccaCampaignsMapper(PersonalInfoDataM uloPersonalInfo,
			PersonalInfos iibPersonalInfo) {
		// TODO Auto-generated method stub
	}

	private void tcbLoansMapper(PersonalInfoDataM uloPersonalInfo,
			PersonalInfos iibPersonalInfo) {
		// TODO Auto-generated method stub
	}

	private void internalApplicantMapper(PersonalInfoDataM uloPersonalInfo,
			PersonalInfos iibPersonalInfo) {
		// TODO Auto-generated method stub
	}
	
	public KBankHeader getKbankHeader() {
		return kbankHeader;
	}

	public void setKbankHeader(KBankHeader kbankHeader) {
		this.kbankHeader = kbankHeader;
	}

}
