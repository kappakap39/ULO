package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;

public interface OrigPrivilegeProjectCodeDAO {
	
	public void createOrigPrivilegeProjectCodeM(PrivilegeProjectCodeDataM privilegeProjectCodeM)throws ApplicationException;
	public void deleteOrigPrivilegeProjectCodeM(String verResultId, String prvlgPrjCdeId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeDataM> loadOrigPrivilegeProjectCodeM(String verResultId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeDataM> loadOrigPrivilegeProjectCodeM(String verResultId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigPrivilegeProjectCodeM(PrivilegeProjectCodeDataM privilegeProjectCodeM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProjectCode(ArrayList<PrivilegeProjectCodeDataM> privilegeProjectCodeList, 
			String verResultId) throws ApplicationException;
}
