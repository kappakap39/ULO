package com.eaf.orig.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.logon.LogonEngine;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.CancleClaimUtil;
import com.eaf.ulo.cache.controller.CacheController;

@WebListener("ORIGListener")
public class ORIGListener implements HttpSessionListener,ServletContextListener{
	private static transient Logger logger = Logger.getLogger(ORIGListener.class);
	public void sessionCreated(HttpSessionEvent arg0) {
		String sessionId = arg0.getSession().getId();
		logger.info("sessionCreated.."+sessionId);
	}
	public void sessionDestroyed(HttpSessionEvent arg0) {
		String sessionId = arg0.getSession().getId();
		logger.info("sessionDestroyed.."+sessionId);
		String userName = (String) arg0.getSession().getAttribute("userName");		
		logger.info("UserName >> "+userName);		
		try{
			if(!Util.empty(userName)){
				LogonEngine.processLogout(arg0,MConstant.PROCESS.SUCCESS);
			}
			CancleClaimUtil.cancleClaim(arg0.getSession());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		clearSession(arg0);	
	}	
	private void clearSession(HttpSessionEvent request){		
		request.getSession().invalidate();	
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		CacheController.clear();
	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}
}