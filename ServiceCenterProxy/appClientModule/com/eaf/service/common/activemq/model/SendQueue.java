package com.eaf.service.common.activemq.model;

public class SendQueue {

	private String body;
	private String applicationGroupId;
	private String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplicationGroupId() {
		return applicationGroupId;
	}

	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
