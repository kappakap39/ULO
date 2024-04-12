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
import com.eaf.service.module.manual.CreateKVIAppServiceProxy;
import com.eaf.service.module.model.CreateKVIAppRequestDataM;
import com.eaf.service.module.model.CreateKVIAppResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CreateKVIApp")
public class CreateKVIApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CreateKVIApp.class);
    public CreateKVIApp() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CreateKVIApp.properties";
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
			
			HashMap<String,Object> object = new HashMap<>();
			object.put(CreateKVIAppServiceProxy.url, prop.get(CreateKVIAppServiceProxy.url));
			object.put(CreateKVIAppServiceProxy.responseConstants.fId, prop.getProperty(CreateKVIAppServiceProxy.responseConstants.fId));
			object.put(CreateKVIAppServiceProxy.responseConstants.tokenId, prop.getProperty(CreateKVIAppServiceProxy.responseConstants.tokenId));
			ResponseUtils.sendJsonResponse(response, object);
			
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			logger.debug("url : "+url);
			CreateKVIAppRequestDataM CreateKVIAppRequest = new CreateKVIAppRequestDataM();
			mapCreateKVIApp(request, CreateKVIAppRequest);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CreateKVIAppServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CreateKVIAppRequest);
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
			
			CreateKVIAppResponseDataM CreateKVIAppResponse = (CreateKVIAppResponseDataM)resp.getObjectData();
			if(null == CreateKVIAppResponse){
				CreateKVIAppResponse = new CreateKVIAppResponseDataM();
			}
			object.put(CreateKVIAppServiceProxy.responseConstants.fId, CreateKVIAppResponse.getfId());
			object.put(CreateKVIAppServiceProxy.responseConstants.tokenId, CreateKVIAppResponse.getTokenId());
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
			prop.setProperty(CreateKVIAppServiceProxy.url, url);
			prop.setProperty(CreateKVIAppServiceProxy.responseConstants.fId, fId);
			prop.setProperty(CreateKVIAppServiceProxy.responseConstants.tokenId, tokenId);
			prop.store(writer, null);
			reader.close();
			writer.close();
			System.gc();
		}
	}

	public void mapCreateKVIApp(HttpServletRequest request, CreateKVIAppRequestDataM CreateKVIAppRequest){
		String fgAppNo = request.getParameter("fgAppNo_input");
		String fcDept = request.getParameter("fcDept_input");
		String fcInputId = request.getParameter("fcInputId_input");
		String fgRequestor = request.getParameter("fgRequestor_input");
		String fgRequestorL = request.getParameter("fgRequestorL_input");
		String fgType = request.getParameter("fgType_input");
		String fgId = request.getParameter("fgId_input");
		String fgCisNo = request.getParameter("fgCisNo_input");
		String fgRequestor1 = request.getParameter("fgRequestor1_input");
		String fgRequestorL1 = request.getParameter("fgRequestorL1_input");
		String fgType1 = request.getParameter("fgType1_input");
		String fgId1 = request.getParameter("fgId1_input");
		String fgCisNo1 = request.getParameter("fgCisNo1_input");
		String fgRequestor2 = request.getParameter("fgRequestor2_input");
		String fgRequestorL2 = request.getParameter("fgRequestorL2_input");
		String fgType2 = request.getParameter("fgType2_input");
		String fgId2 = request.getParameter("fgId2_input");
		String fgCisNo2 = request.getParameter("fgCisNo2_input");
		String fcBusiness = request.getParameter("fcBusiness_input");
		String fcBusinessDesc = request.getParameter("fcBusinessDesc_input");
		
		CreateKVIAppRequest.setFgAppNo(fgAppNo);
		CreateKVIAppRequest.setFcDept(fcDept);
		CreateKVIAppRequest.setFcInputId(fcInputId);
		CreateKVIAppRequest.setFgRequestor(fgRequestor);
		CreateKVIAppRequest.setFgRequestorL(fgRequestorL);
		CreateKVIAppRequest.setFgType(fgType);
		CreateKVIAppRequest.setFgId(fgId);
		CreateKVIAppRequest.setFgCisNo(fgCisNo);
		CreateKVIAppRequest.setFgRequestor1(fgRequestor1);
		CreateKVIAppRequest.setFgRequestorL1(fgRequestorL1);
		CreateKVIAppRequest.setFgType1(fgType1);
		CreateKVIAppRequest.setFgId1(fgId1);
		CreateKVIAppRequest.setFgCisNo1(fgCisNo1);
		CreateKVIAppRequest.setFgRequestor2(fgRequestor2);
		CreateKVIAppRequest.setFgRequestorL2(fgRequestorL2);
		CreateKVIAppRequest.setFgType2(fgType2);
		CreateKVIAppRequest.setFgId2(fgId2);
		CreateKVIAppRequest.setFgCisNo2(fgCisNo2);
		CreateKVIAppRequest.setFcBusiness(fcBusiness);
		CreateKVIAppRequest.setFcBusinessDesc(fcBusinessDesc);
	}
}
