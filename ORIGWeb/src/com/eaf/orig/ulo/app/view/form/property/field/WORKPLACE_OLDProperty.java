package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class WORKPLACE_OLDProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(WORKPLACE_OLDProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("WORKPLACE_OLDProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(personalInfo.getTotWorkYear()) || Util.empty(personalInfo.getTotWorkMonth())){
			formError.error("WORKPLACE_OLD",PREFIX_ERROR+LabelUtil.getText(request,"WORKPLACE_OLD"));
		}
		return formError.getFormError();
	}
}
