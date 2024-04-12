package com.eaf.orig.ulo.model.app;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FraudRemarkDataM implements Serializable,Cloneable{
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
}
