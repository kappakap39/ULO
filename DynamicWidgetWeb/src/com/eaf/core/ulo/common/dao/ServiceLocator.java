package com.eaf.core.ulo.common.dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceLocator {
	private static final transient Logger logger = LogManager.getLogger(ServiceLocator.class);
	public static ServiceLocator serviceLocator;	
	public final static int ORIG_DB = 1;
	public final static int WIDGET = 10;
	private final static String JAVA_ENV = "";
	public final static String ORIG_DATA_SOURCE = "jdbc/orig";
	public final static String WIDGET_DATA_SOURCE = "jdbc/widget";
	public static ServiceLocator getInstance() {
		if(serviceLocator == null){
			serviceLocator = new ServiceLocator();
		}
		return serviceLocator;
	}	
	public Connection getConnection(int dbType) throws Exception{
		try{
			InitialContext ctx = new InitialContext();
			Object obj = null;
			switch(dbType){
				case ServiceLocator.ORIG_DB: obj = ctx.lookup(JAVA_ENV+ORIG_DATA_SOURCE); break;
				case ServiceLocator.WIDGET: obj = ctx.lookup(JAVA_ENV+WIDGET_DATA_SOURCE); break;
				default: obj = ctx.lookup(JAVA_ENV+ORIG_DATA_SOURCE); break;	
			}
			DataSource dataSrc = (DataSource) javax.rmi.PortableRemoteObject.narrow(obj, DataSource.class);
			return dataSrc.getConnection();
		}
		catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.toString());
		}
	}
}
