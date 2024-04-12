package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.NCBDocumentHistoryDataM;

public interface OrigNCBDocumentHistoryDAO {
	
	public void createOrigNCBDocHistoryM(NCBDocumentHistoryDataM ncbDocumentHistM)throws ApplicationException;
	public void deleteOrigNCBDocHistoryM(String personalId, String ncbDocHistoryId)throws ApplicationException;	
	public ArrayList<NCBDocumentHistoryDataM> loadOrigNCBDocHistoryM(String personalID)throws ApplicationException;	 
	public ArrayList<NCBDocumentHistoryDataM> loadOrigNCBDocHistoryM(String personalID,Connection conn)throws ApplicationException;
	public void saveUpdateOrigNCBDocHistoryM(NCBDocumentHistoryDataM ncbDocumentHistM)throws ApplicationException;
}
