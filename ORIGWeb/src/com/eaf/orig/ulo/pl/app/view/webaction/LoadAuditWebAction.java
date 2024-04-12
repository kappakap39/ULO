package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class LoadAuditWebAction extends WebActionHelper implements WebAction {

	static Logger log = Logger.getLogger(LoadAuditWebAction.class);	

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
	public boolean preModelRequest(){		
		String appRecId = getRequest().getParameter("appRecId");
		try{
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
			Vector<PLAuditTrailDataM> auditVect = origBean.loadOrigAuditTrailByRoleAppRecId(appRecId);
			getRequest().getSession().setAttribute("searchAudit", auditVect);
		}catch(Exception e){
			log.error("Exception ", e);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.POPUP_DIALOG;
	}
	
	@Override
	public boolean getCSRFToken(){
		return false;
	}
   
}