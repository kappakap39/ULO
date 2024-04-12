/*
 * Created on Oct 31, 2007
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
package com.eaf.orig.shared.view.form.subform;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: CMRDecisionSubForm
 */
public class CMRDecisionSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(CMRDecisionSubForm.class); 
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
    	ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
    	ORIGUtility utility = new ORIGUtility();
    	
    	String cmr_decision = request.getParameter("cmr_decision");
    	String decision_by = request.getParameter("decision_by");
    	String decision_date = request.getParameter("decision_date");
    	String cmr_reason = request.getParameter("cmr_reason");
    	
    	ApplicationDataM applicationDataM = formHandler.getAppForm();
    	applicationDataM.setXcmrDecision(cmr_decision);
    	applicationDataM.setXcmrOverrideBy(decision_by);
    	applicationDataM.setXcmrOverrideDate(ORIGUtility.parseThaiToEngDate(decision_date));
    	
    	if(!ORIGUtility.isEmptyString(cmr_reason)){
	    	Vector reason = applicationDataM.getReasonVect();
	    	ReasonDataM reasonDataM;
	    	if(reason != null && reason.size() > 0){
	    		for(int i=0; i<reason.size(); i++){
	    			reasonDataM = (ReasonDataM) reason.get(i);
	    			if(OrigConstant.ROLE_XCMR.equals(reasonDataM.getRole())){
	    				logger.debug("Remove reason XCMR");
	    				reason.remove(i);
	    				break;
	    			}
	    		}
	    	}
	    	reasonDataM = new ReasonDataM();
			reasonDataM.setReasonCode(cmr_reason);
			reasonDataM.setReasonType(cmr_decision);
			reasonDataM.setReasonSeq(1);
			reasonDataM.setRole(OrigConstant.ROLE_XCMR);
			reasonDataM.setCreateBy(userM.getUserName());
			reasonDataM.setUpdateBy(userM.getUserName());
			reason.add(reasonDataM);
    	}
		
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public HashMap validateForm(HttpServletRequest request, Object appForm) {
         
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
