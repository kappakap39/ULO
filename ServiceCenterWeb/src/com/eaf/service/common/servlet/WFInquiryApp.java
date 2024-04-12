package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
import com.eaf.service.module.manual.WFInquiryAppServiceProxy;
import com.eaf.service.module.model.WFInquiryAppRequestDataM;
import com.eaf.service.module.model.WFInquiryAppResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/WFInquiryApp")
public class WFInquiryApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(WFInquiryApp.class);
	private String Error = "Error";
    public WFInquiryApp() {
        super();
    }
    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"WFInquiryApp.properties";
    	public static final String errorFile = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"ErrorMessage.properties";
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.file));
			Properties prop = new Properties();
			prop.load(new FileReader(file));
			System.gc();

			PrintWriter pw = response.getWriter();
			pw.print(prop.getProperty(WFInquiryAppServiceProxy.url));
			pw.flush();
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			WFInquiryAppRequestDataM WFInquiryAppRequest = new WFInquiryAppRequestDataM();
			mapWFInquiryApp(request, WFInquiryAppRequest);
			
			File file = new File((filePath.errorFile));
			Properties prop = new Properties();
			prop.load(new FileReader(file));
			
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setServiceId(WFInquiryAppServiceProxy.serviceId);
				serviceRequest.setObjectData(WFInquiryAppRequest);
				
			ServiceResponseDataM resp = new ServiceResponseDataM();
			try{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				resp = proxy.requestService(serviceRequest);
			}catch(Exception e){
				logger.fatal("ERROR : ",e);
			}
			
			HashMap<String,Object> object = new HashMap<String,Object>();
			Gson gson = new Gson();
			object.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			object.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			WFInquiryAppResponseDataM WFInquiryApp = (WFInquiryAppResponseDataM)resp.getObjectData();
			if(null == WFInquiryApp){
				WFInquiryApp = new WFInquiryAppResponseDataM();
			}
			logger.debug("Gson : "+new Gson().toJson(WFInquiryApp));
			
			object.put(WFInquiryAppServiceProxy.responseConstants.noRecord, WFInquiryApp.getNoRecord());
			object.put(WFInquiryAppServiceProxy.responseConstants.applicationGroupM, WFInquiryApp.getApplicationGroupM());
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("save")){
			String url = request.getParameter("url_setting");
			File file = new File((filePath.file));
			Properties prop = new Properties();
			PrintWriter pw = new PrintWriter(file);
			prop.load(new FileReader(file));
			prop.setProperty(WFInquiryAppServiceProxy.url, url);
			prop.store(pw, null);
			
			System.gc();
		}
	}
	
	public void mapWFInquiryApp(HttpServletRequest request, WFInquiryAppRequestDataM WFInquiryAppRequest){
		String cisNo = request.getParameter("cis_no_input");
		String docNo = request.getParameter("doc_no_input");
		String docType = request.getParameter("doc_type_input");
		String thFirstName = request.getParameter("th_first_name_input");
		String thLastName = request.getParameter("th_last_name_input");
		String dob = request.getParameter("dob_input");
		String appStatus = request.getParameter("app_status_input");
		String bookedFlag = request.getParameter("booked_flag_input");
		String productCode = request.getParameter("product_code_input");
		String appFromDate = request.getParameter("app_date_from_input");
		String appToDate = request.getParameter("app_date_to_input");

		try{
			WFInquiryAppRequest.setCisNo(cisNo);
			WFInquiryAppRequest.setDocNo(docNo);
			WFInquiryAppRequest.setDocType(docType);
			WFInquiryAppRequest.setThFirstName(thFirstName);
			WFInquiryAppRequest.setThLastName(thLastName);
			WFInquiryAppRequest.setDob(dob.equals("") ? null : Date.valueOf(dob));
			WFInquiryAppRequest.setAppStatus(appStatus);
			WFInquiryAppRequest.setBookedFlag(bookedFlag);
			WFInquiryAppRequest.setProductCode(productCode);
			WFInquiryAppRequest.setAppFromDate(appFromDate.equals("") ? null : Date.valueOf(appFromDate));
			WFInquiryAppRequest.setAppToDate(appToDate.equals("") ? null : Date.valueOf(appToDate));
		}catch(Exception e){
			logger.fatal("Mapping WFInquiryAppRequestDataM error");
			logger.fatal("ERROR ",e);
		}		
	}

}
