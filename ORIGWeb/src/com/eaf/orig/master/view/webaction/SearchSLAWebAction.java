/*
 * Created on Jan 23, 2008
 * Created by Weeraya
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
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Weeraya
 *
 * Type: SearchSLAWebAction
 */
public class SearchSLAWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchSLAWebAction.class);
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
		
		String slaQueue = getRequest().getParameter("slaQueue");
		if(ORIGUtility.isEmptyString(slaQueue)){
			slaQueue = (String) getRequest().getSession().getAttribute("FIRST_SEARCH_slaQueue");
		}
		getRequest().getSession().setAttribute("FIRST_SEARCH_slaQueue", slaQueue);	
		
		logger.debug("slaQueue from parameter = "+slaQueue);
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT Q_NAME, STATUS, ACTION, JOB_STATE, ROLE, TIME ");
			sql.append("FROM SLA_NOTIFY ");
			if(!ORIGUtility.isEmptyString(slaQueue)){
				sql.append("WHERE Q_NAME = ?");
				valueListM.setString(++index,slaQueue);
        	}
			sql.append(" ORDER BY Q_NAME, ROLE");
			
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_SLA_SCREEN");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            ORIGMasterFormHandler formHandler = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
            formHandler.setShwAddFrm("search");
            
       } catch (Exception e) {
           logger.error("exception ",e);
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
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
