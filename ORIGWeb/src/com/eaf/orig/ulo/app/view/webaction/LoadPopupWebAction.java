package com.eaf.orig.ulo.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class LoadPopupWebAction extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(LoadPopupWebAction.class);
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
		ResponseDataController controller = new ResponseDataController(getRequest(),ResponseData.FunctionId.LOAD_POPUP);	
		try{
			String formId = getRequest().getParameter("formId");
			String mode = getRequest().getParameter("mode");
			String rowId = getRequest().getParameter("rowId");	
			String roleId = FormControl.getFormRoleId(getRequest());		
			logger.debug("formId >> "+formId);
			logger.debug("mode >> "+mode);
			logger.debug("rowId >> "+rowId);		
			logger.debug("roleId >> "+roleId);				
			FormControlDataM formControlM = new FormControlDataM();
				formControlM.setFormId(formId);
				formControlM.setRoleId(roleId);		
				formControlM.setRequest(getRequest());
				formControlM.setResponse(getResponse());	
				formControlM.setMode(mode);
				formControlM.setRowId(rowId);
				formControlM.setFormType(FormHandler.POPUP_FORM);
//			Get FormHandler
			FormHandler currentForm = FormControl.getFormHandler(formControlM);
			String formHandlerName = currentForm.getFormName();		
			logger.debug("formHandlerName >> "+formHandlerName);
			getRequest().getSession().setAttribute(formHandlerName,currentForm);
			FormHandleManager formHandlerManager = (FormHandleManager) getRequest().getSession().getAttribute("formHandlerManager");
			if(null == formHandlerManager){
				formHandlerManager = new FormHandleManager();
				getRequest().getSession().setAttribute("formHandlerManager",formHandlerManager);
			}
			formHandlerManager.setCurrentFormHandler(formHandlerName);
			controller.success(getResponse());
		}catch(Exception e){
			logger.fatal("ERROR",e);
			controller.error(getResponse(),e);
		}
		return true;
	}
	@Override
	public int getNextActivityType(){
		return FrontController.POPUP_DIALOG;
	}
}
