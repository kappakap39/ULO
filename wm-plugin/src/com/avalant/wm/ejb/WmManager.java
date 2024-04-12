package com.avalant.wm.ejb;

import java.util.ArrayList;
import java.util.List;

import com.avalant.wm.model.WmTask;
import com.avalant.wm.model.WmTaskLog;

public interface WmManager {

	public void saveWorkTask(WmTask task) throws Exception;
	public void deleteCompleteTask(String taskId) throws Exception;
	public void updateTaskStatus(String taskId, String status) throws Exception;
	public void updateNextRunTime(String taskId, int addTime) throws Exception;
	public void updateLastRunTime(String taskId) throws Exception;
	public void increaseErrorCount(String taskId) throws Exception;
	public ArrayList<String> findAvaliableAssignServer() throws Exception;
	public void endTaskProcess(WmTask task, boolean taskComplete, WmTaskLog taskLog) throws Exception;
	public void runTaskProcess(String taskId) throws Exception;
	public List<WmTask> getNotAssignTask() throws Exception;
    public void assignTaskProcess(WmTask task) throws Exception;
}
