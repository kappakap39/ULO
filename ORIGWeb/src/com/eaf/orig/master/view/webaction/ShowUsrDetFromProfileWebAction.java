/*
 * Created on Dec 1, 2007
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

/**
 * @author Administrator
 *
 * Type: ShowUsrDetFromProfileWebAction
 */
public class ShowUsrDetFromProfileWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(ShowUsrDetFromProfileWebAction.class);
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
		
		String userName = getRequest().getParameter("userName");
		 String firstName = getRequest().getParameter("firstName");
		 String lastName = getRequest().getParameter("lastName");
		 String description = getRequest().getParameter("description");
		 String region = getRequest().getParameter("region");
		 String telephone = getRequest().getParameter("telephone");
		 String mobilePhone = getRequest().getParameter("mobilePhone");
		 String email = getRequest().getParameter("email");
		 String jobDescription = getRequest().getParameter("jobDescription");
		 String department = getRequest().getParameter("department");
		 String zoneID = getRequest().getParameter("zone");
		 String status = getRequest().getParameter("active");
		 String position = getRequest().getParameter("position");
		 String skipIP = getRequest().getParameter("skipIP");
		 String communicationChannel = getRequest().getParameter("communicationChannel");
		 
		 log.debug("userName ="+userName);
		 log.debug("firstName ="+firstName);
		 log.debug("lastName ="+lastName);
		 log.debug("description ="+description);
		 log.debug("position ="+position);
		 log.debug("communicationChannel ="+communicationChannel);
		 log.debug("skipIP ="+skipIP);
		 log.debug("status ="+status);
		 		 
		 userDetailM = new UserDetailM(); 
		 
		 userDetailM.setUserName(userName);
		 userDetailM.setFirstName(firstName);
		 userDetailM.setLastName(lastName);
		 userDetailM.setDescription(description);
		 userDetailM.setRegion(region);
		 userDetailM.setTelephone(telephone);
		 userDetailM.setMobilePhone(mobilePhone);
		 userDetailM.setPosition(position);
		 userDetailM.setEmail(email);
		 userDetailM.setJobDescription(jobDescription);
		 userDetailM.setZoneID(zoneID);
		 userDetailM.setDepartment(department);
		 userDetailM.setStatus(status);
		 userDetailM.setSkipIP(skipIP);
		 userDetailM.setCommunicate_channel(communicationChannel);
		
		getRequest().getSession().setAttribute("EDIT_USER_DETAIL_DATAM", userDetailM);
		getRequest().getSession().setAttribute("ADD_USER_DETAIL_DATAM", userDetailM);
		
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
