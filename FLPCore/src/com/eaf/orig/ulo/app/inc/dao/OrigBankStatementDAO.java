package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public interface OrigBankStatementDAO {
	
	public void createOrigBankStatementM(BankStatementDataM bankStatementM)throws ApplicationException;
	public void deleteOrigBankStatementM(String incomeId, String bankStatementId)throws ApplicationException;
	public ArrayList<BankStatementDataM> loadOrigBankStatementM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<BankStatementDataM> loadOrigBankStatementM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigBankStatementM(BankStatementDataM bankStatementM)throws ApplicationException;
	public void deleteNotInKeyBankStatement(ArrayList<IncomeCategoryDataM> bankStatementList, 
			String incomeId) throws ApplicationException;
}
