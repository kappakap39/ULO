/*
 * Created on Nov 25, 2007
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
import com.eaf.orig.shared.control.event.FieldIdEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: DeleteFieldIdWebAction
 */
public class DeleteFieldIdWebAction extends WebActionHelper implements
		WebAction {

	Logger log = Logger.getLogger(DeleteFieldIdWebAction.class);
	String[] fieldIdToDelete;
	int[] fieldId_IntToDel;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		FieldIdEvent fieldIdEvent = new FieldIdEvent();		
		fieldIdEvent.setEventType(FieldIdEvent.FIELD_ID_DELETE);
		
		log.debug("FieldIdEvent.FIELD_ID_DELETE=" + FieldIdEvent.FIELD_ID_DELETE);
		
		fieldIdEvent.setObject(fieldId_IntToDel);
		
		log.debug("fieldId_IntToDel = " + fieldId_IntToDel);
		log.debug("fieldIdEvent=" + fieldIdEvent);
		
		return fieldIdEvent;
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
	    
		fieldIdToDelete = getRequest().getParameterValues("fieldIdChk");
		
		ORIGUtility utility = new ORIGUtility();
	    
	    if(fieldIdToDelete == null || fieldIdToDelete.length<=0){
	    	log.debug("////// fieldIdToDelete is null //////" );
	    	return false;
	    }
	    
	    fieldId_IntToDel = new int[fieldIdToDelete.length];
	    for(int i = 0; i < fieldIdToDelete.length; i++){
	    	fieldId_IntToDel[i] = utility.stringToInt(fieldIdToDelete[i]);
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
    	
        return "action=SearchFieldId";
    }
    
    protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
    	log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("FieldID");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
