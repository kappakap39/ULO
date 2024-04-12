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
import com.eaf.orig.shared.control.event.GenParamEvent;

/**
 * @author Administrator
 *
 * Type: DeleteGenParamWebAction
 */
public class DeleteGenParamWebAction extends WebActionHelper implements
		WebAction {

	Logger log = Logger.getLogger(DeleteGenParamWebAction.class);
	String[] genParamToDelete;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		GenParamEvent genParamEvent = new GenParamEvent();		
		genParamEvent.setEventType(GenParamEvent.GEN_PARAM_DELETE);
		
		log.debug("GenParamEvent.GEN_PARAM_DELETE=" + GenParamEvent.GEN_PARAM_DELETE);
		
		genParamEvent.setObject(genParamToDelete);
		
		log.debug("genParamToDelete = " + genParamToDelete);
		log.debug("genParamEvent=" + genParamEvent);
		
		return genParamEvent;
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
		genParamToDelete = getRequest().getParameterValues("genParamChk");
		
		// *** log genParamToDelete
		if(genParamToDelete!=null && genParamToDelete.length>0){
			log.debug("////// genParamToDelete ="+genParamToDelete);
			log.debug("////// genParamToDelete.length ="+genParamToDelete.length);
			for(int i=0;i<genParamToDelete.length;i++){
				log.debug("////// genParamToDelete "+i+" = "+ genParamToDelete[i]);
			}
		}
		// ****
	    
	    if(genParamToDelete == null || genParamToDelete.length<=0){
	    	log.debug("////// genParamToDelete is null //////" );
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
    	
        return "action=SearchGenParam";
    }
    protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
    	log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("GeneralParamDataM");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
