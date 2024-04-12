package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;

public class ValidateSLAReport extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateSLAReport.class);
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		
		String DATE_FROM_CALENDAR = request.getParameter("DATE_FROM_CALENDAR"); 
		String DATE_TO_CALENDAR = request.getParameter("DATE_TO_CALENDAR");
		String COMPLETE_REPORT_RADIO = request.getParameter("COMPLETE_REPORT_RADIO");
		String PROJECT_NO_BOX = request.getParameter("PROJECT_NO_BOX");
		logger.debug("DATE_FROM_CALENDAR : "+DATE_FROM_CALENDAR);
		logger.debug("DATE_TO_CALENDAR : "+DATE_TO_CALENDAR);
		logger.debug("COMPLETE_REPORT_RADIO : "+COMPLETE_REPORT_RADIO);
		logger.debug("PROJECT_NO_BOX : "+PROJECT_NO_BOX);
		if(Util.empty(DATE_FROM_CALENDAR)){
			formError.mandatoryElement("SLA_REPORT_DATE_FROM", "SLA_REPORT_DATE_FROM", DATE_FROM_CALENDAR, request);
		}
		if(Util.empty(DATE_TO_CALENDAR)){
			formError.mandatoryElement("SLA_REPORT_DATE_TO", "SLA_REPORT_DATE_TO", DATE_TO_CALENDAR, request);
		}
		if(Util.empty(COMPLETE_REPORT_RADIO)){
			formError.mandatoryElement("SLA_REPORT_COMPLETE", "SLA_REPORT_COMPLETE", COMPLETE_REPORT_RADIO, request);
		}
//		if(Util.empty(PROJECT_NO_BOX)){
//			formError.mandatoryElement("SLA_REPORT_PROJECT_NO", "SLA_REPORT_PROJECT_NO", PROJECT_NO_BOX, request);
//		}
		return formError.getFormError();
	}
}
