package com.ava.flp.cjd.model;

import java.io.Serializable;

public class CJDReponseVerificationResultDataM implements Serializable,Cloneable {
	public CJDReponseVerificationResultDataM(){
		super();
	}
	private String completedFlag;
	
	public String getCompletedFlag() {
		return completedFlag;
	}
	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}
}
