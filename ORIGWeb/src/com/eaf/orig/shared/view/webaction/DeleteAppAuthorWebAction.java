/*
 * Created on Nov 28, 2007
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
package com.eaf.orig.shared.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.control.event.ApprovAuthorEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: DeleteAppAuthorWebAction
 */
public class DeleteAppAuthorWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(DeleteAppAuthorWebAction.class);
	String[] appAuthorToDelete;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ApprovAuthorEvent authorEvent = new ApprovAuthorEvent();		
		authorEvent.setEventType(ApprovAuthorEvent.POLICY_VERSION_DELETE);
		
		log.debug("ApprovAuthorEvent.POLICY_VERSION_DELETE=" + ApprovAuthorEvent.POLICY_VERSION_DELETE);
		
		authorEvent.setObject(appAuthorToDelete);
		
		log.debug("POLICY_VERSION_DELETE = " + appAuthorToDelete);
		log.debug("authorEvent=" + authorEvent);
		
		return authorEvent;
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
		ORIGUtility utility = new ORIGUtility();
		appAuthorToDelete = getRequest().getParameterValues("approvAuthorChk");
		
		// *** log genParamToDelete
		if(appAuthorToDelete!=null && appAuthorToDelete.length>0){
			log.debug("////// appAuthorToDelete ="+appAuthorToDelete);
			log.debug("////// appAuthorToDelete.length ="+appAuthorToDelete.length);
			for(int i=0;i<appAuthorToDelete.length;i++){
				log.debug("////// appAuthorToDelete "+i+" = "+ appAuthorToDelete[i]);
			}
		}
		// ****
	    
	    if(appAuthorToDelete == null || appAuthorToDelete.length<=0){
	    	log.debug("////// appAuthorToDelete is null //////" );
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
    	
        return "action=SearchApprovAuthor";
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
