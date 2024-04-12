package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;

public interface OrigPrivilegeProjectCodeTransferDocDAO {
	
	public void createOrigPrivilegeProjectCodeTransferDocM(PrivilegeProjectCodeTransferDocDataM privilegeProjectCodeTransferDocM)throws ApplicationException;
	public void deleteOrigPrivilegeProjectCodeTransferDocM(String prvlgDocId, String prvlgTransferDocId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeTransferDocDataM> loadOrigPrivilegeProjectCodeTransferDocM(String prvlgDocId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProjectCodeTransferDocM(PrivilegeProjectCodeTransferDocDataM privilegeProjectCodeTransferDocM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProjectCodeTransferDoc(ArrayList<PrivilegeProjectCodeTransferDocDataM> privilegeProjectCodeTransferDocList, 
			String prvlgDocId) throws ApplicationException;
}
