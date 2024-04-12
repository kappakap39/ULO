package com.eaf.inf.batch.ulo.report.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TaskReportData<T> implements Serializable, Cloneable {
	String reportTaskId;
	List<T> reportData;
	String roleId;
	String position;
	String appType;

	public TaskReportData(String reportTaskId) {
		this.reportTaskId = reportTaskId;
	}

	public TaskReportData(String reportTaskId, InfReportJobDataM reportJob) {
		this.reportTaskId = reportTaskId;
		this.reportJob = reportJob;
		this.reportJobFlag = true;
	}

	public TaskReportData(String reportTaskId, String appType,InfReportJobDataM reportJob) {
		this.reportTaskId = reportTaskId;
		this.reportJob = reportJob;
		this.appType = appType;
		this.reportJobFlag = true;
	}

	boolean reportJobFlag = false;
	InfReportJobDataM reportJob;

	public String getReportTaskId(){
		return this.reportTaskId;
	}
	public void setReportTaskId(String reportTaskId){
		this.reportTaskId = reportTaskId;
	}
	public List<T> getReportData(){
		return this.reportData;
	}
	public void setReportData(List<T> reportData){
		this.reportData = reportData;
	}
	public InfReportJobDataM getReportJob(){
		return this.reportJob;
	}
	public void setReportJob(InfReportJobDataM reportJob){
		this.reportJob = reportJob;
	}
	public boolean isReportJobFlag() {
		return this.reportJobFlag;
	}
	public void setReportJobFlag(boolean reportJobFlag){
		this.reportJobFlag = reportJobFlag;
	}
	public String getRoleId(){
		return this.roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAppType() {
		return this.appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
}
