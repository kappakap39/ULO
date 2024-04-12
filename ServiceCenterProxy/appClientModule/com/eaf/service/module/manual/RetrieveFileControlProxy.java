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
import com.eaf.im.rest.attach.model.RetrieveFileRequest;
import com.eaf.im.rest.attach.model.RetrieveFileResponse;
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

public class RetrieveFileControlProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(RetrieveFileControlProxy.class);
	public final static String serviceId = "RetrieveFile";	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		RetrieveFileRequest retrieveFileRequest = (RetrieveFileRequest) serviceRequest.getObjectData();
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		retrieveFileRequest.setFuncNm(serviceId);
		retrieveFileRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		retrieveFileRequest.setRqDt(ServiceUtil.getCurrentTimestamp());
		retrieveFileRequest.setRqAppId(RqAppId);
		retrieveFileRequest.setUserId(serviceRequest.getUserId());
//		retrieveFileRequest.setTerminalId(serviceRequest.getTerminalId());
//		retrieveFileRequest.setUserLangPref(serviceRequest.getUserLangPref());
		retrieveFileRequest.setCorrID(serviceRequest.getServiceReqResId());		
	    logger.info("retrieveFileRequest >> "+retrieveFileRequest);
	    requestTransaction.serviceInfo(ServiceConstant.OUT, retrieveFileRequest, serviceRequest);
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
			ResponseEntity<RetrieveFileResponse> responseEntity = restTemplate.postForEntity(URI.create(endPointUrl), requestServiceObject, RetrieveFileResponse.class);
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
		RetrieveFileResponse retrieveResponse = (RetrieveFileResponse)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode())&& null != retrieveResponse){
			serviceResponse.setObjectData(retrieveResponse);		
			if(ServiceConstant.Status.SUCCESS.equals(retrieveResponse.getStatusCode())){
				serviceResponse.setStatusCode(retrieveResponse.getStatusCode());
			}else{
				serviceResponse.setStatusCode(retrieveResponse.getStatusCode());
				List<KbankError> Errors = retrieveResponse.getError();
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
		responseTransaction.serviceInfo(ServiceConstant.IN, retrieveResponse, serviceResponse);
		logger.debug(responseTransaction);
		return responseTransaction;
	}
}
