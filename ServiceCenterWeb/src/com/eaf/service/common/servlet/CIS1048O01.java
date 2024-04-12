package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
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
import com.eaf.service.module.manual.CIS1048O01ServiceProxy;
import com.eaf.service.module.model.CIS1048O01AddressDataM;
import com.eaf.service.module.model.CIS1048O01RequestDataM;
import com.eaf.service.module.model.CIS1048O01ResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS1048O01")
public class CIS1048O01 extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static transient Logger logger =Logger.getLogger(CIS1048O01.class);
    public CIS1048O01() {
        super();
    }
    
    public static class filePath{
    	public static String properties = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS1048O01WS.properties";
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
			object.put(CIS1048O01ServiceProxy.url,prop.getProperty(CIS1048O01ServiceProxy.url));
			object.put(CIS1048O01ServiceProxy.responseConstants.cusId,prop.getProperty(CIS1048O01ServiceProxy.responseConstants.cusId));
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			String kbank_emp_id = request.getParameter("kbank_emp_id");
			String hub_number = request.getParameter("hub_number");
			String branch_code = request.getParameter("branch_code");
			String customer_type = request.getParameter("customer_type");
			String customer_id = request.getParameter("customer_id");
			String contact_address_id = request.getParameter("contact_address_id");
			String[] addressTypes = request.getParameterValues("addressType");
			 
			
			logger.debug("addressTypes>>"+addressTypes);
			CIS1048O01RequestDataM CIS1048O01Request = new CIS1048O01RequestDataM();	
			CIS1048O01Request.setBranchNo(branch_code);
			CIS1048O01Request.setContactAddressId(contact_address_id);
			ArrayList<CIS1048O01AddressDataM> CIS1048O01AddressTypes = new ArrayList<CIS1048O01AddressDataM>();
			if(null!=addressTypes && addressTypes.length>0){
				for(String addrType : addressTypes){
					CIS1048O01AddressDataM CIS1048O01addressType = new CIS1048O01AddressDataM();
					CIS1048O01addressType.setAddressType(addrType);
					CIS1048O01AddressTypes.add(CIS1048O01addressType);
				}
			}
			
			CIS1048O01Request.setContactAddressTypes(CIS1048O01AddressTypes);
			CIS1048O01Request.setCustomerId(customer_id);
			CIS1048O01Request.setCustomerType(customer_type);
			CIS1048O01Request.setHubNo(hub_number);
			CIS1048O01Request.setUserId(kbank_emp_id);
					
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS1048O01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1048O01Request);
				logger.debug("serviceRequest>>"+ new Gson().toJson(serviceRequest));
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
			
			CIS1048O01ResponseDataM  CIS1048O01Response = (CIS1048O01ResponseDataM)resp.getObjectData();
			if(null ==  CIS1048O01Response){
				CIS1048O01Response = new  CIS1048O01ResponseDataM();
			}
			object.put(CIS1048O01ServiceProxy.responseConstants.cusId, CIS1048O01Response.getCustomerId());
			
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
			
			prop.setProperty(CIS1048O01ServiceProxy.url, url);
			prop.setProperty(CIS1048O01ServiceProxy.responseConstants.cusId, cusId);
			prop.store(writer, String.valueOf("update date :"+new Date(System.currentTimeMillis())));
			writer.close();
			
		}
	}
}