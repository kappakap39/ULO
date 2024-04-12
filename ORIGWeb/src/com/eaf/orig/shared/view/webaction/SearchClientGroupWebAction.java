package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SearchClientGroupWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(SearchClientGroupWebAction.class);
    private String nextAction = null;
	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		return null;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
	    log.debug("++++++++++++++ Start SearchClientGroupWebAction  ++++++++++++++++++++");
		getRequest().getSession().removeAttribute("ORIGForm");
		ORIGFormHandler ORIGForm = new ORIGFormHandler();
		ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		if(ORIGForm == null){
				ORIGForm = new ORIGFormHandler();
		}
		getRequest().getSession().setAttribute("ORIGForm",ORIGForm);	
	 	
	 	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	 	Vector userRoles = userM.getRoles();
		String clientGroup = getRequest().getParameter("client_group");
		
		try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT HPTBHP07.CLTGRP, HPTBHP07.THDESC FROM  HPTBHP07 ");   
		    
                       
            if(!ORIGUtility.isEmptyString(clientGroup)){
			    sql.append("WHERE HPTBHP07.CLTGRP = ? ");
			    valueListM.setString(++index,clientGroup);
			}
			
			
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=SEARCH_CLIENT_GROUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            getRequest().getSession().setAttribute("CLIENT_GROUP", clientGroup);

       } catch (Exception e) {
           log.error("exception ",e);
       }		
	
		log.debug("++++++++++++++ End SearchClientGroupWebAction  ++++++++++++++++++++");		
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return nextAction;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
