package com.eaf.inf.batch.ulo.memo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InstructionMemoDataM implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public InstructionMemoDataM() {
		super();
	}

	private String applicationGroupNo;			// 1
	private String dearTo;				// 2
	private String tariffCode;				// 4
	private String productName;				// 5
	private Date applyDate;				// 5.1 Value Date
	private String IdNo;				// 6 Owner ID
	private String ownerName;				// 7 Owner
	private String administrativeBranch;		// 7.1
	private String administrativeBranchOrg;		// 8
	private String rateType;				// 9
	private String signSpread;				// 10
	private String primaryAccName;			// 11 Primary Acc. Name (TH)
	private String secondaryAccName;			// 12 Secondary Acc. Name (EN)
	private String term;				// 13 Original Contract Term
	private String safeAccNo;				// 14 SAFE Account NO.
	private String finality;				// 15
	private String cnaeCode;				// 16 CNAE Code
	private String specialProject;			// 17
	private BigDecimal loanAmt;				// 18 Granted Capital A06
	private Date firstInstallmentDate;			// 19, 20 Loans Fixed Day
	private BigDecimal monthlyInstallment;		// 21 Manual Installment Amount
	private Date firstInterestDate;			// 22, 23 Settlement Date
	private List<DebitInterestDataM> debitInterests;	// 24, 25, 26, 27
	private List<String> finalFeeAmt;			// 28
	private BigDecimal disbursementAmt;			// 29 Final Approved Amount
	private String accountNo;				// 30 C/O

	/**
	 * finalFeeAmount 28 Advance Cancellation Fee/Commiss 31 - 35 Final Fee
	 * Amount
	 */
	// private BigDecimal finalFeeAmount2; // 31
	// private BigDecimal finalFeeAmount3; // 32
	// private BigDecimal finalFeeAmount4; // 33
	// private BigDecimal finalFeeAmount5; // 34
	// private BigDecimal finalFeeAmount6; // 35

	private List<BigDecimal> finalFeeAmtOther;		// 31 - 35

	/** Close **/
	private String oldAccountNo;			// 6, 7
	private String paymentReason;			// 9
	private String blAccountNo;				// 10
	private String amountToPay;				// 11

	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}

	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}

	public String getDearTo() {
		return dearTo;
	}

	public void setDearTo(String dearTo) {
		this.dearTo = dearTo;
	}

	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getIdNo() {
		return IdNo;
	}

	public void setIdNo(String idNo) {
		IdNo = idNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getAdministrativeBranch() {
		return administrativeBranch;
	}

	public void setAdministrativeBranch(String administrativeBranch) {
		this.administrativeBranch = administrativeBranch;
	}

	public String getAdministrativeBranchOrg() {
		return administrativeBranchOrg;
	}

	public void setAdministrativeBranchOrg(String administrativeBranchOrg) {
		this.administrativeBranchOrg = administrativeBranchOrg;
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

	public String getPrimaryAccName() {
		return primaryAccName;
	}

	public void setPrimaryAccName(String primaryAccName) {
		this.primaryAccName = primaryAccName;
	}

	public String getSecondaryAccName() {
		return secondaryAccName;
	}

	public void setSecondaryAccName(String secondaryAccName) {
		this.secondaryAccName = secondaryAccName;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getSafeAccNo() {
		return safeAccNo;
	}

	public void setSafeAccNo(String safeAccNo) {
		this.safeAccNo = safeAccNo;
	}

	public String getFinality() {
		return finality;
	}

	public void setFinality(String finality) {
		this.finality = finality;
	}

	public String getCnaeCode() {
		return cnaeCode;
	}

	public void setCnaeCode(String cnaeCode) {
		this.cnaeCode = cnaeCode;
	}

	public String getSpecialProject() {
		return specialProject;
	}

	public void setSpecialProject(String specialProject) {
		this.specialProject = specialProject;
	}

	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}

	public Date getFirstInstallmentDate() {
		return firstInstallmentDate;
	}

	public void setFirstInstallmentDate(Date firstInstallmentDate) {
		this.firstInstallmentDate = firstInstallmentDate;
	}

	public BigDecimal getMonthlyInstallment() {
		return monthlyInstallment;
	}

	public void setMonthlyInstallment(BigDecimal monthlyInstallment) {
		this.monthlyInstallment = monthlyInstallment;
	}

	public Date getFirstInterestDate() {
		return firstInterestDate;
	}

	public void setFirstInterestDate(Date firstInterestDate) {
		this.firstInterestDate = firstInterestDate;
	}

	public List<DebitInterestDataM> getDebitInterests() {
		return debitInterests;
	}

	public void setDebitInterests(List<DebitInterestDataM> debitInterests) {
		this.debitInterests = debitInterests;
	}

	/*
	 * public BigDecimal getFinalFeeAmount2() { return finalFeeAmount2; }
	 * 
	 * public void setFinalFeeAmount2(BigDecimal finalFeeAmount2) {
	 * this.finalFeeAmount2 = finalFeeAmount2; }
	 * 
	 * public BigDecimal getFinalFeeAmount3() { return finalFeeAmount3; }
	 * 
	 * public void setFinalFeeAmount3(BigDecimal finalFeeAmount3) {
	 * this.finalFeeAmount3 = finalFeeAmount3; }
	 * 
	 * public BigDecimal getFinalFeeAmount4() { return finalFeeAmount4; }
	 * 
	 * public void setFinalFeeAmount4(BigDecimal finalFeeAmount4) {
	 * this.finalFeeAmount4 = finalFeeAmount4; }
	 * 
	 * public BigDecimal getFinalFeeAmount5() { return finalFeeAmount5; }
	 * 
	 * public void setFinalFeeAmount5(BigDecimal finalFeeAmount5) {
	 * this.finalFeeAmount5 = finalFeeAmount5; }
	 * 
	 * public BigDecimal getFinalFeeAmount6() { return finalFeeAmount6; }
	 * 
	 * public void setFinalFeeAmount6(BigDecimal finalFeeAmount6) {
	 * this.finalFeeAmount6 = finalFeeAmount6; }
	 */

	public List<String> getFinalFeeAmt() {
		return finalFeeAmt;
	}

	public List<BigDecimal> getFinalFeeAmtOther() {
		return finalFeeAmtOther;
	}

	public void setFinalFeeAmtOther(List<BigDecimal> finalFeeAmtOther) {
		this.finalFeeAmtOther = finalFeeAmtOther;
	}

	public void setFinalFeeAmt(List<String> finalFeeAmt) {
		this.finalFeeAmt = finalFeeAmt;
	}

	public BigDecimal getDisbursementAmt() {
		return disbursementAmt;
	}

	public void setDisbursementAmt(BigDecimal disbursementAmt) {
		this.disbursementAmt = disbursementAmt;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOldAccountNo() {
		return oldAccountNo;
	}

	public void setOldAccountNo(String oldAccountNo) {
		this.oldAccountNo = oldAccountNo;
	}

	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	public String getBlAccountNo() {
		return blAccountNo;
	}

	public void setBlAccountNo(String blAccountNo) {
		this.blAccountNo = blAccountNo;
	}

	public String getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(String amountToPay) {
		this.amountToPay = amountToPay;
	}

}
