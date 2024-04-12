/*
 * Created on Nov 27, 2007
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
import com.eaf.orig.shared.control.event.UserTeamEvent;

/**
 * @author Administrator
 *
 * Type: DeleteUserTeamWebAction
 */
public class DeleteUserTeamWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(DeleteUserTeamWebAction.class);
	String[] teamIdToDelete;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		UserTeamEvent userTeamEvent = new UserTeamEvent();	
		userTeamEvent.setEventType(UserTeamEvent.USER_TEAM_DELETE);
		
		log.debug("UserTeamEvent.USER_TEAM_DELETE=" + UserTeamEvent.USER_TEAM_DELETE);
		
		userTeamEvent.setObject(teamIdToDelete);
		
		log.debug("teamIdToDelete = " + teamIdToDelete);
		log.debug("userTeamEvent=" + userTeamEvent);
		
		return userTeamEvent;
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
	    
		teamIdToDelete = getRequest().getParameterValues("userTeamChk");
		
	    
	    if(teamIdToDelete == null || teamIdToDelete.length<=0){
	    	log.debug("////// teamIdToDelete is null //////" );
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
    
    public String getNextActionParameter() {
    	
        return "action=SearchUserTeam";
    }
    
    protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
//    	log.debug("sorry i'm refreshing Cache.!!!" );
//		com.eaf.cache.TableLookupCache.refreshAll();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
