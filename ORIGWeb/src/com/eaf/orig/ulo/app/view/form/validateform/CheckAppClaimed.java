package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;

public class CheckAppClaimed extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(CheckAppClaimed.class);

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		
		FormErrorUtil formError = new FormErrorUtil();

		// Get Application Group from SESSION
		// ORIGFormHandler ORIGForm = (ORIGFormHandler)
		// request.getSession().getAttribute("ORIGForm");
		String applicationGroupId = request.getParameter("applicationGroupId");
		String claimedBy = ORIGDAOFactory.getApplicationGroupDAO().getClaimBy(applicationGroupId);
		logger.debug("APP : " + applicationGroupId + " Claimed by " + claimedBy);
		if (!Util.empty(claimedBy)) {
			String errMsg = String.format(MessageErrorUtil.getText(request, "ERROR_APP_CLAIMED"), claimedBy);
			formError.error(errMsg);
		}
		return formError.getFormError();
	}
}
