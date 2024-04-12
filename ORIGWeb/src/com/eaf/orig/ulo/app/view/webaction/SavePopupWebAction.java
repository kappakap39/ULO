package com.eaf.orig.ulo.app.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class SavePopupWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SavePopupWebAction.class);
	@Override
	public Event toEvent() {		
		return null;
	}
	@Override
	public boolean requiredModelRequest(){		
		return false;
	}
	@Override
	public boolean processEventResponse(EventResponse response) {		
		return false;
	}
	@Override
	public boolean preModelRequest() {
		ResponseDataController controller = new ResponseDataController(getRequest(),ResponseData.FunctionId.SAVE_POPUP);
		String responseData = "";
		try{
			FormHandleManager formHandlerManager = (FormHandleManager) getRequest().getSession().getAttribute("formHandlerManager");
			String formName = formHandlerManager.getCurrentFormHandler();
			logger.debug("formName >> "+formName);
			FormHandler currentForm = (FormHandler) (getRequest().getSession(true).getAttribute(formName));			
			if(null != currentForm){
				responseData = processForm(currentForm);
			}
			logger.debug("responseData : "+responseData);
			controller.success(getResponse(),responseData);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			controller.error(getResponse(),e);
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public String processForm(FormHandler currentForm){
		String responseData = "";
		String formId = currentForm.getFormId();
		String rowId = currentForm.getRowId();
		String mode = currentForm.getMode();
		logger.debug("formId >> "+formId);
		HashMap<String,Object> Form = FormControl.getForm(formId);
		String className = SQLQueryEngine.display(Form,"FORM_CLASS_NAME");
		logger.debug("className >> "+className);
		if(!Util.empty(className)){
			FormAction formAction = null;
			try{
				formAction = (FormAction)Class.forName(className).newInstance();
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			if(null != formAction){
				formAction.setRequest(getRequest());
				formAction.setResponse(getResponse());
				formAction.setRowId(rowId);
				formAction.setMode(mode);
				formAction.setFormId(formId);
				formAction.setRequestData(currentForm.getRequestData());
				formAction.setObjectForm(currentForm.getObjectForm());
				formAction.setFormData(currentForm.getFormData());
				formAction.setUniqueIds(currentForm.getUniqueIds());
				responseData = formAction.processForm();
				logger.debug("responseData : "+responseData);
			}	
		}
		return responseData;
	}
	@Override
	public int getNextActivityType() {	
		return FrontController.NOTFORWARD;
	}
}
