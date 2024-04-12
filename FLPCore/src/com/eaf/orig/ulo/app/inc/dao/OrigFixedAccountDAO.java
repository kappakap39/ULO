package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.FixedAccountDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public interface OrigFixedAccountDAO {
	
	public void createOrigFixedAccountM(FixedAccountDataM fixedAccountM)throws ApplicationException;
	public void deleteOrigFixedAccountM(String incomeId, String fixedAccId)throws ApplicationException;
	public ArrayList<FixedAccountDataM> loadOrigFixedAccountM(String incomeId, String incomeType)throws ApplicationException;
	public ArrayList<FixedAccountDataM> loadOrigFixedAccountM(String incomeId, String incomeType,Connection conn)throws ApplicationException;
	public void saveUpdateOrigFixedAccountM(FixedAccountDataM fixedAccountM)throws ApplicationException;
	public void deleteNotInKeyFixedAccount(ArrayList<IncomeCategoryDataM> fixedAccountList, 
			String incomeId) throws ApplicationException;
}
