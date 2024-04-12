/*
 * Created on Nov 26, 2007
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
import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.ListBoxEvent;
import com.eaf.orig.shared.model.BusinessClassM;

/**
 * @author Administrator
 *
 * Type: UpdateListBoxWebAction
 */
public class UpdateListBoxWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(UpdateListBoxWebAction.class);
	private ListBoxMasterM listBoxM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ListBoxEvent listBoxEvent = new ListBoxEvent();
		listBoxEvent.setEventType(ListBoxEvent.LISTBOX_UPDATE);
		
		log.debug("ListBoxEvent.LISTBOX_UPDATE=" + ListBoxEvent.LISTBOX_UPDATE);
		
		listBoxEvent.setObject(listBoxM);
		
		log.debug("listBoxM=" + listBoxM);
		log.debug("listBoxEvent=" + listBoxEvent);
		
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

//		java.util.Enumeration names = getRequest().getParameterNames();
//		String name = null;
//		while(names.hasMoreElements()){
//			name = (String)names.nextElement();
//			System.out.println("name :"+name+" value :"+getRequest().getParameter(name));
//		}
		
		
	 	String listBoxID = getRequest().getParameter("lstID");
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
		
		 log.debug("listBoxID ="+listBoxID);
		 log.debug("displayName ="+displayName);
		 log.debug("choiceNo ="+choiceNo);
		 log.debug("fieldID ="+fieldID);
		 log.debug("userName ="+userName);
	//	 log.debug("SelectedBusinessClass ="+SelectedBusinessClass);
	//	 for (int i=0; i<SelectedBusinessClass.length;i++) {
	//	 	log.debug("SelectedBusinessClass "+i+ " = "+SelectedBusinessClass[i]);
	//	 }
		
		 listBoxM = new ListBoxMasterM(); 
		 
		 listBoxM.setListBoxID(listBoxID);
		 listBoxM.setDisplayName(displayName);
		 listBoxM.setChoiceNo(choiceNo);
		 listBoxM.setFieldID(fieldID);
		 listBoxM.setUpdateBy(userName);
		
	//	if(SelectedBusinessClass!=null && SelectedBusinessClass.length>=0){
			selBusVect = (Vector) getRequest().getSession().getAttribute("SELECTED_BUS");
			log.debug("selBusVect = "+selBusVect);
			log.debug("selBusVect.size() = "+selBusVect.size());
			if(selBusVect==null){
				selBusVect = new Vector();
			}
			listBoxM.setListBoxBusinessClass(selBusVect);
	//	}
		for(int i =0;i<selBusVect.size();i++){ 
			BusinessClassM busM = new BusinessClassM(); 
			busM = (BusinessClassM)selBusVect.get(i);
			log.debug("//selBusVect//busM.getId() ="+busM.getId());
		}
		
		log.debug("listBoxM.getListBoxID ="+listBoxM.getListBoxID());
		log.debug("listBoxM.getDisplayName ="+listBoxM.getDisplayName());
		log.debug("listBoxM.getChoiceNo ="+listBoxM.getChoiceNo());
		log.debug("listBoxM.getFieldID ="+listBoxM.getFieldID());
		log.debug("listBoxM.getListBoxBusinessClass ="+listBoxM.getListBoxBusinessClass());
	
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		origMasterForm.setFormErrors(new Vector());
		if (listBoxM.getDisplayName()==null || "".equalsIgnoreCase(listBoxM.getDisplayName())){
			origMasterForm.getFormErrors().add("Display Name is required");
		}
		if (listBoxM.getChoiceNo()==null || "".equalsIgnoreCase(listBoxM.getChoiceNo())){
			origMasterForm.getFormErrors().add("Choice Number is required");
		}
		if (listBoxM.getFieldID()==null || "".equalsIgnoreCase(listBoxM.getFieldID())){
			origMasterForm.getFormErrors().add("Field is required");
		}
		if (listBoxM.getListBoxBusinessClass()==null || listBoxM.getListBoxBusinessClass().size()==0){
			origMasterForm.getFormErrors().add("Selected Business Class is required");
		}
		
		if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
			getRequest().getSession().setAttribute("EDIT_LIST_BOX_DATAM",listBoxM);
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
        return "action=SearchListBox";
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
