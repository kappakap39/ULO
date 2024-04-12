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

import com.eaf.j2ee.pattern.control.FrontController;
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
 * Type: UpdateFieldIdWebAction
 */
public class UpdateFieldIdWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(UpdateFieldIdWebAction.class);
	private FieldIdM fieldIdM;
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		FieldIdEvent fieldIdEvent = new FieldIdEvent();
		fieldIdEvent.setEventType(FieldIdEvent.FIELD_ID_UPDATE);
		
		log.debug("FieldIdEvent.FIELD_ID_UPDATE=" + FieldIdEvent.FIELD_ID_UPDATE);
		
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
		
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (fieldIdM.getFieldID() <= 0){
				origMasterForm.getFormErrors().add("Field ID is required");
			}
			if (fieldIdM.getFieldDesc()==null || "".equalsIgnoreCase(fieldIdM.getFieldDesc())){
				origMasterForm.getFormErrors().add("Field Description is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("EDIT_FIELD_ID_DATAM",fieldIdM);
				return false;
			}else {
				return true;
			}
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchFieldId";
    }
    
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
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
