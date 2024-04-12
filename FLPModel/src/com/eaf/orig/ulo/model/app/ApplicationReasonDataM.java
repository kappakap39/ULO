package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.util.ArrayList;

public class ApplicationReasonDataM implements Serializable, Cloneable{
	private String applicationGroupId;
	private String applicationRecordId;
	private ArrayList<ReasonDataM> reasons;
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public ArrayList<ReasonDataM> getReasons() {
		return reasons;
	}
	public void setReasons(ArrayList<ReasonDataM> reasons) {
		this.reasons = reasons;
	}

}
