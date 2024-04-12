package com.eaf.service.common.model.decisions;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import decisionservice_iib.ApplicationGroupDataM;

public class DecisionServiceResponseDataM  implements Serializable,Cloneable {
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
	private ApplicationGroupDataM responseData = new ApplicationGroupDataM();
	
	public DecisionServiceResponseDataM(){
		super();
	}

	public String getDecisionPoint() {
		return decisionPoint;
	}

	public void setDecisionPoint(String decisionPoint) {
		this.decisionPoint = decisionPoint;
	}

	public ApplicationGroupDataM getResponseData() {
		return responseData;
	}

	public void setResponseData(ApplicationGroupDataM responseData) {
		this.responseData = responseData;
	}
	
	public String getReCalculateActionFlag() {
		return reCalculateActionFlag;
	}

	public void setReCalculateActionFlag(String reCalculateActionFlag) {
		this.reCalculateActionFlag = reCalculateActionFlag;
	}

}
