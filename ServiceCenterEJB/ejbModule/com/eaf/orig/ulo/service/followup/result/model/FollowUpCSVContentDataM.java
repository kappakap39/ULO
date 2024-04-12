package com.eaf.orig.ulo.service.followup.result.model;

import java.io.Serializable;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

@CsvDataType()
public class FollowUpCSVContentDataM  implements Serializable,Cloneable{
	public FollowUpCSVContentDataM () {
		super();
	}
	@CsvField(pos = 1)
	String caseID;
	
	@CsvField(pos = 2)
	String followUpStatus;
	
	@CsvField(pos = 3)
	String branchCode;
	
	@CsvField(pos = 4)
	String recommenderEmpID;
	
	@CsvField(pos = 5)
	String custAvailableTime;
	
	@CsvField(pos = 6)
	String telType;
	
	@CsvField(pos = 7)
	String telNo;
	
	@CsvField(pos = 8)
	String telExt;

	public String getCaseID() {
		return caseID;
	}

	public String getFollowUpStatus() {
		return followUpStatus;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getRecommenderEmpID() {
		return recommenderEmpID;
	}

	public String getCustAvailableTime() {
		return custAvailableTime;
	}

	public String getTelType() {
		return telType;
	}

	public String getTelNo() {
		return telNo;
	}

	public String getTelExt() {
		return telExt;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public void setFollowUpStatus(String followUpStatus) {
		this.followUpStatus = followUpStatus;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setRecommenderEmpID(String recommenderEmpID) {
		this.recommenderEmpID = recommenderEmpID;
	}

	public void setCustAvailableTime(String custAvailableTime) {
		this.custAvailableTime = custAvailableTime;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public void setTelExt(String telExt) {
		this.telExt = telExt;
	}
}
