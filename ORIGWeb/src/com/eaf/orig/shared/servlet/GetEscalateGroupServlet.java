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

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Weeraya
 * 
 * Type: GetEscalateGroupServlet
 */
public class GetEscalateGroupServlet extends HttpServlet {
    Logger logger = Logger.getLogger(GetEscalateGroupServlet.class);

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public GetEscalateGroupServlet() {
        super();
    }

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0,
     *      HttpServletResponse arg1)
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0,
     *      HttpServletResponse arg1)
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.debug("<<< Start CheckEscalateAppServlet >>>");
        try {
            boolean policyPass = false;
            boolean approvalPass = false;
            boolean policyGroupMatch = false;
            StringBuffer result = new StringBuffer();
            ORIGFormHandler formHandler = (ORIGFormHandler) req.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
            UserDetailM userM = (UserDetailM) req.getSession().getAttribute("ORIGUser");
            ApplicationDataM applicationDataM = formHandler.getAppForm();
            if(applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
                LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
            
	            ORIGUtility utility = new ORIGUtility();
	            OrigXRulesUtil xrulesUtility = OrigXRulesUtil.getInstance();
	            Vector guarantorVect = utility.getVectorPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_GUARANTOR);
	            String appType = OrigConstant.HAVE_GUARANTOR;
	            if (guarantorVect.size() == 0) {
	                appType = OrigConstant.NOT_HAVE_GUARANTOR;
	            }
	          
	            PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
	            BigDecimal term;
	            if (("Y").equals(loanDataM.getBalloonFlag())) {
	                term = loanDataM.getBalloonTerm();
	            } else {
	                term = loanDataM.getInstallment1();
	            }
	            
	            
	            // Pattama do for KBank Demo only //Start
	            //String car_type = req.getParameter("car_type");
	            String car_type = "N";
	            // Pattama End
	            logger.debug("car_type = " + car_type);
	            //Check escalate
	            //=========================================
	            String policyVersion = applicationDataM.getPolicyVersion();
	            if (policyVersion == null || "".equals(policyVersion)) {
	                OrigPolicyVersionDataM origPoicy = utility.getPolicyVersion(new java.sql.Date(applicationDataM.getCreateDate().getTime()));
	                if (origPoicy == null) {
	                    logger.fatal("NO Policy Version Match");
	                    result=new StringBuffer("<error>"+"NO Policy Version Match"+"</error>");
	                } else {
	                    applicationDataM.setPolicyVersion(origPoicy.getPolicyVersion());
	                }
	            }
	            String groupName = utility.getApprovalGroup(userM.getUserName());
	            logger.debug(" Group Name " + groupName);
	            //======================Check Policy Not Fail===================
	         //   policyNotFail = utility.checkAppPolicyPass(applicationDataM);
	            policyPass =utility.checkPolicyPass(policyVersion, groupName, personalInfoDataM.getXrulesVerification()); 
	            
	            //=======================Check Approval
	            // Data============================
	            //String groupName = utility.getApprovalGroup(userM.getUserName());
	           // if (groupName != null && !"".equals(groupName)) {
	           // if(groupName != null && !"".equals(groupName)){            
	              //  result.append("Please Contact Admin for Assign User Group");
	               // validateFiled=false;
	            //}else 
	            boolean validateFileds=true;
	            if(ORIGUtility.isEmptyString(car_type)){
	                result=new StringBuffer("<error>"+"Please verify Car(New Car or Used Car) for get Escalate Group"+"</error>");
	                validateFileds=false;
	            }else if(ORIGUtility.isEmptyString(applicationDataM.getScorringResult())){
	                result=new StringBuffer("<error>"+"Please (Re)Excute Application Score"+"</error>");
	                validateFileds=false;
	            }else if(ORIGUtility.isEmptyString(policyVersion)){
	                result=new StringBuffer("<error>"+"No Policy match Application Please Contact admin !"+"</error>");
	                validateFileds=false;
	            }
	                if (validateFileds) {
	                    Vector escalateGroups = null;
	                    BigDecimal downPayment = new BigDecimal(0);
	                    BigDecimal percentDownPayment = new BigDecimal(0);
	                    BigDecimal totalExposure = xrulesUtility.getTotalExposure(personalInfoDataM.getXrulesVerification(), loanDataM);
	                    if (OrigConstant.CAR_TYPE_NEW.equals(car_type)) { 
	 
	                        downPayment=loanDataM.getCostOfDownPayment();
	                        percentDownPayment = utility.calculatePercent(loanDataM.getCostOfCarPrice(), downPayment);
	                    } else {
	                       
	                        try {
	                            downPayment = (new BigDecimal(100)).subtract(loanDataM.getCostOfFinancialAmt().divide(loanDataM.getAppraisalPrice(), 10, 0)
	                                    .multiply(new BigDecimal(100)));
	                            percentDownPayment = downPayment;
	                            logger.debug("Car type Use Down payment param=" + downPayment);
	                        } catch (RuntimeException e1) {
	                            e1.printStackTrace();
	                        }                                                  
	                    }          
	                    //==================================================     
	                    result=new StringBuffer("<data>"+ORIGWFConstant.ApplicationDecision.ESCALATE+"</data>");
	                    //    Vector groupVect =utility.getEscerateGroup(  policyVersion, personalInfoDataM.getCustomerType(), car_type,
	                    //            loanDataM.getLoanType(), applicationDataM.getScorringResult(), totalExposure,   appType,
	                    //              term,   percentDownPayment,personalInfoDataM.getXrulesVerification());
	                       //Pattama do for KBank Demo only //Start
	                    	totalExposure = new BigDecimal(500000);
	                    	term = new BigDecimal(72);
	                    	car_type = "N"; 
	                    	
	                        Vector groupVect =utility.getEscerateGroup(  policyVersion, personalInfoDataM.getCustomerType(), car_type,
	                                "01", applicationDataM.getScorringResult(), totalExposure,   appType,
	                                term,   new BigDecimal(10), personalInfoDataM.getXrulesVerification());
	                        //Pattama End
	                    result.append("<group>");
	                         for(int i=0;i<groupVect.size();i++){
	                             CacheDataM  cacheDataM=(CacheDataM)groupVect.get(i);
	                            result.append("<groupName>"+cacheDataM.getCode()+"</groupName>" );            
	                         }
	                    result.append("</group>"); 
	                }// else {
	                 //   result.append("Please verify Car(New Car or Used Car) for get Escalate Group");
	                //}
	            //}
	            resp.setContentType("text/xml;charset=UTF-8");
	        	resp.setHeader("Cache-Control", "no-cache");
	            PrintWriter pw = resp.getWriter();                         
	            StringBuffer printResult=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	            printResult.append("<xml>");
	            printResult.append(result.toString());
	            printResult.append("</xml>") ;     
	            logger.debug("returnValue = " + printResult.toString());
	            pw.write(printResult.toString());
	            pw.close();
            }
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
    }
}
