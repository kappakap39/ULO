package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
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
import com.eaf.service.module.manual.CIS1047O01ServiceProxy;
import com.eaf.service.module.model.CIS1047O01RequestDataM;
import com.eaf.service.module.model.CIS1047O01ResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS1047O01")
public class CIS1047O01 extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static transient Logger logger =Logger.getLogger(CIS1047O01.class);
    public CIS1047O01() {
        super();
    }
    
    public static class filePath{
    	public static String properties = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS1047O01WS.properties";
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
			object.put(CIS1047O01ServiceProxy.url,prop.getProperty(CIS1047O01ServiceProxy.url));
			object.put(CIS1047O01ServiceProxy.responseConstants.cusId,prop.getProperty(CIS1047O01ServiceProxy.responseConstants.cusId));
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			String hub_number = request.getParameter("hub_number");
			String branch_code = request.getParameter("branch_code");
			String data_type_code = request.getParameter("data_type");
			String customer_id = request.getParameter("customer_id");
			String contact_address_id = request.getParameter("contact_address_id");
			String contact_type = request.getParameter("contact_type");
			String contact_function_type = request.getParameter("contact_function_type");
			 
			
			
			CIS1047O01RequestDataM CIS1047O01Request = new CIS1047O01RequestDataM();	
 
			CIS1047O01Request.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
			CIS1047O01Request.setBranchNo(branch_code);
			CIS1047O01Request.setContactAddressNum(contact_address_id);
			CIS1047O01Request.setContactFunctionTypeCode(contact_function_type);
			CIS1047O01Request.setContactTypeCode(contact_type);
			CIS1047O01Request.setCustomerId(customer_id);
			CIS1047O01Request.setDataTypeCode(data_type_code);
			CIS1047O01Request.setHubNo(hub_number);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS1047O01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1047O01Request);
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
			
			CIS1047O01ResponseDataM  CIS1047O01Response = (CIS1047O01ResponseDataM)resp.getObjectData();
			if(null ==  CIS1047O01Response){
				CIS1047O01Response = new  CIS1047O01ResponseDataM();
			}
			object.put(CIS1047O01ServiceProxy.responseConstants.cusId, CIS1047O01Response.getCustomerId());
			
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
			
			prop.setProperty(CIS1047O01ServiceProxy.url, url);
			prop.setProperty(CIS1047O01ServiceProxy.responseConstants.cusId, cusId);
			prop.store(writer, String.valueOf("update date :"+new Date(System.currentTimeMillis())));
			writer.close();
			
		}
	}
}
