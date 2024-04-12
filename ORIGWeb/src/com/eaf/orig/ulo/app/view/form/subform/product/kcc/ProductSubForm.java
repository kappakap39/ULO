package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class ProductSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(ProductSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("ProductSubForm...");
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
