package com.eaf.orig.ulo.pl.scheduler.task;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import javax.ejb.EJBException;

/**
 * Remote interface for Enterprise Bean: PLSchedulerManager
 */
public interface PLSchedulerManager extends javax.ejb.EJBObject {
	public boolean createScheduleTask(Date startTime,int repeats,String repeatInterval,String taskName) throws java.rmi.RemoteException;
	public boolean createScheduleTaskCRON(String runDay, String startHour, String startMinute, String taskName) throws java.rmi.RemoteException;
	public void cancelTaskScheduler(String taskName) throws EJBException, RemoteException;
	public Vector getAllTaskSchedulerInfo() throws EJBException, RemoteException;
}
