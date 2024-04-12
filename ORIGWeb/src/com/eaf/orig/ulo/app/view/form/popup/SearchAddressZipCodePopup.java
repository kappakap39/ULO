package com.eaf.orig.ulo.app.view.form.popup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class SearchAddressZipCodePopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(SearchAddressZipCodePopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;		
		String SEARCH_ADDRESS_SELECT = request.getParameter("SEARCH_ADDRESS_SELECT");
		logger.debug("SEARCH_ADDRESS_SELECT VALUE>>"+SEARCH_ADDRESS_SELECT);
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
