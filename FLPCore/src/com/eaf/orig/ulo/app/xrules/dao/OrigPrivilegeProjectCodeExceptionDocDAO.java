package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeExceptionDocDataM;

public interface OrigPrivilegeProjectCodeExceptionDocDAO {
	
	public void createOrigPrivilegeProjectCodeExceptionDocM(PrivilegeProjectCodeExceptionDocDataM privilegeProjectCodeExceptionDocM)throws ApplicationException;
	public void deleteOrigPrivilegeProjectCodeExceptionDocM(String prvlgDocId, String exceptDocId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeExceptionDocDataM> loadOrigPrivilegeProjectCodeExceptionDocM(String prvlgDocId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProjectCodeExceptionDocM(PrivilegeProjectCodeExceptionDocDataM privilegeProjectCodeExceptionDocM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProjectCodeExceptionDoc(ArrayList<PrivilegeProjectCodeExceptionDocDataM> privilegeProjectCodeExceptionDocList, 
			String prvlgDocId) throws ApplicationException;
}
