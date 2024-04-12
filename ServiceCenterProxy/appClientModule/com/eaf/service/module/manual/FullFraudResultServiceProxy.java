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
import org.json.JSONArray;
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
import com.eaf.orig.ulo.constant.MConstant;
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
import com.eaf.service.module.model.FullFraudResultRequestDataM;
import com.eaf.service.module.model.FullFraudResultResponseDataM;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class FullFraudResultServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(FullFraudResultServiceProxy.class);
	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("Request Transaction");
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		FullFraudResultRequestDataM fullFraudResultRequest = (FullFraudResultRequestDataM)serviceRequest.getObjectData();
		fullFraudResultRequest.setFuncNm(ServiceConstant.ServiceId.FullFraudResult);
		fullFraudResultRequest.setRqDt(ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		fullFraudResultRequest.setRqAppId(RqAppId);
		fullFraudResultRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		fullFraudResultRequest.setUserId(serviceRequest.getUserId());
		fullFraudResultRequest.setCorrID(fullFraudResultRequest.getRqUID());
		logger.debug("UserId >> "+serviceRequest.getUserId());
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		requestTransaction.serviceInfo(ServiceConstant.OUT, fullFraudResultRequest, serviceRequest);
		return requestTransaction;
	}	
	
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("Service Transaction");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		FullFraudResultRequestDataM fullFraudResultRequest = (FullFraudResultRequestDataM)requestServiceObject;
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
				httpHeaderReq.set("KBank-FuncNm", fullFraudResultRequest.getFuncNm());
				httpHeaderReq.set("KBank-RqUID", fullFraudResultRequest.getRqUID());
				httpHeaderReq.set("KBank-RqDt", fullFraudResultRequest.getRqDt());
				httpHeaderReq.set("KBank-RqAppId", fullFraudResultRequest.getRqAppId());
				httpHeaderReq.set("KBank-UserId", fullFraudResultRequest.getUserId());
				httpHeaderReq.set("KBank-TerminalId", fullFraudResultRequest.getTerminalId());
				httpHeaderReq.set("KBank-UserLangPref", fullFraudResultRequest.getUserLangPref());
				httpHeaderReq.set("KBank-CorrID", fullFraudResultRequest.getCorrID());	
				httpHeaderReq.setContentType(MediaType.APPLICATION_JSON);
			logger.debug("httpHeaderReq : "+httpHeaderReq);
			
			// Prepare JSON as HTTP body
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("applicationGroupNo", fullFraudResultRequest.getApplicationGroupNo());
			jsonBody.put("product", fullFraudResultRequest.getProduct());
			jsonBody.put("asOfDate", fullFraudResultRequest.getAsOfDate());
			jsonBody.put("result", fullFraudResultRequest.getResult());
			logger.debug("FullFraud JSON : "+jsonBody);				
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
			
			//Extract Data from responseBody
			KbankError kbankError = new KbankError();
			ArrayList<KbankError> errorList = new ArrayList<KbankError>();
			FullFraudResultResponseDataM fullFraudResultResponse = new FullFraudResultResponseDataM();
			if(responseBody != null)
			{
				JSONObject respObject = new JSONObject(responseBody);
				JSONArray errorArray = ServiceUtil.getJSONArray(respObject, "Error");
				if(errorArray != null)
				{
					for (int i = 0; i < errorArray.length(); i++) 
					{
					    JSONObject errorObject = errorArray.getJSONObject(i);
					    kbankError.setErrorAppId(ServiceUtil.getJSONString(errorObject, "ErrorAppId"));
						kbankError.setErrorCode(ServiceUtil.getJSONString(errorObject, "ErrorCode"));
						kbankError.setErrorDesc(ServiceUtil.getJSONString(errorObject, "ErrorDesc"));
						kbankError.setErrorSeverity(ServiceUtil.getJSONString(errorObject, "ErrorSeverity"));
					}
					errorList.add(kbankError);
				}
				
				fullFraudResultResponse.setFuncNm(ServiceUtil.getJSONString(respObject, "FuncNm"));
				fullFraudResultResponse.setRqUID(ServiceUtil.getJSONString(respObject, "RqUID"));
				fullFraudResultResponse.setRsAppId(ServiceUtil.getJSONString(respObject, "RsAppId"));
				fullFraudResultResponse.setRsDt(ServiceUtil.getJSONString(respObject, "RsDt"));
				fullFraudResultResponse.setRsUID(ServiceUtil.getJSONString(respObject, "RsUID"));
				fullFraudResultResponse.setError(errorList);
				fullFraudResultResponse.setStatusCode(ServiceUtil.getJSONString(respObject, "StatusCode"));
			}
			
			// Populate serviceTransaction object and return status
			serviceTransaction.setServiceTransactionObject(fullFraudResultResponse);
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
		FullFraudResultResponseDataM fullFraudResultResponse = (FullFraudResultResponseDataM)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != fullFraudResultResponse){
			try{
				serviceResponse.setObjectData(fullFraudResultResponse);
				if(ServiceConstant.Status.SUCCESS.equals(fullFraudResultResponse.getStatusCode())){
					serviceResponse.setStatusCode(fullFraudResultResponse.getStatusCode());
				}else{
					serviceResponse.setStatusCode(fullFraudResultResponse.getStatusCode());
					List<KbankError> Errors = fullFraudResultResponse.getError();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();
						errorInfo.setServiceId(ServiceConstant.ServiceId.FullFraudResult);
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
		responseTransaction.serviceInfo(ServiceConstant.IN,fullFraudResultResponse,serviceResponse);
		return responseTransaction;
	}
}
