package com.eaf.core.ulo.common.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class PerformanceController {
	private static transient Logger logger = Logger.getLogger(PerformanceController.class);
	HashMap<String,Long> performanceData = new HashMap<String,Long>();
	public void create(String debugId){
		performanceData.put(debugId, System.currentTimeMillis());
	}
	public void end(String debugId){
		long startTime = performanceData.get(debugId);
		long finishedTime = System.currentTimeMillis();
		long time = calculateTime(startTime,finishedTime);
		logger.debug(debugId+" used time "+time+" s.");
	}
	private long calculateTime(long startTime, long finishedTime){
		return ((finishedTime - startTime)/1000);
	}
}
