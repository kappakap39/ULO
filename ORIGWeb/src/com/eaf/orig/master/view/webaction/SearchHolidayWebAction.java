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
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SearchHolidayWebAction
 */
public class SearchHolidayWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchHolidayWebAction.class);
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
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
		String searchDate;
		String searchDesc;
		boolean hvValueForWhere=false;
		
				
		searchDate = getRequest().getParameter("searchDate");
		searchDesc = getRequest().getParameter("searchDesc");
			
			logger.debug("searchDate from parameter = "+searchDate);
			logger.debug("searchDesc from parameter = "+searchDesc);
			
			if( (searchDate==null || "".equals(searchDate)) && (searchDesc==null || "".equals(searchDesc)) ){
				searchDate =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_searchDate");
				logger.debug("////searchDate from FIRST_SEARCH_searchDate Session = "+searchDate);
				searchDesc =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_searchDesc");
				logger.debug("////searchDesc from FIRST_SEARCH_searchDesc Session = "+searchDesc);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_searchDate",searchDate);
				logger.debug("////Create searchDate to FIRST_SEARCH_searchDate Session = "+searchDate);
				getRequest().getSession().setAttribute("FIRST_SEARCH_searchDesc",searchDesc);
				logger.debug("////Create searchDesc to FIRST_SEARCH_searchDesc Session = "+searchDesc);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT HOLIDAY_DATE, HOLIDAY_DESC, WORKING_FLAG ");
			sql.append("FROM HOLIDAY_MASTER ");
			
			if(!ORIGUtility.isEmptyString(searchDate)){
				if(!hvValueForWhere){
					sql.append(" WHERE HOLIDAY_DATE = ? ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND HOLIDAY_DATE = ? ");
				}
				valueListM.setDate(++index,ORIGUtility.parseThaiToEngDate(searchDate));
			}			
			if(!ORIGUtility.isEmptyString(searchDesc)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(HOLIDAY_DESC) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(HOLIDAY_DESC) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+searchDesc+"%");
			}
			sql.append(" ORDER BY HOLIDAY_DATE");		
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_HOLIDAY_MASTER_SEARCHING_SCREEN");
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
