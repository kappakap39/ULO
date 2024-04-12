package com.eaf.inf.batch.ulo.card.stack.manager;

import java.sql.Connection;
import java.sql.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.ApplicationDateDAOImpl;
import com.eaf.core.ulo.common.db.control.TransactionHelper;
import com.eaf.core.ulo.common.exception.EngineException;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.card.stack.notification.dao.RunningParamStackDAO;
import com.eaf.inf.batch.ulo.card.stack.notification.dao.RunningParamStackFactory;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackInfoM;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackRequestDataM;

public class RunningParamStackDataManager extends TransactionHelper {
	private static transient Logger logger = Logger.getLogger(RunningParamStackDataManager.class);
	private static String STACK_PURGEDAY_PARAM_TYPE = InfBatchProperty.getInfBatchConfig("STACK_PURGEDAY_PARAM_TYPE");

	@Override
	public void processTransaction(Object objectModel, Connection conn) throws Exception {
		RunningParamStackRequestDataM runningParamStackRequestData = (RunningParamStackRequestDataM) objectModel;

		RunningParamStackInfoM migrateRunningParamStackInfo = null;
		RunningParamStackInfoM purgeRunningParamStackInfo = null;

		if (!InfBatchUtil.empty(runningParamStackRequestData.getRunningParamStackInfoM())) {
			RunningParamStackDAO runningParamStackDAO = RunningParamStackFactory.getRunningParamStackDAO();
			for (RunningParamStackInfoM runningParamStackInfo : runningParamStackRequestData.getRunningParamStackInfoM()) {
				if (InfBatchConstant.RUNNING_PARAM_STACK.PROCESS_MIGRATE.equals(runningParamStackInfo.getProcessType())) {
					migrateRunningParamStackInfo = runningParamStackInfo;
				}
				if (InfBatchConstant.RUNNING_PARAM_STACK.PROCESS_PURGE.equals(runningParamStackInfo.getProcessType())) {
					purgeRunningParamStackInfo = runningParamStackInfo;
				}
			}

			try {
				if (!InfBatchUtil.empty(migrateRunningParamStackInfo)) {
					String paramType = migrateRunningParamStackInfo.getParamType();
					int effectRow = runningParamStackDAO.migrateRunningParamStack(conn, paramType);
					logger.debug("migrateRunningParamStack effectRow >> " + effectRow);
					effectRow = runningParamStackDAO.deleteRunningParamStackTemp(conn, paramType);
					logger.debug("deleteRunningParamStackTemp effectRow >> " + effectRow);
				}

				if (!InfBatchUtil.empty(purgeRunningParamStackInfo)) {
					String paramType = purgeRunningParamStackInfo.getParamType();
					java.sql.Date applicationDate = getApplicationDate();
					int purgeDay = getPurgeDay(paramType);
					int effectRow = runningParamStackDAO.purgeRunningParamStack(conn, paramType, applicationDate, purgeDay);
					logger.debug("purgeRunningParamStack effectRow >> " + effectRow);
				}

			} catch (InfBatchException e) {
				throw new Exception(e.getLocalizedMessage());
			}

		}
	}

	private static int getPurgeDay(String paramType) throws InfBatchException {
		String STACK_PURGEDAY = SystemConfig.getGeneralParam(getParamTypePurgeDay(STACK_PURGEDAY_PARAM_TYPE, paramType));
		try {
			return Integer.parseInt(STACK_PURGEDAY);
		} catch (Exception e) {
			throw new InfBatchException("STACK PURGEDAY " + paramType + " not found. " + e.getLocalizedMessage());
		}
	}

	private static String getParamTypePurgeDay(String stackPurgeDayParamType, String paramType) {
		return stackPurgeDayParamType.replaceAll("\\{PARAM_TYPE\\}", paramType);
	}

	private static Date getApplicationDate() {
		try {
			return new ApplicationDateDAOImpl().getApplicationDate();
		} catch (EngineException e) {
			e.printStackTrace();
		}
		return null;
	}
}
