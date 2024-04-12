package com.eaf.orig.ulo.model.eapp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class WFSubmitApplicationM implements Serializable,Cloneable{

	@JsonProperty("applicationGroupId")
	private String applicationGroupId;
	
	@JsonProperty("applicationGroupNo")
	private String applicationGroupNo;
	
	@JsonProperty("requireVerify")
	private String requireVerify;
	
	@JsonProperty("callAction")
	private String callAction;
	
	@JsonProperty("userId")
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

	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}

	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}

	public String getRequireVerify() {
		return requireVerify;
	}

	public void setRequireVerify(String requireVerify) {
		this.requireVerify = requireVerify;
	}

	public String getCallAction() {
		return callAction;
	}

	public void setCallAction(String callAction) {
		this.callAction = callAction;
	}

}
