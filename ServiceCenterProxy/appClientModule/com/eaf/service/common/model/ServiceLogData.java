package com.eaf.service.common.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceLogData implements Serializable,Cloneable{
	public ServiceLogData(){
		
	}
	private String responseCode;
	private String responseDesc;
	private String errorMsg;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDesc() {
		return responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
