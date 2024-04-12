package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.LoanTierDataM;

public interface OrigLoanTierDAO {
	
	public void createOrigLoanTierM(LoanTierDataM loanTierM)throws ApplicationException;
	public void deleteOrigLoanTierM(String loanId, String tierId)throws ApplicationException;
	public ArrayList<LoanTierDataM> loadOrigLoanTierM(String loanId)throws ApplicationException;	
	public ArrayList<LoanTierDataM> loadOrigLoanTierM(String loanId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigLoanTierM(LoanTierDataM loanTierM)throws ApplicationException;
	public void deleteNotInKeyLoanTier(ArrayList<LoanTierDataM> loanTierList, 
			String loanId) throws ApplicationException;
}
