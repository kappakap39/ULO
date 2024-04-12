package com.eaf.orig.ulo.app.xrules.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;

public interface OrigPrivilegeProductInsuranceDAO {
	
	public void createOrigPrivilegeProductInsuranceM(PrivilegeProjectCodeProductInsuranceDataM privilegeProdInsuranceM)throws ApplicationException;
	public void deleteOrigPrivilegeProductInsuranceM(String prvlgPrjCdeId, String productInsuranceId)throws ApplicationException;
	public ArrayList<PrivilegeProjectCodeProductInsuranceDataM> loadOrigPrivilegeProductInsuranceM(String prvlgPrjCdeId)throws ApplicationException;	 
	public void saveUpdateOrigPrivilegeProductInsuranceM(PrivilegeProjectCodeProductInsuranceDataM privilegeProdInsuranceM)throws ApplicationException;
	public void deleteNotInKeyPrivilegeProductInsurance(ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privilegeProdInsuranceList, 
			String verResultId) throws ApplicationException;
}
