package com.eaf.orig.ulo.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.NotifyDataM;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.control.event.DMEvent;
import com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class SaveDMStoreWebAction extends WebActionHelper implements WebAction  {
	private static transient Logger logger = Logger.getLogger(SaveDMStoreWebAction.class);
	@Override
	public Event toEvent() {
		DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(getRequest());
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		return new DMEvent(DMEvent.DM_SAVE_STORE,dmManageDataM,userM);
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
		return true;
	}
	@Override
	public int getNextActivityType() {
		return 0;
	}
	@Override
	protected void doSuccess(EventResponse erp) {
		try {
			SearchQueryEngine SearchEngine = new SearchQueryEngine();
			SearchEngine.refresh(getRequest());
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	
	@Override
	protected void doFail(EventResponse erp) {
		NotifyDataM notify = erp.getNotify();
		if(null != notify){
			NotifyForm.error(getRequest(), notify);
		}else{
			NotifyForm.error(getRequest());
		}
	}
}
