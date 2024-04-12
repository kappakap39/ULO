package com.eaf.orig.menu.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.factory.EngineFactory;
import com.eaf.core.ulo.common.model.MenuHandlerManager;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.menu.DAO.MenuMDAO;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
import com.master.model.menu.MenuM;

public class Menu_ShowWebAction extends WebActionHelper implements WebAction {	
	private static transient Logger logger = Logger.getLogger(Menu_ShowWebAction.class);
	private String nextActParam = "";
	private final String LOGOUT_SCREEN = "page=LOGOUT_SCREEN";
	private final String BLANK_SCREEN = "page=BLANK_SCREEN";
	private final String WELCOME_SCREEN = "page=WELCOME_SCREEN";
	private int action = FrontController.ACTION;	
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
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		logger.debug("userM.getUserName >> "+userM.getUserName());		
		SessionControl.defaultLocal(getRequest());		
		SessionControl.clearSession(getRequest());			
		menuFlowControl();
		menuHandlerManager();
		return true;
	}	
	private void menuFlowControl(){
		Vector<MenuM> vecMenus = (Vector<MenuM>) getRequest().getSession().getAttribute("vecMenus");	
		if(Util.empty(vecMenus)){
			vecMenus = loadMenu();
			getRequest().getSession().setAttribute("vecMenus", vecMenus);
		}		
		logger.debug("vecMenus >> "+vecMenus);
		MenuM menuM = getMenuM(vecMenus);
		if(null != menuM){
			String URL = menuM.getMenuAction();
			String MENU = menuM.getMenuID();
			String CONTEXT = menuM.getMenuTarget();			
			String DEFAULT_CONTEXT = getRequest().getContextPath().replaceAll("\\/","");
			logger.debug("DEFAULT_CONTEXT >>> "+DEFAULT_CONTEXT);
			logger.debug("ACTION_CONTEXT >>> "+CONTEXT);
			logger.debug("MENU_ID >>> "+MENU);
			logger.debug("URL >>> "+URL);
			if(null != URL){				
				getRequest().getSession(true).setAttribute(SessionControl.FlowControl,mapFlowControl(menuM));
				if(DEFAULT_CONTEXT.equals(CONTEXT)){
					nextActParam = getMenuURL(URL,MENU);
					action = FrontController.ACTION;	
				}else{
					nextActParam = "/"+CONTEXT+"/FrontController?action=Menu_Show&MENU_ID="+MENU;	
					action = FrontController.OUTSIDE;
				}
			}
		}
	}
	private Vector<MenuM> loadMenu(){
		Vector<MenuM> vecMenus = new Vector<>();
		Vector v = (Vector) getRequest().getSession().getAttribute("menuIds");				
		if(v == null) v = new Vector();
		logger.debug("IAS menuIds >> "+v.size());
		Vector vRoles = (Vector) getRequest().getSession().getAttribute("UserRoles");				
		logger.debug("IAS UserRoles >> "+vRoles.size());
		try{
			MenuMDAO menudao = (MenuMDAO)EngineFactory.getMenuMDAO();	
			vecMenus = menudao.loadMenus(v,vRoles);
		}catch (Exception e) {
			logger.fatal("Exception ",e);
		}
		return vecMenus;
	}
	private MenuM getMenuM(Vector<MenuM> vecMenus){
		String MENU_ID = getRequest().getParameter("MENU_ID");
		MenuM menuM = null;
		if(!Util.empty(vecMenus)){
			for(MenuM m :vecMenus){
				String MENU = m.getMenuID();
				if(!Util.empty(MENU_ID)){
					if(!Util.empty(m.getMenuAction()) && null != MENU && MENU.equals(MENU_ID) && !("LABEL".equalsIgnoreCase(m.getMenuType()))){
						menuM = m;
						break;
					}
				}else{
					if(!Util.empty(m.getMenuAction()) && !("LABEL".equalsIgnoreCase(m.getMenuType()))){
						menuM = m;
						break;
					}
				}				
			}
		}
		return menuM;
	}
	private void menuHandlerManager(){
		MenuHandlerManager menuHandler = new MenuHandlerManager();
		Vector<MenuM> vecMenus = (Vector<MenuM>) getRequest().getSession().getAttribute("vecMenus");
		ArrayList<MenuM> topLevelMenus = new ArrayList<MenuM>();
		HashMap<String,ArrayList<MenuM>> subMenus = new HashMap<String, ArrayList<MenuM>>();
		if(!Util.empty(vecMenus)){
			for(MenuM menuM :vecMenus){
				if("EX000".equalsIgnoreCase(menuM.getMenuID().trim())) {
					continue;
				}		
				if("LABEL".equalsIgnoreCase(menuM.getMenuType().trim())) {		
					topLevelMenus.add(menuM);
					subMenus.put(menuM.getMenuID(), new ArrayList<MenuM>());
				}else {
					ArrayList<MenuM> subGroup = subMenus.get(menuM.getMenuReference());			
					if(null != subGroup){
						subGroup.add(menuM);
					}	
				}		
			}
		}
		menuHandler.setTopLevelMenus(topLevelMenus);
		menuHandler.setSubMenus(subMenus);
		getRequest().getSession(true).setAttribute("menuHandlerManager",menuHandler);
	}
	private FlowControlDataM mapFlowControl(MenuM menuM){
		FlowControlDataM flowControl = new FlowControlDataM();
			flowControl.setMenuId(menuM.getMenuID());
			flowControl.setMenuName(menuM.getMenuName());
			flowControl.setMenuAction(menuM.getMenuAction());
			flowControl.setTobMenuId(menuM.getMenuReference());
		return flowControl;
	}
	@Override
	public int getNextActivityType(){		
		return action;
	}
	@Override
	public String getNextActionParameter(){		
		if (Util.empty(nextActParam)){
			nextActParam = BLANK_SCREEN;
		}
		logger.debug("Next Action >> " + nextActParam);		
		return nextActParam;
	}
	public static String getMenuURL(String menuURL ,String menuID) {
		String retMenuURL = "";
		logger.info("MenuURL >>> " + menuURL);
		if (null != menuURL && menuURL.indexOf("?") != -1){			
			menuURL = menuURL.substring(menuURL.lastIndexOf("?") + 1,menuURL.length());
			retMenuURL = menuURL;
		}else if(menuURL.indexOf("page") != -1) {
			retMenuURL = menuURL;
		}	
		retMenuURL = retMenuURL+"&MENU_ID="+menuID;
		return retMenuURL;
	}
}
