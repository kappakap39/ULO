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
 * Type: SearchUserTeamWebAction
 */
public class SearchUserTeamWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchUserTeamWebAction.class);
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
		//*** Remove SESSION
		getRequest().getSession().removeAttribute("USERNAME_SESSION");
		getRequest().getSession().removeAttribute("SEL_MEMBER_SESSION");
		 getRequest().getSession().removeAttribute("selMemStrArry_SESSION");
		 getRequest().getSession().removeAttribute("member_leader_SESSION");
		 getRequest().getSession().removeAttribute("ADD_USER_TEAM_DATAM");
		 getRequest().getSession().removeAttribute("EDIT_USER_TEAM_DATAM");
		// *** END Remove
		
		boolean hvValueForWhere=false;

		String teamName;
		String teamDesc;
				
		teamName = getRequest().getParameter("teamName");
		teamDesc = getRequest().getParameter("teamDesc");
			
			logger.debug("teamName from parameter = "+teamName);
			logger.debug("teamDesc from parameter = "+teamDesc);
			
			if( (teamName==null || "".equals(teamName)) && (teamDesc==null || "".equals(teamDesc)) ){
				teamName =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_teamName");
				logger.debug("////teamName from FIRST_SEARCH_teamName Session = "+teamName);
				teamDesc =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_teamDesc");
				logger.debug("////teamDesc from FIRST_SEARCH_teamDesc Session = "+teamDesc);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_teamName",teamName);
				logger.debug("////Create teamName to FIRST_SEARCH_teamName Session = "+teamName);
				getRequest().getSession().setAttribute("FIRST_SEARCH_teamDesc",teamDesc);
				logger.debug("////Create teamDesc to FIRST_SEARCH_teamDesc Session = "+teamDesc);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT TEAM_NAME, TEAM_DESC, TEAM_ID ");
			sql.append("FROM TEAM ");
			
			if(!ORIGUtility.isEmptyString(teamName)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(TEAM_NAME) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(TEAM_NAME) like UPPER(?) ");
				}
				valueListM.setString(++index,teamName);
			}			
			if(!ORIGUtility.isEmptyString(teamDesc)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(TEAM_DESC) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(TEAM_DESC) like UPPER(?) ");
				}
				valueListM.setString(++index,teamDesc);
			}
			sql.append(" ORDER BY TEAM_NAME");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_USER_TEAM_SEARCHING_SCREEN");
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
