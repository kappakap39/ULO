package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class ApplicationGroupDataM implements Serializable,Cloneable{
	private String appNo;
	private Date appDate;
	private ArrayList<PersonalInfoDataM> personalInfos;
	private ArrayList<ApplicationDataM> applications;
	
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public ArrayList<PersonalInfoDataM> getPersonalInfos() {
		return personalInfos;
	}
	public void setPersonalInfos(ArrayList<PersonalInfoDataM> personalInfos) {
		this.personalInfos = personalInfos;
	}
	public ArrayList<ApplicationDataM> getApplications() {
		return applications;
	}
	public void setApplications(ArrayList<ApplicationDataM> applications) {
		this.applications = applications;
	}

}
