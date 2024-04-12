package com.eaf.core.ulo.common.task.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TaskExecuteDataM implements Serializable,Cloneable{
	public TaskExecuteDataM(){
		super();
	}
	private String uniqueId;
	private String resultCode;
	private String resultDesc;
	private Object responseObject;	
	private Object failObject;
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	public Object getFailObject() {
		return failObject;
	}
	public void setFailObject(Object failObject) {
		this.failObject = failObject;
	}	
}
