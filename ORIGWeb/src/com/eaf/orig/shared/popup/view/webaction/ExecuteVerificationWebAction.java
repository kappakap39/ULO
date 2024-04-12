/*
 * Created on Nov 7, 2007
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
package com.eaf.orig.shared.popup.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.control.event.ApplicationEvent;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.orig.wf.shared.ORIGWFConstant;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: ExecuteVerificationWebAction
 */
public class ExecuteVerificationWebAction extends WebActionHelper implements
        WebAction {
    private static Logger log = (Logger) Logger
            .getLogger(ExecuteVerificationWebAction.class);
    private int serviceID = 0;
    private boolean startFlowFlag=false;
    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    private boolean requireModelRequest = false;

    public Event toEvent() {
        UserDetailM userM = (UserDetailM) getRequest().getSession()
                .getAttribute("ORIGUser");
        if (null == userM) {
            userM = new UserDetailM();
        }        
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession()
        .getAttribute("ORIGForm");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        String userName = userM.getUserName();
        int applicationEvent = ApplicationEvent.NCB_SAVE;
 
        String appJobState=applicationDataM.getJobState();
        log.debug("Job State:"+appJobState);
        log.debug("Appolciation No:"+applicationDataM.getApplicationNo());
      
        ApplicationEvent event = new ApplicationEvent(applicationEvent,
                applicationDataM, userM);
        return event;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        return requireModelRequest;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        return defaultProcessResponse(response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        log.debug("ExecuteVerificationWebAction-->preModelRequest");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession()
                .getAttribute("ORIGForm");
        UserDetailM ORIGUser = (UserDetailM) getRequest().getSession()
                .getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        String personalType =  getRequest().getParameter("appPersonalType");//(String) getRequest().getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        log.debug("Personal Type -->" + personalType);
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(
                    true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(ORIGForm
                    .getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
           // personalInfoDataM.setPersonalType(personalType);
           // applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
        }
        if (applicationDataM.getJobState() == null) {
            applicationDataM.setJobState("ST0200");
        }
        String strXrulesService = getRequest().getParameter(
                "xrulseExecuteService");
        try {
            serviceID = Integer.parseInt(strXrulesService.trim());
        } catch (Exception ex) {
            ex.printStackTrace();
        }      
        if (serviceID == XRulesConstant.ServiceID.NCB) {            
            if (applicationDataM.getApplicationNo() == null
                    || "".equals(applicationDataM.getApplicationNo())) {
                try {
                    log.debug("Application No is null Geneerate applciationNo");
                    //get Application no
                    String applicationNo = ORIGEJBService
                            .getApplicationManager().generateApplicationNo(
                                    applicationDataM);
                    log.debug("Application No = "+applicationNo);
                    applicationDataM.setApplicationNo(applicationNo);
                    applicationDataM.setNcbGenAppNoFlag(true);     
                    startFlowFlag=true;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        log.debug("Call Service " + serviceID);
        OrigXRulesUtil xrulesUtil = OrigXRulesUtil.getInstance();
        XRulesDataM xruleDataM = null;
        log.debug("Application No-->" + applicationDataM.getApplicationNo());
        log.debug("Thai FirstName-->" + personalInfoDataM.getThaiFirstName());
        log.debug("Thai LastName-->" + personalInfoDataM.getThaiLastName());
        log.debug("ID -->" + personalInfoDataM.getIdNo());
        if (serviceID != 0) {
            xruleDataM = xrulesUtil.getXRulesDecision(applicationDataM,
                    ORIGUser, serviceID, personalInfoDataM);
        } else {
            log.debug("Invalid Service Id");
        }
        String xrulesExecuteResult = "";
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM
                .getXrulesVerification();
        xrulesExecuteResult = xrulesUtil.getXRulesVerificationRusult(
                xRulesVerification, serviceID);
        String debtAmountAdjust=xrulesUtil.getVerificationAdjustResult( xRulesVerification, serviceID,personalInfoDataM);
        String openPopup = "Y";
        if (serviceID == XRulesConstant.ServiceID.DEBTBURDEN ||serviceID == XRulesConstant.ServiceID.DEBT_AMOUNT) { 
            openPopup = "N";
            
        }
       String xrulesNCBColor=xRulesVerification.getNCBColor();
        if (serviceID == XRulesConstant.ServiceID.NCB) {
            requireModelRequest = true;
            if(xrulesNCBColor==null||"".equals(xrulesNCBColor)){            	
            }else{
            	xrulesExecuteResult="";
            }
            if( !(OrigConstant.NCBcolor.BLACK.equalsIgnoreCase(xrulesNCBColor)||OrigConstant.NCBcolor.RED.equalsIgnoreCase(xrulesNCBColor)||OrigConstant.NCBcolor.ORANGE.equalsIgnoreCase(xrulesNCBColor)||OrigConstant.NCBcolor.GREEN.equalsIgnoreCase(xrulesNCBColor))){
                openPopup = "N";
            }
            xRulesVerification.setVNCBAdjust(new Vector());
        }    
        log.debug("newApplicationDataM.getIsReExcuteAppScoreFlag() ="+applicationDataM.getIsReExcuteAppScoreFlag());
        log.debug("newApplicationDataM.getIsReExcuteDebtAmtFlag) ="+applicationDataM.getIsReExcuteDebtAmtFlag());
         if(xRulesVerification.getDebtAmountODInterestFlag()!=null){
          getRequest().getSession().setAttribute("debtAmtODInterestFlag", xRulesVerification.getDebtAmountODInterestFlag());
         }
        getRequest().getSession().setAttribute("execResult",
                xrulesExecuteResult);
        getRequest().getSession().setAttribute("xrulseExecuteService",
                strXrulesService);
      
        log.debug("Execute XruleVerification  Xrules Reuslt ="
                + xrulesExecuteResult);
        log.debug("Execute XruleVerification  NCB Color " + xrulesNCBColor);
        getRequest().getSession()
                .setAttribute("xrulesNCBColor", xrulesNCBColor);
        getRequest().getSession()
        .setAttribute("execResultAdjust",debtAmountAdjust);
        getRequest().getSession().setAttribute("openPopup", openPopup);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.PAGE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#defaultProcessResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean defaultProcessResponse(EventResponse response) {
        return super.defaultProcessResponse(response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#doFail(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    protected void doFail(EventResponse erp) {
    	ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        String errMsg = erp.getMessage();
        if(ORIGUtility.isEmptyString(errMsg)){
        	errMsg = "Operation fail,please contact admin";
        }
        formHandler.getFormErrors().add(errMsg);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#doSuccess(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    protected void doSuccess(EventResponse erp) {
    	log.debug("In doSuccess SaveApplicationWebAction");
    	ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	ApplicationDataM applicaitonDataM=(ApplicationDataM)erp.getEncapData();
    	log.debug("doSuccess appstatus "+applicaitonDataM.getApplicationStatus());
    	String jobState = applicaitonDataM.getJobState();
    	if(  startFlowFlag ){
    		log.debug("Set application Status Draft");
    		try{
	    		if (serviceID == XRulesConstant.ServiceID.NCB) {
	    			ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
	    			
	    		    UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	    		    
	    		    WorkflowResponse wfresp = applicationManager.claimApplication(applicaitonDataM.getAppRecordID(), "", "", userM, "", "", "");

	    			
	    			applicaitonDataM = applicationManager.loadApplicationDataMForNCBFirst(applicaitonDataM, applicaitonDataM.getAppRecordID(), null, null);
	    			//log.debug("formHandler.getAppForm().getVehicleDataM().getVehicleID() = "+formHandler.getAppForm().getVehicleDataM().getVehicleID());
	    			//log.debug("applicaitonDataM.getVehicleDataM().getVehicleID() = "+applicaitonDataM.getVehicleDataM().getVehicleID());
	    		    
	    			applicaitonDataM.setJobID(wfresp.getJobId());
	    			applicaitonDataM.setPtID(wfresp.getPtid());
	    		
	    		}
    		}catch(Exception e){
    			log.error("Load Application error",e);
				//errMsg = "Load application error : " + e.getMessage();
    		}
    		applicaitonDataM.setApplicationStatus(ORIGWFConstant.ApplicationStatus.DRAFT);
    		applicaitonDataM.setJobState(jobState);
    		formHandler.setAppForm(applicaitonDataM);
    	}
    	
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
