package com.eaf.service.common.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.service.common.master.ObjectDAO;
import com.eaf.service.common.model.ServiceTypeDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.inf.CachePropertiesInf;
import com.eaf.ulo.cache.util.CacheUtil;

public class ServiceCache extends ObjectDAO{
	private static transient Logger logger = Logger.getLogger(ServiceCache.class);	
	public ServiceCache(){
		super();
	}
	public static ServiceTypeDataM getServiceType(String serviceId){
		return CacheController.getCacheData(CacheConstant.CacheName.SERVICE_TYPE,serviceId);
	}
	public static String getName(String cacheId,String code,String id){
		HashMap<String,Object> cacheResult = CacheController.getCacheData(cacheId,code);
		return CacheUtil.getValue(cacheResult,id);
	}	
	public static String getName(String cacheId,String code,String id,HttpServletRequest request){
		return getName(cacheId,code,id);
	}
	public static String getName(String cacheId,String code){
		try{
			CachePropertiesInf<HashMap<String,Object>> cacheProperties = CacheController.getCacheProperties(cacheId);
			if(null != cacheProperties){
				return cacheProperties.getValue(code);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return "";
	}
	public static String getName(String cacheId,String code,HttpServletRequest request){
		return getName(cacheId, code);
	}
	public static String getName(String cacheId,String search,String value,String id){
		try{
			CachePropertiesInf<HashMap<String,Object>> cacheProperties = CacheController.getCacheProperties(cacheId);
			if(null != cacheProperties){
				ArrayList<HashMap<String,Object>> cacheTypeResults = cacheProperties.getCacheTypeData(CacheConstant.PropertiesType.ALL);
				if(null != cacheTypeResults){
					for(HashMap<String,Object> cacheResult : cacheTypeResults){
						String codeValue = CacheUtil.getValue(cacheResult,search);
						if(null != codeValue && codeValue.equals(value)){
							return CacheUtil.getValue(cacheResult,id);
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return "";
	}
	public static String displayName(String cacheId,String code){
		return getName(cacheId,code,"DISPLAY_NAME");
	}
	public static String displayName(String cacheId,String code,HttpServletRequest request){
		return getName(cacheId,code,"DISPLAY_NAME");
	}
	public static HashMap<String,Object> get(String cacheId,String code){
		HashMap<String,Object> cacheResult = CacheController.getCacheData(cacheId,code);
		if(null == cacheResult){
			cacheResult = new HashMap<String, Object>();
		}
		return cacheResult;
	}
	public static ArrayList<HashMap<String,Object>> search(String cacheId,String search,String code){
		ArrayList<HashMap<String,Object>> searchResults = new ArrayList<HashMap<String,Object>>();
		CachePropertiesInf<HashMap<String,Object>> cacheProperties = CacheController.getCacheProperties(cacheId);
		if(null != cacheProperties){
			ArrayList<HashMap<String,Object>> cacheTypeResults = cacheProperties.getCacheTypeData(CacheConstant.PropertiesType.ALL);
			if(null != cacheTypeResults){
				for(HashMap<String,Object> cacheResult : cacheTypeResults){
					String codeValue = CacheUtil.getValue(cacheResult,search);
					if(null != codeValue && codeValue.equals(code)){
						searchResults.add(cacheResult);
					}
				}
			}	
		}
		return searchResults;
	}
	@SuppressWarnings("rawtypes")
	public static ArrayList<HashMap> getCacheList(String cacheId){
		try{
			CachePropertiesInf<HashMap> cacheProperties = CacheController.getCacheProperties(cacheId);
			if(null != cacheProperties){
				return cacheProperties.getCacheTypeData(CacheConstant.PropertiesType.ALL);		
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return new ArrayList<HashMap>();
	}
	@SuppressWarnings("rawtypes")
	public static String[] getArrayCache(String cacheId){
		ArrayList<String> codeList = new ArrayList<String>();
		ArrayList<HashMap> cacheResults = getCacheList(cacheId);
		if(!ServiceUtil.empty(cacheResults)){
			for (HashMap<String,Object> cacheResult : cacheResults) {
				String CODE = CacheUtil.getValue(cacheResult,"CODE");
				if(!ServiceUtil.empty(CODE)){
					codeList.add(CODE);
				}
			}
		}
		return codeList.toArray(new String[codeList.size()]);
	}
	public static String getConstant(String CONSTANT_ID){
		String CONSTANT_VALUE = CacheController.getCacheData(CacheConstant.CacheName.SYSTEM_CONSTANT,CONSTANT_ID);
		if(ServiceUtil.empty(CONSTANT_VALUE)){
			CONSTANT_VALUE = "";
		}
		return CONSTANT_VALUE;
	}
	public static String[] getArrayConstant(String CONSTANT_ID){
		String CONSTANT_VALUE = CacheController.getCacheData(CacheConstant.CacheName.SYSTEM_CONSTANT,CONSTANT_ID);
		if(ServiceUtil.empty(CONSTANT_VALUE)){
			CONSTANT_VALUE = "";
		}
		return CONSTANT_VALUE.split("\\,");
	}
	public static String getProperty(String CONFIG_ID){
		return CacheController.getCacheData(CacheConstant.CacheName.SYSTEM_CONFIG,CONFIG_ID);
	}
	public static String getGeneralParam(String CONFIG_ID){
		HashMap<String,Object> cacheResult = CacheController.getCacheData(CacheConstant.CacheName.GENERAL_PARAM,CONFIG_ID);
		return CacheUtil.getValue(cacheResult,"PARAM_VALUE");
	}
	public static boolean lookup(String CONSTANT_ID,String VALUE){
		ArrayList<String> configs = new ArrayList<String>(Arrays.asList(getArrayConstant(CONSTANT_ID)));
		return (null != configs && configs.contains(VALUE));
	}
}
