package com.eaf.service.module.model;

import java.io.Serializable;

public class CVRSDQ0001ContactDataM implements Serializable,Cloneable{
	
	public class CONTACT_TYPE{
		public static final String HOME = "1";
		public static final String OFFICE = "2";
	}
	
	private String location;
 	private String telephoneNumber;
 	private String ext;
 	private String email;
 	private String fax;
 	private String faxExt;
 	private String mobileNo;
 	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFaxExt() {
		return faxExt;
	}
	public void setFaxExt(String faxExt) {
		this.faxExt = faxExt;
	}
}
