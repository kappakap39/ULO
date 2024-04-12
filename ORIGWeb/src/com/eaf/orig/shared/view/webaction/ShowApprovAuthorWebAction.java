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
package com.eaf.orig.shared.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.shared.control.event.ApprovAuthorEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: ShowApprovAuthorWebAction
 */
public class ShowApprovAuthorWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(ShowApprovAuthorWebAction.class);
	private ApprovAuthorM approvAuthorM = new ApprovAuthorM();

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ApprovAuthorEvent approvAthrEvent = new ApprovAuthorEvent();
		
		approvAthrEvent.setEventType(ApprovAuthorEvent.APPROV_AUTHOR_SELECT);
		
		log.debug("ApprovAuthorEvent.APPROV_AUTHOR_SELECT=" + ApprovAuthorEvent.APPROV_AUTHOR_SELECT);
		
		approvAthrEvent.setObject(approvAuthorM);
		
		log.debug("approvAuthorM = " + approvAuthorM);
		log.debug("approvAuthorM.getGroupName() = " + approvAuthorM.getGroupName());
		log.debug("approvAuthorM.getLoanType() = " + approvAuthorM.getLoanType());
		log.debug("approvAuthorM.getCustomerType() = " + approvAuthorM.getCustomerType());
		log.debug("approvAthrEvent=" + approvAthrEvent);
		
		return approvAthrEvent;
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
		
		// show edit form
		log.debug("///ShowApprovAuthorWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String grpNm = getRequest().getParameter("grpNm");
			String lnTyp = getRequest().getParameter("lnTyp");
			String cusTyp = getRequest().getParameter("cusTyp");

			approvAuthorM.setGroupName(grpNm);
			approvAuthorM.setLoanType(lnTyp);
			approvAuthorM.setCustomerType(cusTyp);
			
			return true;
		}
		// just show add form & clear session
		if(!"cancelEdit".equals(shwAddFrm)){
			getRequest().getSession().removeAttribute("FIRST_SEARCH_groupName");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_loanType");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_customerType");
		}
		return false;
	}

	public int getNextActivityType() {

		return 0;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("//from Action//approvAuthorM = " + (ApprovAuthorM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_APPROV_AUTHOR_DATAM", (ApprovAuthorM)arg0.getEncapData());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
