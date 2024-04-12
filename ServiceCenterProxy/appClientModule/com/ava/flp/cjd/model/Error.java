package com.ava.flp.cjd.model;

import java.io.Serializable;

public class Error implements Serializable,Cloneable{
	public Error(){
		super();
	}
	private String errorAppId;
	private String errorAppAbbrv;
	private String errorCode;
	private String errorDesc;
	private String errorSeverity;
	
	public String getErrorAppId() {
		return errorAppId;
	}
	public void setErrorAppId(String errorAppId) {
		this.errorAppId = errorAppId;
	}
	public String getErrorAppAbbrv() {
		return errorAppAbbrv;
	}
	public void setErrorAppAbbrv(String errorAppAbbrv) {
		this.errorAppAbbrv = errorAppAbbrv;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getErrorSeverity() {
		return errorSeverity;
	}
	public void setErrorSeverity(String errorSeverity) {
		this.errorSeverity = errorSeverity;
	}
}
