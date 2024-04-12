package com.eaf.orig.ulo.model.calloper;

import java.io.Serializable;

public class ErrorDataM implements Serializable, Cloneable{
	private String ErrorAppId;
	private String ErrorAppAbbrv;
	private String ErrorCode;
	private String ErrorDesc;
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
