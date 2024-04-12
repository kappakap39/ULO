package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public class FIXED_GUARANTEE_AMTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(FIXED_GUARANTEE_AMTProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("FIXED_GUARANTEE_AMTProperty.validateForm");
		IncomeInfoDataM incomeM = (IncomeInfoDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		ArrayList<IncomeCategoryDataM> guaranteeList = incomeM.getAllIncomes();
		if(Util.empty(guaranteeList)) {
			formError.error("AMOUNT_1",MessageErrorUtil.getText(request,"ERROR_ID_FIXED_GUARANTEE_AMT"));
		}
		return formError.getFormError();
	}
}
