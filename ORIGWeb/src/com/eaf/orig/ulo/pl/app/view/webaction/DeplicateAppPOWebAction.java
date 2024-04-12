package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

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
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;

public class DeplicateAppPOWebAction extends WebActionHelper implements WebAction{	
	
	static Logger logger = Logger.getLogger(DeplicateAppPOWebAction.class);
	UserDetailM userM;
	PLApplicationDataM applicationM;
	private String nextaction = "";
	@Override
	public Event toEvent(){		
		String userName = userM.getUserName();		
		applicationM.setOwner(userName);
		
		applicationM.setUpdateBy(userName);
		applicationM.setUpdateDate(new Timestamp(new Date().getTime()));		
		
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setCloanPlApplicationM(PLOrigFormHandler.getCloanPLApplicationDataM(getRequest()));
		appEvent.setObject(applicationM);
		appEvent.setEventType(PLApplicationEvent.REOPEN_DUP);
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
	public boolean preModelRequest(){
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		applicationM.setAppDecision(OrigConstant.Action.REOPEN_BLOCK);		
		applicationM.setUpdateBy(userM.getUserName());
		applicationM.setUpdateDate(new Timestamp(new Date().getTime()));
		applicationM.setAppDate(new Timestamp(new Date().getTime()));
		applicationM.setApplicationStatus(null);
		
		//set reason
		PLReasonDataM reasonM = new PLReasonDataM();
			reasonM.setApplicationRecordId(applicationM.getAppRecordID());
			reasonM.setReasonCode("09");
			reasonM.setReasonType("34");
			reasonM.setCreateBy(userM.getUserName());
			reasonM.setCreateDate(new Timestamp(new Date().getTime()));
			reasonM.setRole(userM.getCurrentRole());
		
		Vector<PLReasonDataM> reasonVect = new Vector<PLReasonDataM>();
			reasonVect.add(reasonM);
		applicationM.setReasonVect(reasonVect);
		
		return true;
	}
	
	@Override
	public int getNextActivityType() {	
		return FrontController.ACTION;
	}
	
	protected void doSuccess(EventResponse erp) {
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		nextaction= "page="+WebActionUtil.getForwordPageSummaryRole(userM.getCurrentRole());
	}

	@Override
	protected void doFail(EventResponse erp) {
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
	public boolean getCSRFToken(){
		return true;
	}
}
