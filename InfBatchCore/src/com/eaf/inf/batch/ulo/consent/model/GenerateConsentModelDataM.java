package com.eaf.inf.batch.ulo.consent.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GenerateConsentModelDataM implements Serializable,Cloneable{
	public GenerateConsentModelDataM(){
		super();
	}
	private String recType;
	private String setId;
	private String businessDate;
	private String consentRefNo;
	private String consentModelFlag;
	private String applicationGroupId;
	private String applicantType;
	
	public String getRecType() {
		return recType;
	}
	public void setRecType(String recType) {
		this.recType = recType;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public String getConsentModelFlag() {
		return consentModelFlag;
	}
	public void setConsentModelFlag(String consentModelFlag) {
		this.consentModelFlag = consentModelFlag;
	}
	public String getSetId() {
		return setId;
	}
	public void setSetId(String setId) {
		this.setId = setId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	
}
