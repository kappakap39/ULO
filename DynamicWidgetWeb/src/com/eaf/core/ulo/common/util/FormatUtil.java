package com.eaf.core.ulo.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FormatUtil {
	public static final String TH = "th";
	public static final String EN = "en";
	public static final int BUDDIST_ERA_MARKER = 543;
	public class Format{
		public static final String ddMMyyyy = "dd/MM/yyyy";
		public static final String ddMMMyyyy = "dd/MMM/yyyy";
		public static final String ddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";
		public static final String ddMMyyyyHHmm = "dd/MM/yyyy HH:mm";
		public static final String dd_MMM_yyyy = "dd MMM yyyy";
		public static final String HHMMSS = "HH:mm:ss";
		public static final String HHMM = "HH:mm";
		public static final String CURRENCY_FORMAT = "###,###,##0.00";
		public static final String DECIMAL_TREE_POINT_FORMAT = "###,###,##0.000";
		public static final String NUMBER_FORMAT = "###,###,###";
		public static final String yyyy = "yyyy";
		public static final String yyyyMMdd = "yyyy/MM/dd";
		public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
		public static final String yyyy_MM_dd = "yyyy-MM-dd";
		public static final String DDMMYYYY = "ddMMyyyy"; 
	}
	public static String getTime(Timestamp time,String format){
		return (null == time)?"":new SimpleDateFormat(format).format(time);
	}
	public static String getDate(Timestamp time,String format){
		return (null == time)?"":new SimpleDateFormat(format).format(time).toUpperCase();
	}
	public static String getDate(Timestamp time,String format,String regional){
		 if(null == time) return "";
		 Date date = new Date(time.getTime());
		 DateFormat $format = new SimpleDateFormat(Format.ddMMMyyyy, java.util.Locale.US);
		 String $date = $format.format(date);
		 String objDateTime[] = $date.split("\\s");
		 String objDate[] = objDateTime[0].split("/");
		 int year = Integer.parseInt(objDate[2]);		
		 if(TH.equals(regional)){
			 year += 543;
		 }
		 if(Format.dd_MMM_yyyy.equals(format)){
			return objDate[0] +" "+objDate[1].toUpperCase()+" "+year;
		 }	
		 return objDate[0] +"/"+objDate[1].toUpperCase()+"/"+year;
	}
}
