package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class EmailRecipientDataM implements Serializable,Cloneable{
	public EmailRecipientDataM(){
		super();
	}
	private String recipientType;
	private String recipientName;
	private String email;
	private String ccEmail;
	private Object recipientObject;
	private ArrayList<String> templates;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCcEmail() {
		return ccEmail;
	}
	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
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
	public void addTemplates(String template) {
		if(null==templates){
			templates = new ArrayList<String>();
		}
		templates.add(template);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailRecipientDataM [");
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
		if (email != null) {
			builder.append("email=");
			builder.append(email);
			builder.append(", ");
		}
		if (ccEmail != null) {
			builder.append("ccEmail=");
			builder.append(ccEmail);
			builder.append(", ");
		}
		if (recipientObject != null) {
			builder.append("recipientObject=");
			builder.append(recipientObject);
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
