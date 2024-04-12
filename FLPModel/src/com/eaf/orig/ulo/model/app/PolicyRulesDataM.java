package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PolicyRulesDataM implements Serializable,Cloneable{
	public PolicyRulesDataM(){
		super();
	}
	
	public void init(String refId,String uniqueId){
		this.verResultId = refId;
		this.policyRulesId = uniqueId;
	}
	private String verResultId;	//XRULES_POLICY_RULES.VER_RESULT_ID(VARCHAR2)	
	private String policyRulesId;	//XRULES_POLICY_RULES.POLICY_RULES_ID(VARCHAR2)
	private String policyCode;	//XRULES_POLICY_RULES.POLICY_CODE(VARCHAR2)
	private String result;	//XRULES_POLICY_RULES.RESULT(VARCHAR2)
	private String verifiedResult;	//XRULES_POLICY_RULES.VERIFIED_RESULT(VARCHAR2)
	private String createBy;	//XRULES_POLICY_RULES.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_POLICY_RULES.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_POLICY_RULES.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_POLICY_RULES.UPDATE_DATE(DATE)	
	private String reason; //XRULES_POLICY_RULES.REASON(VARCHAR2)
	private int rank; //XRULES_POLICY_RULES.RANK(NUMBER)
//	private ArrayList<PolicyRulesDetailDataM> policyRulesDetails;	
	private ArrayList<ORPolicyRulesDataM> orPolicyRules;
	private ArrayList<PolicyRulesConditionDataM> policyRulesConditions;
	private String overrideFlag;
	
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getPolicyRulesId() {
		return policyRulesId;
	}
	public void setPolicyRulesId(String policyRulesId) {
		this.policyRulesId = policyRulesId;
	}
	public String getPolicyCode() {
		return policyCode;
	}
	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}	
//	public ArrayList<PolicyRulesDetailDataM> getPolicyRulesDetails() {
//		return policyRulesDetails;
//	}
//	public void setPolicyRulesDetails(ArrayList<PolicyRulesDetailDataM> policyRulesDetails) {
//		this.policyRulesDetails = policyRulesDetails;
//	}
	public String getVerifiedResult() {
		return verifiedResult;
	}
	public void setVerifiedResult(String verifiedResult) {
		this.verifiedResult = verifiedResult;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public ArrayList<ORPolicyRulesDataM> getOrPolicyRules() {
		return orPolicyRules;
	}
	public void setOrPolicyRules(ArrayList<ORPolicyRulesDataM> orPolicyRules) {
		this.orPolicyRules = orPolicyRules;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getOverrideFlag() {
		return overrideFlag;
	}
	public void setOverrideFlag(String overrideFlag) {
		this.overrideFlag = overrideFlag;
	}

	public ArrayList<String> getORPolicyRulesPolicyCodes(){
		ArrayList<String> orPolicyCodes = new ArrayList<String>();
		if(null!=orPolicyRules){
			for(ORPolicyRulesDataM orPolicyRulesDataM :orPolicyRules){
				String policyCode = orPolicyRulesDataM.getPolicyCode();
				if(null!=policyCode && !"".equals(policyCode)){
					orPolicyCodes.add(policyCode);
				}
			}
		}
		return orPolicyCodes;
	}

	public ArrayList<PolicyRulesConditionDataM> getPolicyRulesConditions() {
		return policyRulesConditions;
	}

	public void setPolicyRulesConditions(ArrayList<PolicyRulesConditionDataM> policyRulesConditions) {
		this.policyRulesConditions = policyRulesConditions;
	}
	
	public PolicyRulesConditionDataM getConditionCode(String conditionCode){
		if(null != conditionCode && null!=policyRulesConditions){
			for(PolicyRulesConditionDataM policyCondition : policyRulesConditions){
				if(null != policyCondition.getConditionCode() && policyCondition.getConditionCode().equals(conditionCode)){
					return policyCondition;
				}
			}
		}

		return null;
	}
	
	public void setPolicyRuleCondition(PolicyRulesConditionDataM policyRuleCondition){
		if(null==policyRulesConditions){
			policyRulesConditions = new ArrayList<PolicyRulesConditionDataM>();
		}
		if(null != policyRuleCondition){
			policyRulesConditions.add(policyRuleCondition);
		}
	}
}
