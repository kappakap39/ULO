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
package com.eaf.orig.master.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.shared.control.event.ListBoxEvent;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: ShowListBoxMasterWebAction
 */
public class ShowListBoxMasterWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(ShowListBoxMasterWebAction.class);
	ListBoxMasterM listBoxM = new ListBoxMasterM();
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ListBoxEvent listBoxEvent = new ListBoxEvent();
		
		listBoxEvent.setEventType(ListBoxEvent.LISTBOX_SELECT);
		
		log.debug("ListBoxEvent.LISTBOX_SELECT=" + ListBoxEvent.LISTBOX_SELECT);
		
		listBoxEvent.setObject(listBoxM);
		
		log.debug("listBoxM = " + listBoxM);
		log.debug("listBoxM.getListBoxID() = " + listBoxM.getListBoxID());
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
	public boolean processEventResponse(EventResponse arg0) {
		
		return defaultProcessResponse(arg0);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		// show edit form
		log.debug("///ShowListBoxMasterWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equalsIgnoreCase(shwAddFrm)){
			
			getRequest().getSession().removeAttribute("BUS_ID"); //clear session BUS_ID
			
			String listID = getRequest().getParameter("listID");						
			listBoxM.setListBoxID(listID);
			
			return true;
		}
		// just show add form
		if(!"cancelEdit".equalsIgnoreCase(shwAddFrm)){
			getRequest().getSession().removeAttribute("First_Search_ListBoxM");
			getRequest().getSession().removeAttribute("ADD_LIST_BOX_DATAM");
			getRequest().getSession().removeAttribute("BUS_ID");
			getRequest().getSession().removeAttribute("SEARCH_BUS");
			getRequest().getSession().removeAttribute("SELECTED_BUS");
		}
		
		// Do when press add button
		if("add".equalsIgnoreCase(shwAddFrm)){
//			OrigMasterListBoxDAO origMasterListBoxDAO = (OrigMasterListBoxDAO)OrigMasterDAOFactory.getOrigMasterListBoxDAO();
			Vector busClassSearchVect = new Vector();
			
			try {
//				busClassSearchVect = origMasterListBoxDAO.SearchAllBusinessClass();
				busClassSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchAllBusinessClass();
				log.debug("busClassSearchVect = "+busClassSearchVect);
				if(busClassSearchVect!=null){
					log.debug("busClassSearchVect.size() = "+busClassSearchVect.size());
				}
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			getRequest().getSession().setAttribute("SEARCH_BUS",busClassSearchVect);
		}
		return false;
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
		log.debug("//from Action//ListBoxMasterM = " + (ListBoxMasterM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_LIST_BOX_DATAM", (ListBoxMasterM)arg0.getEncapData());
		listBoxM = (ListBoxMasterM)arg0.getEncapData();
		getRequest().getSession().setAttribute("SELECTED_BUS", listBoxM.getListBoxBusinessClass());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
