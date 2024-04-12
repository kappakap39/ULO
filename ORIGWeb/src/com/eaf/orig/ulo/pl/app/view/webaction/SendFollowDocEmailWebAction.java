package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class SendFollowDocEmailWebAction extends WebActionHelper implements WebAction {
	
	Logger logger = Logger.getLogger(SendFollowDocEmailWebAction.class);
	
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {		
		try{
			String emailType = getRequest().getParameter("emailType");
			String emailCCTo = getRequest().getParameter("confirm_cc_email");
			
			if(OrigUtil.isEmptyString(emailCCTo)){
				emailCCTo = null;
			}
			
			UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
			PLApplicationDataM applicationM = formHandler.getAppForm();
			
			applicationM.setUpdateBy(userM.getUserName()); //Set update by to current user for email sender name
		
			logger.debug("Email Type : "+emailType);
			logger.debug("Email CC 	 : "+emailCCTo);

			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();					
			
			if(!OrigConstant.FLAG_Y.equals(applicationM.getSmsFollowUp())){
				origBean.SendSMSFollowDoc(applicationM);
			}		
						
			if(!OrigUtil.isEmptyString(emailType)){
				origBean.sendEmailFollowDocToBranch(emailType, applicationM,emailCCTo);
			}

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
