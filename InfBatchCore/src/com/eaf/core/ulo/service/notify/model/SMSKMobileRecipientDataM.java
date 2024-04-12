package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SMSKMobileRecipientDataM implements Serializable,Cloneable{
	public SMSKMobileRecipientDataM(){
		super();
	}
	private String recipientType;
	private String recipientName;
	private String mobileNo;
	private Object recipientObject;
	private String language; 
	private ArrayList<String> templates;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Object getRecipientObject() {
		return recipientObject;
	}
	public void setRecipientObject(Object recipientObject) {
		this.recipientObject = recipientObject;
	}
	public ArrayList<String> getTemplates() {
		return templates;
	}
	public void setTemplates(ArrayList<String> templates) {
		this.templates = templates;
	}	
	public void addTemplates(String templateId) {
		if(null==templates){
			templates = new ArrayList<String>();
		}
		templates.add(templateId);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SMSKMobileRecipientDataM [");
		if (recipientType != null) {
			builder.append("recipientType=");
			builder.append(recipientType);
			builder.append(", ");
		}
		if (recipientName != null) {
			builder.append("recipientName=");
			builder.append(recipientName);
			builder.append(", ");
		}
		if (mobileNo != null) {
			builder.append("mobileNo=");
			builder.append(mobileNo);
			builder.append(", ");
		}
		if (recipientObject != null) {
			builder.append("recipientObject=");
			builder.append(recipientObject);
			builder.append(", ");
		}
		if (language != null) {
			builder.append("language=");
			builder.append(language);
			builder.append(", ");
		}
		if (templates != null) {
			builder.append("templates=");
			builder.append(templates);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
