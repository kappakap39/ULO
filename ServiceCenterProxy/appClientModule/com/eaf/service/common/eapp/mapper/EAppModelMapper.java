package com.eaf.service.common.eapp.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.submitapplication.model.AdditionalServices;
import com.ava.flp.eapp.submitapplication.model.Addresses;
import com.ava.flp.eapp.submitapplication.model.ApplicationGroup;
import com.ava.flp.eapp.submitapplication.model.Applications;
import com.ava.flp.eapp.submitapplication.model.Card;
import com.ava.flp.eapp.submitapplication.model.CardLinkCustomers;
import com.ava.flp.eapp.submitapplication.model.CashTransfers;
import com.ava.flp.eapp.submitapplication.model.Documents;
import com.ava.flp.eapp.submitapplication.model.KycInfo;
import com.ava.flp.eapp.submitapplication.model.LoanTier;
import com.ava.flp.eapp.submitapplication.model.Loans;
import com.ava.flp.eapp.submitapplication.model.PaymentMethods;
import com.ava.flp.eapp.submitapplication.model.PersonalInfos;
import com.ava.flp.eapp.submitapplication.model.ReferencePersons;
import com.ava.flp.eapp.submitapplication.model.SaleInfos;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.KYCDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanPricingDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

public class EAppModelMapper {
	
	private Logger logger = Logger.getLogger(EAppModelMapper.class);
	private String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	private String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private String CC_BUS_CLASS = SystemConstant.getConstant("CC_BUS_CLASS");
	private String KEC_BUS_CLASS = SystemConstant.getConstant("KEC_BUS_CLASS");
	private String KPL_BUS_CLASS = SystemConstant.getConstant("KPL_BUS_CLASS");
	private String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	private int PRIORITY_SPECIAL = SystemConstant.getIntConstant("PRIORITY_SPECIAL");
	private String COVER_PAGE_TYPE_NEW = SystemConstant.getConstant("COVER_PAGE_TYPE_NEW");
	private String COVERPAGE_PRIORITY_HIGH = SystemConstant.getConstant("COVERPAGE_PRIORITY_HIGH");
	private String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	private boolean foundCC = false;
	private boolean foundKEC = false;
	private boolean foundKPL = false;
	private boolean isFirstCC = true;
	private boolean isFirstKEC = true;
	private boolean isFirstKPL = true;
	private HashMap<String, ArrayList<String>> serviceLoanMap = new HashMap<String, ArrayList<String>>();
	private HashMap<String, String> paymentLoanMap = new HashMap<String, String>();
	private String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	private String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE");
	private String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	private String TITLE_OTHER = SystemConstant.getConstant("TITLE_OTHER");
	private String APPLICATION_TYPE_NEW = SystemConstant.getConstant("APPLICATION_TYPE_NEW");
	private String PROCEED = SystemConstant.getConstant("DECISION_SERVICE_PROCEED");
	private String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	private String CALL_ACTION_PROCEED = SystemConstant.getConstant("CALL_ACTION_PROCEED");
	private String FIELD_ID_SPECIAL_ADDITIONAL_SERVICE = SystemConstant.getConstant("FIELD_ID_SPECIAL_ADDITIONAL_SERVICE");
	private String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String MLP_PREFIX = SystemConstant.getConstant("MLP_PREFIX");
    private String CALL_ACTION_PRE_APPROVED = SystemConstant.getConstant("CALL_ACTION_PRE_APPROVED");
	
	public ApplicationGroupDataM mapUloModel(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup eApplicationGroup) throws Exception {
		if(null == uloApplicationGroup){
			uloApplicationGroup = new ApplicationGroupDataM();
		}
		
		String applicationGroupId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_GROUP_PK);
		
		
		uloApplicationGroup.setApplicationDate(Formatter.toSQLDate(getDateValue(eApplicationGroup.getApplicationDate()), "", Formatter.Format.yyyyMMddHHmmss));
		uloApplicationGroup.setApplicationGroupNo(eApplicationGroup.getApplicationGroupNo());
		uloApplicationGroup.setApplicationTemplate(eApplicationGroup.getApplicationTemplate());
		uloApplicationGroup.setApplyChannel(eApplicationGroup.getApplyChannel());
		uloApplicationGroup.setApplyDate(Formatter.toSQLDate(getDateValue(eApplicationGroup.getApplyDate()), "", Formatter.Format.yyyyMMddHHmmss));
		uloApplicationGroup.setRcCode(eApplicationGroup.getRcCode());
		uloApplicationGroup.setRandomNo1(EAppUtil.bigDecimalToInt(eApplicationGroup.getRandomNo1()));
		uloApplicationGroup.setRandomNo2(EAppUtil.bigDecimalToInt(eApplicationGroup.getRandomNo2()));
		uloApplicationGroup.setRandomNo3(EAppUtil.bigDecimalToInt(eApplicationGroup.getRandomNo3()));
		uloApplicationGroup.setBranchNo(eApplicationGroup.getBranchNo());
		
		uloApplicationGroup.setApplicationGroupId(applicationGroupId);
		
		//default source
		uloApplicationGroup.setSource(MConstant.SOURCE_NAME.TABLET);
		uloApplicationGroup.setSourceAction(eApplicationGroup.getCallAction());
		
		uloApplicationGroup.setCoverpagePriority(COVERPAGE_PRIORITY_HIGH);
		uloApplicationGroup.setCoverpageType(COVER_PAGE_TYPE_NEW);
		
		String appType = ProcessUtil.getFinalSubmitApplicationTypes(eApplicationGroup.getApplications());
		if (null !=appType && !appType.isEmpty() && !"".equals(appType)) {
			ProcessUtil.setApplyType(appType, uloApplicationGroup);
		}else{
			logger.debug("apply type is null!!");
		}
		
		logger.debug("appType>>>"+appType);
		logger.debug("appType>>>"+uloApplicationGroup.getApplicationType());
		
		uloApplicationGroup.setApplicationType(APPLICATION_TYPE_NEW);
		
		uloApplicationGroup.setPriority(PRIORITY_SPECIAL);
		
		//default execute flag by pass IA
		uloApplicationGroup.setExecuteFlag(MConstant.FLAG_Y);
		
		mapPersonal(uloApplicationGroup, eApplicationGroup);
		mapApplication(uloApplicationGroup, eApplicationGroup);
		mapReferencePerson(uloApplicationGroup, eApplicationGroup);
		mapSaleInfo(uloApplicationGroup, eApplicationGroup);
		
		return uloApplicationGroup;
	}
	
	public void mapApplication(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup eApplicationGroup) throws Exception{
		if(null!=eApplicationGroup.getApplications() && eApplicationGroup.getApplications().size()>0){
			for(Applications eApplication : eApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = new ApplicationDataM();
				String applicationNo = GenerateUnique.generateApplicationNo(uloApplicationGroup.getApplicationGroupNo()
						, uloApplicationGroup.itemLifeCycle()+1,uloApplicationGroup.getMaxLifeCycle());
				
				uloApplicationGroup.addApplications(uloApplication);
				
				String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
				String applyType = EAppUtil.getApplyType(eApplicationGroup.getApplications());
				
				if(PRODUCT_CRADIT_CARD.equalsIgnoreCase(eApplication.getOrgId())){
					foundCC = true;
				}
				else if(PRODUCT_K_EXPRESS_CASH.equalsIgnoreCase(eApplication.getOrgId())){
					foundKEC = true;
				}
				else if(PRODUCT_K_PERSONAL_LOAN.equalsIgnoreCase(eApplication.getOrgId())){
					foundKPL = true;
				}
				
				uloApplication.setApplicationRecordId(applicationRecordId);
				uloApplication.setSeq(EAppUtil.bigDecimalToInt(eApplication.getSeq()));
				uloApplication.setBusinessClassId(getULOBusinessClass(eApplication.getOrgId()));
				uloApplication.setApplicationType(eApplication.getApplyType());
				uloApplication.setMaincardRecordId(eApplication.getMainApplicationRecordId());
				uloApplication.seteRecommendDecision(ProcessUtil.getULORecommendDecision(eApplication.getRecommendDecision()));
				uloApplication.setRecommendDecision(PROCEED);
				uloApplication.setPreviousRecommendDecision(PROCEED);
				uloApplication.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
				
				if (null != applyType && !applyType.isEmpty() && !"".equals(applyType)) {
					ProcessUtil.setApplyType(applyType, uloApplicationGroup);
				}else{
					logger.debug("Apply type is null.");
				}
				
				//default set life cycle
//				uloApplication.setLifeCycle(1);
				uloApplication.setLifeCycle(Math.max(1,uloApplicationGroup.getMaxLifeCycle()));
				uloApplication.setApplicationNo(applicationNo);
				
				uloApplication.seteApplicationRecordId(eApplication.getApplicationRecordId());
				
				String personalId = mapPersonalRelation(uloApplicationGroup, applicationRecordId, eApplication.getPersonalId(), eApplicationGroup);
				maploan(uloApplicationGroup, uloApplication, eApplication, personalId);
				
				if(MConstant.Product.CREDIT_CARD.equalsIgnoreCase(eApplication.getOrgId())){
					isFirstCC = false;
				}
				else if(MConstant.Product.K_EXPRESS_CASH.equalsIgnoreCase(eApplication.getOrgId())){
					isFirstKEC = false;
				}
				else if(MConstant.Product.K_PERSONAL_LOAN.equalsIgnoreCase(eApplication.getOrgId())){
					isFirstKPL = false;
				}
				uloApplication.setApplyTypeFlag(eApplication.getApplyTypeFlag());
				uloApplication.setPolicyProgramId(eApplication.getPolicyProgramId());
				
				//MLP Additional
				uloApplication.setProjectCode(eApplication.getProjectCode());
				
				CardlinkCustomerDataM cardLinkDataM = ApplicationUtil.getCardlinkCustomer(uloApplicationGroup, applicationRecordId);
				if(cardLinkDataM == null)
				{
					for(PersonalInfos ePersonal : eApplicationGroup.getPersonalInfos())
					{
						if(personalId.equals(ePersonal.getPersonalId()) && ePersonal.getCardLinkCustomers() != null)
						{
							for(CardLinkCustomers eCardLinkCustomer : ePersonal.getCardLinkCustomers())
							{
								if(eCardLinkCustomer != null)
								{
									PersonalInfoDataM personalInfo = uloApplicationGroup.getPersonalInfoByRelation(applicationRecordId);
									String businessClassId = uloApplication.getBusinessClassId();
									logger.info("businessClassId : "+businessClassId);
									String orgId = CacheControl.getName(CACHE_BUSINESS_CLASS,"BUS_CLASS_ID",businessClassId,"ORG_ID");
									String orgNo = CacheControl.getName(CACHE_ORGANIZATION,"ORG_ID",orgId,"ORG_NO");
									logger.debug("orgId : "+orgId);
									logger.debug("orgNo : "+orgNo);
									
									CardlinkCustomerDataM cardLink = new CardlinkCustomerDataM();
									cardLink.setPersonalId(ePersonal.getPersonalId());
									cardLink.setOrgId(orgNo);
									
									if(!Util.empty(eCardLinkCustomer.getCardLinkCustNumber()))
									{
										//Use MLP CardlinkCustomerNo
										String cardlinkCustNo = eCardLinkCustomer.getCardLinkCustNumber();
										logger.debug("cardlinkCustNo : "+cardlinkCustNo);
										cardLink.setCardlinkCustNo(cardlinkCustNo);
										cardLink.setNewCardlinkCustFlag(MConstant.FLAG.NO);
									}
									else
									{
										//Create New CardlinkCustomerNo
										UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
										CardDataM cardM = uloApplication.getCard();
										String cardlinkCustNo = uniqueIDDAO.getCardLinkNo(cardM);
										logger.debug("cardlinkCustNo : "+cardlinkCustNo);
										cardLink.setCardlinkCustNo(cardlinkCustNo);
										cardLink.setNewCardlinkCustFlag(MConstant.FLAG.YES);
									}
									personalInfo.addCardLinkCustomer(cardLink);
								}
							}
						}
					}
				}
				
			}
		}
		
		mapLoanAdditionalService(uloApplicationGroup);
		mapLoanPaymentMethod(uloApplicationGroup);
	}
	
	public void maploan(ApplicationGroupDataM uloApplicationGroup, ApplicationDataM uloApplication, Applications eApplication, String personalId) throws Exception{
		if(null!=eApplication.getLoans() && eApplication.getLoans().size()>0){
			for(Loans eLoan : eApplication.getLoans()){
				LoanDataM uloLoan = new LoanDataM();
				uloApplication.addLoan(uloLoan);
				String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
				
				uloLoan.setRequestLoanAmt(eLoan.getRequestLoanAmt());
				uloLoan.setRequestTerm(eLoan.getRequestTerm());
				uloLoan.setLoanId(loanId);
				
				//<MLP Additional>
				if(!Util.empty(eLoan.getRecommendLoanAmt()))
				{
					uloLoan.setRecommendLoanAmt(eLoan.getRecommendLoanAmt());
					uloLoan.setLoanAmt(eLoan.getRecommendLoanAmt());
				}
				if(!Util.empty(eLoan.getFeeWaiveCode()))
				{
					LoanPricingDataM loanPricing = new LoanPricingDataM();
					loanPricing.setLoanId(uloLoan.getLoanId());
					loanPricing.setPricingId(GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PRICING_PK));
					loanPricing.setFeeWaiveCode(eLoan.getFeeWaiveCode());
					uloLoan.addLoanPricings(loanPricing);
				}
				//</MLP Additional>
				
				mapCard(uloLoan, eApplication, eLoan);
				mapCashTransfer(uloLoan, eLoan);
				
				if((foundCC && isFirstCC) || (foundKEC && isFirstKEC) || (foundKPL && isFirstKPL)){
					mapAdditionalService(uloApplicationGroup, uloApplication, uloLoan, eLoan, personalId, eApplication.getOrgId());
					mapPaymentMethod(uloApplicationGroup, uloLoan, eLoan, personalId, eApplication.getOrgId());
				}
				
				//CJD Additional
				if(eLoan.getLoanTiers() != null && eLoan.getLoanTiers().size() > 0)
				{
					int ltSeq = 0;
					for(LoanTier eLoanTier : eLoan.getLoanTiers())
					{
						LoanTierDataM loanTier = new LoanTierDataM();
						String loanTierId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_TIER_PK);
						loanTier.setSeq(ltSeq);
						loanTier.setLoanId(loanId);
						loanTier.setTierId(loanTierId);
						loanTier.setRateType(eLoanTier.getRateType());
						loanTier.setIntRateAmount(eLoanTier.getIntRateAmount());
						uloLoan.addLoanTiers(loanTier);
						ltSeq++;
					}
				}
			}
		}
	}
	
	public void mapCard(LoanDataM uloLoan, Applications eApplication, Loans eappLoan) throws Exception{
		if(null!=eappLoan.getCard()){
			Card eappCard = eappLoan.getCard();
			CardDataM uloCard = new CardDataM();
			uloLoan.setCard(uloCard);
			
			String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
			
			String cardCode = eappCard.getCardType();
			logger.debug("cardCode : " + cardCode);
			logger.debug("cardLevel : " + eappCard.getCardLevel());
			logger.debug("product : " + eApplication.getOrgId());
			
			String cardType = ServiceFactory.getServiceDAO().getCardType(cardCode, eappCard.getCardLevel(), eApplication.getOrgId());
			logger.debug("cardType : " + cardType);
			
			uloCard.setCardId(cardId);
			uloCard.setLoanId(uloLoan.getLoanId());
			uloCard.setCardLevel(eappCard.getCardLevel());
			uloCard.setCardType(cardType);
			uloCard.setCardNo(eappCard.getCardNo());
			uloCard.setCgaApplyChannel(eappCard.getCgaApplyChannel());
			uloCard.setCgaCode(eappCard.getCgaCode());
			uloCard.setChassisNo(eappCard.getChassisNo());
			uloCard.setMainCardNo(eappCard.getMainCardNo());
			uloCard.setMembershipNo(eappCard.getMembershipNo());
			uloCard.setPercentLimitMaincard(EAppUtil.bigDecimalToString(eappCard.getPercentLimitMainCard()));
			uloCard.setRequestCardType(cardType);
			
			uloCard.setApplicationType(APPLICATION_CARD_TYPE_BORROWER);
			
			//MLP Additional
			uloCard.setCardLinkCardCode(eappCard.getCardLinkcardCode());
			uloCard.setPlasticCode(eappCard.getPlasticCode());
			uloCard.setCardlinkCustId(eappCard.getCardLinkCustomerId());
			if(!Util.empty(uloLoan.getLoanPricings()))
			{
				LoanPricingDataM loanPricing = uloLoan.getLoanPricings().get(0);
				if(loanPricing != null)
				{
					uloCard.setCardFee(loanPricing.getFeeWaiveCode());
				}
			}
		}
	}
	
	public void mapAdditionalService(ApplicationGroupDataM uloApplicationGroup, ApplicationDataM uloApplication, LoanDataM uloLoan, Loans eLoan, String personalId, String product) throws Exception{
		logger.debug("mapAdditionalService");
		if(null != eLoan.getAdditionalServices() && eLoan.getAdditionalServices().size() > 0){
			ArrayList<String> service = new ArrayList<String>();
			for(AdditionalServices eappAdditionalService : eLoan.getAdditionalServices()){
				SpecialAdditionalServiceDataM uloAdditionalService = new SpecialAdditionalServiceDataM();
				uloApplicationGroup.addSpecialAdditionalService(uloAdditionalService);
				
				String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				
				uloAdditionalService.setServiceId(serviceId);
				uloAdditionalService.setCurrentAccName(eappAdditionalService.getCurrentAccName());
				uloAdditionalService.setCurrentAccNo(eappAdditionalService.getCurrentAccNo());
				uloAdditionalService.setSavingAccName(eappAdditionalService.getSavingAccName());
				uloAdditionalService.setSavingAccNo(eappAdditionalService.getSavingAccNo());
//				uloAdditionalService.setServiceType(eappAdditionalService.getServiceType());
				//convert to flp value
				String serviceType = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, "MAPPING6", eappAdditionalService.getServiceType(), "CHOICE_NO");
				if ( SPECIAL_ADDITIONAL_SERVICE_POSTAL.equals( serviceType ) ) {
					uloApplication.setLetterChannel( MConstant.FLAG_Y );
				}
				uloAdditionalService.setServiceType(serviceType);
				uloAdditionalService.setPersonalId(personalId);
				
				uloLoan.addAdditionalServiceId(serviceId);
				
				service.add(serviceId);
			}
			serviceLoanMap.put(product, service);
		}
	}
	
	public void mapCashTransfer(LoanDataM uloLoan, Loans eappLoan) throws Exception{
		if(null != eappLoan.getCashTransfers() && eappLoan.getCashTransfers().size() > 0){
			for(CashTransfers eappCashTransfer : eappLoan.getCashTransfers()){
				CashTransferDataM uloCashTransfer = new CashTransferDataM();
				uloLoan.addCashTransfer(uloCashTransfer);
				
				uloCashTransfer.setAccountName(eappCashTransfer.getAccountName());
				uloCashTransfer.setCashTransferType(eappCashTransfer.getCashTransferType());
				uloCashTransfer.setExpressTransfer(eappCashTransfer.getExpressTransfer());
				uloCashTransfer.setFirstTransferAmount(eappCashTransfer.getFirstTransferAmount());
				uloCashTransfer.setPercentTransfer(eappCashTransfer.getPercentTransfer());
				uloCashTransfer.setTransferAccount(eappCashTransfer.getTransferAccount());
				uloCashTransfer.setCallForCashFlag(eappCashTransfer.getCallForCashFlag());
				
				uloCashTransfer.setLoanId(uloLoan.getLoanId());
			}
		}
	}
	
	public void mapPaymentMethod(ApplicationGroupDataM uloApplicationGroup, LoanDataM uloLoan, Loans eLoan, String personalId, String product) throws Exception{
		logger.debug("mapPaymentMethod");
		if(null != eLoan.getPaymentMethods() && eLoan.getPaymentMethods().size() > 0){
			PaymentMethods eappPaymentMethod = eLoan.getPaymentMethods().get(0);
//			for(PaymentMethods eappPaymentMethod : eLoan.getPaymentMethods()){
				PaymentMethodDataM uloPaymentMethod = new PaymentMethodDataM();
				uloApplicationGroup.addPaymentMethod(uloPaymentMethod);
				
				String paymentMethodId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
				
				uloPaymentMethod.setPaymentMethodId(paymentMethodId);
				uloPaymentMethod.setAccountName(eappPaymentMethod.getAccountName());
				uloPaymentMethod.setAccountNo(eappPaymentMethod.getAccountNo());
				uloPaymentMethod.setAccountType(eappPaymentMethod.getAccountType());
				uloPaymentMethod.setBankCode(eappPaymentMethod.getBankCode());
				uloPaymentMethod.setDueCycle(eappPaymentMethod.getDueCycle());
				uloPaymentMethod.setPaymentMethod(eappPaymentMethod.getPaymentMethod());
				uloPaymentMethod.setPaymentRatio(eappPaymentMethod.getPaymentRatio());
				uloPaymentMethod.setPersonalId(personalId);
				uloPaymentMethod.setCompleteData(INFO_IS_CORRECT);
				
				uloLoan.setPaymentMethodId(paymentMethodId);
				
				paymentLoanMap.put(product, paymentMethodId);
//			}
		}
	}
	
	public void mapReferencePerson(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup eApplicationGroup) throws Exception{
		if(null != eApplicationGroup.getReferencePersons() && eApplicationGroup.getReferencePersons().size() > 0){
			for(ReferencePersons eReferencePerson : eApplicationGroup.getReferencePersons()){
				ReferencePersonDataM uloRefPerson = new ReferencePersonDataM();
				uloApplicationGroup.addReferencePerson(uloRefPerson);
				
				uloRefPerson.setMobile(eReferencePerson.getMobile());
				uloRefPerson.setOfficePhone(eReferencePerson.getOfficePhone());
				uloRefPerson.setOfficePhoneExt(eReferencePerson.getOfficePhoneExt());
				uloRefPerson.setPhone1(eReferencePerson.getPhone1());
				uloRefPerson.setExt1(eReferencePerson.getExt1());
				uloRefPerson.setRelation(eReferencePerson.getRelation());
				uloRefPerson.setSeq(EAppUtil.bigDecimalToInt(eReferencePerson.getSeq()));
				uloRefPerson.setThFirstName(eReferencePerson.getThFirstName());
				uloRefPerson.setThLastName(eReferencePerson.getThLastName());
				uloRefPerson.setThTitleCode(eReferencePerson.getThTitleCode());
				
				uloRefPerson.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
			}
		}
		
	}
	
	public void mapPersonal(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup eApplicationGroup) throws Exception{
		if(null != eApplicationGroup.getPersonalInfos() && eApplicationGroup.getPersonalInfos().size() > 0){
			for(PersonalInfos ePersonal : eApplicationGroup.getPersonalInfos()){
				mapPersonal(uloApplicationGroup, ePersonal);
			}
		}
	}
	
	public void mapAddress(PersonalInfoDataM uloPersonal, PersonalInfos ePersonal) throws Exception{
		if(null != ePersonal.getAddresses() && ePersonal.getAddresses().size() > 0){
			for(Addresses eappAddress : ePersonal.getAddresses()){
				AddressDataM uloAddress = new AddressDataM();
				uloPersonal.addAddress(uloAddress);
				
				uloAddress.setAddress(eappAddress.getAddress());
				uloAddress.setAddressType(eappAddress.getAddressType());
				uloAddress.setAdrsts(eappAddress.getAdrsts());
				uloAddress.setAmphur(eappAddress.getAmphur());
				uloAddress.setBuilding(eappAddress.getBuilding());
				uloAddress.setBuildingType(eappAddress.getBuildingType());
				uloAddress.setCompanyName(eappAddress.getCompanyName());
				uloAddress.setCompanyTitle(eappAddress.getCompanyTitle());
				uloAddress.setCountry(eappAddress.getCountry());
				uloAddress.setDepartment(eappAddress.getDepartment());
				uloAddress.setExt1(eappAddress.getExt1());
				uloAddress.setFax(eappAddress.getFax());
				uloAddress.setFloor(eappAddress.getFloor());
				uloAddress.setMobile(eappAddress.getMobile());
				uloAddress.setMoo(eappAddress.getMoo());
				uloAddress.setPhone1(eappAddress.getPhone1());
				uloAddress.setProvinceDesc(eappAddress.getProvince());
				uloAddress.setRents(eappAddress.getRents());
				uloAddress.setResidem(eappAddress.getResideMonth());
				uloAddress.setResidey(eappAddress.getResideYear());
				uloAddress.setRoad(eappAddress.getRoad());
				uloAddress.setRoom(eappAddress.getRoom());
				uloAddress.setSeq(EAppUtil.bigDecimalToInt(eappAddress.getSeq()));
				uloAddress.setSoi(eappAddress.getSoi());
				uloAddress.setState(eappAddress.getState());
				uloAddress.setTambol(eappAddress.getTambol());
				uloAddress.setVatCode(eappAddress.getVatCode());
				uloAddress.setVatRegistration(eappAddress.getVatRegistration());
				uloAddress.setVilapt(eappAddress.getVilapt());
				uloAddress.setZipcode(eappAddress.getZipcode());
				uloAddress.setPersonalId(uloPersonal.getPersonalId());
				uloAddress.setResidenceProvince(eappAddress.getResidenceProvince());
				DisplayAddressUtil.setAddressLine(uloAddress);
			}
		}
	}

	public void mapKycInfo(PersonalInfoDataM uloPersonal, PersonalInfos ePersonal) throws Exception{
		if(null != ePersonal.getKycInfos() && ePersonal.getKycInfos().size() > 0){
			for(KycInfo eappKyc : ePersonal.getKycInfos()){
				KYCDataM uloKyc = new KYCDataM();
				uloPersonal.setKyc(uloKyc);
				
				uloKyc.setRelationFlag(eappKyc.getRelationFlag());
				uloKyc.setRelTitleDesc(eappKyc.getRelTitleDesc());
				uloKyc.setRelName(eappKyc.getRelName());
				uloKyc.setRelSurname(eappKyc.getRelSurname());
				uloKyc.setRelPosition(eappKyc.getRelPosition());
				uloKyc.setRelDetail(eappKyc.getRelDetail());
				uloKyc.setWorkStartDate(Formatter.toSQLDate(getDateValue(eappKyc.getWorkStartDate()), "", Formatter.Format.yyyyMMddHHmmss));
				uloKyc.setWorkEndDate(Formatter.toSQLDate(getDateValue(eappKyc.getWorkEndDate()), "", Formatter.Format.yyyyMMddHHmmss));
				uloKyc.setRelTitleName(eappKyc.getRelTitleName());
				uloKyc.setCustomerRiskGrade(eappKyc.getCustomerRiskGrade());
				uloKyc.setSanctionList(eappKyc.getSanctionList());
				uloKyc.setPersonalId(uloPersonal.getPersonalId());
			}
		}
	}
	
	public void mapSaleInfo(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup eApplicationGroup) throws Exception{
		if(null != eApplicationGroup.getSaleInfos() && eApplicationGroup.getSaleInfos().size() >0){
			for(SaleInfos eappSaleInfo : eApplicationGroup.getSaleInfos()){
				SaleInfoDataM uloSaleInfo = new SaleInfoDataM();
				uloApplicationGroup.addSaleInfos(uloSaleInfo);
				
				uloSaleInfo.setSalesId(eappSaleInfo.getSalesId());
				uloSaleInfo.setSalesType(eappSaleInfo.getSalesType());
				
				uloSaleInfo.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
			}
		}
	}
	
	public void mapDocument(ApplicationGroupDataM uloApplicationGroup, PersonalInfos ePersonal) throws Exception{
		if(null != ePersonal.getDocuments() && ePersonal.getDocuments().size() > 0){
			//get first
			//set lh ref id to app group
			for(Documents eappDocument : ePersonal.getDocuments()){
				if(!Util.empty(eappDocument.getLhRefId())){
					uloApplicationGroup.setLhRefId(eappDocument.getLhRefId());
					
					//if have lhRefId then set call action to Proceed (only E-App)
                    if ( CALL_ACTION_PRE_APPROVED.equals( uloApplicationGroup.getSourceAction() ) ) {
                    	uloApplicationGroup.setSourceAction(CALL_ACTION_PROCEED);
                    }
					
					break;
				}
			}
		}
	}
	
	public String getULOBusinessClass(String orgId){
		if(PRODUCT_CRADIT_CARD.equals(orgId)){
			return CC_BUS_CLASS;
		}
		else if(PRODUCT_K_EXPRESS_CASH.equals(orgId)){
			return KEC_BUS_CLASS;
		}
		else if(PRODUCT_K_PERSONAL_LOAN.equals(orgId)){
			return KPL_BUS_CLASS;
		}
		return null;
	}
	
	public String mapPersonalRelation(ApplicationGroupDataM uloApplicationGroup, String applicationRecordId, String refPersonalId, ApplicationGroup eApplicationGroup) throws Exception{
		if(null!=eApplicationGroup.getPersonalInfos() && eApplicationGroup.getPersonalInfos().size()>0){
			for(PersonalInfoDataM uloPersonal : uloApplicationGroup.getPersonalInfos()){
				String ePersonalId = uloPersonal.getePersonalId();
				logger.debug("ePersonalId " + ePersonalId);
				if(refPersonalId.equals(ePersonalId)){
					uloPersonal.addPersonalRelation(applicationRecordId, uloPersonal.getPersonalType(), APPLICATION_LEVEL);
					logger.debug("personalInfo.getPersonalId() " + uloPersonal.getPersonalId());
					return uloPersonal.getPersonalId();
				}
			}
		}
		return null;
	}
	
	public PersonalInfoDataM mapPersonal(ApplicationGroupDataM uloApplicationGroup, PersonalInfos ePersonal) throws Exception{
		PersonalInfoDataM uloPersonal = new PersonalInfoDataM();
		uloApplicationGroup.addPersonalInfo(uloPersonal);
		
		String personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
		int PERSONAL_SEQ = uloApplicationGroup.personalSize(ePersonal.getPersonalType())+1;
		uloPersonal.setSeq(PERSONAL_SEQ);
		uloPersonal.setPersonalType(ePersonal.getPersonalType());
		uloPersonal.setBirthDate(Formatter.toSQLDate(getDateValue(ePersonal.getBirthDate()), "", Formatter.Format.yyyyMMddHHmmss));
		
		if(CIDTYPE_PASSPORT.equals(ePersonal.getCidType())){
			uloPersonal.setCidExpDate(Formatter.toSQLDate(getDateValue(ePersonal.getPassportExpiryDate()), "", Formatter.Format.yyyyMMddHHmmss));
		}
		else{
			uloPersonal.setCidExpDate(Formatter.toSQLDate(getDateValue(ePersonal.getCidExpDate()), "", Formatter.Format.yyyyMMddHHmmss));
		}
		uloPersonal.setCidIssueDate(Formatter.toSQLDate(getDateValue(ePersonal.getCidIssueDate()), "", Formatter.Format.yyyyMMddHHmmss));
		uloPersonal.setPlaceReceiveCard(ePersonal.getMailingAddress());
		uloPersonal.setBusinessType(ePersonal.getBusinessType());
		uloPersonal.setCisNo(ePersonal.getCisNo());
		uloPersonal.setCisPrimSegment(ePersonal.getCisPrimSegment());
		uloPersonal.setCisPrimSubSegment(ePersonal.getCisPrimSubSegment());
		uloPersonal.setCisSecSegment(ePersonal.getCisSecSegment());
		uloPersonal.setCisSecSubSegment(ePersonal.getCisSecSubSegment());
		uloPersonal.setCompanyCISId(ePersonal.getCompanyCisId());
		uloPersonal.setContactTime(ePersonal.getContactTime());
		uloPersonal.setCustomerType(ePersonal.getCustomerType());
		uloPersonal.setDegree(ePersonal.getDegree());
		uloPersonal.setDisclosureFlag(ePersonal.getDisclosureFlag());
		uloPersonal.setEnFirstName(ePersonal.getEnFirstName());
		uloPersonal.setEnLastName(ePersonal.getEnLastName());
		uloPersonal.setEnMidName(ePersonal.getEnMidName());
		uloPersonal.setEnTitleCode(ePersonal.getEnTitleCode());
		uloPersonal.setFreelanceIncome(ePersonal.getFreelanceIncome());
		uloPersonal.setFreelanceType(ePersonal.getFreelanceType());
		uloPersonal.setGender(ePersonal.getGender());
		uloPersonal.setIdno(ePersonal.getIdNo());
		uloPersonal.setCidType(ePersonal.getCidType());
		uloPersonal.setMarried(ePersonal.getMarried());
		uloPersonal.setMobileNo(ePersonal.getMobileNo());
		uloPersonal.setMonthlyExpense(ePersonal.getMonthlyExpense());
		uloPersonal.setMonthlyIncome(ePersonal.getMonthlyIncome());
		uloPersonal.setNationality(ePersonal.getNationality());
		uloPersonal.setNetProfitIncome(ePersonal.getNetProfitIncome());
		uloPersonal.setNoOfChild(ePersonal.getNumberOfChild());
		uloPersonal.setOccupation(ePersonal.getOccupation());
		uloPersonal.setPrevCompanyTitle(ePersonal.getPreviousCompanyTitle());
		uloPersonal.setPersonalId(personalId);
		uloPersonal.setPrevWorkMonth(ePersonal.getPreviousWorkMonth());
		uloPersonal.setPrevWorkYear(ePersonal.getPreviousWorkYear());
		uloPersonal.setProfession(ePersonal.getProfession());
		uloPersonal.setProfessionOther(ePersonal.getProfessionOther());
		uloPersonal.setReceiveIncomeBank(ePersonal.getReceiveIncomeBank());
		uloPersonal.setRelationshipType(ePersonal.getRelationshipType());
		uloPersonal.setBureauRequiredFlag(ePersonal.getRequireBureau());
		uloPersonal.setNcbTranNo(ePersonal.getNcbTranNo());
		uloPersonal.setConsentRefNo(ePersonal.getConsentRefNo());
//		uloPersonal.setSalary(ePersonal.getSalary());
		uloPersonal.setSalary(ePersonal.getIncome());
		uloPersonal.setSorceOfIncomeOther(ePersonal.getSourceOfIncomeOther());
		uloPersonal.setSrcOthIncOther(ePersonal.getSrcOthIncOther());
		uloPersonal.setSrcOthIncBonus(ePersonal.getSrcOthIncBonus());
		uloPersonal.setSrcOthIncCommission(ePersonal.getSrcOthIncCommission());
		uloPersonal.setThFirstName(ePersonal.getThFirstName());
		uloPersonal.setThLastName(ePersonal.getThLastName());
		uloPersonal.setThMidName(ePersonal.getThMidName());
		uloPersonal.setThTitleCode(ePersonal.getThTitleCode());
		uloPersonal.setTotWorkMonth(ePersonal.getTotalWorkMonth());
		uloPersonal.setTotWorkYear(ePersonal.getTotalWorkYear());
		uloPersonal.setApplicationIncome(ePersonal.getIncome());
		uloPersonal.setVatRegistFlag(ePersonal.getVatRegisterFlag());
		uloPersonal.setVisaExpDate(Formatter.toSQLDate(getDateValue(ePersonal.getVisaExpiryDate()), "", Formatter.Format.yyyyMMddHHmmss));
		uloPersonal.setVisaType(ePersonal.getVisaType());
		uloPersonal.setWorkPermitExpDate(Formatter.toSQLDate(getDateValue(ePersonal.getWorkPermitExpiryDate()), "", Formatter.Format.yyyyMMddHHmmss));
		uloPersonal.setWorkPermitNo(ePersonal.getWorkPermitNo());
		uloPersonal.setMailingAddress(ePersonal.getMailingAddress());
		//position
		uloPersonal.setPositionLevel(ePersonal.getPositionLevel());
		//salaryLevel
		uloPersonal.setPrevCompany(ePersonal.getPreviousCompanyName());
		uloPersonal.setSpecialMerchantType(ePersonal.getSpecialMerchant());
		uloPersonal.setBusinessTypeOther(ePersonal.getBusinessTypeOther());
		uloPersonal.setPositionDesc(ePersonal.getPositionDesc());
		uloPersonal.setPositionCode(ePersonal.getPositionCode());
		uloPersonal.setBusinessNature(ePersonal.getBusinessNature());
		uloPersonal.setEmailPrimary(ePersonal.getEmailPrimary());
		uloPersonal.setSelfDeclareLimit(ePersonal.getSelfDeclareLimit());
		uloPersonal.setNumberOfIssuer(ePersonal.getNumberOfIssuer());
		uloPersonal.setmThOldLastName(ePersonal.getMThOldLastName());
		uloPersonal.setmThLastName(ePersonal.getMThLastName());
		uloPersonal.setmTitleDesc(ePersonal.getMTitleDesc());
		uloPersonal.setmHomeTel(ePersonal.getMHomeTel());
		uloPersonal.setmIncome(ePersonal.getMIncome());
		uloPersonal.setmPosition(ePersonal.getMPosition());
		uloPersonal.setMonthlyIncome(ePersonal.getOtherIncome());
		uloPersonal.setOtherIncome(ePersonal.getOtherIncome());
		uloPersonal.setOccpationOther(ePersonal.getOccupationOther());
		
		uloPersonal.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
		uloPersonal.setePersonalId(ePersonal.getPersonalId());
		uloPersonal.setCisPrimDesc(ePersonal.getCisPrimDesc());
		uloPersonal.setCisPrimSubDesc(ePersonal.getCisPrimSubDesc());
		uloPersonal.setCisSecDesc(ePersonal.getCisSecDesc());
		uloPersonal.setCisSecSubDesc(ePersonal.getCisSecSubDesc());
		uloPersonal.setLineId(ePersonal.getLineId());
		uloPersonal.setIncomeResource(ePersonal.getIncomeResource());
		uloPersonal.setIncomeResourceOther(ePersonal.getIncomeResourceOther());
		uloPersonal.setCountryOfIncome(ePersonal.getCountryOfIncome());
		
		//default id no consent by id no
		uloPersonal.setIdNoConsent(ePersonal.getIdNo());
		
		String thTitleDesc = ListBoxControl.getName(FIELD_ID_TH_TITLE_CODE, ePersonal.getThTitleCode());
		if(Util.empty(thTitleDesc)){
			uloPersonal.setThTitleCode(TITLE_OTHER);
			uloPersonal.setThTitleDesc(ePersonal.getThTitleDesc());
		}
		else{
			uloPersonal.setThTitleDesc(thTitleDesc);
		}
				
		String enTitleDesc = ListBoxControl.getName(FIELD_ID_EN_TITLE_CODE, ePersonal.getEnTitleCode());
		if(Util.empty(enTitleDesc)){
			uloPersonal.setEnTitleCode(TITLE_OTHER);
			uloPersonal.setEnTitleDesc(ePersonal.getEnTitleDesc());
		}
		else{
			uloPersonal.setEnTitleDesc(enTitleDesc);
		}
		
		mapAddress(uloPersonal, ePersonal);
		mapKycInfo(uloPersonal, ePersonal);
		mapDocument(uloApplicationGroup, ePersonal);
		
		//MLP Additional
		uloPersonal.setStaffFlag(ePersonal.getStaffFlag());
		uloPersonal.setTotVerifiedIncome(ePersonal.getVerifyIncome());
		uloPersonal.setTypeOfFin(ePersonal.getTypeOfFin());
		ePersonal.setPersonalId(personalId);
		
		String applicationGroupNo =  uloApplicationGroup.getApplicationGroupNo();
		if(applicationGroupNo.startsWith(MLP_PREFIX) && !Util.empty(ePersonal.getConsentRefNo()))
		{
			ArrayList<NcbInfoDataM> ncbInfos = new ArrayList<NcbInfoDataM>();
			NcbInfoDataM ncbInfo = new NcbInfoDataM();
			ncbInfo.setPersonalId(personalId);
			ncbInfo.setConsentRefNo(ePersonal.getConsentRefNo());
			ncbInfos.add(ncbInfo);
			uloPersonal.setNcbInfos(ncbInfos);
		}
		
		return uloPersonal;
	}
	
	public void mapLoanAdditionalService(ApplicationGroupDataM uloApplicationGroup) throws Exception{
		if(null != serviceLoanMap && serviceLoanMap.size() > 0) {
			logger.debug("additionalServiceIds : " + serviceLoanMap);
			if(null != uloApplicationGroup.getApplications() && uloApplicationGroup.getApplications().size() > 0){
				for(ApplicationDataM application : uloApplicationGroup.getApplications()){
					if(null != application.getLoans() && application.getLoans().size() > 0){
						for(LoanDataM loan : application.getLoans()){
							for(String serviceId : serviceLoanMap.get(application.getProduct())){
								loan.addAdditionalServiceId(serviceId);
							}
						}
					}
				}
			}
		}
	}
	
	public void mapLoanPaymentMethod(ApplicationGroupDataM uloApplicationGroup) throws Exception{
		if(null != paymentLoanMap && paymentLoanMap.size() > 0) {
			logger.debug("paymentMethodIds : " + paymentLoanMap);
			if(null != uloApplicationGroup.getApplications() && uloApplicationGroup.getApplications().size() > 0){
				for(ApplicationDataM application : uloApplicationGroup.getApplications()){
					if(null != application.getLoans() && application.getLoans().size() > 0){
						for(LoanDataM loan : application.getLoans()){
							loan.setPaymentMethodId(paymentLoanMap.get(application.getProduct()));
						}
					}
				}
			}
		}
	}
	
	public String getDateValue(String date){
		if(Util.empty(date)){
			return null;
		}
		return date = date.substring(0, 19).replace("T", " ");
	}
}
