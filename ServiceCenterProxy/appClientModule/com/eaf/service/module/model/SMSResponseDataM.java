package com.eaf.service.module.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SMSResponseDataM implements Serializable,Cloneable{
	private int responseCode;
	private String responseDetail;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDetail() {
		return responseDetail;
	}
	public void setResponseDetail(String responseDetail) {
		this.responseDetail = responseDetail;
	}
	@Override
	public String toString() {
		return "SMSResponseDataM [responseCode=" + responseCode
				+ ", responseDetail=" + responseDetail + "]";
	}
	
}
