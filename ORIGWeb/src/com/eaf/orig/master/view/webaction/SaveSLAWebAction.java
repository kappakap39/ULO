/*
 * Created on Jan 23, 2008
 * Created by Weeraya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.master.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.SLAEvent;
import com.eaf.orig.shared.model.SLADataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Weeraya
 *
 * Type: SaveSLAWebAction
 */
public class SaveSLAWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SaveSLAWebAction.class);
//	private String nextAction = null;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		SLAEvent event = new SLAEvent();
		ORIGMasterFormHandler formHandler = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		
		event.setEventType(SLAEvent.SLA_SAVE);
		event.setObject(formHandler.getSlaDataM());
		
		return event;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		 
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		 
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
        try {
        	ORIGMasterFormHandler formHandler = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm"); 
        	formHandler.setShwAddFrm("save");
        	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        	
    		String qname = getRequest().getParameter("qname");
    		String time = getRequest().getParameter("time");
    		logger.debug("qnamer = "+qname);
    		logger.debug("time = "+time);
    		
    		ORIGUtility utility = new ORIGUtility();
    		
    		SLADataM dataM = formHandler.getSlaDataM();
    		
    		dataM.setTime(utility.stringToDouble(time));
    		dataM.setUpdateBy(userM.getUserName());
            
       } catch (Exception e) {
           logger.error("exception ",e);
       }
		return true;
	}

	/* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.ACTION;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchSLA";
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
