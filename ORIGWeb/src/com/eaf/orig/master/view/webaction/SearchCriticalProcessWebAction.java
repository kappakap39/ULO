/*
 * Created on Dec 19, 2007
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.master.view.webaction;

import java.util.Vector;
import org.apache.log4j.Logger;
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
public class SearchCriticalProcessWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(SearchCriticalProcessWebAction.class);

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
		
		try {
//			OrigMasterManager origMasterEJB = ORIGEJBService.getOrigMasterManager();
//			double updatedRow = origMasterEJB.updateBeforeSearchCriticalProcess();
//			if(updatedRow>0){
				ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndiWF);
				Vector vWorkListM = OrigManager.queryCriticalProcesses("25");
				getRequest().getSession().setAttribute("CriticalProcessList",vWorkListM);
//			}
		} catch (Exception e) {
			log.error("##### SearchCriticalProcess #####",e);
		}
		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
