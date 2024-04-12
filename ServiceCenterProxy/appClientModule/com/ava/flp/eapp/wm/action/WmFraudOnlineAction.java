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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.avalant.wm.model.WmTask;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;

public class WmFraudOnlineAction extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(WmFraudOnlineAction.class);
	
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
	
	        HttpEntity<String> requestEntity = null;
	        
    		String endPointUrl = SystemConfig.getProperty("WORK_MANAGER_FRAUD_ONLINE_ACTION_URL");
    		WmTask task = (WmTask) objectForm;
    		
    		endPointUrl = endPointUrl + "?applicationGroupId="+task.getRefId();
    		requestEntity = new HttpEntity<String>(httpHeaderReq);
	    	
	    	logger.debug("endPointUrl : "+endPointUrl);	
	    	ResponseEntity<ProcessResponse> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,ProcessResponse.class);
	    	
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
