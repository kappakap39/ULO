package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;

public class ValidateITSIReport extends ValidateFormHelper implements ValidateFormInf{
	private static transient Logger logger = Logger.getLogger(ValidateITSIReport.class);
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		
		String DATE_FROM_CALENDAR = request.getParameter("DATE_FROM_CALENDAR"); 
		String DATE_TO_CALENDAR = request.getParameter("DATE_TO_CALENDAR");
		
		logger.debug("DATE_FROM_CALENDAR : "+DATE_FROM_CALENDAR);
		logger.debug("DATE_TO_CALENDAR : "+DATE_TO_CALENDAR);

		if(Util.empty(DATE_FROM_CALENDAR)){
			formError.mandatoryElement("ITSI_REPORT_TRAN_DATE_FROM", "ITSI_REPORT_TRAN_DATE_FROM", DATE_FROM_CALENDAR, request);
		}
		if(Util.empty(DATE_TO_CALENDAR)){
			formError.mandatoryElement("ITSI_REPORT_TRAN_DATE_TO", "ITSI_REPORT_TRAN_DATE_TO", DATE_TO_CALENDAR, request);
		}
		return formError.getFormError();
	}
}
