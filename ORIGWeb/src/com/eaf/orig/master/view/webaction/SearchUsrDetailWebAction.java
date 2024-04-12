/*
 * Created on Nov 15, 2007
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

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;


/**
 * @author Administrator
 *
 * Type: SearchUsrDetailWebAction
 */
public class SearchUsrDetailWebAction extends WebActionHelper implements
		WebAction {
	
	static Logger logger = Logger.getLogger(SearchUsrDetailWebAction.class);
	private String nextAction = null;

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
		// *** remove SESSION
		getRequest().getSession().removeAttribute("SELECTED_BRANCH");
		getRequest().getSession().removeAttribute("SELECTED_USER_NAME");
		getRequest().getSession().removeAttribute("SELECTED_EXCEPT_ACT");
		getRequest().getSession().removeAttribute("SEARCH_BRANCH");
		getRequest().getSession().removeAttribute("SEARCH_USER_NAME");
		getRequest().getSession().removeAttribute("SEARCH_EXCEPT");
		getRequest().getSession().removeAttribute("BRANCH_DESC_SEARCH");
		getRequest().getSession().removeAttribute("USER_NAME_DESC_SEARCH");
		getRequest().getSession().removeAttribute("EXCEPT_ACT_DESC_SEARCH");
		getRequest().getSession().removeAttribute("AddOrEdit_SESSION");
		getRequest().getSession().removeAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE");
		getRequest().getSession().removeAttribute("EDIT_USER_DETAIL_DATAM");
		getRequest().getSession().removeAttribute("ADD_USER_DETAIL_DATAM");
		
		// *** END REMOVE
		
		String userName;
				
			userName = getRequest().getParameter("user_name");
			logger.debug("////user_name from parameter = "+userName);
			
			if(userName==null || "".equals(userName)){
				userName =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_USER_NAME");
				logger.debug("////user_name from FIRST_SEARCH_USER_NAME Session = "+userName);
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_USER_NAME",userName);
				logger.debug("////Create user_name to FIRST_SEARCH_USER_NAME Session = "+userName);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT USER_NAME, FIRSTNAME, LASTNAME, STATUS ");
			sql.append("FROM US_USER_DETAIL ");
			if(!ORIGUtility.isEmptyString(userName)){
				sql.append("WHERE UPPER(USER_NAME) like UPPER(?) ");
				valueListM.setString(++index,"%"+userName+"%");
			}
			sql.append(" ORDER BY UPPER(USER_NAME) ");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_USER_DETAIL_SEARCHING_SCREEN");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
       } catch (Exception e) {
           logger.error("exception ",e);
       }
		return true;
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
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
