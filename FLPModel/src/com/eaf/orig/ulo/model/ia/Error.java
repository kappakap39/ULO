package com.eaf.orig.ulo.model.ia;

import java.io.Serializable;

public class Error implements Serializable,Cloneable{
	public Error(){
		super();
	}
	private String errorCode;
	private String errorDesc;
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
}
