package com.eaf.service.common.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.eaf.j2ee.pattern.model.ScreenFlow;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.lookup.model.LookupCacheDataM;

@WebServlet(value="/ServiceController",loadOnStartup=-1,asyncSupported=false)
public class ServiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger;
	public static String webContentPath = "";
	static HashMap<String,String> screenController = null;
    @Override
    public void init() throws ServletException {
    	webContentPath = getServletContext().getRealPath("");
    	String logConfigPath = getServletContext().getRealPath("/WEB-INF/log4j.properties");
    	logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure(logConfigPath);
    	logger.debug("init()..");
    	boolean loadCacheOnStartup = CacheController.loadCacheOnStartup(CacheServiceLocator.ORIG_DB);
		logger.debug("loadCacheOnStartup >> "+loadCacheOnStartup);
    	LookupCacheDataM lookupCache = new LookupCacheDataM();
			lookupCache.setLookupName(CacheConstant.LookupName.SERVICE_CENTER);
			lookupCache.setRuntime(CacheConstant.Runtime.SERVER);
			lookupCache.create(CacheConstant.CacheType.METAB_CACHE,CacheServiceLocator.ORIG_DB,"ORIG_METABCACHE",loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONSTANT,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.GENERAL_PARAM,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.INFBATCH_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.ENCRYPTION_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.APPLICATION_DATE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.REPORT_PARAM,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SERVICE_TYPE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SIMULATE_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
		CacheController.startup(lookupCache);
    }
    public static String getScreenController(String screenId){
    	if(null == screenController){
    		screenController = new HashMap<String,String>();
    		createScreenController();
    	}
    	return screenController.get(screenId);
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(null == screenController){
    		screenController = new HashMap<String,String>();
    		createScreenController();
    	}
    	String screenId = request.getParameter("screenId");
    	logger.debug("screenId >> "+screenId);
    	ScreenFlow screenFlow = (ScreenFlow)request.getSession().getAttribute("ScreenFlow");
    	if(null == screenFlow){
    		screenFlow = new ScreenFlow();
    		request.getSession().setAttribute("ScreenFlow",screenFlow);
    	}
    	screenFlow.setScreenId(screenId);
    	response.sendRedirect(getScreenController(screenId));
    }
    private static void createScreenController(){
    	screenController.put("SMSServiceWS","jsp/SMSServiceWS.jsp");
    	screenController.put("CIS0222I01WS","jsp/CIS0222I01WS.jsp");
    	screenController.put("CBS1215I01WS","jsp/CBS1215I01WS.jsp");
    	screenController.put("CIS0314I01WS","jsp/CIS0314I01WS.jsp");
    	screenController.put("CIS1046A01WS","jsp/CIS1046A01WS.jsp");
    	screenController.put("CIS1035A01WS","jsp/CIS1035A01WS.jsp");
    	screenController.put("CIS0315U01WS","jsp/CIS0315U01WS.jsp");    	
    	screenController.put("CIS1036A01WS","jsp/CIS1036A01WS.jsp");
    	screenController.put("CIS1034A01WS","jsp/CIS1034A01WS.jsp");
    	screenController.put("CIS1037A01WS","jsp/CIS1037A01WS.jsp");
    	//New CR4
    	screenController.put("CIS1044U01WS","jsp/CIS1044U01WS.jsp");
    	screenController.put("CIS1047O01WS","jsp/CIS1047O01WS.jsp ");
    	screenController.put("CIS1048O01WS","jsp/CIS1048O01WS.jsp");
    	screenController.put("CVRSDQ0001WS","jsp/CVRSDQ0001WS.jsp");
    	//------------------------------------------------------
    	screenController.put("FindManager","jsp/FindSaleManager.jsp");
    	screenController.put("Fico","jsp/FicoWS.jsp");
    	screenController.put("CreateFicoResponse","jsp/CreateFicoResponse.jsp");
    	screenController.put("WFInquiryApp","jsp/WFInquiryApp.jsp");
    	screenController.put("CheckProductDup","jsp/CheckProductDup.jsp");
    	screenController.put("CreateKVIApplication","jsp/CreateKVIApplication.jsp");
    	screenController.put("EditKVIApplication","jsp/EditKVIApplication.jsp");
    	screenController.put("ImportExportData","jsp/ImportExportData.jsp");
    	screenController.put("BatchReport","jsp/BatchReport.jsp");
    	screenController.put("UpdateApprovalStatus","jsp/UpdateApprovalStatus.jsp");
    	screenController.put("FollowUp","jsp/FollowUp.jsp");
    	screenController.put("UpdateCallOperator","jsp/UpdateCallOperator.jsp");
    	screenController.put("FollowUpResult","jsp/FollowUpResult.jsp");
    	screenController.put("SubmitApplication","jsp/SubmitApplication.jsp");
    	screenController.put("CallIM","jsp/CallIM.jsp");
    	screenController.put("CreateCISCustomer","jsp/CreateCISCustomer.jsp");
    	screenController.put("Iibservice", "jsp/Iibservice.jsp");
    	screenController.put("InfBatchLog","jsp/InfBatchLog.jsp");
    	screenController.put("QRInfo","jsp/QRInfo.jsp");
    	screenController.put("PerformanceReport","report/index.jsp");
    	screenController.put("TransactionLog","log/index.jsp");
    	screenController.put("ConnectivityTest","jsp/ConnectivityTest.jsp");
    	screenController.put("MLPInfo","jsp/MLPInfo.jsp");
    	screenController.put("QueryBox","jsp/QueryBox.jsp");
    	screenController.put("LogBrowser","jsp/LogBrowser.jsp");
    	screenController.put("DashboardConfig","jsp/DashboardConfig.jsp");
    	screenController.put("UnlockIADupSubmit","jsp/UnlockIADupSubmit.jsp");
    	screenController.put("PatchSetupDate","jsp/PatchSetupDate.jsp");
    	screenController.put("BPMUtil","jsp/BPMUtil.jsp");
    }
}
