package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.OtherIncomeDataM;

public interface OrigOtherIncomeDAO {
	
	public void createOrigOtherIncomeM(OtherIncomeDataM othIncomeM)throws ApplicationException;
	public void deleteOrigOtherIncomeM(String incomeId, String othIncomeId)throws ApplicationException;
	public ArrayList<OtherIncomeDataM> loadOrigOtherIncomeM(String incomeId)throws ApplicationException;
	public ArrayList<OtherIncomeDataM> loadOrigOtherIncomeM(String incomeId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigOtherIncomeM(OtherIncomeDataM othIncomeM)throws ApplicationException;
	public void deleteNotInKeyOtherIncome(ArrayList<OtherIncomeDataM> othIncomeList, 
			String incomeId) throws ApplicationException;
}
