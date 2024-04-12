package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public interface OrigIncomeDAO {
	
	public void createOrigIncomeInfoM(IncomeInfoDataM incomeInfoM)throws ApplicationException;
	public void deleteOrigIncomeInfoM(String personalId, String incomeId)throws ApplicationException;
	public ArrayList<IncomeInfoDataM> loadOrigIncomeInfoM(String personalId)throws ApplicationException;
	public ArrayList<IncomeInfoDataM> loadOrigIncomeInfoM(String personalId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigIncomeInfoM(IncomeInfoDataM incomeInfoM)throws ApplicationException;
	public void deleteNotInKeyIncomeInfo(ArrayList<IncomeInfoDataM> incomeInfoList, 
			String personalId) throws ApplicationException;
}
