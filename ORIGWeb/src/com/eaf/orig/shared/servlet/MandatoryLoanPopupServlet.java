/*
 * Created on Dec 5, 2007
 * Created by Weeraya
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
 * @author Weeraya
 *
 * Type: MandatoryPopupServlet
 */
public class MandatoryLoanPopupServlet extends HttpServlet implements Servlet{
	Logger logger = Logger.getLogger(MandatoryLoanPopupServlet.class);
	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
		
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("<<<<<<< Start MandatoryLoanPopupServlet >>>>>>>");
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
        ORIGUtility utility = new ORIGUtility();
        
        String userRole = (String) userM.getRoles().elementAt(0);
        boolean isError = false;
        
        /*Enumeration names = request.getParameterNames();
        while(names.hasMoreElements()){
        	String name = (String)names.nextElement();
        	System.out.println(name + " ->" + request.getParameter(name));
        }*/
        
        //String customer_type = request.getParameter("cus_type");
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        //get Verification
        ORIGMandatoryVerificationErrorUtil mandatoryPreScoreErrorUtil = ORIGMandatoryVerificationErrorUtil
                .getInstance();
        String personalType = (String) request.getSession().getAttribute("PersonalType");
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
    	String customer_type=personalInfoDataM.getCustomerType();
        String loanType = request.getParameter("loanType");
        String haveVat = request.getParameter("haveVat");
        String haveBalloon = request.getParameter("haveBalloon");
        String calulateFlag = request.getParameter("calulateFlag");   
        logger.debug("customer_type = "+customer_type);
        logger.debug("loanType = "+loanType);
        logger.debug("haveVat = "+haveVat);
        logger.debug("haveBalloon = "+haveBalloon);
        StringBuffer sb = new StringBuffer("");
        
        //Create return output to java script
        if("Y".equals(calulateFlag)){
        	isError = errorUtil.getMandateErrorLoanForCalulate(request, customer_type, loanType, haveVat, haveBalloon);
        }else{
        	isError = errorUtil.getMandateErrorLoan(request, customer_type, loanType, haveVat, haveBalloon);
        } 
        
        logger.debug("isError = "+isError);
        if (isError) {
			sb = errorUtil.getDisplayError(formHandler);
		}
        
        //Clear Error data
		formHandler.setFormErrors(new Vector());
		formHandler.setErrorFields(new Vector());
		formHandler.setNotErrorFields(new Vector());
		String returnValue = sb.toString();
		
		//Create response
		resp.setContentType("text/xml;charset=UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		
		PrintWriter pw = resp.getWriter();	
		logger.debug("returnValue = "+returnValue);
		pw.write(returnValue);
		pw.close();
		logger.debug("==> out doPost");
	}
}
