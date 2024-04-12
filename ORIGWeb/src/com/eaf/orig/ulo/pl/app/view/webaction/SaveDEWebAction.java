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
import com.eaf.orig.ulo.pl.app.utility.Performance;
import com.eaf.orig.ulo.pl.app.utility.ValidationForm;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveDEWebAction extends WebActionHelper implements WebAction {
	UserDetailM userM;
	Logger logger = Logger.getLogger(SaveDEWebAction.class);
	private String firstDecision;
	int eventType;
	String nextaction="";
	
	Performance perf = new Performance("SaveDEWebAction",Performance.Module.SAVE_WEBACTION);
	
	@Override
	public Event toEvent() {
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		PLApplicationDataM cloanPlAppM = (PLApplicationDataM)getRequest().getSession().getAttribute(PLOrigFormHandler.CloanPlApplication);
		
		String userName = userM.getUserName();
		
		if(ORIGUtility.isEmptyString(applicationM.getDeFirstId())){
			applicationM.setDeFirstId(userName);
			applicationM.setDeStartDate(new Date());
		}
		
		if (ORIGUtility.isEmptyString(applicationM.getCreateBy())) {
			applicationM.setCreateBy(userName);
			applicationM.setCreateDate(DataFormatUtility.parseTimestamp(new Date()));
			applicationM.setUpdateBy(userName);
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		} else {
			applicationM.setUpdateBy(userName);
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		}
		
		applicationM.setOwner(userName);
		
		if(!OrigConstant.Action.CONFIRM_REJECT.equals(firstDecision)){
			ORIGLogic.LogicMapReasonReject(applicationM, userM);
		}
		
		applicationM.setDeLastId(userName);
		
		PLApplicationEvent event = new PLApplicationEvent(eventType, applicationM, userM);
		event.setCloanPlApplicationM(cloanPlAppM);
		return event;
	}

	@Override
	public boolean requiredModelRequest() {		
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
				
		perf.init("preModelRequest", applicationM.getAppRecordID(), applicationM.getTransactionID(), userM);
		perf.track(Performance.Action.SAVE_WEBACTION, Performance.START);
		try{
			String submitType = getRequest().getParameter("submitType");
			String deDecision = getRequest().getParameter("decision_option");
			
			logger.debug("Decision = "+deDecision);
			logger.debug("Submit Type = "+submitType);
			
			applicationM.setAppDecision(deDecision);
			String searchType = (String) getRequest().getSession().getAttribute("searchType");
			
			if(OrigConstant.FLAG_Y.equals(applicationM.getCheckFirstApp())){
				applicationM.setLifeCycle(1);
			}
									
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xRulesVerM = personalM.getXrulesVerification();
			
			if(null == xRulesVerM){
				xRulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xRulesVerM);
			}
						
			ORIGLogic origLogic = new ORIGLogic();
			
			WebActionUtil webUtil = new WebActionUtil();
			if (OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)) {
			    eventType = PLApplicationEvent.DE_SAVE_DRAFT;
			    applicationM.setAppDecision(OrigConstant.Action.SAVE_DRAFT);
			    			    
				webUtil.getAction(applicationM, userM, submitType, searchType);
				
				logger.debug("Offical AppDecision >> "+applicationM.getAppDecision()); 		
				
				if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
					applicationM.setApplicationStatus(null);
				}
				
				logger.debug("Type Draft Application Status >> "+applicationM.getApplicationStatus());
				
			}else if(OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)) {
				PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
				eventType = PLApplicationEvent.DE_SUBMIT;
				
				//set audit trail only role DE
				applicationM.setAuditFlag(OrigConstant.FLAG_Y);
				
				firstDecision = applicationM.getAppDecision();
				
				/**#SeptemWi Fix Decision*/
				/** Logic Continue if Empty NCB Code Send Decision To CB **/		
				if(origLogic.LogicSendToCB(userM.getCurrentRole(), deDecision, xRulesVerM.getNCBCode())){
					deDecision = OrigConstant.Action.SEND_TO_CB;
					logger.debug("Next Decision >> "+deDecision);
					applicationM.setAppDecision(deDecision);
				}				
				/**End #SeptemWi*/
				
				webUtil.getAction(applicationM, userM, submitType, searchType);
				
				if(applicationM.getEventType() != 0){
					eventType = applicationM.getEventType();
				}
				
				origLogic.LogicClearDataNCB(applicationM, applicationM.getAppDecision());
				
				logger.debug("Submit Decision ... "+applicationM.getAppDecision());
				applicationM.setApplicationStatus(null);
				
				//Praisan to calculate ca point
				if(appUtil.isFinalApplication(applicationM)){
					double caPoint = appUtil.getILogCAPoint(applicationM);
					applicationM.setCaPoint(caPoint);
				}				
			}
			
			applicationM.setDeDecision(applicationM.getAppDecision());	
			
		}catch(Exception e){
//			#septem comment change Logic ERROR Message
//			logger.error("##### SaveDEWebAction error:" + e.getMessage());
			String MSG = Message.error(e);
			formHandler.DestoryErrorFields();
			formHandler.PushErrorMessage("", MSG);
			return false;
		}	
		perf.track(Performance.Action.SAVE_WEBACTION, Performance.END);
		return true;			
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter(){				
		if(eventType == PLApplicationEvent.DE_SUBMIT){
			nextaction = "page=DE_PL_SUMMARY_SCREEN";
		}else if(eventType == PLApplicationEvent.DE_SAVE_DRAFT){
			nextaction = "page=DE_PL_SUMMARY_SCREEN";
		}
		return nextaction;		
	}
	
	@Override
	protected void doSuccess(EventResponse erp) {
		nextaction= "page=DE_PL_SUMMARY_SCREEN";
	}

	@Override
	protected void doFail(EventResponse erp){
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		formHandler.DestoryErrorFields();
//		#septem comment change Logic ERROR Message!
//		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		formHandler.PushErrorMessage("", erp.getMessage());
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}
	
	@Override
	public boolean validationForm(){
		boolean validate = false;		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		perf.init("validationForm", applicationM.getAppRecordID(), applicationM.getTransactionID(), userM);
		
		validate = ValidationForm.getValidationForm(getRequest());	
		if(validate){
			validate = ValidationForm.doValidateDuplicate(getRequest());
		}		
		logger.debug("ValidationForm... "+validate);		
		return validate;
	}
}
