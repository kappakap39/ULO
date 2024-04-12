/*
 * Created on Dec 15, 2007
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

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

/**
 * @author Administrator
 *
 * Type: DisableAppScoreWebAction
 */
public class DisableAppScoreWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(DisableAppScoreWebAction.class);

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
		//*** Keep Data accept,reject
		String accept = getRequest().getParameter("accept");
		String reject = getRequest().getParameter("reject");
		String weight = getRequest().getParameter("weight");
		String scConstant = getRequest().getParameter("scConstant");
		
		getRequest().getSession().setAttribute("SC_CONSTANT",scConstant);
		getRequest().getSession().setAttribute("WEIGHT",weight);
		getRequest().getSession().setAttribute("ACCEPT",accept);
		getRequest().getSession().setAttribute("REJECT",reject);
		//***END Keep
		
		String noDataChk = getRequest().getParameter("noDataChk");
		String disableForm;
		
		log.debug("noDataChk = "+noDataChk);
		if("checked".equalsIgnoreCase(noDataChk)){
			disableForm = "yes";
			log.debug("disableForm = "+disableForm);
		}else{
			disableForm = "no";
			log.debug("disableForm = "+disableForm);
		}
		
		getRequest().getSession().setAttribute("DISABLE_FORM",disableForm);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
