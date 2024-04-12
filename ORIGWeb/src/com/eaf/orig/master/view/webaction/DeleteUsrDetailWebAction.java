/*
 * Created on Nov 21, 2007
 * Created by Prawit Limwattanachai
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
import com.eaf.orig.shared.control.event.OrigMasterEvent;

/**
 * @author Administrator
 *
 * Type: DeleteUsrDetailWebAction
 */
public class DeleteUsrDetailWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(DeleteUsrDetailWebAction.class);
	String[] userDetailToDelete;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		OrigMasterEvent origMasterEvent = new OrigMasterEvent();		
		origMasterEvent.setEventType(OrigMasterEvent.USER_DETAIL_DELETE);
		
		log.debug("OrigMasterEvent.USER_DETAIL_DELETE=" + OrigMasterEvent.USER_DETAIL_DELETE);
		
		origMasterEvent.setObject(userDetailToDelete);
		
		log.debug("userDetailToDelete = " + userDetailToDelete);
		log.debug("origMasterEvent=" + origMasterEvent);
		
		return origMasterEvent;
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

		return defaultProcessResponse(response);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		// *** remove SESSION
		getRequest().getSession().removeAttribute("SELECTED_BRANCH");
		getRequest().getSession().removeAttribute("SELECTED_USER_NAME");
		getRequest().getSession().removeAttribute("SELECTED_EXCEPT_ACT");
		getRequest().getSession().removeAttribute("SEARCH_BRANCH");
		getRequest().getSession().removeAttribute("SEARCH_USER_NAME");
		getRequest().getSession().removeAttribute("SEARCH_EXCEPT");
		getRequest().getSession().removeAttribute("BRANCH_DESC_SEARCH");
		getRequest().getSession().removeAttribute("USER_NAME_DESC_SEARCH");
		getRequest().getSession().removeAttribute("EXCEPT_ACT_DESC_SEARCH");
		getRequest().getSession().removeAttribute("AddOrEdit_SESSION");
		getRequest().getSession().removeAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE");
		getRequest().getSession().removeAttribute("EDIT_USER_DETAIL_DATAM");
		getRequest().getSession().removeAttribute("ADD_USER_DETAIL_DATAM");
		// *** END REMOVE
	    
	    userDetailToDelete = getRequest().getParameterValues("userDetailChk");
	    
	    if(userDetailToDelete == null || userDetailToDelete.length<=0){
	    	log.debug("////// userDetailToDelete is null //////" );
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
    	
    //	System.out.println("Session FIRST_SEARCH_USER_NAME ->" + getRequest().getSession(true).getAttribute("FIRST_SEARCH_USER_NAME"));
    	
        return "action=SearchUserDetail";
    }
    
    protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
    	log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("UsrName");
		com.eaf.cache.TableLookupCache.refreshCache("UserName");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
