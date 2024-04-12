package com.eaf.orig.scheduler.process;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.scheduler.dao.Scheduler.SchedulerDAO;
import com.eaf.orig.scheduler.model.ExpireAppM;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.ibm.websphere.scheduler.TaskStatus;

/**
 * Bean implementation class for Enterprise Bean: SchedulerProcess
 */
public class SchedulerProcessBean implements javax.ejb.SessionBean {
	Logger log = Logger.getLogger(SchedulerProcessBean.class);
	
	public void process(TaskStatus status){
		log.debug("########################################################");
		log.debug("Processing Schedule Task.......");
		log.debug("Task ID : "+status.getTaskId());
		log.debug("Task Name : "+status.getName());
		log.debug("Task Created : "+status.getTimeCreated());
		log.debug("Task Repeat Left : "+status.getRepeatsLeft());
		log.debug("Task Next Time : "+status.getNextFireTime());
		log.debug("########################################################");
		
		System.out.println(">>>Task is AutoCancel");
		SchedulerDAO schedulerDAO = ORIGDAOFactory.getSchedulerDAO();
		try {
			Vector vExpApp = schedulerDAO.loadExpireApplication();
			
			ORIGApplicationManager appManager = ORIGEJBService.getApplicationManager();
			
			if(vExpApp!=null){
				for(int i=0; i<vExpApp.size(); i++){
					ExpireAppM expireAppM = (ExpireAppM)vExpApp.elementAt(i);
					try{
						appManager.autoCancelApplication(expireAppM.getAppRecID(), expireAppM.getJobState(), expireAppM.getAppStatus());
					}catch(Exception e){
						log.error("#### autoCancelApplication error ####");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
