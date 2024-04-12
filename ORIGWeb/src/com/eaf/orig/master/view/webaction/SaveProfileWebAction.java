/*
 * Created on Dec 3, 2007
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
import com.eaf.orig.profile.model.UserDetailM;

/**
 * @author Administrator
 *
 * Type: SaveProfileWebAction
 */
public class SaveProfileWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(SaveProfileWebAction.class);
	private UserDetailM userDetailM;
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
			
			Vector selUsrBranchVect =  (Vector)getRequest().getSession().getAttribute("SELECTED_BRANCH");
			Vector selUsrProfileVect =  (Vector)getRequest().getSession().getAttribute("SELECTED_USER_NAME");
			Vector selUsrExceptActVect =  (Vector)getRequest().getSession().getAttribute("SELECTED_EXCEPT_ACT");
			
			log.debug("selUsrBranchVect ="+selUsrBranchVect);
			log.debug("selUsrProfileVect ="+selUsrProfileVect);
			log.debug("selUsrExceptActVect ="+selUsrExceptActVect);
			
//			getRequest().getSession().removeAttribute("SELECTED_BRANCH");
//			getRequest().getSession().removeAttribute("SELECTED_USER_NAME");
//			getRequest().getSession().removeAttribute("SELECTED_EXCEPT_ACT");
//			getRequest().getSession().removeAttribute("SEARCH_BRANCH");
//			getRequest().getSession().removeAttribute("SEARCH_USER_NAME");
//			getRequest().getSession().removeAttribute("SEARCH_EXCEPT");
//			getRequest().getSession().removeAttribute("BRANCH_DESC_SEARCH");
//			getRequest().getSession().removeAttribute("USER_NAME_DESC_SEARCH");
//			getRequest().getSession().removeAttribute("EXCEPT_ACT_DESC_SEARCH");
			getRequest().getSession().removeAttribute("AddOrEdit_SESSION");
			
			userDetailM = new UserDetailM();
			userDetailM.setUserBranchVect(selUsrBranchVect);
			userDetailM.setUserProfileVect(selUsrProfileVect);
			userDetailM.setUserExceptionVect(selUsrExceptActVect);
			
			getRequest().getSession().setAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE", userDetailM);
			
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
