package com.eaf.orig.ulo.model.dih;

import java.io.Serializable;

public class CISAddressDataM implements Serializable,Cloneable{
	public CISAddressDataM(){
		super();
	}
	private String addressType;
	private String companyName;
	private String vilapt;
	private String building;
	private String room;
	private String floor;
	private String addressNo;
	private String moo;
	private String soi;
	private String road;
	private String country;
	private String tambol;
	private String amphur;
	private String province;
	private String zipCode;
	private String adrId;
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getVilapt() {
		return vilapt;
	}
	public void setVilapt(String vilapt) {
		this.vilapt = vilapt;
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
	public String getAddressNo() {
		return addressNo;
	}
	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}
	public String getMoo() {
		return moo;
	}
	public void setMoo(String moo) {
		this.moo = moo;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTambol() {
		return tambol;
	}
	public void setTambol(String tambol) {
		this.tambol = tambol;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAdrId() {
		return adrId;
	}
	public void setAdrId(String adrId) {
		this.adrId = adrId;
	}	
}
