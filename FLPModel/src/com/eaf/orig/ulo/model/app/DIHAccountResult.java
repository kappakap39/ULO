package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;

import com.eaf.core.ulo.common.model.ErrorData;

@SuppressWarnings("serial")
public class DIHAccountResult implements Serializable,Cloneable{
	public DIHAccountResult(){
		super();
	}
	private boolean foundAccount = false;
	private String accountNo;
	private String accountName; 
	private Date accountDate; 
	private String accountStatus;
	private String sourceAccount;
	private String statusCode;
	private ErrorData errorData;
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public ErrorData getErrorData() {
		return errorData;
	}
	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}
	public String getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public boolean isFoundAccount() {
		return foundAccount;
	}
	public void setFoundAccount(boolean foundAccount) {
		this.foundAccount = foundAccount;
	}
}
