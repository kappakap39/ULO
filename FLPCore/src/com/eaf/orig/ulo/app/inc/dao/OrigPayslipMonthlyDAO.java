package com.eaf.orig.ulo.app.inc.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;

public interface OrigPayslipMonthlyDAO {
	
	public void createOrigPayslipMonthlyM(PayslipMonthlyDataM payslipMonthlyM)throws ApplicationException;
	public void deleteOrigPayslipMonthlyM(String payslipId, String payslipMonthlyId)throws ApplicationException;
	public ArrayList<PayslipMonthlyDataM> loadOrigPayslipMonthlyM(String payslipId)throws ApplicationException;	 
	public void saveUpdateOrigPayslipMonthlyM(PayslipMonthlyDataM payslipMonthlyM)throws ApplicationException;
	public void deleteNotInKeyPayslipMonthly(ArrayList<PayslipMonthlyDataM> payslipMonthlyList, 
			String payslipId) throws ApplicationException;
}
