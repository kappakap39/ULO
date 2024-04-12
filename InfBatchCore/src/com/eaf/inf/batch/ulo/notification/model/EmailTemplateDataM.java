package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;

public class EmailTemplateDataM implements Serializable,Cloneable{
	public EmailTemplateDataM(){
		super();
	}
	
	private String templateId;
	private String templateName;
	private String content;
	private String busGrpId;
	private String subject;
	private String templateType;
	private ArrayList<String> emailElement;
	private ArrayList<String> ccEmailElement;
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBusGrpId() {
		return busGrpId;
	}
	public void setBusGrpId(String busGrpId) {
		this.busGrpId = busGrpId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public ArrayList<String> getEmailElement() {
		return emailElement;
	}
	public void setEmailElement(ArrayList<String> emailElement) {
		this.emailElement = emailElement;
	}
	public ArrayList<String> getCcEmailElement() {
		return ccEmailElement;
	}
	public void setCcEmailElement(ArrayList<String> ccEmailElement) {
		this.ccEmailElement = ccEmailElement;
	}		
	public void addCCEmailElement(String ccEmail) {
		if(null==ccEmailElement){
			ccEmailElement = new ArrayList<String>();
		}
		ccEmailElement.add(ccEmail);
	}
	public void addEmailElement(String email) {
		if(null==emailElement){
			emailElement = new ArrayList<String>();
		}
		emailElement.add(email);
	}
	public String ccEmaileToString() {
		StringBuilder ccEmailStr = new StringBuilder("");
		if(null!=ccEmailElement){
			String COMMA=",";
			for(String ccEmail :ccEmailElement ){
				ccEmailStr.append(COMMA+ccEmail);
				COMMA =",";
			}
		}
		return ccEmailStr.toString();
	}
	public String emaileToString() {
		StringBuilder emailStr = new StringBuilder("");
		if(null!=emailElement){
			String COMMA="";
			for(String email :emailElement ){
				emailStr.append(COMMA+email);
				COMMA =",";
			}
		}
		return emailStr.toString();
	}
}
