package com.eaf.orig.ulo.app.view.form.subform.sup;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;

public class SupCreditShieldSubForm extends CreditShieldSubForm {
	private static transient Logger logger = Logger.getLogger(SupCreditShieldSubForm.class);
	private String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm) {
		String formId = FormControl.getOrigFormId(request);
		logger.debug("formId >> "+formId);
		logger.debug("SUP_CARD_SEPARATELY_FORM >> "+SUP_CARD_SEPARATELY_FORM);
		if(Util.empty(formId) || SUP_CARD_SEPARATELY_FORM.equals(formId)){
			return MConstant.FLAG_Y;
		}
		return MConstant.FLAG_N;
	}
}
