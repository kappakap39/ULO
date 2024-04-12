package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;

public interface OrigIncomeSourceDAO {
	
	public void createOrigIncomeSourceM(IncomeSourceDataM incomeSourceM)throws ApplicationException;
	public void deleteOrigIncomeSourceM(String personalId, String incomeSourceId)throws ApplicationException;
	public ArrayList<IncomeSourceDataM> loadOrigIncomeSourceM(String personalId)throws ApplicationException;
	public ArrayList<IncomeSourceDataM> loadOrigIncomeSourceM(String personalId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigIncomeSourceM(IncomeSourceDataM incomeSourceM)throws ApplicationException;
	public void deleteNotInKeyIncomeSource(ArrayList<IncomeSourceDataM> incomeSourceList, 
			String personalId) throws ApplicationException;
}
