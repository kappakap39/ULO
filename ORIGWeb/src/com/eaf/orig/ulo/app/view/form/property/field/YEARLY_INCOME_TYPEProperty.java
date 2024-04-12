package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class YEARLY_INCOME_TYPEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(YEARLY_INCOME_TYPEProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("YEARLY_INCOME_TYPEProperty.validateForm");
		String PREFIX_ERROR= MessageErrorUtil.getText(request,"PREFIX_ERROR_2");
		YearlyTawi50DataM yearlyTawiM = (YearlyTawi50DataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(yearlyTawiM.getIncome401()) && Util.empty(yearlyTawiM.getIncome402())){
			formError.error("INCOME_TYPE401_"+yearlyTawiM.getSeq(),PREFIX_ERROR+LabelUtil.getText(request,"INCOME_TYPE401_402"));
			formError.element("INCOME_TYPE402_"+yearlyTawiM.getSeq());
		}
		return formError.getFormError();
	}
}
