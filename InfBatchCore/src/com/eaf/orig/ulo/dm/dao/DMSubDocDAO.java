package com.eaf.orig.ulo.dm.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.dm.dao.exception.DMException;
import com.eaf.orig.ulo.model.dm.DocumentDataM;

public interface DMSubDocDAO {
	public void saveTableDMSubDoc(String dmId,ArrayList<DocumentDataM> documentDataMList,Connection conn) throws DMException;
	public ArrayList<DocumentDataM> selectTableDMSubDoc(String dmId) throws DMException;
	public void deleteNotInKeyDMSubDoc(String dmId,ArrayList<DocumentDataM> documentDataMList,Connection conn) throws DMException; 
}
