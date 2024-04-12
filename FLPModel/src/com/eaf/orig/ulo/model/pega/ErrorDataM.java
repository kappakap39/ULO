package com.eaf.orig.ulo.model.pega;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ErrorDataM implements Serializable, Cloneable{
	
	@JsonProperty("ErrorAppId")
	private String ErrorAppId;
	
	@JsonProperty("ErrorAppAbbrv")
	private String ErrorAppAbbrv;
	
	@JsonProperty("ErrorCode")
	private String ErrorCode;
	
	@JsonProperty("ErrorDesc")
	private String ErrorDesc;
	
	@JsonProperty("ErrorSeverity")
	private String ErrorSeverity;
	
	public String getErrorAppId() {
		return ErrorAppId;
	}
	public void setErrorAppId(String errorAppId) {
		ErrorAppId = errorAppId;
	}
	public String getErrorAppAbbrv() {
		return ErrorAppAbbrv;
	}
	public void setErrorAppAbbrv(String errorAppAbbrv) {
		ErrorAppAbbrv = errorAppAbbrv;
	}
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getErrorDesc() {
		return ErrorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		ErrorDesc = errorDesc;
	}
	public String getErrorSeverity() {
		return ErrorSeverity;
	}
	public void setErrorSeverity(String errorSeverity) {
		ErrorSeverity = errorSeverity;
	}
	
}
