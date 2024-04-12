package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SMSTemplateDataM implements Serializable,Cloneable {
	public SMSTemplateDataM(){
		super();
	}	
	private String templatId;
	private String templatName;
	private String bussClass;
	private String messageEn;
	private String messageTh;
	public String getTemplatId() {
		return templatId;
	}
	public void setTemplatId(String templatId) {
		this.templatId = templatId;
	}
	public String getTemplatName() {
		return templatName;
	}
	public void setTemplatName(String templatName) {
		this.templatName = templatName;
	}
	public String getBussClass() {
		return bussClass;
	}
	public void setBussClass(String bussClass) {
		this.bussClass = bussClass;
	}
	public String getMessageEn() {
		return messageEn;
	}
	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}
	public String getMessageTh() {
		return messageTh;
	}
	public void setMessageTh(String messageTh) {
		this.messageTh = messageTh;
	}
}
