package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public interface OrigFinInstrumentDAO {
	
	public void createOrigFinInstrumentM(FinancialInstrumentDataM finInstrumentM)throws ApplicationException;
	public void deleteOrigFinInstrumentM(String incomeId, String finInstrument)throws ApplicationException;
	public ArrayList<FinancialInstrumentDataM> loadOrigFinInstrumentM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<FinancialInstrumentDataM> loadOrigFinInstrumentM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigFinInstrumentM(FinancialInstrumentDataM finInstrumentM)throws ApplicationException;
	public void deleteNotInKeyFinInstrument(ArrayList<IncomeCategoryDataM> finInstrumentList, 
			String incomeId) throws ApplicationException;
}
