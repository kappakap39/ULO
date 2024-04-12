package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CIS1035A01RequestDataM implements Serializable,Cloneable{
	private String userId;
	private String hubNo;
	private String branchNo;
	private String confirmFlag;
	private String validateFlag;
	private int totalRecord;
	private ArrayList<CISContactDataM> cisContacts;
	private String customerType;
	private String customerId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHubNo() {
		return hubNo;
	}
	public void setHubNo(String hubNo) {
		this.hubNo = hubNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getConfirmFlag() {
		return confirmFlag;
	}
	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public ArrayList<CISContactDataM> getCisContact() {
		return cisContacts;
	}
	public void setCisContact(ArrayList<CISContactDataM> cisContacts) {
		this.cisContacts = cisContacts;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
