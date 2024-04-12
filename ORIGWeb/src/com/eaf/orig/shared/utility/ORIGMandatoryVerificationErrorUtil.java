/*
 * Created on Dec 10, 2007
 * Created by Sankom Sanpunya
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
package com.eaf.orig.shared.utility;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.wf.shared.ORIGWFConstant;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: ORIGMandatoryVerificationErrorUtil
 */
public class ORIGMandatoryVerificationErrorUtil implements Serializable {

    /**
     *  
     */
    public ORIGMandatoryVerificationErrorUtil() {
        super();

    }

    private static ORIGMandatoryVerificationErrorUtil me;

    public static Logger logger = Logger.getLogger(ORIGMandatoryVerificationErrorUtil.class);

    public synchronized static ORIGMandatoryVerificationErrorUtil getInstance() {
        if (me == null) {
            me = new ORIGMandatoryVerificationErrorUtil();
        }
        return me;
    }

    public boolean getErrorMessageNCB(HttpServletRequest request, String customerType, String personalType) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();
        String paramField = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        ORIGUtility utility = new ORIGUtility();
        String errorMsg = null;
        paramField = "identification_no";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {

            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        if (OrigConstant.PERSONAL_TYPE_APPLICANT.endsWith(personalType)) {
            paramField = "office_code";
            if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
                vErrors.add(errorMsg);
                vErrorFields.add(paramField);
                resultFlag = true;
            }
            paramField = "area_code";
            if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
                vErrors.add(errorMsg);
                vErrorFields.add(paramField);
                resultFlag = true;
            }
            if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                paramField = "pre_score_mkt_code";
                if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
                    errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
                    vErrors.add(errorMsg);
                    vErrorFields.add(paramField);
                    resultFlag = true;
                }
            }            
        }
        paramField = "name_thai";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }

        if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
            paramField = "surname_thai";
            if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
                vErrors.add(errorMsg);
                vErrorFields.add(paramField);
                resultFlag = true;
            }
        }

        paramField = "birth_date";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "m_birth_date";
        if (!ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            int age = utility.stringToInt(request.getParameter("m_age"));
            String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
            logger.debug("null date=" + nullDate);
            logger.debug("param field=" + paramField);
            if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter(paramField)))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
                vErrors.add(errorMsg + "[Marriage]");
                vErrorFields.add(paramField);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramField);
            }
        }
       
        return resultFlag;
    }

    public boolean getErrorMessageDedup(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        //=============IdNo ======================
        paramFields = "identification_no";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        return resultFlag;
    }

    public boolean getErrorMessageDupVehicle(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        //=============Car======================
        paramFields = "car";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        if (OrigConstant.CAR_TYPE_USE.equals(value)) {//car type use car
            //=============Chassis No======================
            paramFields = "car_classis_no";
            value = request.getParameter(paramFields);
            if (value == null || value.equals("")) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
            //=============Engine no======================
            paramFields = "car_engine_no";
            value = request.getParameter(paramFields);
            if (value == null || value.equals("")) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
        }
        return resultFlag;
    }

    public boolean getErrorMessageExistingCustomer(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        //=============IdNo ======================
        paramFields = "identification_no";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        return resultFlag;
    }

    public boolean getErrorMessageBlacklist(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();
        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        //=============IdNo ======================
        paramFields = "identification_no";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        //=============Thai Name======================
        paramFields = "name_thai";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        //=============Thai Surname Name======================
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())
                || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            paramFields = "surname_thai";
            value = request.getParameter(paramFields);
            if (value == null || value.equals("")) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
        }
        if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
            if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
                paramFields = "pre_score_houseid";
                value = request.getParameter(paramFields);
                if (value == null || value.equals("")) {
                    errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                    vErrors.add(errorMsg);
                    vErrorFields.add(paramFields);
                    resultFlag = true;
                } else {
                    vNotErrorFields.add(paramFields);
                }
            }
        } else {
            // paramFields = "house_id";
            /*
             * AddressDataM addrDoc = utility.getAddressByType(
             * personalInfoDataM, OrigConstant.ADDRESS_TYPE_DOC); if (addrDoc !=
             * null) { value=addrDoc.getHouseID(); } if (value == null ||
             * value.equals("")) { errorMsg =
             * ErrorUtil.getShortErrorMessage(request, "HOUSE_ID");
             * vErrors.add(errorMsg); //vErrorFields.add(paramFields);
             * resultFlag = true; } else { // vNotErrorFields.add(paramFields); }
             */
        }
        return resultFlag;
    }

    public boolean getErrorMessageBlaclistVehicle(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        //      =============Car======================
        paramFields = "car";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        if (OrigConstant.CAR_TYPE_USE.equals(value)) {//car type use car
            //=============Chassis No======================
            paramFields = "car_classis_no";
            value = request.getParameter(paramFields);
            if (value == null || value.equals("")) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
            //=============Engine no======================
            paramFields = "car_engine_no";
            value = request.getParameter(paramFields);
            if (value == null || value.equals("")) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
            //=============Registration======================
            paramFields = "car_register_no";
            value = request.getParameter(paramFields);
            if (value == null || value.equals("")) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
        }//use car
        return resultFlag;
    }

    public boolean getErrorMessagePolicyRules(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();
        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        XRulesVerificationResultDataM xRulesVerification = null;
        if (personalInfoDataM != null) {
            xRulesVerification = personalInfoDataM.getXrulesVerification();
        }

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {

            //=============NCB======================
            //paramFields="car";
            //value = request.getParameter(paramFields);
            //paramFields = "res_NCB";
            if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
                paramFields = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.NCB));
                if (xRulesVerification == null || xRulesVerification.getNCBColor() == null || "".equals(xRulesVerification.getNCBColor())) {
                    errorMsg = ErrorUtil.getShortErrorMessage(request, "ncb");
                    vErrors.add(errorMsg);
                    vErrorFields.add(paramFields);
                    resultFlag = true;
                } else {
                    vNotErrorFields.add(paramFields);
                }
            }

            //================================
            //paramFields = "res_DebtBurden";
            paramFields = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN));
            if (xRulesVerification == null || xRulesVerification.getDEBT_BDResult() == null || "".equals(xRulesVerification.getDEBT_BDResult())) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_burden");
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
        }
        return resultFlag;
    }

public boolean getErrorMessageDebbd(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();
        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
                                                                      // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }
        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        //================================
        //NCB
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())&& (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType()) || (OrigConstant.PERSONAL_TYPE_GUARANTOR
                .equals(personalInfoDataM.getPersonalType()) && (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag()) || OrigConstant.COBORROWER_FLAG_UN_ACTIVE
                        .equals(personalInfoDataM.getCoborrowerFlag()))))) {
            paramFields = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.NCB));
            if (xrulesVerificationResult.getNCBColor() == null || "".equals(xrulesVerificationResult.getNCBColor())) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "ncb");
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
        }
        //paramFields = "res_DebtAmt";
        paramFields = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT));
        if (xrulesVerificationResult.getDebtAmountResult() == null || "".equals(xrulesVerificationResult.getDebtAmountResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt");
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================

        return resultFlag;
    }    public boolean getErrorMessageFICO(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");// (String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }
        // ================NCB========================
        String paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.NCB));
        logger.debug("xrulesVerificationResult.getNCBColor()" + xrulesVerificationResult.getNCBColor());
        if (xrulesVerificationResult.getNCBColor() == null || "".equals(xrulesVerificationResult.getNCBColor())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "ncb");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        return resultFlag;
    }

    public boolean getErrorMessagePreScoreIndividual(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramField = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");// (String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }

        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }

        paramField = "identification_no";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {

            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "office_code";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "name_thai";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }

        paramField = "surname_thai";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "area_code";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "birth_date";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "m_birth_date";
        if (!ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            int age = utility.stringToInt(request.getParameter("m_age"));
            String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
            if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter(paramField)))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
                vErrors.add(errorMsg + "[Marriage]");
                vErrorFields.add(paramField);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramField);
            }
        }

        logger.debug("xrulesVerificationResult.getDEBT_BDResult()" + xrulesVerificationResult.getDEBT_BDResult());
        //====================Existing Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER));
        if (xrulesVerificationResult.getExistCustResult() == null || "".equals(xrulesVerificationResult.getExistCustResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "existing_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================Blacklist Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.BLACKLIST));
        if (xrulesVerificationResult.getBLResult() == null || "".equals(xrulesVerificationResult.getBLResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "blacklist_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //   ====================DEBT AMT====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT));
        if (xrulesVerificationResult.getDebtAmountResult() == null || "".equals(xrulesVerificationResult.getDebtAmountResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //      ==================Debt Burden======================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN));
        if (xrulesVerificationResult.getDEBT_BDResult() == null || "".equals(xrulesVerificationResult.getDEBT_BDResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_burden");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================NCB========================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.NCB));
        logger.debug("xrulesVerificationResult.getNCBColor()" + xrulesVerificationResult.getNCBColor());
        if (xrulesVerificationResult.getNCBColor() == null || "".equals(xrulesVerificationResult.getNCBColor())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "ncb");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //=============Car======================
        paramField = "car";
        value = request.getParameter(paramField);
        if (value != null && value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if(value != null){
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Brand======================
        paramField = "car_brand";
        value = request.getParameter(paramField);
        if (value != null && value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if(value != null){
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Catagory======================
        paramField = "car_category";
        value = request.getParameter(paramField);
        if (value != null && value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if(value != null){
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Year======================
        paramField = "car_year";
        value = request.getParameter(paramField);
        if (value != null && value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (value != null && value.length() != 4) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "invalid_car_year");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if(value != null){
            vNotErrorFields.add(paramField);
        }
        //================================

        //=============Marketing Code======================
        paramField = "pre_score_mkt_code";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        // =============AGE======================
        /*
         * paramField = "age"; value = request.getParameter(paramField); if
         * (value == null || value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */
        //=============Gender======================
        paramField = "gender";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //=================================

        //=============Occupation======================
        paramField = "pre_score_occupation";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Position======================
        /*
         * paramField = "pre_score_position"; value =
         * request.getParameter(paramField); if (value == null ||
         * value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */
        //================================
        //=============Bustiness Type======================
        paramField = "pre_score_bus_type";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Salary======================
        paramField = "pre_score_salary";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Other Income======================
        paramField = "pre_score_other_income";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Qualification======================
        paramField = "pre_score_qualification";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Total Working Year======================
        paramField = "pre_score_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Total Working Month======================
        paramField = "pre_score_month";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=================Working Year Not More than Age===================
        //paramField = "pre_score_month";
        String strWorkingYear = request.getParameter("pre_score_year");
        String strWorkingMonth = request.getParameter("pre_score_month");
        String strAge = request.getParameter("age");
        if (!(strWorkingYear == null || strWorkingYear.equals("")) && !(strWorkingMonth == null || strWorkingMonth.equals(""))
                && !(strAge == null || strAge.equals(""))) {
            int iworkYear = utility.stringToInt(strWorkingYear);
            int iworkMonth = utility.stringToInt(strWorkingMonth);
            int iAge = utility.stringToInt(strAge);
            if (iAge < iworkYear) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
                vErrors.add(errorMsg);
                resultFlag = true;
            }
        }
        //=====================================================================
        //=============House Regis Status======================
        paramField = "pre_score_house_regis_status";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Time current address======================
        paramField = "pre_score_time_current_address_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        paramField = "pre_score_time_current_address_month";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Land Ownership======================
        paramField = "pre_score_land_ownership";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Down payment======================
        paramField = "pre_score_down_payment";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Term Loan======================
        paramField = "pre_score_term_loan";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============No.of Guarantor======================
        paramField = "pre_score_no_of_guarantor";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============car price======================
        paramField = "pre_score_car_price";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //=============Finnance Amount Vat======================
        paramField = "pre_score_financeAmtVAT";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Pre Score Installment vat======================
        paramField = "pre_score_InstallmentAmtVAT";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Pre Score House ID======================
        paramField = "pre_score_houseid";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //======================Time in current address
        // /AGE=====================
        String strAddressMonth = request.getParameter("pre_score_time_current_address_month");
        String strAddressYear = request.getParameter("pre_score_time_current_address_year");
        // String strAge=request.getParameter("age");
        if ((strAge != null && !"".equals(strAge)) && (strAddressMonth != null && !"".equals(strAddressMonth))
                && (strAddressYear != null && !"".equals(strAddressYear))) {
            int timeInCurrentAddressYear = utility.stringToInt(request.getParameter("pre_score_time_current_address_year"));
            int timeInCurrentAddressMonth = utility.stringToInt(request.getParameter("pre_score_time_current_address_month"));
            int age = utility.stringToInt(request.getParameter("age"));
            if (timeInCurrentAddressMonth > 0) {
                timeInCurrentAddressYear = timeInCurrentAddressYear + 1;
            }
            if (timeInCurrentAddressYear > age) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "TIME_IN_CURRENT_ADDRESS_AGE");
                vErrors.add(errorMsg);
                resultFlag = true;
            }
        }
        return resultFlag;
    }

    public boolean getErrorMessagePreScoreCorporate(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramField = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }

        paramField = "identification_no";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {

            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "office_code";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "name_thai";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }

        paramField = "area_code";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "birth_date";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "m_birth_date";
        if (!ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            int age = utility.stringToInt(request.getParameter("m_age"));
            String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
            if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter(paramField)))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
                vErrors.add(errorMsg + "[Marriage]");
                vErrorFields.add(paramField);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramField);
            }
        }

        //==================Debt Burden======================
        logger.debug("xrulesVerificationResult.getDEBT_BDResult()" + xrulesVerificationResult.getDEBT_BDResult());
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN));
        if (xrulesVerificationResult.getDEBT_BDResult() == null || "".equals(xrulesVerificationResult.getDEBT_BDResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_burden");
            vErrorFields.add(paramField);
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //====================Existing Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER));
        if (xrulesVerificationResult.getExistCustResult() == null || "".equals(xrulesVerificationResult.getExistCustResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "existing_customer");
            vErrorFields.add(paramField);
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================DEBT AMT====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT));
        if (xrulesVerificationResult.getDebtAmountResult() == null || "".equals(xrulesVerificationResult.getDebtAmountResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt");
            vErrorFields.add(paramField);
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //=============Car======================
        paramField = "car";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Brand======================
        paramField = "car_brand";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Catagory======================
        paramField = "car_category";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Year======================
        paramField = "car_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (value.length() != 4) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "invalid_car_year");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        // =============AGE======================
        paramField = "age";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //=============Gender======================
        paramField = "gender";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //=================================
        //=============Marketing Code======================
        paramField = "pre_score_mkt_code";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Share Capital======================
        paramField = "pre_score_share_capital";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Buiness Size======================
        paramField = "pre_score_bus_size";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Bustiness Type======================
        paramField = "pre_score_bus_type";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);//"Business
            // Type
            // is
            // required.";
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Salary======================
        paramField = "pre_score_salary";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);//"Salary
            // Type
            // is
            // required.";
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Other Income======================
        paramField = "pre_score_other_income";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);//"Other
            // Income
            // is
            // required.";
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //=============Land Ownership======================
        paramField = "pre_score_land_ownership";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);//"Land
            // Ownership
            // is
            // required.";
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        // =============Cheque Return======================
        paramField = "pre_score_cheque_return";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);//
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============No of Employee======================
        paramField = "pre_score_no_of_employee";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Overdue Over 60 Day ======================
        paramField = "pre_score_overdue_over_60_days";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============???????????????????????? 6 ?????======================
        paramField = "pre_score_max_time_overdue";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Number of Revolving======================
        paramField = "pre_score_number_of_revolving_loan";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Auto Mobile Hire======================
        paramField = "pre_score_number_of_automobile_hire_purchase";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Down payment======================
        paramField = "pre_score_down_payment";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Term Loan======================
        paramField = "pre_score_term_loan";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //================================
        //=============NPL======================
        paramField = "pre_score_NPL_histry_restructure";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============car price======================
        paramField = "pre_score_car_price";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //=============Finnance Amount Vat======================
        paramField = "pre_score_financeAmtVAT";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Pre Score Installment vat======================
        paramField = "pre_score_InstallmentAmtVAT";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        return resultFlag;
    }

    public boolean getErrorMessagePreScoreForeigner(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramField = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }
        //==================Debt Burden======================
        logger.debug("xrulesVerificationResult.getDEBT_BDResult()" + xrulesVerificationResult.getDEBT_BDResult());

        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN));
        if (xrulesVerificationResult.getDEBT_BDResult() == null || "".equals(xrulesVerificationResult.getDEBT_BDResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_burden");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //====================Existing Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER));
        if (xrulesVerificationResult.getExistCustResult() == null || "".equals(xrulesVerificationResult.getExistCustResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "existing_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================Blacklist Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.BLACKLIST));
        if (xrulesVerificationResult.getBLResult() == null || "".equals(xrulesVerificationResult.getBLResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "blacklist_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //====================DEBT AMT====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT));
        if (xrulesVerificationResult.getDebtAmountResult() == null || "".equals(xrulesVerificationResult.getDebtAmountResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //=============Car======================
        paramField = "car";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Brand======================
        paramField = "car_brand";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Catagory======================
        paramField = "car_category";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Year======================
        paramField = "car_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (value.length() != 4) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "invalid_car_year");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        // =============AGE======================
        paramField = "age";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //=============Gender======================
        paramField = "gender";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //=================================
        //=============Marketing Code======================
        paramField = "pre_score_mkt_code";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Occupation======================
        paramField = "pre_score_occupation";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Position======================
        /*
         * paramField = "pre_score_position"; value =
         * request.getParameter(paramField); if (value == null ||
         * value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */
        //================================
        //=============Bustiness Type======================
        paramField = "pre_score_bus_type";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Salary======================
        paramField = "pre_score_salary";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Other Income======================
        paramField = "pre_score_other_income";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Qualification======================
        paramField = "pre_score_qualification";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Total Working Year======================
        paramField = "pre_score_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Total Working Month======================
        paramField = "pre_score_month";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============House Regis Status======================
        paramField = "pre_score_house_regis_status";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Time current address======================
        paramField = "pre_score_time_current_address_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        paramField = "pre_score_time_current_address_month";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Land Ownership======================
        paramField = "pre_score_land_ownership";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Overdue Over 60 Day ======================
        paramField = "pre_score_overdue_over_60_days";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============???????????????????????? 6 ?????======================
        paramField = "pre_score_max_time_overdue";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Number of Revolving======================
        paramField = "pre_score_number_of_revolving_loan";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Auto Mobile Hire======================
        paramField = "pre_score_number_of_automobile_hire_purchase";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Down payment======================
        paramField = "pre_score_down_payment";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Term Loan======================
        paramField = "pre_score_term_loan";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============No.of Guarantor======================
        paramField = "pre_score_no_of_guarantor";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============car price======================
        paramField = "pre_score_car_price";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //=============Finnance Amount Vat======================
        paramField = "pre_score_financeAmtVAT";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Pre Score Installment vat======================
        paramField = "pre_score_InstallmentAmtVAT";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=================Working Year Not More than Age===================
        //paramField = "pre_score_month";
        String strWorkingYear = request.getParameter("pre_score_year");
        String strWorkingMonth = request.getParameter("pre_score_month");
        String strAge = request.getParameter("age");
        if (!(strWorkingYear == null || strWorkingYear.equals("")) && !(strWorkingMonth == null || strWorkingMonth.equals(""))
                && !(strAge == null || strAge.equals(""))) {
            int iworkYear = utility.stringToInt(strWorkingYear);
            int iworkMonth = utility.stringToInt(strWorkingMonth);
            int iAge = utility.stringToInt(strAge);
            if (iAge < iworkYear) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
                vErrors.add(errorMsg);
                resultFlag = true;
            }

        }
        //================================
        //      ======================Time in current address
        // /AGE=====================
        String strAddressMonth = request.getParameter("pre_score_time_current_address_month");
        String strAddressYear = request.getParameter("pre_score_time_current_address_year");
        // String strAge=request.getParameter("age");
        if ((strAge != null && !"".equals(strAge)) && (strAddressMonth != null && !"".equals(strAddressMonth))
                && (strAddressYear != null && !"".equals(strAddressYear))) {
            int timeInCurrentAddressYear = utility.stringToInt(request.getParameter("pre_score_time_current_address_year"));
            int timeInCurrentAddressMonth = utility.stringToInt(request.getParameter("pre_score_time_current_address_month"));
            int age = utility.stringToInt(request.getParameter("age"));
            if (timeInCurrentAddressMonth > 0) {
                timeInCurrentAddressYear = timeInCurrentAddressYear + 1;
            }
            if (timeInCurrentAddressYear > age) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "TIME_IN_CURRENT_ADDRESS_AGE");
                vErrors.add(errorMsg);
                resultFlag = true;
            }
        }
        //=============Pre Score House ID======================
        /*
         * paramField = "pre_score_houseid"; value =
         * request.getParameter(paramField); if (value == null ||
         * value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */
        //================================
        return resultFlag;
    }

    public boolean getErrorMessageExecuteALL(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();
        String personalType = request.getParameter("appPersonalType");// (String)
        // request.getSession().getAttribute("PersonalType");

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        //=============IdNo ======================
        paramFields = "identification_no";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        if (OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalType)) {
            //=============Car======================
            paramFields = "car";
            value = request.getParameter(paramFields);
            if (value == null || value.equals("")) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            //================================
            if (OrigConstant.CAR_TYPE_USE.equals(value)) {//car type use car
                //=============Chassis No======================
                paramFields = "car_classis_no";
                value = request.getParameter(paramFields);
                if (value == null || value.equals("")) {
                    errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                    vErrors.add(errorMsg);
                    vErrorFields.add(paramFields);
                    resultFlag = true;
                } else {
                    vNotErrorFields.add(paramFields);
                }
                //================================
                //=============Engine no======================
                paramFields = "car_engine_no";
                value = request.getParameter(paramFields);
                if (value == null || value.equals("")) {
                    errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
                    vErrors.add(errorMsg);
                    vErrorFields.add(paramFields);
                    resultFlag = true;
                } else {
                    vNotErrorFields.add(paramFields);
                }
                //================================
            }
        }
        return resultFlag;
    }

    public boolean getErrorMessageExecuteALLGuarantor(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        //=============IdNo ======================
        paramFields = "identification_no";
        value = request.getParameter(paramFields);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramFields);
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        return resultFlag;
    }

    public boolean getErrorMessageDebAmount(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();
        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }
        String paramFields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        //if individual check NCB
        //================================
        //NCB
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())
                && (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType()) || (OrigConstant.PERSONAL_TYPE_GUARANTOR
                        .equals(personalInfoDataM.getPersonalType()) && (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag()) || OrigConstant.COBORROWER_FLAG_UN_ACTIVE
                        .equals(personalInfoDataM.getCoborrowerFlag()))))) {
            paramFields = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.NCB));
            if (xrulesVerificationResult.getNCBColor() == null || "".equals(xrulesVerificationResult.getNCBColor())) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "ncb");
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(xrulesVerificationResult.getDebtAmountODInterestFlag())) {
                paramFields = "debt_mor";
                if (request.getParameter(paramFields) == null) {
                    errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_mor");
                    vErrors.add(errorMsg);
                    vErrorFields.add(paramFields);
                    resultFlag = true;
                } else {
                    vNotErrorFields.add(paramFields);
                }
            }
        } else {//corporate /foreigner
            paramFields = "debt_mor";
            if (request.getParameter(paramFields) == null) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_mor");
                vErrors.add(errorMsg);
                vErrorFields.add(paramFields);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramFields);
            }

        }
        //================================
        //================================
        //Existing Customer;
        paramFields = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER));
        if (xrulesVerificationResult.getExistCustResult() == null || "".equals(xrulesVerificationResult.getExistCustResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "existing_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramFields);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramFields);
        }
        //================================
        //Check Cal Debt Co Borrower /Guarantor
        if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
            //Load Data Gruantor
            Vector guarantorVect = utility.getVectorPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_GUARANTOR);
            if (guarantorVect != null && guarantorVect.size() > 0) {
                for (int i = 0; i < guarantorVect.size(); i++) {
                    PersonalInfoDataM guaranPersonalInfoDataM = (PersonalInfoDataM) guarantorVect.get(i);
                    if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(guaranPersonalInfoDataM.getCoborrowerFlag())
                            || ((OrigConstant.COBORROWER_FLAG_NO.equals(guaranPersonalInfoDataM.getCoborrowerFlag())) && OrigConstant.ORIG_FLAG_Y
                                    .equals(guaranPersonalInfoDataM.getDebtIncludeFlag()))) {
                        XRulesVerificationResultDataM xRulesVerDataM = guaranPersonalInfoDataM.getXrulesVerification();
                        if (xRulesVerDataM != null && xRulesVerDataM.getDebtAmountResult() == null) {
                            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt_co") + "  " + personalInfoDataM.getIdNo() + " is required.";
                            vErrors.add(errorMsg);
                            resultFlag = true;
                        }
                    }
                }
            }
        }
        return resultFlag;

    }

    public boolean getErrorMessageScoreIndividual(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramField = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }
        logger.debug("xrulesVerificationResult.getDEBT_BDResult()" + xrulesVerificationResult.getDEBT_BDResult());
        //====================Existing Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER));
        if (xrulesVerificationResult.getExistCustResult() == null || "".equals(xrulesVerificationResult.getExistCustResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "existing_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================Blacklist Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.BLACKLIST));
        if (xrulesVerificationResult.getBLResult() == null || "".equals(xrulesVerificationResult.getBLResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "blacklist_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //    ====================DEBT AMT====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT));
        if (xrulesVerificationResult.getDebtAmountResult() == null || "".equals(xrulesVerificationResult.getDebtAmountResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //      ==================Debt Burden======================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN));
        if (xrulesVerificationResult.getDEBT_BDResult() == null || "".equals(xrulesVerificationResult.getDEBT_BDResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_burden");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================NCB========================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.NCB));
        logger.debug("xrulesVerificationResult.getNCBColor()" + xrulesVerificationResult.getNCBColor());
        if (xrulesVerificationResult.getNCBColor() == null || "".equals(xrulesVerificationResult.getNCBColor())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "ncb");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        /*//=============Car======================
        paramField = "car";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Brand======================
        paramField = "car_brand";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Catagory======================
        paramField = "car_category";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Year======================
        paramField = "car_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (value.length() != 4) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "invalid_car_year");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================*/
        //=============Age ======================
        paramField = "age";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Marital Status ======================
        paramField = "marital_status";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Education level ======================
        paramField = "qualification";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Gender ======================
        paramField = "gender";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Year of work ======================
        paramField = "year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Month of work ======================
        paramField = "month";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //===================================
        //=============Occupation======================
        paramField = "occupation";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //===================Current Address Status================
        AddressDataM currentAddress = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        if (currentAddress == null) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "current_address");
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            logger.debug("Current Address Status= " + currentAddress.getStatus());
            if (currentAddress.getStatus() == null || "".equals(currentAddress.getStatus())) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "current_address_status");
                vErrors.add(errorMsg);
                resultFlag = true;
            }
        }

        //================================
        //=================Working Year Not More than Age===================
        String strWorkingYear = request.getParameter("year");
        String strWorkingMonth = request.getParameter("month");
        String strAge = request.getParameter("age");
        if (!(strWorkingYear == null || strWorkingYear.equals("")) && !(strWorkingMonth == null || strWorkingMonth.equals(""))
                && !(strAge == null || strAge.equals(""))) {
            int iworkYear = utility.stringToInt(strWorkingYear);
            int iworkMonth = utility.stringToInt(strWorkingMonth);
            int iAge = utility.stringToInt(strAge);
            if (iAge < iworkYear) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
                vErrors.add(errorMsg);
                resultFlag = true;
            }
        }
        //=============House Regis Status======================
        /*
         * paramField = "house_regis_status"; value =
         * request.getParameter(paramField); if (value == null ||
         * value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); } //=============Time
         * in Current Address====================== //time_year //time_month
         * paramField = "time_year"; value = request.getParameter(paramField);
         * if (value == null || value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); } paramField =
         * "time_month"; value = request.getParameter(paramField); if (value ==
         * null || value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */

        //=================LOAN ===============
        // Down Payment /Term Loan
        /*Vector vLoan = applicationDataM.getLoanVect();
        if (vLoan == null || vLoan.size() == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "loan");
            vErrors.add(errorMsg);
            resultFlag = true;
        }*/
        //===================Adddress /Birth Date==================
        paramField = "birth_date";
        boolean addressValid = true;
        String strBirthDate = request.getParameter(paramField);
        Date birthDate = ORIGUtility.parseThaiToEngDate(strBirthDate);
        if (birthDate != null) {
            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
            Calendar clNow = Calendar.getInstance();
            Calendar clBd = Calendar.getInstance();
            clBd.setTime(birthDate);
            int age = clNow.get(Calendar.YEAR) - clBd.get(Calendar.YEAR);
            if (clNow.get(Calendar.MONTH) < clBd.get(Calendar.MONTH)
                    || (clNow.get(Calendar.MONTH) == clBd.get(Calendar.MONTH) && clNow.get(Calendar.DAY_OF_MONTH) < clBd.get(Calendar.DAY_OF_MONTH))) {
                age = age - 1;
            }
            Vector vAddress = personalInfoDataM.getAddressVect();
            if (vAddress != null) {
                for (int i = 0; i < vAddress.size(); i++) {
                    AddressDataM addressDataM = (AddressDataM) vAddress.get(i);
                    BigDecimal resideYear = addressDataM.getResideYear();
                    if (resideYear != null) {
                        int year = resideYear.intValue();
                        int month = resideYear.movePointLeft(2).intValue() % 100;
                        if (month > 0) {
                            year++;
                        }
                        if (year != 0 && year > age) {
                            addressValid = false;
                            vErrors.add("Age must not less than reside year/month in Address Type "
                                    + cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType()));
                        }
                    }
                }
            }

        }
        //=================================
        return resultFlag;
    }

    public boolean getErrorMessageScoreCorporate(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramField = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }
        //==================Debt Burden======================
        logger.debug("xrulesVerificationResult.getDEBT_BDResult()" + xrulesVerificationResult.getDEBT_BDResult());
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN));
        if (xrulesVerificationResult.getDEBT_BDResult() == null || "".equals(xrulesVerificationResult.getDEBT_BDResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_burden");
            vErrorFields.add(paramField);
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================Existing Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER));
        if (xrulesVerificationResult.getExistCustResult() == null || "".equals(xrulesVerificationResult.getExistCustResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "existing_customer");
            vErrorFields.add(paramField);
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================DEBT AMT====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT));
        if (xrulesVerificationResult.getDebtAmountResult() == null || "".equals(xrulesVerificationResult.getDebtAmountResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt");
            vErrorFields.add(paramField);
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
/*
        //=============Car======================
        paramField = "car";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Brand======================
        paramField = "car_brand";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Catagory======================
        paramField = "car_category";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Year======================
        paramField = "car_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (value.length() != 4) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "invalid_car_year");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }*/
        //================================
        //=============Marketing Code======================
        /*
         * paramField = "pre_score_mkt_code"; value =
         * request.getParameter(paramField); if (value == null ||
         * value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */
        //================================
        //==========business age=====
        paramField = "age";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //=============Share Capital======================
        // paramField = "pre_score_share_capital";
        // value = request.getParameter(paramField);
        /*
         * if (personalInfoDataM.getCorperatedVect() == null ||
         * personalInfoDataM.getCorperatedVect().size() == 0) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, "balanceSheet");
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */
        //=============Share Capital======================
        paramField = "pre_score_share_capital";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (new BigDecimal(0).compareTo(utility.stringToBigDecimal(value)) == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField + "_moreThanZero");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //================================
        //=============Land of Owner ship======================
        paramField = "land_ownership";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //=============Cheque Returned======================
        paramField = "cheque_return";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //=============Number of Employee======================
        paramField = "no_of_employee";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //=================LOAN ===============
        // Down Payment /Term Loan
        Vector vLoan = applicationDataM.getLoanVect();
        if (vLoan == null || vLoan.size() == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "loan");
            vErrors.add(errorMsg);
            resultFlag = true;
        }
        //=============Overdue Over 60 Day ======================
        paramField = "pre_score_overdue_over_60_days";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============???????????????????????? 6 ?????======================
        paramField = "pre_score_max_time_overdue";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Number of Revolving======================
        paramField = "pre_score_number_of_revolving_loan";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Auto Mobile Hire======================
        paramField = "pre_score_number_of_automobile_hire_purchase";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============NPL======================
        paramField = "pre_score_NPL_histry_restructure";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //      =================Working Year Not More than Age===================
        String strWorkingYear = request.getParameter("year");
        String strWorkingMonth = request.getParameter("month");
        String strAge = request.getParameter("age");
        if (!(strWorkingYear == null || strWorkingYear.equals("")) && !(strWorkingMonth == null || strWorkingMonth.equals(""))
                && !(strAge == null || strAge.equals(""))) {
            int iworkYear = utility.stringToInt(strWorkingYear);
            int iworkMonth = utility.stringToInt(strWorkingMonth);
            int iAge = utility.stringToInt(strAge);
            if (iAge < iworkYear) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
                vErrors.add(errorMsg);
                resultFlag = true;
            }

        }
        //===================Adddress /Birth Date==================
        paramField = "birth_date";
        boolean addressValid = true;
        String strBirthDate = request.getParameter(paramField);
        Date birthDate = ORIGUtility.parseThaiToEngDate(strBirthDate);
        if (birthDate != null) {
            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
            Calendar clNow = Calendar.getInstance();
            Calendar clBd = Calendar.getInstance();
            clBd.setTime(birthDate);
            int age = clNow.get(Calendar.YEAR) - clBd.get(Calendar.YEAR);
            if (clNow.get(Calendar.MONTH) < clBd.get(Calendar.MONTH)
                    || (clNow.get(Calendar.MONTH) == clBd.get(Calendar.MONTH) && clNow.get(Calendar.DAY_OF_MONTH) < clBd.get(Calendar.DAY_OF_MONTH))) {
                age = age - 1;
            }
            Vector vAddress = personalInfoDataM.getAddressVect();
            if (vAddress != null) {
                for (int i = 0; i < vAddress.size(); i++) {
                    AddressDataM addressDataM = (AddressDataM) vAddress.get(i);
                    BigDecimal resideYear = addressDataM.getResideYear();
                    if (resideYear != null) {
                        int year = resideYear.intValue();
                        int month = resideYear.movePointLeft(2).intValue() % 100;
                        if (month > 0) {
                            year++;
                        }
                        if (year != 0 && year > age) {
                            addressValid = false;
                            vErrors.add("Age must not less than reside year/month in Address Type "
                                    + cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType()));
                        }
                    }
                }
            }

        }
        //=================================
        return resultFlag;
    }

    public boolean getErrorMessageScoreForeigner(HttpServletRequest request) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM applicationDataM = form.getAppForm();
        if (applicationDataM == null)
            applicationDataM = new ApplicationDataM();

        String paramField = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        ORIGUtility utility = new ORIGUtility();
        String personalType = request.getParameter("appPersonalType");//(String)
        // request.getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xrulesVerificationResult = personalInfoDataM.getXrulesVerification();
        if (xrulesVerificationResult == null) {
            xrulesVerificationResult = new XRulesVerificationResultDataM();
        }

        paramField = "identification_no";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {

            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "office_code";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "name_thai";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }

        paramField = "surname_thai";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "area_code";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "birth_date";
        if (ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        }
        paramField = "m_birth_date";
        if (!ORIGUtility.isEmptyString(request.getParameter(paramField))) {
            int age = utility.stringToInt(request.getParameter("m_age"));
            String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
            if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter(paramField)))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
                vErrors.add(errorMsg + "[Marriage]");
                vErrorFields.add(paramField);
                resultFlag = true;
            } else {
                vNotErrorFields.add(paramField);
            }
        }

        //==================Debt Burden======================
        logger.debug("xrulesVerificationResult.getDEBT_BDResult()" + xrulesVerificationResult.getDEBT_BDResult());

        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN));
        if (xrulesVerificationResult.getDEBT_BDResult() == null || "".equals(xrulesVerificationResult.getDEBT_BDResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_burden");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================Existing Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER));
        if (xrulesVerificationResult.getExistCustResult() == null || "".equals(xrulesVerificationResult.getExistCustResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "existing_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================Blacklist Customer====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.BLACKLIST));
        if (xrulesVerificationResult.getBLResult() == null || "".equals(xrulesVerificationResult.getBLResult())) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "blacklist_customer");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //====================DEBT AMT====================
        paramField = (String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT));
        if (xrulesVerificationResult.getDebtAmountResult() == null) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "debt_amt");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }

        //=============Car======================
        paramField = "car";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Brand======================
        paramField = "car_brand";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Catagory======================
        paramField = "car_category";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Car Year======================
        paramField = "car_year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else if (value.length() != 4) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "invalid_car_year");
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Age ======================
        paramField = "age";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Marital Status ======================
        paramField = "marital_status";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Education level ======================
        paramField = "qualification";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Gender ======================
        paramField = "gender";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Year of work ======================
        paramField = "year";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Month of work ======================
        paramField = "month";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================

        //===================================
        //=============Occupation======================
        paramField = "occupation";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //===================Current Address Status================
        AddressDataM currentAddress = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        if (currentAddress == null) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "current_address");
            vErrors.add(errorMsg);
            resultFlag = true;
        } else {
            logger.debug("Current Address Status= " + currentAddress.getStatus());
            if (currentAddress.getStatus() == null || "".equals(currentAddress.getStatus())) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "current_address_status");
                vErrors.add(errorMsg);
                resultFlag = true;
            }
        }

        //      =================Working Year Not More than Age===================
        String strWorkingYear = request.getParameter("year");
        String strWorkingMonth = request.getParameter("month");
        String strAge = request.getParameter("age");
        if (!(strWorkingYear == null || strWorkingYear.equals("")) && !(strWorkingMonth == null || strWorkingMonth.equals(""))
                && !(strAge == null || strAge.equals(""))) {
            int iworkYear = utility.stringToInt(strWorkingYear);
            int iworkMonth = utility.stringToInt(strWorkingMonth);
            int iAge = utility.stringToInt(strAge);
            if (iAge < iworkYear) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
                vErrors.add(errorMsg);
                resultFlag = true;
            }

        }
        //=============House Regis Status======================
        /*
         * paramField = "house_regis_status"; value =
         * request.getParameter(paramField); if (value == null ||
         * value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); } //=============Time
         * in Current Address====================== //time_year //time_month
         * paramField = "time_year"; value = request.getParameter(paramField);
         * if (value == null || value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); } paramField =
         * "time_month"; value = request.getParameter(paramField); if (value ==
         * null || value.equals("")) { errorMsg =
         * ErrorUtil.getShortErrorMessage(request, paramField);
         * vErrors.add(errorMsg); vErrorFields.add(paramField); resultFlag =
         * true; } else { vNotErrorFields.add(paramField); }
         */

        //=================LOAN ===============
        // Down Payment /Term Loan
        Vector vLoan = applicationDataM.getLoanVect();
        if (vLoan == null || vLoan.size() == 0) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, "loan");
            vErrors.add(errorMsg);
            resultFlag = true;
        }
        //=============Overdue Over 60 Day ======================
        paramField = "pre_score_overdue_over_60_days";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============???????????????????????? 6 ?????======================
        paramField = "pre_score_max_time_overdue";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Number of Revolving======================
        paramField = "pre_score_number_of_revolving_loan";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //=============Auto Mobile Hire======================
        paramField = "pre_score_number_of_automobile_hire_purchase";
        value = request.getParameter(paramField);
        if (value == null || value.equals("")) {
            errorMsg = ErrorUtil.getShortErrorMessage(request, paramField);
            vErrors.add(errorMsg);
            vErrorFields.add(paramField);
            resultFlag = true;
        } else {
            vNotErrorFields.add(paramField);
        }
        //================================
        //===================Adddress /Birth Date==================
        paramField = "birth_date";
        boolean addressValid = true;
        String strBirthDate = request.getParameter(paramField);
        Date birthDate = ORIGUtility.parseThaiToEngDate(strBirthDate);
        if (birthDate != null) {
            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
            Calendar clNow = Calendar.getInstance();
            Calendar clBd = Calendar.getInstance();
            clBd.setTime(birthDate);
            int age = clNow.get(Calendar.YEAR) - clBd.get(Calendar.YEAR);
            if (clNow.get(Calendar.MONTH) < clBd.get(Calendar.MONTH)
                    || (clNow.get(Calendar.MONTH) == clBd.get(Calendar.MONTH) && clNow.get(Calendar.DAY_OF_MONTH) < clBd.get(Calendar.DAY_OF_MONTH))) {
                age = age - 1;
            }
            Vector vAddress = personalInfoDataM.getAddressVect();
            if (vAddress != null) {
                for (int i = 0; i < vAddress.size(); i++) {
                    AddressDataM addressDataM = (AddressDataM) vAddress.get(i);
                    BigDecimal resideYear = addressDataM.getResideYear();
                    if (resideYear != null) {
                        int year = resideYear.intValue();
                        int month = resideYear.movePointLeft(2).intValue() % 100;
                        if (month > 0) {
                            year++;
                        }
                        if (year != 0 && year > age) {
                            addressValid = false;
                            vErrors.add("Age must not less than reside year/month in Address Type "
                                    + cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType()));
                        }
                    }
                }
            }

        }
        //=================================
        return resultFlag;
    }

}
