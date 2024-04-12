package com.eaf.orig.ulo.email.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class EmailM implements Serializable,Cloneable {
	
	public EmailM() {
		super();
	}
	
	private String emailID;
	private String to;
	private String from;
	private String subject;
	private String content;
	private String contentType;
	private String toInbox;
	private Timestamp createDate;
	private String resentFlag;
	private Timestamp sentDate;
	private String ccTo;
	private String fileDir;
	private String fileName;
	private ArrayList<String> attachments;
	
	
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;	}

	public String getCcTo() {
		return ccTo;
	}
	public void setCcTo(String ccTo) {
		this.ccTo = ccTo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getToInbox() {
		return toInbox;
	}
	public void setToInbox(String toInbox) {
		this.toInbox = toInbox;
	}
 
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public ArrayList<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<String> attachments) {
		this.attachments = attachments;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailM [");
		if (emailID != null) {
			builder.append("emailID=");
			builder.append(emailID);
			builder.append(", ");
		}
		if (to != null) {
			builder.append("to=");
			builder.append(to);
			builder.append(", ");
		}
		if (from != null) {
			builder.append("from=");
			builder.append(from);
			builder.append(", ");
		}
		if (subject != null) {
			builder.append("subject=");
			builder.append(subject);
			builder.append(", ");
		}
		if (content != null) {
			builder.append("content=");
			builder.append(content);
			builder.append(", ");
		}
		if (toInbox != null) {
			builder.append("toInbox=");
			builder.append(toInbox);
			builder.append(", ");
		}
		if (createDate != null) {
			builder.append("createDate=");
			builder.append(createDate);
			builder.append(", ");
		}
		if (resentFlag != null) {
			builder.append("resentFlag=");
			builder.append(resentFlag);
			builder.append(", ");
		}
		if (sentDate != null) {
			builder.append("sentDate=");
			builder.append(sentDate);
			builder.append(", ");
		}
		if (ccTo != null) {
			builder.append("ccTo=");
			builder.append(ccTo);
			builder.append(", ");
		}
		if (fileDir != null) {
			builder.append("fileDir=");
			builder.append(fileDir);
			builder.append(", ");
		}
		if (fileName != null) {
			builder.append("fileName=");
			builder.append(fileName);
		}
		builder.append("]");
		return builder.toString();
	}
	 
}
