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

public class InboxDFWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {
		 logger.debug("InboxDFWebAction");		 
		 UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		 ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
			if(menuM == null) menuM = new ProcessMenuM();			
	     WorkflowTool wfXMLUtil = new WorkflowTool();		
	     ORIGInboxDataM oInboxM = null;
	     try{
	    	 oInboxM = wfXMLUtil.SearchWorkQueue(getRequest(), userM, menuM);	 	    	 
	     }catch(Exception e){
	    	 logger.fatal("Error "+e.getMessage());
		 }			
	     if(null == oInboxM) oInboxM = new ORIGInboxDataM();	     
	     getRequest().getSession().setAttribute(ORIGInboxDataM.Constant.ORIG_INBOX,oInboxM);
		return true;
	}

	public int getNextActivityType() {

		return FrontController.PAGE;// to summary
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
