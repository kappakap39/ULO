package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;

public class ManageOnJobFlagWebAction extends WebActionHelper implements
		WebAction {
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
		// TODO Auto-generated method stub
		logger.debug("@@@@@ Need call workflwo API");
		String onJobFlag = (String)getRequest().getSession().getAttribute("PL_CA_ON_JOB");
		if(OrigConstant.FLAG_Y.equals(onJobFlag)){
			getRequest().getSession().setAttribute("PL_CA_ON_JOB",OrigConstant.FLAG_N);
		}else{
			getRequest().getSession().setAttribute("PL_CA_ON_JOB",OrigConstant.FLAG_Y);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return FrontController.ACTION;
	}
	
	public String getNextActionParameter() {
		return "action=CAPLInbox";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
