package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SMSRequestDataM implements Serializable,Cloneable{
	private ArrayList<String> mobileNoElement = new ArrayList<String>();
	private String msg;
	private int templateId;
	private String smsLanguage;
	private String departmentCode;
	private int priority;
	private String messageType;
	private String clientId;
	
	public ArrayList<String> getMobileNoElement() {
		return mobileNoElement;
	}
	public void setMobileNoElement(ArrayList<String> mobileNoElement) {
		this.mobileNoElement = mobileNoElement;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getSmsLanguage() {
		return smsLanguage;
	}
	public void setSmsLanguage(String smsLanguage) {
		this.smsLanguage = smsLanguage;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
