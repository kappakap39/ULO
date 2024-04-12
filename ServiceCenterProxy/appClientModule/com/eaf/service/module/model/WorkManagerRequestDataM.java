package com.eaf.service.module.model;

public class WorkManagerRequestDataM {
	private String refId;
	private String WmFn;
	private String refCode;
	public String getWmFn() {
		return WmFn;
	}

	public void setWmFn(String wmFn) {
		WmFn = wmFn;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	
}
