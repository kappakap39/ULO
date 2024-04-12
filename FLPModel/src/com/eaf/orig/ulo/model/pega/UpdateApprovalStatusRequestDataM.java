package com.eaf.orig.ulo.model.pega;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import com.eaf.orig.ulo.model.app.ApplicationDataM;

public class UpdateApprovalStatusRequestDataM implements Serializable, Cloneable{
	private String applicationGroupNo;
	private String jobState;
	private ArrayList<ApplicationDataM> applications;
	private Date applicationDate;
	private String isVetoEligible;
	
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
	public ArrayList<ApplicationDataM> getApplications() {
		return applications;
	}
	public void setApplications(ArrayList<ApplicationDataM> applications) {
		this.applications = applications;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getIsVetoEligible() {
		return isVetoEligible;
	}
	public void setIsVetoEligible(String isVetoEligible) {
		this.isVetoEligible = isVetoEligible;
	}
	
}
