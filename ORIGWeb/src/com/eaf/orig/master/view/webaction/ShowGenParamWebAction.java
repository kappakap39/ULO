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
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.shared.control.event.GenParamEvent;

/**
 * @author Administrator
 *
 * Type: ShowGenParamWebAction
 */
public class ShowGenParamWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(ShowGenParamWebAction.class);
	
	GeneralParamM genParamM = new GeneralParamM();
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		GenParamEvent genParamEvent = new GenParamEvent();
		
		genParamEvent.setEventType(GenParamEvent.GEN_PARAM_SELECT);
		
		log.debug("GenParamEvent.GEN_PARAM_SELECT=" + GenParamEvent.GEN_PARAM_SELECT);
		
		genParamEvent.setObject(genParamM);
		
		log.debug("genParamM = " + genParamM);
		log.debug("genParamM.getParamCode() = " + genParamM.getParamCode() );
		log.debug("genParamM.getBusClassID() = " + genParamM.getBusClassID() );
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
	public boolean processEventResponse(EventResponse arg0) {
		
		return defaultProcessResponse(arg0);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		// show edit form
		log.debug("///ShowGenParamWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String genParamCde = getRequest().getParameter("genParamCde");
			String busID = getRequest().getParameter("busID");
						
			genParamM.setParamCode(genParamCde);
			genParamM.setBusClassID(busID);
			
			return true;
		}
		// just show add form & clear session
		if(!"cancelEdit".equals(shwAddFrm)){
			getRequest().getSession().removeAttribute("FIRST_SEARCH_paramCode");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_paramValue");
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
		log.debug("//from Action//genParamM = " + (GeneralParamM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_GEN_PARAM_M", (GeneralParamM)arg0.getEncapData());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
