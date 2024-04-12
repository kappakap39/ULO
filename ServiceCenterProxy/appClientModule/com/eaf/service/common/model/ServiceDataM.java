package com.eaf.service.common.model;


public interface ServiceDataM {
	public void serviceInfo(String activityType,Object serviceDataObject,Object serviceProxyObject);
	public String getStatusCode();
	public String getActivityType();
	public Object getServiceDataObject();
	public Object getServiceProxyObject();
	public String getServiceId();
	public String getTransactionId();
	public String getServiceReqResId();
	public int getStepId();
	public String getuserId();
	public String getUniqueId();
	public String getRefId();
	public String getServiceData();
	public ServiceErrorInfo getErrorInfo();
}
