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

/**
 * @author weeraya
 *
 * Type: SaveApplicationWebAction
 */
public class PDSaveApplicationWebAction extends WebActionHelper implements
        WebAction {

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
		if(ORIGUtility.isEmptyString(applicationDataM.getCreateBy())){
			applicationDataM.setCreateBy(userM.getUserName());
			applicationDataM.setUpdateBy(userM.getUserName());
		}else{
			applicationDataM.setUpdateBy(userM.getUserName());
		}
		ApplicationEvent event = new ApplicationEvent(ApplicationEvent.UW_SAVE, applicationDataM, userM);
		
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
    public boolean processEventResponse(EventResponse arg0) {
		 
		return defaultProcessResponse(arg0);
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
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchPD";
    }
    
    protected void doSuccess(EventResponse arg0) {
    	//ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	//ApplicationEventResponse eventResponse = (ApplicationEventResponse) arg0;
        //String errMsg = eventResponse.getMessage();
    	//formHandler.getAppForm().setApplicationNo(eventResponse.getAppNo());
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
