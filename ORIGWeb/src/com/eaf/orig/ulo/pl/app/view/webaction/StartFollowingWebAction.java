package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class StartFollowingWebAction extends WebActionHelper implements WebAction {
	static Logger logger = Logger.getLogger(StartFollowingWebAction.class);
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest() {
		try{			
			UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
			PLApplicationDataM applicationM = formHandler.getAppForm();
				applicationM.setUpdateBy(userM.getUserName()); //Set update by to current user for email sender name
			
			if(!OrigConstant.FLAG_Y.equals(applicationM.getSmsFollowUp())){
				ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();	
					origBean.SendSMSStartFollow(applicationM);			
			}
			
			logger.debug("SmsFollowUp >> "+applicationM.getSmsFollowUp());
			
			JsonObjectUtil json = new JsonObjectUtil();
				json.CreateJsonObject("sms-followup", applicationM.getSmsFollowUp());
				json.ResponseJsonArray(getResponse());
				
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}			
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
