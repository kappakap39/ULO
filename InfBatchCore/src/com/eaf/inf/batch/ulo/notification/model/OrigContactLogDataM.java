package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class OrigContactLogDataM implements Serializable,Cloneable{
	public OrigContactLogDataM(){
		super();
	}
	private String contactLogId;
	private String sendTo;//SEND_TO
	private String sendBy;//SEND_BY
	private Timestamp sendDate;// SEND_DATE
	private String sendStatus;//SEND_STATUS
	private String createBy;//CREATE_BY
	private String contactType;//CONTACT_TYPE
	private String templateName;//TEMPLATE_NAME
	private String ccTo;//CC_TO
	private String message;//MESSAGE
	private String subject;//SUBJECT
	private String applicationGroupId;//APPLICATION_GROUP_ID
	private int lifeCycle;
	
	public String getContactLogId() {
		return contactLogId;
	}
	public void setContactLogId(String contactLogId) {
		this.contactLogId = contactLogId;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getSendBy() {
		return sendBy;
	}
	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	public Timestamp getSendDate() {
		return sendDate;
	}
	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getCcTo() {
		return ccTo;
	}
	public void setCcTo(String ccTo) {
		this.ccTo = ccTo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
}
