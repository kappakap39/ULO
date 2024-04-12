package com.eaf.orig.ulo.formcontrol.view.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.j2ee.pattern.view.form.TabHandleManager;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class LoadFormWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(LoadFormWebAction.class);
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
	public boolean preModelRequest() {
		String formId = getRequest().getParameter("formId");
		String roleId = getRequest().getParameter("roleId");
		logger.debug("formId >> "+formId);
		logger.debug("roleId >> "+roleId);
		
		FlowControlDataM flowControl = (FlowControlDataM) getRequest().getSession().getAttribute(SessionControl.FlowControl);
			flowControl.setRole(roleId);
//		Create FormControlDataM
		FormControlDataM formControlM = new FormControlDataM();
			formControlM.setFormId(formId);
			formControlM.setRoleId(roleId);		
			formControlM.setRequest(getRequest());
			formControlM.setResponse(getResponse());
//		Get FormHandler
		FormHandler currentForm = FormControl.getFormHandler(formControlM);
		String formHandlerName = currentForm.getFormName();		
		logger.debug("formHandlerName >> "+formHandlerName);
//		Create Tab Instant Session.
		TabHandleManager tabHandlerManager = (TabHandleManager) getRequest().getSession().getAttribute("tabHandlerManager");
		if(null == tabHandlerManager){
			tabHandlerManager = new TabHandleManager();
			getRequest().getSession().setAttribute("tabHandlerManager",tabHandlerManager);
		}
		tabHandlerManager.clear();
		tabHandlerManager.setTabId(formHandlerName);		
//		Create Form Instant Session.
		FormHandleManager formHandlerManager = (FormHandleManager) getRequest().getSession().getAttribute("formHandlerManager");
		if(null == formHandlerManager){
			formHandlerManager = new FormHandleManager();
			getRequest().getSession().setAttribute("formHandlerManager",formHandlerManager);
		}
		formHandlerManager.clear();
		formHandlerManager.setCurrentFormHandler(formHandlerName);
//		set currentForm Session
		getRequest().getSession().setAttribute(formHandlerName,currentForm);		
		return true;
	}
	@Override
	public int getNextActivityType() {
		return 0;
	}
}
