package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Connection;
import org.apache.log4j.Logger;
import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.model.todolist.WfJobDataM;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.ORIGMapModelModule;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

public class FirstPLAppWebAction extends WebActionHelper implements WebAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	private String action = "";
	private Connection connWorkFlow = null;

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
		
		String role = getRequest().getParameter("role");		
		String first = getRequest().getParameter("first");
		String menuJobstate = getRequest().getParameter("menu-jobstate");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String searchType = (String) getRequest().getSession().getAttribute("searchType");
		
		ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
		if(null == currentMenuM) currentMenuM = new ProcessMenuM();	
		
		if(!OrigUtil.isEmptyString(menuJobstate)){
			currentMenuM.setMenuJobstate(menuJobstate);
		}
				
		if(OrigUtil.isEmptyString(searchType)){
			searchType = getRequest().getParameter("searchType");
			getRequest().getSession().setAttribute("searchType", searchType);
		}
				
		if(!OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
//		for block application where has block flag #Vikrom 20121106
			this.SessionApplicationLogic(searchType, userM);
		}
		
		if("true".equalsIgnoreCase(first)){
			userM.setCurrentRole(role);
			userM.setRoleMenu(role);
		}else{
			role = ORIGLogic.getRoleMenu(getRequest());
		}
		
		// for role DF
		if(OrigConstant.ROLE_DF_REJECT.equals(role) && !"true".equalsIgnoreCase(first)){
			role = OrigConstant.ROLE_DF;
		}
		
		logger.debug("MenuRoleAction()..role "+role);
		
		String menuID = getRequest().getParameter("MenuID");				
		if(OrigUtil.isEmptyString(menuID)){			
			menuID = currentMenuM.getMenuID();
		}
						
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();		
		action = cacheUtil.getMetabActionParam(role, searchType, menuID);
		
		if(OrigUtil.isEmptyString(action)){
			logger.debug("Not Found Next Action Param");
			action = "page=BLANK_SCREEN";
		}
		
		logger.info("Next Action Param >> "+action);
		
		getRequest().getSession().removeAttribute("VALUE_LIST");
		getRequest().getSession().removeAttribute("SearchM");
		
		return true;
	}

	@Override
	public int getNextActivityType(){		
		return FrontController.ACTION;  
	}

	public String getNextActionParameter(){
		return action;
	}
	
	private Connection getWorkFlowConnection(){
		try{
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		}catch(Exception e){
			logger.fatal("Connection is null" + e.getMessage());
		}
		return null;
	}
	
	@Override
	public boolean getCSRFToken(){
		return false;
	}	
	
	public void SessionApplicationLogic(String searchType ,UserDetailM userM){
		logger.debug("SessionApplicationLogic >> ");
		PLOrigFormHandler origFrom = (PLOrigFormHandler) getRequest().getSession(false).getAttribute("PLORIGForm");
		try{
			if(null != origFrom){
				PLApplicationDataM applicationM = origFrom.getAppForm();
				if(null != applicationM){					
					applicationM.setAppDecision("SAVE");
					
					WebActionUtil webU = new WebActionUtil();
						webU.getAction(applicationM, userM, null, searchType);
				
					if(OrigConstant.Action.BLOCK.equals(applicationM.getAppDecision())){						
						//connection
						this.connWorkFlow = getWorkFlowConnection();
						
						WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
						
						BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
					
						WorkflowResponse wfResponse = bpmWorkflow.getWorkQueueDataM(workflowM, this.connWorkFlow);	
						
						WfJobDataM wfJobM = null;
						if(null != wfResponse){
							wfJobM = wfResponse.getWfJobM();							
							if(null != wfJobM){
								if(!OrigConstant.wfProcessState.BLOCK.equals(wfJobM.getProcessState())){
									applicationM.setApplicationStatus(null);
									PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();
										origBean.completeApplication(applicationM, userM);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("Exception >> ",e);
		}
		
		try{			
			if(!OrigUtil.isEmptyString(userM.getUserName())){
				ORIGApplicationManager manager = ORIGEJBService.getApplicationManager();
				manager.cancleclaimByUserId(userM.getUserName());
			}
		}catch (Exception e) {
			logger.fatal("Exception >> ",e);
		}
		
	}
}
