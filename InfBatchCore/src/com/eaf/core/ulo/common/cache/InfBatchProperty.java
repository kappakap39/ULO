package com.eaf.core.ulo.common.cache;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.util.CacheUtil;

public class InfBatchProperty extends InfBatchObjectDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchProperty.class);
	public InfBatchProperty(){
		super();
	}
	public static String getInfBatchConfig(String configId){
		return CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,configId);
	}
	public static String getSQLParameter(String configId){
		return "'"+getInfBatchConfig(configId).replaceAll("\\,", "','")+"'";
	}
	public static String getEncryptionConfig(String configId){
		return CacheController.getCacheData(CacheConstant.CacheName.ENCRYPTION_CONFIG,configId);
	}
	public static String getInfBatchLibPath(String configLibPath,String libFolder){
		return InfBatchManager.getInfBatchLibPath(configLibPath,libFolder);
	}
	public static String getInfBatchLibPath(){		
		return CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"RESOURCE_INF_BATCH_LIB_PATH");
	}
	public static Date getDate(){
		return  CacheController.getCacheData(CacheConstant.CacheName.APPLICATION_DATE,CacheConstant.CacheName.APPLICATION_DATE);
	}
	public static Date getDateGeneralParam(){
		return FormatUtil.StringToDate(SystemConfig.getGeneralParam("CTE_DATE"), FormatUtil.Format.yyyy_MM_dd);
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
	public static String getReportParam(String type, String code, String columnReturn) {
		String reportParamCode = type+"_"+code;
		logger.debug("reportParamCode >> "+reportParamCode);
		HashMap<String,Object> cacheResult = CacheController.getCacheData(CacheConstant.CacheName.REPORT_PARAM,reportParamCode);		
		return CacheUtil.getValue(cacheResult,columnReturn);
	}
	public static String getGeneralParam(String code){
		HashMap<String,Object> cacheResult = CacheController.getCacheData(CacheConstant.CacheName.GENERAL_PARAM,code);
		return CacheUtil.getValue(cacheResult,"PARAM_VALUE");
	}
	public static boolean containsGeneralParam(String paramCode,String paramValue){
		try{
			ArrayList<String> paramValues = new ArrayList<>(Arrays.asList((getGeneralParam(paramCode).split("\\,"))));
			logger.debug("paramValues >> "+paramValues);
			return paramValues.contains(paramValue);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return false;
	}	
	public static boolean loopGeneralParam(String paramCode,String paramValue){
		try{
			ArrayList<String> paramValues = new ArrayList<>(Arrays.asList((getGeneralParam(paramCode).split("\\,"))));
//			logger.debug("paramValues >> "+paramValues);
			return paramValues.contains(paramValue);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return false;
	}
	public static ArrayList<String> getListInfBatchConfig(String configId){
		String configValue = getInfBatchConfig(configId);
		ArrayList<String> result = new ArrayList<String>();		
		if(configValue != null && !("").equals(configValue)){
			String[] datas = configValue.split(",");
			result = new ArrayList<String>();
			for(int i=0; i<datas.length; i++){
				result.add(datas[i]);
			}
		}		
		return result;
	}
	public static String[] getArrayInfBatchConfig(String configId){
		String configValue = getInfBatchConfig(configId);
		String[] result = null;
		if(configValue != null && !("").equals(configValue)){
			result = configValue.split(",");
		}
		return result;
	}
	public static boolean lookup(String CONSTANT_ID,String VALUE){
		ArrayList<String> configs = new ArrayList<String>(Arrays.asList(getArrayInfBatchConfig(CONSTANT_ID)));
		return (null != configs && configs.contains(VALUE));
	}
	public static boolean lookup(String CONSTANT_ID,List<String> values){
		ArrayList<String> configs = new ArrayList<String>(Arrays.asList(getArrayInfBatchConfig(CONSTANT_ID)));
		if(null!=values){
			for (String value : values) {
				if(null != configs && configs.contains(value)){
					return true;
				}
			}
		}
		return false;
	}
	public static List<String> getListLog4jConfig(String configId, String logConfigPath){
		List<String> result = new ArrayList<>();
		String configValue = CacheUtil.getPropertyConfig(logConfigPath, configId);
		if(!InfBatchUtil.empty(configValue)){
			String[] values = configValue.split(",");
			for(String value : values){
				result.add(value);
			}
		}
		return result;
	}
	public static String getLog4jConfig(String configId, String logConfigPath){
		String result = "";
		String configValue = CacheUtil.getPropertyConfig(logConfigPath, configId);
		if(!InfBatchUtil.empty(configValue)){
			result = configValue;
		}
		return result;
	}
	public static String getBatchType(String batchId){
		String batchType = getInfBatchConfig(batchId+"_"+InfBatchConstant.ReportParam.BATCH_TYPE);
		return batchType;
	}
}
