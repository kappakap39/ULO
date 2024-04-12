package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbNameDataM;

public interface OrigNCBNameDAO {
	
	public void createOrigNcbNameM(NcbNameDataM ncbNameM)throws ApplicationException;
	public void deleteOrigNcbNameM(String trackingCode, int seq)throws ApplicationException;	
	public ArrayList<NcbNameDataM> loadOrigNcbNameM(String trackingCode)throws ApplicationException;	
	public ArrayList<NcbNameDataM> loadOrigNcbNameM(String trackingCode,Connection conn)throws ApplicationException;
	public void saveUpdateOrigNcbNameM(NcbNameDataM ncbNameM)throws ApplicationException;
	public void deleteNotInKeyNcbName(ArrayList<NcbNameDataM> ncbNameList, String trackingCode)
			throws ApplicationException;
}
