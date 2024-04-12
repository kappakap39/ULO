package com.eaf.service.common.model.decisions;

import java.io.Serializable;

import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DecisionServiceRequestDataM implements Serializable,Cloneable{
	public static enum CallerScreen{SME_POPUP,HR_VER_POPUP,CUST_VER_POPUP,WEB_VER_POPUP,OCCUPATION,INCOME,CA_SUMMARY_UPDATE_OR,INCOME_VARIENCE};
	
	public DecisionServiceRequestDataM(){
		super();
	}
	private String decisionPoint;
	private ApplicationGroupDataM applicationGroup;
	private String roleId;
	private String userId;
	private CallerScreen callerScreen;	
	private String transactionId;
	private String reCalculateActionFlag;
	private String fromAction;
	public String getDecisionPoint() {
		return decisionPoint;
	}
	public void setDecisionPoint(String decisionPoint) {
		this.decisionPoint = decisionPoint;
	}
	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}
	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public CallerScreen getCallerScreen() {
		return callerScreen;
	}
	public void setCallerScreen(CallerScreen callerScreen) {
		this.callerScreen = callerScreen;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getReCalculateActionFlag() {
		return reCalculateActionFlag;
	}
	public void setReCalculateActionFlag(String reCalculateActionFlag) {
		this.reCalculateActionFlag = reCalculateActionFlag;
	}
	public String getFromAction() {
		return fromAction;
	}
	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}
}
