package com.eaf.core.ulo.common.control;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eaf.ulo.cache.controller.CacheController;

@WebListener
public class WidgetListener implements HttpSessionListener,ServletContextListener{
	private static final transient Logger logger = LogManager.getLogger(WidgetListener.class);
    public void sessionCreated(HttpSessionEvent se) {
    	String sessionId = se.getSession().getId();
		logger.info("sessionCreated.."+sessionId);
    }
    public void sessionDestroyed(HttpSessionEvent se) {
    	String sessionId = se.getSession().getId();
		logger.info("sessionDestroyed.."+sessionId);
		clearSession(se);
    }	
	private void clearSession(HttpSessionEvent request){		
		Enumeration<String> attributeAll = request.getSession().getAttributeNames();
		while(attributeAll.hasMoreElements()){			
			String attributeName = (String) attributeAll.nextElement();
			request.getSession().removeAttribute(attributeName);
		}		
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		CacheController.clear();
	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}
}
