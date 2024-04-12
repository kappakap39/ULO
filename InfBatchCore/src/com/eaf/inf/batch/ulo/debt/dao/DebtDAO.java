package com.eaf.inf.batch.ulo.debt.dao;

import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.debt.model.GenerateDebtDataM;
import com.eaf.inf.batch.ulo.debt.model.GenerateDebtReportDataM;

public interface DebtDAO {
	public ArrayList<GenerateDebtDataM> getOutputDebt() throws InfBatchException;
	public ArrayList<GenerateDebtReportDataM> getOutputDebtReport(String systemDate) throws InfBatchException;
	public void deleteBotDebtAmount(String systemDate) throws InfBatchException;
}
