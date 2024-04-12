package com.eaf.orig.ulo.app.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class ActionController extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(ActionController.class);
	private String NEXT_ACTION = "";
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
		
		String FIRST_ACTION = getRequest().getParameter("FIRST_ACTION");
		String MENU_ID = getRequest().getParameter("MENU_ID");
		
		logger.debug("FIRST_ACTION >> "+FIRST_ACTION);
		logger.debug("MENU_ID >> "+MENU_ID);	
		
		SessionControl.defaultLocal(getRequest());		
		SessionControl.clearSession(getRequest());	
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute(SessionControl.ORIGUser);
		FlowControlDataM flowControl = (FlowControlDataM) getRequest().getSession().getAttribute(SessionControl.FlowControl);
		if(null == flowControl){
			flowControl = new FlowControlDataM();
			flowControl.setMenuId(MENU_ID);
			getRequest().getSession().setAttribute(SessionControl.FlowControl,flowControl);
		}		
		
		HashMap<String,String> storeAction = getAction(MENU_ID);
		logger.debug("storeAction >> "+storeAction);
		flowControl.setStoreAction(storeAction);
		
		String ROLE = storeAction.get("ROLE");		
		logger.debug("ROLE >> "+ROLE);
		
		if(MConstant.FLAG.YES.equals(FIRST_ACTION)){
			userM.setCurrentRole(ROLE);
			userM.setRoleMenu(ROLE);
			flowControl.setRole(ROLE);
		}
		
		if(Util.empty(MENU_ID)){			
			MENU_ID = flowControl.getMenuId();
		}	
		
		NEXT_ACTION = getActionURL(storeAction);
		
		if(Util.empty(NEXT_ACTION)){
			logger.debug("Not found action set page=BLANK_SCREEN");
			NEXT_ACTION = "page=BLANK_SCREEN";
		}		
		logger.info("Next action >> "+NEXT_ACTION);	
		
		return true;
	}
	public String getActionURL(HashMap<String,String> storeAction){
		String URL = "";
		if(!Util.empty(storeAction) && storeAction.size()>1){
			String AMPERSAND = "";
			for(String KEY :storeAction.keySet()){
				URL += AMPERSAND+FormatUtil.display(KEY)+"="+FormatUtil.display(storeAction.get(KEY));
				AMPERSAND = "&";
			}
		}
		return URL;
	}
	private HashMap<String,String> getAction(String MENU_ID){
		HashMap<String,String> storeAction = new HashMap<String, String>();
		SQLQueryEngine QueryEngine = new SQLQueryEngine();
		HashMap<String,String> hTable = QueryEngine.LoadModule("SELECT A.* FROM METAB_ACTION_PARAM A WHERE A.MENU_ID = ?",MENU_ID);
		try{
			String ACTION_PARAM = hTable.get("ACTION_PARAM");
			if(!Util.empty(ACTION_PARAM)){
				String [] URLObject = ACTION_PARAM.split("\\&");				
				if(null != URLObject){
					logger.debug("URLObject.length >> "+URLObject.length);
					for(String PARAM : URLObject){
						String[] PARAMObject = PARAM.split("\\=");
						logger.debug("PARAMObject.length >> "+PARAMObject.length);
						String KEY = PARAMObject[0];
						String VALUE = PARAMObject[1];
						logger.debug("KEY >> "+KEY);
						logger.debug("VALUE >> "+VALUE);
						storeAction.put(KEY,VALUE);
					}					
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		storeAction.put("ROLE",hTable.get("ROLE_NAME"));
		return storeAction;
	}
	@Override
	public int getNextActivityType(){		
		return FrontController.ACTION;  
	}
	public String getNextActionParameter(){
		return NEXT_ACTION;
	}
}
