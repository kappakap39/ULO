package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ApplicationResult implements Serializable,Cloneable{
	public ApplicationResult(){
		
	}
	private String ruleId;
	private String ruleResult;
	private String reasonCode;
	private int reasonRank;
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public int getReasonRank() {
		return reasonRank;
	}
	public void setReasonRank(int reasonRank) {
		this.reasonRank = reasonRank;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleResult() {
		return ruleResult;
	}
	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}
}
