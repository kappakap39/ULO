package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;

public interface OrigPrivilegeProductSavingDAO {
	
	public void createOrigPrivilegeProductSavingM(PrivilegeProjectCodeProductSavingDataM privilegeProdSavingM)throws ApplicationException;
	public void deleteOrigPrivilegeProductSavingM(String prvlgPrjCdeId, String productSavingId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeProductSavingDataM> loadOrigPrivilegeProductSavingM(String prvlgPrjCdeId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProductSavingM(PrivilegeProjectCodeProductSavingDataM privilegeProdSavingM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProductSaving(ArrayList<PrivilegeProjectCodeProductSavingDataM> privilegeProdSavingList, 
			String prvlgPrjCdeId) throws ApplicationException;
}
