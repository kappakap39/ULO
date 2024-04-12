package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class NotifyFLPFinalApproveResultRequestHeaderM implements Serializable, Cloneable {

	String appId;
	String messageUid;
	String messageDt;
	String appUser;
	String appPwd;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMessageUid() {
		return messageUid;
	}
	public void setMessageUid(String messageUid) {
		this.messageUid = messageUid;
	}
	public String getMessageDt() {
		return messageDt;
	}
	public void setMessageDt(String messageDt) {
		this.messageDt = messageDt;
	}
	public String getAppUser() {
		return appUser;
	}
	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}
	public String getAppPwd() {
		return appPwd;
	}
	public void setAppPwd(String appPwd) {
		this.appPwd = appPwd;
	}
	
}
