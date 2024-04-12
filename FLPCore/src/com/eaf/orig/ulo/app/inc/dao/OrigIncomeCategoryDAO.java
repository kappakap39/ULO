package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public interface OrigIncomeCategoryDAO {
	
	public void createOrigIncomeCategory(IncomeCategoryDataM incomeCategoryM)throws ApplicationException;
	public void deleteOrigIncomeCategory(String incomeId, String incomeType)throws ApplicationException;
	public ArrayList<IncomeCategoryDataM> loadOrigIncomeCategory(String incomeId, String incomeType)throws ApplicationException;	
	public ArrayList<IncomeCategoryDataM> loadOrigIncomeCategory(String incomeId, String incomeType,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigIncomeCategory(IncomeCategoryDataM incomeCategoryM)throws ApplicationException;
	public void deleteNotInKeyIncomeCategory(IncomeInfoDataM incomeDataM) throws ApplicationException;
}
