package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbIdDataM;

public interface OrigNCBIdDAO {
	
	public void createOrigNcbIdM(NcbIdDataM ncbIdM)throws ApplicationException;
	public void deleteOrigNcbIdM(String trackingCode, int seq)throws ApplicationException;	
	public ArrayList<NcbIdDataM> loadOrigNcbIdM(String trackingCode)throws ApplicationException;
	public ArrayList<NcbIdDataM> loadOrigNcbIdM(String trackingCode,Connection conn)throws ApplicationException;
	public void saveUpdateOrigNcbIdM(NcbIdDataM ncbIdM)throws ApplicationException;
	public void deleteNotInKeyNcbId(ArrayList<NcbIdDataM> ncbIdList, String trackingCode)
			throws ApplicationException;
}
