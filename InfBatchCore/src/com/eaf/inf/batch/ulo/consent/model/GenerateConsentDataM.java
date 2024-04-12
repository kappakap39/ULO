package com.eaf.inf.batch.ulo.consent.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GenerateConsentDataM implements Serializable,Cloneable{
	public GenerateConsentDataM(){
		super();
	}
	private String ncbDocHistoryId;
	private String recType;
	private String setID;
	private String consentRefNo;
	private String branchNo;
	private String applicantType;
	private String applicationGroupId;
	
	public String getNcbDocHistoryId() {
		return ncbDocHistoryId;
	}
	public void setNcbDocHistoryId(String ncbDocHistoryId) {
		this.ncbDocHistoryId = ncbDocHistoryId;
	}
	public String getRecType() {
		return recType;
	}
	public void setRecType(String recType) {
		this.recType = recType;
	}
	public String getSetID() {
		return setID;
	}
	public void setSetID(String setID) {
		this.setID = setID;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}	
	
}
