/**
 * Create Date Mar 20, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.view.form.exception.InvalidDateFormatException;
import com.eaf.orig.cache.properties.MainSaleTypeProperties;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;

/**
 * @author Sankom
 *
 */    
public class DataFormatUtility{  
	
   private static Logger log = Logger.getLogger(DataFormatUtility.class);
   
   public static class DateFormatConstant{
	   public static final String FORMAT_DDMMYYYY_S = "dd/MM/yyyy";
	   public static final String FORMAT_DDMMYYY_S_HHMM="dd/MM/yyyy hh:mm";
	   public static final String FORMAT_DDMMYYY_S_HHMMSS="dd/MM/yyyy hh:mm:ss";
	   public static final String FORMAT_DDMMYYY_S_HHMMSS24="dd/MM/yyyy HH:mm:ss";
	   public static final String FORMAT_YYYYMMDD_S = "yyyy/MM/dd";
   }  
   public static class DateFormatType{
	   public static final int FORMAT_DDMMYYY_S = 1;
	   public static final int FORMAT_DDMMYYY_S_HHMM = 2;
	   public static final int FORMAT_DAY_OF_WEEK = 3;
	   public static final int FORMAT_CONSTELLATION = 4;
	   public static final int FORMAT_DDMMYYY_S_HHMMSS = 5;
	   public static final int FORMAT_DDMMYYY_S_HHMMSS24 = 6;
   }
   
   public static Date stringToDate(String date,String format) throws Exception{
	   if(!date.isEmpty()){
		   if (format.equalsIgnoreCase("ddmmyyyy")) {
				if (date.length() == 8) {
					Calendar cal = Calendar.getInstance();
					int dd = StringToInt(date.substring(0, 2));
					int mm = StringToInt(date.substring(2, 4)) - 1;
					int yyyy = StringToInt(date.substring(4, 8));
					cal.set(yyyy, mm, dd);
					return cal.getTime();
				} else {
					throw new InvalidDateFormatException(
						"DisplayFormatUtil>>StringToDate>>Date format received is "
							+ date
							+ "---> Date format expected is ddmmyyyy");
				}
			} else if (format.equalsIgnoreCase("yyyymmdd")) {
				if (date.length() == 8) {
					Calendar cal = Calendar.getInstance();
					int yyyy = StringToInt(date.substring(0, 4));
					int mm = StringToInt(date.substring(4, 6)) - 1;
					int dd = StringToInt(date.substring(6, 8));
					cal.set(yyyy, mm, dd);
					return cal.getTime();
				} else {
					throw new InvalidDateFormatException(
						"DisplayFormatUtil>>StringToDate>>Date format received is "
							+ date
							+ "---> Date format expected is yyyymmdd");
				}
			} else if (format.equalsIgnoreCase("dd/mm/yyyy")) {
				int fromIndex;
				int toIndex;
				String dd;
				String mm;
				String yyyy;
				fromIndex = 0;
				toIndex = date.indexOf("/");
				if (toIndex >= 0) { // found
					dd = date.substring(fromIndex, toIndex);
					dd = (dd.length() == 1 ? "0" + dd : dd);
					fromIndex = toIndex + 1;
					toIndex = date.indexOf("/", fromIndex);
					if (toIndex >= 0) { // found
						mm = date.substring(fromIndex, toIndex);
						mm = (mm.length() == 1 ? "0" + mm : mm);
						fromIndex = toIndex + 1;
						yyyy = date.substring(fromIndex, fromIndex+4);
					} else {
						throw new InvalidDateFormatException(
							"DisplayFormatUtil>>StringToDate>>Date format received is "
								+ date
								+ "---> Date format expected is dd/mm/yyyy");
					}
				} else {
					throw new InvalidDateFormatException(
						"DisplayFormatUtil>>StringToDate>>Date format received is "
							+ date
							+ "---> Date format expected is dd/mm/yyyy");
				}
				Calendar cal = Calendar.getInstance();
				int d = StringToInt(dd);
				int m = StringToInt(mm) - 1;
				int y = StringToInt(yyyy);
				cal.set(y, m, d);
				return cal.getTime();
			}else if (format.equalsIgnoreCase("mm/dd/yyyy")) {
				int fromIndex;
				int toIndex;
				String dd;
				String mm;
				String yyyy;
				fromIndex = 0;
				toIndex = date.indexOf("/");
				if (toIndex >= 0) { // found
					mm = date.substring(fromIndex, toIndex);
					mm = (mm.length() == 1 ? "0" + mm : mm);
					fromIndex = toIndex + 1;
					toIndex = date.indexOf("/", fromIndex);
					if (toIndex >= 0) { // found
						dd = date.substring(fromIndex, toIndex);
						dd = (dd.length() == 1 ? "0" + dd : dd);
						fromIndex = toIndex + 1;
						yyyy = date.substring(fromIndex);
					} else {
						throw new InvalidDateFormatException(
							"DisplayFormatUtil>>StringToDate>>Date format received is "
								+ date
								+ "---> Date format expected is mm/dd/yyyy");
					}
				} else {
					throw new InvalidDateFormatException(
						"DisplayFormatUtil>>StringToDate>>Date format received is "
							+ date
							+ "---> Date format expected is mm/dd/yyyy");
				}
				Calendar cal = Calendar.getInstance();
				int d = StringToInt(dd);
				int m = StringToInt(mm) - 1;
				int y = StringToInt(yyyy);
				cal.set(y, m, d);
				return cal.getTime();
			}else{		
				//format.equalsIgnoreCase("dd/MM/YYYY HH:mm:ss"
				if("".equals(date)){return null;}
				try {
					SimpleDateFormat formatter = new SimpleDateFormat(format); //please notice the capital M
					return  formatter.parse(date);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
	   } else {
		   return null;
	   }
   }   
   
   public static Date stringToDateTH(String date,String format)	throws Exception {		
		if (format.equalsIgnoreCase("ddmmyyyy")) {
			if (date.length() == 8) {
				Calendar cal = Calendar.getInstance();
				int dd = StringToInt(date.substring(0, 2));
				int mm = StringToInt(date.substring(2, 4)) - 1;
				int yyyy = StringToInt(date.substring(4, 8));
				yyyy=yyyy-543;
				cal.set(yyyy, mm, dd);
				return cal.getTime();
			} else {
				throw new InvalidDateFormatException(
					"DisplayFormatUtil>>StringToDate>>Date format received is "
						+ date
						+ "---> Date format expected is ddmmyyyy");
			}
		} else if (format.equalsIgnoreCase("yyyymmdd")) {
			if (date.length() == 8) {
				Calendar cal = Calendar.getInstance();
				int yyyy = StringToInt(date.substring(0, 4));
				int mm = StringToInt(date.substring(4, 6)) - 1;
				int dd = StringToInt(date.substring(6, 8));
				yyyy=yyyy-543;
				cal.set(yyyy, mm, dd);
				return cal.getTime();
			} else {
				throw new InvalidDateFormatException(
					"DisplayFormatUtil>>StringToDate>>Date format received is "
						+ date
						+ "---> Date format expected is yyyymmdd");
			}
		} else if (format.equalsIgnoreCase("dd/mm/yyyy")) {
			int fromIndex;
			int toIndex;
			String dd;
			String mm;
			String yyyy;
			fromIndex = 0;
			toIndex = date.indexOf("/");
			if (toIndex >= 0) { // found
				dd = date.substring(fromIndex, toIndex);
				dd = (dd.length() == 1 ? "0" + dd : dd);
				fromIndex = toIndex + 1;
				toIndex = date.indexOf("/", fromIndex);
				if (toIndex >= 0) { // found
					mm = date.substring(fromIndex, toIndex);
					mm = (mm.length() == 1 ? "0" + mm : mm);
					fromIndex = toIndex + 1;
					yyyy = date.substring(fromIndex);
				} else {
					throw new InvalidDateFormatException(
						"DisplayFormatUtil>>StringToDate>>Date format received is "
							+ date
							+ "---> Date format expected is dd/mm/yyyy");
				}
			} else {
				throw new InvalidDateFormatException(
					"DisplayFormatUtil>>StringToDate>>Date format received is "
						+ date
						+ "---> Date format expected is dd/mm/yyyy");
			}
			Calendar cal = Calendar.getInstance();
			int d = StringToInt(dd);
			int m = StringToInt(mm) - 1;
			int y = StringToInt(yyyy);
			y=y-543;
			cal.set(y, m, d);
			return cal.getTime();
		}else if (format.equalsIgnoreCase("mm/dd/yyyy")) {
			int fromIndex;
			int toIndex;
			String dd;
			String mm;
			String yyyy;
			fromIndex = 0;
			toIndex = date.indexOf("/");
			if (toIndex >= 0) { // found
				mm = date.substring(fromIndex, toIndex);
				mm = (mm.length() == 1 ? "0" + mm : mm);
				fromIndex = toIndex + 1;
				toIndex = date.indexOf("/", fromIndex);
				if (toIndex >= 0) { // found
					dd = date.substring(fromIndex, toIndex);
					dd = (dd.length() == 1 ? "0" + dd : dd);
					fromIndex = toIndex + 1;
					yyyy = date.substring(fromIndex);
				} else {
					throw new InvalidDateFormatException(
						"DisplayFormatUtil>>StringToDate>>Date format received is "
							+ date
							+ "---> Date format expected is mm/dd/yyyy");
				}
			} else {
				throw new InvalidDateFormatException(
					"DisplayFormatUtil>>StringToDate>>Date format received is "
						+ date
						+ "---> Date format expected is mm/dd/yyyy");
			}
			Calendar cal = Calendar.getInstance();
			int d = StringToInt(dd);
			int m = StringToInt(mm) - 1;
			int y = StringToInt(yyyy);
			y=y-543;
			cal.set(y, m, d);
			return cal.getTime();
		}else   {		
			//format.equalsIgnoreCase("dd/MM/YYYY HH:mm:ss"
			if("".equals(date)){return null;}
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(format); //please notice the capital M
				 Date dt=formatter.parse(date);
				 Calendar cl=Calendar.getInstance();
				 cl.setTime(dt);
				 cl.set(  Calendar.YEAR,cl.get(Calendar.YEAR)-543);
				return cl.getTime();
			} catch (Exception e) {
				log.fatal("Error ",e);
				return null;
			}
		}	 	 
 
   }
    public static int StringToInt(String str) {
	   if(!OrigUtil.isEmptyString(str)){
		   return (Integer.valueOf(str)).intValue();
	   }else{
		   return 0;
	   }
	}
   
	public static long StringToLong(String str) {
		 if(!OrigUtil.isEmptyString(str)){
			return (Long.valueOf(str)).longValue();
		}else{
			return 0;
		}
	}
	
	public static double StringToDouble(String str) {
		 if(!OrigUtil.isEmptyString(str)){
			return (Double.valueOf(str)).doubleValue();
		}else{
			return 0;
		}
	}
	
	public static String dateToStringFormatTh(Date date){
		if(date==null){return "";}
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
		String strDate = df.format(date);
		String year = strDate.substring(6,10);
		String yearTh = String.valueOf(Integer.parseInt(year)+ 543);
		StringBuilder str = new StringBuilder(strDate);
		str.replace(6,10,yearTh);
		
		return str.toString();
	}
	 public static String IntToString(int val) {
		if(val==0)	return "";			
		return String.valueOf(val);
	 }
	
	
	public static String dateToString(Date d ,String format){
		return  DateEnToStringDateTh(d ,DateFormatType.FORMAT_DDMMYYY_S);
	}
	
	/**
	 * @author septemwi
	 * */
	public static String DateEnToStringDateTh(Date date , int dateType){
//			log.debug("[dateEnToStringDateTh].. Date "+date);
			if(date == null){
				return "";
			}			
			
			DateFormat dateFormat = null;
			Calendar calender = null;			
			
			switch (dateType){
				case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S :
					dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYYY_S);
					calender = Calendar.getInstance();
					calender.setTime(date);
					calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
					calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
					calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)+543);
					return dateFormat.format(calender.getTime());
				case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMM:
					dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMM);
					calender = Calendar.getInstance();
					calender.setTime(date);
						calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
						calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
						calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)+543);
						calender.set(Calendar.HOUR,calender.get(Calendar.HOUR));
						calender.set(Calendar.MINUTE,calender.get(Calendar.MINUTE));
					return dateFormat.format(calender.getTime());
				case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS:
					dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMMSS);
					calender = Calendar.getInstance();
					calender.setTime(date);
					calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
					calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
					calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)+543);
					calender.set(Calendar.HOUR,calender.get(Calendar.HOUR));
					calender.set(Calendar.MINUTE,calender.get(Calendar.MINUTE));
					calender.set(Calendar.SECOND,calender.get(Calendar.SECOND));
					return dateFormat.format(calender.getTime());
				case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24:
					dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMMSS24);
					calender = Calendar.getInstance();
					calender.setTime(date);
					calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
					calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
					calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)+543);
					calender.set(Calendar.HOUR_OF_DAY,calender.get(Calendar.HOUR_OF_DAY));
					calender.set(Calendar.MINUTE,calender.get(Calendar.MINUTE));
					calender.set(Calendar.SECOND,calender.get(Calendar.SECOND));
					return dateFormat.format(calender.getTime());
				default:
					break;
			}
		return "";
	}
	
	public static String TimestampEnToStringDateTh(Timestamp date , int dateType){
		if(date == null){
			return "";
		}			
		
		DateFormat dateFormat = null;
		Calendar calender = null;			
		
		switch (dateType){
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMM:
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMM);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)+543);
				return dateFormat.format(calender.getTime());
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS:
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMMSS);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)+543);
				return dateFormat.format(calender.getTime());
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24:
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMMSS24);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)+543);
				return dateFormat.format(calender.getTime());
			default:
			break;
		}
	return "";
}
	
	/**
	 * @author septemwi
	 * */
	
	public static Date StringThToDateEn(String date ,int dateType) throws Exception{
		if(OrigUtil.isEmptyString(date)){
			return null;
		}		
		try{
			switch (dateType){
				case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S :
						SimpleDateFormat dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYYY_S);
						 Calendar calender = Calendar.getInstance();
						 calender.setTime(dateFormat.parse(date));
						 calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
						 calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
						 calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)-543);
					return calender.getTime();
				default:
					break;			
			}	
		}catch (Exception e) {
			log.fatal("[StringThToDateEn] Error "+e.getMessage());
		}
		return null;
	}
	public static Date StringEnToDateEn(String date ,int dateType) throws Exception{
		if(OrigUtil.isEmptyString(date)){
			return null;
		}		
		try{
			switch (dateType){
				case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S :
					SimpleDateFormat dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYYY_S);
					return new Date(dateFormat.parse(date).getTime());
				default:
					break;			
			}	
		}catch (Exception e) {
			log.fatal("[StringThToDateEn] Error "+e.getMessage());
		}
		return null;
	}
	public static Timestamp StringThToTimestampEn(String date ,int dateType) throws Exception{
		if(OrigUtil.isEmptyString(date)){
			return null;
		}		
		try{
			switch (dateType){
				case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S :
						SimpleDateFormat dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYYY_S);
						 Calendar calender = Calendar.getInstance();
						 calender.setTime(dateFormat.parse(date));
						 calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
						 calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
						 calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)-543);
					return new Timestamp(calender.getTime().getTime());
				default:
					break;			
			}	
		}catch (Exception e) {
			log.fatal("[StringThToDateEn] Error "+e.getMessage());
		}
		return null;
	}
	
	public static String dateTimetoStringForThai(Date dateValue){
		if (dateValue != null) {
			DecimalFormat dformat = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			c.setTime(dateValue);
			StringBuilder date = new StringBuilder();
			date.append(dformat.format(c.get(Calendar.DAY_OF_MONTH))).append(
				"/");
			date.append(dformat.format(c.get(Calendar.MONTH) + 1)).append("/");
			date.append((c.get(Calendar.YEAR)+543));
			date.append(" ");
			date.append(dformat.format(c.get(Calendar.HOUR_OF_DAY))).append(
				":");
			date.append(dformat.format(c.get(Calendar.MINUTE))).append(":");
			date.append(dformat.format(c.get(Calendar.SECOND)));
			return date.toString();
		} else {
			return "";
		}
		
	}
	public static String dateTimetoStringForEN(Date dateValue){
		if (dateValue != null) {
			DecimalFormat dformat = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			c.setTime(dateValue);
			StringBuilder date = new StringBuilder();
			date.append(dformat.format(c.get(Calendar.DAY_OF_MONTH))).append("/");
			date.append(dformat.format(c.get(Calendar.MONTH) + 1)).append("/");
			date.append(c.get(Calendar.YEAR));
			date.append(" ");
			date.append(dformat.format(c.get(Calendar.HOUR_OF_DAY))).append(":");
			date.append(dformat.format(c.get(Calendar.MINUTE))).append(":");
			date.append(dformat.format(c.get(Calendar.SECOND)));
			return date.toString();
		} else {
			return "";
		}		
	}
	public static String datetoStringForThai(Date dateValue){
		
		if (dateValue != null) {
			DecimalFormat dformat = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			c.setTime(dateValue);
			StringBuilder date = new StringBuilder();
			date.append(dformat.format(c.get(Calendar.DAY_OF_MONTH))).append("/");
			date.append(dformat.format(c.get(Calendar.MONTH) + 1)).append("/");
			date.append((c.get(Calendar.YEAR)+543));
			return date.toString();
		} else {
			return "";
		}
		
	}
	public static String stringDateTimeValueListForThai(String strDateTime) {
		if (strDateTime != null && !strDateTime.isEmpty()) {
			String[] arrDateTime = strDateTime.split(" ");
			String strDate = arrDateTime[0];
			String strTime = arrDateTime[1];
			
			String[] arrDate = strDate.split("-");
			String[] arrTime = strTime.split(":");
			if (arrTime[2] != null && arrTime[2].length() >= 2) {
				arrTime[2] = arrTime[2].substring(0, 2);
			}
			
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(arrDate[2]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[0]), Integer.parseInt(arrTime[0]), Integer.parseInt(arrTime[1]), Integer.parseInt(arrTime[2]));
			DecimalFormat dformat = new DecimalFormat("00");
			
			StringBuilder date = new StringBuilder();
			date.append(arrDate[2]).append("/");
			date.append(arrDate[1]).append("/");
			date.append(Integer.parseInt(arrDate[0])+543);

			date.append(" ");
			date.append(dformat.format(c.get(Calendar.HOUR_OF_DAY))).append(":");
			date.append(dformat.format(c.get(Calendar.MINUTE))).append(":");
			date.append(dformat.format(c.get(Calendar.SECOND)));
			return date.toString();
		} else {
			return "-";
		}
	}
	
	/**
	 * Format parameter yyyy-mm-dd
	 */
	public static String stringDateValueListForThai(String strDate) {
		if (strDate != null && !strDate.isEmpty()) {
			
			String[] arrDate = strDate.split("-");
	
			StringBuilder date = new StringBuilder();
			date.append(arrDate[2]).append("/");
			date.append(arrDate[1]).append("/");
			date.append(Integer.parseInt(arrDate[0])+543);
			return date.toString();
		} else {
			return "";
		}
	}
	public static BigDecimal replaceCommaForBigDecimal(String num) {
		//logger.debug("num = "+num);
		if(OrigUtil.isEmptyString(num)){
			return new BigDecimal(0);
		}
		StringBuilder buf = new StringBuilder(num);
//		int size = buf.length();
		for (int i = 0; i < buf.length(); i++) {
			char c = buf.charAt(i);
			//logger.debug("c"+c);
			Character cW = new Character(c);
			Character compare = new Character(',');
			if (cW.equals(compare)) {
				buf.replace(i, i + 1, "");
				//logger.debug("buf = "+buf);
			}
		}
		BigDecimal d = new BigDecimal(buf + "");
		return d;
	}
	
	/**#Septemwi*/	
	public static String DisplayCurrencyNullEmpty(BigDecimal bigDecZero) {
		try{
			if (bigDecZero == null || String.valueOf(bigDecZero) == null) 
				return "";			
			return  displayCommaNumber(bigDecZero);
		}catch(Exception e){
			log.error("Error >>> "+e.getMessage());
			return "";
		}
	}
	
	public static String DisplayCurrencyNullZero(BigDecimal bigDecZero) {
		try{
			if (bigDecZero == null || String.valueOf(bigDecZero) == null || bigDecZero.compareTo(BigDecimal.ZERO)==0) 
				return "0.00";			
			return  displayCommaNumber(bigDecZero);
		}catch(Exception e){
			log.error("Error >>> "+e.getMessage());
			return "";
		}
	}
	
	public static String DisplayNumberNullEmpty(BigDecimal bigDecZero) {
		try{
			if (bigDecZero == null || String.valueOf(bigDecZero) == null) 
				return "";			
			return  String.valueOf(bigDecZero);
		}catch(Exception e){
			log.error("Error >>> "+e.getMessage());
			return "";
		}
	}
	
	public static String DisplayNumberNullZero(BigDecimal bigDecZero) {
		try{
			if (bigDecZero == null || String.valueOf(bigDecZero) == null || bigDecZero.compareTo(BigDecimal.ZERO)==0) 
				return "0";			
			return  String.valueOf(bigDecZero);
		}catch(Exception e){
			log.error("Error >>> "+e.getMessage());
			return "";
		}
	}
	
	/**End #SepteMWi*/
	
	public static String displayBigDecimalZeroToEmpty(BigDecimal bigDecZero) {
		try{
			if (bigDecZero == null || String.valueOf(bigDecZero) == null || bigDecZero.compareTo(BigDecimal.ZERO)==0)
				return "";			
			return String.valueOf(displayCommaNumber(bigDecZero));	
		}catch(Exception e){
			log.error("Error >>> ", e);
			return "";
		}
	}
	public static String displayBigDecimal(BigDecimal bigDecZero) {
		try{
			if (bigDecZero == null || String.valueOf(bigDecZero) == null ||bigDecZero.compareTo(BigDecimal.ZERO)==0) 
				return "";			
			return  String.valueOf(bigDecZero);
		}catch(Exception e){
			log.error("Error >>> "+e.getMessage());
			return "";
		}
	}
	public static String DisplayNumber(BigDecimal number ,String format ,boolean nullEmpty){
		if(number == null && format == null)
				return (nullEmpty)?"":"0";
		if(number == null && nullEmpty)
			return "";				
		try{
			if(number == null) number = new BigDecimal("0");
			DecimalFormat decFormat = new DecimalFormat(format);
			return decFormat.format(number);
		}catch (Exception e) {
			log.fatal("displayNumber Error >>> ",e);
		}
		return "";
	}
	
	public static String displayNumberInteger(BigDecimal number) throws Exception {
		
		if(!OrigUtil.isEmptyBigDecimal(number)){
			return String.valueOf(number.setScale(2).intValue());
		}
		
		return "0";
	}
	
	public static String displayCommaNumber(BigDecimal number) throws Exception {
	    if(null != number){
			String numberCommaFormat = "0.00";
			if (String.valueOf(number).trim().length() > 0) {
				String decFirst = "";
				String decLast = "";
				NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
				String commaFormat =
					format.format(number.setScale(2, BigDecimal.ROUND_HALF_UP));
				int indx = commaFormat.indexOf(".");
				if (indx != -1) {
					decFirst = commaFormat.substring(0, indx);
					decLast = commaFormat.substring(indx + 1);
					if (decLast.length() == 1) {
						decLast = decLast + "0";
					}
					numberCommaFormat = decFirst + "." + decLast;
				} else {
					numberCommaFormat = commaFormat + ".00";
				}
			} //if
			return numberCommaFormat;
		} else {
	        return "0.00";
	    }
	}

	public static BigDecimal StringToBigDecimal(String number){
		if(OrigUtil.isEmptyString(number)){
			return new BigDecimal(0);
		}				
		StringBuilder buf = new StringBuilder(number);		
		for (int i = 0; i < buf.length(); i++) {
			char c = buf.charAt(i);			
			Character cW = new Character(c);
			Character compare = new Character(',');
			if (cW.equals(compare)) {
				buf.replace(i, i + 1, "");				
			}
		}		
		BigDecimal d = new BigDecimal(buf+"");		
		return d;
	}
	public static BigDecimal StringToBigDecimalEmptyNull(String number){
		if(OrigUtil.isEmptyString(number)) return null;
		StringBuilder buf = new StringBuilder(number);		
		for (int i = 0; i < buf.length(); i++) {
			char c = buf.charAt(i);			
			Character cW = new Character(c);
			Character compare = new Character(',');
			if (cW.equals(compare)) {
				buf.replace(i, i + 1, "");				
			}
		}		
		BigDecimal d = new BigDecimal(buf+"");		
		return d;
	}
	public static String displayIntegerToString(Integer number)throws Exception{
		if(number == null){
			return "";
		}		
		return String.valueOf(number);	
	}
	public static String displayIntToString(int number){
		return String.valueOf(number);	
	}
	
	/**
	 * @author Pipe
	 * */
	public static String dateToStringTH(Date date){
		return  DateEnToStringDateTh(date ,DateFormatType.FORMAT_DDMMYYY_S);
	}
	public static String displayZeroEmpty(int number)throws Exception{
		if( number == 0) return "";
		return String.valueOf(number);	
	}
	
	/**
	 * Formate parameter dd/mm/yyyy
	 * */
	public static String StringDateTHToStringDateEN (String date) throws Exception{
		if (OrigUtil.isEmptyString(date)) {
			return "";
		}
		String year = String.valueOf(Integer.parseInt(date.substring(6, 10)) - 543);
		StringBuilder str = new StringBuilder(date);
		str.replace(6,10,year);
		return str.toString();
	}
	
	/**
	 * Formate parameter dd/mm/yyyy
	 * */
	public static String StringDateENToStringDateTH(String date) throws Exception{
		if (OrigUtil.isEmptyString(date)) {
			return "";
		}
//		log.debug("date = "+date+" date.length() = "+date.length());
		String year = String.valueOf(Integer.parseInt(date.substring(6, 10)) + 543);
		StringBuilder str = new StringBuilder(date);
		str.replace(6,10,year);
		return str.toString();
	}
	/**
	 * @param parameter yyyy/dd/mm ...
	 * @return dd/mm/yyyy
	 * */
	public static String StringDateTHToStringDateENFormatYYYY_MM_DD (String date) throws Exception{
		if (OrigUtil.isEmptyString(date)) {
			return "";
		}
		String year = String.valueOf(Integer.parseInt(date.substring(0, 4)) - 543);
		String month = String.valueOf(date.substring(5, 7));
		String day = String.valueOf(date.substring(8, 10));
		StringBuilder str = new StringBuilder("");
		str.append(day).append("/").append(month).append("/").append(year);
		return str.toString();
	}
	
	/**
	 * @param parameter yyyy/dd/mm ...
	 * @return dd/mm/yyyy
	 * */
	public static String StringDateENToStringDateTHFormatYYYY_MM_DD(String date) throws Exception{
		if (OrigUtil.isEmptyString(date)) {
			return "";
		}
		String year = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 543);
		String month = String.valueOf(date.substring(5, 7));
		String day = String.valueOf(date.substring(8, 10));
		StringBuilder str = new StringBuilder("");
		str.append(day).append("/").append(month).append("/").append(year);
		return str.toString();
	}
	public static String dateToStringYYYYMMDD(Date date){
		if(date==null){return "";}
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String strDate = df.format(date);
		return strDate;
	}
	public static String StringNullToSpecific(String input, String specific) {
		if (input != null) {
			return input;
		}
		return specific;
	}	
	public static String DateThToStringDateEn(Date date , int dateType){
//		log.debug("[dateEnToStringDateTh].. Date "+date);
		if(date == null){
			return "";
		}
		DateFormat dateFormat = null;
		Calendar calender = null;
		switch (dateType){
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S :
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYYY_S);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
				calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)-543);
				return dateFormat.format(calender.getTime());
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMM:
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMM);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
				calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR)-543);
				calender.set(Calendar.HOUR,calender.get(Calendar.HOUR));
				calender.set(Calendar.MINUTE,calender.get(Calendar.MINUTE));
				return dateFormat.format(calender.getTime());			
			default:
				break;
		}
		return "";
	}
	
	public static String dateEnToStringDateEn(Date date , int dateType){
		DateFormat dateFormat = null;
		if(date == null) return "";
		Calendar calender = null;
		switch (dateType){
			case DataFormatUtility.DateFormatType.FORMAT_DAY_OF_WEEK:
				calender = Calendar.getInstance();
				calender.setTime(date);
				String[] strDays = new String[]{"\u0E2D\u0E32\u0E17\u0E34\u0E15\u0E22\u0E4C"
												,"\u0E08\u0E31\u0E19\u0E17\u0E23\u0E4C"
												,"\u0E2D\u0E31\u0E07\u0E04\u0E32\u0E23"
												,"\u0E1E\u0E38\u0E18"
												,"\u0E1E\u0E24\u0E2B\u0E31\u0E2A\u0E1A\u0E14\u0E35"
												,"\u0E28\u0E38\u0E01\u0E23\u0E4C"
												,"\u0E40\u0E2A\u0E32\u0E23\u0E4C"
												};
				return strDays[calender.get(Calendar.DAY_OF_WEEK) - 1];
			case DataFormatUtility.DateFormatType.FORMAT_CONSTELLATION:
				calender = Calendar.getInstance();
				calender.setTime(date);
				String[] strConstellation =  new String[]{"\u0E23\u0E30\u0E01\u0E32"
															,"\u0E08\u0E2D"
															,"\u0E01\u0E38\u0E19"
															,"\u0E0A\u0E27\u0E14"
															,"\u0E09\u0E25\u0E39"
															,"\u0E02\u0E32\u0E25"
															,"\u0E40\u0E16\u0E32\u0E30"
															,"\u0E21\u0E30\u0E42\u0E23\u0E07"
															,"\u0E21\u0E30\u0E40\u0E2A\u0E47\u0E07"
															,"\u0E21\u0E30\u0E40\u0E21\u0E35\u0E22"
															,"\u0E21\u0E30\u0E41\u0E21"
															,"\u0E27\u0E2D\u0E01"
															};
				if((calender.get(Calendar.YEAR)%12)==0){
					return strConstellation[11];
				}
				return strConstellation[(calender.get(Calendar.YEAR)%12)-1];
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S :
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYYY_S);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
				calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR));
				return dateFormat.format(calender.getTime());
			default:
				break;
		}
		return "";
	}
	
	/**
	 * Method displayZeroDigitsNumber.
	 * @param number
	 * @return String
	 */
	public static String displayZeroDigitsNumber(double number) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(0);
		return nf.format(number);
	}
	
	public static String displayDigitsNumber(int digit, double number){
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(digit);
		nf.setMaximumFractionDigits(digit);
		return nf.format(number);
	}
	
	public static java.sql.Date parseDate(java.util.Date uDate) {
		if(uDate==null){return null;}
		return new java.sql.Date(uDate.getTime());
	}
	
	public static java.sql.Timestamp parseTimestamp(java.util.Date uDate){
		if(uDate==null){return null;}
		return new java.sql.Timestamp(uDate.getTime());
	}
	
	public java.sql.Date getSysTimeStamp() {
        Calendar cal = Calendar.getInstance();
        return new java.sql.Date(cal.getTime().getTime());
    }
	
	public static String ConvertMessageCode(String status){
		if(status==null){return "";}
		String result = status.replace(" ", "_");
		return result.toUpperCase();		
	}
	
	public static String displayCommaNumberNullOrZeroToSpecify(BigDecimal number, String specify) throws Exception {
	    if(number != null && number.doubleValue() != 0){
			String numberCommaFormat = "0.00";
			if (String.valueOf(number).trim().length() > 0) {
				String decFirst = "";
				String decLast = "";
				NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
				String commaFormat =
					format.format(number.setScale(2, BigDecimal.ROUND_HALF_UP));
				int indx = commaFormat.indexOf(".");
				if (indx != -1) {
					decFirst = commaFormat.substring(0, indx);
					decLast = commaFormat.substring(indx + 1);
					if (decLast.length() == 1) {
						decLast = decLast + "0";
					}
					numberCommaFormat = decFirst + "." + decLast;
				} else {
					numberCommaFormat = commaFormat + ".00";
				}
			} //if
			return numberCommaFormat;
	    }else{
	        return specify;
	    }
	}
	
	public static String LongToString(long value){
		return String.valueOf(value);	
	}
	
	public static double parseDoubleNullToZero(BigDecimal value){
		if(value != null){
			return value.doubleValue();
		}else{
			return 0;
		}
	}
	public static long parseLongNullToZero(BigDecimal value){
		if(value != null){
			return value.longValue();
		}else{
			return 0;
		}
	}
	
	public static String getThaiTextDescMessage(String textCode){
		Locale locale = new Locale("th","TH");
		ResourceBundle resource = ResourceBundle.getBundle(locale+"/properties/getMessage", locale);
		if (textCode != null && !textCode.equals("")) {
			try {
				return resource.getString(textCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
/*** Add for empty textbox */
	public static String displayCommaNumber(BigDecimal number, boolean permit) throws Exception {
		if(number == null){
			if(permit) return "";
			return "0.00";
		}
		String numberCommaFormat = "0.00";
		if (String.valueOf(number).trim().length() > 0) {
				String decFirst = "";
				String decLast = "";
				NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
				String commaFormat =
					format.format(number.setScale(2, BigDecimal.ROUND_HALF_UP));
				int indx = commaFormat.indexOf(".");
				if (indx != -1) {
					decFirst = commaFormat.substring(0, indx);
					decLast = commaFormat.substring(indx + 1);
					if (decLast.length() == 1) {
						decLast = decLast + "0";
					}
					numberCommaFormat = decFirst + "." + decLast;
				} else {
					numberCommaFormat = commaFormat + ".00";
				}
		}//if
		return numberCommaFormat;
	}
	
	public static String displayCommaNumberEmptyNull(BigDecimal number, boolean permit) throws Exception {
		if(OrigUtil.isEmptyBigDecimal(number) || number.compareTo(new BigDecimal(0)) == 0){
			if(permit) return "";
			return "";
		}
		String numberCommaFormat = "0.00";
		if (String.valueOf(number).trim().length() > 0) {
				String decFirst = "";
				String decLast = "";
				NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
				String commaFormat =
					format.format(number.setScale(2, BigDecimal.ROUND_HALF_UP));
				int indx = commaFormat.indexOf(".");
				if (indx != -1) {
					decFirst = commaFormat.substring(0, indx);
					decLast = commaFormat.substring(indx + 1);
					if (decLast.length() == 1) {
						decLast = decLast + "0";
					}
					numberCommaFormat = decFirst + "." + decLast;
				} else {
					numberCommaFormat = commaFormat + ".00";
				}
		}//if
		return numberCommaFormat;
	}
	
	public static Vector getSaleTypebyGroup(String currentSaleType, String displayMode){
		if(currentSaleType==null){return new Vector();}
		ORIGCacheUtil utility = ORIGCacheUtil.getInstance();
		
		Vector<MainSaleTypeProperties> vSaleType;
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			vSaleType = utility.loadCacheByActive("MainSaleType");
		}else{
			vSaleType = utility.loadCacheByName("MainSaleType");
		}
		Vector<MainSaleTypeProperties> vSaleTypeTemp = new Vector<MainSaleTypeProperties>();
		String currentGroup =null;
		
		for(int i=0;i<vSaleType.size();i++){
			MainSaleTypeProperties data = vSaleType.get(i);
			if(null != data.getCode() 
					&& data.getCode().equalsIgnoreCase(currentSaleType)){
//				log.debug("data.getCode()= "+data.getCode());
//				log.debug("currentSaleType= "+currentSaleType);
				currentGroup = data.getSaleTypeGroup();
				break;
			}
		}
//		log.debug("currentGroup= "+currentGroup);
		for(int i=0;i<vSaleType.size();i++){
			MainSaleTypeProperties data = vSaleType.get(i);
			if(null != data.getSaleTypeGroup() 
					&& data.getSaleTypeGroup().equalsIgnoreCase(currentGroup)){
				vSaleTypeTemp.add(vSaleType.get(i));
			}
		}
		return 	vSaleTypeTemp;
		
	}
	
	public static String dateEnToString(Date date , int dateType){
//		log.debug("[dateEnToStringDateTh].. Date "+date);
		if(date == null){
			return "";
		}			
		
		DateFormat dateFormat = null;
		Calendar calender = null;			
		
		switch (dateType){
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S :
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYYY_S);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
				calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR));
				return dateFormat.format(calender.getTime());
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMM:
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMM);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
				calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR));
				calender.set(Calendar.HOUR,calender.get(Calendar.HOUR));
				calender.set(Calendar.MINUTE,calender.get(Calendar.MINUTE));
				return dateFormat.format(calender.getTime());
			case DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS:
				dateFormat = new SimpleDateFormat(DataFormatUtility.DateFormatConstant.FORMAT_DDMMYYY_S_HHMMSS);
				calender = Calendar.getInstance();
				calender.setTime(date);
				calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
				calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
				calender.set(Calendar.YEAR,calender.get(Calendar.YEAR));
				calender.set(Calendar.HOUR,calender.get(Calendar.HOUR));
				calender.set(Calendar.MINUTE,calender.get(Calendar.MINUTE));
				calender.set(Calendar.SECOND,calender.get(Calendar.SECOND));
				return dateFormat.format(calender.getTime());
			default:
				break;
		}
	return "";
	}
	
	public static Date trim(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	
	public static String paseIntegerNullToSpecify(Integer intObject, String specify){
		if(intObject == null){
			return specify;
		}else{
			return String.valueOf(intObject.intValue());
		}
	}
	
	public static String DateFormat(String date,String format){
		if(null == date){
			return "-";
		}
		try{
			if("ddMMyyyy".equals(format)){
				SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
				Date DATE = new Date(dateFormat.parse(date).getTime());
				return DateEnToStringDateTh(DATE,DateFormatType.FORMAT_DDMMYYY_S);
			}
		}catch(Exception e){
			return "-";
		}
		return "-";
	}
	
}
