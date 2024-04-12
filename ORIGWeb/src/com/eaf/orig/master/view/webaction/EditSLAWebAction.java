/*
 * Created on Jan 23, 2008
 * Created by Weeraya
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
import com.eaf.orig.shared.model.SLADataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Weeraya
 *
 * Type: SaveSLAWebAction
 */
public class EditSLAWebAction extends WebActionHelper implements
		WebAction {

	static Logger logger = Logger.getLogger(EditSLAWebAction.class);
	private String nextAction = null;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
        try {
        	ORIGMasterFormHandler formHandler = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm"); 
    		ValueListM valueListM = (ValueListM) getRequest().getSession().getAttribute("VALUE_LIST");
    		String qname = getRequest().getParameter("qname");
    		String role = getRequest().getParameter("role");
    		logger.debug("qnamer = "+qname);
    		logger.debug("role = "+role);
    		
    		Vector slaVectResult = valueListM.getResult();
    		Vector slaVectData;
    		SLADataM dataM = new SLADataM();
    		ORIGUtility utility = new ORIGUtility();
    		for(int i=1; i<slaVectResult.size(); i++){
    			slaVectData = (Vector) slaVectResult.get(i);
    			logger.debug("slaVectData.elementAt(1) = "+slaVectData.elementAt(1));
    			logger.debug("slaVectData.elementAt(5) = "+slaVectData.elementAt(5));
    			if(slaVectData.elementAt(1).equals(qname) && slaVectData.elementAt(5).equals(role)){
    				dataM = new SLADataM();
    				dataM.setQName((String) slaVectData.elementAt(1));
    				dataM.setAppStatus((String) slaVectData.elementAt(2));
    				dataM.setAction((String) slaVectData.elementAt(3));
    				dataM.setJobState((String) slaVectData.elementAt(4));
    				dataM.setRole((String) slaVectData.elementAt(5));
    				dataM.setTime(utility.stringToDouble((String) slaVectData.elementAt(6)));
    			}
    		} 
    		formHandler.setSlaDataM(dataM);
    		formHandler.setShwAddFrm("edit");
            
       } catch (Exception e) {
           logger.error("exception ",e);
       }
		return true;
	}

	/* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.PAGE;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
