package com.eaf.service.common.iib.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.ClosedEndFundDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.DocumentVerificationDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;
import com.eaf.orig.ulo.model.app.FixedAccountDataM;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
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
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeExceptionDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeMGMDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductTradingDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;
import com.eaf.orig.ulo.model.app.ProjectCodeDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceMapDataM;
import com.eaf.orig.ulo.model.app.TaweesapDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.eaf.orig.ulo.model.app.WisdomDataM;
import com.eaf.orig.ulo.model.app.WorkFlowDecisionM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountDataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportDataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportRuleDataM;
import com.eaf.orig.ulo.model.ncb.NcbAddressDataM;
import com.eaf.orig.ulo.model.ncb.NcbIdDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.orig.ulo.model.ncb.NcbNameDataM;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.AdditionalServiceDataM;
import decisionservice_iib.DocumentSlaTypeDataM;
import decisionservice_iib.KBankHeader;
import decisionservice_iib.PolicyRuleReasonDataM;

public class DecisionServiceRequestMapper {
	private static transient Logger logger = Logger.getLogger(DecisionServiceRequestMapper.class);
	private KBankHeader  kbankHeader;	
	String RECOMMEND_DECISION_CANCEL = ServiceCache.getConstant("RECOMMEND_DECISION_CANCEL");
	public  decisionservice_iib.ApplicationGroupDataM  getDecsionServiceMapper(ApplicationGroupDataM uloApplicationGroup,String decisionPoint)throws Exception{
		if (null==uloApplicationGroup) {
			String msg = "uloApplicationGroup is null!";
			throw new Exception(msg);
		}else{
			decisionservice_iib.ApplicationGroupDataM iibApplicationGroup  = new decisionservice_iib.ApplicationGroupDataM();
			mapApplicationGroup(decisionPoint, uloApplicationGroup, iibApplicationGroup);
			applicationMapper(uloApplicationGroup, iibApplicationGroup);
			personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
			
			if(null!=uloApplicationGroup.getReferencePersons()){
				for(ReferencePersonDataM uloRefPerson : uloApplicationGroup.getReferencePersons()){
					decisionservice_iib.ReferencePersonDataM iibRefPersonal = new decisionservice_iib.ReferencePersonDataM();
					mapReferencePerson(uloRefPerson, iibRefPersonal);
					iibApplicationGroup.getReferencePersons().add(iibRefPersonal);
				}
			}
			if(null!=uloApplicationGroup.getSaleInfos()){
				for(SaleInfoDataM uloSaleInfo : uloApplicationGroup.getSaleInfos()){
					decisionservice_iib.SaleInfoDataM iibSaleInfo = new decisionservice_iib.SaleInfoDataM();
					mapSaleInfo(uloSaleInfo, iibSaleInfo);
					iibApplicationGroup.getSaleInfos().add(iibSaleInfo);
				}
			}
			return iibApplicationGroup;
		}
	}
	
	public void  applicationMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {

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
					decisionservice_iib.ApplicationDataM iibApplication = new decisionservice_iib.ApplicationDataM();
					iibApplicationGroup.getApplications().add(iibApplication);
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					LoanDataM uloLoan =   uloApplication.getLoan();
					if(null!=uloLoan){
						mapBilingCycle(uloApplicationGroup,uloLoan, iibApplication);
					}			
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);
					
					decisionservice_iib.VerificationResultDataM iibVerResult = new decisionservice_iib.VerificationResultDataM();
					iibApplication.setVerificationResult(iibVerResult);
					verificationMapper(uloApplication, iibVerResult);
				}
			}
		}
	}
	
	public void  personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		ArrayList<PersonalInfoDataM> uloPersonalInfos = uloApplicationGroup.getPersonalInfos();
		
		if (null==uloPersonalInfos) {
			String msg = "uloPersonal is null!";
			throw new Exception(msg);
		}else{
			for(PersonalInfoDataM uloPersonalInfo :  uloPersonalInfos){
				logger.debug("uloPersonalInfo.personalId : "+uloPersonalInfo.getPersonalId());
				decisionservice_iib.PersonalInfoDataM iibPersonalInfo = new decisionservice_iib.PersonalInfoDataM();
				iibApplicationGroup.getPersonalInfos().add(iibPersonalInfo);
			
				mapPersonalInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
				addressMapper(uloPersonalInfo, iibPersonalInfo);
				cardLinkMapper(uloPersonalInfo, iibPersonalInfo);
				cardLinkCustomerMapper(uloPersonalInfo, iibPersonalInfo);
				debtInfoMapper(uloPersonalInfo, iibPersonalInfo);
				ncbMapper(uloPersonalInfo, iibPersonalInfo);
				incomeMapper(uloPersonalInfo, iibPersonalInfo);
				allIncomeBundlingMapper(uloPersonalInfo, uloApplicationGroup, iibPersonalInfo);
				imageSplitMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
				
				//KPL Additional
				//tcbLoansMapper(uloPersonalInfo, iibPersonalInfo);
				//internalApplicantMapper(uloPersonalInfo, iibPersonalInfo);
				//ccaCampaignsMapper(uloPersonalInfo, iibPersonalInfo);
				
				decisionservice_iib.VerificationResultDataM iibVerResult = new decisionservice_iib.VerificationResultDataM();
				iibPersonalInfo.setVerificationResult(iibVerResult);
				if(null!=uloPersonalInfo.getVerificationResult()){					
					verificationMapper(uloPersonalInfo, iibVerResult);
				}	
				incomeSourceMapper(uloPersonalInfo, iibVerResult);
			}
		}
	}
	
	private void ccaCampaignsMapper(PersonalInfoDataM uloPersonalInfo,
			decisionservice_iib.PersonalInfoDataM iibPersonalInfo) {
		// TODO Auto-generated method stub
	}

	private void tcbLoansMapper(PersonalInfoDataM uloPersonalInfo,
			decisionservice_iib.PersonalInfoDataM iibPersonalInfo) {
		// TODO Auto-generated method stub
	}

	private void internalApplicantMapper(PersonalInfoDataM uloPersonalInfo,
			decisionservice_iib.PersonalInfoDataM iibPersonalInfo) {
		// TODO Auto-generated method stub
	}

	public void imageSplitMapper(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo) throws Exception{
		ArrayList<ApplicationImageSplitDataM> applicationImageSplits = ProcessUtil.getPersonalApplicationImageSplits(uloApplicationGroup,uloPersonalInfo);
		if(null!=applicationImageSplits && applicationImageSplits.size()>0){
			mapApplicationImages(applicationImageSplits, iibPersonalInfo);
		}else{
			logger.debug("doc of personal id:"+uloPersonalInfo.getPersonalId()+"is null!!");
		}
	}
	
	public void  loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
		if (null==uloApplication.getLoans()) {
			String msg = "uloLoans is null!";
			throw new Exception(msg);
		}else{
			for(LoanDataM uloLoan : uloApplication.getLoans()){
				decisionservice_iib.LoanDataM iibLoan = new decisionservice_iib.LoanDataM();
				mapLoan(uloLoan, iibLoan);
				
				iibLoan.setCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				//add coa product code to ulo loan for compare set card type
				uloLoan.setRequestCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				iibApplication.getLoans().add(iibLoan);
				
				if(null!=uloLoan.getSpecialAdditionalServiceIds() && uloLoan.getSpecialAdditionalServiceIds().size()>0){
					iibLoan.getSpecialAdditionalServiceIds().addAll(uloLoan.getSpecialAdditionalServiceIds());
				}
				ArrayList<SpecialAdditionalServiceDataM>  specialAdditionalService = uloApplicationGroup.getSpecialAdditionalServiceLoan(uloLoan.getLoanId());
				if(null!=specialAdditionalService  && specialAdditionalService.size()>0){	
					for(SpecialAdditionalServiceDataM uloAdditionalService : specialAdditionalService){
						decisionservice_iib.AdditionalServiceDataM iibAdditionalService = new AdditionalServiceDataM();
						mapAdditionalService(uloAdditionalService, iibAdditionalService);
						iibLoan.getAdditionalServices().add(iibAdditionalService);
					}
				}
								
				if(null!=uloLoan.getPaymentMethodId()){
					PaymentMethodDataM uloPaymentMethod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
					decisionservice_iib.PaymentMethodDataM  iibPaymentMethod = new decisionservice_iib.PaymentMethodDataM();
					mapPaymentMethod(uloPaymentMethod, iibPaymentMethod);
					iibLoan.getPaymentMethods().add(iibPaymentMethod);
				}
				
				decisionservice_iib.CardDataM iibCard = new decisionservice_iib.CardDataM();
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
	public void  loanFeeMapper(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan)throws Exception {
		if (null==uloLoan.getLoanFees()) {
			logger.debug("uloLoanFees() is null!");
		}else{
			for(LoanFeeDataM uloLoanFee : uloLoan.getLoanFees()){
				decisionservice_iib.LoanFeeDataM iibLoanFee = new decisionservice_iib.LoanFeeDataM();
				mapLoanFee(uloLoanFee, iibLoanFee);
				iibLoan.getLoanFees().add(iibLoanFee);
			}
		}
	}
	
	public void  loanTierMapper(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan)throws Exception {
		if (null==uloLoan.getLoanTiers()) {
			logger.debug("ulotLoanTiers is null!");
		}else{
			for(LoanTierDataM uloLoanTier : uloLoan.getLoanTiers()){
				decisionservice_iib.LoanTierDataM iibLoanTier = new decisionservice_iib.LoanTierDataM();
				mapLoanTier(uloLoanTier, iibLoanTier);
				iibLoan.getLoanTiers().add(iibLoanTier);
			}
		}
	}
	public void  loanPricingMapper(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan)throws Exception {
		if (null==uloLoan.getLoanPricings()) {
			logger.debug("uloLoanPricing is null!");
		}else{
			for(LoanPricingDataM uloLoanPricing : uloLoan.getLoanPricings()){
				decisionservice_iib.LoanPricingDataM iibLoanPricing = new decisionservice_iib.LoanPricingDataM();
				mapLoanPricing(uloLoanPricing, iibLoanPricing);
				iibLoan.getLoanPricings().add(iibLoanPricing);
			}
		}
	}
		
	public void  incomeMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo)throws Exception {
		if (null==uloPersonalInfo.getIncomeInfos()) {
			logger.debug("uloIncome is null!");
		}else{
			for(IncomeInfoDataM   uloIncomeInfo: uloPersonalInfo.getIncomeInfos()){
				decisionservice_iib.IncomeInfoDataM iibIncomeInfo = new decisionservice_iib.IncomeInfoDataM();
				mapIncomeInfo(uloIncomeInfo, iibIncomeInfo);
				iibPersonalInfo.getIncomeInfos().add(iibIncomeInfo);
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
						decisionservice_iib.PayslipDataM iibPayslip = new decisionservice_iib.PayslipDataM();
						payslipMapper(uloPayslip, iibPayslip);
						if(uloPayslip != null && uloPayslip.getNoMonth() >= 6){
							//Refer to ST000000000078 : Alternative condition for income type, the value will be retrieved from SystemID4
							incomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "CHOICE_NO", uloPayslip.getIncomeType(), "SYSTEM_ID2");
							iibIncomeInfo.setIncomeType(incomeType);
						}
						
						iibIncomeInfo.getPayslips().add(iibPayslip);
					}else if (uloIncomeElement instanceof TaweesapDataM) {
						TaweesapDataM uloTaweesap = (TaweesapDataM) uloIncomeElement;
						decisionservice_iib.TaweesapDataM iibTaweesap = new decisionservice_iib.TaweesapDataM();
						taweesapMapper(uloTaweesap, iibTaweesap);
						iibIncomeInfo.getTaweesaps().add(iibTaweesap);
						
					}else if (uloIncomeElement instanceof SavingAccountDataM) {
						SavingAccountDataM uloSavingAccount = (SavingAccountDataM) uloIncomeElement;	
						decisionservice_iib.SavingAccountDataM iibSavingAccount = new decisionservice_iib.SavingAccountDataM();
						savingAccountMapper(uloSavingAccount, iibSavingAccount);
						iibIncomeInfo.getSavingAccounts().add(iibSavingAccount);
						
					}else if (uloIncomeElement instanceof SalaryCertDataM) {
						SalaryCertDataM uloSalaryCert= (SalaryCertDataM) uloIncomeElement;
						decisionservice_iib.SalaryCertDataM iibSalaryCert = new decisionservice_iib.SalaryCertDataM();
						salaryCertMapper(uloSalaryCert, iibSalaryCert);
						iibIncomeInfo.getSalaryCerts().add(iibSalaryCert);
						
					}else if (uloIncomeElement instanceof PreviousIncomeDataM) {
						PreviousIncomeDataM uloPreviosIncome = (PreviousIncomeDataM) uloIncomeElement;
						decisionservice_iib.PreviousIncomeDataM iibPreviousIncome = new decisionservice_iib.PreviousIncomeDataM();
						previousIncomeMapper(uloPreviosIncome, iibPreviousIncome);
						iibIncomeInfo.getPreviousIncomes().add(iibPreviousIncome);
						
					}else if (uloIncomeElement instanceof PayrollDataM) {
						PayrollDataM uloPayroll = (PayrollDataM) uloIncomeElement;
						decisionservice_iib.PayrollDataM iibPayroll = new decisionservice_iib.PayrollDataM();
						payrollMapper(uloPayroll, iibPayroll);
						iibIncomeInfo.getPayRolls().add(iibPayroll);
						
					}else if (uloIncomeElement instanceof OpenedEndFundDataM) {
						OpenedEndFundDataM uloOpenedEndFund = (OpenedEndFundDataM) uloIncomeElement;
						decisionservice_iib.OpenedEndFundDataM iibOpenedEndFund = new decisionservice_iib.OpenedEndFundDataM();
						openedEndFundMapper(uloOpenedEndFund, iibOpenedEndFund);
						iibIncomeInfo.getOpendEndFounds().add(iibOpenedEndFund);
						
					}else if (uloIncomeElement instanceof MonthlyTawi50DataM) {
						MonthlyTawi50DataM uloMonthlyTawi50= (MonthlyTawi50DataM) uloIncomeElement;
						decisionservice_iib.MonthlyTawi50DataM iibMonthlyTawi50 = new decisionservice_iib.MonthlyTawi50DataM();
						monthlyTawi50Mapper(uloMonthlyTawi50, iibMonthlyTawi50);
						iibIncomeInfo.getMonthlyTawi50S().add(iibMonthlyTawi50);
						
					}else if (uloIncomeElement instanceof KVIIncomeDataM) {
						KVIIncomeDataM uloKVIincome = (KVIIncomeDataM) uloIncomeElement;
						decisionservice_iib.KviIncomeDataM iibKVIIncome = new decisionservice_iib.KviIncomeDataM();
						kviIncomeMapper(uloKVIincome, iibKVIIncome);
						iibIncomeInfo.getKviIncomes().add(iibKVIIncome);
						
					}else if (uloIncomeElement instanceof FixedGuaranteeDataM) {
						FixedGuaranteeDataM uloFixedGuarantee = (FixedGuaranteeDataM) uloIncomeElement;
						decisionservice_iib.FixedGuaranteeDataM iibFixedGuarantee = new decisionservice_iib.FixedGuaranteeDataM();
						fixedGuaranteeMapper(uloFixedGuarantee, iibFixedGuarantee);
						iibIncomeInfo.getFixedGuarantees().add(iibFixedGuarantee);
						
					}else if (uloIncomeElement instanceof FixedAccountDataM) {
						FixedAccountDataM uloFixedAccount = (FixedAccountDataM) uloIncomeElement;
						decisionservice_iib.FixedAccountDataM iibFixedAccount = new decisionservice_iib.FixedAccountDataM();
						fixedAccountMapper(uloFixedAccount, iibFixedAccount);
						iibIncomeInfo.getFixedAccounts().add(iibFixedAccount);
						
					}else if (uloIncomeElement instanceof FinancialInstrumentDataM) {
						FinancialInstrumentDataM uloFinancialInst = (FinancialInstrumentDataM) uloIncomeElement;
						decisionservice_iib.FinancialInstrumentDataM iibFinalcialInst = new decisionservice_iib.FinancialInstrumentDataM();
						financialInstrumentMapper(uloFinancialInst, iibFinalcialInst);
						iibIncomeInfo.getFinancialInstruments().add(iibFinalcialInst);
						
					}else if (uloIncomeElement instanceof ClosedEndFundDataM) {
						ClosedEndFundDataM uloCloseEndFund = (ClosedEndFundDataM) uloIncomeElement;
						decisionservice_iib.ClosedEndFundDataM iibCloseEndFund = new decisionservice_iib.ClosedEndFundDataM();
						closedEndFundMapper(uloCloseEndFund, iibCloseEndFund);
						iibIncomeInfo.getCloseEndFounds().add(iibCloseEndFund);
						
					}else if (uloIncomeElement instanceof BankStatementDataM) {
						BankStatementDataM uloBankStatement = (BankStatementDataM) uloIncomeElement;
						decisionservice_iib.BankStatementDataM iibBankStatement = new decisionservice_iib.BankStatementDataM();
						bankStatementMapper(uloBankStatement, iibBankStatement);
						iibIncomeInfo.getBankStatements().add(iibBankStatement);
						
					}else if (uloIncomeElement instanceof YearlyTawi50DataM) {
						YearlyTawi50DataM uloYearlyTawi50 = (YearlyTawi50DataM) uloIncomeElement;
						decisionservice_iib.YearlyTawi50DataM iibYearlyTawi50 = new decisionservice_iib.YearlyTawi50DataM();
						yearlyTawi50Mapper(uloYearlyTawi50, iibYearlyTawi50);
						iibIncomeInfo.getYearlyTawi50S().add(iibYearlyTawi50);
					}
				}
				
			}
		}
		 
	}
	
	public void  bankStatementMapper(BankStatementDataM  uloBankStatement, decisionservice_iib.BankStatementDataM  iibBankStatement)throws Exception {
		if (null==uloBankStatement) {
			logger.debug("uloBankStatementis null!");
		}else{
			mapBankStatement(uloBankStatement, iibBankStatement);
			if(null!=uloBankStatement.getBankStatementDetails() &&  uloBankStatement.getBankStatementDetails().size()>0){
				for(BankStatementDetailDataM uloBankStatementDetail : uloBankStatement.getBankStatementDetails()){
					decisionservice_iib.BankStatementDetailDataM iibBankStatementDetail = new decisionservice_iib.BankStatementDetailDataM();
					mapBankStatementDetail(uloBankStatementDetail, iibBankStatementDetail);
					iibBankStatement.getBankStatementDetails().add(iibBankStatementDetail);
				}
			}
		}
	}
	
	public void  closedEndFundMapper(ClosedEndFundDataM  uloCloseEndFund, decisionservice_iib.ClosedEndFundDataM  iibCloseEndFund)throws Exception {
		if (null==uloCloseEndFund) {
			logger.debug("uloCloseEndFund null!");
		}else{
			mapClosedEndFund(uloCloseEndFund, iibCloseEndFund);
		}	
	}
	
	public void  financialInstrumentMapper(FinancialInstrumentDataM  uloFinancialInst,decisionservice_iib.FinancialInstrumentDataM  iibFinalcialInst)throws Exception {
		if (null==uloFinancialInst) {
			logger.debug("uloFinancialInst null!");
		}else{
			mapFinancialInstrument(uloFinancialInst, iibFinalcialInst);
		}
	}
	
	public void  fixedAccountMapper(FixedAccountDataM uloFixedAccount,decisionservice_iib.FixedAccountDataM  iibFixedAccount)throws Exception {
		if (null==uloFixedAccount) {
			logger.debug("uloFixedAccount null!");
		}else{
			mapFixedAccount(uloFixedAccount, iibFixedAccount);
		}
	}
		  
	public void  fixedGuaranteeMapper(FixedGuaranteeDataM  uloFixedGuarantee,decisionservice_iib.FixedGuaranteeDataM  iibFixedGuarantee)throws Exception {
		if (null==uloFixedGuarantee) {
			logger.debug("uloFixedGuarantee null!");
		}else{
			mapFixedGuarantee(uloFixedGuarantee, iibFixedGuarantee);
		}
	}
	
	public void  kviIncomeMapper(KVIIncomeDataM  uloKVIincome,decisionservice_iib.KviIncomeDataM  iibKVIIncome)throws Exception {
		if (null==uloKVIincome) {
			logger.debug("uloKVIincome null!");
		}else{
			mapKviIncome(uloKVIincome, iibKVIIncome);
		}
	}
	
	public void  monthlyTawi50Mapper(MonthlyTawi50DataM  uloMonthlyTawi50,decisionservice_iib.MonthlyTawi50DataM  iibMonthlyTawi50)throws Exception {
		if (null==uloMonthlyTawi50) {
			logger.debug("uloMonthlyTawi50 null!");
		}else{
			mapMonthlyTawi50(uloMonthlyTawi50, iibMonthlyTawi50);
			if(null!=uloMonthlyTawi50.getMonthlyTaxi50Details() &&  uloMonthlyTawi50.getMonthlyTaxi50Details().size()>0){
				for(MonthlyTawi50DetailDataM uloMonthlyTawi50Detail : uloMonthlyTawi50.getMonthlyTaxi50Details()){
					decisionservice_iib.MonthlyTawi50DetailDataM iibMonthlyTawi50Detail = new decisionservice_iib.MonthlyTawi50DetailDataM();
					mapMonthlyTawi50Detail(uloMonthlyTawi50Detail, iibMonthlyTawi50Detail);
					iibMonthlyTawi50.getMonthlyTaxi50Details().add(iibMonthlyTawi50Detail);
				}
			}
		}
	}
	
	public void  openedEndFundMapper(OpenedEndFundDataM  uloOpenedEndFund,decisionservice_iib.OpenedEndFundDataM  iibOpenedEndFund) throws Exception {
		if (null==uloOpenedEndFund) {
			logger.debug("uloOpenedEndFund null!");
		}else{
			mapOpenedEndFund(uloOpenedEndFund, iibOpenedEndFund);
			if(null!=uloOpenedEndFund.getOpenEndFundDetails() && uloOpenedEndFund.getOpenEndFundDetails().size()>0){
				for(OpenedEndFundDetailDataM uloOpenedEndFundDetail : uloOpenedEndFund.getOpenEndFundDetails()){
					decisionservice_iib.OpenedEndFundDetailDataM iibOpenedEndFundDetail = new decisionservice_iib.OpenedEndFundDetailDataM();
					mapOpenedEndFundDetail(uloOpenedEndFundDetail, iibOpenedEndFundDetail);
					iibOpenedEndFund.getOpenEndFundDetails().add(iibOpenedEndFundDetail);
				}
			}
		}
	}
	
	public void  payrollMapper(PayrollDataM uloPayroll,decisionservice_iib.PayrollDataM  iibPayroll)throws Exception {
		if (null==uloPayroll) {
			logger.debug("uloPayroll null!");
		}else{
			mapPayroll(uloPayroll, iibPayroll);
		}
	}
	
	public void  payslipMapper(PayslipDataM   uloPaySlip,decisionservice_iib.PayslipDataM  iibPaySlip)throws Exception {
		if (null==uloPaySlip) {
			logger.debug("uloPaySlip null!");
		}else{
			mapPayslip(uloPaySlip, iibPaySlip);
			if(null!=uloPaySlip.getPayslipMonthlys() &&  uloPaySlip.getPayslipMonthlys() .size()>0){
				for(PayslipMonthlyDataM uloPaySlipMonthly : uloPaySlip.getPayslipMonthlys()){
					decisionservice_iib.PayslipMonthlyDataM iibPaySlipMonthly = new decisionservice_iib.PayslipMonthlyDataM();				
					mapPayslipMonthly(uloPaySlipMonthly, iibPaySlipMonthly);
					iibPaySlip.getPayslipMonthlys().add(iibPaySlipMonthly);
				}
			}
		}
	}
	
	public void  previousIncomeMapper(PreviousIncomeDataM uloPreviosIncome,decisionservice_iib.PreviousIncomeDataM  iibPreviousIncome)throws Exception {
		if (null==uloPreviosIncome) {
			logger.debug("uloPreviosIncome null!");
		}else{
			mapPreviousIncome(uloPreviosIncome, iibPreviousIncome);
		}
	}
	
	public void  salaryCertMapper(SalaryCertDataM  uloSalaryCert,decisionservice_iib.SalaryCertDataM  iibSalaryCert)throws Exception {
		if (null==uloSalaryCert) {
			logger.debug("uloSalaryCert null!");
		}else{
			mapSalaryCert(uloSalaryCert, iibSalaryCert);
		}
	}
	
	public void  savingAccountMapper(SavingAccountDataM  uloSavingAccount,decisionservice_iib.SavingAccountDataM  iibSavingAccount)throws Exception {
		if (null==uloSavingAccount) {
			logger.debug("uloSavingAccount null!");
		}else{
			mapSavingAccount(uloSavingAccount, iibSavingAccount);
			if(null!=uloSavingAccount.getSavingAccountDetails() && uloSavingAccount.getSavingAccountDetails().size()>0){
				for(SavingAccountDetailDataM uloSavingAccDetail : uloSavingAccount.getSavingAccountDetails()){
					decisionservice_iib.SavingAccountDetailDataM iibSavingAccountDetail = new decisionservice_iib.SavingAccountDetailDataM();
					mapSavingAccountDetail(uloSavingAccDetail, iibSavingAccountDetail);
					iibSavingAccount.getSavingAccountDetails().add(iibSavingAccountDetail);
				}
			}
		}
	}
	
	public void  taweesapMapper(TaweesapDataM uloTaweesap,decisionservice_iib.TaweesapDataM  iibTaweesap)throws Exception {
		if (null==uloTaweesap) {
			logger.debug("uloTaweesap null!");
		}else{
			mapTaweesapDataM(uloTaweesap, iibTaweesap);
		}
	}
	
	public void  yearlyTawi50Mapper(YearlyTawi50DataM  uloYearlyTawi50,decisionservice_iib.YearlyTawi50DataM  iibYearlyTawi50)throws Exception {
		if (null==uloYearlyTawi50) {
			logger.debug("uloYearlyTawi50 null!");
		}else{
			mapYearlyTawi50(uloYearlyTawi50, iibYearlyTawi50);
		}
	}
	
	public void  allIncomeBundlingMapper(PersonalInfoDataM uloPerson, ApplicationGroupDataM uloAppGroup,decisionservice_iib.PersonalInfoDataM iibPersonalInfo)throws Exception{
		String appLevelRelation = DecisionServiceCacheControl.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
		String FIELD_ID_REVENUE_CATEGORY = DecisionServiceCacheControl.getConstant("FIELD_ID_REVENUE_CATEGORY");
		List<ApplicationDataM> uloApplications = uloAppGroup.filterApplicationPersonalLifeCycle(uloPerson.getPersonalId(), appLevelRelation);
		if(null!=uloApplications){
		 
			for(ApplicationDataM uloApplication : uloApplications){
				//Add HL
				BundleHLDataM uloBundleHL = uloApplication.getBundleHL();
				if(uloBundleHL != null){
					decisionservice_iib.BundleHLDataM iibBundleHL = new decisionservice_iib.BundleHLDataM();
					mapBundleHL(uloBundleHL, iibBundleHL);

					decisionservice_iib.IncomeInfoDataM incomeHL = new decisionservice_iib.IncomeInfoDataM();
					String choiceNo = DecisionServiceCacheControl.getConstant("INC_TYPE_BUNDLE_HL");
					String incomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "CHOICE_NO", choiceNo, "SYSTEM_ID2");
					incomeHL.setIncomeType(incomeType);
					incomeHL.setBundleHL(iibBundleHL);
					iibPersonalInfo.getIncomeInfos().add(incomeHL);
				}
				
				//Add KL
				BundleKLDataM uloBundleKL = uloApplication.getBundleKL();
				if(uloBundleKL != null){
					decisionservice_iib.BundleKLDataM iibBundleKL = new decisionservice_iib.BundleKLDataM();
					mapBundleKL(uloBundleKL, iibBundleKL);
					decisionservice_iib.IncomeInfoDataM incomeKL = new decisionservice_iib.IncomeInfoDataM();
					
					String choiceNo = DecisionServiceCacheControl.getConstant("INC_TYPE_BUNDLE_KL");
					String incomeType  = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "CHOICE_NO", choiceNo, "SYSTEM_ID2");						
					incomeKL.setIncomeType(incomeType);
					incomeKL.setBundleKL(iibBundleKL);
					iibPersonalInfo.getIncomeInfos().add(incomeKL);
				}
				
				//Add SME
				BundleSMEDataM uloBundleSME = uloApplication.getBundleSME();
				if(uloBundleSME != null){
					decisionservice_iib.BundleSMEDataM iibBundleSME = new decisionservice_iib.BundleSMEDataM();
					mapBundleSME(uloBundleSME, iibBundleSME);
					
					decisionservice_iib.IncomeInfoDataM incomeSME= new decisionservice_iib.IncomeInfoDataM();
					String choiceNo = DecisionServiceCacheControl.getConstant("INC_TYPE_BUNDLE_SME");
					String incomeType = DecisionServiceCacheControl.getNameListBox(FIELD_ID_REVENUE_CATEGORY, "CHOICE_NO", choiceNo, "SYSTEM_ID2");
					incomeSME.setIncomeType(incomeType);
					incomeSME.setBundleSME(iibBundleSME);
					iibPersonalInfo.getIncomeInfos().add(incomeSME);
				}
				
				
 
			}	
		}

	}
		
	public void  ncbMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo)throws Exception {
		if(null!=uloPersonalInfo.getNcbInfos()&&uloPersonalInfo.getNcbInfos().size()>0){
			NcbInfoDataM   uloNcbInfo = uloPersonalInfo.getNcbInfos().get(0);
			decisionservice_iib.NcbInfoDataM iibNcbInfo= new decisionservice_iib.NcbInfoDataM();
			decisionservice_iib.KcbsResponse iibKcbsReason = new decisionservice_iib.KcbsResponse();
			mapNcbInfo(uloNcbInfo, iibNcbInfo);
			mapKcbsReason(uloNcbInfo,iibKcbsReason);
			iibPersonalInfo.setNcbInfo(iibNcbInfo);
			iibPersonalInfo.setKcbsResponse(iibKcbsReason);
		}else{
			logger.debug("ulo ncb is null");
		}
	}
	public void  addressMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo)throws Exception {
		if(null==uloPersonalInfo.getAddresses()){
			logger.debug("uloAddress is null!");
		}else{
			for(AddressDataM uloAddress : uloPersonalInfo.getAddresses()){
				decisionservice_iib.AddressDataM iibAddress = new decisionservice_iib.AddressDataM();
				mapAddress(uloAddress, iibAddress);
				iibPersonalInfo.getAddresses().add(iibAddress);
			}
		}
	}
	
	public void  incomeSourceMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.VerificationResultDataM iibVerification)throws Exception {
		if(null==uloPersonalInfo.getIncomeSources()){
			logger.debug("uloIncomeSources is null!");
		}else{
			iibVerification.setVerifiedIncomeSource(uloPersonalInfo.getSorceOfIncome());
			for(IncomeSourceDataM   uloIncomeSource: uloPersonalInfo.getIncomeSources()){
				decisionservice_iib.IncomeSourceDataM iibIncomeSource= new decisionservice_iib.IncomeSourceDataM();
				mapIncomeSource(uloIncomeSource, iibIncomeSource);
				iibVerification.getIncomeSources().add(iibIncomeSource);
			}
		}
	}
	
	public void  cardLinkMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo)throws Exception {
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
	public void  cardLinkCustomerMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo)throws Exception {
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
	
	public void  debtInfoMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo)throws Exception {
		if(null==uloPersonalInfo.getDebtInfos()){
			logger.debug("uloDebtInfo is null!");
		}else{
			decisionservice_iib.DebtInfoDataM iibDebtInfo = new decisionservice_iib.DebtInfoDataM();
			mapDebtInfo(uloPersonalInfo.getDebtInfos(), iibDebtInfo);
			iibPersonalInfo.getDebtInfos().add(iibDebtInfo);
		}
	}
		
	public void  cashTransferMapper(LoanDataM uloLoan ,decisionservice_iib.LoanDataM iibLoan)throws Exception {
		if(null!=uloLoan.getCashTransfers() && uloLoan.getCashTransfers().size()>0){
			for(CashTransferDataM uloCashTransfer : uloLoan.getCashTransfers()){
				decisionservice_iib.CashTransferDataM iibCashTransfer = new decisionservice_iib.CashTransferDataM();
				mapCashTransfer(uloCashTransfer, iibCashTransfer);
				iibLoan.getCashTransfers().add(iibCashTransfer);
			}
		}else{
			logger.debug("uloCashTransfers is null!");
		}
	}
	
	public void  verificationMapper(Object uloObject,decisionservice_iib.VerificationResultDataM iibVerificationResult)throws Exception {
		if(uloObject instanceof PersonalInfoDataM){
			PersonalInfoDataM uloPersonal = (PersonalInfoDataM)uloObject;
			VerificationResultDataM uloVerification =uloPersonal.getVerificationResult();
			if(null!=uloVerification){
				mapPersonalVerificationResult(uloVerification, iibVerificationResult);
				if(null!=uloVerification.getRequiredDocs()&& uloVerification.getRequiredDocs().size()>0){
					for(RequiredDocDataM uloRequiredDoc : uloVerification.getRequiredDocs()){
						decisionservice_iib.RequiredDocDataM iibRequiredDoc = new decisionservice_iib.RequiredDocDataM();
						mapRequiredDoc(uloRequiredDoc, iibRequiredDoc);
						iibVerificationResult.getRequiredDocs().add(iibRequiredDoc);
					}
				}
				
				if(null!=uloVerification.getWebVerifications()&& uloVerification.getWebVerifications().size()>0){
					for(WebVerificationDataM uloWebVerification : uloVerification.getWebVerifications()){
						decisionservice_iib.WebVerificationDataM iibWebVerification = new decisionservice_iib.WebVerificationDataM();
						mapWebVerification(uloWebVerification, iibWebVerification);
						iibVerificationResult.getWebVerifications().add(iibWebVerification);
					}
				}
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
	
	public void  policyRulesMapper(ArrayList<PolicyRulesDataM> uloPolicyRulesList,decisionservice_iib.VerificationResultDataM iibVerificationResult) {
		if(null!=uloPolicyRulesList && uloPolicyRulesList.size()>0){
			ArrayList<String> policyCodes = new ArrayList<String>();
			for(PolicyRulesDataM uloPolicyRules : uloPolicyRulesList){
				String policyCode = uloPolicyRules.getPolicyCode();
				decisionservice_iib.PolicyRulesDataM iibPolicyRule = new decisionservice_iib.PolicyRulesDataM();
				if(!policyCodes.contains(policyCode)){
					iibPolicyRule=ProcessUtil.getDecisionServicePolicyRule(iibVerificationResult.getPolicyRules(), policyCode);	
					if(null==iibPolicyRule){
						iibPolicyRule = new decisionservice_iib.PolicyRulesDataM();
						iibVerificationResult.getPolicyRules().add(iibPolicyRule);
					}
				}else{
					iibVerificationResult.getPolicyRules().add(iibPolicyRule);
				}						
				mapPolicyRules(uloPolicyRules, iibPolicyRule);
				if(null!=uloPolicyRules.getOrPolicyRules()){
				   for(ORPolicyRulesDataM uloORPolicyRules : uloPolicyRules.getOrPolicyRules()){
					   decisionservice_iib.OrPolicyRulesDataM iibOrPolicyRules = new decisionservice_iib.OrPolicyRulesDataM();						
					   mapORPolicyRules(uloORPolicyRules, iibOrPolicyRules);
					   iibPolicyRule.getOrPolicyRules().add(iibOrPolicyRules);
				   }
			   	}
				if(null!=uloPolicyRules.getPolicyRulesConditions()){
					for(PolicyRulesConditionDataM uloPolicyRuleCondition : uloPolicyRules.getPolicyRulesConditions()){
					   decisionservice_iib.PolicyRuleConditionDataM iibPolicyRuleCondition = new decisionservice_iib.PolicyRuleConditionDataM();
					   mapPolicyRulesCondition(uloPolicyRuleCondition, iibPolicyRuleCondition);
					   iibPolicyRule.getPolicyRuleConditions().add(iibPolicyRuleCondition);
				   }
				}
				if(null!=uloPolicyRules.getReason()){
					decisionservice_iib.PolicyRuleReasonDataM iibPolicyRuleReason = new PolicyRuleReasonDataM();
					iibPolicyRuleReason.setReasonCode(uloPolicyRules.getReason());
					iibPolicyRuleReason.setReasonRank(String.valueOf(uloPolicyRules.getRank()));
					iibPolicyRule.getPolicyRuleReasons().add(iibPolicyRuleReason);
				}
				// add for check existing policy
				policyCodes.add(policyCode);
				 
			}
		}
	}
	
	public void privilegeProjectCodeMapper(VerificationResultDataM uloVerification,decisionservice_iib.VerificationResultDataM iibVerResult) {
		iibVerResult.setRequiredVerPrivilegeFlag(uloVerification.getRequiredVerPrivilegeFlag());
		iibVerResult.setVerPrivilegeResultCode(uloVerification.getVerPrivilegeResultCode());
		
		//Mapping PrivilegeProjectCodes
		if(null!= uloVerification.getPrivilegeProjectCodes() && uloVerification.getPrivilegeProjectCodes().size()>0){
				PrivilegeProjectCodeDataM uloPrivilegeProjectCode  =  uloVerification.getPrivilegeProjectCodes().get(0);
				decisionservice_iib.PrivilegeProjectCodeDataM  iibPrivilegeProjectCode = new decisionservice_iib.PrivilegeProjectCodeDataM();
				mapPrivilegeProjectCode(uloPrivilegeProjectCode, iibPrivilegeProjectCode);
				iibVerResult.setPrivilegeProjectCode(iibPrivilegeProjectCode);
				
				//Product Saving
				ArrayList<PrivilegeProjectCodeProductSavingDataM>  uloPrvSavings = uloPrivilegeProjectCode.getPrivilegeProjectCodeProductSavings();
				if(null!=uloPrvSavings && uloPrvSavings.size()>0){
					for(PrivilegeProjectCodeProductSavingDataM  uloPrvSaving :uloPrvSavings){
						decisionservice_iib.PrivilegeProjectCodeProductSavingDataM  iibPrvSaving = new decisionservice_iib.PrivilegeProjectCodeProductSavingDataM();
						mapPrivilegeProjectCodeProductSaving(uloPrvSaving, iibPrvSaving);
						iibPrivilegeProjectCode.getPrivilegeProjectCodeProductSavings().add(iibPrvSaving);
					}
				}else{
					logger.debug("PrivilegeProjectCodesProductSaving is null!!");
				}
				
				//Product CCA
				ArrayList<PrivilegeProjectCodeProductCCADataM> uloPrvProductCCAs  =uloPrivilegeProjectCode.getPrivilegeProjectCodeProductCCAs();
				if(null!=uloPrvProductCCAs && uloPrvProductCCAs.size()>0){
					PrivilegeProjectCodeProductCCADataM uloPrvProductCCA  = uloPrvProductCCAs.get(0);
					decisionservice_iib.PrivilegeProjectCodeProductCCADataM  iibPrvProductCCA = new decisionservice_iib.PrivilegeProjectCodeProductCCADataM();
					mapPrivilegeProjectCodeProductCCA(uloPrvProductCCA, iibPrvProductCCA);
					iibPrivilegeProjectCode.setPrivilegeProjectCodeProductCCAs(iibPrvProductCCA);
				}else{
					logger.debug("PrivilegeProjectCodesProductCCA is null!!");
				}
				
				//Product Insurance
				ArrayList<PrivilegeProjectCodeProductInsuranceDataM> uloPrvProductInsurances = uloPrivilegeProjectCode.getPrivilegeProjectCodeProductInsurances();
				if(null!=uloPrvProductInsurances && uloPrvProductInsurances.size()>0){
					for(PrivilegeProjectCodeProductInsuranceDataM uloPrvProductInsurance:uloPrvProductInsurances){
						decisionservice_iib.PrivilegeProjectCodeProductInsuranceDataM iibPrvProductInsurance = new decisionservice_iib.PrivilegeProjectCodeProductInsuranceDataM();
						mapPrivilegeProjectCodeProductInsurance(uloPrvProductInsurance, iibPrvProductInsurance);
						iibPrivilegeProjectCode.getPrivilegeProjectCodeProductInsurances().add(iibPrvProductInsurance);
					}
				}else{
					logger.debug("PrivilegeProjectCodesProductInsurance is null!!");
				}
				
				//Project Code Document
				ArrayList<PrivilegeProjectCodeDocDataM> uloPrvProjectDocs = uloPrivilegeProjectCode.getPrivilegeProjectCodeDocs();
				if(null!=uloPrvProjectDocs && uloPrvProjectDocs.size()>0){
					PrivilegeProjectCodeDocDataM uloPrvProjectDoc = uloPrvProjectDocs.get(0);
					
					//Project Code Exception
					ArrayList<PrivilegeProjectCodeExceptionDocDataM>  uloPrvProjectCodeExceptions =uloPrvProjectDoc.getPrivilegeProjectCodeExceptionDocs();
					if(null!=uloPrvProjectCodeExceptions && uloPrvProjectCodeExceptions.size()>0){
						PrivilegeProjectCodeExceptionDocDataM  uloPrvProjectCodeException = uloPrvProjectCodeExceptions.get(0);
						decisionservice_iib.PrivilegeProjectCodeExceptionDocDataM  iibPrvException = new decisionservice_iib.PrivilegeProjectCodeExceptionDocDataM();
						mapPrivilegeProjectCodeExceptionDoc(uloPrvProjectCodeException, iibPrvException);
						iibPrivilegeProjectCode.setPrivilegeProjectCodeExceptionDoc(iibPrvException);
					}									
					
					//Project Code KAsset
					ArrayList<PrivilegeProjectCodeKassetDocDataM>  uloPrvKAssets  = uloPrvProjectDoc.getPrivilegeProjectCodeKassetDocs();
					if(null!=uloPrvKAssets && uloPrvKAssets.size()>0){
						PrivilegeProjectCodeKassetDocDataM uloPrvKAsset =uloPrvKAssets.get(0);
						decisionservice_iib.PrivilegeProjectCodeKassetDocDataM iibKAasset = new decisionservice_iib.PrivilegeProjectCodeKassetDocDataM();
						mapPrivilegeProjectCodeKassetDoc(uloPrvKAsset, iibKAasset);
						iibPrivilegeProjectCode.setPrivilegeProjectCodeKassetDoc(iibKAasset);
					}
					
					//Project Code Tradding
					ArrayList<PrivilegeProjectCodeProductTradingDataM> uloProjectCodeTradings = uloPrvProjectDoc.getPrivilegeProjectCodeProductTradings();
					if(null!=uloProjectCodeTradings && uloProjectCodeTradings.size()>0){
						PrivilegeProjectCodeProductTradingDataM  uloPrvProjectCodeTrading = uloProjectCodeTradings.get(0);
						decisionservice_iib.PrivilegeProjectCodeProductTradingDataM  iibPrvProjectCodeTrading = new decisionservice_iib.PrivilegeProjectCodeProductTradingDataM();
						mapPrivilegeProjectCodeProductTrading(uloPrvProjectCodeTrading, iibPrvProjectCodeTrading);
						iibPrivilegeProjectCode.setPrivilegeProjectCodeProductTrading(iibPrvProjectCodeTrading);
					}
				}
		}else{
			logger.debug("PrivilegeProjectCodes is null!!");
		}
	}
	
	
	public  void mapApplicationGroup(String decisionPoint,ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup){
		iibApplicationGroup.setApplicationDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplicationGroup.getApplicationDate()));
		iibApplicationGroup.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
		iibApplicationGroup.setApplicationGroupNo(uloApplicationGroup.getApplicationGroupNo());
		iibApplicationGroup.setApplicationTemplate(uloApplicationGroup.getApplicationTemplate());
		iibApplicationGroup.setApplicationType(uloApplicationGroup.getApplicationType());
		iibApplicationGroup.setApplyChannel(uloApplicationGroup.getApplyChannel());
		iibApplicationGroup.setApplyDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplicationGroup.getApplyDate()));
		iibApplicationGroup.setBundingId(uloApplicationGroup.getBundingId());
		iibApplicationGroup.setBundleFlag(uloApplicationGroup.getBundleFlag());
		iibApplicationGroup.setFraudApplicantFlag(uloApplicationGroup.getFraudApplicantFlag());
		iibApplicationGroup.setFraudCompanyFlag(uloApplicationGroup.getFraudCompanyFlag());
		iibApplicationGroup.setFraudFlag(uloApplicationGroup.getFraudFlag());
		iibApplicationGroup.setIsVetoEligible(uloApplicationGroup.getIsVetoEligible());
		iibApplicationGroup.setNumberOfSubmission(uloApplicationGroup.getMaxLifeCycle());
		iibApplicationGroup.setPolicyExSignOffBy(uloApplicationGroup.getPolicyExSignOffBy());
		iibApplicationGroup.setPolicyExSignOffDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplicationGroup.getPolicyExSignOffDate()));
		iibApplicationGroup.setProductId(uloApplicationGroup.getProductId());
		iibApplicationGroup.setRandomNo1(uloApplicationGroup.getRandomNo1());
		iibApplicationGroup.setRandomNo2(uloApplicationGroup.getRandomNo2());
		iibApplicationGroup.setRandomNo3(uloApplicationGroup.getRandomNo3());
		iibApplicationGroup.setCallAction(decisionPoint);	
		iibApplicationGroup.setSystemDate(DecisionServiceUtil.getSystemdate());
		iibApplicationGroup.setReCalculateActionFlag(uloApplicationGroup.getReCalculateActionFlag());
		iibApplicationGroup.setProcessAction(ProcessUtil.getProcessActionApplicationGroup(uloApplicationGroup));
		String vetoFlag=DecisionServiceUtil.FLAG_N;
		if(uloApplicationGroup.getMaxLifeCycle()>1){
			vetoFlag= DecisionServiceUtil.FLAG_Y;
		}
		iibApplicationGroup.setVetoFlag(vetoFlag);
	}
	
	
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication, decisionservice_iib.ApplicationDataM  iibApplication ){
		PersonalInfoDataM uloPersonalApplication = uloApplicationGroup.getPersonalInfoByRelation(uloApplication.getApplicationRecordId());
		if(null==uloPersonalApplication){
			uloPersonalApplication = ProcessUtil.getApplicationTypePersonalInfo(uloApplicationGroup);
		}
		
		iibApplication.setPersonalId(null!=uloPersonalApplication?uloPersonalApplication.getPersonalId():"");
		iibApplication.setApplicationGroupId(uloApplication.getApplicationGroupId());
		iibApplication.setApplicationNo(uloApplication.getApplicationNo());
		iibApplication.setApplicationRecordId(uloApplication.getApplicationRecordId());
		iibApplication.setApplicationType(uloApplication.getApplicationType());
		iibApplication.setApplyType(uloApplication.getApplicationType());
		iibApplication.setBookingBy(uloApplication.getBookingBy());
		iibApplication.setBookingDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplication.getBookingDate()));
		iibApplication.setBusinessClassId(uloApplication.getBusinessClassId());
		iibApplication.setCardlinkFlag(uloApplication.getCardlinkFlag());
		iibApplication.setCardlinkRefNo(uloApplication.getCardlinkRefNo());
		iibApplication.setCardlinkSeq(uloApplication.getCardlinkSeq());
		iibApplication.setDiffRequestResult(uloApplication.getDiffRequestResult());
		iibApplication.setExistingPriorityPass(uloApplication.getExistingPriorityPass());
		iibApplication.setFinalAppDecision(ProcessUtil.getDecisionFinalAppDecision(uloApplication.getFinalAppDecision()));
		iibApplication.setFinalAppDecisionBy(uloApplication.getFinalAppDecisionBy());
		iibApplication.setFinalAppDecisionDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplication.getFinalAppDecisionDate()));
		iibApplication.setIsVetoEligibleFlag(uloApplication.getIsVetoEligibleFlag());
		iibApplication.setLifeCycle(uloApplication.getLifeCycle());
		iibApplication.setMaincardRecordId(uloApplication.getMaincardRecordId());
		iibApplication.setMaxCreditLine(uloApplication.getMaxCreditLine());
		iibApplication.setOrgId(uloApplication.getProduct());
		iibApplication.setPolicyProgramId(uloApplication.getPolicyProgramId());
		iibApplication.setPolicySegmentId(uloApplication.getPolicySegmentId());
		iibApplication.setRequestProjectCode(uloApplication.getProjectCode());
		iibApplication.setRecommendProjectCode(uloApplication.getProjectCode());
		iibApplication.setRecommentCreditLine(uloApplication.getRecommentCreditLine());
		iibApplication.setRefApplicationRecordId(uloApplication.getRefApplicationRecordId());
		iibApplication.setSeq(uloApplication.getSeq());
		logger.debug("uloApplication Seq >>"+uloApplication.getSeq());
//		String productStatus =ProcessUtil.getDecisionServiceProductStatus(uloApplication.getRecommendDecision());
		String productStatus =ProcessUtil.getDecisionServiceProductStatus(uloApplication.getPreviousRecommendDecision());
		if(null == productStatus || "".equals(productStatus)){
			productStatus =ProcessUtil.getDecisionServiceProductStatus(uloApplication.getRecommendDecision());
		}
		logger.debug("productStatus>>"+productStatus);
		iibApplication.setPreviousProductStatus(productStatus);
		iibApplication.setRecommendDecision(productStatus);
		String[] referCriterias =ProcessUtil.selectReferCriterias(uloApplication.getReferCriteria());
		if(null!=referCriterias){
			for(String referCriteria : referCriterias){
				decisionservice_iib.ReferCriteriaDataM iibReferCririaDataM = new decisionservice_iib.ReferCriteriaDataM();
				iibReferCririaDataM.setName(referCriteria);		
				iibApplication.getReferCriteria().add(iibReferCririaDataM);
			}				
		}
		
		iibApplication.setSpSignOffFlag(uloApplication.getSpSignOffFlag());
		iibApplication.setSpSignoffAuthBy(uloApplication.getSpSignoffAuthBy());
		iibApplication.setSpSignoffDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplication.getSpSignoffDate()));
		iibApplication.setTcbAccountNo(uloApplication.getTcbAccountNo());
		iibApplication.setVlinkFlag(uloApplication.getVlinkFlag());
		iibApplication.setMainApplicationRecordId(uloApplication.getMaincardRecordId());		
		LoanDataM uloLoan = uloApplication.getLoan();
		if(null!=uloLoan){
			if(null!=uloLoan.getCard()){			
				iibApplication.setSubProduct(ProcessUtil.getSubProduct(uloApplication.getProduct(), uloLoan.getCard().getCardType()));
			}
		}
		iibApplication.setReExecuteKeyProgramFlag(uloApplication.getReExecuteKeyProgramFlag());
		
		//KPL Additional
		iibApplication.setBlockFlag(uloApplication.getSavingPlusFlag());
	}
	
	public void mapBilingCycle(ApplicationGroupDataM uloApplicationGroup, LoanDataM uloLoan,decisionservice_iib.ApplicationDataM iibApplication){
		PaymentMethodDataM uloPaymentMenthod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
		if(null!=uloPaymentMenthod && null!=uloPaymentMenthod.getDueCycle())
			iibApplication.setBillingCycle(DecisionServiceUtil.bigDecimalToInt(uloPaymentMenthod.getDueCycle()));
	}
	
	public  void mapApplicationImage(ApplicationImageDataM uloAppImage, decisionservice_iib.ApplicationImageDataM iibAppImage){
		iibAppImage.setActiveStatus(uloAppImage.getActiveStatus());
		iibAppImage.setActivityType(uloAppImage.getActivityType());
		iibAppImage.setAppImgId(uloAppImage.getAppImgId());
		iibAppImage.setApplicationGroupId(uloAppImage.getApplicationGroupId());
		iibAppImage.setFileName(uloAppImage.getFileName());
		iibAppImage.setFileType(uloAppImage.getFileType());
		iibAppImage.setPath(uloAppImage.getPath());
		iibAppImage.setRealPath(uloAppImage.getRealPath());
		iibAppImage.setRefNo(uloAppImage.getRefNo());
		iibAppImage.setUserId(uloAppImage.getUserId());
	}
	
	public  void mapFinalVerifyResult(FinalVerifyResultDataM uloFinalVerResult,decisionservice_iib.FinalVerifyResultDataM iibFinalVerResult){
		iibFinalVerResult.setVerifyId(null);
		iibFinalVerResult.setVerifyResultCode(uloFinalVerResult.getVerifyResultCode());
		iibFinalVerResult.setVerifyResultDesc(uloFinalVerResult.getVerifyResultDesc());
	}
	
	public  void mapPaymentMethod(PaymentMethodDataM uloPaymentMethod,decisionservice_iib.PaymentMethodDataM iibPaymentMethod){
		iibPaymentMethod.setAccountName(uloPaymentMethod.getAccountName());		
		iibPaymentMethod.setAccountNo(uloPaymentMethod.getAccountNo());
		iibPaymentMethod.setAccountType(uloPaymentMethod.getAccountType());
		iibPaymentMethod.setBankCode(uloPaymentMethod.getBankCode());
		iibPaymentMethod.setDueCycle(uloPaymentMethod.getDueCycle());
		iibPaymentMethod.setPaymentMethod(uloPaymentMethod.getPaymentMethod());
		iibPaymentMethod.setPaymentMethodId(uloPaymentMethod.getPaymentMethodId());
		iibPaymentMethod.setPaymentRatio(uloPaymentMethod.getPaymentRatio());
		iibPaymentMethod.setPersonalId(uloPaymentMethod.getPersonalId());
		iibPaymentMethod.setProductType(uloPaymentMethod.getProductType());
	}
	
	public  void mapPersonalInfo(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo){
		iibPersonalInfo.setWfVerfiedFlag(ProcessUtil.getIsInformationComplete(uloPersonalInfo.getPersonalId(), uloApplicationGroup) ? DecisionServiceUtil.FLAG_N : DecisionServiceUtil.FLAG_Y);
		
		if(ProcessUtil.PERSONAL_TYPE.INFOMATION.equals(uloPersonalInfo.getPersonalType())){
			iibPersonalInfo.setPersonalType(ProcessUtil.PERSONAL_TYPE.APPLICANT);
			iibPersonalInfo.setInfoFlag(DecisionServiceUtil.FLAG_Y);
			logger.debug("set personal type =A");
		}else{
			iibPersonalInfo.setPersonalType(uloPersonalInfo.getPersonalType());
		}

		iibPersonalInfo.setApplicationGroupId(uloPersonalInfo.getApplicationGroupId());
		iibPersonalInfo.setAssetsAmount(uloPersonalInfo.getAssetsAmount());
		iibPersonalInfo.setBirthDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloPersonalInfo.getBirthDate()));
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
		iibPersonalInfo.setCompanyCisId(uloPersonalInfo.getCompanyCISId());
		iibPersonalInfo.setContractTime(uloPersonalInfo.getContactTime());
		iibPersonalInfo.setCustomerType(ProcessUtil.CUSTOMER_TYPE.INDIVIDUAL);
		iibPersonalInfo.setDegree(uloPersonalInfo.getDegree());
		iibPersonalInfo.setDisclosureFlag(uloPersonalInfo.getDisclosureFlag());
		iibPersonalInfo.setEmploymentType(uloPersonalInfo.getEmploymentType());
		iibPersonalInfo.setEnFirstName(uloPersonalInfo.getEnFirstName());
		iibPersonalInfo.setEnLastName(uloPersonalInfo.getEnLastName());
		iibPersonalInfo.setEnMidName(uloPersonalInfo.getEnMidName());
		iibPersonalInfo.setEnTitleCode(uloPersonalInfo.getEnTitleCode());
		iibPersonalInfo.setFreelanceIncome(uloPersonalInfo.getFreelanceIncome());
		iibPersonalInfo.setFreelanceType(uloPersonalInfo.getFreelanceType());
		iibPersonalInfo.setGender(uloPersonalInfo.getGender());
		iibPersonalInfo.setIdNo(uloPersonalInfo.getIdno());
		iibPersonalInfo.setMailingAddress(uloPersonalInfo.getMailingAddress());
		iibPersonalInfo.setMarried(uloPersonalInfo.getMarried());
		iibPersonalInfo.setMobileNo(uloPersonalInfo.getMobileNo());
		iibPersonalInfo.setMonthlyExpense(uloPersonalInfo.getMonthlyExpense());
		iibPersonalInfo.setMonthlyIncome(uloPersonalInfo.getMonthlyIncome());
		iibPersonalInfo.setNationality(uloPersonalInfo.getNationality());
		iibPersonalInfo.setNetProfitIncome(uloPersonalInfo.getNetProfitIncome());
		iibPersonalInfo.setNumberOfChild(uloPersonalInfo.getNoOfChild());
		iibPersonalInfo.setOccpationOther(uloPersonalInfo.getOccpationOther());
		iibPersonalInfo.setPersonalId(uloPersonalInfo.getPersonalId());
		iibPersonalInfo.setPreviousCompanyName(uloPersonalInfo.getPrevCompany());
		iibPersonalInfo.setPreviousCompanyTitle(uloPersonalInfo.getPrevCompanyTitle());
		iibPersonalInfo.setPreviousWorkMonth(uloPersonalInfo.getPrevWorkMonth());
		iibPersonalInfo.setPreviousWorkYear(uloPersonalInfo.getPrevWorkYear());
		iibPersonalInfo.setProfession(uloPersonalInfo.getProfession());
		iibPersonalInfo.setProfessionOther(uloPersonalInfo.getProfessionOther());
		iibPersonalInfo.setReceiveIncomeBank(uloPersonalInfo.getReceiveIncomeBank());
		iibPersonalInfo.setRelationshipType(uloPersonalInfo.getRelationshipType());
		iibPersonalInfo.setSalary(uloPersonalInfo.getSalary());
		iibPersonalInfo.setSorceOfIncome(uloPersonalInfo.getSorceOfIncome());
		iibPersonalInfo.setSorceOfIncomeOther(uloPersonalInfo.getSorceOfIncomeOther());
		iibPersonalInfo.setSrcOthIncBonus(uloPersonalInfo.getSrcOthIncBonus());
		iibPersonalInfo.setSrcOthIncCommission(uloPersonalInfo.getSrcOthIncCommission());
		iibPersonalInfo.setSrcOthIncOther(uloPersonalInfo.getSrcOthIncOther());
		iibPersonalInfo.setThFirstName(uloPersonalInfo.getThFirstName());
		iibPersonalInfo.setThLastName(uloPersonalInfo.getThLastName());
		iibPersonalInfo.setThMidName(uloPersonalInfo.getThMidName());
		iibPersonalInfo.setThTitleCode(uloPersonalInfo.getThTitleCode());
		iibPersonalInfo.setTotalWorkMonth(uloPersonalInfo.getTotWorkMonth());
		iibPersonalInfo.setTotalWorkYear(uloPersonalInfo.getTotWorkYear());
		iibPersonalInfo.setTypeofFin(uloPersonalInfo.getTypeOfFin());
		iibPersonalInfo.setVatRegisterFlag(uloPersonalInfo.getVatRegistFlag());
		if(ProcessUtil.CIDTYPE_PASSPORT.equals(uloPersonalInfo.getCidType())){
			iibPersonalInfo.setPassportExpiryDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloPersonalInfo.getCidExpDate()));
		}
		iibPersonalInfo.setVisaExpiryDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloPersonalInfo.getVisaExpDate()));
		iibPersonalInfo.setVisaType(uloPersonalInfo.getVisaType());
		iibPersonalInfo.setWorkPermitExpDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloPersonalInfo.getWorkPermitExpDate()));
		iibPersonalInfo.setWorkPermitNo(uloPersonalInfo.getWorkPermitNo());
		iibPersonalInfo.setPositionType(ProcessUtil.getPositionType(uloPersonalInfo.getPositionCode()));
		iibPersonalInfo.setPositionLevel(uloPersonalInfo.getPositionLevel());
		iibPersonalInfo.setSalaryLevel(ProcessUtil.getSalaryLevel(uloPersonalInfo.getPositionCode()));
		iibPersonalInfo.setOccupation(uloPersonalInfo.getOccupation());
		iibPersonalInfo.setTotVerifiedIncome(uloPersonalInfo.getTotVerifiedIncome());
		
		//KPL Additional
		iibPersonalInfo.setSpecialMerchant(uloPersonalInfo.getSpecialMerchantType());
		iibPersonalInfo.setCompanyType(uloPersonalInfo.getSpecialMerchantType());
		
		//CR247
		if(!ServiceUtil.empty(uloPersonalInfo.getPayrollDate())){
			iibPersonalInfo.setPayDate(DecisionServiceUtil.parseInt( uloPersonalInfo.getPayrollDate() ) );
		}
		
		List<DocumentSlaTypeDataM> docSlAs = DecisionServiceUtil.getDocumentSLAType(uloApplicationGroup, uloPersonalInfo);
		if(null!=docSlAs){
			iibPersonalInfo.getDocumentSlaTypes().addAll(docSlAs);
		}else{
			logger.debug("DOCUMENT SLA IS NULL");
		}
 	
	}
	
	public  void mapAddress(AddressDataM uloAddress,decisionservice_iib.AddressDataM iibAddress){
		iibAddress.setAddress(uloAddress.getAddress());
		iibAddress.setAddress1(uloAddress.getAddress1());
		iibAddress.setAddress2(uloAddress.getAddress2());
		iibAddress.setAddress3(uloAddress.getAddress3());
		iibAddress.setAddressFormat(uloAddress.getAddressFormat());
		iibAddress.setAddressId(uloAddress.getAddressId());
		iibAddress.setAddressType(uloAddress.getAddressType());
		iibAddress.setAdrsts(uloAddress.getAdrsts());
		iibAddress.setAmphur(uloAddress.getAmphur());
		iibAddress.setBranchName(uloAddress.getBranchName());
		iibAddress.setBranchType(uloAddress.getBranchType());
		iibAddress.setBuildingType(uloAddress.getBuildingType());
		iibAddress.setBuilding(uloAddress.getBuilding());
		iibAddress.setCompanyName(uloAddress.getCompanyName());
		iibAddress.setCompanyTitle(uloAddress.getCompanyTitle());
		iibAddress.setCountry(uloAddress.getCountry());
		iibAddress.setCompanyTitle(uloAddress.getCompanyTitle());
		iibAddress.setDepartment(uloAddress.getDepartment());
		iibAddress.setExt1(uloAddress.getExt1());
		iibAddress.setExt2(uloAddress.getExt2());
		iibAddress.setFax(uloAddress.getFax());
		iibAddress.setFloor(uloAddress.getFloor());
		iibAddress.setMobile(uloAddress.getMobile());
		iibAddress.setMoo(uloAddress.getMoo());
		iibAddress.setPersonalId(uloAddress.getPersonalId());
		iibAddress.setPhone1(uloAddress.getPhone1());
		iibAddress.setPhone2(uloAddress.getPhone2());
		iibAddress.setProvince(uloAddress.getProvince());
		iibAddress.setProvinceDesc(uloAddress.getProvinceDesc());
		iibAddress.setRents(uloAddress.getRents());
		iibAddress.setResidem(uloAddress.getResidem());
		iibAddress.setResidey(uloAddress.getResidey());
		iibAddress.setRoad(uloAddress.getRoad());
		iibAddress.setRoom(uloAddress.getRoom());
		iibAddress.setSeq(uloAddress.getSeq());
		iibAddress.setSoi(uloAddress.getSoi());
		iibAddress.setState(uloAddress.getState());
		iibAddress.setTambol(uloAddress.getTambol());
		iibAddress.setVatCode(uloAddress.getVatCode());
		iibAddress.setVatRegistration(uloAddress.getVatRegistration());
		iibAddress.setVilapt(uloAddress.getVilapt());
		iibAddress.setZipcode(uloAddress.getZipcode());
		iibAddress.setResideMonth(uloAddress.getResidem());
		iibAddress.setResideYear(uloAddress.getResidey());
		
	}
	
	public  void mapDebtInfo(ArrayList<DebtInfoDataM> uloDebtInfos,decisionservice_iib.DebtInfoDataM iibDebtInfo){
		DebtInfoDataM  uloWelfareDebtInfo =ProcessUtil.filterDebtInfo(uloDebtInfos,ProcessUtil.DEBT_TYPE.WELFARE);
		DebtInfoDataM  uloCommercialDebtInfo =ProcessUtil.filterDebtInfo(uloDebtInfos,ProcessUtil.DEBT_TYPE.COMMERCIAL);
		DebtInfoDataM  uloOtherDebtInfo =ProcessUtil.filterDebtInfo(uloDebtInfos,ProcessUtil.DEBT_TYPE.OTHER);
		
		if(null!=uloWelfareDebtInfo){
			iibDebtInfo.setWelfareAmount(uloWelfareDebtInfo.getDebtAmt());
			iibDebtInfo.setPersonalId(uloWelfareDebtInfo.getPersonalId());
		}
		if(null!=uloCommercialDebtInfo){
			iibDebtInfo.setDebtCommercialAmount(uloCommercialDebtInfo.getDebtAmt());
			iibDebtInfo.setPersonalId(uloCommercialDebtInfo.getPersonalId());
		}
		if(null!=uloOtherDebtInfo){
			iibDebtInfo.setOtherDebtAmount(uloOtherDebtInfo.getDebtAmt());
			iibDebtInfo.setPersonalId(uloOtherDebtInfo.getPersonalId());
		}
		iibDebtInfo.setTotalOtherDebtAmount(ProcessUtil.totalOtherDebtAmount(iibDebtInfo));
	}
	
	public  void mapIncomeInfo(IncomeInfoDataM uloIncomeInfo,decisionservice_iib.IncomeInfoDataM iibIncomeInfo){
		iibIncomeInfo.setCompareFlag(uloIncomeInfo.getCompareFlag());
		iibIncomeInfo.setIncomeId(uloIncomeInfo.getIncomeId());
		iibIncomeInfo.setPersonalId(uloIncomeInfo.getPersonalId());
		iibIncomeInfo.setRemark(uloIncomeInfo.getRemark());
		iibIncomeInfo.setSeq(uloIncomeInfo.getSeq());		
	}
	
	public  void mapBankStatement( BankStatementDataM uloBankStatement, decisionservice_iib.BankStatementDataM iibBankStatement){
		iibBankStatement.setAdditionalCode(uloBankStatement.getAdditionalCode());
		iibBankStatement.setBankCode(uloBankStatement.getBankCode());
		iibBankStatement.setBankStatementId(uloBankStatement.getBankStatementId());
		iibBankStatement.setStatementCode(uloBankStatement.getStatementCode());		
	}
	
	public  void mapBankStatementDetail( BankStatementDetailDataM uloBankStatementDetail, decisionservice_iib.BankStatementDetailDataM iibBankStatementDetail  ){
		iibBankStatementDetail.setAmount(uloBankStatementDetail.getAmount());
		iibBankStatementDetail.setBankStatementDetailId(uloBankStatementDetail.getBankStatementDetailId());
		iibBankStatementDetail.setBankStatementId(uloBankStatementDetail.getBankStatementId());
		iibBankStatementDetail.setMonth(uloBankStatementDetail.getMonth());
		iibBankStatementDetail.setSeq(uloBankStatementDetail.getSeq());
		iibBankStatementDetail.setYear(uloBankStatementDetail.getYear());
	}
	
	public  void mapClosedEndFund(ClosedEndFundDataM uloCloseEndFund, decisionservice_iib.ClosedEndFundDataM iibCloseEndFund){
		iibCloseEndFund.setAccountBalance(uloCloseEndFund.getAccountBalance());
		iibCloseEndFund.setAccountName(uloCloseEndFund.getAccountName());
		iibCloseEndFund.setAccountNo(uloCloseEndFund.getAccountNo());
		iibCloseEndFund.setBankCode(uloCloseEndFund.getBankCode());
		iibCloseEndFund.setClsEndFundId(uloCloseEndFund.getClsEndFundId());
		iibCloseEndFund.setFundName(uloCloseEndFund.getFundName());
		iibCloseEndFund.setHoldingRatio(uloCloseEndFund.getHoldingRatio());
		iibCloseEndFund.setOpenDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloCloseEndFund.getOpenDate()));
	}
	
	public  void mapFinancialInstrument(FinancialInstrumentDataM uloFinancialInst , decisionservice_iib.FinancialInstrumentDataM iibFinalcialInst  ){
		iibFinalcialInst.setCurrentBalance(uloFinancialInst.getCurrentBalance());
		iibFinalcialInst.setExpireDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloFinancialInst.getExpireDate()));
		iibFinalcialInst.setFinInstrument(uloFinancialInst.getFinInstrument());
		iibFinalcialInst.setHolderName(uloFinancialInst.getHolderName());
		iibFinalcialInst.setHoldingRatio(uloFinancialInst.getHoldingRatio());
		iibFinalcialInst.setInstrumentType(uloFinancialInst.getInstrumentType());
		iibFinalcialInst.setInstrumentTypeDesc(uloFinancialInst.getInstrumentTypeDesc());
		iibFinalcialInst.setIssuerName(uloFinancialInst.getIssuerName());
		iibFinalcialInst.setOpenDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloFinancialInst.getOpenDate()));
	}
	
	public  void mapFixedAccount(FixedAccountDataM uloFixedAccount, decisionservice_iib.FixedAccountDataM iibFixedAccount){
		iibFixedAccount.setAccountBalance(uloFixedAccount.getAccountBalance());
		iibFixedAccount.setAccountName(uloFixedAccount.getAccountName());
		iibFixedAccount.setAccountNo(uloFixedAccount.getAccountNo());
		iibFixedAccount.setBankCode(uloFixedAccount.getBankCode());
		iibFixedAccount.setFixedAccId(uloFixedAccount.getFixedAccId());
		iibFixedAccount.setHoldingRatio(uloFixedAccount.getHoldingRatio());
		iibFixedAccount.setOpenDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloFixedAccount.getOpenDate()));
	}
	
	public  void mapKviIncome(KVIIncomeDataM uloKVIincome, decisionservice_iib.KviIncomeDataM iibKVIIncome){
		iibKVIIncome.setKviId(uloKVIincome.getKviId());
		iibKVIIncome.setKviFid(uloKVIincome.getKviFid());
		iibKVIIncome.setPercentChequeReturn(uloKVIincome.getPercentChequeReturn());
		iibKVIIncome.setTokenId(uloKVIincome.getTokenId());
		iibKVIIncome.setVerifiedIncome(uloKVIincome.getVerifiedIncome());
	}
	
	public  void mapMonthlyTawi50(MonthlyTawi50DataM uloMonthlyTawi50 ,decisionservice_iib.MonthlyTawi50DataM iibMonthlyTawi50){
		iibMonthlyTawi50.setCompanyName(uloMonthlyTawi50.getCompanyName());
		iibMonthlyTawi50.setMonthlyTawiId(uloMonthlyTawi50.getMonthlyTawiId());
		iibMonthlyTawi50.setTawiIncomeType(uloMonthlyTawi50.getTawiIncomeType());	
	}
	
	public  void mapMonthlyTawi50Detail(MonthlyTawi50DetailDataM uloMonthlyTawi50Detail ,decisionservice_iib.MonthlyTawi50DetailDataM iibMonthlyTawi50Detail){
		iibMonthlyTawi50Detail.setAmount(uloMonthlyTawi50Detail.getAmount());
		iibMonthlyTawi50Detail.setMonth(uloMonthlyTawi50Detail.getMonth());
		iibMonthlyTawi50Detail.setMonthlyTawiDetailId(uloMonthlyTawi50Detail.getMonthlyTawiDetailId());
		iibMonthlyTawi50Detail.setMonthlyTawiId(uloMonthlyTawi50Detail.getMonthlyTawiId());
		iibMonthlyTawi50Detail.setSeq(uloMonthlyTawi50Detail.getSeq());
		iibMonthlyTawi50Detail.setYear(uloMonthlyTawi50Detail.getYear());
	}
	
	public  void mapOpenedEndFund(OpenedEndFundDataM uloOpenedEndFund,decisionservice_iib.OpenedEndFundDataM iibOpenedEndFund){
		iibOpenedEndFund.setAccountName(uloOpenedEndFund.getAccountName());
		iibOpenedEndFund.setAccountNo(uloOpenedEndFund.getAccountNo());
		iibOpenedEndFund.setBankCode(uloOpenedEndFund.getBankCode());
		iibOpenedEndFund.setFundName(uloOpenedEndFund.getFundName());
		iibOpenedEndFund.setHoldingRatio(uloOpenedEndFund.getHoldingRatio());
		iibOpenedEndFund.setOpenDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloOpenedEndFund.getOpenDate()));
		iibOpenedEndFund.setOpnEndFundId(uloOpenedEndFund.getOpnEndFundId());
	}
	
	public  void mapOpenedEndFundDetail(OpenedEndFundDetailDataM uloOpenedEndFundDetail,decisionservice_iib.OpenedEndFundDetailDataM iibOpenedEndFundDetail){
		iibOpenedEndFundDetail.setAmount(uloOpenedEndFundDetail.getAmount());
		iibOpenedEndFundDetail.setMonth(uloOpenedEndFundDetail.getMonth());
		iibOpenedEndFundDetail.setOpnEndFundDetailId(uloOpenedEndFundDetail.getOpnEndFundDetailId());
		iibOpenedEndFundDetail.setOpnEndFundId(uloOpenedEndFundDetail.getOpnEndFundId());
		iibOpenedEndFundDetail.setSeq(uloOpenedEndFundDetail.getSeq());
		iibOpenedEndFundDetail.setYear(uloOpenedEndFundDetail.getYear());
	}
	
	public  void mapPayroll(PayrollDataM uloPayroll , decisionservice_iib.PayrollDataM iibPayroll){
		iibPayroll.setIncome(uloPayroll.getIncome());
		iibPayroll.setNoOfEmployee(uloPayroll.getNoOfEmployee());
		iibPayroll.setPayrollId(uloPayroll.getPayrollId());
	}
	
	public  void mapPayslip(PayslipDataM uloPaySlip ,decisionservice_iib.PayslipDataM iibPaySlip){
		iibPaySlip.setBonus(uloPaySlip.getBonus());
		iibPaySlip.setBonusMonth(uloPaySlip.getBonusMonth());
		iibPaySlip.setBonusYear(uloPaySlip.getBonusYear());
		iibPaySlip.setNoMonth(uloPaySlip.getNoMonth());
		iibPaySlip.setPayslipId(uloPaySlip.getPayslipId());
		iibPaySlip.setSalaryDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloPaySlip.getSalaryDate()));
		iibPaySlip.setSpacialPension(uloPaySlip.getSpacialPension());
		iibPaySlip.setSumIncome(uloPaySlip.getSumIncome());
		iibPaySlip.setSumOthIncome(uloPaySlip.getSumOthIncome());
		iibPaySlip.setSumSso(uloPaySlip.getSumSso());
	}
	
	public  void mapPayslipMonthly(PayslipMonthlyDataM uloPaySlipMonthly ,decisionservice_iib.PayslipMonthlyDataM iibPaySlipMonthly){
		iibPaySlipMonthly.setIncomeDesc(uloPaySlipMonthly.getIncomeDesc());
		iibPaySlipMonthly.setIncomeOthDesc(uloPaySlipMonthly.getIncomeOthDesc());
		iibPaySlipMonthly.setIncomeType(uloPaySlipMonthly.getIncomeType());
		iibPaySlipMonthly.setPayslipId(uloPaySlipMonthly.getPayslipId());
		iibPaySlipMonthly.setPayslipMonthlyId(uloPaySlipMonthly.getPayslipMonthlyId());
		iibPaySlipMonthly.setSeq(uloPaySlipMonthly.getSeq());
		
		if(null!=uloPaySlipMonthly.getPayslipMonthlyDetails() && uloPaySlipMonthly.getPayslipMonthlyDetails().size()>0){
			for(PayslipMonthlyDetailDataM uloPaySlipMonthlyDetail : uloPaySlipMonthly.getPayslipMonthlyDetails()){
				decisionservice_iib.PayslipMonthlyDetailDataM iibPaySlipMonthlyDetail = new decisionservice_iib.PayslipMonthlyDetailDataM();
				mapPayslipMonthlyDetail(uloPaySlipMonthlyDetail, iibPaySlipMonthlyDetail);
				iibPaySlipMonthly.getPayslipMonthlyDetails().add(iibPaySlipMonthlyDetail);				
			}		
		}
		
	}
	
	public  void mapPayslipMonthlyDetail(PayslipMonthlyDetailDataM uloPaySlipMonthlyDetail ,decisionservice_iib.PayslipMonthlyDetailDataM iibPaySlipMonthlyDetail){
		iibPaySlipMonthlyDetail.setAmount(uloPaySlipMonthlyDetail.getAmount());
		iibPaySlipMonthlyDetail.setMonth(uloPaySlipMonthlyDetail.getMonth());
		iibPaySlipMonthlyDetail.setPayslipMonthlyDetailId(uloPaySlipMonthlyDetail.getPayslipMonthlyDetailId());
		iibPaySlipMonthlyDetail.setPayslipMonthlyId(uloPaySlipMonthlyDetail.getPayslipMonthlyId());
		iibPaySlipMonthlyDetail.setSeq(uloPaySlipMonthlyDetail.getSeq());
		iibPaySlipMonthlyDetail.setYear(uloPaySlipMonthlyDetail.getYear());
	}
	
	public  void mapPreviousIncome(PreviousIncomeDataM uloPreviosIncome ,decisionservice_iib.PreviousIncomeDataM iibPreviousIncome){
		iibPreviousIncome.setIncome(uloPreviosIncome.getIncome());
		iibPreviousIncome.setIncomeDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloPreviosIncome.getIncomeDate()));
		iibPreviousIncome.setPreviousIncomeId(uloPreviosIncome.getIncomeId());
		iibPreviousIncome.setProduct(uloPreviosIncome.getProduct());
	}
	
	public  void mapSalaryCert(SalaryCertDataM uloSalaryCert ,decisionservice_iib.SalaryCertDataM iibSalaryCert){
		iibSalaryCert.setCompanyName(uloSalaryCert.getCompanyName());
		iibSalaryCert.setIncome(uloSalaryCert.getIncome());
		iibSalaryCert.setOtherIncome(uloSalaryCert.getOtherIncome());
		iibSalaryCert.setTotalIncome(uloSalaryCert.getTotalIncome());
		iibSalaryCert.setSalaryCertId(uloSalaryCert.getSalaryCertId());
		
	}
	
	public  void mapSavingAccount(SavingAccountDataM uloSavingAccount ,decisionservice_iib.SavingAccountDataM iibSavingAccount){
		iibSavingAccount.setAccountBalance(uloSavingAccount.getAccountBalance());
		iibSavingAccount.setAccountName(uloSavingAccount.getAccountName());
		iibSavingAccount.setAccountNo(uloSavingAccount.getAccountNo());
		iibSavingAccount.setBankCode(uloSavingAccount.getBankCode());
		iibSavingAccount.setHoldingRatio(uloSavingAccount.getHoldingRatio());
		iibSavingAccount.setOpenDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloSavingAccount.getOpenDate()));
		iibSavingAccount.setSavingAccId(uloSavingAccount.getSavingAccId());
	}
	
	public  void mapSavingAccountDetail(SavingAccountDetailDataM uloSavingAccountDetail ,decisionservice_iib.SavingAccountDetailDataM iibSavingAccountDetail){
		iibSavingAccountDetail.setAmount(uloSavingAccountDetail.getAmount());
		iibSavingAccountDetail.setMonth(uloSavingAccountDetail.getMonth());
		iibSavingAccountDetail.setSavingAccDetailId(uloSavingAccountDetail.getSavingAccDetailId());
		iibSavingAccountDetail.setSavingAccId(uloSavingAccountDetail.getSavingAccId());
		iibSavingAccountDetail.setSeq(uloSavingAccountDetail.getSeq());
		iibSavingAccountDetail.setYear(uloSavingAccountDetail.getYear());
	}
	
	public  void mapTaweesapDataM(TaweesapDataM uloTaweesap ,decisionservice_iib.TaweesapDataM iibTaweesap){
		iibTaweesap.setAum(uloTaweesap.getAum());
		iibTaweesap.setTweesapId(uloTaweesap.getTweesapId());
	}
	
	public  void mapYearlyTawi50(YearlyTawi50DataM uloYearlyTawi50,decisionservice_iib.YearlyTawi50DataM iibYearlyTawi50){
		iibYearlyTawi50.setCompanyName(uloYearlyTawi50.getCompanyName());
		iibYearlyTawi50.setIncome401(uloYearlyTawi50.getIncome401());
		iibYearlyTawi50.setIncome402(uloYearlyTawi50.getIncome402());
		iibYearlyTawi50.setMonth(uloYearlyTawi50.getMonth());
		iibYearlyTawi50.setSumSso(uloYearlyTawi50.getSumSso());
		iibYearlyTawi50.setYear(uloYearlyTawi50.getYear());
		iibYearlyTawi50.setYearlyTawiId(uloYearlyTawi50.getYearlyTawiId());
	}
	
	public  void mapIncomeSource(IncomeSourceDataM uloIncomeSource,decisionservice_iib.IncomeSourceDataM iibIncomeSource){
		iibIncomeSource.setIncomeSource(uloIncomeSource.getIncomeSource());
		iibIncomeSource.setIncomeSourceId(uloIncomeSource.getIncomeSource());
		iibIncomeSource.setPersonalId(uloIncomeSource.getPersonalId());
	}
	
	public  void mapNcbInfo(NcbInfoDataM uloNcbInfo,decisionservice_iib.NcbInfoDataM iibNcbInfo){
		iibNcbInfo.setConsentRefNo(uloNcbInfo.getConsentRefNo());
		iibNcbInfo.setNumTimesOfEnquiry(uloNcbInfo.getNoOfTimesEnquiry());
		iibNcbInfo.setNumTimesOfEnquiry6Month(uloNcbInfo.getNoOfTimesEnquiry6M());
		iibNcbInfo.setTrackingCode(uloNcbInfo.getTrackingCode());
	}
	public void mapKcbsReason(NcbInfoDataM uloNcbInfo,decisionservice_iib.KcbsResponse iibKcbsReason){
		iibKcbsReason.setConsentRefNo(uloNcbInfo.getConsentRefNo());
	}
	public  void mapNcbAccount(NcbAccountDataM uloNcbAccount,decisionservice_iib.NcbAccountDataM iibNcbAccount){

		iibNcbAccount.setAccountNumber(uloNcbAccount.getAccountNumber());
		iibNcbAccount.setAccountStatus(uloNcbAccount.getAccountStatus());
		iibNcbAccount.setAccountType(uloNcbAccount.getAccountType());
		iibNcbAccount.setAmtOwed(uloNcbAccount.getAmtOwed());
		iibNcbAccount.setAmtPastDue(uloNcbAccount.getAmtPastDue());
		iibNcbAccount.setAsOfDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getAsOfDate()));
		iibNcbAccount.setCollateral1(uloNcbAccount.getCollateral1());
		iibNcbAccount.setCollateral2(uloNcbAccount.getCollateral2());
		iibNcbAccount.setCollateral3(uloNcbAccount.getCollateral3());
		iibNcbAccount.setCreditCardType(uloNcbAccount.getCreditCardType());
		iibNcbAccount.setCreditTypeFlag(uloNcbAccount.getCreditTypeFlag());
		iibNcbAccount.setCreditlimOrloanamt(uloNcbAccount.getCreditlimOrloanamt());
		iibNcbAccount.setCurrencyCode(uloNcbAccount.getCurrencyCode());
		iibNcbAccount.setDateAccClosed(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getDateAccClosed()));
		iibNcbAccount.setDateAccOpened(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getDateAccOpened()));
		iibNcbAccount.setDateOfLastPmt(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getDateOfLastPmt()));
		iibNcbAccount.setDefaultDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getDefaultDate()));
		iibNcbAccount.setGroupSeq(uloNcbAccount.getGroupSeq());
		iibNcbAccount.setInstallAmt(uloNcbAccount.getInstallAmt());
		iibNcbAccount.setInstallFreq(uloNcbAccount.getInstallFreq());
		iibNcbAccount.setInstallNoofpay(uloNcbAccount.getInstallNoofpay());
		iibNcbAccount.setLastDebtRestDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getLastDebtRestDate()));
		iibNcbAccount.setLoanClass(uloNcbAccount.getLoanClass());
		iibNcbAccount.setLastDebtRestDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getLastDebtRestDate()));
		iibNcbAccount.setLoanClass(uloNcbAccount.getLoanClass());
		iibNcbAccount.setLoanObjective(uloNcbAccount.getLoanObjective());
		iibNcbAccount.setMatch(uloNcbAccount.getMatch());
		iibNcbAccount.setMemberCode(uloNcbAccount.getMemberCode());
		iibNcbAccount.setNegativeFlag(uloNcbAccount.getNegativeFlag());
		iibNcbAccount.setNumberOfCoborr(uloNcbAccount.getNumberofCoborr());
		iibNcbAccount.setOwnerShipIndicator(uloNcbAccount.getOwnershipIndicator());
		iibNcbAccount.setPercentPayment(uloNcbAccount.getPercentPayment());
		iibNcbAccount.setPmtHist1(uloNcbAccount.getPmtHist1());
		iibNcbAccount.setPmtHist2(uloNcbAccount.getPmtHist2());
		iibNcbAccount.setPmtHistEnddate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getPmtHistEnddate()));
		iibNcbAccount.setPmtHistStartdate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAccount.getPmtHistStartdate()));
		iibNcbAccount.setPositiveFlag(uloNcbAccount.getPositiveFlag());
		iibNcbAccount.setSegmentValue(uloNcbAccount.getSegmentValue());
		iibNcbAccount.setSeq(uloNcbAccount.getSeq());
		iibNcbAccount.setTrackingCode(uloNcbAccount.getTrackingCode());
		iibNcbAccount.setUnitMake(uloNcbAccount.getUnitMake());
		iibNcbAccount.setUnitModel(uloNcbAccount.getUnitModel());
				
	}
	public  void mapNcbAccountReport(NcbAccountReportDataM uloNcbAccReport,decisionservice_iib.NcbAccountReportDataM iibNcbAccReport){
		iibNcbAccReport.setAccountReportId(uloNcbAccReport.getAccountReportId());
		iibNcbAccReport.setRestructureGT1Yr(uloNcbAccReport.getRestructureGT1Yr());
		iibNcbAccReport.setSeq(uloNcbAccReport.getSeq());
		iibNcbAccReport.setTrackingCode(uloNcbAccReport.getTrackingCode()); 
	}
	public  void mapNcbAccountReportRule(NcbAccountReportRuleDataM uloNcbAccReportRule,decisionservice_iib.NcbAccountReportRuleDataM iibNcbReportRule){
		iibNcbReportRule.setAccountReportId(uloNcbAccReportRule.getAccountReportId());
		iibNcbReportRule.setProductCode(uloNcbAccReportRule.getProductCode());
		iibNcbReportRule.setRuleId(uloNcbAccReportRule.getRuleId());
		iibNcbReportRule.setRuleResult(uloNcbAccReportRule.getRuleResult());
		
	}
	public  void mapNcbAddress(NcbAddressDataM uloNcbAddress,decisionservice_iib.NcbAddressDataM iibNcbAddress){
		iibNcbAddress.setAddressLine1(uloNcbAddress.getAddressLine1());
		iibNcbAddress.setAddressLine2(uloNcbAddress.getAddressLine2());
		iibNcbAddress.setAddressLine3(uloNcbAddress.getAddressLine3());
		iibNcbAddress.setAddressType(uloNcbAddress.getAddressType());
		iibNcbAddress.setCountry(uloNcbAddress.getCountry());
		iibNcbAddress.setDistrict(uloNcbAddress.getDistrict());
		iibNcbAddress.setGroupSeq(uloNcbAddress.getGroupSeq() );
		iibNcbAddress.setPostalCode(uloNcbAddress.getPostalCode());
		iibNcbAddress.setProvince(uloNcbAddress.getProvince());
		iibNcbAddress.setReportedDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbAddress.getReportedDate()));
		iibNcbAddress.setResidentialStatus(uloNcbAddress.getResidentialStatus());
		iibNcbAddress.setSegmentValue(uloNcbAddress.getSegmentValue());
		iibNcbAddress.setSeq(uloNcbAddress.getSeq());
		iibNcbAddress.setSubdistrict(uloNcbAddress.getSubdistrict());
		iibNcbAddress.setTelephone(uloNcbAddress.getTelephone());
		iibNcbAddress.setTelephoneType(uloNcbAddress.getTelephoneType());
		iibNcbAddress.setTrackingCode(uloNcbAddress.getTrackingCode());

		
	}
	public  void mapNcbId(NcbIdDataM uloNcbId,decisionservice_iib.NcbIdDataM iibNcbId){
		iibNcbId.setGroupSeq(uloNcbId.getGroupSeq());
		iibNcbId.setIdIssueCountry(uloNcbId.getIdIssueCountry());
		iibNcbId.setIdNumber(uloNcbId.getIdNumber());
		iibNcbId.setSegmentValue(uloNcbId.getSegmentValue());
		iibNcbId.setSeq(uloNcbId.getSeq());
		iibNcbId.setTrackingCode(uloNcbId.getTrackingCode());
		
	}
	public  void mapNcbName(NcbNameDataM uloNcbName,decisionservice_iib.NcbNameDataM iibNcbName){
		iibNcbName.setConsentToEnquire(uloNcbName.getConsentToEnquire());
		iibNcbName.setDateOfBirth(DecisionServiceUtil.dateToXMLGregorianCalendar(uloNcbName.getDateOfBirth()));
		iibNcbName.setFamilyName1(uloNcbName.getFamilyName1());
		iibNcbName.setFamilyName2(uloNcbName.getFamilyName2());
		iibNcbName.setFirstName(uloNcbName.getFirstName());
		iibNcbName.setGender(uloNcbName.getGender());
		iibNcbName.setGroupSeq(uloNcbName.getGroupSeq());
		iibNcbName.setMaritalStatus(uloNcbName.getMaritalStatus());
		iibNcbName.setMiddle(uloNcbName.getMiddleName());
		iibNcbName.setNationality(uloNcbName.getNationality());
		iibNcbName.setNumberOfChildren(uloNcbName.getNumberOfChildren());
		iibNcbName.setOccupation(uloNcbName.getOccupation());
		iibNcbName.setSegmentValue(uloNcbName.getSegmentValue());
		iibNcbName.setSeq(uloNcbName.getSeq());
		iibNcbName.setSpouseName(uloNcbName.getSpouseName());
		iibNcbName.setTitle(uloNcbName.getTitle());
		iibNcbName.setTrackingCode(uloNcbName.getTrackingCode());
		
	}
//	public  void mapCardlinkCustomer(CardlinkCustomerDataM uloCardLinkCustomer,decisionservice_iib.CardlinkCustomer iibCardLinkCustomer){
//		iibCardLinkCustomer.setAddressLine1("");
//		iibCardLinkCustomer.setAddressLine2("");
//		iibCardLinkCustomer.setAddressLine3("");
//		iibCardLinkCustomer.setBillCycle(0);
//		iibCardLinkCustomer.setCardlinkCustNo(uloCardLinkCustomer.getCardlinkCustNo());
//		iibCardLinkCustomer.setOrgNo(uloCardLinkCustomer.getOrgId());
//		iibCardLinkCustomer.setCreditLine(null);
//		iibCardLinkCustomer.setEmpPosition("");
//		iibCardLinkCustomer.setFinancialInfoCode(uloCardLinkCustomer.getFinancialInfoCode());
//		iibCardLinkCustomer.setFirstOpenDate(null);
//		iibCardLinkCustomer.setIncomeAmount(null);
//		iibCardLinkCustomer.setMobileNo("");
//		iibCardLinkCustomer.setOccupation("");
//		iibCardLinkCustomer.setReferAddressLine1("");
//		iibCardLinkCustomer.setReferAddressLine2("");
//		iibCardLinkCustomer.setReferAddressLine3("");
//		iibCardLinkCustomer.setReferAddressLine1("");
//		iibCardLinkCustomer.setReferAddressLine2("");
//		iibCardLinkCustomer.setReferAddressLine3("");
//		iibCardLinkCustomer.setReferZipcode("");
//		iibCardLinkCustomer.setVatCode(uloCardLinkCustomer.getVatCode());
//		iibCardLinkCustomer.setZipCode("");
//	}
//	public  void mapCardlinkCard(CardlinkCardDataM uloCardLinkCard,decisionservice_iib.CardlinkCard iibCardLinkCard){
//		iibCardLinkCard.setAutoPayAcctNo("");
//		iibCardLinkCard.setAutoPayFlag("");
//		iibCardLinkCard.setBillCycle(0);
//		iibCardLinkCard.setBlockCode(uloCardLinkCard.getBlockCode());
//		iibCardLinkCard.setBlockDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloCardLinkCard.getBlockDate()));
//		iibCardLinkCard.setCardCode(uloCardLinkCard.getCardCode());
//		iibCardLinkCard.setCardFlag("");
//		iibCardLinkCard.setCardNo(uloCardLinkCard.getCardNo());
//		iibCardLinkCard.setCardNoInHash("");
//		iibCardLinkCard.setCardNoInEncrypt("");
//		iibCardLinkCard.setCardlinkCardId(uloCardLinkCard.getCardlinkCardId());
//		iibCardLinkCard.setCardlinkCustNo(uloCardLinkCard.getCardlinkCustNo());
//		iibCardLinkCard.setCardType("");
//		iibCardLinkCard.setCardTypeBillingCycle("");
//		iibCardLinkCard.setCardLevelEligibleCard("");
//		iibCardLinkCard.setCoaProductCode(uloCardLinkCard.getCoaProductCode());;
//		iibCardLinkCard.setCreditLimitAmount(null);
//		iibCardLinkCard.setEmbossname3("");
//		iibCardLinkCard.setExpiryDate(null);
//		iibCardLinkCard.setMainCustNo("");
//		iibCardLinkCard.setMainOrgNo("");
//		iibCardLinkCard.setMainFlag("");
//		iibCardLinkCard.setMonth24Profile("");
//		iibCardLinkCard.setOpenDate(null);
//		iibCardLinkCard.setOrgNo(uloCardLinkCard.getOrgId());
//		iibCardLinkCard.setOutstandingBal(null);
//		iibCardLinkCard.setPlasticCode(uloCardLinkCard.getPlasticCode());
//		iibCardLinkCard.setProjectCode(uloCardLinkCard.getProjectCode());
//		iibCardLinkCard.setRealCustNo("");
//		iibCardLinkCard.setRealOrgNo("");
//		iibCardLinkCard.setSupCustNo(uloCardLinkCard.getSupCustNo());
//		iibCardLinkCard.setSupOrgNo(uloCardLinkCard.getSupOrgId());
//		iibCardLinkCard.setWealthFlag("");
//		iibCardLinkCard.setCardNoInEncrypt(ProcessUtil.getCardEncrypt(uloCardLinkCard.getCardCode()));
//		
//	}
	public  void mapFixedGuarantee(FixedGuaranteeDataM uloFixedGuarantee,decisionservice_iib.FixedGuaranteeDataM iibFixedGuarantee){
		iibFixedGuarantee.setAccountName(uloFixedGuarantee.getAccountName());
		iibFixedGuarantee.setAccountNo(uloFixedGuarantee.getAccountNo());
		iibFixedGuarantee.setBranchNo(uloFixedGuarantee.getBranchNo());
		iibFixedGuarantee.setFixedGuaranteeId(uloFixedGuarantee.getFixedGuaranteeId());
		iibFixedGuarantee.setRetentionAmt(uloFixedGuarantee.getRetentionAmt());
		iibFixedGuarantee.setRetentionDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloFixedGuarantee.getRetentionDate()));
		iibFixedGuarantee.setRetentionType(uloFixedGuarantee.getRetentionType());
		iibFixedGuarantee.setRetentionTypeDesc(uloFixedGuarantee.getRetentionTypeDesc());
		iibFixedGuarantee.setSub(uloFixedGuarantee.getSub());
	}
	
	public  void mapProjectCode(ProjectCodeDataM uloProjectCode,decisionservice_iib.ProjectCodeDataM iibProjectCode){
		iibProjectCode.setApplicationGroupId(uloProjectCode.getApplicationGroupId());
		iibProjectCode.setProjectCode(uloProjectCode.getProjectCode());
	}
	
	public  void mapReferencePerson(ReferencePersonDataM uloRefPersonal,decisionservice_iib.ReferencePersonDataM iibRefPersonal){
		iibRefPersonal.setApplicationGroupId(uloRefPersonal.getApplicationGroupId());
		iibRefPersonal.setExt1(uloRefPersonal.getExt1());
		iibRefPersonal.setExt2(uloRefPersonal.getExt2());
		iibRefPersonal.setMobile(uloRefPersonal.getMobile());;
		iibRefPersonal.setOfficePhone(uloRefPersonal.getOfficePhone());
		iibRefPersonal.setOfficePhoneExt(uloRefPersonal.getOfficePhoneExt());
		iibRefPersonal.setPhone1(uloRefPersonal.getPhone1());
		iibRefPersonal.setPhone2(uloRefPersonal.getPhone2());
		iibRefPersonal.setRelation(uloRefPersonal.getRelation());
		iibRefPersonal.setSeq(uloRefPersonal.getSeq());
		iibRefPersonal.setThFirstName(uloRefPersonal.getThFirstName());
		iibRefPersonal.setThLastName(uloRefPersonal.getThLastName());
		iibRefPersonal.setThTitleCode(uloRefPersonal.getThTitleCode());
		iibRefPersonal.setThTitleDesc(uloRefPersonal.getThTitleDesc());
	}
	
	public  void mapSaleInfo(SaleInfoDataM uloSaleInfo,decisionservice_iib.SaleInfoDataM iibSaleInfo){
		iibSaleInfo.setApplicationGroupId(uloSaleInfo.getApplicationGroupId());
		iibSaleInfo.setRegion(uloSaleInfo.getRegion());
		iibSaleInfo.setSaleChannel(uloSaleInfo.getSaleChannel());
		iibSaleInfo.setSalesBranchCode(uloSaleInfo.getSalesBranchCode());
		iibSaleInfo.setSalesBranchName(uloSaleInfo.getSalesBranchName());
		iibSaleInfo.setSalesComment(uloSaleInfo.getSalesComment());
		iibSaleInfo.setSalesDeptName(uloSaleInfo.getSalesDeptName());
		iibSaleInfo.setSalesId(uloSaleInfo.getSalesId());
		iibSaleInfo.setSalesInfoId(uloSaleInfo.getSalesInfoId());
		iibSaleInfo.setSalesName(uloSaleInfo.getSalesName());
		iibSaleInfo.setSalesName(uloSaleInfo.getSalesName());
		iibSaleInfo.setSalesPhoneNo(uloSaleInfo.getSalesPhoneNo());
		iibSaleInfo.setSalesRCCode(uloSaleInfo.getSalesRCCode());
		iibSaleInfo.setSalesType(uloSaleInfo.getSalesType());
		iibSaleInfo.setZone(uloSaleInfo.getZone());
	}
	
	public  void mapAdditionalService(SpecialAdditionalServiceDataM uloAdditionalService,decisionservice_iib.AdditionalServiceDataM iibAdditionalService){
		iibAdditionalService.setCurrentAccName(uloAdditionalService.getCurrentAccName());
		iibAdditionalService.setCurrentAccNo(uloAdditionalService.getCurrentAccNo());
		iibAdditionalService.setSavingAccName(uloAdditionalService.getSavingAccName());
		iibAdditionalService.setSavingAccNo(uloAdditionalService.getSavingAccNo());
		iibAdditionalService.setServiceType(uloAdditionalService.getServiceType());
	}
	
	public  void mapSpecialAdditionalServiceMap(SpecialAdditionalServiceMapDataM uloAdditionalServiceMap,decisionservice_iib.SpecialAdditionalServiceMapDataM iibAdditionalServiceMap){
		iibAdditionalServiceMap.setLoanId(uloAdditionalServiceMap.getLoanId());
		iibAdditionalServiceMap.setServiceId(uloAdditionalServiceMap.getServiceId());
	}
	
	public  void mapPersonalVerificationResult(VerificationResultDataM uloVerification,decisionservice_iib.VerificationResultDataM iibVerResult){
		iibVerResult.setDocCompletedFlag(uloVerification.getDocCompletedFlag());
		iibVerResult.setRequiredVerCustFlag(uloVerification.getRequiredVerCustFlag());
		iibVerResult.setRequiredVerIncomeFlag(uloVerification.getRequiredVerIncomeFlag());
		iibVerResult.setRequiredVerPrivilegeFlag(uloVerification.getRequiredVerPrivilegeFlag());
		iibVerResult.setRequiredVerWebFlag(uloVerification.getRequiredVerWebFlag());
		iibVerResult.setRequirePriorityPass(uloVerification.getRequirePriorityPass());
		iibVerResult.setSummaryIncomeResultCode(uloVerification.getSummaryIncomeResultCode());
		iibVerResult.setTotalSavingandFundFromPriviliegeProjectCode(ProcessUtil.calculateTotalSavingandFundFromPriviliegeProjectCode(uloVerification));
		iibVerResult.setVerPrivilegeResultCode(uloVerification.getVerPrivilegeResultCode());
		iibVerResult.setVerWebResultCode(uloVerification.getVerWebResultCode());
		iibVerResult.setRefId(uloVerification.getRefId());
		iibVerResult.setVerLevel(uloVerification.getVerLevel());
		iibVerResult.setRiskGradeCoarseCode(uloVerification.getRiskGradeCoarseCode());
		iibVerResult.setRiskGradeFineCode(uloVerification.getRiskGradeFineCode());
		iibVerResult.setVerCusResultCode(ProcessUtil.getVerCusResult(uloVerification.getVerCusResultCode()));		
	}
	
	public  void mapApplicationVerificationResult(VerificationResultDataM uloVerification,decisionservice_iib.VerificationResultDataM iibVerResult){
		if(null!=uloVerification){
			iibVerResult.setRefId(uloVerification.getRefId());
			iibVerResult.setVerLevel(uloVerification.getVerLevel());
		}
	}
	
	
	public  void mapDocumentVerification(DocumentVerificationDataM uloDocVerification,decisionservice_iib.DocumentVerificationDataM iibDocVerification){
		iibDocVerification.setDocumentVerId(uloDocVerification.getDocumentVerId());
		iibDocVerification.setFollowingType(uloDocVerification.getFollowingType());
		iibDocVerification.setVerResult(uloDocVerification.getVerResult());
		iibDocVerification.setVerResultId(uloDocVerification.getVerResultId());
	}
	
	public  void mapHrVerification(HRVerificationDataM uloHrVerification,decisionservice_iib.HrVerificationDataM iibHrVerification){
		iibHrVerification.setContactType(uloHrVerification.getContactType());
		iibHrVerification.setHrVerifyId(uloHrVerification.getHrVerifyId());
		iibHrVerification.setPhoneNo(uloHrVerification.getPhoneNo());
		iibHrVerification.setPhoneVerStatus(uloHrVerification.getPhoneVerStatus());
		iibHrVerification.setRemark(uloHrVerification.getRemark());
		iibHrVerification.setVerResultId(uloHrVerification.getVerResultId());
	}
	
	public  void mapIdentifyQuestionSet(IdentifyQuestionSetDataM uloIdentifyQuestionSet,decisionservice_iib.IdentifyQuestionSetDataM iibIdentifyQuestionSet){
		iibIdentifyQuestionSet.setCallTo(uloIdentifyQuestionSet.getCallTo());
		iibIdentifyQuestionSet.setQuestionSetCode(uloIdentifyQuestionSet.getQuestionSetCode());
		iibIdentifyQuestionSet.setQuestionSetId(uloIdentifyQuestionSet.getQuestionSetId());
		iibIdentifyQuestionSet.setQuestionSetType(uloIdentifyQuestionSet.getQuestionSetType());
		iibIdentifyQuestionSet.setVerResultId(uloIdentifyQuestionSet.getVerResultId());
	}
	public  void mapIdentifyQuestion(IdentifyQuestionDataM uloIdentifyQuestion,decisionservice_iib.IdentifyQuestionDataM iibIdentifyQuestion){
		iibIdentifyQuestion.setCustomerAnswer(uloIdentifyQuestion.getCustomerAnswer());
		iibIdentifyQuestion.setIdentifyQuestionId(uloIdentifyQuestion.getIdentifyQuestionId());
		iibIdentifyQuestion.setQuestionNo(uloIdentifyQuestion.getQuestionNo());
		iibIdentifyQuestion.setQuestionSetCode(uloIdentifyQuestion.getQuestionSetCode());
		iibIdentifyQuestion.setQuestionSetType(uloIdentifyQuestion.getQuestionSetType());
		iibIdentifyQuestion.setResult(uloIdentifyQuestion.getResult());
		iibIdentifyQuestion.setSeq(uloIdentifyQuestion.getSeq());
		iibIdentifyQuestion.setVerResultId(uloIdentifyQuestion.getVerResultId());
	}
	
	public  void mapPhoneVerification(PhoneVerificationDataM uloPhoneVerification,decisionservice_iib.PhoneVerificationDataM iibPhoneVerification){
		iibPhoneVerification.setCallType(uloPhoneVerification.getCallType());
		iibPhoneVerification.setContactType(uloPhoneVerification.getContactType());
		iibPhoneVerification.setPhoneVerStatus(uloPhoneVerification.getPhoneVerStatus());
		iibPhoneVerification.setPhoneVerifyId(uloPhoneVerification.getPhoneVerifyId());
		iibPhoneVerification.setRemark(uloPhoneVerification.getRemark());
		iibPhoneVerification.setSeq(uloPhoneVerification.getSeq());
		iibPhoneVerification.setTelephoneNumber(uloPhoneVerification.getTelephoneNumber());
		iibPhoneVerification.setVerResultId(uloPhoneVerification.getVerResultId());
	}
	
	public  void mapORPolicyRules(ORPolicyRulesDataM uloORPolicyRules,decisionservice_iib.OrPolicyRulesDataM iibORPolicyRules){
		iibORPolicyRules.setAppRecordId(uloORPolicyRules.getAppRecordId());
		iibORPolicyRules.setMinApprovalAuth(uloORPolicyRules.getMinApprovalAuth());
		iibORPolicyRules.setOrPolicyRulesId(uloORPolicyRules.getOrPolicyRulesId());
		iibORPolicyRules.setOverideCode(uloORPolicyRules.getPolicyCode());
		iibORPolicyRules.setPolicyRulesId(uloORPolicyRules.getPolicyRulesId());
		iibORPolicyRules.setPolicyType(uloORPolicyRules.getPolicyType());
		iibORPolicyRules.setReason(uloORPolicyRules.getReason());
		iibORPolicyRules.setResultDesc(uloORPolicyRules.getResultDesc());
//		iibORPolicyRules.setResult(uloORPolicyRules.getVerifiedResult());
		iibORPolicyRules.setOverideResult(uloORPolicyRules.getVerifiedResult());
//		iibORPolicyRules.setOverideCode("");
		if(null!=uloORPolicyRules.getOrPolicyRulesDetails() && uloORPolicyRules.getOrPolicyRulesDetails().size()>0){
			for(ORPolicyRulesDetailDataM uloORPolicyRulesDetail : uloORPolicyRules.getOrPolicyRulesDetails()){
				decisionservice_iib.OrPolicyRulesDetailDataM iibORPolicyRulesDetail = new decisionservice_iib.OrPolicyRulesDetailDataM();
				mapORPolicyRulesDetail(uloORPolicyRulesDetail, iibORPolicyRulesDetail);
				iibORPolicyRules.getOrPolicyRulesDetails().add(iibORPolicyRulesDetail);
			}
		}
	}
	
	public void mapPolicyRulesCondition(PolicyRulesConditionDataM uloPolicyRuleCondition, decisionservice_iib.PolicyRuleConditionDataM iibPolicyRuleCondition){
		iibPolicyRuleCondition.setConditionCode(uloPolicyRuleCondition.getConditionCode());
	}
	
	public  void mapORPolicyRulesDetail(ORPolicyRulesDetailDataM uloORPolicyRulesDetail,decisionservice_iib.OrPolicyRulesDetailDataM iibORPolicyRulesDetail){
		iibORPolicyRulesDetail.setGuidelineCode(uloORPolicyRulesDetail.getGuidelineCode());
		iibORPolicyRulesDetail.setMinApprovalAuth(uloORPolicyRulesDetail.getMinApprovalAuth());
		iibORPolicyRulesDetail.setOrPolicyRulesDetailId(uloORPolicyRulesDetail.getOrPolicyRulesDetailId());
		iibORPolicyRulesDetail.setOrPolicyRulesId(uloORPolicyRulesDetail.getOrPolicyRulesId());
		iibORPolicyRulesDetail.setRank(uloORPolicyRulesDetail.getRank());
		iibORPolicyRulesDetail.setReason(uloORPolicyRulesDetail.getReason());
		iibORPolicyRulesDetail.setResult(uloORPolicyRulesDetail.getVerifiedResult());
		
		if(null!=uloORPolicyRulesDetail.getGuidelines() && uloORPolicyRulesDetail.getGuidelines().size()>0){
			for(GuidelineDataM uloGuideline : uloORPolicyRulesDetail.getGuidelines()){
				decisionservice_iib.GuidelineDataM iibGuideline = new decisionservice_iib.GuidelineDataM();
				mapGuideline(uloGuideline, iibGuideline);
				iibORPolicyRulesDetail.getGuidelines().add(iibGuideline);
			}
		}	
	}
	
	public  void mapGuideline(GuidelineDataM uloGuideline,decisionservice_iib.GuidelineDataM iibGuideline){
		iibGuideline.setGuideLineDataId(uloGuideline.getGuideLineDataId());
		iibGuideline.setName(uloGuideline.getName());
		iibGuideline.setOrPolicyRulesDetailId(uloGuideline.getOrPolicyRulesDetailId());
		iibGuideline.setValue(uloGuideline.getValue());
	}
	
	public  void mapPolicyRules(PolicyRulesDataM uloPolicyRules,decisionservice_iib.PolicyRulesDataM iibPolicyRules){
		iibPolicyRules.setPolicyCode(uloPolicyRules.getPolicyCode());
		iibPolicyRules.setPolicyRulesId(uloPolicyRules.getPolicyRulesId());
		iibPolicyRules.setResult(uloPolicyRules.getVerifiedResult());
		iibPolicyRules.setVerResultId(uloPolicyRules.getVerResultId());
		iibPolicyRules.setOverrideFlag(uloPolicyRules.getOverrideFlag());
	}
	
	public  void mapPrivilegeProjectCode(PrivilegeProjectCodeDataM uloPrivilegeProjectCode,decisionservice_iib.PrivilegeProjectCodeDataM iibPrivilegeProjectCode){
		iibPrivilegeProjectCode.setProjectCode(uloPrivilegeProjectCode.getProjectCode());
		iibPrivilegeProjectCode.setPrivilegeType(uloPrivilegeProjectCode.getProjectType());
		iibPrivilegeProjectCode.setVerResultId(uloPrivilegeProjectCode.getVerResultId());			
	}
	
	public  void mapPrivilegeProjectCodeDoc(PrivilegeProjectCodeDocDataM uloPrivilegeProjectCodeDoc,decisionservice_iib.PrivilegeProjectCodeDocDataM iibPrivilegeProjectCodeDoc){
		iibPrivilegeProjectCodeDoc.setDocType(uloPrivilegeProjectCodeDoc.getDocType());
		iibPrivilegeProjectCodeDoc.setPrvlgDocId(uloPrivilegeProjectCodeDoc.getPrvlgDocId());
		iibPrivilegeProjectCodeDoc.setPrvlgPrjCdeId(uloPrivilegeProjectCodeDoc.getPrvlgPrjCdeId());
		
	}
	
	public  void mapPrivilegeProjectCodeExceptionDoc(PrivilegeProjectCodeExceptionDocDataM uloPrivilegeProjectCodeExceptionDoc,decisionservice_iib.PrivilegeProjectCodeExceptionDocDataM iibPrivilegeProjectCodeExceptionDoc){
		iibPrivilegeProjectCodeExceptionDoc.setExceptDocFrom(uloPrivilegeProjectCodeExceptionDoc.getExceptDocFrom());
		iibPrivilegeProjectCodeExceptionDoc.setExceptDocId(uloPrivilegeProjectCodeExceptionDoc.getExceptDocId());
		iibPrivilegeProjectCodeExceptionDoc.setExceptPolicy(uloPrivilegeProjectCodeExceptionDoc.getExceptPolicy());
		iibPrivilegeProjectCodeExceptionDoc.setExceptPolicyOth(uloPrivilegeProjectCodeExceptionDoc.getExceptPolicyOth());
		iibPrivilegeProjectCodeExceptionDoc.setPrvlgDocId(uloPrivilegeProjectCodeExceptionDoc.getPrvlgDocId());
	}
	
	public  void mapPrivilegeProjectCodeKassetDoc(PrivilegeProjectCodeKassetDocDataM uloPrivilegeProjectCodeKassetDoc,decisionservice_iib.PrivilegeProjectCodeKassetDocDataM iibPrivilegeProjectCodeKassetDoc){
		iibPrivilegeProjectCodeKassetDoc.setKassetType(uloPrivilegeProjectCodeKassetDoc.getKassetType());
		iibPrivilegeProjectCodeKassetDoc.setMonth1(uloPrivilegeProjectCodeKassetDoc.getMonth1m());
		iibPrivilegeProjectCodeKassetDoc.setMonth2(uloPrivilegeProjectCodeKassetDoc.getMonth2m());
		iibPrivilegeProjectCodeKassetDoc.setMonth3(uloPrivilegeProjectCodeKassetDoc.getMonth3m());
		iibPrivilegeProjectCodeKassetDoc.setMonth4(uloPrivilegeProjectCodeKassetDoc.getMonth4m());
		iibPrivilegeProjectCodeKassetDoc.setMonth5(uloPrivilegeProjectCodeKassetDoc.getMonth5m());
		iibPrivilegeProjectCodeKassetDoc.setMonth6(uloPrivilegeProjectCodeKassetDoc.getMonth6m());
		iibPrivilegeProjectCodeKassetDoc.setFund6M(uloPrivilegeProjectCodeKassetDoc.getFund6m());
		iibPrivilegeProjectCodeKassetDoc.setFundL6M(uloPrivilegeProjectCodeKassetDoc.getFundL6m());
		iibPrivilegeProjectCodeKassetDoc.setIncome(uloPrivilegeProjectCodeKassetDoc.getIncome());
		iibPrivilegeProjectCodeKassetDoc.setKassetDocId(uloPrivilegeProjectCodeKassetDoc.getKassetDocId());
		iibPrivilegeProjectCodeKassetDoc.setPrvlgDocId(uloPrivilegeProjectCodeKassetDoc.getPrvlgDocId());
		iibPrivilegeProjectCodeKassetDoc.setWealth(uloPrivilegeProjectCodeKassetDoc.getWealth());
	}
	
	public  void mapPrivilegeProjectCodeMGMDoc(PrivilegeProjectCodeMGMDocDataM uloPrivilegeProjectCodeMGMDoc,decisionservice_iib.PrivilegeProjectCodeMGMDocDataM iibPrivilegeProjectCodeMGMDoc){
		iibPrivilegeProjectCodeMGMDoc.setPrivlgMgmDocId(uloPrivilegeProjectCodeMGMDoc.getPrivlgMgmDocId());
		iibPrivilegeProjectCodeMGMDoc.setPrvlgDocId(uloPrivilegeProjectCodeMGMDoc.getPrvlgDocId());
		iibPrivilegeProjectCodeMGMDoc.setReferCardHolderName(uloPrivilegeProjectCodeMGMDoc.getReferCardHolderName());
		iibPrivilegeProjectCodeMGMDoc.setReferCardNo(uloPrivilegeProjectCodeMGMDoc.getReferCardNo());
	}
	
	public  void mapPrivilegeProjectCodeTransferDoc(PrivilegeProjectCodeTransferDocDataM uloPrivilegeProjectCodeTransferDoc,decisionservice_iib.PrivilegeProjectCodeTransferDocDataM iibPrivilegeProjectCodeTransferDoc){
		iibPrivilegeProjectCodeTransferDoc.setAccountNo(uloPrivilegeProjectCodeTransferDoc.getAccountNo());
		iibPrivilegeProjectCodeTransferDoc.setAmount(uloPrivilegeProjectCodeTransferDoc.getAmount());
		iibPrivilegeProjectCodeTransferDoc.setCisNo(uloPrivilegeProjectCodeTransferDoc.getCisNo());
		iibPrivilegeProjectCodeTransferDoc.setHoldingRatio(uloPrivilegeProjectCodeTransferDoc.getHoldingRatio());
		iibPrivilegeProjectCodeTransferDoc.setIdNo(uloPrivilegeProjectCodeTransferDoc.getIdNo());
		iibPrivilegeProjectCodeTransferDoc.setInvestType(uloPrivilegeProjectCodeTransferDoc.getInvestType());
		iibPrivilegeProjectCodeTransferDoc.setPrvlgTransferDocId(uloPrivilegeProjectCodeTransferDoc.getPrvlgTransferDocId());
		iibPrivilegeProjectCodeTransferDoc.setPrvlgDocId(uloPrivilegeProjectCodeTransferDoc.getPrvlgDocId());
		iibPrivilegeProjectCodeTransferDoc.setRelationship(uloPrivilegeProjectCodeTransferDoc.getRelationship());
		iibPrivilegeProjectCodeTransferDoc.setSeq(uloPrivilegeProjectCodeTransferDoc.getSeq());
	}
	
	public  void mapPrivilegeProjectCodeRccmdPrjCde(PrivilegeProjectCodeRccmdPrjCdeDataM uloPrivilegeProjectCodeRccmdPrjCde,decisionservice_iib.PrivilegeProjectCodeRccmdPrjCdeDataM iibPrivilegeProjectCodeRccmdPrjCde){
		iibPrivilegeProjectCodeRccmdPrjCde.setProjectCode(uloPrivilegeProjectCodeRccmdPrjCde.getProjectCode());
		iibPrivilegeProjectCodeRccmdPrjCde.setProjectCodeDesc(uloPrivilegeProjectCodeRccmdPrjCde.getProjectCodeDesc());
		iibPrivilegeProjectCodeRccmdPrjCde.setPrvlgPrjCdeId(uloPrivilegeProjectCodeRccmdPrjCde.getPrvlgPrjCdeId());
		iibPrivilegeProjectCodeRccmdPrjCde.setRccmdPrjCdeId(uloPrivilegeProjectCodeRccmdPrjCde.getRccmdPrjCdeId());
	}
	
	public  void mapPrivilegeProjectCodeProductCCA(PrivilegeProjectCodeProductCCADataM uloPrivilegeProjectCodeProductCCA,decisionservice_iib.PrivilegeProjectCodeProductCCADataM iibPrivilegeProjectCodeProductCCA){
		iibPrivilegeProjectCodeProductCCA.setCcaProduct(uloPrivilegeProjectCodeProductCCA.getCcaProduct());
		iibPrivilegeProjectCodeProductCCA.setProductCcaId(uloPrivilegeProjectCodeProductCCA.getProductCcaId());
		iibPrivilegeProjectCodeProductCCA.setPrvlgPrjCdeId(uloPrivilegeProjectCodeProductCCA.getPrvlgPrjCdeId());
		iibPrivilegeProjectCodeProductCCA.setResult(uloPrivilegeProjectCodeProductCCA.getResult());
	}
	
	public  void mapPrivilegeProjectCodeProductInsurance(PrivilegeProjectCodeProductInsuranceDataM uloPrivilegeProjectCodeProductInsurance,decisionservice_iib.PrivilegeProjectCodeProductInsuranceDataM iibPrivilegeProjectCodeProductInsurance){
		iibPrivilegeProjectCodeProductInsurance.setInsuranceType(uloPrivilegeProjectCodeProductInsurance.getInsuranceType());
		iibPrivilegeProjectCodeProductInsurance.setPolicyNo(uloPrivilegeProjectCodeProductInsurance.getPolicyNo());
		iibPrivilegeProjectCodeProductInsurance.setPremium(uloPrivilegeProjectCodeProductInsurance.getPremium());
		iibPrivilegeProjectCodeProductInsurance.setProductInsuranceId(uloPrivilegeProjectCodeProductInsurance.getProductInsuranceId());
		iibPrivilegeProjectCodeProductInsurance.setPrvlgPrjCdeId(uloPrivilegeProjectCodeProductInsurance.getPrvlgPrjCdeId());
		iibPrivilegeProjectCodeProductInsurance.setSeq(uloPrivilegeProjectCodeProductInsurance.getSeq());
		iibPrivilegeProjectCodeProductInsurance.setCisNo(uloPrivilegeProjectCodeProductInsurance.getCisno());
		iibPrivilegeProjectCodeProductInsurance.setRelationship(uloPrivilegeProjectCodeProductInsurance.getRelationTranfer());
	}
	
	public  void mapPrivilegeProjectCodeProductSaving(PrivilegeProjectCodeProductSavingDataM uloPrivilegeProjectCodeProductSaving,decisionservice_iib.PrivilegeProjectCodeProductSavingDataM iibPrivilegeProjectCodeProductSaving){
		iibPrivilegeProjectCodeProductSaving.setAccountBalance(uloPrivilegeProjectCodeProductSaving.getAccountBalance());
		iibPrivilegeProjectCodeProductSaving.setAccountNo(uloPrivilegeProjectCodeProductSaving.getAccountNo());
		iibPrivilegeProjectCodeProductSaving.setCisNo(uloPrivilegeProjectCodeProductSaving.getCisNo());
		iibPrivilegeProjectCodeProductSaving.setIdNo(uloPrivilegeProjectCodeProductSaving.getIdNo());
		iibPrivilegeProjectCodeProductSaving.setProductSavingId(uloPrivilegeProjectCodeProductSaving.getProductSavingId());
		iibPrivilegeProjectCodeProductSaving.setProductType(uloPrivilegeProjectCodeProductSaving.getProductType());
		iibPrivilegeProjectCodeProductSaving.setPrvlgPrjCdeId(uloPrivilegeProjectCodeProductSaving.getPrvlgPrjCdeId());
		iibPrivilegeProjectCodeProductSaving.setSeq(uloPrivilegeProjectCodeProductSaving.getSeq());
		iibPrivilegeProjectCodeProductSaving.setHoldingRatio(DecisionServiceUtil.parseInteger(uloPrivilegeProjectCodeProductSaving.getHoldingRatio()));
		iibPrivilegeProjectCodeProductSaving.setRelationship(uloPrivilegeProjectCodeProductSaving.getRelationshipTransfer());
		iibPrivilegeProjectCodeProductSaving.setStartValues(uloPrivilegeProjectCodeProductSaving.getAccountBalanceStart());
	}
	
	public void mapPrivilegeProjectCodeProductTrading(PrivilegeProjectCodeProductTradingDataM uloPrivilegeProjectCodeProductTrading,decisionservice_iib.PrivilegeProjectCodeProductTradingDataM iibPrivilegeProjectCodeProductTrading){
		iibPrivilegeProjectCodeProductTrading.setPropertyValue(uloPrivilegeProjectCodeProductTrading.getPropertyValue());
	}
	
	public  void mapRequiredDoc(RequiredDocDataM uloRequiredDoc,decisionservice_iib.RequiredDocDataM iibRequiredDoc){
		iibRequiredDoc.setDocumentGroupCode(uloRequiredDoc.getDocumentGroupCode());
		iibRequiredDoc.setRequiredDocId(uloRequiredDoc.getRequiredDocId());
		iibRequiredDoc.setScenarioType(uloRequiredDoc.getScenarioType());
		iibRequiredDoc.setVerResultId(uloRequiredDoc.getVerResultId());
		if(null!=uloRequiredDoc.getRequiredDocDetails() && uloRequiredDoc.getRequiredDocDetails().size()>0){
			for(RequiredDocDetailDataM uloRequiredDocDetail : uloRequiredDoc.getRequiredDocDetails()){
				if(null!=uloRequiredDocDetail.getDocumentCode() && !"".equals(uloRequiredDocDetail.getDocumentCode())){
					decisionservice_iib.RequiredDocDetailDataM iibRequiredDocDetail = new decisionservice_iib.RequiredDocDetailDataM();
					mapRequiredDocDetail(uloRequiredDocDetail, iibRequiredDocDetail);
					iibRequiredDoc.getRequiredDocDetails().add(iibRequiredDocDetail);
				}
			}
		}	
	}
	
	public  void mapRequiredDocDetail(RequiredDocDetailDataM uloRequiredDocDetail,decisionservice_iib.RequiredDocDetailDataM iibRequiredDocDetail){
		iibRequiredDocDetail.setDocumentCode(uloRequiredDocDetail.getDocumentCode());
		iibRequiredDocDetail.setReceivedFlag(uloRequiredDocDetail.getReceivedFlag());
		iibRequiredDocDetail.setRequiredDocDetailId(uloRequiredDocDetail.getRequiredDocDetailId());
		iibRequiredDocDetail.setRequiredDocId(uloRequiredDocDetail.getRequiredDocDetailId());
		iibRequiredDocDetail.setDoclists("");
		iibRequiredDocDetail.setMandatory("");
		iibRequiredDocDetail.setNum("");
	}
	
	public  void mapWebVerification(WebVerificationDataM uloWebVerification,decisionservice_iib.WebVerificationDataM iibWebVerification){
		iibWebVerification.setRemark(uloWebVerification.getRemark());
		iibWebVerification.setVerResult(uloWebVerification.getVerResult());
		iibWebVerification.setVerResultId(uloWebVerification.getVerResultId());
		iibWebVerification.setWebCode(uloWebVerification.getWebCode());
		iibWebVerification.setWebVerifyId(uloWebVerification.getWebVerifyId());
		iibWebVerification.setWebResult(uloWebVerification.getVerResult());
	}
	
	public  void mapWorkFlowDecision(WorkFlowDecisionM uloWorkFlowDecision,decisionservice_iib.WorkFlowDecisionM iibWorkFlowDecision){
		iibWorkFlowDecision.setDecision(uloWorkFlowDecision.getDecision());
		iibWorkFlowDecision.setDecisionDesc(uloWorkFlowDecision.getDecisionDesc());
		iibWorkFlowDecision.setMessage(uloWorkFlowDecision.getMessage());
	}
		
	public  void mapBundleKL(BundleKLDataM uloBundleKL,decisionservice_iib.BundleKLDataM iibBundleKL){
		iibBundleKL.setApplicationRecordId(uloBundleKL.getApplicationRecordId());
		iibBundleKL.setEstimatedIncome(uloBundleKL.getEstimated_income());
		iibBundleKL.setVerifiedDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloBundleKL.getVerifiedDate()));
		iibBundleKL.setVerifiedIncome(uloBundleKL.getVerifiedIncome());
		iibBundleKL.setApproveCreditLine("");
	}
	
	public  void mapBundleHL(BundleHLDataM uloBundleHL,decisionservice_iib.BundleHLDataM iibBundleHL){
		iibBundleHL.setApplicationRecordId(uloBundleHL.getApplicationRecordId());
		iibBundleHL.setApproveCreditLine(uloBundleHL.getApproveCreditLine());
		iibBundleHL.setApprovedDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloBundleHL.getApprovedDate()));
		iibBundleHL.setCcApprovedAmt(uloBundleHL.getCcApprovedAmt());
		iibBundleHL.setKecApprovedAmt(uloBundleHL.getKecApprovedAmt());
		iibBundleHL.setVerifiedIncome(uloBundleHL.getVerifiedIncome());
		iibBundleHL.setAccountOpenDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloBundleHL.getAccountOpenDate()));
		iibBundleHL.setMortGageDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloBundleHL.getMortGageDate()));
		iibBundleHL.setAccountNo(uloBundleHL.getAccountNo());
	}
	public  void mapBundleSME(BundleSMEDataM uloBundleSME,decisionservice_iib.BundleSMEDataM iibBundleSME){
		iibBundleSME.setApplicantQuality(uloBundleSME.getApplicantQuality());
		iibBundleSME.setApplicationRecordId(uloBundleSME.getApplicationRecordId());
		iibBundleSME.setApprovedDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloBundleSME.getApprovalDate()));
		iibBundleSME.setApprovalLimit(uloBundleSME.getApprovalLimit());
		iibBundleSME.setBorrowingType(uloBundleSME.getBorrowingType());
		iibBundleSME.setBusOwnerFlag(uloBundleSME.getBusOwnerFlag());
		iibBundleSME.setBusinessCode(uloBundleSME.getBusinessCode());
		iibBundleSME.setCorporateRatio(uloBundleSME.getCorporateRatio());
		iibBundleSME.setIndividualRatio(uloBundleSME.getIndividualRatio());
		iibBundleSME.setGDscrReq(uloBundleSME.getgDscrReq());
		iibBundleSME.setGTotExistPayment(uloBundleSME.getgTotExistPayment());
		iibBundleSME.setGTotNewPayReq(uloBundleSME.getgTotNewPayReq());
		iibBundleSME.setApproveCreditLine(DecisionServiceUtil.toString(uloBundleSME.getApprovalLimit()));
		iibBundleSME.setAccountOpenDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloBundleSME.getAccountDate()));
		iibBundleSME.setVerifiedIncome(uloBundleSME.getVerifiedIncome());
	}
	
	public  void mapLoan(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan){
		iibLoan.setAccountNo(uloLoan.getAccountNo());
		iibLoan.setApplicationRecordId(uloLoan.getApplicationRecordId());
		iibLoan.setCurrentCreditLimit(uloLoan.getCurrentCreditLimit());
		iibLoan.setDisbursementAmt(uloLoan.getDisbursementAmt());
		iibLoan.setFirstInstallmentDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloLoan.getFirstInstallmentDate()));
		iibLoan.setFirstInterestDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloLoan.getFirstInterestDate()));	
		//iibLoan.setInstallmentAmt(uloLoan.getInstallmentAmt()); //Defect#2142 2138
		iibLoan.setInstallmentAmount(uloLoan.getInstallmentAmt());
		iibLoan.setLoanAmt(uloLoan.getLoanAmt());
		iibLoan.setLoanId(uloLoan.getLoanId());
		iibLoan.setMaxCreditLimit(uloLoan.getMaxCreditLimit());
		iibLoan.setMaxCreditLimitBot(uloLoan.getMaxCreditLimitBot());
		iibLoan.setMaxCreditLimitWithOutDBR(uloLoan.getMaxCreditLimitWithOutDBR());
		iibLoan.setMinCreditLimit(uloLoan.getMinCreditLimit());
		iibLoan.setOldAccountNo(uloLoan.getOldAccountNo());
		iibLoan.setPaymentMethodId(uloLoan.getPaymentMethodId());
		iibLoan.setRateType(uloLoan.getRateType());
		iibLoan.setRecommendLoanAmt(uloLoan.getRecommendLoanAmt());
		iibLoan.setRequestTerm(uloLoan.getRequestTerm());
		iibLoan.setSignSpread(uloLoan.getSignSpread());
		iibLoan.setSpecialProject(uloLoan.getSpecialProject());
		iibLoan.setTerm(uloLoan.getTerm());
		iibLoan.setFinalApproveAmt(uloLoan.getLoanAmt());
		iibLoan.setRequestLoanAmt(uloLoan.getRequestLoanAmt());
		iibLoan.setNormalCreditLimit(uloLoan.getFinalCreditLimit());
		
		// DF_FLP02552_2530 : Add Max Debt Burden Credit Limit
		iibLoan.setMaxDebtBurdenCreditLimit( uloLoan.getMaxDebtBurdenCreditLimit() );
	}
	
	public  void mapLoanCalcDiff(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan)
	{
		 mapLoan(uloLoan,iibLoan);
		 iibLoan.setFinalApproveAmt(null);
		 iibLoan.setRecommendLoanAmt(null);
		 iibLoan.setRequestLoanAmt(uloLoan.getLoanAmt());
		 iibLoan.setRequestTerm(uloLoan.getTerm());
	}
	
	public  void mapCard(CardDataM uloCard,decisionservice_iib.CardDataM iibCard){
		iibCard.setApplicationType(uloCard.getApplicationType());
		iibCard.setCampaignCode(uloCard.getCampaignCode());
		iibCard.setCardFee(uloCard.getCardFee());
		iibCard.setCardId(uloCard.getCardId());
		iibCard.setCardLevel(uloCard.getCardLevel());
		iibCard.setCardlinkCardCode(uloCard.getCardLinkCardCode());
		iibCard.setCardNo(uloCard.getCardNo());
		iibCard.setCardNoEncrypted(uloCard.getCardNoEncrypted());
		iibCard.setCardNoMark(uloCard.getCardNoMark());
		iibCard.setCardType(uloCard.getCardType());
		iibCard.setCardlinkCustId(uloCard.getCardlinkCustId());
		iibCard.setCgaApplyChannel(uloCard.getCgaApplyChannel());
		iibCard.setCgaCode(uloCard.getCgaCode());
		iibCard.setChassisNo(uloCard.getChassisNo());
		iibCard.setFlpCardType(uloCard.getFlpCardType());
		iibCard.setGangNo(uloCard.getGangNo());
		iibCard.setHashingCardNo(uloCard.getHashingCardNo());
		iibCard.setLoanId(uloCard.getLoanId());
		iibCard.setMainCardHolderName(uloCard.getMainCardHolderName());
		iibCard.setMainCardNo(uloCard.getMainCardNo());
		iibCard.setMainCardPhoneNo(uloCard.getMainCardPhoneNo());
		iibCard.setMaxEligibleCardLevel(uloCard.getMaxEligibleCardLevel());
		iibCard.setMaxEligibleCardType(uloCard.getMaxEligibleCardType());
		iibCard.setMembershipNo(uloCard.getMembershipNo());
		iibCard.setNoAppInGang(uloCard.getNoAppInGang());
		iibCard.setPercentLimitMaincard(uloCard.getPercentLimitMaincard());
		iibCard.setPersonalId(uloCard.getPersonalId());
		iibCard.setPlasticCode(uloCard.getPlasticCode());
		iibCard.setRecommendCardCode(uloCard.getRecommendCardCode());
		iibCard.setReferralCardNo(uloCard.getReferralCardNo());
		iibCard.setRequestCardType(uloCard.getRequestCardType());
		iibCard.setSeq(uloCard.getSeq());
		iibCard.setCardEntryType(ProcessUtil.getCardEntryType(uloCard.getApplicationType()));
		if(null!=uloCard.getWisdom()){
			decisionservice_iib.WisdomDataM iibWisdom = new decisionservice_iib.WisdomDataM();
			mapWisdom(uloCard.getWisdom(), iibWisdom);
			iibCard.setWisdom(iibWisdom);
		}
	}
	
	public  void mapWisdom(WisdomDataM uloWisdom,decisionservice_iib.WisdomDataM iibWisdom){
		iibWisdom.setCardId(uloWisdom.getCardId());
		iibWisdom.setInsuranceType(uloWisdom.getInsuranceType());
		iibWisdom.setNoPriorityPassSup(uloWisdom.getNoPriorityPassSup());
		iibWisdom.setPremiumAmt(uloWisdom.getPremiumAmt());
		iibWisdom.setPriorityPassFlag(uloWisdom.getPriorityPassFlag());
		iibWisdom.setPriorityPassMain(uloWisdom.getPriorityPassMain());
		iibWisdom.setPriorityPassMemo(uloWisdom.getPriorityPassMemo());
		iibWisdom.setPriorityPassNo(uloWisdom.getPriorityPassNo());
		iibWisdom.setQuotaOf(uloWisdom.getQuotaOf());
		iibWisdom.setReferralWisdomNo(uloWisdom.getReferralWisdomNo());
		iibWisdom.setReqPriorityPassSup(uloWisdom.getReqPriorityPassSup());
		iibWisdom.setTransferFrom(uloWisdom.getTransferFrom());
	}
	
	public  void mapCashTransfer(CashTransferDataM uloCashTransfer,decisionservice_iib.CashTransferDataM iibCashTransfer){
		iibCashTransfer.setAccountName(uloCashTransfer.getAccountName());
		iibCashTransfer.setBankTransfer(uloCashTransfer.getBankTransfer());
		iibCashTransfer.setCallForCashFlag(uloCashTransfer.getCallForCashFlag());
		iibCashTransfer.setCashTransferId(uloCashTransfer.getCashTransferId());
		iibCashTransfer.setCashTransferType(uloCashTransfer.getCashTransferType());
		iibCashTransfer.setCompleteData(uloCashTransfer.getCompleteData());
		iibCashTransfer.setExpressTransfer(uloCashTransfer.getExpressTransfer());
		iibCashTransfer.setFirstTransferAmount(uloCashTransfer.getFirstTransferAmount());
		iibCashTransfer.setLoanId(uloCashTransfer.getLoanId());
		iibCashTransfer.setPercentTransfer(uloCashTransfer.getPercentTransfer());
		iibCashTransfer.setProductType(uloCashTransfer.getProductType());
		iibCashTransfer.setTransferAccount(uloCashTransfer.getTransferAccount());
	}
	
	public  void mapLoanFee(LoanFeeDataM uloLoanFee,decisionservice_iib.LoanFeeDataM iibLoanFee){
		iibLoanFee.setEndMonth(uloLoanFee.getEndMonth());
		iibLoanFee.setFee(uloLoanFee.getFee());
		iibLoanFee.setFeeCalcType(uloLoanFee.getFeeCalcType());
		iibLoanFee.setFeeLevelType(uloLoanFee.getFeeLevelType());
		iibLoanFee.setFeeSrc(uloLoanFee.getFeeSrc());
		iibLoanFee.setFeeType(uloLoanFee.getFeeType());
		iibLoanFee.setFinalFeeAmount(uloLoanFee.getFinalFeeAmount());
		iibLoanFee.setMaxFee(uloLoanFee.getMaxFee());
		iibLoanFee.setMinFee(uloLoanFee.getMinFee());
		iibLoanFee.setRefId(uloLoanFee.getRefId());
		iibLoanFee.setSeq(uloLoanFee.getSeq());
		iibLoanFee.setStartMonth(uloLoanFee.getStartMonth());
	}
	
	public  void mapLoanPricing(LoanPricingDataM uloLoanPricing,decisionservice_iib.LoanPricingDataM iibLoanPricing){
		iibLoanPricing.setFeeEndMonth(uloLoanPricing.getFeeEndMonth());
		iibLoanPricing.setFeeStartMonth(uloLoanPricing.getFeeStartMonth());
		iibLoanPricing.setFeeWaiveCode(uloLoanPricing.getFeeWaiveCode());
		iibLoanPricing.setLoanId(uloLoanPricing.getLoanId());
		iibLoanPricing.setPricingId(uloLoanPricing.getPricingId());
		iibLoanPricing.setTerminationFeeCode(uloLoanPricing.getTerminationFeeCode());
		iibLoanPricing.setTerminationFeeFlag(uloLoanPricing.getTerminationFeeFlag());
	}
	
	public  void mapLoanTier(LoanTierDataM uloLoanTier,decisionservice_iib.LoanTierDataM iibLoanTier){
		iibLoanTier.setInterestRate(uloLoanTier.getIntRateAmount());
		iibLoanTier.setRateType(uloLoanTier.getRateType());
	}
	
	public  void mapReason(ReasonDataM uloReason,decisionservice_iib.ReasonDataM iibReason){
		iibReason.setApplicationRecordId(uloReason.getApplicationRecordId());
		iibReason.setReasonCode(uloReason.getReasonCode());
		iibReason.setReasonOthDesc(uloReason.getReasonOthDesc());
		iibReason.setReasonType(uloReason.getReasonType());
		iibReason.setRemark(uloReason.getRemark());
		iibReason.setRole(uloReason.getRole());
	}
	
	public  void mapApplicationImages(ArrayList<ApplicationImageSplitDataM> imageSplits ,decisionservice_iib.PersonalInfoDataM iibPersonalInfo){
		 if(null!=imageSplits && imageSplits.size()>0){
			 List<String> docTypes = new ArrayList<String>();
			 for(ApplicationImageSplitDataM  appImage : imageSplits){
				 logger.debug("appImage.getDocType()>>"+appImage.getDocType());
				 if(!docTypes.contains(appImage.getDocType())){
					 decisionservice_iib.DocumentDataM iibDocument = new decisionservice_iib.DocumentDataM();
					 iibDocument.setDocumentType(appImage.getDocType());
					 iibPersonalInfo.getDocuments().add(iibDocument);
					 docTypes.add(appImage.getDocType());
				 }
			 }
		
		 }
	}
	
	public KBankHeader getKbankHeader() {
		return kbankHeader;
	}

	public void setKbankHeader(KBankHeader kbankHeader) {
		this.kbankHeader = kbankHeader;
	}

}
