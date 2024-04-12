package com.eaf.service.common.iib.mapper.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.inf.CachePropertiesInf;
import com.eaf.ulo.cache.util.CacheUtil;

public class DecisionServiceCacheControl {
	private static transient Logger logger = Logger.getLogger(DecisionServiceCacheControl.class);
	
	public DecisionServiceCacheControl(){
		super();
	}
	
	@SuppressWarnings({"rawtypes"})
	public static ArrayList<HashMap> getCacheList(String cacheId){
		ArrayList<HashMap> cacheTypeResults = new ArrayList<HashMap>();
		try{
			CachePropertiesInf<HashMap> cacheProperties = CacheController.getCacheProperties(cacheId);
			if(null != cacheProperties){
				return cacheProperties.getCacheTypeData(CacheConstant.PropertiesType.ALL);		
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return cacheTypeResults;
	}
	public static Object getCache(String cacheId){
		return getCacheList(cacheId);
	}
	
	@SuppressWarnings("rawtypes")
	public static String[] getArrayCache(String cacheId){
		ArrayList<String> codeList = new ArrayList<String>();
		ArrayList<HashMap> cacheResults = getCacheList(cacheId);
		if(!CacheUtil.empty(cacheResults)){
			for (HashMap<String,Object> cacheResult : cacheResults) {
				String CODE = CacheUtil.getValue(cacheResult,"CODE");
				if(!CacheUtil.empty(CODE)){
					codeList.add(CODE);
				}
			}
		}
		return codeList.toArray(new String[codeList.size()]);
	}
	public static String getConstant(String CONSTANT_ID){
		String CONSTANT_VALUE = CacheController.getCacheData(CacheConstant.CacheName.SYSTEM_CONSTANT,CONSTANT_ID);
		if(CacheUtil.empty(CONSTANT_VALUE)){
			logger.warn("getConstant() value of the constant "+CONSTANT_ID+" is not valid in properties file!");
			CONSTANT_VALUE = "";
		}		
		return CONSTANT_VALUE;
	}
	public static String[] getArrayConstant(String CONSTANT_ID){
		String CONSTANT_VALUE = getConstant(CONSTANT_ID);
		if(null == CONSTANT_VALUE){
			CONSTANT_VALUE = "";
		}
		return CONSTANT_VALUE.split("\\,");
	}
	public static String getProperty(String CONFIG_ID){
		return CacheController.getCacheData(CacheConstant.CacheName.SYSTEM_CONFIG,CONFIG_ID);
	}
	public static String getGeneralParam(String CONFIG_ID){
		HashMap<String,Object> cacheResult = CacheController.getCacheData(CacheConstant.CacheName.GENERAL_PARAM,CONFIG_ID);
		String resultValue = CacheUtil.getValue(cacheResult,"PARAM_VALUE");
		if(CacheUtil.empty(resultValue)){
			logger.warn("Invalid Param code : "+CONFIG_ID+" in general param!");
		}
		return resultValue;
	}
	public static String getGeneralParam(String CONFIG_ID, String tagetColumn){
		if(tagetColumn == null)return null;
		HashMap<String,Object> cacheResult = CacheController.getCacheData(CacheConstant.CacheName.GENERAL_PARAM,CONFIG_ID);
		String resultValue = CacheUtil.getValue(cacheResult,tagetColumn);
		if(CacheUtil.empty(resultValue)){
			logger.warn("Invalid Param code : "+CONFIG_ID+" , tagetColumn : "+tagetColumn+" in general param!");
		}
		return resultValue;
	}
	public static String getName(String cacheId,String code,String id){
		HashMap<String,Object> cacheResult = CacheController.getCacheData(cacheId,code);
		return CacheUtil.getValue(cacheResult,id);
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
	/**Search value from LIST_BOX_MASTER or Master Table by criteria
	 * 
	 * @param cacheId [FIELD_ID or CacheId]
	 * @param search [Column name of LIST_BOX_MASTER or Master Table that input value associated with]
	 * @param value [input value that stored in "search" parameter]
	 * @param id [Column name of LIST_BOX_MASTER or Master Table where output value is expected]
	 * @return
	 */
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
		logger.warn("getName() unable to get value from cache : "+cacheId+" , INPUT Column : "+search+" , Search Value : "+value+" , Target Column : "+id);
		return "";
	}	
	public static String displayName(String cacheId,String code){
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


	public static String getNameListBox(String fieldId,String compareField,String compareValue,String searchFieldId){
		String propertiesType = "ALL";
		CachePropertiesInf<HashMap<String,Object>> cacheProperties = CacheController.getCacheProperties(fieldId);
		if(null != cacheProperties){
			ArrayList<HashMap<String,Object>> cacheTypeResults = cacheProperties.getCacheTypeData(propertiesType);
			if(null != cacheTypeResults){
				for(HashMap<String,Object> cacheResult : cacheTypeResults){
					String codeValue = CacheUtil.getValue(cacheResult,compareField);
					if(null != codeValue && codeValue.equals(compareValue)){
						return CacheUtil.getValue(cacheResult,searchFieldId);
					}
				}
			}	
		}
		logger.warn("getNameListBox() unable to Search value : "+compareValue+" from cache '"+fieldId+"', Input Column : "+compareField+" , Expect Output Column : "+searchFieldId);
		return "";
	}
}
