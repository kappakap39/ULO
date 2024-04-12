package com.eaf.service.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

public class DataFormat {
	private static transient Logger logger = Logger.getLogger(DataFormat.class);
	public static String getString(Object data){
		if(data instanceof String)
			return (String)data;
		else
			return null;
	}
	
	public static Integer getInteger(Object data){
		if(data instanceof Integer){
			return (Integer)data;
		}else{
			if(!data.toString().equals("")){
				return Integer.parseInt(data.toString());
			}else{
				return 0;
			}
		}
	}
	
	public static Calendar getCalendar(String data,String format) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
	   	Date date = dateFormat.parse(data);
	   	calendar.setTime(date);
		return calendar;
	}
	
	public static String[] getStringArray(Object data){
		if(data instanceof String[])
			return (String[])data;
		else
			return null;
	}
	
	public static Integer[] getIntegerArray(Object data){
		if(data instanceof String[]){
			String[] value = (String[])data;
			int size = value.length;
			Integer[] idata = new Integer[size];
			for(int i=0;i<size;i++){
				if(value[i].equals("")){
					idata[i] = 0;
				}else{
					idata[i] = Integer.parseInt(value[i]);
				}
			}
			return (Integer[])idata;
		}
		else
			return null;
	}
	
	public static XMLGregorianCalendar getGregorianCalendar(String data,String format) throws Exception{
		if(null==data || "".equals(data)) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = new Date();
		d = sdf.parse(data);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		XMLGregorianCalendar resp = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		return resp;
	}
	
	public static XMLGregorianCalendar[] getArrayGregorianCalendar(Object object,String format) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = new Date();
		XMLGregorianCalendar[] resp = null;
		String[] data = (String[])object;
		for(int i=0;i<data.length;i++){
			d = sdf.parse(data[i]);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			resp[i] = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		}
		return resp;
	}
	
	public static java.sql.Date stringToDate(String date){
		try{
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			return sqlDate;
		}catch(Exception e){
			logger.debug("Format Error return null");
			return null;
		}
	}

}
