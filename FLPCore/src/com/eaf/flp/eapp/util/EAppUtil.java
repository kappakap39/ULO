package com.eaf.flp.eapp.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.ava.flp.eapp.submitapplication.model.Applications;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

public class EAppUtil {
	
	private static transient Logger logger = Logger.getLogger(ProcessUtil.class);
	public static final String APPLY_TYPE_NEW = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_NEW");
	public static final String APPLY_TYPE_ADD = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_ADD");
	public static final String APPLY_TYPE_UPGRADE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_UPGRADE");
	public static final String APPLY_TYPE_INCREASE = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_INCREASE");
	public static final String APPLY_TYPE_SUPPLIMENTARY = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_ADD_SUP");
	public static final String APPLY_TYPE_ERROR = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_ERROR");
	public static final String APPLY_TYPE_ERROR_CODE = DecisionServiceCacheControl.getConstant("APPLY_TYPE_ERROR_CODE");
	public static final String APPLY_TYPE_EXP = DecisionServiceCacheControl.getConstant("APPLICATION_TYPE_EXP");
	
	public static Date convertJodaDateTimeToDate(DateTime jodaDate){
		if(null == jodaDate){
			return null;
		}
		return new Date(jodaDate.minusHours(7).getMillis());
	}
	public static Timestamp convertJodaDateTimeToTimeStamp(DateTime jodaDate){
		if(null == jodaDate){
			return null;
		}
		return new Timestamp(jodaDate.minusHours(7).getMillis());
	}
	public static int bigDecimalToInt(BigDecimal bigValue){
		if(Util.empty(bigValue)){
			return 0;
		}
		return bigValue.intValue();
	}
	public static String bigDecimalToString(BigDecimal bigValue){
		if(Util.empty(bigValue)){
			return null;
		}
		return bigValue.toString();
	}
	public static BigDecimal stringToBigDecimal(String value){
		if(Util.empty(value)){
			return null;
		}
		return new BigDecimal(value);
	}
	public static String getImType(String qr){
		String qrtmp = "";
		if(qr.length()>18){
			qrtmp=qr.substring(5,18);
		}
		if(SystemConfig.containsGeneralParam("E_APP_QR1",qrtmp)){
			return MConstant.IMType.E_APP;
		}
		return MConstant.IMType.NORMAL;
	}

	public static boolean eAppByQr1(String qr){		
		if(SystemConfig.getArrayListGeneralParam("EAPP_PRODUCT_TYPE_CODE").contains(getProductTypeNo(qr))){
			return true;
		}
		return false;
	}
	public static String getQr1(String product){		
		if(MConstant.Product.CREDIT_CARD.equals(product)){
			return SystemConstant.getConstant("EAPP_QR1_CC_NEW");
		}else if(MConstant.Product.K_EXPRESS_CASH.equals(product)){
			return SystemConstant.getConstant("EAPP_QR1_KEC_NEW");
		}else if(MConstant.Product.K_PERSONAL_LOAN.equals(product)){
			return SystemConstant.getConstant("EAPP_QR1_KPL_NEW");
		}
		return SystemConstant.getConstant("EAPP_QR1_CC_NEW");
	}
	public static String getFormatType(String qr){
		String result="";
		if(qr.length()>20){
			result = qr.substring(0,2);
		}
		return result;
	}
	public static String getItPurpose(String qr){
		String result="";
		if(qr.length()>20){
			result = qr.substring(2,3);
			
		}
		return result;
	}
	public static String getFormLocation(String qr){
		String result="";
		if(qr.length()>20){
			result=qr.substring(3,4);
		}
		return result;
	}
	
	public static String getProductTypeNo(String qr){
		String result="";
		if(qr.length()>20){
			result=qr.substring(5,14);
			
		}
		return result;
	}
	public static String getFormTypeNo(String qr){
		String result="";
		if(qr.length()>20){
			result=qr.substring(15,18);
			
		}
		return result;
	}
	public static String getFormVersion(String qr){
		String result = "";
		if(qr.length()>20){
			result=qr.substring(19,21);
			
		}
		return result;
	}
	public static BigDecimal intToBigDecimal(int bigValue){
		if(Util.empty(bigValue)){
			return BigDecimal.ZERO;
		}
		return new BigDecimal(bigValue);
	}
	public static DateTime convertXmlGregorianCalendarToJodaDateTime(XMLGregorianCalendar xmlGregorianCalendar){
		if(null == xmlGregorianCalendar){
			return null;
		}
		return new DateTime (xmlGregorianCalendar.toGregorianCalendar().getTime());
	}
	public static String getApplyType(List<Applications> applicationList) {
		if(applicationList == null || applicationList.size() <= 0) {
			return null;
		}
		String result = null;
		Set<String> applyTypes = new HashSet<String>();
		for (Applications application : applicationList) {
			if (application == null || application.getApplyType() == null || application.getApplyType().isEmpty()) {
				continue;
			}
			applyTypes.add(application.getApplyType());
		}
		if(applyTypes.contains(APPLY_TYPE_ERROR)){
			result = APPLY_TYPE_ERROR;
		} else if (applyTypes.contains(APPLY_TYPE_NEW)) {
			result = APPLY_TYPE_NEW;
		} else if (applyTypes.contains(APPLY_TYPE_ADD)) {
			result = APPLY_TYPE_ADD;
		} else if (applyTypes.contains(APPLY_TYPE_UPGRADE)) {
			result = APPLY_TYPE_UPGRADE;
		} else if (applyTypes.contains(APPLY_TYPE_INCREASE)) {
			result = APPLY_TYPE_INCREASE;
		} else if (applyTypes.contains(APPLY_TYPE_EXP)) {
			result = APPLY_TYPE_EXP;
		} else if (applyTypes.contains(APPLY_TYPE_SUPPLIMENTARY)) {
			result = APPLY_TYPE_SUPPLIMENTARY;
		}
		logger.debug("result>>" + result);
		return result;
	}
	public static boolean cjdByQr1(String qr){		
		if(SystemConfig.getArrayListGeneralParam("CJD_PRODUCT_TYPE_CODE").contains(getProductTypeNo(qr))){
			return true;
		}
		return false;
	}
}
