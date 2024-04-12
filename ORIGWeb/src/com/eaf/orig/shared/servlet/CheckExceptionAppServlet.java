/*
 * Created on Nov 22, 2007
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
import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Weeraya
 *
 * Type: CheckExceptionAppServlet
 */
public class CheckExceptionAppServlet extends HttpServlet {
	Logger logger = Logger.getLogger(CheckExceptionAppServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CheckExceptionAppServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
	    logger.debug("<<< Start CheckExceptionAppServlet >>>");
	    try{
	    	ORIGFormHandler formHandler = (ORIGFormHandler) req.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
	    	UserDetailM userM = (UserDetailM) req.getSession().getAttribute("ORIGUser");
	    	ApplicationDataM applicationDataM = formHandler.getAppForm();
	    	LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
	    	ORIGUtility utility = new ORIGUtility();
	    	Vector guarantorVect = utility.getVectorPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_GUARANTOR);
	    	String appType = OrigConstant.HAVE_GUARANTOR;
			if(guarantorVect.size() == 0){
				appType = OrigConstant.NOT_HAVE_GUARANTOR;
			}
			VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
			PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
			BigDecimal term;
			if(("Y").equals(loanDataM.getBalloonFlag())){
				term = loanDataM.getBalloonTerm();
			}else{
				term = loanDataM.getInstallment1();
			}
			logger.debug("loanDataM.getCampaign() = "+loanDataM.getCampaign());
			logger.debug("loanDataM.getCampaignTemp() = "+loanDataM.getCampaignTemp());
			//Check Exception
			boolean isException = false;
			if(ORIGUtility.isEmptyString(applicationDataM.getXcmrOverrideBy()) || OrigConstant.ROLE_DE.equals(userM.getRoles().elementAt(0)) 
			   || !ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN.equals(applicationDataM.getXcmrDecision())){
				isException = utility.checkException(loanDataM);
			}
			logger.debug("isException = "+isException);
			//Check escalate
			StringBuffer result = new StringBuffer("");
			if(isException){
				applicationDataM.setIsException(true);
				result.append(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
			}else{
				applicationDataM.setIsException(false);
			}
	        resp.setContentType("text/xml;charset=UTF-8");
			resp.setHeader("Cache-Control", "no-cache");
			
			PrintWriter pw = resp.getWriter();
			logger.debug("returnValue = "+result.toString());
			
			pw.write(result.toString());
			pw.close();
		} catch (Exception e) {
			logger.error("Error >> ", e);
		}
	}
}
