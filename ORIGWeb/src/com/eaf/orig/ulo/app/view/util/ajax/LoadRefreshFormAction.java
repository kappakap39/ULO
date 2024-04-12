package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class LoadRefreshFormAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(LoadRefreshFormAction.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.LOAD_REFRESH_FORM_ACTION);	
		try{
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			String roleId = FormControl.getFormRoleId(request);
			String applicationType = applicationGroup.getApplicationType();
			logger.debug("roleId >> "+roleId);
			logger.debug("applicationType >> "+applicationType);
			String formId = FormControl.getMasterFormId(applicationType, roleId, request);
			logger.debug("formId >> "+formId);
			FormControl.refreshFromAction(formId,ORIGForm,request);
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
