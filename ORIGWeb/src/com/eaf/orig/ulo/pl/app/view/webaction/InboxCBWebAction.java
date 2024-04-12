package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.pl.app.utility.WorkflowTool;
import com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM;

public class InboxCBWebAction extends WebActionHelper implements WebAction {

	static Logger logger = Logger.getLogger(InboxCBWebAction.class);

	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){
			
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");		
		if (menuM == null)	menuM = new ProcessMenuM();
		
		logger.debug("Load InboxCBWebAction >> TDID "+menuM.getMenuID());
		
		try{			
			
			WorkflowTool wfXMLUtil = new WorkflowTool();
			ORIGInboxDataM oInboxM = wfXMLUtil.SearchWorkQueue(getRequest(), userM, menuM);	
			
			getRequest().getSession().setAttribute(ORIGInboxDataM.Constant.ORIG_INBOX, oInboxM);	
			
		}catch (Exception e) {
			logger.fatal("Exception >> "+e);
		}		
		
		return true;
	}

	@Override
	public int getNextActivityType(){	 
		return FrontController.PAGE;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
