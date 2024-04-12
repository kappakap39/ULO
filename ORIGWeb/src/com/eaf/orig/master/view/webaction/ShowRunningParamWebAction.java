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
import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.shared.control.event.RunParamEvent;

/**
 * @author Administrator
 *
 * Type: ShowRunningParamWebAction
 */
public class ShowRunningParamWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(ShowUserDetailWebAction.class);
	RunningParamM runParamM = new RunningParamM();

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		RunParamEvent runParamEvent = new RunParamEvent();
		
		runParamEvent.setEventType(RunParamEvent.RUN_PARAM_SELECT);
		
		log.debug("RunParamEvent.RUN_PARAM_SELECT=" + RunParamEvent.RUN_PARAM_SELECT);
		
		runParamEvent.setObject(runParamM);
		
		log.debug("runParamM = " + runParamM);
		log.debug("runParamM.getParamID() = " + runParamM.getParamID());
		log.debug("runParamM.getParamType() = " + runParamM.getParamType());
		log.debug("runParamEvent=" + runParamEvent);
		
		return runParamEvent;
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
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		// show edit form
		log.debug("///ShowRunningParamWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String runParamIDEdit = getRequest().getParameter("runParamIDEdit");
			String runParamType = getRequest().getParameter("runParamType");
			
			runParamM.setParamID(runParamIDEdit);
			runParamM.setParamType(runParamType);
			
			return true;
		}
		// just show add form & clear session
		if(!"cancelEdit".equals(shwAddFrm)){
			getRequest().getSession().removeAttribute("FIRST_SEARCH_paramID");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_paramDesc");
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
		log.debug("//from Action//runParamM = " + (RunningParamM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_RUN_PARAM_DATAM", (RunningParamM)arg0.getEncapData());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
