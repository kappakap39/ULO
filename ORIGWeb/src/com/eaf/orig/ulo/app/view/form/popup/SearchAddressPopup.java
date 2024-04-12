package com.eaf.orig.ulo.app.view.form.popup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.subform.normal.AddressSubForm1;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class SearchAddressPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AddressSubForm1.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("SearchAddressPopup.setProperties ");
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;		

	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "SERACH_ADDRESS_POPUP";
		logger.debug("subformId >> "+subformId);
		String SEARCH_ADDRESS_SELECT = request.getParameter("SEARCH_ADDRESS_SELECT");		
		FormErrorUtil formError = new FormErrorUtil();		
		if (Util.empty(SEARCH_ADDRESS_SELECT)) {
			formError.error("SEARCH_ADDRESS_SELECT", MessageUtil.getText(request, "ADDRESS_SELECT_REQUIRED"));
		}			
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
