package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.io.Serializable;

public class DecisionServiceResponse implements Serializable,Cloneable{
	public DecisionServiceResponse(){
		super();
	}
	private String resultCode;
	private String resultDesc;
	private String errorType;
	
	private String decisionPoint;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	 
	public String getDecisionPoint() {
		return decisionPoint;
	}
	public void setDecisionPoint(String decisionPoint) {
		this.decisionPoint = decisionPoint;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}	
}
