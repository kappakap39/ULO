package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class NcbResultPopupWebAction extends WebActionHelper implements WebAction{
	
	Logger logger = Logger.getLogger(this.getClass());
	
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
	public boolean preModelRequest(){	
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
