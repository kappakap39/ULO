/*
 * Created on Dec 17, 2007
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

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.FicoM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.FICOEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveFicoScoreWebAction
 */
public class SaveFicoScoreWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(SaveFicoScoreWebAction.class);
	FicoM ficoM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		FICOEvent ficoEvent = new FICOEvent();
		
		ficoEvent.setEventType(FICOEvent.FICO_SAVE);
		log.debug("FICOEvent.FICO_SAVE=" + FICOEvent.FICO_SAVE);
		ficoEvent.setObject(ficoM);
		log.debug("ficoM=" + ficoM);
		log.debug("ficoEvent=" + ficoEvent);
		
		return ficoEvent;
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
		
		String accept = getRequest().getParameter("accept");
		String reject = getRequest().getParameter("reject");
		
		log.debug("accept = "+accept);
		log.debug("reject = "+reject);
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		
		ficoM = new FicoM();
		
		ficoM.setAcceptScore(utility.stringToDouble(accept));
		ficoM.setRejectScore(utility.stringToDouble(reject));
		ficoM.setUpdateBy(userName);
		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    public String getNextActionParameter() {
    	
        return "action=GetLastFicoInfo";
    }
	
	protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		String svComplete = "Completed";
		getRequest().getSession().setAttribute("COMPLETED",svComplete);
		
		//***Refresh Cache
		log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("FICOErrorCode");
		com.eaf.cache.TableLookupCache.refreshCache("FICOScore");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
