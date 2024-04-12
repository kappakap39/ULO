package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

@SuppressWarnings("serial")
public class LoanDataM implements Serializable,Cloneable{
	public LoanDataM(){
		super();
	}
	public LoanDataM(String referId,String uniqueId){
		super();
		this.applicationRecordId = referId;
		this.loanId = uniqueId;
	}
	public void init(String referId,String uniqueId){
		this.applicationRecordId = referId;
		this.loanId = uniqueId;
	}
	private String applicationRecordId;	//ORIG_LOAN.APPLICATION_RECORD_ID(VARCHAR2)
	private String loanId;	//ORIG_LOAN.LOAN_ID(VARCHAR2)
	private BigDecimal recommendLoanAmt;	//ORIG_LOAN.RECOMMEND_LOAN_AMT(NUMBER)
	private BigDecimal requestLoanAmt;	//ORIG_LOAN.REQUEST_LOAN_AMT(NUMBER)
	private BigDecimal loanAmt;	//ORIG_LOAN.LOAN_AMT(NUMBER)
	private BigDecimal installmentAmt;	//ORIG_LOAN.INSTALLMENT_AMT(NUMBER)
	private BigDecimal interestRate;	//ORIG_LOAN.INTEREST_RATE(NUMBER)
	private BigDecimal term;	//ORIG_LOAN.TERM(NUMBER)
	private BigDecimal finalCreditLimit;	//ORIG_LOAN.FINAL_CREDIT_LIMIT(NUMBER)
	private BigDecimal currentCreditLimit;	//ORIG_LOAN.CURRENT_CREDIT_LIMIT(NUMBER)
	private BigDecimal requestTerm;	//ORIG_LOAN.REQUEST_TERM(NUMBER)
	private String createBy;	//ORIG_LOAN.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_LOAN.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_LOAN.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_LOAN.UPDATE_DATE(DATE)	
	private String accountNo; //ORIG_LOAN.ACCOUNT_NO(VARCHAR2)
	private BigDecimal maxCreditLimit; //ORIG_LOAN.MAX_CREDIT_LIMIT(NUMBER)
	private BigDecimal minCreditLimit; //ORIG_LOAN.MAX_CREDIT_LIMIT(NUMBER)
	private BigDecimal maxCreditLimitBot; //ORIG_LOAN.MAX_CREDIT_LIMIT(NUMBER)
	private BigDecimal recommendTerm; //ORIG_LOAN.MAX_CREDIT_LIMIT(NUMBER)
	private Date firstInstallmentDate; //ORIG_LOAN.FIRST_INSTALLMENT_DATE(DATE)
	private String rateType; //ORIG_LOAN.RATE_TYPE(VARCHAR2)
	private String signSpread; //ORIG_LOAN.SIGN_SPREAD(VARCHAR2)
	private String specialProject; //ORIG_LOAN.SPECIAL_PROJECT(VARCHAR2)
	private BigDecimal disbursementAmt; //ORIG_LOAN.DISBURSEMENT_AMT(NUMBER)
	private String oldAccountNo; //ORIG_LOAN.OLD_ACCOUNT_NO(VARCHAR2)
	private Date firstInterestDate; //ORIG_LOAN.FIRST_INTEREST_DATE(DATE)
	private String paymentMethodId; //ORIG_LOAN.PAYMENT_METHOD_ID(VARCHAR2)
	private BigDecimal botFactor; //ORIG_LOAN.BOT_FACTOR(NUMBER)
	private String botCondition; //ORIG_LOAN.BOT_CONDITION(VARCHAR2)
    private BigDecimal productFactor; //ORIG_LOAN.PRODUCT_FACTOR(NUMBER)
	private BigDecimal debtBurden; // ORIG_LOAN.DEBT_BURDEN(NUMBER)
    private BigDecimal debtBurdenCreditLimit; // ORIG_LOAN.DEBT_BURDEN_CREDITLIMIT(NUMBER)
    private BigDecimal debtAmount; // ORIG_LOAN.DEBT_AMOUNT(NUMBER)
    private BigDecimal debtRecommend; // ORIG_LOAN.DEBT_RECOMMEND(NUMBER)
	private CardDataM card;
	private ArrayList<CashTransferDataM> cashTransfers;
	private ArrayList<String> specialAdditionalServiceIds = new ArrayList<String>();
	private ArrayList<LoanTierDataM> loanTiers;
	private ArrayList<LoanFeeDataM> loanFees;
	private String genPaymentMethod = "N";
	private String genSpecialAdditionalService = "N";
	private ArrayList<LoanPricingDataM> loanPricings;
	private BigDecimal NormalCreditLimit;
	
	//KPL Additional
	private Date accountOpenDate; //ORIG_LOAN.ACCOUNT_OPEN_DATE(DATE)
	private CapportLoanDataM capport;
	private BigDecimal stampDuty; //ORIG_LOAN.STAMP_DUTY NUMBER
	private String amountCapPortName; //ORIG_LOAN.AMOUNT_CAP_PORT_NAME  VARCHAR2(100)
	private String applicationCapPortName; //ORIG_LOAN.APPLICATION_CAPPORT_NAME VARCHAR2(100)
	
	private String coreBankProduct; //ORIG_LOAN.CB_PRODUCT VARCHAR2(30)
	private String coreBankSubProduct; //ORIG_LOAN.CB_SUB_PRODUCT VARCHAR2(30)
	private String coreBankMarketCode; //ORIG_LOAN.CB_MARKET_CODE VARCHAR2(30)
	
	//For CR0216
	private BigDecimal maxCreditLimitWithOutDBR; //ORIG_LOAN.MAX_CREDIT_LIMIT_WITHOUT_DBR(NUMBER)
	
	// DF_FLP02552_2530 : Add Max Debt Burden Credit Limit
	private BigDecimal maxDebtBurdenCreditLimit; //ORIG_LOAN.MAX_DBR_CREDIT_LIMIT(NUMBER)
	
	private String requestCoaProductCode;
	
	public String getCoreBankProduct() {
		return coreBankProduct;
	}
	public void setCoreBankProduct(String coreBankProduct) {
		this.coreBankProduct = coreBankProduct;
	}
	public String getCoreBankSubProduct() {
		return coreBankSubProduct;
	}
	public void setCoreBankSubProduct(String coreBankSubProduct) {
		this.coreBankSubProduct = coreBankSubProduct;
	}
	public String getCoreBankMarketCode() {
		return coreBankMarketCode;
	}
	public void setCoreBankMarketCode(String coreBankMarketCode) {
		this.coreBankMarketCode = coreBankMarketCode;
	}
	public BigDecimal getStampDuty() {
		return stampDuty;
	}
	public void setStampDuty(BigDecimal stampDuty) {
		this.stampDuty = stampDuty;
	}
	public String getAmountCapPortName() {
		return amountCapPortName;
	}
	public void setAmountCapPortName(String amountCapPortName) {
		this.amountCapPortName = amountCapPortName;
	}
	public String getApplicationCapPortName() {
		return applicationCapPortName;
	}
	public void setApplicationCapPortName(String applicationCapPortName) {
		this.applicationCapPortName = applicationCapPortName;
	}
	public CapportLoanDataM getCapport() {
		return capport;
	}
	public void setCapport(CapportLoanDataM capport) {
		this.capport = capport;
	}
	public Date getAccountOpenDate() {
		return accountOpenDate;
	}
	public void setAccountOpenDate(Date accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}
	
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getCurrentCreditLimit() {
		return currentCreditLimit;
	}
	public void setCurrentCreditLimit(BigDecimal currentCreditLimit) {
		this.currentCreditLimit = currentCreditLimit;
	}
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	public BigDecimal getFinalCreditLimit() {
		return finalCreditLimit;
	}
	public void setFinalCreditLimit(BigDecimal finalCreditLimit) {
		this.finalCreditLimit = finalCreditLimit;
	}
	public BigDecimal getRecommendLoanAmt() {
		return recommendLoanAmt;
	}
	public void setRecommendLoanAmt(BigDecimal recommendLoanAmt) {
		this.recommendLoanAmt = recommendLoanAmt;
	}
	public BigDecimal getRequestLoanAmt() {
		return requestLoanAmt;
	}
	public void setRequestLoanAmt(BigDecimal requestLoanAmt) {
		this.requestLoanAmt = requestLoanAmt;
	}	
	public BigDecimal getTerm() {
		return term;
	}
	public void setTerm(BigDecimal term) {
		this.term = term;
	}
	public BigDecimal getRequestTerm() {
		return requestTerm;
	}
	public void setRequestTerm(BigDecimal requestTerm) {
		this.requestTerm = requestTerm;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getMaxCreditLimit() {
		return maxCreditLimit;
	}
	public void setMaxCreditLimit(BigDecimal maxCreditLimit) {
		this.maxCreditLimit = maxCreditLimit;
	}
	public CardDataM getCard() {
		return card;
	}
	public void setCard(CardDataM card) {
		this.card = card;
	}	
	public ArrayList<CashTransferDataM> getCashTransfers() {
		return cashTransfers;
	}
	public void setCashTransfers(ArrayList<CashTransferDataM> cashTransfers) {
		this.cashTransfers = cashTransfers;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public BigDecimal getInstallmentAmt() {
		return installmentAmt;
	}
	public void setInstallmentAmt(BigDecimal installmentAmt) {
		this.installmentAmt = installmentAmt;
	}
	public CardDataM getCardInfo(String applicationType){
		if (card != null && applicationType.equals(card.getApplicationType())) {
			return card;
		}
		return null;
	}
	public CashTransferDataM getCashTransfer(String cashTransferType) {
		if(null != cashTransfers){
			for (CashTransferDataM cashTransfer : cashTransfers) {
				if(null != cashTransfer && null != cashTransferType && cashTransferType.equals(cashTransfer.getCashTransferType())){
					return cashTransfer;
				}
			}
		}
		return null;
	}
	public CashTransferDataM getCashTransfer() {
		if(null != cashTransfers){
			for (CashTransferDataM cashTransfer : cashTransfers) {
				if(null != cashTransfer){
					return cashTransfer;
				}
			}
		}
		return null;
	}
	public void removeCashTransfer(String cashTransferType) {
		if(null != cashTransfers){
			Iterator<CashTransferDataM> iter = cashTransfers.iterator();
			while(iter.hasNext()){
				CashTransferDataM cashTransfer = iter.next();
				if(null != cashTransfer && null != cashTransferType && cashTransferType.equals(cashTransfer.getCashTransferType())){
					iter.remove();
				}
			}
		}
	}
	public CashTransferDataM getCashTransfer(String []cashTransFerType) {		
		ArrayList<String> cashTransFerTypes = new ArrayList<String>(Arrays.asList(cashTransFerType));
		if(null != cashTransfers){
			for (CashTransferDataM cashTransfer : cashTransfers) {
				if(null != cashTransfer && null != cashTransFerTypes && cashTransFerTypes.contains(cashTransfer.getCashTransferType())){
					return cashTransfer;
				}
			}
		}
		return null;
	}
	public void removeCashTransfer(String []cashTransFerType) {		
		ArrayList<String> cashTransFerTypes = new ArrayList<String>(Arrays.asList(cashTransFerType));
		if(null != cashTransfers){
			Iterator<CashTransferDataM> iter = cashTransfers.iterator();
			while(iter.hasNext()){
				CashTransferDataM cashTransfer = iter.next();
				if(null != cashTransfer && null != cashTransFerTypes && cashTransFerTypes.contains(cashTransfer.getCashTransferType())){
					iter.remove();
				}
			}
		}
	}
	public void removeEmptyCashTransferType() {
		if(null != cashTransfers){
			Iterator<CashTransferDataM> iter = cashTransfers.iterator();
			while(iter.hasNext()){
				CashTransferDataM cashTransfer = iter.next();
				if(null == cashTransfer.getCashTransferType()){
					iter.remove();
				}
			}
		}
	}	
	public void addCashTransfer(CashTransferDataM cashTransfer){
		if(null == cashTransfers){
			cashTransfers = new ArrayList<CashTransferDataM>(); 
		}
		cashTransfers.add(cashTransfer);
	}
	public BigDecimal getMinCreditLimit() {
		return minCreditLimit;
	}
	public void setMinCreditLimit(BigDecimal minCreditLimit) {
		this.minCreditLimit = minCreditLimit;
	}
	public BigDecimal getMaxCreditLimitBot() {
		return maxCreditLimitBot;
	}
	public void setMaxCreditLimitBot(BigDecimal maxCreditLimitBot) {
		this.maxCreditLimitBot = maxCreditLimitBot;
	}
	public BigDecimal getRecommendTerm() {
		return recommendTerm;
	}
	public void setRecommendTerm(BigDecimal recommendTerm) {
		this.recommendTerm = recommendTerm;
	}
	public Date getFirstInstallmentDate() {
		return firstInstallmentDate;
	}
	public void setFirstInstallmentDate(Date firstInstallmentDate) {
		this.firstInstallmentDate = firstInstallmentDate;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getSignSpread() {
		return signSpread;
	}
	public void setSignSpread(String signSpread) {
		this.signSpread = signSpread;
	}
	public String getSpecialProject() {
		return specialProject;
	}
	public void setSpecialProject(String specialProject) {
		this.specialProject = specialProject;
	}
	public BigDecimal getDisbursementAmt() {
		return disbursementAmt;
	}
	public void setDisbursementAmt(BigDecimal disbursementAmt) {
		this.disbursementAmt = disbursementAmt;
	}
	public String getOldAccountNo() {
		return oldAccountNo;
	}
	public void setOldAccountNo(String oldAccountNo) {
		this.oldAccountNo = oldAccountNo;
	}
	public Date getFirstInterestDate() {
		return firstInterestDate;
	}
	public void setFirstInterestDate(Date firstInterestDate) {
		this.firstInterestDate = firstInterestDate;
	}	
	public ArrayList<String> getSpecialAdditionalServiceIds() {
		return specialAdditionalServiceIds;
	}
	public void setSpecialAdditionalServiceIds(ArrayList<String> specialAdditionalServiceIds) {
		this.specialAdditionalServiceIds = specialAdditionalServiceIds;
	}
	public ArrayList<LoanTierDataM> getLoanTiers() {
		return loanTiers;
	}
	
	public LoanTierDataM  getLoanTierRateType(String rateType) {
		if(null!=loanTiers && null !=rateType){
			for(LoanTierDataM loanTier : loanTiers){
				if(rateType.equals(loanTier.getRateType())){
					return loanTier;
				}
			}
		}
		return null;
	}
	
	public void setLoanTiers(ArrayList<LoanTierDataM> loanTiers) {
		this.loanTiers = loanTiers;
	}
	
	public void addLoanTiers(LoanTierDataM loanTierDataM ) {
		if(null==loanTiers){
			loanTiers = new ArrayList<LoanTierDataM>();
		}
		loanTiers.add(loanTierDataM);
	}
	
	public ArrayList<LoanFeeDataM> getLoanFees() {
		return loanFees;
	}
	public void setLoanFees(ArrayList<LoanFeeDataM> loanFees) {
		this.loanFees = loanFees;
	}
	public String getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public void addAdditionalServiceId(String serviceId) {
		if(null == specialAdditionalServiceIds) {
			specialAdditionalServiceIds = new ArrayList<String>();
		}
		if(!specialAdditionalServiceIds.contains(serviceId)) {
			specialAdditionalServiceIds.add(serviceId);
		}
	}
	public void removeAdditionalServiceId(String serviceId) {
		if(null != specialAdditionalServiceIds) {
			Iterator<String> iter = specialAdditionalServiceIds.iterator();
			while(iter.hasNext()) {
				if(iter.next().equals(serviceId)) {
					iter.remove();
				}
			}
		}
	}
	public String getGenPaymentMethod() {
		return genPaymentMethod;
	}
	public void setGenPaymentMethod(String genPaymentMethod) {
		this.genPaymentMethod = genPaymentMethod;
	}
	public String getGenSpecialAdditionalService() {
		return genSpecialAdditionalService;
	}
	public void setGenSpecialAdditionalService(String genSpecialAdditionalService) {
		this.genSpecialAdditionalService = genSpecialAdditionalService;
	}	
	public ArrayList<LoanPricingDataM> getLoanPricings() {
		return loanPricings;
	}
	public LoanPricingDataM  getLoanPricingById(String pricingId) {
		if(null!=loanPricings && null!=pricingId){
			for(LoanPricingDataM loanPricing :loanPricings){
				if(pricingId.equals(loanPricing.getPricingId())){
					return loanPricing;
				}
			}
		}
		return null;
	}
	
	public void setLoanPricings(ArrayList<LoanPricingDataM> loanPricings) {
		this.loanPricings = loanPricings;
	}
	public void addLoanPricings(LoanPricingDataM loanPricing) {
		if(null==loanPricings){
			loanPricings = new ArrayList<LoanPricingDataM>();
		}
		loanPricings.add(loanPricing);
	}
	
	public BigDecimal getNormalCreditLimit() {
		return NormalCreditLimit;
	}
	public void setNormalCreditLimit(BigDecimal normalCreditLimit) {
		NormalCreditLimit = normalCreditLimit;
	}
	public BigDecimal getBotFactor() {
		return botFactor;
	}
	public void setBotFactor(BigDecimal botFactor) {
		this.botFactor = botFactor;
	}
	public String getBotCondition() {
		return botCondition;
	}
	public void setBotCondition(String botCondition) {
		this.botCondition = botCondition;
	}
	
	public BigDecimal getProductFactor() {
		return productFactor;
	}
	public void setProductFactor(BigDecimal productFactor) {
		this.productFactor = productFactor;
	}
	public BigDecimal getDebtBurden() {
		return debtBurden;
	}
	public void setDebtBurden(BigDecimal debtBurden) {
		this.debtBurden = debtBurden;
	}
	public BigDecimal getDebtBurdenCreditLimit() {
		return debtBurdenCreditLimit;
	}
	public void setDebtBurdenCreditLimit(BigDecimal debtBurdenCreditlimit) {
		this.debtBurdenCreditLimit = debtBurdenCreditlimit;
	}
	public BigDecimal getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}
	public BigDecimal getDebtRecommend() {
		return debtRecommend;
	}
	public void setDebtRecommend(BigDecimal debtRecomment) {
		this.debtRecommend = debtRecomment;
	}
	public BigDecimal getMaxCreditLimitWithOutDBR() {
		return maxCreditLimitWithOutDBR;
	}
	public void setMaxCreditLimitWithOutDBR(BigDecimal maxCreditLimitWithOutDBR) {
		this.maxCreditLimitWithOutDBR = maxCreditLimitWithOutDBR;
	}
	public String getRequestCoaProductCode() {
		return requestCoaProductCode;
	}
	public void setRequestCoaProductCode(String requestCoaProductCode) {
		this.requestCoaProductCode = requestCoaProductCode;
	}
	public BigDecimal getMaxDebtBurdenCreditLimit() {
		return maxDebtBurdenCreditLimit;
	}
	public void setMaxDebtBurdenCreditLimit(BigDecimal maxDebtBurdenCreditLimit) {
		this.maxDebtBurdenCreditLimit = maxDebtBurdenCreditLimit;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoanDataM [applicationRecordId=");
		builder.append(applicationRecordId);
		builder.append(", loanId=");
		builder.append(loanId);
		builder.append(", recommendLoanAmt=");
		builder.append(recommendLoanAmt);
		builder.append(", requestLoanAmt=");
		builder.append(requestLoanAmt);
		builder.append(", loanAmt=");
		builder.append(loanAmt);
		builder.append(", installmentAmt=");
		builder.append(installmentAmt);
		builder.append(", interestRate=");
		builder.append(interestRate);
		builder.append(", term=");
		builder.append(term);
		builder.append(", finalCreditLimit=");
		builder.append(finalCreditLimit);
		builder.append(", currentCreditLimit=");
		builder.append(currentCreditLimit);
		builder.append(", requestTerm=");
		builder.append(requestTerm);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append(", maxCreditLimit=");
		builder.append(maxCreditLimit);
		builder.append(", minCreditLimit=");
		builder.append(minCreditLimit);
		builder.append(", maxCreditLimitBot=");
		builder.append(maxCreditLimitBot);
		builder.append(", recommendTerm=");
		builder.append(recommendTerm);
		builder.append(", firstInstallmentDate=");
		builder.append(firstInstallmentDate);
		builder.append(", rateType=");
		builder.append(rateType);
		builder.append(", signSpread=");
		builder.append(signSpread);
		builder.append(", specialProject=");
		builder.append(specialProject);
		builder.append(", disbursementAmt=");
		builder.append(disbursementAmt);
		builder.append(", oldAccountNo=");
		builder.append(oldAccountNo);
		builder.append(", firstInterestDate=");
		builder.append(firstInterestDate);
		builder.append(", paymentMethodId=");
		builder.append(paymentMethodId);
		builder.append(", card=");
		builder.append(card);
		builder.append(", cashTransfers=");
		builder.append(cashTransfers);
		builder.append(", specialAdditionalServiceIds=");
		builder.append(specialAdditionalServiceIds);
		builder.append(", loanTiers=");
		builder.append(loanTiers);
		builder.append(", loanFees=");
		builder.append(loanFees);
		builder.append(", genPaymentMethod=");
		builder.append(genPaymentMethod);
		builder.append(", genSpecialAdditionalService=");
		builder.append(genSpecialAdditionalService);
		builder.append(", botFactor=");
		builder.append(botFactor);
		builder.append(", botCondition=");
		builder.append(botCondition);
		
		builder.append(", productFactor=");
		builder.append(productFactor);
		builder.append(", debtBurden=");
		builder.append(debtBurden);
		builder.append(", debtBurdenCreditLimit=");
		builder.append(debtBurdenCreditLimit);
		builder.append(", debtAmount=");
		builder.append(debtAmount);
		builder.append(", debtRecomment=");
		builder.append("debtRecommend");
		builder.append(", maxCreditLimitWithOutDBR=");
		builder.append(maxCreditLimitWithOutDBR);
		builder.append(", maxDebtBurdenCreditLimit=");
		builder.append(maxDebtBurdenCreditLimit);
		builder.append("]");
		return builder.toString();
	}	
	  
	
}
