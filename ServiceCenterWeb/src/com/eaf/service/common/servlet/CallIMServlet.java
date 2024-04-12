package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.im.rest.callIM.model.CallIMRequest;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CallIMControlProxy;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CallIMServlet")
public class CallIMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CallIMServlet.class);
	public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CallIM.properties";
	} 
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("CallIMServlet");
    	String process = req.getParameter("process");
    	try{
	    	if("default".equals(process)){
	    		File file = new File((filePath.file));
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
					properties.load(fileInput);
				fileInput.close();				
				HashMap<String,Object> data = new HashMap<>();
				data.put("url", properties.get("urlWebService"));
				ResponseUtils.sendJsonResponse(resp, data);
	    	}else if("request".equals(process)){
	    		String url = req.getParameter("UrlWebService");
	    		String SetID = req.getParameter("SetID");	    		
	    		String RcCode = req.getParameter("RcCode");
	    		String BranchCode = req.getParameter("BranchCode");
	    		String ScanChannel = req.getParameter("ScanChannel");
	    		String RequestTokenOnly = req.getParameter("RequestTokenOnly");
	    			
	    		CallIMRequest callIMRequest = new CallIMRequest();
		    		callIMRequest.setSetID(SetID);
		    		callIMRequest.setRCCode(RcCode);
		    		callIMRequest.setBranchCode(BranchCode);
		    		callIMRequest.setScanChannel(ScanChannel);
		    		callIMRequest.setRequestTokenOnly(RequestTokenOnly);
	    		
		    	ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setServiceId(CallIMControlProxy.serviceId);
					serviceRequest.setEndpointUrl(url);
					serviceRequest.setIgnoreServiceLog(true);
					serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
					serviceRequest.setObjectData(callIMRequest);
					
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest);
				HashMap<String,Object> data = new HashMap<String,Object>();			
				Gson gson = new Gson();
				data.put("jsonRq", gson.toJson(serviceResponse.getRequestObjectData()));
				data.put("jsonRs", gson.toJson(serviceResponse.getResponseObjectData()));
				
				ResponseUtils.sendJsonResponse(resp, data);			
	    	}else if("save".equals(process)){
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
