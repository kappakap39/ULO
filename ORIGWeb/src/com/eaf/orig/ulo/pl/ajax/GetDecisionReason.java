/**
 * Create Date Apr 11, 2012 
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
package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;


public class GetDecisionReason implements AjaxDisplayGenerateInf {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
//     String decisionCode = request.getParameter("decision_option");
		
        String displayMode = request.getParameter("displayMode-decision");
        String reasonCode = request.getParameter("reasonCode");
        
        logger.debug("displayMode >> "+displayMode);
        logger.debug("reasonCode >> "+reasonCode);
        
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        PLOrigFormHandler OrigForm =  (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
        PLApplicationDataM applicationM =  (PLApplicationDataM) OrigForm.getAppForm();
        Vector<PLReasonDataM> reasonVect = applicationM.getReasonVect();
        if(reasonVect==null){
        	reasonVect = new Vector<PLReasonDataM>();
        }
        
        logger.debug("BusClassID >> "+applicationM.getBusinessClassId());
        
//      logger.debug("plReasonVect From New= "+plReasonVect+"Size= "+plReasonVect.size());
//      logger.debug("DecisionCode >> "+decisionCode);
//      logger.debug("DisplayMode >> "+displayMode);
//      logger.debug("ReasonCode >> "+reasonCode);
        
    	String searchType = (String) request.getSession().getAttribute("searchType");
        
        return HTMLRenderUtil.CreateReasonTable(reasonCode, displayMode, userM.getCurrentRole(), reasonVect ,applicationM.getBusinessClassId(),searchType);
	}

}
