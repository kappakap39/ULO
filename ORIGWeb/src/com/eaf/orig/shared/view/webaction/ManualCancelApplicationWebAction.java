/*
 * Created on Nov 23, 2007
 * Created by Administrator
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
import com.eaf.orig.shared.control.event.ApplicationEvent;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Administrator
 *
 * Type: CancelApplicationWebAction
 */
public class ManualCancelApplicationWebAction extends WebActionHelper implements
		WebAction {
	Logger logger = Logger.getLogger(ManualCancelApplicationWebAction.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
	    UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		
		ORIGFormHandler formHandler=(ORIGFormHandler)getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM applicationDataM = formHandler.getAppForm();
		String userName = userM.getUserName();
		//if(ORIGUtility.isEmptyString(applicationDataM.getUwFirstId())){
		//	applicationDataM.setUwFirstId(userName);
		//	applicationDataM.setUwStartDate(new Date());
		//}
		
		//applicationDataM.setUwLastId(userName);
		
		
		
		if(ORIGUtility.isEmptyString(applicationDataM.getCreateBy())){
			applicationDataM.setCreateBy(userName);
			applicationDataM.setUpdateBy(userName);
		}else{
			applicationDataM.setUpdateBy(userName);
		}
		ApplicationEvent event = new ApplicationEvent(ApplicationEvent.MANUAL_CANCEL, applicationDataM, userM);
		applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.AUTO_CANCEL);
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
		 
		return   defaultProcessResponse(response);
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
	 
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return FrontController.PAGE;
	}
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	//public String getNextActionParameter() {
	//	String menu = (String)getRequest().getSession(true).getAttribute("PROPOSAL_MENU");
	//	if(("Y").equals(menu)){
	//		return "action=FirstAccess";
	//	}
	//	return "action=FristApp";
	    
//	}
}
