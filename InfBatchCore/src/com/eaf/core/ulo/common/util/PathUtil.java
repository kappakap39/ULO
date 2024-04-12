package com.eaf.core.ulo.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;

public class PathUtil {
	private static transient Logger logger = Logger.getLogger(PathUtil.class);
	public static String JASPER_REPORT_PATH = InfBatchConstant.ReportParam.JASPER_REPORT_PATH;

	public static String getPath(String config) throws Exception{
		String REPORT_PATH = InfBatchProperty.getInfBatchConfig(config);
		if(!InfBatchUtil.empty(REPORT_PATH)){
			while(StringUtils.substringsBetween(REPORT_PATH, "{", "}") != null){
				String[] prefixTypes = StringUtils.substringsBetween(REPORT_PATH, "{", "}");
				for(String prefixType : prefixTypes){
					String PREFIX_PATH = InfBatchProperty.getInfBatchConfig(prefixType);
					if(!InfBatchUtil.empty(PREFIX_PATH)){
						REPORT_PATH = REPORT_PATH.replace("{"+prefixType+"}", PREFIX_PATH);
					}
				}
			}
		}
		return REPORT_PATH;
	}
	
	public static String getPath(String batchId, String postfixType) throws Exception{
		String REPORT_PATH = InfBatchProperty.getInfBatchConfig(batchId+"_"+postfixType);
		if(!InfBatchUtil.empty(REPORT_PATH)){
			while(StringUtils.substringsBetween(REPORT_PATH, "{", "}") != null){
				String[] prefixTypes = StringUtils.substringsBetween(REPORT_PATH, "{", "}");
				for(String prefixType : prefixTypes){
					String PREFIX_PATH = InfBatchProperty.getInfBatchConfig(prefixType);
					if(!InfBatchUtil.empty(PREFIX_PATH)){
						REPORT_PATH = REPORT_PATH.replace("{"+prefixType+"}", PREFIX_PATH);
					}
				}
			}
		}
		return REPORT_PATH;
	}
	
	public static List<String> getMultiplePath(String batchId, String postfixType) throws Exception{
		List<String> REPORT_PATHS = new ArrayList<>();
		String pathConfig =InfBatchProperty.getInfBatchConfig(batchId+"_"+postfixType);
		if(!InfBatchUtil.empty(pathConfig)){
			String[] PATHS = pathConfig.split(",");
			for(String PATH : PATHS){
				String prefixType = StringUtils.substringBetween(PATH, "{", "}");
				if(!InfBatchUtil.empty(prefixType)){
					String PREFIX_PATH = InfBatchProperty.getInfBatchConfig(prefixType);
					PATH = PATH.replace("{"+prefixType+"}", PREFIX_PATH);
				}
				REPORT_PATHS.add(PATH);
			}
		}
		return REPORT_PATHS;
	}
	
	public static String getJasperPath() throws Exception{
		String JASPER_PATH = InfBatchProperty.getInfBatchConfig(JASPER_REPORT_PATH);
		String prefixType = StringUtils.substringBetween(JASPER_PATH, "{", "}");
		String PREFIX_PATH = InfBatchProperty.getInfBatchConfig(prefixType);
		JASPER_PATH = JASPER_PATH.replace("{"+prefixType+"}", PREFIX_PATH);
		return JASPER_PATH;
	}
}
