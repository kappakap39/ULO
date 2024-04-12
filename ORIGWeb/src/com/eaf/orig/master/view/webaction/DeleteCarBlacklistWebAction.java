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
import com.eaf.orig.shared.control.event.CarBlacklistEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: DeleteCarBlacklistWebAction
 */
public class DeleteCarBlacklistWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(DeleteCarBlacklistWebAction.class);
	String[] blackVehIDToDelete;
	int[] blackVehID_intToDelete;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		CarBlacklistEvent carBlacklistEvent = new CarBlacklistEvent();		
		carBlacklistEvent.setEventType(CarBlacklistEvent.CAR_BKLT_DELETE);
		
		log.debug("CarBlacklistEvent.CAR_BKLT_DELETE=" + CarBlacklistEvent.CAR_BKLT_DELETE);
		
		carBlacklistEvent.setObject(blackVehID_intToDelete);
		
		log.debug("blackVehID_intToDelete = " + blackVehID_intToDelete);
		log.debug("carBlacklistEvent=" + carBlacklistEvent);
		
		return carBlacklistEvent;
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
		blackVehIDToDelete = getRequest().getParameterValues("carBlacklistChk");
		
		ORIGUtility utility = new ORIGUtility();
	    
	    if(blackVehIDToDelete == null || blackVehIDToDelete.length<=0){
	    	log.debug("////// blackVehIDToDelete is null //////" );
	    	return false;
	    }
	    
	    blackVehID_intToDelete = new int[blackVehIDToDelete.length];
	    for(int i = 0; i < blackVehIDToDelete.length; i++){
	    	blackVehID_intToDelete[i] = utility.stringToInt(blackVehIDToDelete[i]);
	    	log.debug("////// blackVehID_intToDelete "+i+" "+blackVehID_intToDelete[i] );
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
    	
        return "action=SearchCarBlacklist";
    }
    protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
    	log.debug("sorry i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshAll();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
