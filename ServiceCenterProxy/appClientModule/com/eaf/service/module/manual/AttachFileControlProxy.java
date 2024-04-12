package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.im.rest.attach.model.AttachFileRequest;
import com.eaf.im.rest.attach.model.AttachFileResponse;
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

public class AttachFileControlProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(AttachFileControlProxy.class);
	public final static String serviceId = "AttachFile";	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		MultiValueMap<String, Object> requestParams = new LinkedMultiValueMap<String, Object>();
		try{
			AttachFileRequest attachFileRequest = (AttachFileRequest) serviceRequest.getObjectData();
				requestParams.add("FuncNm",serviceId);
				String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
				requestParams.add("RqAppId",RqAppId);
				requestParams.add("RqUID",ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
				requestParams.add("RqDt",ServiceUtil.getReqRespTimestamp());
				requestParams.add("UserId",serviceRequest.getUserId());
				requestParams.add("CorrID",serviceRequest.getServiceReqResId());
				requestParams.add("UniqueId",serviceRequest.getUniqueId());
				
				requestParams.add("ContentFile",new InputStreamResource(((Part)serviceRequest.getRawData("ContentFile")).getInputStream()));
				requestParams.add("TokenID",attachFileRequest.getTokenID());
				requestParams.add("SetID",attachFileRequest.getSetID());
				requestParams.add("ApplicantType",attachFileRequest.getCustomerType());
				requestParams.add("CustomerID",attachFileRequest.getCustomerID());
				requestParams.add("CustomerIDType",attachFileRequest.getCustomerIDType());
				requestParams.add("CISNumber",attachFileRequest.getCISNumber());
				requestParams.add("DocTypeCode",attachFileRequest.getDocumentType());
				requestParams.add("CMSOriginalID",attachFileRequest.getDocumentName());
				requestParams.add("DocumentFormat",attachFileRequest.getDocumentFormat());
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}		
	    logger.info("requestParams >> "+requestParams);
	    requestTransaction.serviceInfo(ServiceConstant.OUT, requestParams, serviceRequest);
		return requestTransaction;
	} 
	
	@SuppressWarnings("unchecked")
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
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
				messageConverters.add(new ResourceHttpMessageConverter());
		        messageConverters.add(new FormHttpMessageConverter());
		        messageConverters.add(new SourceHttpMessageConverter<javax.xml.transform.Source>());
		        messageConverters.add(new StringHttpMessageConverter());
		        messageConverters.add(new MappingJackson2HttpMessageConverter());
	        restTemplate.setMessageConverters(messageConverters);
	        String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
	        HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> requestParams = (MultiValueMap<String, Object>)requestServiceObject;
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String,Object>>(requestParams,httpHeaders);
			ResponseEntity<AttachFileResponse> responseEntity = restTemplate.exchange(endPointUrl,HttpMethod.POST,requestEntity,AttachFileResponse.class);
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
		AttachFileResponse attachResponse = (AttachFileResponse)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != attachResponse){
			serviceResponse.setObjectData(attachResponse);		
			if(ServiceConstant.Status.SUCCESS.equals(attachResponse.getStatusCode())){
				serviceResponse.setStatusCode(attachResponse.getStatusCode());
			}else{
				serviceResponse.setStatusCode(attachResponse.getStatusCode());
				List<KbankError> Errors = attachResponse.getError();
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
		responseTransaction.serviceInfo(ServiceConstant.IN,attachResponse,serviceResponse);
		return responseTransaction;
	}
}
