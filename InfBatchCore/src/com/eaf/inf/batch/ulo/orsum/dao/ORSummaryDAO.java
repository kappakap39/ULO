package com.eaf.inf.batch.ulo.orsum.dao;

import java.sql.Connection;
import java.util.Date;

import com.eaf.core.ulo.common.exception.InfBatchException;

public interface ORSummaryDAO {
	public void refreshOrSummary(Date currentDate, Connection conn) throws InfBatchException;
	public void accumOrPeriodSummary(Connection conn) throws InfBatchException;

}
