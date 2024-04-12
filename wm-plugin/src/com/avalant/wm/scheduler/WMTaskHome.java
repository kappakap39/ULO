package com.avalant.wm.scheduler;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBMetaData;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;

import com.ibm.websphere.scheduler.TaskHandler;
import com.ibm.websphere.scheduler.TaskHandlerHome;

public class WMTaskHome implements TaskHandlerHome {

	@Override
	public EJBMetaData getEJBMetaData() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HomeHandle getHomeHandle() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Handle handle) throws RemoteException, RemoveException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Object primaryKey) throws RemoteException,
			RemoveException {
		// TODO Auto-generated method stub

	}

	@Override
	public TaskHandler create() throws CreateException, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
