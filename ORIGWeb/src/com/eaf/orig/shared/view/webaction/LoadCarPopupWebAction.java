/*
 * Created on Sep 25, 2007
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

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: LoadCarNamePopupWebaction
 */
public class LoadCarPopupWebAction extends WebActionHelper implements
        WebAction {
    Logger log  = Logger.getLogger(LoadCarPopupWebAction.class);
    private String nextAction = null;
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
        log.debug("Start LoadCarPopupWebAction");
        
        String idNo = getRequest().getParameter("seq");
        log.debug("idNo is:"+idNo);
        
        try {
        	StringBuffer sql = new StringBuffer();
        	StringBuffer sqlTemp = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			ValueListM valueListMCoClient = new ValueListM();
			int index = 0;
			int indexCoClient = 0;
			
			sql.append("SELECT V.VEHICLE_ID,V.CAR,V.BRAND,V.MODEL,V.GEAR,V.CC,L.TOTAL_OF_CARPRICE,V.DRAW_DOWN_STATUS FROM ORIG_VEHICLE V,ORIG_LOAN L ");
			sql.append("WHERE V.VEHICLE_ID = L.VEHICLE_ID AND V.DRAW_DOWN_STATUS = 'NEW' ");   
		    
                       
            if(!ORIGUtility.isEmptyString(idNo)){
			    sql.append("AND V.IDNO = ? ");
			    valueListM.setString(++index,idNo);
			}
			
			
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=CAR_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
       } catch (Exception e) {
           log.error("exception ",e);
       }

        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
		return FrontController.ACTION;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return nextAction;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
