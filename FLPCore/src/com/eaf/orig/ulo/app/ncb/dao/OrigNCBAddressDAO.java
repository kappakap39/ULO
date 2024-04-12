package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbAddressDataM;

public interface OrigNCBAddressDAO {
	
	public void createOrigNcbAddressM(NcbAddressDataM ncbAddressM)throws ApplicationException;
	public void deleteOrigNcbAddressM(String trackingCode, int seq)throws ApplicationException;	
	public ArrayList<NcbAddressDataM> loadOrigNcbAddressM(String trackingCode)throws ApplicationException;
	public ArrayList<NcbAddressDataM> loadOrigNcbAddressM(String trackingCode,Connection conn)throws ApplicationException;
	public void saveUpdateOrigNcbAddressM(NcbAddressDataM ncbAddressM)throws ApplicationException;
	public void deleteNotInKeyNcbAddress(ArrayList<NcbAddressDataM> ncbAddressList, String trackingCode)
			throws ApplicationException;
}
