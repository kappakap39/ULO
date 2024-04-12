package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.CBPullJobUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.orig.bpm.ulo.model.WorkflowDataM;

public class CBPullJobWebAction extends WebActionHelper implements WebAction {
	static Logger logger = Logger.getLogger(CBPullJobWebAction.class);
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
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest(){				
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");	
		SearchHandler HandlerM = (SearchHandler) getRequest().getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		HandlerM.setErrorMSG(null);
		HandlerM.setErrorList(null);
		try{			
			int totalJob = Integer.parseInt(getRequest().getParameter("totalJob"));
			logger.debug("Get Total Job >> "+totalJob);
			WorkflowDataM workflowM = new WorkflowDataM();
			workflowM.setTotalJob(totalJob);
			
			ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
			if(currentMenuM == null) currentMenuM = new ProcessMenuM();
			
			workflowM.setTdID(currentMenuM.getMenuID());
			
			CBPullJobUtility pullJob = CBPullJobUtility.GetInstance();
				pullJob.CBPullJob(workflowM, userM);			
		}catch(Exception e){
//			logger.fatal("Exception ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
		}	
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter(){
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		return "action=CBPLInbox&searchType="+OrigConstant.SEARCH_TYPE_INBOX+"&role="+userM.getCurrentRole();
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}

}
