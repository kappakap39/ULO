/*
 * Created on Nov 27, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.master.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.RunParamEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: UpdateRunParamWebAction
 */
public class UpdateRunParamWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(UpdateRunParamWebAction.class);
	private RunningParamM runnParamM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		RunParamEvent runParamEvent = new RunParamEvent();
		
		runParamEvent.setEventType(RunParamEvent.RUN_PARAM_UPDATE);
		log.debug("RunParamEvent.RUN_PARAM_UPDATE=" + RunParamEvent.RUN_PARAM_UPDATE);
		runParamEvent.setObject(runnParamM);
		log.debug("runnParamM=" + runnParamM);
		log.debug("runParamEvent=" + runParamEvent);
		
		return runParamEvent;
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
		ORIGUtility utility = new ORIGUtility();
		String prmID = getRequest().getParameter("prmID");
		String prmdesc = getRequest().getParameter("prmdesc");
		String paramType = getRequest().getParameter("paramType");
		String runningFrom = getRequest().getParameter("runningFrom");
		String runningTo = getRequest().getParameter("runningTo");
		String value1 = getRequest().getParameter("value1");
		String value2 = getRequest().getParameter("value2");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		 
		log.debug("prmID ="+prmID);
		 log.debug("prmdesc ="+prmdesc);
		 log.debug("paramType ="+paramType);
		 log.debug("runningFrom ="+runningFrom);
		 log.debug("runningTo ="+runningTo);
		 log.debug("value1 ="+value1);
		 log.debug("value2 ="+value2);
		 log.debug("userName ="+userName);
		 		 
		 runnParamM = new RunningParamM(); 
		 
		 runnParamM.setParamID(prmID);
		 runnParamM.setParamDesc(prmdesc);
		 runnParamM.setParamType(paramType);
		 runnParamM.setValueFrom(utility.stringToDouble((runningFrom)));
		 runnParamM.setValueTo(utility.stringToDouble((runningTo)));
		 runnParamM.setValue1(utility.stringToDouble((value1)));
		 runnParamM.setValue2(utility.stringToDouble((value2)));
		 runnParamM.setUpdateBy(userName);
		
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (runningFrom==null||"".equals(runningFrom)){
				origMasterForm.getFormErrors().add("Running From is required");
			}
			if (runningTo==null||"".equals(runningTo)){
				origMasterForm.getFormErrors().add("Running To is required");
			}
			if ( value1==null||"".equals(value1)){
				origMasterForm.getFormErrors().add("Value1 is required");
			}
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("EDIT_RUN_PARAM_DATAM",runnParamM);
				return false;
			}else {
				return true;
			}
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchRunningParam";
    }
    
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
//		***Refresh Cache
//		log.debug("i'm refreshing Cache.!!!" );
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
