package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;

public class OldDataSubform extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OldDataSubform.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) 
	{
		
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) 
	{
		FormErrorUtil formError = new FormErrorUtil();
		return formError.getFormError();
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request)
	{
		return false;
	}	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		return fieldElements;
	}
}
