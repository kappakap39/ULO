package com.eaf.service.module.model;

import java.io.Serializable;

public class CIS0222I01RequestDataM implements Serializable,Cloneable{
	private String tumbol;
	private String amphur;
	private String province;
	
	public String getTumbol() {
		return tumbol;
	}
	public void setTumbol(String tumbol) {
		this.tumbol = tumbol;
	}
	public String getAmphur() {
		return amphur;
	}
	public void setAmphur(String amphur) {
		this.amphur = amphur;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
}
