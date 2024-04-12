package com.eaf.orig.ulo.formcontrol.view.action;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class SaveSubFormWebAction extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(SaveSubFormWebAction.class);
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
		logger.debug("SaveSubFormWebAction()..");
		return true;
	}
	@Override
	public int getNextActivityType(){
		return 0;
	}

}
