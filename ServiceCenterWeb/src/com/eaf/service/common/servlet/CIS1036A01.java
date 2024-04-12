package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.eaf.service.module.manual.CIS1036A01ServiceProxy;
import com.eaf.service.module.model.CIS1036A01RequestDataM;
import com.eaf.service.module.model.CIS1036A01ResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS1036A01")
public class CIS1036A01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CIS1036A01.class);

    public CIS1036A01() {
        super();
    }
    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"CIS1036A01WS.properties";
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.file));
			InputStream inStream = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(inStream);
			inStream.close();
			
			HashMap<String,Object> object = new HashMap<>();
			object.put(CIS1036A01ServiceProxy.url,prop.getProperty(CIS1036A01ServiceProxy.url));
			object.put(CIS1036A01ServiceProxy.responseConstants.cusId,prop.getProperty(CIS1036A01ServiceProxy.responseConstants.cusId));
			object.put(CIS1036A01ServiceProxy.responseConstants.addrId,prop.getProperty(CIS1036A01ServiceProxy.responseConstants.addrId));
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			
			String userId = request.getParameter("userId_input");
			String hubNo = request.getParameter("hubNo_input");
			String brNo = request.getParameter("brNo_input");
			String confirmFlag = request.getParameter("confirmFlag_input");
			String validateFlag = request.getParameter("validateFlag_input");
			String cusType = request.getParameter("cusType_input");
			String cusId = request.getParameter("cusId_input");
			String cusTypeCode = request.getParameter("cusTypeCode_input");
			
			String addrId = request.getParameter("addrId_input");
			String addrName = request.getParameter("addrName_input");
			String mailBoxNo = request.getParameter("mailBoxNo_input");
			String mailNo = request.getParameter("mailNo_input");
			String moo = request.getParameter("moo_input");
			String mooban = request.getParameter("mooban_input");
			String build = request.getParameter("build_input");
			String room = request.getParameter("room_input");
			String floor = request.getParameter("floor_input");
			String soi = request.getParameter("soi_input");
			String road = request.getParameter("road_input");
			String tumbol = request.getParameter("tunbol_input");
			String amphur = request.getParameter("amphur_input");
			String province = request.getParameter("province_input");
			String postcode = request.getParameter("postcode_input");
			String country = request.getParameter("country_input");
			String line1 = request.getParameter("line1_input");
			String line2 = request.getParameter("line2_input");
						
			CIS1036A01RequestDataM CIS1036A01Request = new CIS1036A01RequestDataM();
				CIS1036A01Request.setUserId(userId);
				CIS1036A01Request.setHubNo(hubNo);
				CIS1036A01Request.setBranchNo(brNo);
				CIS1036A01Request.setConfirmFlag(confirmFlag);
				CIS1036A01Request.setValidateFlag(validateFlag);
				CIS1036A01Request.setCustomerType(cusType);
				CIS1036A01Request.setCustomerId(cusId);
				CIS1036A01Request.setCustomerTypeCode(cusTypeCode);
				
				CIS1036A01Request.setAddressId(addrId);
				CIS1036A01Request.setAddressName(addrName);
				CIS1036A01Request.setMailBoxNo(mailBoxNo);
				CIS1036A01Request.setMailNo(mailNo);
				CIS1036A01Request.setMoo(moo);
				CIS1036A01Request.setMooban(mooban);
				CIS1036A01Request.setBuilding(build);
				CIS1036A01Request.setRoom(room);
				CIS1036A01Request.setFloor(floor);
				CIS1036A01Request.setSoi(soi);
				CIS1036A01Request.setRoad(road);
				CIS1036A01Request.setTumbol(tumbol);
				CIS1036A01Request.setAmphur(amphur);
				CIS1036A01Request.setProvince(province);
				CIS1036A01Request.setPostCode(postcode);
				CIS1036A01Request.setCountry(country);
				CIS1036A01Request.setLine1(line1);
				CIS1036A01Request.setLine2(line2);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS1036A01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1036A01Request);
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
			
			CIS1036A01ResponseDataM CIS1036A01Response = (CIS1036A01ResponseDataM)resp.getObjectData();
			if(null == CIS1036A01Response){
				CIS1036A01Response = new CIS1036A01ResponseDataM();
			}
			object.put(CIS1036A01ServiceProxy.responseConstants.cusId, CIS1036A01Response.getCustomerId());
			object.put(CIS1036A01ServiceProxy.responseConstants.addrId, CIS1036A01Response.getAddressId());
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("save")){
			String url = request.getParameter("url_setting");
			String cusId = request.getParameter("cusId_setting");
			String addrId = request.getParameter("addrId_setting");
			
			File file = new File((filePath.file));
			InputStream inStream = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(inStream);
			
			OutputStream out = new FileOutputStream(file);
			prop.setProperty(CIS1036A01ServiceProxy.url,url);
			prop.setProperty(CIS1036A01ServiceProxy.responseConstants.cusId, cusId);
			prop.setProperty(CIS1036A01ServiceProxy.responseConstants.addrId, addrId);
			prop.store(out, null);
			out.close();
		}
	}

}
