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
 * Type: ResultSubForm
 */
public class DEResultSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(UWResultSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
    	ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGUtility utility = new ORIGUtility();
        
        String decision = request.getParameter("decision_de");
        String[] reason = request.getParameterValues("reason_de");
        logger.debug("reasons de = "+reason);
        
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        applicationDataM.setDeDecision(decision);
        Vector reasonVt = utility.removeReasonByRole(applicationDataM.getReasonVect(), OrigConstant.ROLE_DE);
        if(reason!=null){
        	int maxSeq = applicationDataM.getReasonVect().size();
        	logger.debug("reason.length1 = " + reason.length);
			ReasonDataM reasonDataM; 
			for(int i=0; i<reason.length; i++){
				reasonDataM = new ReasonDataM();
				reasonDataM.setReasonCode(reason[i]);
				reasonDataM.setReasonType(decision);
				reasonDataM.setReasonSeq(i+1);
				reasonDataM.setRole(OrigConstant.ROLE_DE);
				reasonDataM.setCreateBy(userM.getUserName());
				reasonDataM.setUpdateBy(userM.getUserName());
				reasonVt.add(reasonDataM);
			}
		}	
        applicationDataM.setReasonVect(reasonVt);
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
        boolean result = false;
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ApplicationDataM appForm = formHandler.getAppForm();
        if (appForm == null) {
            appForm = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        Vector vErrors = formHandler.getFormErrors();
        Vector vErrorFields = formHandler.getErrorFields();
        Vector vNotErrorFields = formHandler.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        String reasons[] = request.getParameterValues("reason_de");
        String decision = request.getParameter("decision_de");
        
        logger.debug("decision = " + decision);
        if (!ORIGUtility.isEmptyString(decision)) {
            if (reasons == null) {
                errorMsg = "Please select at least 1 reason and maximum 3 reasons.";
                vErrors.add(errorMsg);
                result = true;
            } else {
                if (reasons.length > 3) {
                    errorMsg = "Please select at least 1 reason and maximum 3 reasons.";
                    vErrors.add(errorMsg);
                    result = true;
                }
            }
        }
        
        return result;
    }

}
