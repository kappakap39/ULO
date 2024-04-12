package com.avalant.wm.lookup;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.avalant.wm.ejb.WmManager;

public class LookUpWMManager {

	private static Logger logger = Logger.getLogger(LookUpWMManager.class);
	
	public static WmManager getWmManager(){
		try{
			Context ctx = new InitialContext();
	        return (WmManager) ctx.lookup("ejblocal:com.avalant.wm.ejb.WmManagerBean");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		    return null;
		}  
	}
}
