package com.eaf.service.module.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eaf.orig.model.util.ModelUtil;

@SuppressWarnings("serial")
public class CheckProductDupDataM implements Serializable, Cloneable{
	private String	idNo;
	private String	userId;
	private String	requestId;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}