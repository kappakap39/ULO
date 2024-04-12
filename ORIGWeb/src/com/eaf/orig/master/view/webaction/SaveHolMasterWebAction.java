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

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.HolidayM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.HolMasterEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveHolMasterWebAction
 */
public class SaveHolMasterWebAction extends WebActionHelper implements
		WebAction {

	Logger log = Logger.getLogger(SaveHolMasterWebAction.class);
	private HolidayM holidayM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		HolMasterEvent holMasterEvent = new HolMasterEvent();
		
		holMasterEvent.setEventType(HolMasterEvent.HOL_MASTER_SAVE);
		log.debug("HolMasterEvent.HOL_MASTER_SAVE=" + HolMasterEvent.HOL_MASTER_SAVE);
		holMasterEvent.setObject(holidayM);
		log.debug("holidayM=" + holidayM);
		log.debug("holMasterEvent=" + holMasterEvent);
		
		return holMasterEvent;
		
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
		//clear session for search field
		getRequest().getSession().removeAttribute("FIRST_SEARCH_HOL_DATE");
		getRequest().getSession().removeAttribute("FIRST_SEARCH_HOL_DESC");
		
		String holDate = getRequest().getParameter("holidayDate");
		String holDesc = getRequest().getParameter("holidayDesc");
		String workingFlag = getRequest().getParameter("workingFlag");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		 
		 log.debug("holDate ="+holDate);
		 log.debug("holDesc ="+holDesc);
		 log.debug("userName ="+userName);
		 		 
		 holidayM = new HolidayM(); 
		 
		 holidayM.setHolDate(ORIGUtility.parseThaiToEngDate(holDate));
		 holidayM.setHolDesc(holDesc);
		 holidayM.setWorkFlag(workingFlag);
		 holidayM.setUpdateBy(userName);
		 holidayM.setCreateBy(userName);
		
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		 origMasterForm.setShwAddFrm("add");
			origMasterForm.setFormErrors(new Vector());
			if (holidayM.getHolDate()==null || "".equalsIgnoreCase(String.valueOf(holidayM.getHolDate()))){
				origMasterForm.getFormErrors().add("Holiday Date is required");
			}
			if (holidayM.getHolDesc()==null || "".equalsIgnoreCase(holidayM.getHolDesc())){
				origMasterForm.getFormErrors().add("Holiday Description is required");
			}
			if (holidayM.getWorkFlag()==null || "".equalsIgnoreCase(holidayM.getWorkFlag())){
				origMasterForm.getFormErrors().add("Working Flag is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("ADD_HOLIDAY_DATAM",holidayM);
				return false;
			}else {
				getRequest().getSession().setAttribute("ADD_HOLIDAY_DATAM",holidayM);
				return true;
			}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return 0;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		origMasterForm.setShwAddFrm("add");
        String errMsg = arg0.getMessage();
        origMasterForm.getFormErrors().add(errMsg);
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("i'm in doSuccess.!!!");
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
		getRequest().getSession().removeAttribute("ADD_HOLIDAY_DATAM");
		
//		***Refresh Cache
//    	log.debug("sorry i'm refreshing Cache.!!!" );
//		com.eaf.cache.TableLookupCache.refreshAll();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
