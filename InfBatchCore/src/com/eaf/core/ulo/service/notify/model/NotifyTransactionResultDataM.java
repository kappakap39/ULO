package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.notify.task.NotifyTask;

@SuppressWarnings("serial")
public class NotifyTransactionResultDataM implements Serializable,Cloneable{
	public NotifyTransactionResultDataM(){
		super();
	}
	private NotifyTask uniqueId;
	private Object transactionObject;
	private Object configurationObject;
	private ArrayList<ServiceResultDataM> serviceResults = new ArrayList<ServiceResultDataM>();
	
	public NotifyTask getUniqueId() {
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
	public void setUniqueId(String uniqueId,int lifeCycle) {
		if(null==this.uniqueId){
			this.uniqueId = new NotifyTask();
		}
		this.uniqueId.setId(uniqueId);
		this.uniqueId.setLifeCycle(lifeCycle);
	}
	public Object getTransactionObject() {
		return transactionObject;
	}
	public void setTransactionObject(Object transactionObject) {
		this.transactionObject = transactionObject;
	}
	public Object getConfigurationObject() {
		return configurationObject;
	}
	public void setConfigurationObject(Object configurationObject) {
		this.configurationObject = configurationObject;
	}
	public ArrayList<ServiceResultDataM> getServiceResults() {
		return serviceResults;
	}
	public void setServiceResults(ArrayList<ServiceResultDataM> serviceResults) {
		this.serviceResults = serviceResults;
	}
	public void add(ServiceResultDataM serviceResult){
		serviceResults.add(serviceResult);
	}
	public ServiceResultDataM getServiceResult(){		
		return (serviceResults.size() > 0)?serviceResults.get(0):null;
	}
}
