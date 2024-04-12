package com.eaf.service.module.model;

import java.io.Serializable;

public class CIS1044U01ResponseDataM implements Serializable,Cloneable{
 private String customerId;
 private String status;

	 public String getCustomerId() {
		 return customerId;
	 }
	
	 public void setCustomerId(String customerId) {
		this.customerId = customerId;
	 }
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
