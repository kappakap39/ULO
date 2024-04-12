package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;

public class WFInquiryAppRequestDataM implements Serializable,Cloneable{
	private String cisNo;
	private String docNo;
	private String docType;
	private String thFirstName;
	private String thLastName;
	private Date dob;
	private String appStatus;
	private String bookedFlag;
	private String productCode;
	private Date appFromDate;
	private Date appToDate;
	
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getBookedFlag() {
		return bookedFlag;
	}
	public void setBookedFlag(String bookedFlag) {
		this.bookedFlag = bookedFlag;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Date getAppFromDate() {
		return appFromDate;
	}
	public void setAppFromDate(Date appFromDate) {
		this.appFromDate = appFromDate;
	}
	public Date getAppToDate() {
		return appToDate;
	}
	public void setAppToDate(Date appToDate) {
		this.appToDate = appToDate;
	}
	
}
