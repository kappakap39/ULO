package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbAccountDataM;

public interface OrigNCBAccountDAO {
	
	public void createOrigNcbAccountM(NcbAccountDataM ncbAccountM)throws ApplicationException;
	public void deleteOrigNcbAccountM(String trackingCode, int seq)throws ApplicationException;	
	public ArrayList<NcbAccountDataM> loadOrigNcbAccountM(String trackingCode, int seq)throws ApplicationException;	
	public ArrayList<NcbAccountDataM> loadOrigNcbAccountM(String trackingCode, int seq,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigNcbAccountM(NcbAccountDataM ncbAccountM)throws ApplicationException;
	public void deleteNotInKeyNcbAccount(ArrayList<NcbAccountDataM> ncbAccountList, String trackingCode)
			throws ApplicationException;
}
