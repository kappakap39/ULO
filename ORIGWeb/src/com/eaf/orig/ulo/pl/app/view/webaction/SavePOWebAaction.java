package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
import java.util.Date;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.ajax.ClearDataInformation;
import com.eaf.orig.ulo.pl.app.utility.ValidationForm;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class SavePOWebAaction extends WebActionHelper implements WebAction {
	
	
	PLApplicationDataM applicationM;
	UserDetailM userM;
	private int eventType;
	
	@Override
	public Event toEvent() {
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(applicationM);
		appEvent.setEventType(eventType);
		appEvent.setUserM(userM);
		return appEvent;
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
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler)getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		applicationM = formHandler.getAppForm();
		
		String submitType = getRequest().getParameter("submitType");
		
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)){
			
			if(OrigConstant.Action.APPROVE.equals(applicationM.getAppDecision())){
				
				eventType = PLApplicationEvent.SUBMIT_REOPEN;
				applicationM.setReopenFlag(OrigConstant.FLAG_Y);
				applicationM.setAppDecision(OrigConstant.Action.REOPEN);
				applicationM.setLifeCycle(applicationM.getLifeCycle()+1);
				applicationM.setAppDate(new Timestamp(new Date().getTime()));
				applicationM.setApplicationStatus(null);
				//Clear final app decision
				applicationM.setFinalAppDecision(null);
				applicationM.setFinalAppDecisionBy(null);
				applicationM.setFinalAppDecisionDate(null);
				
				PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);				
					personalM.setNcbDocVect(null);
				
				//Clear verification data case approve request
				ClearDataInformation clearData = new ClearDataInformation();
				clearData.ClearPLXRulesVerificationResultDataM(null,applicationM, userM);				
				applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
				
			} else if (OrigConstant.Action.REJECT.equals(applicationM.getAppDecision())){
				
				eventType = PLApplicationEvent.SUBMIT_REOPEN;
				applicationM.setAppDecision(OrigConstant.Action.REOPEN_WITH_REJECT);
				applicationM.setLifeCycle(applicationM.getLifeCycle()+1);
				applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
				applicationM.setAppDate(new Timestamp(new Date().getTime()));
				applicationM.setApplicationStatus(null);
				OrigApplicationUtil.getInstance().setFinalAppDecision(applicationM, userM);	
			}
		}
		
		//set value
		applicationM.setUpdateBy(userM.getUserName());
		applicationM.setUpdateDate(new Timestamp(new Date().getTime()));
		
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	public String getNextActionParameter() {
		return "page=DE_PL_SUMMARY_SCREEN";
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}
	
	@Override
	public boolean validationForm(){
		return ValidationForm.getPoValidationForm(getRequest());
	}

}
