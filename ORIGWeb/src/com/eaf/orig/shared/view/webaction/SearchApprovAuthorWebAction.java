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
package com.eaf.orig.shared.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SearchApprovAuthorWebAction
 */
public class SearchApprovAuthorWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchApprovAuthorWebAction.class);
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
//		UserDetailM userDetailM = (UserDetailM)getRequest().getSession().getAttribute("USER_DETAIL_DATAM");
		ApprovAuthorM approvAuthorM = new ApprovAuthorM();
		boolean hvValueForWhere=false;
		String versionSearch;
		//String loanType;
		//String customerType;
				
		versionSearch = getRequest().getParameter("versionSearch");
		String queryApp=getRequest().getParameter("queryApp");
		//loanType = getRequest().getParameter("loanType");
		//customerType = getRequest().getParameter("customerType");
			
			logger.debug("versionSearch from parameter = "+versionSearch);	
			//logger.debug("loanType from parameter = "+loanType);
			//logger.debug("customerType from parameter = "+customerType);
			
			if( (versionSearch==null || "".equals(versionSearch) || !OrigConstant.ORIG_FLAG_Y.equals(queryApp))   ){
			    versionSearch =(String)getRequest().getSession().getAttribute("VERSION_SEARCH");
				logger.debug("////versionSearch from VERSION_SEARCH Session = "+versionSearch);				 				
			}else{
				getRequest().getSession().setAttribute("VERSION_SEARCH",versionSearch);
				logger.debug("////Create versionSearch to VERSION_SEARCH Session = "+versionSearch);				 
			}
            if(versionSearch==null){
            versionSearch="";    
            }
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT POLICY_VERSION,TRIM(TO_CHAR (effective_date, 'DD/MM/'))||TRIM(TO_CHAR (TO_NUMBER(TO_CHAR (effective_date,'YYYY'),'9999')+ 543, '9999')) efectiveDate ");
            sql.append(" ,TRIM(TO_CHAR (expire_date, 'DD/MM/'))||TRIM(TO_CHAR (TO_NUMBER(TO_CHAR (expire_date,'YYYY'),'9999')+ 543, '9999')) ExpireDate,DESCRIPTION  ");
			sql.append("FROM ORIG_POLICY_VERSION ");			 
		    sql.append(" WHERE ");			 						 				 
					sql.append(" UPPER(POLICY_VERSION) like (?) ");					 				 
				valueListM.setString(++index,"%"+versionSearch.toUpperCase()+"%");			 						 
			sql.append(" ORDER BY EFFECTIVE_DATE");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_APPROV_AUTHOR_SEARCHING_SCREEN");
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
