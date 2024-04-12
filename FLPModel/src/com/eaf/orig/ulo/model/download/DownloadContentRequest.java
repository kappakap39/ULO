package com.eaf.orig.ulo.model.download;

import java.io.Serializable;
import java.sql.Date;

public class DownloadContentRequest implements Serializable, Cloneable{
	private String FuncNm;
	private String RqUID;
	private Date RqDt;
	private String RqAppId;
	private String UserId;
	private String TerminalId;
	private String UserLangPref;
	private String CorrID;
	
	private String Authorization;
	private String DocumentChronicleID;
	private String DocumentObjectID;
	private String Docbase;
	
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
	public Date getRqDt() {
		return RqDt;
	}
	public void setRqDt(Date rqDt) {
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
	public String getAuthorization() {
		return Authorization;
	}
	public void setAuthorization(String authorization) {
		Authorization = authorization;
	}
	public String getDocumentChronicleID() {
		return DocumentChronicleID;
	}
	public void setDocumentChronicleID(String documentChronicleID) {
		DocumentChronicleID = documentChronicleID;
	}
	public String getDocumentObjectID() {
		return DocumentObjectID;
	}
	public void setDocumentObjectID(String documentObjectID) {
		DocumentObjectID = documentObjectID;
	}
	public String getDocbase() {
		return Docbase;
	}
	public void setDocbase(String docbase) {
		Docbase = docbase;
	}

}
