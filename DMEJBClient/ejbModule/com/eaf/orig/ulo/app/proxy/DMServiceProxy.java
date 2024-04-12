package com.eaf.orig.ulo.app.proxy;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.app.ejb.view.DMManager;

public class DMServiceProxy {
	private static transient Logger logger = Logger.getLogger(DMServiceProxy.class);
	
	public static DMManager getDMManager(){
		try{
			Context ctx = new InitialContext();
	        return (DMManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.app.ejb.DMManagerBean");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		    return null;
		}    
	}
}
