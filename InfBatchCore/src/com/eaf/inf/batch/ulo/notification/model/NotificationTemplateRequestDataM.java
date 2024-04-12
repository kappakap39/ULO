package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotificationTemplateRequestDataM implements Serializable, Cloneable{
	private Object requestObj;
	private RecipientInfoDataM recipientInfo;
	public Object getRequestObj(){
		return requestObj;
	}
	public void setRequestObj(Object requestObj){
		this.requestObj = requestObj;
	}
	public RecipientInfoDataM getRecipientInfo(){
		return recipientInfo;
	}
	public void setRecipientInfo(RecipientInfoDataM recipientInfo){
		this.recipientInfo = recipientInfo;
	}
}
