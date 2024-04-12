package com.eaf.orig.ulo.app.inc.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;

public interface OrigMonthlyTawiDetailDAO {
	
	public void createOrigMonthlyTawiDetailM(MonthlyTawi50DetailDataM monthlyTawiDetailM)throws ApplicationException;
	public void deleteOrigMonthlyTawiDetailM(String monthlyTawiId, String monthlyTawiDetailId)throws ApplicationException;
	public ArrayList<MonthlyTawi50DetailDataM> loadOrigMonthlyTawiDetailM(String monthlyTawiId)throws ApplicationException;	 
	public void saveUpdateOrigMonthlyTawiDetailM(MonthlyTawi50DetailDataM monthlyTawiDetailM)throws ApplicationException;
	public void deleteNotInKeyMonthlyTawiDetail(ArrayList<MonthlyTawi50DetailDataM> monthlyTawiDtlList, 
			String monthlyTawiId) throws ApplicationException;
}
