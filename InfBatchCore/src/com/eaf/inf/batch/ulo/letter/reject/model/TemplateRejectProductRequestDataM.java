package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class TemplateRejectProductRequestDataM implements Serializable,Cloneable{
	private Object requestObject;
	private Object config;
	private boolean isBundle;
	public Object getRequestObject(){
		return requestObject;
	}
	public void setRequestObject(Object requestObject){
		this.requestObject = requestObject;
	}
	public Object getConfig(){
		return config;
	}
	public void setConfig(Object config){
		this.config = config;
	}
	public boolean isBundle() {
		return isBundle;
	}
	public void setBundle(boolean isBundle) {
		this.isBundle = isBundle;
	}
}
