package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.im.rest.attach.model.DeleteFileRequest;
import com.eaf.im.rest.attach.model.DeleteFileResponse;
import com.eaf.im.rest.kbank.model.KbankError;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class DeleteFileControlProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(DeleteFileControlProxy.class);
	public final static String serviceId = "DeleteFile";	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		DeleteFileRequest deleteRequest = (DeleteFileRequest) serviceRequest.getObjectData();
		deleteRequest.setFuncNm(serviceId);
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		deleteRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		deleteRequest.setRqDt(ServiceUtil.getCurrentTimestamp());
		deleteRequest.setRqAppId(RqAppId);
		deleteRequest.setUserId(serviceRequest.getUserId());
//		deleteRequest.setTerminalId(serviceRequest.getTerminalId());
//		deleteRequest.setUserLangPref(serviceRequest.getUserLangPref());
		deleteRequest.setCorrID(serviceRequest.getServiceReqResId());		
	    logger.info("deleteRequest >> "+deleteRequest);
	    requestTransaction.serviceInfo(ServiceConstant.OUT, deleteRequest, serviceRequest);
		return requestTransaction;
	}	
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
				@Override
				protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
			        if(connection instanceof HttpsURLConnection ){
			            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
							@Override
							public boolean verify(String arg0, SSLSession arg1) {
								return true;
							}
						});
			        }
					super.prepareConnection(connection, httpMethod);
				}
			});
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
	        HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<DeleteFileResponse> responseEntity = restTemplate.postForEntity(URI.create(endPointUrl),requestServiceObject,DeleteFileResponse.class);
			logger.info("StatusCode >> "+responseEntity.getStatusCode());
			serviceTransaction.setServiceTransactionObject(responseEntity.getBody());
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}	
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		DeleteFileResponse deleteResponse = (DeleteFileResponse)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != deleteResponse){
			serviceResponse.setObjectData(deleteResponse);		
			if(ServiceConstant.Status.SUCCESS.equals(deleteResponse.getStatusCode())){
				serviceResponse.setStatusCode(deleteResponse.getStatusCode());
			}else{
				serviceResponse.setStatusCode(deleteResponse.getStatusCode());
				List<KbankError> Errors = deleteResponse.getError();
				ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
					errorInfo.setErrorSystem(ResponseData.SystemType.IM);
					errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
					errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
				if(!ServiceUtil.empty(Errors)){
					KbankError Error = Errors.get(0);
					errorInfo.setErrorCode(Error.getErrorCode());
					errorInfo.setErrorDesc(Error.getErrorDesc());
					errorInfo.setErrorInformation(new Gson().toJson(Errors));
				}
				serviceResponse.setErrorInfo(errorInfo);
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN, serviceTransaction.getServiceTransactionObject(), serviceResponse);
		return responseTransaction;
	}
}
