package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import com.eaf.core.ulo.service.pega.UpdateApprovalStatus;
import com.eaf.inf.batch.ulo.pega.InfBatchUpdateApprovalStatus;
import com.eaf.inf.batch.ulo.pega.model.CSVContentDataM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusData;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusResponse;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;


@WebServlet("/UpdateApprovalStatusServlet")
public class UpdateApprovalStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(UpdateApprovalStatusServlet.class);	
    public UpdateApprovalStatusServlet() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"UpdateApprovalStatus.properties";
    }    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	logger.debug("UpdateApprovalStatusServlet");
    	String process = req.getParameter("process");
    	try{
	    	if("default".equals(process)){
	    		File file = new File(filePath.file);
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.load(fileInput);
				fileInput.close();
				
				HashMap<String,Object> data = new HashMap<>();
				data.put("url", properties.get("urlWebService"));
				ResponseUtils.sendJsonResponse(resp, data);
	    	}else if("request".equals(process)){
	    		String url = req.getParameter("UrlWebService");
	    		String applicationGroupId = req.getParameter("ApplicationGroupId");
	    		OrigApplicationGroupDAO origDAO = ORIGDAOFactory.getApplicationGroupDAO();
	    		ApplicationGroupDataM applicationGroup = origDAO.loadOrigApplicationGroupM(applicationGroupId);	   
	    		if(null != applicationGroup){
	    			applicationGroup = new ApplicationGroupDataM();
	    		}
	    		
	    		UpdateApprovalStatusData updateApprovalStatusData = UpdateApprovalStatus.validateUpdateApprovalStatus(applicationGroup);
	    		String isClose = updateApprovalStatusData.getIsClose();
				String isVetoEligible = updateApprovalStatusData.getIsVetoEligible();		
				logger.debug("updateApprovalStatusData : "+updateApprovalStatusData);
				UpdateApprovalStatusRequest updateApprovalRequest = new UpdateApprovalStatusRequest();
				if(MConstant.FLAG.NO.equals(isClose) && MConstant.FLAG.YES.equals(isVetoEligible)){
					ArrayList<ApplicationGroupDataM> applicationGroups = new ArrayList<ApplicationGroupDataM>();
						applicationGroups.add(applicationGroup);
					ArrayList<CSVContentDataM> csvContents = InfBatchUpdateApprovalStatus.mapCSVContent(applicationGroups,isClose);
						updateApprovalRequest.setCSVContent(InfBatchUpdateApprovalStatus.getCSVContent(csvContents));
				}
								
	    		ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
	    			serviceRequest.setEndpointUrl(url);
	    			serviceRequest.setServiceId(UpdateApprovalStatusServiceProxy.serviceId);
	    			serviceRequest.setIgnoreServiceLog(true);
					serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
	    			serviceRequest.setUniqueId(applicationGroupId);
	    			serviceRequest.setObjectData(updateApprovalRequest);
	    			
	    		ServiceCenterProxy proxy = new ServiceCenterProxy();
	    		ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
	    		UpdateApprovalStatusResponse updateApprovalStatusResponse = (UpdateApprovalStatusResponse)serivceResponse.getObjectData();
	    		if(null == updateApprovalStatusResponse){
	    			updateApprovalStatusResponse = new UpdateApprovalStatusResponse();
	    		}
	    		HashMap<String,Object> data = new HashMap<String,Object>();
	    		Gson gson = new Gson();
				data.put("jsonRq", gson.toJson(serivceResponse.getRequestObjectData()));
				data.put("jsonRs", gson.toJson(serivceResponse.getResponseObjectData()));
	    		ResponseUtils.sendJsonResponse(resp, data);
	    	}else if("save".equals(process)){
	    		String url = req.getParameter("UrlWebService");	    		
	    		File file = new File(filePath.file);
				InputStream inStream = new FileInputStream(file);
				PrintWriter writer = new PrintWriter(file);
				Properties prop = new Properties();
				prop.load(inStream);
				inStream.close();
				
				prop.setProperty("urlWebService", url);
				prop.store(writer, null);
				writer.close();
	    	}
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
    }
}
