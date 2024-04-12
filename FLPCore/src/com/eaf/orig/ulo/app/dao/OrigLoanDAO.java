package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.LoanDataM;

public interface OrigLoanDAO {
	
	public void createOrigLoanM(LoanDataM loanM)throws ApplicationException;
	public void deleteOrigLoanM(String applicationRecordId, String loanId)throws ApplicationException;
	public ArrayList<LoanDataM> loadOrigLoanM(String applicationRecordId)throws ApplicationException;
	public ArrayList<LoanDataM> loadOrigLoanM(String applicationRecordId,Connection conn)throws ApplicationException;
	public ArrayList<LoanDataM> loadOrigLoanVlink(String applicationRecordId,Connection conn)throws ApplicationException;
	public ArrayList<LoanDataM> loadOrigLoanMGroup(String applicationRecordId)throws ApplicationException;	 
	public void saveUpdateOrigLoanM(LoanDataM loanM)throws ApplicationException;
	public void deleteNotInKeyLoan(ArrayList<LoanDataM> loanList, String applicationRecordId) throws ApplicationException;
	public void updateKPLOpenLoanAccountInfo(String applicationGroupNo, String accountNo, Timestamp accountOpenDate, Connection conn)throws ApplicationException;
}
