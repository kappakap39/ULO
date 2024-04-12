package com.eaf.orig.ulo.model.submitapplication.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SubmitApplicationObjectDataM implements Serializable, Cloneable{
	public SubmitApplicationObjectDataM () {
		super();
	}	
	private String applicationGroupId;
	private String coverPageType;
	private Object inquireDocSetResponse;
	private Object submitApplicationRequest;
	private String transactionId;
	private String userId;
	private boolean existApplication = false;
	
	public Object getInquireDocSetResponse() {
		return inquireDocSetResponse;
	}
	public void setInquireDocSetResponse(Object inquireDocSetResponse) {
		this.inquireDocSetResponse = inquireDocSetResponse;
	}
	public Object getSubmitApplicationRequest() {
		return submitApplicationRequest;
	}
	public void setSubmitApplicationRequest(Object submitApplicationRequest) {
		this.submitApplicationRequest = submitApplicationRequest;
	}	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getCoverPageType() {
		return coverPageType;
	}
	public void setCoverPageType(String coverPageType) {
		this.coverPageType = coverPageType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isExistApplication() {
		return existApplication;
	}
	public void setExistApplication(boolean existApplication) {
		this.existApplication = existApplication;
	}
	
}
