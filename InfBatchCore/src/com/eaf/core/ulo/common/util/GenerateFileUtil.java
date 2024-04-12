package com.eaf.core.ulo.common.util;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.GenerateFileControllConnectionInf;
import com.eaf.core.ulo.common.inf.GenerateFileInf;

public class GenerateFileUtil {
	private static transient Logger logger = Logger.getLogger(GenerateFileUtil.class);
	
	public static GenerateFileInf getGenerateFileInf(String generateId)throws Exception{
		logger.debug("GenerateFileUtil start");
		GenerateFileInf generateFileInf = null;
		String className = InfBatchProperty.getInfBatchConfig(generateId);
		logger.debug("className >> "+className);
		try{
			generateFileInf = (GenerateFileInf)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			logger.error("ERROR "+e.getLocalizedMessage());
			throw new InfBatchException(e.getLocalizedMessage());
		}
		logger.debug("GenerateFileUtil end");
		return generateFileInf;
	}
	
	public static GenerateFileControllConnectionInf getGenerateFileControllInf(String generateId)throws Exception{
		logger.debug("GenerateFileUtil start");
		GenerateFileControllConnectionInf generateFileInf = null;
		String className = InfBatchProperty.getInfBatchConfig(generateId);
		logger.debug("className >> "+className);
		try{
			generateFileInf = (GenerateFileControllConnectionInf)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		logger.debug("GenerateFileUtil end");
		return generateFileInf;
	}
}
