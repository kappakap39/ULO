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
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author weeraya
 *
 * Type: SaveApplicationWebAction
 */
public class UWSubmitApplicationWebAction extends WebActionHelper implements
        WebAction {
	
	Logger logger = Logger.getLogger(UWSubmitApplicationWebAction.class); 
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
		String userName = userM.getUserName();
		if(ORIGUtility.isEmptyString(applicationDataM.getUwFirstId())){
			applicationDataM.setUwFirstId(userName);
			applicationDataM.setUwStartDate(new Date());
		}
		
		applicationDataM.setUwLastId(userName);
		if(ORIGWFConstant.ApplicationDecision.APPROVE.equals(applicationDataM.getUwDecision())){
			applicationDataM.setUwEndDate(new Date());
		}
		
		String searchType = (String)getRequest().getSession().getAttribute("searchType");
		int eventType = ApplicationEvent.UW_SUBMIT;
		if("Reopen".equals(searchType)){
			eventType = ApplicationEvent.UW_REOPEN;
			applicationDataM.setPdDecision("");
		}
		if(ORIGUtility.isEmptyString(applicationDataM.getCreateBy())){
			applicationDataM.setCreateBy(userName);
			applicationDataM.setUpdateBy(userName);
		}else{
			applicationDataM.setUpdateBy(userName);
		}
		ApplicationEvent event = new ApplicationEvent(eventType, applicationDataM, userM);

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
    	
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
    	ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	ApplicationDataM applicationDataM = formHandler.getAppForm();
		String orgId = ORIGFormUtil.getOrgID(applicationDataM.getBusinessClassId());
		if (OrigConstant.ORG_CREDIT_CARD.equalsIgnoreCase(orgId)){
		    return "page=UW_CREDIT_SUMMARY_SCREEN";
		}else if (OrigConstant.ORG_PLOAN.equalsIgnoreCase(orgId)){
		    return "page=UW_PLOAN_SUMMARY_SCREEN";
		}else if (OrigConstant.ORG_MORTGAGE.equalsIgnoreCase(orgId)){
		    return "page=UW_MORTGAGE_SUMMARY_SCREEN";
		}else{
		    return "page=UW_SUMMARY_SCREEN";
		}
    }
    
    protected void doSuccess(EventResponse arg0) {
    	logger.debug("In doSuccess UWSubmitApplicationWebAction");
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
    		logger.debug("Error >> ",e);
    	}
	}
    
	protected void doFail(EventResponse arg0) {
		ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        String errMsg = arg0.getMessage();
        formHandler.getFormErrors().add(errMsg);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
