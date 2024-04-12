package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;

public class ValidateAuditLogReport extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateAuditLogReport.class);
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		
		String DATE_FROM_CALENDAR = request.getParameter("DATE_FROM_CALENDAR"); 
		String TIME_FROM_HOUR_BOX = request.getParameter("TIME_FROM_HOUR_BOX");
		String TIME_FROM_MIN_BOX = request.getParameter("TIME_FROM_MIN_BOX");
		String DATE_TO_CALENDAR = request.getParameter("DATE_TO_CALENDAR");
		String TIME_TO_HOUR_BOX = request.getParameter("TIME_TO_HOUR_BOX");
		String TIME_TO_MIN_BOX = request.getParameter("TIME_TO_MIN_BOX");
		logger.debug("DATE_FROM_CALENDAR : "+DATE_FROM_CALENDAR);
		logger.debug("TIME_FROM_HOUR_BOX : "+TIME_FROM_HOUR_BOX);
		logger.debug("TIME_FROM_MIN_BOX : "+TIME_FROM_MIN_BOX);
		logger.debug("DATE_TO_CALENDAR : "+DATE_TO_CALENDAR);
		logger.debug("TIME_TO_HOUR_BOX : "+TIME_TO_HOUR_BOX);
		logger.debug("TIME_TO_MIN_BOX : "+TIME_TO_MIN_BOX);
		if(Util.empty(DATE_FROM_CALENDAR)){
			formError.mandatoryElement("AUDIT_LOG_DATE_FROM", "AUDIT_LOG_DATE_FROM", DATE_FROM_CALENDAR, request);
		}
		if(Util.empty(TIME_FROM_HOUR_BOX) ){
			formError.mandatoryElement("AUDIT_LOG_TIME_FROM", "AUDIT_LOG_TIME_FROM", TIME_FROM_HOUR_BOX, request);
		}
		if(Util.empty(TIME_FROM_MIN_BOX)){
			formError.mandatoryElement("AUDIT_LOG_TIME_FROM", "AUDIT_LOG_TIME_FROM", TIME_FROM_MIN_BOX, request);
		}
		if(Util.empty(DATE_TO_CALENDAR)){
			formError.mandatoryElement("AUDIT_LOG_DATE_TO", "AUDIT_LOG_DATE_TO", DATE_TO_CALENDAR, request);
		}
		if(Util.empty(TIME_TO_HOUR_BOX)){
			formError.mandatoryElement("AUDIT_LOG_TIME_TO", "AUDIT_LOG_TIME_TO", TIME_TO_HOUR_BOX, request);
		}
		if(Util.empty(TIME_TO_MIN_BOX)){
			formError.mandatoryElement("AUDIT_LOG_TIME_TO", "AUDIT_LOG_TIME_TO", TIME_TO_MIN_BOX, request);
		}
		return formError.getFormError();
	}
}
