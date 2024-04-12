package com.eaf.orig.ulo.app.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.model.PolicyRulesDataM;
@SuppressWarnings("serial")
public class VerificationResultDataM implements Serializable,Cloneable{
	private String verResultId;	//XRULES_VERIFICATION_RESULT.VER_RESULT_ID(VARCHAR2)
	private String refId; //XRULES_VERIFICATION_RESULT.REF_ID(VARCHAR2)
	private String verLevel;	//XRULES_VERIFICATION_RESULT.VER_LEVEL(VARCHAR2)
	private String docCompletedFlag;	//XRULES_VERIFICATION_RESULT.DOC_COMPLETED_FLAG(VARCHAR2)
	private String createBy;	//XRULES_VERIFICATION_RESULT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_VERIFICATION_RESULT.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_VERIFICATION_RESULT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_VERIFICATION_RESULT.UPDATE_DATE(DATE)
	private ArrayList<PolicyRulesDataM> policyRules;
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getVerLevel() {
		return verLevel;
	}
	public void setVerLevel(String verLevel) {
		this.verLevel = verLevel;
	}
	public String getDocCompletedFlag() {
		return docCompletedFlag;
	}
	public void setDocCompletedFlag(String docCompletedFlag) {
		this.docCompletedFlag = docCompletedFlag;
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
	public ArrayList<PolicyRulesDataM> getPolicyRules() {
		return policyRules;
	}
	public void setPolicyRules(ArrayList<PolicyRulesDataM> policyRules) {
		this.policyRules = policyRules;
	}
}