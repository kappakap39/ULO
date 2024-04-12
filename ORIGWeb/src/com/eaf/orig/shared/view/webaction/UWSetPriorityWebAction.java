/*
 * Created on Oct 30, 2007
 * Created by weeraya
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
package com.eaf.orig.shared.view.webaction;


import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author weeraya
 *
 * Type: UWSetPriorityWebAction
 */
public class UWSetPriorityWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(UWSetPriorityWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
		return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse arg0) {
		 
		return defaultProcessResponse(arg0);
	}

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
    	try{
	    	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			String userName =userM.getUserName();
	    	String[] priorityName = getRequest().getParameterValues("priorityName");
	    	ORIGApplicationManager manager = ORIGEJBService.getApplicationManager();
	        if (priorityName != null) {
	
	            for (int i = 0; i < priorityName.length; i++) {
	                logger.debug(">>> priority : " + priorityName[i] + " : " + priorityName[i]);
	
	                String priority = getRequest().getParameter(priorityName[i]);
	                String appId = priorityName[i].substring(1);
	                logger.debug(">>> appRecordId : " + appId);
	                manager.uwSetPriority(appId, priority, userName);
	            }
	        }
    	}catch(Exception e){
    		logger.fatal("[UWSetPriorityWebAction].... Error >> ", e);
    		return false;
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
        return "action=FristApp";
    }
    
    protected void doSuccess(EventResponse arg0) {
    	
	}
    
	protected void doFail(EventResponse arg0) {
		ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        String errMsg = arg0.getMessage();
        formHandler.getFormErrors().add(errMsg);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
