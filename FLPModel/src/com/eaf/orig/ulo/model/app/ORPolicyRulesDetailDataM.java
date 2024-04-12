package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.orig.model.util.ModelUtil;

@SuppressWarnings("serial")
public class ORPolicyRulesDetailDataM implements Serializable,Cloneable{
	public ORPolicyRulesDetailDataM(){
		super();
	}	
	public void init(String refId,String uniqueId){
		this.orPolicyRulesId = refId;
		this.orPolicyRulesDetailId = uniqueId;
	}
	private String orPolicyRulesDetailId;	//XRULES_OR_POLICY_RULES_DETAIL.OR_POLICY_RULES_DETAIL_ID(VARCHAR2)
	private String orPolicyRulesId;	//XRULES_OR_POLICY_RULES_DETAIL.OR_POLICY_RULES_ID(VARCHAR2)
	private String result;	//XRULES_OR_POLICY_RULES_DETAIL.RESULT(VARCHAR2)
	private int rank;	//XRULES_OR_POLICY_RULES_DETAIL.RANK(NUMBER)
	private String reason;	//XRULES_OR_POLICY_RULES_DETAIL.REASON(VARCHAR2)
	private String verifiedResult;	//XRULES_OR_POLICY_RULES_DETAIL.VERIFIED_RESULT(VARCHAR2)
	private String minApprovalAuth;	//XRULES_OR_POLICY_RULES_DETAIL.MIN_APPROVAL_AUTH(VARCHAR2)
	private String guidelineCode;	//XRULES_OR_POLICY_RULES_DETAIL.GUIDELINE_CODE(VARCHAR2)
	private ArrayList<GuidelineDataM> guidelines;
	private String createBy;	//XRULES_OR_POLICY_RULES_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_OR_POLICY_RULES_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_OR_POLICY_RULES_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_OR_POLICY_RULES_DETAIL.UPDATE_DATE(DATE)
	
	public String getOrPolicyRulesDetailId() {
		return orPolicyRulesDetailId;
	}
	public void setOrPolicyRulesDetailId(String orPolicyRulesDetailId) {
		this.orPolicyRulesDetailId = orPolicyRulesDetailId;
	}
	public String getOrPolicyRulesId() {
		return orPolicyRulesId;
	}
	public void setOrPolicyRulesId(String orPolicyRulesId) {
		this.orPolicyRulesId = orPolicyRulesId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getVerifiedResult() {
		return verifiedResult;
	}
	public void setVerifiedResult(String verifiedResult) {
		this.verifiedResult = verifiedResult;
	}
	public String getMinApprovalAuth() {
		return minApprovalAuth;
	}
	public void setMinApprovalAuth(String minApprovalAuth) {
		this.minApprovalAuth = minApprovalAuth;
	}
	public String getGuidelineCode() {
		return guidelineCode;
	}
	public void setGuidelineCode(String guidelineCode) {
		this.guidelineCode = guidelineCode;
	}
	public ArrayList<GuidelineDataM> getGuidelines() {
		return guidelines;
	}
	public void setGuidelines(ArrayList<GuidelineDataM> guidelines) {
		this.guidelines = guidelines;
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
	public String getGuidelineData(){
		if(null != guidelines) {
			StringBuilder guidelineData = new StringBuilder("");
			String comma = "";
			for(GuidelineDataM guidelineM : guidelines) {
				String textData = "";
				if(!ModelUtil.empty(guidelineM.getName())){
					textData = guidelineM.getName();
				}
				if(!ModelUtil.empty(guidelineM.getValue())){
					if(ModelUtil.empty(textData)){
						textData = guidelineM.getValue();
					}else{
						textData += " : "+guidelineM.getValue();
					}
				}
				if(!ModelUtil.empty(textData)){
					 guidelineData.append(comma+textData);
					comma = ",";
				}
			}
			return guidelineData.toString();
		}
		return null;
	}
}
