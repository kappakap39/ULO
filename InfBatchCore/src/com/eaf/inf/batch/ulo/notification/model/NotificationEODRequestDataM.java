package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;

@SuppressWarnings("serial")
public class NotificationEODRequestDataM implements Serializable,Cloneable{
	public NotificationEODRequestDataM(){
		
	}
	private HashMap<String, ArrayList<NotificationEODDataM>> notificationData;
	private HashMap<String,ArrayList<String>> reasonData;
	HashMap<String, ArrayList<VCEmpInfoDataM>> managerData;
	public HashMap<String, ArrayList<NotificationEODDataM>> getNotificationData() {
		return notificationData;
	}
	public void setNotificationData(
			HashMap<String, ArrayList<NotificationEODDataM>> notificationData) {
		this.notificationData = notificationData;
	}
	public HashMap<String, ArrayList<String>> getReasonData() {
		return reasonData;
	}
	public void setReasonData(HashMap<String, ArrayList<String>> reasonData) {
		this.reasonData = reasonData;
	}
	public HashMap<String, ArrayList<VCEmpInfoDataM>> getManagerData() {
		return managerData;
	}
	public void setManagerData(
			HashMap<String, ArrayList<VCEmpInfoDataM>> managerData) {
		this.managerData = managerData;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationEODRequestDataM [notificationData=");
		builder.append(notificationData);
		builder.append(", reasonData=");
		builder.append(reasonData);
		builder.append("]");
		return builder.toString();
	}
	
}
