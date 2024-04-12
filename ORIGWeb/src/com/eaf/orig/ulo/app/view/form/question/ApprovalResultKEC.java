package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;

public class ApprovalResultKEC  extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ApprovalResultKEC.class);
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/ApprovalResultKEC.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}	
	@Override
	public String processElement(HttpServletRequest request, Object objectElement){
		return null;
	}
}
