package com.eaf.core.ulo.common.model;

import java.io.Serializable;

public class GenerateResultDataM implements Serializable,Cloneable{
	public GenerateResultDataM(){
		super();
	}
	private String resultCode;
	private String resultDesc;
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
}
