package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

public class REFERENCE_NAMEProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(REFERENCE_NAMEProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("REFERENCE_NAMEProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		ReferencePersonDataM referencePerson = (ReferencePersonDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(referencePerson.getThTitleCode()) || Util.empty(referencePerson.getThFirstName()) || Util.empty(referencePerson.getThLastName())){
			formError.error("REFERENCE_NAME",PREFIX_ERROR+LabelUtil.getText(request,"REFERENCE_NAME"));
		}
		return formError.getFormError();
	}
}
