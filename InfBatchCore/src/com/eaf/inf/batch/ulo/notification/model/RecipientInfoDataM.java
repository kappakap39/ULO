package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

public class RecipientInfoDataM implements Serializable,Cloneable{
	public RecipientInfoDataM(){
		super();
	}
	private String email;
	private String phoneNo;
	private String personalType;
	private String policyProgramId;
	private String finalDecision;
	private String businessClassId;
	private String nationality;
	private String applicationRecordId;
	private String applicationGroupId;
	private String personalId;
	private String applicationStatus;
	private int maxLifeCycle;
	private String templateId;
	private String cashTranferType;
	private String cardCode;
	private String sourse;
	public String getSourse() {
		return sourse;
	}
	public void setSourse(String sourse) {
		this.sourse = sourse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getPolicyProgramId() {
		return policyProgramId;
	}
	public void setPolicyProgramId(String policyProgramId) {
		this.policyProgramId = policyProgramId;
	}
	public String getFinalDecision() {
		return finalDecision;
	}
	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getProduct(){
		return (null != businessClassId)?businessClassId.split("\\_")[0]:null;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public int getMaxLifeCycle() {
		return maxLifeCycle;
	}
	public void setMaxLifeCycle(int maxLifeCycle) {
		this.maxLifeCycle = maxLifeCycle;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getCashTranferType() {
		return cashTranferType;
	}
	public void setCashTranferType(String cashTranferType) {
		this.cashTranferType = cashTranferType;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecipientInfoDataM [email=");
		builder.append(email);
		builder.append(", phoneNo=");
		builder.append(phoneNo);
		builder.append(", personalType=");
		builder.append(personalType);
		builder.append(", policyProgramId=");
		builder.append(policyProgramId);
		builder.append(", finalDecision=");
		builder.append(finalDecision);
		builder.append(", businessClassId=");
		builder.append(businessClassId);
		builder.append(", nationality=");
		builder.append(nationality);
		builder.append(", applicationRecordId=");
		builder.append(applicationRecordId);
		builder.append(", applicationGroupId=");
		builder.append(applicationGroupId);
		builder.append(", personalId=");
		builder.append(personalId);
		builder.append(", applicationStatus=");
		builder.append(applicationStatus);
		builder.append(", maxLifeCycle=");
		builder.append(maxLifeCycle);
		builder.append(", templateId=");
		builder.append(templateId);
		builder.append(", cashTranferType=");
		builder.append(cashTranferType);
		builder.append(", cardCode=");
		builder.append(cardCode);
		builder.append("]");
		return builder.toString();
	}	
	
}
