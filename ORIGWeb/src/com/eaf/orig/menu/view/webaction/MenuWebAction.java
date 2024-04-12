package com.eaf.orig.menu.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.CancleClaimUtil;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class MenuWebAction extends WebActionHelper implements WebAction {	
	private static transient Logger logger = Logger.getLogger(MenuWebAction.class);
	String CACHE_PROCESS_MENU = SystemConstant.getConstant("CACHE_PROCESS_MENU");
	String LOGON_CONTEXT = SystemConfig.getProperty("LOGON_CONTEXT");
	int nextActivity = FrontController.ACTION;	
	String nextAction = "";
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
		CancleClaimUtil.cancleClaim(getRequest());
		SessionControl.clearSession(getRequest());	
		String menuId = getRequest().getParameter("menuId");
		logger.debug("menuId >> "+menuId);
		FlowControlDataM flowControl = getFlowControl(menuId);		
		getRequest().getSession().setAttribute("FlowControl",flowControl);	
		if(!empty(flowControl)){
			if(Util.empty(flowControl.getOutsideUrl()) || MConstant.FLAG_N.equals(flowControl.getOutsideUrl())){
				nextAction = getAction(flowControl.getMenuUrl(),flowControl.getMenuId());
			}else if (MConstant.FLAG_Y.equals(flowControl.getOutsideUrl())){
				nextActivity = FrontController.OUTSIDE_URL;
				UserDetailM userM = (UserDetailM)getRequest().getSession(true).getAttribute("ORIGUser");				
				nextAction = flowControl.getMenuUrl()+"&menuId="+menuId+"&userName="+userM.getUserName();
			}
		}
		if(Util.empty(nextAction)){
			nextAction = "page=BLANK_SCREEN";
		}
		logger.debug("nextAction >> "+nextAction);
		return true;
	}
	private FlowControlDataM getFlowControl(String menuId){
		FlowControlDataM flowControl = new FlowControlDataM();
		HashMap<String,Object> processMenu = CacheControl.get(CACHE_PROCESS_MENU,menuId);
		flowControl.setMenuId(SQLQueryEngine.display(processMenu,"MENU_ID"));
		flowControl.setMenuName(SQLQueryEngine.display(processMenu,"MENU_NAME"));
		String actionTarget = SQLQueryEngine.display(processMenu,"ACTION_TARGET");
		logger.debug("actionTarget >> "+actionTarget);
		flowControl.setContext(actionTarget);
		String outsideUrl = SQLQueryEngine.display(processMenu,"OUTSIDE_URL");			
		flowControl.setOutsideUrl(MConstant.FLAG_N);
		if(null == LOGON_CONTEXT){
			LOGON_CONTEXT = "/ORIGWeb";
		}
		if(MConstant.FLAG_Y.equals(outsideUrl)&&!LOGON_CONTEXT.equals(actionTarget)){
			flowControl.setOutsideUrl(MConstant.FLAG_Y);
		}
		logger.debug("outsideUrl >> "+flowControl.getOutsideUrl());
		flowControl.setMenuUrl(SQLQueryEngine.display(processMenu,"MENU_URL"));
		return flowControl;
	}
	@Override
	public int getNextActivityType() {		
		return nextActivity;
	}
	private boolean empty(FlowControlDataM flowControl){
		return (null == flowControl || Util.empty(flowControl.getMenuId()));
	}
	@Override
	public String getNextActionParameter() {
		return nextAction;
	}
	private String getAction(String menuUrl,String menuId){
		String url = "";
		if (null != menuUrl && menuUrl.indexOf("?") != -1){			
			menuUrl = menuUrl.substring(menuUrl.lastIndexOf("?") + 1,menuUrl.length());
			url = menuUrl;
		}else if(menuUrl.indexOf("page") != -1) {
			url = menuUrl;
		}	
		url += "&menuId="+menuId;
		url += "&handleForm=N";
		url = url.replaceAll("ActionControl","FrontAction");
		return url;
	}
}
