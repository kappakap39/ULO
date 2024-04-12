package com.eaf.orig.ulo.model.followup;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class FollowUpResultRequest implements Serializable, Cloneable{
	
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
	
	@JsonProperty("CaseID")
	private String CaseID;
	
	@JsonProperty("CSVContent")
	private String CSVContent;
	
	public String getFuncNm() {
		return FuncNm;
	}
	public void setFuncNm(String funcNm) {
		FuncNm = funcNm;
	}
	public String getRqUID() {
		return RqUID;
	}
	public void setRqUID(String rqUID) {
		RqUID = rqUID;
	}
	public String getRqDt() {
		return RqDt;
	}
	public void setRqDt(String rqDt) {
		RqDt = rqDt;
	}
	public String getRqAppId() {
		return RqAppId;
	}
	public void setRqAppId(String rqAppId) {
		RqAppId = rqAppId;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getTerminalId() {
		return TerminalId;
	}
	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}
	public String getUserLangPref() {
		return UserLangPref;
	}
	public void setUserLangPref(String userLangPref) {
		UserLangPref = userLangPref;
	}
	public String getCorrID() {
		return CorrID;
	}
	public void setCorrID(String corrID) {
		CorrID = corrID;
	}
	public String getCaseID() {
		return CaseID;
	}
	public void setCaseID(String caseID) {
		CaseID = caseID;
	}
	public String getCSVContent() {
		return CSVContent;
	}
	public void setCSVContent(String cSVContent) {
		CSVContent = cSVContent;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FollowUpResultRequest [FuncNm=");
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
		builder.append(", CaseID=");
		builder.append(CaseID);
		builder.append(", CSVContent=");
		builder.append(CSVContent);
		builder.append("]");
		return builder.toString();
	}	
}
