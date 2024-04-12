/*
 * Created on Sep 4, 2008
 * Created by Sankom
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
package com.eaf.orig.shared.view.form.subform;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author Sankom
 *
 * Type: XUWDecisionSubForm
 */
public class XUWDecisionSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(XUWDecisionSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        
        logger.debug("XUWDecisionSubForm");
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
    	ORIGUtility utility = new ORIGUtility();
    	
    	String xuw_decision = request.getParameter("exuw_decision");
    	String decision_by = request.getParameter("exuw_decision_by");
    	String decision_date = request.getParameter("exuw_decision_date");
    	String xuw_reason = request.getParameter("exuw_reason");
    	
    	ApplicationDataM applicationDataM = formHandler.getAppForm();
    	applicationDataM.setXuwDecision(xuw_decision);
    	applicationDataM.setXuwOverrideBy(decision_by);
    	applicationDataM.setXuwOverrideDate( new Timestamp(ORIGUtility.parseThaiToEngDate(decision_date).getTime()));
    	applicationDataM.setXuwDecisionReason(xuw_reason);
    	/* if(!ORIGUtility.isEmptyString(xuw_reason)){
	    	Vector reason = applicationDataM.getReasonVect();
	    	ReasonDataM reasonDataM;
	    	if(reason != null && reason.size() > 0){
	    		for(int i=0; i<reason.size(); i++){
	    			reasonDataM = (ReasonDataM) reason.get(i);
	    			if(OrigConstant.ROLE_XCMR.equals(reasonDataM.getRole())){
	    				logger.debug("Remove reason XUW");
	    				reason.remove(i);
	    				break;
	    			}
	    		}
	    	}
	    	reasonDataM = new ReasonDataM();
			reasonDataM.setReasonCode(xuw_reason);
			reasonDataM.setReasonType(xuw_decision);
			reasonDataM.setReasonSeq(1);
			reasonDataM.setRole(OrigConstant.ROLE_XCMR);
			reasonDataM.setCreateBy(userM.getUserName());
			reasonDataM.setUpdateBy(userM.getUserName());
			reason.add(reasonDataM);
    	}*/
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public HashMap validateForm(HttpServletRequest request, Object appForm) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateSubForm(javax.servlet.http.HttpServletRequest)
     */
    public boolean validateSubForm(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return false;
    }

}
