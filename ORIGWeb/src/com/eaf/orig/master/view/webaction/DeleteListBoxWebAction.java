/*
 * Created on Nov 26, 2007
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
import com.eaf.orig.shared.control.event.ListBoxEvent;

/**
 * @author Administrator
 *
 * Type: DeleteListBoxWebAction
 */
public class DeleteListBoxWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(DeleteListBoxWebAction.class);
	String[] listBoxToDelete;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ListBoxEvent listBoxEvent = new ListBoxEvent();		
		listBoxEvent.setEventType(ListBoxEvent.LISTBOX_DELETE);
		
		log.debug("ListBoxEvent.LISTBOX_DELETE=" + ListBoxEvent.LISTBOX_DELETE);
		
		listBoxEvent.setObject(listBoxToDelete);
		
		log.debug("listBoxToDelete = " + listBoxToDelete);
		log.debug("listBoxEvent=" + listBoxEvent);
		
		return listBoxEvent;
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
		listBoxToDelete = getRequest().getParameterValues("listboxChk");
		    
		    if(listBoxToDelete == null || listBoxToDelete.length<=0){
		    	log.debug("////// listBoxToDelete is null //////" );
		    	return false;
		    }
		    
			return true;
	}

	public int getNextActivityType() {
         
        return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
    	
    //	System.out.println("Session FIRST_SEARCH_USER_NAME ->" + getRequest().getSession(true).getAttribute("FIRST_SEARCH_USER_NAME"));
    	
        return "action=SearchListBox";
    }
    protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
    	log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("ORIGListBox");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
