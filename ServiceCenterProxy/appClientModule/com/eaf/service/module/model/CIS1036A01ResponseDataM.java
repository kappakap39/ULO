package com.eaf.service.module.model;

import java.io.Serializable;

public class CIS1036A01ResponseDataM implements Serializable,Cloneable{
	private String customerId;
	private String addressId;
	
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
	
}
