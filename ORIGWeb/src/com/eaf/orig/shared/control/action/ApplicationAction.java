/*
 * Created on Oct 4, 2007
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
package com.eaf.orig.shared.control.action;

import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.control.event.ApplicationEvent;
import com.eaf.orig.shared.control.event.ApplicationEventResponse;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author weeraya
 * 
 * Type: ApplicationAction
 */
public class ApplicationAction implements Action {
    Logger logger = Logger.getLogger(ApplicationAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.control.action.Action#perform(com.eaf.j2ee.pattern.control.event.Event)
     */
    public EventResponse perform(Event ev) {
        ApplicationEventResponse eventResponse = null;
        ApplicationEvent event = (ApplicationEvent) ev;
        int eventType = event.getEventType();
        Object object = event.getObject();
        UserDetailM userDetailM = event.getUserM();
        ORIGApplicationManager manager = ORIGEJBService.getApplicationManager();
        Object returnObject = null;
        String providerUrlWF = (String) LoadXML.getServiceURL().get("ORIGWF");
        String jndiWF = (String) LoadXML.getServiceJNDI().get("ORIGWF");
        String providerUrlEXT = null;
        String jndiEXT = null;
        String providerUrlIMG = (String) LoadXML.getServiceURL().get("IMGWF");
        String jndiIMG = (String) LoadXML.getServiceJNDI().get("IMGWF");

        ORIGUtility utility = new ORIGUtility();
        ApplicationDataM applicationDataM = new ApplicationDataM();
        if (ApplicationEvent.DE_SUBMIT_ALL != eventType) {        
            applicationDataM = (ApplicationDataM) object;
    	}
        //String appNo;
        String appType;
        PersonalInfoDataM personalInfoDataM;
        Vector guarantorVect;
//        boolean result;
        ServiceDataM serviceDataM;
        boolean chkCar = true;
        if (ORIGUtility.isEmptyString(applicationDataM.getCreateBy())) {
            applicationDataM.setCreateBy(userDetailM.getUserName());
        } else {
            applicationDataM.setUpdateBy(userDetailM.getUserName());
        }
        try {
            switch (eventType) {
            case ApplicationEvent.DE_SAVE:
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
                chkCar = manager.deSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT, providerUrlIMG, jndiIMG);
                /*
                 * if(applicationDataM.getApplicationNo() == null ||
                 * ("").equals(applicationDataM.getApplicationNo())){
                 * applicationDataM.setApplicationNo(appNo); }
                 */
                break;
            case ApplicationEvent.DE_SUBMIT_ALL:
                Vector vResults = (Vector)object;
                for (int i=0;i < vResults.size();i++) {                
                    ApplicationDataM appM = (ApplicationDataM)vResults.get(i);
                    logger.debug("appM.getAppRecordID()====>"+appM.getAppRecordID());
                    if (ORIGUtility.isEmptyString(appM.getCreateBy())) {
                        appM.setCreateBy(userDetailM.getUserName());
                    } else {
                        appM.setUpdateBy(userDetailM.getUserName());
                    }
                    personalInfoDataM = utility.getPersonalInfoByType(appM, OrigConstant.PERSONAL_TYPE_APPLICANT);
	                guarantorVect = utility.getVectorPersonalInfoByType(appM, OrigConstant.PERSONAL_TYPE_GUARANTOR);
	                appType = OrigConstant.HAVE_GUARANTOR;
	                if (guarantorVect.size() == 0) {
	                    appType = OrigConstant.NOT_HAVE_GUARANTOR;
	                }
	                //Check Exception
	                if (appM.getDeDecision() == null || ("").equals(appM.getDeDecision())) {                   
	                        Vector reasonVect = appM.getReasonVect();               
	                        appM.setDeDecision(ORIGWFConstant.ApplicationDecision.SUBMIT);
	                        appM.setIsException(false);
	                        reasonVect = utility.removeReasonByRole(reasonVect, OrigConstant.ROLE_UW);                 
	                }
	                if (ORIGUtility.isEmptyString(appM.getPriority())) {
	                    appM.setPriority(OrigConstant.Priority.NORMAL);
	                }
	                //Set Original data for loan information
	                if(appM.getLoanVect()!=null && appM.getLoanVect().size()>0){
	                    LoanDataM loanDataM = (LoanDataM) appM.getLoanVect().elementAt(0);
	                    if (loanDataM.getOrigFinance() == null) {
	                        loanDataM.setOrigDownPayment(loanDataM.getCostOfDownPayment());
	                        loanDataM.setOrigFinance(loanDataM.getCostOfFinancialAmt());
	                        loanDataM.setOrigInstallmentAmt(loanDataM.getCostOfInstallment1());
	                        if ("Y".equals(loanDataM.getBalloonFlag())) {
	                            loanDataM.setOrigInstallmentTerm(loanDataM.getBalloonTerm());
	                        } else {
	                            loanDataM.setOrigInstallmentTerm(loanDataM.getInstallment1());
	                        }
	                    } 
	                }
	                serviceDataM = getServiceDataM();
	                chkCar = manager.deSubmitApplication(appM, userDetailM, providerUrlWF, jndiWF, personalInfoDataM.getCustomerType(), appType,
	                        providerUrlEXT, jndiEXT, providerUrlIMG, jndiIMG, serviceDataM);
	                /*
	                 * if(applicationDataM.getApplicationNo() == null ||
	                 * ("").equals(applicationDataM.getApplicationNo())){
	                 * applicationDataM.setApplicationNo(appNo); }
	                 */
	                appM.setAppDecision(appM.getDeDecision());
                }    
                break;            
            case ApplicationEvent.DE_SUBMIT:
                personalInfoDataM = utility.getPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_APPLICANT);
                guarantorVect = utility.getVectorPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_GUARANTOR);
                appType = OrigConstant.HAVE_GUARANTOR;
                if (guarantorVect.size() == 0) {
                    appType = OrigConstant.NOT_HAVE_GUARANTOR;
                }
                //Check Exception
                if (applicationDataM.getDeDecision() == null || ("").equals(applicationDataM.getDeDecision())) {                   
                        Vector reasonVect = applicationDataM.getReasonVect();               
                        applicationDataM.setDeDecision(ORIGWFConstant.ApplicationDecision.SUBMIT);
                        applicationDataM.setIsException(false);
                        reasonVect = utility.removeReasonByRole(reasonVect, OrigConstant.ROLE_UW);                 
                }
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                //Set Original data for loan information
                if(applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
                    LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
                    if (loanDataM.getOrigFinance() == null) {
                        loanDataM.setOrigDownPayment(loanDataM.getCostOfDownPayment());
                        loanDataM.setOrigFinance(loanDataM.getCostOfFinancialAmt());
                        loanDataM.setOrigInstallmentAmt(loanDataM.getCostOfInstallment1());
                        if ("Y".equals(loanDataM.getBalloonFlag())) {
                            loanDataM.setOrigInstallmentTerm(loanDataM.getBalloonTerm());
                        } else {
                            loanDataM.setOrigInstallmentTerm(loanDataM.getInstallment1());
                        }
                    } 
                }
                serviceDataM = getServiceDataM();
                chkCar = manager.deSubmitApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, personalInfoDataM.getCustomerType(), appType,
                        providerUrlEXT, jndiEXT, providerUrlIMG, jndiIMG, serviceDataM);
                /*
                 * if(applicationDataM.getApplicationNo() == null ||
                 * ("").equals(applicationDataM.getApplicationNo())){
                 * applicationDataM.setApplicationNo(appNo); }
                 */
                applicationDataM.setAppDecision(applicationDataM.getDeDecision());
                break;
            case ApplicationEvent.CMR_SAVE:
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                manager.cmrSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                /*
                 * if(applicationDataM.getApplicationNo() == null ||
                 * ("").equals(applicationDataM.getApplicationNo())){
                 * applicationDataM.setApplicationNo(appNo); }
                 */
                break;
            case ApplicationEvent.CMR_SUBMIT:
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                manager.cmrSubmitApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                /*
                 * if(applicationDataM.getApplicationNo() == null ||
                 * ("").equals(applicationDataM.getApplicationNo())){
                 * applicationDataM.setApplicationNo(appNo); }
                 */
                break;
            case ApplicationEvent.UW_SAVE:
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
                appType = OrigConstant.HAVE_GUARANTOR;
                guarantorVect = utility.getVectorPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_GUARANTOR);
                if (guarantorVect.size() == 0) {
                    appType = OrigConstant.NOT_HAVE_GUARANTOR;
                }
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                personalInfoDataM = utility.getPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_APPLICANT);

                if(applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
	                LoanDataM loanDataM3 = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
	                if (loanDataM3.getInternalCkecker() == null || "".equals(loanDataM3.getInternalCkecker())) {
	                    String interanChk = utility.getInternalChecker(userDetailM.getUserName());
	                    if (interanChk != null && !"".equals(interanChk)) {
	                        loanDataM3.setInternalCkecker(interanChk);
	                    }
	                }
                }
                chkCar = manager.uwSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT, personalInfoDataM
                        .getCustomerType(), appType);
                break;
            case ApplicationEvent.UW_SUBMIT:
                String uwDecision = applicationDataM.getUwDecision();
                
                logger.debug("ApplicationEvent.UW_SUBMIT >> "+uwDecision);
                
                applicationDataM.setAppDecision(uwDecision);                
                appType = OrigConstant.HAVE_GUARANTOR;
                guarantorVect = utility.getVectorPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_GUARANTOR);
                if (guarantorVect.size() == 0) {
                    appType = OrigConstant.NOT_HAVE_GUARANTOR;
                }
                /*
                 * if(applicationDataM.getIsException()){
                 * applicationDataM.setUwDecision(ORIGWFConstant.ApplicationDecision.SUBMIT_WITH_EXCEPTION); }
                 */
                /*
                 * if(ORIGWFConstant.ApplicationDecision.CONDITIONAL_APPROVE.equals(applicationDataM.getUwDecision())){
                 * //Check Exception result = utility.checkException((LoanDataM)
                 * applicationDataM.getLoanVect().elementAt(0)); if(result){
                 * applicationDataM.setIsException(true);
                 * applicationDataM.setUwDecision(ORIGWFConstant.ApplicationDecision.SUBMIT_WITH_EXCEPTION);
                 * }else{ applicationDataM.setIsException(false);
                 * applicationDataM.setUwDecision(applicationDataM.getUwDecision()); } }
                 */

                //Clear Reason for next step
                //if (applicationDataM.getIsException()) {
                if (ORIGWFConstant.ApplicationDecision.APPROVE.equals(uwDecision)) {
                    Vector reasonVect = applicationDataM.getReasonVect();
                    reasonVect = utility.removeReasonByRole(reasonVect, OrigConstant.ROLE_PD);
                    applicationDataM.setPdDecision(null);
                    applicationDataM.setPdDecisionReason(null);
                } else if (OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION.equals(uwDecision)) {
                    Vector reasonVect = applicationDataM.getReasonVect();
                    reasonVect = utility.removeReasonByRole(reasonVect, OrigConstant.ROLE_XUW);
                    applicationDataM.setXuwDecision(null);
                    applicationDataM.setXuwOverrideBy(null);
                    applicationDataM.setXuwOverrideDate(null);
                    applicationDataM.setIsException(false);
                    applicationDataM.setApplicationStatus(ORIGWFConstant.ApplicationStatus.NEW);
                    applicationDataM.setJobState(OrigConstant.JobState.XUW_NEW_STATE);
                    applicationDataM.setXuwPolicyException(OrigConstant.ORIG_FLAG_Y);
                    //applicationDataM
                    //		.setAppDecision(OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION);
                }else if( ORIGWFConstant.ApplicationDecision.ESCALATE.equals(uwDecision) ) {
                    applicationDataM.setIsException(false);
                    applicationDataM.setEscalateBy(userDetailM.getUserName());
                    applicationDataM.setEscalateDate(new Date(System.currentTimeMillis()));
                }
                else if (applicationDataM.getIsException() || ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(uwDecision)) {
                    Vector reasonVect = applicationDataM.getReasonVect();
                    reasonVect = utility.removeReasonByRole(reasonVect, OrigConstant.ROLE_XCMR);
                    applicationDataM.setXcmrDecision(null);
                    applicationDataM.setXcmrOverrideBy(null);
                    applicationDataM.setXcmrOverrideDate(null);
                    applicationDataM.setIsException(true);
                    applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
                } 
                //new
               
                personalInfoDataM = utility.getPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_APPLICANT);
                serviceDataM = getServiceDataM();

                //Set vehicle status for rejet and cancel
                if ((ORIGWFConstant.ApplicationDecision.REJECT.equals(uwDecision)) && ("Y").equals(applicationDataM.getDrawDownFlag())) {
                    VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
                    vehicleDataM.setDrawDownStatus(ORIGWFConstant.ApplicationStatus.REJECTED);
                } else if ((ORIGWFConstant.ApplicationDecision.CANCEL.equals(uwDecision)) && ("Y").equals(applicationDataM.getDrawDownFlag())) {
                    VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
                    vehicleDataM.setDrawDownStatus(ORIGWFConstant.ApplicationStatus.CANCELLED);
                }
                logger.debug("applicationDataM.getUwDecision() = " + applicationDataM.getUwDecision());

                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                String creditApproval = utility.getCreditApproval(userDetailM.getUserName());
                if(applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
                    LoanDataM loanDataM2 = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
                    loanDataM2.setCreditApproval(creditApproval);
                }
                
                chkCar = manager.uwSubmitApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT, personalInfoDataM
                        .getCustomerType(), appType, serviceDataM);
                logger.debug("applicationDataM.getUwDecision() = " + applicationDataM.getUwDecision());
                break;
            case ApplicationEvent.UW_REOPEN:
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                if (applicationDataM.getIsException()) {
                    applicationDataM.setUwDecision(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
                    applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
                }

                Vector appReasonVect = applicationDataM.getReasonVect();
                //appReasonVect = utility.removeReasonByRole(appReasonVect,
                // OrigConstant.ROLE_UW);
                //applicationDataM.setUwDecision(null);

                chkCar = manager.uwReopenApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.PD_SAVE:
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
                manager.pdSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.PD_SUBMIT:
                //Check complete document
                applicationDataM.setIsCompleteDoc(utility.checkCompleteDoc(applicationDataM.getDocumentCheckListDataM()));
                if (ORIGUtility.isEmptyString(applicationDataM.getPdDecision())) {
                    if (applicationDataM.getIsCompleteDoc()) {
                        applicationDataM.setPdDecision(ORIGWFConstant.ApplicationDecision.COMPLETE_DOC);
                    } else {
                        applicationDataM.setPdDecision(ORIGWFConstant.ApplicationDecision.INCOMPLETE_DOC);
                    }
                }
                if (ORIGWFConstant.ApplicationDecision.SEND_BACK_TO_DE.equals(applicationDataM.getPdDecision())) {
                    Vector reasonVect = applicationDataM.getReasonVect();
                    reasonVect = utility.removeReasonByRole(reasonVect, OrigConstant.ROLE_DE);
                    applicationDataM.setDeDecision(null);
                }
                String pdDecision = applicationDataM.getPdDecision();
                //Set vehicle status for rejet and cancel
                if ((ORIGWFConstant.ApplicationDecision.WITHDRAW.equals(pdDecision)) && ("Y").equals(applicationDataM.getDrawDownFlag())) {
                    VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
                    vehicleDataM.setDrawDownStatus(ORIGWFConstant.ApplicationStatus.WITHDREW);
                }
                applicationDataM.setAppDecision(applicationDataM.getPdDecision());
                manager.pdSubmitApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.PROPOSAL_SAVE:
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                String interanChkProposalSave = utility.getInternalChecker(userDetailM.getUserName());
                if (applicationDataM.getLoanVect() == null) {
                    applicationDataM.setLoanVect(new Vector());
                }
                LoanDataM loanDataMProposalSave;
                if (applicationDataM.getLoanVect().size() == 0) {
                    loanDataMProposalSave = new LoanDataM();
                    applicationDataM.getLoanVect().add(loanDataMProposalSave);
                } else {
                    loanDataMProposalSave = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
                }
                loanDataMProposalSave.setInternalCkecker(interanChkProposalSave);
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
                manager.proposalSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.PROPOSAL_SUBMIT:
                if (ORIGUtility.isEmptyString(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                String interanChkProposalSubmit = utility.getInternalChecker(userDetailM.getUserName());
                if (applicationDataM.getLoanVect() == null) {
                    applicationDataM.setLoanVect(new Vector());
                }
                LoanDataM loanDataMProposalSubmit;
                if (applicationDataM.getLoanVect().size() == 0) {
                    loanDataMProposalSubmit = new LoanDataM();
                    applicationDataM.getLoanVect().add(loanDataMProposalSubmit);
                } else {
                    loanDataMProposalSubmit = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
                }
                if (loanDataMProposalSubmit.getInternalCkecker() == null || "".equals(loanDataMProposalSubmit.getInternalCkecker())) {
                    loanDataMProposalSubmit.setInternalCkecker(interanChkProposalSubmit);
                }
                String creditApprovalProposal = utility.getCreditApproval(userDetailM.getUserName());
                loanDataMProposalSubmit.setCreditApproval(creditApprovalProposal);
                applicationDataM.setAppDecision(applicationDataM.getUwDecision());
                manager.proposalSubmitApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.XCMR_SAVE:
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
                manager.xcmrSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.XCMR_SUBMIT:
                if (ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_DE.equals(applicationDataM.getXcmrDecision())) {
                    Vector reasonVect = applicationDataM.getReasonVect();
                    reasonVect = utility.removeReasonByRole(reasonVect, OrigConstant.ROLE_DE);
                    applicationDataM.setDeDecision(null);
                } else if (ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN.equals(applicationDataM.getXcmrDecision())) {

                    applicationDataM.setEscalateTo(null);
                }
                if (ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_UW.equals(applicationDataM.getXcmrDecision())) {

                    Vector reasonVect = applicationDataM.getReasonVect();
                    applicationDataM.setXcmrOverrideBy("");
                    applicationDataM.setXcmrOverrideDate(null);
                    //20080409 Sankom not remove uw Decision reason
                    //reasonVect = utility.removeReasonByRole(reasonVect,
                    //	OrigConstant.ROLE_UW);
                    //applicationDataM.setUwDecision(null);
                    //applicationDataM.setUwDecisionReason(null);
                    applicationDataM.setEscalateTo(null);
                }
                applicationDataM.setAppDecision(applicationDataM.getXcmrDecision());
                manager.xcmrSubmitApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.NCB_SAVE:
                if (applicationDataM.getPriority() == null || "".equals(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                returnObject = manager.ncbSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT, providerUrlIMG,
                        jndiIMG);
                break;
            case ApplicationEvent.PRESCORE_SAVE:
                if (applicationDataM.getPriority() == null || "".equals(applicationDataM.getPriority())) {
                    applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                }
                returnObject = manager.preScoreSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT, providerUrlIMG,
                        jndiIMG);
                break;
            case ApplicationEvent.PD_REVERSE:
                //if (applicationDataM.getPriority() == null
                //		|| "".equals(applicationDataM.getPriority())) {
                //	applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                //}
                returnObject = manager.pdReverseApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.MANUAL_CANCEL:
                //if (applicationDataM.getPriority() == null
                //		|| "".equals(applicationDataM.getPriority())) {
                //	applicationDataM.setPriority(OrigConstant.Priority.NORMAL);
                //}
                appType = OrigConstant.HAVE_GUARANTOR;
                guarantorVect = utility.getVectorPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_GUARANTOR);
                if (guarantorVect.size() == 0) {
                    appType = OrigConstant.NOT_HAVE_GUARANTOR;
                }
                personalInfoDataM = utility.getPersonalInfoByType((ApplicationDataM) object, OrigConstant.PERSONAL_TYPE_APPLICANT);
                serviceDataM = getServiceDataM();
                chkCar = manager.manualCancelApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT, personalInfoDataM
                        .getCustomerType(), appType, serviceDataM);
                // applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.AUTO_CANCEL);
                //logger.debug("applicationDataM.getUwDecision() = "
                //		+ applicationDataM.getUwDecision());
                break;
            //----------XUW----------
            case ApplicationEvent.XUW_SAVE:
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
                manager.xuwSaveApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;
            case ApplicationEvent.XUW_SUBMIT:
                applicationDataM.setApplicationStatus(ORIGWFConstant.ApplicationStatus.NEW);
                applicationDataM.setJobState(ORIGWFConstant.JobState.UW_NEW_STATE);
                if (OrigConstant.ApplicationDecision.NOT_OVERIDED_POLICY.equals(applicationDataM.getXuwDecision())) {
                    applicationDataM.setXuwPolicyException(OrigConstant.POLICY_NOT_OVERRIDED);
                    applicationDataM.setEscalateTo(null);
                    applicationDataM.setXuwOverrideBy(null);
                    applicationDataM.setXuwOverrideDate(null);
                } else if (OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(applicationDataM.getXuwDecision())) {
                    applicationDataM.setXuwPolicyException(OrigConstant.POLICY_OVERRIDED);
                    //applicationDataM.setXuwOverrideBy(userDetailM.getUserName());
                    // applicationDataM.setXuwOverrideDate(new
                    // Timestamp(System.currentTimeMillis()));
                    applicationDataM.setEscalateTo(null);
                }
                applicationDataM.setAppDecision(applicationDataM.getXuwDecision());
                manager.xuwSubmitApplication(applicationDataM, userDetailM, providerUrlWF, jndiWF, providerUrlEXT, jndiEXT);
                break;

            default:
                break;
            }
        } catch (Exception e) {
            logger.error("ApplicationAction.perform error >>", e);
            String errMsg = "Operation fail,please contact admin";
            Object encapData = null;
            if (eventResponse != null)
                encapData = eventResponse.getEncapData();
            return new ApplicationEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
        }
//        Comment By Rawi Songchaisin Not Car.
//        if (!chkCar) {
//            logger.debug("<< Car is used >>");
//            String errMsg = "Please select new Car.";
//            Object encapData = null;
//            if (eventResponse != null)
//                encapData = eventResponse.getEncapData();
//            return new ApplicationEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
//        }
        return new ApplicationEventResponse(event.getEventType(), EventResponseHelper.SUCCESS, "", returnObject);
    }

    private ServiceDataM getServiceDataM() {
        ORIGUtility utility = new ORIGUtility();
        ServiceDataM serviceDataM = new ServiceDataM();
        serviceDataM.setSmsHost(utility.getGeneralParamByCode("ORIG_SMS_HOST"));
        serviceDataM.setSmsPort(utility.getGeneralParamByCode("ORIG_SMS_PORT"));
        serviceDataM.setSmsSender(utility.getGeneralParamByCode("ORIG_SENDER_SMS"));
        serviceDataM.setEmailHost(utility.getGeneralParamByCode("ORIG_EMAIL_HOST"));
        serviceDataM.setEmailPort(utility.getGeneralParamByCode("ORIG_EMAIL_PORT"));
        serviceDataM.setEmailSender(utility.getGeneralParamByCode("ORIG_SENDER_EMAIL"));

        return serviceDataM;
    }

}
