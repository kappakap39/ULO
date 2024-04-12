package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.List;

public class SearchWorkFlowInquiryDataM implements Serializable, Cloneable{
	private List<String> applicationGroupIds;
	private String personalId;
	private boolean isSearchApplicationGroupId = false;
	private boolean isSearchUnmatched = false;
	
	public List<String> getApplicationGroupIds() {
		return applicationGroupIds;
	}
	public void setApplicationGroupIds(List<String> applicationGroupIds) {
		this.applicationGroupIds = applicationGroupIds;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public boolean isSearchApplicationGroupId() {
		return isSearchApplicationGroupId;
	}
	public void setSearchApplicationGroupId(boolean isSearchApplicationGroupId) {
		this.isSearchApplicationGroupId = isSearchApplicationGroupId;
	}
	public boolean isSearchUnmatched() {
		return isSearchUnmatched;
	}
	public void setSearchUnmatched(boolean isSearchUnmatched) {
		this.isSearchUnmatched = isSearchUnmatched;
	}
}
