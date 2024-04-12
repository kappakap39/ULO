package com.eaf.orig.ulo.pl.app.view.webaction;

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
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.ValidationForm;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
/**
 * @author Praisan
 * 
 */
public class SaveFUWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SaveFUWebAction.class);
	private int eventType;
	@Override
	public Event toEvent() {
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if (null == userM) {
			userM = new UserDetailM();
		}
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		String userName = userM.getUserName();
		if (ORIGUtility.isEmptyString(applicationM.getFuFirstId())) {
			applicationM.setFuFirstId(userName);
			applicationM.setFuStartDate(new Date());
		}
		applicationM.setFuLastId(userName);
		applicationM.setFuLastDate(new Date());
		
		PLApplicationEvent event = new PLApplicationEvent(eventType,applicationM, userM);
			event.setCloanPlApplicationM(PLOrigFormHandler.getCloanPLApplicationDataM(getRequest()));
		
		return event;
	}

	@Override
	public boolean requiredModelRequest(){
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if (null == userM) {
			userM = new UserDetailM();
		}
		try{
			String submitType = getRequest().getParameter("submitType");
			String searchType = (String) getRequest().getSession().getAttribute("searchType");
					
			if (OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)) {
				eventType = PLApplicationEvent.FU_SAVE_DRAFT;
				applicationM.setAppDecision(OrigConstant.Action.SAVE_DRAFT);
				
				logger.debug("@@@@@ Current ApplicationStatus:" + applicationM.getApplicationStatus());
				logger.debug("@@@@@ AppDecision Before getAction:" + applicationM.getAppDecision());
				    
				WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, submitType, searchType);
				
				logger.debug("@@@@@ AppDecision After  getAction:" + applicationM.getAppDecision());
	
				if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
					applicationM.setApplicationStatus(null);
				}else{
					if(OrigConstant.ApplicationStatus.RECEIVED_DOCUMENT.equals(applicationM.getApplicationStatus())
							|| OrigConstant.FLAG_Y.equals(applicationM.getSmsFollowUp())){
						applicationM.setApplicationStatus(WorkflowConstant.AppStatus.FOLLOWED);
					}
				}
				logger.debug("@@@@@ Final ApplicationStatus:" + applicationM.getApplicationStatus());
					
			} else if (OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)) {
				eventType = PLApplicationEvent.FU_SUBMIT;
				// Modify application Decision
				logger.debug("@@@@@ AppDecision from user:" + applicationM.getAppDecision());
				
				WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, submitType, searchType);
				if(applicationM.getEventType() != 0){
					eventType = applicationM.getEventType();
				}
				
				applicationM.setApplicationStatus(null);
				
				logger.debug("@@@@@ AppDecision After FinalAppDecision:" + applicationM.getAppDecision());			
			}
			return true;
		}catch(Exception e){
//			logger.error("##### SaveFUWebAction Error:" + e.getMessage());
			formHandler.DestoryErrorFields();
//			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			formHandler.PushErrorMessage("", MSG);		
			return false;
		}
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}
	
	public String getNextActionParameter() {
		return "page=FU_PL_SUMMARY_SCREEN";
	}
	
	@Override
	protected void doSuccess(EventResponse erp) {
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.error("##### SaveFUWebAction Backend Error:" + erp.getMessage());
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		formHandler.DestoryErrorFields();
//		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		formHandler.PushErrorMessage("", erp.getMessage());
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}
	
	@Override
	public boolean validationForm(){
		return ValidationForm.getValidationForm(getRequest());
	}
}
