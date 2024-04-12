package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;

public class INCOME_OTH_DESCProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(INCOME_OTH_DESCProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("INCOME_OTH_DESCProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		PayslipMonthlyDataM monthlyM = (PayslipMonthlyDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(!Util.empty(monthlyM.getIncomeDesc()) && 
				SystemConstant.getConstant("INCOME_TYPE_OTHER").equals(monthlyM.getIncomeDesc())
				&& Util.empty(monthlyM.getIncomeOthDesc())) {
			formError.error("INCOME_OTH_DESC_"+monthlyM.getSeq(),PREFIX_ERROR+LabelUtil.getText(request,"INCOME_OTH_DESC"));
		}
		return formError.getFormError();
	}
}
