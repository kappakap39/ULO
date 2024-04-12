package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.SMSServiceProxy;
import com.eaf.service.module.model.SMSRequestDataM;
import com.eaf.service.module.model.SMSResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;

@WebServlet("/SmsService")
public class SmsService extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static transient Logger logger = Logger.getLogger(SmsService.class);
    public SmsService() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"SMSServiceWS.properties";
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String processCode = request.getParameter("process");
		logger.debug("processCode >> "+processCode);
		if("requestSMS".equals(processCode)){
			String url = request.getParameter("urlWebService");
			String mobileNo = request.getParameter("mobileNoElement");
			String msg = request.getParameter("msg");
			String templateId = request.getParameter("templateId");
			String smsLanguage = request.getParameter("smsLang");
			String departmentCode = request.getParameter("departmentCode");
			String priority = request.getParameter("priority");
			String messageType = request.getParameter("messageType");
			String clientId = request.getParameter("clientId");
			ArrayList<String> mobileNoElement = new ArrayList<String>();
				mobileNoElement.add(mobileNo);			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			serviceRequest.setServiceId(SMSServiceProxy.serviceId);
			serviceRequest.setEndpointUrl(url);			
			SMSRequestDataM smsRequest = new SMSRequestDataM();
				smsRequest.setMobileNoElement(mobileNoElement);
				smsRequest.setMessageType(msg);
				smsRequest.setTemplateId(Integer.parseInt(templateId));
				smsRequest.setSmsLanguage(smsLanguage);
				smsRequest.setDepartmentCode(departmentCode);
				smsRequest.setPriority(Integer.parseInt(priority));
				smsRequest.setMessageType(messageType);
				smsRequest.setClientId(clientId);			
			serviceRequest.setObjectData(smsRequest);
			ServiceResponseDataM serviceResponse = null;	
			try{
				serviceResponse = proxy.requestService(serviceRequest);
			}catch(Exception e){
				e.printStackTrace();
			}
			HashMap<String,Object> data = new HashMap<String,Object>();			
			try{
				String serviceReqResId = serviceResponse.getServiceReqResId();
				logger.debug("serviceReqResId >> "+serviceReqResId);
				ServiceRequestDataM jsonRqRs = ServiceFactory.getServiceDAO().getRequestResponseData(serviceReqResId);
				data.put("jsonRq", jsonRqRs.getRawData("requestJSON"));
				data.put("jsonRs", jsonRqRs.getRawData("responseJSON"));
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}			
			SMSResponseDataM SMSResponse = (SMSResponseDataM)serviceResponse.getObjectData();
			if(null == SMSResponse){
				SMSResponse = new SMSResponseDataM();
			}
			data.put(SMSServiceProxy.responseConstants.responseCode,SMSResponse.getResponseCode());
			data.put(SMSServiceProxy.responseConstants.responseDetail,FormatUtil.displayText(SMSResponse.getResponseDetail()));			
			ResponseUtils.sendJsonResponse(response, data);			
		}else if("editSMS".equals(processCode)){
			String url = request.getParameter("urlWebServiceEdit");
			String responseCode = request.getParameter("responseCodeEdit");
			String responseDetail = request.getParameter("responseDetailEdit");
			
			logger.debug("url >> "+url);
			logger.debug("responseCode >> "+responseCode);
			logger.debug("responseDetail >> "+responseDetail);

			if(url == null) url = "";
			if(responseCode == null) responseCode = "";
			if(responseDetail == null) responseDetail = "";

			File file = new File((filePath.file));
			FileOutputStream fileUpdate = new FileOutputStream(file);
			Properties props = new Properties();
			props.setProperty(SMSServiceProxy.url, url);
			props.setProperty(SMSServiceProxy.responseConstants.responseCode, responseCode);
			props.setProperty(SMSServiceProxy.responseConstants.responseDetail, responseDetail);
			props.store(fileUpdate, null);
			if(null != fileUpdate){
				fileUpdate.flush();
				fileUpdate.close();
			}
			System.gc();
			logger.debug("props : "+props);			
		}else if("defualt".equals(processCode)){
			File file = new File((filePath.file));
			FileInputStream fileInput = new FileInputStream(file);
			Properties props = new Properties();
			props.load(fileInput);
			fileInput.close();
			Map<String,Object> data = new HashMap<String,Object>();
			data.put(SMSServiceProxy.url, props.getProperty("urlWebService"));
			data.put(SMSServiceProxy.responseConstants.responseCode, props.getProperty("responseCode"));
			data.put(SMSServiceProxy.responseConstants.responseDetail, props.getProperty("responseDetail"));
			logger.debug("data : "+data);
			ResponseUtils.sendJsonResponse(response, data);
		}
		
	}

}
