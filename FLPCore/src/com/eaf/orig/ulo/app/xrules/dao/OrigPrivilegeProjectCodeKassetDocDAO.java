package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM;

public interface OrigPrivilegeProjectCodeKassetDocDAO {
	
	public void createOrigPrivilegeProjectCodeKassetDocM(PrivilegeProjectCodeKassetDocDataM privilegeProjectCodeKassetDocM)throws ApplicationException;
	public void deleteOrigPrivilegeProjectCodeKassetDocM(String prvlgDocId, String kassetDocId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeKassetDocDataM> loadOrigPrivilegeProjectCodeKassetDocM(String prvlgDocId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProjectCodeKassetDocM(PrivilegeProjectCodeKassetDocDataM privilegeProjectCodeKassetDocM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProjectCodeKassetDoc(ArrayList<PrivilegeProjectCodeKassetDocDataM> privilegeProjectCodeKassetDocList, 
			String prvlgDocId) throws ApplicationException;
}
