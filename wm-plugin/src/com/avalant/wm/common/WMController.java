package com.avalant.wm.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.lookup.model.LookupCacheDataM;

/**
 * Servlet implementation class WMController
 */
@WebServlet(value="/WMController", loadOnStartup=-1,asyncSupported=false)
public class WMController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger;
	public static String webContentPath = "";

	@Override
	public void init() throws ServletException {
		webContentPath = getServletContext().getRealPath("");
    	String logConfigPath = getServletContext().getRealPath("/WEB-INF/log4j.properties");
    	logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure(logConfigPath);
    	logger.debug("init()..");
    	boolean loadCacheOnStartup = CacheController.loadCacheOnStartup(CacheServiceLocator.ORIG_DB);
		logger.debug("loadCacheOnStartup >> "+loadCacheOnStartup);
    	LookupCacheDataM lookupCache = new LookupCacheDataM();
			lookupCache.setLookupName(CacheConstant.LookupName.ORIG);
			lookupCache.setRuntime(CacheConstant.Runtime.SERVER);
			lookupCache.create(CacheConstant.CacheType.METAB_CACHE,CacheServiceLocator.ORIG_DB,"ORIG_METABCACHE",loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONSTANT,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.GENERAL_PARAM,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.INFBATCH_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.ENCRYPTION_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.APPLICATION_DATE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.REPORT_PARAM,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SERVICE_TYPE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SIMULATE_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
		CacheController.startup(lookupCache);
	}

}
