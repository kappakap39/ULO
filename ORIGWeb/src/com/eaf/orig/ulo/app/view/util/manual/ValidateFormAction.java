package com.eaf.orig.ulo.app.view.util.manual;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;

public class ValidateFormAction extends MandatoryForm{
	private static transient Logger logger = Logger.getLogger(ValidateFormAction.class);
	public static final String ValidateForm = "ValidateForm";	
	public String DISPLAY_ERROR_POPUP = SystemConstant.getConstant("DISPLAY_ERROR_POPUP");
	public String DISPLAY_ERROR_ALERT = SystemConstant.getConstant("DISPLAY_ERROR_ALERT");
	public String DISPLAY_ERROR_SCREEN = SystemConstant.getConstant("DISPLAY_ERROR_SCREEN");
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VALIDATE_FORM_ACTION);
		String data = "";
		try{
			String validateId = request.getParameter("validateId");
			logger.debug("validateId >> "+validateId);
			ValidateFormInf validateInf = ImplementControl.getValidateForm(ValidateForm,validateId);			
			if(null != validateInf){
				JSONUtil errorForm = new JSONUtil();
				String validateFlag = validateInf.validateFlag(request,null);
				if(ValidateFormInf.VALIDATE_YES.equals(validateFlag)){
					HashMap<String,Object> errorForms = validateInf.validateForm(request,null);
					if(!Util.empty(errorForms.get("STATUS_CODE")) && ResponseData.SYSTEM_EXCEPTION.equals((String)errorForms.get("STATUS_CODE"))){
					errorForm.put("ERROR",createErrorMsg(errorForms));
					errorForm.put("DISPLAY_ERROR_TYPE",DISPLAY_ERROR_SCREEN);
					errorForm.put("FORM_ID",FormControl.getFormId(request));
					}else{
					errorForm.put("ERROR",createErrorMsg(errorForms));
					errorForm.put("ELEMENT",createElement(errorForms));
					errorForm.put("SUCCESS",createSuccessElement(errorForms));
					}
				}
				data = errorForm.getJSON();
			}
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createElement(HashMap<String,Object> errors){
		JSONUtil json = new JSONUtil();
		ArrayList<String> $element = (ArrayList)errors.get(FormErrorUtil.ELEMENT);
		logger.debug("$element >> "+$element);
		if(null != $element){
			for (String elementId : $element) {
				json.put(elementId,elementId);
			}
		}
		String ELEMENT = json.getJSON();
		logger.debug("ELEMENT >> "+ELEMENT);
		return ELEMENT;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createSuccessElement(HashMap<String,Object> errors){
		JSONUtil json = new JSONUtil();
		ArrayList<String> $element = (ArrayList)errors.get(FormErrorUtil.SUCCESS);
		logger.debug("$element >> "+$element);
		if(null != $element){
			for (String elementId : $element) {
				json.put(elementId,elementId);
			}
		}
		String SUCCESS = json.getJSON();
		logger.debug("SUCCESS >> "+SUCCESS);
		return SUCCESS;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createErrorMsg(HashMap<String,Object> errors){
		StringBuilder errorMsg = new StringBuilder();
		ArrayList<String> $error = (ArrayList)errors.get(FormErrorUtil.ERROR);
		logger.debug("$error >> "+$error);
		if(null != $error){
			for (String error : $error) {
				errorMsg.append("<div>"+error+"</div>");
			}
		}
		logger.debug("ERROR MSG >> "+errorMsg);
		return errorMsg.toString();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createErrorMsgByKey(HashMap<String,Object> errors,String key){
		StringBuilder errorMsg = new StringBuilder();
		ArrayList<String> $error = (ArrayList)errors.get(key);
		logger.debug("$error >> "+$error);
		if(null != $error){
			for (String error : $error) {
				errorMsg.append("<div>"+error+"</div>");
			}
		}
		logger.debug("ERROR MSG >> "+errorMsg);
		return errorMsg.toString();
	}
}
