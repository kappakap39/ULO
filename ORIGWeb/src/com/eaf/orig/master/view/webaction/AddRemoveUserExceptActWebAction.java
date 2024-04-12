/*
 * Created on Dec 3, 2007
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
import com.eaf.orig.master.shared.model.UserExceptActM;

/**
 * @author Administrator
 *
 * Type: AddRemoveUserExceptActWebAction
 */
public class AddRemoveUserExceptActWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(AddRemoveUserExceptActWebAction.class);

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
		
		//*** END
		
		String[]  exceptAct = getRequest().getParameterValues("exceptAct");
		String[]  selectedExceptAct = getRequest().getParameterValues("selectedExceptAct");
		String[]  exceptActID = null;
		String[]  exceptActDesc = null;
		String[]  selExceptActID = null;
		String[]  selExceptActDesc = null;
		String[]  temp = null;
		String[]  temp2 = null;
		
		if(exceptAct!=null && exceptAct.length>0){
			log.debug("------------------> all exceptAct length = " + exceptAct.length);
			exceptActID = new String[exceptAct.length];
		    exceptActDesc = new String[exceptAct.length];
		    temp = new String[exceptAct.length];
		    
				for(int i = 0; i < exceptAct.length; i++){
					temp = exceptAct[i].split(",");
					exceptActID[i] = temp[0];
					exceptActDesc[i] =	temp[1];		
				}
		}
		if(selectedExceptAct!=null && selectedExceptAct.length>0){
			selExceptActID = new String[selectedExceptAct.length];
		    selExceptActDesc = new String[selectedExceptAct.length];
		    temp2 = new String[selectedExceptAct.length];
		    
				for(int i = 0; i < selectedExceptAct.length; i++){
					temp2 = selectedExceptAct[i].split(",");
					selExceptActID[i] = temp2[0];
					selExceptActDesc[i] =	temp2[1];		
				}
	    }
		
		Vector selExceptActVect = (Vector) getRequest().getSession().getAttribute("SELECTED_EXCEPT_ACT");
		if(selExceptActVect==null){
			selExceptActVect = new Vector();
		}
		Vector ExceptActVect = (Vector) getRequest().getSession().getAttribute("SEARCH_EXCEPT");
		if(ExceptActVect==null){
			ExceptActVect = new Vector();
		}
		String addOrRemove = getRequest().getParameter("addOrRemove");
		log.debug("------------------> addOrRemove = " + addOrRemove);
		if((addOrRemove != null) && "add".equalsIgnoreCase(addOrRemove)){
			log.debug("///im adding selExceptActVect");		
				if (exceptAct !=null && exceptAct.length>0) {
						log.debug("------------------> all exceptAct length = " + exceptAct.length);
				
					for (int i=0; i<exceptAct.length;i++) {
						String exceptId= exceptActID[i];
						String exceptDesc= exceptActDesc[i];
						UserExceptActM exceptActM = new UserExceptActM();		
						exceptActM.setExceptID(exceptId);
						exceptActM.setExceptDesc(exceptDesc);
						selExceptActVect.add(exceptActM);	
						
						/* remove from busVect */
						if(ExceptActVect.size()>0){
							UserExceptActM exceptSearchM = new UserExceptActM();
							for (int j=0; j<ExceptActVect.size();j++){
								exceptSearchM = (UserExceptActM)ExceptActVect.get(j);
								if(exceptId.equals(exceptSearchM.getExceptID())){
									ExceptActVect.removeElementAt(j);
								}
							}
						}
					}
				}	
		}else if((addOrRemove != null) && "remove".equalsIgnoreCase(addOrRemove)){
			log.debug("///im removing selExceptActVect");		
			if (selectedExceptAct !=null && selectedExceptAct.length>0) {
					log.debug("------------------> all selectedExceptAct length = " + selectedExceptAct.length);

				for (int i=0; i<selectedExceptAct.length;i++) {
					String selExceptId= selExceptActID[i];
					String selExceptDesc= selExceptActDesc[i];
					UserExceptActM exceptActM = new UserExceptActM();		
					exceptActM.setExceptID(selExceptId);
					exceptActM.setExceptDesc(selExceptDesc);
					ExceptActVect.add(exceptActM);	
					
					/* remove from selBusVect */
					if(selExceptActVect.size()>0){
						UserExceptActM exceptActSelM =new UserExceptActM();
						for (int j=0; j<selExceptActVect.size();j++){
							exceptActSelM = (UserExceptActM)selExceptActVect.get(j);
							if(selExceptId.equals(exceptActSelM.getExceptID())){
								selExceptActVect.removeElementAt(j);
							}
						}
					}
				}			
			}
		
		}	
		
		getRequest().getSession().setAttribute("SELECTED_EXCEPT_ACT",selExceptActVect);
		getRequest().getSession().setAttribute("SEARCH_EXCEPT",ExceptActVect);
		
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
