package com.eaf.orig.ulo.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.control.util.BackWebActionUtil;
import com.eaf.orig.ulo.control.util.CancleClaimUtil;

public class CloseApplicationWebAction extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(CloseApplicationWebAction.class);
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
		CancleClaimUtil.cancleClaim(getRequest());
		return true;
	}
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter() {
		return BackWebActionUtil.processBackWebAction(getRequest());
	}
}
