package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;

public class CashTranferSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CashTranferSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
	
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
