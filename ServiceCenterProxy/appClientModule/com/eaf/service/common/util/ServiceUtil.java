package com.eaf.service.common.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.owasp.esapi.ESAPI;

import com.eaf.service.common.api.ServiceApplicationDate;

public class ServiceUtil {
	
	private static transient Logger logger = Logger.getLogger(ServiceUtil.class);
    public static boolean empty(String data){
       return (data == null || "null".equals(data) || data.length() == 0 || data.trim().length() == 0);
    } 
    public static boolean empty(ArrayList<?> vList){
    	return (null == vList || vList.size() == 0 || vList.isEmpty());
    }
    public static boolean empty(Vector<?> vVect){
    	return (null == vVect || vVect.size() == 0 || vVect.isEmpty());
    }
    public static boolean empty(BigDecimal num){
    	return (null == num);
    }
    public static boolean empty(HashMap<?,?> hashMap){
    	return (null == hashMap || hashMap.size() == 0 || hashMap.isEmpty());
    }
    public static boolean empty(Date date){
    	return (null == date);
    }
    public static boolean empty(Object obj){    	
    	if(obj instanceof String){
    		return empty((String)obj);
    	}else if(obj instanceof ArrayList<?>){
    		return empty((ArrayList<?>)obj);
    	}else if(obj instanceof Vector<?>){
    		return empty((Vector<?>)obj);
    	}else if(obj instanceof BigDecimal){
    		return empty((BigDecimal)obj);
    	}else if(obj instanceof Date){
    		return empty((Date)obj);
    	}else if(obj instanceof HashMap<?,?>){
    		return empty((HashMap<?,?>)obj);
    	}
    	return (null == obj);
    }
    public static Date getCurrentDate(){
    	return ServiceApplicationDate.getDate();
    }
    public static Timestamp getCurrentTimestamp(){
    	return ServiceApplicationDate.getTimestamp();
    }
    public static boolean number(String num) {
        return (empty(num))?true:num.matches("-?\\d+(.\\d+)?");
    }
	public static java.sql.Date getPreviousMonth(java.sql.Date inputDate, int prevIndex) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(inputDate);
		calen.set(Calendar.DATE, 1);
		calen.add(Calendar.MONTH, -prevIndex);
		return new java.sql.Date(calen.getTimeInMillis());
	}
	static final String KEY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	public static String token(int len){
	   StringBuilder key = new StringBuilder(len);
	   for(int i = 0; i < len; i++) 
		  key.append(KEY.charAt(rnd.nextInt(KEY.length())));
	   return key.toString();
	}	
	public static String generateServiceReqResId(){
		SimpleDateFormat date = new SimpleDateFormat("yyMMddHHmmssSSS");
		String transectionId = date.format(getCurrentTimestamp())+token(3);
		return transectionId;		
	}
	public static String generateTransectionId(){	
		SimpleDateFormat date = new SimpleDateFormat("yyMMddHHmmssSSS");
		String transectionId = date.format(getCurrentTimestamp())+token(3);
		return transectionId;
	}
	public static String generateRqUID(String RqAppId,String uniqueId){
		StringBuilder rqUIDResult = new StringBuilder();
		rqUIDResult.append(RqAppId);
		rqUIDResult.append("_");
//			Calendar calendar = ServiceApplicationDate.getCalendar();
//		rqUIDResult.append(calendar.get(Calendar.YEAR)).append(getMonth(calendar)).append(calendar.get(Calendar.DAY_OF_MONTH));
		rqUIDResult.append(formatDate(ServiceApplicationDate.getDate(),"yyyyMMdd"));
		if(!ServiceUtil.empty(uniqueId)){
			rqUIDResult.append("_");
			rqUIDResult.append(uniqueId);	
		}
		return rqUIDResult.toString();
	}
	public static String getMonth(Calendar calendar){
		int month = (calendar.get(Calendar.MONTH)+1);
		String monthResult = null;
		if (month <= 9) {
			monthResult = "0" + String.valueOf(month);
		} else {
			monthResult = String.valueOf(month);
		}
		return monthResult;
	}
	public static final String TH = "th";
	public static final String EN = "en";
	public class Format{
		public static final String ddMMyyyy = "dd/MM/yyyy";
		public static final String dd_MMM_yyyy = "dd MMM yyyy";
		public static final String yyyy_MM_dd_HHMMSS= "yyyy-MM-dd HH:mm:ss";
		public static final String HHMMSS = "HH:mm:ss";
		public static final String CURRENCY_FORMAT = "###,###,##0.00";
		public static final String ddMMMyyyy = "dd/MMM/yyyy";
		public static final String ddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";
		public static final String ddMMyyyyHHmm = "dd/MM/yyyy HH:mm";
		public static final String HHMM = "HH:mm";
		public static final String DECIMAL_TREE_POINT_FORMAT = "###,###,##0.000";
		public static final String NUMBER_FORMAT = "###,###,###";
		public static final String yyyy = "yyyy";
		public static final String yyyyMMdd = "yyyy/MM/dd";
		public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
		public static final String yyyy_MM_dd = "yyyy-MM-dd";
		public static final String DDMMYYYY = "ddMMyyyy"; 
	}
	public static String display(String str){
		return empty(str)?"":ESAPI.encoder().encodeForHTML(str.trim());
	}
	public static String display(BigDecimal number){
		return display(number,"###0",false);
	}
	public static String display(BigDecimal number,String format){
		return display(number,format,false);
	}
	public static String display(BigDecimal number,boolean nullEmpty){
		return display(number,"###0",nullEmpty);
	}
	public static String display(BigDecimal number,String format,boolean nullEmpty){
		if(number == null && format == null)return (nullEmpty)?"":"0";
		if(number == null && nullEmpty)return "";		
		try{
			if(number == null) number = BigDecimal.ZERO;
			DecimalFormat numFormat = new DecimalFormat(format);
			return numFormat.format(number);
		}catch (Exception e){		
		}
		return "";
	}
	public static String displayCurrency(BigDecimal number,boolean nullEmpty){
		if(number == null)return (nullEmpty)?"":"0.00";
		try{
			String format = "#,##0.00";
			DecimalFormat numFormat = new DecimalFormat(format);
			return numFormat.format(number);
		}catch (Exception e){		
		}
		return "0.00";
	}
	public static String displayAttributeModel(String attribute){
		String value = "";
		attribute = attribute.toLowerCase();
		String[] Obj = attribute.split("\\_");
		for (int j = 0; j < Obj.length; j++) {
			String t = Obj[j];
			if(j > 0){
				value +=t.substring(0,1).toUpperCase()+t.substring(1);;
			}else{
				value += t;
			}
		}
		return value;
	}
	public static String display(Date date,String regional,String format){
		if(null == date){
			return "";
		}
		DateFormat $format = new SimpleDateFormat(format, java.util.Locale.US);
		String $date = $format.format(date);
		if(TH.equals(regional)){
			String objDate[] = $date.split("/");
			int year = Integer.parseInt(objDate[2])+543;
			$date =  objDate[0]+"/"+objDate[1]+"/"+year;
		}
		return $date;
	}
	public static String display(Timestamp date,String regional,String format){
		if(null == date){
			return "";
		}
		DateFormat $format = new SimpleDateFormat(format, java.util.Locale.US);
		String $date = $format.format(new java.sql.Date(date.getTime()));
		if(TH.equals(regional)){
			String objDate[] = $date.split("/");
			int year = Integer.parseInt(objDate[2])+543;
			$date =  objDate[0]+"/"+objDate[1]+"/"+year;
		}
		return $date;
	}
	public static String display(Date date,String regional){
		return display(date,regional,Format.ddMMyyyy);
	}
	public static String formatDate(Date date,String format){
		DateFormat $format = new SimpleDateFormat(format, java.util.Locale.US);
		String $date = $format.format(date);
		return $date;
	}
	public static Date toDate(String date,String regional){
		return toDate(date,regional,Format.ddMMyyyy);
	}
	public static BigDecimal toBigDecimal(String num){
		return (empty(num))?BigDecimal.ZERO:new BigDecimal(removeComma(num));
	}
	public static BigDecimal toBigDecimal(String num,boolean nullEmpty){
		if(nullEmpty && empty(num)){
			return null;
		}
		return (empty(num))?BigDecimal.ZERO:new BigDecimal(removeComma(num));
	}
	public static BigDecimal toBigDecimal(BigDecimal num,boolean nullEmpty){
		if(nullEmpty && empty(num)){
			return null;
		}
		return (empty(num))?BigDecimal.ZERO:num;
	}
	public static BigDecimal toBigDecimal(Integer num,boolean nullEmpty){
		if(nullEmpty && empty(num)){
			return null;
		}
		return (empty(num))?BigDecimal.ZERO:new BigDecimal(num);
	}
	public static BigDecimal toBigDecimal(Object num,boolean nullEmpty){
		if(nullEmpty && empty(num)){
			return null;
		}
		BigDecimal _num = BigDecimal.ZERO;
		if(!empty(_num)){
			if(num instanceof BigDecimal){
				_num = toBigDecimal((BigDecimal)num,nullEmpty);
			}else if(num instanceof String){
				_num = toBigDecimal((String)num,nullEmpty);
			}else if(num instanceof Integer){
				_num = toBigDecimal((Integer)num,nullEmpty);
			}
		}
		return _num;
	}
	public static String toString(int number){
		return toString(number, false);	
	}
	public static String toString(Date date){
		if(null==date) return "";
		return String.valueOf(date);
	}
	
	public static String toString(BigDecimal number){
		if(null==number) return "";
		return String.valueOf(number);
	}
	
	public static String toString(int number,boolean nullEmpty){
		if(nullEmpty && number == 0) {
			return null;
		}
		return String.valueOf(number);	
	}
	
	public static Date toDate(String date,String regional,String format){
		Date _date = null;
		if(empty(date)){
			return null;
		}
		try{
			if(TH.equals(regional)){
				String objDate[] = date.split("/");
				int year = Integer.parseInt(objDate[2])-543;
				date =  objDate[0]+"/"+objDate[1]+"/"+year;
			}
			DateFormat $format = new SimpleDateFormat(format,java.util.Locale.US);
			_date = new java.sql.Date($format.parse(date).getTime());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return _date;
	}
	public static String removeComma(String value){
		return (empty(value))?value:value.replaceAll("\\,","");
	}
	public static int getInt(String num){
		if(empty(num)){
			num = "0";
		}
		return Integer.parseInt(num);
	}
	public static Long getLong(String num){
		try{
			if(empty(num)){
				num = "0";
			}
		return Long.parseLong(num);
		}catch(Exception e){return new Long(0);}
	}
	public static String getString(int num){
		return String.valueOf(num);
	}
	public static String display(HashMap<String,Object> Row,String key,String format){
		Object obj = Row.get(key);
		if(null != obj){
			if(obj instanceof java.math.BigDecimal){
				return display((BigDecimal)obj,format);
			}else if(obj instanceof java.lang.Double){
				return String.valueOf(obj);
			}
		}
		return "";
	}
	public static String display(HashMap<String,Object> Row,String key,String format,String regional){
		Object obj = Row.get(key);
		if(null != obj){
			if(obj instanceof java.sql.Date){  
				java.sql.Date date = (java.sql.Date)obj;
				display(date,regional,format);
			}else if(obj instanceof java.sql.Timestamp){
				  
			}
		}
		return "";
	}
	public static String displayText(String str){
		return empty(str)?"":str;
	}
	public static String toNumericStr(String str){
		if(str==null)return "";
		
		str = str.replaceAll("[^\\d]", "");
		str = str.replaceFirst("^0+(?!$)", "");
		return str;
	}
	
	public static String replaceAll(String str,String txt){
		if(str==null)return "";
		
		str = str.replaceAll(txt, "");
	
		return str;
	}
	
	public static String getReqRespTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(getCurrentTimestamp());
	}
	public static Date getReqRespTimestamp(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return new Date(sdf.parse(time).getTime());
	}
	
	public static XMLGregorianCalendar getXMLGregorianCalendar(){
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(ServiceApplicationDate.getCalendar().getTime());
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			logger.fatal("XML Gregorian Calendar can not generate");
		}
		return xmlGregorianCalendar;
	}
	public static Date toDate(Calendar calendarDate){
		try{
			if(null != calendarDate){
				return new Date(calendarDate.getTimeInMillis());
			}
		}catch(Exception e){}
		return null;
	}
	public static Timestamp toTimestamp(Calendar calendarDate){
		try{
			if(null != calendarDate){
				return new Timestamp(calendarDate.getTimeInMillis());
			}
		}catch(Exception e){}
		return null;
	}
	
	public static int differenceYear(Date startDate,Date endDate){
		if(null==startDate || null==endDate)return 0;
		int diffYear = 0;
		try{
		  Calendar startCalendar = Calendar.getInstance(); 
		  startCalendar.setTime(startDate);
		  Calendar endCalendar = Calendar.getInstance();
		  endCalendar.setTime(endDate);
		  diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		}catch(Exception e){
			e.printStackTrace();
		}
		return diffYear;
	}	
	
	public static String displayDateFormat(Date date,String regional,String format){
		if(null==date) return "";
		Timestamp dateTimeStamp = new Timestamp(date.getTime());
		return displayDateFormat(dateTimeStamp, regional, format);
	}
	public static String displayDateFormat(Timestamp date,String regional,String format){
		if(null == date){
			return "";
		}
		DateFormat $format = new SimpleDateFormat(Format.ddMMyyyyHHmmss, java.util.Locale.US);
		String $date = $format.format(date);
		//logger.debug("Date str : " + $date);
		String objDateTime[] = $date.split("\\s");
		String objDate[] = objDateTime[0].split("/");
		int year = Integer.parseInt(objDate[2]);
		if (TH.equals(regional)) {
			year += 543;
		}
		
		String dateStr1 = objDate[0] + "/" + objDate[1] + "/" + year;
		String dateStr2 = year + "/" + objDate[1] + "/" + objDate[0];
		String timeStr = "";
		if (objDateTime.length > 1) {
			timeStr = objDateTime[1];
		}
		if (Format.ddMMyyyyHHmmss.equals(format)) {
			$date = dateStr1 + " | " + timeStr;
		} else if (Format.ddMMyyyy.equals(format)) {
			$date = dateStr1;
		} else if (Format.HHMMSS.equals(format)) {
			$date = timeStr;
		} else if (Format.ddMMyyyyHHmm.equals(format)) {
			$date = dateStr1 + " " + timeStr.substring(0, 5);
		} else if (Format.yyyyMMdd.equals(format)) {
			$date = dateStr2;
		} 
		return $date;
	}
	public static String removeAllSpecialCharactors(String str){
		if(!empty(str)){
			str=str.replaceAll("[\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E\\u0021-\\u002F]","");
		}
		return str;
	}
	
	public static String getJSONString(JSONObject jsonObj, String key)
	{
		try
		{
		   return  jsonObj.getString(key);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static JSONObject getJSONObject(JSONObject jsonObj, String key)
	{
		try
		{
		   return  jsonObj.getJSONObject(key);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static JSONArray getJSONArray(JSONObject jsonObj, String key)
	{
		try
		{
		   return  jsonObj.getJSONArray(key);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static String getServerIp(){
		InetAddress inetAddress=null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(inetAddress!=null){
			return inetAddress.getHostAddress();
		}else{
			return "";
		}

	}

}
