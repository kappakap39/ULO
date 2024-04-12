package com.avalant.wm.work;

import org.apache.log4j.Logger;

import com.avalant.wm.lookup.LookUpWMManager;
import com.avalant.wm.model.WmTask;
import com.avalant.wm.model.WmTaskLog;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ComplexClassExclusionStrategy;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.websphere.asynchbeans.Work;

public class WorkTask implements Work{
	
	private Logger logger = Logger.getLogger(WorkTask.class);
	private String wmFunc;
	private String resultCode;
	private String resultDesc;
	private String taskId;
	private WmTask task; 
	private String responseStatus;
	private WmTaskLog taskLog;
	
	public WorkTask() {
		
	}
	
	public WorkTask(String wmFunc, WmTask task){
		this.wmFunc = wmFunc;
		this.taskId = task.getTaskId();
		this.task = task;
	}
	
	@Override
	public void run() {
		logger.debug("Start");
	    try{
	    	logger.debug("WorkManagerTask " + wmFunc);
	    	LookUpWMManager.getWmManager().runTaskProcess(taskId);
	    	
	        ProcessActionInf processActionInf = this.actionProcess(wmFunc);
			if(null!=processActionInf){
				processActionInf.init(task);
				ProcessResponse response = (ProcessResponse) processActionInf.processAction();
				logger.debug("response >> "+ response);
				responseStatus = response.getStatusCode();
				logger.debug("WorkTask Status code : " + responseStatus);
				logger.debug("response getErrorData >> "+ response.getErrorData());
				Gson gson = new GsonBuilder().setExclusionStrategies(new ComplexClassExclusionStrategy()).create();
				String msg = gson.toJson(response);
				
				taskLog = new WmTaskLog();
				taskLog.setTaskId(taskId);
				taskLog.setTaskErrorId(genTaskErrorId());
				taskLog.setTask(wmFunc);
				taskLog.setRespCode(responseStatus);
				try{
					logger.debug("response " + response.getErrorData().toString());
					String errorMsg = response.getErrorData().getErrorDesc();
					if(Util.empty(errorMsg)){
						errorMsg = response.getErrorData().getErrorInformation();
					}
					taskLog.setErrorMsg(errorMsg);
				}catch(Exception e){
//					logger.fatal("ERROR",e);
					taskLog.setErrorMsg(e.getMessage());
				}
				
				taskLog.setMsg(msg);
		    }else{
				logger.error("cannot find action process for :" + wmFunc);
			}
	    }catch(Exception e){
	    	logger.fatal("ERROR",e);
	    }
	    
	    release();
	}
	
	@Override
	public void release() {
		try {
			boolean taskComplete = false;
			if(ServiceResponse.Status.SUCCESS.equals(responseStatus)){
				taskComplete = true;
			}
			LookUpWMManager.getWmManager().endTaskProcess(task, taskComplete, taskLog);
		} catch (Exception e) {
			logger.fatal(e);
		}
		logger.debug("End");
	}
	
	private ProcessActionInf actionProcess(String action){
		String CACHE_WORK_MANAGER_ACTION = SystemConstant.getConstant("CACHE_WORK_MANAGER_ACTION");
		ProcessActionInf processActionInf = null; 
		logger.debug("action >> "+action);
		try{
			String className = CacheControl.getName(CACHE_WORK_MANAGER_ACTION, action);
			logger.debug("className >> "+className);
			processActionInf = (ProcessActionInf)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return processActionInf;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	private String genTaskErrorId(){
		return task.getTaskId() + "_" + (task.getErrorTotal()+1);
	}
}
