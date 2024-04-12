package com.eaf.orig.ulo.email.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.eaf.notify.task.NotifyTask;

@SuppressWarnings("serial")
public class EmailRequest implements Serializable,Cloneable{	
	public EmailRequest() {
		super();
	}	
	private String templateId;
	private String templateName;
	private String emailId;
	private String from;
	private String fileName;
	private String[] to;
	private String[] ccTo;
	private String subject;
	private String content;
	private String contentType;
	private String toInbox;
	private Timestamp createDate;
	private String resentFlag;
	private Timestamp sentDate;
	private ArrayList<String> attachments;
	private NotifyTask uniqueId;
	private List<NotifyTask> uniqueIds;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public void setTo(String to){
		if(null != to){
			this.to = to.split("\\,");
		}
	}
	public String[] getCcTo() {
		return ccTo;
	}
	public void setCcTo(String ccTo) {
		if(null != ccTo){
			this.ccTo = ccTo.split("\\,");
		}
	}
	public void setCcTo(String[] ccTo) {
		this.ccTo = ccTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getToInbox() {
		return toInbox;
	}
	public void setToInbox(String toInbox) {
		this.toInbox = toInbox;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getResentFlag() {
		return resentFlag;
	}
	public void setResentFlag(String resentFlag) {
		this.resentFlag = resentFlag;
	}
	public Timestamp getSentDate() {
		return sentDate;
	}
	public void setSentDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}
	public ArrayList<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<String> attachments) {
		this.attachments = attachments;
	}
	public void add(String attachment){
		if(null == attachments){
			this.attachments = new ArrayList<String>();
		}
		this.attachments.add(attachment);
	}	
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
	
	public String getEmailToString() {
		StringBuilder  emails = new StringBuilder("");
		if(null!=to){
			String COMMA="";
			for(String email :to){
				if(!emails.toString().contains(email)){
					emails.append(COMMA+email);
					COMMA=",";
				}	
			}
		}
		return emails.toString();
	}
	public String getCCemailToString() {
		StringBuilder  ccEmails = new StringBuilder("");
		if(null!=ccTo){
			String COMMA="";
			for(String cc :ccTo){
				if(!ccEmails.toString().contains(cc)){
					ccEmails.append(COMMA+cc);
					COMMA=",";
				}
			}
		}
		return ccEmails.toString();
	}
	public NotifyTask getUniqueId(){
		if(null==this.uniqueId)this.uniqueId = new NotifyTask();
		return uniqueId;
	}
	public void setUniqueId(NotifyTask uniqueId) {
		this.uniqueId = uniqueId;
	}
	public void setUniqueIds(List<NotifyTask> uniqueIds) {
		this.uniqueIds = uniqueIds;
	}
	public List<NotifyTask> getUniqueIds() {
		return uniqueIds;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nEmailRequest");
		builder.append("\n emailId=");
		builder.append(emailId);
		builder.append("\n from=");
		builder.append(from);
		builder.append("\n fromName=");
		builder.append(fileName);
		builder.append("\n to=");
		builder.append(to);
		builder.append("\n ccTo=");
		builder.append(ccTo);
		builder.append("\n subject=");
		builder.append(subject);
		builder.append("\n content=");
		builder.append(content);
		builder.append("\n contentType=");
		builder.append(contentType);
		builder.append("\n toInbox=");
		builder.append(toInbox);
		builder.append("\n createDate=");
		builder.append(createDate);
		builder.append("\n resentFlag=");
		builder.append(resentFlag);
		builder.append("\n sentDate=");
		builder.append(sentDate);
		builder.append("\n attachments=");
		builder.append(attachments);
		builder.append("\n uniqueId=");
		builder.append(uniqueId);
		builder.append("\n uniqueIds=");
		builder.append(uniqueIds);
		builder.append("\nEnd");
		return builder.toString();
	}
}
