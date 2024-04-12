package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.view.form.ORIGSubForm;

public class AutoCompleteSubForm extends ORIGSubForm{
	private static Logger logger = Logger.getLogger(AutoCompleteSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {		
		return null;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
