package com.eaf.inf.batch.ulo.eapp.fraudresult.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EAppFraudResultM implements Cloneable, Serializable {

	private String applicationNo;
	private String fraudResult;
	private String systemFlag;
	private String asOfDate;
	private String product;
	private String resubmitFlag;
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
	
}
