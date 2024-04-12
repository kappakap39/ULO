package com.eaf.orig.rest.bpm.model;

import com.orig.bpm.workflow.model.BPMInboxInstance;

public class ULOTask {
	private BPMInboxInstance task;
	private String applicationGroupId;
	private String thName;

	public BPMInboxInstance getTask() {
		return task;
	}

	public void setTask(BPMInboxInstance task) {
		this.task = task;
	}

	public String getApplicationGroupId() {
		return applicationGroupId;
	}

	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}

	public String getThName() {
		return thName;
	}

	public void setThName(String thName) {
		this.thName = thName;
	}

}
