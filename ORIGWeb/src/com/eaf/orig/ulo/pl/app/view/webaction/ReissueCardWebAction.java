package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.control.message.Message;
//import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;

public class ReissueCardWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(ReissueCardWebAction.class);
	
	UserDetailM userM = new UserDetailM();
	PLAccountCardDataM accCardM = new PLAccountCardDataM();
	String accountID = null;

	@Override
	public Event toEvent(){		
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(accCardM);
		appEvent.setEventType(PLApplicationEvent.RE_ISSUE_CARD_NO);
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
			accountID = getRequest().getParameter("accId");
			userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			String applicationNo  = getRequest().getParameter("appNo");
			
			accCardM.setAccountId(accountID);
			accCardM.setApplicationNo(applicationNo);
			accCardM.setCardStatus(OrigConstant.Status.STATUS_ACTIVE);
			accCardM.setCreateBy(userM.getUserName());
			accCardM.setUpdateBy(userM.getUserName());
		
		}catch(Exception e){
//			log.fatal("Exception ReissueCardWebAction >> ",e);
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
	protected void doFail(EventResponse erp){
//		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());	
	}
	
	@Override
	protected void doSuccess(EventResponse erp){
		SearchHandler.PushErrorMessage(getRequest(), MessageResourceUtil.getTextDescription(getRequest(), "REISSUE"));
	}
	@Override
	public boolean getCSRFToken(){
		return false;
	}	
}
