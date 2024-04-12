/*
 * Created on Dec 4, 2007
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

import org.apache.log4j.Logger;

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
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigScoringUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Sankom
 * 
 * Type: CalculateScoringWebAction
 */
public class CalculatePreScoringWebAction extends WebActionHelper implements WebAction {
    private static Logger log = (Logger) Logger.getLogger(CalculatePreScoringWebAction.class);
    private boolean requireModelRequest = false;
    private boolean startFlowFlag=false;
    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        if (null == userM) {
            userM = new UserDetailM();
        }
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        String userName = userM.getUserName();
        int applicationEvent = ApplicationEvent.PRESCORE_SAVE;
        String appJobState = applicationDataM.getJobState();
        log.debug("Job State:" + appJobState);
        log.debug("Appolciation No:" + applicationDataM.getApplicationNo());
        ApplicationEvent event = new ApplicationEvent(applicationEvent, applicationDataM, userM);
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
        log.debug("CalculatePreScoringWebAction-->preModelRequest");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
        UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        String personalType = getRequest().getParameter("appPersonalType");//(String) getRequest().getSession().getAttribute("PersonalType");
        /*
         * PersonalInfoDataM personalInfoDataM;
         * if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
         * personalInfoDataM = (PersonalInfoDataM)
         * getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA"); }else{
         * personalInfoDataM =
         * utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT); }
         * if(personalInfoDataM == null){ personalInfoDataM = new
         * PersonalInfoDataM(); personalInfoDataM.setPersonalType(personalType);
         * applicationDataM.getPersonalInfoVect().add(personalInfoDataM); }
         */
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
        requireModelRequest=true;
        getRequest().getSession().setAttribute("scoringType", OrigConstant.Scoring.SCORING_TYPE_PRESCORE);
        OrigScoringUtility origScoring = new OrigScoringUtility();
        String scoringResult = origScoring.calcuateApplicationPreScoreing(applicationDataM, ORIGUser);
        getRequest().getSession().setAttribute("scoringResult", scoringResult);
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

    /* (non-Javadoc)
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
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#doSuccess(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    protected void doSuccess(EventResponse erp) {
        log.debug("In doSuccess SaveApplicationWebAction");
    	ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	ApplicationDataM applicaitonDataM=(ApplicationDataM)erp.getEncapData();
    	log.debug("doSuccess appstatus "+applicaitonDataM.getApplicationStatus());
    	String jobState = applicaitonDataM.getJobState();    	 		 				        
    	if(startFlowFlag){
    		log.debug("Set application Status Draft");
    		try{	     
	    	   ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
	    	   applicaitonDataM = applicationManager.loadApplicationDataMForNCBFirst(applicaitonDataM, applicaitonDataM.getAppRecordID(), null, null); 	    
    		}catch(Exception e){
    			log.error("Load Application error",e); 
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
