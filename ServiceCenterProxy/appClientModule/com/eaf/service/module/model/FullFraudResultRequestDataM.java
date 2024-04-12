package com.eaf.service.module.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class FullFraudResultRequestDataM implements Serializable, Cloneable{
	
	@JsonProperty("FuncNm")
	private String FuncNm;
	
	@JsonProperty("RqUID")
	private String RqUID;
	
	@JsonProperty("RqDt")
	private String RqDt;
	
	@JsonProperty("RqAppId")
	private String RqAppId;
	
	@JsonProperty("UserId")
	private String UserId;
	
	@JsonProperty("TerminalId")
	private String TerminalId;
	
	@JsonProperty("UserLangPref")
	private String UserLangPref;
	
	@JsonProperty("CorrID")
	private String CorrID;
	
	@JsonProperty("applicationGroupNo")
	private String applicationGroupNo;
	
	@JsonProperty("product")
	private String product;

	@JsonProperty("asOfDate")
	private String asOfDate;

	@JsonProperty("result")
	private String result;
	
	public String getFuncNm() {
		return FuncNm;
	}
	public void setFuncNm(String funcNm) {
		this.FuncNm = funcNm;
	}
	public String getRqUID() {
		return RqUID;
	}
	public void setRqUID(String rqUID) {
		this.RqUID = rqUID;
	}
	public String getRqDt() {
		return RqDt;
	}
	public void setRqDt(String rqDt) {
		this.RqDt = rqDt;
	}
	public String getRqAppId() {
		return RqAppId;
	}
	public void setRqAppId(String rqAppId) {
		this.RqAppId = rqAppId;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		this.UserId = userId;
	}
	public String getTerminalId() {
		return TerminalId;
	}
	public void setTerminalId(String terminalId) {
		this.TerminalId = terminalId;
	}
	public String getUserLangPref() {
		return UserLangPref;
	}
	public void setUserLangPref(String userLangPref) {
		this.UserLangPref = userLangPref;
	}
	public String getCorrID() {
		return CorrID;
	}
	public void setCorrID(String corrID) {
		this.CorrID = corrID;
	}
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
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FullFraudResultRequest [FuncNm=");
		builder.append(FuncNm);
		builder.append(", RqUID=");
		builder.append(RqUID);
		builder.append(", RqDt=");
		builder.append(RqDt);
		builder.append(", RqAppId=");
		builder.append(RqAppId);
		builder.append(", UserId=");
		builder.append(UserId);
		builder.append(", TerminalId=");
		builder.append(TerminalId);
		builder.append(", UserLangPref=");
		builder.append(UserLangPref);
		builder.append(", CorrID=");
		builder.append(CorrID);
		builder.append(", applicationGroupNo=");
		builder.append(applicationGroupNo);
		builder.append(", product=");
		builder.append(product);
		builder.append(", asOfDate=");
		builder.append(asOfDate);
		builder.append(", result=");
		builder.append(result);
		builder.append("]");
		return builder.toString();
	}	
}
