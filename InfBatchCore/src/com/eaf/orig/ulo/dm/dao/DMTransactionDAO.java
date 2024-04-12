package com.eaf.orig.ulo.dm.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.dm.dao.exception.DMException;
import com.eaf.orig.ulo.model.dm.HistoryDataM;

public interface DMTransactionDAO {
	public void saveTableDMTransaction(String dmId,HistoryDataM  docHistory,Connection conn) throws DMException;
	public ArrayList<HistoryDataM> selectTableDMTransaction(String dmId) throws DMException;
}
