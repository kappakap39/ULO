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
import com.eaf.orig.master.shared.model.ExceptActionM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.ExceptActEvent;

/**
 * @author Administrator
 *
 * Type: SaveExceptActionWebAction
 */
public class SaveExceptActionWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(SaveExceptActionWebAction.class);
	private ExceptActionM exceptActM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ExceptActEvent exceptActEvent = new ExceptActEvent();
		
		exceptActEvent.setEventType(ExceptActEvent.EXCEPT_ACT_SAVE);
		log.debug("ExceptActEvent.EXCEPT_ACT_SAVE=" + ExceptActEvent.EXCEPT_ACT_SAVE);
		exceptActEvent.setObject(exceptActM);
		log.debug("exceptActM=" + exceptActM);
		log.debug("exceptActEvent=" + exceptActEvent);
		
		return exceptActEvent;
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
		getRequest().getSession().removeAttribute("FIRST_SEARCH_exceptionName");
		getRequest().getSession().removeAttribute("FIRST_SEARCH_exceptionDesc");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		
		String exceptionName = getRequest().getParameter("exceptionName");
		String exceptionDesc = getRequest().getParameter("exceptionDesc");
		 
		 log.debug("exceptionName ="+exceptionName);
		 log.debug("exceptionDesc ="+exceptionDesc);
		 log.debug("userName ="+userName);
		 		 
		 exceptActM = new ExceptActionM(); 
		 
		 exceptActM.setExceptionName(exceptionName);
		 exceptActM.setExceptionDesc(exceptionDesc);
		 exceptActM.setCreateBy(userName);
		 exceptActM.setUpdateBy(userName);
		
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (exceptActM.getExceptionName()==null || "".equalsIgnoreCase(exceptActM.getExceptionName())){
				origMasterForm.getFormErrors().add("Exception Action Name is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("ADD_EXCEPT_ACT_DATAM",exceptActM);
				return false;
			}else {
				getRequest().getSession().setAttribute("ADD_EXCEPT_ACT_DATAM",exceptActM);
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
	}
	protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
		getRequest().getSession().removeAttribute("ADD_FIELD_ID_DATAM");
		
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
