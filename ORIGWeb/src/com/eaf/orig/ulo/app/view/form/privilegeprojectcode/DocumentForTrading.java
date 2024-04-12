package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductTradingDataM;

public class DocumentForTrading extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentForTrading.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/document/DocumentForTrading.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		int PROJECT_DOC_TRADING_INDEX=0;
		
		String DOCUMENT_FOR_TRADING_BATH = request.getParameter("DOCUMENT_FOR_TRADING_BATH");
		logger.debug("DOCUMENT_FOR_TRADING_BATH >> "+DOCUMENT_FOR_TRADING_BATH);
		
		PrivilegeProjectCodeDocDataM privilegeProjectCodeDocs = (PrivilegeProjectCodeDocDataM)objectElement;
		ArrayList<PrivilegeProjectCodeProductTradingDataM> privilegeProductTradings = privilegeProjectCodeDocs.getPrivilegeProjectCodeProductTradings();
		if(Util.empty(privilegeProductTradings)){
			privilegeProductTradings = new ArrayList<PrivilegeProjectCodeProductTradingDataM>();
		}
		
		PrivilegeProjectCodeProductTradingDataM privilegeProductTrading = privilegeProjectCodeDocs.getPrivilegeProjectCodeProductTradings(PROJECT_DOC_TRADING_INDEX);
		if(Util.empty(privilegeProductTrading)){
			privilegeProductTrading = new PrivilegeProjectCodeProductTradingDataM();
			privilegeProductTradings.add(PROJECT_DOC_TRADING_INDEX, privilegeProductTrading);
		}
		
		privilegeProductTrading.setPropertyValue(FormatUtil.toBigDecimal(DOCUMENT_FOR_TRADING_BATH, true));
		privilegeProjectCodeDocs.setPrivilegeProjectCodeProductTradings(privilegeProductTradings);
		
		privilegeProjectCodeDocs.setPrivilegeProjectCodeExceptionDocs(null);
		privilegeProjectCodeDocs.setPrivilegeProjectCodeKassetDocs(null);
		
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {
		return null;
	}
}
