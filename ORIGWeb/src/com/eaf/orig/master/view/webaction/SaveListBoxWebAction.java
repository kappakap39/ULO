/*
 * Created on Nov 22, 2007
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
import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.ListBoxEvent;

/**
 * @author Administrator
 *
 * Type: SaveListBoxWebAction
 */
public class SaveListBoxWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(SaveListBoxWebAction.class);
	private ListBoxMasterM listBoxMasterM;
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		ListBoxEvent listBoxEvent = new ListBoxEvent();
		//UserDetailM userDetailM = (UserDetailM)getRequest().getSession().getAttribute("SAVE_USER_DETAIL_DATAM");
		
		listBoxEvent.setEventType(ListBoxEvent.LISTBOX_SAVE);
		log.debug("OrigMasterEvent.LISTBOX_SAVE=" + ListBoxEvent.LISTBOX_SAVE);
		listBoxEvent.setObject(listBoxMasterM);
		log.debug("listBoxMasterM=" + listBoxMasterM);
		log.debug("origMasterEvent=" + listBoxEvent);
		
		return listBoxEvent;
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
		//**** clear session ****
		getRequest().getSession().removeAttribute("First_Search_ListBoxM");
		getRequest().getSession().removeAttribute("BUS_ID");
		//****

	//	String listBoxID = getRequest().getParameter("listBoxID");
		String displayName = getRequest().getParameter("displayNameAdd");
		String choiceNo = getRequest().getParameter("choiceNo");
		String fieldID = getRequest().getParameter("fieldIDAdd");
		Vector selBusVect = new Vector();
	//	String[]  SelectedBusinessClass = getRequest().getParameterValues("selectedBusinessClass");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		
		// log.debug("listBoxID ="+listBoxID);
		 log.debug("displayName ="+displayName);
		 log.debug("choiceNo ="+choiceNo);
		 log.debug("fieldID ="+fieldID);
	//	 log.debug("SelectedBusinessClass ="+SelectedBusinessClass);
		 log.debug("userName ="+userName);
		
		listBoxMasterM = new ListBoxMasterM(); 
		 
		//listBoxMasterM.setListBoxID(listBoxID);
		listBoxMasterM.setDisplayName(displayName);
		listBoxMasterM.setChoiceNo(choiceNo);
		listBoxMasterM.setFieldID(fieldID);
		listBoxMasterM.setUpdateBy(userName);
		
			selBusVect = (Vector) getRequest().getSession().getAttribute("SELECTED_BUS");
			if(selBusVect==null){
				selBusVect = new Vector();
			}
			listBoxMasterM.setListBoxBusinessClass(selBusVect);
		
		//log.debug("listBoxMasterM.getListBoxID ="+listBoxMasterM.getListBoxID());
		log.debug("listBoxMasterM.getDisplayName ="+listBoxMasterM.getDisplayName());
		log.debug("listBoxMasterM.getChoiceNo ="+listBoxMasterM.getChoiceNo());
		log.debug("listBoxMasterM.getFieldID ="+listBoxMasterM.getFieldID());
		log.debug("listBoxMasterM.getListBoxBusinessClass ="+listBoxMasterM.getListBoxBusinessClass());
		
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		origMasterForm.setFormErrors(new Vector());
		if (listBoxMasterM.getDisplayName()==null || "".equalsIgnoreCase(listBoxMasterM.getDisplayName())){
			origMasterForm.getFormErrors().add("Display Name is required");
		}
		if (listBoxMasterM.getChoiceNo()==null || "".equalsIgnoreCase(listBoxMasterM.getChoiceNo())){
			origMasterForm.getFormErrors().add("Choice Number is required");
		}
		if (listBoxMasterM.getFieldID()==null || "".equalsIgnoreCase(listBoxMasterM.getFieldID())){
			origMasterForm.getFormErrors().add("Field is required");
		}
		if (listBoxMasterM.getListBoxBusinessClass()==null || listBoxMasterM.getListBoxBusinessClass().size()==0){
			origMasterForm.getFormErrors().add("Selected Business Class is required");
		}
		
		if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
			getRequest().getSession().setAttribute("ADD_LIST_BOX_DATAM",listBoxMasterM);
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
	
	protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
//		***Refresh Cache
		log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("ORIGListBox");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
