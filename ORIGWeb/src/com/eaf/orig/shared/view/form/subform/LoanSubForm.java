/*
 * Created on Sep 21, 2007
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: LoanSubForm
 */
public class LoanSubForm extends ORIGSubForm {

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
         

    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
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
        
        if (appForm.getLoanVect() == null || appForm.getLoanVect().size() == 0) {
            errorMsg = "Loan is required.";
            vErrors.add(errorMsg);
            result = true;
        } else {
            LoanDataM loanM = (LoanDataM) appForm.getLoanVect().elementAt(0);
            if (loanM.getCostOfCarPrice().compareTo(new BigDecimal(0)) == 0) {
                errorMsg = "Car Price of Loan is required.";
                vErrors.add(errorMsg);
                result = true;
            }
            if (!OrigConstant.LOAN_TYPE_LEASING.equalsIgnoreCase(loanM.getLoanType())) {
                if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
                    if (new BigDecimal(0).compareTo(loanM.getAppraisalPrice()) == 0) {
                        errorMsg = "Appraisal Price of Loan is required.";
                        vErrors.add(errorMsg);
                        result = true;
                    }
                }
            }

        }
        
        return result;
    }

}
