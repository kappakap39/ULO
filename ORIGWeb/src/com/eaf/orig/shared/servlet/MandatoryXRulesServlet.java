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
import com.eaf.xrules.shared.constant.XRulesConstant;

/**
 * @author Sankom Sanpunya
 * 
 * Type: MandatoryXRulesServlet
 */
public class MandatoryXRulesServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(MandatoryXRulesServlet.class);

    /**
     *  
     */
    public MandatoryXRulesServlet() {
        super();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("<<<<<<< Start MandatoryXRulesServlet >>>>>>>");
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        StringBuffer sb = new StringBuffer("");
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        //get Verification
        ORIGMandatoryVerificationErrorUtil mandatoryPreScoreErrorUtil = ORIGMandatoryVerificationErrorUtil.getInstance();
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
    	String customer_type=personalInfoDataM.getCustomerType();
    	 
        try{
	        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();	
	        String userRole = (String) userM.getRoles().elementAt(0);
	        boolean isError = false;
	        String serviceId = request.getParameter("serviceId");
	        logger.debug("serviceId = " + serviceId);
	        //String customer_type = request.getParameter("cus_type");         
	        //logger.debug("customer_type = "+customer_type);
	        //String personalType = (String) request.getSession().getAttribute("PersonalType");
	        int intService=0;
	        try {
	              intService=Integer.parseInt(serviceId);
	        } catch (NumberFormatException e) {           
	            e.printStackTrace();
	        }
	        ORIGMandatoryVerificationErrorUtil  mandatoryVerErrorUtil=ORIGMandatoryVerificationErrorUtil.getInstance();
	        switch (intService) {
	        case XRulesConstant.ServiceID.NCB: {            
	            isError=mandatoryVerErrorUtil.getErrorMessageNCB(request,customer_type,personalType);// origMandatoryErrorUtil.getMandateErrorSaveNewApp(request,customer_type);
	            break;
	           }
	        case XRulesConstant.ServiceID.BLACKLIST: {
	            isError= mandatoryVerErrorUtil.getErrorMessageBlacklist(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.BLACKLIST_VEHICLE: {
	            isError= mandatoryVerErrorUtil.getErrorMessageBlaclistVehicle(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.EXIST_CUSTOMER: {
	            isError= mandatoryVerErrorUtil.getErrorMessageExistingCustomer(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.DEDUP: {
	            isError= mandatoryVerErrorUtil.getErrorMessageDedup(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.DUP_VEHICLE: {
	            isError= mandatoryVerErrorUtil.getErrorMessageDupVehicle(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.DEBTBURDEN: {
	            isError= mandatoryVerErrorUtil.getErrorMessageDebbd(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.FICO: {
	            isError= mandatoryVerErrorUtil.getErrorMessageFICO(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.POLICYRULES: {
	            isError= mandatoryVerErrorUtil.getErrorMessagePolicyRules(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.DEBT_AMOUNT: {
	            isError= mandatoryVerErrorUtil.getErrorMessageDebAmount(request);
	            break;
	           }
	        case XRulesConstant.ServiceID.ALL: {
	            if(  OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(personalType) ){
	                //dedup ,existing customer
	                isError=mandatoryVerErrorUtil.getErrorMessageExecuteALLGuarantor(request);
	            }else{
	//              Execute Dedup ,Dedup Vehihcle ,Existing Cust         
	                isError=mandatoryVerErrorUtil.getErrorMessageExecuteALL(request);                                           
	            }
	            break;
	           }
	        
	        }
	        //Create return output to java script
	       /* if (OrigConstant.PopupName.POPUP_ADDRESS.equals(popupType)) {
	            isError = errorUtil.getMandateErrorAddress(request, customer_type);
	        } else if (OrigConstant.PopupName.POPUP_CHANGE_NAME.equals(popupType)) {
	            isError = errorUtil.getMandateErrorChangeName(request,
	                    customer_type);
	        } else if (OrigConstant.PopupName.POPUP_FINANCE.equals(popupType)) {
	            isError = errorUtil.getMandateErrorFinance(request, customer_type);
	        } else if (OrigConstant.PopupName.POPUP_OTHER_NAME.equals(popupType)) {
	            isError = errorUtil
	                    .getMandateErrorOtherName(request, customer_type);
	        }*/
	
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
				sb.append("<xml>");
				sb.append("<error>This page has been expired. Please close this window and login again on new window.</error>");
				sb.append("</xml>");
			}else{
				sb = new StringBuffer("");
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<xml>");
				sb.append("<error>Can not retrieve data, please contact admin.</error>");
				sb.append("</xml>");
			}
		}
	    String returnValue = sb.toString();
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
