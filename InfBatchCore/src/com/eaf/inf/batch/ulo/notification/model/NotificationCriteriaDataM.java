package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationCriteriaDataM implements Serializable, Cloneable{
	private String applicationTemplate;
	private String reasonCode;
	private String sendTime;
	private String cashTransferType;
	private String notificationType;
	private String channelCode;
	private String applicationStatus;
	private ArrayList<String> sendToList;
	public String getApplicationTemplate(){
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate){
		this.applicationTemplate = applicationTemplate;
	}
	public String getReasonCode(){
		return reasonCode;
	}
	public void setReasonCode(String reasonCode){
		this.reasonCode = reasonCode;
	}
	public String getSendTime(){
		return sendTime;
	}
	public void setSendTime(String sendTime){
		this.sendTime = sendTime;
	}
	public String getCashTransferType() {
		return cashTransferType;
	}
	public void setCashTransferType(String cashTransferType) {
		this.cashTransferType = cashTransferType;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public ArrayList<String> getSendToList(){
		return sendToList;
	}
	public void setSendToList(ArrayList<String> sendToList){
		this.sendToList = sendToList;
	}
	public void addSendTo(String sendTo){
		if(null == this.sendToList){
			this.sendToList = new ArrayList<String>();
		}
		this.sendToList.add(sendTo);
	}
}
