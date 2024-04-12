package com.eaf.orig.ulo.pl.service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.shared.ejb.ORIGContactManager;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.ulo.pl.app.ejb.ORIGLogManager;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGSchedulerManager;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilRemote;
import com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class PLORIGEJBService {
	static Logger logger = Logger.getLogger(PLORIGEJBService.class);
	public static PLORIGApplicationManager getPLORIGApplicationManager() {
		try{
	        Context ctx = new InitialContext();
	        return (PLORIGApplicationManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager");
		}catch(Exception e){
			e.printStackTrace();
		    return null;
		}    
	}
	
	public static  NCBServiceManager getNCBServiceManager(){
		try{
			Context ctx = new InitialContext();
			Object ref = ctx.lookup("com.eaf.ncb.service.ejb.NCBServiceManager");
			return (NCBServiceManager) PortableRemoteObject.narrow(ref,NCBServiceManager.class);
		}catch(Exception e){
			e.printStackTrace();
		    return null;
		}    	 
	}
	
	public static ORIGGeneratorManager getGeneratorManager() throws NamingException{
		try{
	        Context ctx = new InitialContext();
	        return (ORIGGeneratorManager) ctx.lookup("ejblocal:com.eaf.orig.shared.ejb.ORIGGeneratorManager");
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	public static ExecuteServiceManager getExecuteServiceManager() throws NamingException{
//		logger.debug("Look up ExecuteServiceManager >> ");
		Context ctx = new InitialContext();
		Object ref = ctx.lookup("com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager");
		return (ExecuteServiceManager) PortableRemoteObject.narrow(ref,ExecuteServiceManager.class);		
	}
	
	public static ORIGDAOUtilLocal getORIGDAOUtilLocal(){
		try{
	        Context ctx = new InitialContext();
	        return (ORIGDAOUtilLocal) ctx.lookup("ejblocal:com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal");
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	
	public static ORIGLogManager getORIGLogManager(){
		try{
	        Context ctx = new InitialContext();
	        return (ORIGLogManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.pl.app.ejb.ORIGLogManager");
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	
	public static ORIGDAOUtilRemote getORIGDAOUtilRemote(){
		try{	        
//	        logger.debug("Look up ORIGDAOUtilRemote >> ");
			Context ctx = new InitialContext();
			Object ref = ctx.lookup("com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilRemote");
			return (ORIGDAOUtilRemote) PortableRemoteObject.narrow(ref,ORIGDAOUtilRemote.class);
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	
	public static XRulesRemoteDAOUtilManager getXRulesDAOUtilManager() throws NamingException{		
//		logger.debug("Look up XRulesRemoteDAOUtilManager >> ");
		Context ctx = new InitialContext();
		Object ref = ctx.lookup("com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager");
		return (XRulesRemoteDAOUtilManager) PortableRemoteObject.narrow(ref,XRulesRemoteDAOUtilManager.class);		
	}
	
	public static ORIGContactManager getORIGContactManager(){
		try{	        
//	        logger.debug("Look up ORIGContactManager >> ");
			Context ctx = new InitialContext();
			Object ref = ctx.lookup("com.eaf.orig.shared.ejb.ORIGContactManager");
			return (ORIGContactManager) PortableRemoteObject.narrow(ref,ORIGContactManager.class);	
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	
	public static PLORIGSchedulerManager getPLORIGSchedulerManager() {
		try{
	        Context ctx = new InitialContext();
	        Object ref = ctx.lookup("com.eaf.orig.ulo.pl.app.ejb.PLORIGSchedulerManager");
	        return (PLORIGSchedulerManager) PortableRemoteObject.narrow(ref,PLORIGSchedulerManager.class);
		}catch(Exception e){
			e.printStackTrace();
		    return null;
		}    
	}
}
