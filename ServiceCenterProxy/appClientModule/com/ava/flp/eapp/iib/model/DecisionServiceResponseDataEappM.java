package com.ava.flp.eapp.iib.model;

import org.codehaus.jackson.annotate.JsonProperty;

import decisionservice_iib.ApplicationGroupDataM;

public class DecisionServiceResponseDataEappM {
	public class Result{
		public static final int EAI_SUCCESS_CODE = 0;
		public static final String SUCCESS = "00";
		public static final String BUSINESS_EXCEPTION = "10";
		public static final String SYSTEM_EXCEPTION = "20";
		public static final String WARNING = "30";
	}
	public class ResultDesc{
		public static final String SUCCESS = "SUCCESS";
		public static final String ERROR = "ERROR";
	}
	
	@JsonProperty("decisionPoint")
	private String decisionPoint;
	
	@JsonProperty("reCalculateActionFlag")
	private String reCalculateActionFlag;
	
	@JsonProperty("responseData")
	private ApplicationGroup responseData = new ApplicationGroup();
	
	public DecisionServiceResponseDataEappM(){
		super();
	}

	public String getDecisionPoint() {
		return decisionPoint;
	}

	public void setDecisionPoint(String decisionPoint) {
		this.decisionPoint = decisionPoint;
	}

	public ApplicationGroup getResponseData() {
		return responseData;
	}

	public void setResponseData(ApplicationGroup responseData) {
		this.responseData = responseData;
	}
	
	public String getReCalculateActionFlag() {
		return reCalculateActionFlag;
	}

	public void setReCalculateActionFlag(String reCalculateActionFlag) {
		this.reCalculateActionFlag = reCalculateActionFlag;
	}
}
