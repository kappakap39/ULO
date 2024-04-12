package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;

public class CVRSValidationResultDataM implements Serializable,Cloneable{
	public CVRSValidationResultDataM(){
		super();
	}
	public class FIELD_GROUP{
		public static final String PERSONAL="PERSONAL";
		public static final String OTHER_ADDRESS="OTHER_ADDRESS";
		public static final String CONTACT_NUMBER="CONTACT_NUMBER";
		public static final String CONTACT_EMAIL="CONTACT_EMAIL";
	}
	private String resultId;
	private String personalId;
	private String fieldGroup;
	private String fieldId;
	private String fieldName;
	private String errorCode;
	private String errorDesc;
	private Date   createDate;
	private String createBy;
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getFieldGroup() {
		return fieldGroup;
	}
	public void setFieldGroup(String fieldGroup) {
		this.fieldGroup = fieldGroup;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}	
}
