package com.eaf.inf.batch.ulo.notifycompletedapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public interface NotifyCompletedAppDAO {
	public HashMap<String, Object> getNotifyCompletedApp() throws InfBatchException;
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException;
	public Date getApplicationDate() throws InfBatchException;
}
