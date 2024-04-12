package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class ValidateAddCardRequestInfo extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateAddCardRequestInfo.class);

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		int MAX_ADD_SUP_CARD = Integer.parseInt(SystemConfig.getGeneralParam("MAX_ADD_SUP_CARD"));
		// Get Application Group from SESSION
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		// Get Personal Info by Type PERSONAL_TYPE_SUPPLEMENTARY
		List<ApplicationDataM> Applications = applicationGroup.getApplications();
		int ApplicationsCount = Applications.size();
		logger.debug("ApplicationsCount : " + ApplicationsCount);

		if (ApplicationsCount >= MAX_ADD_SUP_CARD) {
			formError.error(MessageUtil.getText(request, "MSG_MAX_CARD"));
		}
		return formError.getFormError();
	}
}
