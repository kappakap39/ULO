package com.eaf.orig.ulo.app.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.j2ee.pattern.view.form.TabHandleManager;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

@SuppressWarnings("serial")
public class LoadOldAppWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(LoadOldAppWebAction.class);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean preModelRequest() {
		String applicationGroupId = getRequest().getParameter("APPLICATION_GROUP_ID");
		String TASK_ID = getRequest().getParameter("TASK_ID");
		String APPLICATION_TYPE = getRequest().getParameter("APPLICATION_TYPE");
		String JOB_STATE = getRequest().getParameter("JOB_STATE");
		String APPLICATION_TEMPLATE = getRequest().getParameter("APPLICATION_TEMPLATE");
		
		FlowControlDataM flowControl = (FlowControlDataM) getRequest().getSession().getAttribute(SessionControl.FlowControl);
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String roleId = FormControl.getFormRoleId(getRequest());
		String transactionId = roleId+"_"+applicationGroupId;
		logger.debug("transactionId >> "+transactionId);
		TraceController trace = new TraceController(this.getClass().getName(),transactionId);
		getRequest().getSession().setAttribute("transactionId",transactionId);
		logger.debug("flowControl.getActionType() >> " + flowControl.getActionType());
		logger.debug("APPLICATION_GROUP_ID >> "+applicationGroupId);	
		logger.debug("TASK_ID >> "+TASK_ID);
		logger.debug("roleId >> "+roleId);
		FormControlDataM formControlM = new FormControlDataM();
		
		HashMap objectRequestForm = new HashMap();
		objectRequestForm.put("APPLICATION_GROUP_ID",applicationGroupId);
		objectRequestForm.put("TASK_ID",TASK_ID);
		objectRequestForm.put("APPLICATION_TYPE",APPLICATION_TYPE);
		objectRequestForm.put("JOB_STATE",JOB_STATE);
		objectRequestForm.put("APPLICATION_TEMPLATE",APPLICATION_TEMPLATE);
		
		String formId;
		
		//Load OLD_DATA_FORM
		formId = "OLD_DATA_FORM";
		logger.debug("Load.formId >> "+formId);
		
		formControlM.setFormId(formId);
		formControlM.setRoleId(roleId);
		formControlM.setRequest(getRequest());
		formControlM.setResponse(getResponse());
		
		trace.create("LoadApplication");
		try{
		FormHandler currentForm = FormControl.getFormHandlerAppGroup(formControlM);
		trace.end("LoadApplication");
		String formHandlerName = currentForm.getFormName();
		logger.debug("formHandlerName >> "+formHandlerName);	
		
		//Create Tab Instant Session.
		TabHandleManager tabHandlerManager = (TabHandleManager) getRequest().getSession().getAttribute("tabHandlerManager");
		if(null == tabHandlerManager){
			tabHandlerManager = new TabHandleManager();
			getRequest().getSession().setAttribute("tabHandlerManager",tabHandlerManager);
		}
		tabHandlerManager.clear();
		tabHandlerManager.setTabId(formHandlerName);	
		
		//Create Form Instant Session.
		FormHandleManager formHandlerManager = (FormHandleManager) getRequest().getSession().getAttribute("formHandlerManager");
		if(null == formHandlerManager){
			formHandlerManager = new FormHandleManager();
			getRequest().getSession().setAttribute("formHandlerManager",formHandlerManager);
		}
		formHandlerManager.clear();
		formHandlerManager.setCurrentFormHandler(formHandlerName);
		
		//Set currentForm Session
		getRequest().getSession().setAttribute(formHandlerName,currentForm);	
		
		//createOrigEnquiryLog removed due to absent of constraint APPLICATION_GROUP_ID(deledted)
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			NotifyForm.error(getRequest(),MessageErrorUtil.getText("ERROR_LOAD_APPLICATION"));
			return false;
		}
		trace.trace();
		return true;
	}
	@Override
	public int getNextActivityType() {
		return 0;
	}
}
