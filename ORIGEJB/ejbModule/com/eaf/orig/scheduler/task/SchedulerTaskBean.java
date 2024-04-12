package com.eaf.orig.scheduler.task;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.ibm.websphere.scheduler.BeanTaskInfo;
import com.ibm.websphere.scheduler.Scheduler;
import com.ibm.websphere.scheduler.TaskHandlerHome;
import com.ibm.websphere.scheduler.TaskStatus;

/**
 * Bean implementation class for Enterprise Bean: SchedulerTask
 */
public class SchedulerTaskBean implements javax.ejb.SessionBean {
	
	Logger log = Logger.getLogger(SchedulerTaskBean.class);
	
	public boolean scheduleTask(String startTime, int repeats, long repeatInterval, String taskName){
		boolean result = false;
		
		try{
			//Get the Scheduler
			Context initialContext = new InitialContext();
			System.out.println("Getting the Scheduler");
			
			Object schedulerObj = initialContext.lookup("origScheduler");
			Scheduler scheduler = (Scheduler)PortableRemoteObject.narrow(schedulerObj, Scheduler.class);
			//Get the task handler which will be called when the task runs.
			System.out.println("Getting the task handler");
			
			Object taskHandlerObj = initialContext.lookup("java:comp/env/ejb/SchedulerProcess");
			TaskHandlerHome taskHandlerHome = (TaskHandlerHome)PortableRemoteObject.narrow(taskHandlerObj, TaskHandlerHome.class);
			//Create our Schedule Task Info for our task handler
			System.out.println("SchedulerTaskBean: Creating the task");
			BeanTaskInfo taskInfo = scheduler.createBeanTaskInfo();
			taskInfo.setTaskHandler(taskHandlerHome);
			
//			Date stateDate = new Date();
//			Calendar curDate = Calendar.getInstance();
//			String hourStr = startTime.substring(0, 2);
//			String minStr = startTime.substring(2,4);
//			
//			curDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourStr));
//			curDate.set(Calendar.MINUTE, Integer.parseInt(minStr));
//			curDate.set(Calendar.SECOND, 00);
//			
//			stateDate = curDate.getTime();
//			
//			Calendar  calendar = null;
//			stateDate = curDate.getTime();
//			
//			while(stateDate.before(new Date())){
//				calendar = Calendar.getInstance();
//				calendar.setTime(stateDate);
//				calendar.add(Calendar.DATE, Integer.parseInt(Long.toString(repeatInterval)));
//				stateDate = calendar.getTime();
//			}
			
			System.out.println(">>> SchedulerTaskBean >> startTime "+startTime);
			taskInfo.setUserCalendar(null,"CRON");
			taskInfo.setStartTimeInterval(startTime);
			taskInfo.setRepeatInterval(startTime);
			taskInfo.setNumberOfRepeats(repeats);
			taskInfo.setName(taskName);
			//set the name of the task which is associated to a TaskID
			TaskStatus status = scheduler.create(taskInfo);
			System.out.println("SchedulerTaskBean: Task submitted");
			System.out.println("SchedulerTaskBean: Task ID is: "+status.getTaskId());
			System.out.println("SchedulerTaskBean: The Name of the Task is: "+status.getName());
			System.out.println("StartBean.scheduleTask: Task status is: "+status.getStatus());
			result = true;
		}catch(Exception e){
			System.out.println("StartBean.scheduleTask: failed");
			e.printStackTrace();
		}
		
		return result;
	}
	
	private javax.ejb.SessionContext mySessionCtx;
	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) {
		mySessionCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
}
