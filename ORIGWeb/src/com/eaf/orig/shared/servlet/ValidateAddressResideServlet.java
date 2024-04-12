/*
 * Created on Mar 11, 2008
 * Created by Avalant
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
package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGMandatoryErrorUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Avalant
 * 
 * Type: ValidateAddressResideServlet
 */
public class ValidateAddressResideServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(ValidateAddressResideServlet.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse ressonse) throws ServletException, IOException {
        logger.debug("<<<<<<< Start ValidateAddressResideServlet >>>>>>>");
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        StringBuffer sb = new StringBuffer("");
        String returnValue = "";
        try {
            ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
            ORIGUtility utility = new ORIGUtility();
            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
            String userRole = (String) userM.getRoles().elementAt(0);
            //boolean isError = false;
            String personalType = (String) request.getSession().getAttribute("PersonalType");
            PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
            if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
            } else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
                personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
            }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
            }
            if (personalInfoDataM == null) {
                personalInfoDataM = new PersonalInfoDataM();
            }
            ApplicationDataM applicationDataM = formHandler.getAppForm();
            if (applicationDataM == null) {
                applicationDataM = new ApplicationDataM();
            }
            Vector vErrors = formHandler.getFormErrors();
            Vector vErrorFields = formHandler.getErrorFields();
            Vector vNotErrorFields = formHandler.getNotErrorFields();
            //get Verification
            String paramField = "birth_date";
            String errorMsg = "";
            boolean addressValid = true;
            String strBirthDate = request.getParameter(paramField);
            Date birthDate = ORIGUtility.parseThaiToEngDate(strBirthDate);
            if (birthDate!=null) {
               
                Calendar clNow = Calendar.getInstance();
                Calendar clBd = Calendar.getInstance();
                clBd.setTime(birthDate);
                int ageYear = clNow.get(Calendar.YEAR) - clBd.get(Calendar.YEAR);
                int ageMonth=clNow.get(Calendar.MONTH)-clBd.get(Calendar.MONTH);
                if(clNow.get(Calendar.MONTH)<clBd.get(Calendar.MONTH)){
                    ageYear=ageYear-1;
                    ageMonth=12+clNow.get(Calendar.MONTH)-clBd.get(Calendar.MONTH);
                 }
                Vector vAddress = personalInfoDataM.getAddressVect();
                if (vAddress != null) {
                    for (int i = 0; i < vAddress.size(); i++) {
                        AddressDataM addressDataM = (AddressDataM) vAddress.get(i);
                        BigDecimal resideYear = addressDataM.getResideYear();
                        if (resideYear != null) {
                            int year = resideYear.intValue();
                            int month = resideYear.movePointLeft(2).intValue() % 100;
                           // if (month > 0) {
                           //     year++;
                           // }
                            if (year>ageYear||(year==ageYear && month>ageMonth)) {
                                addressValid = false;
                                vErrors.add("Age must not less than reside year/month in Address Type "
                                        + cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType()));
                            }
                        }
                    }
                }
                if (!addressValid) {
                    vErrorFields.add(paramField);
                } else {
                    vNotErrorFields.add(paramField);
                }
                logger.debug("addressValid = " + addressValid);
                if (!addressValid) {
                    sb = errorUtil.getDisplayError(formHandler);
                }
            }
            //Clear Error data
            formHandler.setFormErrors(new Vector());
            formHandler.setErrorFields(new Vector());
            formHandler.setNotErrorFields(new Vector());
        } catch (Exception e) {
            logger.error("Error >>> ", e);
            if (userM == null) {
                sb = new StringBuffer("");
                sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                sb.append("<error>This page has been expired. Please close this window and login again on new window.</error>");
            } else {
                sb = new StringBuffer("");
                sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                sb.append("<error>Can not retrieve data, please contact admin.</error>");
            }
        }
        ressonse.setContentType("text/xml;charset=UTF-8");
        ressonse.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = ressonse.getWriter();
        returnValue = sb.toString();
        logger.debug("returnValue = " + returnValue);
        pw.write(returnValue);
        pw.close();
        logger.debug("==> out doPost");
    }
}
