package com.eaf.core.ulo.common.task.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.eaf.core.ulo.common.cache.InfBatchProperty;

@SuppressWarnings("serial")
public class ProcessTaskDataM implements Serializable,Cloneable{
	public ProcessTaskDataM(String processName){
		super();
		this.processName = processName;
		this.processId = processName+"_"+token(8);
		this.corePoolSize = Integer.parseInt(InfBatchProperty.getInfBatchConfig("CORE_POOL_SIZE"));
		this.maxPoolSize = Integer.parseInt(InfBatchProperty.getInfBatchConfig("MAX_POOL_SIZE"));
		this.queueCapacity = Integer.parseInt(InfBatchProperty.getInfBatchConfig("QUEUE_CAPACITY"));
		this.keepAliveTime = Integer.parseInt(InfBatchProperty.getInfBatchConfig("KEEP_ALIVE_TIME"));
	}
	public ProcessTaskDataM(String processName,int corePoolSize,int maxPoolSize,int queueCapacity,int keepAliveTime){
		this.processName = processName;
		this.processId = processName+"_"+token(8);
		this.corePoolSize = corePoolSize;
		this.maxPoolSize = maxPoolSize;
		this.queueCapacity = queueCapacity;
		this.keepAliveTime = keepAliveTime;
	}
	private String configClassName;
	private String processName;
	private String processId;
	private int corePoolSize;
	private int maxPoolSize;
	private int queueCapacity;
	private int keepAliveTime;	
	private ArrayList<TaskExecuteDataM> taskExecutes = new ArrayList<TaskExecuteDataM>();
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getQueueCapacity() {
		return queueCapacity;
	}
	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
	public int getKeepAliveTime() {
		return keepAliveTime;
	}
	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
	static final String KEY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	public static String token(int len){
	   StringBuilder key = new StringBuilder(len);
	   for(int i = 0; i < len; i++) 
		  key.append( KEY.charAt(rnd.nextInt(KEY.length())));
	   return key.toString();
	}
	public synchronized void put(TaskExecuteDataM taskExecute){
		taskExecutes.add(taskExecute);
	}
	public ArrayList<TaskExecuteDataM> getTaskExecutes() {
		return taskExecutes;
	}
	public String getConfigClassName() {
		return configClassName;
	}
	public void setConfigClassName(String configClassName) {
		this.configClassName = configClassName;
	}
	public TaskExecuteDataM find(String taskId){
	    if (this.taskExecutes != null) {
	      for (TaskExecuteDataM taskExecute : this.taskExecutes) {
	        if ((taskExecute.getUniqueId() != null) && (taskExecute.getUniqueId().equals(taskId))) {
	          return taskExecute;
	        }
	      }
	    }
	    return new TaskExecuteDataM();
	}
}
