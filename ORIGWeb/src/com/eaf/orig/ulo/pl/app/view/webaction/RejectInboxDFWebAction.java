/**
 * Create Date May 29, 2012 
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
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.pl.app.utility.WorkflowTool;
import com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM;

public class RejectInboxDFWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		logger.debug("RejectInboxDFWebAction");	 
			 UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			 ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
				if(menuM == null) menuM = new ProcessMenuM();			
		     WorkflowTool wfXMLUtil = new WorkflowTool();		
		     ORIGInboxDataM oInboxM = null;
		     try{
		    	 String currentRole = userM.getCurrentRole();
		    	 logger.debug("DF currentRole= "+currentRole);
		    	 userM.setCurrentRole(OrigConstant.ROLE_DF);//set role DF for work flow
		    	 oInboxM = wfXMLUtil.SearchWorkQueue(getRequest(), userM, menuM);
		    	 userM.setCurrentRole(OrigConstant.ROLE_DF_REJECT);//set role DF Reject for subform
		     }catch(Exception e){
		    	 logger.fatal("Error "+e.getMessage());
			 }			
		     if(null == oInboxM) oInboxM = new ORIGInboxDataM();	     
		     getRequest().getSession().setAttribute(ORIGInboxDataM.Constant.ORIG_INBOX,oInboxM);
			return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter() {
		return "page=DF_PL_REJECT_INBOX_SCREEN";
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
