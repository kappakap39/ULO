package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;

public interface OrigPreviousIncomeDAO {
	
	public void createOrigPreviousIncomeM(PreviousIncomeDataM prevIncomeM)throws ApplicationException;
	public void deleteOrigPreviousIncomeM(String incomeId, String previousIncomeId)throws ApplicationException;
	public ArrayList<PreviousIncomeDataM> loadOrigPreviousIncomeM(String incomeId, String incomeType)throws ApplicationException;	
	public ArrayList<PreviousIncomeDataM> loadOrigPreviousIncomeM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigPreviousIncomeM(PreviousIncomeDataM prevIncomeM)throws ApplicationException;
	public void deleteNotInKeyPreviousIncome(ArrayList<IncomeCategoryDataM> prevIncomeList, 
			String incomeId) throws ApplicationException;
}
