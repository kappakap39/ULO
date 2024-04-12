package com.eaf.orig.ulo.pl.app.utility;

import java.lang.reflect.Field; 

import org.apache.log4j.Logger;

import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
/**
 * @author septemwi
 * This Class Used For Engine Class
 * */
public class ClassEngine {	

	Logger logger = Logger.getLogger(this.getClass());	
	
	public static final String STRING = "String";
	public static final String DATE	= "Date";
	public static final String TIMESTAMP = "Timestamp";
	public static final String BIGDECIMAL = "BigDecimal";
	public static final String BIGDECIMALNULL = "BigDecimalNULL";
	public static final String INT = "int";
	
	public Object ObjectClassEngine(Object object ,String fieldName){
		Field field = getField(object, fieldName);
		Object obj = getValue(field, object);		
		return obj;
	}
	public Field getField(Object object , String fieldName){
		Class clazz = object.getClass();
		Field fieldM = null;
		try{			
			fieldM = clazz.getDeclaredField(fieldName);
		}catch(NoSuchFieldException ex0) {
//			logger.debug("[NoSuchFieldException]");
			fieldM = getField(clazz, fieldName);	
		}catch(IllegalArgumentException ex0) {
			logger.fatal("[IllegalArgumentException] Error "+ex0.getMessage());
		}
		return fieldM;
	}
	public Field getField(Class clazz, String fieldName){
		Class superClazz = clazz.getSuperclass();
		Field fieldM = null;
		try {
			fieldM = superClazz.getDeclaredField(fieldName);
		}catch(NoSuchFieldException ex1){
			logger.fatal("[NoSuchFieldException] Error "+ex1.getMessage());
		}catch(IllegalArgumentException ex1) {
			logger.fatal("[IllegalArgumentException] Error "+ex1.getMessage());
		}
		return fieldM;
	}
	public Object getValue(Field field , Object object){		
		field.setAccessible(true);
		Object obj = new Object();
		try {
			obj = field.get(object);
//			logger.debug("[getValue] "+field.getName()+" " +obj);
		} catch (IllegalArgumentException e) {
			logger.fatal("[IllegalArgumentException] Error "+e.getMessage());
		} catch (IllegalAccessException e) {
			logger.fatal("[IllegalAccessException] Error "+e.getMessage());
		}
		return obj;
	}
	public String getType(Field field){
		String typeObj = field.getType().getSimpleName();
		return typeObj;
	}
	public Object ConvertValue(String typeObj,String value){
//		logger.debug("[ConvertValue] "+typeObj+" "+value);
		if(STRING.equals(typeObj)){
			return (OrigUtil.isEmptyString(value))?null:value;
		}else if(DATE.equals(typeObj)){
			try {
				return DataFormatUtility.StringThToDateEn(value, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
			} catch (Exception e) {
				return null;
			}
		}else if(TIMESTAMP.equals(typeObj)){
			try {
				return DataFormatUtility.StringThToTimestampEn(value, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
			}catch(Exception e){
				return null;
			}
		}else if(BIGDECIMAL.equals(typeObj)){
			return DataFormatUtility.StringToBigDecimalEmptyNull(value);
		}else if(INT.equals(typeObj)){
			if(OrigUtil.isEmptyString(value))return null;
			return Integer.parseInt(value);
		}		
		return null;
	}
	public Object ConvertBirthDate(String value){
		try{
			return DataFormatUtility.StringEnToDateEn(value, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
		}catch(Exception e){
			return null;
		}		
	}
	public void setValue(Field field , Object object , Object value){
		field.setAccessible(true);
		try{
//			logger.debug("[setValue] Value "+value);
			field.set(object, value);
		}catch (IllegalArgumentException e) {
			logger.fatal("[IllegalArgumentException] Error "+e.getMessage());
		}catch (IllegalAccessException e) {
			logger.fatal("[IllegalAccessException] Error "+e.getMessage());
		}
	}
}
