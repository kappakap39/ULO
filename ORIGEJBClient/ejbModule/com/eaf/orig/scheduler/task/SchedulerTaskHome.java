package com.eaf.orig.scheduler.task;
/**
 * Home interface for Enterprise Bean: SchedulerTask
 */
public interface SchedulerTaskHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: SchedulerTask
	 */
	public com.eaf.orig.scheduler.task.SchedulerTask create()
		throws javax.ejb.CreateException,
		java.rmi.RemoteException;
}
