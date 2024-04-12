package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Date;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class SaveDFWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SaveDFWebAction.class);
	private int eventType;
	String nextaction="";	
	
	@Override
	public Event toEvent(){
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		
		PLApplicationDataM applicationM =  PLOrigFormHandler.getPLApplicationDataM(getRequest());

		PLApplicationDataM cloanPlAppM = (PLApplicationDataM)getRequest().getSession().getAttribute(PLOrigFormHandler.CloanPlApplication);
		
		String userName = userM.getUserName();
		
		if(ORIGUtility.isEmptyString(applicationM.getDfFirstId())){
			applicationM.setDfFirstId(userName);
			applicationM.setDfStartDate(new Date());
		}
		
		logger.debug("applicationDataM.getCreateBy() = "+applicationM.getCreateBy());
		
		if(ORIGUtility.isEmptyString(applicationM.getCreateBy())){
			applicationM.setCreateBy(userName);
			applicationM.setUpdateBy(userName);
		}else{
			applicationM.setUpdateBy(userName);
		}
		
		applicationM.setOwner(userName);		
		applicationM.setDfLastId(userName);	
		applicationM.setDfLastDate(new Date());
		
		PLApplicationEvent event = new PLApplicationEvent(eventType, applicationM, userM);
		
		event.setCloanPlApplicationM(cloanPlAppM);

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
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String searchType = (String) getRequest().getSession().getAttribute("searchType");
		try{
			
			String submitType = getRequest().getParameter("submitType");			
			logger.debug("submitType="+submitType);
			
			if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
				eventType=PLApplicationEvent.DF_SAVE_DRAFT;
				applicationM.setAppDecision(OrigConstant.Action.SAVE_DRAFT);
			    WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, OrigConstant.SUBMIT_TYPE_DRAFT, searchType);
			}else if(OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)){
				eventType = PLApplicationEvent.DF_SUBMIT;
				applicationM.setApplicationStatus(null);
				String currentRole = userM.getCurrentRole();
				String jobState = applicationM.getJobState();
			  
				if(currentRole.equalsIgnoreCase(OrigConstant.ROLE_DF) && 
						(jobState.equalsIgnoreCase(OrigConstant.roleJobState.DF_APPROVE) || 
								jobState.equalsIgnoreCase(OrigConstant.roleJobState.DF_APPROVE_CC))){  //Praisan add STI2701 for CC and CG
	//			  	#SeptemWi Comment This Used Fix Action
					applicationM.setAppDecision(OrigConstant.Action.COMPLETE_CIS);
			    } else{
	//			  	#SeptemWi Comment This Used Fix Action
			    	applicationM.setAppDecision(OrigConstant.Action.COMPLETE_REJECT);
			    }
			  
				WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, submitType, searchType);
				
				if(applicationM.getEventType() != 0){
					eventType = applicationM.getEventType();
				}
				applicationM.setApplicationStatus(null);
			}
			return true;
		}catch(Exception e){
//			logger.error("##### SaveDFWebAction error:" + e.getMessage());
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
		nextaction="page=DF_PL_SUMMARY_SCREEN";	
		return nextaction;
	}

	@Override
	protected void doSuccess(EventResponse erp) {
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.error("##### SaveDFWebAction Backend Error:" + erp.getMessage());
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		formHandler.DestoryErrorFields();
//		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		formHandler.PushErrorMessage("", erp.getMessage());
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}
	
}
