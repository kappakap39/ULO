package com.eaf.j2ee.pattern.control;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Enumeration;
import java.util.HashMap;
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

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.properties.UseSessionHash;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.ObjectM;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.orig.profile.dao.UserProfileDAOImpl;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
//import com.eaf.core.ulo.common.rest.RESTClient;
//import com.eaf.core.ulo.common.rest.RESTResponse;
//import com.eaf.ias.shared.model.helper.IASServiceResponseMapper;

@WebServlet("/FrontAction")
public class FrontAction extends HttpServlet {
//	private static final String INDEX_PAGE = "index.jsp";
	String CACHE_PROCESS_MENU = SystemConstant.getConstant("CACHE_PROCESS_MENU");
	String CACHE_METAB_ACTION_PARAM = SystemConstant.getConstant("CACHE_METAB_ACTION_PARAM");
	String IAS_SERVICE_OBJECTROLE_URL = SystemConfig.getProperty("IAS_SERVICE_OBJECTROLE_URL");
	private static final long serialVersionUID = 1L;
    private static transient Logger logger = Logger.getLogger(FrontAction.class);
    String page = "";
    String action = "";
    public FrontAction() {
        super();
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionControl.defaultLocal(request);		
		SessionControl.clearSession(request);
		FlowControlDataM flowControl = processFlowControl(request);
		HashMap<String,String> storeAction = flowControl.getStoreAction();
		page = (String)storeAction.get("page");
		action = (String)storeAction.get("action");
		serviceProcess(request,response);
//		String LOGON_CONTEXT = SystemConfig.getProperty("LOGON_CONTEXT");
//		String INDEX_PAGE_URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+LOGON_CONTEXT+"/"+INDEX_PAGE;
//		logger.debug("INDEX_PAGE_URL >> "+INDEX_PAGE_URL);
//		response.sendRedirect(INDEX_PAGE_URL);		
		String INDEX_PAGE_URL = SystemConfig.getProperty("ORIG_URL");
		logger.debug("INDEX_PAGE_URL >> "+INDEX_PAGE_URL);
		response.sendRedirect(INDEX_PAGE_URL);
	}
	public void serviceProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.getSession().setAttribute("error",null);
		logger.debug("Server Name >> "+ request.getServerName());
		ErrorUtil.clearErrorInSession(request);
		String removeSession = request.getParameter("removeSession");
		logger.debug("removeSession = "+ removeSession);
		if(removeSession !=null && removeSession.equals("Y")){
			clearSession(request);
		}
		ScreenFlowManager screenFlowManager = (ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");		
		if(screenFlowManager == null){
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
	}
	private void clearSession(HttpServletRequest request){		
		Enumeration<String> attributeAll = request.getSession().getAttributeNames();
		while(attributeAll.hasMoreElements()){			
			String attributeName = (String) attributeAll.nextElement();
			if(!getUseSessionHash().containsKey(attributeName)){
				request.getSession().removeAttribute(attributeName);
			}
		}		
	}	
	private HashMap<String,String> getUseSessionHash(){
		return UseSessionHash.getUseSessionHash();
	}
	private FlowControlDataM processFlowControl(HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		String userName = request.getParameter("userName");
		String AUTHENTICATION_LDAP = SystemConfig.getProperty("AUTHENTICATION_LDAP");		
		logger.debug("AUTHENTICATION_LDAP >> "+AUTHENTICATION_LDAP);
		if(MConstant.FLAG.YES.equals(AUTHENTICATION_LDAP)){
			userName = request.getRemoteUser();
		}
		logger.debug("menuId >> "+menuId);
		logger.debug("userName >> "+userName);
		UserDetailM userM = getUser(request,userName);
		logger.debug("userM >> "+userM);
		FlowControlDataM flowControl = getFlowControl(menuId);		
		request.getSession().setAttribute("FlowControl",flowControl);		
		HashMap<String,String> storeAction = getFrontAction(menuId);
		logger.debug("storeAction >> "+storeAction);
		flowControl.setStoreAction(storeAction);			
		String role = storeAction.get("ROLE");
		String actionType = storeAction.get("ACTION_TYPE");	
		String formRole = storeAction.get("FORM_ROLE");				
		logger.debug("role >> "+role);
		logger.debug("actionType >> "+actionType);
		logger.debug("formRole >> "+formRole);		
		userM.setCurrentRole(role);
		userM.setRoleMenu(role);
		flowControl.setRole(role);
		flowControl.setActionType(actionType);
		flowControl.setFormRole(formRole);
		return flowControl;
	}
	
	private HashMap<String,String> getFrontAction(String menuId){
		HashMap<String,String> storeAction = new HashMap<String, String>();
		HashMap<String, Object> metabAction = CacheControl.get(CACHE_METAB_ACTION_PARAM,menuId);
		String requestAction = SQLQueryEngine.display(metabAction,"ACTION_PARAM"); 
		String roleName = SQLQueryEngine.display(metabAction,"ROLE_NAME"); 
		try{
			if(!Util.empty(requestAction)){
				String [] requestActionObjects = requestAction.split("\\&");				
				if(null != requestActionObjects){
					for(String requestActionObject : requestActionObjects){
						String[] requestObject = requestActionObject.split("\\=");
						String attrbute = requestObject[0];
						String value = requestObject[1];
						storeAction.put(attrbute,value);
					}					
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		storeAction.put("ROLE",roleName);
		return storeAction;
	}
	private FlowControlDataM getFlowControl(String menuId){
		FlowControlDataM flowControl = new FlowControlDataM();
		HashMap<String,Object> processMenu = CacheControl.get(CACHE_PROCESS_MENU,menuId);
			flowControl.setMenuId(SQLQueryEngine.display(processMenu,"MENU_ID"));
			flowControl.setMenuName(SQLQueryEngine.display(processMenu,"MENU_NAME"));
			flowControl.setContext(SQLQueryEngine.display(processMenu,"ACTION_TARGET"));
			flowControl.setOutsideUrl(SQLQueryEngine.display(processMenu,"OUTSIDE_URL"));
			flowControl.setMenuUrl(SQLQueryEngine.display(processMenu,"MENU_URL"));
		return flowControl;
	}
	private UserDetailM getUser(HttpServletRequest request,String userName){
		UserDetailM userM = (UserDetailM)request.getSession(true).getAttribute("ORIGUser");
		if(null == userM){
			userM = getUserModel(request,userName);
		}		
		request.getSession(true).setAttribute("UserRoles",userM.getRoles().clone());
		request.getSession(true).setAttribute("ORIGUser",userM);
		request.getSession(true).setAttribute("OfficeCode",userM.getDefaultOfficeCode());	
		request.getSession().setAttribute("userName",userName);
		return userM;
	}
	private UserDetailM getUserModel(HttpServletRequest request,String userName){
		logger.debug("getUserModel().."+userName);
		try{
			UserDetailM userM = new UserProfileDAOImpl().getUserProfile(userName);
			userM = mapUserRole(request,userM,userName);
			return userM;
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return null;
	}
	private UserDetailM mapUserRole(HttpServletRequest request,UserDetailM userM,String userName) throws Exception{		
		IASServiceRequest serviceRequest = new IASServiceRequest();
		serviceRequest.setUserName(userName);
		serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);				
//		RESTClient restClient = new RESTClient();
//		RESTResponse restResponse = restClient.executeRESTCall(IAS_SERVICE_OBJECTROLE_URL,serviceRequest);
//		Vector<RoleM> vRoles = IASServiceResponseMapper.getRole(restResponse.getJsonResponse());	
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
		ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_OBJECTROLE_URL, serviceRequest, IASServiceResponse.class);
		IASServiceResponse serviceResponse = responseEntity.getBody();
		Vector<RoleM> vRoles = serviceResponse.getRoles();
		if(null == vRoles){
			vRoles = new Vector<>();
		}
		Vector<String> roles = new Vector<String>();
		for(int i = 0; i < vRoles.size(); i++){
			RoleM role = (RoleM) vRoles.get(i);
			roles.add(role.getRoleName());
		}
		userM.setRoles(roles);		
		Vector<String> v = new Vector<String>();
//		Vector<ObjectM> vresult = IASServiceResponseMapper.getObject(restResponse.getJsonResponse());		
		Vector<ObjectM> vObject = serviceResponse.getObjects();
		if(null == vObject){
			vObject = new Vector<ObjectM>();
		}
		for(int i = 0; i < vObject.size(); i++){
			ObjectM objM = (ObjectM) vObject.get(i);				
			if(objM.getObjectType().equalsIgnoreCase(OrigConstant.OBJECT_TYPE)){
				v.add(objM.getObjectName());	
			}
		}
		request.getSession().setAttribute("iamObjects",vObject);
		request.getSession().setAttribute("menuIds",v);		
		return userM;
	}
}
