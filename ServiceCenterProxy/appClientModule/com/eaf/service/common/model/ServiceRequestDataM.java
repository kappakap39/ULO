package com.eaf.service.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ServiceRequestDataM implements Serializable,Cloneable{
	public ServiceRequestDataM(){
		super();
	}
	private String serviceId;
	private String transactionId;
	private String serviceReqResId;
	private String uniqueId;
	private String refId;
	private int stepId;
	private String userId;
	private Timestamp requestTime;
	private String endpointUrl;
	private HashMap<String,Object> rawData;
	private Object objectData;
	private boolean ignoreServiceLog = false;
	private Object serviceData;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}	
	public HashMap<String, Object> getRawData() {
		return rawData;
	}
	public void setRawData(HashMap<String, Object> rawData) {
		this.rawData = rawData;
	}
	public void putRawData(String key,Object value){
		if(null == rawData){
			rawData = new HashMap<>();
		}
		rawData.put(key,value);
	}
	public Object getRawData(String key){
		if(null == rawData){
			rawData = new HashMap<>();
		}
		return rawData.get(key);
	}
	public String getServiceReqResId() {
		return serviceReqResId;
	}
	public void setServiceReqResId(String serviceReqResId) {
		this.serviceReqResId = serviceReqResId;
	}
	public String getEndpointUrl() {
		return endpointUrl;
	}
	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Object getObjectData() {
		return objectData;
	}
	public void setObjectData(Object objectData) {
		this.objectData = objectData;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public boolean isIgnoreServiceLog() {
		return ignoreServiceLog;
	}
	public void setIgnoreServiceLog(boolean ignoreServiceLog) {
		this.ignoreServiceLog = ignoreServiceLog;
	}
	public Object getServiceData() {
		return serviceData;
	}
	public void setServiceData(Object serviceData) {
		this.serviceData = serviceData;
	}

}
