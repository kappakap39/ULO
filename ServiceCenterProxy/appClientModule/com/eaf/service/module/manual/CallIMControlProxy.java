package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.im.rest.callIM.model.CallIMRequest;
import com.eaf.im.rest.callIM.model.CallIMResponse;
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

public class CallIMControlProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CallIMControlProxy.class);
	public final static String serviceId = "CallIM";	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		CallIMRequest callIMRequest = (CallIMRequest) serviceRequest.getObjectData();
			callIMRequest.setFuncNm(serviceId);
			callIMRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			callIMRequest.setRqDt(ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
			callIMRequest.setRqAppId(RqAppId);
			callIMRequest.setUserId(serviceRequest.getUserId());
			callIMRequest.setCorrID(serviceRequest.getServiceReqResId());		
	    logger.info("callIMRequest >> "+callIMRequest);
	    requestTransaction.serviceInfo(ServiceConstant.OUT, callIMRequest, serviceRequest);
		return requestTransaction;
	}
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CallIMRequest callIMRequest = (CallIMRequest) requestServiceObject;
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
	        logger.info("endPointUrl : "+endPointUrl);	        
	        HttpHeaders httpHeaderReq = new HttpHeaders();
				httpHeaderReq.set("KBank-FuncNm", callIMRequest.getFuncNm());
				httpHeaderReq.set("KBank-RqUID", callIMRequest.getRqUID());
				httpHeaderReq.set("KBank-RqDt", ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
				httpHeaderReq.set("KBank-RqAppId", callIMRequest.getRqAppId());
				httpHeaderReq.set("KBank-UserId", callIMRequest.getUserId());
				httpHeaderReq.set("KBank-TerminalId", callIMRequest.getTerminalId());
				httpHeaderReq.set("KBank-UserLangPref", callIMRequest.getUserLangPref());
				httpHeaderReq.set("KBank-CorrID", callIMRequest.getCorrID());
			logger.debug("httpHeaderReq >> "+httpHeaderReq);
			HttpEntity<CallIMRequest> requestEntity = new HttpEntity<CallIMRequest>(callIMRequest,httpHeaderReq);
			ResponseEntity<CallIMResponse> responseEntity = restTemplate.postForEntity(endPointUrl, requestEntity, CallIMResponse.class);
			
			HttpHeaders httpHeaderResp = responseEntity.getHeaders();
			String statusCode = httpHeaderResp.getFirst("KBank-StatusCode");
			CallIMResponse responseBody = responseEntity.getBody();
			logger.debug("httpHeaderResp >> "+httpHeaderResp);
			logger.debug("StatusCode >> "+statusCode);
			logger.debug("responseBody >> "+responseBody);
			
			KbankError kbankError = new KbankError();
				kbankError.setErrorAppAbbrv(httpHeaderResp.getFirst("KBank-ErrorAppAbbrv"));
				kbankError.setErrorAppId(httpHeaderResp.getFirst("KBank-ErrorAppId"));
				kbankError.setErrorCode(httpHeaderResp.getFirst("KBank-ErrorCode"));
				kbankError.setErrorDesc(httpHeaderResp.getFirst("KBank-ErrorDesc"));
				kbankError.setErrorSeverity(httpHeaderResp.getFirst("KBank-ErrorSeverity"));
			ArrayList<KbankError> errorList = new ArrayList<KbankError>();
				errorList.add(kbankError);
				CallIMResponse callIMResponse = new CallIMResponse();
				callIMResponse.setCorrID(httpHeaderResp.getFirst("KBank-CorrID"));
				callIMResponse.setFuncNm(httpHeaderResp.getFirst("KBank-FuncNm"));
				callIMResponse.setRqUID(httpHeaderResp.getFirst("KBank-RsUID"));
				callIMResponse.setRsAppId(httpHeaderResp.getFirst("KBank-RsAppId"));
				callIMResponse.setRsDt(httpHeaderResp.getFirst("KBank-RsDt"));
				callIMResponse.setRsUID(httpHeaderResp.getFirst("KBank-RqUID"));
				callIMResponse.setError(errorList);
				callIMResponse.setTokenID(responseBody.getTokenID());
				callIMResponse.setIMURL(responseBody.getIMURL());
				callIMResponse.setStatusCode(statusCode);			
			serviceTransaction.setServiceTransactionObject(callIMResponse);
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
		CallIMResponse callIMResponse = (CallIMResponse)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode())&& null != callIMResponse){
			serviceResponse.setObjectData(callIMResponse);		
			if(ServiceConstant.Status.SUCCESS.equals(callIMResponse.getStatusCode())){
				serviceResponse.setStatusCode(callIMResponse.getStatusCode());
			}else{
				serviceResponse.setStatusCode(callIMResponse.getStatusCode());
				List<KbankError> Errors = callIMResponse.getError();
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
		responseTransaction.serviceInfo(ServiceConstant.IN,callIMResponse,serviceResponse);
		return responseTransaction;
	}
}
