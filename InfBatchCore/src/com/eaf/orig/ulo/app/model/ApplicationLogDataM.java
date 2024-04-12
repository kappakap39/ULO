package com.eaf.orig.ulo.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
@SuppressWarnings("serial")
public class ApplicationLogDataM implements Serializable, Cloneable {
	private String applicationGroupId;	//ORIG_APPLICATION_LOG.APPLICATION_GROUP_ID(VARCHAR2)
	private String appLogId;	//ORIG_APPLICATION_LOG.APP_LOG_ID(VARCHAR2)
	private BigDecimal lifeCycle;	//ORIG_APPLICATION_LOG.LIFE_CYCLE(NUMBER)
	private String jobState;	//ORIG_APPLICATION_LOG.JOB_STATE(VARCHAR2)
	private String action;	//ORIG_APPLICATION_LOG.ACTION(VARCHAR2)
	private String applicationStatus;	//ORIG_APPLICATION_LOG.APPLICATION_STATUS(VARCHAR2)
	private Date claimDate;	//ORIG_APPLICATION_LOG.CLAIM_DATE(DATE)
	private String actionDesc;	//ORIG_APPLICATION_LOG.ACTION_DESC(VARCHAR2)
	private String createBy;	//ORIG_APPLICATION_LOG.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_APPLICATION_LOG.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_APPLICATION_LOG.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_APPLICATION_LOG.UPDATE_DATE(DATE)
	private String applicationRecordId;//ORIG_APPLICATION_LOG.APPLICATION_RECORD_ID;
	private String roleName; //ROLE_NAME
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getAppLogId() {
		return appLogId;
	}
	public void setAppLogId(String appLogId) {
		this.appLogId = appLogId;
	}
	public BigDecimal getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(BigDecimal lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	public String getActionDesc() {
		return actionDesc;
	}
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
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
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}