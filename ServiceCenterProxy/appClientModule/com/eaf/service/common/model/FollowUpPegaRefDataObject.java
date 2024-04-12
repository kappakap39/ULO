package com.eaf.service.common.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class FollowUpPegaRefDataObject implements Serializable,Cloneable{
	public static class Action{
		public static final String SERVICE_SUBMIT = "ServiceSubmit";
	}
	public static class FollowUpGroup{
		public static final String DUMMY = "DUMMY";
		public static final String FOLLOW = "FOLLOW";
	}
	@Expose
	private String action;
	@Expose
	private String followUpGroup;
	@Expose
	private int lifeCycle = 1;
	@Expose
	private String taskId;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getFollowUpGroup() {
		return followUpGroup;
	}
	public void setFollowUpGroup(String followUpGroup) {
		this.followUpGroup = followUpGroup;
	}	
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FollowUpPegaRefDataObject [");
		if (action != null) {
			builder.append("action=");
			builder.append(action);
			builder.append(", ");
		}
		if (followUpGroup != null) {
			builder.append("followUpGroup=");
			builder.append(followUpGroup);
			builder.append(", ");
		}
		builder.append("lifeCycle=");
		builder.append(lifeCycle);
		builder.append("]");
		return builder.toString();
	}		
}
