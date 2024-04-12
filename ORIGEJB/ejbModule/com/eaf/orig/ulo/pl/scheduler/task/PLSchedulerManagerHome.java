package com.eaf.orig.ulo.pl.scheduler.task;

/**
 * Home interface for Enterprise Bean: PLSchedulerManager
 */
public interface PLSchedulerManagerHome extends javax.ejb.EJBHome {

	/**
	 * Creates a default instance of Session Bean: PLSchedulerManager
	 */
	public com.eaf.orig.ulo.pl.scheduler.task.PLSchedulerManager create()
		throws javax.ejb.CreateException,
		java.rmi.RemoteException;
}
