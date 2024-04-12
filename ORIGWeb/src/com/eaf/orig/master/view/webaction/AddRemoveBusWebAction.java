/*
 * Created on Nov 23, 2007
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
import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.shared.model.BusinessClassM;

/**
 * @author Administrator
 *
 * Type: AddRemoveBusWebAction
 */
public class AddRemoveBusWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(AddRemoveBusWebAction.class);

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
		//*** for show Edit Data Changed
		String listBoxID = getRequest().getParameter("lstID");
		String displayName = getRequest().getParameter("displayNameAdd");
		String choiceNo = getRequest().getParameter("choiceNo");
		String fieldID = getRequest().getParameter("fieldIDAdd");
		String searchBusID = getRequest().getParameter("searchBusID");
		
		 log.debug("listBoxID ="+listBoxID);
		 log.debug("displayName ="+displayName);
		 log.debug("choiceNo ="+choiceNo);
		 log.debug("fieldID ="+fieldID);
		
		ListBoxMasterM listBoxMasterM = new ListBoxMasterM(); 
		 
		listBoxMasterM.setListBoxID(listBoxID);
		listBoxMasterM.setDisplayName(displayName);
		listBoxMasterM.setChoiceNo(choiceNo);
		listBoxMasterM.setFieldID(fieldID);
		
		getRequest().getSession().setAttribute("EDIT_LIST_BOX_DATAM",listBoxMasterM);
		getRequest().getSession().setAttribute("ADD_LIST_BOX_DATAM",listBoxMasterM);
		getRequest().getSession().setAttribute("BUS_ID",searchBusID);
		//*** END
		
		String[]  BusinessClass = getRequest().getParameterValues("businessClass");
		String[]  SelectedBusinessClass = getRequest().getParameterValues("selectedBusinessClass");
		Vector selBusVect = (Vector) getRequest().getSession().getAttribute("SELECTED_BUS");
		if(selBusVect==null){
			selBusVect = new Vector();
		}
		Vector busVect = (Vector) getRequest().getSession().getAttribute("SEARCH_BUS");
		if(busVect==null){
			busVect = new Vector();
		}
		String addOrRemove = getRequest().getParameter("addOrRemove");
		
		if((addOrRemove != null) && "add".equalsIgnoreCase(addOrRemove)){
			log.debug("///im adding selBusVect");		
				if (BusinessClass !=null && BusinessClass.length>0) {
						log.debug("------------------> all BusinessClass length = " + BusinessClass.length);
				
					for (int i=0; i<BusinessClass.length;i++) {
						String id= BusinessClass[i];
						BusinessClassM busM = new BusinessClassM();		
						busM.setId(id);
						selBusVect.add(busM);	
						
						/* remove from busVect */
						if(busVect.size()>0){
							BusinessClassM busSearchM = new BusinessClassM();
							for (int j=0; j<busVect.size();j++){
								busSearchM = (BusinessClassM)busVect.get(j);
								if((id).equals(busSearchM.getId())){
									busVect.removeElementAt(j);
								}
							}
						}
					}
				}	
		}else if((addOrRemove != null) && "remove".equalsIgnoreCase(addOrRemove)){
			log.debug("///im removing selBusVect");		
			if (SelectedBusinessClass !=null && SelectedBusinessClass.length>0) {
					log.debug("------------------> all SelectedBusinessClass length = " + SelectedBusinessClass.length);

				for (int i=0; i<SelectedBusinessClass.length;i++) {
					String id= SelectedBusinessClass[i];
					BusinessClassM busM = new BusinessClassM();		
					busM.setId(id);
					busVect.add(busM);	
					
					/* remove from selBusVect */
					if(selBusVect.size()>0){
						BusinessClassM busSelM =new BusinessClassM();
						for (int j=0; j<selBusVect.size();j++){
							busSelM = (BusinessClassM)selBusVect.get(j);
							if((id).equals(busSelM.getId())){
								selBusVect.removeElementAt(j);
							}
						}
					}
				}			
			}
		
		}	
		
		getRequest().getSession().setAttribute("SELECTED_BUS",selBusVect);
		getRequest().getSession().setAttribute("SEARCH_BUS",busVect);
		
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
