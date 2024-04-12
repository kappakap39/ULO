package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public interface OrigYearlyTawiDAO {
	
	public void createOrigYearlyTawiM(YearlyTawi50DataM yearlyTawiM)throws ApplicationException;
	public void deleteOrigYearlyTawiM(String incomeId, String yearlyTawiId)throws ApplicationException;
	public ArrayList<YearlyTawi50DataM> loadOrigYearlyTawiM(String incomeId, String incomeType)throws ApplicationException;	
	public ArrayList<YearlyTawi50DataM> loadOrigYearlyTawiM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigYearlyTawiM(YearlyTawi50DataM yearlyTawiM)throws ApplicationException;
	public void deleteNotInKeyYearlyTawi(ArrayList<IncomeCategoryDataM> yearlyTawiMList, 
			String incomeId) throws ApplicationException;
}
