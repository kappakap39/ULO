package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
//DF000000000594 0229 #112
public class PERSONAL_TYPEProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(PERSONAL_TYPEProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,
			Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
			logger.debug("properties Error Add sup : "+objectForm);
		if(Util.empty(objectForm)){
			logger.debug("ERROR_ADD_SUPPLEMENTARY : ");
			formError.error(MessageErrorUtil.getText("ERROR_ADD_SUPPLEMENTARY"));
		}
		return formError.getFormError();
	}
	
}
