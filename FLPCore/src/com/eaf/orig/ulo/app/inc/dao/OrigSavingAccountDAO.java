package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;

public interface OrigSavingAccountDAO {
	
	public void createOrigSavingAccountM(SavingAccountDataM savingAccountM)throws ApplicationException;
	public void deleteOrigSavingAccountM(String incomeId, String savingAccId)throws ApplicationException;
	public ArrayList<SavingAccountDataM> loadOrigSavingAccountM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<SavingAccountDataM> loadOrigSavingAccountM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigSavingAccountM(SavingAccountDataM savingAccountM)throws ApplicationException;
	public void deleteNotInKeySavingAccount(ArrayList<IncomeCategoryDataM> savingAccountMList, 
			String incomeId) throws ApplicationException;
}
