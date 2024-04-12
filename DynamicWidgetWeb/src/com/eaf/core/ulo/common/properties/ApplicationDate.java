package com.eaf.core.ulo.common.properties;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.inf.CachePropertiesInf;

public class ApplicationDate {
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
}
