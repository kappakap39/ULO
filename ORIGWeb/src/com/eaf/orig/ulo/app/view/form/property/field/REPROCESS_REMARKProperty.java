package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;

public class REPROCESS_REMARKProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(REPROCESS_REMARKProperty.class);
	@Override
	public boolean invokeValidateFlag(){
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
	public HashMap<String, Object> validateForm(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String REPROCESS_REMARK = request.getParameter("REPROCESS_REMARK");
		logger.debug("REPROCESS_REMARK : " + REPROCESS_REMARK);
		if (Util.empty(REPROCESS_REMARK)) {
			formError.mandatoryElement("REPROCESS_REMARK", "REPROCESS_REMARK", objectForm, request);
		}
		return formError.getFormError();
	}
}
