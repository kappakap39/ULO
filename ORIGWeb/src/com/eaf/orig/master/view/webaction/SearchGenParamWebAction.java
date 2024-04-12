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
 * Type: SearchGenParamWebAction
 */
public class SearchGenParamWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchGenParamWebAction.class);
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
//		UserDetailM userDetailM = (UserDetailM)getRequest().getSession().getAttribute("USER_DETAIL_DATAM");

		String paramCode;
		String paramValue;
		
		paramCode = getRequest().getParameter("paramCode");
		paramValue = getRequest().getParameter("paramValue");
			
		logger.debug("paramCode from parameter = "+paramCode);
		logger.debug("paramValue from parameter = "+paramValue);	
			
		if( (paramCode==null || "".equals(paramCode)) && (paramValue==null || "".equals(paramValue)) ){
			paramCode =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_paramCode");
			logger.debug("////paramCode from FIRST_SEARCH_FIELD Session = "+paramCode);
			paramValue =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_paramValue");
			logger.debug("////paramValue from FIRST_SEARCH_FIELD Session = "+paramValue);
			
		}else{
			getRequest().getSession().setAttribute("FIRST_SEARCH_paramCode",paramCode);
			logger.debug("////Create paramCode to FIRST_SEARCH_paramCode Session = "+paramCode);
			getRequest().getSession().setAttribute("FIRST_SEARCH_paramValue",paramValue);
			logger.debug("////Create paramValue to FIRST_SEARCH_paramValue Session = "+paramValue);
		}
            
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT G.PARAM_CODE, G.PARAM_VALUE, B.BUS_CLASS_DESC, G.BUS_CLASS_ID ");
			sql.append("FROM GENERAL_PARAM G, BUSINESS_CLASS B ");
			
			sql.append("WHERE G.BUS_CLASS_ID = B.BUS_CLASS_ID ");
			if(!ORIGUtility.isEmptyString(paramCode)){
				sql.append("  AND UPPER(PARAM_CODE) like UPPER(?) ");
				valueListM.setString(++index,"%"+paramCode+"%");
			}
			if(!ORIGUtility.isEmptyString(paramValue)){
				sql.append("  AND UPPER(PARAM_VALUE) like UPPER(?) ");
				valueListM.setString(++index,"%"+paramValue+"%");
			}
			sql.append(" ORDER BY PARAM_CODE");	
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_GENERAL_PARAMETER_SEARCHING_SCREEN");
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
