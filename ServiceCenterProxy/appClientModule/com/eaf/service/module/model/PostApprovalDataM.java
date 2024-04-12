package com.eaf.service.module.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class PostApprovalDataM implements Serializable, Cloneable {

	@JsonProperty("applicationGroupNo")
	private String applicationGroupNo;
	
	@JsonProperty("recommendDecision")
	private String recommendDecision;
	
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getRecommendDecision() {
		return recommendDecision;
	}
	public void setRecommendDecision(String recomendDecision) {
		this.recommendDecision = recomendDecision;
	}
	
}
