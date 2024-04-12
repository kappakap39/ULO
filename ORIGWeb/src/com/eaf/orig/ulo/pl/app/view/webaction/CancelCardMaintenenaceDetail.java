package com.eaf.orig.ulo.pl.app.view.webaction;

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
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class CancelCardMaintenenaceDetail extends WebActionHelper implements WebAction {
	
	private Logger log = Logger.getLogger(this.getClass());
	PLApplicationDataM applicationM = null;
	UserDetailM userM = null;

	@Override
	public Event toEvent(){		
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(applicationM);
		appEvent.setEventType(PLApplicationEvent.SS_CANCEL);
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
		try{			
			String applicationNo = getRequest().getParameter("appNo");		
			String accountID = getRequest().getParameter("accId");
			
			log.debug("accountID >> "+accountID);
			
			userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			applicationM = new PLApplicationDataM();
			applicationM.setApplicationNo(applicationNo);
			
			UtilityDAO dao = new UtilityDAOImpl();	
			applicationM.setAppRecordID(dao.getAppRecordID(applicationNo));			
			log.debug("AppRecID >> "+applicationM.getAppRecordID());
			
			PLORIGApplicationManager appBean = PLORIGEJBService.getPLORIGApplicationManager();			
			applicationM = appBean.getAppInfo(applicationM.getAppRecordID());			
			applicationM.setAppDecision(OrigConstant.Action.CANCEL_APPLICATION_AFTER_CARDLINK);
			applicationM.setAccountID(accountID);			
			
			Vector<PLReasonDataM> reasonVect = new Vector<PLReasonDataM>();
			PLReasonDataM reasonM = new PLReasonDataM();
				reasonM.setApplicationRecordId(applicationM.getAppRecordID());
				reasonM.setCreateBy(userM.getUserName());
				reasonM.setUpdateBy(userM.getUserName());
				reasonM.setReasonCode("01");
				reasonM.setReasonType("123");
				reasonM.setRole(userM.getCurrentRole());
			reasonVect.add(reasonM);
			
			applicationM.setReasonVect(reasonVect);
			
			String searchType = (String) getRequest().getSession().getAttribute("searchType");
			
			WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, OrigConstant.SUBMIT_TYPE_SEND, searchType);
			
		}catch(Exception e){
//			log.fatal("Exception CancelCardMaintenenaceDetail >> ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}
	
	@Override
	protected void doFail(EventResponse erp) {
//		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());	
	}
	@Override
	protected void doSuccess(EventResponse erp){
		getRequest().getSession().removeAttribute("VALUE_LIST");
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
