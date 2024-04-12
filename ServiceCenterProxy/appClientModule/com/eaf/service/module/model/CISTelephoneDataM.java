package com.eaf.service.module.model;

import java.io.Serializable;

public class CISTelephoneDataM implements Serializable, Cloneable{
	private String phoneLocation;
	private String phoneNo;
	private String phoneExt;
	private String phoneType;
	private String fax;
	
	public String getPhoneLocation() {
		return phoneLocation;
	}
	public void setPhoneLocation(String phoneLocation) {
		this.phoneLocation = phoneLocation;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPhoneExt() {
		return phoneExt;
	}
	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}		
}
