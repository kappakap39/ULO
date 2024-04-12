package com.eaf.orig.logon.view.webaction;

import java.sql.Timestamp;
import java.util.HashMap;
//import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
//import com.eaf.ias.service.ejb.IASServiceManager;
//import com.eaf.ias.service.proxy.IASServiceProxy;
//import com.eaf.ias.shared.model.ObjectM;
//import com.eaf.ias.shared.model.RoleM;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.logon.LogonEngine;
import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;

public class NaosUserWebAction extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(NaosUserWebAction.class);
	public NaosUserWebAction(){
		lastLogonDate = ApplicationDate.getTimestamp();
	}
	private String nextAction = "";
	private String userName = "";
	private int nextActivityType = FrontController.ACTION;
	private Timestamp lastLogonDate;
	String AUTHENTICATION_LDAP = SystemConfig.getProperty("AUTHENTICATION_LDAP");
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
		String type = getRequest().getParameter("type");
		logger.info("type >> "+type);
		try{
			if(MConstant.LOGON.equals(type)){
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
		nextAction = "page=LOGOUT_SCREEN";		
		nextActivityType = FrontController.LOGOUT;
		if(!Util.empty(errorCode)){
			getRequest().getSession().setAttribute("ERROR_CODE",LogonEngine.getCode(errorCode));
			getRequest().getSession().setAttribute("ERROR_MSG",errorMsg);
		}
		LogonEngine.processLogout(getRequest(),(!Util.empty(errorCode))?MConstant.PROCESS.FAIL:MConstant.PROCESS.SUCCESS);
	}
	private void processLogon() throws Exception{
		logger.info("processLogon..");
		nextAction = "action=Menu_Show";
		nextActivityType = FrontController.ACTION;
		getRequest().getSession(true).removeAttribute("GEMenu");
		getRequest().getSession(true).removeAttribute("ORIGUser");				
		logger.debug("AUTHENTICATION_LDAP >> "+AUTHENTICATION_LDAP);
		if(MConstant.FLAG.YES.equals(AUTHENTICATION_LDAP)){
			userName = (String) getRequest().getRemoteUser();	
			logger.debug("..getRemoteUser()=>userName is "+userName);
		}else{
			userName = (String) getRequest().getSession().getAttribute("userName");
			logger.debug("..getSessionUser()=>userName is "+userName);
		}		
		if(!Util.empty(userName)){
			getRequest().getSession().setAttribute("userName",userName);
			mapProfile(getRequest());
			getRequest().getSession().removeAttribute("ERROR_CODE");
			getRequest().getSession().removeAttribute("ERROR_MSG");
//			#rawi comment not used ServletContext control session between application war
			String LOGON_CONTEXT = SystemConfig.getProperty("LOGON_CONTEXT");
			String SESSION_ID = getRequest().getSession().getId();
			logger.debug("SESSION_ID >> "+SESSION_ID);
			logger.debug("LOGON_CONTEXT >> "+LOGON_CONTEXT);
			ServletContext LogonContext = getRequest().getServletContext().getContext(LOGON_CONTEXT);
			HashMap<String,HashMap<String,Object>> SESSION = (HashMap)LogonContext.getAttribute("SESSION");
			if(null == SESSION){
				SESSION = new HashMap<String,HashMap<String,Object>>();
			}
			HashMap<String,Object> StoreHash = new HashMap<String, Object>();
				StoreHash.put("ONLINE","Y");
				StoreHash.put("userName",getRequest().getSession().getAttribute("userName"));
				StoreHash.put("LastLogonDate",lastLogonDate);
			SESSION.put(SESSION_ID,StoreHash);
			LogonContext.setAttribute("SESSION",SESSION);			
			LogonEngine.processLogon(getRequest(),MConstant.PROCESS.SUCCESS);
		}else{
			processLogout(LogonEngine.ERROR_USER_CONFIGURATION_NOT_COMPLETED,"");
		}
	}	
	private void mapProfile(HttpServletRequest request) throws Exception{
		UserDetailM userM = ORIGDAOFactory.getUserProfileDAO().getUserProfile(userName);
		userM.setLastLogonDate(lastLogonDate);
		userM = mapRole(userM);			
		request.getSession(true).setAttribute("UserRoles",userM.getRoles().clone());
		request.getSession(true).setAttribute("ORIGUser",userM);
		request.getSession(true).setAttribute("OfficeCode",userM.getDefaultOfficeCode());	
	}
	private UserDetailM mapRole(UserDetailM userM) throws Exception{		
//		IASServiceManager proxy = IASServiceProxy.getIASServiceManager(IAS_EJB_URL,IAS_EJB_PORT);
//		Vector<RoleM> vRoles = proxy.getRole(userName);			
//		Vector<String> roles = new Vector<String>();
//		for(int i = 0; i < vRoles.size(); i++){
//			RoleM role = (RoleM) vRoles.get(i);
//			roles.add(role.getRoleName());
//		}
//		userM.setRoles(roles);		
//		Vector<String> v = new Vector<String>();
//		Vector<ObjectM> vresult = proxy.getObjectInUser(userName, OrigConstant.SYSTEM_NAME);			
//		for(int i = 0; i < vresult.size(); i++){
//			ObjectM objM = (ObjectM) vresult.get(i);				
//			if(objM.getObjectType().equalsIgnoreCase(OrigConstant.OBJECT_TYPE)){
//				v.add(objM.getObjectName());	
//			}
//		}
//		for(int j=0; j<roles.size();j++){
//			String rName = (String)roles.get(j);
//			Vector<RoleM> vParentRoles = proxy.getParentOfRole(rName);				
//			Vector<String> pRoles = new Vector<String>();
//			if(vParentRoles != null && !vParentRoles.isEmpty()){
//				for(int i=0;i<vParentRoles.size();i++){
//					RoleM role = (RoleM) vParentRoles.get(i);
//					pRoles.add(role.getRoleName());							
//				}
//				Vector<ObjectM> vparentResult = proxy.getObjectInRole(pRoles);						
//				if(vparentResult != null && !vparentResult.isEmpty()){
//					for (int i = 0; i < vparentResult.size(); i++) {
//						ObjectM objM = (ObjectM) vparentResult.get(i);
//						if (objM.getObjectType().equalsIgnoreCase(OrigConstant.OBJECT_TYPE)) {
//							v.add(objM.getObjectName());	
//						}
//					}
//					vresult.addAll(vparentResult);		
//				}
//			}	
//		}		
//		getRequest().getSession().setAttribute("iamObjects", vresult);
//		getRequest().getSession().setAttribute("menuIds", v);		
		return userM;
	}		
	public int getNextActivityType(){
		return nextActivityType;
	}
	public String getNextActionParameter(){
		return nextAction;
	}	
}
