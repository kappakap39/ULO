package com.eaf.orig.ulo.service.followup.result.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.DocumentScenarioDataM;



@SuppressWarnings("serial")
public class FollowUpResultApplicationDataM implements Serializable, Cloneable{
	public FollowUpResultApplicationDataM(){
		super();
	}
	private String applicationGroupId;
	private String applicationGroupNo;
	private String applicationType;
	private String jobState;
	private String instantId;
	private String followUpStatus;
	private ArrayList<String> documentSLAType;
	private String userId;		
	private FollowUpCSVContentDataM followUpContent;
	private ArrayList<DocumentCheckListDataM> documentCheckListDataM;
	private ArrayList<DocumentScenarioDataM> documentScenarioDataM;
	public ArrayList<DocumentScenarioDataM> getDocumentScenarioDataM() {
		return documentScenarioDataM;
	}
	public void setDocumentScenarioDataM(
			ArrayList<DocumentScenarioDataM> documentScenarioDataM) {
		this.documentScenarioDataM = documentScenarioDataM;
	}
	public ArrayList<DocumentCheckListDataM> getDocumentCheckListDataM() {
		return documentCheckListDataM;
	}
	public void setDocumentCheckListDataM(
			ArrayList<DocumentCheckListDataM> documentCheckListDataM) {
		this.documentCheckListDataM = documentCheckListDataM;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getInstantId() {
		return instantId;
	}
	public void setInstantId(String instantId) {
		this.instantId = instantId;
	}
	public String getFollowUpStatus() {
		return followUpStatus;
	}
	public void setFollowUpStatus(String followUpStatus) {
		this.followUpStatus = followUpStatus;
	}
	public ArrayList<String> getDocumentSLAType() {
		return documentSLAType;
	}
	public void setDocumentSLAType(ArrayList<String> documentSLAType) {
		this.documentSLAType = documentSLAType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public FollowUpCSVContentDataM getFollowUpContent() {
		return followUpContent;
	}
	public void setFollowUpContent(FollowUpCSVContentDataM followUpContent) {
		this.followUpContent = followUpContent;
	}	
}
