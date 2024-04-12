package com.eaf.core.ulo.service.template.model;

import java.io.Serializable;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.notify.task.NotifyTask;

@SuppressWarnings("serial")
public class TemplateBuilderRequest implements Serializable,Cloneable{
	public TemplateBuilderRequest(){
		super();
		isBundle = false;
	}
	private String templateId;
	private String templateType;	
	private NotifyTask uniqueId;
	private Object requestObject;
	private Object configurationObject;
	private String language;
	private boolean isBundle;
	
	private int dbType = InfBatchServiceLocator.ORIG_DB;
	public String getLanguage(){
		return language;
	}
	public void setLanguage(String language){
		this.language = language;
	}
	public String getTemplateId(){
		return templateId;
	}
	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}
	public String getTemplateType(){
		return templateType;
	}
	public void setTemplateType(String templateType){
		this.templateType = templateType;
	}
	public NotifyTask getUniqueId() {
		if(null==this.uniqueId)this.uniqueId = new NotifyTask();
		return uniqueId;
	}
	public void setUniqueId(NotifyTask uniqueId) {
		this.uniqueId = uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		if(null==this.uniqueId){
			this.uniqueId = new NotifyTask();
		}
		this.uniqueId.setId(uniqueId);
	}
	public Object getRequestObject(){
		return requestObject;
	}
	public void setRequestObject(Object requestObject){
		this.requestObject = requestObject;
	}
	public Object getConfigurationObject(){
		return configurationObject;
	}
	public void setConfigurationObject(Object configurationObject){
		this.configurationObject = configurationObject;
	}
	public int getDbType(){
		return dbType;
	}
	public void setDbType(int dbType){
		this.dbType = dbType;
	}
	public boolean isBundle() {
		return isBundle;
	}
	public void setBungle(boolean isBundle) {
		this.isBundle = isBundle;
	}
}
