package com.eaf.orig.ulo.app.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ApplicationGroupDataM implements Serializable,Cloneable{
	private String applicationGroupId;
	private String applicationGroupNo;
	private String jobState;
	private String applicationStatus;
	private String applicationType;
	private String applicationTemplate;
	private String lifeCycle;
	private String createBy; // ORIG_APPLICATION_GROUP.CREATE_BY(VARCHAR2)
	private Timestamp createDate; // ORIG_APPLICATION_GROUP.CREATE_DATE(DATE)
	private String updateBy; // ORIG_APPLICATION_GROUP.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate; // ORIG_APPLICATION_GROUP.UPDATE_DATE(DATE)
	private ArrayList<ApplicationDataM> applications;
	private ArrayList<PersonalInfoDataM> personalInfos;
	private ArrayList<ApplicationLogDataM> applicationLogs;
	private String source;	//ORIG_APPLICATION_GROUP.SOURCE(VARCHAR2)
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
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getApplicationTemplate() {
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}
	public String getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(String lifeCycle) {
		this.lifeCycle = lifeCycle;
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
	public ArrayList<ApplicationDataM> getApplications() {
		return applications;
	}
	public void setApplications(ArrayList<ApplicationDataM> applications) {
		this.applications = applications;
	}
	public ArrayList<PersonalInfoDataM> getPersonalInfos() {
		return personalInfos;
	}
	public void setPersonalInfos(ArrayList<PersonalInfoDataM> personalInfos) {
		this.personalInfos = personalInfos;
	}
	public ArrayList<ApplicationLogDataM> getApplicationLogs() {
		return applicationLogs;
	}
	public void setApplicationLogs(ArrayList<ApplicationLogDataM> applicationLogs) {
		this.applicationLogs = applicationLogs;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public ArrayList<PersonalInfoDataM> getPersonalInfos(String personalType) {
		ArrayList<PersonalInfoDataM> _personalInfos = new ArrayList<PersonalInfoDataM>();
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (null != personalType && personalType.equals(personalInfo.getPersonalType())) {
					_personalInfos.add(personalInfo);
				}
			}
		}
		return _personalInfos;
	}
	public ArrayList<String> getApplicationLogJobStates(){
		ArrayList<String> jobStateLogList = new ArrayList<String>(); 
		if(null != applicationLogs){
			for(ApplicationLogDataM applicationLog : applicationLogs){
				String jobState = applicationLog.getJobState();
				if(!jobStateLogList.contains(jobState)){
					jobStateLogList.add(jobState);
				}
			}
		}
		return jobStateLogList;
	}
}
