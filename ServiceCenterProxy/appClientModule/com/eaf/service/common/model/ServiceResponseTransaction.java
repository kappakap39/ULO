package com.eaf.service.common.model;

import com.eaf.service.common.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServiceResponseTransaction implements ServiceDataM{	
	private String serviceId;
	private String transactionId;
	private String serviceReqResId;
	private int stepId;
	private String userId;
	private String statusCode;
	private String activityType;
	private Object serviceResponseObject;
	private ServiceResponseDataM serviceResponse;
	private String uniqueId;
	private String refId;
	private String serviceData;
	private ServiceErrorInfo errorInfo;	
	@Override
	public void serviceInfo(String activityType,Object serviceDataObject,Object serviceProxyObject) {		
		this.activityType = activityType;
		this.serviceResponseObject = serviceDataObject;
		if(serviceProxyObject instanceof ServiceResponseDataM){
			this.serviceResponse = (ServiceResponseDataM)serviceProxyObject;
			this.serviceId = serviceResponse.getServiceId();
			this.transactionId = serviceResponse.getTransactionId();
			this.serviceReqResId = serviceResponse.getServiceReqResId();
			this.stepId = serviceResponse.getStepId();
			this.uniqueId = serviceResponse.getUniqueId();
			this.refId = serviceResponse.getRefId();
			this.statusCode = serviceResponse.getStatusCode();
			this.errorInfo = serviceResponse.getErrorInfo();
			this.userId = serviceResponse.getUserId();
			if(!ServiceUtil.empty(serviceResponse.getServiceData())){
				if(serviceResponse.getServiceData() instanceof String){
					this.serviceData=(String)serviceResponse.getServiceData();
				}else{
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					this.serviceData = (gson.toJson(serviceResponse.getServiceData()));
				}
				
			}
		}
	}
	@Override
	public String getActivityType() {
		return activityType;
	}
	@Override
	public Object getServiceDataObject() {
		return serviceResponseObject;
	}
	@Override
	public Object getServiceProxyObject() {
		return serviceResponse;
	}
	@Override
	public String getServiceId() {
		return serviceId;
	}
	@Override
	public String getTransactionId() {
		return transactionId;
	}
	@Override
	public String getServiceReqResId() {
		return serviceReqResId;
	}
	@Override
	public int getStepId() {
		return stepId;
	}
	@Override
	public String getuserId() {
		return userId;
	}	
	@Override
	public String getUniqueId() {
		return uniqueId;
	}
	@Override
	public String getRefId() {
		return refId;
	}
	@Override
	public String getServiceData() {
		return serviceData;
	}
	@Override
	public String getStatusCode() {
		return statusCode;
	}
	@Override
	public ServiceErrorInfo getErrorInfo() {
		return errorInfo;
	}
}
