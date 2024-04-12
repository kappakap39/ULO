package com.eaf.service.common.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceTransaction implements Serializable,Cloneable{
	public ServiceTransaction(){
		super();
	}
	private String statusCode;
	private Object serviceTransactionObject;
	private ServiceErrorInfo errorInfo;
		
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Object getServiceTransactionObject() {
		return serviceTransactionObject;
	}
	public void setServiceTransactionObject(Object serviceTransactionObject) {
		this.serviceTransactionObject = serviceTransactionObject;
	}
	public ServiceErrorInfo getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(ServiceErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}	
}
