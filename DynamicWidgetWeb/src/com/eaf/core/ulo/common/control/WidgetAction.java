package com.eaf.core.ulo.common.control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.lookup.model.LookupCacheDataM;

@WebServlet(value="/WidgetAction",loadOnStartup=-1,asyncSupported=false)
public class WidgetAction extends HttpServlet {
	private static final transient Logger logger = LogManager.getLogger(WidgetAction.class);
	private static final long serialVersionUID = 1L;     
    public WidgetAction() {
        super();
    }
    @Override
    public void init() throws ServletException {
    	logger.debug("init..");
    	boolean loadCacheOnStartup = CacheController.loadCacheOnStartup(CacheServiceLocator.ORIG_DB);
		logger.debug("loadCacheOnStartup >> "+loadCacheOnStartup);
    	LookupCacheDataM lookupCache = new LookupCacheDataM();
			lookupCache.setLookupName(CacheConstant.LookupName.DYNAMIC_WIDGET);
			lookupCache.setRuntime(CacheConstant.Runtime.SERVER);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.APPLICATION_DATE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
		CacheController.startup(lookupCache);
    }
}
