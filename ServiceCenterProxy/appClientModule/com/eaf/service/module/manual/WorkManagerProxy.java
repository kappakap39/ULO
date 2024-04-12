package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.tempuri.ResponseOnline;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.DecisionService;
import com.ava.flp.eapp.iib.model.DecisionServiceResponseDataEappM;
import com.ava.flp.eapp.iib.model.KBankHeader;
import com.ava.flp.eapp.iib.serializer.DateTimeSerializer;
import com.avalant.wm.model.WorkManagerRequest;
import com.avalant.wm.model.WorkManagerResponse;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.orig.ulo.model.common.ResponseData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.iib.eapp.mapper.EDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.FraudonlinecheckProxy.responseConstant;
import com.eaf.service.module.model.WorkManagerRequestDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WorkManagerProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(EDecisionServiceProxy.class);
	public final static String serviceId = "WorkManager";	
	//private String DECISION_ERROR = ServiceCache.getConstant("DECISION_ERROR");
	
	private String requestDecision = null;
	@Override
	public ServiceRequestTransaction requestTransaction() throws Exception {
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		WorkManagerRequestDataM  applicationRequest = (WorkManagerRequestDataM)serviceRequest.getObjectData();
	
		
		WorkManagerRequest workManagerRequest=new WorkManagerRequest();
		workManagerRequest.setWmFn(applicationRequest.getWmFn());
		workManagerRequest.setRefId(applicationRequest.getRefId());
		workManagerRequest.setRefCode(applicationRequest.getRefCode());
		
		logger.debug("requestData>>>"+ new Gson().toJson(workManagerRequest));
		requestTransaction.serviceInfo(ServiceConstant.OUT, workManagerRequest, serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			String URL = getEndpointUrl();
			logger.debug("URL : "+URL);
			WorkManagerRequest requestData = (WorkManagerRequest)requestServiceObject;
			WorkManagerResponse responseData = restService(URL, requestData);
			serviceTransaction.setServiceTransactionObject(responseData);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceTransaction;
	}
	private WorkManagerResponse restService(String URL,WorkManagerRequest requestData){
		
		ResponseEntity<WorkManagerResponse> responseEntity = null;
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
	    	
			String endPointUrl = URL;
	        logger.info("endPointUrl : "+endPointUrl);	        
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        httpHeaderReq.add("Content-Type", "application/json");

	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
	        Gson gson=gsonBuilder.create();
	        
	        logger.debug("body->"+gson.toJson(requestData));
	        HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(requestData),httpHeaderReq);
			responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,WorkManagerResponse.class);
	    	
	    	logger.debug("response >> "+responseEntity);
	
	    }catch(Exception e){
	    	//errorMsg = e.getLocalizedMessage();
	    	logger.fatal("ERROR",e);
	    }
		WorkManagerResponse responseData=responseEntity.getBody();
		return responseData;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		WorkManagerResponse responseObject = (WorkManagerResponse)serviceTransaction.getServiceTransactionObject();
	
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && !ServiceUtil.empty(responseObject)){
			try{
				
				if(responseConstant.Status.SUCCESS.equals(responseObject.getStatusCode())){	
					serviceResponse.setObjectData(responseObject);
					serviceResponse.setStatusCode(responseConstant.Status.SUCCESS);
				}else{
					serviceResponse.setStatusCode(responseConstant.Status.SUCCESS);
					//com.kasikornbank.eai.cbs1215i01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();		
						errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(ResponseData.SystemType.FRAUD);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					
						errorInfo.setErrorCode(responseObject.getStatusCode());
//						errorInfo.setErrorDesc(responseObject.getErrorDesc());
//						errorInfo.setErrorInformation(responseObject.getErrorDesc());
					
					serviceResponse.setErrorInfo(errorInfo);
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);			
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}  
}
