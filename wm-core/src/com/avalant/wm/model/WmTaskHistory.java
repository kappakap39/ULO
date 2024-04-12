package com.avalant.wm.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WmTaskHistory implements Cloneable, Serializable {

	private String taskId;
	private String wmFunc;
	private String taskData;
	private String refId;
	private String refCode;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getWmFunc() {
		return wmFunc;
	}
	public void setWmFunc(String wmFunc) {
		this.wmFunc = wmFunc;
	}
	public String getTaskData() {
		return taskData;
	}
	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
}
