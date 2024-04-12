package com.eaf.orig.ulo.app.util;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.factory.ModuleFactory;

public class GenerateUnique {
	private static transient Logger logger = Logger.getLogger(GenerateUnique.class);
	public static String generate(String name){
		try{
			return ModuleFactory.getUniqueIDGeneratorDAO().getUniqueBySequence(name);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			return null;
		}
	}	
	public static String generate(String name,Connection conn){
		try{
			return ModuleFactory.getUniqueIDGeneratorDAO().getUniqueBySequence(name,conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			return null;
		}
	}
	public static String generate(String name,int dbType){
		try{
			return ModuleFactory.getUniqueIDGeneratorDAO().getUniqueBySequence(name,dbType);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			return null;
		}
	}
	public static String generateApplicationNo(String uniqueId,int seq,int lifeCycle){
		try{
			return String.format("%s/%02d/%02d",uniqueId,seq,lifeCycle);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			return null;
		}
	}
	public static String generateApplicationNo(String applicationGroupNo,int lifeCycle){
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		logger.debug("lifeCycle >> "+lifeCycle);
		try{
			if(!Util.empty(applicationGroupNo)){
				String[] objectUniqueId = applicationGroupNo.split("\\/");
				String lifeCycleNo = String.format("%02d",lifeCycle);
				logger.debug("lifeCycleNo >> "+lifeCycleNo);
				return String.format("%s/%s/%s",objectUniqueId[0],objectUniqueId[1],lifeCycleNo);
			}
		}catch(Exception e){
			logger.debug("ERROR",e);
		}
		return null;
	}
}
