package com.eaf.service.rest.controller.submitapplication.model;

import java.io.Serializable;

import com.eaf.im.rest.docset.model.InquireDocSetResponse;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;

@SuppressWarnings("serial")
public class CreateApplicationRequest implements Serializable,Cloneable{
	private SubmitApplicationRequest submitApplicationRequest;
	private InquireDocSetResponse inquireDocSetResponse;
	private String applicationGroupId;
	private String coverPageType;
	private String userId;
	public SubmitApplicationRequest getSubmitApplicationRequest() {
		return submitApplicationRequest;
	}
	public void setSubmitApplicationRequest(
			SubmitApplicationRequest submitApplicationRequest) {
		this.submitApplicationRequest = submitApplicationRequest;
	}
	public InquireDocSetResponse getInquireDocSetResponse() {
		return inquireDocSetResponse;
	}
	public void setInquireDocSetResponse(InquireDocSetResponse inquireDocSetResponse) {
		this.inquireDocSetResponse = inquireDocSetResponse;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}		
}
