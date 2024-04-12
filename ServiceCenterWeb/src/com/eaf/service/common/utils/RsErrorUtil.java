package com.eaf.service.common.utils;

import org.apache.log4j.Logger;

import com.ava.workflow.KBankRsHeader;
import com.ava.workflow.RsError;
import com.eaf.service.common.api.ServiceCache;

public class RsErrorUtil {
	private static transient Logger logger = Logger.getLogger(RsErrorUtil.class);
	public static final String CAN_NOT_PROVIDE_ERROR="00";//For systems that cannot provide Error Severity
	public static final String FATAL_ERROR="01"; //FATAL (very severe error events that will presumably lead the application to abort) 
	public static final String EVENTS_ERROR="02"; // WARNING (potentially harmful situations)
	public static final String WARNING_ERROR="03";// INFO (informational messages that highlight the progress of the application at coarse-grained level)
	public static final String INFO_ERROR="04";//INFO (informational messages that highlight the progress of the application at coarse-grained level)
	public static final String DEBUG_ERROR="05";//DEBUG (fine-grained informational events that are most useful to debug an application) 
	public static final String TRACE_ERROR="06";// TRACE (finer-grained informational events than the DEBUG)		

	public static void setRsErrorDefualt(RsError rsError,KBankRsHeader kBankRsHeader){
		try {
			String APP_ABBRV = ServiceCache.getGeneralParam("APP_ABBRV");
			rsError.setErrorAppAbbrv(APP_ABBRV);
			rsError.setErrorAppId(kBankRsHeader.getRsAppId());
			rsError.setErrorSeverity(CAN_NOT_PROVIDE_ERROR);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		
	}
}
