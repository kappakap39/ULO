/*
 * Created on Dec 19, 2007
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.master.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.wf.service.ejb.ORIGWFServiceManager;

/**
 * @author Joe
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecoveryAppWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(RecoveryAppWebAction.class);
	String action="action=SearchCriticalProcess";;
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
		String providerUrlWF = (String)LoadXML.getServiceURL().get("ORIGWF");
		String jndiWF = (String)LoadXML.getServiceJNDI().get("ORIGWF");
		
		String[] recoveryApp = getRequest().getParameterValues("recoveryChk");
//		String[] processName = getRequest().getParameterValues("processName");
		String recoveryType = getRequest().getParameter("recoveryType");
		Vector errorApp = new Vector();
		
		try {
			ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndiWF);
			
			if(recoveryApp!=null && recoveryApp.length>0){
				for(int i=0; i<recoveryApp.length; i++){
					String[] app = recoveryApp[i].split("#");
					log.debug(">> processName = ["+i+"] "+app[1]);
					log.debug(">> recoveryApp = ["+i+"] "+app[0]);
					try{
						if(recoveryType!=null && "complete".equalsIgnoreCase(recoveryType)){
							OrigManager.forceCompleteApplication(app[0]);
						}else{
							OrigManager.forceRetryApplicaion(app[0]);
						}
					}catch(Exception ex){
						errorApp.add(app[1]);
					}
				}
				if(errorApp.size()>0){
					getRequest().getSession().setAttribute("RetryErr",errorApp);
				}
			}
			
		} catch (Exception e) {
			log.error("##### SearchCriticalProcess #####",e);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return FrontController.ACTION;
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#setNextActionParameter(java.lang.String)
	 */
	public String getNextActionParameter() {
		return action;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
