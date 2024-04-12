package com.eaf.core.ulo.common.task.api;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;

public class TaskWorker implements Runnable{
	private static transient Logger logger = Logger.getLogger(TaskWorker.class);
	protected ProcessTaskDataM processTask;
	protected TaskDataM task;
    public TaskWorker(ProcessTaskDataM processTask,TaskDataM task) {
    	this.processTask = processTask;
    	this.task = task;
    }
	@Override
	public void run(){
		logger.debug("TaskWorker.run");
		logger.debug("TaskWorker.processName >>"+processTask.getProcessName());
		logger.debug("TaskWorker.getProcessId >>"+processTask.getProcessId());
		logger.debug("TaskWorker.task >>"+task);
		try{
			String className = InfBatchProperty.getInfBatchConfig(processTask.getProcessName());
			logger.debug("className >> "+className);
			TaskInf taskInf = null;
			try{			        		    	
				taskInf = (TaskInf)Class.forName(className).newInstance();			        		    	
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}	
			if(null != taskInf){
				TaskExecuteDataM taskExecute = taskInf.onTask(task);
				processTask.put(taskExecute);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}	
}
