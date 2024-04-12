package com.eaf.j2ee.pattern.control;
   
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.UseSessionHash;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.owasp.CSRFTokenUtil;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionFinder;
import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.lookup.model.LookupCacheDataM;

@SuppressWarnings("serial")
@WebServlet(value="/FrontController",loadOnStartup=-1,asyncSupported=false)
public class FrontController extends HttpServlet{
	
	public static final int PAGE 			= 0;
	public static final int ACTION 			= 1;
	public static final int NOTFORWARD 		= 3;
	public static final int REDIRECT 		= 4;
	public static final int FORWARD 		= 5;
	public static final int POPUP_DIALOG	= 6;
	public static final int OUTSIDE			= 7; // for redirect to other context root
	public static final int POPUP_WINDOW	= 8;
	public static final int CSRF_TOKEN		= 9;
	public static final int LOGOUT 			= 10;
	
	private static final String INDEX_PAGE = "index.jsp";
	private static final String POPUP_WINDOW_SCREEN = "normal_popup.jsp";
	private static final String SESSION_TIMEOUT_PAGE = "timeout.jsp";
	private static final String POPUP_DIALOG_SCREEN = "popup.jsp";
	@SuppressWarnings("unused")
	private static final String BLANK_SCREEN = "blank.jsp";
	private static final String LOGOUT_SCREEN = "logout.jsp";
	@SuppressWarnings("unused")
	private static final String NOT_AUTHORIZED_SCREEN= "notauthorized.jsp";
		
	private static String mappingXMLPath = "";
	private static String batchConfigPath = "";
	private static String logConfigPath = "";	
	private static String webCharEncoder = "";
	
	private static Logger logger;		
	private HashMap<String,String> useSessionHash = null;
		
	public void init() throws ServletException  {
		
		String logConfigPath = getServletContext().getRealPath("/WEB-INF/log4j.properties");
		String config = getServletContext().getRealPath("/WEB-INF/struts-config.xml");		
		
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure(logConfigPath);
		
		logger.info("===================== initial XML ====================");
		new LoadXML(config);
		
		useSessionHash = getUseSessionHash();
		
		logger.info("================== build up cache ====================");		
		boolean loadCacheOnStartup = CacheController.loadCacheOnStartup(CacheServiceLocator.WAREHOUSE_DB);
		logger.debug("loadCacheOnStartup >> "+loadCacheOnStartup);
		LookupCacheDataM lookupCache = new LookupCacheDataM();
			lookupCache.setLookupName(CacheConstant.LookupName.DM);
			lookupCache.setRuntime(CacheConstant.Runtime.SERVER);
			lookupCache.create(CacheConstant.CacheType.METAB_CACHE,CacheServiceLocator.WAREHOUSE_DB,"ORIG_METABCACHE",loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONSTANT,CacheServiceLocator.WAREHOUSE_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONFIG,CacheServiceLocator.WAREHOUSE_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.GENERAL_PARAM,CacheServiceLocator.WAREHOUSE_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.INFBATCH_CONFIG,CacheServiceLocator.WAREHOUSE_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.ENCRYPTION_CONFIG,CacheServiceLocator.WAREHOUSE_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.APPLICATION_DATE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.JAVASCRIPT_VARIABLE,loadCacheOnStartup);
		CacheController.startup(lookupCache);
		
	}
	
    public void SystemProperty() {
    	System.setProperty("com.sun.media.jai.disableMediaLib", "true"); 
		System.setProperty("com.sun.media.imageio.disableCodecLib", "true"); 
    }
    
	public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
				
		String action = (String) request.getParameter("action");
		String page = (String) request.getParameter("page");
		
		if (webCharEncoder.trim().length() == 0){
			webCharEncoder = request.getCharacterEncoding();
			
			if (webCharEncoder == null) {webCharEncoder = "";}
		}
		
		logger.info("..........webCharEncoder is " + webCharEncoder);
		
		if(action == null){
			action = "";
		}
		if(page == null){
			page = "";
		}
		
		logger.debug("Service Action = "+ action);
		logger.debug("Service Page = "+ page);
		
		UserDetailM userM = (UserDetailM)request.getSession(true).getAttribute("ORIGUser");			
		logger.debug("userM >> "+userM);
		
		if(userM != null && !Util.empty(userM.getUserName())){			
			serviceProcess(request, response);
		}else{
			logger.debug("SESSION_TIMEOUT >>>");
			request.getSession().invalidate();
			response.sendRedirect(SESSION_TIMEOUT_PAGE);
		}		
	}		 
	
	public void serviceProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{		
		request.getSession().setAttribute("error",null);
				
		String servletString = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/FrontController?";
		
		String page = (String) request.getParameter("page");
		String action = (String) request.getParameter("action");
		String removeSession = (String) request.getParameter("removeSession");

		logger.info("FrontController>> start service===================================================");
		
		logger.debug("Server Name >> "+ request.getServerName());
		ErrorUtil.clearErrorInSession(request);
		
		logger.debug("removeSession = "+ removeSession);
		if(removeSession !=null && removeSession.equals("Y")){
			clearSession(request);
		}
		
		ScreenFlowManager screenFlowManager = (ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
		
		if (screenFlowManager == null) {
			screenFlowManager = new ScreenFlowManager();
			request.getSession(true).setAttribute("screenFlowManager", screenFlowManager);
		}
										
		FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");	
		
		if (formHandleManager == null) {
			request.getSession(true).setAttribute("formHandlerManager", new FormHandleManager());
		}
		
		logger.debug("page="+page);
		
		if(page != null && !page.equals("") && screenFlowManager!=null) {
			screenFlowManager.setNextScreenByPage(page);
		}
		
		logger.debug("getSession in screenFlowManager="+request.getSession(true).getAttribute("screenFlowManager")); 
			
		logger.debug("action="+action);
		
		if(action !=null && action.equalsIgnoreCase("logout")){
			request.getSession().invalidate(); 
		}
					
		WebAction webAction = null;
		boolean eventResponseResult = false;
		
		int nextActivityType = PAGE;	
		
		if(!Util.empty(action)){
							 
			logger.debug("FormHandleManager: "+formHandleManager);
							
			boolean hasNoFormErrors = false;
			
			if(formHandleManager != null){
				logger.debug("validating form");
				hasNoFormErrors = formHandleManager.processForm(request);
			}else{
				logger.debug("No FormhandlerManager in session");
				hasNoFormErrors = true;
			}
			
			logger.debug("hasNoFormErrors="+hasNoFormErrors);			

			if(hasNoFormErrors){
				
				webAction = (WebAction) WebActionFinder.find(action);
				
				logger.debug("WebAction to be found :"+webAction);
				
				String userName = "";
				String AUTHENTICATION_LDAP = SystemConfig.getProperty("AUTHENTICATION_LDAP");		
				logger.debug("AUTHENTICATION_LDAP >> "+AUTHENTICATION_LDAP);
				if(MConstant.FLAG.YES.equals(AUTHENTICATION_LDAP)){
					userName = (String) request.getRemoteUser();	
					logger.debug("Get Remote User: "+userName);
				}else{
					userName = (String) request.getSession().getAttribute("userName");
					logger.debug("Get Session User: "+userName);
				}
				
				boolean csrfToken = this.CSRFToken(request, webAction);
				logger.info("Authorized CSRF Token >> "+csrfToken);	

				if(csrfToken){
			 			webAction.setRequest(request); 
			 			webAction.setResponse(response); 
//			 			webAction.setContext(getServletContext());
			 			
					if (webAction.validationForm()) {
			 			
						boolean frontEndResult = webAction.preModelRequest();  
						
						if(webAction.requiredModelRequest() && frontEndResult){
							
							EventResponse eventResp = (new RequestProcessor(request)).processRequest(webAction);
							
							if(eventResp == null){
								logger.debug("EvenResponse is null");							
							}else{
								boolean result = webAction.processEventResponse(eventResp);
								eventResponseResult = result;
								logger.debug("ProcessEventResponse: result is " + result);

								if (screenFlowManager == null) {
									screenFlowManager = new ScreenFlowManager();
									logger.info("about to call setNextScreenByAction but screenFlowManager == null. create new screenFlowManager in session");
									request.getSession().setAttribute("screenFlowManager", screenFlowManager);	
		
								}
								logger.debug("RESULT "+result);
								if(webAction.getNextActivityType() == POPUP_DIALOG || webAction.getNextActivityType() == POPUP_WINDOW){
									screenFlowManager.setNextPopupByAction(action, result);
								}else{
									screenFlowManager.setNextScreenByAction(action, result);
								}
							}							  
						}else{
							if(frontEndResult)
								eventResponseResult = true;
							else
								eventResponseResult = false;
								
							if(screenFlowManager == null){
								screenFlowManager = new ScreenFlowManager();
								logger.debug("about to call setNextScreenByAction but screenFlowManager == null. create new screenFlowManager in session");
								request.getSession().setAttribute("screenFlowManager", screenFlowManager);	
							}
							if(webAction.getNextActivityType() == POPUP_DIALOG || webAction.getNextActivityType() == POPUP_WINDOW){
								screenFlowManager.setNextPopupByAction(action, eventResponseResult);
							}else{
								screenFlowManager.setNextScreenByAction(action, eventResponseResult);
							}
						}
			 		}else{			 				
						eventResponseResult = false;
						logger.warn("Not Authorized Validatetion Form.. !!");
						if(screenFlowManager == null){
							screenFlowManager = new ScreenFlowManager();
							logger.debug("about to call setNextScreenByAction but screenFlowManager == null. create new screenFlowManager in session");
							request.getSession().setAttribute("screenFlowManager", screenFlowManager);	
						}
						if(webAction.getNextActivityType() == POPUP_DIALOG || webAction.getNextActivityType() == POPUP_WINDOW){
							screenFlowManager.setNextPopupByAction(action, eventResponseResult);
						}else{
							screenFlowManager.setNextScreenByAction(action, eventResponseResult);
						}
			 		}			
				} else {
					logger.warn("Not authorized CSRF token to perform this action .. !!");
					nextActivityType = CSRF_TOKEN;
				}
			}else{
				logger.debug("Form Error : hasNoFormErrors = false");
			}			
 		}
		
		if(webAction != null && nextActivityType != CSRF_TOKEN){
			if (eventResponseResult) {
			 	nextActivityType = webAction.getNextActivityType();
			}else{
				if(webAction.getNextActivityType() == FrontController.NOTFORWARD) 
						nextActivityType = FrontController.NOTFORWARD;
			}
		}
		logger.debug("nextActivityType = " + nextActivityType);

		logger.info("end service==================================================");  
		
		switch (nextActivityType) {
			case PAGE:
				logger.debug(">>>>>>>>INDEX_PAGE");
				response.sendRedirect(INDEX_PAGE);
				break;
			case ACTION:
				String redirectedAction= webAction.getNextActionParameter();
				logger.debug("FrontController>> redirecting....." + servletString + redirectedAction);
				response.sendRedirect(servletString + redirectedAction);
				break;		
			case NOTFORWARD:
			    logger.debug("Not Forward Front Controller");
			     break;	
			case REDIRECT:
				String redirectedPage= webAction.getNextActionParameter();
				logger.debug("redirectedPage = " + redirectedPage);
 				response.sendRedirect(request.getContextPath() + redirectedPage);
 				break;
 			case FORWARD:
				String forwardPage= webAction.getNextActionParameter();
				logger.debug("forwardPage = " + forwardPage);
				response.sendRedirect(forwardPage);
 				break;
 			case POPUP_DIALOG:
 				logger.debug(">>>>>>>>POPUP_DIALOG_SCREEN");
 				response.sendRedirect(POPUP_DIALOG_SCREEN);
 				break;
 			case POPUP_WINDOW:
				logger.debug(">>>>>>>>POPUP_WINDOW_SCREEN");
				response.sendRedirect(POPUP_WINDOW_SCREEN);
				break;
 			case OUTSIDE:
				String outsideURL= webAction.getNextActionParameter();
				logger.debug("FrontController>> redirecting....." + outsideURL);
				response.sendRedirect(outsideURL);
				break;
 			case CSRF_TOKEN:
 				String screenFlow = "NOT_AUTHORIZED_SCREEN";
 				if(!CSRFTokenUtil.SessionToken(request)){ 					
 					screenFlow = "SESSION_TIME_OUT_SCREEN";
 				}
 				screenFlowManager.setCurrentScreen(screenFlow);
				logger.debug(">>>>>>>>NOT_AUTHORIZED_SCREEN");
				response.sendRedirect(screenFlow);
 				break;
 			case LOGOUT:
				logger.debug(">>>>>>>>LOGOUT_SCREEN");
				response.sendRedirect(LOGOUT_SCREEN);
				break;
			default:
				response.sendRedirect(INDEX_PAGE);
				break;
		} 
		
	}
	public static String getMappingXMLPath() {
		return mappingXMLPath;
	}
	public static void setMappingXMLPath(String mappingXMLPath) {
		FrontController.mappingXMLPath = mappingXMLPath;
	}
	public static String getBatchConfigPath() {
		return batchConfigPath;
	}
	public static void setBatchConfigPath(String batchConfigPath) {
		FrontController.batchConfigPath = batchConfigPath;
	}	
	private void clearSession(HttpServletRequest request){		
		Enumeration<String> attributeAll = request.getSession().getAttributeNames();
		while(attributeAll.hasMoreElements()){			
			String attributeName = (String) attributeAll.nextElement();
			if(!useSessionHash.containsKey(attributeName)){
				request.getSession().removeAttribute(attributeName);
			}
		}		
	}	
	private HashMap<String,String> getUseSessionHash(){
		return UseSessionHash.getUseSessionHash();
	}
	public static String getLogConfigPath() {
		return logConfigPath;
	}
	public static void setLogConfigPath(String logConfigPath) {
		FrontController.logConfigPath = logConfigPath;
	}
	public static String getWebCharEncoder() {
		String result = "UTF-8";
		if (webCharEncoder.trim().length() > 0){
			result = webCharEncoder;
		}		
		return result;
	}
	public static void setWebCharEncoder(String webCharEncoder) {
		FrontController.webCharEncoder = webCharEncoder;
	}
	public boolean CSRFToken(HttpServletRequest request , WebAction webaction){
		logger.info("CSRF Token >> webaction.getCSRFToken >> "+webaction.getCSRFToken());
		if(webaction.getCSRFToken()){
			try {
				if(!CSRFTokenUtil.isValid(request)){
					return false;
				}
			}catch(Exception e){
				logger.fatal("Exception ",e);
				return false;
			}
		}
		return true;
	}
 }
