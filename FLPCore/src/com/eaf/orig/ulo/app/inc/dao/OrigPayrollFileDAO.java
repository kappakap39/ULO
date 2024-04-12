package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.PayrollFileDataM;

public interface OrigPayrollFileDAO {
	
	public void createOrigPayrollFileM(PayrollFileDataM payrollFileM)throws ApplicationException;
	public void deleteOrigPayrollFileM(String incomeId, String payrollFileId)throws ApplicationException;
	public ArrayList<PayrollFileDataM> loadOrigPayrollFileM(String incomeId, String incomeType)throws ApplicationException;	
	public ArrayList<PayrollFileDataM> loadOrigPayrollFileM(String incomeId, String incomeType,Connection conn)throws ApplicationException;
	public void saveUpdateOrigPayrollFileM(PayrollFileDataM payrollFileM)throws ApplicationException;
	public void deleteNotInKeyPayrollFile(ArrayList<IncomeCategoryDataM> payrollFileList, 
			String incomeId) throws ApplicationException;
}
