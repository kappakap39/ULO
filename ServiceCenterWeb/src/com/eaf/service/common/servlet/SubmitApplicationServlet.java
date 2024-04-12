package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;
import com.eaf.service.rest.application.model.SubmitApplicationResponse;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/SubmitApplicationServlet")
public class SubmitApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(SubmitApplicationServlet.class);
	
	 public SubmitApplicationServlet() {
	        super();
	  }
	    public static class filePath{
	    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"SubmitApplication.properties";
	    }
	 @Override
	    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	logger.debug("SubmitApplicationServlet");
	    	String process = req.getParameter("process");
	    	try{
	    		if("default".equals(process)){
		    		File file = new File((filePath.file));
					FileInputStream fileInput = new FileInputStream(file);
					Properties properties = new Properties();
					properties.load(fileInput);
					fileInput.close();
					
					HashMap<String,Object> data = new HashMap<>();
					data.put("url", properties.get("UrlWebService"));
					ResponseUtils.sendJsonResponse(resp, data);
		    	}else if("request".equals(process)){
		    		String url = req.getParameter("UrlWebService");
		    		String qr1 = req.getParameter("qr1");
		    		String qr2 = req.getParameter("qr2");
		    		String rcCode = req.getParameter("rcCode");
		    		String branchCode = req.getParameter("branchCode");
		    		String channel = req.getParameter("channel");
		    		String scanDate = req.getParameter("scanDate");
		    		String WebScanUser = req.getParameter("WebScanUser");
		    		String pegaEventFlag = req.getParameter("pegaEventFlag");
		    		logger.debug("pegaEventFlag : "+pegaEventFlag);
		    		
		    		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		    		submitApplicationRequest.setQr1(qr1);
		    		submitApplicationRequest.setQr2(qr2);
		    		submitApplicationRequest.setRcCode(rcCode);
		    		submitApplicationRequest.setBranchCode(branchCode);
		    		submitApplicationRequest.setChannel(channel);
		    		submitApplicationRequest.setScanDate(this.toUtilDate(scanDate));
		    		submitApplicationRequest.setWebScanUser(WebScanUser);
		    		submitApplicationRequest.setPegaEventFlag(pegaEventFlag);
		    		
		    		
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
		    		messageConverters.add(new FormHttpMessageConverter());
		    		messageConverters.add(new SourceHttpMessageConverter<javax.xml.transform.Source>());
		    		messageConverters.add(new StringHttpMessageConverter());
		    		messageConverters.add(new MappingJackson2HttpMessageConverter());
		    		restTemplate.setMessageConverters(messageConverters);
		    		ResponseEntity<SubmitApplicationResponse> submitApplicationResponse = restTemplate.postForEntity(URI.create(url), submitApplicationRequest, SubmitApplicationResponse.class);
		    		
		    		Gson gson = new Gson();
		    		HashMap<String,Object> data = new HashMap<String,Object>();
		    		data.put("jsonRq", gson.toJson(submitApplicationRequest));
		    		data.put("jsonRs", gson.toJson(submitApplicationResponse));
		    		ResponseUtils.sendJsonResponse(resp, data);
		    	}
	    	}catch(Exception e){
	    		logger.fatal("ERROR ",e);
	    	}
	    }
	 
 	
 	private Date toUtilDate(String dateParam){
 		try {
 			DateFormat sdff = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
 	 		if(null!=dateParam && !"".equals(dateParam)){
 	 			return sdff.parse(dateParam);
 	 		}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
 		
 		return null;
 	}
}
