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
import com.eaf.orig.master.shared.model.CarBlacklistM;
import com.eaf.orig.shared.control.event.CarBlacklistEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: ShowCarBlacklistWebAction
 */
public class ShowCarBlacklistWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(ShowCarBlacklistWebAction.class);
	private CarBlacklistM carBlacklistM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		CarBlacklistEvent carBlacklistEvent = new CarBlacklistEvent();
		
		carBlacklistEvent.setEventType(CarBlacklistEvent.CAR_BKLT_SELECT);
		
		log.debug("CarBlacklistEvent.CAR_BKLT_SELECT=" + CarBlacklistEvent.CAR_BKLT_SELECT);
		
		carBlacklistEvent.setObject(carBlacklistM);
		
		log.debug("carBlacklistM = " + carBlacklistM);
		log.debug("carBlacklistM.getBlVehicleID() = " + carBlacklistM.getBlVehicleID());
		log.debug("carBlacklistEvent=" + carBlacklistEvent);
		
		return carBlacklistEvent;
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
		ORIGUtility utility = new ORIGUtility();
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		if("add".equalsIgnoreCase(shwAddFrm)){
			getRequest().getSession().removeAttribute("FIRST_SEARCH_chassisNum");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_regisNum");
			getRequest().getSession().removeAttribute("BLACK_VEH_ID_EDIT");
			getRequest().getSession().removeAttribute("ADD_CAR_BLACKLIST_DATAM");
		}
		
		carBlacklistM = new CarBlacklistM();
		
		// show edit form
		log.debug("///ShowCarBlacklistWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String blackVehEdit = getRequest().getParameter("blackVehEdit");
			
			getRequest().getSession().setAttribute("BLACK_VEH_ID_EDIT",blackVehEdit);
						
			carBlacklistM.setBlVehicleID(utility.stringToInt(blackVehEdit));
			
			return true;
		}
		// just show add form & clear session
		if(!"cancelEdit".equalsIgnoreCase(shwAddFrm)){
//			getRequest().getSession().removeAttribute("FIRST_SEARCH_chassisNum");
//			getRequest().getSession().removeAttribute("FIRST_SEARCH_regisNum");
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
		log.debug("//from Action//carBlacklistM = " + (CarBlacklistM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_CAR_BLACKLIST_DATAM", (CarBlacklistM)arg0.getEncapData());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
