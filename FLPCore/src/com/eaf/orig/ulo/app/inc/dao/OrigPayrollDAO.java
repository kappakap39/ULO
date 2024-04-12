package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.PayrollDataM;

public interface OrigPayrollDAO {
	
	public void createOrigPayrollM(PayrollDataM payrollM)throws ApplicationException;
	public void deleteOrigPayrollM(String incomeId, String payrollId)throws ApplicationException;
	public ArrayList<PayrollDataM> loadOrigPayrollM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<PayrollDataM> loadOrigPayrollM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigPayrollM(PayrollDataM payrollM)throws ApplicationException;
	public void deleteNotInKeyPayroll(ArrayList<IncomeCategoryDataM> payrollList, 
			String incomeId) throws ApplicationException;
}
