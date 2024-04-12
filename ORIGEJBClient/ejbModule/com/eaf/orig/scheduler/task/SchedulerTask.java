package com.eaf.orig.scheduler.task;
/**
 * Remote interface for Enterprise Bean: SchedulerTask
 */
public interface SchedulerTask extends javax.ejb.EJBObject {
	public boolean scheduleTask(
		String startTime,
		int repeats,
		long repeatInterval,
		String taskName) throws java.rmi.RemoteException;
}
