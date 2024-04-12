package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class LoadSubForm implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(LoadSubForm.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.LOAD_SUBFORM);
		String data = null;
		try{
			String functionId = request.getParameter("functionId");
			String subformId = request.getParameter("subformId");
			String formId = request.getParameter("formId");
			String handleSubform = request.getParameter("handleSubform");
			logger.debug("functionId >> "+functionId);
			logger.debug("subformId >> "+subformId);
			logger.debug("formId >> "+formId);
			logger.debug("handleSubform >> "+handleSubform);
			if("Y".equals(handleSubform)&&!Util.empty(subformId)){
				ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
				ORIGSubForm subform = FormControl.getSubformObject(subformId);
				if(null != subform){
					subform.setProperties(request, applicationGroup);
				}
			}else{
				FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
				if(null == formHandleManager){
					formHandleManager = new FormHandleManager();
				}		
				String formName = formHandleManager.getCurrentFormHandler();
				logger.debug("formName >> "+formName);
				FormHandler currentForm = (FormHandler) (request.getSession(true).getAttribute(formName));
				if(null != currentForm){
					if("formHandleAction".equals(functionId)){
						currentForm.setProperties(request);
						data = formId;
					}else{
						currentForm.setProperties(request,subformId);
						data = subformId;
					}
				}
			}
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
