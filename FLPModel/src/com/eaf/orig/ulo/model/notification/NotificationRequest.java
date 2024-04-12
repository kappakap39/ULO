package com.eaf.orig.ulo.model.notification;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotificationRequest implements Serializable,Cloneable{
	public NotificationRequest () {
		super();
	}
	private String applicationGroupId;
	private String status;
	private String sendTime;
	private String saleType;
	private int lifeCycle = 1;
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
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
		builder.append("NotificationRequest [");
		if (applicationGroupId != null) {
			builder.append("applicationGroupId=");
			builder.append(applicationGroupId);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (sendTime != null) {
			builder.append("sendTime=");
			builder.append(sendTime);
			builder.append(", ");
		}
		if (saleType != null) {
			builder.append("saleType=");
			builder.append(saleType);
			builder.append(", ");
		}
		builder.append("lifeCycle=");
		builder.append(lifeCycle);
		builder.append("]");
		return builder.toString();
	}
	
}
