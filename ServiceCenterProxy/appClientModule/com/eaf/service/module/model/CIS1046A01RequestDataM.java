package com.eaf.service.module.model;

import java.util.ArrayList;

public class CIS1046A01RequestDataM {
	private String userId;
	private String hubNumber;
	private String branchNo;
	private String confirmFlag;
	private String validateFlag;
	private String customerType;
	private String customerId;
	private String addressId;
	private ArrayList<CISAddrRelationDataM> cisAddrRelations;
	private String accountId;
	private String accountReferenceId;
	private String accountLevel;
	private String prospectFlag;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHubNumber() {
		return hubNumber;
	}
	public void setHubNumber(String hubNumber) {
		this.hubNumber = hubNumber;
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
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public ArrayList<CISAddrRelationDataM> getCisAddrRelations() {
		return cisAddrRelations;
	}
	public void setCisAddrRelations(ArrayList<CISAddrRelationDataM> cisAddrRelations) {
		this.cisAddrRelations = cisAddrRelations;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountReferenceId() {
		return accountReferenceId;
	}
	public void setAccountReferenceId(String accountReferenceId) {
		this.accountReferenceId = accountReferenceId;
	}
	public String getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}
	public String getProspectFlag() {
		return prospectFlag;
	}
	public void setProspectFlag(String prospectFlag) {
		this.prospectFlag = prospectFlag;
	}	
}
