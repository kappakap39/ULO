package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;

public class FRAUD_REMARKProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(FRAUD_REMARKProperty.class);

	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public boolean invokeValidateForm() {
		return true;
	}

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}
	
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();

		
		String FRAUD_REMARK = request.getParameter("FRAUD_REMARK");
		logger.debug("FRAUD_REMARK : " + FRAUD_REMARK);
		
		if (Util.empty(FRAUD_REMARK)) {
			formError.mandatoryElement("FRAUD_REMARK", "FRAUD_REMARK", objectForm, request);
		}

		return formError.getFormError();
	}

}
