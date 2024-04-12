/**
 * Create Date May 3, 2012 
 * Create By Praisan
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
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigBusinessClassUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.app.utility.Performance;
import com.eaf.orig.ulo.pl.app.utility.ValidationForm;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

/**
 * @author Praisan
 * 
 */
public class SaveCAWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SaveCAWebAction.class);
	private int eventType;

	Performance perf = new Performance("SaveCAWebAction",Performance.Module.SAVE_WEBACTION);
	
	@Override
	public Event toEvent() {
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if (null == userM) {
			userM = new UserDetailM();
		}
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		
		PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
		String userName = userM.getUserName();
		if (ORIGUtility.isEmptyString(applicationM.getCaFirstId())) {
			applicationM.setCaFirstId(userName);
			applicationM.setCaStartDate(new Date());
		}
		
		//#septem for FCP_KEC_CC,FCP_KEC_CG get Reason Reject for decision Reject!
		if(OrigConstant.BusClass.FCP_KEC_CC.equals(applicationM.getBusinessClassId())
				|| OrigConstant.BusClass.FCP_KEC_CG.equals(applicationM.getBusinessClassId())){
			if("RJ".equals(xrulesVerM.getRecommendResult())){
				ORIGLogic.LogicMapReasonReject(applicationM, userM);
			}
		}
		
		applicationM.setCaLastId(userName);
		
		appUtil.stampUserActionAndDate(applicationM, userName);
		
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
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if (null == userM) {
			userM = new UserDetailM();
		}
		
		perf.init("preModelRequest", applicationM.getAppRecordID(), applicationM.getTransactionID(), userM);
		perf.track(Performance.Action.SAVE_WEBACTION, Performance.START);
		
		try{
			String submitType = getRequest().getParameter("submitType");
			String searchType = (String) getRequest().getSession().getAttribute("searchType");
					
			if (OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)) {
				eventType=PLApplicationEvent.CA_SAVE_DRAFT;
				
				applicationM.setAppDecision(OrigConstant.Action.SAVE_DRAFT);
				
				logger.debug("@@@@@ Current ApplicationStatus:" + applicationM.getApplicationStatus());
				logger.debug("@@@@@ AppDecision Before getAction:" + applicationM.getAppDecision());
				    
				WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, submitType, searchType);
				logger.debug("@@@@@ AppDecision After  getAction:" + applicationM.getAppDecision());
	
				if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
					applicationM.setApplicationStatus(null);
					applicationM.setCaDecision(applicationM.getAppDecision()); //set CA decision
				}
			}else if (OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)) {
				PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
				eventType = PLApplicationEvent.CA_SUBMIT;
				
				WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, submitType, searchType);
				if (applicationM.getEventType() != 0) {
					eventType = applicationM.getEventType();
				}
				
				applicationM.setApplicationStatus(null);
	
				logger.debug("@@@@@ AppDecision After FinalAppDecision : " + applicationM.getAppDecision());
				
				applicationM.setCaDecision(applicationM.getAppDecision()); //set CA decision
				
				ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();		
				String dfComplteApproveFlag = origBean.isDataFinalCompleteApprove(applicationM.getAppRecordID());
							
				//check CIS number for skip DF
				if((hasCISNo(applicationM) && !OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW))
						|| OrigConstant.FLAG_Y.equals(dfComplteApproveFlag)){
					if(OrigConstant.Action.APPROVE.equals(applicationM.getAppDecision())){
						applicationM.setAppDecision(OrigConstant.Action.APPROVE_SKIP_DF);
					}else if(OrigConstant.Action.OVERRIDE.equals(applicationM.getAppDecision())){
						applicationM.setAppDecision(OrigConstant.Action.OVERRIDE_SKIP_DF);
					}else if(OrigConstant.Action.POLICY_EXCEPTION.equals(applicationM.getAppDecision())){
						applicationM.setAppDecision(OrigConstant.Action.POLICY_EXCEPTION_SKIP_DF);
					}
				}
				
				if (OrigConstant.FLAG_Y.equals(applicationM.getReopenFlag()) && OrigConstant.Action.REJECT.equals(applicationM.getAppDecision())){
					applicationM.setAppDecision(OrigConstant.Action.REJECT_SKIP_DF);
				}
				
				ORIGLogic origLogic = new ORIGLogic();
				origLogic.LogicClearDataNCB(applicationM, applicationM.getAppDecision());
				
				logger.debug("@@@@@ AppDecision after chedk CIS :" + applicationM.getAppDecision());
				
				if(appUtil.isFinalApplication(applicationM)){
					double caPoint = appUtil.getILogCAPoint(applicationM);
					logger.debug("@@@@@ caPoint :"+caPoint);
					applicationM.setCaPoint(caPoint);
				}
				
				if(OrigConstant.Action.ESCALATE.equals(applicationM.getAppDecision())){
					// Cal DLA
					String levelID = "";
					try {
						// set Job type
						if (OrigConstant.typeColor.typeGreen.equals(applicationM.getJobType())
								|| OrigConstant.typeColor.typeRed .equals(applicationM.getJobType())) {
							levelID = PLORIGEJBService.getORIGDAOUtilLocal().getDLA(applicationM.getFinalCreditLimit(),applicationM.getJobType());
						} else if (OrigConstant.typeColor.typeYellow.equals(applicationM.getJobType())) {// Yellow
							if(applicationM.getPolicyExcDocNo()!=null &&!"".equals(applicationM.getPolicyExcDocNo())){
								levelID = PLORIGEJBService.getORIGDAOUtilLocal().getDLA(applicationM.getFinalCreditLimit(),applicationM.getJobType());
							}else{
								levelID = OrigApplicationUtil.getInstance().getJobTypeByPolicy(applicationM);
							}
						}
					} catch (Exception e) {
						logger.fatal("error:",e);
						throw e;
					}
					logger.debug("@@@@@ DLA level:" + levelID);
					applicationM.setGroupAllocateID(applicationM.getJobType());
					applicationM.setLevelID(levelID);
				}
			}
			logger.debug("@@@@@ Event Type :"+eventType);

		}catch(Exception e){
//			logger.error("##### SaveCAWebAction error:" + e.getMessage());
			formHandler.DestoryErrorFields();
//			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			formHandler.PushErrorMessage("", MSG);
			return false;
		}
		perf.track(Performance.Action.SAVE_WEBACTION, Performance.END);
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;// to summary
	}

	public String getNextActionParameter() {
		return "page=CA_PL_SUMMARY_SCREEN";
	}

	@Override
	protected void doSuccess(EventResponse erp) {
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.error("##### SaveCAWebAction Backend Error:" + erp.getMessage());
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		formHandler.DestoryErrorFields();
//		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		formHandler.PushErrorMessage("", erp.getMessage());
	}
	
	private boolean hasCISNo(PLApplicationDataM appM){
		if(appM != null){
			PLPersonalInfoDataM applicantPersonM = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			if(applicantPersonM != null){
				String cisNo = applicantPersonM.getCisNo();
				logger.debug("@@@@@ CIS number:" + cisNo);
				if(cisNo != null && !"".equals(cisNo) && !"-".equals(cisNo)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}
	
	@Override
	public boolean validationForm() {
		return ValidationForm.getCaValidationForm(getRequest());
	}
}
