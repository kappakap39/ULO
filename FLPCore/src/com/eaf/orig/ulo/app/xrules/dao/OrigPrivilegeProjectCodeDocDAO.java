package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;

public interface OrigPrivilegeProjectCodeDocDAO {
	
	public void createOrigPrivilegeProjectCodeDocM(PrivilegeProjectCodeDocDataM privilegeProjectCodeDocM)throws ApplicationException;
	public void deleteOrigPrivilegeProjectCodeDocM(String prvlgPrjCdeId, String prvlgDocId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeDocDataM> loadOrigPrivilegeProjectCodeDocM(String prvlgPrjCdeId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProjectCodeDocM(PrivilegeProjectCodeDocDataM privilegeProjectCodeDocM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProjectCodeDoc(ArrayList<PrivilegeProjectCodeDocDataM> privilegeProjectCodeDocList, 
			String prvlgPrjCdeId) throws ApplicationException;
}
