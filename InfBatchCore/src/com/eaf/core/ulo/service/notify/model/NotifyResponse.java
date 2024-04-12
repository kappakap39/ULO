package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NotifyResponse implements Serializable,Cloneable{
	public NotifyResponse(){
		super();
	}
	
	private String statusCode;
	private String statusDesc;
	private ArrayList<NotifyTemplateDataM> notifyTemplateList;
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
	public ArrayList<NotifyTemplateDataM> getNotifyTemplateList() {
		return notifyTemplateList;
	}
	public void setNotifyTemplateList(ArrayList<NotifyTemplateDataM> notifyTemplateList) {
		this.notifyTemplateList = notifyTemplateList;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotifyResponse [statusCode=");
		builder.append(statusCode);
		builder.append(", statusDesc=");
		builder.append(statusDesc);
		builder.append("]");
		return builder.toString();
	}		
}
