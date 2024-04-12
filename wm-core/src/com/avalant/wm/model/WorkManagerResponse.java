package com.avalant.wm.model;

public class WorkManagerResponse {
	String wmFn;
	String refId;
	String statusCode;
	String taskId;
	String statusDesc;
	
	public String getWmFn() {
		return wmFn;
	}
	public void setWmFn(String wmFn) {
		this.wmFn = wmFn;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
