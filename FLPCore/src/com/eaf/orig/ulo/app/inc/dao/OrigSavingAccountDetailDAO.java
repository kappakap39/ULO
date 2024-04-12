package com.eaf.orig.ulo.app.inc.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;

public interface OrigSavingAccountDetailDAO {
	
	public void createOrigSavingAccountDetailM(SavingAccountDetailDataM savingAccountDetailM)throws ApplicationException;
	public void deleteOrigSavingAccountDetailM(String savingAccId, String savingAccDetailId)throws ApplicationException;
	public ArrayList<SavingAccountDetailDataM> loadOrigSavingAccountDetailM(String savingAccId)throws ApplicationException;	 
	public void saveUpdateOrigSavingAccountDetailM(SavingAccountDetailDataM savingAccountDetailM)throws ApplicationException;
	public void deleteNotInKeySavingAccountDetail(ArrayList<SavingAccountDetailDataM> savingAccountDtlMList, 
			String savingAccId) throws ApplicationException;
}
