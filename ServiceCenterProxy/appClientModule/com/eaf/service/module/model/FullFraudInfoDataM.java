package com.eaf.service.module.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eaf.orig.model.util.ModelUtil;

@SuppressWarnings("serial")
public class FullFraudInfoDataM implements Serializable, Cloneable{
	private String	applicationGroupNo;
	private String	product;
	private String 	result;
	private String	asOfDate;
	private String	applicationGroupId;
	private String	userId;
	private String	requestId;
	//private String	systemFlag;
	//private String	resubmitFlag;
	//private String	additionalInfo;
	
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
//	public String getSystemFlag() {
//		return systemFlag;
//	}
//	public void setSystemFlag(String systemFlag) {
//		this.systemFlag = systemFlag;
//	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
//	public String getResubmitFlag() {
//		return resubmitFlag;
//	}
//	public void setResubmitFlag(String resubmitFlag) {
//		this.resubmitFlag = resubmitFlag;
//	}
//	public String getAdditionalInfo() {
//		return additionalInfo;
//	}
//	public void setAdditionalInfo(String additionalInfo) {
//		this.additionalInfo = additionalInfo;
//	}
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		if (ModelUtil.empty(asOfDate))
			this.asOfDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		else
			this.asOfDate = asOfDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}