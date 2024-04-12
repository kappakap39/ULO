package com.eaf.service.common.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceLogDataM implements Serializable,Cloneable{
	private String serviceId;
	private String serviceReqRespId;
	private String transactionId;
	private String refCode;
	private String respCode;
	private String respDesc;
	private String errorMessage;
	private String uniqueId;
	private String activityType;
	private Object serviceDataObject;
	private String userId;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceReqRespId() {
		return serviceReqRespId;
	}
	public void setServiceReqRespId(String serviceReqRespId) {
		this.serviceReqRespId = serviceReqRespId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public Object getServiceDataObject() {
		return serviceDataObject;
	}
	public void setServiceDataObject(Object serviceDataObject) {
		this.serviceDataObject = serviceDataObject;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
}
