package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class RESIDEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(RESIDEProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("RESIDEProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		AddressDataM currentAddress = (AddressDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(currentAddress.getResidey()) || Util.empty(currentAddress.getResidem())){
			formError.error("RESIDE",PREFIX_ERROR+LabelUtil.getText(request,"RESIDE"));
		}
		return formError.getFormError();
	}
}
