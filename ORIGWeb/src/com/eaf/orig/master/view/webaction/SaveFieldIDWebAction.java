/*
 * Created on Nov 25, 2007
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
import com.eaf.orig.master.shared.model.FieldIdM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.FieldIdEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveFieldIDWebAction
 */
public class SaveFieldIDWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(SaveFieldIDWebAction.class);
	private FieldIdM fieldIdM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		FieldIdEvent fieldIdEvent = new FieldIdEvent();
		//UserDetailM userDetailM = (UserDetailM)getRequest().getSession().getAttribute("SAVE_USER_DETAIL_DATAM");
		
		fieldIdEvent.setEventType(FieldIdEvent.FIELD_ID_SAVE);
		log.debug("FieldIdEvent.FIELD_ID_SAVE=" + FieldIdEvent.FIELD_ID_SAVE);
		fieldIdEvent.setObject(fieldIdM);
		log.debug("fieldIdM=" + fieldIdM);
		log.debug("fieldIdEvent=" + fieldIdEvent);
		
		return fieldIdEvent;
		
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
		getRequest().getSession().removeAttribute("FIRST_SEARCH_FIELD_ID");
		getRequest().getSession().removeAttribute("FIRST_SEARCH_FIELD_DESC");
		
		ORIGUtility utility = new ORIGUtility();
		String id = getRequest().getParameter("id");
		String description = getRequest().getParameter("description");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		 
		 log.debug("id ="+id);
		 log.debug("description ="+description);
		 log.debug("userName ="+userName);
		 		 
		 fieldIdM = new FieldIdM(); 
		 
		 fieldIdM.setFieldID(utility.stringToInt((id)));
		 fieldIdM.setFieldDesc(description);
		 fieldIdM.setUpdateBy(userName);
		 
		 getRequest().getSession().setAttribute("ADD_FIELD_ID_DATAM",fieldIdM);
		 
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (fieldIdM.getFieldID() <= 0){
				origMasterForm.getFormErrors().add("Field ID is required");
			}
			if (fieldIdM.getFieldDesc()==null || "".equalsIgnoreCase(fieldIdM.getFieldDesc())){
				origMasterForm.getFormErrors().add("Field Description is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				return false;
			}else {
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
        String errMsg = arg0.getMessage();
        origMasterForm.getFormErrors().add(errMsg);
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("sorry i'm in do success.!!!" );
		
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
		getRequest().getSession().removeAttribute("ADD_FIELD_ID_DATAM");
		
		//***Refresh Cache
		log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("FieldID");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
