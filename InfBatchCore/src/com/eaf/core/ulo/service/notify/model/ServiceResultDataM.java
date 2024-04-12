package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceResultDataM implements Serializable,Cloneable{
	public ServiceResultDataM(){
		super();
	}
	public static final String SEND = "S";
	public static final String NO_ACTION = "N";
	private String action;
	private String templateType;
	private Object requestObject;
	private Object responseObject;
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public Object getRequestObject() {
		return requestObject;
	}
	public void setRequestObject(Object requestObject) {
		this.requestObject = requestObject;
	}
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
