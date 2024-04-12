package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class PolicyRulesDetailDataM implements Serializable,Cloneable{
	public PolicyRulesDetailDataM(){
		super();
	}
	private String policyRulesDetailId;	//XRULES_POLICY_RULES_DETAIL.POLICY_RULES_DETAIL_ID(VARCHAR2)
	private String policyRulesId;	//XRULES_POLICY_RULES_DETAIL.POLICY_RULES_ID(VARCHAR2)
	private String result;	//XRULES_POLICY_RULES_DETAIL.RESULT(VARCHAR2)
	private String createBy;	//XRULES_POLICY_RULES_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_POLICY_RULES_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_POLICY_RULES_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_POLICY_RULES_DETAIL.UPDATE_DATE(DATE)
	private String verifiedResult;	//XRULES_POLICY_RULES_DETAIL.VERIFIED_RESULT(VARCHAR2)
	private String guidelineCode;	//XRULES_POLICY_RULES_DETAIL.GUIDELINE_CODE(VARCHAR2)
	
	public String getPolicyRulesDetailId() {
		return policyRulesDetailId;
	}
	public void setPolicyRulesDetailId(String policyRulesDetailId) {
		this.policyRulesDetailId = policyRulesDetailId;
	}
	public String getPolicyRulesId() {
		return policyRulesId;
	}
	public void setPolicyRulesId(String policyRulesId) {
		this.policyRulesId = policyRulesId;
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
	public String getVerifiedResult() {
		return verifiedResult;
	}
	public void setVerifiedResult(String verifiedResult) {
		this.verifiedResult = verifiedResult;
	}
	public String getGuidelineCode() {
		return guidelineCode;
	}
	public void setGuidelineCode(String guidelineCode) {
		this.guidelineCode = guidelineCode;
	}
	
}
