package com.eaf.core.ulo.common.task.api;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskDataM;

public class ExecuteTaskManager {
	private static transient Logger logger = Logger.getLogger(ExecuteTaskManager.class);
	private ProcessTaskDataM processTask;
	private ThreadPoolExecutor executorPool = null;
	private int totalTask = 0;	
	public ExecuteTaskManager(ProcessTaskDataM processTask){
		this.processTask = processTask;
		String processName = processTask.getProcessName();
		String processId = processTask.getProcessId();
		int corePoolSize = processTask.getCorePoolSize();
		int maxPoolSize = processTask.getMaxPoolSize();
		int queueCapacity = processTask.getQueueCapacity();
		int keepAliveTime = processTask.getKeepAliveTime(); 
		logger.debug("TaskManager.processName >> "+processName);
		logger.debug("TaskManager.processId >> "+processId);
		logger.debug("TaskManager.corePoolSize >> "+corePoolSize);
		logger.debug("TaskManager.maxPoolSize >> "+maxPoolSize);
		logger.debug("TaskManager.queueCapacity >> "+queueCapacity);
		logger.debug("TaskManager.keepAliveTime >> "+keepAliveTime);
		executorPool = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(queueCapacity,true));
	}
	public void execute(TaskDataM task){
		try{
			totalTask++;
			if(null != executorPool){
				Runnable worker = new TaskWorker(processTask,task);
				executorPool.execute(worker);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	public void shutdown(){
		if(null != executorPool){
			executorPool.shutdown();
		}
		while(!executorPool.isTerminated());
	}
	public int getTotalTask() {
		return totalTask;
	}
	public void setTotalTask(int totalTask) {
		this.totalTask = totalTask;
	}	
}
