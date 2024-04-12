package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;

public interface OrigMonthlyTawiDAO {
	
	public void createOrigMonthlyTawiM(MonthlyTawi50DataM monthlyTawiM)throws ApplicationException;
	public void deleteOrigMonthlyTawiM(String incomeId, String monthlyTawiId)throws ApplicationException;
	public ArrayList<MonthlyTawi50DataM> loadOrigMonthlyTawiM(String incomeId, String incomeType)throws ApplicationException;	
	public ArrayList<MonthlyTawi50DataM> loadOrigMonthlyTawiM(String incomeId, String incomeType,Connection conn)throws ApplicationException;
	public void saveUpdateOrigMonthlyTawiM(MonthlyTawi50DataM monthlyTawiM)throws ApplicationException;
	public void deleteNotInKeyMonthlyTawi(ArrayList<IncomeCategoryDataM> monthlyTawiMList, 
			String incomeId) throws ApplicationException;
}
