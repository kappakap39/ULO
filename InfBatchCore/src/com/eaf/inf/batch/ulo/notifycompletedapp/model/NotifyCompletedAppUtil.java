package com.eaf.inf.batch.ulo.notifycompletedapp.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequestDataM;

public class NotifyCompletedAppUtil {
	private static transient Logger logger  = Logger.getLogger(NotifyCompletedAppUtil.class);
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String PERSONAL_LIST = "PERSONAL_LIST";
	public static final String APPLICATION_GROUP_NO = "APPLICATION_GROUP_NO";
	public static final String APPLICATION_GROUP_ID = "APPLICATION_GROUP_ID";
	public static final String IS_VETO_ELIGIBLE = "IS_VETO_ELIGIBLE";
	public static final String JOB_STATE = "JOB_STATE";
	public static final String APPLICATION_DATE = "APPLICATION_DATE";
	
	public static ApplicationDataM getApplicationCriteria(String applicationList,String applicationGroupId){
		 ApplicationDataM applicationDataM = new ApplicationDataM();
		try {
			 if(!InfBatchUtil.empty(applicationList)){
				 String[] applications = applicationList.split("_");
				 String appRecordId = applications.length>0?applications[0]:"";
				 String lifeCycle = applications.length>1?applications[1]:"0";
				 String lastDecisionDate = applications.length>2?applications[2]:"";
				 logger.debug("appRecordId>>"+appRecordId);
				 logger.debug("lifeCycle>>"+lifeCycle);
				 logger.debug("lastDecisionDate>>"+lastDecisionDate);
				 applicationDataM.setApplicationGroupId(applicationGroupId);
				 applicationDataM.setApplicationRecordId(appRecordId);
				 applicationDataM.setLifeCycle(Integer.parseInt(lifeCycle));
				 applicationDataM.setFinalAppDecisionDate(toSQLDate(lastDecisionDate,Formatter.Format.dd_MMM_yyyy2));
				 return applicationDataM;
			 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		
		return applicationDataM;
	}	
	public static String getNewLine(){
	return System.getProperty("line.separator");		
	}
	public static String getApplicantTypeOfConsent(String personalType,int numOfsup){
		 String NOTIFY_COMPLETED_APP_APPLICANT = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_APPLICANT");
		 String NOTIFY_COMPLETED_APP_SUP = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_SUP");
		 logger.debug(">>personalType>>"+personalType);
		try {
			if(NOTIFY_COMPLETED_APP_APPLICANT.equals(personalType)){
				return "M";
			}else if(NOTIFY_COMPLETED_APP_SUP.equals(personalType)){
				return "S"+String.valueOf(++numOfsup) ;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return"";
	}
	
	public static String cmsUploadFlag(String sendToEDWS){
		if(YES.equals(sendToEDWS)){
			return YES;
		} 
		return NO;
	}
	public static java.sql.Date toSQLDate(String date,String format){
		java.sql.Date _date = null;
		if(InfBatchUtil.empty(date)){
			return null;
		}
		try{
			DateFormat $format = new SimpleDateFormat(format,java.util.Locale.US);
			_date = new java.sql.Date($format.parse(date).getTime());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return _date;
	}
}
