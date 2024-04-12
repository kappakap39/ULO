package com.avalant.wm.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.avalant.wm.common.WorkManagerUtil;
import com.avalant.wm.dao.WmDAOFactory;
import com.avalant.wm.lookup.LookUpWMManager;
import com.avalant.wm.model.WmTask;
import com.avalant.wm.model.WmTaskLog;
import com.avalant.wm.proxy.WorkManagerProxy;
import com.avalant.wm.work.WorkTask;
import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;
import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.ibm.websphere.asynchbeans.WorkManager;
import com.ibm.websphere.management.AdminServiceFactory;

/**
 * Session Bean implementation class WMTaskScheduler
 */
@Stateless
public class WMTaskScheduler {

	private static Logger logger = Logger.getLogger(WMTaskScheduler.class);
	private String WM_SERVER_ASSIGN_TASK = SystemConfig.getGeneralParam("WM_SERVER_ASSIGN_TASK");
	private ArrayList<String> WM_EAPP_ACTION_TASK = SystemConstant.getArrayListConstant("WM_EAPP_ACTION_TASK");
	private ArrayList<String> WM_CJD_ACTION_TASK = SystemConstant.getArrayListConstant("WM_CJD_ACTION_TASK");
	
	@Schedule(hour="*", minute="*", second="0/15")
	public void SchedAssignTask(){
		Calendar runTime = ServiceApplicationDate.getCalendar();
		if(!WorkManagerUtil.isWorkHour(runTime)){
			return;
		}
		
		logger.debug("############## Start Assign Task ##############");
		String processName = AdminServiceFactory.getAdminService().getProcessName();
		logger.debug("run on Server " + processName);
		if(!WM_SERVER_ASSIGN_TASK.equals(processName)){
			logger.debug("server is not " + WM_SERVER_ASSIGN_TASK + " not assign task");
			return;
		}

		try {
			List<WmTask> taskList = WmDAOFactory.getWMDAO().getNotTaskAssign(WM_EAPP_ACTION_TASK);
			logger.debug("number of task to assign : " + taskList.size());
			List<String> assignServer = LookUpWMManager.getWmManager().findAvaliableAssignServer();
			if(Util.empty(assignServer)){
				logger.debug("No Avaliable Server to Assign");
				return;
			}
			logger.debug("Server Avaliable to Assign : " + assignServer);
			for(int index=0; index < taskList.size(); index++){
				WmTask task = taskList.get(index);
				String serverName = assignServer.get(index%assignServer.size());
				logger.debug("serverName : " + serverName);
				task.setServerName(serverName);
				LookUpWMManager.getWmManager().assignTaskProcess(task);
			}
		} catch (Exception e) {
			logger.fatal("Error : ",e);
		}
		logger.debug("############## End Assign Task ##############");
	}
	
	@Schedule(hour="*", minute="*", second="0/15")
	public void SchedWorkManager(){
		Calendar runTime = ServiceApplicationDate.getCalendar();
		if(!WorkManagerUtil.isWorkHour(runTime)){
			return;
		}
		logger.debug("############## Start Work Task ##############");
		String processName = AdminServiceFactory.getAdminService().getProcessName();
		logger.debug("run on Server " + processName);
		try{
			List<WmTask> taskList = WmDAOFactory.getWMDAO().getAssignTaskByTime(processName, WM_EAPP_ACTION_TASK);
			logger.debug("number of assign task to this server : " + taskList.size());
			if(!Util.empty(taskList)){
				WorkManager wm = WorkManagerProxy.lookup();
				
				for(WmTask wmTask : taskList){
					WorkTask workTask = new WorkTask(wmTask.getWmFunc(), wmTask);
					wm.startWork(workTask);
				}
			}
		}catch(Exception e){
			logger.fatal("Error : ",e);
		}
		logger.debug("############## End Work Task ##############");
	}
	
	@Schedule(hour="*", minute="0/30")
	public void SchedClearLongRunTask(){
		Calendar runTime = ServiceApplicationDate.getCalendar();
		if(!WorkManagerUtil.isWorkHour(runTime)){
			return;
		}
		String processName = AdminServiceFactory.getAdminService().getProcessName();
		try{
			if(!WM_SERVER_ASSIGN_TASK.equals(processName)){
				logger.debug("server is not " + WM_SERVER_ASSIGN_TASK + " not clear long run task");
				return;
			}
			List<WmTask> taskList = WmDAOFactory.getWMDAO().getLongRunTask();
			if(!Util.empty(taskList)){
				boolean taskComplete = false;
				WmTaskLog taskLog = null;
				for(WmTask task : taskList){
					taskLog = new WmTaskLog();
					taskLog.setTaskId(task.getTaskId());
					taskLog.setTaskErrorId(task.getTaskId() + "_" + (task.getErrorTotal()+1));
					taskLog.setTask(task.getWmFunc());
					taskLog.setRespCode("20");
					taskLog.setErrorMsg("Long Run Task");
					
					LookUpWMManager.getWmManager().endTaskProcess(task, taskComplete, taskLog);
				}
			}
		}catch(Exception e){
			logger.fatal("Error : ",e);
		}
	}
	
	@Schedule(hour="*", minute="*", second="0/15")
	public void SchedAssignTaskCJD(){
		Calendar runTime = ServiceApplicationDate.getCalendar();
		if(!WorkManagerUtil.isWorkHourCJD(runTime)){
			return;
		}
		
		logger.debug("############## Start Assign CJD Task ##############");
		String processName = AdminServiceFactory.getAdminService().getProcessName();
		logger.debug("run on Server " + processName);
		if(!WM_SERVER_ASSIGN_TASK.equals(processName)){
			logger.debug("server is not " + WM_SERVER_ASSIGN_TASK + " not assign task");
			return;
		}

		try {
			List<WmTask> taskList = WmDAOFactory.getWMDAO().getNotTaskAssign(WM_CJD_ACTION_TASK);
			logger.debug("number of task to assign : " + taskList.size());
			List<String> assignServer = LookUpWMManager.getWmManager().findAvaliableAssignServer();
			if(Util.empty(assignServer)){
				logger.debug("No Avaliable Server to Assign");
				return;
			}
			logger.debug("Server Avaliable to Assign : " + assignServer);
			for(int index=0; index < taskList.size(); index++){
				WmTask task = taskList.get(index);
				String serverName = assignServer.get(index%assignServer.size());
				logger.debug("serverName : " + serverName);
				task.setServerName(serverName);
				LookUpWMManager.getWmManager().assignTaskProcess(task);
			}
		} catch (Exception e) {
			logger.fatal("Error : ",e);
		}
		logger.debug("############## End Assign CJD Task ##############");
	}
	
	@Schedule(hour="*", minute="*", second="0/15")
	public void SchedWorkManagerCJD(){
		Calendar runTime = ServiceApplicationDate.getCalendar();
		if(!WorkManagerUtil.isWorkHourCJD(runTime)){
			return;
		}
		logger.debug("############## Start Work CJD Task ##############");
		String processName = AdminServiceFactory.getAdminService().getProcessName();
		logger.debug("run on Server " + processName);
		try{
			List<WmTask> taskList = WmDAOFactory.getWMDAO().getAssignTaskByTime(processName, WM_CJD_ACTION_TASK);
			logger.debug("number of assign task to this server : " + taskList.size());
			if(!Util.empty(taskList)){
				WorkManager wm = WorkManagerProxy.lookupCJD();
				
				for(WmTask wmTask : taskList){
					WorkTask workTask = new WorkTask(wmTask.getWmFunc(), wmTask);
					wm.startWork(workTask);
				}
			}
		}catch(Exception e){
			logger.fatal("Error : ",e);
		}
		logger.debug("############## End Work CJD Task ##############");
	}
	
	@Schedule(hour="*", minute="*", second="0/45")
	public void SchedCardlinkTask(){
		Calendar runTime = ServiceApplicationDate.getCalendar();
		if(!WorkManagerUtil.isWorkHourCJD(runTime)){
			return;
		}
		logger.debug("############## Start Cardlink Task ##############");
		String processName = AdminServiceFactory.getAdminService().getProcessName();
		logger.debug("run on Server " + processName);
		if(!WM_SERVER_ASSIGN_TASK.equals(processName)){
			logger.debug("server is not " + WM_SERVER_ASSIGN_TASK + " not create cardlink task");
			return;
		}
		try{
			List<String> applications = CJDCardLinkAction.getDistinctQR();
			logger.debug("number of application cardlink result : " + applications.size());
			if(!Util.empty(applications)){
				for(String applicationNo : applications){
					//create or re-run task of application no
					logger.debug("applicationNo : " + applicationNo);
					boolean result = WmDAOFactory.getWMDAO().createCardlinkTask(applicationNo);
					if(result){
						logger.debug("success create or rerun task");
					}
				}
			}
		}catch(Exception e){
			logger.fatal("Error : ",e);
		}
		logger.debug("############## End Cardlink Task ##############");
	}
	
	@Schedule(hour="*", minute="0/15")
	public void SchedTaskError() {
		Calendar runTime = ServiceApplicationDate.getCalendar();
		if(!WorkManagerUtil.isWorkHour(runTime)){
			return;
		}
		logger.debug("############## Start Work Error Task ##############");
		try{
			List<WmTask> taskList = WmDAOFactory.getWMDAO().getTaskErrors();
			if ( ! Util.empty( taskList ) ) {
				String msgSubject = "E-App Task Error";
				String msgBody = getBodyMsg(taskList);
				
				String EMAIL_ADDRESS_FROM = InfBatchProperty.getGeneralParam("EMAIL_POLICY_PO");
				logger.debug("EMAIL_ADDRESS_FROM : " + EMAIL_ADDRESS_FROM );
				String EMAIL_TO = InfBatchProperty.getInfBatchConfig("UPDATE_ACCOUNT_WARNING_EMAIL_TO");
				
				EmailRequest emailRequest = new EmailRequest();
				emailRequest.setTo( EMAIL_TO );
				emailRequest.setFrom( EMAIL_ADDRESS_FROM );
				emailRequest.setSubject( msgSubject );
				emailRequest.setContent( msgBody );
				emailRequest.setSentDate(InfBatchProperty.getTimestamp());
				
				EmailResponse emailResponse = new EmailResponse();
				try 
				{
					EmailClient emailClient = new EmailClient();
					emailResponse = emailClient.send(emailRequest);
					logger.debug("emailResponse : " + emailResponse.getStatusCode() + " - " + emailResponse.getStatusDesc());
				} 
				catch (Exception e) 
				{
					logger.fatal("ERROR ", e);
				}
			
			}
		}catch(Exception e){
			logger.fatal("Error : ",e);
		}
		logger.debug("############## End Work Error Task ##############");
	}
	
	private String getBodyMsg(List<WmTask> taskList){
		StringBuilder msgBody = new StringBuilder();
		msgBody.append("<p> REF_CODE &emsp;&emsp;&emsp;&emsp; TASK &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; CREATE_TIME &emsp;&emsp;&emsp;&emsp;&emsp; LAST_RUN_TIME &emsp;&emsp; ERROR_TOTAL </p>");
		for (WmTask wmTask : taskList) {
			msgBody.append("<p> " + wmTask.getRefCode() + " &emsp; ");
			msgBody.append( wmTask.getWmFunc() + " &emsp; ");
			msgBody.append( wmTask.getCreateTime() + " &emsp; ");
			msgBody.append( wmTask.getLastRuntime() + " &emsp; ");
			msgBody.append( wmTask.getErrorTotal() + " &emsp; ");
			msgBody.append("</p>");
		}
		logger.debug(" msg email body : " + msgBody.toString() );
		return msgBody.toString();
	}
}
