package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.LoanFeeDataM;

public interface OrigLoanFeeDAO {
	
	public void createOrigLoanFeeM(LoanFeeDataM loanFeeM)throws ApplicationException;
	public void deleteOrigLoanFeeM(String refId, String refLevel, String loanFeeId)throws ApplicationException;
	public ArrayList<LoanFeeDataM> loadOrigLoanFeeM(String refId, String refLevel)throws ApplicationException;
	public ArrayList<LoanFeeDataM> loadOrigLoanFeeM(String refId, String refLevel,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigLoanFeeM(LoanFeeDataM loanFeeM)throws ApplicationException;
	public void deleteNotInKeyLoanFee(ArrayList<LoanFeeDataM> loanFeeList, 
			String refId, String refLevel) throws ApplicationException;
}
