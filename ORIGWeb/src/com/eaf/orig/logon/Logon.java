package com.eaf.orig.logon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.factory.EngineFactory;
import com.eaf.core.ulo.common.model.MenuHandlerManager;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.ObjectM;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.j2ee.pattern.control.util.LogoutUtil;
import com.eaf.orig.filter.LastAuthenticationErrorHelper;
import com.eaf.orig.logon.dao.OrigLogOnDAO;
import com.eaf.orig.menu.DAO.MenuMDAO;
import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.constant.MConstant;
import com.master.model.menu.MenuM;

@WebServlet(value="/Logon")
public class Logon extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(Logon.class);
	private static boolean dashboardSystem = true;
	private static Timestamp lastLogonDate;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");	
		if(!Util.empty(userName)){
			userName = userName.toUpperCase();
		}
		logger.debug("userName : "+userName);
//		logger.debug("password : "+password);
		initLogon(userName, password, request);
		List<String> errorLogon = new ArrayList<String>();
		try{
			IASServiceResponse serviceResponse = authenIAS(userName,password,request, errorLogon);
			if(Util.empty(errorLogon)) {
				String errorLdap = authenLDAP(userName, password, request, response);
				if(!Util.empty(errorLdap)) {
					errorLogon.add(errorLdap);
				}
			}
			if(Util.empty(errorLogon)) {
				request.getSession().setAttribute("FirstLogon",MConstant.FLAG.YES);
				request.getSession().setAttribute("userName",userName);
				request.getSession().setAttribute("password",password);
				request.getSession().setAttribute("linkUrl",LogoutUtil.getUrl(request));
				processLogon(request, serviceResponse);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			errorLogon.add(LogonEngine.ERROR_PLEASE_CONTRACT_ADMIN);
		}
		logger.info("errorLogon : "+errorLogon);
		if(Util.empty(errorLogon)){
			String url = getRedirectUrl(MConstant.LOGON, request);
			logger.debug("url : "+url);
			response.sendRedirect(url);
		}else{
			String errorCode = errorLogon.get(0);
			logger.error("errorCode : "+errorCode);
			request.getSession().setAttribute("ERROR_CODE",LogonEngine.getCode(errorCode));
			LogonDataM logon = (LogonDataM)request.getSession().getAttribute("LogonData");
				logon.setAction(MConstant.LOGON);
				logon.setType(MConstant.PROCESS.FAIL);
				logon.setCreateDate(ApplicationDate.getTimestamp());
			try{
				ORIGServiceProxy.getControlLogManager().saveLog(logon);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			logger.debug("LogonEngine.getCode : "+LogonEngine.getCode(errorCode));
			response.sendRedirect("origLogon.jsp?ERROR_CODE="+LogonEngine.getCode(errorCode));
		}
	}
	
	private void initLogon(String userName,String password,HttpServletRequest request){
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("password");
		request.getSession().removeAttribute("FirstLogon");
		request.getSession().removeAttribute("ERROR_CODE");
		request.getSession().removeAttribute("ERROR_MSG");
		LogonDataM logon = new LogonDataM();
			logon.setUserName(userName);
		request.getSession().setAttribute("LogonData",logon);
		request.getSession().setAttribute("LogonUserName",userName);
		request.getSession().setAttribute("LogonPassword",password);
	}
	
	private IASServiceResponse authenIAS(String userName,String password,HttpServletRequest request, List<String> errorLogon){
		String IAS_SERVICE_AUTHENTICATION_URL = SystemConfig.getProperty("IAS_SERVICE_AUTHENTICATION_URL");
		String CHECK_LOGON_FLAG = SystemConfig.getProperty("CHECK_LOGON_FLAG");
		if(Util.empty(userName)){
			errorLogon.add(LogonEngine.ERROR_REQUIRE_USERNAME);
		}
		if(Util.empty(password)){
			errorLogon.add(LogonEngine.ERROR_REQUIRE_PASSWORD);
		}
		IASServiceResponse serviceResponse = null;
		if(!Util.empty(userName) && !Util.empty(password)){
			try{
				IASServiceRequest serviceRequest = new IASServiceRequest();
					serviceRequest.setUserName(userName);
					serviceRequest.setPassword(password);
					serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);				
					RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
						@Override
						protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
					        if(connection instanceof HttpsURLConnection ){
					            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
									@Override
									public boolean verify(String arg0, SSLSession arg1) {
										return true;
									}
								});
					        }
							super.prepareConnection(connection, httpMethod);
						}
					});
				ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_AUTHENTICATION_URL,serviceRequest,IASServiceResponse.class);
				serviceResponse = responseEntity.getBody();
				String authResult = serviceResponse.getAuthResult();
				logger.info("authResult ="+authResult);
				if(authResult.indexOf("Logon Fail") == -1){			 
					Vector<RoleM> vRoles = serviceResponse.getRoles();
					Vector<ObjectM> vObject = serviceResponse.getObjects();
					if (vRoles == null || vRoles.size() == 0 || vObject == null || vObject.size() == 0){					
						errorLogon.add(LogonEngine.ERROR_USER_CONFIGURATION_NOT_COMPLETED);
					}		
					logger.info("CHECK_LOGON_FLAG >> "+CHECK_LOGON_FLAG);
					OrigLogOnDAO logOnDAO = ModuleFactory.getOrigLogOnDAO();								
					String LOGON_FLAG = logOnDAO.logonOrigApp(userName);
					logger.info("LOGON_FLAG >> "+LOGON_FLAG);
					if(MConstant.FLAG.NOTFOUND.equals(LOGON_FLAG)){ 
						errorLogon.add(LogonEngine.ERROR_USER_CONFIGURATION_NOT_COMPLETED);
					}else if(MConstant.FLAG.INACTIVE.equals(LOGON_FLAG)){
						errorLogon.add(LogonEngine.ERROR_INACTIVE_USER);
					}else if(MConstant.FLAG.YES.equals(LOGON_FLAG) && MConstant.FLAG.YES.equals(CHECK_LOGON_FLAG)){
						errorLogon.add(LogonEngine.ERROR_LOGON_ANOTHER_COMPUTER);
					}
				}else{
					errorLogon.add(LogonEngine.ERROR_MSG);
					request.getSession().setAttribute("ERROR_MSG",authResult);
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				errorLogon.add(LogonEngine.ERROR_PLEASE_CONTRACT_ADMIN);
			}
		}
		return serviceResponse;
	}
	
	private String authenLDAP(String userName,String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String ERROR_CODE = "";
		boolean isServletAuthen = request.authenticate(response);
		logger.debug("isServletAuthen : " + isServletAuthen);
		logger.debug("Authen method : "+request.getAuthType());
		try {
			if(isServletAuthen){
				request.logout();
			}
			request.login(userName, password);
			logger.debug("result : "+request.authenticate(response));
		}catch(Exception e){
			LastAuthenticationErrorHelper errorHelper = new LastAuthenticationErrorHelper();
				Throwable rootCause = errorHelper.getRootCause();
				String exception = errorHelper.getException();
			if(rootCause!=null){
				// Map error code
				logger.debug("Cause Message : "+rootCause.getCause().getMessage());
				logger.debug("Cause toString : "+rootCause.getCause().toString());
				logger.debug("Cause ClassName : "+rootCause.getClass().getName());
				rootCause.getCause().printStackTrace();
				ERROR_CODE = LdapErrorUtility.getError(rootCause.getCause().toString(),exception);
			}
		}
		logger.debug("ERROR_CODE : "+ERROR_CODE);
		return ERROR_CODE;
	}
	
	private void processLogon(HttpServletRequest request, IASServiceResponse serviceResponse) throws Exception{
		String AUTHENTICATION_LDAP = SystemConfig.getProperty("AUTHENTICATION_LDAP");
		String userName = "";
		request.getSession(true).removeAttribute("GEMenu");
		request.getSession(true).removeAttribute("ORIGUser");				
		logger.debug("AUTHENTICATION_LDAP >> "+AUTHENTICATION_LDAP);
		if(MConstant.FLAG.YES.equals(AUTHENTICATION_LDAP)){
			userName = (String)request.getRemoteUser();	
			logger.debug("..getRemoteUser()=>userName is "+userName);
		}else{
			userName = (String)request.getSession().getAttribute("userName");
			logger.debug("..getSessionUser()=>userName is "+userName);
		}		
		if(!Util.empty(userName)){
			request.getSession().setAttribute("userName",userName);
			SessionControl.defaultLocal(request);		
			SessionControl.clearSession(request);
			loadProfile(request, userName, serviceResponse);
			loadMenu(request);
			LogonEngine.processLogon(request,MConstant.PROCESS.SUCCESS);
		}else{
			processLogout(LogonEngine.ERROR_USER_CONFIGURATION_NOT_COMPLETED,"", request);
		}
	}
	
	private void processLogout(String errorCode,String errorMsg, HttpServletRequest request){
		if(!Util.empty(errorCode)){
			request.getSession().setAttribute("ERROR_CODE",LogonEngine.getCode(errorCode));
			request.getSession().setAttribute("ERROR_MSG",errorMsg);
		}
		LogonEngine.processLogout(request,(!Util.empty(errorCode))?MConstant.PROCESS.FAIL:MConstant.PROCESS.SUCCESS);
	}
	
	private void loadMenu(HttpServletRequest request) throws Exception{
		Vector v = (Vector)request.getSession().getAttribute("menuIds");
		Vector vRoles = (Vector)request.getSession().getAttribute("UserRoles");
		Vector<MenuM> vecMenus = null;
		try{
			MenuMDAO menudao = (MenuMDAO)EngineFactory.getMenuMDAO();	
			vecMenus = menudao.loadMenus(v,vRoles);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		if(null == vecMenus){
			vecMenus = new Vector<MenuM>();
		}
		MenuHandlerManager menuHandler = new MenuHandlerManager();
		ArrayList<MenuM> topLevelMenus = new ArrayList<MenuM>();
		HashMap<String,ArrayList<MenuM>> subMenus = new HashMap<String, ArrayList<MenuM>>();
		if(!Util.empty(vecMenus)){
			for(MenuM menuM :vecMenus){
				String menuId = menuM.getMenuID();
				logger.debug("menuId : "+menuId);
				if("EX000".equalsIgnoreCase(menuM.getMenuID().trim())) {
					continue;
				}		
				if("LABEL".equalsIgnoreCase(menuM.getMenuType().trim())) {		
					topLevelMenus.add(menuM);
				}else{
					ArrayList<MenuM> subGroup = subMenus.get(menuM.getMenuReference());			
					if(null == subGroup){
						subGroup = new ArrayList<MenuM>();
						subMenus.put(menuM.getMenuReference(), subGroup);
					}
					subGroup.add(menuM);
				}		
			}
		}
		logger.debug("topLevelMenus : "+topLevelMenus.size());
		logger.debug("subMenus : "+subMenus.size());
		menuHandler.setTopLevelMenus(topLevelMenus);
		menuHandler.setSubMenus(subMenus);
		request.getSession(true).setAttribute("menuHandlerManager",menuHandler);
		request.getSession(true).setAttribute("vecMenus", vecMenus);
	}	
	
	private void loadProfile(HttpServletRequest request, String userName, IASServiceResponse serviceResponse) throws Exception{
		lastLogonDate = ApplicationDate.getTimestamp();
		UserDetailM userM = ORIGDAOFactory.getUserProfileDAO().getUserProfile(userName);
		userM.setLastLogonDate(lastLogonDate);
		userM = loadRole( request, userM, userName, serviceResponse);			
		request.getSession(true).setAttribute("UserRoles",userM.getRoles().clone());
		request.getSession(true).setAttribute("ORIGUser",userM);
		request.getSession(true).setAttribute("OfficeCode",userM.getDefaultOfficeCode());	
	}
	
	private UserDetailM loadRole(HttpServletRequest request, UserDetailM userM, String userName, IASServiceResponse serviceResponse) throws Exception{
		IASServiceRequest serviceRequest = new IASServiceRequest();
		serviceRequest.setUserName(userName);
		serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);	
		if(null==serviceResponse){
			serviceResponse = new IASServiceResponse();
		}
		ArrayList<String> Systems = new ArrayList<String>();	
		Vector<RoleM> vRoles = serviceResponse.getRoles();
		Vector<String> roles = new Vector<String>();
		if(!Util.empty(vRoles)) {
			for(int i = 0; i < vRoles.size(); i++){
				RoleM role = (RoleM) vRoles.get(i);
				roles.add(role.getRoleName());
			}
		}
		userM.setRoles(roles);
		Vector<String> v = new Vector<String>();		
		Vector<ObjectM> vObject = serviceResponse.getObjects();
		if(null!=vObject)
		for(int i = 0; i < vObject.size(); i++){
			ObjectM objM = (ObjectM) vObject.get(i);				
			if(objM.getObjectType().equalsIgnoreCase(OrigConstant.OBJECT_TYPE)){
				String objectName = objM.getObjectName();
				if(!Systems.contains(objectName)){
					if(OrigConstant.SystemName.DASHBOARD.equals(objectName)){
						Systems.add(OrigConstant.SystemName.DASHBOARD);
					}else{
						if(!Systems.contains(OrigConstant.SystemName.ORIG)){
							Systems.add(OrigConstant.SystemName.ORIG);
						}
						dashboardSystem = false;
					}
				}
				v.add(objectName);	
			}
		}
		logger.debug("Systems >> "+Systems);
		userM.setSystems(Systems);
		userM.setDashboardSystem(dashboardSystem);
		request.getSession().setAttribute("iamObjects", vObject);
		request.getSession().setAttribute("menuIds", v);		
		return userM;
	}		
	
	private MenuM getFirstMenuAction(HttpServletRequest request){
		Vector<MenuM> vecMenus = (Vector<MenuM>)request.getSession().getAttribute("vecMenus");
		if(!Util.empty(vecMenus)){
			for(MenuM menuM : vecMenus){
				String menuId = menuM.getMenuID();
				String menuType = menuM.getMenuType();
				if(!Util.empty(menuId)&& !("LABEL".equalsIgnoreCase(menuType))){
					return menuM;
				}
			}
		}
		return null;
	}
	
	private  String getRedirectUrl(String processType, HttpServletRequest request) {
		String nextAction = "";
		if((MConstant.LOGON.equals(processType))){	
			if(dashboardSystem){
				nextAction = SystemConfig.getProperty("DASHBOARD_URL");
			}else{
				MenuM menu = getFirstMenuAction(request);
				if(null != menu&&!Util.empty(menu.getMenuID())){
					nextAction = "FrontController?action=MenuAction&handleForm=N&menuId="+menu.getMenuID();
				}
				if(Util.empty(nextAction)){
					nextAction = "FrontController?page=BLANK_SCREEN";
				}
			}
		}else{
			nextAction = "FrontController?page=LOGOUT_SCREEN";
		}
		logger.debug("nextAction >> "+nextAction);
		return nextAction;
	}
}
