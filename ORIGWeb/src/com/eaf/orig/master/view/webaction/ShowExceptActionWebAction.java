/*
 * Created on Nov 18, 2007
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
import com.eaf.orig.master.shared.model.ExceptActionM;
import com.eaf.orig.shared.control.event.ExceptActEvent;

/**
 * @author Administrator
 *
 * Type: ShowExceptActionWebAction
 */
public class ShowExceptActionWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(ShowExceptActionWebAction.class);
	
	ExceptActionM exceptActM =new ExceptActionM(); 

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ExceptActEvent exceptActEvent = new ExceptActEvent();
		
		exceptActEvent.setEventType(ExceptActEvent.EXCEPT_ACT_SELECT);
		
		log.debug("ExceptActEvent.EXCEPT_ACT_SELECT=" + ExceptActEvent.EXCEPT_ACT_SELECT);
		
		exceptActEvent.setObject(exceptActM);
		
		log.debug("exceptActM = " + exceptActM);
		log.debug("exceptActM.getExceptionId() = " + exceptActM.getExceptionId());
		log.debug("exceptActEvent=" + exceptActEvent);
		
		return exceptActEvent;
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
	public boolean processEventResponse(EventResponse arg0) {
		
		return defaultProcessResponse(arg0);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		// show edit form
		log.debug("///ShowExceptActionWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String exceptIDEdit = getRequest().getParameter("exceptIDEdit");
						
			exceptActM.setExceptionId(exceptIDEdit);
			getRequest().getSession().setAttribute("EXCEPT_ID_EDIT",exceptIDEdit);
			
			return true;
		}
		// just show add form & clear session
		if(!"cancelEdit".equals(shwAddFrm)){
			getRequest().getSession().removeAttribute("FIRST_SEARCH_exceptionName");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_exceptionDesc");
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return 0;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("//from Action//exceptActM = " + (ExceptActionM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_EXCEPT_ACT_DATAM", (ExceptActionM)arg0.getEncapData());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
