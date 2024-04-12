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

public class SubmitDEFullWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SubmitDEFullWebAction.class);
	@Override
	public Event toEvent() {
		logger.debug("[SubmitDEFullWebAction]..[toEvent].");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		if(userM == null){
			userM = new UserDetailM();
		}	
		
		PLApplicationDataM plApplicationDataM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		
		String userName = userM.getUserName();
		
		if(ORIGUtility.isEmptyString(plApplicationDataM.getDeFirstId())){
			plApplicationDataM.setDeFirstId(userName);
			plApplicationDataM.setDeStartDate(new Date());
		}
		
		
		if(ORIGUtility.isEmptyString(plApplicationDataM.getCreateBy())){
			plApplicationDataM.setCreateBy(userName);
			plApplicationDataM.setUpdateBy(userName);
		}else{			
			plApplicationDataM.setUpdateBy(userName);
		}
		logger.debug("[SubmitDEFullWebAction]..[toEvent]..Create By "+plApplicationDataM.getCreateBy());
		logger.debug("[SubmitDEFullWebAction]..[toEvent]..userName "+userName);
		plApplicationDataM.setDeLastId(userName);			
		PLApplicationEvent event = new PLApplicationEvent(PLApplicationEvent.DE_SUBMIT, plApplicationDataM, userM);		
		
		return event;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {
		// TODO Auto-generated method stub
		logger.debug("[SubmitDEFullWebAction]..[preModelRequest].");
		return false;
	}

	@Override
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return FrontController.PAGE;
	}
	
	public String getNextActionParameter() {		
			
		return "page=DE_PL_SUMMARY_SCREEN";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
