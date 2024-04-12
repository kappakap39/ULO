/*
 * Created on Dec 8, 2007
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
package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGMandatoryErrorUtil;
import com.eaf.orig.shared.utility.ORIGMandatoryVerificationErrorUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Sankom Sanpunya
 * 
 * Type: MandatoryXRulesServlet
 */
public class MandatoryPreScoreServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(MandatoryPreScoreServlet.class);

    /**
     *  
     */
    public MandatoryPreScoreServlet() {
        super();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("<<<<<<< Start MandatoryPreScoreServlet >>>>>>>");
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession()
                .getAttribute(OrigConstant.FORMHANDLER_NAME);
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute(
                "ORIGUser");
        StringBuffer sb = new StringBuffer("");
        String returnValue;
        try{
	        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
	        ORIGUtility utility = new ORIGUtility();
	
	        String userRole = (String) userM.getRoles().elementAt(0);
	        boolean isError = false;
	        //String serviceId = request.getParameter("serviceId");
	        // logger.debug("serviceId = " + serviceId);
	        
	        //String customer_type = request.getParameter("cus_type");
	        //logger.debug("customer_type = "+customer_type);
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        if (applicationDataM == null) {
	            applicationDataM = new ApplicationDataM();
	        }
	        //get Verification
	        ORIGMandatoryVerificationErrorUtil mandatoryPreScoreErrorUtil = ORIGMandatoryVerificationErrorUtil
	                .getInstance();
	        String personalType = request.getParameter("appPersonalType");//(String) request.getSession().getAttribute("PersonalType");
	    	PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	    	}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
	    		personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
			}
	    	if(personalInfoDataM == null){
	    		personalInfoDataM = new PersonalInfoDataM();		
	    	}
	        //check Debt Amt,NCB,Debtburdent
	        //
	       
	        //debt amount
	        /*
	         * if(xrulesVerificationResult.getDebtAmountResult()==null) ){
	         *  }
	         */
	
	        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL
	                .equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
	                        
	            isError =   mandatoryPreScoreErrorUtil
	                            .getErrorMessagePreScoreIndividual(request);
	        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE
	                .equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
	            isError =   mandatoryPreScoreErrorUtil
	                            .getErrorMessagePreScoreCorporate(request);
	        } else if (OrigConstant.CUSTOMER_TYPE_FOREIGNER
	                .equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
	            isError =   mandatoryPreScoreErrorUtil
	                            .getErrorMessagePreScoreForeigner(request);
	        }        
	        logger.debug("isError = " + isError);
	        if (isError) {
	            sb = errorUtil.getDisplayError(formHandler);
	        }
	
	        //Clear Error data
	        formHandler.setFormErrors(new Vector());
	        formHandler.setErrorFields(new Vector());
	        formHandler.setNotErrorFields(new Vector());
	    }catch(Exception e){
			logger.error("Error >>> ", e);
			if(userM == null){
				sb = new StringBuffer("");
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<error>This page has been expired. Please close this window and login again on new window.</error>");
			}else{
				sb = new StringBuffer("");
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<error>Can not retrieve data, please contact admin.</error>");
			}
		}
        returnValue = sb.toString();
        //Create response
        resp.setContentType("text/xml;charset=UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = resp.getWriter();
        logger.debug("returnValue = " + returnValue);
        pw.write(returnValue);
        pw.close();
        logger.debug("==> out doPost");
    }
}
