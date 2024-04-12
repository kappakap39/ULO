package com.eaf.core.ulo.common.api;

import java.io.File;
import java.security.Security;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.driver.OracleDriver;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.inf.InfBatch;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Log4jUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.dao.OracleConnectionPoolController;
import com.eaf.ulo.cache.lookup.model.LookupCacheDataM;
import com.eaf.ulo.cache.util.CacheUtil;

import cs.jdbc.driver.CompositeDriver;

public class InfBatchManager {
	public InfBatchManager(){
		super();
	}
	static transient Logger logger;
	public static void main(String[] args) {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		if(null != args && args.length > 0){
			InfBatchRequestDataM infBatchRequest = getInfBatchRequest(args);
			if(!InfBatchUtil.empty(infBatchRequest.getBatchId())){
				infBatchResponse = execute(infBatchRequest);
			}else{
				infBatchResponse.setBatchId(null);
				infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			}
		}else{
			infBatchResponse.setBatchId(null);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}
		String resultCode = infBatchResponse.getResultCode();
		if(Util.empty(resultCode)){
			resultCode = "0";
		}
		System.exit(Integer.parseInt(resultCode));
	}
	public static InfBatchResponseDataM execute(InfBatchRequestDataM infBatchRequest){
		String runtime = infBatchRequest.getRuntime();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		String batchId = infBatchRequest.getBatchId();
		if(InfBatchConstant.DISABLE.equals(batchId)){
			infBatchResponse.setBatchId(null);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			return infBatchResponse;
		}
		String configLibPath = infBatchRequest.getConfigLibPath();
		System.out.println("runtime : "+runtime);
		System.out.println("batchId : "+batchId);
		System.out.println("configLibPath : "+configLibPath);
		infBatchResponse.setBatchId(batchId);
		Timestamp startTime = null;
		try{
			init(runtime,batchId,configLibPath);
			logger.info("BatchId : "+batchId);
			logger.info("configLibPath : "+configLibPath);
			startTime = InfBatchProperty.getTimestamp();
			logger.info("Batch Start Time : "+startTime);
			if(!InfBatchUtil.empty(batchId)){
				String className = InfBatchProperty.getInfBatchConfig(batchId);
				logger.debug("className : "+className);
				InfBatch infBatch = null;
				try{			        		    	
					infBatch = (InfBatch)Class.forName(className).newInstance();			        		    	
	 			}catch(Exception e){
	 				logger.fatal("ERROR ",e);
	 			}
				if(null != infBatch){
					infBatch.preProcessAction(infBatchRequest);
					try{
						infBatchResponse = infBatch.processAction(infBatchRequest);
					}catch(Exception e){
						logger.fatal("ERROR ",e);
						if(null == infBatchResponse){
							infBatchResponse = new InfBatchResponseDataM();
						}
						infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
						infBatchResponse.setResultDesc(e.getLocalizedMessage());
					}
					infBatch.postProcessAction(infBatchRequest,infBatchResponse);
				}else{
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
				}
			}else{
				infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.fatal("ERROR ", e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}finally{
			InfBatchResultController.logResultData(infBatchResponse);
			if(CacheConstant.Runtime.JAVA.equals(runtime)){
				OracleConnectionPoolController.closeConnection();
			}
		}
		Timestamp endTime = InfBatchProperty.getTimestamp();
		logger.info("Batch End Time : "+endTime);
		return infBatchResponse;
	}
	
	public static InfBatchRequestDataM getInfBatchRequest(String[] args){
		InfBatchRequestDataM infBatchRequest = new InfBatchRequestDataM();
		if(args.length >= 1){
			try{infBatchRequest.setBatchId(args[0]);}catch(Exception e){}
			try{infBatchRequest.setConfigLibPath(args[1]);}catch(Exception e){}
			try{infBatchRequest.setSystemDate(args[2]);}catch(Exception e){}
		}
		infBatchRequest.setRuntime(CacheConstant.Runtime.JAVA);
		System.out.println(infBatchRequest);
		return infBatchRequest;
	}
	
	public static void init(String runtime) throws Exception{
		init(runtime,"","");
	}
	
	public static void init(String runtime, String batchId,String configLibPath) throws Exception{
		if(CacheConstant.Runtime.JAVA.equals(runtime)){
			Security.setProperty("ssl.SocketFactory.provider","");
			Security.setProperty("ssl.ServerSocketFactory.provider","");
			try{
				DriverManager.registerDriver(new OracleDriver());
				DriverManager.registerDriver(new CompositeDriver());
			}catch(Exception e){
				e.printStackTrace();
			}
			Log4jUtil.initLog(batchId,configLibPath,CacheServiceLocator.ORIG_DB);
			logger = Logger.getLogger(InfBatchManager.class);
			LookupCacheDataM lookupCache = new LookupCacheDataM();
				lookupCache.setLookupName(CacheConstant.LookupName.InfBatchCore);
				lookupCache.setRuntime(CacheConstant.Runtime.JAVA);
				lookupCache.create(CacheConstant.CacheType.GENERAL_PARAM,CacheServiceLocator.ORIG_DB,true);
				lookupCache.create(CacheConstant.CacheType.INFBATCH_CONFIG,CacheServiceLocator.ORIG_DB,true,configLibPath);
				lookupCache.create(CacheConstant.CacheType.ENCRYPTION_CONFIG,CacheServiceLocator.ORIG_DB,true,configLibPath);
				lookupCache.create(CacheConstant.CacheType.APPLICATION_DATE,CacheServiceLocator.ORIG_DB,true);
				lookupCache.create(CacheConstant.CacheType.REPORT_PARAM,CacheServiceLocator.ORIG_DB,true);
				lookupCache.create(CacheConstant.CacheType.SERVICE_TYPE,CacheServiceLocator.ORIG_DB,true);
				lookupCache.create(CacheConstant.CacheType.SYSTEM_CONSTANT,CacheServiceLocator.ORIG_DB,true);
				lookupCache.create(CacheConstant.CacheType.SYSTEM_CONFIG,CacheServiceLocator.ORIG_DB,true);
				List<String> cacheNames = new ArrayList<String>();
					cacheNames.add("BusinessClassInfo");
					cacheNames.add("CardInfo");
					cacheNames.add("CardType");
					cacheNames.add("ChannelMapping");
					cacheNames.add("DocumentList");
					cacheNames.add("DocumentListMapping");
					cacheNames.add("DocumentListReason");
					cacheNames.add("ListBox");
					cacheNames.add("Organization");
					cacheNames.add("ProductInfo");
					cacheNames.add("Template");
					cacheNames.add("User");
					cacheNames.add("WorkflowParam");
					cacheNames.add("CardInfoFull");
				lookupCache.create(CacheConstant.CacheType.METAB_CACHE,CacheServiceLocator.ORIG_DB,"ORIG_METABCACHE",true,cacheNames);
				String infBatchConfig = getInfBatchLibPath(configLibPath,InfBatchConstant.InfBatchLib.Resource)+File.separator+"InfBatchConfig.properties";
				logger.debug("infBatchConfig >> "+infBatchConfig);
				lookupCache.createDatabase(CacheServiceLocator.ORIG_DB,
						CacheUtil.getPropertyConfig(infBatchConfig,"ORIG_DB_HOST"),
						CacheUtil.getPropertyConfig(infBatchConfig,"ORIG_DB_USER"),
						CacheUtil.getPropertyConfig(infBatchConfig,"ORIG_DB_PASSWORD"));
				lookupCache.createDatabase(CacheServiceLocator.WAREHOUSE_DB,
						CacheUtil.getPropertyConfig(infBatchConfig,"DM_DB_HOST"),
						CacheUtil.getPropertyConfig(infBatchConfig,"DM_DB_USER"),
						CacheUtil.getPropertyConfig(infBatchConfig,"DM_DB_PASSWORD"));
				lookupCache.createDatabase(CacheServiceLocator.DIH,
						CacheUtil.getPropertyConfig(infBatchConfig,"DIH_DB_HOST"),
						CacheUtil.getPropertyConfig(infBatchConfig,"DIH_DB_USER"),
						CacheUtil.getPropertyConfig(infBatchConfig,"DIH_DB_PASSWORD"));
				lookupCache.createDatabase(CacheServiceLocator.OL_DB,
						CacheUtil.getPropertyConfig(infBatchConfig,"OL_DB_HOST"),
						CacheUtil.getPropertyConfig(infBatchConfig,"OL_DB_USER"),
						CacheUtil.getPropertyConfig(infBatchConfig,"OL_DB_PASSWORD"));
			CacheController.startup(lookupCache);
			OracleConnectionPoolController.initOracleDataSource();
		}else if(CacheConstant.Runtime.SERVER.equals(runtime)){
			if(null == logger){
				logger = Logger.getLogger(InfBatchManager.class);
			}
			CacheController.refreshCache(CacheConstant.CacheName.GENERAL_PARAM);
			CacheController.refreshCache(CacheConstant.CacheName.INFBATCH_CONFIG);
			CacheController.refreshCache(CacheConstant.CacheName.ENCRYPTION_CONFIG);
			CacheController.refreshCache(CacheConstant.CacheName.APPLICATION_DATE);
			CacheController.refreshCache(CacheConstant.CacheName.REPORT_PARAM);
		}
	}
	public static void init(){
		String logConfigPath = getInfBatchLibPath("",InfBatchConstant.InfBatchLib.Resource)+File.separator+"log4j.properties";
		System.out.println("logConfigPath >> "+logConfigPath);
		logger = Logger.getLogger(InfBatchManager.class);
		PropertyConfigurator.configure(logConfigPath);
	}
	public static String getInfBatchLibPath(String configLibPath,String libFolder){
		return ((!InfBatchUtil.empty(configLibPath))
				?configLibPath
				:(System.getProperty("user.dir")).replace("InfBatchCore","")
				+File.separator
				+InfBatchConstant.InfBatchLib.InfBatchLib)				
				+File.separator
				+libFolder;
	}
}
