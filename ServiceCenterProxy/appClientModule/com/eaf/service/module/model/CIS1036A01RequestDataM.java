package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CIS1036A01RequestDataM implements Serializable,Cloneable{
	private String userId;
	private String hubNo;
	private String branchNo;
	private String confirmFlag;
	private String validateFlag;
	private String customerType;
	private String customerId;
	private String customerTypeCode;
	private String addressId;
	private String addressName;
	private String mailBoxNo;
	private String mailNo;
	private String moo;
	private String mooban;
	private String building;
	private String room;
	private String floor;
	private String soi;
	private String road;
	private String tumbol;
	private String amphur;
	private String province;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHubNo() {
		return hubNo;
	}
	public void setHubNo(String hubNo) {
		this.hubNo = hubNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getConfirmFlag() {
		return confirmFlag;
	}
	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerTypeCode() {
		return customerTypeCode;
	}
	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getMailBoxNo() {
		return mailBoxNo;
	}
	public void setMailBoxNo(String mailBoxNo) {
		this.mailBoxNo = mailBoxNo;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getMoo() {
		return moo;
	}
	public void setMoo(String moo) {
		this.moo = moo;
	}
	public String getMooban() {
		return mooban;
	}
	public void setMooban(String mooban) {
		this.mooban = mooban;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getSoi() {
		return soi;
	}
	public void setSoi(String soi) {
		this.soi = soi;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
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
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	private String postCode;
	private String country;
	private String line1;
	private String line2;
}
