package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class WFInquiryAppResponseDataM implements Serializable,Cloneable{
	private Integer noRecord;
	private ArrayList<ApplicationGroupDataM> applicationGroups;
	private String personalId;
	
	public Integer getNoRecord() {
		return noRecord;
	}
	public void setNoRecord(Integer noRecord) {
		this.noRecord = noRecord;
	}
	public ArrayList<ApplicationGroupDataM> getApplicationGroupM() {
		return applicationGroups;
	}
	public void setApplicationGroupM(ArrayList<ApplicationGroupDataM> applicationGroups) {
		this.applicationGroups = applicationGroups;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
}
