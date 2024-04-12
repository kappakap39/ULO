package com.avalant.wm.model;

import java.io.Serializable;

public class WmTaskData implements Cloneable, Serializable {

	private String taskId;
	private String taskData;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskData() {
		return taskData;
	}
	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}
	
}
