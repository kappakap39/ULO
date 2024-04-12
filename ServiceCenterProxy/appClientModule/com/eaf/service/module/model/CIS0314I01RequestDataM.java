package com.eaf.service.module.model;

import java.io.Serializable;

public class CIS0314I01RequestDataM implements Serializable,Cloneable {
	private String customerType;
	private String customerId;
	
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
