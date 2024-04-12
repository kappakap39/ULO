/*
 * Created on Sep 25, 2007
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

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

/**
 * @author weeraya
 *
 * Type: LoadCachePopupWebAction1
 */
public class LoadCachePopupWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadCachePopupWebAction.class);
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
        String cacheName = getRequest().getParameter("cacheName");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
        
        String codeFromMain = null;
        
        if(OrigConstant.CACHE_NAME_AREA.equals(cacheName)){
            codeFromMain = getRequest().getParameter("area_code");
        }
        
        logger.debug("cacheName = "+cacheName);
        logger.debug("codeFromMain = "+codeFromMain);
        logger.debug("code = "+code);
        
		Vector dataVect = new Vector();;
		if(!ORIGUtility.isEmptyString(codeFromMain)){
		    dataVect = utility.getDataFormCacheByValue(cacheName, codeFromMain);
		}else if(!ORIGUtility.isEmptyString(code)){
		    dataVect = utility.getDataFormCacheByValue(cacheName, code);
		}else{
		    dataVect = utility.getDataFormCacheByValue(cacheName, null);
		}
		
		logger.debug("dataVect = "+dataVect);
		
		//if data in cache is null then get data form table HPTBHP91
		if(dataVect.size() == 0){
		    dataVect = utility.getMasterDataFormCache(cacheName);
		}
		
		ValueListM valueListM = new ValueListM();
		valueListM.setTextboxCode(textboxCode);
		valueListM.setTextboxDesc(textboxDesc);
		valueListM.setSearchAction("LoadCachePopup");
        valueListM.setNextPage(false);
        valueListM.setItemsPerPage(10);
        valueListM.setReturnToAction("page=CACHE_POPUP");
        valueListM.setCacheName(cacheName);
	        
        getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
        getRequest().getSession().setAttribute("CACHE_POPUP", dataVect);
        nextAction = "action=ValueListCacheWebAction";
        
        if(!ORIGUtility.isEmptyString(code)){
        	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
        }else{
        	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, cacheName);
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
