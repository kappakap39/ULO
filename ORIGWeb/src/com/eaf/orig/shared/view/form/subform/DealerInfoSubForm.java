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

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: DealerInfoSubForm
 */
public class DealerInfoSubForm extends ORIGSubForm {

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        
        String identificationNo = request.getParameter("identification_no");
        String dealer = request.getParameter("dealer");
        String seller = request.getParameter("seller");
        String salesman = request.getParameter("salesman");
        
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        
        applicationDataM.setDealerCode(dealer);
        applicationDataM.setSellerCode(seller);
        applicationDataM.setSalesManCode(salesman);

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
        // TODO Auto-generated method stub
        return false;
    }

}
