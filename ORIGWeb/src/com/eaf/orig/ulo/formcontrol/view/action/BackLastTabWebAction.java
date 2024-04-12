package com.eaf.orig.ulo.formcontrol.view.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.TabHandleManager;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class BackLastTabWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(BackLastTabWebAction.class);
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
		ResponseDataController controller = new ResponseDataController(getRequest(),ResponseData.FunctionId.BACK_LAST_TAB);		
		try{
			TabHandleManager tabHandlerManager = (TabHandleManager) getRequest().getSession().getAttribute("tabHandlerManager");
			if(null != tabHandlerManager){
				tabHandlerManager.reverseTab();
			}
			FormHandleManager formHandlerManager = (FormHandleManager) getRequest().getSession().getAttribute("formHandlerManager");
			if(null != formHandlerManager){
				formHandlerManager.reverseFormHandle();
			}
			controller.success(getResponse());
		}catch(Exception e){
			logger.fatal("ERROR",e);
			controller.error(getResponse(),e);
		}
		return true;
	}
	@Override
	public int getNextActivityType(){
		return FrontController.NOTFORWARD;
	}	
}
