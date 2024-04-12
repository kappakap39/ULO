package com.eaf.orig.shared.popup.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;

public class LoadRaceWebAction  extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadRaceWebAction.class);
    private String nextAction = null;
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
		
		String race = getRequest().getParameter("race");
		String race_desc = getRequest().getParameter("race_desc");
		
		logger.debug("race = "+race);
	    logger.debug("race_desc = "+race_desc);
	    
	    try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append(" SELECT *  FROM MS_RACE ");
			sql.append("  ");
			sql.append(" ORDER BY THDESC ");
			
			
	    } catch (Exception e) {
	           logger.error("exception ",e);
	    }
		
		return true;		
	}

	@Override
	public int getNextActivityType() {		
		 return FrontController.ACTION;
	}
	
    public String getNextActionParameter(){
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
