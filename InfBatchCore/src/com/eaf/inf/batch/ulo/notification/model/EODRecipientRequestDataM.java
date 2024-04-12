package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationEODConfigDataM;

@SuppressWarnings("serial")
public class EODRecipientRequestDataM implements Serializable, Cloneable{
	public EODRecipientRequestDataM(){
		
	}
	List<NotificationEODDataM> notificationEods = new ArrayList<NotificationEODDataM>();;
	List<String> emails = new ArrayList<String>();
	NotificationEODConfigDataM notificationEODConfig = new NotificationEODConfigDataM();
	public List<NotificationEODDataM> getNotificationEods(){
		return notificationEods;
	}
	public void setNotificationEods(List<NotificationEODDataM> notificationEods){
		this.notificationEods = notificationEods;
	}
	public List<String> getEmails(){
		return emails;
	}
	public void setEmails(List<String> emails){
		this.emails = emails;
	}
	public NotificationEODConfigDataM getNotificationEODConfig() {
		return notificationEODConfig;
	}
	public void setNotificationEODConfig(
			NotificationEODConfigDataM notificationEODConfig) {
		this.notificationEODConfig = notificationEODConfig;
	}
}
	
