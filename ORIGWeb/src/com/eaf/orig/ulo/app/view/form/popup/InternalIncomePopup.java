package com.eaf.orig.ulo.app.view.form.popup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.view.form.ORIGSubForm;

public class InternalIncomePopup extends ORIGSubForm {
	
	private static transient Logger logger = Logger.getLogger(CardLinkCurrentAddressPopup.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
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
