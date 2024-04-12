package com.eaf.orig.ulo.pl.app.view.webaction;

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
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class SaveCashDay5ApplicationWebAction extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(SaveDCWebAction.class);
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
		if (ORIGUtility.isEmptyString(applicationM.getDcFirstId())) {
			applicationM.setDcFirstId(userName);
			applicationM.setDcStartDate(new Date());
		}
		
		if(ORIGUtility.isEmptyString(applicationM.getCreateBy())) {
			applicationM.setCreateBy(userName);
			applicationM.setCreateDate(DataFormatUtility.parseTimestamp(new Date()));
			applicationM.setUpdateBy(userName);
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		}else{
			applicationM.setUpdateBy(userName);			 
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		}

		applicationM.setDcLastId(userName);
		
		PLApplicationEvent event = new PLApplicationEvent(eventType, applicationM, userM);
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
	public boolean preModelRequest() {
		PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		try{
			String submitType = getRequest().getParameter("submitType");
			if (OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)) {
				eventType = PLApplicationEvent.CASHDAY5_SAVE;
			}
			if(appUtil.isFinalApplication(applicationM)){
				double caPoint = appUtil.getILogCAPoint(applicationM);
				applicationM.setCaPoint(caPoint);
			}
			return true;
		}catch(Exception e){
//			logger.error("ERROR " + e.getMessage());
			formHandler.DestoryErrorFields();
//			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			formHandler.PushErrorMessage("", MSG);
			return false;
		}
	}
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter() {
		return "page=DC_PL_SUMMARY_SCREEN";
	}

	@Override
	protected void doSuccess(EventResponse erp) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		applicationM.setReasonVect(new Vector());		 
	}

	@Override
	protected void doFail(EventResponse erp) {
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
