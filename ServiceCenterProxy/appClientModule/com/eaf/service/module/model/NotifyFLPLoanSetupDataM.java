package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class NotifyFLPLoanSetupDataM implements Serializable, Cloneable {

	String ismId;
	String to;
	String  de2SubmitDate;
	String toPerform;
	String loanAcctRel;
	String cisId;		//3.1 setupLoanInfoResult -> CIS_NO
	String customerName;
	String productGroup;	// 4.1 GeneralParam -> LOAN_SETUP_PRODUCT_GROUP (LN) 
	String productType;
	String acctSubType;
	String marketCode;		// 4.4 setupLoanInfoResult -> CB_MARKET_CODE
	String acctTitleThai;	// 5.1 ACCT_TITILE_THAI ( "TH_TITLE_DESC" + " " + "TH_FIRST_NAME" + " " + "TH_MID_NAME" + " " + "TH_LAST_NAME"  )
	String idNo;			// 5.2 setupLoanInfoResult -> IDNO
	BigDecimal contractLimit;
	BigDecimal creditLimit;
	String currency;	// 5.6 GeneralParam -> LOAN_SETUP_CURRENCY (THB)
	String  contractDate;
	Integer term;		// 5.8 setupLoanInfoResult -> TERM
	String termUnit;	// 5.9 GeneralParam -> LOAN_SETUP_TERM_UNIT (M)
	String  disbursementDate;
	String paymentFrequency;	// 5.11 GeneralParam -> LOAN_SETUP_PAYMENT_FREQ (1MA25)
	String  firstPaymentDue;
	String deductAcct;
	String businessCode;	// 6.1 GeneralParam -> LOAN_SETUP_BUSINESS_CODE (010050000)
	String specialProject;
	String finalityCode;
	String ownerBranch;
	String accountOption;
	String earlyPayoffPenaltyMtd;
	String  interestEffectiveDate;
	String interestIndex;
	BigDecimal interestSpread;	// 8.3 setupLoanInfoResult -> INTEREST_RATE
	String  installmentEffectiveDate;
	String paymentCalculation;	// 8.5 GeneralParam -> LOAN_SETUP_PAYMENT_CAL (15)
	BigDecimal installmentAmount;	// 8.6 setupLoanInfoResult -> INSTALLMENT_AMT
	BigDecimal disbursementAmount;
	String custAcctNo1;
	BigDecimal custAcctNoAmt1;
	BigDecimal borrowStampDutyAmt;
	String unsecuredApprovalName;
	String unsecuredApprovalId;
	String unsecuredApprovalIniName;
	String unsecuredApprovalIniId;
	String stmtAddrId;
	
	public String getIsmId() {
		return ismId;
	}
	public void setIsmId(String ismId) {
		this.ismId = ismId;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDe2SubmitDate() {
		return de2SubmitDate;
	}
	public void setDe2SubmitDate(String de2SubmitDate) {
		this.de2SubmitDate = de2SubmitDate;
	}
	public String getToPerform() {
		return toPerform;
	}
	public void setToPerform(String toPerform) {
		this.toPerform = toPerform;
	}
	public String getLoanAcctRel() {
		return loanAcctRel;
	}
	public void setLoanAcctRel(String loanAcctRel) {
		this.loanAcctRel = loanAcctRel;
	}
	public String getCisId() {
		return cisId;
	}
	public void setCisId(String cisId) {
		this.cisId = cisId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getAcctSubType() {
		return acctSubType;
	}
	public void setAcctSubType(String acctSubType) {
		this.acctSubType = acctSubType;
	}
	public String getMarketCode() {
		return marketCode;
	}
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	public String getAcctTitleThai() {
		return acctTitleThai;
	}
	public void setAcctTitleThai(String acctTitleThai) {
		this.acctTitleThai = acctTitleThai;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public BigDecimal getContractLimit() {
		return contractLimit;
	}
	public void setContractLimit(BigDecimal contractLimit) {
		this.contractLimit = contractLimit;
	}
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getContractDate() {
		return contractDate;
	}
	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public String getTermUnit() {
		return termUnit;
	}
	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
	}
	public String getDisbursementDate() {
		return disbursementDate;
	}
	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}
	public String getPaymentFrequency() {
		return paymentFrequency;
	}
	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}
	public String getFirstPaymentDue() {
		return firstPaymentDue;
	}
	public void setFirstPaymentDue(String firstPaymentDue) {
		this.firstPaymentDue = firstPaymentDue;
	}
	public String getDeductAcct() {
		return deductAcct;
	}
	public void setDeductAcct(String deductAcct) {
		this.deductAcct = deductAcct;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getSpecialProject() {
		return specialProject;
	}
	public void setSpecialProject(String specialProject) {
		this.specialProject = specialProject;
	}
	public String getFinalityCode() {
		return finalityCode;
	}
	public void setFinalityCode(String finalityCode) {
		this.finalityCode = finalityCode;
	}
	public String getOwnerBranch() {
		return ownerBranch;
	}
	public void setOwnerBranch(String ownerBranch) {
		this.ownerBranch = ownerBranch;
	}
	public String getAccountOption() {
		return accountOption;
	}
	public void setAccountOption(String accountOption) {
		this.accountOption = accountOption;
	}
	public String getEarlyPayoffPenaltyMtd() {
		return earlyPayoffPenaltyMtd;
	}
	public void setEarlyPayoffPenaltyMtd(String earlyPayoffPenaltyMtd) {
		this.earlyPayoffPenaltyMtd = earlyPayoffPenaltyMtd;
	}
	public String getInterestEffectiveDate() {
		return interestEffectiveDate;
	}
	public void setInterestEffectiveDate(String interestEffectiveDate) {
		this.interestEffectiveDate = interestEffectiveDate;
	}
	public String getInterestIndex() {
		return interestIndex;
	}
	public void setInterestIndex(String interestIndex) {
		this.interestIndex = interestIndex;
	}
	public BigDecimal getInterestSpread() {
		return interestSpread;
	}
	public void setInterestSpread(BigDecimal interestSpread) {
		this.interestSpread = interestSpread;
	}
	public String getInstallmentEffectiveDate() {
		return installmentEffectiveDate;
	}
	public void setInstallmentEffectiveDate(String installmentEffectiveDate) {
		this.installmentEffectiveDate = installmentEffectiveDate;
	}
	public String getPaymentCalculation() {
		return paymentCalculation;
	}
	public void setPaymentCalculation(String paymentCalculation) {
		this.paymentCalculation = paymentCalculation;
	}
	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public BigDecimal getDisbursementAmount() {
		return disbursementAmount;
	}
	public void setDisbursementAmount(BigDecimal disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}
	public String getCustAcctNo1() {
		return custAcctNo1;
	}
	public void setCustAcctNo1(String custAcctNo1) {
		this.custAcctNo1 = custAcctNo1;
	}
	public BigDecimal getCustAcctNoAmt1() {
		return custAcctNoAmt1;
	}
	public void setCustAcctNoAmt1(BigDecimal custAcctNoAmt1) {
		this.custAcctNoAmt1 = custAcctNoAmt1;
	}
	public BigDecimal getBorrowStampDutyAmt() {
		return borrowStampDutyAmt;
	}
	public void setBorrowStampDutyAmt(BigDecimal borrowStampDutyAmt) {
		this.borrowStampDutyAmt = borrowStampDutyAmt;
	}
	public String getUnsecuredApprovalName() {
		return unsecuredApprovalName;
	}
	public void setUnsecuredApprovalName(String unsecuredApprovalName) {
		this.unsecuredApprovalName = unsecuredApprovalName;
	}
	public String getUnsecuredApprovalId() {
		return unsecuredApprovalId;
	}
	public void setUnsecuredApprovalId(String unsecuredApprovalId) {
		this.unsecuredApprovalId = unsecuredApprovalId;
	}
	public String getUnsecuredApprovalIniName() {
		return unsecuredApprovalIniName;
	}
	public void setUnsecuredApprovalIniName(String unsecuredApprovalIniName) {
		this.unsecuredApprovalIniName = unsecuredApprovalIniName;
	}
	public String getUnsecuredApprovalIniId() {
		return unsecuredApprovalIniId;
	}
	public void setUnsecuredApprovalIniId(String unsecuredApprovalIniId) {
		this.unsecuredApprovalIniId = unsecuredApprovalIniId;
	}
	public String getStmtAddrId() {
		return stmtAddrId;
	}
	public void setStmtAddrId(String stmtAddrId) {
		this.stmtAddrId = stmtAddrId;
	}

}
