package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ReprocessLogDataM;

public interface OrigReprocessLogDAO {
	public void createOrigReprocessLog(ReprocessLogDataM reprocessLog)throws ApplicationException;
	public void deleteOrigReprocessLog(String applicationGroupId, String reprocessLogId)throws ApplicationException;
	public ArrayList<ReprocessLogDataM> loadReprocessLog(String applicationGroupId)throws ApplicationException;	 
	public ArrayList<ReprocessLogDataM> loadReprocessLog(String applicationGroupId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigReprocessLog(ReprocessLogDataM reprocessLog)throws ApplicationException;
}
