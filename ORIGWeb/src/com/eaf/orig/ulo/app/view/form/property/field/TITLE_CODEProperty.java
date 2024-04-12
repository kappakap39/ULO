package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.model.Age;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class TITLE_CODEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(TITLE_CODEProperty.class);
	private String MINIMUM_OLD_CHIELD = SystemConfig.getGeneralParam("MINIMUM_OLD_CHIELD");
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
			if(ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig)){
				return ValidateFormInf.VALIDATE_YES;
			}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		if(!Util.empty(personalInfo)){if(PersonalInfoUtil.validateAge(FormatUtil.getInt(MINIMUM_OLD_CHIELD), personalInfo)){
			formError.error("TH_TITLE_DESC", MessageErrorUtil.getText(request,"ERROR_TITLECODE_MORE_AGE"));
		}	
		}
		return formError.getFormError();
	}
	
}
