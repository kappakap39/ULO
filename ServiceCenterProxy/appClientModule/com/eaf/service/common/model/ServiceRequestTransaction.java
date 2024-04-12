package com.eaf.service.common.model;

import com.eaf.service.common.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServiceRequestTransaction implements ServiceDataM{	
	private String serviceId;
	private String transactionId;
	private String serviceReqResId;
	private int stepId;
	private String userId;
	private String statusCode;
	private String activityType;
	private Object serviceRequestObject;
	private String uniqueId;
	private String refId;
	private ServiceRequestDataM serviceRequest;
	private String serviceData;
	private ServiceErrorInfo errorInfo;	
	@Override
	public void serviceInfo(String activityType,Object serviceDataObject,Object serviceProxyObject) {
		this.activityType = activityType;
		this.serviceRequestObject = serviceDataObject;
		if(serviceProxyObject instanceof ServiceRequestDataM){
			this.serviceRequest = (ServiceRequestDataM)serviceProxyObject;
			this.serviceId = serviceRequest.getServiceId();
			this.transactionId = serviceRequest.getTransactionId();
			this.serviceReqResId = serviceRequest.getServiceReqResId();
			this.stepId = serviceRequest.getStepId();
			this.uniqueId = serviceRequest.getUniqueId();
			this.refId = serviceRequest.getRefId();
			this.userId = serviceRequest.getUserId();
			if(!ServiceUtil.empty(serviceRequest.getServiceData())){
				if(serviceRequest.getServiceData() instanceof String){
					this.serviceData =(String)serviceRequest.getServiceData();
				}else{
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					this.serviceData = (gson.toJson(serviceRequest.getServiceData()));
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
		return serviceRequestObject;
	}
	@Override
	public Object getServiceProxyObject() {
		return serviceRequest;
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
	public ServiceErrorInfo getErrorInfo() {
		return errorInfo;
	}
	@Override
	public String getStatusCode() {
		return statusCode;
	}
}
