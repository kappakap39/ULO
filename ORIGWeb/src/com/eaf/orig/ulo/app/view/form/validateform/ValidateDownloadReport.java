package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;

public class ValidateDownloadReport extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateDownloadReport.class);
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String reportDate = request.getParameter("reportDate");
		logger.debug("reportDate : "+reportDate);
		if(Util.empty(reportDate)){
			formError.mandatoryElement("DATE_REPORT","SEARCH_DATE_REPORT",reportDate,request);
		}
		return formError.getFormError();
	}
}
