package com.eaf.service.common.api;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.inf.CachePropertiesInf;

public class ServiceApplicationDate {
	public static Date getDate(){
		String cacheName = CacheConstant.CacheName.APPLICATION_DATE;
		CachePropertiesInf<Date> currentDate = CacheController.getCacheProperties(cacheName);
		return currentDate.getCacheData(cacheName);
	}
	public static Timestamp getTimestamp(){
		return new Timestamp(getCalendar().getTime().getTime());
	}
	public static Calendar getCalendar(){
		Calendar currentCalendar =  Calendar.getInstance(Locale.ENGLISH);
		currentCalendar.setTime(new java.util.Date(getDate().getTime()));
		Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
		calendar.set(currentCalendar.get(Calendar.YEAR),currentCalendar.get(Calendar.MONTH),currentCalendar.get(Calendar.DAY_OF_MONTH));
		return calendar;
	}
	
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
		}
		return null;
		
	}
}
