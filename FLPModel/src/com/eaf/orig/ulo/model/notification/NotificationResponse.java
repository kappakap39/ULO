package com.eaf.orig.ulo.model.notification;

public class NotificationResponse {
	public NotificationResponse(){
		super();
	}
	private String statusCode;
	private String statusDesc;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationResponse [statusCode=");
		builder.append(statusCode);
		builder.append(", statusDesc=");
		builder.append(statusDesc);
		builder.append("]");
		return builder.toString();
	}	
}
