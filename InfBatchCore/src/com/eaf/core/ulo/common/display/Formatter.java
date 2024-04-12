package com.eaf.core.ulo.common.display;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;

public class Formatter{
	private static transient Logger logger = Logger.getLogger(Formatter.class);
	public static Locale locale = new Locale("th", "TH");
	public static final String TH = "th";
	public static final String EN = "en";
	public class Format{
		public static final String ddMMyyyy = "dd/MM/yyyy";
		public static final String ddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";
		public static final String dd_MMM_yyyy = "dd MMM yyyy";
		public static final String dd_MMM_yyyy2 = "dd-MMM-yyyy";
		public static final String DDMMYYYY = "ddMMyyyy";
		public static final String yyyyMMdd_HHmmss = "yyyyMMdd_HHmmss";
		public static final String YYYYMMDD = "yyyyMMdd";
		public static final String YYMMDD = "YYMMdd";
		public static final String yyMMDD = "yyMMdd";
		public static final String MMMddyyyy = "MMM dd,yyyy";
		public static final String HHMMSS = "HH:mm:ss";
		public static final String HHMM = "HH:mm";
		public static final String CURRENCY_FORMAT = "###,###,##0.00";
		public static final String DECIMAL_TREE_POINT_FORMAT = "###,###,##0.000";
		public static final String NUMBER_FORMAT = "###,###,###";
		public static final String MMMMddyyyy = "MMMM dd, yyyy";
		public static final String yyyy_MM_dd = "yyyy-MM-dd";
		public static final String yyyy_MM_dd_T_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'";
		public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	}
	public static String format(Date date) {
		return format(date, Locale.US);
	}
	public static String formatTh(Date date) {
		return format(date,locale);
	}
	public static String format(Date date, Locale locale) {
		return format(date,InfBatchProperty.getInfBatchConfig("DATE_FORMAT"), locale);
	}
	public static String format(Date date, String pattern) {
		return format(date, pattern, Locale.US);
	}
	public static String format(Date date, String pattern, Locale locale) {
		if (null == date || null == pattern || null == locale) {
			return null;
		}
		return new SimpleDateFormat(pattern, locale).format(date);
	}
	public static String formatCurrency(BigDecimal number) {
		return formatCurrency(number, false);
	}
	public static String formatCurrency(BigDecimal number, boolean nullEmpty) {
		if (number == null)
			return (nullEmpty) ? "" : "0.00";
		try {
			String format = "#,##0.00";
			DecimalFormat numFormat = new DecimalFormat(format);
			return numFormat.format(number);
		} catch (Exception e) {
		}
		return "0.00";
	}
	public static String getDay(Date date) {
		if (null == date) {
			return null;
		}
		return new SimpleDateFormat("dd", Locale.US).format(date);
	}
	public static String getMonth(Date date) {
		if (null == date) {
			return null;
		}
		return new SimpleDateFormat("MM", Locale.US).format(date);
	}
	public static String getMonthName(Date date) {
		if (null == date) {
			return null;
		}
		return new SimpleDateFormat("MMMM", Locale.US).format(date);
	}
	public static String getYear(Date date) {
		if (null == date) {
			return null;
		}
		return new SimpleDateFormat("yyyy", Locale.US).format(date);
	}
	public static String replece(String str){
		return InfBatchUtil.empty(str)?"":str;
	}
	public static String displayText(String str){
		return InfBatchUtil.empty(str)?"":str;
	}
	public static String display(String str){
		return InfBatchUtil.empty(str)?"":ESAPI.encoder().encodeForHTML(str.trim());
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
		if(TH.equals(regional) && !Format.HHMMSS.equals(format)){
			String objDate[] = $date.split("/");
			if(objDate.length == 3){
				int year = Integer.parseInt(objDate[2])+543;
				$date =  objDate[0]+"/"+objDate[1]+"/"+year;
			}
		}
		return $date;
	}
	public static String display(Timestamp date,String regional,String format){
		if(null == date){
			return "";
		}
		DateFormat $format = new SimpleDateFormat(format, java.util.Locale.US);
		String $date = $format.format(date);
		if (TH.equals(regional) && !Format.HHMMSS.equals(format)) {
			String objDateTime[] = $date.split("\\s");
			String objDate[] = objDateTime[0].split("/");
			int year = Integer.parseInt(objDate[2]) + 543;
			$date = objDate[0] + "/" + objDate[1] + "/" + year + " | " + objDateTime[1];
		}
		return $date;
	}
	public static String display(Date date,String regional){
		return display(date,regional,Format.ddMMyyyy);
	}
	public static Date toDate(String date,String regional){
		return toDate(date,regional,Format.ddMMyyyy);
	}
	public static BigDecimal toBigDecimal(String num){
		return (InfBatchUtil.empty(num))?BigDecimal.ZERO:new BigDecimal(removeComma(num));
	}
	public static BigDecimal toBigDecimal(String num,boolean nullEmpty){
		if(nullEmpty && InfBatchUtil.empty(num)){
			return null;
		}
		return (InfBatchUtil.empty(num))?BigDecimal.ZERO:new BigDecimal(removeComma(num));
	}
	public static BigDecimal toBigDecimal(BigDecimal num,boolean nullEmpty){
		if(nullEmpty && InfBatchUtil.empty(num)){
			return null;
		}
		return (InfBatchUtil.empty(num))?BigDecimal.ZERO:num;
	}
	public static BigDecimal toBigDecimal(Object num,boolean nullEmpty){
		if(nullEmpty && InfBatchUtil.empty(num)){
			return null;
		}
		BigDecimal _num = BigDecimal.ZERO;
		if(!InfBatchUtil.empty(_num)){
			if(num instanceof BigDecimal){
				_num = toBigDecimal((BigDecimal)num,nullEmpty);
			}else if(num instanceof String){
				_num = toBigDecimal((String)num,nullEmpty);
			}
		}
		return _num;
	}
	public static String toString(int number){
		return toString(number, false);	
	}
	
	public static String toString(int number,boolean nullEmpty){
		if(nullEmpty && number == 0) {
			return null;
		}
		return String.valueOf(number);	
	}
	
	public static Date toDate(String date,String regional,String format){
		Date _date = null;
		if(InfBatchUtil.empty(date)){
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
	static final String KEY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	public static String token(int len){
	   StringBuilder key = new StringBuilder(len);
	   for(int i = 0; i < len; i++) 
		  key.append( KEY.charAt(rnd.nextInt(KEY.length())));
	   return key.toString();
	}
	public static String removeComma(String value){
		return (InfBatchUtil.empty(value))?value:value.replaceAll("\\,","");
	}
	public static String removeDash(String value){
		return (InfBatchUtil.empty(value))?value:value.replaceAll("\\-","");
	}
	public static int getInt(String num){
		if(InfBatchUtil.empty(num)){
			num = "0";
		}
		return Integer.parseInt(num);
	}
	public static String getString(int num){
		return String.valueOf(num);
	}
	public static String display(Map<String,Object> Row,String key){
		Object obj = Row.get(key);
		if(null != obj){
			if(obj instanceof String){
				return display((String)obj);
			}else if(obj instanceof java.sql.Date){
				java.sql.Date date = (java.sql.Date)obj;
				display(date,TH);
			}else if(obj instanceof java.sql.Timestamp){
				
			}else if(obj instanceof java.math.BigDecimal){
				return display((BigDecimal)obj);
			}else if(obj instanceof java.lang.Double){
				return String.valueOf(obj);
			}else if(obj instanceof java.lang.Long){
				return String.valueOf(obj);
			}
		}
		return "";
	}
	public static String display(HashMap<String,Object> Row,String key){
		Object obj = Row.get(key);
		if(null != obj){
			if(obj instanceof String){
				return display((String)obj);
			}else if(obj instanceof java.sql.Date){
				java.sql.Date date = (java.sql.Date)obj;
				return display(date,TH);
			}else if(obj instanceof java.sql.Timestamp){
				Timestamp timestamp = (Timestamp) obj;
				return display(timestamp, TH, Format.ddMMyyyyHHmmss);
			}else if(obj instanceof java.math.BigDecimal){
				return display((BigDecimal)obj);
			}else if(obj instanceof java.lang.Double){
				return String.valueOf(obj);
			}else if(obj instanceof java.lang.Long){
				return String.valueOf(obj);
			}
		}
		return "";
	}
	public static String display(Map<String,Object> Row,String key,String format){
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
	public static String display(Map<String,Object> Row,String key,String format,String regional){
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
	public static String getSmartDataEntryId(String id1,String id2){
		return id1+"_"+id2;
	}
	public static String getSmartDataEntryId(String id1,int seq){
		return id1+"_"+String.valueOf(seq);
	}
	public static String getSmartDataEntryId(String id1,String id2,int seq){
		return id1+"_"+id2+"_"+String.valueOf(seq);
	}
	public static String getTime(Timestamp time,String format){
		return (null == time)?"":new SimpleDateFormat(format).format(time);
	}
	public static String getDate(Timestamp time,String format){
		return (null == time)?"":new SimpleDateFormat(format).format(time).toUpperCase();
	}
	public static String getPhoneNo(String input){
		String mask = "#-####-####";
		String value = "";
		if(!InfBatchUtil.empty(input)){
			try{
				MaskFormatter maskFormatter = new MaskFormatter(mask);
					maskFormatter.setValueContainsLiteralCharacters(false);
				value =	maskFormatter.valueToString(input);
			}catch(Exception e){			
			}
		}
		return value;
	}
	public static String getMobileNo(String input){
		String mask = "##-####-####";
		String value = "";
		if(!InfBatchUtil.empty(input)){
			try{
				MaskFormatter maskFormatter = new MaskFormatter(mask);
					maskFormatter.setValueContainsLiteralCharacters(false);
				value =	maskFormatter.valueToString(input);
			}catch(Exception e){			
			}
		}
		return value;
	}
	public static String getAccountNo(String input){
		String mask = "###-#-#####-#";
		String value = "";
		if(!InfBatchUtil.empty(input)){
			try{
				MaskFormatter maskFormatter = new MaskFormatter(mask);
					maskFormatter.setValueContainsLiteralCharacters(false);
				value =	maskFormatter.valueToString(input);
			}catch(Exception e){			
			}
		}
		return value;
	}
	public static String getCardNo(String input){
		String mask = "####-####-####-####";
		String value = "";
		if(!InfBatchUtil.empty(input)){
			try{
				MaskFormatter maskFormatter = new MaskFormatter(mask);
					maskFormatter.setValueContainsLiteralCharacters(false);
				value =	maskFormatter.valueToString(input);
			}catch(Exception e){			
			}
		}
		return value;
	}
	
	public static java.sql.Date toSQLDate(String date,String regional,String format){
		java.sql.Date _date = null;
		if(InfBatchUtil.empty(date)){
			return null;
		}
		try{
			if(Formatter.EN.equals(regional)){
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
	
	public static String getVcEmpId(String userWebScan){
		return (!Util.empty(userWebScan) && userWebScan.length()>=6)?userWebScan.substring(userWebScan.length()-6):"";
	}
}
