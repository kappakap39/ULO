package com.eaf.orig.ulo.app.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class FrontWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(FrontWebAction.class);
	String nextAction = "";
	String CACHE_METAB_ACTION_PARAM = SystemConstant.getConstant("CACHE_METAB_ACTION_PARAM");
	@Override
	public Event toEvent(){
		return null;
	}
	@Override
	public boolean requiredModelRequest(){
		return false;
	}
	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}
	@Override
	public boolean preModelRequest(){	
		SessionControl.defaultLocal(getRequest());		
		SessionControl.clearSession(getRequest());
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		FlowControlDataM flowControl = (FlowControlDataM) getRequest().getSession().getAttribute("FlowControl");		
		String menuId = getRequest().getParameter("menuId");
		String firstAction = getRequest().getParameter("firstAction");
		logger.debug("menuId >> "+menuId);
		logger.debug("firstAction >> "+firstAction);
		HashMap<String,String> storeAction = getAction(menuId);
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
		nextAction = getActionUrl(storeAction);		
		if(Util.empty(nextAction)){
			nextAction = "page=BLANK_SCREEN";
		}		
		logger.info("nextAction >> "+nextAction);			
		return true;
	}
	public String getActionUrl(HashMap<String,String> storeAction){
		String url = "";
		if(!Util.empty(storeAction) && storeAction.size()>1){
			String and = "";
			for(String KEY :storeAction.keySet()){
				url += and+FormatUtil.displayText(KEY)+"="+FormatUtil.displayText(storeAction.get(KEY));
				and = "&";
			}
		}
		url += "&handleForm=N";
		return url;
	}
	private HashMap<String,String> getAction(String menuId){
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
	@Override
	public int getNextActivityType(){		
		return FrontController.ACTION;  
	}
	public String getNextActionParameter(){
		return nextAction;
	}
}
