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

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.j2ee.system.LoadXML;
//import com.eaf.orig.scheduler.dao.Scheduler.SchedulerDAO;
import com.eaf.orig.shared.control.event.QTimeEvent;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.wf.service.ejb.ORIGWFServiceManager;

/**
 * @author Administrator
 *
 * Type: DeleteQTimeWebAction
 */
public class DeleteQTimeWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(DeleteQTimeWebAction.class);
	String[] qTimeIDToDelete;
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		QTimeEvent qTimeEvent = new QTimeEvent();	
		qTimeEvent.setEventType(QTimeEvent.Q_TIME_DELETE);
		
		log.debug("QTimeEvent.Q_TIME_DELETE=" + QTimeEvent.Q_TIME_DELETE);
		
		qTimeEvent.setObject(qTimeIDToDelete);
		
		log.debug("qTimeIDToDelete = " + qTimeIDToDelete);
		log.debug("qTimeEvent=" + qTimeEvent);
		
		return qTimeEvent;
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
		qTimeIDToDelete = getRequest().getParameterValues("qTimeoutChk");
		
	    if(qTimeIDToDelete == null || qTimeIDToDelete.length<=0){
	    	log.debug("////// qTimeIDToDelete is null //////" );
	    	return false;
	    }
	    
		return true;
	}

	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    public String getNextActionParameter() {
    	
        return "action=SearchQueueTime";
    }
    protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
//    	log.debug("sorry i'm refreshing Cache.!!!" );
//		com.eaf.cache.TableLookupCache.refreshAll();
    	
    	/** Set Scheduler **/
    	String providerUrlWF = (String)LoadXML.getServiceURL().get("ORIGWF");
		String jndiWF = (String)LoadXML.getServiceJNDI().get("ORIGWF");
    	
		ORIGApplicationManager appManager = ORIGEJBService.getApplicationManager();
//		SchedulerDAO schedulerDAO = ORIGDAOFactory.getSchedulerDAO();
		try{
			appManager.deleteOldSchedulerTask("AutoCancel");
//			String startTime = schedulerDAO.loadWorkingTime("AUTO_CANCEL");
			
			String startTime = PLORIGEJBService.getORIGDAOUtilLocal().loadWorkingTime("AUTO_CANCEL");
			
			ORIGWFServiceManager schedManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndiWF);
			schedManager.StartScheduler(startTime,-1,1,"AutoCancel");
			log.info("##### Startup Scheduler success #####");
		}catch(Exception e){
			log.error("##### Startup Scheduler error #####",e);
		}
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
