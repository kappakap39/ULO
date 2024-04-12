package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ReasonDataM;

public class REASON_CODEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(EN_FIRST_LAST_NAMEProperty.class);

	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		ReasonDataM reasonM = (ReasonDataM)objectForm;
		String REASON_CODE_OTH = SystemConstant.getConstant("REASON_CODE_OTH");
		if(Util.empty(reasonM.getReasonCode())){
			formError.error("REASON_CODE", MessageErrorUtil.getText(request,"SELECT_REASON_SHORT_DESC"));
		}
		logger.debug("REASON_CODE : "+reasonM.getReasonCode());
		logger.debug("REASON_OTH_DESC : "+reasonM.getReasonOthDesc());
		
		if (REASON_CODE_OTH.equals(reasonM.getReasonCode())) {
			if (Util.empty(reasonM.getReasonOthDesc())) {
				formError.error("REASON_OTH_DESC", MessageErrorUtil.getText(request, "REASON_DETAIL_SHORT_DESC"));
			}
		}
		return formError.getFormError();
	}
}
