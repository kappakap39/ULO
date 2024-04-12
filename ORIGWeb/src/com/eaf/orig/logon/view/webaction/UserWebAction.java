package com.eaf.orig.logon.view.webaction;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.factory.EngineFactory;
import com.eaf.core.ulo.common.model.MenuHandlerManager;
import com.eaf.core.ulo.common.properties.SystemConfig;
//import com.eaf.core.ulo.common.rest.RESTClient;
//import com.eaf.core.ulo.common.rest.RESTResponse;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.ObjectM;
import com.eaf.ias.shared.model.RoleM;
//import com.eaf.ias.shared.model.helper.IASServiceResponseMapper;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.logon.LogonEngine;
import com.eaf.orig.menu.DAO.MenuMDAO;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.master.model.menu.MenuM;

public class UserWebAction extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(UserWebAction.class);
	public UserWebAction(){
		lastLogonDate = ApplicationDate.getTimestamp();
	}
	private String userName = "";
	private Timestamp lastLogonDate;	
	private boolean dashboardSystem = true;
	private String processType = MConstant.LOGOUT;
	String AUTHENTICATION_LDAP = SystemConfig.getProperty("AUTHENTICATION_LDAP");
	String IAS_SERVICE_OBJECTROLE_URL = SystemConfig.getProperty("IAS_SERVICE_OBJECTROLE_URL");
	String IAS_EJB_URL = SystemConfig.getProperty("IAS_EJB_URL");
	String IAS_EJB_PORT = SystemConfig.getProperty("IAS_EJB_PORT");
	public Event toEvent(){
		return null;
	}
	public boolean requiredModelRequest(){
		return false;
	}
	public boolean processEventResponse(EventResponse response) {
		return false;
	}
	public boolean preModelRequest(){	
		processType = getRequest().getParameter("type");
		logger.info("processType >> "+processType);
		try{
			if(MConstant.LOGON.equals(processType)){
				processLogon();			
			}else{
				processLogout();
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processLogout(LogonEngine.ERROR_PLEASE_CONTRACT_ADMIN,"");
		}
		return true;
	}
	private void processLogout(){
		processLogout("","");
	}
	private void processLogout(String errorCode,String errorMsg){
		logger.info("processLogout..");
		if(!Util.empty(errorCode)){
			getRequest().getSession().setAttribute("ERROR_CODE",LogonEngine.getCode(errorCode));
			getRequest().getSession().setAttribute("ERROR_MSG",errorMsg);
		}
		LogonEngine.processLogout(getRequest(),(!Util.empty(errorCode))?MConstant.PROCESS.FAIL:MConstant.PROCESS.SUCCESS);
	}
	private void processLogon() throws Exception{
		logger.info("processLogon..");
		getRequest().getSession(true).removeAttribute("GEMenu");
		getRequest().getSession(true).removeAttribute("ORIGUser");				
		logger.debug("AUTHENTICATION_LDAP >> "+AUTHENTICATION_LDAP);
		if(MConstant.FLAG.YES.equals(AUTHENTICATION_LDAP)){
			userName = (String)getRequest().getRemoteUser();	
			logger.debug("..getRemoteUser()=>userName is "+userName);
		}else{
			userName = (String)getRequest().getSession().getAttribute("userName");
			logger.debug("..getSessionUser()=>userName is "+userName);
		}		
		if(!Util.empty(userName)){
			getRequest().getSession().setAttribute("userName",userName);
			SessionControl.defaultLocal(getRequest());		
			SessionControl.clearSession(getRequest());
			loadProfile();
			loadMenu();
			LogonEngine.processLogon(getRequest(),MConstant.PROCESS.SUCCESS);
		}else{
			processLogout(LogonEngine.ERROR_USER_CONFIGURATION_NOT_COMPLETED,"");
		}
	}	
	private void loadMenu()throws Exception{
		Vector v = (Vector)getRequest().getSession().getAttribute("menuIds");
		Vector vRoles = (Vector)getRequest().getSession().getAttribute("UserRoles");
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
//					subMenus.put(menuM.getMenuID(), new ArrayList<MenuM>());
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
		getRequest().getSession(true).setAttribute("menuHandlerManager",menuHandler);
		getRequest().getSession(true).setAttribute("vecMenus", vecMenus);
	}	
	private void loadProfile() throws Exception{
		UserDetailM userM = ORIGDAOFactory.getUserProfileDAO().getUserProfile(userName);
		userM.setLastLogonDate(lastLogonDate);
		userM = loadRole(userM);			
		getRequest().getSession(true).setAttribute("UserRoles",userM.getRoles().clone());
		getRequest().getSession(true).setAttribute("ORIGUser",userM);
		getRequest().getSession(true).setAttribute("OfficeCode",userM.getDefaultOfficeCode());	
	}
	private UserDetailM loadRole(UserDetailM userM) throws Exception{	
		IASServiceRequest serviceRequest = new IASServiceRequest();
		serviceRequest.setUserName(userName);
		serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);				
//		RESTClient restClient = new RESTClient();
//		RESTResponse restResponse = restClient.executeRESTCall(IAS_SERVICE_OBJECTROLE_URL,serviceRequest);
		
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
		ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_OBJECTROLE_URL,serviceRequest,IASServiceResponse.class);
		IASServiceResponse serviceResponse = responseEntity.getBody();
		
		ArrayList<String> Systems = new ArrayList<String>();
//		Vector<RoleM> vRoles = IASServiceResponseMapper.getRole(restResponse.getJsonResponse());		
		Vector<RoleM> vRoles = serviceResponse.getRoles();		
		Vector<String> roles = new Vector<String>();
		for(int i = 0; i < vRoles.size(); i++){
			RoleM role = (RoleM) vRoles.get(i);
			roles.add(role.getRoleName());
		}
		userM.setRoles(roles);		
		Vector<String> v = new Vector<String>();
//		Vector<ObjectM> vresult = IASServiceResponseMapper.getObject(restResponse.getJsonResponse());		
		Vector<ObjectM> vObject = serviceResponse.getObjects();
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
		getRequest().getSession().setAttribute("iamObjects", vObject);
		getRequest().getSession().setAttribute("menuIds", v);		
		return userM;
	}		
	public int getNextActivityType(){
		int nextActivityType = FrontController.ACTION;
		if(MConstant.LOGON.equals(processType)){
			nextActivityType = (dashboardSystem)?FrontController.OUTSIDE_URL:FrontController.ACTION;
		}else{
			nextActivityType = FrontController.LOGOUT;
		}	
		return nextActivityType;
	}
	public String getNextActionParameter(){
		String nextAction = "";
		if((MConstant.LOGON.equals(processType))){	
			if(dashboardSystem){
				nextAction = SystemConfig.getProperty("DASHBOARD_URL");
			}else{
				MenuM menu = getFirstMenuAction();
				if(null != menu&&!Util.empty(menu.getMenuID())){
					nextAction = "action=MenuAction&handleForm=N&menuId="+menu.getMenuID();
				}
				if(Util.empty(nextAction)){
					nextAction = "page=BLANK_SCREEN";
				}
			}
		}else{
			nextAction = "page=LOGOUT_SCREEN";
		}
		logger.debug("nextAction >> "+nextAction);
		return nextAction;
	}
	private MenuM getFirstMenuAction(){
		Vector<MenuM> vecMenus = (Vector<MenuM>)getRequest().getSession().getAttribute("vecMenus");
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
}
