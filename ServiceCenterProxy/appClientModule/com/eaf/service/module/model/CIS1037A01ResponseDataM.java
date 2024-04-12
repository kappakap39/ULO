package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CIS1037A01ResponseDataM implements Serializable,Cloneable{
	private String addressId;
	private String phoneId;
	private String mailId;
	private ArrayList<RegulationDataM> regulations;
	private Integer totalRegulation;
	private String riskCode;
	private String customerId;
	private String officialAddressId;
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public ArrayList<RegulationDataM> getRegulations() {
		return regulations;
	}
	public void setRegulations(ArrayList<RegulationDataM> regulations) {
		this.regulations = regulations;
	}
	public Integer getTotalRegulation() {
		return totalRegulation;
	}
	public void setTotalRegulation(Integer totalRegulation) {
		this.totalRegulation = totalRegulation;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOfficialAddressId() {
		return officialAddressId;
	}
	public void setOfficialAddressId(String officialAddressId) {
		this.officialAddressId = officialAddressId;
	}

}
