package com.eaf.orig.ulo.service.lookup;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.service.ejb.view.ServiceCenterManager;

public class LookupServiceCenter {
	private static transient Logger logger = Logger.getLogger(LookupServiceCenter.class);
	public static ServiceCenterManager getServiceCenterManager(){
		try{
			Context ctx = new InitialContext();
	        return (ServiceCenterManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.service.ejb.ServiceCenterManagerBean");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		    return null;
		}  
	}

}
