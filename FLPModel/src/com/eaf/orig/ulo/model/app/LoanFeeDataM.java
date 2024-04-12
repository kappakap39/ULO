package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class LoanFeeDataM implements Serializable,Cloneable {
	public LoanFeeDataM(){
		super();
	}
	public void init(String refId,String loanFeeId){
		this.refId = refId;
		this.loanFeeId = loanFeeId;
	}
	private String loanFeeId;	//ORIG_LOAN_FEE.ORIG_LOAN_FEE_ID(NUMBER)
	private String refId;	//ORIG_LOAN_FEE.REF_ID(VARCHAR2)
	private int seq;	//ORIG_LOAN_FEE.SEQ(NUMBER)
	private BigDecimal minFee;	//ORIG_LOAN_FEE.MIN_FEE(NUMBER)
	private String feeLevelType;	//ORIG_LOAN_FEE.FEE_LEVEL_TYPE(VARCHAR2)
	private String feeSrc;	//ORIG_LOAN_FEE.FEE_SRC(VARCHAR2)
	private BigDecimal fee;	//ORIG_LOAN_FEE.FEE(NUMBER)
	private String feeType;	//ORIG_LOAN_FEE.FEE_TYPE(VARCHAR2)
	private String feeCalcType;	//ORIG_LOAN_FEE.FEE_CALC_TYPE(VARCHAR2)
	private BigDecimal maxFee;	//ORIG_LOAN_FEE.MAX_FEE(NUMBER)
	private BigDecimal finalFeeAmount;	//ORIG_LOAN_FEE.FINAL_FEE_AMOUNT(NUMBER)
	private String createBy;	//ORIG_LOAN_FEE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_LOAN_FEE.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_LOAN_FEE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_LOAN_FEE.UPDATE_DATE(DATE)
	private Integer startMonth;
	private Integer endMonth;
	
	public String getLoanFeeId() {
		return loanFeeId;
	}
	public void setLoanFeeId(String loanFeeId) {
		this.loanFeeId = loanFeeId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public BigDecimal getMinFee() {
		return minFee;
	}
	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}
	public String getFeeLevelType() {
		return feeLevelType;
	}
	public void setFeeLevelType(String feeLevelType) {
		this.feeLevelType = feeLevelType;
	}
	public String getFeeSrc() {
		return feeSrc;
	}
	public void setFeeSrc(String feeSrc) {
		this.feeSrc = feeSrc;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getFeeCalcType() {
		return feeCalcType;
	}
	public void setFeeCalcType(String feeCalcType) {
		this.feeCalcType = feeCalcType;
	}
	public BigDecimal getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}
	public BigDecimal getFinalFeeAmount() {
		return finalFeeAmount;
	}
	public void setFinalFeeAmount(BigDecimal finalFeeAmount) {
		this.finalFeeAmount = finalFeeAmount;
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
	public Integer getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}
	public Integer getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}	
}
