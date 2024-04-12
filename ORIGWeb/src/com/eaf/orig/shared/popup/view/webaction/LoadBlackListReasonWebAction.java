/*
 * Created on Jan, 2008
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
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

/**
 * @author weeraya
 *
 * Type: LoadBlackListReasonWebAction
 */
public class LoadBlackListReasonWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadBlackListReasonWebAction.class);
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
    	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ORIGUtility utility = new ORIGUtility();
        String blckReason = getRequest().getParameter("blckReason");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
        
        logger.debug("blckReason = "+blckReason);
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT L.CHOICE_NO, L.DISPLAY_NAME ");
			sql.append("FROM LIST_BOX_MASTER L, LIST_BOX_BUS_CLASS LB ");
			sql.append("WHERE L.LIST_BOX_ID = LB.LIST_BOX_ID ");
			sql.append("AND L.FIELD_ID = 22");
            
			if(!ORIGUtility.isEmptyString(blckReason)){
			    sql.append("AND L.CHOICE_NO = ? ");
			    valueListM.setString(++index,blckReason);
			    sql.append("ORDER BY L.CHOICE_NO ");
			}else if(!ORIGUtility.isEmptyString(code)){
			    sql.append("AND UPPER(L.DISPLAY_NAME) like ? ");
			    valueListM.setString(++index,"%"+code.toUpperCase()+"%");
			    sql.append("ORDER BY L.DISPLAY_NAME ");
			}
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadBlackListReason");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	//getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, area_code);
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
