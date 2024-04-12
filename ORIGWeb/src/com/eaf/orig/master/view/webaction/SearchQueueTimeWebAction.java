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
 * Type: SearchQueueTimeWebAction
 */
public class SearchQueueTimeWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchQueueTimeWebAction.class);
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

		String qTimeOutID;
		String qTimeOutDesc;
		
				
		qTimeOutID = getRequest().getParameter("qTimeOutID");
		qTimeOutDesc = getRequest().getParameter("qTimeOutDesc");
			
			logger.debug("qTimeOutID from parameter = "+qTimeOutID);
			logger.debug("qTimeOutDesc from parameter = "+qTimeOutDesc);
			
			if( (qTimeOutID==null || "".equals(qTimeOutID)) && (qTimeOutDesc==null || "".equals(qTimeOutDesc)) ){
				qTimeOutID =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_qTimeOutID");
				logger.debug("////qTimeOutID from FIRST_SEARCH_qTimeOutID Session = "+qTimeOutID);
				qTimeOutDesc =(String)getRequest().getSession().getAttribute("FIRST_SEARCH_qTimeOutDesc");
				logger.debug("////qTimeOutDesc from FIRST_SEARCH_qTimeOutDesc Session = "+qTimeOutDesc);
				
			}else{
				getRequest().getSession().setAttribute("FIRST_SEARCH_qTimeOutID",qTimeOutID);
				logger.debug("////Create qTimeOutID to FIRST_SEARCH_qTimeOutID Session = "+qTimeOutID);
				getRequest().getSession().setAttribute("FIRST_SEARCH_qTimeOutDesc",qTimeOutDesc);
				logger.debug("////Create qTimeOutDesc to FIRST_SEARCH_qTimeOutDesc Session = "+qTimeOutDesc);
			}
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT Q_TIME_OUT_ID, Q_TIME_OUT_DESC ");
			sql.append("FROM QUEUE_TIME_OUT ");
			
			if(!ORIGUtility.isEmptyString(qTimeOutID)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(Q_TIME_OUT_ID) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(Q_TIME_OUT_ID) like UPPER(?) ");
				}
				valueListM.setString(++index,qTimeOutID);
			}			
			if(!ORIGUtility.isEmptyString(qTimeOutDesc)){
				if(!hvValueForWhere){
					sql.append(" WHERE UPPER(Q_TIME_OUT_DESC) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(Q_TIME_OUT_DESC) like UPPER(?) ");
				}
				valueListM.setString(++index,qTimeOutDesc);
			}
			sql.append(" ORDER BY Q_TIME_OUT_ID");			
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_QUEUE_TIMEOUT_SEARCHING_SCREEN");
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
