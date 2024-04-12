package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;

public class PolicyRulesConditionDataM implements Serializable,Cloneable{
	
	public PolicyRulesConditionDataM(){
		super();
	}
	
	private String policyRulesConditionId;//POLICY_RULES_CONDITION_ID	VARCHAR2(50 CHAR)
	private String policyRulesId;//POLICY_RULES_ID	VARCHAR2(50 CHAR)
	private String conditionCode;//CONDITION_CODE VARCHAR2(100 CHAR)
	private String createBy;//CREATE_BY	VARCHAR2(50 CHAR)
	private Date createDate;//CREATE_DATE	DATE
	private String updateBy;//UPDATE_BY	VARCHAR2(50 CHAR)
	private Date updateDate;//UPDATE_DATE	DATE
	
	
	public String getPolicyRulesConditionId() {
		return policyRulesConditionId;
	}
	public void setPolicyRulesConditionId(String policyRulesConditionId) {
		this.policyRulesConditionId = policyRulesConditionId;
	}
	public String getPolicyRulesId() {
		return policyRulesId;
	}
	public void setPolicyRulesId(String policyRulesId) {
		this.policyRulesId = policyRulesId;
	}
	public String getConditionCode() {
		return conditionCode;
	}
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
