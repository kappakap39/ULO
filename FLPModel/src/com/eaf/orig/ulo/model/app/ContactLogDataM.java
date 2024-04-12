package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ContactLogDataM implements Serializable,Cloneable{
	public ContactLogDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_CONTACT_LOG.APPLICATION_GROUP_ID(VARCHAR2)
	private String contactLogId;	//ORIG_CONTACT_LOG.CONTACT_LOG_ID(VARCHAR2)
	private String contactType;	//ORIG_CONTACT_LOG.CONTACT_TYPE(VARCHAR2)
	private Date sendDate;	//ORIG_CONTACT_LOG.SEND_DATE(DATE)
	private String sendBy;	//ORIG_CONTACT_LOG.SEND_BY(VARCHAR2)
	private String ccTo;	//ORIG_CONTACT_LOG.CC_TO(VARCHAR2)
	private String templateName;	//ORIG_CONTACT_LOG.TEMPLATE_NAME(VARCHAR2)
	private String sendTo;	//ORIG_CONTACT_LOG.SEND_TO(VARCHAR2)
	private String sendStatus;	//ORIG_CONTACT_LOG.SEND_STATUS(VARCHAR2)
	private String createBy;	//ORIG_CONTACT_LOG.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_CONTACT_LOG.CREATE_DATE(DATE)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getContactLogId() {
		return contactLogId;
	}
	public void setContactLogId(String contactLogId) {
		this.contactLogId = contactLogId;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendBy() {
		return sendBy;
	}
	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	public String getCcTo() {
		return ccTo;
	}
	public void setCcTo(String ccTo) {
		this.ccTo = ccTo;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
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
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
}
