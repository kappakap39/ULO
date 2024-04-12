/**
 * Create Date Apr 26, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 */
package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Date;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.app.utility.ValidationForm;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

/**
 * @author Sankom
 * 
 */
public class SaveVCWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SaveVCWebAction.class);
	private int eventType;

	@Override
	public Event toEvent() {
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if (null == userM) {
			userM = new UserDetailM();
		}
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		String userName = userM.getUserName();
		
		if (ORIGUtility.isEmptyString(applicationM.getVcFirstId())) {
			applicationM.setVcFirstId(userName);
			applicationM.setVcStartDate(new Date());
		}
		
		logger.debug("Create By " + applicationM.getCreateBy());
		
		if (ORIGUtility.isEmptyString(applicationM.getCreateBy())) {
			applicationM.setCreateBy(userName);
			applicationM.setUpdateBy(userName);
		} else {
			logger.debug("UserName = " + userName);
			applicationM.setUpdateBy(userName);
		}
		//Sankom Set Reject Reason
//		if(OrigConstant.Action.REJECT.equals(applicationM.getAppDecision())){ //auto generate reject reason from rules detail
//			logger.debug("VC AppDecision Reject !! Generate Reject Reason From Rules Result ");
//			OrigApplicationUtil origAppUtil = new OrigApplicationUtil();
//			Vector<PLReasonDataM> rejectVect = origAppUtil.generateRejectReasonFromRulesResult(applicationM, userM);
//			applicationM.setReasonVect(rejectVect);
//		}
		//End Set Reject Reason
		
		ORIGLogic.LogicMapReasonReject(applicationM, userM);
		
		applicationM.setVcLastId(userName);
		
		PLApplicationEvent event = new PLApplicationEvent(eventType, applicationM, userM);
		event.setCloanPlApplicationM(PLOrigFormHandler.getCloanPLApplicationDataM(getRequest()));

		return event;
	}

	@Override
	public boolean requiredModelRequest(){
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		
		PLApplicationDataM applicationM = formHandler.getAppForm();

		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");

		String searchType = getRequest().getParameter("searchType");
		String submitType = getRequest().getParameter("submitType");
		
		logger.debug("SubmitType >> " + submitType);
		
		try{	
			WebActionUtil webUtil = new WebActionUtil();
		
			if (OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)) {
				eventType = PLApplicationEvent.VC_SAVE_DRAFT;
				applicationM.setAppDecision(OrigConstant.Action.SAVE_DRAFT);
				
				webUtil.getAction(applicationM, userM, submitType, searchType);
	
				if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
					applicationM.setApplicationStatus(null);
				}
				
			}else if(OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)) {
				PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
				
				eventType = PLApplicationEvent.VC_SUBMIT;					

				webUtil.getAction(applicationM, userM, submitType, searchType);
				if (applicationM.getEventType() != 0) {
					eventType = applicationM.getEventType();
				}
				applicationM.setApplicationStatus(null);	
				
				logger.debug("AppDecision ... " + applicationM.getAppDecision());
				
				//Praisan to calculate ca point
				if(appUtil.isFinalApplication(applicationM)){
					double caPoint = appUtil.getILogCAPoint(applicationM);
					logger.debug("@@@@@ caPoint :"+caPoint);
					applicationM.setCaPoint(caPoint);
				}
				return true;
			}
		}catch(Exception e){
//			logger.error("##### SaveVCWebAction error:" + e.getMessage());
			formHandler.DestoryErrorFields();
//			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			formHandler.PushErrorMessage("", MSG);	
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType() {

		return 0;
	}

	@Override
	protected void doSuccess(EventResponse erp) {

		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.error("##### SaveVCWebAction Backend Error:" + erp.getMessage());
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		formHandler.DestoryErrorFields();
//		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		formHandler.PushErrorMessage("", erp.getMessage());
	}

	@Override
	public boolean getCSRFToken(){		
		return true;
	}
	
	@Override
	public boolean validationForm(){
		return ValidationForm.getValidationForm(getRequest());
	}

}
