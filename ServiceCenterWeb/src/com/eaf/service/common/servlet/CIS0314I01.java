package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.eaf.service.module.manual.CIS0314I01ServiceProxy;
import com.eaf.service.module.model.CIS0314I01RequestDataM;
import com.eaf.service.module.model.CIS0314I01ResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS0314I01")
public class CIS0314I01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CIS0314I01.class);
    public CIS0314I01() {
        super();
    }
    public static class filePath{
    	public static final String properties = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS0314I01WS.properties";
    }    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String processCode = request.getParameter("process");
		logger.debug("processCode >> "+processCode);
		if("defualt".equals(processCode)){
			File file = new File((filePath.properties));
			InputStream inSteam = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(inSteam);
			inSteam.close();
			
			HashMap<String, Object> data = new HashMap<String,Object>();
			data.put(CIS0314I01ServiceProxy.url,prop.getProperty(CIS0314I01ServiceProxy.url));
			data.put(CIS0314I01ServiceProxy.responseContant.customerId,prop.getProperty(CIS0314I01ServiceProxy.responseContant.customerId));
			data.put(CIS0314I01ServiceProxy.responseContant.customerType,prop.getProperty(CIS0314I01ServiceProxy.responseContant.customerType));
			data.put(CIS0314I01ServiceProxy.responseContant.thTitleName,prop.getProperty(CIS0314I01ServiceProxy.responseContant.thTitleName));
			data.put(CIS0314I01ServiceProxy.responseContant.thName,prop.getProperty(CIS0314I01ServiceProxy.responseContant.thName));
			data.put(CIS0314I01ServiceProxy.responseContant.thSurname,prop.getProperty(CIS0314I01ServiceProxy.responseContant.thSurname));
			data.put(CIS0314I01ServiceProxy.responseContant.engTitleName,prop.getProperty(CIS0314I01ServiceProxy.responseContant.engTitleName));
			data.put(CIS0314I01ServiceProxy.responseContant.engName,prop.getProperty(CIS0314I01ServiceProxy.responseContant.engName));
			data.put(CIS0314I01ServiceProxy.responseContant.engSurname,prop.getProperty(CIS0314I01ServiceProxy.responseContant.engSurname));
			data.put(CIS0314I01ServiceProxy.responseContant.srcAstCode,prop.getProperty(CIS0314I01ServiceProxy.responseContant.srcAstCode));
			data.put(CIS0314I01ServiceProxy.responseContant.srcAstOthDesc,prop.getProperty(CIS0314I01ServiceProxy.responseContant.srcAstOthDesc));
			data.put(CIS0314I01ServiceProxy.responseContant.policalPosDesc,prop.getProperty(CIS0314I01ServiceProxy.responseContant.policalPosDesc));
			data.put(CIS0314I01ServiceProxy.responseContant.astValAmt,prop.getProperty(CIS0314I01ServiceProxy.responseContant.astValAmt));
			data.put(CIS0314I01ServiceProxy.responseContant.riskReaCode,prop.getProperty(CIS0314I01ServiceProxy.responseContant.riskReaCode));
			data.put(CIS0314I01ServiceProxy.responseContant.astValCode,prop.getProperty(CIS0314I01ServiceProxy.responseContant.astValCode));
			data.put(CIS0314I01ServiceProxy.responseContant.astValDesc,prop.getProperty(CIS0314I01ServiceProxy.responseContant.astValDesc));
			data.put(CIS0314I01ServiceProxy.responseContant.riskLevelCode,prop.getProperty(CIS0314I01ServiceProxy.responseContant.riskLevelCode));
			
			ResponseUtils.sendJsonResponse(response, data);
		}else if("request".equals(processCode)){
			String url = request.getParameter("urlWebService");
			String customerType = request.getParameter("customerTypeInput");
			String customerId = request.getParameter("customerIdInput");
			
			CIS0314I01RequestDataM CIS0314I01Request = new CIS0314I01RequestDataM(); 
				CIS0314I01Request.setCustomerType(customerType);
				CIS0314I01Request.setCustomerId(customerId);
			
			ServiceRequestDataM serviceReq = new ServiceRequestDataM();
				serviceReq.setServiceId(CIS0314I01ServiceProxy.serviceId);
				serviceReq.setEndpointUrl(url);
				serviceReq.setIgnoreServiceLog(true);
				serviceReq.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceReq.setObjectData(CIS0314I01Request);
				
				ServiceResponseDataM resp = null;	
				try{
					ServiceCenterProxy proxy = new ServiceCenterProxy();
						resp = proxy.requestService(serviceReq);
				}catch(Exception e){
					e.printStackTrace();
				}
			HashMap<String,Object> data = new HashMap<String,Object>();
			Gson gson = new Gson();
			data.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			data.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			
			CIS0314I01ResponseDataM CIS0314I01Response = (CIS0314I01ResponseDataM)resp.getObjectData();
			if(null == CIS0314I01Response){
				CIS0314I01Response =  new CIS0314I01ResponseDataM();
			}
			data.put(CIS0314I01ServiceProxy.responseContant.customerId, CIS0314I01Response.getCustomerId());
			data.put(CIS0314I01ServiceProxy.responseContant.customerType, CIS0314I01Response.getCustomerType());
			data.put(CIS0314I01ServiceProxy.responseContant.thTitleName, CIS0314I01Response.getThaiTitleName());
			data.put(CIS0314I01ServiceProxy.responseContant.thName, CIS0314I01Response.getThaiName());
			data.put(CIS0314I01ServiceProxy.responseContant.thSurname, CIS0314I01Response.getThaiSurname());
			data.put(CIS0314I01ServiceProxy.responseContant.engTitleName, CIS0314I01Response.getEnglishTitleName());
			data.put(CIS0314I01ServiceProxy.responseContant.engName, CIS0314I01Response.getEnglishName());
			data.put(CIS0314I01ServiceProxy.responseContant.engSurname, CIS0314I01Response.getEnglishSurname());
			data.put(CIS0314I01ServiceProxy.responseContant.srcAstCode, CIS0314I01Response.getSoucreAssetCode());
			data.put(CIS0314I01ServiceProxy.responseContant.srcAstOthDesc, CIS0314I01Response.getSoucreAssetOtherDescription());
			data.put(CIS0314I01ServiceProxy.responseContant.policalPosDesc, CIS0314I01Response.getPolicalPostionDescription());
			data.put(CIS0314I01ServiceProxy.responseContant.astValAmt, CIS0314I01Response.getAssetValueAmount());
			data.put(CIS0314I01ServiceProxy.responseContant.riskReaCode, CIS0314I01Response.getRiskReasonCode());
			data.put(CIS0314I01ServiceProxy.responseContant.astValCode, CIS0314I01Response.getAssetValueCode());
			data.put(CIS0314I01ServiceProxy.responseContant.astValDesc, CIS0314I01Response.getAssetValueDescription());
			data.put(CIS0314I01ServiceProxy.responseContant.riskLevelCode, CIS0314I01Response.getRiskLevelCode());
			
			ResponseUtils.sendJsonResponse(response, data);
			
		}else if("save".equals(processCode)){
			String url = request.getParameter("urlWebServiceEdit");
			String customerId = request.getParameter("customerIdEdit");
			String customerType = request.getParameter("customerTypeEdit");
			String thTitleName = request.getParameter("thTitleNameEdit");
			String thName = request.getParameter("thNameEdit");
			String thSurname = request.getParameter("thSurnameEdit");
			String engTitleName = request.getParameter("engTitleNameEdit");
			String engName = request.getParameter("engNameEdit");
			String engSurname = request.getParameter("engSurnameEdit");
			String scrAstCode = request.getParameter("scrAstCodeEdit");
			String scrAstOthDesc = request.getParameter("scrAstOthDescEdit");
			String policalPosDesc = request.getParameter("policalPosDescEdit");
			String astValAmt = request.getParameter("astValAmtEdit");
			String riskReaCode = request.getParameter("riskReaCodeEdit");
			String astValCode = request.getParameter("astValCodeEdit");
			String astValDesc = request.getParameter("astValDescEdit");
			String riskLvCode = request.getParameter("riskLvCodeEdit");
			
			File file = new File((filePath.properties));
			FileOutputStream outputStream = new FileOutputStream(file);
			Properties prop = new Properties();
			
			prop.setProperty(CIS0314I01ServiceProxy.url, url);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.customerId, customerId);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.customerType, customerType);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.thTitleName, thTitleName);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.thName, thName);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.thSurname, thSurname);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.engTitleName, engTitleName);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.engName, engName);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.engSurname, engSurname);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.srcAstCode, scrAstCode);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.srcAstOthDesc, scrAstOthDesc);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.policalPosDesc, policalPosDesc);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.astValAmt, astValAmt);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.riskReaCode, riskReaCode);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.astValCode, astValCode);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.astValDesc, astValDesc);
			prop.setProperty(CIS0314I01ServiceProxy.responseContant.riskLevelCode, riskLvCode);
			
			prop.store(outputStream, null);
			
			outputStream.flush();
			outputStream.close();
		}
	}

}
