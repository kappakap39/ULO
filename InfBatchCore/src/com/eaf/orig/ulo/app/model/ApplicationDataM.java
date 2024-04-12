package com.eaf.orig.ulo.app.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
@SuppressWarnings("serial")
public class ApplicationDataM implements Serializable,Cloneable{
	private String applicationRecordId;
	private String applicationGroupId;
	private String finalAppdecision;
	private String lifeCycle;
	private String businessClassId;
	private Date finalAppDecisionDate;
	private String finalAppDecisionBy;
	private String createBy;	//ORIG_APPLICATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_APPLICATION.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_APPLICATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_APPLICATION.UPDATE_DATE(DATE)
	private VerificationResultDataM verificationResult;
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getFinalAppdecision() {
		return finalAppdecision;
	}
	public void setFinalAppdecision(String finalAppdecision) {
		this.finalAppdecision = finalAppdecision;
	}
	public String getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(String lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	public Date getFinalAppDecisionDate() {
		return finalAppDecisionDate;
	}
	public void setFinalAppDecisionDate(Date finalAppDecisionDate) {
		this.finalAppDecisionDate = finalAppDecisionDate;
	}
	public String getFinalAppDecisionBy() {
		return finalAppDecisionBy;
	}
	public void setFinalAppDecisionBy(String finalAppDecisionBy) {
		this.finalAppDecisionBy = finalAppDecisionBy;
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
	public VerificationResultDataM getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(VerificationResultDataM verificationResult) {
		this.verificationResult = verificationResult;
	}
	public String getMinRankReason(){
		String NOTIFICATION_FINAL_APP_DECISION_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
		int minRank = 0;
		String minRankReasonCode = "";
		if(finalAppdecision.equals(NOTIFICATION_FINAL_APP_DECISION_REJECT)){
			ArrayList<PolicyRulesDataM> policyRuleList = verificationResult.getPolicyRules();
			if(!InfBatchUtil.empty(policyRuleList)){
				for(PolicyRulesDataM policyRule : policyRuleList){
					int rank = policyRule.getRank();
					String reasonCode = policyRule.getReason();
					if(rank!=0 && !InfBatchUtil.empty(reasonCode)){
						if(minRank==0){ // first time
							minRank = rank;
							minRankReasonCode = reasonCode;
						}else if(rank < minRank){
							minRank = rank;
							minRankReasonCode = reasonCode;
						}
					}
					
				}
			}
		}
		return minRankReasonCode;
	}
}
