package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.EditKVIAppServiceProxy;
import com.eaf.service.module.model.EditKVIAppRequestDataM;
import com.eaf.service.module.model.EditKVIAppResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/EditKVIApp")
public class EditKVIApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(EditKVIApp.class);       
    public EditKVIApp() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"EditKVIApp.properties";
    }    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.file));
			FileReader reader = new FileReader(file);
			Properties prop = new Properties();
			prop.load(reader);
			reader.close();
			
			HashMap<String, Object> object = new HashMap<>();
			object.put(EditKVIAppServiceProxy.url, prop.getProperty(EditKVIAppServiceProxy.url));
			object.put(EditKVIAppServiceProxy.responseConstants.fId, prop.getProperty(EditKVIAppServiceProxy.responseConstants.fId));
			object.put(EditKVIAppServiceProxy.responseConstants.tokenId, prop.getProperty(EditKVIAppServiceProxy.responseConstants.tokenId));
			ResponseUtils.sendJsonResponse(response, object);
			
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			String fId = request.getParameter("fId_input");
			
			EditKVIAppRequestDataM EditKVIAppRequest = new EditKVIAppRequestDataM();
				EditKVIAppRequest.setfId(fId);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(EditKVIAppServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));			
				serviceRequest.setObjectData(EditKVIAppRequest);
			ServiceResponseDataM resp = null;	
			try{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
					resp = proxy.requestService(serviceRequest);
			}catch(Exception e){
				e.printStackTrace();
			}
			HashMap<String,Object> object = new HashMap<String,Object>();
			Gson gson = new Gson();
			object.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			object.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			
			EditKVIAppResponseDataM EditKVIAppResponse = (EditKVIAppResponseDataM)resp.getObjectData();
			if(null == EditKVIAppResponse){
				EditKVIAppResponse = new EditKVIAppResponseDataM();
			}
			object.put(EditKVIAppServiceProxy.responseConstants.fId, EditKVIAppResponse.getfId());
			object.put(EditKVIAppServiceProxy.responseConstants.tokenId, EditKVIAppResponse.getTokenId());
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("edit")){
			String url = request.getParameter("url_setting");
			String fId = request.getParameter("fId_setting");
			String tokenId = request.getParameter("tokenId_setting");
			
			File file = new File((filePath.file));
			FileReader reader = new FileReader(file);
			FileWriter writer = new FileWriter(file);
			Properties prop = new Properties();
			prop.load(reader);
			prop.setProperty(EditKVIAppServiceProxy.url, url);
			prop.setProperty(EditKVIAppServiceProxy.responseConstants.fId, fId);
			prop.setProperty(EditKVIAppServiceProxy.responseConstants.tokenId, tokenId);
			prop.store(writer, null);
			writer.close();
			reader.close();
			System.gc();
		}
	}

}
