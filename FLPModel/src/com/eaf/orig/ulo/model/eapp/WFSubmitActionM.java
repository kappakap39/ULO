package com.eaf.orig.ulo.model.eapp;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WFSubmitActionM implements Cloneable, Serializable {

	private String applicationGroupId;
	private String callAction;
	private String userId;
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCallAction() {
		return callAction;
	}
	public void setCallAction(String callAction) {
		this.callAction = callAction;
	}
	
}
