package com.kbank.eappu.model;

import com.google.gson.annotations.Expose;

/**
 * A header model of message.
 * @author Pinyo.L 
 **/
public class MessageHeader {
	@Expose
	private String appId;
	@Expose
	private String requestDateTime;
	@Expose
	private String requestUniqueId;
	@Expose
	private String mobileNo;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(String requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public String getRequestUniqueId() {
		return requestUniqueId;
	}
	public void setRequestUniqueId(String requestUniqueId) {
		this.requestUniqueId = requestUniqueId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}
