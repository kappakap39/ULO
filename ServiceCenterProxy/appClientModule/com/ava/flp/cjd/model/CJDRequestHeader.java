package com.ava.flp.cjd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class CJDRequestHeader implements Serializable,Cloneable{
	public CJDRequestHeader(){
		super();
	}
	private String appId;
	private String messageUid;
	private Date messageDt;
	private String appUser;
	private String appPwd;
	
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
	public Date getMessageDt() {
		return messageDt;
	}
	public void setMessageDt(Date messageDt) {
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
