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
 * Type: SearchCarBlacklistWebAction
 */
public class SearchCarBlacklistWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchCarBlacklistWebAction.class);
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
		
		String chassisNum;
		String regisNum;
		
				
		chassisNum = getRequest().getParameter("chassisNum");
		regisNum = getRequest().getParameter("registrationNum");
			
			logger.debug("chassisNum from parameter = "+chassisNum);
			logger.debug("regisNum from parameter = "+regisNum);
			
			if( (chassisNum==null || "".equals(chassisNum)) && (regisNum==null || "".equals(regisNum)) ){
				chassisNum =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_chassisNum");
				logger.debug("////chassisNum from FIRST_SEARCH_chassisNum Session = "+chassisNum);
				regisNum =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_regisNum");
				logger.debug("////regisNum from FIRST_SEARCH_regisNum Session = "+regisNum);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_chassisNum",chassisNum);
				logger.debug("////Create chassisNum to FIRST_SEARCH_chassisNum Session = "+chassisNum);
				getRequest().getSession().setAttribute("FIRST_SEARCH_regisNum",regisNum);
				logger.debug("////Create regisNum to FIRST_SEARCH_regisNum Session = "+regisNum);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT CHASSIS_NUMBER, REGISTRATION_NUMBER, BL_VEHICAL_ID ");
			sql.append("FROM BLACKLIST_VEHICAL ");
			
			if(!ORIGUtility.isEmptyString(chassisNum)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(CHASSIS_NUMBER) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(CHASSIS_NUMBER) like UPPER(?) ");
				}
				valueListM.setString(++index,chassisNum);
			}			
			if(!ORIGUtility.isEmptyString(regisNum)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(REGISTRATION_NUMBER) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(REGISTRATION_NUMBER) like UPPER(?) ");
				}
				valueListM.setString(++index,regisNum);
			}
			sql.append(" ORDER BY CHASSIS_NUMBER");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_CAR_BLACKLIST_SEARCHING_SCREEN");
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
