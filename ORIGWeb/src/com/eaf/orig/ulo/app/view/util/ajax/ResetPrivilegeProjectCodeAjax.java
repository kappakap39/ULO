package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;

public class ResetPrivilegeProjectCodeAjax implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(ResetPrivilegeProjectCodeAjax.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.CHECK_ROLE_REASSIGN_TASK);
		String data = "VERIFY_PRIVILEGE_PROJECT_CODE_SUBFORM";
		try{
			EntityFormHandler entityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");		
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)entityForm.getObjectForm();	
			privilegeProjectCode.setPrivilegeProjectCodePrjCdes(new ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>());
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}

