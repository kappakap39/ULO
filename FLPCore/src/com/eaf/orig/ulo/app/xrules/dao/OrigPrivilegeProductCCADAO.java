package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;

public interface OrigPrivilegeProductCCADAO {
	
	public void createOrigPrivilegeProductCCAM(PrivilegeProjectCodeProductCCADataM privilegeProdCCAM)throws ApplicationException;
	public void deleteOrigPrivilegeProductCCAM(String prvlgPrjCdeId, String productCcaId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeProductCCADataM> loadOrigPrivilegeProductCCAM(String prvlgPrjCdeId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProductCCAM(PrivilegeProjectCodeProductCCADataM privilegeProdCCAM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProductCCA(ArrayList<PrivilegeProjectCodeProductCCADataM> privilegeProdCCAList, 
			String prvlgPrjCdeId) throws ApplicationException;
}
