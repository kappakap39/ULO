package com.eaf.inf.batch.ulo.fraudresult.model;

import java.io.Serializable;

public class FraudResultDataM  implements Serializable, Cloneable {
	private String	applicationGroupNo;
	private String	product;
	private String	systemFlag;
	private String 	result;
	private String	resubmitFlag;
	private String	additionalInfo;
	private String	asOfDate;
	
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSystemFlag() {
		return systemFlag;
	}
	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	
}
