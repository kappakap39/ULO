package com.eaf.orig.ulo.app.dao;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.LoanSetupStampDutyDataM;

public interface LoanSetupStampDutyDAO {
	public void createLoanSetupStampDuty(LoanSetupStampDutyDataM loanSetupStampDutyDataM) throws ApplicationException;
	public LoanSetupStampDutyDataM loadLoanSetupStampDuty(String claimId) throws ApplicationException;
}
