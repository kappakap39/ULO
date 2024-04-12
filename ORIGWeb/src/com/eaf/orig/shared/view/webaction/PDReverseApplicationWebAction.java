/*
 * Created on Mar 25, 2008
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.control.event.ApplicationEvent;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Avalant
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */          
public class PDReverseApplicationWebAction extends WebActionHelper implements
		WebAction {
	Logger logger = Logger.getLogger(PDReverseApplicationWebAction.class);
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		  UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			if(null == userM){
				userM = new UserDetailM();
			}
			
			ORIGFormHandler formHandler=(ORIGFormHandler)getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
			ApplicationDataM applicationDataM = formHandler.getAppForm();
			if(ORIGUtility.isEmptyString(applicationDataM.getCreateBy())){
				applicationDataM.setCreateBy(userM.getUserName());
				applicationDataM.setUpdateBy(userM.getUserName());
			}else{
				applicationDataM.setUpdateBy(userM.getUserName());
			}
			ApplicationEvent event = new ApplicationEvent(ApplicationEvent.PD_REVERSE, applicationDataM, userM);
		return event;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		 
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		 
		return defaultProcessResponse(response);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		 
		logger.debug("PDReverseApplicationWebAction Call");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
	 
		return FrontController.PAGE;
	}

	 
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#doSuccess(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	protected void doSuccess(EventResponse erp) {
		logger.debug("In doSuccess PDReverseApplicationWebAction");
    	ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	ApplicationDataM applicaitonDataM=(ApplicationDataM)erp.getEncapData();
    	formHandler.setAppForm(applicaitonDataM);
	}
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
