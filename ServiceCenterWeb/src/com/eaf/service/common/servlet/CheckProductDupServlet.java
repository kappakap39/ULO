package com.eaf.service.common.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.service.module.model.CheckProductDupRequestDataM;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.google.gson.Gson;

@WebServlet("/CheckDup")
public class CheckProductDupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CheckProductDupServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("CallCheckProductDup");
    	
    	try{
	    	
	    		String url = SystemConfig.getProperty("CHECK_PRODUCT_DUP_SERVICE_URL");
	    		String idNo = req.getParameter("idNo");
	    			
	    		CheckProductDupRequestDataM cpdRequest = new CheckProductDupRequestDataM();
	    		
	    		cpdRequest.setIdNo(idNo);
	    		
		    	ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setServiceId(ServiceConstant.ServiceId.CheckProductDup);
					serviceRequest.setEndpointUrl(url);
					serviceRequest.setIgnoreServiceLog(true);
					serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
					serviceRequest.setObjectData(cpdRequest);
					
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest);
				
				HashMap<String,Object> data = new HashMap<String,Object>();			
				Gson gson = new Gson();
				data.put("jsonRq", gson.toJson(serviceResponse.getRequestObjectData()));
				data.put("jsonRs", gson.toJson(serviceResponse.getResponseObjectData()));
				
				ResponseUtils.sendJsonResponse(resp, data);			
	    	
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
	}
}
