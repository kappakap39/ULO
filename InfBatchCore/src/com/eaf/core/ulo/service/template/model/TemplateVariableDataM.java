package com.eaf.core.ulo.service.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eaf.notify.task.NotifyTask;

@SuppressWarnings("serial")
public class TemplateVariableDataM implements Serializable,Cloneable{
	public TemplateVariableDataM(){
		super();
	}
	private ArrayList<String> attachments;
	private Map<String,Object> templateVariable = new HashMap<String, Object>();
	private Map<String,Object> sortingVariable = new HashMap<String, Object>();
	private Object variableObject;
	private NotifyTask uniqueId;
	private List<NotifyTask> uniqueIds;
	public Map<String, Object> getTemplateVariable() {
		return templateVariable;
	}
	public void setTemplateVariable(Map<String, Object> templateVariable) {
		this.templateVariable = templateVariable;
	}
	public Map<String, Object> getSortingVariable() {
		return sortingVariable;
	}
	public void setSortingVariable(Map<String, Object> sortingVariable) {
		this.sortingVariable = sortingVariable;
	}
	public Object getVariableObject() {
		return variableObject;
	}
	public void setVariableObject(Object variableObject) {
		this.variableObject = variableObject;
	}	
	public void put(String keyValue,String objectValue){
		templateVariable.put(keyValue,objectValue);
	}
	public ArrayList<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<String> attachments) {
		this.attachments = attachments;
	}
	public NotifyTask getUniqueId() {
		if(null==this.uniqueId)this.uniqueId = new NotifyTask();
		return uniqueId;
	}
	public void setUniqueId(NotifyTask uniqueId) {
		this.uniqueId = uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		if(null==this.uniqueId)this.uniqueId = new NotifyTask();
		this.uniqueId.setId(uniqueId);
	}
	public List<NotifyTask> getUniqueIds() {
		return uniqueIds;
	}
	public void setUniqueIds(List<NotifyTask> uniqueIds) {
		this.uniqueIds = uniqueIds;
	}
}
