package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ValidateAddSuplementary implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ValidateAddSuplementary.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VALIDATE_ADD_SUPLEMENTARY);
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String classid = request.getParameter("subid");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM mainCardPersonal =applicationGroup.getPersonalInfo(SystemConstant.getConstant("PERSONAL_TYPE_INFO"));
		PersonalInfoDataM supCardPersonal =applicationGroup.getPersonalInfo(SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY"));
//		PERSONAL_TYPE_SUPPLEMENTARY
		try {				
			if(!Util.empty(mainCardPersonal)&&!Util.empty(supCardPersonal)){
				logger.debug("mainCISNO :"+mainCardPersonal.getCisNo());
				logger.debug("subCISNO :"+supCardPersonal.getCisNo());
				if(mainCardPersonal.getCisNo().equals(supCardPersonal.getCisNo())){
					return responseData.error(MessageErrorUtil.getText("ERROR_MAIN_CARD"));
					}
				}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			return responseData.error(e);
		}
		return responseData.success();
	}

}
