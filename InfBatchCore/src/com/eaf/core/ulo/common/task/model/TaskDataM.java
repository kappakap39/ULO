package com.eaf.core.ulo.common.task.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TaskDataM implements Serializable,Cloneable{
	public TaskDataM(){
		super();
	}
	private String taskId;
	private TaskObjectDataM taskObject;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public TaskObjectDataM getTaskObject() {
		return taskObject;
	}
	public void setTaskObject(TaskObjectDataM taskObject) {
		this.taskObject = taskObject;
	}	
}
