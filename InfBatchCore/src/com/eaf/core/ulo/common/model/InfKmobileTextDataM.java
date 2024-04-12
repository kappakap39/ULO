package com.eaf.core.ulo.common.model;

import java.io.Serializable;

public class InfKmobileTextDataM implements Serializable,Cloneable{
	
	public InfKmobileTextDataM(){
		super();
	}
	
	private String sendBy;
	private String customer;//[MobileNO/CisID/CitizenID]
	private String messageTH;
	private String messageEN;
	private String alertMessageTH;
	private String alertMessageEN;
	private String imageTH;
	private String imageEN;
	private String sendFlag;
	private String schedule;
	private String pageCode;
	private String messageName;
	private String campaignName;
	public String getSendBy() {
		return sendBy;
	}
	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getMessageTH() {
		return messageTH;
	}
	public void setMessageTH(String messageTH) {
		this.messageTH = messageTH;
	}
	public String getMessageEN() {
		return messageEN;
	}
	public void setMessageEN(String messageEN) {
		this.messageEN = messageEN;
	}
	public String getAlertMessageTH() {
		return alertMessageTH;
	}
	public void setAlertMessageTH(String alertMessageTH) {
		this.alertMessageTH = alertMessageTH;
	}
	public String getAlertMessageEN() {
		return alertMessageEN;
	}
	public void setAlertMessageEN(String alertMessageEN) {
		this.alertMessageEN = alertMessageEN;
	}
	public String getImageTH() {
		return imageTH;
	}
	public void setImageTH(String imageTH) {
		this.imageTH = imageTH;
	}
	public String getImageEN() {
		return imageEN;
	}
	public void setImageEN(String imageEN) {
		this.imageEN = imageEN;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getPageCode() {
		return pageCode;
	}
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
	
	
}
