package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotificationInfoRequestDataM implements Serializable,Cloneable{
	public NotificationInfoRequestDataM(){
		super();
	}
	
	private String applicationGroupId;
	private String applicationStatus;
	private String sendingTime;
	private String SaleType;
	private int lifeCycle;
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getSendingTime() {
		return sendingTime;
	}
	public void setSendingTime(String sendingTime) {
		this.sendingTime = sendingTime;
	}
	public String getSaleType() {
		return SaleType;
	}
	public void setSaleType(String saleType) {
		SaleType = saleType;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
}
