/*
 * Created on Nov 23, 2007
 * Created by Administrator
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

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.img.wf.service.ejb.IMGWFServiceManager;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.service.ejb.ORIGWFServiceManager;

/**
 * @author Administrator
 *
 * Type: CancelApplicationWebAction
 */
public class CancelApplicationWebAction extends WebActionHelper implements
		WebAction {
	Logger logger = Logger.getLogger(CancelApplicationWebAction.class);
	
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
		try{
			ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
			
//			String aiid = formHandler.getAppForm().getAiid();
//			logger.debug("Aiid->" + aiid);
			String apprecordId = formHandler.getAppForm().getAppRecordID();
			
			logger.debug("apprecordId->" + apprecordId);
			
			if(!ORIGUtility.isEmptyString(apprecordId)){
				
				String requestID = formHandler.getAppForm().getRequestID();
				if(requestID != null && !("").equals(requestID)){
//					Remove WPS
//					String providerUrlIMG = (String)LoadXML.getServiceURL().get("IMGWF");
//					String jndiIMG = (String)LoadXML.getServiceJNDI().get("IMGWF");
//					
//					IMGWFServiceManager IMGManager = ORIGEJBService.getImageWFServiceManagerHome(providerUrlIMG, jndiIMG);
//					//if(IMGManager.isExistingInMainApplicationFlow(requestID)){
//					  //  logger.debug("Imge Exist Cancel claim");
//						IMGManager.cancelClaimApplication(aiid);
//					//}
				}else{
//					Remove WPS
//					String providerUrlWF = (String)LoadXML.getServiceURL().get("ORIGWF");
//					String jndiWF = (String)LoadXML.getServiceJNDI().get("ORIGWF");
//					
//					ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndiWF);
//					OrigManager.cancelClaimApplication(aiid);
					
//				    WorkflowResponse workflowResponse = new WorkflowResponse(); 
					
					ORIGApplicationManager origAppManager = ORIGEJBService.getApplicationManager();
					
					origAppManager.cancleclaim(formHandler.getAppForm(), "", "");
				  
				}
			}
		}catch(Exception e){
			logger.error("Error >>"+e);
			return false;
		}
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
		String menu = (String)getRequest().getSession(true).getAttribute("PROPOSAL_MENU");
		if(("Y").equals(menu)){
			return "action=FirstAccess";
		}
		return "action=FristApp";
	}
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
