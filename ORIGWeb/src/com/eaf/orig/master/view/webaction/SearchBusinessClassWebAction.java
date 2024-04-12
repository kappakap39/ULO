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
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterListBoxDAO;
import com.eaf.orig.shared.model.BusinessClassM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: SearchBusinessClassWebAction
 */
public class SearchBusinessClassWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(SearchBusinessClassWebAction.class);

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
		//*** END ***
		
		String searchBusID = getRequest().getParameter("searchBusID");
		if(searchBusID==null){
			searchBusID = (String) getRequest().getSession().getAttribute("BUS_ID");
		}
//		OrigMasterListBoxDAO origMasterListBoxDAO = (OrigMasterListBoxDAO)OrigMasterDAOFactory.getOrigMasterListBoxDAO();
		Vector busClassSearchVect = new Vector();
		
		Vector selectedBusVect = (Vector) getRequest().getSession().getAttribute("SELECTED_BUS");
		log.debug("selectedBusVect = "+selectedBusVect);
		
		if(searchBusID!=null && !"".equals(searchBusID)){
			getRequest().getSession().setAttribute("BUS_ID",searchBusID);
			try {
//				busClassSearchVect = origMasterListBoxDAO.SearchBusinessClassByDesc(searchBusID);
				
				busClassSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchBusinessClassByDesc(searchBusID);
				
				log.debug("busClassSearchVect = "+busClassSearchVect);
				log.debug("busClassSearchVect.size() = "+busClassSearchVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			// *** remove selbus from searchbus ****
			if((selectedBusVect!=null) && (busClassSearchVect!=null)){
				log.debug("now im checking selectedBusVect && busClassSearchVect at if1");
				if((selectedBusVect.size()>0) && (busClassSearchVect.size()>0)){
					log.debug("now im checking selectedBusVect && busClassSearchVect at if2");
					BusinessClassM selBusM = null;
					for(int i = 0; i < selectedBusVect.size(); i++){
						selBusM = (BusinessClassM) selectedBusVect.get(i);
						for(int j = 0; j < busClassSearchVect.size(); j++){
							BusinessClassM busM = (BusinessClassM) busClassSearchVect.get(j);
							if( (busM.getId()).equalsIgnoreCase(selBusM.getId())){
								busClassSearchVect.removeElementAt(j);
							}
						}
					}
				}
			}
		}
		getRequest().getSession().setAttribute("SEARCH_BUS",busClassSearchVect);
		log.debug("now im returning true");
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
