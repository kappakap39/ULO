package com.eaf.orig.ulo.model.pega;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class UpdateApprovalStatusRequest implements Serializable, Cloneable{
	
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
	public String getCSVContent() {
		return CSVContent;
	}
	public void setCSVContent(String cSVContent) {
		CSVContent = cSVContent;
	}
}
