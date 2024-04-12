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
 * @author weeraya
 *
 * Type: LoadCarModelWebAction
 */
public class LoadCarModelWebAction extends WebActionHelper implements WebAction {
    private static Logger logger = Logger.getLogger(LoadCarModelWebAction.class);
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
        String car_model = getRequest().getParameter("car_model");
        String car_brand = getRequest().getParameter("car_brand");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
        boolean isFrist = true;
        logger.debug("car_model = "+car_model);
        logger.debug("car_brand = "+car_brand);
        logger.debug("code = "+code);
        
        try {
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT MODEL, TH_DESC, EN_DESC, BRAND, GEAR, CC, WEIGHT ");
			sql.append("FROM MS_CAR_MODEL ");
            
			if(!ORIGUtility.isEmptyString(car_model)){
			    sql.append("WHERE MODEL = ? ");
			    valueListM.setString(++index,car_model);
			    isFrist = false;
			}else if(!ORIGUtility.isEmptyString(code)){
			    sql.append("WHERE UPPER(TH_DESC) like ? ");
			    valueListM.setString(++index,"%"+code.toUpperCase()+"%");
			    isFrist = false;
			}
			if(isFrist){
				if(!ORIGUtility.isEmptyString(car_brand)){
				    sql.append("WHERE BRAND = ? ");
				    valueListM.setString(++index,car_brand);
				}
			}else{
				if(!ORIGUtility.isEmptyString(car_brand)){
				    sql.append("AND BRAND = ? ");
				    valueListM.setString(++index,car_brand);
				}
			}
			sql.append("ORDER BY MODEL ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadCarModel");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	//getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, car_model);
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
