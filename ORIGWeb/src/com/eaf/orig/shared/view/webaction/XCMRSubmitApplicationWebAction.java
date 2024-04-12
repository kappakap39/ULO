/*
 * Created on Oct 30, 2007
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
package com.eaf.orig.shared.view.webaction;

import java.util.Date;

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
import com.eaf.orig.shared.control.event.ApplicationEventResponse;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: SaveApplicationWebAction
 */
public class XCMRSubmitApplicationWebAction extends WebActionHelper implements
        WebAction {
    Logger log = Logger.getLogger(XCMRSubmitApplicationWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		
		ORIGFormHandler formHandler=(ORIGFormHandler)getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM applicationDataM = formHandler.getAppForm();
		
		String username = userM.getUserName();		 
		if(ORIGUtility.isEmptyString(applicationDataM.getUwFirstId())){
			applicationDataM.setCmrExFirstId(username);
			applicationDataM.setCmrExStartDate(new Date());
		}
		
		applicationDataM.setCmrExLastId(username);
		if(ORIGUtility.isEmptyString(applicationDataM.getCreateBy())){
			applicationDataM.setCreateBy(username);
			applicationDataM.setUpdateBy(username);
		}else{
			applicationDataM.setUpdateBy(username);
		}
        
		ApplicationEvent event = new ApplicationEvent(ApplicationEvent.XCMR_SUBMIT, applicationDataM, userM);

		return event;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse arg0) {
		 
		return defaultProcessResponse(arg0);
	}

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
    	/*ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
        ORIGUtility utility = new ORIGUtility();
        
        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
        String personalSeq = getRequest().getParameter("personalSeq");
        int seq = 0;
        if(!ORIGUtility.isEmptyString(personalSeq)){
            seq = Integer.parseInt(personalSeq);
        }
        
        //Get Personal Info
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);	    		  	
        
        boolean isError = errorUtil.getMandateErrorCMR(getRequest(), personalInfoDataM.getCustomerType());
        
        if(isError){
        	return false;
        }*/
        
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
    	
        return FrontController.PAGE;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
    	return "page=XCMR_SUMMARY_SCREEN";
    }
    
    protected void doSuccess(EventResponse arg0) {
        log.debug("In doSuccess XCMRSubmitApplicationWebAction");
    	ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
    	ApplicationDataM applicationDataM = formHandler.getAppForm();
//		String decision = applicationDataM.getUwDecision();
//		String role = (String) userM.getRoles().elementAt(0);
    	try{
    		//if(applicationDataM.getIsException()){
    			ORIGUtility utility = new ORIGUtility();
    			ServiceDataM serviceDataM = utility.getServiceDataM();
        		ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
        		applicationManager.sendSMSAndEmail(applicationDataM, serviceDataM, userM);
    			//new SendEmailAndSMS(getRequest()).start();
    		//}
    		
    	}catch(Exception e){
    		log.debug("Error >> ",e);
    	}
	}
    
	protected void doFail(EventResponse arg0) {
		ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationEventResponse eventResponse = (ApplicationEventResponse) arg0;
        String errMsg = eventResponse.getMessage();
        formHandler.getFormErrors().add(errMsg);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
