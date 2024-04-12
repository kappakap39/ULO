package com.eaf.orig.ulo.formcontrol.view.action;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class LoadSubFormWebAction extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(LoadSubFormWebAction.class);
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
		String SUBFORM_ID = getRequest().getParameter("SUBFORM_ID");
		String ROLE_ID = getRequest().getParameter("ROLE_ID");
		logger.debug("SUBFORM_ID >> "+SUBFORM_ID);
		logger.debug("ROLE_ID >> "+ROLE_ID);
		FlowControlDataM flowControl = (FlowControlDataM) getRequest().getSession().getAttribute(SessionControl.FlowControl);
			flowControl.setRole(ROLE_ID);
		ORIGFormHandler origFromHander = new ORIGFormHandler();
		ArrayList<ORIGSubForm> subForms = new ArrayList<ORIGSubForm>();
		SQLQueryEngine QueryEngine = new SQLQueryEngine();
		HashMap SubForm = QueryEngine.LoadModule("SELECT * FROM SC_SUBFORM WHERE SUBFORM_ID = ?",SUBFORM_ID);
		logger.debug("SubForm >> "+SubForm);
		try{
			ORIGSubForm subFrom = (ORIGSubForm)Class.forName(SQLQueryEngine.display(SubForm,"SUBFORM_CLASS_NAME")).newInstance();
				subFrom.setSubFormClass(SQLQueryEngine.display(SubForm,"SUBFORM_CLASS_NAME"));
				subFrom.setSubFormID(SQLQueryEngine.display(SubForm,"SUBFORM_ID"));
				subFrom.setSubformDesc(SQLQueryEngine.display(SubForm,"SUBFORM_DESC"));
				subFrom.setFileName(SQLQueryEngine.display(SubForm,"SUBFORM_FILE_NAME"));
			subForms.add(subFrom);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		origFromHander.setSubFormId(SUBFORM_ID);
		origFromHander.setSubForm(subForms);
		getRequest().getSession().setAttribute(ORIGFormHandler.ORIGForm,origFromHander);
		return true;
	}
	@Override
	public int getNextActivityType(){
		return 0;
	}
}
