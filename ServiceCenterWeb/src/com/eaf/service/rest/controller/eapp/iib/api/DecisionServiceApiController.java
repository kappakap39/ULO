package com.eaf.service.rest.controller.eapp.iib.api;

import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.ava.flp.eapp.iib.model.DecisionService;
import com.ava.flp.eapp.iib.model.KBankHeader;
import com.ava.flp.eapp.iib.serializer.DateTimeSerializer;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2018-12-25T18:34:24.643+07:00")
@Controller
public class DecisionServiceApiController implements DecisionServiceApi {
	private static transient Logger logger = Logger
			.getLogger(DecisionServiceApiController.class);

	String CALL_EDECISION_SERVICE_URL=SystemConfig.getProperty("CALL_EDECISION_SERVICE_URL");
	
	public ResponseEntity<DecisionService> decisionService(

	@ApiParam(value = "") @RequestBody DecisionService body

	) {
		String url = CALL_EDECISION_SERVICE_URL;
		
		KBankHeader KBankHeader=body.getKbankHeader();
		String errorMsg = "";
		ResponseEntity<DecisionService> responseEntity = null;
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
	    	
			String endPointUrl = url;
	        logger.info("endPointUrl : "+endPointUrl);	        
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        httpHeaderReq.add("Content-Type", "application/json; charset=UTF-8");

	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
	        Gson gson=gsonBuilder.create();
	        
	        logger.debug("body->"+gson.toJson(body));
	        HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(body),httpHeaderReq);
			responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,DecisionService.class);
	    	
	    	logger.debug("response >> "+responseEntity);
	
	    }catch(Exception e){
	    	errorMsg = e.getLocalizedMessage();
	    	logger.fatal("ERROR",e);
	    }

	    
		// do some magic!
		return new ResponseEntity<DecisionService>(responseEntity.getBody(),HttpStatus.OK);
	}

}
