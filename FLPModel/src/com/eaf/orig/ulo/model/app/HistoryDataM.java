package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class HistoryDataM implements Serializable,Cloneable{
	private String historyDataId;		//ORIG_HISTORY_DATA.HISTORY_DATA_ID(VARCHAR2)
	private String applicationGroupId;	//ORIG_HISTORY_DATA.APPLICATION_GROUP_ID(VARCHAR2)
	private String role;				//ORIG_HISTORY_DATA.ROLE(VARCHAR2)
	private String appData;				//ORIG_HISTORY_DATA.APP_DATA(VARCHAR2)
	private String createBy;			//ORIG_HISTORY_DATA.CREATE_BY(VARCHAR2)
	private Timestamp createDate;		//ORIG_HISTORY_DATA.CREATE_DATE(DATE)
	private String updateBy;			//ORIG_HISTORY_DATA.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;		//ORIG_HISTORY_DATA.UPDATE_DATE(DATE)
	
	public String getHistoryDataId() {
		return historyDataId;
	}
	public void setHistoryDataId(String historyDataId) {
		this.historyDataId = historyDataId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAppData() {
		return appData;
	}
	public void setAppData(String appData) {
		this.appData = appData;
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
}
