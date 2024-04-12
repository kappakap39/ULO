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
import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SearchListBoxWebAction
 */
public class SearchListBoxWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(SearchListBoxWebAction.class);
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
		ListBoxMasterM listBoxMasterM = new ListBoxMasterM();

		String listBoxID;
		String displayName;
		String fieldID;
		boolean hvValueForWhere=false;
				
		listBoxID = getRequest().getParameter("listBoxIDSearch");
		displayName = getRequest().getParameter("displayName");
		fieldID = getRequest().getParameter("fieldID");
			
			logger.debug("listBoxID from parameter = "+listBoxID);	
			
			listBoxMasterM.setListBoxID(listBoxID);
			listBoxMasterM.setDisplayName(displayName);
			listBoxMasterM.setFieldID(fieldID);
			if((listBoxID==null || "".equals(listBoxID)) && (displayName==null || "".equals(displayName)) && (fieldID==null || "".equals(fieldID))){
				
				listBoxMasterM = (ListBoxMasterM)getRequest().getSession().getAttribute("First_Search_ListBoxM");
				listBoxID = listBoxMasterM.getListBoxID();
				displayName = listBoxMasterM.getDisplayName();
				fieldID = listBoxMasterM.getFieldID();
				
			}else{
				
				getRequest().getSession().setAttribute("First_Search_ListBoxM", listBoxMasterM);
			}

		try{
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT LIST_BOX_ID, DISPLAY_NAME, CHOICE_NO, FIELD_ID ");
			sql.append("FROM LIST_BOX_MASTER ");
			sql.append("WHERE ");
			
			if(!ORIGUtility.isEmptyString(listBoxID)){
				if(!hvValueForWhere){
					sql.append(" UPPER(LIST_BOX_ID) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(LIST_BOX_ID) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+listBoxID+"%");
			}			
			if(!ORIGUtility.isEmptyString(displayName)){
				if(!hvValueForWhere){
					sql.append(" UPPER(DISPLAY_NAME) like UPPER(?) ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND UPPER(DISPLAY_NAME) like UPPER(?) ");
				}
				valueListM.setString(++index,"%"+displayName+"%");
			}
			if(!ORIGUtility.isEmptyString(fieldID)){
				if(!hvValueForWhere){
					sql.append(" FIELD_ID = ? ");
					hvValueForWhere=true;
				}else{
					sql.append(" AND FIELD_ID = ? ");
				}
				valueListM.setString(++index,fieldID);
			}
			sql.append(" ORDER BY LIST_BOX_ID ");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MS_LISTBOX_MASTER_SEARCHING_SCREEN");
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
