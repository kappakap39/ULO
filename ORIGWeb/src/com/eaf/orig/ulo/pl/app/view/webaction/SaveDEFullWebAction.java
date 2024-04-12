package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Date;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class SaveDEFullWebAction extends WebActionHelper implements WebAction  {
	Logger logger = Logger.getLogger(SaveDEFullWebAction.class);
	private int eventType;
	@Override
	public Event toEvent() {
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		
//		if (OrigConstant.ROLE_DE_FULL.equals(userRoles.elementAt(0))) {
//			userRoles.removeElementAt(0);
//            userRoles.insertElementAt(OrigConstant.ROLE_DE, 0);
//            
//        }
		logger.debug("userM.getCurrentRole() = "+userM.getCurrentRole());
		PLOrigFormHandler formHandler=(PLOrigFormHandler)getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM plApplicationDataM = formHandler.getAppForm();
		PLApplicationDataM cloanPlAppM = (PLApplicationDataM)getRequest().getSession().getAttribute(PLOrigFormHandler.CloanPlApplication);
		
		String userName = userM.getUserName();
		
		if(ORIGUtility.isEmptyString(plApplicationDataM.getDeFirstId())){
			plApplicationDataM.setDeFirstId(userName);
			plApplicationDataM.setDeStartDate(new Date());
		}
		
		logger.debug("applicationDataM.getCreateBy() = "+plApplicationDataM.getCreateBy());
		
		if(ORIGUtility.isEmptyString(plApplicationDataM.getCreateBy())){
			plApplicationDataM.setCreateBy(userName);
			plApplicationDataM.setUpdateBy(userName);
		}else{
			logger.debug("userName = "+userName);
			plApplicationDataM.setUpdateBy(userName);
		}
		
		plApplicationDataM.setOwner(userName);
		
		logger.debug("PLPERSONALINFO ID= "+(plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT)).getIdNo());
		plApplicationDataM.setDeLastId(userName);			
		PLApplicationEvent event = new PLApplicationEvent(eventType, plApplicationDataM, userM);
		event.setCloanPlApplicationM(cloanPlAppM);

	return event;
}


	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {
		
		PLOrigFormHandler formHandler=(PLOrigFormHandler)getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM plApplicationDataM = formHandler.getAppForm();
		String submitType=getRequest().getParameter("submitType");
		logger.debug("submitType="+submitType);
		if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
		  eventType=PLApplicationEvent.DE_SAVE_DRAFT;
		}else if(OrigConstant.SUBMIT_TYPE_SEND.equals(submitType)){
		  eventType=PLApplicationEvent.DE_SUBMIT;
		  //for test
		  logger.debug("DE_SUBMIT");
		  logger.debug("plApplicationDataM.getApplicationStatus()= "+plApplicationDataM.getApplicationStatus());
		  plApplicationDataM.setApplicationStatus(null);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return FrontController.ACTION;
	}
	public String getNextActionParameter() {
		return "page=DE_PL_SUMMARY_SCREEN";
	}

	@Override
	protected void doSuccess(EventResponse erp) {
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		super.doFail(erp);
	}


	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
