package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;

public class ControlFormHandle implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ControlFormHandle.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.CONTROL_FORM_HANDLE);
		String data = null;
		try{
			FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
			if(null == formHandleManager){
				formHandleManager = new FormHandleManager();
			}
			formHandleManager.reverseFormHandle();
			String formName = formHandleManager.getCurrentFormHandler();
			logger.debug("formName >> "+formName);
			data = formName;
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
