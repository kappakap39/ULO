package com.eaf.service.module.model;

import java.util.ArrayList;

public class CIS1046A01ResponseDataM {
	private String customerId;
	private String addressId;
	private ArrayList<CISAddrRelationDataM> CISAddrRelations;
	private String accountReferenceId;
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
	public ArrayList<CISAddrRelationDataM> getCISAddrRelations() {
		return CISAddrRelations;
	}
	public void setCISAddrRelations(ArrayList<CISAddrRelationDataM> cISAddrRelations) {
		CISAddrRelations = cISAddrRelations;
	}
	public String getAccountReferenceId() {
		return accountReferenceId;
	}
	public void setAccountReferenceId(String accountReferenceId) {
		this.accountReferenceId = accountReferenceId;
	}
}
