package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class RuleDetailsPopupWebAction extends WebActionHelper implements WebAction {

	private final Logger logger = Logger.getLogger(RuleDetailsPopupWebAction.class);

	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		logger.debug("++++++++LOAD RULES DETAILS POPUP WEBACTIION++++++++++");
        return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
