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
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author weeraya
 * 
 * Type: VerificationSubForm
 */
public class VerificationSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(VerificationSubForm.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest,
     *      java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession()
                .getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        String personalType = (String) request.getSession().getAttribute(
                "PersonalType");
        String personalSeq = request.getParameter("personalSeq");
        int seq = 0;
        if (!ORIGUtility.isEmptyString(personalSeq)) {
            seq = Integer.parseInt(personalSeq);
        }
        logger.debug("personalType = " + personalType);
        logger.debug("personalSeq = " + seq);
        //      Get Personal Info
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        PersonalInfoDataM personalInfoDataM=null;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true)
                    .getAttribute("MAIN_POPUP_DATA");
        } else  if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM,
                    OrigConstant.PERSONAL_TYPE_APPLICANT);
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xruesVerification = personalInfoDataM
                .getXrulesVerification();
        if (xruesVerification == null) {
            xruesVerification = new XRulesVerificationResultDataM();
        }
        String strDebBdParam = request.getParameter("debt_mor");
        BigDecimal debBDParam = null;
        if(!"".equals(strDebBdParam)&&null!=strDebBdParam){
           debBDParam = utility.stringToBigDecimal(strDebBdParam);        
            xruesVerification.setDEBT_BD_PARAM(debBDParam);
        }else  if( !(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType()) )){
           //Gurator Not have NCB
            xruesVerification.setDEBT_BD_PARAM(new BigDecimal(0));
        }
        String consentFlag = request.getParameter("consent_flag");
        String strConsentDate = request.getParameter("consent_date");
        logger.debug("Consent = " + consentFlag);
        logger.debug("Consent Date = " + strConsentDate);
        personalInfoDataM.setConsentFlag(consentFlag);
        if (strConsentDate != null && !"".equals(strConsentDate)) {
            Date consentDate = null;
            consentDate = ORIGUtility.parseThaiToEngDate(strConsentDate);
            personalInfoDataM.setConsentDate( consentDate);
        }
        request.getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
        request.getSession().setAttribute("SUPCARD_POPUP_DATA", personalInfoDataM);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest,
     *      java.lang.Object)
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
        String customerType = (String)request.getSession().getAttribute("CUSTOMET_TYPE");
        
        if (!("Y").equals(appForm.getDrawDownFlag())) {
            String personalType = request.getParameter("appPersonalType");//(String)
            PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
            if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
            } else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
                personalInfoDataM = utility.getPersonalInfoByType(appForm, OrigConstant.PERSONAL_TYPE_APPLICANT);
            }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
            }
            if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
                if (("Y").equals(request.getParameter("consent_flag"))) {
                    if (ORIGUtility.isEmptyString((String) request.getParameter("consent_date"))) {
                        errorMsg = "NCB consent date is required.";
                        vErrors.add(errorMsg);
                        vErrorFields.add("consent_date");
                        result = true;
                    } else {
                        vNotErrorFields.add("consent_date");
                    }
                }
                XRulesVerificationResultDataM rulesVerificationResultDataM = personalInfoDataM.getXrulesVerification();
                if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
                        && ORIGUtility.isEmptyString(rulesVerificationResultDataM.getNCBResult())) {
                    errorMsg = "NCB is required.";
                    vErrors.add(errorMsg);
                    vErrorFields.add("resNCB");
                    result = true;
                } else {
                    //vNotErrorFields.add("resNCB");
                }
            } else {
//                boolean result2 = getMandateErrorGuarantorVerDE(request, customerType);
//                if (result2) {
//                    result = true;
//                }
            }
        }
        
        return result;
    }

}
