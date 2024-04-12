package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.eaf.service.module.manual.CIS0315U01ServiceProxy;
import com.eaf.service.module.model.CIS0315U01RequestDataM;
import com.eaf.service.module.model.CIS0315U01ResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS0315U01")
public class CIS0315U01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CIS0315U01.class);

    public CIS0315U01() {
        super();
    }
    
    public static class filePath{
    	public static final String properties = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"CIS0315U01WS.properties";
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.properties));
			InputStream inStream = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(inStream);
			inStream.close();
			
			HashMap<String, Object> object = new HashMap<>();
			object.put(CIS0315U01ServiceProxy.url,prop.getProperty(CIS0315U01ServiceProxy.url));
			object.put(CIS0315U01ServiceProxy.responseConstants.cusId,prop.getProperty(CIS0315U01ServiceProxy.responseConstants.cusId));
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			String cusId = request.getParameter("cusId_input");
			String scrAsstCode = request.getParameter("scrAsstCode_input");
			String scrAsstOthDesc = request.getParameter("scrAsstOthDesc_input");
			String policalPosDesc = request.getParameter("policalPosDesc_input");
			String asstValAmt = request.getParameter("asstValAmt_input");
			String asstValCode = request.getParameter("asstValCode_input");
			
			CIS0315U01RequestDataM CIS0315U01Request = new CIS0315U01RequestDataM();
				CIS0315U01Request.setCustomerId(cusId);
				CIS0315U01Request.setSoucreAssetCode(scrAsstCode);
				CIS0315U01Request.setSoucreAssetOtherDescription(scrAsstOthDesc);
				CIS0315U01Request.setPolicalPositionDescription(policalPosDesc);
				CIS0315U01Request.setAssetValueAmount(new BigDecimal(asstValAmt.equals("") ? "0.0" : asstValAmt));
				CIS0315U01Request.setAssetValueCode(asstValCode);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS0315U01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS0315U01Request);
			
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
			
			CIS0315U01ResponseDataM CIS0315U01Response = (CIS0315U01ResponseDataM)resp.getObjectData();
			if(null == CIS0315U01Response){
				CIS0315U01Response = new CIS0315U01ResponseDataM();
			}
			object.put(CIS0315U01ServiceProxy.responseConstants.cusId, CIS0315U01Response.getCustomerId());
			
			ResponseUtils.sendJsonResponse(response, object);
			
		}else if(process.equals("save")){
			String url = request.getParameter("url_setting");
			String cusId = request.getParameter("cusId_setting");
			
			File file = new File((filePath.properties));
			InputStream inputStream = new FileInputStream(file);
			Properties prop = new Properties();
			PrintWriter writer = new PrintWriter(file);
			prop.load(inputStream);
			inputStream.close();
			
			prop.setProperty(CIS0315U01ServiceProxy.url, url);
			prop.setProperty(CIS0315U01ServiceProxy.responseConstants.cusId, cusId);
			prop.store(writer, null);
			writer.close();
			
		}
	}

}
