/*
 * Created on Nov 13, 2007
 * Created by Sankom Sanpunya
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
package com.eaf.orig.shared.popup.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.xrules.shared.constant.XRulesConstant;

/**
 * @author Sankom
 *
 * Type: LoadXrulesPhoneVerPopupWebAction
 */
public class LoadXrulesPhoneVerPopupWebAction extends WebActionHelper implements
        WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(LoadXrulesPhoneVerPopupWebAction.class);
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
    public boolean processEventResponse(EventResponse response) {      
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        String firstPhoneVerPopup=getRequest().getParameter("firstPhoneVerPopup");
        log.debug(" LoadXrulesPhoneVerPopupWebAction preModelRequest() firstPhoneVerPopup "+firstPhoneVerPopup);
        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
        String personalTypeParam=getRequest().getParameter("appPersonalType");                
        if(personalTypeParam!=null&&!"".equals(personalTypeParam)&&!personalTypeParam.equals(personalType)){
            personalType=personalTypeParam;
            getRequest().getSession().setAttribute("PersonalType",personalTypeParam);
        }
        //clear phone ver item
        if( "Y".equalsIgnoreCase(firstPhoneVerPopup)){
           getRequest().getSession().removeAttribute("phoneVerItem");
           String txtResultName=getRequest().getParameter("txtResultName");
       	String txtButtonName=getRequest().getParameter("txtButtonName");
       	getRequest().getSession().setAttribute("txtResultName",txtResultName);
       	getRequest().getSession().setAttribute("txtButtonName",txtButtonName);
       	getRequest().getSession().setAttribute("xrulseExecuteService",String.valueOf(XRulesConstant.ServiceID.PHONE_VER));    
        }               
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

}
