package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.pl.app.utility.WorkflowTool;
import com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM;

public class InboxSupWebAction extends WebActionHelper implements WebAction {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String action = "";
	private int next = 0;

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
//		logger.debug("InboxSupWebAction");
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
		if (menuM == null)
			menuM = new ProcessMenuM();
		
		WorkflowTool wfXMLUtil = new WorkflowTool();
		ORIGInboxDataM oInboxM = null;
		
		try {
			oInboxM = wfXMLUtil.SearchWorkQueue(getRequest(), userM, menuM);
		} catch (Exception e) {
			logger.fatal("Error " + e.getMessage());
		}
		
		if (null == oInboxM)
			oInboxM = new ORIGInboxDataM();
		
		getRequest().getSession().setAttribute(ORIGInboxDataM.Constant.ORIG_INBOX, oInboxM);
		return true;
	}

	@Override
	public int getNextActivityType() {
		return next;
	}
	@Override
	public String getNextActionParameter() {
		return action;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
