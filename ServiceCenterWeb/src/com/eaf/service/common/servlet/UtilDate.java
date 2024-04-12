package com.eaf.service.common.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;




public class UtilDate {
	private static transient Logger logger = Logger.getLogger(UtilDate.class);
	public static XMLGregorianCalendar convertDate(String data){
		try {
			
//			SimpleDateFormat convertDate = new SimpleDateFormat("yyyyMMDD");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				Date date = dateFormat.parse(data);
				logger.debug("###DATA###"+data);
				GregorianCalendar cal = new GregorianCalendar();
//				cal.setGregorianChange(date);
				cal.setTime(date);
				logger.debug("###DATE###"+date+"####YEAR####"+date.getYear());
				XMLGregorianCalendar xmlDate3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(
								cal.get(Calendar.YEAR),date.getMonth()+1,date.getDate(),
								date.getHours(), date.getMinutes(),
								date.getSeconds(),
								DatatypeConstants.FIELD_UNDEFINED,
								DatatypeConstants.FIELD_UNDEFINED);
				logger.debug("###DATE XML###"+xmlDate3);
			return xmlDate3;

		}catch(Exception e){
			((DatatypeConfigurationException) e).printStackTrace();
		}
		return null;	
	}
}
