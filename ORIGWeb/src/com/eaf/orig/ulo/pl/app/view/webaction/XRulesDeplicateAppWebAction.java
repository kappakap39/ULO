package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
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
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class XRulesDeplicateAppWebAction extends WebActionHelper implements WebAction{	
	static Logger logger = Logger.getLogger(XRulesDeplicateAppWebAction.class);
	private String nextaction = "";
	@Override
	public Event toEvent(){		
		logger.debug("[toEvent]");		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		String userName = userM.getUserName();		
		applicationM.setOwner(userName);		
			
		applicationM.setUpdateBy(userName);
		applicationM.setUpdateDate(new Timestamp(new Date().getTime()));	
			
		PLApplicationEvent event = new PLApplicationEvent(PLApplicationEvent.DEPLICATE_APPLICATION, applicationM, userM);		
			event.setCloanPlApplicationM(PLOrigFormHandler.getCloanPLApplicationDataM(getRequest()));		
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
	public boolean preModelRequest(){		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());		
		applicationM.setAppDecision(OrigConstant.Action.BLOCK);
		applicationM.setReasonVect(applicationM.getExecuteReasonVect());
		applicationM.setAuditFlag(OrigConstant.FLAG_Y);
		applicationM.setBlockFlag(OrigConstant.FLAG_B);
		return true;
	}
	
	@Override
	public int getNextActivityType() {	
		return FrontController.ACTION;
	}
	
	protected void doSuccess(EventResponse erp) {
		logger.debug("[doSuccess].");
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		nextaction= "page="+WebActionUtil.getForwordPageSummaryRole(userM.getCurrentRole());
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.debug("[doFail].");
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		formHandler.DestoryErrorFields();
//		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		formHandler.PushErrorMessage("", erp.getMessage());
	}

	@Override
	public String getNextActionParameter(){	
		return nextaction;
	}
	
	@Override
	public boolean getCSRFToken() {
		return true;
	}
	
}
