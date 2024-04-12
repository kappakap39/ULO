package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class NotifyFLPApplicationDataM implements Serializable, Cloneable {

	String applicationRecordId;
	String orgId;
	String subProduct;
	String recommendDecision;
	String approveDate;
	String notifyMessage;
	NotifyFLPLoanSetupDataM loanSetup;
	
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSubProduct() {
		return subProduct;
	}
	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}
	public String getRecommendDecision() {
		return recommendDecision;
	}
	public void setRecommendDecision(String recommendDecision) {
		this.recommendDecision = recommendDecision;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	public String getNotifyMessage() {
		return notifyMessage;
	}
	public void setNotifyMessage(String notifyMessage) {
		this.notifyMessage = notifyMessage;
	}
	public NotifyFLPLoanSetupDataM getLoanSetup() {
		return loanSetup;
	}
	public void setLoanSetup(NotifyFLPLoanSetupDataM loanSetup) {
		this.loanSetup = loanSetup;
	}
}
