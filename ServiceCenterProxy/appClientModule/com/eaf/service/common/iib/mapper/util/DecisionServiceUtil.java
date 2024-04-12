package com.eaf.service.common.iib.mapper.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.DocumentSlaTypes;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.iib.eapp.mapper.EDCDecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.EDV1DecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.EDV2DecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.EDecisionServiceRequestMapper;
import com.eaf.service.common.iib.eapp.mapper.EDecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.EFAEDecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.FollowVERIFICATIONEDecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.IncomeInquiryEDecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.POSTEDecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.ReExecuteVERIFICATIONEDecisionServiceResponseMapper;
import com.eaf.service.common.iib.eapp.mapper.VERIFICATIONEDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.request.DCDecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.DE1DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.DE2DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.DV1DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.DV2DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.DocDecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.FIDecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.IA1DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.IncomeCalculationDecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.IncomeDecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.PB1DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.request.PostDecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.response.DCDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.DE1DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.DE2DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.DV1DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.DV2DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.DocDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.FIDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.FIRecalculateDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.IA1DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.IncomeCalculationDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.IncomeDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.PB1DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.response.PostDecisionServiceResponseMapper;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.inf.CachePropertiesInf;

import decisionservice_iib.DocumentSlaTypeDataM;

public class DecisionServiceUtil {
	private static transient Logger logger = Logger.getLogger(DecisionServiceUtil.class);
	
	public static final String PERSONAL_APPLICATION_LEVEL = "A";
	public static final String INC_TYPE_PREVIOUS_INCOME ="16";
	public static final String FLAG_Y="Y";
	public static final String FLAG_N="N";
	public static final String INFORMATION_COMPLETED_CODE = "00";
	public static final String COMPARE_FLAG_WRONG = "W";
	public static final String STRING_BOOLEAN_TRUE = "true";
	public static final String STRING_BOOLEAN_FALSE = "false";
	public static final String TH = "TH";
	public static final String EN = "EN";
	protected static final double DEFAULT_DOUBLE = -9999D;
	protected static final int DEFAULT_INT = -9999;
	
	public static final Set<String> CIS_COMPLETION_FIELDS = new HashSet<String>();
	static{
		CIS_COMPLETION_FIELDS.add("TH_FIRST_NAME");
		CIS_COMPLETION_FIELDS.add("TH_LAST_NAME");
		CIS_COMPLETION_FIELDS.add("EN_FIRST_NAME");
		CIS_COMPLETION_FIELDS.add("EN_LAST_NAME");
	}
	
	public static final Set<String> APPLY_TYPE_ERROR_CODES = new HashSet<String>();
	static{
		APPLY_TYPE_ERROR_CODES.add("ERR001");
	}
	
	public class DECISION_POINT{
		public static final String DECISION_POINT_IA="IA1";
		public static final String DECISION_POINT_PB1="PB1";
		public static final String DECISION_POINT_DE1="DE1";
		public static final String DECISION_POINT_DE2="DE2";
		public static final String DECISION_POINT_FI="FI";
		public static final String DECISION_POINT_DV1="DV1";
		public static final String DECISION_POINT_DV2="DV2";
		public static final String DECISION_POINT_DC="DC";
		public static final String DECISION_POINT_DOC_COMPLETE="DOC_COMPLETE";
		public static final String DECISION_POINT_BUREAU_DOC="BUREAU_DOC";
		public static final String DECISION_POINT_INCOME="INCOME";
		public static final String DECISION_POINT_POST="POST_APPROVE";
		public static final String DECISION_POINT_FI_RECAL="FI_RECAL";
		public static final String DECISION_POINT_VERIFICATION="VERIFICATION_SERVICE";
		public static final String DECISION_POINT_EPV="EPV";
		public static final String DECISION_POINT_EPB1="EPB1";
		public static final String DECISION_POINT_EPB2="EPB2";
		public static final String DECISION_POINT_EPA="EPA";
		public static final String DECISION_POINT_EDV1="EDV1";
		public static final String DECISION_POINT_EDV2="EDV2";
		public static final String DECISION_POINT_EDC="EDC";
		public static final String DECISION_POINT_EFA="EFA";
		public static final String DECISION_POINT_INCOME_SERVICE="INCOME_SERVICE";
		public static final String DECISION_POINT_INCOME_INQUIRY="INCOME_INQUIRY";
		public static final String DECISION_POINT_POST_APPROVAL="POST_APPROVAL_SERVICE";
		public static final String DECISION_POINT_INCOME_CALCULATION_SERVICE="INCOME_CALCULATION_SERVICE";
	}
	
	public static class APPLY_TYPE{
		public static final String ERR = "ERR";
		public static final String NEW = "NEW";
		public static final String ADD = "ADD";
		public static final String UPG = "UPG";
		public static final String INC = "INC";
	}
	
	public static final String FROM_FOLLOW_ACTION = "FOLLOW_ACTION";
	public static final String FROM_RE_EXECUTE_ACTION = "RE_EXECUTE_ACTION";
	
	public static  XMLGregorianCalendar dateToXMLGregorianCalendar(Date dateVal ) {
		if(null==dateVal){
			return null;
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dateVal);
		XMLGregorianCalendar xmlGregorianDate;
		try {
			xmlGregorianDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			return xmlGregorianDate;
		} catch (DatatypeConfigurationException e) {
			logger.fatal("ERROR",e);
		}
		return null;
		
	}
	
	public static  DateTime dateToDateTime(Date dateVal ) {
		if(null==dateVal){
			return null;
		}
		

		
		try {
			DateTime dateTime = new DateTime(dateVal);
			return dateTime;
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
		
	}
	
	
	public static  String dateToString(java.util.Date dateVal ,String format) {
		if(null==dateVal){
			return null;
		}
		
		DateFormat df = new SimpleDateFormat(format);
		
		try {
			String reportDate = df.format(dateVal);
			return reportDate;
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
		
	}
	
	public static XMLGregorianCalendar getSystemdate() {
		try {
			Date dateVal = ServiceFactory.getServiceDAO().getAppCurrentDate();
			if(null==dateVal){
				return null;
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dateVal);
			XMLGregorianCalendar xmlGregorianDate;
			try {
				xmlGregorianDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
				return xmlGregorianDate;
			} catch (DatatypeConfigurationException e) {
				logger.fatal("ERROR",e);
			}
		} catch (Exception e1) {
			logger.fatal("ERROR",e1);
		}
		
		return null;
		
	}
	public static DateTime getSystemdateTime() {
		try {
			Date dateVal = ServiceFactory.getServiceDAO().getAppCurrentDate();
			if(null==dateVal){
				return null;
			}
			
			try {
				DateTime dateTime = new DateTime(dateVal);
				return dateTime;
			} catch (Exception e) {
				logger.fatal("ERROR",e);
			}
		} catch (Exception e1) {
			logger.fatal("ERROR",e1);
		}
		
		return null;
		
	}
	public static Date getDate(){
		String cacheName = CacheConstant.CacheName.APPLICATION_DATE;
		CachePropertiesInf<Date> currentDate = CacheController.getCacheProperties(cacheName);
		Date date = currentDate.getCacheData(cacheName);
		Calendar currentCalendar =  Calendar.getInstance(Locale.ENGLISH);
		currentCalendar.setTime(date);
		Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
		calendar.set(currentCalendar.get(Calendar.YEAR),currentCalendar.get(Calendar.MONTH),currentCalendar.get(Calendar.DAY_OF_MONTH));
		date.setTime(calendar.getTime().getTime());
		return date;
	}
	
	public static Calendar getApplicationDate(){
		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		cal.setTime(DecisionServiceUtil.getDate());
		return cal;
	}
	public static DateTime getApplicationDateTime(){
		return dateToDateTime(DecisionServiceUtil.getDate());
	
	}
	public   static Date toSqlDate(XMLGregorianCalendar dateVal ) {
		 if(null!=dateVal){
			 java.util.Date dateUtil = dateVal.toGregorianCalendar().getTime();
			 return new Date(dateUtil.getTime());
		 }
		return null;
		
	}
	public   static Date toSqlDate(DateTime dateVal ) {
		 if(null!=dateVal){

			 return new Date(dateVal.toDate().getTime());
		 }
		return null;
		
	}
	public  static String toString(Object val) {
		 if(null!=val){
			 return String.valueOf(val);
		 }
		return "";
		
	}
	
	public static String getSurrogateKey(String...keys){
		if(keys == null || keys.length < 1){
			return "";
		}
		StringBuilder result = new StringBuilder();
		String separator = "";
		final String pipe = "|";
		for(int i = 0, l = keys.length; i < l; i++){
			String key = keys[i];
			if(key == null){
				result.append(pipe);
			}else{
				result.append(separator).append(key);
			}
			separator = pipe;
		}
		return result.toString();
	}
	
	public static String getAction(DecisionServiceRequestDataM   request){
		if(request == null)return "";
		StringBuilder sb = new StringBuilder();
		sb.append(request.getDecisionPoint() == null? "":request.getDecisionPoint());
		if(request.getCallerScreen() != null){
			sb.append("-");
			sb.append(request.getCallerScreen());
		}		
		
		return sb.toString();
	}
	
	public static DecisionServiceRequestMapper getIIBRequestMapperClass(String decisionPoint){
		if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_IA.equals(decisionPoint)){
			return new IA1DecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_PB1.equals(decisionPoint)){
			return new PB1DecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE1.equals(decisionPoint)){
			return new DE1DecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE2.equals(decisionPoint)){
			return new DE2DecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI.equals(decisionPoint)){
			return new FIDecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV1.equals(decisionPoint)){
			return new DV1DecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV2.equals(decisionPoint)){
			return new DV2DecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DC.equals(decisionPoint)){
			return new DCDecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_BUREAU_DOC.equals(decisionPoint)
				|| DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DOC_COMPLETE.equals(decisionPoint)){
			return new DocDecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME.equals(decisionPoint)){
			return new IncomeDecisionServiceRequestMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_SERVICE.equals(decisionPoint)){
			return new IncomeDecisionServiceRequestMapper();
		}		else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST.equals(decisionPoint)){
			return new PostDecisionServiceRequestMapper();
		}
		return null;
	}
	public static EDecisionServiceRequestMapper getIIBRequestMapperClassEApp(String decisionPoint){
		return new EDecisionServiceRequestMapper();
	}
	
	public static DecisionServiceResponseMapper getIIBResponseMapperClass(String decisionPoint){
		if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_IA.equals(decisionPoint)){
			return new IA1DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_PB1.equals(decisionPoint)){
			return new PB1DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE1.equals(decisionPoint)){
			return new DE1DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE2.equals(decisionPoint)){
			return new DE2DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI.equals(decisionPoint)){
			return new FIDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV1.equals(decisionPoint)){
			return new DV1DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV2.equals(decisionPoint)){
			return new DV2DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DC.equals(decisionPoint)){
			return new DCDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DOC_COMPLETE.equals(decisionPoint)
				|| DecisionServiceUtil.DECISION_POINT.DECISION_POINT_BUREAU_DOC.equals(decisionPoint)){
			return new DocDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME.equals(decisionPoint)){
			return new IncomeDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST.equals(decisionPoint)){
			return new PostDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI_RECAL.equals(decisionPoint)){
			return new FIRecalculateDecisionServiceResponseMapper();
		}
		return null;
	}
	public static EDecisionServiceResponseMapper getIIBResponseMapperClassEApp(String decisionPoint){
		if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_VERIFICATION.equals(decisionPoint)){
			return new VERIFICATIONEDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDV2.equals(decisionPoint)){
			return new EDV2DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_SERVICE.equals(decisionPoint)){
			return new EDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_INQUIRY.equals(decisionPoint)){
			return new IncomeInquiryEDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EFA.equals(decisionPoint)){
			return new EFAEDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST_APPROVAL.equals(decisionPoint)){
			return new POSTEDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDC.equals(decisionPoint)){
			return new EDCDecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDV1.equals(decisionPoint)){
			return new EDV1DecisionServiceResponseMapper();
		}else if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_CALCULATION_SERVICE.equals(decisionPoint)){
			return new IncomeCalculationDecisionServiceResponseMapper();
		}
				
		return new EDecisionServiceResponseMapper();
	}
	public static EDecisionServiceResponseMapper getIIBResponseMapperClassEApp(String decisionPoint, String fromAction){
		if(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_VERIFICATION.equals(decisionPoint)){
			if(FROM_FOLLOW_ACTION.equals(fromAction)){
				return new FollowVERIFICATIONEDecisionServiceResponseMapper();
			}
			else if(FROM_RE_EXECUTE_ACTION.equals(fromAction)){
				return new ReExecuteVERIFICATIONEDecisionServiceResponseMapper();
			}
			return new VERIFICATIONEDecisionServiceResponseMapper();
		}
		return new EDecisionServiceResponseMapper();
	}
	public static int parseInt(BigDecimal input){
		if(input == null){
//			log.debug("Converter.parseInt(), input is null!");
			return 0;
		}
		return input.intValue();
	}
	
	public static int parseInt(Double input){
		if(input == null){
//			log.debug("Converter.parseInt(), input is null!");
			return 0;
		}
		return input.intValue();
	}
	
	public static Integer parseInteger(BigDecimal input){
		if(input == null){
//			log.debug("Converter.parseInt(), input is null!");
			return null;
		}
		return input.intValue();
	}
	
	public static int parseInt(Integer input){
		if(input == null){
//			log.debug("Converter.parseInt(), input is null!");
			return 0;
		}
		return input;
	}
	
	public static int parseInt(String input){
		if(input == null || input.isEmpty()){
//			log.debug("Converter.parseInt(), input is null!");
			return 0;
		}
		int result = 0;
		try{
			result = Integer.parseInt(input);
		}catch(Exception e){}
		return result;
	}
	
	public static String parseBooleanString(boolean input){
		if(input){
			return STRING_BOOLEAN_TRUE;
		}else{
			return STRING_BOOLEAN_FALSE;
		}
	}
	public static BigDecimal intToBigDecimal(Integer num){
		if(null==num) return BigDecimal.ZERO;
		return new BigDecimal(num);
	}
	public static BigDecimal longToBigDecimal(Long num){
		if(null==num) return BigDecimal.ZERO;
		return new BigDecimal(num);
	}
	public static Integer bigDecimalToInt(BigDecimal num){
		if(null==num) return Integer.valueOf("0");
		return Integer.valueOf(String.valueOf(num));
	}
	
	public static String bigDecimalToString(BigDecimal num){
		if(null==num) return BigDecimal.ZERO.toString();
		return num.toString();
	}
	public static String intToString(Integer num){
		if(null==num) return "0";
		return String.valueOf(num);
	}
	public static BigDecimal stringToBigDecimal(String num){
		if(null==num) return BigDecimal.ZERO;
		BigDecimal value = BigDecimal.ZERO;
		try {			
			value = new BigDecimal(num);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return value;
	}
	public static Integer stringToInt(String num){
		if(null==num) return Integer.valueOf("0");
		Integer value = Integer.valueOf("0");
		try {			
			value = Integer.valueOf(num);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return value;
	}
	
	public static List<DocumentSlaTypeDataM> getDocumentSLAType(ApplicationGroupDataM uloAppGroup, PersonalInfoDataM uloPerson){
		if(uloAppGroup == null){
			return null;
		}
		if(uloPerson == null){
			return null;
		}
		VerificationResultDataM ver = uloPerson.getVerificationResult();
		if(ver == null){
			return null;
		}
		ArrayList<String> appGroupDocType = uloAppGroup.getOverSlaDocumentType();
		logger.debug("application group DocumentSLAType>>"+appGroupDocType);
		String slaTypeDoc = ProcessUtil.DOC_SLA_TYPE.FOLLOW_DOCUMENT_SLA_TYPE;
		String slaTypeCust = ProcessUtil.DOC_SLA_TYPE.CUSTOMER_DOCUMENT_SLA_TYPE;
		String verTypeCompleteWithOverSLA = DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED_NOT_FRAUD");//07
		String VALIDATION_STATUS_COMPLETED_NOT_PASS = DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	
		//Cus OVER SLA && (Has Follow Up Reason || doc not complete)
		if(verTypeCompleteWithOverSLA.equals(ver.getVerCusResultCode()) && 
				(hasFollowUpReason(uloAppGroup) || DecisionServiceUtil.FLAG_N.equalsIgnoreCase(ver.getDocCompletedFlag()))){
			List<DocumentSlaTypeDataM>  decisionDocSLAs = new  ArrayList<DocumentSlaTypeDataM>();
			
			/*
			 * ### Chatmongkol Change logic to add decisionDocSLA Case Vercustomer complete with over sla ###
			 * DocumentSlaTypeDataM iibDocSLA = new DocumentSlaTypeDataM();
			iibDocSLA.setDocumentSlaType(slaTypeDoc);				
			decisionDocSLAs.add(iibDocSLA);*/
			
			setDoccumentOverslaVercustomer(uloAppGroup,decisionDocSLAs,uloPerson);
				
			
			DocumentSlaTypeDataM iibCusDocSLA = new DocumentSlaTypeDataM();
			iibCusDocSLA.setDocumentSlaType(slaTypeCust);
			decisionDocSLAs.add(iibCusDocSLA);
			return decisionDocSLAs;
		}
		
			//Cus Fail && (Has Follow Up Reason || doc not complete)
			if(VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(ver.getVerCusResultCode()) && 
				(hasFollowUpReason(uloAppGroup) || DecisionServiceUtil.FLAG_N.equalsIgnoreCase(ver.getDocCompletedFlag()))){
				List<DocumentSlaTypeDataM>  decisionDocSLAs = new  ArrayList<DocumentSlaTypeDataM>();
				setDoccumentOverslaVercustomer(uloAppGroup,decisionDocSLAs,uloPerson);
				return decisionDocSLAs;
			}
		
		//Over sla
		if(!ServiceUtil.empty(appGroupDocType)){
			List<DocumentSlaTypeDataM>  decisionDocSLAs = new  ArrayList<DocumentSlaTypeDataM>();
			for(String appGroupDoc : appGroupDocType){
				DocumentSlaTypeDataM iibDocSLA = new DocumentSlaTypeDataM();
				iibDocSLA.setDocumentSlaType(appGroupDoc);
				decisionDocSLAs.add(iibDocSLA);
			}
			return decisionDocSLAs;
		}
		
		if(verTypeCompleteWithOverSLA.equals(ver.getVerCusResultCode())){
			List<DocumentSlaTypeDataM>  decisionDocSLAs = new  ArrayList<DocumentSlaTypeDataM>();
			DocumentSlaTypeDataM iibCusDocSLA = new DocumentSlaTypeDataM();
			iibCusDocSLA.setDocumentSlaType(slaTypeCust);
			decisionDocSLAs.add(iibCusDocSLA);
			return decisionDocSLAs;
		}else if(verTypeCompleteWithOverSLA.equals(ver.getVerHrResultCode())){
			List<DocumentSlaTypeDataM>  decisionDocSLAs = new  ArrayList<DocumentSlaTypeDataM>();
			DocumentSlaTypeDataM iibHRDocSLA = new DocumentSlaTypeDataM();
			iibHRDocSLA.setDocumentSlaType(ProcessUtil.DOC_SLA_TYPE.HR_DOCUMENT_SLA_TYPE);
			decisionDocSLAs.add(iibHRDocSLA);
			return decisionDocSLAs;
		}
		return null;
	}
	
	public static List<DocumentSlaTypes> getEDocumentSLAType(ApplicationGroupDataM uloAppGroup, PersonalInfoDataM uloPerson){
		if(uloAppGroup == null){
			return null;
		}
		if(uloPerson == null){
			return null;
		}
		VerificationResultDataM ver = uloPerson.getVerificationResult();
		if(ver == null){
			return null;
		}
		ArrayList<String> appGroupDocType = uloAppGroup.getOverSlaDocumentType();
		logger.debug("application group DocumentSLAType>>"+appGroupDocType);
		String slaTypeDoc = ProcessUtil.DOC_SLA_TYPE.FOLLOW_DOCUMENT_SLA_TYPE;
		String slaTypeCust = ProcessUtil.DOC_SLA_TYPE.CUSTOMER_DOCUMENT_SLA_TYPE;
		String verTypeCompleteWithOverSLA = DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED_NOT_FRAUD");//07
		String VALIDATION_STATUS_COMPLETED_NOT_PASS = DecisionServiceCacheControl.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	
		//Cus OVER SLA && (Has Follow Up Reason || doc not complete)
		if(verTypeCompleteWithOverSLA.equals(ver.getVerCusResultCode()) && 
				(hasFollowUpReason(uloAppGroup) || DecisionServiceUtil.FLAG_N.equalsIgnoreCase(ver.getDocCompletedFlag()))){
			List<DocumentSlaTypes>  decisionDocSLAs = new  ArrayList<DocumentSlaTypes>();
			
			/*
			 * ### Chatmongkol Change logic to add decisionDocSLA Case Vercustomer complete with over sla ###
			 * DocumentSlaTypeDataM iibDocSLA = new DocumentSlaTypeDataM();
			iibDocSLA.setDocumentSlaType(slaTypeDoc);				
			decisionDocSLAs.add(iibDocSLA);*/
			
			setEDoccumentOverslaVercustomer(uloAppGroup,decisionDocSLAs,uloPerson);
				
			
			DocumentSlaTypes iibCusDocSLA = new DocumentSlaTypes();
			iibCusDocSLA.setDocumentSlaType(slaTypeCust);
			decisionDocSLAs.add(iibCusDocSLA);
			return decisionDocSLAs;
		}
		
			//Cus Fail && (Has Follow Up Reason || doc not complete)
			if(VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(ver.getVerCusResultCode()) && 
				(hasFollowUpReason(uloAppGroup) || DecisionServiceUtil.FLAG_N.equalsIgnoreCase(ver.getDocCompletedFlag()))){
				List<DocumentSlaTypes>  decisionDocSLAs = new  ArrayList<DocumentSlaTypes>();
				setEDoccumentOverslaVercustomer(uloAppGroup,decisionDocSLAs,uloPerson);
				return decisionDocSLAs;
			}
		
		//Over sla
		if(!ServiceUtil.empty(appGroupDocType)){
			List<DocumentSlaTypes>  decisionDocSLAs = new  ArrayList<DocumentSlaTypes>();
			for(String appGroupDoc : appGroupDocType){
				DocumentSlaTypes iibDocSLA = new DocumentSlaTypes();
				iibDocSLA.setDocumentSlaType(appGroupDoc);
				decisionDocSLAs.add(iibDocSLA);
			}
			return decisionDocSLAs;
		}
		
		if(verTypeCompleteWithOverSLA.equals(ver.getVerCusResultCode())){
			List<DocumentSlaTypes>  decisionDocSLAs = new  ArrayList<DocumentSlaTypes>();
			DocumentSlaTypes iibCusDocSLA = new DocumentSlaTypes();
			iibCusDocSLA.setDocumentSlaType(slaTypeCust);
			decisionDocSLAs.add(iibCusDocSLA);
			return decisionDocSLAs;
		}else if(verTypeCompleteWithOverSLA.equals(ver.getVerHrResultCode())){
			List<DocumentSlaTypes>  decisionDocSLAs = new  ArrayList<DocumentSlaTypes>();
			DocumentSlaTypes iibHRDocSLA = new DocumentSlaTypes();
			iibHRDocSLA.setDocumentSlaType(ProcessUtil.DOC_SLA_TYPE.HR_DOCUMENT_SLA_TYPE);
			decisionDocSLAs.add(iibHRDocSLA);
			return decisionDocSLAs;
		}
		return null;
	}
	
	public static boolean hasFollowUpReason(ApplicationGroupDataM uloAppGroup){
		if(uloAppGroup == null)return false;
		
		List<DocumentCheckListDataM> docCheckList = uloAppGroup.getDocumentCheckLists();
		if(docCheckList == null || docCheckList.isEmpty())return false;
		
		for(DocumentCheckListDataM docCheck : docCheckList){
			if(docCheck == null)continue;
			List<DocumentCheckListReasonDataM> reasons = docCheck.getDocumentCheckListReasons();
			if(reasons == null || reasons.isEmpty())continue;
			
			for(DocumentCheckListReasonDataM reason : reasons){
				if(reason == null)continue;
				if(reason.getDocReason() != null && !reason.getDocReason().isEmpty() && !DecisionServiceUtil.FLAG_Y.equals(reason.getGenerateFlag())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void setDoccumentOverslaVercustomer(ApplicationGroupDataM uloAppGroup,List<DocumentSlaTypeDataM>  decisionDocSLAs,PersonalInfoDataM uloPerson){
		List<DocumentCheckListDataM> docCheckList = uloAppGroup.getDocumentCheckLists();
		ArrayList<String> docSLAtype = new ArrayList<String>();
		VerificationResultDataM uloVer= uloPerson.getVerificationResult();
		ArrayList<RequiredDocDataM> requiredDocList = uloVer.getRequiredDocs();
		for(RequiredDocDataM requiredDoc :requiredDocList){
			String docCompleteFlag = requiredDoc.getDocCompletedFlag();
			String scenaRioType = requiredDoc.getScenarioType();
			if(!ServiceUtil.empty(docCompleteFlag) && !ServiceUtil.empty(scenaRioType)){
				if(ServiceCache.getConstant("INCOME_DOC").equals(scenaRioType) && ServiceConstant.NO.equals(docCompleteFlag)
						&& !docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE04"))){
					DocumentSlaTypeDataM iibDocSLA = new DocumentSlaTypeDataM();
					iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE04"));				
					decisionDocSLAs.add(iibDocSLA);
					docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE04"));
				}else if(!ServiceCache.getConstant("INCOME_DOC").equals(scenaRioType) && ServiceConstant.NO.equals(docCompleteFlag)
						&& !docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE01"))){
					DocumentSlaTypeDataM iibDocSLA = new DocumentSlaTypeDataM();
					iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE01"));				
					decisionDocSLAs.add(iibDocSLA);
					docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE01"));
				}
			}
		}
		if(!ServiceUtil.empty(docCheckList)){
			for(DocumentCheckListDataM doc : docCheckList){
				if(!ServiceUtil.empty(doc)){
					ArrayList<DocumentCheckListReasonDataM> docCheckListReasons = doc.getDocumentCheckListReasons();
					if(!ServiceUtil.empty(docCheckListReasons)){
						for(DocumentCheckListReasonDataM docCheckListReason:docCheckListReasons){
							if(!ServiceUtil.empty(docCheckListReason)){
								if(!ServiceUtil.empty(doc.getDocTypeId()) && doc.getDocTypeId().equals(ServiceCache.getConstant("INCOME_DOC"))){
									if(!docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE04")) && !ServiceConstant.YES.equals(docCheckListReason.getGenerateFlag())){
										DocumentSlaTypeDataM iibDocSLA = new DocumentSlaTypeDataM();
										iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE04"));				
										decisionDocSLAs.add(iibDocSLA);
										docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE04"));
									}
								}else{
									if(!docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE01")) && !ServiceConstant.YES.equals(docCheckListReason.getGenerateFlag())){
											DocumentSlaTypeDataM iibDocSLA = new DocumentSlaTypeDataM();
										iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE01"));				
										decisionDocSLAs.add(iibDocSLA);
										docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE01"));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void setEDoccumentOverslaVercustomer(ApplicationGroupDataM uloAppGroup,List<DocumentSlaTypes>  decisionDocSLAs,PersonalInfoDataM uloPerson){
		List<DocumentCheckListDataM> docCheckList = uloAppGroup.getDocumentCheckLists();
		ArrayList<String> docSLAtype = new ArrayList<String>();
		VerificationResultDataM uloVer= uloPerson.getVerificationResult();
		ArrayList<RequiredDocDataM> requiredDocList = uloVer.getRequiredDocs();
		for(RequiredDocDataM requiredDoc :requiredDocList){
			String docCompleteFlag = requiredDoc.getDocCompletedFlag();
			String scenaRioType = requiredDoc.getScenarioType();
			if(!ServiceUtil.empty(docCompleteFlag) && !ServiceUtil.empty(scenaRioType)){
				if(ServiceCache.getConstant("INCOME_DOC").equals(scenaRioType) && ServiceConstant.NO.equals(docCompleteFlag)
						&& !docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE04"))){
					DocumentSlaTypes iibDocSLA = new DocumentSlaTypes();
					iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE04"));				
					decisionDocSLAs.add(iibDocSLA);
					docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE04"));
				}else if(!ServiceCache.getConstant("INCOME_DOC").equals(scenaRioType) && ServiceConstant.NO.equals(docCompleteFlag)
						&& !docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE01"))){
					DocumentSlaTypes iibDocSLA = new DocumentSlaTypes();
					iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE01"));				
					decisionDocSLAs.add(iibDocSLA);
					docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE01"));
				}
			}
		}
		if(!ServiceUtil.empty(docCheckList)){
			for(DocumentCheckListDataM doc : docCheckList){
				if(!ServiceUtil.empty(doc)){
					ArrayList<DocumentCheckListReasonDataM> docCheckListReasons = doc.getDocumentCheckListReasons();
					if(!ServiceUtil.empty(docCheckListReasons)){
						for(DocumentCheckListReasonDataM docCheckListReason:docCheckListReasons){
							if(!ServiceUtil.empty(docCheckListReason)){
								if(!ServiceUtil.empty(doc.getDocTypeId()) && doc.getDocTypeId().equals(ServiceCache.getConstant("INCOME_DOC"))){
									if(!docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE04")) && !ServiceConstant.YES.equals(docCheckListReason.getGenerateFlag())){
										DocumentSlaTypes iibDocSLA = new DocumentSlaTypes();
										iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE04"));				
										decisionDocSLAs.add(iibDocSLA);
										docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE04"));
									}
								}else{
									if(!docSLAtype.contains(ServiceCache.getConstant("DOC_SLA_TYPE01")) && !ServiceConstant.YES.equals(docCheckListReason.getGenerateFlag())){
										DocumentSlaTypes iibDocSLA = new DocumentSlaTypes();
										iibDocSLA.setDocumentSlaType(ServiceCache.getConstant("DOC_SLA_TYPE01"));				
										decisionDocSLAs.add(iibDocSLA);
										docSLAtype.add(ServiceCache.getConstant("DOC_SLA_TYPE01"));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static LoanTierDataM  getLoanTierDataSeq(ArrayList<LoanTierDataM> loanTiers,  int filterSeq) {
		if(null!=loanTiers && loanTiers.size()>0){
			for(LoanTierDataM loanTier :loanTiers){
				if(filterSeq==loanTier.getSeq()){
					return loanTier;
				}
			}
		}
		return null;
	}
	
	public static String isVetoEligible(List<decisionservice_iib.ApplicationDataM>  decisionApps) {
		String DECISION_FINAL_DECISION_REJECT = DecisionServiceCacheControl.getConstant("DECISION_FINAL_DECISION_REJECT");
		String isVetoEligible = FLAG_N;
		if (decisionApps == null || decisionApps.size() < 1)
			return FLAG_N;
		for (decisionservice_iib.ApplicationDataM decisionApp : decisionApps) {
			if (decisionApp == null)
				continue;
			String isVetoEligibleFlag = decisionApp.getIsVetoEligibleFlag();
			if (isVetoEligibleFlag == null || "".equals(isVetoEligibleFlag))
				continue;
			if(ProcessUtil.REC_APP_DECISION_REJECT.equals(ProcessUtil.getULORecommendDecision(decisionApp.getRecommendDecision()))){
				if (FLAG_Y.equalsIgnoreCase(isVetoEligibleFlag)) {
					isVetoEligible = FLAG_Y;
				}else{
					return FLAG_N;
				}
			}
		}
		return isVetoEligible;
	}
	public static String isVetoEligibles(List<Applications>  decisionApps) {
		String DECISION_FINAL_DECISION_REJECT = DecisionServiceCacheControl.getConstant("DECISION_FINAL_DECISION_REJECT");
		String isVetoEligible = FLAG_N;
		if (decisionApps == null || decisionApps.size() < 1)
			return FLAG_N;
		for (Applications decisionApp : decisionApps) {
			if (decisionApp == null)
				continue;
			String isVetoEligibleFlag = decisionApp.getIsVetoEligibleFlag();
			if (isVetoEligibleFlag == null || "".equals(isVetoEligibleFlag))
				continue;
			if(ProcessUtil.REC_APP_DECISION_REJECT.equals(ProcessUtil.getULORecommendDecision(decisionApp.getRecommendDecision()))){
				if (FLAG_Y.equalsIgnoreCase(isVetoEligibleFlag)) {
					isVetoEligible = FLAG_Y;
				}else{
					return FLAG_N;
				}
			}
		}
		return isVetoEligible;
	}
	public static long parseLong(Integer input) {
		if(input == null)return 0L;
		return Long.valueOf(input);
	}
	
	public static boolean isCardNull(CardDataM card)
	{
		if(card == null)
		{return true;}
		else
		{return false;}
	}
	
	public static String getFinalAppDiffReq(String recommendDecision)
	{
		if(recommendDecision != null)
		{
			if (ProcessUtil.REC_APP_DECISION_APPROVE.equalsIgnoreCase(recommendDecision)) 
			{return ProcessUtil.FINAL_APP_DECISION_APPROVE;}
			 else if (ProcessUtil.REC_APP_DECISION_REJECT.equalsIgnoreCase(recommendDecision))
			{return ProcessUtil.FINAL_APP_DECISION_REJECT; }
		}
		return null;
	}
}
