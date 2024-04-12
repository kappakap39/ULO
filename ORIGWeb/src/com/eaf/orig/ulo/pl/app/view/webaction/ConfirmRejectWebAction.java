package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationLogDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class ConfirmRejectWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(ConfirmRejectWebAction.class);
	Vector<PLApplicationDataM> appDataVect = new Vector<PLApplicationDataM>();
	UserDetailM userM = new UserDetailM();

	@Override
	public Event toEvent() {
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(appDataVect);
		appEvent.setEventType(PLApplicationEvent.DE_CONFIRM_REJECT);
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
		
		String[] appRecId = getRequest().getParameterValues("check");
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		try{		
			if (appRecId != null && appRecId.length > 0) {
				PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();		
				
				for (int i = 0; i < appRecId.length; i++){										
					PLApplicationDataM applicationM = origBean.loadPLApplicationDataM(appRecId[i]);
					
					applicationM.setUpdateBy(userM.getUserName());
					applicationM.setAppRecordID(appRecId[i]);
					applicationM.setApplicationStatus(null);
					
					Vector<PLApplicationLogDataM> appLogVect = new Vector<PLApplicationLogDataM>();
					PLApplicationLogDataM appLogM = new PLApplicationLogDataM();
					appLogM.setApplicationRecordID(appRecId[i]);
					appLogM.setUpdateBy(userM.getUserName());
					appLogM.setCreateBy(userM.getUserName());
					appLogVect.add(appLogM);
					
					applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
					
					applicationM.setApplicationLogVect(appLogVect);
					
					if(!OrigUtil.isEmptyString(applicationM.getReopenFlag()) && OrigConstant.FLAG_Y.equals(applicationM.getReopenFlag())){
						applicationM.setAppDecision(OrigConstant.Action.REJECT_SKIP_DF);
					}else{
						applicationM.setAppDecision(OrigConstant.Action.CONFIRM_REJECT);
					}
					
					ORIGLogic.LogicMapReasonReject(applicationM, userM);
					
					appDataVect.add(applicationM);
					applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
					
					//set final application decision #Praisan 20130426
					OrigApplicationUtil.getInstance().setFinalAppDecision(applicationM, userM);
					
					getRequest().getSession().removeAttribute("PL_INBOX_DATA");
				}
			}
		}catch (Exception e) {
//			log.fatal("Exception >> ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter() {
		return "action=InboxSupWebAction&role="+userM.getCurrentRole()+"&searchType="+OrigConstant.SEARCH_TYPE_PL_CONFIRM_REJECT;
	}

	@Override
	protected void doSuccess(EventResponse erp) {
	}

	@Override
	protected void doFail(EventResponse erp){	
//		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());	
	}

	@Override
	public boolean getCSRFToken() {
		return true;
	}
   
}
