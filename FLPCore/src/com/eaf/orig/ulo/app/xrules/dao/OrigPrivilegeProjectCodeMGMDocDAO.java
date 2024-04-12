package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeMGMDocDataM;

public interface OrigPrivilegeProjectCodeMGMDocDAO {
	
	public void createOrigPrivilegeProjectCodeMGMDocM(PrivilegeProjectCodeMGMDocDataM privilegeProjectCodeMGMDocM)throws ApplicationException;
	public void deleteOrigPrivilegeProjectCodeMGMDocM(String prvlgDocId, String privlgMgmDocId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeMGMDocDataM> loadOrigPrivilegeProjectCodeMGMDocM(String prvlgDocId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProjectCodeMGMDocM(PrivilegeProjectCodeMGMDocDataM privilegeProjectCodeMGMDocM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProjectCodeMGMDoc(ArrayList<PrivilegeProjectCodeMGMDocDataM> privilegeProjectCodeMGMDocList, 
			String prvlgDocId) throws ApplicationException;
}
