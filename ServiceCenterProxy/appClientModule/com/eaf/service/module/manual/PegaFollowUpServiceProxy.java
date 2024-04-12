package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.orig.ulo.model.pega.ErrorDataM;
import com.eaf.orig.ulo.model.pega.FollowUpRequest;
import com.eaf.orig.ulo.model.pega.FollowUpResponse;
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
import com.google.gson.Gson;
import com.ibm.json.java.JSONObject;

public class PegaFollowUpServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(PegaFollowUpServiceProxy.class);
	public static final String serviceId = "FollowUp";	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("Request Transaction");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		FollowUpRequest followUpRequest = (FollowUpRequest)serviceRequest.getObjectData();
		String uniqueId = serviceRequest.getUniqueId();
		logger.debug("uniqueId >> "+uniqueId);
		followUpRequest.setFuncNm(serviceId);
		followUpRequest.setRqDt(ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		logger.debug("RqAppId >> "+RqAppId);
		followUpRequest.setRqAppId(RqAppId);
		followUpRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,uniqueId));
		followUpRequest.setUserId(requestTransaction.getuserId());
		requestTransaction.serviceInfo(ServiceConstant.OUT, followUpRequest, serviceRequest);
		return requestTransaction;
	}
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("Service Transaction");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		FollowUpRequest followUpRequest = (FollowUpRequest)requestServiceObject;
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
	        logger.info("endPointUrl : "+endPointUrl);	        
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        	httpHeaderReq.set("Authorization","Basic "+ServiceCache.getProperty("PEGA_AUTHORIZATION_FOLLOWUP"));
				httpHeaderReq.set("KBank-FuncNm", followUpRequest.getFuncNm());
				httpHeaderReq.set("KBank-RqUID", followUpRequest.getRqUID());
				httpHeaderReq.set("KBank-RqDt", followUpRequest.getRqDt());
				httpHeaderReq.set("KBank-RqAppId", followUpRequest.getRqAppId());
				httpHeaderReq.set("KBank-UserId", followUpRequest.getUserId());
				httpHeaderReq.set("KBank-TerminalId", followUpRequest.getTerminalId());
				httpHeaderReq.set("KBank-UserLangPref", followUpRequest.getUserLangPref());
				httpHeaderReq.set("KBank-CorrID", followUpRequest.getCorrID());
			logger.debug("httpHeaderReq >> "+httpHeaderReq);			
			JSONObject followUpRequestObject = new JSONObject();
				followUpRequestObject.put("CaseID", followUpRequest.getCaseID());
				followUpRequestObject.put("CSVContent", followUpRequest.getCSVContent());
				followUpRequestObject.put("PegaOperatorID", followUpRequest.getPegaOperatorID());
			logger.debug("followUpRequestObject : "+followUpRequestObject);					
			HttpEntity<String> requestEntity = new HttpEntity<String>(followUpRequestObject.toString(),httpHeaderReq);
			ResponseEntity<String> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,String.class);			
			HttpHeaders httpHeaderResp = responseEntity.getHeaders();
			String statusCode = httpHeaderResp.getFirst("KBank-StatusCode");
			String responseBody = responseEntity.getBody();
			logger.debug("httpHeaderResp >> "+httpHeaderResp);
			logger.debug("StatusCode >> "+statusCode);
			logger.debug("responseBody >> "+responseBody);			
			ErrorDataM kbankError = new ErrorDataM();
				kbankError.setErrorAppAbbrv(httpHeaderResp.getFirst("KBank-ErrorAppAbbrv"));
				kbankError.setErrorAppId(httpHeaderResp.getFirst("KBank-ErrorAppId"));
				kbankError.setErrorCode(httpHeaderResp.getFirst("KBank-ErrorCode"));
				kbankError.setErrorDesc(httpHeaderResp.getFirst("KBank-ErrorDesc"));
				kbankError.setErrorSeverity(httpHeaderResp.getFirst("KBank-ErrorSeverity"));
			ArrayList<ErrorDataM> errorList = new ArrayList<ErrorDataM>();
				errorList.add(kbankError);
			FollowUpResponse followUpResponse = new FollowUpResponse();
				followUpResponse.setCorrID(httpHeaderResp.getFirst("KBank-CorrID"));
				followUpResponse.setFuncNm(httpHeaderResp.getFirst("KBank-FuncNm"));
				followUpResponse.setRqUID(httpHeaderResp.getFirst("KBank-RsUID"));
				followUpResponse.setRsAppId(httpHeaderResp.getFirst("KBank-RsAppId"));
				followUpResponse.setRsDt(httpHeaderResp.getFirst("KBank-RsDt"));
				followUpResponse.setRsUID(httpHeaderResp.getFirst("KBank-RqUID"));
				followUpResponse.setError(errorList);
				followUpResponse.setStatusCode(statusCode);
			serviceTransaction.setServiceTransactionObject(followUpResponse);
			serviceTransaction.setStatusCode(ServiceConstant.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			serviceTransaction.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("Response Transaction");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		FollowUpResponse followResponse = (FollowUpResponse)serviceTransaction.getServiceTransactionObject();
//		#rawi comment change logic 
//		FollowUpPegaRefDataObject refDataObject = (FollowUpPegaRefDataObject)serviceRequest.getServiceData();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode())&& null != followResponse){
			serviceResponse.setObjectData(followResponse);		
			if(ServiceConstant.Status.SUCCESS.equals(followResponse.getStatusCode())){
				serviceResponse.setStatusCode(followResponse.getStatusCode());
			}else{
				serviceResponse.setStatusCode(followResponse.getStatusCode());
				List<ErrorDataM> Errors = followResponse.getError();
				ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
					errorInfo.setErrorSystem(ResponseData.SystemType.PEGA);
					errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
					errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
				if(!ServiceUtil.empty(Errors)){
					ErrorDataM Error = Errors.get(0);
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
//		serviceResponse.setServiceData(refDataObject);
		serviceResponse.setServiceData(serviceRequest.getServiceData());
		responseTransaction.serviceInfo(ServiceConstant.IN, followResponse, serviceResponse);
		return responseTransaction;
	}
		
	public static void main(String[] args) {
		String authorization = "FLPService:rules";
		System.out.println(DatatypeConverter.printBase64Binary(authorization.getBytes()));
	}
	
}