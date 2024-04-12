package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;

@SuppressWarnings("serial")
public class NotifyTemplateDataM implements Serializable,Cloneable{
	public NotifyTemplateDataM(){
		super();
	}
	private String templateId;
	private String templateType;
	private String nationality;
	private Object templateObject;
	private int dbType = InfBatchServiceLocator.ORIG_DB;

	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public int getDbType() {
		return dbType;
	}
	public void setDbType(int dbType) {
		this.dbType = dbType;
	}
	public Object getTemplateObject() {
		return templateObject;
	}
	public void setTemplateObject(Object templateObject) {
		this.templateObject = templateObject;
	}	
}
