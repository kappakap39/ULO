/**
 * Create Date Apr 19, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.pl.app.utility.WorkflowTool;
import com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM;

/**
 * @author Sankom
 *
 */
public class InboxVCWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(this.getClass().getName());
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	@Override
	public Event toEvent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	@Override
	public boolean requiredModelRequest() {

		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	@Override
	public boolean processEventResponse(EventResponse response) {

		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	@Override
	public boolean preModelRequest() {
        logger.debug("InboxVCWebAction");        
		// UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		/* BPMUtility bpmUtil=new BPMUtility();		 
		 //load Inbox Data
		 String userName=userM.getUserName();
		 String role = userM.getCurrentRole();
		 String tdId = getRequest().getParameter("MenuID");
		 String queueType = getRequest().getParameter("queueType");
		 String atPage="0";
		 String itemPerPage="50";
		 String totalItem="";
		 boolean isNextPage=false;
		 PLInboxModelDataM inboxModel=bpmUtil.searchBPMJobList(userName, role, tdId, queueType, atPage, itemPerPage, totalItem, isNextPage);
		 logger.debug("inboxVect="+inboxModel);
		 getRequest().getSession().setAttribute("PL_INBOX_DATA",inboxModel) ;	*/
		 UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		 ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
			if(menuM == null) menuM = new ProcessMenuM();			
	     WorkflowTool wfXMLUtil = new WorkflowTool();		
	     ORIGInboxDataM oInboxM = null;
	     try{
	    	 oInboxM = wfXMLUtil.SearchWorkQueue(getRequest(), userM, menuM);	 	    	 
	     }catch(Exception e){
	    	 logger.fatal("Error "+e.getMessage());
		 }			
	     if(null == oInboxM) oInboxM = new ORIGInboxDataM();	     
	     getRequest().getSession().setAttribute(ORIGInboxDataM.Constant.ORIG_INBOX,oInboxM);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	@Override
	public int getNextActivityType() {

		return FrontController.PAGE;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
