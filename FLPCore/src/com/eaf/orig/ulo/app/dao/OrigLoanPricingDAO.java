package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.LoanPricingDataM;

public interface OrigLoanPricingDAO {

	void saveUpdateOrigLoanPricing(LoanPricingDataM loanPricing) throws ApplicationException;

	void deleteNotInKeyLoanPricing(List<LoanPricingDataM> loanPricings, String loanId) throws ApplicationException;

	ArrayList<LoanPricingDataM> loadLoanPricingByLoanId(String loanId) throws ApplicationException;
	ArrayList<LoanPricingDataM> loadLoanPricingByLoanId(String loanId,Connection conn) throws ApplicationException;
	void deleteLoanPricing(String loanId,String pricingId) throws ApplicationException;
}
