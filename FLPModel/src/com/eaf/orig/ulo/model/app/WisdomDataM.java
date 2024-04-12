package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class WisdomDataM implements Serializable,Cloneable {
	public WisdomDataM(){
		super();
	}
	
	public void init(String referId){
		this.cardId = referId;
	}
	private String cardId;	//ORIG_WISDOM_INFO.CARD_ID(VARCHAR2)
	private String priorityPassFlag;	//ORIG_WISDOM_INFO.PRIORITY_PASS_FLAG(VARCHAR2)
	private String transferFrom;	//ORIG_WISDOM_INFO.TRANSFER_FROM(VARCHAR2)
	private BigDecimal premiumAmt;	//ORIG_WISDOM_INFO.PREMIUM_AMT(NUMBER)
	private String insuranceType;	//ORIG_WISDOM_INFO.INSURANCE_TYPE(VARCHAR2)
	private String quotaOf;	//ORIG_WISDOM_INFO.QUOTA_OF(VARCHAR2)
	private String referralWisdomNo;	//ORIG_WISDOM_INFO.REFERRAL_WISDOM_NO(VARCHAR2)
	private String priorityPassNo;	//ORIG_WISDOM_INFO.PRIORITY_PASS_NO(VARCHAR2)
	private String createBy;	//ORIG_WISDOM_INFO.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_WISDOM_INFO.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_WISDOM_INFO.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_WISDOM_INFO.UPDATE_DATE(DATE)
	
	private	String	ReqPriorityPassSup;//ORIG_WISDOM_INFO.REQ_PRIORITY_PASS_SUP(VARCHAR2)
	private	String PriorityPassMemo;	//ORIG_WISDOM_INFO.PRIORITY_PASS_MEMO(VARCHAR2)
	private	String PriorityPassMain;	//ORIG_WISDOM_INFO.PRIORITY_PASS_MAIN(VARCHAR2)
	private	BigDecimal NoPriorityPassSup;	//ORIG_WISDOM_INFO.NO_PRIORITY_PASS_SUP(NUMBER)
	private BigDecimal noPriorityPass; //ORIG_WISDOM_INFO.NO_PRIORITY_PASS(NUMBER)
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPriorityPassFlag() {
		return priorityPassFlag;
	}
	public void setPriorityPassFlag(String priorityPassFlag) {
		this.priorityPassFlag = priorityPassFlag;
	}
	public String getTransferFrom() {
		return transferFrom;
	}
	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}
	public BigDecimal getPremiumAmt() {
		return premiumAmt;
	}
	public void setPremiumAmt(BigDecimal premiumAmt) {
		this.premiumAmt = premiumAmt;
	}
	public String getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}	
	public String getQuotaOf() {
		return quotaOf;
	}
	public void setQuotaOf(String quotaOf) {
		this.quotaOf = quotaOf;
	}
	public String getReferralWisdomNo() {
		return referralWisdomNo;
	}
	public void setReferralWisdomNo(String referralWisdomNo) {
		this.referralWisdomNo = referralWisdomNo;
	}
	public String getPriorityPassNo() {
		return priorityPassNo;
	}
	public void setPriorityPassNo(String priorityPassNo) {
		this.priorityPassNo = priorityPassNo;
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
	public String getReqPriorityPassSup() {
		return ReqPriorityPassSup;
	}
	public void setReqPriorityPassSup(String reqPriorityPassSup) {
		ReqPriorityPassSup = reqPriorityPassSup;
	}
	public String getPriorityPassMemo() {
		return PriorityPassMemo;
	}
	public void setPriorityPassMemo(String priorityPassMemo) {
		PriorityPassMemo = priorityPassMemo;
	}
	public String getPriorityPassMain() {
		return PriorityPassMain;
	}
	public void setPriorityPassMain(String priorityPassMain) {
		PriorityPassMain = priorityPassMain;
	}
	public BigDecimal getNoPriorityPassSup() {
		return NoPriorityPassSup;
	}
	public void setNoPriorityPassSup(BigDecimal noPriorityPassSup) {
		NoPriorityPassSup = noPriorityPassSup;
	}
	public BigDecimal getNoPriorityPass() {
		return noPriorityPass;
	}
	public void setNoPriorityPass(BigDecimal noPriorityPass) {
		this.noPriorityPass = noPriorityPass;
	}
}
