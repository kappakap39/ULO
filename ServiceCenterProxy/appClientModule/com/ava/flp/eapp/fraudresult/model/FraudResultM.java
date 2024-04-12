package com.ava.flp.eapp.fraudresult.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class FraudResultM implements Serializable, Cloneable {

	@JsonProperty("applicationNo")
	private String applicationNo;
	
	@JsonProperty("fraudResult")
	private String fraudResult;
	
	@JsonProperty("systemFlag")
	private String systemFlag;
	
	@JsonProperty("asOfDate")
	private String asOfDate;
	
	@JsonProperty("product")
	private String product;
	
	@JsonProperty("resubmitFlag")
	private String resubmitFlag;
	
	@JsonProperty("additionalInfo")
	private String additionalInfo;

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getFraudResult() {
		return fraudResult;
	}

	public void setFraudResult(String fraudResult) {
		this.fraudResult = fraudResult;
	}
	
	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public String getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getResubmitFlag() {
		return resubmitFlag;
	}

	public void setResubmitFlag(String resubmitFlag) {
		this.resubmitFlag = resubmitFlag;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String toString() {
		return "FraudResultM [applicationNo=" + applicationNo
				+ ", fraudResult=" + fraudResult 
				+ ", systemFlag=" + systemFlag
				+ ", asOfDate=" + asOfDate
				+ ", product=" + product
				+ ", reSubmitFlag=" + resubmitFlag
				+ ", additionalInfo=" + additionalInfo + "]";
	}
	
}
