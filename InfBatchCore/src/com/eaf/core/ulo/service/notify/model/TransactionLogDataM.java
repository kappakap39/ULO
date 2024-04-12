package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class TransactionLogDataM implements Serializable,Cloneable {

	public TransactionLogDataM(){
		super();
	}
	
	private String applicationgroupId;
	private int lifeCycle;
	public String getApplicationgroupId() {
		return applicationgroupId;
	}
	public void setApplicationgroupId(String applicationgroupId) {
		this.applicationgroupId = applicationgroupId;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransactionLogDataM [");
		if (applicationgroupId != null) {
			builder.append("applicationgroupId=");
			builder.append(applicationgroupId);
			builder.append(", ");
		}
		builder.append("lifeCycle=");
		builder.append(lifeCycle);
		builder.append("]");
		return builder.toString();
	}
	
}
