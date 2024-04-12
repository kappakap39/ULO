package com.eaf.core.ulo.common.rest;

import java.io.Serializable;

import org.json.JSONObject;

public class RESTResponse implements Serializable,Cloneable{
	public RESTResponse(){
		super();
	}
	private String statusCode;
	private String statusDesc;
	private JSONObject jsonResponse;
	private Object responseObject;
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
	public JSONObject getJsonResponse() {
		return jsonResponse;
	}
	public void setJsonResponse(JSONObject jsonResponse) {
		this.jsonResponse = jsonResponse;
	}	
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RESTResponse [statusCode=");
		builder.append(statusCode);
		builder.append(", statusDesc=");
		builder.append(statusDesc);
		builder.append("]");
		return builder.toString();
	}	
}
