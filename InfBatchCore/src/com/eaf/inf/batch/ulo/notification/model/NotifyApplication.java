package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("serial")
public class NotifyApplication implements Serializable, Cloneable {
	public NotifyApplication(){
		
	}
	private	String applpicationGroupId;
	private	String applicationGroupNo;
	private String personalId;
	private	String jobState;
	private	String customerName;
	private	String mobileNo;
	private	String saleId;
	private	String recommendId;
	private	String finalAppDecision;
	private	String applicationRecordId;
	private	String cardType;
	private	String cardLevel;
	private	String personalType;
	private	Integer seq;
	private	String productName;
	private int lifeCycle;
	private	List<ApplicationResult> applicationResults = new ArrayList<ApplicationResult>();
	private	String verifiedResult;
	public String getApplpicationGroupId() {
		return applpicationGroupId;
	}
	public void setApplpicationGroupId(String applpicationGroupId) {
		this.applpicationGroupId = applpicationGroupId;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public String getFinalAppDecision() {
		return finalAppDecision;
	}
	public void setFinalAppDecision(String finalAppDecision) {
		this.finalAppDecision = finalAppDecision;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardLevel() {
		return cardLevel;
	}
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<ApplicationResult> getApplicationResults() {
		return applicationResults;
	}
	public void setApplicationResults(List<ApplicationResult> applicationResults) {
		this.applicationResults = applicationResults;
	}
	public String getVerifiedResult() {
		return verifiedResult;
	}
	public void setVerifiedResult(String verifiedResult) {
		this.verifiedResult = verifiedResult;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	
}
