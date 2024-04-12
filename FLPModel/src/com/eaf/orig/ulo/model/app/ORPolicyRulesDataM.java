package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ORPolicyRulesDataM implements Serializable,Cloneable{
	public ORPolicyRulesDataM(){
		super();
	}
	
	public void init(String refId,String uniqueId){
		this.policyRulesId = refId;
		this.orPolicyRulesId = uniqueId;
	}
	private String orPolicyRulesId;	//XRULES_OR_POLICY_RULES.OR_POLICY_RULES_ID(VARCHAR2)
	private String policyRulesId;	//XRULES_OR_POLICY_RULES.POLICY_RULES_ID(VARCHAR2)
	private String reason;	//XRULES_OR_POLICY_RULES.REASON(VARCHAR2)
	private String policyCode;	//XRULES_OR_POLICY_RULES.POLICY_CODE(VARCHAR2)
	private String resultDesc;	//XRULES_OR_POLICY_RULES.RESULT_DESC(VARCHAR2)
	private String policyType;	//XRULES_OR_POLICY_RULES.POLICY_TYPE(VARCHAR2)
	private String result;	//XRULES_OR_POLICY_RULES.RESULT(VARCHAR2)
	private String verifiedResult;	//XRULES_OR_POLICY_RULES.VERIFIED_RESULT(VARCHAR2)
	private String createBy;	//XRULES_OR_POLICY_RULES.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_OR_POLICY_RULES.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_OR_POLICY_RULES.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_OR_POLICY_RULES.UPDATE_DATE(DATE)
	private String minApprovalAuth;	//XRULES_OR_POLICY_RULES.MIN_APPROVAL_AUTH(VARCHAR2)
	private String appRecordId;	//XRULES_OR_POLICY_RULES.MIN_APPROVAL_AUTH(VARCHAR2)
	
	
	private ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDetails;
	
	public String getOrPolicyRulesId() {
		return orPolicyRulesId;
	}
	public void setOrPolicyRulesId(String orPolicyRulesId) {
		this.orPolicyRulesId = orPolicyRulesId;
	}
	public String getPolicyRulesId() {
		return policyRulesId;
	}
	public void setPolicyRulesId(String policyRulesId) {
		this.policyRulesId = policyRulesId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPolicyCode() {
		return policyCode;
	}
	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getVerifiedResult() {
		return verifiedResult;
	}
	public void setVerifiedResult(String verifiedResult) {
		this.verifiedResult = verifiedResult;
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
	public ArrayList<ORPolicyRulesDetailDataM> getOrPolicyRulesDetails() {
		return orPolicyRulesDetails;
	}
	public void setOrPolicyRulesDetails(ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDetails) {
		this.orPolicyRulesDetails = orPolicyRulesDetails;
	}
	public String getMinApprovalAuth() {
		return minApprovalAuth;
	}
	public void setMinApprovalAuth(String minApprovalAuth) {
		this.minApprovalAuth = minApprovalAuth;
	}	
	
	public void addPolicyRuleDetail(ORPolicyRulesDetailDataM orPolicyRulesDetail) {
		this.orPolicyRulesDetails.add(orPolicyRulesDetail);
	}
	public String getAppRecordId() {
		return appRecordId;
	}
	public void setAppRecordId(String appRecordId) {
		this.appRecordId = appRecordId;
	}	
	
}
