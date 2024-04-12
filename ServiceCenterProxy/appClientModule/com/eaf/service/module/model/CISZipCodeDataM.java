package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;

public class CISZipCodeDataM implements Serializable,Cloneable{
	private String amphur;
	private Date lastMaintenanceDate;
	private String province;
	private String refNumber;
	private String tumbol;
	private String userId;
	private String zipCode;
	
	public String getAmphur() {
		return amphur;
	}
	public void setAmphur(String amphur) {
		this.amphur = amphur;
	}
	public Date getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}
	public void setLastMaintenanceDate(Date lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getTumbol() {
		return tumbol;
	}
	public void setTumbol(String tumbol) {
		this.tumbol = tumbol;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
