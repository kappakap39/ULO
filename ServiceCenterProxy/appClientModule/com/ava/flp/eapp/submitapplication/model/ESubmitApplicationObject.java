package com.ava.flp.eapp.submitapplication.model;



public class ESubmitApplicationObject {
	
	private Object applicationGroup = null;
	private String userId = null;
	private String transactionId;
	private String callAction;

	public Object getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(Object applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCallAction() {
		return callAction;
	}

	public void setCallAction(String callAction) {
		this.callAction = callAction;
	}
}

