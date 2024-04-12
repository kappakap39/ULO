package com.eaf.orig.ulo.email.model;

import java.io.Serializable;

public class EmailResponse implements Serializable,Cloneable{
	public EmailResponse(){
		super();
	}
	public class Status{
		public static final String SUCCESS = "0";
		public static final String FAIL = "1";
		public static final String WARNING = "2";
	}
	private String statusCode;
	private String statusDesc;
	private String invalidEmail;
	private String sendFailEmail;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getInvalidEmail() {
		return invalidEmail;
	}
	public void setInvalidEmail(String invalidEmail) {
		this.invalidEmail = invalidEmail;
	}
	public String getSendFailEmail() {
		return sendFailEmail;
	}
	public void setSendFailEmail(String sendFailEmail) {
		this.sendFailEmail = sendFailEmail;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailResponse [statusCode=");
		builder.append(statusCode);
		builder.append(", statusDesc=");
		builder.append(statusDesc);
		builder.append(", invalidEmail=");
		builder.append(invalidEmail);
		builder.append(", sendFailEmail=");
		builder.append(sendFailEmail);
		builder.append("]");
		return builder.toString();
	}		
}
