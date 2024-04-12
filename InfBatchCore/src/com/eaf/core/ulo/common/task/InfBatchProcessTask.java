package com.eaf.core.ulo.common.task;

import java.io.File;
import java.sql.DriverManager;

import oracle.jdbc.driver.OracleDriver;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.lookup.model.LookupCacheDataM;

public class InfBatchProcessTask {
	static transient Logger logger;
	public static void main(String[] args) {
		init();
		try{
			if(null != args && args.length > 0){
				ProcessTaskDataM processTask = getInfBatchTask(args);
				String processName = processTask.getProcessName();
				String processId = processTask.getProcessId();
				logger.debug("processName >> "+processName);
				logger.debug("processId >> "+processId);
				ProcessTaskManager processTaskManager = new ProcessTaskManager();
					processTaskManager.run(processTask);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	public static ProcessTaskDataM getInfBatchTask(String[] args){
		String processName = "";
		if(args.length >= 1){
			processName = args[0];
		}
		ProcessTaskDataM processTask = new ProcessTaskDataM(processName);
		return processTask;
	}
	private static void init(){		
		String logConfigPath = InfBatchManager.getInfBatchLibPath("",InfBatchConstant.InfBatchLib.Resource)+File.separator+"log4j.properties";
		System.out.println("logConfigPath >> "+logConfigPath);
		logger = Logger.getLogger(InfBatchProcessTask.class);
		PropertyConfigurator.configure(logConfigPath);
		try{
			DriverManager.registerDriver(new OracleDriver());
		}catch(Exception e){
			e.printStackTrace();
		}
		LookupCacheDataM lookupCache = new LookupCacheDataM();
			lookupCache.setLookupName("BATCH");
			lookupCache.setRuntime(CacheConstant.Runtime.JAVA);
			lookupCache.create(CacheConstant.CacheType.GENERAL_PARAM,CacheServiceLocator.ORIG_DB,true);
			lookupCache.create(CacheConstant.CacheType.INFBATCH_CONFIG,CacheServiceLocator.ORIG_DB,true);
			lookupCache.create(CacheConstant.CacheType.ENCRYPTION_CONFIG,CacheServiceLocator.ORIG_DB,true);
			lookupCache.create(CacheConstant.CacheType.APPLICATION_DATE,CacheServiceLocator.ORIG_DB,true);
			lookupCache.create(CacheConstant.CacheType.REPORT_PARAM,CacheServiceLocator.ORIG_DB,true);
		CacheController.startup(lookupCache);
	}
//	private static String getInfBatchLibPath(String libFolder){
//		return (System.getProperty("user.dir")).replace("InfBatchCore","")
//				+InfBatchConstant.InfBatchLib.InfBatchLib+File.separator
//					+libFolder+File.separator;
//	}
}
