package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.rest.model.BPMUserRequest;
import com.eaf.service.rest.model.BPMUserResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class BpmPurgeUserProxy extends ServiceControlHelper implements ServiceControl  {
	private static transient Logger logger = Logger.getLogger(KmobileServiceProxy.class);
	public final static String serviceId = "BpmPurgeUser";
	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		//KmobileRequestDataM kMobileRequest = (KmobileRequestDataM) serviceRequest.getObjectData();
		
		List<BPMUserRequest> listBpmUserRequest  = (List<BPMUserRequest>) serviceRequest.getObjectData();
		
		requestTransaction.serviceInfo(ServiceConstant.OUT,listBpmUserRequest,serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
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
		HttpHeaders headers = new HttpHeaders();
		try{
			List<BPMUserRequest> listBpmUserRequest =  (List<BPMUserRequest>) requestServiceObject;
			String endPointUrl = serviceRequest.getEndpointUrl();
			logger.info("endPointUrl >> "+endPointUrl);
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<BPMUserResponse> responseEntity = restTemplate.postForEntity(endPointUrl, listBpmUserRequest, BPMUserResponse.class,headers);
			serviceTransaction.setServiceTransactionObject(responseEntity);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}

	@Override
	public ServiceResponseTransaction responseTransaction(
			ServiceTransaction serviceTransaction) {
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		ResponseEntity<BPMUserResponse> responseEntity = (ResponseEntity<BPMUserResponse>) serviceTransaction.getServiceTransactionObject();
		BPMUserResponse bmpResponse = responseEntity.getBody();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseEntity){
			try{
				HttpStatus httpStatus = responseEntity.getStatusCode();
				logger.debug("bmpResponse.getResultCode() >> "+bmpResponse.getResultCode());

				if(ServiceConstant.RestStatus.STATUS_SUCCESS.equals(bmpResponse.getResultCode())){
					serviceResponse.setObjectData(bmpResponse);
					serviceResponse.setStatusCode(ServiceConstant.RestStatus.STATUS_SUCCESS);
				}else{
					serviceResponse.setObjectData(bmpResponse);
					serviceResponse.setStatusCode(ServiceConstant.RestStatus.STATUS_FAIL);
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();		
					errorInfo.setServiceId(serviceId);
					errorInfo.setErrorSystem(ResponseData.SystemType.BPM_REMOVE_USER);
					errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
					errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					errorInfo.setErrorCode(bmpResponse.getResultCode());
					errorInfo.setErrorDesc(bmpResponse.getResultDesc());
					serviceResponse.setErrorInfo(errorInfo);					
				}
			}catch(Exception e){		
				logger.fatal("ERROR",e);
				serviceResponse.setStatusCode(ServiceConstant.RestStatus.STATUS_FAIL);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.RestStatus.STATUS_FAIL);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN,bmpResponse,serviceResponse);
		return responseTransaction;
	}

}
