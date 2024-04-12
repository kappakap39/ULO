package com.eaf.orig.ulo.app.proxy;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.app.ejb.view.ApplicationManager;
import com.eaf.orig.ulo.app.ejb.view.ControlLogManager;
import com.eaf.orig.ulo.app.ejb.view.GeneratorManager;

public class ORIGServiceProxy {
	private static transient Logger logger = Logger.getLogger(ORIGServiceProxy.class);
	public static ControlLogManager getControlLogManager(){
		try{
			Context ctx = new InitialContext();
	        return (ControlLogManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.app.ejb.view.ControlLogManager");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		    return null;
		}    
	}
	public static ApplicationManager getApplicationManager(){
		try{
			Context ctx = new InitialContext();
	        return (ApplicationManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.app.ejb.ApplicationManagerBean");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		    return null;
		}  
	}
	public static GeneratorManager getGeneratorManager(){
		try{
			Context ctx = new InitialContext();
	        return (GeneratorManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.app.ejb.GeneratorManagerBean");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		    return null;
		}  
	}
}
