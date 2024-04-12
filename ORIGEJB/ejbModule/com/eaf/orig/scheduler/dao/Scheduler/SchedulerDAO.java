/*
 * Created on 12 เม.ย. 2550
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.scheduler.dao.Scheduler;

import java.util.Vector;

/**
 * @author Wichaya
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SchedulerDAO {
	public String loadWorkingTime(String queueTimeOutID) throws Exception;
	public Vector loadExpireApplication() throws Exception;
	public int deleteOldTask(String taskName) throws Exception;
//	public Calendar loadExpiryDate(String org, String AppRecID, String appDecision, String queue) throws Exception;
}
