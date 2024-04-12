package com.eaf.orig.ulo.app.view.util.manual;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FormErrorDataM;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;

public class MandatoryForm implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(MandatoryForm.class);
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.MANDATORY_FORM);
		String data = "";
		try {
			FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
			if(null == formHandleManager){
				formHandleManager = new FormHandleManager();
			}		
			String formName = formHandleManager.getCurrentFormHandler();
			logger.debug("formName >> "+formName);
			FormHandler currentForm = (FormHandler) (request.getSession(true).getAttribute(formName));
			if(null != currentForm){
				currentForm.setProperties(request);
				boolean processForm = currentForm.processForm(request);
				logger.debug("processForm >> "+processForm);
				if(!processForm){
					JSONUtil errorForm = new JSONUtil();
					ArrayList<FormErrorDataM> errorForms = currentForm.getErrorForm();
					logger.debug("errorForms >> "+errorForms);
					if(null != errorForms){
						errorForm.put("ERROR",createErrorMsg(errorForms));
						errorForm.put("ELEMENT",createElement(errorForms));
					}
					data = errorForm.getJSON();
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			return responseData.error(e);
		}
		logger.debug("ERROR >> "+data);
		return responseData.success(data);
	}
	public String createElement(ArrayList<FormErrorDataM> errorForms){
		JSONUtil json = new JSONUtil();
		for (FormErrorDataM formError:errorForms){
			String subFormId = formError.getSubformId();
			logger.debug("subFormId >> "+subFormId);
			ArrayList<String> $element = (ArrayList)formError.getErrors().get(FormErrorUtil.ELEMENT);
			logger.debug("$element >> "+$element);
			if(null != $element){
				for (String elementId : $element) {
					json.put(elementId,elementId);
				}
			}
		}
		String ELEMENT = json.getJSON();
		logger.debug("ELEMENT >> "+ELEMENT);
		return ELEMENT;
	}
	public String createErrorMsg(ArrayList<FormErrorDataM> errorForms){
		StringBuilder errorMsg = new StringBuilder();
		for (FormErrorDataM formError:errorForms){
			String subFormId = formError.getSubformId();
			logger.debug("subFormId >> "+subFormId);
			ArrayList<String> $error = (ArrayList)formError.getErrors().get(FormErrorUtil.ERROR);
			logger.debug("$error >> "+$error);
			if(null != $error){
				for (String error : $error) {
					errorMsg.append("<div>"+error+"</div>");
				}
			}
		}
		logger.debug("ERROR MSG >> "+errorMsg);
		return errorMsg.toString();
	}
}
