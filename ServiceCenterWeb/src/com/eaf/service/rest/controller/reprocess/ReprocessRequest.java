package com.eaf.service.rest.controller.reprocess;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReprocessRequest {
	
	@JsonProperty("applicationGroupId")
	String applicationGroupId;

	public String getApplicationGroupId() {
		return applicationGroupId;
	}

	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReprocessRequest [wmFn=");
		builder.append(applicationGroupId);
		builder.append("]");
		return builder.toString();
	}
}
