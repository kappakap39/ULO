package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.ERROR;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.formcontrol.view.form.ErrorDataM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class SaveReallocateWebAction extends WebActionHelper implements WebAction {

	static Logger logger = Logger.getLogger(SaveReallocateWebAction.class);
	
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
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		SearchHandler HandlerM = (SearchHandler) getRequest().getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		HandlerM.setErrorMSG(null);
		HandlerM.setErrorList(null);
		
		String[] checks = getRequest().getParameterValues("check_apprecid");
		
		try{
			if(null != checks){		
				
				ErrorDataM errorM = null;
				PLApplicationDataM applicationM  = null;	
				
				PLORIGApplicationManager appBean = PLORIGEJBService.getPLORIGApplicationManager();
				
				for(String appRecID : checks){
					logger.debug("[SaveReallocate] appRecID "+appRecID);
					try{
						applicationM = appBean.getAppInfo(appRecID);
						MapObject(applicationM);
						appBean.AllocateApplication(applicationM,userM);
					}catch(Exception e){
//						logger.error("ERROR: Reallocate appRecID "+appRecID+" MESSAGE "+e.getMessage());
//						String msg = ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR");
						String msg = Message.error(e);
						if(ERROR.MSG_CANNOT_CLAIM.equals(e.getMessage())){
							msg = ErrorUtil.getShortErrorMessage(getRequest(),ERROR.MSG_CANNOT_CLAIM);
						}
						errorM =  new ErrorDataM(applicationM.getApplicationNo(),msg);
						HandlerM.add(errorM);
					}
				}
				
				HandlerM.GenarateErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(), "APP_NO"));
			}
		}catch(Exception e){
//			logger.fatal("Exception >> ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}		
		getRequest().getSession().removeAttribute("VALUE_LIST");
		return true;
	}
	
	private void MapObject(PLApplicationDataM applicationM){
		applicationM.setApplicationStatus(null);
		applicationM.setOwner(null);
		applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
		applicationM.setAppDecision(OrigConstant.Action.REALLOCATE);
	}
	
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter() {
		return "action=SearchReassignWebAction";
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}

}