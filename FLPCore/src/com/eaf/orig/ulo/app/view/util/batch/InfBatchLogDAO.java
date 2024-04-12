package com.eaf.orig.ulo.app.view.util.batch;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public interface InfBatchLogDAO {
	public void blockInfBatchLogReprocess(String applicationGroupId,int lifeCycle,String logMessage)throws ApplicationException;
	public void blockInfBatchLogReprocess(String applicationGroupId,int lifeCycle,String logMessage,Connection conn)throws ApplicationException;
}
