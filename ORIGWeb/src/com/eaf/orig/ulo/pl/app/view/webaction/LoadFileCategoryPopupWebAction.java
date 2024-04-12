/**
 * Create Date Mar 14, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.view.webaction;

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
 * @author Sankom
 *
 */
public class LoadFileCategoryPopupWebAction extends WebActionHelper implements WebAction {
	private final Logger logger = Logger.getLogger(LoadFileCategoryPopupWebAction.class);
	private String nextAction = null;

	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {

        String fileCategory = getRequest().getParameter("file_category_code");
        String fileCategoryDesc = getRequest().getParameter("file_category_desc");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");

        logger.debug("File Category >>> " + fileCategory);
        logger.debug("Code >>> " + code);
        logger.debug("TxtBox Code >>> " + textboxCode);
        logger.debug("TxtBox Desc >>> " + textboxDesc);
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT L.CHOICE_NO, L.DISPLAY_NAME ");
			sql.append("FROM LIST_BOX_MASTER L, LIST_BOX_BUS_CLASS LB ");
			sql.append("WHERE L.LIST_BOX_ID = LB.LIST_BOX_ID ");
			sql.append("AND L.FIELD_ID =40   ");
            
			if(!ORIGUtility.isEmptyString(fileCategory)) {
			    sql.append("AND L.CHOICE_NO = ? ");
			    valueListM.setString(++index,fileCategory);
			    sql.append("ORDER BY L.CHOICE_NO ");
			} else if(!ORIGUtility.isEmptyString(code)) {
			    sql.append("AND UPPER(L.DISPLAY_NAME) like ? ");
			    valueListM.setString(++index,"%"+code.toUpperCase()+"%");
			    sql.append("ORDER BY L.DISPLAY_NAME ");
			}
			
			logger.debug("sql ... "+sql);
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadFileCategoryPopup");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=MASTER_DIALOG_POPUP");
            valueListM.setCurrentScreen("MASTER_DIALOG_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            
            nextAction = "action=ValueListDialogPopup";
            getRequest().getSession().removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);  
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, fileCategoryDesc);
            }
        } catch(Exception ex) {
        	logger.error("Error", ex);
        }
        
		return true;
	}

	@Override
	public int getNextActivityType() {
		 
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter() {
		 
		return nextAction;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
