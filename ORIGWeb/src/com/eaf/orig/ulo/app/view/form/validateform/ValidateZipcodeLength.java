package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;

public class ValidateZipcodeLength extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateProvinceLength.class);

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		logger.debug("ValidateZipcodeLength.validateForm");
		FormErrorUtil formError = new FormErrorUtil();
		String ERROR_LENGTH_OF_FIELD_SEARCH_ZIPCODE = MessageErrorUtil.getText("ERROR_LENGTH_OF_FIELD_SEARCH_ZIPCODE");
		formError.error("SEARCH_ZIPCODE", ERROR_LENGTH_OF_FIELD_SEARCH_ZIPCODE);
		return formError.getFormError();
		
	}
}