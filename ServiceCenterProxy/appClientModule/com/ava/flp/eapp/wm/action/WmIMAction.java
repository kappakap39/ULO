package com.ava.flp.eapp.wm.action;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.avalant.wm.model.WmTask;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.doc.CreateDocSetRequest;
import com.eaf.orig.ulo.model.pega.ErrorDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.util.ServiceUtil;
import com.google.gson.Gson;

public class WmIMAction extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(WmIMAction.class);
	
	@Override
	public Object processAction() {
		ProcessResponse response = new ProcessResponse();
		response.setStatusCode(ServiceConstant.Status.SUCCESS);
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
	    	
	    	
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        httpHeaderReq.setContentType(MediaType.APPLICATION_JSON);
	
	        Gson gson = new Gson();
	        HttpEntity<String> requestEntity = null;
	        
    		String endPointUrl = SystemConfig.getProperty("WORK_MANAGER_IM_ACTION_URL");
    		WmTask task = (WmTask) objectForm;
    		
    		CreateDocSetRequest body = gson.fromJson(task.getData().getTaskData(), CreateDocSetRequest.class);
    		logger.debug("body->"+gson.toJson(body));
    		
    		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			httpHeaderReq.set("KBank-FuncNm", ServiceConstant.ServiceId.ECreateDocSet);
			httpHeaderReq.set("KBank-RqUID", ServiceUtil.generateRqUID(RqAppId,body.getSetID()));
			httpHeaderReq.set("KBank-RqDt", ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
			httpHeaderReq.set("KBank-RqAppId", RqAppId);
			httpHeaderReq.set("KBank-UserId", SystemConstant.getConstant("SYSTEM_USER"));
    		
    		requestEntity = new HttpEntity<String>(gson.toJson(body),httpHeaderReq);
	    	
	    	logger.debug("endPointUrl : "+endPointUrl);	
	    	ResponseEntity<String> result = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,String.class);
	    	HttpHeaders httpHeaderResp = result.getHeaders();
			String statusCode = httpHeaderResp.getFirst("KBank-StatusCode");
			String responseBody = result.getBody();
			logger.debug("httpHeaderResp >> "+httpHeaderResp);
			logger.debug("StatusCode >> "+statusCode);
			logger.debug("responseBody >> "+responseBody);
			ProcessResponse resultResponse = new ProcessResponse();
			resultResponse.setStatusCode(statusCode);
			if(!Util.empty(httpHeaderResp.getFirst("KBank-ErrorCode"))){
				ErrorData errorData = new ErrorData();
//				errorData.setErrorAppAbbrv(httpHeaderResp.getFirst("KBank-ErrorAppAbbrv"));
				errorData.setErrorId(httpHeaderResp.getFirst("KBank-ErrorAppId"));
				errorData.setErrorCode(httpHeaderResp.getFirst("KBank-ErrorCode"));
				errorData.setErrorDesc(httpHeaderResp.getFirst("KBank-ErrorDesc"));
				errorData.setErrorInformation(httpHeaderResp.getFirst("KBank-ErrorDesc"));
				errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
//				errorData.setErrorSeverity(httpHeaderResp.getFirst("KBank-ErrorSeverity"));
				
				resultResponse.setErrorData(errorData);
			}
	    	
    		ResponseEntity<ProcessResponse> responseEntity = new ResponseEntity<ProcessResponse>(resultResponse, HttpStatus.OK);
	    	
	    	logger.debug("response >> "+responseEntity);
	    	
	    	response = responseEntity.getBody();
	    	
	    }catch(Exception e){
	    	logger.fatal("ERROR",e);
	    	response.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
	    	ErrorData errorData = new ErrorData();
			errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
			errorData.setErrorInformation(e.getLocalizedMessage());
			errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
			errorData.setErrorCode(ErrorData.ErrorType.SYSTEM_ERROR);
			errorData.setErrorDesc(e.getMessage());
	    	response.setErrorData(errorData);
	    }
		return response;
	}
}
