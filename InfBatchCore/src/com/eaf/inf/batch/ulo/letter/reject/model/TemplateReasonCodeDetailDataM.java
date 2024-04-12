package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class TemplateReasonCodeDetailDataM implements Serializable, Cloneable{
	public TemplateReasonCodeDetailDataM() {
		super();
	}
	private String applicationGroupId;
	private String language;
	private String personalType;
	private String policyProgramId;
	private String finalDecision;
	private String applicationRecordId;
	private String personalId;
	private String cardCode;
	private String reasonCode;
	private String businessClassId;
	private TemplateAppDecisionDataM templateAppDecision;
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getPolicyProgramId() {
		return policyProgramId;
	}
	public void setPolicyProgramId(String policyProgramId) {
		this.policyProgramId = policyProgramId;
	}
	public String getFinalDecision() {
		return finalDecision;
	}
	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	public TemplateAppDecisionDataM getTemplateAppDecision() {
		return templateAppDecision;
	}
	public void setTemplateAppDecision(TemplateAppDecisionDataM templateAppDecision) {
		this.templateAppDecision = templateAppDecision;
	}
}
