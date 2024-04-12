/*
 * Created on Oct 4, 2007
 * Created by weeraya  
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.service;

import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

//import com.eaf.ias.service.ejb.IASServiceManager;
import com.eaf.img.wf.service.ejb.IMGWFServiceManager;
import com.eaf.img.wf.service.ejb.IMGWFServiceManagerHome;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.master.ejb.OrigMasterManager;
import com.eaf.orig.scheduler.task.SchedulerTask;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.wf.service.ejb.ORIGWFServiceManager;
import com.eaf.orig.wf.service.ejb.ORIGWFServiceManagerHome;
import com.eaf.xrules.service.manager.XRulesServiceManager;
import com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;


/**
 * @author weeraya
 *
 * Type: ORIGEJBService
 */
public class ORIGEJBService {
	
	static @EJB ORIGApplicationManager origAppManager;
	static @EJB XRulesServiceManager xRulesServiceManager;
	static @EJB NCBServiceManager ncbServiceManager;

	
	private static Logger logger = Logger.getLogger(ORIGEJBService.class);
	
    public static String APPLICATION_MANAGER_EJB = "orig/ORIGApplicationManagerHome";
    public static String GENERATOR_MANAGER_EJB = "ejb/ORIGGeneratorManager";
    public static String EXT_APPLICATION_MANAGER_EJB = "orig/extint/ApplicationEXTManagerHome";
    public static String ORIG_XRULES_SERVICE_MANAGER_EJB="ejb/eaf/xrules/XRulesServiceManagerHome";
    public static String ORIG_MASTER_SERVICE_MANAGER_EJB="orig/master/OrigMasterManagerHome";
    public static String ORIG_SCHEDULER_SERVICE_MANAGER_EJB="ejb/orig/scheduler/SchedulerTaskHome";
    private static String EJB_ENV = "java:comp/env/";
    private static Properties ejbProperties;
    private static final String WS_INITIAL_CONTEXT = "com.ibm.websphere.naming.WsnInitialContextFactory";
    private static final String ORIG_ADDRESS_DAO = "com.eaf.orig.ulo.pl.app.dao";
    /**
	 * 
	 *  look up JNDI name and get Home of ApplicationManager
	 *  @return ApplicationManager 
	 * 
	 */
	public static ORIGApplicationManager getApplicationManager(){
		try{
//			InitialContext initialContext = new InitialContext();
//			Object objref = initialContext.lookup(EJB_ENV+APPLICATION_MANAGER_EJB);
//			ORIGApplicationManagerHome home = (ORIGApplicationManagerHome)PortableRemoteObject.narrow(objref,ORIGApplicationManagerHome.class);
//			logger.debug(">>> getApplicationManager");
			Context ctx = new InitialContext();
	        origAppManager = (ORIGApplicationManager) ctx.lookup("ejblocal:com.eaf.orig.shared.ejb.ORIGApplicationManager");
//	        origAppManager = (ORIGApplicationManager) ctx.lookup(EJB_ENV+"ejb/ORIGApplicationManager");
	        return origAppManager;
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	/**
	 * 
	 *  look up JNDI name and get Home of GeneratorManager
	 *  @return GeneratorManager 
	 * 
	 */

	public static ORIGGeneratorManager getGeneratorManager(){
		try{
//			InitialContext initialContext = new InitialContext();
//			Object objref = initialContext.lookup(EJB_ENV+GENERATOR_MANAGER_EJB);
//			ORIGGeneratorManagerHome home = (ORIGGeneratorManagerHome)PortableRemoteObject.narrow(objref, ORIGGeneratorManagerHome.class);
//	        return home.create();
	        Context ctx = new InitialContext();
	        return (ORIGGeneratorManager) ctx.lookup("ejblocal:com.eaf.orig.shared.ejb.ORIGGeneratorManager");
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	/** look up JNDI name and get Home of OrigMasterManager by PoNg
	 * 
	 */
	public static OrigMasterManager getOrigMasterManager(){
		try{
//			Logger logger = Logger.getLogger(ORIGEJBService.class);
//			InitialContext initialContext = new InitialContext();
//			Object objref = initialContext.lookup(EJB_ENV+ORIG_MASTER_SERVICE_MANAGER_EJB);
//			logger.debug("objref = "+objref);
//			OrigMasterManagerHome home = (OrigMasterManagerHome)PortableRemoteObject.narrow(objref,OrigMasterManagerHome.class);
//	        return home.create();
	        Context ctx = new InitialContext();
	        return (OrigMasterManager) ctx.lookup("ejblocal:com.eaf.orig.master.ejb.OrigMasterManager");
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	
	/***
	 * @author septemwi
	 * @deprecated 
	 * {@link PLORIGEJBService#getPLORIGApplicationManager()}
	 */
	@Deprecated
	public static PLORIGApplicationManager getPLORIGApplicationManager(){
		try{
	        Context ctx = new InitialContext();
	        return (PLORIGApplicationManager) ctx.lookup("ejblocal:com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager");
		}catch(Exception e){
			logger.fatal("error :",e);
		    return null;
		}    
	}
	
	
	/**
	 * 
	 *  look up JNDI name and get Home of ApplicationEXTManagerHome
	 *  @return ApplicationEXTManager 
	 * 
	 */
	/*public static ApplicationEXTManager getApplicationEXTManager(String providerUrl, String jndi)throws Exception{
		ApplicationEXTManagerHome home = null;
		InitialContext initialContext = null;
		try {
			System.out.println(">>>>> ApplicationEXTManager providerUrl " + providerUrl);
			System.out.println(">>>>> ApplicationEXTManager jndi " + jndi);
			
			Hashtable env = new Hashtable(); 
			 //Set Initial Context
			env.put(Context.INITIAL_CONTEXT_FACTORY,  WS_INITIAL_CONTEXT ); 
			//Set Provider URL
			env.put(Context.PROVIDER_URL, providerUrl  );   
			initialContext= new InitialContext(env); 
			System.out.println(">>>>>" + initialContext.getNameInNamespace());
	    	
			System.out.println( "initial context success" ) ;
			System.out.println("Start lookup");
			//Looh up JNDI Name		    
			Object result = initialContext.lookup(jndi);
	    
	    
			System.out.println( "lookup success" ) ;
			//Narrow object to Home Object 
			Object obj =  javax.rmi.PortableRemoteObject.narrow(result, ApplicationEXTManagerHome.class); 
			home = (ApplicationEXTManagerHome)obj;
       	
			//initialContext.close();
		}catch( NamingException ex ) {
			ex.printStackTrace();
			throw new Exception("Exception in getHome : ",ex);
		}catch( Exception ex ) {
			ex.printStackTrace();
			throw new Exception("Exception in getHome : ",ex);
		}finally{
			try{
				initialContext.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}
	
	
		return home.create();  
	}*/
	
	/**
	 * 
	 *  look up JNDI name and get Home of ApplicationEXTManagerHome
	 *  @return ApplicationEXTManager 
	 * 
	 */
//	public static ApplicationEXTManager getApplicationEXTManager(String providerUrl, String jndi)throws Exception{
//		try{
//			InitialContext initialContext = new InitialContext();
//			Object objref = initialContext.lookup(EJB_ENV+EXT_APPLICATION_MANAGER_EJB);
//			ApplicationEXTManagerHome home = (ApplicationEXTManagerHome)PortableRemoteObject.narrow(objref,ApplicationEXTManagerHome.class);
//	        return home.create(); 
//		}catch(Exception e){
//		    e.printStackTrace();
//		    return null;
//		}
//	}
	
	public static XRulesServiceManager  getXRulesServiceManager(){
		try{
//			InitialContext initialContext = new InitialContext();
//			Object objref = initialContext.lookup(EJB_ENV+ORIG_XRULES_SERVICE_MANAGER_EJB);
//			XRulesServiceManagerHome home = (XRulesServiceManagerHome)PortableRemoteObject.narrow(objref,XRulesServiceManagerHome.class);
//	        return home.create();
			
//	        Context ctx = new InitialContext();
//	        xRulesServiceManager = (XRulesServiceManager) ctx.lookup("com.eaf.xrules.service.manager.XRulesServiceManager");
//	        return xRulesServiceManager;
			
//			logger.debug("Look up XRulesServiceManager >> ");
			Context ctx = new InitialContext();
			Object ref = ctx.lookup("com.eaf.xrules.service.manager.XRulesServiceManager");
			return (XRulesServiceManager) PortableRemoteObject.narrow(ref,XRulesServiceManager.class);
	        
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}

	public static  NCBServiceManager getNCBServiceManager() throws NamingException, CreateException {
		try{		
//			logger.debug("Look up NCBServiceManager >> ");
			Context ctx = new InitialContext();
			Object ref = ctx.lookup("com.eaf.ncb.service.ejb.NCBServiceManager");
			return (NCBServiceManager) PortableRemoteObject.narrow(ref,NCBServiceManager.class);			
		}catch(Exception e){
			e.printStackTrace();
		    return null;
		}    	 
	}
	
	public static SchedulerTask getSchedulerManager(){
		try{
//			InitialContext initialContext = new InitialContext();
//			Object objref = initialContext.lookup(EJB_ENV+ORIG_SCHEDULER_SERVICE_MANAGER_EJB);
//			SchedulerTaskHome home = (SchedulerTaskHome)PortableRemoteObject.narrow(objref,SchedulerTaskHome.class);
//	        return home.create();
	        Context ctx = new InitialContext();
	        return (SchedulerTask) ctx.lookup(EJB_ENV+ORIG_SCHEDULER_SERVICE_MANAGER_EJB);
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	
	/**
	 * @return Returns the ejbProperties.
	 */
	public static Properties getEjbProperties() {
		return ejbProperties;
	}
	/**
	 * @param ejbProperties The ejbProperties to set.
	 */
	public static void setEjbProperties(Properties ejbProperties) {
		ORIGEJBService.ejbProperties = ejbProperties;
	}
	
	public static ORIGWFServiceManager getORIGWFServiceManagerHome(String providerUrl, String jndi)throws Exception {
		ORIGWFServiceManagerHome home = null;
		InitialContext initialContext = null;
		try {
			System.out.println(">>>>> ORIGWFServiceManager providerUrl " + providerUrl);
			System.out.println(">>>>> ORIGWFServiceManager jndi " + jndi);
			
			Hashtable env = new Hashtable(); 
			 //Set Initial Context
			env.put(Context.INITIAL_CONTEXT_FACTORY,  WS_INITIAL_CONTEXT ); 
			//Set Provider URL
			env.put(Context.PROVIDER_URL, providerUrl  );   
			initialContext= new InitialContext(env); 
			System.out.println(">>>>>" + initialContext.getNameInNamespace());
	    	
			System.out.println( "initial context success" ) ;
			System.out.println("Start lookup");
			//Looh up JNDI Name		    
			Object result = initialContext.lookup(jndi);
	    
	    
			System.out.println( "lookup success" ) ;
			//Narrow object to Home Object 
			Object obj =  javax.rmi.PortableRemoteObject.narrow(result, ORIGWFServiceManagerHome.class); 
			home = (ORIGWFServiceManagerHome)obj;
       	
			//initialContext.close();
		}catch( NamingException ex ) {
			ex.printStackTrace();
			throw new Exception("Exception in getHome : ",ex);
		}catch( Exception ex ) {
			ex.printStackTrace();
			throw new Exception("Exception in getHome : ",ex);
		}finally{
			try{
				initialContext.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}
	
	
		return home.create();
	}
	
	public static IMGWFServiceManager getImageWFServiceManagerHome(String providerUrl, String jndi)throws Exception {
		IMGWFServiceManagerHome home = null;
		InitialContext initialContext = null;
		try {
			System.out.println(">>>>> IMGWFServiceManager providerUrl " + providerUrl);
			System.out.println(">>>>> IMGWFServiceManager jndi " + jndi);
			
			Hashtable env = new Hashtable(); 
			 //Set Initial Context
			env.put(Context.INITIAL_CONTEXT_FACTORY,  WS_INITIAL_CONTEXT ); 
			//Set Provider URL
			env.put(Context.PROVIDER_URL, providerUrl  );   
			initialContext= new InitialContext(env); 
			System.out.println(">>>>>" + initialContext.getNameInNamespace());
	    	
			System.out.println( "initial context success" ) ;
			System.out.println("Start lookup");
			//Looh up JNDI Name		    
			Object result = initialContext.lookup(jndi);
	    
	    
			System.out.println( "lookup success" ) ;
			//Narrow object to Home Object 
			Object obj =  javax.rmi.PortableRemoteObject.narrow(result, IMGWFServiceManagerHome.class); 
			home = (IMGWFServiceManagerHome)obj;
       	
			//initialContext.close();
		}catch( NamingException ex ) {
			ex.printStackTrace();
			throw new Exception("Exception in getHome : ",ex);
		}catch( Exception ex ) {
			ex.printStackTrace();
			throw new Exception("Exception in getHome : ",ex);
		}finally{
			try{
				initialContext.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}
		
		return home.create();
	}
	
	/**
	 * 
	 *  look up JNDI name and get Home of ApplicationManager
	 *  @return ApplicationManager 
	 * 
	 */
	public static ORIGApplicationManager getApplicationManagerNonJava(){
		try{
//			InitialContext initialContext = new InitialContext();
//			Object objref = initialContext.lookup(APPLICATION_MANAGER_EJB);
//			ORIGApplicationManagerHome home = (ORIGApplicationManagerHome)PortableRemoteObject.narrow(objref,ORIGApplicationManagerHome.class);
//	        return home.create();
	        Context ctx = new InitialContext();
	        return (ORIGApplicationManager) ctx.lookup(APPLICATION_MANAGER_EJB);
		}catch(Exception e){
		    e.printStackTrace();
		    return null;
		}    
	}
	
	public static XRulesRemoteDAOUtilManager getORIGXRulesDAOUtilManager()throws NamingException{
//		logger.debug("Look up XRulesRemoteDAOUtilManager >> ");
		Context ctx = new InitialContext();
		Object ref = ctx.lookup("com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager");
		return (XRulesRemoteDAOUtilManager) PortableRemoteObject.narrow(ref,XRulesRemoteDAOUtilManager.class);
	}	

	public static ExecuteServiceManager getExecuteServiceManager() throws NamingException{
//		logger.debug("Look up ExecuteServiceManager >> ");
		Context ctx = new InitialContext();
		Object ref = ctx.lookup("com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager");
		return (ExecuteServiceManager) PortableRemoteObject.narrow(ref,ExecuteServiceManager.class);
	}	
	
//	public static IASServiceManager getIASServiceManager(){
//		try{
//			String URL = "iiop://"+ORIGConfig.IAS_EJB_URL+":"+ORIGConfig.IAS_EJB_PORT;
//			logger.info("URL : " + URL);		
//			Hashtable<String,String> env = new Hashtable<String, String>();		
//				env.put(Context.INITIAL_CONTEXT_FACTORY,"com.ibm.websphere.naming.WsnInitialContextFactory");
//				env.put(Context.PROVIDER_URL,URL);
//			Context ctx = new InitialContext(env);
//			Object ref = ctx.lookup("com.eaf.ias.service.ejb.IASServiceManager");
//			return (IASServiceManager) PortableRemoteObject.narrow(ref,IASServiceManager.class);
//		}catch (Exception e) {
//			logger.fatal("Exception ",e);
//		}
//		return null;
//	}
	
}
