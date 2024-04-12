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
 * Type: SearchRunningParamWebAction
 */
public class SearchRunningParamWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchRunningParamWebAction.class);
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
		boolean hvValueForWhere=false;

		String paramID;
		String paramDesc;
		
				
		paramID = getRequest().getParameter("paramID");
		paramDesc = getRequest().getParameter("paramDesc");
			
		logger.debug("paramID from parameter = "+paramID);	
		logger.debug("paramDesc from parameter = "+paramDesc);
			
			if( (paramID==null || "".equals(paramID)) && (paramDesc==null || "".equals(paramDesc)) ){
				paramID =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_paramID");
				logger.debug("////paramID from FIRST_SEARCH_FIELD Session = "+paramID);
				paramDesc =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_paramDesc");
				logger.debug("////paramID from FIRST_SEARCH_FIELD Session = "+paramDesc);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_paramID",paramID);
				logger.debug("////Create paramID to FIRST_SEARCH_paramID Session = "+paramID);
				getRequest().getSession().setAttribute("FIRST_SEARCH_paramDesc",paramDesc);
				logger.debug("////Create paramDesc to FIRST_SEARCH_paramDesc Session = "+paramDesc);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT PARAM_ID, PARAM_TYPE, PARAM_DESC ");
			sql.append("FROM RUNNING_PARAM ");
			
			if(!ORIGUtility.isEmptyString(paramID)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(PARAM_ID) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(PARAM_ID) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+paramID+"%");
			}			
			if(!ORIGUtility.isEmptyString(paramDesc)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(PARAM_DESC) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(PARAM_DESC) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+paramDesc+"%");
			}
			sql.append(" ORDER BY PARAM_ID");		
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_RUNNING_PARAMETER_SEARCHING_SCREEN");
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
