package com.eaf.orig.ulo.app.view.util.dih.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KbankSaleInfoDataM implements Serializable,Cloneable{
	private String saleId;
	private String saleName;
	private String rcCode;
	private String region;
	private String branchCode;
	private String thDeptName;
	private String enDeptName;
	private String officePhone;
	private String mobileNo;
	private String zone;
	private String thBnsDeptName;
	
	private boolean foundResult = false;
	
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getRcCode() {
		return rcCode;
	}
	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}	
	public String getThDeptName() {
		return thDeptName;
	}
	public void setThDeptName(String thDeptName) {
		this.thDeptName = thDeptName;
	}
	public String getEnDeptName() {
		return enDeptName;
	}
	public void setEnDeptName(String enDeptName) {
		this.enDeptName = enDeptName;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public boolean isFoundResult() {
		return foundResult;
	}
	public void setFoundResult(boolean foundResult) {
		this.foundResult = foundResult;
	}
	public String getThBnsDeptName() {
		return thBnsDeptName;
	}
	public void setThBnsDeptName(String thBnsDeptName) {
		this.thBnsDeptName = thBnsDeptName;
	}	
}
