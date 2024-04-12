package com.eaf.orig.ulo.app.model;

import java.io.Serializable;
import java.sql.Timestamp;
@SuppressWarnings("serial")
public class PolicyRulesDataM implements Serializable,Cloneable{
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
}
