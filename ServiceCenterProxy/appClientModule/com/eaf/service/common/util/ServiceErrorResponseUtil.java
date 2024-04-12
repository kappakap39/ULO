package com.eaf.service.common.util;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceResponseDataM;

public class ServiceErrorResponseUtil{
	public static ProcessResponse fail(ServiceResponseDataM serviceResponse){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(serviceResponse.getStatusCode());
		ErrorData errorData = new ErrorData();
		ServiceErrorInfo errorInfo = serviceResponse.getErrorInfo();
		if(null != errorInfo){
			errorData.setErrorCode(errorInfo.getErrorCode());
			errorData.setErrorDesc(errorInfo.getErrorDesc());
			errorData.setErrorInformation(errorInfo.getErrorInformation());
			errorData.setErrorSystem(errorInfo.getErrorSystem());
			errorData.setErrorTime(errorInfo.getErrorTime());
			errorData.setErrorType(errorInfo.getErrorType());
			errorData.setServiceId(errorInfo.getServiceId());
		}
		processResponse.setErrorData(errorData);
		return processResponse;
	}
	public static ProcessResponse fail(Exception e,String serviceId){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
		ErrorData errorData = new ErrorData();
		errorData.setErrorCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
		errorData.setErrorDesc(e.getLocalizedMessage());
		errorData.setErrorInformation(e.getLocalizedMessage());
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
		errorData.setServiceId(serviceId);
		
		processResponse.setErrorData(errorData);
		return processResponse;
	}
	public static ProcessResponse error(ServiceResponseDataM serviceResponse){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(serviceResponse.getStatusCode());
		ErrorData errorData = new ErrorData();
		ServiceErrorInfo errorInfo = serviceResponse.getErrorInfo();
		if(null != errorInfo){
			errorData.setErrorCode(errorInfo.getErrorCode());
			errorData.setErrorDesc(errorInfo.getErrorDesc());
			errorData.setErrorInformation(errorInfo.getErrorInformation());
			errorData.setErrorSystem(errorInfo.getErrorSystem());
			errorData.setErrorTime(errorInfo.getErrorTime());
			errorData.setErrorType(errorInfo.getErrorType());
			errorData.setServiceId(errorInfo.getServiceId());
		}
		processResponse.setErrorData(errorData);
		return processResponse;
	}
}
