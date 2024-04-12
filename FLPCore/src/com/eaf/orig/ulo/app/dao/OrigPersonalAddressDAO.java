package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.AddressDataM;

public interface OrigPersonalAddressDAO {
	
	public void createOrigAddressM(AddressDataM addressM)throws ApplicationException;
	public void deleteOrigAddressM(String personalID, String addressId)throws ApplicationException;	
	public ArrayList<AddressDataM> loadOrigAddressM(String personalID)throws ApplicationException;	
	public ArrayList<AddressDataM> loadOrigAddressM(String personalID,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigAddressM(AddressDataM addressM)throws ApplicationException;
	public void deleteNotInKeyAddress(ArrayList<AddressDataM> addressList, String personalID)
			throws ApplicationException;
}
