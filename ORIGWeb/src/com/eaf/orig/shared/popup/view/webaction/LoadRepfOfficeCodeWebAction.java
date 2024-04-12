/*
 * Created on Oct 3, 2007
 * Created by weeraya
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
package com.eaf.orig.shared.popup.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

/**
 * @author edit by Nuttawoot
 *
 * Type: LoadReportOfficeCodeWebAction
 */
public class LoadRepfOfficeCodeWebAction extends WebActionHelper implements WebAction {
    private static Logger logger = Logger.getLogger(LoadRepfOfficeCodeWebAction.class);
	private String nextAction = "";
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
        StringBuffer sql = new StringBuffer();
        String foffice_code = getRequest().getParameter("foffice_code");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
        boolean isFrist = true;
        
        logger.debug("foffice_code = "+foffice_code);
        logger.debug("code = "+code);
        
        try {
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT OFFCDE,THNAM ");
			sql.append("FROM HPTBHP00 ");
            
			if(!ORIGUtility.isEmptyString(foffice_code)){
			    sql.append("WHERE UPPER(OFFCDE) = UPPER(?) ");
			    valueListM.setString(++index,foffice_code);
			    isFrist = false;
			    sql.append("ORDER BY OFFCDE ");
			}else if(!ORIGUtility.isEmptyString(code)){
			    sql.append("WHERE UPPER(THNAM) like ? ");
			    valueListM.setString(++index,"%"+code.toUpperCase()+"%");
			    isFrist = false;
			    sql.append("ORDER BY THNAM ");
			}
			
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadRepfOfficeCode");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	//getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, foffice_code);
            }

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
