/*
 * Created on Dec 13, 2007
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
 * Type: CancelScoreTypeWebAction
 */
public class CancelScoreTypeWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(CancelScoreTypeWebAction.class);

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
		
		log.debug("i'm canceling scoreType");
		
		//***Remove SESSION
		getRequest().getSession().removeAttribute("CUS_TYPE");
		getRequest().getSession().removeAttribute("CUS_THAI_DESC");
		getRequest().getSession().removeAttribute("SCORE_TYPE_VECT");
		getRequest().getSession().removeAttribute("SCORE_TYPE");
		getRequest().getSession().removeAttribute("CHAR_CODE");
		getRequest().getSession().removeAttribute("SPECIFIC_EDIT");
		getRequest().getSession().removeAttribute("WEIGHT");
		getRequest().getSession().removeAttribute("CHAR_TYPE_M_EDIT");
		getRequest().getSession().removeAttribute("SEARCH_SCORE_TYPE_VECT");
		getRequest().getSession().removeAttribute("SEARCH_CHAR_TYPE_HASH");
		getRequest().getSession().removeAttribute("CHAR_TYPE_VECT");
		getRequest().getSession().removeAttribute("SEL_AND_ADD_CHAR_M_VECT");
		getRequest().getSession().removeAttribute("SPECIFIC_LIST_BOX");
		getRequest().getSession().removeAttribute("ACCEPT");
		getRequest().getSession().removeAttribute("REJECT");
		getRequest().getSession().removeAttribute("DISABLE_FORM");
		getRequest().getSession().removeAttribute("SC_CONSTANT");
		//***END Remove
		
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
