package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.HistoryDataM;

public interface OrigHistoryDataDAO {
	
	public void createOrigHistoryDataM(HistoryDataM historyDataM)throws ApplicationException;
	public void createOrigHistoryDataM(HistoryDataM historyDataM,Connection conn)throws ApplicationException;
	public void saveUpdateOrigHistoryDataM(HistoryDataM historyDataM)throws ApplicationException;
	public void saveUpdateOrigHistoryDataM(HistoryDataM historyDataM,Connection conn)throws ApplicationException;
	public HistoryDataM loadOrigHistoryDataM(String appGroupId, String role)throws ApplicationException;

}
