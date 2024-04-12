package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;

public class CISDocInfoDataM implements Serializable,Cloneable{
	private String documentNo;
	private String documentType;
	private String placeIssue;
	private Date issueDate;
	private Date expiryDate;
	private String successFlag;
	private String errorDescription;
	
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getPlaceIssue() {
		return placeIssue;
	}
	public void setPlaceIssue(String placeIssue) {
		this.placeIssue = placeIssue;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
