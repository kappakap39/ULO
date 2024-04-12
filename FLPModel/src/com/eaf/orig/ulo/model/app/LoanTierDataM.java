package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class LoanTierDataM implements Serializable,Cloneable{
	public LoanTierDataM(){
		super();
	}
	public void init(String loanId,String tierId){
		this.loanId = loanId;
		this.tierId = tierId;
	}
	private String loanId;	//ORIG_LOAN_TIER.LOAN_ID(VARCHAR2)
	private String tierId;	//ORIG_LOAN_TIER.TIER_ID(VARCHAR2)
	private int seq;	//ORIG_LOAN_TIER.SEQ(NUMBER)
	private String rateType;	//ORIG_LOAN_TIER.RATE_TYPE(VARCHAR2)
	private BigDecimal term;	//ORIG_LOAN_TIER.TERM(NUMBER)
	private String termType;	//ORIG_LOAN_TIER.TERM_TYPE(VARCHAR2)
	private BigDecimal monthlyInstallment;	//ORIG_LOAN_TIER.MONTHLY_INSTALLMENT(NUMBER)
	private BigDecimal lastInstallment;	//ORIG_LOAN_TIER.LAST_INSTALLMENT(NUMBER)
	private BigDecimal totalInstallment;	//ORIG_LOAN_TIER.TOTAL_INSTALLMENT(NUMBER)
	private BigDecimal rateAmount;	//ORIG_LOAN_TIER.RATE_AMOUNT(NUMBER)
	private BigDecimal intRateSpread;	//ORIG_LOAN_TIER.INT_RATE_SPREAD(NUMBER)
	private String intRateSign;	//ORIG_LOAN_TIER.INT_RATE_SIGN(VARCHAR2)
	private String intRateIndex;	//ORIG_LOAN_TIER.INT_RATE_INDEX(VARCHAR2)
	private BigDecimal effectiveRate;	//ORIG_LOAN_TIER.EFFECTIVE_RATE(NUMBER)
	private BigDecimal flatRate;	//ORIG_LOAN_TIER.FLAT_RATE(NUMBER)
	private String createBy;	//ORIG_LOAN_TIER.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_LOAN_TIER.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_LOAN_TIER.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_LOAN_TIER.UPDATE_DATY(DATE)
	private BigDecimal intRateAmount; //ORIG_LOAN_TIER.INT_RATE_AMOUNT(NUMBER)
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public BigDecimal getMonthlyInstallment() {
		return monthlyInstallment;
	}
	public void setMonthlyInstallment(BigDecimal monthlyInstallment) {
		this.monthlyInstallment = monthlyInstallment;
	}
	public BigDecimal getFlatRate() {
		return flatRate;
	}
	public void setFlatRate(BigDecimal flatRate) {
		this.flatRate = flatRate;
	}
	public BigDecimal getTotalInstallment() {
		return totalInstallment;
	}
	public void setTotalInstallment(BigDecimal totalInstallment) {
		this.totalInstallment = totalInstallment;
	}
	public String getIntRateIndex() {
		return intRateIndex;
	}
	public void setIntRateIndex(String intRateIndex) {
		this.intRateIndex = intRateIndex;
	}
	public BigDecimal getTerm() {
		return term;
	}
	public void setTerm(BigDecimal term) {
		this.term = term;
	}
	public BigDecimal getRateAmount() {
		return rateAmount;
	}
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}
	public BigDecimal getIntRateSpread() {
		return intRateSpread;
	}
	public void setIntRateSpread(BigDecimal intRateSpread) {
		this.intRateSpread = intRateSpread;
	}
	public String getIntRateSign() {
		return intRateSign;
	}
	public void setIntRateSign(String intRateSign) {
		this.intRateSign = intRateSign;
	}
	public String getTierId() {
		return tierId;
	}
	public void setTierId(String tierId) {
		this.tierId = tierId;
	}
	public BigDecimal getLastInstallment() {
		return lastInstallment;
	}
	public void setLastInstallment(BigDecimal lastInstallment) {
		this.lastInstallment = lastInstallment;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public BigDecimal getEffectiveRate() {
		return effectiveRate;
	}
	public void setEffectiveRate(BigDecimal effectiveRate) {
		this.effectiveRate = effectiveRate;
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
	public BigDecimal getIntRateAmount() {
		return intRateAmount;
	}
	public void setIntRateAmount(BigDecimal intRateAmount) {
		this.intRateAmount = intRateAmount;
	}	
	
}
