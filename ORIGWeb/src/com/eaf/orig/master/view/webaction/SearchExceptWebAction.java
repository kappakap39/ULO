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
 * Type: SearchExceptWebAction
 */
public class SearchExceptWebAction extends WebActionHelper implements WebAction {

	static Logger logger = Logger.getLogger(SearchExceptWebAction.class);
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

		String exceptionName;
		String exceptionDesc;
				
		exceptionName = getRequest().getParameter("name");
		exceptionDesc = getRequest().getParameter("desc");
			
			logger.debug("exceptionName from parameter = "+exceptionName);	
			logger.debug("exceptionDesc from parameter = "+exceptionDesc);	
			
			if( (exceptionName==null || "".equals(exceptionName)) && (exceptionDesc==null || "".equals(exceptionDesc)) ){
				exceptionName =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_exceptionName");
				logger.debug("////exceptionName from FIRST_SEARCH_exceptionName Session = "+exceptionName);
				exceptionDesc =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_exceptionDesc");
				logger.debug("////exceptionDesc from FIRST_SEARCH_exceptionDesc Session = "+exceptionDesc);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_exceptionName",exceptionName);
				logger.debug("////Create exceptionName to FIRST_SEARCH_exceptionName Session = "+exceptionName);
				getRequest().getSession().setAttribute("FIRST_SEARCH_exceptionDesc",exceptionDesc);
				logger.debug("////Create exceptionDesc to FIRST_SEARCH_exceptionDesc Session = "+exceptionDesc);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT EXCEPTION_NAME, EXCEPTION_DESC, EXCEPTION_ID ");
			sql.append("FROM EXCEPTION ");
			
			if(!ORIGUtility.isEmptyString(exceptionName)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(EXCEPTION_NAME) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(EXCEPTION_NAME) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+exceptionName+"%");
			}			
			if(!ORIGUtility.isEmptyString(exceptionDesc)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(EXCEPTION_DESC) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(EXCEPTION_DESC) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+exceptionDesc+"%");
			}
			sql.append(" ORDER BY EXCEPTION_NAME");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_EXCEPT_ACT_SEARCHING_SCREEN");
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
