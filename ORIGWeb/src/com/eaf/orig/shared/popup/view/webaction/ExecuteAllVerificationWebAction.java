/*
 * Created on Dec 6, 2007
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


import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 *
 * Type: ExecuteAllVerificationWebAction
 */
public class ExecuteAllVerificationWebAction extends WebActionHelper implements
        WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(ExecuteVerificationWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {      
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        log.debug("ExecuteAllVerificationWebAction-->preModelRequest");         
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
    	if(applicationDataM == null){
    		applicationDataM = new ApplicationDataM();
    	}
    	ORIGUtility utility = new ORIGUtility();
    	String personalType= getRequest().getParameter("appPersonalType");//(String) getRequest().getSession().getAttribute("PersonalType");
    	PersonalInfoDataM personalInfoDataM;
    	log.debug("Personal Type -->"+personalType);
    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		}else{
    		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
    	}
    	if(personalInfoDataM == null){
    		personalInfoDataM = new PersonalInfoDataM();
    		//personalInfoDataM.setPersonalType(personalType);
    		//applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
    	}  
    	if(applicationDataM.getJobState()==null){
     	   applicationDataM.setJobState("ST0200");
    	}        	     	
    	OrigXRulesUtil xrulesUtil=OrigXRulesUtil.getInstance();
    	XRulesDataM xruleDataM=null;
    	log.debug("Application No-->"+applicationDataM.getApplicationNo());
    	log.debug("Thai FirstName-->"+personalInfoDataM.getThaiFirstName());
    	log.debug("Thai LastName-->"+personalInfoDataM.getThaiLastName());
    	log.debug("ID -->"+personalInfoDataM.getIdNo());    	 
    	
    	String xrulesExecuteResult="";    
    	XRulesVerificationResultDataM xRulesVerification=null;
    	//XRulesVerificationResultDataM xRulesVerification=personalInfoDataM.getXrulesVerification();    	     	 
    	//xrulesExecuteResult=xrulesUtil.getXRulesVerificationRusult(xRulesVerification,serviceID);
    	HashMap hExecuteResult=new HashMap();
    	//Dedup    	
    	 xruleDataM=xrulesUtil.getXRulesDecision(applicationDataM,ORIGUser,XRulesConstant.ServiceID.DEDUP,personalInfoDataM);
    	 xRulesVerification=personalInfoDataM.getXrulesVerification();
       	 xrulesExecuteResult=xrulesUtil.getXRulesVerificationRusult(xRulesVerification,XRulesConstant.ServiceID.DEDUP);
    	 hExecuteResult.put(String.valueOf(XRulesConstant.ServiceID.DEDUP) ,xrulesExecuteResult);
    	 log.debug("Dedup Result "+xrulesExecuteResult );
    	 if(! OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(personalInfoDataM.getPersonalType())){
    	//Dedup Vehicle
    	 xruleDataM=xrulesUtil.getXRulesDecision(applicationDataM,ORIGUser,XRulesConstant.ServiceID.DUP_VEHICLE,personalInfoDataM);
    	 xRulesVerification=personalInfoDataM.getXrulesVerification();
       	 xrulesExecuteResult=xrulesUtil.getXRulesVerificationRusult(xRulesVerification,XRulesConstant.ServiceID.DUP_VEHICLE);
    	 hExecuteResult.put(String.valueOf(XRulesConstant.ServiceID.DUP_VEHICLE) ,xrulesExecuteResult);
    	 log.debug("Dedup Vehicle  Result "+xrulesExecuteResult );
    	 }
    	//existing customer 
    	 xruleDataM=xrulesUtil.getXRulesDecision(applicationDataM,ORIGUser,XRulesConstant.ServiceID.EXIST_CUSTOMER,personalInfoDataM);
    	 xRulesVerification=personalInfoDataM.getXrulesVerification();
       	 xrulesExecuteResult=xrulesUtil.getXRulesVerificationRusult(xRulesVerification,XRulesConstant.ServiceID.EXIST_CUSTOMER);
    	 hExecuteResult.put(String.valueOf(XRulesConstant.ServiceID.EXIST_CUSTOMER) ,xrulesExecuteResult);
    	 log.debug("Existing customer Result "+xrulesExecuteResult );
    	 getRequest().getSession().setAttribute("hExecResult",hExecuteResult);    	    	     	     	    	      	           
       return true;          
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {        
        return FrontController.PAGE;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
