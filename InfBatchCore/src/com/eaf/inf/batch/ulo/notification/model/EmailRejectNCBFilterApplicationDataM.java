package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EmailRejectNCBFilterApplicationDataM implements Serializable, Cloneable{
	public EmailRejectNCBFilterApplicationDataM(){
		
	}
	private String applicationGroupId;
	private String applicationTemplate;
	private String saleChannel;
	private String recommendChannel;
	private String appStatus;;
	private int lifeCycle;
	
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationTemplate(){
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate){
		this.applicationTemplate = applicationTemplate;
	}
	public String getSaleChannel() {
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}
	public String getRecommendChannel() {
		return recommendChannel;
	}
	public void setRecommendChannel(String recommendChannel) {
		this.recommendChannel = recommendChannel;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
}
