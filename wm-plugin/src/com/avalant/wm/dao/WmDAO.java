package com.avalant.wm.dao;

import java.util.ArrayList;
import java.util.List;

import com.avalant.wm.model.Task;
import com.avalant.wm.model.WmTask;
import com.avalant.wm.model.WmTaskHistory;
import com.avalant.wm.model.WmTaskLog;

public interface WmDAO {

	public void saveTaskManager(WmTask task, WmTaskHistory taskHistory) throws Exception;
	public Task getTask(String serverName) throws Exception;
	public List<WmTask> getNotTaskAssign() throws Exception;
	public List<WmTask> getNotTaskAssign(ArrayList<String> taskGroup) throws Exception;
	public String getLastAssign() throws Exception;
	public void deleteCompleteTask(String taskId) throws Exception;
	public List<WmTask> getAssignTask(String processName) throws Exception;
	public void updateTaskStatus(String taskId, String status) throws Exception;
	public List<WmTask> getAssignTaskByTime(String processName) throws Exception;
	public List<WmTask> getAssignTaskByTime(String processName, ArrayList<String> taskGroup) throws Exception;
	public void updateLastRunTime(String taskId) throws Exception;
	public void updateNextRunTime(String taskId, int addTime) throws Exception;
	public void increaseErrorCount(String taskId) throws Exception;
	public int countServerTask(String serverName) throws Exception;
	public void createErrorLog(WmTaskLog taskLog) throws Exception;
	public List<WmTask> getLongRunTask() throws Exception;
	public void updateNextDayRunTime(String taskId) throws Exception;
	public boolean createCardlinkTask(String applicationNo) throws Exception;
	public List<WmTask> getTaskErrors() throws Exception;
}
