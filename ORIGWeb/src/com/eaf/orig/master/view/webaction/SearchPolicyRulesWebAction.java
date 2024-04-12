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
public class SearchPolicyRulesWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchPolicyRulesWebAction.class);
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

		String policyCode;
		String policyDesc;
		
				
		policyCode = getRequest().getParameter("policyCode");
		//policyType = getRequest().getParameter("policyType");
		policyDesc = getRequest().getParameter("policyDesc");	
		logger.debug("policyCode from parameter = "+policyCode);	
		logger.debug("policyDesc from parameter = "+policyDesc);
			
			if( (policyCode==null || "".equals(policyCode)) && (policyDesc==null || "".equals(policyDesc)) ){
				policyCode =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_policyCode");
				logger.debug("////policyCode from FIRST_SEARCH_FIELD Session = "+policyCode);
				policyDesc =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_policyDesc");
				logger.debug("////policyType from FIRST_SEARCH_FIELD Session = "+policyDesc);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_paramID",policyCode);
				logger.debug("////Create policyCode to FIRST_SEARCH_paramID Session = "+policyCode);
				getRequest().getSession().setAttribute("FIRST_SEARCH_paramDesc",policyDesc);
				logger.debug("////Create policyType to FIRST_SEARCH_paramDesc Session = "+policyDesc);
			}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT POLICY_CODE, POLICY_TYPE, DESCRIPTION ");
			sql.append("FROM POLICY_RULES ");
			
			if(!ORIGUtility.isEmptyString(policyCode)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(POLICY_CODE) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(POLICY_CODE) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+policyCode+"%");
			}			
			if(!ORIGUtility.isEmptyString(policyDesc)){
				if(!hvValueForWhere){
					sql.append("WHERE UPPER(DESCRIPTION) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(DESCRIPTION) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+policyDesc+"%");
			}
			sql.append(" ORDER BY POLICY_CODE");		
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_POLICY_RULES_SEARCHING_SCREEN");
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
