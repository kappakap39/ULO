package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;

public interface OrigKVIIncomeDAO {
	
	public void createOrigKVIIncomeM(KVIIncomeDataM kviIncomeM)throws ApplicationException;
	public void deleteOrigKVIIncomeM(String incomeId, String kviId)throws ApplicationException;
	public ArrayList<KVIIncomeDataM> loadOrigKVIIncomeM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<KVIIncomeDataM> loadOrigKVIIncomeM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigKVIIncomeM(KVIIncomeDataM kviIncomeM)throws ApplicationException;
	public void deleteNotInKeyKVIIncome(ArrayList<IncomeCategoryDataM> kviIncomeMList, 
			String incomeId) throws ApplicationException;
}
