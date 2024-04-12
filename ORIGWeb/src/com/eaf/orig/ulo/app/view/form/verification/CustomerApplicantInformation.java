package com.eaf.orig.ulo.app.view.form.verification;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;

public class CustomerApplicantInformation  extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(CustomerApplicantInformation.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/verification/CustomerApplicantInformationSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
}
