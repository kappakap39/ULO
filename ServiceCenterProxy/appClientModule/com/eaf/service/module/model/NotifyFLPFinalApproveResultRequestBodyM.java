package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NotifyFLPFinalApproveResultRequestBodyM implements Serializable, Cloneable {

	String mobileNo;
	String applicationGroupNo;
	String applicationDate;
	String applicationTemplate;
	String applyChannel;
	
	ArrayList<NotifyFLPApplicationDataM> applications;
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicationTemplate() {
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}
	public String getApplyChannel() {
		return applyChannel;
	}
	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
	}
	public ArrayList<NotifyFLPApplicationDataM> getApplications() {
		return applications;
	}
	public void setApplications(ArrayList<NotifyFLPApplicationDataM> applications) {
		this.applications = applications;
	}
	
}
