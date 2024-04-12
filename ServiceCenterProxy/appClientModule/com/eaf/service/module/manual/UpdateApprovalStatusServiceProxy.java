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
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusResponse;
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
import com.ibm.json.java.JSONObject;


public class UpdateApprovalStatusServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(UpdateApprovalStatusServiceProxy.class);
	public static final String serviceId = "UpdateApprovalStatus";	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("Request Transaction");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		UpdateApprovalStatusRequest updateApprovalStatusRequest = (UpdateApprovalStatusRequest)serviceRequest.getObjectData();
		updateApprovalStatusRequest.setFuncNm(serviceId);
		updateApprovalStatusRequest.setRqDt(ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		updateApprovalStatusRequest.setRqAppId(RqAppId);
		updateApprovalStatusRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		updateApprovalStatusRequest.setUserId(serviceRequest.getUserId());
		logger.debug("UserId >> "+serviceRequest.getUserId());
		requestTransaction.serviceInfo(ServiceConstant.OUT, updateApprovalStatusRequest, serviceRequest);
		return requestTransaction;
	}	
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("Service Transaction");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		UpdateApprovalStatusRequest updateApprovalStatusRequest = (UpdateApprovalStatusRequest)requestServiceObject;
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
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        	httpHeaderReq.set("Authorization","Basic "+ServiceCache.getProperty("PEGA_AUTHORIZATION_UPDATE_APPROVAL_STATUS"));
				httpHeaderReq.set("KBank-FuncNm", updateApprovalStatusRequest.getFuncNm());
				httpHeaderReq.set("KBank-RqUID", updateApprovalStatusRequest.getRqUID());
				httpHeaderReq.set("KBank-RqDt", updateApprovalStatusRequest.getRqDt());
				httpHeaderReq.set("KBank-RqAppId", updateApprovalStatusRequest.getRqAppId());
				httpHeaderReq.set("KBank-UserId", updateApprovalStatusRequest.getUserId());
				httpHeaderReq.set("KBank-TerminalId", updateApprovalStatusRequest.getTerminalId());
				httpHeaderReq.set("KBank-UserLangPref", updateApprovalStatusRequest.getUserLangPref());
				httpHeaderReq.set("KBank-CorrID", updateApprovalStatusRequest.getCorrID());				
			logger.debug("httpHeaderReq : "+httpHeaderReq);							
			JSONObject csvContentObject = new JSONObject();
			csvContentObject.put("CSVContent", updateApprovalStatusRequest.getCSVContent());
			logger.debug("csvContentObject : "+csvContentObject);				
			HttpEntity<String> requestEntity = new HttpEntity<String>(csvContentObject.toString(),httpHeaderReq);
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
				kbankError.setErrorDesc(httpHeaderResp.getFirst("KBank-rrorDesc"));
				kbankError.setErrorSeverity(httpHeaderResp.getFirst("KBank-ErrorSeverity"));
			ArrayList<ErrorDataM> errorList = new ArrayList<ErrorDataM>();
				errorList.add(kbankError);
			UpdateApprovalStatusResponse updateApprovalStatusResponse = new UpdateApprovalStatusResponse();
				updateApprovalStatusResponse.setCorrID(httpHeaderResp.getFirst("KBank-CorrID"));
				updateApprovalStatusResponse.setFuncNm(httpHeaderResp.getFirst("KBank-FuncNm"));
				updateApprovalStatusResponse.setRqUID(httpHeaderResp.getFirst("KBank-RsUID"));
				updateApprovalStatusResponse.setRsAppId(httpHeaderResp.getFirst("KBank-RsAppId"));
				updateApprovalStatusResponse.setRsDt(httpHeaderResp.getFirst("KBank-RsDt"));
				updateApprovalStatusResponse.setRsUID(httpHeaderResp.getFirst("KBank-RqUID"));
				updateApprovalStatusResponse.setError(errorList);
				updateApprovalStatusResponse.setStatusCode(statusCode);
			serviceTransaction.setServiceTransactionObject(updateApprovalStatusResponse);
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
		UpdateApprovalStatusResponse updateApprovalStatusResponse = (UpdateApprovalStatusResponse)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != updateApprovalStatusResponse){
			try{
				serviceResponse.setObjectData(updateApprovalStatusResponse);		
				if(ServiceConstant.Status.SUCCESS.equals(updateApprovalStatusResponse.getStatusCode())){
					serviceResponse.setStatusCode(updateApprovalStatusResponse.getStatusCode());
				}else{
					serviceResponse.setStatusCode(updateApprovalStatusResponse.getStatusCode());
					List<ErrorDataM> Errors = updateApprovalStatusResponse.getError();
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
			}catch(Exception e){
				logger.fatal("ERROR",e);	
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN,updateApprovalStatusResponse,serviceResponse);
		return responseTransaction;
	}	
	public static void main(String[] args) {
		String authorization = "FLPService:rules";
		System.out.println(DatatypeConverter.printBase64Binary(authorization.getBytes()));
	}
}
