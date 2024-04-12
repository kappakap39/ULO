package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveFraudCompanyWebAction extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){
		try{
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}			
			
			String fraudcompDecision = getRequest().getParameter("fraudcomp-decision");			
			
			xrulesVerM.setFraudCompanyDecision(fraudcompDecision);							
		
		}catch (Exception e) {
			logger.fatal("Error ",e);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
