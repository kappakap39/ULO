package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;

public interface OrigNCBInfoDAO {
	
	public void createOrigNcbInfoM(NcbInfoDataM ncbInfoM)throws ApplicationException;
	public void deleteOrigNcbInfoM(String personalId, String trackingCode)throws ApplicationException;	
	public ArrayList<NcbInfoDataM> loadOrigNcbInfos(String personalID)throws ApplicationException;	
	public ArrayList<NcbInfoDataM> loadOrigNcbInfos(String personalID,Connection conn)throws ApplicationException;
	public NcbInfoDataM loadOrigNcbInfoM(String personalID)throws ApplicationException;	 
	public void saveUpdateOrigNcbInfoM(NcbInfoDataM ncbInfoM)throws ApplicationException;
	public void deleteNotInKeyNcbInfo(ArrayList<NcbInfoDataM> ncbInfoList, String personalID)
			throws ApplicationException;
}
