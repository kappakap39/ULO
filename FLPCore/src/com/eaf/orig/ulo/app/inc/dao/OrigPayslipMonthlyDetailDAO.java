package com.eaf.orig.ulo.app.inc.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;

public interface OrigPayslipMonthlyDetailDAO {
	
	public void createOrigPayslipMonthlyDetailM(PayslipMonthlyDetailDataM payslipMonthlyDetailM)throws ApplicationException;
	public void deleteOrigPayslipMonthlyDetailM(String payslipMonthlyId, String payslipMonthlyDetailId)throws ApplicationException;
	public ArrayList<PayslipMonthlyDetailDataM> loadOrigPayslipMonthlyDetailM(String payslipMonthlyId)throws ApplicationException;	 
	public void saveUpdateOrigPayslipMonthlyDetailM(PayslipMonthlyDetailDataM payslipMonthlyDetailM)throws ApplicationException;
	public void deleteNotInKeyPayslipMonthlyDetail(ArrayList<PayslipMonthlyDetailDataM> payslipMonthlyDtlList, 
			String payslipMonthlyId) throws ApplicationException;
}
