package com.eaf.orig.ulo.model.followup;

import java.io.Serializable;

public class CSVContentDataM implements Serializable, Cloneable{
	private String CaseID;
	private String FollowUpStatus;
	private String BranchCode;
	private String RecommenderEmpID;
	private String CustAvailableTime;
	private String TelType;
	private String TelNo;
	private String TelExt;
	
	public String getCaseID() {
		return CaseID;
	}
	public void setCaseID(String caseID) {
		CaseID = caseID;
	}
	public String getFollowUpStatus() {
		return FollowUpStatus;
	}
	public void setFollowUpStatus(String followUpStatus) {
		FollowUpStatus = followUpStatus;
	}
	public String getBranchCode() {
		return BranchCode;
	}
	public void setBranchCode(String branchCode) {
		BranchCode = branchCode;
	}
	public String getRecommenderEmpID() {
		return RecommenderEmpID;
	}
	public void setRecommenderEmpID(String recommenderEmpID) {
		RecommenderEmpID = recommenderEmpID;
	}
	public String getCustAvailableTime() {
		return CustAvailableTime;
	}
	public void setCustAvailableTime(String custAvailableTime) {
		CustAvailableTime = custAvailableTime;
	}
	public String getTelType() {
		return TelType;
	}
	public void setTelType(String telType) {
		TelType = telType;
	}
	public String getTelNo() {
		return TelNo;
	}
	public void setTelNo(String telNo) {
		TelNo = telNo;
	}
	public String getTelExt() {
		return TelExt;
	}
	public void setTelExt(String telExt) {
		TelExt = telExt;
	}
	
}
