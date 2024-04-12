package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotificationTemplateResponseDataM implements Serializable, Cloneable{
	private Object responseObj;
	public Object getResponseObj(){
		return responseObj;
	}
	public void setResponseObj(Object responseObj){
		this.responseObj = responseObj;
	}
	
}
