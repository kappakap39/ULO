/*
 * Created on Nov 15, 2007
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
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.OrigMasterEvent;

/**
 * @author Administrator
 *
 * Type: ShowUserDetailWebAction
 */
public class ShowUserDetailWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(ShowUserDetailWebAction.class);
	
	UserDetailM userDetailM =new UserDetailM(); 

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		OrigMasterEvent origMasterEvent = new OrigMasterEvent();
		
		origMasterEvent.setEventType(OrigMasterEvent.USER_DETAIL_SELECT);
		
		log.debug("OrigMasterEvent.USER_DETAIL_SELECT=" + OrigMasterEvent.USER_DETAIL_SELECT);
		
		origMasterEvent.setObject(userDetailM);
		
		log.debug("userDetailM = " + userDetailM);
		log.debug("userDetailM.getUserName() = " + userDetailM.getUserName());
		log.debug("origMasterEvent=" + origMasterEvent);
		
		return origMasterEvent;
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
		String showProfile = getRequest().getParameter("showProfile");
		String AddOrEdit = getRequest().getParameter("AddOrEdit");
		log.debug("AddOrEdit ="+AddOrEdit);
		
		// show edit form
		log.debug("///ShowUserDetailWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equalsIgnoreCase(shwAddFrm) && !"cancelFromProf".equalsIgnoreCase(showProfile)){
			log.debug("!!! NOW i'm select data to edit");
			String userNameEdit = getRequest().getParameter("userNameEdit");
						
			userDetailM.setUserName(userNameEdit);
			
			return true;
		}
		// just show add form
		if(!"cancelEdit".equalsIgnoreCase(shwAddFrm)){
			log.debug("!!! NOW i'm remove all SESSION");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_USER_NAME");
//			 *** remove SESSION
			getRequest().getSession().removeAttribute("SELECTED_BRANCH");
			getRequest().getSession().removeAttribute("SELECTED_USER_NAME");
			getRequest().getSession().removeAttribute("SELECTED_EXCEPT_ACT");
			getRequest().getSession().removeAttribute("SEARCH_BRANCH");
			getRequest().getSession().removeAttribute("SEARCH_USER_NAME");
			getRequest().getSession().removeAttribute("SEARCH_EXCEPT");
			getRequest().getSession().removeAttribute("BRANCH_DESC_SEARCH");
			getRequest().getSession().removeAttribute("USER_NAME_DESC_SEARCH");
			getRequest().getSession().removeAttribute("EXCEPT_ACT_DESC_SEARCH");
			getRequest().getSession().removeAttribute("AddOrEdit_SESSION");
			getRequest().getSession().removeAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE");
			getRequest().getSession().removeAttribute("EDIT_USER_DETAIL_DATAM");
			getRequest().getSession().removeAttribute("ADD_USER_DETAIL_DATAM");
			// *** END REMOVE
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
		log.debug("//from Action//userDetailM = " + (UserDetailM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_USER_DETAIL_DATAM", (UserDetailM)arg0.getEncapData());
		
		userDetailM = (UserDetailM)arg0.getEncapData();
		getRequest().getSession().setAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE", userDetailM);
		getRequest().getSession().setAttribute("SELECTED_BRANCH", userDetailM.getUserBranchVect());
		getRequest().getSession().setAttribute("SELECTED_USER_NAME", userDetailM.getUserProfileVect());
		getRequest().getSession().setAttribute("SELECTED_EXCEPT_ACT", userDetailM.getUserExceptionVect());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
