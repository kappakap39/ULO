package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
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
import com.eaf.service.module.model.CheckProductDupRequestDataM;
import com.eaf.service.module.model.CheckProductDupResponseDataM;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import org.codehaus.jackson.map.DeserializationConfig;

public class CheckProductDupServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CheckProductDupServiceProxy.class);
	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("Request Transaction");
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		CheckProductDupRequestDataM checkProductDupResultRequest = (CheckProductDupRequestDataM)serviceRequest.getObjectData();
		checkProductDupResultRequest.setFuncNm(ServiceConstant.ServiceId.CheckProductDup);
		checkProductDupResultRequest.setRqDt(ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		checkProductDupResultRequest.setRqAppId(RqAppId);
		checkProductDupResultRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		checkProductDupResultRequest.setUserId(serviceRequest.getUserId());
		checkProductDupResultRequest.setCorrID(checkProductDupResultRequest.getRqUID());
		logger.debug("UserId >> "+serviceRequest.getUserId());
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		requestTransaction.serviceInfo(ServiceConstant.OUT, checkProductDupResultRequest, serviceRequest);
		return requestTransaction;
	}	
	
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("Service Transaction");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		CheckProductDupRequestDataM checkProductDupResultRequest = (CheckProductDupRequestDataM)requestServiceObject;
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
			
			// Prepare HTTP headers
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        	httpHeaderReq.set("Authorization","Basic "+ServiceCache.getProperty("PEGA_AUTHORIZATION_UPDATE_APPROVAL_STATUS"));
				httpHeaderReq.set("KBank-FuncNm", checkProductDupResultRequest.getFuncNm());
				httpHeaderReq.set("KBank-RqUID", checkProductDupResultRequest.getRqUID());
				httpHeaderReq.set("KBank-RqDt", checkProductDupResultRequest.getRqDt());
				httpHeaderReq.set("KBank-RqAppId", checkProductDupResultRequest.getRqAppId());
				httpHeaderReq.set("KBank-UserId", checkProductDupResultRequest.getUserId());
				httpHeaderReq.set("KBank-TerminalId", checkProductDupResultRequest.getTerminalId());
				httpHeaderReq.set("KBank-UserLangPref", checkProductDupResultRequest.getUserLangPref());
				httpHeaderReq.set("KBank-CorrID", checkProductDupResultRequest.getCorrID());	
				httpHeaderReq.setContentType(MediaType.APPLICATION_JSON);
			logger.debug("httpHeaderReq : "+httpHeaderReq);
			
			// Prepare JSON as HTTP body
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("idNo", checkProductDupResultRequest.getIdNo());
			logger.debug("CheckProductDup JSON : "+jsonBody);				
			HttpEntity<String> requestEntity = new HttpEntity<String>(jsonBody.toString(),httpHeaderReq);

			// Calling service
			ResponseEntity<String> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,String.class);			
			
			// Process response
			HttpHeaders httpHeaderResp = responseEntity.getHeaders();
			String statusCode = httpHeaderResp.getFirst("KBank-StatusCode");
			String responseBody = responseEntity.getBody();
			logger.debug("httpHeaderResp >> "+httpHeaderResp);
			logger.debug("StatusCode >> "+statusCode);
			logger.debug("responseBody >> "+responseBody);			
			KbankError kbankError = new KbankError();
				kbankError.setErrorAppAbbrv(httpHeaderResp.getFirst("KBank-ErrorAppAbbrv"));
				kbankError.setErrorAppId(httpHeaderResp.getFirst("KBank-ErrorAppId"));
				kbankError.setErrorCode(httpHeaderResp.getFirst("KBank-ErrorCode"));
				kbankError.setErrorDesc(httpHeaderResp.getFirst("KBank-rrorDesc"));
				kbankError.setErrorSeverity(httpHeaderResp.getFirst("KBank-ErrorSeverity"));
			ArrayList<KbankError> errorList = new ArrayList<KbankError>();
				errorList.add(kbankError);
			CheckProductDupResponseDataM checkProductDupResultResponse = new CheckProductDupResponseDataM();
			checkProductDupResultResponse.setCorrID(httpHeaderResp.getFirst("KBank-CorrID"));
			checkProductDupResultResponse.setFuncNm(httpHeaderResp.getFirst("KBank-FuncNm"));
			checkProductDupResultResponse.setRqUID(httpHeaderResp.getFirst("KBank-RqUID"));
			checkProductDupResultResponse.setRsAppId(httpHeaderResp.getFirst("KBank-RsAppId"));
			checkProductDupResultResponse.setRsDt(httpHeaderResp.getFirst("KBank-RsDt"));
			checkProductDupResultResponse.setRsUID(httpHeaderResp.getFirst("KBank-RsUID"));
			checkProductDupResultResponse.setError(errorList);
			checkProductDupResultResponse.setStatusCode(statusCode);
			
			//Extract Data from responseBody
			ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    	CheckProductDupResponseDataM checkProductDupResponseBody = objectMapper.readValue(responseBody, CheckProductDupResponseDataM.class);
	    	checkProductDupResultResponse.setDuplicateProducts(checkProductDupResponseBody.getDuplicateProducts());
			
			// Populate serviceTransaction object and return status
			serviceTransaction.setServiceTransactionObject(checkProductDupResultResponse);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}
	
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction){
		logger.debug("Response Transaction");
		logger.debug("UserId >> "+serviceResponse.getUserId());
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CheckProductDupResponseDataM checkProductDupResultResponse = (CheckProductDupResponseDataM)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != checkProductDupResultResponse){
			try{
				serviceResponse.setObjectData(checkProductDupResultResponse);
				if(ServiceConstant.Status.SUCCESS.equals(checkProductDupResultResponse.getStatusCode())){
					serviceResponse.setStatusCode(checkProductDupResultResponse.getStatusCode());
				}else{
					serviceResponse.setStatusCode(checkProductDupResultResponse.getStatusCode());
					List<KbankError> Errors = checkProductDupResultResponse.getError();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();
						errorInfo.setServiceId(ServiceConstant.ServiceId.CheckProductDup);
						errorInfo.setErrorSystem(ResponseData.SystemType.FLP);
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
			}catch(Exception e){
				logger.fatal("ERROR",e);	
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN,checkProductDupResultResponse,serviceResponse);
		return responseTransaction;
	}
}
