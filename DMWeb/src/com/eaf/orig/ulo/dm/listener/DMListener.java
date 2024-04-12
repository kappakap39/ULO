package com.eaf.orig.ulo.dm.listener;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.eaf.ulo.cache.controller.CacheController;

@WebListener("DMListener")
public class DMListener implements HttpSessionListener,ServletContextListener{
	public DMListener(){
		super();
	}
	private static transient Logger logger = Logger.getLogger(DMListener.class);
	public void sessionCreated(HttpSessionEvent arg0) {
		String sessionId = arg0.getSession().getId();
		logger.info("sessionCreated.."+sessionId);
	}
	public void sessionDestroyed(HttpSessionEvent arg0) {	
		String sessionId = arg0.getSession().getId();
		logger.info("sessionDestroyed.."+sessionId);
		clearSession(arg0);	
	}	
	private void clearSession(HttpSessionEvent request){		
		Enumeration<String> attributeAll = request.getSession().getAttributeNames();
		while(attributeAll.hasMoreElements()){			
			String attributeName = (String) attributeAll.nextElement();
			request.getSession().removeAttribute(attributeName);
		}		
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		CacheController.clear();
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}
}
