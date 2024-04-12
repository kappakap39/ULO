package com.eaf.service.common.inf;

import java.util.List;


import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.ServiceResponseTransaction;

public abstract class ServiceControlHelper implements ServiceControl{
	public ServiceControlHelper(){
		super();
	}	
	public ServiceRequestDataM serviceRequest;
	public ServiceResponseDataM serviceResponse;	
	public String endpointUrl;
	@Override
	public ServiceRequestDataM getServiceRequest() {
		return serviceRequest;
	}
	@Override
	public ServiceResponseDataM getServiceResponse() {
		return serviceResponse;
	}
	@Override
	public void perServiceTransaction(ServiceRequestDataM serviceRequest,ServiceResponseDataM serviceResponse) {
		this.serviceRequest = serviceRequest;
		this.serviceResponse = serviceResponse;
	}
	@Override
	public void postServiceTransaction(ServiceRequestTransaction requestTransaction,ServiceResponseTransaction responseTransaction) {
		serviceResponse.setStatusCode(responseTransaction.getStatusCode());
		serviceResponse.setErrorInfo(responseTransaction.getErrorInfo());
		serviceResponse.setRequestObjectData(requestTransaction.getServiceDataObject());
		serviceResponse.setResponseObjectData(responseTransaction.getServiceDataObject());
	}
	@Override
	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}
	@Override
	public String getEndpointUrl() {
		return endpointUrl;
	}
	public ServiceErrorInfo error(String errorType,Exception e){	
		String serviceId = serviceRequest.getServiceId();
		ServiceErrorInfo errorData = new ServiceErrorInfo();
		errorData.setServiceId(serviceId);
		errorData.setErrorType(errorType);
		errorData.setErrorInformation(e.getLocalizedMessage());
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		return errorData;
	}
	public ServiceErrorInfo error(String errorType,String  errInfo){	
		String serviceId = serviceRequest.getServiceId();
		ServiceErrorInfo errorData = new ServiceErrorInfo();
		errorData.setServiceId(serviceId);
		errorData.setErrorType(errorType);
		errorData.setErrorInformation(errInfo);
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		return errorData;
	}
	public ServiceErrorInfo error(String errorType,List<decisionservice_iib.Error> errors){	
		String serviceId = serviceRequest.getServiceId();
		ServiceErrorInfo errorData = new ServiceErrorInfo();
		errorData.setServiceId(serviceId);
		errorData.setErrorType(errorType);
		if(null!=errors){
			StringBuilder errorList = new StringBuilder("");
			for(decisionservice_iib.Error err : errors){
				errorList.append(err.getErrorAppAbbrv()+": ["+err.getErrorCode()+"] - "+err.getErrorDesc()+System.getProperty("line.separator"));
			}
			errorData.setErrorInformation(errorList.toString());
		}	
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		return errorData;
	}
	public ServiceErrorInfo errorEapp(String errorType,List<com.ava.flp.eapp.iib.model.Error> errors){	
		String serviceId = serviceRequest.getServiceId();
		ServiceErrorInfo errorData = new ServiceErrorInfo();
		errorData.setServiceId(serviceId);
		errorData.setErrorType(errorType);
		if(null!=errors){
			StringBuilder errorList = new StringBuilder("");
			for(com.ava.flp.eapp.iib.model.Error err : errors){
				errorList.append(err.getErrorAppId()+": ["+err.getErrorCode()+"] - "+err.getErrorDesc()+System.getProperty("line.separator"));
			}
			errorData.setErrorInformation(errorList.toString());
		}	
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		return errorData;
	}
}
