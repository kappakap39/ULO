package com.avalant.wm.model;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class WmTask implements Cloneable, Serializable {

	private String taskId;
	private String wmFunc;
	private String serverName;
	private String taskData;
	private String refId;
	private String refCode;
	private int errorCount = 0;
	private int errorTotal = 0;
	private String status;
	private Timestamp createTime;
	private Timestamp lastRuntime;
	private WmTaskData data;
	
	public String getWmFunc() {
		return wmFunc;
	}
	public void setWmFunc(String wmFunc) {
		this.wmFunc = wmFunc;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getTaskData() {
		return taskData;
	}
	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public int getErrorTotal() {
		return errorCount;
	}
	public void setErrorTotal(int errorTotal) {
		this.errorTotal = errorTotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public WmTaskData getData() {
		return data;
	}
	public void setData(WmTaskData data) {
		this.data = data;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastRuntime() {
		return lastRuntime;
	}
	public void setLastRuntime(Timestamp lastRuntime) {
		this.lastRuntime = lastRuntime;
	}
}
