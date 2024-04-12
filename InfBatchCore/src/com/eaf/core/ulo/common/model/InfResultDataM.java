package com.eaf.core.ulo.common.model;

import java.io.Serializable;

public class InfResultDataM implements Serializable, Cloneable{
	public InfResultDataM(){
		super();
	}
	private String resultCode;
	private String resultDesc;
	private String errorMsg;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}	
}
