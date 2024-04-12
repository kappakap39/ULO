package com.avalant.wm.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class WorkManagerRequest implements Serializable,Cloneable{
	@JsonProperty("wmFn")
	String wmFn; //WorkManager Function Name
	
	@JsonProperty("refId")
	String refId; //RefId
	
	@JsonProperty("taskData")
	String taskData; //Json String
	
	@JsonProperty("refCode")
	String refCode;
	
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
	public String getTaskData() {
		return taskData;
	}
	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkManagerRequest [wmFn=");
		builder.append(wmFn);
		builder.append(", refId=");
		builder.append(refId);
		builder.append(", taskData=");
		builder.append(taskData);
		builder.append(", refCode=");
		builder.append(refCode);
		builder.append("]");
		return builder.toString();
	}
	
}
