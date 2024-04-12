package com.eaf.core.ulo.common.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;

public class SystemConfig {
	static final transient Logger logger = LogManager.getLogger(SystemConfig.class);
	public static String getProperty(String CONFIG_ID){
		return CacheController.getCacheData(CacheConstant.CacheName.SYSTEM_CONFIG,CONFIG_ID);
	}
}
