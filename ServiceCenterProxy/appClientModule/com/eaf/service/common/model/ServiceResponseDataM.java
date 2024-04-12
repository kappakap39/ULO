package com.eaf.service.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ServiceResponseDataM implements Serializable,Cloneable {
	public ServiceResponseDataM(){
		super();
	}
	private String serviceId;
	private String transactionId;
	private String serviceReqResId;
	private int stepId;
	private String userId;
	private String statusCode;
	private Timestamp requestTime;
	private Timestamp responseTime;
	private String uniqueId;
	private String refId;
	private HashMap<String,Object> rawData;
	private Object objectData;
	private boolean ignoreServiceLog = false;
	private Object requestObjectData;
	private Object responseObjectData;
	private Object serviceData;
	private ServiceErrorInfo errorInfo;	
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public Timestamp getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
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
	public String getServiceReqResId() {
		return serviceReqResId;
	}
	public void setServiceReqResId(String serviceReqResId) {
		this.serviceReqResId = serviceReqResId;
	}	
	public Object getRawData(String key){
		if(null == rawData){
			rawData = new HashMap<>();
		}
		return rawData.get(key);
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
	public Object getRequestObjectData() {
		return requestObjectData;
	}
	public void setRequestObjectData(Object requestObjectData) {
		this.requestObjectData = requestObjectData;
	}
	public Object getResponseObjectData() {
		return responseObjectData;
	}
	public void setResponseObjectData(Object responseObjectData) {
		this.responseObjectData = responseObjectData;
	}
	public Object getServiceData() {
		return serviceData;
	}
	public void setServiceData(Object serviceData) {
		this.serviceData = serviceData;
	}
	public ServiceErrorInfo getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(ServiceErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}	
}
