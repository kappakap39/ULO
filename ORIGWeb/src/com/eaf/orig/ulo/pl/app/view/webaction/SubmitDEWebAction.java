package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class SubmitDEWebAction extends WebActionHelper implements WebAction {
Logger logger = Logger.getLogger(SaveDEWebAction.class);
String nextaction="";
	@Override
	public Event toEvent() {
		
		logger.debug("[SubmitDEWebAction]..[toEvent].");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		if(userM == null){
			userM = new UserDetailM();
		}	
		
		PLApplicationDataM plApplicationDataM = PLOrigFormHandler.getPLApplicationDataM(getRequest());	
		String userName = userM.getUserName();
		
		nextaction="page=DE_PL_SUMMARY_SCREEN";			
	
		if(ORIGUtility.isEmptyString(plApplicationDataM.getDeFirstId())){
			plApplicationDataM.setDeFirstId(userName);
			plApplicationDataM.setDeStartDate(new Date());
		}
		logger.debug("[SubmitDEWebaction]...[nextaction=]"+nextaction);
		
		if(ORIGUtility.isEmptyString(plApplicationDataM.getCreateBy())){
			plApplicationDataM.setCreateBy(userName);
			plApplicationDataM.setUpdateBy(userName);
		}else{			
			plApplicationDataM.setUpdateBy(userName);
		}
		logger.debug("[SubmitDEWebAction]..[toEvent]..Create By "+plApplicationDataM.getCreateBy());
		logger.debug("[SubmitDEWebAction]..[toEvent]..userName "+userName);
		plApplicationDataM.setDeLastId(userName);			
		PLApplicationEvent event = new PLApplicationEvent(PLApplicationEvent.DE_SUBMIT, plApplicationDataM, userM);		
		
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
			
		logger.debug("[SubmitDEWebAction]..[preModelRequest].");
						
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;			
	}

	@Override
	public String getNextActionParameter() {		
		logger.debug("[SubmitDEWebAction]..[getNextActionParameter]..Next Action");	
		return nextaction;
	}
	
	@Override
	protected void doSuccess(EventResponse erp) {
		logger.debug("[SubmitDEWebAction]..[doSuccess].");
		//super.doSuccess(erp);
		
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.debug("[SubmitDEWebAction]..[doFail].");
		//super.doFail(erp);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
