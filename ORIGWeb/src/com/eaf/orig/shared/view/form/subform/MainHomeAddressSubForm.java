/*
 * Created on Sep 18, 2007
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

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: MainHomeAddressSubForm
 */
public class MainHomeAddressSubForm extends ORIGSubForm {

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
         

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
        
        //Check Address
        PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(appForm,OrigConstant.PERSONAL_TYPE_APPLICANT);

    	if(personalInfoDataM == null){
    		personalInfoDataM = new PersonalInfoDataM();		
    	}
    	
    	String personalType = request.getParameter("appPersonalType");
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
            personalInfoDataM = utility.getPersonalInfoByType(appForm, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
        }
        int addressSize = utility.getAddressSizeByPersonalType(personalInfoDataM.getAddressVect(), personalInfoDataM.getCustomerType(), personalType);
        if (personalInfoDataM.getAddressVect() == null) {
            errorMsg = "Address is required.";
            vErrors.add(errorMsg);
            result = true;
        } else {
            //if (personalInfoDataM.getAddressVect().size() != addressSize) {
            if (personalInfoDataM.getAddressVect().size() < addressSize) {
                errorMsg = "All Address types is required.";
                vErrors.add(errorMsg);
                result = true;
            }
        }
        return result;
    }

}
