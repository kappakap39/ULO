package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class CBSRetentionDataM implements Serializable,Cloneable{
	private String cancelAuthUserId;
	private String cancelBranchDesc;
	private String cancelBranchId;
	private String cancelRententionReason;
	private String cancelUserId;
	private String concept;
	private String createAuthUserId;
	private String currencyCode;
	private String entryOrigin;
	private int indexSequence;
	private BigDecimal retentionAmount;
	private Date retentionCancelDate;
	private Timestamp retentionCancelTime;
	private Timestamp retentionCreateTime;
	private Date retentionCreateDate;
	private String retentionIndicator;
	private String retentionIndicatorDesc;
	private Date retentionMaturityDate;
	private String retentionNumber;
	private String retentionSituationCode;
	private String retentionType;
	private String retentionTypeDesc;
	private String subAccountNum;
	private Date transactionDate;
	private String transactionBranchId;
	private String transactionBranchName;
	private String userId;
	
	public String getCancelAuthUserId() {
		return cancelAuthUserId;
	}
	public void setCancelAuthUserId(String cancelAuthUserId) {
		this.cancelAuthUserId = cancelAuthUserId;
	}
	public String getCancelBranchDesc() {
		return cancelBranchDesc;
	}
	public void setCancelBranchDesc(String cancelBranchDesc) {
		this.cancelBranchDesc = cancelBranchDesc;
	}
	public String getCancelBranchId() {
		return cancelBranchId;
	}
	public void setCancelBranchId(String cancelBranchId) {
		this.cancelBranchId = cancelBranchId;
	}
	public String getCancelRententionReason() {
		return cancelRententionReason;
	}
	public void setCancelRententionReason(String cancelRententionReason) {
		this.cancelRententionReason = cancelRententionReason;
	}
	public String getCancelUserId() {
		return cancelUserId;
	}
	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getCreateAuthUserId() {
		return createAuthUserId;
	}
	public void setCreateAuthUserId(String createAuthUserId) {
		this.createAuthUserId = createAuthUserId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getEntryOrigin() {
		return entryOrigin;
	}
	public void setEntryOrigin(String entryOrigin) {
		this.entryOrigin = entryOrigin;
	}
	public int getIndexSequence() {
		return indexSequence;
	}
	public void setIndexSequence(int indexSequence) {
		this.indexSequence = indexSequence;
	}
	public BigDecimal getRetentionAmount() {
		return retentionAmount;
	}
	public void setRetentionAmount(BigDecimal retentionAmount) {
		this.retentionAmount = retentionAmount;
	}
	public Date getRetentionCancelDate() {
		return retentionCancelDate;
	}
	public void setRetentionCancelDate(Date retentionCancelDate) {
		this.retentionCancelDate = retentionCancelDate;
	}
	public Timestamp getRetentionCancelTime() {
		return retentionCancelTime;
	}
	public void setRetentionCancelTime(Timestamp retentionCancelTime) {
		this.retentionCancelTime = retentionCancelTime;
	}
	public Timestamp getRetentionCreateTime() {
		return retentionCreateTime;
	}
	public void setRetentionCreateTime(Timestamp retentionCreateTime) {
		this.retentionCreateTime = retentionCreateTime;
	}
	public Date getRetentionCreateDate() {
		return retentionCreateDate;
	}
	public void setRetentionCreateDate(Date retentionCreateDate) {
		this.retentionCreateDate = retentionCreateDate;
	}
	public String getRetentionIndicator() {
		return retentionIndicator;
	}
	public void setRetentionIndicator(String retentionIndicator) {
		this.retentionIndicator = retentionIndicator;
	}
	public String getRetentionIndicatorDesc() {
		return retentionIndicatorDesc;
	}
	public void setRetentionIndicatorDesc(String retentionIndicatorDesc) {
		this.retentionIndicatorDesc = retentionIndicatorDesc;
	}
	public Date getRetentionMaturityDate() {
		return retentionMaturityDate;
	}
	public void setRetentionMaturityDate(Date retentionMaturityDate) {
		this.retentionMaturityDate = retentionMaturityDate;
	}
	public String getRetentionNumber() {
		return retentionNumber;
	}
	public void setRetentionNumber(String retentionNumber) {
		this.retentionNumber = retentionNumber;
	}
	public String getRetentionSituationCode() {
		return retentionSituationCode;
	}
	public void setRetentionSituationCode(String retentionSituationCode) {
		this.retentionSituationCode = retentionSituationCode;
	}
	public String getRetentionType() {
		return retentionType;
	}
	public void setRetentionType(String retentionType) {
		this.retentionType = retentionType;
	}
	public String getRetentionTypeDesc() {
		return retentionTypeDesc;
	}
	public void setRetentionTypeDesc(String retentionTypeDesc) {
		this.retentionTypeDesc = retentionTypeDesc;
	}
	public String getSubAccountNum() {
		return subAccountNum;
	}
	public void setSubAccountNum(String subAccountNum) {
		this.subAccountNum = subAccountNum;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionBranchId() {
		return transactionBranchId;
	}
	public void setTransactionBranchId(String transactionBranchId) {
		this.transactionBranchId = transactionBranchId;
	}
	public String getTransactionBranchName() {
		return transactionBranchName;
	}
	public void setTransactionBranchName(String transactionBranchName) {
		this.transactionBranchName = transactionBranchName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
