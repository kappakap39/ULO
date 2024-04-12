package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.model.app.ReasonDataM;

public class ValidateCancelApplication extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateCancelApplication.class);

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		
		logger.debug("ValidateCancelApplication.validateForm");
		FormErrorUtil formError = new FormErrorUtil();
		
		String REASON_CODE = request.getParameter("REASON_CODE"); 
		String REASON_OTH_DESC = request.getParameter("REASON_OTH_DESC");

		String subFormId = "POPUP_CALLCENTER_CANCEL_REASON_SUBFORM";
		ReasonDataM reasonM = new ReasonDataM();
			reasonM.setReasonCode(REASON_CODE);
			reasonM.setReasonOthDesc(REASON_OTH_DESC);
		formError.mandatory(subFormId, "REASON", reasonM, request);
		return formError.getFormError();
	}
}
