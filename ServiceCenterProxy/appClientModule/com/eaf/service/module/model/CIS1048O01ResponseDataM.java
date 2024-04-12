package com.eaf.service.module.model;

import java.io.Serializable;

public class CIS1048O01ResponseDataM implements Serializable,Cloneable{
	 private String customerId;
	 private String statusCode;

	 public String getCustomerId() {
		return customerId;
	 }

	 public void setCustomerId(String customerId) {
		this.customerId = customerId;
	 }

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	} 	 
}