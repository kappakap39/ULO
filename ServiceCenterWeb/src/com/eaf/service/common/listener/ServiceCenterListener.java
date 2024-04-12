package com.eaf.service.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.eaf.ulo.cache.controller.CacheController;

@WebListener
public class ServiceCenterListener implements ServletContextListener {
    public ServiceCenterListener() {
    	
    }
    public void contextInitialized(ServletContextEvent sce) {
    	
    }
    public void contextDestroyed(ServletContextEvent sce) {
       CacheController.clear();
    }	
}
