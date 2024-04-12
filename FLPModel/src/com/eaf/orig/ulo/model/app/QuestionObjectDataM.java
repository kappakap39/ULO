package com.eaf.orig.ulo.model.app;

public class QuestionObjectDataM {
	private String personalId;
	private String questionSetCode;
	private ApplicationGroupDataM applicationGroup;
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getQuestionSetCode() {
		return questionSetCode;
	}
	public void setQuestionSetCode(String questionSetCode) {
		this.questionSetCode = questionSetCode;
	}
	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}
	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	
}
