package com.avalant.wm.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WmTaskLog implements Cloneable, Serializable {

	private String taskId;
	private String taskErrorId;
	private String task;
	private String respCode;
	private String errorMsg;
	private String msg;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskErrorId() {
		return taskErrorId;
	}
	public void setTaskErrorId(String taskErrorId) {
		this.taskErrorId = taskErrorId;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
