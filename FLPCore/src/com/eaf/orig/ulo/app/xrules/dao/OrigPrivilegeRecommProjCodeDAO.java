package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;

public interface OrigPrivilegeRecommProjCodeDAO {
	
	public void createOrigPrivilegeRecommProjCodeM(PrivilegeProjectCodeRccmdPrjCdeDataM prvRecommProjCodeM)throws ApplicationException;
	public void deleteOrigPrivilegeRecommProjCodeM(String prvlgPrjCdeId, String rccmdPrjCdeId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> loadOrigPrivilegeRecommProjCodeM(String prvlgPrjCdeId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeRecommProjCodeM(PrivilegeProjectCodeRccmdPrjCdeDataM prvRecommProjCodeM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeRecommProjCode(ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> prvRecommProjCodeList, 
			String prvlgPrjCdeId) throws ApplicationException;
}
