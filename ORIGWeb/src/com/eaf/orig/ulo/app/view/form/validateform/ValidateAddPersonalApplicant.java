package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ValidateAddPersonalApplicant extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateAddPersonalApplicant.class);

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String REQ_PERSONAL_TYPE =  request.getParameter("REQ_PERSONAL_TYPE");
		int MAX_APPLICANT = Integer.parseInt(SystemConfig.getGeneralParam("IA_MAX_APPLICANT"));
		int MAX_SUP = Integer.parseInt(SystemConfig.getGeneralParam("MAX_ADD_SUP_APPLICANT"));
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
		// Get Application Group from SESSION
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		// Get Personal Info by Type PERSONAL_TYPE_APPLICANT
		List<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(REQ_PERSONAL_TYPE);
		int personalCount = Util.empty(personalInfos)? 0: personalInfos.size();
		if(PERSONAL_TYPE_APPLICANT.equals(REQ_PERSONAL_TYPE)) {
			logger.debug("personalCount : " + personalCount);
			if (personalCount >= MAX_APPLICANT) {
				formError.error(MessageUtil.getText(request, "MAIN_PERSONAL_EXCEED"));
			}
		}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(REQ_PERSONAL_TYPE)) {
			logger.debug("suppersonalCount : " + personalCount);
			if (personalCount >= MAX_SUP) {
				formError.error(MessageUtil.getText(request, "SUP_PERSONAL_EXCEED"));
			}
		}
		return formError.getFormError();
	}
}
