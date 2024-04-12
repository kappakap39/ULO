package com.avalant.wm.ejb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.avalant.wm.common.WorkManagerUtil;
import com.avalant.wm.dao.WmDAOFactory;
import com.avalant.wm.model.WmTask;
import com.avalant.wm.model.WmTaskHistory;
import com.avalant.wm.model.WmTaskLog;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.api.ServiceApplicationDate;

/**
 * Session Bean implementation class WmManagerBean
 */
@Stateless
@Local(WmManager.class)
@LocalBean
public class WmManagerBean implements WmManager {

	private static Logger logger = Logger.getLogger(WmManagerBean.class);
	private int MAX_TASK_PER_SERVER = Integer.parseInt(SystemConfig.getProperty("MAX_TASK_PER_SERVER"));
	private int MAX_ERROR_COUNT = Integer.parseInt(SystemConfig.getProperty("MAX_ERROR_COUNT"));
	private int ADD_NEXT_TIME = Integer.parseInt(SystemConfig.getProperty("ADD_NEXT_TIME"));
	private ArrayList<String> WM_ALL_SERVER = SystemConfig.getArrayListGeneralParam("WM_ALL_SERVER");
	private int REMAIN_CAPACITY = Integer.parseInt(SystemConfig.getProperty("REMAIN_CAPACITY"));
	private int ADD_NEXT_TIME_OVER_MAX_ERROR = Integer.parseInt(SystemConfig.getProperty("ADD_NEXT_TIME_OVER_MAX_ERROR"));
	String CACHE_WORKFLOW_PARAM = SystemConstant.getConstant("CACHE_WORKFLOW_PARAM");
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");

	@Override
	public void saveWorkTask(WmTask task) throws Exception {
		if(Util.empty(task.getTaskId())){
			String taskId = GenerateUnique.generate(MConstant.WM_TASK_ID_PK_SEQUENCE);
			task.setTaskId(taskId);
		}
		WmTaskHistory taskHistory = new WmTaskHistory();
		taskHistory.setTaskId(task.getTaskId());
		taskHistory.setWmFunc(task.getWmFunc());
		taskHistory.setRefId(task.getRefId());
		taskHistory.setRefCode(task.getRefCode());
		if(null != task.getData() && !Util.empty(task.getData().getTaskData())){
			taskHistory.setTaskData(task.getData().getTaskData());
		}
		
		WmDAOFactory.getWMDAO().saveTaskManager(task, taskHistory);
	}

	@Override
	public void deleteCompleteTask(String taskId) throws Exception {
		WmDAOFactory.getWMDAO().deleteCompleteTask(taskId);
	}

	@Override
	public void updateTaskStatus(String taskId, String status) throws Exception {
		WmDAOFactory.getWMDAO().updateTaskStatus(taskId, status);
	}

	@Override
	public void updateNextRunTime(String taskId, int addTime) throws Exception {
		WmDAOFactory.getWMDAO().updateNextRunTime(taskId, addTime);
	}

	@Override
	public void updateLastRunTime(String taskId) throws Exception {
		WmDAOFactory.getWMDAO().updateLastRunTime(taskId);
	}

	@Override
	public void increaseErrorCount(String taskId) throws Exception {
		WmDAOFactory.getWMDAO().increaseErrorCount(taskId);
	}

	@Override
	public ArrayList<String> findAvaliableAssignServer() throws Exception {
		ArrayList<String> listServer = new ArrayList<String>();
		if(Util.empty(WM_ALL_SERVER)){
			logger.info("Generalparam WM_ALL_SERVER is NULL!! Cannot Assign Task");
			return null;
		}
		for(String serverName : WM_ALL_SERVER){
			int countTask = WmDAOFactory.getWMDAO().countServerTask(serverName);
			int remaining = MAX_TASK_PER_SERVER - countTask;
			double remainingPercent = remaining * 100 / MAX_TASK_PER_SERVER;
			
			logger.debug("server countTask : " + countTask);
			logger.debug("remaining amount : " + remaining);
			logger.debug("remainingPercent : " + remainingPercent);
			
			if(remainingPercent > REMAIN_CAPACITY){
				listServer.add(serverName);
			}
		}
		
		return listServer;
	}

	@Override
	public void endTaskProcess(WmTask task, boolean taskComplete, WmTaskLog taskLog)
			throws Exception {
		String taskId = task.getTaskId();
		String status = "";
		if(taskComplete){
			deleteCompleteTask(taskId);
			status = MConstant.TASK_STATUS.COMPLETE;
		}
		else{
			int errorCount = task.getErrorCount() + 1;
			logger.debug("errorCount : " + errorCount);
			logger.debug("errorTotal : " + task.getErrorTotal());
			logger.debug("!!ERROR_MSG!! : " + taskLog.getErrorMsg());
			if(ERROR_SUBMIT_APP_WAIT_FOR_APPROVE.equals(taskLog.getErrorMsg())){
				logger.debug("!!ERROR_SUBMIT_APP_WAIT_FOR_APPROVE!!");
				WmDAOFactory.getWMDAO().updateNextDayRunTime(taskId);
			}
			else if(MAX_ERROR_COUNT <= errorCount){
//				status = MConstant.TASK_STATUS.WAIT_ERROR_COLLECT;
				//check next run time not over working hour
//				String WORKING_HR_END_KBANK = CacheControl.getName(CACHE_WORKFLOW_PARAM, "WORKING_HR_END_KBANK");
//				logger.debug("WORKING_HR_END_KBANK : " + WORKING_HR_END_KBANK);
//				if(Util.empty(WORKING_HR_END_KBANK)){
//					logger.debug("WORKING_HR_END_KBANK is null set default to 19:30");
//					WORKING_HR_END_KBANK = "19:30";
//				}
//				int endWorkingHour = Integer.parseInt(WORKING_HR_END_KBANK.split(":")[0]);
//				int endWorkingMin = Integer.parseInt(WORKING_HR_END_KBANK.split(":")[1]);
//				Calendar endWorkingTime = ServiceApplicationDate.getCalendar();
//				endWorkingTime.set(Calendar.HOUR_OF_DAY, endWorkingHour);
//				endWorkingTime.set(Calendar.MINUTE, endWorkingMin);
//				endWorkingTime.set(Calendar.SECOND, 0);
				
				Calendar newRunTime = ServiceApplicationDate.getCalendar();
				newRunTime.add(Calendar.SECOND, ADD_NEXT_TIME_OVER_MAX_ERROR);
//				if(endWorkingTime.compareTo(newRunTime) >= 0){
				if(WorkManagerUtil.isWorkHour(newRunTime)){
					//after max error add run time with new value
					updateNextRunTime(taskId, ADD_NEXT_TIME_OVER_MAX_ERROR);
				}
				else{
					//if over working hour stop this task
					status = MConstant.TASK_STATUS.WAIT_ERROR_COLLECT;
				}
			}
			else{
				Calendar newRunTime = ServiceApplicationDate.getCalendar();
				newRunTime.add(Calendar.SECOND, ADD_NEXT_TIME);
				if(WorkManagerUtil.isWorkHour(newRunTime)){
					updateNextRunTime(taskId, ADD_NEXT_TIME);
				}
				else{
					WmDAOFactory.getWMDAO().updateNextDayRunTime(taskId);
				}
			}
			increaseErrorCount(taskId);
			
			Date date = new Date();
			String refId = createRefId(date);
			String serverTime = createServerTime(date);
			String refMsg = " Error Reference Id = " + refId + ", Server Time " + serverTime;
			logger.error("[Reference Id].." + refId);
			logger.error("[Error Reference].." + refMsg);
			if(null != taskLog){
				taskLog.setErrorMsg(refMsg + " " + taskLog.getErrorMsg());
				WmDAOFactory.getWMDAO().createErrorLog(taskLog);
			}
		}
		
		task.setStatus(status);
		//clear assign server
		task.setServerName("");
		saveWorkTask(task);
	}

	@Override
	public void runTaskProcess(String taskId) throws Exception {
		updateLastRunTime(taskId);
    	updateTaskStatus(taskId, MConstant.TASK_STATUS.RUNNING);
	}

	@Override
	public List<WmTask> getNotAssignTask() throws Exception {
		List<WmTask> taskList = WmDAOFactory.getWMDAO().getNotTaskAssign();
		return null;
	}

	@Override
	public void assignTaskProcess(WmTask task) throws Exception {
        saveWorkTask(task);
        updateNextRunTime(task.getTaskId(), 0);
	}
	
	private static String createServerTime(Date date) { SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	  return format.format(date);
	}
	
	private static String createRefId(Date date) { SimpleDateFormat format = new SimpleDateFormat("HHmmssSSS");
	  return format.format(date);
	}
}
