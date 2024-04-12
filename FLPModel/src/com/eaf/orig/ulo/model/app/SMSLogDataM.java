package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class SMSLogDataM implements Serializable,Cloneable{
	public SMSLogDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_SMS_LOG.APPLICATION_GROUP_ID(VARCHAR2)
	private String smsLogId;	//ORIG_SMS_LOG.SMS_LOG_ID(VARCHAR2)
	private String message;	//ORIG_SMS_LOG.MESSAGE(VARCHAR2)
	private String sendBy;	//ORIG_SMS_LOG.SEND_BY(VARCHAR2)
	private String sendStatus;	//ORIG_SMS_LOG.SEND_STATUS(VARCHAR2)
	private String sendTo;	//ORIG_SMS_LOG.SEND_TO(VARCHAR2)
	private String sendNo;	//ORIG_SMS_LOG.SEND_NO(VARCHAR2)
	private Date sendDate;	//ORIG_SMS_LOG.SEND_DATE(DATE)
	private String createBy;	//ORIG_SMS_LOG.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_SMS_LOG.CREATE_DATE(DATE)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getSmsLogId() {
		return smsLogId;
	}
	public void setSmsLogId(String smsLogId) {
		this.smsLogId = smsLogId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSendBy() {
		return sendBy;
	}
	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getSendNo() {
		return sendNo;
	}
	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
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
