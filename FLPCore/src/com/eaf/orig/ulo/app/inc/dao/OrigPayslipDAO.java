package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;

public interface OrigPayslipDAO {
	
	public void createOrigPayslipM(PayslipDataM payslipM)throws ApplicationException;
	public void deleteOrigPayslipM(String incomeId, String payslipId)throws ApplicationException;
	public ArrayList<PayslipDataM> loadOrigPayslipM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<PayslipDataM> loadOrigPayslipM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigPayslipM(PayslipDataM payslipM)throws ApplicationException;
	public void deleteNotInKeyPayslip(ArrayList<IncomeCategoryDataM> payslipList, 
			String incomeId) throws ApplicationException;
}
