package com.eaf.inf.batch.ulo.notifycompletedapp.model;

import java.io.Serializable;

public class NotifyCompleteAppDataM implements Serializable, Cloneable {
	public NotifyCompleteAppDataM() {
		super();
	}
	private String applicationGroupId;
	private String idNo;
	private String idType;
	private String personalType;
	private String cisId;
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getCisId() {
		return cisId;
	}
	public void setCisId(String cisId) {
		this.cisId = cisId;
	}	
}
