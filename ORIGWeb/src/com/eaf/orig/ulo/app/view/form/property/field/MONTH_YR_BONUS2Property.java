package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PayslipDataM;

public class MONTH_YR_BONUS2Property extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(MONTH_YR_BONUS2Property.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("MONTH_YR_BONUS2Property.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		PayslipDataM payslipM = (PayslipDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(!Util.empty(payslipM.getBonus()) && payslipM.getNoMonth() == 0) {
			if(Util.empty(payslipM.getBonusMonth()) ){
				formError.error("MONTH2_BONUS",PREFIX_ERROR+LabelUtil.getText(request,"MONTH_YR_BONUS"));
			}
			if(Util.empty(payslipM.getBonusYear())) {
				formError.error("YEAR2_BONUS",PREFIX_ERROR+LabelUtil.getText(request,"MONTH_YR_BONUS"));
			}
		}
		return formError.getFormError();
	}
}
