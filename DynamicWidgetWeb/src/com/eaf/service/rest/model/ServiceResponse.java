package com.eaf.service.rest.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceResponse implements Serializable,Cloneable{
	public ServiceResponse(){
		super();
	}
	public class Status{
		public static final String SUCCESS = "00";
		public static final String BUSINESS_EXCEPTION = "10";
		public static final String SYSTEM_EXCEPTION = "20";
	}
	public class StatusDesc{
		public static final String SUCCESS = "SUCCESS";
		public static final String ERROR = "ERROR";
	}
	public String statusCode;
	public String statusDesc;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}	
}
