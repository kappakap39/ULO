package com.eaf.inf.batch.ulo.creditshield.dao;

import java.sql.Connection;
import java.util.Date;

import com.eaf.core.ulo.common.exception.InfBatchException;

public interface CreditShieldDAO {
	public void refreshCreditShieldSummary(Date currentDate, Connection conn) throws InfBatchException;
}
