package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CIS1048O01RequestDataM implements Serializable,Cloneable{
	 private String userId;
	 private String hubNo;
	 private String branchNo;
	 private String customerType;
	 private String customerId;
	 private String contactAddressId;
	 private ArrayList<CIS1048O01AddressDataM> contactAddressTypes;
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
	public String getContactAddressId() {
		return contactAddressId;
	}
	public void setContactAddressId(String contactAddressId) {
		this.contactAddressId = contactAddressId;
	}
	public ArrayList<CIS1048O01AddressDataM> getContactAddressTypes() {
		return contactAddressTypes;
	}
	public void setContactAddressTypes(
			ArrayList<CIS1048O01AddressDataM> contactAddressTypes) {
		this.contactAddressTypes = contactAddressTypes;
	}	  	 
}
