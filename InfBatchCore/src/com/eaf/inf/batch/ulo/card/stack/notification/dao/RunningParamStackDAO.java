package com.eaf.inf.batch.ulo.card.stack.notification.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackRequestDataM;

public interface RunningParamStackDAO {
	public ArrayList<RunningParamStackRequestDataM> getCardParams()
			throws InfBatchException;

	public int migrateRunningParamStack(Connection conn, String paramType) throws InfBatchException;

	public int purgeRunningParamStack(Connection conn, String paramType, java.sql.Date applicationDate, int purgeDay) throws InfBatchException;

	public int deleteRunningParamStackTemp(Connection conn, String paramType) throws InfBatchException;
}
