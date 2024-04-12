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
 * Type: SearchMandFieldWebAction
 */
public class SearchMandFieldWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchMandFieldWebAction.class);
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

		String formNameId;
		String fieldName;
		
				
		formNameId = getRequest().getParameter("formNameId");
		fieldName = getRequest().getParameter("fieldName");
			
			logger.debug("formNameId from parameter = "+formNameId);	
			logger.debug("fieldName from parameter = "+fieldName);	
			
			if( (formNameId==null || "".equals(formNameId)) && (fieldName==null || "".equals(fieldName)) ){
				formNameId =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_formNameId");
				logger.debug("////formNameId from FIRST_SEARCH_formNameId Session = "+formNameId);
				fieldName =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_fieldName");
				logger.debug("////fieldName from FIRST_SEARCH_fieldName Session = "+fieldName);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_formNameId",formNameId);
				logger.debug("////Create formNameId to FIRST_SEARCH_formNameId Session = "+formNameId);
				getRequest().getSession().setAttribute("FIRST_SEARCH_fieldName",fieldName);
				logger.debug("////Create fieldName to FIRST_SEARCH_fieldName Session = "+fieldName);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT FORM_NAME_ID, FIELD_NAME, CMR_MANDATORY_FLAG, DE_MANDATORY_FLAG, UW_MANDATORY_FLAG, PD_MANDATORY_FLAG, XCMR_MANDATORY_FLAG, CUSTOMER_TYPE ");
			sql.append("FROM SC_MANDATORY_FIELDS ");
			
			if(!ORIGUtility.isEmptyString(formNameId)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(FORM_NAME_ID) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(FORM_NAME_ID) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+formNameId+"%");
			}			
			if(!ORIGUtility.isEmptyString(fieldName)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(FIELD_NAME) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(FIELD_NAME) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+fieldName+"%");
			}
			sql.append(" ORDER BY FORM_NAME_ID, FIELD_NAME");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_MANDATORY_FIELD_SEARCHING_SCREEN");
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
