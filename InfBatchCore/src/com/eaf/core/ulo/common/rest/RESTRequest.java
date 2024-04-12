package com.eaf.core.ulo.common.rest;

import java.io.Serializable;

import org.restlet.data.Method;

public class RESTRequest implements Serializable,Cloneable{
	public RESTRequest(String url){
		this.url = url;
	}
	public RESTRequest(String url,Object requestObject){
		this.url = url;
		this.requestObject = requestObject;
	}
	private String url;
	private Method method = Method.PUT;
	private Object requestObject;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object getRequestObject() {
		return requestObject;
	}
	public void setRequestObject(Object requestObject) {
		this.requestObject = requestObject;
	}	
}
