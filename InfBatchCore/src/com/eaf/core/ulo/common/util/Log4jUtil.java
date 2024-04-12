package com.eaf.core.ulo.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.flp.util.FLPPasswordUtil;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.util.CacheUtil;

public class Log4jUtil {
	static String BATCH_ID_LOG = "";
	static String EXECUTE_LOG = "";
	static String batchConfigPath = "";
	static String infBatchConfigPath = "";
	static String logConfigPath = "";
	
	public static String getInfBatchLibPath(String configLibPath,int dbType,String libFolder){
		String LibName = (dbType == CacheServiceLocator.IM_DB)?"IMBatchCore":"InfBatchCore";
		if(dbType ==CacheServiceLocator.OL_DB){
			LibName = "MFBatchCore";
		}
		String LibFolderName = (dbType == CacheServiceLocator.IM_DB)?InfBatchConstant.InfBatchLib.IMBatchLib:InfBatchConstant.InfBatchLib.InfBatchLib;
		if(dbType ==CacheServiceLocator.OL_DB){
			LibFolderName = InfBatchConstant.InfBatchLib.MFBatchLib;
		}
		return ((!InfBatchUtil.empty(configLibPath))?configLibPath:(System.getProperty("user.dir")).replace(LibName,"")
				+File.separator
				+LibFolderName)				
				+File.separator
				+libFolder;
	}
	
	private static void init(String batchId,String configLibPath, int dbType) throws Exception{
		infBatchConfigPath = getInfBatchLibPath(configLibPath,dbType,InfBatchConstant.InfBatchLib.Resource)+File.separator+"InfBatchConfig.properties";		
		logConfigPath = getInfBatchLibPath(configLibPath,dbType,InfBatchConstant.InfBatchLib.Resource)+File.separator+"log4j.properties";
		
		System.out.println("infBatchConfigPath : " + infBatchConfigPath);
		System.out.println("logConfigPath : " + logConfigPath);
		
		HashMap<String, String> infBatchConfigs = getPropertyConfigs(infBatchConfigPath);	
		String batchLog4jFileName = infBatchConfigs.get(batchId+"_"+"LOG4J_FILE_NAME");	
		System.out.println("batchLog4jFileName : "+batchLog4jFileName);
		if(!InfBatchUtil.empty(batchLog4jFileName)){
			logConfigPath = getInfBatchLibPath(configLibPath,dbType,InfBatchConstant.InfBatchLib.Resource)+File.separator+batchLog4jFileName;
		}
		
		String batchIdLogPath = infBatchConfigs.get(batchId+"_"+InfBatchConstant.PATH.LOG_PATH);		
			String prefixType = StringUtils.substringBetween(batchIdLogPath, "{", "}");
			String PREFIX_PATH = infBatchConfigs.get(prefixType);
			batchIdLogPath = batchIdLogPath.replace("{"+prefixType+"}", PREFIX_PATH);
		
		Connection conn = getConnection(infBatchConfigs, dbType);
		Date applicationDate = getApplicationDate(conn);
		
		String batchIdLogFileName = infBatchConfigs.get("LOG_BATCH_ID_FILE_NAME");
			String YYYYMMDD = Formatter.display(applicationDate, Formatter.EN, Formatter.Format.YYYYMMDD);
			batchIdLogFileName = batchIdLogFileName.replace("YYYYMMDD", YYYYMMDD);
		mkdir(batchIdLogPath);
		BATCH_ID_LOG = batchIdLogPath+File.separator+batchIdLogFileName;
		
		String batchExecuteLogPath = infBatchConfigs.get("PREFIX_BATCH_LOG_PATH");
		String batchExecuteLogFileName = infBatchConfigs.get("LOG_BATCH_EXECUTE_FILE_NAME");
			batchExecuteLogFileName = batchIdLogFileName.replace("YYYYMMDD", YYYYMMDD);
		mkdir(batchExecuteLogPath);
		EXECUTE_LOG = batchExecuteLogPath+File.separator+batchExecuteLogFileName;
	}
	
	public static void initLog(String batchId,String configLibPath, int dbType) throws Exception{
		try{
			init(batchId,configLibPath,dbType);
			createBatchLogFile();	//batch process
			createLogFile();	//summary proces
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//batch process
	private static void createBatchLogFile() throws Exception{
		String LOG_PATTERN = InfBatchProperty.getLog4jConfig("LOG_PATTERN", logConfigPath);
		ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout(LOG_PATTERN));
			consoleAppender.setThreshold(Level.ALL);
			Filter filterConsole = new Filter() {
				@Override
				public int decide(LoggingEvent arg0) {
					return getDecide(arg0, InfBatchProperty.getListLog4jConfig("LOG_DEBUG_LEVEL", logConfigPath));
				}
			};
			consoleAppender.addFilter(filterConsole);
			consoleAppender.activateOptions();
		Logger.getRootLogger().addAppender(consoleAppender);
		
		FileAppender fileAppender = new FileAppender();
			fileAppender.setFile(BATCH_ID_LOG);
			fileAppender.setLayout(new PatternLayout(LOG_PATTERN));
			fileAppender.setThreshold(Level.DEBUG);
			fileAppender.setAppend(true);
			Filter filter = new Filter() {
				@Override
				public int decide(LoggingEvent arg0) {
					return getDecide(arg0, InfBatchProperty.getListLog4jConfig("LOG_DEBUG_LEVEL", logConfigPath));
				}
			};
			fileAppender.addFilter(filter);
			fileAppender.activateOptions();
		Logger.getRootLogger().addAppender(fileAppender);
	}
	
	//summary process
	private static void createLogFile() throws Exception{
		String LOG_PATTERN = InfBatchProperty.getLog4jConfig("LOG_PATTERN", logConfigPath);
		FileAppender fileAppender = new FileAppender();
			fileAppender.setFile(EXECUTE_LOG);
			fileAppender.setLayout(new PatternLayout(LOG_PATTERN));
			fileAppender.setThreshold(Level.DEBUG);
			Filter filter = new Filter() {
				@Override
				public int decide(LoggingEvent arg0) {
					return getDecide(arg0, InfBatchProperty.getListLog4jConfig("LOG_SUMMARY_LEVEL", logConfigPath));
				}
			};
			fileAppender.addFilter(filter);
			fileAppender.setAppend(true);
			fileAppender.activateOptions();
		Logger.getRootLogger().addAppender(fileAppender);
	}
	
	private static int getDecide(LoggingEvent arg0, List<String> logLevels){
		if(InfBatchUtil.empty(logLevels)){
			logLevels.add(Level.DEBUG.toString());
		}
		if(Level.TRACE == arg0.getLevel() && logLevels.contains(arg0.getLevel().toString())){
			return Filter.ACCEPT;
		}else if(Level.INFO == arg0.getLevel() && logLevels.contains(arg0.getLevel().toString())){
			return Filter.ACCEPT;
		}else if(Level.DEBUG == arg0.getLevel() && logLevels.contains(arg0.getLevel().toString())){
			return Filter.ACCEPT;
		}else if(Level.WARN == arg0.getLevel()  && logLevels.contains(arg0.getLevel().toString())){
			return Filter.ACCEPT;
		}else if(Level.ERROR == arg0.getLevel() && logLevels.contains(arg0.getLevel().toString())){
			return Filter.ACCEPT;
		}else if(Level.FATAL == arg0.getLevel() && logLevels.contains(arg0.getLevel().toString())){
			return Filter.ACCEPT;
		}else{
			return Filter.DENY;
		}
	}
	
	public static String getInitLogPath(String batchId, String infBatchConfig){
		String batchLogPath = "";
		batchLogPath = CacheUtil.getPropertyConfig(infBatchConfig, batchId+"_"+InfBatchConstant.PATH.LOG_PATH);
		String prefixType = StringUtils.substringBetween(batchLogPath, "{", "}");
		String PREFIX_PATH = InfBatchProperty.getLog4jConfig(prefixType, logConfigPath);
		batchLogPath = batchLogPath.replace("{"+prefixType+"}", PREFIX_PATH);
		return batchLogPath;
	}
	
	public static void mkdir(String folderName){
		File file = new File(folderName);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public static HashMap<String, String> getPropertyConfigs(String path){
		FileInputStream fis = null;
		Properties prop = new Properties();
		HashMap<String, String> configs = new HashMap<>();
		try{
			File f = new File(path);			
			fis = new FileInputStream(f);
			prop.load(fis);
			Set<Object> keys = prop.keySet();
			for(Object object : keys){
				String key = (String)object;
				configs.put(key, prop.getProperty(key));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			} 
		}
		return configs;
	}
	
	public static Connection getConnection(HashMap<String, String> infBatchConfigs,int dbType) throws Exception{
		Connection conn = null;
		if(CacheServiceLocator.ORIG_DB == dbType){
			String ORIG_DB_HOST = infBatchConfigs.get("ORIG_DB_HOST");
			String ORIG_DB_USER = infBatchConfigs.get("ORIG_DB_USER");
			String ORIG_DB_PASSWORD = infBatchConfigs.get("ORIG_DB_PASSWORD");
			conn = DriverManager.getConnection(ORIG_DB_HOST, ORIG_DB_USER,FLPPasswordUtil.decrypt(ORIG_DB_PASSWORD));
		}else if(CacheServiceLocator.IM_DB == dbType){
			String ORIG_DB_HOST = infBatchConfigs.get("IM_DB_HOST");
			String ORIG_DB_USER = infBatchConfigs.get("IM_DB_USER");
			String ORIG_DB_PASSWORD = infBatchConfigs.get("IM_DB_PASSWORD");
			conn = DriverManager.getConnection(ORIG_DB_HOST, ORIG_DB_USER,FLPPasswordUtil.decrypt(ORIG_DB_PASSWORD));
		}else if(CacheServiceLocator.OL_DB == dbType){
			String OL_DB_HOST = infBatchConfigs.get("OL_DB_HOST");
			String OL_DB_USER = infBatchConfigs.get("OL_DB_USER");
			String OL_DB_PASSWORD = infBatchConfigs.get("OL_DB_PASSWORD");
			conn = DriverManager.getConnection(OL_DB_HOST, OL_DB_USER,FLPPasswordUtil.decrypt(OL_DB_PASSWORD));
		}
		return conn;
	}
	
	private static Date getApplicationDate(Connection conn) throws Exception{
		Date applicationDate = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			if(conn != null){
				StringBuilder SQL = new StringBuilder();
				SQL.append("SELECT * FROM APPLICATION_DATE");
				ps = conn.prepareStatement(SQL.toString());
				rs = ps.executeQuery();
				if(rs.next()){
					applicationDate = rs.getDate("APP_DATE");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		
		return applicationDate;
	}
}
