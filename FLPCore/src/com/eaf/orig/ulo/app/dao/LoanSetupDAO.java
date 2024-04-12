package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.Timestamp;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.LoanSetupDataM;
import com.eaf.orig.ulo.model.app.LoanSetupStampDutyDataM;

public interface LoanSetupDAO {
	public void createLoanSetup(LoanSetupDataM loanSetupDataM) throws ApplicationException;
	public LoanSetupDataM loadLoanSetup(String applicationGroupNo) throws ApplicationException;
	public void changeInfoAfterSetupLoan(String applicationGroupNo, String accountNo, Timestamp openDate, String userId, Connection conn) throws ApplicationException;	
}
