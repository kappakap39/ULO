package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.Calendar;

public class SSHeaderRequest implements Serializable {

	private static final long serialVersionUID = 8317414342055058060L;
	
	private String FuncNm;
	private String RqUID;
	private Calendar RqDt;
	private String RqAppId;
	private String UserId;	
	private String CorrID;
	private String UserLangPref;
	
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
	public Calendar getRqDt() {
		return RqDt;
	}
	public void setRqDt(Calendar rqDt) {
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
}
