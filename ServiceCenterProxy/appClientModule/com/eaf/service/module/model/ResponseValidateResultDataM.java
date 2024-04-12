package com.eaf.service.module.model;

import java.io.Serializable;

public class ResponseValidateResultDataM implements Serializable,Cloneable{
	public class FIELD_GROUP{
		public static final String PERSONAL="PERSONAL";
		public static final String OTHER_ADDRESS="OTHER_ADDRESS";
		public static final String CONTACT_NUMBER="CONTACT_NUMBER";
		public static final String CONTACT_EMAIL="CONTACT_EMAIL";
	}
	private String fildGroup;
	private String fieldId;
	private String fieldName;
	private String errorCode;
	private String errorDesc;
	private int seq;
	private String personalId;
	
	public String getFildGroup() {
		return fildGroup;
	}
	public void setFildGroup(String fildGroup) {
		this.fildGroup = fildGroup;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
}
