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
 * Type: SearchFieldIdWebAction
 */
public class SearchFieldIdWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchFieldIdWebAction.class);
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
		ORIGUtility utility = new ORIGUtility();

		String fieldID;
		String fieldDesc;
		boolean hvValueForWhere=false;
		
				
		fieldID = getRequest().getParameter("fieldID");
		fieldDesc = getRequest().getParameter("fieldDesc");
			
			logger.debug("fieldID from parameter = "+fieldID);
			logger.debug("fieldDesc from parameter = "+fieldDesc);
			
			if( (fieldID==null || "".equals(fieldID)) && (fieldDesc==null || "".equals(fieldDesc)) ){
				fieldID =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_FIELD_ID");
				logger.debug("////fieldID from FIRST_SEARCH_FIELD_ID Session = "+fieldID);
				fieldDesc =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_FIELD_DESC");
				logger.debug("////fieldDesc from FIRST_SEARCH_FIELD_DESC Session = "+fieldDesc);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_FIELD_ID",fieldID);
				logger.debug("////Create fieldID to FIRST_SEARCH_FIELD_ID Session = "+fieldID);
				getRequest().getSession().setAttribute("FIRST_SEARCH_FIELD_DESC",fieldDesc);
				logger.debug("////Create fieldDesc to FIRST_SEARCH_FIELD_DESC Session = "+fieldDesc);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT FIELD_ID, FIELD_DESC ");
			sql.append("FROM FIELD_ID ");
			
			if(!ORIGUtility.isEmptyString(fieldID)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(FIELD_ID) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(FIELD_ID) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+utility.stringToInt((fieldID))+"%");
			}			
			if(!ORIGUtility.isEmptyString(fieldDesc)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(FIELD_DESC) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(FIELD_DESC) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+fieldDesc+"%");
			}
			sql.append(" ORDER BY FIELD_ID");	
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_FIELD_ID_SEARCHING_SCREEN");
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
