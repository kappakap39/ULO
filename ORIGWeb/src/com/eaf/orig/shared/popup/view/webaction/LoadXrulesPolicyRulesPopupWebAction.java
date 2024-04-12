/*
 * Created on Nov 13, 2007
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

/**
 * @author Sankom
 *
 * Type: LoadXrulesPolicyRulesPopupWebAction
 */
public class LoadXrulesPolicyRulesPopupWebAction extends WebActionHelper
        implements WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(LoadXrulesPolicyRulesPopupWebAction.class);
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
        log.debug("LoadXrulesPolicyRulesPopupWebAction-->preModelRequest");
        log.debug("Execute LoadXrulesPolicyRulesPopup  Webation");
        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
        String personalTypeParam=getRequest().getParameter("appPersonalType");                
        if(personalTypeParam!=null&&!"".equals(personalTypeParam)&&!personalTypeParam.equals(personalType)){
            personalType=personalTypeParam;
            getRequest().getSession().setAttribute("PersonalType",personalTypeParam);
        }
       /* ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
    	if(applicationDataM == null){
    		applicationDataM = new ApplicationDataM();
    	}
    	ORIGUtility utility = new ORIGUtility();
    	String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
    	PersonalInfoDataM personalInfoDataM;
    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
    	}else{
    		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
    	}
    	if(personalInfoDataM == null){
    		personalInfoDataM = new PersonalInfoDataM();
    		personalInfoDataM.setPersonalType(personalType);
    		applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
    	}
    	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
        if(vehicleDataM==null){
        vehicleDataM=new VehicleDataM();
        applicationDataM.setVehicleDataM(vehicleDataM);
        } 
    	if(applicationDataM.getJobState()==null){
     	   applicationDataM.setJobState("ST0200");
    	}        	     	    	
    	int serviceID=XRulesConstant.ServiceID.POLICYRULES;
    	log.debug("Call Service "+serviceID);
    	OrigXRulesUtil xrulesUtil=OrigXRulesUtil.getInstance();
    	XRulesDataM xruleDataM=null;
    	log.debug("Thai FirstName-->"+personalInfoDataM.getThaiFirstName());
    	log.debug("Thai LastName-->"+personalInfoDataM.getThaiLastName());
    	log.debug("ID -->"+personalInfoDataM.getIdNo());        	
    	xruleDataM=xrulesUtil.getXRulesDecision(applicationDataM,ORIGUser,serviceID,personalInfoDataM);    	
    	String xrulesExecuteResult="";    	
    	XRulesVerificationResultDataM xRulesVerification=personalInfoDataM.getXrulesVerification();    	     	 
    	xrulesExecuteResult=xrulesUtil.getXRulesVerificationRusult(xRulesVerification,serviceID);
    	String strXrulesService=String.valueOf(serviceID);    	
    	String txtResultName=getRequest().getParameter("txtResultName");
    	String txtButtonName=getRequest().getParameter("txtButtonName");
    	getRequest().getSession().setAttribute("txtResultName",txtResultName);
    	getRequest().getSession().setAttribute("txtButtonName",txtButtonName);*/    	        
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
