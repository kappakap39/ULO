package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;

public interface OrigRequiredDocDAO {
	
	public void createOrigRequiredDocM(RequiredDocDataM requiredDocM)throws ApplicationException;
	public void createOrigRequiredDocM(RequiredDocDataM requiredDocM,Connection conn)throws ApplicationException;
	public void deleteOrigRequiredDocM(String verResultId, String requiredDocId)throws ApplicationException;
	public ArrayList<RequiredDocDataM> loadOrigRequiredDocM(String verResultId)throws ApplicationException;
	public ArrayList<RequiredDocDataM> loadOrigRequiredDocM(String verResultId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigRequiredDocM(RequiredDocDataM requiredDocM)throws ApplicationException;
	public void saveUpdateOrigRequiredDocM(RequiredDocDataM requiredDocM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyRequiredDoc(ArrayList<RequiredDocDataM> requiredDocList, 
			String verResultId) throws ApplicationException;
	public void deleteNotInKeyRequiredDoc(ArrayList<RequiredDocDataM> requiredDocList, 
			String verResultId,Connection conn) throws ApplicationException;
}
