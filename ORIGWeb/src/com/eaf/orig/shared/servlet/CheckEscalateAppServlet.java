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
import java.net.URLDecoder;
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
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Weeraya
 * 
 * Type: CheckApproveAppServlet
 */
public class CheckEscalateAppServlet extends HttpServlet {
    Logger logger = Logger.getLogger(CheckEscalateAppServlet.class);
    private StringBuffer result;
    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public CheckEscalateAppServlet() {
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
        /**
         * 1.Check Policy Rules Not Fail 1.1 Pass Check Approval Pass data(2)
         * 1.2 Else Check Approval Fail data(2) 2. if Pass Approval Check Policy
         * rules 2.1 if policy pass check Campaign (4) 2.2 if policy fail send
         * to XUW 3. if Approval fail Escarate 4. Check Campaign 4.1 if Campaign
         * pass flow 4.2 if campaign fail send to XCMR
         */
        logger.debug("<<< Start CheckExceptionAppServlet >>>");
        String applicationDecison = req.getParameter("applicatonDecision");
        logger.debug("applicationDecison "+applicationDecison);
		applicationDecison = URLDecoder.decode(applicationDecison,"UTF-8");
        
        ORIGFormHandler formHandler = (ORIGFormHandler) req.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        
        if (ORIGWFConstant.ApplicationDecision.APPROVE.equals(applicationDecison)) {
            if(OrigConstant.ORG_AUTO_LOAN.equals(ORIGFormUtil.getOrgID(applicationDataM.getBusinessClassId()))){
                validateApprove(req, resp);
            }else{
                validatePass(req, resp);
            }
            //Escarate
            //Policy
            //Campaign
        } else if (ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(applicationDecison)) {
            if(OrigConstant.ORG_AUTO_LOAN.equals(ORIGFormUtil.getOrgID(applicationDataM.getBusinessClassId()))){
                validateCampaignException(req, resp);
            }else{
                validatePass(req, resp);
            }
            //Escarate
            //Policy
        } else if (OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION.equals(applicationDecison)) {
            if(OrigConstant.ORG_AUTO_LOAN.equals(ORIGFormUtil.getOrgID(applicationDataM.getBusinessClassId()))){
                validatePolicyException(req, resp);
            }else{
                validatePass(req, resp);
            }
            //Escarate
        }
    }

    public void validateApprove(HttpServletRequest req, HttpServletResponse resp) {
        try {
            logger.debug("validate applicationDecison Approved");
            ORIGFormHandler formHandler = (ORIGFormHandler) req.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
            UserDetailM userM = (UserDetailM) req.getSession().getAttribute("ORIGUser");
            ApplicationDataM applicationDataM = formHandler.getAppForm();
            LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);

            boolean policyGroupPass = false;
            boolean approvalPass = false;
            boolean policyException = false;

            ORIGUtility utility = new ORIGUtility();
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
            String car_type = req.getParameter("car_type");
            logger.debug("car_type = " + car_type);
             //= new StringBuffer("");
            //=========================================
            String policyVersion = applicationDataM.getPolicyVersion();
            if (policyVersion == null || "".equals(policyVersion)) {
                OrigPolicyVersionDataM origPoicy = utility.getPolicyVersion(new java.sql.Date(applicationDataM.getCreateDate().getTime()));
                if (origPoicy == null) {
                    logger.fatal("NO Policy Version Match");
                    result=new StringBuffer("<error>"+"NO Policy Version Match"+"</error>");
                } else {
                    applicationDataM.setPolicyVersion(origPoicy.getPolicyVersion());
                    policyVersion = origPoicy.getPolicyVersion();
                }
            }
            String groupName = utility.getApprovalGroup(userM.getUserName());
            logger.debug(" Group Name " + groupName);
            //======================Check Policy Not Fail===================
            policyGroupPass =utility.checkPolicyPass(policyVersion, groupName, personalInfoDataM.getXrulesVerification());
            //=======================Check Approval           
            approvalPass = this.checkApproval(userM,groupName,car_type, applicationDataM, loanDataM, personalInfoDataM, policyVersion, policyGroupPass, appType, term);
            //===================================================================================
            //Check Policy Exception
            if (approvalPass) {//Check Policy Excepiton
                
                if (!(OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(applicationDataM.getXuwDecision()) && !ORIGUtility.isEmptyString(applicationDataM
                        .getXuwOverrideBy()))) {                                        
                     policyException = utility.checkPolicyException(policyVersion,  personalInfoDataM.getXrulesVerification());                    
                    if (policyException) {
                        //set Exception
                        logger.debug(" Policy exception  Hit");
                        result = new StringBuffer("<data>"+OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION+"</data>");
                        applicationDataM.setXuwPolicyException(OrigConstant.POLICY_INCORECT);
                    }

                }
            }

            //============================================================
            //Check Campaign Exception
            logger.debug(" Policy exception "+policyException);
            if (approvalPass&&!policyException) { //Check Campaign
                if (!(ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN.equals(applicationDataM.getXcmrDecision())) ) {
                    boolean isException = utility.checkException((LoanDataM) applicationDataM.getLoanVect().elementAt(0));
                    logger.debug("isException = " + isException); //Check
                    // escalate
                    if (isException) {
                        applicationDataM.setIsException(true);
                        result = new StringBuffer("<data>"+ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION+"</data>");
                    }else{
                    result= new StringBuffer("<data>pass</data>");    
                    }
                }else{
                    result= new StringBuffer("<data>pass</data>");    
                }
            }
            resp.setContentType("text/xml;charset=UTF-8");
            resp.setHeader("Cache-Control", "no-cache");

            PrintWriter pw = resp.getWriter();
           // logger.debug("returnValue = " + result.toString());
          //  pw.write(result.toString());             
            StringBuffer  printResult=new  StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            printResult.append("<xml>");
            if(result!=null){
            printResult.append(result.toString());
            }
            printResult.append("</xml>") ;     
            logger.debug("returnValue = " + printResult.toString());
            pw.write(printResult.toString());
            pw.close();
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
    }

    public void validateCampaignException(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("validate applicationDecison Campaign Exception");
        try {
            ORIGFormHandler formHandler = (ORIGFormHandler) req.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
            UserDetailM userM = (UserDetailM) req.getSession().getAttribute("ORIGUser");
            ApplicationDataM applicationDataM = formHandler.getAppForm();
            LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
            boolean policyGroupPass = false;
            boolean approvalPass = false;
            boolean policyException = false;

            ORIGUtility utility = new ORIGUtility();
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
            String car_type = req.getParameter("car_type");
            logger.debug("car_type = " + car_type);
             //= new StringBuffer("");
            //=========================================
            String policyVersion = applicationDataM.getPolicyVersion();
            if (policyVersion == null || "".equals(policyVersion)) {
                OrigPolicyVersionDataM origPoicy = utility.getPolicyVersion(new java.sql.Date(applicationDataM.getCreateDate().getTime()));
                if (origPoicy == null) {
                    logger.fatal("NO Policy Version Match");
                    result=new StringBuffer("<error>"+"NO Policy Version Match"+"</error>");
                } else {
                    applicationDataM.setPolicyVersion(origPoicy.getPolicyVersion());
                    policyVersion = origPoicy.getPolicyVersion();
                }
            }
            String groupName = utility.getApprovalGroup(userM.getUserName());
            logger.debug(" Group Name " + groupName);
            //======================Check Policy Not Fail===================
            policyGroupPass =utility.checkPolicyPass(policyVersion, groupName, personalInfoDataM.getXrulesVerification());
            //=======================Check Approval           
            approvalPass = this.checkApproval(userM,groupName,car_type, applicationDataM, loanDataM, personalInfoDataM, policyVersion, policyGroupPass, appType, term);
            //===================================================================================
            //Check Policy Exception
            if (approvalPass) {//Check Policy Excepiton
                if (!(OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(applicationDataM.getXuwDecision()) && !ORIGUtility.isEmptyString(applicationDataM
                        .getXuwOverrideBy()))) {
                     policyException = utility.checkPolicyException(policyVersion,  personalInfoDataM.getXrulesVerification());           
                    if (policyException) {
                        //set Exception
                        logger.debug("Policy Exception "+policyException);
                        result = new StringBuffer("<data>"+OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION+"</data>");
                        applicationDataM.setXuwPolicyException(OrigConstant.POLICY_INCORECT);
                    }

                }
            }
            //============================================================
            //Check Campaign Exception

            /*
             * if (policyGroupMatch) { //Check Campaign if
             * (ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN.equals(applicationDataM.getXcmrDecision()) &&
             * !ORIGUtility.isEmptyString(applicationDataM.getXcmrOverrideBy())) {
             * boolean isException = utility.checkException((LoanDataM)
             * applicationDataM.getLoanVect().elementAt(0));
             * logger.debug("isException = " + isException); //Check // escalate
             * if (isException) {
             * 
             * applicationDataM.setIsException(true); result = new
             * StringBuffer(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION); } } }
             */
            resp.setContentType("text/xml;charset=UTF-8");
            resp.setHeader("Cache-Control", "no-cache");
            PrintWriter pw = resp.getWriter();
            StringBuffer  printResult=new  StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            printResult.append("<xml>");
            if(result!=null){
            printResult.append(result.toString());
            }
            printResult.append("</xml>") ;     
            logger.debug("returnValue = " + printResult.toString());
            pw.write(printResult.toString());
            pw.close();
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
    }

    public void validatePolicyException(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("validate applicationDecison Policy Exception");
        try {
            ORIGFormHandler formHandler = (ORIGFormHandler) req.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
            UserDetailM userM = (UserDetailM) req.getSession().getAttribute("ORIGUser");
            ApplicationDataM applicationDataM = formHandler.getAppForm();
            LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
            boolean policyGroupPass = false;
            boolean approvalPass = false;
            boolean policyException = false;

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
            String car_type = req.getParameter("car_type");
            logger.debug("car_type = " + car_type);
             //= new StringBuffer("");
            //=========================================
            String policyVersion = applicationDataM.getPolicyVersion();
            if (policyVersion == null || "".equals(policyVersion)) {
                OrigPolicyVersionDataM origPoicy = utility.getPolicyVersion(new java.sql.Date(applicationDataM.getCreateDate().getTime()));
                if (origPoicy == null) {
                    logger.fatal("NO Policy Version Match");
                    result=new StringBuffer("<error>"+"NO Policy Version Match"+"</error>");
                } else {
                    applicationDataM.setPolicyVersion(origPoicy.getPolicyVersion());
                    policyVersion = origPoicy.getPolicyVersion();
                }
            }
            String groupName = utility.getApprovalGroup(userM.getUserName());
            logger.debug(" Group Name " + groupName);
            //======================Check Policy Not Fail===================
            policyGroupPass =utility.checkPolicyPass(policyVersion, groupName, personalInfoDataM.getXrulesVerification());
            //=======================Check Approval           
            approvalPass = this.checkApproval(userM,groupName,car_type, applicationDataM, loanDataM, personalInfoDataM, policyVersion, policyGroupPass, appType, term);
            //===================================================================================
            //===================================================================================
            //Check Policy Exception
            /*
             * if (approvalPass) {//Check Policy Excepiton if
             * (OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(applicationDataM.getXuwDecision()) &&
             * !ORIGUtility.isEmptyString(applicationDataM.getXuwOverrideBy())) {
             * policyGroupMatch = utility.checkPolicyException(policyVersion,
             * groupName, personalInfoDataM.getXrulesVerification()); if
             * (!policyGroupMatch) { //set Exception
             * 
             * result = new
             * StringBuffer(OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION);
             * applicationDataM.setXuwPolicyException(OrigConstant.POLICY_INCORECT); } } }
             * 
             * //============================================================
             * //Check Campaign Exception /* if (policyGroupMatch) { //Check
             * Campaign if
             * (ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN.equals(applicationDataM.getXcmrDecision()) &&
             * !ORIGUtility.isEmptyString(applicationDataM.getXcmrOverrideBy())) {
             * boolean isException = utility.checkException((LoanDataM)
             * applicationDataM.getLoanVect().elementAt(0));
             * logger.debug("isException = " + isException); //Check // escalate
             * if (isException) {
             * 
             * applicationDataM.setIsException(true); result = new
             * StringBuffer(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION); } } }
             */
            resp.setContentType("text/xml;charset=UTF-8");
            resp.setHeader("Cache-Control", "no-cache");
            PrintWriter pw = resp.getWriter();
            StringBuffer  printResult=new  StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            printResult.append("<xml>");
            if(result!=null){
            printResult.append(result.toString());
            }
            printResult.append("</xml>") ;     
            logger.debug("returnValue = " + printResult.toString());
            pw.write(printResult.toString());
            pw.close();
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
    }

    //private boolean checkPolicyMatch() {
    //    return true;
    //}

    private boolean checkApproval(UserDetailM userM,String groupName, String car_type, ApplicationDataM applicationDataM, LoanDataM loanDataM,
            PersonalInfoDataM personalInfoDataM, String policyVersion, boolean policyNotFail, String appType, BigDecimal term) {
        // Data============================
        ORIGUtility utility = new ORIGUtility();
        OrigXRulesUtil xrulesUtility = OrigXRulesUtil.getInstance();
        
        boolean approvalPass = false;
        boolean validateFileds=true;
        if(ORIGUtility.isEmptyString(groupName)){            
            result=new StringBuffer("<error>"+"Please Contact Admin for Assign User Group"+"</error>");
            validateFileds=false;
        }else if(ORIGUtility.isEmptyString(car_type)){
            result=new StringBuffer("<error>"+"Please verify Car(New Car or Used Car) for get Escalate Group"+"</error>");
            validateFileds=false;
        }else if(ORIGUtility.isEmptyString(applicationDataM.getScorringResult())){
            result=new StringBuffer("<error>"+"Please (Re)Excute Application Score"+"</error>");
            validateFileds=false;
        }else if(ORIGUtility.isEmptyString(policyVersion)){
            result=new StringBuffer("<error>"+"No Policy match Application Please Contact admin !"+"</error>");
            validateFileds=false;
        }
        
        if  (validateFileds) {
           
                Vector escalateGroups = null;
                BigDecimal downPayment = new BigDecimal(0);
                BigDecimal percentDownPayment = new BigDecimal(0);
                BigDecimal creditApproval = utility.getFinalCreditAppoval(applicationDataM);
                if (OrigConstant.CAR_TYPE_NEW.equals(car_type)) {
                    //Get User Group
                    downPayment = loanDataM.getCostOfDownPayment();
                    percentDownPayment = utility.calculatePercent(loanDataM.getCostOfCarPrice(), downPayment);
                    //logger.debug("percentDownPayment before Check
                    // Authority" + percentDownPayment);
                    approvalPass = utility.checkApprovalAuthority(policyVersion, groupName, policyNotFail, personalInfoDataM.getCustomerType(), car_type,
                            loanDataM.getLoanType(), applicationDataM.getScorringResult(), creditApproval, appType, term, percentDownPayment);
                    //logger.debug("percentDownPayment after Check
                    // Authority " + percentDownPayment);
                } else {

                    try {
                        downPayment = (new BigDecimal(100)).subtract(loanDataM.getCostOfFinancialAmt().divide(loanDataM.getAppraisalPrice(), 10, 0).multiply(
                                new BigDecimal(100)));
                        logger.debug("Car type Use Down payment param=" + downPayment);
                        percentDownPayment = downPayment;
                    } catch (RuntimeException e1) {
                        logger.error("", e1);
                    }
                    approvalPass = utility.checkApprovalAuthority(policyVersion, groupName, policyNotFail, personalInfoDataM.getCustomerType(), car_type,
                            loanDataM.getLoanType(), applicationDataM.getScorringResult(), creditApproval, appType, term, downPayment);

                }

                //==================================================
                logger.debug(" Appoval Pass " + approvalPass);
                if (!approvalPass) {
                    logger.debug(" Appoval Fail  " + approvalPass);
                    logger.debug("percentDownPayment " + percentDownPayment);
                    result=new StringBuffer("<data>"+ORIGWFConstant.ApplicationDecision.ESCALATE+"</data>");
                    
                    Vector groupVect =utility.getEscerateGroup(  policyVersion, personalInfoDataM.getCustomerType(), car_type,
                            loanDataM.getLoanType(), applicationDataM.getScorringResult(), creditApproval,   appType,
                              term,   percentDownPayment,personalInfoDataM.getXrulesVerification());
                    
                result.append("<group>");
                     for(int i=0;i<groupVect.size();i++){
                         CacheDataM  cacheDataM=(CacheDataM)groupVect.get(i);
                        result.append("<group_name>"+cacheDataM.getCode()+"</group_name>" );            
                     }
                result.append("</group>"); 
                } else {
                    result=new StringBuffer("<data>pass</data>");
                }
            }  
        
        return approvalPass;
    }
    
    private void validatePass(HttpServletRequest req, HttpServletResponse resp){
        try{
	        StringBuffer result= new StringBuffer("<data>pass</data>");  
	        resp.setContentType("text/xml;charset=UTF-8");
	        resp.setHeader("Cache-Control", "no-cache");
	
	        PrintWriter pw = resp.getWriter();             
	        StringBuffer  printResult=new  StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        printResult.append("<xml>");
	        if(result!=null){
	        printResult.append(result.toString());
	        }
	        printResult.append("</xml>") ;     
	        logger.debug("returnValue = " + printResult.toString());
	        pw.write(printResult.toString());
	        pw.close();
        }catch (Exception e) {
            logger.error("Error >> ", e);
        }
    }
}
