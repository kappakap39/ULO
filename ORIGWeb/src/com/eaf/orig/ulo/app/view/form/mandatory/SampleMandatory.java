package com.eaf.orig.ulo.app.view.form.mandatory;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;

public class SampleMandatory extends ValidateFormHelper implements ValidateFormInf{
	private static transient Logger logger = Logger.getLogger(SampleMandatory.class);
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {	
		return ValidateFormInf.VALIDATE_NO;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		return null;
	}
}
