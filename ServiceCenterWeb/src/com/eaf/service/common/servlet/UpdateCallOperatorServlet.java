package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.orig.ulo.model.calloper.UpdateCallOperatorRequest;
import com.eaf.orig.ulo.model.calloper.UpdateCallOperatorResponse;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;


@WebServlet("/UpdateCallOperatorServlet")
public class UpdateCallOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(UpdateCallOperatorServlet.class);	
    public UpdateCallOperatorServlet() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"UpdateCallOperator.properties";
    }    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	logger.debug("UpdateCallOperatorServlet");
    	String processCode = req.getParameter("process");
    	logger.debug("processCode >> "+processCode);
    	try{
	    	if("default".equals(processCode)){
	    		File file = new File((filePath.file));
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.load(fileInput);
				fileInput.close();
				
				HashMap<String,Object> data = new HashMap<>();
				data.put("url", properties.get("urlWebService"));
				ResponseUtils.sendJsonResponse(resp, data);
	    	}else if("request".equals(processCode)){
	    		String url = req.getParameter("UrlWebService");
	    		String CaseID = req.getParameter("CaseID");
	    		String CallOperator = req.getParameter("CallOperator");

				UpdateCallOperatorRequest updateCallOperatorRequest = new UpdateCallOperatorRequest();
					String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
					updateCallOperatorRequest.setFuncNm(ServiceConstant.ServiceId.UpdateCallOperator);
					updateCallOperatorRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,CaseID));
					updateCallOperatorRequest.setRqDt(ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
					updateCallOperatorRequest.setUserId(ServiceCache.getGeneralParam("KBANK_USER_ID"));
					updateCallOperatorRequest.setRqAppId(RqAppId);
				
					updateCallOperatorRequest.setCaseID(CaseID);
					updateCallOperatorRequest.setCallOperator(CallOperator);					
//				
//	    		ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
//	    			serviceRequest.setEndpointUrl(url);
//	    			serviceRequest.setServiceId("UpdateCallOperator");
//	    			serviceRequest.setUniqueId(updateCallOperatorRequest.getCaseID());
//	    			serviceRequest.setObjectData(updateCallOperatorRequest);
//	    			
//	    		ServiceCenterProxy proxy = new ServiceCenterProxy();
//	    		ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
//	    		UpdateCallOperatorResponse updateCallOperatorResponse = (UpdateCallOperatorResponse)serivceResponse.getObjectData();
//	    		HashMap<String,Object> data = new HashMap<String,Object>();
//	    		try{
//					ServiceRequestDataM jsonRqRs = ServiceFactory.getServiceDAO().getRequestResponseData(serivceResponse.getServiceReqResId());
//					data.put("jsonRq", jsonRqRs.getRawData("requestJSON"));
//					data.put("jsonRs", jsonRqRs.getRawData("responseJSON"));
//				}catch(Exception e){
//					logger.fatal("Error RqRs : ",e);
//				}
//	    		Gson gson = new Gson();
//	    		data.put("jsonRq", gson.toJson(updateCallOperatorRequest));
//	    		data.put("jsonRs", gson.toJson(updateCallOperatorResponse));
					
				UpdateCallOperatorResponse updateCallOperatorResponse = null;
			    String errorMsg = "";
			    ResponseEntity<String> responseEntity = null;
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
						httpHeaderReq.set("KBank-FuncNm", updateCallOperatorRequest.getFuncNm());
						httpHeaderReq.set("KBank-RqUID", updateCallOperatorRequest.getRqUID());
						httpHeaderReq.set("KBank-RqDt", ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
						httpHeaderReq.set("KBank-RqAppId", updateCallOperatorRequest.getRqAppId());
						httpHeaderReq.set("KBank-UserId", updateCallOperatorRequest.getUserId());
						httpHeaderReq.set("KBank-TerminalId", updateCallOperatorRequest.getTerminalId());
						httpHeaderReq.set("KBank-UserLangPref", updateCallOperatorRequest.getUserLangPref());
						httpHeaderReq.set("KBank-CorrID", updateCallOperatorRequest.getCorrID());
			    	HttpEntity<UpdateCallOperatorRequest> requestEntity = new HttpEntity<UpdateCallOperatorRequest>(updateCallOperatorRequest,httpHeaderReq);
					responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,String.class);
		    			    		
					
			    }catch(Exception e){
			    	errorMsg = e.getLocalizedMessage();
			    	logger.fatal("ERROR",e);
			    }
			    HttpHeaders httpHeaderResp = responseEntity.getHeaders();
				String statusCode = httpHeaderResp.getFirst("KBank-StatusCode");
				String responseBody = responseEntity.getBody();
				logger.debug("httpHeaderResp >> "+httpHeaderResp);
				logger.debug("StatusCode >> "+statusCode);
				logger.debug("responseBody >> "+responseBody);
				
	    		HashMap<String,Object> data = new HashMap<String,Object>();
	    		Gson gson = new Gson();
	    		data.put("jsonRq", gson.toJson(updateCallOperatorRequest));
	    		HashMap<String,Object> responseObject = new HashMap<>();
	    			responseObject.put("httpHeaderResp", httpHeaderResp);
	    			responseObject.put("responseBody",responseBody);
	    			responseObject.put("StatusCode",statusCode);
	    		data.put("jsonRs",gson.toJson(responseObject));
	    		
	    		ResponseUtils.sendJsonResponse(resp, data);
	    		
	    	}else if("save".equals(processCode)){
	    		String url = req.getParameter("UrlWebService");
	    		
	    		File file = new File((filePath.file));
				InputStream inStream = new FileInputStream(file);
				PrintWriter writer = new PrintWriter(file);
				Properties prop = new Properties();
				prop.load(inStream);
				inStream.close();
				
				prop.setProperty("urlWebService", url);
				prop.store(writer, null);
				writer.close();
	    	}
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
    }
}
