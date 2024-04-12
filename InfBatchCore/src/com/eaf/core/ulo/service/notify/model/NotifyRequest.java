package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotifyRequest implements Serializable,Cloneable{	
	private String notifyId;
	private String uniqueId;
	private Object requestObject;	
	public String getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Object getRequestObject() {
		return requestObject;
	}
	public void setRequestObject(Object requestObject) {
		this.requestObject = requestObject;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotifyRequest [");
		if (notifyId != null) {
			builder.append("notifyId=");
			builder.append(notifyId);
			builder.append(", ");
		}
		if (uniqueId != null) {
			builder.append("uniqueId=");
			builder.append(uniqueId);
			builder.append(", ");
		}
		if (requestObject != null) {
			builder.append("requestObject=");
			builder.append(requestObject);
		}
		builder.append("]");
		return builder.toString();
	}	
	
}
