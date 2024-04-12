package com.eaf.inf.batch.ulo.notifycompletedapp.dao;


public class NotifyCompletedAppFactory {
	public  static NotifyCompletedAppDAO getNotifyCompletedAppDAO() {
		return new NotifyCompletedAppDAOImpl();
	}
}
