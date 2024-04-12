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

import com.eaf.core.ulo.common.performance.TraceController;
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
        
/**   
 * @author tarath
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
@WebServlet(value="/FrontController",loadOnStartup=-1,asyncSupported=false) 
public class FrontController extends HttpServlet{
	private static final long serialVersionUID = 1L;
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
	public static final int OUTSIDE_URL		= 11;
	private static final String INDEX_PAGE="index.jsp";
	private static final String POPUP_WINDOW_SCREEN="normal_popup.jsp";
	private static final String SESSION_TIMEOUT_PAGE="timeout.jsp";
	private static final String POPUP_DIALOG_SCREEN = "popup.jsp";
	@SuppressWarnings("unused")
	private static final String BLANK_SCREEN = "blank.jsp";
	private static final String LOGOUT_SCREEN = "logout.jsp";
	@SuppressWarnings("unused")
	private static final String NOT_AUTHORIZED_SCREEN= "notauthorized.jsp";
	
	private static String mappingXMLPath = "";
	private static String batchConfigPath = "";
	private static String logConfigPath = "";
	//private static SystemInitial sysInit = null;
	
	private static String webCharEncoder = "";
	
	//declare logger
	private static Logger logger;
		
	private HashMap<String,String> useSessionHash = null;
		
	/* - check whether user login
	 * - if it only redirect( page parameter !=null ), redirect it. 
	 * - if it has form, validate form
	 * - create event
	 * - send event to Backend process
	 * - get response back from backend
	 * - map ModelManager to FormHandler 
	 * - screenFlowManager redirect to the next page
	 */
	 
	/* 1) Check whether user is logined
	 */
	
	/* 2) If redirect only, redirect to appropriate JSPs
	 */
	
	/* 3) If form, validate form
	 */
	 
	//initial class from xml file
	public void init() throws ServletException  {
//		configuration path
		String logConfigPath = getServletContext().getRealPath("/WEB-INF/log4j.properties");
		String config = getServletContext().getRealPath("/WEB-INF/struts-config.xml");		
		
//		load logger(Log4J)
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure(logConfigPath);
			
		
		logger.info("===================== initial XML ====================");
		new LoadXML(config);
		
//		Init useSessionHash
		useSessionHash = getUseSessionHash();
		boolean loadCacheOnStartup = CacheController.loadCacheOnStartup(CacheServiceLocator.ORIG_DB);
		logger.debug("loadCacheOnStartup >> "+loadCacheOnStartup);
		LookupCacheDataM lookupCache = new LookupCacheDataM();
			lookupCache.setLookupName(CacheConstant.LookupName.ORIG);
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
			lookupCache.create(CacheConstant.CacheType.CACHE_PARAMETER,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.JAVASCRIPT_VARIABLE,loadCacheOnStartup);
			
		CacheController.startup(lookupCache);
	}
	
    public static void SystemProperty() {
    	System.setProperty("com.sun.media.jai.disableMediaLib", "true"); 
		System.setProperty("com.sun.media.imageio.disableCodecLib", "true");
    }
    
	public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
				
		String action = (String) request.getParameter("action");
		String page = (String) request.getParameter("page");
		String ajaxAction = request.getParameter("ajaxAction");
		
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
		
		// Check Session Time Out
		logger.debug("Service Action = "+ action);
		logger.debug("Service Page = "+ page);
		logger.debug("ajaxAction = "+ ajaxAction);
		
		UserDetailM userM = (UserDetailM) request.getSession(true).getAttribute("ORIGUser");		
		logger.debug("userM >> "+userM);
						
		if("UserAction".equalsIgnoreCase(action) || "LOGOUT_SCREEN".equalsIgnoreCase(page) || (userM != null && !Util.empty(userM.getUserName()))){	
			 String transactionId = (String)request.getSession().getAttribute("transactionId");
			 logger.debug("transactionId >> "+transactionId);
			 String traceName = !Util.empty(action)?action:page;
			 TraceController trace = new TraceController("FrontController",transactionId);
	    	 trace.create(traceName);
	    	 serviceProcess(request, response);
	    	 trace.end(traceName);
	    	 trace.trace();
		}else{
			logger.debug("SESSION_TIMEOUT..");
			request.getSession().invalidate();
			if("Y".equals(ajaxAction)){
				 ResponseDataController controller = new ResponseDataController();
				 	controller.timeout(response);
			}else{
				if("LoadPopupForm".equalsIgnoreCase(action)){
					response.sendRedirect(SESSION_TIMEOUT_PAGE);
				}else{
					response.sendRedirect(INDEX_PAGE);
				} 
			}
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
				
		// If the request type is PAGE
		logger.debug("page="+page);
		
		if(page != null && !page.equals("") && screenFlowManager!=null) {
			screenFlowManager.setNextScreenByPage(page);
		}
		
		logger.debug("getSession in screenFlowManager="+request.getSession(true).getAttribute("screenFlowManager")); 
		
		// If the request type is ACTION		
		logger.debug("action="+action);
		
		// ACTION = logout
		if(action !=null && action.equalsIgnoreCase("logout")){
			//clear Session
			request.getSession().invalidate(); 
		}
		 
			
		WebAction webAction = null;
		boolean eventResponseResult = false;
				
		// Default next activity is PAGE		
		int nextActivityType = PAGE;	
		
		// ACTION = other actions	
		if(!Util.empty(action)){
							 
			logger.debug("FormHandleManager: "+formHandleManager);
							
			boolean hasNoFormErrors = false;
			
			if(formHandleManager != null){
				//1. Validate form
				logger.debug("validating form");
				hasNoFormErrors = formHandleManager.processForm(request);
			}else{
				//2. No FormHandler Manager in Session
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
	
		 			//  2. Process action before calling model
				if(csrfToken){
			 			webAction.setRequest(request); 
			 			webAction.setResponse(response); 
//			 			webAction.setContext(getServletContext());
			 			
					if (webAction.validationForm()) {
			 			
						boolean frontEndResult = webAction.preModelRequest();  
						//  3. Process model request
		
						if(webAction.requiredModelRequest() && frontEndResult){
							
							EventResponse eventResp = (new RequestProcessor(request)).processRequest(webAction);
							
							if(eventResp == null){
								// There is error processing request in RequestProcessor object
								// Do not set the next page, similar to the regular form error
								logger.debug("EvenResponse is null");
							
							} else {
								
								// 4. Process response from model
								 						 
								boolean result = webAction.processEventResponse(eventResp);
								eventResponseResult = result;
								logger.debug("ProcessEventResponse: result is " + result);
								
								/* Akarapol: No need to remove current form in session. Let users remove them.
								if (formHandleManager != null) {
								if (result) {
									System.out.println("FrontController>> remove current form in session: " + formHandleManager.getCurrentFormHandler());
									formHandleManager.removeCurrentFormInSession(request);
								}
								}
								*/
							
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
							  
						} else {
								// Set the next page
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
				// There is form error. Do not set the next page. Stop here
				logger.debug("Form Error : hasNoFormErrors = false");
			}			
 		}
		
		if(webAction != null && nextActivityType != CSRF_TOKEN){
			if (eventResponseResult) {
				// If result from EventProcessor is true or
				// if there is no Model Request for this Web Action
			 	nextActivityType = webAction.getNextActivityType();
			}else{
				// If result Eventprocesser is false And NotForward Action Do Not Forward 
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
			case OUTSIDE_URL:
				String outsideAction = webAction.getNextActionParameter();
				logger.debug("FrontController>> redirecting....."+outsideAction);
				response.sendRedirect(outsideAction);
				break;
			case ACTION:
				String redirectedAction = webAction.getNextActionParameter();
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
