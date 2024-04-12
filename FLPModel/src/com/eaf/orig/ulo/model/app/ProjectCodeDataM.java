package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProjectCodeDataM implements Serializable,Cloneable{
	public ProjectCodeDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_PROJECT_CODE.APPLICATION_GROUP_ID(VARCHAR2)
	private String projectCode;	//ORIG_PROJECT_CODE.PROJECT_CODE(VARCHAR2)
	private String createBy;	//ORIG_PROJECT_CODE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_PROJECT_CODE.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_PROJECT_CODE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_PROJECT_CODE.UPDATE_DATE(DATE)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
