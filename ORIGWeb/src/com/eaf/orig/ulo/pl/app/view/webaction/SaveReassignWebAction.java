package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.view.webaction.SaveAppScoreWebAction;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.ERROR;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.formcontrol.view.form.ErrorDataM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class SaveReassignWebAction extends WebActionHelper implements WebAction {
	
	static Logger logger = Logger.getLogger(SaveAppScoreWebAction.class);
	
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		SearchHandler HandlerM = (SearchHandler) getRequest().getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		HandlerM.setErrorMSG(null);
		HandlerM.setErrorList(null);
		
		String[] checks = getRequest().getParameterValues("check_apprecid");
		String reassignTo = getRequest().getParameter("reassignTo");
		
		logger.debug("reassignTo >> "+reassignTo);
		
		try{
			if(null != checks){		
				
				ErrorDataM errorM = null;
				PLApplicationDataM applicationM  = null;	
				
				PLORIGApplicationManager appBean = PLORIGEJBService.getPLORIGApplicationManager();
				UtilityDAO dao = new UtilityDAOImpl();	
				
				for(String appRecID : checks){
					logger.debug("[SaveReassign] appRecID "+appRecID);
					try{
						if(validate(appRecID,reassignTo)){
							applicationM = appBean.getAppInfo(appRecID);
							MapObject(applicationM,reassignTo);
							appBean.ReAssignApplication(applicationM,userM,reassignTo);
						}else{							
							logger.error("ERROR : cannot reassign this application to user "+reassignTo);
							String message = MessageResourceUtil.getTextDescription(getRequest(),"MSG_REASSIGN_NOT_AUTHORIZED");
							errorM =  new ErrorDataM(dao.getApplicationNo(appRecID),message);
							HandlerM.add(errorM);
						}
					}catch(Exception e){
//						logger.error("ERROR: SaveReassign appRecID "+appRecID+" MESSAGE "+e.getMessage());
//						String msg = ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR");
						String msg = Message.error(e);
						if(ERROR.MSG_CANNOT_CLAIM.equals(e.getMessage())){
							msg = ErrorUtil.getShortErrorMessage(getRequest(),ERROR.MSG_CANNOT_CLAIM);
						}
						errorM =  new ErrorDataM(dao.getApplicationNo(appRecID),msg);
						HandlerM.add(errorM);
					}
				}
				
				HandlerM.GenarateErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(), "APP_NO"));
			}
		}catch(Exception e){
//			logger.fatal("Exception >> ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}		
		getRequest().getSession().removeAttribute("VALUE_LIST");
		return true;		
	}
	
	private boolean validate(String appRecID,String reassignTo) throws Exception{
		boolean validate = false;		
		UtilityDAO dao = new UtilityDAOImpl();		
		HashMap<String,String> info = dao.getWorkflowInfo(appRecID);
		String role = getRole(info);		
		logger.debug("validate >> "+role);			
		if(null != role){
			String userID = dao.LoadUserID(reassignTo,role);
			if(!OrigUtil.isEmptyString(userID)){
				validate = true;
			}
		}		
		return validate;
	}
	private String getRole(HashMap<String,String> info){
		String role = null;
		String m_role = info.get(OrigConstant.WorkflowInfo.ROLE);
		String m_ptid = info.get(OrigConstant.WorkflowInfo.PTID);
		if(null != m_role && null != m_ptid){
			role = ORIGLogic.getRoleFormWf(m_role,m_ptid);
		}
		return role;
	}
	private void MapObject(PLApplicationDataM applicationM,String reassignTo){
		//applicationM.setApplicationStatus(null);
		applicationM.setOwner(reassignTo);
		applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
		applicationM.setAppDecision(OrigConstant.Action.REASSIGN);
	}
	
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter() {
		return "action=SearchReassignWebAction";
	}
	
	@Override
	public boolean getCSRFToken(){		
		return true;
	}
	
	@Override
	public boolean validationForm(){
		boolean validate = true;
		String reassignTo = getRequest().getParameter("reassignTo");
		if(OrigUtil.isEmptyString(reassignTo)){
			validate = false;
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"SELECT_OWNER"));
		}		
		return validate;
	}
	
}