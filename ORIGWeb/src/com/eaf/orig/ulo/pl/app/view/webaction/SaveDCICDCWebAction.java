/**
 * Create Date Apr 2, 2012 
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
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.app.utility.ValidationForm;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class SaveDCICDCWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(this.getClass().getName());
	private String firstDecision;
	private int eventType;
	@Override
	public Event toEvent() {
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		String userName = userM.getUserName();
		if (ORIGUtility.isEmptyString(applicationM.getDcFirstId())) {
			applicationM.setDcFirstId(userName);
			applicationM.setDcStartDate(new Date());
		}

		logger.debug("Create By " + applicationM.getCreateBy());
		
		if (ORIGUtility.isEmptyString(applicationM.getCreateBy())) {
			applicationM.setCreateBy(userName);
			applicationM.setCreateDate(DataFormatUtility.parseTimestamp(new Date()));
			applicationM.setUpdateBy(userName);
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		} else {
			applicationM.setUpdateBy(userName);
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		}
		
		if(!OrigConstant.Action.CONFIRM_REJECT.equals(firstDecision)){
			ORIGLogic.LogicMapReasonReject(applicationM, userM);
		}
		
		applicationM.setDcLastId(userName);
		
		PLApplicationEvent event = new PLApplicationEvent(eventType,applicationM, userM);
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
				eventType = PLApplicationEvent.DC_SAVE_DRAFT;
				applicationM.setAppDecision(OrigConstant.Action.SAVE_DRAFT);
				
				webUtil.getAction(applicationM, userM, submitType, searchType);
	
				if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
					applicationM.setApplicationStatus(null);
				}
			}else if(OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)) {
				PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
				
				eventType = PLApplicationEvent.DC_SUBMIT;					
				
				applicationM.setAuditFlag(OrigConstant.FLAG_Y);
				
				firstDecision = applicationM.getAppDecision();
				
				webUtil.getAction(applicationM, userM, submitType, searchType);
				if (applicationM.getEventType() != 0) {
					eventType = applicationM.getEventType();
				}
				applicationM.setApplicationStatus(null);
				
				ORIGLogic origLogic = new ORIGLogic();
					origLogic.LogicClearDataNCB(applicationM, applicationM.getAppDecision());
				
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
//			logger.error("##### SaveDCWebAction error:" + e.getMessage());
			formHandler.DestoryErrorFields();
//			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			formHandler.PushErrorMessage("", MSG);			
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}

	public String getNextActionParameter() {
		return "page=DC_PL_SUMMARY_SCREEN";
	}

	@Override
	protected void doSuccess(EventResponse erp){
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.error("##### SaveDCICDCWebAction Backend Error:" + erp.getMessage());
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
	public boolean validationForm() {
		boolean validate = false;		
		
		validate = ValidationForm.getValidationForm(getRequest());			
		if(validate){
			validate = ValidationForm.doValidateDuplicate(getRequest());
		}
		
		logger.debug("ValidationForm... "+validate);
		
		return validate;
	}

}
