package com.eaf.orig.ulo.model.json;

import java.io.Serializable;

public class ElementFormDataM implements Serializable,Cloneable{
	public ElementFormDataM(){
		super();
	}
	private String elementId;
	private String elementValue;
	private String elementParam;
	private String formName = "";
	private String errorCode = "";
	private String messageType = "";
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getElementValue() {
		return elementValue;
	}
	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getElementParam() {
		return elementParam;
	}
	public void setElementParam(String elementParam) {
		this.elementParam = elementParam;
	}	
}
