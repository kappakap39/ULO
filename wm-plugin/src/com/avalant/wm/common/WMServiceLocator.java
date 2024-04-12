package com.avalant.wm.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.flp.util.FLPPasswordUtil;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.OracleConnectionPoolController;
import com.eaf.ulo.cache.lookup.model.DatabaseProperties;

public class WMServiceLocator {
	
	private static transient Logger logger = Logger.getLogger(WMServiceLocator.class);
	
	public static WMServiceLocator serviceLocator;	
	
	public final static int ORIG_DB = 1;
	public final static int IMG_DB = 2;
	public final static int SCHEDULER_DB = 3;
	public final static int XRULES_DB = 4;
	public final static int EXT_DB = 5;
	public final static int BPC_DB = 6;
	public final static int WORKFLOW_DB = 7;
	public final static int WAREHOUSE_DB = 8;
	public final static int DIH = 9;
	public final static int OL_DB = 12;
	
	private final static String JAVA_ENV = "";
	public final static String ORIG_DATA_SOURCE = "jdbc/orig";
	public final static String IMG_DATA_SOURCE="jdbc/im";
	public final static String SCHEDULER_DATA_SOURCE = "jdbc/scheduler";
	public final static String XRULES_DATA_SOURCE = "jdbc/xrules";
	public final static String EXT_DATA_SOURCE = "jdbc/ext";
	public final static String BPC_DATA_SOURCE = "jdbc/bpc";
	public final static String WORKFLOW_DATA_SOURCE = "jdbc/origwf";
	public final static String WAREHOUSE_DATA_SOURCE = "jdbc/dm";
	public final static String DIH_DATA_SOURCE = "jdbc/dih";
	public final static String OL_DATA_SOURCE = "jdbc/ol";
	
	public static WMServiceLocator getInstance() {
		if (serviceLocator == null) {
			serviceLocator = new WMServiceLocator();
		}
		return serviceLocator;
	}
	public Connection getConnection(int dbType) throws WMServiceLocatorException{
		try{
			String Runtime = CacheController.getRuntime();
			logger.debug("Runtime >> "+Runtime);
			if(CacheConstant.Runtime.JAVA.equals(Runtime)){
				return getRuntimeConnection(dbType);
			}else if(CacheConstant.Runtime.SERVER.equals(Runtime)){
				return getJdbcConnection(dbType);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
			throw new WMServiceLocatorException(e.toString());
		}
		return null;
	}
	public Connection getJdbcConnection(int dbType) throws WMServiceLocatorException{
		try{
			InitialContext ctx = new InitialContext();
			Object obj = null;
			switch(dbType){
				case ORIG_DB: obj = ctx.lookup(JAVA_ENV+ORIG_DATA_SOURCE); break;
				case IMG_DB: obj = ctx.lookup(JAVA_ENV+IMG_DATA_SOURCE); break;
				case SCHEDULER_DB: obj = ctx.lookup(JAVA_ENV+SCHEDULER_DATA_SOURCE); break;
				case XRULES_DB: obj = ctx.lookup(JAVA_ENV+XRULES_DATA_SOURCE); break;	
				case EXT_DB: obj = ctx.lookup(JAVA_ENV+EXT_DATA_SOURCE); break;	
				case BPC_DB: obj = ctx.lookup(JAVA_ENV+BPC_DATA_SOURCE); break;
				case WORKFLOW_DB: obj = ctx.lookup(JAVA_ENV+WORKFLOW_DATA_SOURCE); break;
				case WAREHOUSE_DB: obj = ctx.lookup(JAVA_ENV+WAREHOUSE_DATA_SOURCE); break;
				case DIH: obj = ctx.lookup(JAVA_ENV+DIH_DATA_SOURCE); break;
				case OL_DB: obj = ctx.lookup(JAVA_ENV+OL_DATA_SOURCE); break;
				default: obj = ctx.lookup(JAVA_ENV+ORIG_DATA_SOURCE); break;	
			}
			DataSource dataSrc = (DataSource) javax.rmi.PortableRemoteObject.narrow(obj, DataSource.class);
			return dataSrc.getConnection();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new WMServiceLocatorException(e.toString());
		}
	}
	public Connection getRuntimeConnection(int dbType) throws SQLException{
		if(DIH == dbType){
			DatabaseProperties dbProp = getDatabaseProperties(dbType);
			logger.debug(dbProp);
			return DriverManager.getConnection(dbProp.getHost(),dbProp.getUser(),FLPPasswordUtil.decrypt(dbProp.getPassword()));
		}else{
			return OracleConnectionPoolController.getConnection(dbType);
		}
	}
	public DatabaseProperties getDatabaseProperties(int dbType){
		DatabaseProperties dbProp = null;
		switch (dbType) {
			case ORIG_DB: dbProp = new DatabaseProperties(
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"ORIG_DB_HOST"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"ORIG_DB_USER"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"ORIG_DB_PASSWORD")); 
				break;
			case WAREHOUSE_DB: dbProp = new DatabaseProperties(
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"DM_DB_HOST"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"DM_DB_USER"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"DM_DB_PASSWORD")); 
				break;
			case DIH: dbProp = new DatabaseProperties(
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"DIH_DB_HOST"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"DIH_DB_USER"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"DIH_DB_PASSWORD")); 
				break;
			case IMG_DB: dbProp = new DatabaseProperties(
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"IM_DB_HOST"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"IM_DB_USER"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"IM_DB_PASSWORD")); 
				break;
			case OL_DB: dbProp = new DatabaseProperties(
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"OL_DB_HOST"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"OL_DB_USER"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"OL_DB_PASSWORD")); 
				break;
			default: dbProp = new DatabaseProperties(
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"ORIG_DB_HOST"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"ORIG_DB_USER"), 
					(String)CacheController.getCacheData(CacheConstant.CacheName.INFBATCH_CONFIG,"ORIG_DB_PASSWORD")); ;
		}
		return dbProp;
	}
}
