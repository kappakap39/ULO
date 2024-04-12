package com.eaf.orig.ulo.app.view.form.subform.summary.normal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;

public class SummaryNormalKCCElement extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(SummaryNormalKCCElement.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/summary/normal/SummaryNormalKCCSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		return null;
	}
}
