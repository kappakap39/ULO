package com.eaf.inf.batch.ulo.doa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.api.TaskWorker;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public class DeleteOldAppProcessTaskManager {
	private static transient Logger logger = Logger.getLogger(DeleteOldAppProcessTaskManager.class);
	
	public void process(ProcessTaskDataM processTask) throws InfBatchException{
		logger.debug("TaskManager.run");
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
		try{
			ArrayList<TaskObjectDataM> taskObjects = getTaskObjects(processTask);
			logger.debug("TaskManager.start");
			for(TaskObjectDataM taskObject : taskObjects){
				String taskId = getTaskId();
//				logger.debug("taskId >> "+taskId);
				TaskDataM task = new TaskDataM();
					task.setTaskId(taskId);
					task.setTaskObject(taskObject);	
				try{
					TaskWorker worker = new TaskWorker(processTask,task);
					worker.run();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
				}
			}
			logger.debug("TaskManager.finish");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
	
	public void run(ProcessTaskDataM processTask) throws InfBatchException{
		logger.debug("TaskManager.run");
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
		try{
			ArrayList<TaskObjectDataM> taskObjects = getTaskObjects(processTask);
			
			if(taskObjects.size() > 0)
			{
				logger.debug("TaskManager.start");
				ThreadPoolExecutor deleteOldAppExecutorPool = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,TimeUnit.MINUTES, 
														new ArrayBlockingQueue<Runnable>(queueCapacity,true));
			
				int cleanupZone = (taskObjects.size() > corePoolSize) ? taskObjects.size() - corePoolSize : 0;
				logger.debug("cleanupZone =  " + cleanupZone);
				
				if(taskObjects.size() > corePoolSize)
				{
					Collection<Future<?>> futures = new LinkedList<Future<?>>();
					
					for(int i = 0; i < cleanupZone ; i++)
					{
						TaskObjectDataM taskObject = taskObjects.get(i);
						String taskId = getTaskId();
						
						TaskDataM task = new TaskDataM();
						task.setTaskId(taskId);
						task.setTaskObject(taskObject);	
							
						try
						{
							Runnable worker = new TaskWorker(processTask,task);
							futures.add(deleteOldAppExecutorPool.submit(worker));
						}
						catch(Exception e){
							logger.fatal("ERROR ",e);
						}
					}
					
					logger.info("Wait for all task in non-cleanupZone to finished...");
					for (Future<?> future:futures) {
					    future.get();
					}
				}
				
				logger.info("Run set of task within clenupZone...");
				for(int i = cleanupZone; i < taskObjects.size() ; i++)
				{
					TaskObjectDataM taskObject = taskObjects.get(i);
					String taskId = getTaskId();
					
					TaskDataM task = new TaskDataM();
					task.setTaskId(taskId);
					task.setTaskObject(taskObject);	
						
					try
					{
						Runnable worker = new DeleteOldAppTaskWorker(processTask,task);
						deleteOldAppExecutorPool.execute(worker);
					}
					catch(Exception e){
						logger.fatal("ERROR ",e);
					}
				}
				
				deleteOldAppExecutorPool.shutdown();
				
				try
				{
					//According to document Long.MAX_VALUE = wait forever until all thread done.
					deleteOldAppExecutorPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} 
				catch (InterruptedException e) 
				{
					logger.fatal("ERROR ", e);
				}
			}
			
			logger.debug("TaskManager.finish");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}

	private ArrayList<TaskObjectDataM> getTaskObjects(ProcessTaskDataM processTask) throws InfBatchException{
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		String className = InfBatchProperty.getInfBatchConfig(processTask.getProcessName());
		logger.debug("className >> "+className);
		TaskInf taskInf = null;
		try{			        		    	
			taskInf = (TaskInf)Class.forName(className).newInstance();			        		    	
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}	
		if(null != taskInf){
			try{
				taskObjects = taskInf.getTaskObjects();
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return taskObjects;
	}
	private String getTaskId(){
		return ProcessTaskDataM.token(8);
	}
	
}
